package concurrent_utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2 , new A());

    public static void main(String[] args) {
        Thread parse = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
            }
            System.out.println(1);
        });
        parse.start();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
        }
        System.out.println(2);
    }
    static class A implements Runnable{

        @Override
        public void run() {
            System.out.println(3);
        }
    }
}
