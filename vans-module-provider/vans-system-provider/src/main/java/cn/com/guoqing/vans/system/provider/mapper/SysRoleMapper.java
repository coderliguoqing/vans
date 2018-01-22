package cn.com.guoqing.vans.system.provider.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysRole;

import java.util.List;

/**
 * 角色DAO接口
 *
 * @author mij
 */
@Mapper
public interface SysRoleMapper extends CrudDao<SysRole> {

    /**
     * 查询用户角色列表
     *
     * @param userId the user id
     * @return the list
     */
    List<SysRole> findListByUserId(Integer userId);

    /**
     * 删除角色菜单
     *
     * @param role the role
     * @return the int
     */
    int deleteRoleMenu(SysRole role);

    /**
     * 插入角色菜单
     *
     * @param role the role
     * @return the int
     */
    int insertRoleMenu(SysRole role);
}
