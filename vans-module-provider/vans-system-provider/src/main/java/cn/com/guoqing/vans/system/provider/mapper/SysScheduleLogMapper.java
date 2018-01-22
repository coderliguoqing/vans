package cn.com.guoqing.vans.system.provider.mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysScheduleLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时器运行时日志表
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 17:00:02
 */

@Mapper
public interface SysScheduleLogMapper extends CrudDao<SysScheduleLogEntity> {
	
}
