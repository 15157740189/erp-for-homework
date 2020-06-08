package cn.edu.hziee.springboot01.cache;

import cn.edu.hziee.springboot01.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Slf4j
public class RedisCache<K,V> implements Cache<K,V> {
  private  JedisUtil jedisUtil=new JedisUtil();
    private final String CACHE_PREFIX="kana-cache";
    private byte[] getKey(K k){
        if (k instanceof String){
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }
    @Override
    public V get(K k) throws CacheException {
        log.info("从RedisCache中获取授权数据");
        byte[] value=jedisUtil.get(getKey(k));
        if (value!=null)
            return (V) SerializationUtils.deserialize(value);
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key=getKey(k);
        byte[] value=SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key=getKey(k);
        byte[] value=jedisUtil.get(key);
        jedisUtil.del(key);
        if (value!=null)
            return (V) SerializationUtils.deserialize(value);
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        Set<byte[]> keys= jedisUtil.keys(CACHE_PREFIX);
        return keys.size();
    }

    @Override
    public Set<K> keys() {
        Set<byte[]> keys= jedisUtil.keys(CACHE_PREFIX);
        Set<K> newKeys= new HashSet<>();
        if (CollectionUtils.isEmpty(keys)){
            return null;
        }
        for (byte[] key:keys) {
           K k= (K) SerializationUtils.deserialize(key);
            newKeys.add(k);
        }
        return newKeys;
    }

    @Override
    public Collection<V> values() {
        Set<byte[]> keys= jedisUtil.keys(CACHE_PREFIX);
        Set<V> values=new HashSet<>();
        if (CollectionUtils.isEmpty(keys)){
            return values;
        }
        for (byte[] key: keys) {
            V v= (V) SerializationUtils.deserialize(key);
            values.add(v);
        }
        return values;
    }
}
