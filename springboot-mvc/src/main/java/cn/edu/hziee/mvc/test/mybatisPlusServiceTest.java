package cn.edu.hziee.mvc.test;

import cn.edu.hziee.mvc.entity.Book;
import cn.edu.hziee.mvc.service.BookService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class mybatisPlusServiceTest {
    @Autowired
    BookService bookService;
    @Test
    public void testService(){
        Book one=bookService.getOne(Wrappers.<Book>lambdaQuery().gt(Book::getBookNumber,50),false);
    }
    @Test
    public void chain(){
        List<Book> bookList=bookService.lambdaQuery().gt(Book::getBookNumber,100)
                .like(Book::getBookName,"mysql").list();
        bookList.forEach(System.out::println);
    }
}
