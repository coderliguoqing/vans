package cn.com.guoqing.vans.admin.timer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.com.guoqing.vans.admin.timer.service.JobService;
import cn.com.guoqing.vans.system.api.entity.SysScheduleJobEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleLogEntity;
import cn.com.guoqing.vans.system.api.service.ISysScheduleJobService;

@Service(value="jobService")
public class JobServiceImpl implements JobService {
	
	@Autowired
	@Qualifier("Scheduler")
	private Scheduler scheduler;
	@Reference
	private ISysScheduleJobService scheduleJobService;
	
	public List<SysScheduleJobEntity> getAllJob() throws Exception {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<SysScheduleJobEntity> jobList = new ArrayList<SysScheduleJobEntity>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				SysScheduleJobEntity job = new SysScheduleJobEntity();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}
	
	public List<SysScheduleJobEntity> getRunningJob() throws Exception {
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<SysScheduleJobEntity> jobList = new ArrayList<SysScheduleJobEntity>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			SysScheduleJobEntity job = new SysScheduleJobEntity();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	public void pauseJob(SysScheduleJobEntity scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	public void resumeJob(SysScheduleJobEntity scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	public void deleteJob(SysScheduleJobEntity scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
		scheduleJobService.deleteJobById(scheduleJob.getId());
	}

	public void runAJobNow(SysScheduleJobEntity scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	public void updateJobCron(SysScheduleJobEntity scheduleJob) throws Exception {

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
		
	}

	public void deleteTask(SysScheduleJobEntity scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	@Override
	public List<String> getJobPara(String beanClass, String methodName)
			throws Exception {
		return scheduleJobService.getJobPara(beanClass, methodName);
	}
	
	@Override
	public void addLog(SysScheduleLogEntity mallSysScheduleLog) throws Exception {
		scheduleJobService.addLog(mallSysScheduleLog);
	}

}
