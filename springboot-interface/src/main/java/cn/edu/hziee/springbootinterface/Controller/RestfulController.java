package cn.edu.hziee.springbootinterface.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestfulController {

    @GetMapping("restful")
    @ResponseBody
    public String index(){
        return "restful";
    }
}
