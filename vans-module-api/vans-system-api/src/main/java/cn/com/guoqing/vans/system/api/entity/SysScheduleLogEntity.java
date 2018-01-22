package cn.com.guoqing.vans.system.api.entity;

import java.util.Date;
import cn.com.guoqing.vans.common.api.DataEntity;



/**
 * 定时器运行时日志表
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 17:00:02
 */
public class SysScheduleLogEntity extends DataEntity {
    private static final long serialVersionUID = 1L;
	
	//当前作业ID
	private Integer jobId;
	//作业名称
	private String jobName;
	//所属分组
	private String jobGroup;
	//
	private String beanClass;
	//方法名
	private String methodName;
	//所属bean
	private String beanId;
	//当前运行方法参数值
	private String paraValue;
	//定时器运行开始时间
	private Date startTime;
	//方法运行结束时间
	private Date endTime;
	//运行时长
	private String runTime;

	/**
	 * 设置：当前作业ID
	 */
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取：当前作业ID
	 */
	public Integer getJobId() {
		return jobId;
	}
	/**
	 * 设置：作业名称
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 获取：作业名称
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * 设置：所属分组
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	/**
	 * 获取：所属分组
	 */
	public String getJobGroup() {
		return jobGroup;
	}
	/**
	 * 设置：
	 */
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	/**
	 * 获取：
	 */
	public String getBeanClass() {
		return beanClass;
	}
	/**
	 * 设置：方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：方法名
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置：所属bean
	 */
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	/**
	 * 获取：所属bean
	 */
	public String getBeanId() {
		return beanId;
	}
	/**
	 * 设置：当前运行方法参数值
	 */
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	/**
	 * 获取：当前运行方法参数值
	 */
	public String getParaValue() {
		return paraValue;
	}
	/**
	 * 设置：定时器运行开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：定时器运行开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：方法运行结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：方法运行结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：运行时长
	 */
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	/**
	 * 获取：运行时长
	 */
	public String getRunTime() {
		return runTime;
	}

}
