package cn.edu.hziee.springbootinterface.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class RedisService {
    @Autowired
    RedisTemplate redisTemplate;

    /**
     *写入缓存
     * @param key
     * @param value
     * @return
     */
       public boolean set(final String key,Object value){
          boolean result=false;
          try {
              ValueOperations<String, Object> ops=
                      redisTemplate.opsForValue();
              ops.set(key,value);
              result=true;
          }catch (Exception e){
              e.printStackTrace();
          }
          return result;
       }
    /**
     *写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean setEx(final String key,Object value,Long expireTime){
        boolean result=false;
        try {
            ValueOperations<String, Object> ops=
                    redisTemplate.opsForValue();
            ops.set(key,value);
            redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
            log.info((String) ops.get(key));
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     *判断缓存中是否有对应key
     * @param key
     * @return
     */
    public boolean exists(final String key){
       return redisTemplate.hasKey(key);
    }

    /**
     *读取缓存
     * @param key
     * @return
     */
    public Object get(final String key){
      Object result=null;
        ValueOperations<String, Object> ops=
                redisTemplate.opsForValue();
        result=ops.get(key);
        return result;
    }
    /**
     *删除对应key
     * @param key
     * @return
     */
    public boolean remove(final String key){
        if (exists(key)){
            Boolean delete=redisTemplate.delete(key);
            return delete;
        }
        return false;
    }


}
