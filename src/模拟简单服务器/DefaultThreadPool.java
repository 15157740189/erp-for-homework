package 模拟简单服务器;

;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
   //线程池最大限制数
   private static final int MAX_WORKER_NUMBERS = 10;
  //线程池默认的数量
  private static final int DEFAULT_WORKER_NUMBERS = 5;
  //线程池最小的数量
  private static final int MIN_WORKER_NUMBERS = 1;
  //工作列表，将会向里面插入工作
  private final LinkedList<Job> jobs = new LinkedList<>();
  //工作者列表
  private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
  //工作者线程的数量
  private int workerNum = DEFAULT_WORKER_NUMBERS;
  //线程编号生成
    private AtomicLong threadNum = new AtomicLong();
    public DefaultThreadPool() {
        initializeWorker(DEFAULT_WORKER_NUMBERS);
    }


    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS :
                num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
        initializeWorker(workerNum);
    }

    @Override
    public void execute(Job job) {
        if (job != null){
            //添加一个工作，然后进行通知
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();

        }
    }

    @Override
    public void addWorker(int num) {
        synchronized (jobs){
            //限制新增的worker数量
            if (num + this.workerNum > MAX_WORKER_NUMBERS){
                num = MAX_WORKER_NUMBERS;
            }
            initializeWorker(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs){
            //限制新增的worker数量
            if (num >= this.workerNum){
               throw new IllegalArgumentException("beyond workNum");
            }
            //按照给定数量停止worker
            int count = 0;
            while (count < num){
                Worker worker = workers.get(count);
                if (workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum += num;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    //初始化线程工作者
    private void initializeWorker(int num){
        for (int i = 0; i < num ; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }
    //工作者 辅助消费任务
    class Worker implements Runnable{
        //是否工作
        private volatile boolean running = true;
        @Override
        public void run() {
            while (running){
                Job job = null;
                synchronized (jobs){
                    //如果工作者列表是空的，那么就wait
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    //取出一个job
                    job = jobs.removeFirst();
                    if (job != null){
                        try {
                            job.run();
                        }catch (Exception ex){
                        }
                    }
                }
            }
        }
        public void shutdown(){
            running = false;
        }
    }
}
