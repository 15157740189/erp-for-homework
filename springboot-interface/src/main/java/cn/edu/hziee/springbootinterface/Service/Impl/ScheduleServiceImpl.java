package cn.edu.hziee.springbootinterface.Service.Impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl {
    int count1=1;
    int count2=2;

    //@Scheduled(fixedRate = 1000)
    @Async
    public void job1(){
        System.out.println(Thread.currentThread().getName()+" 每秒执行一次");
    }
}
