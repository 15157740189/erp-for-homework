package cn.edu.hziee.mvc.service;

import cn.edu.hziee.mvc.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoles(Long id);

    List<Role> findRoles(String roleName, String note);

    int deleteRole(Long id);

    Role insertRole(Role role);

    Role updateRole(Role role);
}
