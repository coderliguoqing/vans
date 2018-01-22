package cn.com.guoqing.vans.system.provider.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.guoqing.vans.common.api.Paging;

import java.util.List;

import cn.com.guoqing.vans.system.provider.mapper.SysOperationLogMapper;
import cn.com.guoqing.vans.system.api.entity.SysOperationLogEntity;
import cn.com.guoqing.vans.system.api.service.ISysOperationLogService;



@Service
@com.alibaba.dubbo.config.annotation.Service
@Transactional(readOnly = true)
public class SysOperationLogServiceImpl implements ISysOperationLogService {
	@Autowired
	private SysOperationLogMapper sysOperationLogMapper;
	
	@Override
	public SysOperationLogEntity get(Long id){
		return sysOperationLogMapper.get(Integer.parseInt(String.valueOf(id)));
	}

    @Override
    public PageInfo<SysOperationLogEntity> findSysOperationLogPage(Paging page, SysOperationLogEntity sysOperationLog){
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());

        List<SysOperationLogEntity> list = sysOperationLogMapper.findList(sysOperationLog);
        return new PageInfo<>(list);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int insert(SysOperationLogEntity sysOperationLog){
        return sysOperationLogMapper.insert(sysOperationLog);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int update(SysOperationLogEntity sysOperationLog){
		 return sysOperationLogMapper.update(sysOperationLog);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteById(Long id){
        return sysOperationLogMapper.deleteById(Integer.parseInt(String.valueOf(id)));
	}
	
}
