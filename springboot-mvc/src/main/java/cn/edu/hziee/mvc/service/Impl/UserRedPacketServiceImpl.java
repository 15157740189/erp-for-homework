package cn.edu.hziee.mvc.service.Impl;

import cn.edu.hziee.mvc.entity.RedPacket;
import cn.edu.hziee.mvc.entity.UserRedPacket;
import cn.edu.hziee.mvc.mapper.RedPacketDao;
import cn.edu.hziee.mvc.mapper.UserRedPacketDao;
import cn.edu.hziee.mvc.service.RedisRedPacketService;
import cn.edu.hziee.mvc.service.UserRedPacketService;
import cn.edu.hziee.mvc.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class UserRedPacketServiceImpl implements UserRedPacketService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedisRedPacketService redisRedPacketService;
    @Autowired
    private UserRedPacketDao userRedPacketDao;
    @Autowired
    private RedPacketDao redPacketDao;
    //Lua脚本
    String script="local listKey = 'red_packet_list_'..KEYS[1] \n"
            +"local redPacket = 'red_packet_'..KEYS[1] \n"
            + "local stock = tonumber(redis.call( 'hget',redPacket,'stock'))\n"
            +"if stock <= 0 then return 0 end \n"
            +"stock = stock -1 \n"
            +"redis.call('hset',redPacket,'stock',tostring(stock)) \n"
            +"redis.call( 'rpush',listKey,ARGV[1]) \n"
            +"if stock==0 then return 2 end \n"
            +"return 1 \n";
   //在缓存Lua脚本后，使用该变量保存Redis返回的32位的SHA1编码，使用他去执行的缓存的Lua脚本
    String sha1=null;
    public static final int FAILED=0;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public int grapRedPacket(Long redPacketId, Long userId) {
        //记录开始时间
        Long start = System.currentTimeMillis();
        //无限循环，等待成功或者时间满 100 毫秒退出
        while (true) {
            //获取当前循环时间
            Long end=System.currentTimeMillis();
            if (start - end >100){
                //超过100ms退出(基于时间戳的可重入锁 //也可基于次数)
                return FAILED;
            }
            //获取红包信息
            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
            //当前红包库存大于0
            if (redPacket.getStock() > 0) {
                //再次传入线程保存的version旧值给sql判断，是否有其他线程修改过数据
                int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
                //生成抢红包信息
                if (update == 0) {
                    return FAILED;
                }
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("抢红包" + redPacketId);
                //插入抢红包信息
                int result = userRedPacketDao.grapRedPacket(userRedPacket);
                log.info("抢红包成功,该用户id为：" + userRedPacket.getId());
                return result;
            }
            log.info(("抢红包失败"));
            return FAILED;
        }
    }

    @Override
    public Long grapRedPacketByRedis(Long redPacketId, Long userId) {
        String args=userId+"="+System.currentTimeMillis();
        Long result;
        Jedis jedis= JedisUtils.getJedis();
       try {
           if (sha1==null){
               sha1=jedis.scriptLoad(script);
           }
           Object res=jedis.evalsha(sha1,1,redPacketId+"",args);
           result=(Long) res;
           //返回2时为最后一个红包
           if (result==2) {
               log.info("红包已被抢完");
               //获取单个小红包金额
               String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
               //触发保存数据库操作
               Double unitAmount = Double.parseDouble(unitAmountStr);
               System.err.println("thread_name   =   " + Thread.currentThread().getName());
               redisRedPacketService.saveUserRedPacketByRedis(redPacketId, unitAmount);
           }
       }finally {
           if (jedis!=null&&jedis.isConnected()){
               jedis.close();
           }
       }
        return result;
    }


}
