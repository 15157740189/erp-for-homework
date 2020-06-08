package cn.edu.hziee.mvc.controller;


import cn.edu.hziee.mvc.controller.VO.UserVO;
import cn.edu.hziee.mvc.error.BusinessException;
import cn.edu.hziee.mvc.error.EmBusinessError;
import cn.edu.hziee.mvc.response.CommonReturnType;
import cn.edu.hziee.mvc.service.UserService;
import cn.edu.hziee.mvc.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BasicController{
    @Autowired
    UserService userService;
    @RequestMapping("/get")
    @ResponseBody
          public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException{
        //service服务传递给前端数据
            UserModel userModel= userService.getUserById(id);
            //userModel=null;
        //若获取的用户信息不存在
        if (userModel==null)
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);

            //将核心领域模型用户对象转化给可供UI使用的ViewObject
            UserVO userVO=convertFromModel(userModel);
            //返回通用对象
             return CommonReturnType.create(userVO);
          }
          private UserVO convertFromModel(UserModel userModel){
        if (userModel==null)
            return null;
              UserVO userVO=new UserVO();
              BeanUtils.copyProperties(userModel,userVO);
              return userVO;
          }

}
