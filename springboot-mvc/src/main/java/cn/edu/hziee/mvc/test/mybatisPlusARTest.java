package cn.edu.hziee.mvc.test;

import cn.edu.hziee.mvc.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class mybatisPlusARTest {
    @Autowired
    cn.edu.hziee.mvc.mapper.bookMapper bookMapper;
    @Test
    public void ARtest(){
        Book book=new Book();
        book.setId(12);
        book.setBookId("11250");
        book.setType("科研");
        book.setBookNumber(15);
        book.setBookPlace("四号柜1层");
        book.setAdminId("11011");
        book.setProducerName("庆丰报纸铺");
        book.setBookName("mysql索引深入第三版");
        boolean insert=book.insertOrUpdate();//要设置id才会查询是否存在
        System.out.println(insert);
        book.setType("科普");
        book.setBookName("什么是数据库");
        boolean update=book.updateById();
        System.out.println(update);
        boolean delete=book.deleteById();
        System.out.println(delete);
    }
}
