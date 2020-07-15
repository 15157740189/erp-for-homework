package cn.edu.hziee.wxstudio.延时队列;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuartzDemo {

    @Scheduled(cron = "0/5 * * * * ? ")
    public void process(){
        System.out.println("一个定时任务");
    }
}
