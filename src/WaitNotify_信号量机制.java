import java.util.Calendar;
import java.util.Date;

public class WaitNotify_信号量机制 {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws Exception{
        Thread waitThread = new Thread(new Wait() , "WaitThread");
        waitThread.start();
        SleepUtils.second(1);
        Thread notifyThread = new Thread(new Notify() , "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{

        @Override
        public void run() {
           //加锁 拥有lock的monitor
            synchronized (lock){
                // 当条件不满足时，继续wait，同时释放了lock锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + "flag is true , running at" + Calendar.getInstance().getTime());
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                //完成工作
                System.out.println(Thread.currentThread() + "flag is false , running at" + Calendar.getInstance().getTime());
            }
        }
    }

    static class Notify implements Runnable{

        @Override
        public void run() {
            //加锁 拥有lock的monitor
            synchronized (lock){
                 //获取lock的锁，然后进行通知，通知时不会释放lock的锁
                //直到当前线程释放了lock后，WaitThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + "hold lock. notify at" + Calendar.getInstance().getTime());
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }
            //再次加锁
            synchronized (lock){
                System.out.println(Thread.currentThread() + "hold lock again . sleep at" + Calendar.getInstance().getTime());
                SleepUtils.second(5);
            }
        }
    }
}
