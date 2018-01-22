package cn.com.guoqing.vans.system.api.service;

import cn.com.guoqing.vans.system.api.entity.SysOperationLogEntity;

import com.github.pagehelper.PageInfo;
import cn.com.guoqing.vans.common.api.Paging;

/**
 * 
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 14:39:56
 */
public interface ISysOperationLogService {


    //分页查询
    PageInfo<SysOperationLogEntity> findSysOperationLogPage(Paging page, SysOperationLogEntity sysOperationLog);

	SysOperationLogEntity get(Long id);
	
	int insert(SysOperationLogEntity sysOperationLog);

    int update(SysOperationLogEntity sysOperationLog);
	
	int deleteById(Long id);
}
