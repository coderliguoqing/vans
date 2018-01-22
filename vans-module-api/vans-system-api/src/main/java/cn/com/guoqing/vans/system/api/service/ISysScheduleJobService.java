package cn.com.guoqing.vans.system.api.service;

import java.util.List;

import cn.com.guoqing.vans.system.api.entity.SysScheduleJobEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleLogEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleParaEntity;

/**
 * spring quartz定时任务记录
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 17:00:12
 */
public interface ISysScheduleJobService {


	/**
	 * <p>Description:添加日志信息</p>
	 * JobLogService.java
	 * return:void
	 */
	public void addLog(SysScheduleLogEntity mallSysScheduleLog) throws Exception;
	
	/**
	 * <p>Description:根据主键获取对应的日志信息</p>
	 * JobLogService.java
	 * return:MallSysLogInfo
	 */
	public SysScheduleLogEntity getLogInfo( int id ) throws Exception;
	

	/**
	 * <p>Description:获取所有的任务</p>
	 * JobTaskService.java
	 * return:List<MallMoonScheduleJob>
	 */
	public List<SysScheduleJobEntity> getAllTask() throws Exception;
	
	/**
	 * 
	 * <p>Description:添加任务到数据库中</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void addTask(SysScheduleJobEntity job) throws Exception;
	
	/**
	 * 
	 * <p>Description:从数据库中查询job</p>
	 * JobTaskService.java
	 * return:MallMoonScheduleJob
	 */
	public SysScheduleJobEntity getTaskById(Integer jobId) throws Exception;

	/**
	 * 
	 * <p>Description:更新任务的cron表达式</p>
	 * JobTaskService.java
	 * return:void
	 */
	public void updateCron(Integer jobId, String cron) throws Exception;
	
	
	
	/**
	 * 
	 * <p>Description:添加job的参数</p>
	 * JobTaskService.java
	 * return:void
	 */
	public boolean addJobPara(List<SysScheduleParaEntity> list) throws Exception;
	
	
	/**
	 * 
	 * <p>Description:更新job的参数</p>
	 * JobTaskService.java
	 * return:void
	 */
	public boolean updateJobPara( List<SysScheduleParaEntity> list, int jobId ) throws Exception;
	
	/**
	 * 
	 * <p>Description:通过类名和方法名获取定时器参数</p>
	 * JobTaskService.java
	 * return:List<String>
	 */
	public List<String> getJobPara( String beanClass, String methodName ) throws Exception;
	
	/**
	 * 
	 * <p>Description:通过springid获取定时器参数</p>
	 * JobTaskService.java
	 * return:List<String>
	 */
	public List<String> getJobPara( String springId ) throws Exception;
	
	/**
	 * <p>Description:根据定时任务的参数获取的JOBID</p>
	 * JobTaskService.java
	 * return:int
	 */
	public int getJobId( SysScheduleJobEntity mallSysScheduleJob ) throws Exception;
	
	/**
	 * 更新job
	 * @param scheduleJobEntity
	 */
	public void updateJob( SysScheduleJobEntity scheduleJobEntity );
	
	/**
	 * 根据ID删除job
	 * @param id
	 */
	public void deleteJobById( Integer id );
	
}
