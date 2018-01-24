package cn.com.guoqing.vans.admin.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.com.guoqing.vans.admin.web.common.controller.BaseController;
import cn.com.guoqing.vans.admin.web.response.ResponseBean;
import cn.com.guoqing.vans.admin.web.security.model.AuthUser;
import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.common.security.JwtTokenUtil;
import cn.com.guoqing.vans.common.util.StringHelper;
import cn.com.guoqing.vans.common.util.WebUtils;
import cn.com.guoqing.vans.system.api.entity.SysMenu;
import cn.com.guoqing.vans.system.api.entity.SysRole;
import cn.com.guoqing.vans.system.api.entity.SysUser;
import cn.com.guoqing.vans.system.api.service.ISystemService;

/**
 * 
 * @Description  authentication controller
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController extends BaseController{
	
	private Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Reference
	private ISystemService systemService;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RedisRepository redisRepository;
	
	
	/**
	 * create token
	 * @param jsonObject
	 * @param session
	 * @return
	 */
	@PostMapping("/token")
	public ResponseBean createToken(@RequestBody JSONObject jsonObject, HttpSession session){
		String userName = jsonObject.getString("userName").trim();
		String password = jsonObject.getString("password").trim();
//		String kaptcha = jsonObject.getString("kaptcha").trim();
		
/*		if( !redisRepository.exists("key_rand_" + session.getId()) ){
			return new ResponseBean(false, 1001, "验证码已失效，请重新登录", null);
		}
		
		//验证验证码是否正确
		String code = redisRepository.get("key_rand_" + session.getId());
		if( !code.equals(kaptcha) ){
			return new ResponseBean(false, 1002, "验证码错误，请重新登录", null);
		}else{
			//验证成功即删除验证码
			redisRepository.del("key_rand_" + session.getId());
		}*/
		
		//做用户登录次数限制，1分钟内不能超过5次
		Map<String, Object> map = systemService.userLoginLimit(userName);
		if( (Boolean)map.get("isSuccess") == false ){
			return new ResponseBean(false, 1003, map.get("responseMsg").toString(), null);
		}
		
		try {
			
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userName, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			final String token = jwtTokenUtil.generateToken(userDetails, jwtTokenUtil.getRandomKey());
			
			return new ResponseBean(true, 0, "登录成功", JwtTokenUtil.TOKEN_TYPE_BEARER + " " + token);
		} catch (UsernameNotFoundException e) {
			LOGGER.info("用户认证失败：" + "userName wasn't in the system");
			return new ResponseBean(false, 1004, "用户名或密码错误", null);
		} catch (LockedException lae) {
			LOGGER.info("用户认证失败：" + "account for that username is locked, can't login");
			return new ResponseBean(false, 1005, "账号已被锁定，不能登录", null);
		} catch (AuthenticationException ace) {
			LOGGER.info("用户认证失败：" + ace);
			ace.printStackTrace();
			return new ResponseBean(false, 1006, "登录失败", null);
		}
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@DeleteMapping("/token")
	public ResponseBean deleteToken(HttpServletRequest request){
		String tokenHeader = request.getHeader("Authorization");
		String token = tokenHeader.split(" ")[1];
		jwtTokenUtil.deleteToken(token);
		return new ResponseBean(true, 0, "请求成功", null);
	}
	
	 /**
     * List page info.
     *
     * @param role the role
     * @param page the page
     * @return the page info
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value = "/role/list")
    public PageInfo<SysRole> list( @RequestBody JSONObject jsonObject) {
    	SysRole role = JSONObject.parseObject(jsonObject.getJSONObject("role").toJSONString(), SysRole.class);
		Paging page = (Paging) JSONObject.parseObject(jsonObject.getJSONObject("page").toJSONString(), Paging.class);
        return systemService.findRolePage(page, role);
    }

    /**
     * All list.
     *
     * @return the list
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/role/all")
    public List<SysRole> all() {
        return systemService.findAllRoleList();
    }

    /**
     * Gets role.
     *
     * @param roleId the role id
     * @return the role
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/role/{roleId}")
    public SysRole getRole(@PathVariable("roleId") Integer roleId) {
        return systemService.getRoleById(roleId);
    }

    /**
     * Save role sys role.
     *
     * @param role the role
     * @return the sys role
     */
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @PostMapping(value = "/role/edit")
    public SysRole saveRole(@Valid @RequestBody JSONObject jsonObject) {
    	SysRole role = JSONObject.parseObject(jsonObject.getJSONObject("role").toJSONString(), SysRole.class);
        return systemService.saveRole(role);
    }

    /**
     * Delete response entity.
     *
     * @param roleId the role id
     * @return the response entity
     */
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @DeleteMapping(value = "/role/{roleId}")
    public ResponseEntity delete(@PathVariable("roleId") Integer roleId) {
        systemService.deleteRoleById(roleId);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    /**
     * Gets current user info.
     *
     * @return the current user info
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/user/info")
    public Object getCurrentUserInfo() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Save current user info response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/user/info")
    public ResponseEntity saveCurrentUserInfo(@Valid @RequestBody SysUser user) {
        AuthUser authUser = WebUtils.getCurrentUser();
        //只能更新当前用户信息
        if (authUser.getId() == user.getId()) {
            // 保存用户信息
            systemService.updateUserInfo(user);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    
    /**
     * Reset password response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws BusinessException the business exception
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/user/password")
    public ResponseBean updatePassword(@RequestBody JSONObject jsonObject) {
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");

        try {
			AuthUser user = WebUtils.getCurrentUser();

			// 重置密码
			if (StringHelper.isNotBlank(oldPassword) && StringHelper.isNotBlank(newPassword)) {

			    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			        return new ResponseBean(false, 1001, "旧密码错误", null);
			    }

			    systemService.updateUserPasswordById(user.getId(), passwordEncoder.encode(newPassword));
			}
			return new ResponseBean(true, 0, "请求成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false, 1001, "网络繁忙", null);
		}
    }
    
    /**
     * List page info.
     *
     * @param user the user
     * @param page the page
     * @return the page info
     */
    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value = "/user/list")
    public PageInfo<SysUser> userList(@RequestBody JSONObject jsonObject) {
    	SysUser user = JSONObject.parseObject(jsonObject.getJSONObject("user").toJSONString(), SysUser.class);
		Paging page = (Paging) JSONObject.parseObject(jsonObject.getJSONObject("page").toJSONString(), Paging.class);
        return systemService.findUserPage(page, user);
    }

    /**
     * Gets user.
     *
     * @param userId the user id
     * @return the user
     */
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/user/{userId}")
    public SysUser getUser(@PathVariable("userId") Integer userId) {
        return systemService.getUserById(userId);
    }

    /**
     * Save user sys user.
     *
     * @param user the user
     * @return the sys user
     */
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @PostMapping(value = "/user/edit")
    public ResponseBean saveUser( @RequestBody JSONObject jsonObject) {
    	try {
			SysUser user = JSONObject.parseObject(jsonObject.getJSONObject("user").toJSONString(), SysUser.class);
			String password = user.getPassword();
			//如果是新增用户
			if( user.getId() == null ){
				//用户密码不能为空
				if (StringHelper.isNotBlank(password)) {
					user.setPassword(passwordEncoder.encode(password));
				}        	
			}
			// 保存用户信息
			systemService.saveUser(user);
	        return new ResponseBean(true, 0, "请求成功", null);
		} catch (Exception e) {
			e.printStackTrace();
	        return new ResponseBean(false, 1001, "网络繁忙，请稍后再试", null);
		}

    }

    /**
     * Delete response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @DeleteMapping(value = "/user/{userId}")
    public ResponseEntity userDelete(@PathVariable("userId") Integer userId) {

        systemService.deleteUserById(userId);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    /**
     * 将用户选择的皮肤信息存入缓存
     * @param skins
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/user/changeSkins")
    public ResponseBean changeSkins(@RequestBody String skins ){
    	AuthUser authUser = WebUtils.getCurrentUser();
    	Integer userId = authUser.getId();
    	if( StringHelper.isEmpty(skins) ){
    		return new ResponseBean(false, 1002 ,"请选择皮肤风格", null);
    	}else{
    		String redisKey = "style_skins_" + userId;
    		redisRepository.set(redisKey, skins);
    	}
    	return new ResponseBean(true, 0 ,"请求成功", null);
    }
    
    @PostMapping(value = "/user/getSkins")
    public Map<String, Object> getSkins() {
    	Map<String, Object> resultMap = new HashMap<>();
    	AuthUser authUser = WebUtils.getCurrentUser();
    	Integer userId = authUser.getId();
		String redisKey = "style_skins_" + userId;
		String skins = redisRepository.get(redisKey);
		resultMap.put("skins", skins);
    	return resultMap;
    }
    
    /**
     * 获取用户已授权列表，未授权列表
     * @param jsonObject
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:auth')")
    @PostMapping(value = "/user/authList")
    public ResponseBean authList( @RequestBody JSONObject jsonObject) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
			int userId = jsonObject.getInteger("userId");
			List<SysRole> authList = systemService.getAuthList(userId);
			List<SysRole> unAuthList = systemService.getUnAuthList(userId);
			result.put("authList", authList);
			result.put("unAuthList", unAuthList);
	        return new ResponseBean(true, 0, "请求成功", result);
		} catch (Exception e) {
			e.printStackTrace();
	        return new ResponseBean(false, 1001, "网络繁忙，请稍后再试", null);
		}
    }
    
    /**
     * 获取用户未授权列表
     * @param jsonObject
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:auth')")
    @PostMapping(value = "/user/editAuthList")
    public ResponseBean editAuthList( @RequestBody JSONObject jsonObject) {
    	try {
			int userId = jsonObject.getInteger("userId");
			String type = jsonObject.getString("type");
			List<SysRole> list = JSONArray.parseArray(jsonObject.getJSONArray("list").toJSONString(), SysRole.class);
			//授权
			if( "auth".equals(type) ){
				systemService.addAuthList(userId, list);
			}else{
				//取消授权
				systemService.cancelAuthList(userId, list);
			}
	        return new ResponseBean(true, 0, "请求成功", null);
		} catch (Exception e) {
			e.printStackTrace();
	        return new ResponseBean(false, 1001, "网络繁忙，请稍后再试", null);
		}
    }
    
    /**
     * Gets menu nav.
     *
     * @return the menu nav
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/menu/nav")
    public List<SysMenu> getMenuNav() {

        AuthUser user = WebUtils.getCurrentUser();

        return systemService.getMenuNav(user.getId());
    }

    /**
     * Gets menu tree.
     *
     * @return the menu tree
     */
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value = "/menu/tree")
    public List<SysMenu> getMenuTree() {

        AuthUser user = WebUtils.getCurrentUser();

        return systemService.getMenuTree(user.getId());
    }

    /**
     * Gets menu list.
     *
     * @return the menu list
     */
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value = "/menu/list")
    public List<SysMenu> getMenuList() {

        AuthUser user = WebUtils.getCurrentUser();

        return systemService.getMenuList(user.getId());
    }

    /**
     * Delete menu response entity.
     *
     * @param menuId the menu id
     * @return the response entity
     */
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    @DeleteMapping(value = "/menu/delete")
    public ResponseEntity deleteMenu(@RequestBody JSONObject jsonObject) {
    	Integer menuId = jsonObject.getInteger("menuId");
        systemService.deleteMenuById(menuId);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Gets menu.
     *
     * @param menuId the menu id
     * @return the menu
     */
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value = "/menu/getMenuInfo")
    public SysMenu getMenu(@RequestBody JSONObject jsonObject) {
    	Integer menuId = jsonObject.getInteger("menuId");
        return systemService.getMenuById(menuId);

    }

    /**
     * Save menu sys menu.
     *
     * @param menu the menu
     * @return the sys menu
     */
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    @PostMapping(value = "/menu/edit")
    public SysMenu saveMenu(@RequestBody JSONObject jsonObject) {
    	SysMenu menu = JSONObject.parseObject(jsonObject.getJSONObject("sysMenu").toJSONString(), SysMenu.class);
        return systemService.saveMenu(menu);
    }

}
