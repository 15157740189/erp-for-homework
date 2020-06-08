package cn.edu.hziee.wxstudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WxstudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxstudioApplication.class, args);
    }

}
