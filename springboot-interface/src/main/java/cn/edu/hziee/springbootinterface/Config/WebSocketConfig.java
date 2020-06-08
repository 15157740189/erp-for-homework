package cn.edu.hziee.springbootinterface.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
//使用 STOMP 协议
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //增加一个聊天服务端点
        registry.addEndpoint("/socket").withSockJS();
        //增加一个用户服务端点
        registry.addEndpoint("/wsuser").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端订阅路径前缀
        registry.enableSimpleBroker("/sub","/queue");
       //服务端点请求前缀
        registry.setApplicationDestinationPrefixes("/request");
    }

   /* @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new  ServerEndpointExporter();
    }*/
}
