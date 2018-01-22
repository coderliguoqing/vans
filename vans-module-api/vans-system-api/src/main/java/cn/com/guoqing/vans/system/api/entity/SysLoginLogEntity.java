package cn.com.guoqing.vans.system.api.entity;

import cn.com.guoqing.vans.common.api.DataEntity;



/**
 * 登录记录
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 15:13:26
 */
public class SysLoginLogEntity extends DataEntity {
    private static final long serialVersionUID = 1L;
	//日志名称
	private String logName;
	//管理员id
	private Integer userId;
	//是否执行成功
	private String succeed;
	//具体消息
	private String message;
	//登录ip
	private String ip;

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
	 * 设置：管理员id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：管理员id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：是否执行成功
	 */
	public void setSucceed(String succeed) {
		this.succeed = succeed;
	}
	/**
	 * 获取：是否执行成功
	 */
	public String getSucceed() {
		return succeed;
	}
	/**
	 * 设置：具体消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取：具体消息
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置：登录ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：登录ip
	 */
	public String getIp() {
		return ip;
	}

}
