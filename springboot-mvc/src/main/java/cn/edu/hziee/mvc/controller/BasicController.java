package cn.edu.hziee.mvc.controller;

import cn.edu.hziee.mvc.error.BusinessException;
import cn.edu.hziee.mvc.error.EmBusinessError;
import cn.edu.hziee.mvc.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BasicController {
    //定义ExceptionHandler解决尚未被controller吸收的Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception ex){
        Map<String, Object> responseData = new HashMap<>(16);
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode", businessException.getErrorCode());
            responseData.put("errMsg", businessException.getErrorMsg());
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrorCode());
            responseData.put("errMsg", EmBusinessError.UNKNOW_ERROR.getErrorMsg());
        }
          /* CommonReturnType commonReturnType=new CommonReturnType();
        commonReturnType.setStatus("fail");
        commonReturnType.setData(reseponseData);*/
        return  CommonReturnType.create(responseData,"fail");
    }
}
