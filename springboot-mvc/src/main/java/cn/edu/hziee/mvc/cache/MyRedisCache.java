package cn.edu.hziee.mvc.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Component
public class MyRedisCache implements Cache {
/*    @Autowired
    JedisPool jedisPool;
    Jedis jedis=jedisPool.getResource();*/


    private static RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        MyRedisCache.redisTemplate = redisTemplate;
    }


    private String id;//namespace
    private final ReadWriteLock lock= new ReentrantReadWriteLock();
/*public static MyRedisCache redisCache;
    @PostConstruct
    public void init(){
        redisCache=this;
        redisCache.redisTemplate=this.redisTemplate;
    }*/
    public MyRedisCache() { }
    public MyRedisCache(String id) {
        this.id=id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        System.out.println("缓存数据"+o1.toString());
     redisTemplate.opsForValue().set(o.toString(),o1);
    }

    @Override
    public Object getObject(Object key) {
        log.info(key.getClass().toString());
        Object cache = redisTemplate.opsForValue().get(key.toString());
        if (cache != null){
            System.out.println("缓存命中");
            return cache;
    }else {
            System.out.println("缓存未命中");
            return null;
        }
    }

    @Override
    public Object removeObject(Object o) {
        redisTemplate.delete(o.toString());
        return null;
    }

    @Override
    public void clear() {  //当一个mapper触发了写，缓存清除
     Set<String> keys=redisTemplate.keys("*"+this.getId()+"*");
     redisTemplate.delete(keys);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }
}
