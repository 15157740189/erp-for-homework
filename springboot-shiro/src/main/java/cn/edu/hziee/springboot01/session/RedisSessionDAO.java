package cn.edu.hziee.springboot01.session;

import cn.edu.hziee.springboot01.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Slf4j
public class RedisSessionDAO extends AbstractSessionDAO {
    private final String SHIRO_SEESION_PREFIX="kana-session";
    //@Autowired
    JedisUtil jedisUtil=new JedisUtil();
    private byte[] getKey(String key){
        return (SHIRO_SEESION_PREFIX+key).getBytes();
    }
    private void saveSession(Session session){
        log.info("save Session !");
        if (session!=null&&session.getId()!=null){
            byte[] key=getKey(session.getId().toString());
            byte[] value= SerializationUtils.serialize(session);
            jedisUtil.set(key,value);
            jedisUtil.expire(key,600);
        }
    }
    @Override
    protected Serializable doCreate(Session session) {
        log.info("Create Session !");
        Serializable sessionId=generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("Read Session !");
        if (sessionId==null)
            return null;
        byte[] key=getKey(sessionId.toString());
        if (jedisUtil==null)
            log.error("jedisUtil没有注入");
        byte[] value=jedisUtil.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.info("update Session !");
          saveSession(session);
    }

    @Override
    public void delete(Session session) {
        log.info("delete Session !");
       if (session==null||session.getId()==null){
           return;
       }
        byte[] key=getKey(session.getId().toString());
        jedisUtil.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        log.info("get ActiveSessions !");
        Set<byte[]> keys=jedisUtil.keys(SHIRO_SEESION_PREFIX);
        Set<Session> sessions=new HashSet<>();
        if (CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for (byte[] key: keys) {
            Session session= (Session) SerializationUtils.deserialize(key);
            sessions.add(session);
        }
        return sessions;
    }
}
