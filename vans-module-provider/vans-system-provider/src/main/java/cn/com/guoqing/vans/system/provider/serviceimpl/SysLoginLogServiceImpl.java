package cn.com.guoqing.vans.system.provider.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.guoqing.vans.common.api.Paging;

import java.util.List;

import cn.com.guoqing.vans.system.provider.mapper.SysLoginLogMapper;
import cn.com.guoqing.vans.system.api.entity.SysLoginLogEntity;
import cn.com.guoqing.vans.system.api.service.ISysLoginLogService;



@Service
@com.alibaba.dubbo.config.annotation.Service
@Transactional(readOnly = true)
public class SysLoginLogServiceImpl implements ISysLoginLogService {
	@Autowired
	private SysLoginLogMapper sysLoginLogMapper;
	
	@Override
	public SysLoginLogEntity get(Integer id){
		return sysLoginLogMapper.get(id);
	}

    @Override
    public PageInfo<SysLoginLogEntity> findSysLoginLogPage(Paging page, SysLoginLogEntity sysLoginLog){
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());

        List<SysLoginLogEntity> list = sysLoginLogMapper.findList(sysLoginLog);
        return new PageInfo<>(list);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int insert(SysLoginLogEntity sysLoginLog){
        return sysLoginLogMapper.insert(sysLoginLog);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int update(SysLoginLogEntity sysLoginLog){
		 return sysLoginLogMapper.update(sysLoginLog);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteById(Integer id){
        return sysLoginLogMapper.deleteById(id);
	}
	
}
