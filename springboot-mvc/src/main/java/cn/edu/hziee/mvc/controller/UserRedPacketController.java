package cn.edu.hziee.mvc.controller;

import cn.edu.hziee.mvc.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserRedPacketController {
@Autowired
private UserRedPacketService userRedPacketService;

    @RequestMapping("/grapRedPacket")
    @ResponseBody
    public Map<String,Object> grapRedPacket(Long redPacketId,Long userId){
         //抢红包
        Long result=userRedPacketService.grapRedPacketByRedis(redPacketId,userId);
        Map<String,Object> retMap=new HashMap<>(16);
        boolean flag=result > 0;
        retMap.put("success",flag);
        retMap.put("message",flag?"抢红包成功":"抢红包失败");
        return retMap;
    }
    @RequestMapping("/testRedPacket")
    public String testRedPacket(){
        return "test";
    }
}
