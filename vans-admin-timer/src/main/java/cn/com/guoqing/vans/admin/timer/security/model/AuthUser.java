package cn.com.guoqing.vans.admin.timer.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.com.guoqing.vans.common.security.model.AbstractAuthUser;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

/**
 * 
 * @Description  自定义权限对象，便于携带用户信息
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
public class AuthUser extends AbstractAuthUser {

	/**
     * id
     */
    private Integer id;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    
    /**
     * 所属公司编码
     */
    private String companyCode;
    
    /**
     * 所属公司名称
     */
    private String companyName;
    
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 权限
     */
    private Collection<SimpleGrantedAuthority> authorities;
    /**
     * 锁定
     */
    private boolean enabled;
    
	public AuthUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
    

}
