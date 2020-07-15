package cn.edu.hziee.wxstudio.延时队列;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Order order1=new Order("order1",5, TimeUnit.SECONDS);
        Order order2=new Order("order2",10, TimeUnit.SECONDS);
        Order order3=new Order("order3",15, TimeUnit.SECONDS);
        DelayQueue<Order> delayQueue=new DelayQueue<>();
        delayQueue.put(order1);
        delayQueue.put(order2);
        delayQueue.put(order3);
        System.out.println("订单延迟队列开始时间:"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        while (delayQueue.size()!=0){
            /**
             * 取队列头部元素是否过期
             */
            Order task=delayQueue.poll();
            if (task!=null){
                System.out.format("订单:{%s}被取消, 取消时间:{%s}\n", task.name,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            Thread.sleep(1000);
        }
    }
}
