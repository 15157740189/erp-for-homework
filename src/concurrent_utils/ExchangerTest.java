package concurrent_utils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {

    private static final Exchanger<String> exchanger = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        threadPool.execute(() -> {
            String A = "银行流水A"; //录入数据
            try {
                exchanger.exchange(A);
            } catch (InterruptedException e) {
            }
        });

     threadPool.execute(() -> {
        String B = "银行流水B"; //录入数据
        try {
         String A = exchanger.exchange(B);
            System.out.println("录入数据是否相等：" + A.equals(B) + " A: " + A + " B: " + B);
        } catch (InterruptedException e) {
        }
    });
     threadPool.shutdown();
}

 }

