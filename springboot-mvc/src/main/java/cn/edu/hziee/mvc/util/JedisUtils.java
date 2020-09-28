package cn.edu.hziee.mvc.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
    private static JedisPool jedisPool;

    static {
        // 配置连接池
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(3);
        config.setMinIdle(2);
        // 创建连接池
        jedisPool = new JedisPool(config, "localhost", 6379);
    }
    /**
     * 获取redis连接
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}

