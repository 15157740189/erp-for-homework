package cn.edu.hziee.thymeleaf.produce;

import cn.edu.hziee.thymeleaf.model.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

   public void send(Order order) throws Exception{
     /*  rabbitTemplate.convertAndSend(exchange,
               routing,路由件
               order,消息体内容
               correlationData)消息体唯一id;*/
       CorrelationData correlationData=new CorrelationData();
       correlationData.setId(order.getMessageId());
       rabbitTemplate.convertAndSend("order-exchange",
               "order.kana",
               order,
               correlationData);
   }
}
