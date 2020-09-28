package cn.edu.hziee.springbootinterface.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailService;
    @Value(value = "${system.user.password.secret}")
    private String secret=null;
    String pwdQuery=" select user_name pwd,available " + " from t_user where user_name = ?";
    String roleQuery=" select u.user_name,r.role_name"
            +" from t_user u, t_user_role ur,t_role r"
            +" where u.id=ur.user_id and r.id=ur.role_id"
            +" and u.user_name = ?";

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder=new Pbkdf2PasswordEncoder("kana");
        String pbk1 = passwordEncoder.encode("123456");
        System.out.println("pbk1: " + pbk1);
        System.out.println("pbk1 password:" + passwordEncoder.matches("123456",pbk1));
    }
    /**
     *覆盖 WebSecurityConfigurerAdapter 用户详情方法
     * @param auth 用户签名管理器构造器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码编码器
        PasswordEncoder passwordEncoder=new Pbkdf2PasswordEncoder(this.secret);
        //PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);

       /* auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource)
                .usersByUsernameQuery(pwdQuery)
                .authoritiesByUsernameQuery(roleQuery);*/
/*            PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
               InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userConfig =
                auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder);
                userConfig.withUser("admin")
                        .password(passwordEncoder.encode("abc"))
                        .authorities("ROLE_USER","ROLE_ADMIN");
                userConfig.withUser("myuser")
                        .password(passwordEncoder.encode("123456"))
                        .authorities("ROLE_USER");*/
       /* //使用内存存储
        auth.inMemoryAuthentication()
        //设置密码编码器
        .passwordEncoder(passwordEncoder)
                //／注册用户 admin， 密码为 abc ，并赋予 USER 和 ADMIN 的角色权限
                .withUser("admin")
                //可通过 passwordEncoder.encode("abc")得到加密后的密码 "$2a$10$5OpFvQlTIbM9Bx2pfbKVzurdQXL9zndmlSrAjEkPyIuCcZ7CqR6je"
         .password(passwordEncoder.encode("abc"))
                .roles("user","admin")
                //连接方法 and
                .and()
                //注册用户 myuser，密码为 123456 ， 并赋予 USER 的角色权限
        .withUser("myuser")
                //可通过 passwordEncoder.encode("123456") 得到加密后的密码 "$2a$10$ezWluns4ZV63FgCLiFHJqOI6oR6jaaPYn33jNrxnkHZ.ayAFmfzLS"
        .password(passwordEncoder.encode("123456"))
        .roles("USER");*/
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests()
               // .antMatchers("/studio/**","/ws/**").permitAll()
                //使用 Spring 表达式限定只有角色 user 或者 admin
                .antMatchers("/user/**").access("hasAuthority('user') or hasAuthority('admin')")
                //设置访问权限给角色 role .admin，要求是完整登录（非记住我登录）
                .antMatchers("/admin/welcome1").access("hasAuthority('admin') && isFullyAuthenticated()")
                //限定”/admin/welcome2”访问权限给角色 ROLE ADMIN，允许不完整登录
                .antMatchers("/admin/welcome2").access("hasAuthority('admin')")
                .and().rememberMe().tokenValiditySeconds(86400).key ("remember-me-key")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/studio/sendUser")
                .and().httpBasic();
    }
}
