package cn.edu.hziee.springbootinterface.Service.Impl;

import cn.edu.hziee.springbootinterface.Entity.User;

import cn.edu.hziee.springbootinterface.Service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RabbitMqServiceImpl implements RabbitMqService,
        //实现 ConfirmCallback 接口，这样可以回调
        RabbitTemplate.ConfirmCallback {

    @Value("${rabbitmq.queue.msg}")
    private String  msgRouting=null;

    @Value("${rabbitmq.queue.user}")
    private String userRouting =null;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Override
    public void sendMsg(String msg) {
        log.info("发送消息："+msg);
        //设置回调
        rabbitTemplate.setConfirmCallback(this);
        //发送消息，通过 msgRouting 确定队列
        rabbitTemplate.convertAndSend(msgRouting,msg);
    }

    @Override
    public void sendUser(User user) {
        log.info("发送用户消息："+user);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(userRouting,user);
    }

    @Override
    public void confirm(CorrelationData correlationData,
                        boolean ack, String cause) {

        if (ack){
            log.info("消息成功回调");
        } else {
            log.info("消息消费失败："+cause);
        }

    }
}
