package cn.edu.hziee.thymeleaf.controller;

import cn.edu.hziee.thymeleaf.model.PunchMan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class testController {
    /**
     *添加一个英雄角色
     * @param request
     * @param punchMan
     * @return
     */
/*    @RequestMapping("/index")
    public String test(HttpServletRequest request){
        return "test";
        }
    */
@RequestMapping("/strengthTest")
    public void ajaxTestStrength(HttpServletResponse response,@RequestParam(defaultValue = "0") Integer strength)throws IOException{
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    if (StringUtils.isEmpty(String.valueOf(strength))){
    } else if (strength<200)
        out.print("<font color='#ff0000'> 拉力最好不要小于200kg</font> ");
    }
    @RequestMapping("/test")
    public String test(HttpServletRequest request, @Valid  PunchMan punchMan,
                       BindingResult result,HttpServletResponse response, Model model)  {
            if (result.hasErrors()) {
                log.error("error:" + result.getFieldError().getDefaultMessage());
                return "test";
            }
            // PunchMan punchMan=new PunchMan(strength,name,level);
            log.info(punchMan.toString());
            List<PunchMan> heroList = new ArrayList();
            heroList.add(punchMan);
            model.addAttribute("hero", heroList);
        return "test";
    }
}
