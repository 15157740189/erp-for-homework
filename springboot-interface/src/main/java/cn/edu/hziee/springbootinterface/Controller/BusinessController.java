package cn.edu.hziee.springbootinterface.Controller;

import cn.edu.hziee.springbootinterface.Service.TokenService;
import cn.edu.hziee.springbootinterface.Util.AutoIdempotent;
import cn.edu.hziee.springbootinterface.Util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class BusinessController {
    @Resource
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/get/token")
    @ResponseBody
    public String getToken(){
        String token=tokenService.createToken();
        if (!StringUtils.isEmpty(token)){
        }
        return token;
    }
    @AutoIdempotent
    @ResponseBody
    @PostMapping("/test/Idempotent")
    public String testIdempotent(){
        return "right";
    }
}
