package cn.com.guoqing.vans.system.api.entity;

import cn.com.guoqing.vans.common.api.DataEntity;



/**
 * 
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 14:39:56
 */
public class SysOperationLogEntity extends DataEntity {
    private static final long serialVersionUID = 1L;

	//日志类型
	private String logType;
	//日志名称
	private String logName;
	//用户ID
	private Integer userId;
	//类名称
	private String className;
	//方法名称
	private String method;
	//是否成功
	private String succeed;
	//备注
	private String message;

	/**
	 * 设置：日志类型
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}
	/**
	 * 获取：日志类型
	 */
	public String getLogType() {
		return logType;
	}
	/**
	 * 设置：日志名称
	 */
	public void setLogName(String logName) {
		this.logName = logName;
	}
	/**
	 * 获取：日志名称
	 */
	public String getLogName() {
		return logName;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：类名称
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取：类名称
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置：方法名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：方法名称
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：是否成功
	 */
	public void setSucceed(String succeed) {
		this.succeed = succeed;
	}
	/**
	 * 获取：是否成功
	 */
	public String getSucceed() {
		return succeed;
	}
	/**
	 * 设置：备注
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取：备注
	 */
	public String getMessage() {
		return message;
	}
}
