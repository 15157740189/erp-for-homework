package cn.edu.hziee.springboot01.test;

import cn.edu.hziee.springboot01.model.User;
import cn.edu.hziee.springboot01.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisTest {
    private final static Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    UserService service;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
   // RedisTemplate<String,String>  stringRedisTemplate=redisTemplate;
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest()
    class ApplicationTest {
        @Autowired
        private StringRedisTemplate stringRedisTemplate;
        @Test
        public void test() throws Exception {
            // 保存字符串
            stringRedisTemplate.opsForValue().set("aaa", "111");
            Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
        }
    }

   @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest()
     class ApplicationTests {
        @Autowired
        private RedisTemplate redisTemplate;
        @Test
        public void test() throws Exception {
            User user = new User();
            user.setUserName("无敌的白金之星");
            user.setPassword("嚎哭深渊");
            redisTemplate.opsForValue().set("user_1", user);
            User user1 = (User) redisTemplate.opsForValue().get("user_1");
            System.out.println(user1.getUserName());
        }
    }

    @Test
    public void redisTester() {
        Jedis jedis = new Jedis("localhost", 6379, 100000);
        int i = 0;
        try {
            long start = System.currentTimeMillis();// 开始毫秒数
            while (true) {
                long end = System.currentTimeMillis();
                if (end - start >= 1000) {// 当大于等于1000毫秒（相当于1秒）时，结束操作
                    break;
                }
                i++;
                jedis.set("test" + i, i + "");
            }
        } finally {// 关闭连接
            jedis.close();
        }
        // 打印1秒内对Redis的操作次数
        System.out.println("redis每秒操作：" + i + "次");
    }
    // list数据类型适合于消息队列的场景:比如12306并发量太高，而同一时间段内只能处理指定数量的数据！必须满足先进先出的原则，其余数据处于等待
    @Test
    public void listPushResitTest() {
        // leftPush依次由右边添加
        stringRedisTemplate.opsForList().rightPush("myList", "1");
        stringRedisTemplate.opsForList().rightPush("myList", "2");
        stringRedisTemplate.opsForList().rightPush("myList", "A");
        stringRedisTemplate.opsForList().rightPush("myList", "B");
        // leftPush依次由左边添加
        stringRedisTemplate.opsForList().leftPush("myList", "0");
    }

    @Test
    public void listGetListResitTest() {
        // 查询类别所有元素
        List<String> listAll = stringRedisTemplate.opsForList().range("myList", 0, -1);
        logger.info("list all {}", listAll);
        // 查询前3个元素
        List<String> list = stringRedisTemplate.opsForList().range("myList", 0, 3);
        logger.info("list limit {}", list);
    }

    @Test
    public void listRemoveOneResitTest() {
        // 删除先进入的B元素
        stringRedisTemplate.opsForList().remove("myList", 1, "B");
    }

    @Test
    public void listRemoveAllResitTest() {
        // 删除所有A元素
        stringRedisTemplate.opsForList().remove("myList", 0, "A");
    }
    @Test
    public void hashPutResitTest() {
        // map的key值相同，后添加的覆盖原有的
        stringRedisTemplate.opsForHash().put("banks:12600000", "a", "b");
    }

    @Test
    public void hashGetEntiresResitTest() {
        // 获取map对象
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("banks:12600000");
        logger.info("objects:{}", map);
    }

    @Test
    public void hashGeDeleteResitTest() {
        // 根据map的key删除这个元素
        stringRedisTemplate.opsForHash().delete("banks:12600000", "c");
    }

    @Test
    public void hashGetKeysResitTest() {
        // 获得map的key集合
        Set<Object> objects = stringRedisTemplate.opsForHash().keys("banks:12600000");
        logger.info("objects:{}", objects);
    }

    @Test
    public void hashGetValueListResitTest() {
        // 获得map的value列表
        List<Object> objects = stringRedisTemplate.opsForHash().values("banks:12600000");
        logger.info("objects:{}", objects);
    }

    @Test
    public void hashSize() { // 获取map对象大小
        long size = stringRedisTemplate.opsForHash().size("banks:12600000");
        logger.info("size:{}", size);
    }
}
