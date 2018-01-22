package cn.com.guoqing.vans.system.api.entity;

import cn.com.guoqing.vans.common.api.DataEntity;

/**
 * 
 * @Description  列属性
 *
 * @author Guoqing
 * @Date 2018年1月15日
 */
public class SysTable  extends DataEntity {
	private static final long serialVersionUID = 1445403870473532561L;
	//表名
    private String tableName;
    //表类型
    private String engine;
    //表备注
    private String tableComment;
    
    //创建时间
    private String createTime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
