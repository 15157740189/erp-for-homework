package cn.edu.hziee.mvc.config;

import cn.edu.hziee.mvc.redis.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    public JedisPool redisPoolFactory() {
        JedisPool jedisPool;
        // 配置连接池
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(3);
        config.setMinIdle(2);
        // 创建连接池
        jedisPool = new JedisPool(config, "localhost", 6379);
        return jedisPool;
    }
    /*@Bean(name ="redisCacheManager")
    public CacheManager initRedisCacheManager(@Autowired RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //设置超时时间为 10 分钟，单位为秒
        cacheManager.setDefaultExpiration(600); //设置缰存名称
        List<String> cacheNames = new ArrayList<String>();
        cacheNames.add("redisCacheManager");
        cacheManager.setCacheNames(cacheNames);
        return cacheManager;
    }*/
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        //配置监听通道chat
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        //配置处理消息的类和方法
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
