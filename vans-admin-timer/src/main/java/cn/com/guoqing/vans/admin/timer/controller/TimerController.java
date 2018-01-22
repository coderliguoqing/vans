package cn.com.guoqing.vans.admin.timer.controller;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.guoqing.vans.admin.timer.factory.QuartzJobFactory;
import cn.com.guoqing.vans.admin.timer.response.ResponseBean;
import cn.com.guoqing.vans.admin.timer.service.JobService;
import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.common.util.SpringContextHolder;
import cn.com.guoqing.vans.system.api.entity.SysScheduleJobEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleParaEntity;
import cn.com.guoqing.vans.system.api.service.ISysScheduleJobService;

/**
 * 
 * @Description  定时器控制类
 *
 * @author Guoqing
 * @Date 2018年1月19日
 */
@RestController
@CrossOrigin
@RequestMapping("/task")
public class TimerController {
	
	private Logger LOGGER = LoggerFactory.getLogger(TimerController.class);
	
	@Reference
	private ISysScheduleJobService scheduleJobService;
	@Autowired
	private RedisRepository redisRepository;
	@Autowired
	private JobService jobService;
	@Autowired
	@Qualifier("Scheduler")
	private Scheduler scheduler;
	
	/**
	 * 修改定时任务运行状态
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/changeJobStatus")
	public ResponseBean changeJobStatus( @RequestBody JSONObject jsonObject) throws Exception{
		int jobId = jsonObject.getInteger("jobId");
		String cmd = jsonObject.getString("cmd");
		
		SysScheduleJobEntity job = scheduleJobService.getTaskById(jobId);
		if( job == null ){
			return new ResponseBean(false, 1001, "job can not null", null);
		}
		if( "stop".equals(cmd)){
			jobService.deleteTask(job);
			job.setJobStatus(SysScheduleJobEntity.STATUS_NOT_RUNNING);
		}else{
			job.setJobStatus(SysScheduleJobEntity.STATUS_RUNNING);
			addJob(job);
		}
		scheduleJobService.updateJob(job);
		return new ResponseBean(true, 0, "请求成功", null);
	}
	
	/**
	 * <p>Description:获取定时器列表</p>
	 * JobtaskController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/getTaskList", method = RequestMethod.POST)
	public ResponseBean getTaskList(@RequestBody JSONObject jsonObject){
		try {
			return new ResponseBean(true, 0, "请求成功", scheduleJobService.getAllTask());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false, 1201, "请求数据库失败", null);
		}
	}
	
	/**
	 * <p>Description:添加一个定时器</p>
	 * JobtaskController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/addTask", method = RequestMethod.POST)
	public ResponseBean addTask(@RequestBody JSONObject jsonObject){
		
		Boolean isContinue = Boolean.TRUE;
		
		if( !jsonObject.containsKey("MallSysScheduleJob") ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}else{
			SysScheduleJobEntity scheduleJob = new SysScheduleJobEntity();
			scheduleJob.setJobName(jsonObject.getJSONObject("MallSysScheduleJob").getString("jobName"));
			scheduleJob.setJobGroup(jsonObject.getJSONObject("MallSysScheduleJob").getString("jobGroup"));
			scheduleJob.setJobStatus(jsonObject.getJSONObject("MallSysScheduleJob").getString("jobStatus"));
			scheduleJob.setCronExpression(jsonObject.getJSONObject("MallSysScheduleJob").getString("cronExpression"));
			scheduleJob.setDescription(jsonObject.getJSONObject("MallSysScheduleJob").getString("description"));
			scheduleJob.setBeanClass(jsonObject.getJSONObject("MallSysScheduleJob").getString("beanClass"));
			scheduleJob.setIsConcurrent(jsonObject.getJSONObject("MallSysScheduleJob").getString("isConcurrent"));
			scheduleJob.setSpringId(jsonObject.getJSONObject("MallSysScheduleJob").getString("springId"));
			scheduleJob.setMethodName(jsonObject.getJSONObject("MallSysScheduleJob").getString("methodName"));
			scheduleJob.setCreateDate(new Date());
			scheduleJob.setUpdateDate(new Date());
			
			JSONArray jsonArray = jsonObject.getJSONArray("paras");
			List<SysScheduleParaEntity> list = new ArrayList<SysScheduleParaEntity>();
			list = (List<SysScheduleParaEntity>) JSONArray.parseArray(jsonArray.toJSONString(), SysScheduleParaEntity.class);
			
			try {
				
				CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
				isContinue = Boolean.TRUE;
			} catch (Exception e) {
				isContinue = Boolean.FALSE;
				e.printStackTrace();
				return new ResponseBean(false, 1301, "cron表达式有误，不能被解析！", null);
			}
			Object object = null;
			
			if( isContinue == true ){
				
				try {
					if( StringUtils.isNotBlank(scheduleJob.getSpringId()) ){
						object = SpringContextHolder.getBean(scheduleJob.getSpringId());
					}else{
						Class clazz = Class.forName(scheduleJob.getBeanClass());
						object = clazz.newInstance();
					}
				} catch (Exception e) {
					isContinue = Boolean.FALSE;
					e.printStackTrace();
					return new ResponseBean(false, 1301, "未找到目标类", null);
				}
			}
			
			if( isContinue == true ){
				if( object == null ){
					isContinue = Boolean.FALSE;
					return new ResponseBean(false, 1301, "未找到目标类", null);
				}else{
					Class clazz = object.getClass();
					Method method = null;
					
					try {
						method = clazz.getMethod(scheduleJob.getMethodName(), null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if( method == null ){
						isContinue = Boolean.FALSE;
						return new ResponseBean(false, 1301, "未找到目标方法", null);
					}
				}
			}
			
			if( isContinue == true ){
				try {
					scheduleJobService.addTask(scheduleJob);
					//添加定时器之后，即时启动定时器
					addJob(scheduleJob);
					//获取jobid
					int jobId = scheduleJobService.getJobId(scheduleJob);
					List<SysScheduleParaEntity> paraList = new ArrayList<SysScheduleParaEntity>();
					if( list.size() > 0 ){
						for( SysScheduleParaEntity para : list ){
							para.setJobId(jobId);
							paraList.add(para);
						}
					}
					scheduleJobService.addJobPara(paraList);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseBean(false, 1301, "保存失败，请检查name group组合是否重复！", null);
				}
			}
		}
		return new ResponseBean(true, 0, "保存成功！", null);
	}
	
	/**
	 * 立即执行任务
	 * @param jsonObject
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/runJobNow", method = RequestMethod.POST)
	public ResponseBean runJobNow( @RequestBody JSONObject jsonObject){
		
		if( !jsonObject.containsKey("MallSysScheduleJob") ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}else{
			try {
				JSONObject object = jsonObject.getJSONObject("MallSysScheduleJob");
				SysScheduleJobEntity scheduleJob = (SysScheduleJobEntity) JSONObject.parseObject(object.toJSONString(), SysScheduleJobEntity.class);
				jobService.runAJobNow(scheduleJob);
				return new ResponseBean(true, 0, "请求成功", null);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseBean(false, 1201, "未找到运行的作业", null);
			}
		}
	}
	
	/**
	 * <p>Description:修改定时器表达式</p>
	 * JobtaskController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/updateCron", method = RequestMethod.POST)
	public ResponseBean updateCron(@RequestBody JSONObject jsonObject){
		
		if( !jsonObject.containsKey("jobId") || !jsonObject.containsKey("cron") ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}
		
		Integer jobId = jsonObject.getInteger("jobId");
		String cron = jsonObject.getString("cron");
		if( jobId == null || "".equals(cron) || cron == null ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}
		
		try {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false, 1301, "cron表达式有误，不能被解析！", null);
		}
		
		try {
			scheduleJobService.updateCron(jobId, cron);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false, 1301, "cron更新失败，请重试!", null);
		}
		return new ResponseBean(true, 0, "请求成功", null);
	}
	
	/**
	 * <p>Description:删除定时器</p>
	 * JobtaskController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/deleteTask", method = RequestMethod.POST)
	public ResponseBean deleteTask(@RequestBody JSONObject jsonObject){
		if( !jsonObject.containsKey("jobId") ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}
		Integer jobId = jsonObject.getInteger("jobId");
		if( jobId == null ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}else{
			try {
				SysScheduleJobEntity scheduleJob = scheduleJobService.getTaskById(jobId);
				jobService.deleteJob(scheduleJob);
				return new ResponseBean(true, 0, "请求成功", null);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseBean(false, 1301, "操作失败，请重试!", null);
			}
		}
	}
	
	/**
	 * <p>Description:修改定时器参数</p>
	 * JobtaskController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/updateJobPara", method = RequestMethod.POST)
	public ResponseBean updateJobPara(@RequestBody JSONObject jsonObject){
		JSONArray jsonArray = jsonObject.getJSONArray("criteria");
		List<SysScheduleParaEntity> list = new ArrayList<SysScheduleParaEntity>();
		list = (List<SysScheduleParaEntity>) JSONArray.parseArray(jsonArray.toJSONString(), SysScheduleParaEntity.class);
		
		if( !jsonObject.containsKey("jobId") || !jsonObject.containsKey("paraNum") ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}
		Integer jobId = jsonObject.getInteger("jobId");
		try {
			scheduleJobService.updateJobPara(list, jobId);
			return new ResponseBean(true, 0, "请求成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false, 1301, "操作失败，请重试!", null);
		}
	}
	
	/**
	 * <p>Description:暂停一个JOB</p>
	 * JobtaskController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/pauseJob", method = RequestMethod.POST)
	public ResponseBean pauseJob(@RequestBody JSONObject jsonObject){
		if( !jsonObject.containsKey("jobName") || !jsonObject.containsKey("jobGroup") ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}
		String jobName = jsonObject.getString("jobName");
		String jobGroup = jsonObject.getString("jobGroup");
		SysScheduleJobEntity scheduleJob = new SysScheduleJobEntity();
		scheduleJob.setJobName(jobName);
		scheduleJob.setJobGroup(jobGroup);
		
		try {
			jobService.pauseJob(scheduleJob);
			return new ResponseBean(true, 0, "请求成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false, 1301, "操作失败，请重试!", null);
		}
	}
	
	/**
	 * <p>Description:恢复定时任务</p>
	 * JobtaskController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/resumeJob", method = RequestMethod.POST)
	public ResponseBean resumeJob(@RequestBody JSONObject jsonObject){
		if( !jsonObject.containsKey("jobName") || !jsonObject.containsKey("jobGroup") ){
			return new ResponseBean(false, 1101, "非法参数", null);
		}
		String jobName = jsonObject.getString("jobName");
		String jobGroup = jsonObject.getString("jobGroup");
		SysScheduleJobEntity scheduleJob = new SysScheduleJobEntity();
		scheduleJob.setJobName(jobName);
		scheduleJob.setJobGroup(jobGroup);
	
		try {
			jobService.resumeJob(scheduleJob);
			return new ResponseBean(true, 0, "请求成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false, 1301, "操作失败，请重试!", null);
		}
	}
	
	@PostConstruct
	public void start() throws Exception {
		scheduler.start();

		// 这里获取任务信息数据
		List<SysScheduleJobEntity> jobList = scheduleJobService.getAllTask();
	
		for (SysScheduleJobEntity job : jobList) {
			addJob(job);
		}

	}
	
	public void addJob(SysScheduleJobEntity job) throws Exception {
		if( job == null || !SysScheduleJobEntity.STATUS_RUNNING.equals(job.getJobStatus())){
			return;
		}
		
		//添加任务时，初始化任务并发运行标记在redis中的key。主要解决服务器重启导致上次任务的运行标记未清除的问题
		if( redisRepository.exists(job.getJobGroup() + "_" + job.getJobName())){
			redisRepository.del(job.getJobGroup() + "_" + job.getJobName());
		}
		
		LOGGER.info(scheduler.getSchedulerName() + "--------------------------add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		
		//不存在，创建一个
		if( trigger == null ){
//			Class clazz = SysScheduleJobEntity.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
			Class clazz = QuartzJobFactory.class;
			
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			
			jobDetail.getJobDataMap().put("scheduleJob", job);
			
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			
			scheduler.scheduleJob(jobDetail, trigger);
			
		}else{
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

}
