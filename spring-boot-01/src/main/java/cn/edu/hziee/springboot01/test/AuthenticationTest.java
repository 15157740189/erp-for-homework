package cn.edu.hziee.springboot01.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationTest {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @BeforeEach
    public void addUser(){
        simpleAccountRealm.addAccount("Mark","123456");
    }

    @Test
    public void testAuthentication(){
        // 1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        // 2、主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3、  SecurityManager认证
        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);

        //    4、Authenticator认证
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        //       5、 Realm验证

        //退出
        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());

    }

}
