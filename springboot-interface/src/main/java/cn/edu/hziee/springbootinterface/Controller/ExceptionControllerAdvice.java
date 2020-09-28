package cn.edu.hziee.springbootinterface.Controller;

import cn.edu.hziee.springbootinterface.Exception.APIException;
import cn.edu.hziee.springbootinterface.Exception.ResultCode;
import cn.edu.hziee.springbootinterface.Vo.ResultVo;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//全局异常处理
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public ResultVo<String> APIExceptionHandler(APIException e) {
        return new ResultVo<>(ResultCode.FAILED,e.getMsg());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ExceptionHandler注解指定你想处理的异常类型
    public ResultVo<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        ObjectError objectError=ex.getBindingResult().getAllErrors().get(0);
        return new ResultVo<>(ResultCode.VALIDATE_FAILED,objectError.getDefaultMessage());
    }

}
