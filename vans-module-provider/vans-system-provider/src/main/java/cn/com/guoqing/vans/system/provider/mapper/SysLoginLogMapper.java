package cn.com.guoqing.vans.system.provider.mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysLoginLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录记录
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 15:13:26
 */

@Mapper
public interface SysLoginLogMapper extends CrudDao<SysLoginLogEntity> {
	
}
