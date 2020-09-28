import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

@Slf4j
public class JdbcRealmTest {
    DruidDataSource dataSource=new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/springboot?characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }
    @Test
    public void testAuthentication(){
        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        //2.主体认证提交
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();

      /*  String sql="select password from users where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);
        自定义sql语句*/

        UsernamePasswordToken token=new UsernamePasswordToken("shiro","123456");
        subject.login(token);
        log.info("isAuthenticated()："+subject.isAuthenticated());
        log.info("isAuthenticated()："+subject.isAuthenticated());
        subject.checkRole("admin");
       subject.checkPermission("user:select");
        subject.logout();
    }
}
