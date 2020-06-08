package cn.edu.hziee.springbootinterface;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


import javax.sql.DataSource;


@SpringBootApplication
@EnableScheduling
@EnableWebSocket
public class SpringbootInterfaceApplication  {

/*    @Value("${rabbitmq.queue.msg}")
    private String msgQueueName=null;
    @Value("${rabbitmq.queue.user}")
    private String userQueueName=null;
    @Bean
    public Queue creatQueueMsg(){
        //创建字符串消息队列， boolean 值代表是否持久化消息
      return new Queue(msgQueueName,true);
    }
    @Bean
    public Queue creatQueueUser(){
        //创建用户消息队列， boolean 值代表是否持久化消息
        return new Queue(userQueueName,true);
    }*/
    public static void main(String[] args) {
        SpringApplication.run(SpringbootInterfaceApplication.class, args);
    }

}
