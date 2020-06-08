package cn.edu.hziee.springbootinterface;

import cn.edu.hziee.springbootinterface.Entity.Cup;
import cn.edu.hziee.springbootinterface.Entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class MongoDBTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void MongoTest(){
        Cup cup=new Cup();
        cup.setCup_id(3L);
        cup.setNote("why not?");
        cup.setCup_name("中型杯子");
        List<Role> roles=new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());
        cup.setRoles(roles);
        mongoTemplate.save(cup);
        Criteria criteriaId=Criteria.where("note").regex("hello");
        Query queryId=Query.query(criteriaId);
        System.out.print("输出信息：");
        mongoTemplate.findAll(Cup.class).forEach(System.out::println);;
        mongoTemplate.find(queryId, Cup.class).forEach(System.out::println);
    }
}
