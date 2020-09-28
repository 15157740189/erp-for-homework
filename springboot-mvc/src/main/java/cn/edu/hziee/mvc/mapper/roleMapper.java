package cn.edu.hziee.mvc.mapper;

import cn.edu.hziee.mvc.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface roleMapper {
    Role getRoles( @Param("id") Long id);
    List<Role> findRoles(@Param("roleName")String roleName, @Param("note") String note);
    int deleteRole(Long id);
    int insertRole(Role role);
    int updateRole(Role role);
}
