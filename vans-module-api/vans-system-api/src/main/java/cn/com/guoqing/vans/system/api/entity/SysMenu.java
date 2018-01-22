package cn.com.guoqing.vans.system.api.entity;

import org.hibernate.validator.constraints.Length;

import cn.com.guoqing.vans.common.api.DataEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单Entity
 *
 * @author Guoqing
 */
public class SysMenu extends DataEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 父级编号
     */
    private Integer parentId;
    /**
     * 所有父级编号
     */
    private String parentIds;
    /**
     * 名称
     */
    private String text;
    /**
     * 链接
     */
    private String url;
    /**
     * 图标
     */
    private String icon;
    /**
     * 页面打开方式
     */
    private String targetType;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否在菜单中显示（1：显示；0：不显示）
     */
    private Boolean isShow;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 是否是叶子节点
     */
    private Boolean leaf = true;
    /**
     * 子节点
     */
    private List<SysMenu> children = new ArrayList<>();

    public SysMenu() {
        super();
    }

    public SysMenu(Integer id) {
        super(id);
    }

    @Override
    public void preInsert() {
        super.preInsert();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min = 1, max = 100)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Length(min = 0, max = 2000)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 0, max = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	@NotNull
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    @Length(min = 0, max = 200)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    /**
     * 添加子节点
     *
     * @param node 菜单节点
     */
    public void addChild(SysMenu node) {
        this.children.add(node);
    }

}