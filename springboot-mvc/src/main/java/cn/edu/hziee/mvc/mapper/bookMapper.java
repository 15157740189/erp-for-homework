package cn.edu.hziee.mvc.mapper;


import cn.edu.hziee.mvc.entity.Book;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface bookMapper extends BaseMapper<Book> {
    @Select("select * from book ${ew.customSqlSegment}")
   List<Book> selectAll(@Param(Constants.WRAPPER) Wrapper<Book> wrapper);
}
