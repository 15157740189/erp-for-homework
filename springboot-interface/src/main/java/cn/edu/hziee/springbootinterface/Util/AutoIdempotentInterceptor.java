package cn.edu.hziee.springbootinterface.Util;

import cn.edu.hziee.springbootinterface.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
@Component
public class AutoIdempotentInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod= (HandlerMethod) handler;
        Method method=handlerMethod.getMethod();
        //被AutoIdempotent标记的扫描
        AutoIdempotent methodAnnotation=method.getAnnotation(AutoIdempotent.class);
        if (methodAnnotation!=null){
            try {
                return tokenService.checkToken(request);//幂等性校验
            }catch (Exception e){
                e.printStackTrace();
           // writeReturnJson(response,"Exception Happen");
          throw new RuntimeException("过滤器错误");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    private void writeReturnJson(HttpServletResponse response,String json) throws Exception{
        PrintWriter writer=null;
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        }catch (IOException e){
        }finally {
            if (writer!=null){
                writer.close();
            }
        }
    }
}
