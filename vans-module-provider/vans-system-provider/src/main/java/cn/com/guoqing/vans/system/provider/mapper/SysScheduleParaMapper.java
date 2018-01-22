package cn.com.guoqing.vans.system.provider.mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysScheduleParaEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 定时器作业的参数表
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 17:00:19
 */

@Mapper
public interface SysScheduleParaMapper extends CrudDao<SysScheduleParaEntity> {
	
	List<SysScheduleParaEntity> getListByJobId( int jobId );
	
	void deleteByJobId(int jobId);
}
