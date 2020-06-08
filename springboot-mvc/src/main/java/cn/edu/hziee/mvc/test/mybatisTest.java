package cn.edu.hziee.mvc.test;

import cn.edu.hziee.mvc.dao.PasswordDO;
import cn.edu.hziee.mvc.dao.UserDO;
import cn.edu.hziee.mvc.entity.Role;
import cn.edu.hziee.mvc.mapper.RedPacketDao;
import cn.edu.hziee.mvc.service.RedisRedPacketService;
import cn.edu.hziee.mvc.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class mybatisTest {
    @Autowired
    RedisRedPacketService redisRedPacketService;
    @Autowired
    RoleService roleService;
    @Autowired
    cn.edu.hziee.mvc.mapper.userMapper userMapper;
    @Autowired
    cn.edu.hziee.mvc.mapper.passwordMapper passwordMapper;
    @Autowired
    cn.edu.hziee.mvc.mapper.roleMapper roleMapper;
    @Autowired
    RedPacketDao redPacketDao;
    @Test
    public void Test() {
        UserDO userDO = userMapper.selectByUserId(1);
        if (userDO != null)
            log.info(userDO.toString());
        PasswordDO passwordDO = passwordMapper.selectPasswordByUserId(1);
        if (passwordDO != null)
            log.info(passwordDO.toString());
    }
    @Test
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public void TestRoleMapper(){
        System.out.println(roleMapper.getRoles(1L).toString());
        Role role=new Role();
        role.setRoleName("杂技");
        role.setNote("note");
        System.out.println(roleMapper.insertRole(role));
        roleMapper.findRoles(null,"note").forEach(System.out::println);
        role.setNote("new_note");
        role.setId(3L);
        System.out.println(roleMapper.updateRole(role));
        System.out.println(roleMapper.getRoles(4L).toString());
    }
    @Test
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public void TestRoleService() {
        Role role=new Role();
        role.setRoleName("杂技");
        role.setNote("note");
        System.out.println(roleService.insertRole(role));
        System.out.println( roleService.getRoles(1L));
    }
    @Test
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public void TestRedPacket() {
        System.out.println(redPacketDao.getRedPacket(1L).getStock());
    }

    @Test
    public void TestRedisRedPacketService(){
        redisRedPacketService.saveUserRedPacketByRedis(5L, (double)10);
    }
    }
