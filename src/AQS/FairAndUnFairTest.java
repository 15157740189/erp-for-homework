package AQS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnFairTest {

    private static MyReentrantLock fairLock = new MyReentrantLock(true);
    private static MyReentrantLock unFairLock = new MyReentrantLock(false);


    public static void main(String[] args) {
        TestLock(fairLock);
        SleepUtils.second(5);
        TestLock(unFairLock);
    }

    public static void TestLock(MyReentrantLock lock){
        for (int i = 0; i < 5 ; i++) {
            Thread job = new Job(lock);
            job.start();
        }
    }

    private static class Job extends Thread{
        private MyReentrantLock lock;

        public Job(MyReentrantLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName());
            System.out.println(lock.getQueueThreads());
            System.out.println();
            lock.unlock();
        }
    }

    private static class MyReentrantLock extends ReentrantLock{
        public MyReentrantLock(boolean fair){
            super(fair);
        }
        public Collection<Thread> getQueueThreads(){
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }

    }
}
