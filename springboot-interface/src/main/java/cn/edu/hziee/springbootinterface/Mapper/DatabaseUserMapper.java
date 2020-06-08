package cn.edu.hziee.springbootinterface.Mapper;

import cn.edu.hziee.springbootinterface.Entity.DatabaseUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DatabaseUserMapper extends BaseMapper<DatabaseUser> {
}
