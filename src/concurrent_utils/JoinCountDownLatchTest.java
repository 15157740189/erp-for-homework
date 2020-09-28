package concurrent_utils;

import java.util.concurrent.CountDownLatch;

public class JoinCountDownLatchTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(2 );

    public static void main(String[] args) throws InterruptedException {

        Thread parse1 = new Thread(() -> {
            System.out.println("parse1 finish");
        });
        Thread parse2 = new Thread(() -> {
            System.out.println("parse2 finish");
        });
        parse1.start();
        parse2.start();
        parse1.join();
        parse2.join();
        System.out.println("all parse finish");

        //使用CountDownLatch
        Thread parse = new Thread(() -> {
            System.out.println("1 finish");
            countDownLatch.countDown();
            System.out.println("2 finish");
            countDownLatch.countDown();
        });
        parse.start();
        countDownLatch.await();
        System.out.println("3 finish");
    }
}
