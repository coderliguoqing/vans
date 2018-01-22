package cn.com.guoqing.vans.system.provider.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import cn.com.guoqing.vans.system.provider.mapper.SysScheduleJobMapper;
import cn.com.guoqing.vans.system.provider.mapper.SysScheduleLogMapper;
import cn.com.guoqing.vans.system.provider.mapper.SysScheduleParaMapper;
import cn.com.guoqing.vans.system.api.entity.SysScheduleJobEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleLogEntity;
import cn.com.guoqing.vans.system.api.entity.SysScheduleParaEntity;
import cn.com.guoqing.vans.system.api.service.ISysScheduleJobService;



@Service
@com.alibaba.dubbo.config.annotation.Service
@Transactional(readOnly = true)
public class SysScheduleServiceImpl implements ISysScheduleJobService {
	
	public final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysScheduleLogMapper scheduleLogMapper;
	@Autowired
	private SysScheduleJobMapper scheduleJobMapper;
	@Autowired
	private SysScheduleParaMapper scheduleParaMapper;
	
	@Override
	@Transactional(readOnly = false)
	public void addLog(SysScheduleLogEntity mallSysScheduleLog) throws Exception {
		mallSysScheduleLog.preInsert();
		scheduleLogMapper.insert(mallSysScheduleLog);
	}

	@Override
	public SysScheduleLogEntity getLogInfo(int id) throws Exception {
		return scheduleLogMapper.get(id);
	}
	
	public List<SysScheduleJobEntity> getAllTask() throws Exception {
		return scheduleJobMapper.findAllList();
	}

	@Transactional(readOnly = false)
	public void addTask(SysScheduleJobEntity job) throws Exception {
		job.preInsert();
		scheduleJobMapper.insert(job);
	}

	public SysScheduleJobEntity getTaskById(Integer jobId) throws Exception {
		return scheduleJobMapper.get(jobId);
	}

	@Transactional(readOnly = false)
	public void updateCron(Integer jobId, String cron) throws Exception {
		SysScheduleJobEntity job = getTaskById(jobId);
		if( job == null ){
			return;
		}
		job.setCronExpression(cron);
		if( SysScheduleJobEntity.STATUS_RUNNING.equals(job.getJobStatus())){
			//updateJobCron(job);
		}
		job.preUpdate();
		scheduleJobMapper.update(job);
	}

	
	@Override
	@Transactional(readOnly = false)
	public boolean addJobPara(List<SysScheduleParaEntity> list) throws Exception {
		if( list.size() == 0 ){
			return false;
		}
		
		for (int i = 0; i < list.size(); i++) {
			scheduleParaMapper.insert(list.get(i));
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateJobPara(List<SysScheduleParaEntity> list, int jobId ) throws Exception {
		//先删除原有参数，然后插入新的参数
		scheduleParaMapper.deleteByJobId(jobId);
		
		if( list.size() > 0 ){
			for (int i = 0; i < list.size(); i++) {
				SysScheduleParaEntity para = list.get(i);
				para.setJobId(jobId);
				scheduleParaMapper.insert(para);
			}
		}
		return true;
	}

	@Override
	public List<String> getJobPara(String beanClass, String methodName)
			throws Exception {
		List<String> result = new ArrayList<>();
		SysScheduleJobEntity scheduleJobEntity = scheduleJobMapper.getJobByPara(beanClass, methodName);
		List<SysScheduleParaEntity> list = scheduleParaMapper.getListByJobId(scheduleJobEntity.getId());
		if( list != null && list.size() > 0 ){
			for( SysScheduleParaEntity paraEntity : list ){
				result.add(paraEntity.getSortno(), paraEntity.getParamValue());
			}
		}
		return result;
	}

	@Override
	public List<String> getJobPara(String springId) throws Exception {
		List<String> result = new ArrayList<String>();

		List<SysScheduleJobEntity> list = scheduleJobMapper.getJobBySpringId(springId);
		if( list != null && list.size() > 0 ){
			int jobId = list.get(0).getId();
			List<SysScheduleParaEntity> paraList = scheduleParaMapper.getListByJobId(jobId);
			if( paraList != null && paraList.size() > 0 ){
				for (int i = 0; i < paraList.size(); i++) {
					//向插入参数结果的中插入根据排序字段的排序的结果
					result.add(paraList.get(i).getSortno() - 1, paraList.get(i).getParamValue());
				}
			}
		}
		return result;
	}

	@Override
	public int getJobId(SysScheduleJobEntity scheduleJobEntity) throws Exception {
		List<SysScheduleJobEntity> list = scheduleJobMapper.findList(scheduleJobEntity);
		return list.get(0).getId();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateJob(SysScheduleJobEntity scheduleJobEntity) {
		scheduleJobEntity.preUpdate();
		scheduleJobMapper.update(scheduleJobEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteJobById(Integer id) {
		scheduleJobMapper.deleteById(id);
	}
	
}
