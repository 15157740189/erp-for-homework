package cn.edu.hziee.springbootinterface;

import cn.edu.hziee.springbootinterface.Entity.DatabaseRole;
import cn.edu.hziee.springbootinterface.Entity.DatabaseUser;
import cn.edu.hziee.springbootinterface.Mapper.DatabaseRoleMapper;
import cn.edu.hziee.springbootinterface.Mapper.DatabaseUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class MPRoleTest {
    String roleQuery=" select r.*"
            +" from t_user u, t_user_role ur,t_role r"
            +" where u.id=ur.user_id and r.id=ur.role_id"
            +" and u.user_name = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DatabaseUserMapper databaseUserMapper;
    @Autowired
    DatabaseRoleMapper databaseRoleMapper;
    @Test
    public void select(){
        List<DatabaseRole> roleList=jdbcTemplate.query(roleQuery,new Object[]{"admin"},new BeanPropertyRowMapper(DatabaseRole.class));
        roleList.forEach(System.out::println);
        QueryWrapper<DatabaseUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("user_name","admin");
      List<DatabaseUser> list= databaseUserMapper.selectList(queryWrapper);
      list.forEach(System.out::println);

    }
}
