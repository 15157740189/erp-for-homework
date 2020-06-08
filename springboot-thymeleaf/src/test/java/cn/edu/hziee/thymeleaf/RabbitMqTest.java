package cn.edu.hziee.thymeleaf;

import cn.edu.hziee.thymeleaf.model.Order;
import cn.edu.hziee.thymeleaf.produce.OrderSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Slf4j
public class RabbitMqTest {
  @Autowired
    OrderSender orderSender;
    @Test
  public void testSend() throws Exception{
        Order order=new Order();
        order.setId("20200325205101");
        order.setName("hello,rabbitmq,测试订单");
        order.setMessageId(System.currentTimeMillis()+"$"+ UUID.randomUUID().toString());
        orderSender.send(order);
    }
}
