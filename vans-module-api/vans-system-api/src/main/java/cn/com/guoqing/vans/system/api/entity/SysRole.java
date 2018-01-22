package cn.com.guoqing.vans.system.api.entity;

import org.hibernate.validator.constraints.Length;

import cn.com.guoqing.vans.common.api.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色Entity
 *
 * @author Guoqing
 */
public class SysRole extends DataEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 名称
     */
    private String name;
    
    /**
     * 角色代码
     */
    private String code;
    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 菜单列表
     */
    private List<SysMenu> menus = new ArrayList<>();

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}
