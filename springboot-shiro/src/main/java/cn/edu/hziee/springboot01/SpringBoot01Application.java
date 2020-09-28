package cn.edu.hziee.springboot01;

import cn.edu.hziee.springboot01.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableTransactionManagement//开启事务支持
//@ComponentScan(basePackages ={"cn.edu.hziee.springboot01"} )
public class SpringBoot01Application {
    public static void main(String[] args) {
        //SpringApplication.run(SpringBoot01Application.class, args);
        ApplicationContext applicationContext =
                SpringApplication.run(SpringBoot01Application.class, args);
        SpringUtil.setApplicationContext(applicationContext);
        SpringUtil.printBean();
    }

}
