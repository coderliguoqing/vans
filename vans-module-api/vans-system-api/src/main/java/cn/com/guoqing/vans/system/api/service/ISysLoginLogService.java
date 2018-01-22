package cn.com.guoqing.vans.system.api.service;

import cn.com.guoqing.vans.system.api.entity.SysLoginLogEntity;

import com.github.pagehelper.PageInfo;
import cn.com.guoqing.vans.common.api.Paging;

/**
 * 登录记录
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 15:13:26
 */
public interface ISysLoginLogService {


    //分页查询
    PageInfo<SysLoginLogEntity> findSysLoginLogPage(Paging page, SysLoginLogEntity sysLoginLog);

	SysLoginLogEntity get(Integer id);
	
	int insert(SysLoginLogEntity sysLoginLog);

    int update(SysLoginLogEntity sysLoginLog);
	
	int deleteById(Integer id);
}
