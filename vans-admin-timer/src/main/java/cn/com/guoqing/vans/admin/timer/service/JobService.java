package cn.com.guoqing.vans.admin.timer.service;

import java.util.List;

import cn.com.guoqing.vans.system.api.entity.SysScheduleJobEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleLogEntity;

public interface JobService {
	
	/**
	 * 
	 * <p>Description:获取所有计划中的任务列表</p>
	 * JobTaskService.java
	 * return:List<MallMoonScheduleJob>
	 */
	public List<SysScheduleJobEntity> getAllJob() throws Exception;
	
	/**
	 * 
	 * <p>Description:获取正在运行当中的job</p>
	 * JobTaskService.java
	 * return:List<MallMoonScheduleJob>
	 */
	public List<SysScheduleJobEntity> getRunningJob() throws Exception;
	
	/**
	 * 
	 * <p>Description:暂停一个job</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void pauseJob(SysScheduleJobEntity scheduleJob) throws Exception;
	
	/**
	 * 
	 * <p>Description:恢复一个Job</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void resumeJob(SysScheduleJobEntity scheduleJob) throws Exception;
	
	/**
	 * 
	 * <p>Description:从数据删除一个job</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void deleteJob(SysScheduleJobEntity scheduleJob) throws Exception;
	
	/**
	 * 
	 * <p>Description:删除一个任务</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void deleteTask(SysScheduleJobEntity scheduleJob) throws Exception;
	
	/**
	 * 立即执行Job
	 * <p>Description:</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void runAJobNow(SysScheduleJobEntity scheduleJob) throws Exception;
	
	/**
	 * 
	 * <p>Description:更新job时间表达式</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void updateJobCron(SysScheduleJobEntity scheduleJob) throws Exception;
	
	/**
	 * 获取方法执行参数
	 * @param beanClass
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public List<String> getJobPara(String beanClass, String methodName) throws Exception;
	
	/**
	 * 添加日志
	 * @param mallSysScheduleLog
	 * @throws Exception
	 */
	public void addLog(SysScheduleLogEntity mallSysScheduleLog) throws Exception;

}
