package cn.edu.hziee.mvc.mapper;

import cn.edu.hziee.mvc.dao.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface userMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByUserId(Integer id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    List<UserDO> selectAllUser();
}