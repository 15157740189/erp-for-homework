package cn.edu.hziee.mvc.redis;

import cn.edu.hziee.mvc.cache.MyRedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements MessageListener {
   private static StringRedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisMessageListener.redisTemplate = redisTemplate;
    }
        @Override
        public void onMessage(Message message, byte[] pattern) {
            //获取消息
            byte[] body=message.getBody();
            //使用值序列化器转换
            String msgBody= (String) redisTemplate.getValueSerializer().deserialize(body);
            System.err.println(msgBody);
            //获取channel
            byte[] channel=message.getChannel();
            //使用字符序列化器转换
            String channelStr=redisTemplate.getStringSerializer().deserialize(channel);
            System.err.println(channelStr);
            //渠道名称转换
            String bytesStr=new String(pattern);
            System.err.println(bytesStr);
        }
        public void sendMessage(String channel,String message){
            redisTemplate.convertAndSend(channel,message);
        }

    }

