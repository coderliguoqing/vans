package cn.com.guoqing.vans.system.provider.serviceimpl;


import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.system.api.entity.SysMenu;
import cn.com.guoqing.vans.system.api.entity.SysRole;
import cn.com.guoqing.vans.system.api.entity.SysUser;
import cn.com.guoqing.vans.system.api.service.ISystemService;
import cn.com.guoqing.vans.system.provider.mapper.SysMenuMapper;
import cn.com.guoqing.vans.system.provider.mapper.SysRoleMapper;
import cn.com.guoqing.vans.system.provider.mapper.SysUserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author Guoqing
 */
@Service
@com.alibaba.dubbo.config.annotation.Service
@Transactional(readOnly = true)
public class SystemService implements ISystemService {

    /**
     * 系统用户Mapper
     */
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 系统角色Mapper
     */
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 系统菜单Mapper
     */
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public SysUser getUserByLoginName(String loginName) {
        SysUser user = sysUserMapper.getByLoginName(loginName);
        if (user == null) {
            return null;
        }

        Integer userId = user.getId();
        user.setRoles(sysRoleMapper.findListByUserId(userId));

        List<SysMenu> menuList;
        //超级管理员
        if (SysUser.ADMIN_USER_ID.equals(userId)) {
            menuList = sysMenuMapper.findAllList();
        } else {
            menuList = sysMenuMapper.findListByUserId(userId);
        }

        user.setMenus(menuList);
        return user;
    }

    @Override
    public PageInfo<SysUser> findUserPage(Paging page, SysUser user) {
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<SysUser> list = sysUserMapper.findList(user);
        return new PageInfo<>(list);
    }

    @Override
    public SysUser getUserById(Integer userId) {
        SysUser user = sysUserMapper.get(userId);
        if (user != null) {
            user.setRoles(sysRoleMapper.findListByUserId(userId));
        }
        return sysUserMapper.get(userId);
    }

    @Override
    @Transactional(readOnly = false)
    public SysUser saveUser(SysUser user) {
        if ( user.getId() == null ) {
            user.preInsert();
            sysUserMapper.insert(user);
        } else {
            // 更新用户数据
            user.preUpdate();
            sysUserMapper.update(user);
        }

        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUserInfo(SysUser user) {
        // 更新用户数据
        user.preUpdate();
        sysUserMapper.updateInfo(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUserById(Integer userId) {
        sysUserMapper.deleteById(userId);
        SysUser user = new SysUser();
        user.setId(userId);
        //删除用户对应的角色信息
        sysUserMapper.deleteUserRole(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUserPasswordById(Integer userId, String newPassword) {

        SysUser user = new SysUser(userId);
        user.setPassword(newPassword);
        sysUserMapper.updatePasswordById(user);
    }

    //Menu

    @Override
    public List<SysMenu> getMenuNav(Integer userId) {
        return makeTree(getMenuListByUserId(userId), true);
    }

    @Override
    public List<SysMenu> getMenuTree(Integer userId) {
        return makeTree(getMenuListByUserId(userId), false);
    }

    @Override
    public List<SysMenu> getMenuList(Integer userId) {
        List<SysMenu> resultList = new ArrayList<>();
        //按父子顺序排列菜单列表
        sortList(resultList, getMenuListByUserId(userId), "");
        return resultList;
    }

    /**
     * 菜单排序
     *
     * @param list       目标菜单列表
     * @param sourceList 原始菜单列表
     * @param parentId   父级编号
     */
    private void sortList(List<SysMenu> list, List<SysMenu> sourceList, String parentId) {
        sourceList.stream()
            .filter(menu -> menu.getParentId() != null && menu.getParentId().equals(parentId))
            .forEach(menu -> {
                list.add(menu);
                // 判断是否还有子节点, 有则继续获取子节点
                sourceList.stream()
                    .filter(child -> child.getParentId() != null && child.getParentId().equals(menu.getId()))
                    .peek(child -> sortList(list, sourceList, menu.getId().toString()))
                    .findFirst();
            });
    }

    /**
     * 获得用户菜单列表，超级管理员可以查看所有菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    private List<SysMenu> getMenuListByUserId(Integer userId) {
        List<SysMenu> menuList;
        //超级管理员
        if ( SysUser.ADMIN_USER_ID == userId ) {
            menuList = sysMenuMapper.findAllList();
        } else {
            menuList = sysMenuMapper.findListByUserId(userId);
        }
        return menuList;
    }

    /**
     * 构建树形结构
     *
     * @param originals 原始数据
     * @param useShow   是否使用开关控制菜单显示隐藏
     * @return 菜单列表
     */
    private List<SysMenu> makeTree(List<SysMenu> originals, boolean useShow) {

        // 构建一个Map,把所有原始数据的ID作为Key,原始数据对象作为VALUE
        Map<String, SysMenu> dtoMap = new HashMap<>();
        for (SysMenu node : originals) {
            // 原始数据对象为Node，放入dtoMap中。
            String id = node.getId().toString();
            if (node.getShow() || !useShow) {
                dtoMap.put(id, node);
            }
        }

        List<SysMenu> result = new ArrayList<>();
        for (Map.Entry<String, SysMenu> entry : dtoMap.entrySet()) {
            SysMenu node = entry.getValue();
            //String parentId = node.getParentId().toString();
            if (node.getParentId()== null) {
                // 如果是顶层节点，直接添加到结果集合中
                result.add(node);
            } else {
                // 如果不是顶层节点，有父节点，然后添加到父节点的子节点中
                SysMenu parent = dtoMap.get(node.getParentId().toString());
                parent.addChild(node);
                parent.setLeaf(false);
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMenuById(Integer menuId) {
        sysMenuMapper.deleteById(menuId);
    }

    @Override
    public SysMenu getMenuById(Integer menuId) {
        return sysMenuMapper.get(menuId);
    }

    @Override
    @Transactional(readOnly = false)
    public SysMenu saveMenu(SysMenu menu) {

    	/**
    	 * 该方法应该分为4个分支考虑
    	 * 1、新增顶级菜单时，id,parentId 均为空，insert数据
    	 * 2、新增非顶级菜单时,id为空， parentId 非空，insert数据
    	 * 3、编辑顶级菜单时，id非空， parentId 为空，update数据
    	 * 4、编辑非顶级菜单时，id非空， parentId 非空，update数据
    	 */
    	if( menu.getId() == null ){
    		if( menu.getParentId() == null ){
    			//新增顶级菜单
    			menu.preInsert();
    			sysMenuMapper.insert(menu);
    		}else{
    			//新增非顶级菜单
    			SysMenu parentMenu = this.getMenuById(menu.getParentId());
    			//获取父菜单的parentIds
    			String parentParentIds = parentMenu == null ? "" : parentMenu.getParentIds(); 
    			String parentIds = parentParentIds + parentMenu.getId() + ",";
    			menu.setParentIds(parentIds);
    			menu.preInsert();
    			sysMenuMapper.insert(menu);
    		}
    	}else{
			//编辑顶级菜单
    		//编辑非顶级菜单
			menu.preUpdate();
			sysMenuMapper.update(menu);
    	}
        return menu;
    }

    //Role

    @Override
    public PageInfo<SysRole> findRolePage(Paging page, SysRole role) {
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<SysRole> list = sysRoleMapper.findList(role);
        return new PageInfo<>(list);
    }

    @Override
    public List<SysRole> findAllRoleList() {
        return sysRoleMapper.findAllList();
    }

    @Override
    public SysRole getRoleById(Integer roleId) {

        SysRole role = sysRoleMapper.get(roleId);
        if (role != null) {
            role.setMenus(sysMenuMapper.findListByRoleId(roleId));
        }

        return role;
    }


    @Override
    @Transactional(readOnly = false)
    public SysRole saveRole(SysRole role) {
        if ( role.getId() == null ) {
            role.preInsert();
            sysRoleMapper.insert(role);
        } else {
            // 更新角色数据
            role.preUpdate();
            sysRoleMapper.update(role);
            sysRoleMapper.deleteRoleMenu(role);
        }

        // 更新角色与菜单关联
        if (role.getMenus() != null && !role.getMenus().isEmpty()) {
            sysRoleMapper.insertRoleMenu(role);
        }

        return role;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteRoleById(Integer roleId) {
        sysRoleMapper.deleteById(roleId);
    }

	@Override
	public List<SysRole> getAuthList(int userId) {
		return sysUserMapper.getAuthList(userId);
	}

	@Override
	public List<SysRole> getUnAuthList(int userId) {
		return sysUserMapper.getUnAuthList(userId);
	}

	@Override
	@Transactional(readOnly = false)
	public void addAuthList(int userId, List<SysRole> list) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		for( SysRole role: list ){
			params.put("userId", userId);
			params.put("roleId", role.getId());
			sysUserMapper.addAuthInfo(params);
		}
		
	}

	@Override
	@Transactional(readOnly = false)
	public void cancelAuthList(int userId, List<SysRole> list) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		for( SysRole role: list ){
			params.put("userId", userId);
			params.put("roleId", role.getId());
			sysUserMapper.cancelAuthInfo(params);
		}
	}

	@Override
	public Map<String, Object> userLoginLimit(String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 1、实现方式为，将当前用户的登陆时间记录到缓存系统中，设置失效时间为1分钟
		 * 2、第二次登陆的时候，查询缓存是否存在上次的登陆记录，如果存在，则更新当前的缓存信息
		 * 3、当连续到某次登陆与缓存中的第一次登录时间大于1分钟时，失效该缓存重新记录缓存
		 * 4、如果连续超过5次的登陆都集中在1分钟内时，锁定该用户15分钟之内不可登陆
		 */
		if( redisRepository.exists("lock_user_" + account) ){
			//可以拿到锁定的时间
			String lockTime = redisRepository.get("lock_user_" + account);
			long time = Long.parseLong(lockTime);
			long nowTime = System.currentTimeMillis();
			float exeTime = (float)(nowTime - time)/60000;
			int seco = (int) exeTime;
			int count = 15 -seco;
			if( count == 0 ){
				count = 1;
			}
			map.put("isSuccess", false);
			map.put("responseMsg", "您的账号由于登陆过于频繁，已经被系统锁定，请" + count + "分钟之后再试");
		}else{
			//检查系统是否存在当前用户登陆的次数限制
			if( redisRepository.exists("count_user_login_" + account)){
				String countUserLogin = redisRepository.get("count_user_login_" + account);
				List<Long> list = new ArrayList<Long>();
				JSONArray jsonArray = JSONArray.parseArray(countUserLogin);
				list = (List<Long>) JSONArray.parseArray(jsonArray.toJSONString(), Long.class);
				//获取第一次登陆的时间
				long firstTime = list.get(0);
				long nowTime = System.currentTimeMillis();
				float exeTime = (float)(nowTime - firstTime)/1000;
				int seco = (int) exeTime/60;
				int count = 15 -seco;
				if( count == 0 ){
					count = 1;
				}
				//如果当前时间与第一次登陆时间的时间差大于1分钟
				if( exeTime > 60 ){
					redisRepository.del("count_user_login_" + account);
					map.put("isSuccess", true);
					map.put("responseMsg", "检验成功");
				}else{
					//检查当前list里面的登陆次数
					if( list.size() < 5 ){
						list.add(nowTime);
						redisRepository.setExpire("count_user_login_" + account, list.toString(), 60);
						map.put("isSuccess", true);
						map.put("responseMsg", "检验成功");
					}else{
						redisRepository.setExpire("lock_user_" + account, nowTime+"", 900);
						map.put("isSuccess", false);
						map.put("responseMsg", "您的账号由于登陆过于频繁，已经被系统锁定，请" + count + "分钟之后再试");
					} 
				}
			}else{
				//存入当前登陆时间
				List<Long> list = new ArrayList<Long>();
				long nowTime = System.currentTimeMillis();
				list.add(nowTime);
				redisRepository.setExpire("count_user_login_" + account, list.toString(), 60);
				
				map.put("isSuccess", true);
				map.put("responseMsg", "检验成功");
			}
		}
		return map;
	}
}
