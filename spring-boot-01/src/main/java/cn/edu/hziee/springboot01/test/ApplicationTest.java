package cn.edu.hziee.springboot01.test;

import cn.edu.hziee.springboot01.model.User;
import cn.edu.hziee.springboot01.service.LoginService;
import cn.edu.hziee.springboot01.service.impl.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Slf4j
public class ApplicationTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private LoginService loginService;
    @Test
    public void test() throws Exception {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }
    @Test
    public void testService() throws Exception {
       loginService.getUserByName("wsl");
    }
}
