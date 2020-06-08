import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
@Slf4j
public class IniRealmTest {
    @Test
    public void testAuthentication(){
        JdbcRealm jdbcRealm=new JdbcRealm();
        IniRealm iniRealm=new IniRealm("classpath:user.ini");
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        //2.主体认证提交
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("shiro","123456");
        subject.login(token);
        log.info("isAuthenticated()："+subject.isAuthenticated());
        log.info("isAuthenticated()："+subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:delete");
        subject.logout();
    }
}
