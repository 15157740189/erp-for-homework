package cn.edu.hziee.springboot01.controller;


import cn.edu.hziee.springboot01.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping("/index")
    public String log() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(User user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
               user.getUserName(),
               user.getPassword()
        );
        if (subject.isAuthenticated())
            return "love";
        //log.info(usernamePasswordToken.getUsername());
        //log.info(String.valueOf(usernamePasswordToken.getPassword()));
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            usernamePasswordToken.setRememberMe(user.isRememberMe());
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            log.info("登录失败，账户或密码错误");
            return "/error";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "/error";
        }
        return "love";
    }
    @RequestMapping("/logout")
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
            return "redirect:/index";
        }
        return "redirect:/index";
    }

    //注解验角色和权限
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @RequestMapping("/love")
    public String index() {
        return "love";
    }
}


