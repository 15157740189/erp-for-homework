package cn.edu.hziee.thymeleaf.consumer;

import cn.edu.hziee.thymeleaf.model.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class OrderReceive {
   private HashMap<Long,Object> messageMap=new HashMap<>(16);
    static long ID=0L;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue",durable = "true"),
            exchange = @Exchange(name = "order-exchange",durable = "true",type = "topic"),
            key = "order.*"

    ))
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,
                               @Headers Map<String,Object>headers,
                               Channel channel) throws Exception{
        //消费者操作
            log.info("收到消息，开始收费");
            log.info(("订单ID："+order.getId()));
            messageMap.put(ID++,order);
        Long deliveryTag= (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag,false);
    }
    public HashMap<Long,Object> getMessageMap(){
        return messageMap;
    }
}
