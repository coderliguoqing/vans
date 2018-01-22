package cn.com.guoqing.vans.admin.timer.security;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.com.guoqing.vans.admin.timer.security.model.AuthUserFactory;
import cn.com.guoqing.vans.system.api.entity.SysUser;
import cn.com.guoqing.vans.system.api.service.ISystemService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security User Detail Service
 *
 * @author Guoqing
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 系统服务
     */
    @Reference
    private ISystemService systemService;

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        SysUser user = systemService.getUserByLoginName(loginName);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
        } else {
            return AuthUserFactory.create(user);
        }
    }
}
