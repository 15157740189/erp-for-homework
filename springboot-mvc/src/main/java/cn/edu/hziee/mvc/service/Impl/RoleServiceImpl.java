package cn.edu.hziee.mvc.service.Impl;

import cn.edu.hziee.mvc.entity.Role;
import cn.edu.hziee.mvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    cn.edu.hziee.mvc.mapper.roleMapper roleMapper;

    /**
     * 使用自定义Cacheable 定义缓存策略
     * 当缓存中有值，则返回缓存数据，否则访问方法得到数据
     * 通过 value 引用缓存管理器，通过 key 定义键
     * @param id 角色编号
     *  @return 角色
     *  */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    @Cacheable(cacheNames = "redisCacheManager",key = "'redis_role_'+#id")
    public Role getRoles(Long id) {
        return roleMapper.getRoles(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public List<Role> findRoles(String roleName, String note) {
        List<Role> list=roleMapper.findRoles(roleName,note);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    @CacheEvict(cacheNames = "redisCacheManager",key = "'redis_role_'+#id")
    public int deleteRole(Long id) {
        return  roleMapper.deleteRole(id);
    }

    /**
     * 使用@CachePut 则表示无论如何都会执行方法，最后将方法的返回值再保存到缓存中
     * 使用在插入数据的地方，则表示保存到数据库后,会同期插入 Redis 缓存中
     *@param role 角色对象
     * @return 角色对象（会回填主键）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    @CachePut(cacheNames = "redisCacheManager",key = "'redis_role_'+#result.id")
    public Role insertRole(Role role) {
        roleMapper.insertRole(role);
        return role;
    }
    /*使用@CachePut 更新数据库数据同时也会更新缓存
     * @param role 角色对象
     *  @return 影响条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    @Cacheable(cacheNames = "redisCacheManager",key = "'redis_role_'+#role.id")
    public Role updateRole(Role role) {
        roleMapper.updateRole(role);
        return role;
    }
}
