package cn.com.guoqing.vans.admin.timer.factory;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.com.guoqing.vans.admin.timer.service.JobService;
import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.common.util.SpringContextHolder;
import cn.com.guoqing.vans.system.api.entity.SysScheduleJobEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleLogEntity;




/**
 * 
 * @Description: 计划任务执行处 无状态
 * @author liguoqing
 * @date 2014年4月24日 下午5:05:47
 */
//@DisallowConcurrentExecution
@Service
public class QuartzJobFactory implements Job {
	public final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private RedisRepository redisRepository;
	@Reference
	private JobService jobService;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		redisRepository = (RedisRepository) SpringContextHolder.getBean("redisRepository");
		jobService = (JobService) SpringContextHolder.getBean("jobService");
		SysScheduleJobEntity scheduleJob = (SysScheduleJobEntity) context.getMergedJobDataMap().get("scheduleJob");
		SysScheduleLogEntity log = new SysScheduleLogEntity();
		log.setBeanClass(scheduleJob.getBeanClass());
		log.setBeanId(scheduleJob.getSpringId());
		long beginTime = System.currentTimeMillis();//1、开始时间
		log.setStartTime(new Date());
		
		/**
		 * @date 2017-11-09 
		 * 此处由于@DisallowConcurrentExecution注解在项目中不生效，导致job的有状态任务执行不能起作用；
		 * 故用redis作为全局控制器，防止有状态的任务并发执行
		 */
		String concurrentKey = scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName();
		Boolean status = false;
		if( "1".equals( scheduleJob.getIsConcurrent() ) ){
			if( redisRepository.exists(concurrentKey) ){
				status = true;
			}else{
				//如果当前job的key不存在redis，则再redis中标记当前任务
				redisRepository.set(concurrentKey, concurrentKey);
			}
		}
		
		if( status){
			//当前状态正在执行，跳过本次执行计划
			logger.info(scheduleJob.getJobGroup() + "-" + scheduleJob.getJobName() + "：任务有状态，且正在运行中，跳过此次任务...");
		}else{
			TaskUtils.invokMethod(scheduleJob);	
			//任务执行完成后，检查当前任务是否已经解除锁定的key
			if( redisRepository.exists(concurrentKey) ){
				//删除redis中的key
				redisRepository.del(concurrentKey);
			}
		}
		
		try {
			log.setEndTime(new Date());
			long endTime = System.currentTimeMillis();//2、结束时间
			long consumeTime = endTime - beginTime;//3、消耗的时间
			log.setRunTime(Long.toString(consumeTime));
			log.setJobGroup(scheduleJob.getJobGroup());
			log.setJobId(scheduleJob.getId());
			log.setJobName(scheduleJob.getJobName());
			log.setMethodName(scheduleJob.getMethodName());
			log.setParaValue(jobService.getJobPara(scheduleJob.getBeanClass(), scheduleJob.getMethodName()).toString());
			jobService.addLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}