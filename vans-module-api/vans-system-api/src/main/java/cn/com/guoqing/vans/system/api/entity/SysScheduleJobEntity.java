package cn.com.guoqing.vans.system.api.entity;

import java.util.Date;
import cn.com.guoqing.vans.common.api.DataEntity;



/**
 * spring quartz定时任务记录
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 17:00:12
 */
public class SysScheduleJobEntity extends DataEntity {
    private static final long serialVersionUID = 1L;
    
    public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";

	//任务名称
	private String jobName;
	//任务分组
	private String jobGroup;
	//任务状态（是否启动任务）
	private String jobStatus;
	//cron表达式
	private String cronExpression;
	//描述
	private String description;
	//任务调用的 包名+类名
	private String beanClass;
	//任务是否有状态
	private String isConcurrent;
	//spring bean
	private String springId;
	//任务调用的方法
	private String methodName;

	/**
	 * 设置：任务名称
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 获取：任务名称
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * 设置：任务分组
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	/**
	 * 获取：任务分组
	 */
	public String getJobGroup() {
		return jobGroup;
	}
	/**
	 * 设置：任务状态（是否启动任务）
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	/**
	 * 获取：任务状态（是否启动任务）
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	/**
	 * 设置：cron表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * 获取：cron表达式
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：任务调用的 包名+类名
	 */
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	/**
	 * 获取：任务调用的 包名+类名
	 */
	public String getBeanClass() {
		return beanClass;
	}
	/**
	 * 设置：任务是否有状态
	 */
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	/**
	 * 获取：任务是否有状态
	 */
	public String getIsConcurrent() {
		return isConcurrent;
	}
	/**
	 * 设置：spring bean
	 */
	public void setSpringId(String springId) {
		this.springId = springId;
	}
	/**
	 * 获取：spring bean
	 */
	public String getSpringId() {
		return springId;
	}
	/**
	 * 设置：任务调用的方法
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：任务调用的方法
	 */
	public String getMethodName() {
		return methodName;
	}
	
}
