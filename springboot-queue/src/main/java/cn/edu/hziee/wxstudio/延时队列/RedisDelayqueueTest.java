package cn.edu.hziee.wxstudio.延时队列;


import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Slf4j
public class RedisDelayqueueTest {
    @Autowired
    PollOrderQueue pollOrderQueue;

@Test
    public void test() throws InterruptedException {
    pollOrderQueue.pollOrderQueue();
}
}
