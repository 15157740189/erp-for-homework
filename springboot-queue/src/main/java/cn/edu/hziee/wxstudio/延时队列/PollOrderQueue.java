package cn.edu.hziee.wxstudio.延时队列;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Component
public class PollOrderQueue {
    private static JedisPool jedisPool;
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String DELAY_QUEUE="delayqueue";
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

    public void pollOrderQueue() throws InterruptedException {

        while (true) {
            Jedis jedis=getJedis();
            Set<Tuple> set = jedis.zrangeWithScores(DELAY_QUEUE, 0, 0);

            String value = ((Tuple) set.toArray()[0]).getElement();
            int score = (int) ((Tuple) set.toArray()[0]).getScore();

            Calendar cal = Calendar.getInstance();
            int nowSecond = (int) (cal.getTimeInMillis() / 1000);
            if (nowSecond >= score) {
                jedis.zrem(DELAY_QUEUE, value);
                System.out.println(sdf.format(new Date()) + " removed key:" + value);
            }

            if (jedis.zcard(DELAY_QUEUE) <= 0) {
                System.out.println(sdf.format(new Date()) + " zset empty ");
                return;
            }
            Thread.sleep(1000);
        }
    }
}
