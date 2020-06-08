package cn.edu.hziee.mvc.test;


import cn.edu.hziee.mvc.entity.Book;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class mybatisPlusUpdateTest {
    @Autowired
    cn.edu.hziee.mvc.mapper.bookMapper bookMapper;
    @Test
    public void updateById(){
        Book book=new Book();
        book.setId(7);
        book.setBookName("mysql深入索引再版（二版）");
        book.setBookPlace("四号柜二层");
        int rows=bookMapper.updateById(book);
        System.out.println("影响了："+rows+ "行");
    }
    @Test
    public void updateByWrapper(){
        UpdateWrapper<Book> updateWrapper=new UpdateWrapper<>();
      /*  updateWrapper.eq("bnumber",15).like("bplace","3层");//where中的条件
        Book book=new Book();
        book.setProducerName("庆丰报纸铺");
        book.setAdminId("YR874");
        int rows=bookMapper.update(book,updateWrapper);*/
        updateWrapper.eq("bnumber",15).like("bplace","3层")
                .set("pname","庆丰报纸铺").set("cid","YR874");
        int rows=bookMapper.update(null,updateWrapper);//可用此代替上方方法
        System.out.println("影响了："+rows+ "行");
    }
}
