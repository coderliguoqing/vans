package cn.com.guoqing.vans.common.security.model;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @Description  security user
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
public abstract class AbstractAuthUser implements UserDetails {

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired(){
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked(){
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired(){
		return true;
	}

}
