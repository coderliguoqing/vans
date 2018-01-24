package cn.com.guoqing.vans.admin.timer.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.com.guoqing.vans.admin.timer.security.model.AuthUser;
import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.common.security.JwtTokenUtil;

@Component
@ConfigurationProperties("jwt")
public class TokenUtil extends JwtTokenUtil {

	@Autowired
	private RedisRepository redisRepository;
	
	/**
	 * 获取用户信息
	 * @param token
	 * @return
	 */
	public UserDetails getUserDetails(String token){
		String userName = getUsernameFromToken(token);
		String user = redisRepository.get("user_auth_info_"+userName);
		return new Gson().fromJson(user, AuthUser.class);
	}

	
}
