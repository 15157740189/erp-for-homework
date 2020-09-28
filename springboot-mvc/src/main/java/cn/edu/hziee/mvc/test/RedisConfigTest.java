package cn.edu.hziee.mvc.test;

import cn.edu.hziee.mvc.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Slf4j
public class RedisConfigTest {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    //lua 封装对象
    public void Test13(){
        //定义脚本封装类
        DefaultRedisScript<Role> redisScript=new DefaultRedisScript<>();
        //设置脚本
        redisScript.setScriptText("redis.call ('set', KEYS [1] , ARGV[1]) return redis.call('get',KEYS[1])" );
        //定义操作的key列表
        List<String> keyList=new ArrayList<>();
        keyList.add("role2");
        Role role=new Role();
        role.setId(1L);
        role.setRoleName("role_name_2");
        role.setNote("note_2");
        String sha1=redisScript.getSha1();
        //获得标识字符串
        System.out.println(sha1);
        //设置返回结果类型（不设置返回空）
        redisScript.setResultType(Role.class);
        //定义序列化器
        JdkSerializationRedisSerializer serializer=new JdkSerializationRedisSerializer();
        //执行 参数分别为RedisScript接口对象 参数序列化器 结果序列化器 Redis的 key列表，参数列表
        Role obj=(Role)redisTemplate.execute(redisScript, serializer,serializer,keyList,role);
    }
    @Test
    public void testUsePool() {
        Jedis jedis = jedisPool.getResource();
        // 使用jedis进行操作
        jedis.set("name", "otherNameValNew");
        // 用完之后，一定要手动关闭连接（归还给连接池）
        jedis.close();
    }
}
