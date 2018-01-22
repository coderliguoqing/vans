package cn.com.guoqing.vans.system.provider.mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysOperationLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 14:39:56
 */

@Mapper
public interface SysOperationLogMapper extends CrudDao<SysOperationLogEntity> {
	
}
