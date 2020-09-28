package AQS;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TwinsLockTest {

    public static void main(String[] args) {
        final Lock lock = new TwinsLock();
        final Lock mutexLock = new Mutex();
        //final Lock lock = new ReentrantReadWriteLock().writeLock();
        class Worker extends Thread{
            @Override
            public void run() {
                while (true){
                    try {
                        lock.lock();
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                        System.out.println();
                    } finally {
                        lock.unlock();
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }
}
