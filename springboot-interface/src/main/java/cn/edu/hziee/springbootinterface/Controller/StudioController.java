package cn.edu.hziee.springbootinterface.Controller;


import cn.edu.hziee.springbootinterface.Entity.User;
import cn.edu.hziee.springbootinterface.Service.AsyncService;
import cn.edu.hziee.springbootinterface.Service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/studio")
public class StudioController {
    @Autowired
    AsyncService asyncService;
    @Autowired
    RabbitMqService rabbitMqService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @GetMapping("/async")
    @ResponseBody
    public String async(){
        System.out.println("请求线程名称为： "+Thread.currentThread().getName());
        //调用异步服务
        asyncService.generateReport();
        return "async";
    }

    @GetMapping("/websocket")
    public String websocket(){
        return "websocket";
    }
    //发送页面
    @GetMapping("/send")
    public String send(){
        return "send";
    }
    //接收页面
    @GetMapping("/receive")
    public String receive(){
        return "receive";
    }
    //对特定用户发送页面
    @GetMapping("/sendUser")
    public String sendUser(){
        return "send-user";
    }
   //接收用户消息页丽
    @GetMapping("/receiveUser")
    public String receiveUser(){
        return "receive-user";
    }

   //定义消息请求路径
    @MessageMapping("/send")
    @SendTo("/sub/chat")
    public String sendMsg(String value){
        return value;
    }

    //将消息发送给特定用户
    @MessageMapping("/sendUser")
    public void sendToUser(Principal principal,String body) {
    String srcUser=principal.getName();
    //解析用户和消息
        String[] args=body.split(",");
        String desUser=args[0];
        String message=desUser+" 有人给你发来了消息："+args[1];
        //发送到用户和监听地址
        simpMessagingTemplate.convertAndSendToUser(desUser,
                "/queue/customer",message);
    }

   /* @GetMapping("/msg")
    @ResponseBody
    public Map<String,Object> msg(String msg){
        rabbitMqService.sendMsg(msg);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("message",msg);
        resultMap.put("success",true);
        return resultMap;
    }
    @GetMapping("/user")
    @ResponseBody
    public Map<String,Object> user(){
        User user=new User();
        user.setId(Long.parseLong(UUID.randomUUID().toString()));
        user.setAccount("todaytekoki");
        user.setPassword("167loveyou");
        user.setEmail("15187945782@163.com");
        rabbitMqService.sendUser(user);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("user",user);
        resultMap.put("success",true);
        return resultMap;
    }*/
}
