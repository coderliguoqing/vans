package cn.com.guoqing.vans.common.api;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Entity 基类
 *
 * @author guoqing
 */
public abstract class BaseEntity implements Serializable {

    /**
     * 实体编号（唯一标识）
     */
    private Integer id;

    public BaseEntity() {

    }

    public BaseEntity(Integer id) {
        this();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert();

    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
