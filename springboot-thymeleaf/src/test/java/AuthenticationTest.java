import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
public class AuthenticationTest {
    SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("shiro","123456","admin","user");
    }
    @Test
    public void testAuthentication(){
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //2.主体认证提交
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("shiro","123456");
        subject.login(token);
        log.info("isAuthenticated()："+subject.isAuthenticated());
        subject.logout();
        log.info("isAuthenticated()："+subject.isAuthenticated());
        subject.checkRoles("admin","user");
    }
}
