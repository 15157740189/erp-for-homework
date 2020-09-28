package cn.edu.hziee.springbootinterface.Util;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//只可用在方法上
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface AutoIdempotent {
//标注自动幂等
}
