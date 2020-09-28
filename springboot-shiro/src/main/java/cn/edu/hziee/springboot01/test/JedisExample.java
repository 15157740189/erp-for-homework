package cn.edu.hziee.springboot01.test;

import cn.edu.hziee.springboot01.util.JedisUtils;
import org.junit.Test;
import redis.clients.jedis.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class JedisExample {
    @Test
  public   void testFirst(){
// 连接redis
        Jedis jedis = new Jedis("localhost", 6379);
         // 默认6379端口
        // string类型
        jedis.set("name", "demo");
        String name = jedis.get("name");
        // list类型
        jedis.lpush("myList", "hello");
        jedis.rpush("myList", "world");
        String lpopVal = jedis.lpop("myList");
        String rpopVal = jedis.rpop("myList");
        // set类型
        jedis.sadd("mySet", "123");
        jedis.sadd("mySet", "456");
        jedis.sadd("mySet", "789");
        jedis.srem("mySet", "789");
        jedis.scard("mySet");
        // zset类型
        jedis.zadd("myZset", 99, "X");
        jedis.zadd("myZset", 90, "Y");
        jedis.zadd("myZset", 97, "Z");
        Double zscore = jedis.zscore("myZset", "Z");
        // 其他
        jedis.incr("intKey");
        jedis.incrBy("intKey", 5);
        jedis.del("intKey");
        // 触发持久化
        // jedis.save();
        // jedis.bgsave()
        // 关闭连接
        jedis.close();
    }
    @Test
    public void testUsePool() {
        // 配置连接池
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        // 创建连接池
        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);
        Jedis jedis = jedisPool.getResource();
        // 使用jedis进行操作
        jedis.set("name", "otherNameVal");
        // 用完之后，一定要手动关闭连接（归还给连接池）
        jedis.close();
    }
    @Test
    public void testJedisPoolUtil() {
        Jedis jedis = JedisUtils.getJedis();
        jedis.set("name123", "demo");
        // 一定要关闭jedis连接
        jedis.close();
    }
    @Test
    public void testExample() {
        // 设置redis集群的节点信息
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.1.3", 6379));
        nodes.add(new HostAndPort("192.168.1.4", 6379));
        nodes.add(new HostAndPort("192.168.1.5", 6379));
        // 创建jediscluster，可以理解为jedis对象
        JedisCluster cluster = new JedisCluster(nodes);
        // 和jedis的使用方式几乎一样
        cluster.set("name", "nameDemo");
        // 使用完毕后，不需要释放连接
        // cluster.close();
    }
    @Test
    public void clusterWithPool() throws UnknownHostException {
        InetAddress host = InetAddress.getLocalHost();
        String ip = host.getHostAddress();
        // 设置redis集群的节点信息
        Set<HostAndPort> nodes = new HashSet<>();
       // nodes.add(new HostAndPort("localhost", 6379));
        nodes.add(new HostAndPort("192.168.1.0", 6379));
        nodes.add(new HostAndPort("192.168.1.3", 6379));
        nodes.add(new HostAndPort("192.168.1.4", 6379));
        nodes.add(new HostAndPort("192.168.1.5", 6379));
        // 配置连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(5);
        jedisPoolConfig.setMaxIdle(3);
        jedisPoolConfig.setMinIdle(2);

        // 创建jediscluster，传入节点列表和连接池配置
        JedisCluster cluster = new JedisCluster(nodes, jedisPoolConfig);
        // 和jedis的使用方式几乎一样
        cluster.set("name", "nameDemo2121");
        // 使用完毕后，不需要释放连接
        // cluster.close();
    }
}
