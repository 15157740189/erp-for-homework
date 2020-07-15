package cn.edu.hziee.springboot01.service.impl;

import cn.edu.hziee.springboot01.mapper.UserMapper;
import cn.edu.hziee.springboot01.model.User;
import cn.edu.hziee.springboot01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> getAllUser() {
        return userMapper.selectAllUser();
    }

/*   @Transactional
    @Override
    public int update(User user) {
       int update=userMapper.updateByPrimaryKey(user);
        int a=10/0;//抛出异常
       //开启事务后会回滚
        return update;
    }*/
}
