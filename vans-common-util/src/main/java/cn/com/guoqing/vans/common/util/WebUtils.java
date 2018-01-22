package cn.com.guoqing.vans.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @Description  WEB工具类
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
public final class WebUtils {
	
	@SuppressWarnings("unchecked")
	public static <T extends UserDetails> T getCurrentUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (T)authentication.getPrincipal();
	}

}
