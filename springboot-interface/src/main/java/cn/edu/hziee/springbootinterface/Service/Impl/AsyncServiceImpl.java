package cn.edu.hziee.springbootinterface.Service.Impl;

import cn.edu.hziee.springbootinterface.Service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {
    @Override
    @Async //声明异步调用
    public void generateReport() {
    //打印异步线程名称
        System.out.println("报表线程名称为： "+Thread.currentThread().getName());
    }

}
