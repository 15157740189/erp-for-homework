package cn.edu.hziee.springbootinterface.Service.Impl;

import cn.edu.hziee.springbootinterface.Entity.DatabaseRole;
import cn.edu.hziee.springbootinterface.Entity.DatabaseUser;
import cn.edu.hziee.springbootinterface.Mapper.DatabaseUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    String roleQuery=" select r.*"
            +" from t_user u, t_user_role ur,t_role r"
            +" where u.id=ur.user_id and r.id=ur.role_id"
            +" and u.user_name = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DatabaseUserMapper databaseUserMapper;
    /*   static {
           Map<String, List<String>> userMap=new HashMap<>(10);
           userMap.put("user", Arrays.asList("select"));
           userMap.put("admin", Arrays.asList("select","add","update","delete"));
       }*/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        QueryWrapper<DatabaseUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("user_name",userName);
        List<DatabaseUser> list= databaseUserMapper.selectList(queryWrapper);
        if (list.size()==0){
            throw new RuntimeException("无此人信息");
        }
        DatabaseUser dbUser=list.get(0);
        List<DatabaseRole> roleList=jdbcTemplate.query(roleQuery,new Object[]{userName},new BeanPropertyRowMapper(DatabaseRole.class));
        return changeToUser(dbUser,roleList);
    }

    private UserDetails changeToUser(DatabaseUser dbUser, List<DatabaseRole> roleList) {
        //／／权限91J表
        List<GrantedAuthority> authorityList = new ArrayList<>();
        //赋予查询到的角色
        for (DatabaseRole role : roleList) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole_name());
            authorityList.add(authority);
        }
        //创建 UserDetails 对象，设置用户名、密码和权限
        UserDetails userDetails = new User(dbUser.getUserName(), dbUser.getPwd(), authorityList);
        return userDetails;
    }
}
