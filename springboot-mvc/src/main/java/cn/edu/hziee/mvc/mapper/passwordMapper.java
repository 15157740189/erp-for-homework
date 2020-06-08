package cn.edu.hziee.mvc.mapper;


import cn.edu.hziee.mvc.dao.PasswordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface passwordMapper {
    PasswordDO selectPasswordByUserId(Integer userId);
    PasswordDO selectPasswordByUserId(Integer userId, RowBounds rowBounds);
    //可以使用rowBounds分页 （逻辑分页）
    List<PasswordDO> selectAllUser();
}
