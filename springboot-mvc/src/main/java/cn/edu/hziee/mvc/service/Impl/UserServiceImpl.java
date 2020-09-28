package cn.edu.hziee.mvc.service.Impl;

import cn.edu.hziee.mvc.dao.PasswordDO;
import cn.edu.hziee.mvc.dao.UserDO;
import cn.edu.hziee.mvc.service.UserService;
import cn.edu.hziee.mvc.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

     @Autowired
     cn.edu.hziee.mvc.mapper.userMapper userMapper;
     @Autowired
     cn.edu.hziee.mvc.mapper.passwordMapper passwordMapper;
    @Override
    public UserModel getUserById(int id) {
       UserDO userDO= userMapper.selectByUserId(id);
       if (userDO==null)
           return null;
      PasswordDO passwordDO= passwordMapper.selectPasswordByUserId(userDO.getId());
      //PasswordDO passwordDO= passwordMapper.selectPasswordByUserId(userDO.getId(),new RowBounds(1,5));
        // RowBounds分页
       return convertFromDataObject(userDO,passwordDO);
    }
    private UserModel convertFromDataObject(UserDO userDO, PasswordDO passwordDO){
        if (userDO==null)
            return null;
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if (passwordDO!=null)
        userModel.setPassword(passwordDO.getPassword());
        return userModel;
    }
}
