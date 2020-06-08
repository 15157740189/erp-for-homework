package cn.edu.hziee.springboot01.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

public class RedisCacheManager implements CacheManager {

private RedisCache redisCache=new RedisCache();
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
