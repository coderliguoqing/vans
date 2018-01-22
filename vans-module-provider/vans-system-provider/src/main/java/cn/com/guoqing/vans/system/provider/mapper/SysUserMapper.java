package cn.com.guoqing.vans.system.provider.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysRole;
import cn.com.guoqing.vans.system.api.entity.SysUser;

import java.util.HashMap;
import java.util.List;

/**
 * 用户DAO接口
 *
 * @author mij
 */
@Mapper
public interface SysUserMapper extends CrudDao<SysUser> {

    /**
     * 根据登录名称查询用户
     *
     * @param loginName 登录名
     * @return SysUser by login name
     */
    SysUser getByLoginName(String loginName);

    /**
     * 更新用户密码
     *
     * @param user the user
     * @return the int
     */
    int updatePasswordById(SysUser user);

    /**
     * 删除用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int deleteUserRole(SysUser user);

    /**
     * 插入用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int insertUserRole(SysUser user);

    /**
     * 保存用户信息
     *
     * @param user the user
     */
    void updateInfo(SysUser user);

    /**
     * 获取用户已授权角色列表
     * @param userId
     * @return
     */
    List<SysRole> getAuthList( int userId);
    
    /**
     * 获取用户未授权角色列表
     * @param userId
     * @return
     */
    List<SysRole> getUnAuthList( int userId );
    
    /**
     * 添加用户角色信息
     * @param params
     */
    void addAuthInfo( HashMap<String, Object> params );
    
    /**
     * 删除用户角色信息
     * @param params
     */
    void cancelAuthInfo( HashMap<String, Object> params );
}
