package cn.edu.hziee.springbootinterface.Service;

import cn.edu.hziee.springbootinterface.Entity.User;

public interface RabbitMqService {
    void sendMsg(String msg);
    void sendUser(User user);
}
