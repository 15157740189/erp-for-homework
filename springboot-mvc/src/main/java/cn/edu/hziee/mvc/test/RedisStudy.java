package cn.edu.hziee.mvc.test;


import cn.edu.hziee.mvc.entity.Role;

import cn.edu.hziee.mvc.redis.RedisMessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class RedisStudy {
    @Autowired
    StringRedisTemplate redisTemplate;

    JedisPoolConfig poolConfig = new JedisPoolConfig();
    private final static Logger logger = LoggerFactory.getLogger(RedisStudy.class);

    @Test
    public void Test1() {
        Jedis jedis = new Jedis("localhost", 6379);
        int i = 0;
        try {
            long start = System.currentTimeMillis();
            while (true) {
                long end = System.currentTimeMillis();
                if (end - start >= 1000) {
                    break;
                }
                i++;
                jedis.set("test" + i, i + "");
            }
        } finally {
            jedis.close();
        }
        System.out.println("redis每秒操作：" + i + "次");
    }

    @Test
    public void Test2() {
        poolConfig.setMaxIdle(50);//最大空闲数
        poolConfig.setMaxTotal(100);//最大连接数
        poolConfig.setMaxWaitMillis(20000);//最大等待毫秒数
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
     /*   RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(new RedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());*/
    }

    @Test
    public void Test3() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("role_name_1");
        role.setNote("note_1");
        SessionCallback callback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.boundValueOps("role_1").set(role);
                return operations.boundValueOps("role_1").get();
                //保证在同一个连接 资源损耗较小
            }
        };
        Role saveRole = (Role) redisTemplate.execute(callback);
        System.out.println(saveRole.getId());
    }

    @Test
    //String
    public void Test4() {
        redisTemplate.opsForValue().set("key1", "value1");
        redisTemplate.opsForValue().set("key2", "value2");
        String value1 = redisTemplate.opsForValue().get("key1");
        System.out.println(value1);
        redisTemplate.delete("key1");
        Long length = redisTemplate.opsForValue().size("key2");
        System.out.println(length);
        String oldValue = redisTemplate.opsForValue().getAndSet("key2", "new_value2");
        System.out.println(oldValue);
        String rangeValue2 = redisTemplate.opsForValue().get("key2", 3, 7);
        System.out.println(rangeValue2);
        int newLen = redisTemplate.opsForValue().append("key2", "_app");
        System.out.println(newLen);
        String appendValue2 = redisTemplate.opsForValue().get("key2");
        System.out.println(appendValue2);
    }

    @Test
    //hash
    public void Test5() {
        String key = "hash";
        Map<String, String> map = new HashMap<>();
        map.put("f1", "value1");
        map.put("f2", "value2");
        //相当于hmset
        redisTemplate.opsForHash().putAll(key, map);
        //相当于hset
        redisTemplate.opsForHash().put(key, "f3", "6");
        //相当于hexists key filed
        redisTemplate.opsForHash().hasKey("key", "f3");
        //相当于hgetall
        Map keyValMap = redisTemplate.opsForHash().entries("key");
        redisTemplate.opsForHash().increment(key, "f3", 2);
        //相当于 hincrbyfloat 命令
        redisTemplate.opsForHash().increment(key, "f3", 0.88);
        // 相当于 hvals 命令
        List valueList = redisTemplate.opsForHash().values(key);
        // 相当于 hkeys 命令
        Set keyList = redisTemplate.opsForHash().keys(key);
        List<String> fieldList = new ArrayList<String>();
        fieldList.add("f1");
        fieldList.add("f2");
        // 相当于 hmget 命令
        List valueList2 = redisTemplate.opsForHash().multiGet(key, keyList);
        // 相当于 hsetnx 命令
        boolean success = redisTemplate.opsForHash().putIfAbsent(key, "f4", "val4");
        System.out.println(success);
        // 相当于 hdel 命令
        Long result = redisTemplate.opsForHash().delete(key, "f1", "f2");
        System.out.println(result);
    }

    @Test
    //linked-list
    public void Test6() throws IOException {
        //把 node3 插入链表 list
        redisTemplate.opsForList().leftPush("list", "node3");
        List<String> nodeList = new ArrayList<>();
        for (int i = 2; i >= 1; i--) {
            nodeList.add("node" + i);
        }//相当于lpush把多个值插入左链表
        redisTemplate.opsForList().leftPushAll("list", nodeList);
        //rpush
        redisTemplate.opsForList().rightPush("list", "node4");
        //获取下标为0的节点
        String node = redisTemplate.opsForList().index("list", 0);
        //获取链表长度
        long size = redisTemplate.opsForList().size("list");
        //从左边弹出一个节点
        String lpop = redisTemplate.opsForList().leftPop("list");
        //从右边弹出一个节点
        String rpop = redisTemplate.opsForList().rightPop("list");
        //注意，需要使用更为底层的命令才能操作 linsert 命令
        // 使用 linsert 命令在node2 前插入一个节点
        redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8"),
                RedisListCommands.Position.BEFORE, "node2".getBytes("utf-8"), "before node ".getBytes("utf-8"));
        //使用 linsert 命令在 node2 后插入一个节点
        redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8"), RedisListCommands.Position.AFTER, "node2"
                .getBytes("utf-8"), "after_node".getBytes("utf-8"));
        //判断 list 是否存在，如果存在则从左边插入 head 节点
        redisTemplate.opsForList().leftPushIfPresent("list", "head");
        //判断 list 是否存在，如果存在则从右边插入 end 节点
        redisTemplate.opsForList().rightPushIfPresent("list", "end");
        //从左到右，或者下标从 0 到 10 的节点元素
        List valueList = redisTemplate.opsForList().range("list", 0, 10);
        nodeList.clear();
        for (int i = 1; i <= 3; i++) {
            nodeList.add("node");
//在链表左边插入三个值为 node 的节点
            redisTemplate.opsForList().leftPushAll("list", nodeList);
//从左到右删除至多三个 node 节点
            redisTemplate.opsForList().remove("list", 3, "node");
//给链表下标为 0 的节点设置新值
            redisTemplate.opsForList().set("list", 0, "new_head_value ");
        }
        redisTemplate.opsForList().rightPop("list",1, TimeUnit.SECONDS);
        redisTemplate.opsForList().leftPop("list",1, TimeUnit.SECONDS);
        nodeList.clear();
        for (int i = 0; i <3 ; i++) {
            nodeList.add("data"+i);
        }
        redisTemplate.opsForList().leftPushAll("list2",nodeList);
        redisTemplate.opsForList().rightPopAndLeftPush("list1","list2");
        redisTemplate.opsForList().rightPopAndLeftPush("list1","list2",1, TimeUnit.SECONDS);
    }
    @Test
    //set
    public void Test7(){
        Set set=null;
        redisTemplate .boundSetOps ("set1").add ("v1","v2","v3","v4","v5","v6");
        redisTemplate .boundSetOps ("set2").add("v0","v2","v4","v6","v8");
               //求集合长度
        Long size=redisTemplate.opsForSet().size ("set1");
        System.out.println(size);
        //求差集
        set= redisTemplate.opsForSet().difference ("set1","set2");
        System.out.print("差集：");
        set.forEach(System.out::print);
        //求并集
        set= redisTemplate.opsForSet ().intersect ("set1","set2");
        System.out.print("并集：");
        set.forEach(System.out::print);
        //判断是否集合中的元素
        boolean exists= redisTemplate.opsForSet().isMember ("set1","v1");
        System.out.println("是否为集合元素 "+exists);
        //获取集合所有元素
         set = redisTemplate. opsForSet ().members ("set1");
        set.forEach(System.out::print);
        // 从集合中随机弹出一个元素
        String val= redisTemplate.opsForSet().pop ("set1");
        System.out.println(val);
        // 随机获取一个集合的元素
         val= redisTemplate . opsForSet().randomMember ("set1");
                //随机获取2个集合的元素
        List list= redisTemplate.opsForSet() .randomMembers ("set1", 2L);
        list.forEach(System.out::print);
                // 删除一个集合的元素，参数可以是多个
        redisTemplate.opsForSet ().remove ("set1","v1");
                // 求两个集合的并集
        redisTemplate .opsForSet().union ("set1","set2");
                // 求两个集合的差集，并保存到集合 diff set 中
        redisTemplate .opsForSet().differenceAndStore("set1","set2","diff set ");
                // 求两个集合的交集，并保存到集合 inter set 中
        redisTemplate . opsForSet() . intersectAndStore ("set1","set2","inter set") ;
                // 求两个集合的并集，并保存到集合 union set 中
        redisTemplate.opsForSet () . unionAndStore ("set1","set2","union set") ;
    }
    @Test
    //基数（计数器）
    public void Test8(){
        redisTemplate.opsForHyperLogLog().add("HyperLogLog","a","b","c","d","a");
        redisTemplate.opsForHyperLogLog().add("HyperLogLog2","a");
        redisTemplate.opsForHyperLogLog().add("HyperLogLog2","z");
        Long size=redisTemplate.opsForHyperLogLog().size("HyperLogLog");
        System.err.println(size);
        size=redisTemplate.opsForHyperLogLog().size("HyperLogLog2");
        System.err.println(size);
        redisTemplate.opsForHyperLogLog().union("des_key","HyperLogLog","HyperLogLog2");
        size=redisTemplate.opsForHyperLogLog().size("des_key");
        System.err.println(size);
    }
    @Test
    //事务
    public void Test9(){
        SessionCallback callback=new SessionCallback() {
            @Override
            public Object execute(RedisOperations ops) throws DataAccessException {
                ops.multi();
                ops.boundValueOps("key3").set("new_value3");
                String value=(String)ops.boundValueOps("key3").get();
                System.out.println("事务执行前，语句不被执行，故value = "+value);
                //执行事务
                List list=ops.exec();
                value= redisTemplate.opsForValue().get("key3");
                return value;
            }
        };
        String value=(String) redisTemplate.execute(callback);
        System.out.println(value);
    }
    @Test
    //流水线
    public  void Test10(){

        poolConfig.setMaxIdle(50);//最大空闲数
        poolConfig.setMaxTotal(100);//最大连接数
        poolConfig.setMaxWaitMillis(20000);//最大等待毫秒数
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        long start=System.currentTimeMillis();// 开启流水线
        Pipeline pipeline=jedis.pipelined();
        for (int i = 0; i <10000 ; i++) {
          int j=i+1;
          pipeline.set("pipeline_key_"+j,"pipeline_value_"+j);
          pipeline.get("pipeline_key_"+j);
        }
        // pipeline.sync();//执行同步不返回结果
        // pipeline.syncAndReturnAll(); //将返回执行过的命令返回的 List 列表结果
        List result= pipeline.syncAndReturnAll();
        SessionCallback callback=new SessionCallback() {
            @Override
            public Object execute(RedisOperations ops) throws DataAccessException {
                for (int i = 0; i <10000 ; i++) {
                    int j=i+1;
                   redisTemplate.opsForValue().set("pipeline_key_"+j,"pipeline_value_"+j);
                   redisTemplate.opsForValue().get("pipeline_key_"+j);
                }
                return null;
            }
        };
        List resultList=redisTemplate.executePipelined(callback);
        Long end=System.currentTimeMillis();
        System.err.println("耗时"+(start-end)+"毫秒");
    }
    @Test
    //超时测试
    public void Test11(){
       SessionCallback callback= new SessionCallback() {
           @Override
           public Object execute(RedisOperations ops) throws DataAccessException {
               ops.boundValueOps("key5").set("new_value5");
               String keyValue= (String) ops.boundValueOps("key5").get();
               Long expSecond=ops.getExpire("key5");
               System.err.println(expSecond);
               boolean b=false;
               b=ops.expire("key5",120L,TimeUnit.SECONDS);
               b=ops.persist("key5");
               Long l=0L;
               l=ops.getExpire("key5");
               Long now=System.currentTimeMillis();
               Date date=new Date();
               date.setTime(now + 120000);
               ops.expireAt("key",date);
               return null;
           }
       };
        redisTemplate.execute(callback);
    }
    @Test
    //lua
    public void Test12(){
        /*Jedis jedis= (Jedis) redisTemplate.getConnectionFactory()
                .getConnection().getNativeConnection();*/
        poolConfig.setMaxIdle(50);//最大空闲数
        poolConfig.setMaxTotal(100);//最大连接数
        poolConfig.setMaxWaitMillis(20000);//最大等待毫秒数
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        String helloLua= (String) jedis.eval("return 'hello lua'");
        System.out.println(helloLua);
        jedis.eval ("redis.call ('set', KEYS [1] , ARGV[1])", 1 , "lua-key" , "lua-value");
        String luaValue=jedis.get("lua-key");
        System.out.println(luaValue);
        String sha1= jedis.scriptLoad("redis.call ('set', KEYS [1] , ARGV[1])");
        System.out.println(sha1);
        jedis.evalsha(sha1,1,new String[]{"sha-key","sha-value"});
        String shaValue=jedis.get("sha-key");
        System.out.println(shaValue);
        jedis.close();
    }

   @Test
    public void messageSendTest(){
       String channel="chat";
       redisTemplate.convertAndSend(channel,"订阅消息发布");

   }

}

