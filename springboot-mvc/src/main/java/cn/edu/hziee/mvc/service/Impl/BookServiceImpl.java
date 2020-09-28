package cn.edu.hziee.mvc.service.Impl;

import cn.edu.hziee.mvc.entity.Book;
import cn.edu.hziee.mvc.mapper.bookMapper;
import cn.edu.hziee.mvc.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends ServiceImpl<bookMapper, Book>implements BookService {

}
