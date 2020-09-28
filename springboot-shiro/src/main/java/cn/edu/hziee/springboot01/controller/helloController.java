package cn.edu.hziee.springboot01.controller;


import cn.edu.hziee.springboot01.config.ConfigInfo;
import cn.edu.hziee.springboot01.model.PunchMan;
import cn.edu.hziee.springboot01.model.User;
import cn.edu.hziee.springboot01.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@Slf4j
public class helloController {

    @Value("${boot.way}")
    private String name;
    @Autowired
    ConfigInfo configInfo;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping("/hello")
    public @ResponseBody
    String hello() {
        System.out.println(stringRedisTemplate);
        return "Hello,Springboot. Signature:" + name + "  "+stringRedisTemplate;
    }

    @RequestMapping("/hi")
    public  String index(Model model, HttpServletRequest request){
           //model.addAttribute("mModel",name);
        request.setAttribute("mModel",configInfo.getName());
           request.setAttribute("mRequest", configInfo.getLocation());
           request.getSession().setAttribute("session",configInfo);
           return "index";
    }
    @RequestMapping("/select")
    public @ResponseBody List<User> helloMybatis() {
        return userService.getAllUser();
    }

    @RequestMapping("/lv")
    public  String love() {
        return "love";
    }

    @RequestMapping("/form")
    public  String form(HttpServletRequest request) {
        List<User> list=new ArrayList<>();
        for (User user:userService.getAllUser()) {
            list.add(user);
        }
        request.setAttribute("User_data", list);
        return "form";
    }

    /**
     *添加一个英雄角色
     * @param request
     * @param punchMan
     * @return
     */
    @RequestMapping("/test")
    public String test(HttpServletRequest request, @Valid @ModelAttribute PunchMan punchMan, BindingResult result,Model model){
         if (result.hasErrors()){
             log.error(result.getFieldError().getDefaultMessage());
             return "error";
         }
         List heroList=new ArrayList();
         heroList.add(punchMan);
         model.addAttribute("hero",heroList);
        return "test";
    }

}