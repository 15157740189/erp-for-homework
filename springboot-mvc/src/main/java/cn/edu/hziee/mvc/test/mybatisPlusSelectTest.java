package cn.edu.hziee.mvc.test;

import cn.edu.hziee.mvc.entity.Book;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class mybatisPlusSelectTest {
         @Autowired
         cn.edu.hziee.mvc.mapper.bookMapper bookMapper;
    @Test
    //查询所有的列的数据
    public void select(){
        List<Book> bookList=bookMapper.selectList(null);
       Assert.assertEquals(5,bookList.size());
        bookList.forEach(book->log.info(book.toString()));
    }
    @Test
    //插入一个书本数据
    public void insert(){
      Book book=new Book();
        book.setBookId("11984");
        book.setType("科研");
        book.setBookNumber(15);
        book.setBookPlace("四号柜三层");
        book.setAdminId("11011");
        book.setProducerName("新蛤社");
      book.setBookName("mysql索引深入第二版");
      int rows=bookMapper.insert(book);
        System.out.println("影响行数"+rows);
      //log.info("影响行数"+rows);
    }
    @Test
    //通过主键id集合查询
    public void selectByIdList(){
        List<Integer> idList= Arrays.asList(2,4,6);
       List<Book> bookList=bookMapper.selectBatchIds(idList);
        bookList.forEach(System.out::println);
    }
    @Test
    //通过Map查询
    public void selectByMap(){
        Map<String,Object> colunmMap=new HashMap<>();
        colunmMap.put("pname","新蛤社");
        List<Book> bookList=bookMapper.selectByMap(colunmMap);
        bookList.forEach(System.out::println);
    }
    @Test
    //通过like (rightlike leftlike)模糊查询 le为<= ge为>=
    public void selectByWrapper1(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
       // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.like("bplace","一号柜").le("bnumber",80);//le小于等于 ge反之
         List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //between  以及isNotNull判null（null不等于字段为空）
    public void selectByWrapper2(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.between("bnumber",55,150).isNotNull("bplace");
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //or()即为sql语句or desc降序 asc升序
    public void selectByWrapper3(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.likeRight("bplace","一号柜").or().ge("bnumber",70)
                .orderByDesc("id").orderByAsc("bid");
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //查询需要的确切日期 还有原生sql语句嵌套查询（复杂的sql语句需要手写 如sum集合函数 或者avg max min）
    public void selectByWrapper4(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.apply("date_format(pdate,'%Y-%m-%d') ={0}","2019-11-04")
                .inSql("cid","select cid from admin where cname like '香菜%' ");
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //函数式编程 表示数量小于120 (gt为大于) 或者bplace字段为null的数据
    public void selectByWrapper5(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.likeRight("bplace","三号柜")
                .and(wq->wq.lt("bnumber", 120).or().isNotNull("bplace"));
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //当处于第一个字段时不可用and 即可用nested
    public void selectByWrapper6(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.nested(wq->wq.lt("bnumber", 120).or().isNotNull("bplace"));
        //当是第一个时不可为and
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //last中可使用 limit(start size)进行查找 in()则可用集合查找对应列
    public void selectByWrapper7(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        String start="1";
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.in("type",Arrays.asList("玄幻","新闻","科研")).last("limit "+start+",2");
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //查找需要的行可用select 若entity字段不与表中字段匹配 表字段后需要用实体字段标注
    public void selectByWrapper8(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.select("id","pname producerName","bname bookName").in("type",Arrays.asList("玄幻","新闻","科研"));
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //lambda式写法
    public void selectByWrapper9(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        // QueryWrapper<Book> query= Wrappers.<Book>query();
        queryWrapper.in("type",Arrays.asList("玄幻","新闻","科研"))
                .select(Book.class,info->!info.getColumn().equals("bid"));
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //condition 可用StringUtils.isEmpty()判空 空则跳过
    public void condition(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
       String pname="庆丰报纸铺";
       String bname="";
        queryWrapper.like(StringUtils.isEmpty(bname),"bname",bname)
                .like(StringUtils.isEmpty(pname),"pname",pname);;
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //使用实体查询
    public void selectByWrapperEntity(){
        Book book=new Book();
        book.setProducerName("庆丰报纸铺");
        book.setType("新闻");
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>(book);
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //使用AllEq查询 可用函数式编程设置条件
    public void selectByWrapperAllEq(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        Map<String,Object> param=new HashMap<>(16);
        param.put("pname","庆丰报纸铺");
        param.put("type","科普");
        queryWrapper.allEq((k,v)->!k.equals("pname"),param );
        List<Book> bookList=bookMapper.selectList(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //map查询 返回多组map 当是求均值最值以及groupby分组时可用使用
    public void selectByWrapperByMaps(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
       // Map<String,Object> param=new HashMap<>(16);
        queryWrapper.select("avg(bnumber) avg_number","min(bnumber) min_number","max(bnumber) max_number")
                .groupBy("type").having("sum(bnumber)<{0}",300);
        List<Map<String,Object>> bookList=bookMapper.selectMaps(queryWrapper);
        bookList.forEach(System.out::println);
    }
    @Test
    //list只返回第一个字段
    public void selectByWrapperByObjs(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("pname producerName","bname bookName").like("bplace","五号");
        List<Object> bookList=bookMapper.selectObjs(queryWrapper);
        bookList.forEach(System.out::println);//只输出第一个字段的数据
    }
    @Test
    //查询count
    public void selectByWrapperCount(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("bplace","三号");
        Integer count=bookMapper.selectCount(queryWrapper);
        System.out.println("总记录数："+count);
    }
    @Test
    //lambda查询 好处是不用运行时检查 可在编译时就检查参数是否错误
    public void selectLambda() {
        LambdaQueryWrapper<Book> lambdaQuery=new QueryWrapper<Book>().lambda();
        lambdaQuery.like(Book::getBookPlace,"一号");
        List<Book> bookList=bookMapper.selectList(lambdaQuery);
       // List<Book> bookList=bookMapper.selectAll(lambdaQuery); 自定义sql  要注意表中字段要和entity一样
        bookList.forEach(System.out::println);
    }
    @Test
   public void selectByPage(){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.lt("bnumber",120);
        Page<Book> Page1=new Page<>(3,2);
        Page<Book> Page2=new Page<>(3,2,false);//false代表不查询总记录数
        IPage<Book> iPage1=bookMapper.selectPage(Page1,queryWrapper);
        System.out.println("总页数 "+iPage1.getPages());
        List<Book> bookList0=iPage1.getRecords();
        bookList0.forEach(System.out::println);
       log.info("----不同的查询方式----");
        IPage<Map<String,Object>> iPage2=bookMapper.selectMapsPage(Page2,queryWrapper);
        System.out.println("总页数 "+iPage2.getPages());
        System.out.println("总记录数 "+iPage2.getTotal());
        List<Map<String,Object>> bookList=iPage2.getRecords();
        bookList.forEach(System.out::println);
    }
}
