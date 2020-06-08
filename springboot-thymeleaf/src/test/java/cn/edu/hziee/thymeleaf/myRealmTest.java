package cn.edu.hziee.thymeleaf;

import cn.edu.hziee.thymeleaf.realm.HeroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

@Slf4j
public class myRealmTest {

        HeroRealm heroRealm=new HeroRealm();
    @Test
    public void testAuthentication(){
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(heroRealm);
        //2.主体认证提交
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        heroRealm.setCredentialsMatcher(matcher);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("琦玉老师","8848");
        subject.login(token);
        log.info("isAuthenticated()："+subject.isAuthenticated());
        subject.checkRoles("admin","user");
        subject.logout();
        log.info("isAuthenticated()："+subject.isAuthenticated());

    }}
