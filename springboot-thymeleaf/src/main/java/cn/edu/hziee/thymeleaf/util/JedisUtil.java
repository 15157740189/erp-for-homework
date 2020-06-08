package cn.edu.hziee.thymeleaf.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class JedisUtil {

   private static JedisPool jedisPool;

    public static Jedis getJedis() {
        return jedisPool.getResource();
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
