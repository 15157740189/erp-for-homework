package cn.edu.hziee.springboot01.config;





import cn.edu.hziee.springboot01.cache.RedisCache;
import cn.edu.hziee.springboot01.cache.RedisCacheManager;
import cn.edu.hziee.springboot01.session.CustomSessionManager;
import cn.edu.hziee.springboot01.session.RedisSessionDAO;
import cn.edu.hziee.springboot01.shiro.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class ShiroConfig  extends CachingConfigurerSupport {
    /*@Bean
    public JedisPool redisPoolFactory() {
            JedisPool jedisPool;
            // 配置连接池
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(5);
            config.setMaxIdle(3);
            config.setMinIdle(2);
            // 创建连接池
             jedisPool = new JedisPool(config, "localhost", 6379);
        return jedisPool;
    }*/
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCacheManager(new RedisCacheManager());
        return customRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");
        //对所有用户认证
      //  map.put("/*", "authc");
        map.put("/*", "anon");
        map.put("/login","anon");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/index");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/love");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public CustomSessionManager sessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        //这里可以不设置。Shiro有默认的session管理。如果缓存为Redis则需改用Redis的管理
        customSessionManager.setSessionDAO(new RedisSessionDAO());
        customSessionManager.setCacheManager(new RedisCacheManager());
        return customSessionManager;
    }
   /* @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setSessionDAO(new RedisSessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
       // sessionManager.setSessionIdCookie(sessionIdCookie());
      //  Collection<SessionListener> listeners = new ArrayList<SessionListener>();
       // listeners.add(new BDSessionListener());
       // sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }*/
    /*@Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());
        //直接看源码
        //redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;
    }*/


}


