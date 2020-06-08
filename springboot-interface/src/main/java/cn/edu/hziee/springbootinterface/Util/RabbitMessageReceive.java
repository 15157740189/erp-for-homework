package cn.edu.hziee.springbootinterface.Util;

import cn.edu.hziee.springbootinterface.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class RabbitMessageReceive {
    @RabbitListener(queues = {"${rabbitmq.queue.msg}"})
    public void  receiveMsg(String msg){
      log.info("收到消息："+ msg);
    }
    @RabbitListener(queues = {"${rabbitmq.queue.user}"})
    public void  receiveUser(User user){
        log.info("收到用户消息："+ user);
    }
}
