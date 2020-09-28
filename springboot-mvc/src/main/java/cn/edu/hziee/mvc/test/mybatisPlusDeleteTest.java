package cn.edu.hziee.mvc.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class mybatisPlusDeleteTest {
    @Autowired
    cn.edu.hziee.mvc.mapper.bookMapper bookMapper;
    @Test
    public void deleteById(){
        int rows=bookMapper.deleteById("10");
      //列举方法 bookMapper.deleteByMap() bookMapper.deleteBatchIds( ) bookMapper.delete(传入wrapper)

        System.out.println("影响行数："+rows);
    }
}
