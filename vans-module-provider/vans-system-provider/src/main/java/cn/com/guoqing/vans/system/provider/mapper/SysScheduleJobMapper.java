package cn.com.guoqing.vans.system.provider.mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysScheduleJobEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spring quartz定时任务记录
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 17:00:12
 */

@Mapper
public interface SysScheduleJobMapper extends CrudDao<SysScheduleJobEntity> {
	
	SysScheduleJobEntity getJobByPara(@Param("beanClass") String beanClass, @Param("methodName") String methodName);
	
	List<SysScheduleJobEntity> getJobBySpringId(String springId);
	
}
