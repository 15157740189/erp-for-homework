package cn.edu.hziee.springboot01.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;
@Slf4j
@Component
public class JedisUtil {

/*   @Autowired
   private  JedisPool jedisPool;*/

    public Jedis getJedis() {
       /* if (jedisPool==null)
        log.error("jedisPool注入失败");
        return jedisPool.getResource();*/
       return JedisUtils.getJedis();
    }

    public byte[] expire(byte[] key, int time) {
        Jedis jedis=getJedis();
        try {
            jedis.expire(key,time);
            return key;
        }finally {
            jedis.close();
        }
    }
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis=getJedis();
        try {
            jedis.set(key,value);
            return value;
        }finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis=getJedis();
        try {
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }
    public  void del(byte[] key) {
        Jedis jedis=getJedis();
        try {
             jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_seesion_prefix) {
        Jedis jedis=getJedis();
        try {
            return jedis.keys((shiro_seesion_prefix+"*").getBytes());
        }finally {
            jedis.close();
        }
    }
}
