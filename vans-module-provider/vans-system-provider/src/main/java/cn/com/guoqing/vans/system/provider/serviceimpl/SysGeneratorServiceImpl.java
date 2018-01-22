package cn.com.guoqing.vans.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.system.api.entity.SysTable;
import cn.com.guoqing.vans.system.api.service.ISysGeneratorService;
import cn.com.guoqing.vans.system.provider.config.GeneratorConfig;
import cn.com.guoqing.vans.system.provider.mapper.SysGeneratorMapper;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
@com.alibaba.dubbo.config.annotation.Service(owner = "Guoqing",version="1.0.0",timeout=10000)
public class SysGeneratorServiceImpl implements ISysGeneratorService {

	@Autowired
	private SysGeneratorMapper sysGeneratorMapper;

	@Override
	public Map<String, String> queryTable(String tableName) {
		return sysGeneratorMapper.queryTable(tableName);
	}

	@Override
	public List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorMapper.queryColumns(tableName);
	}

	@Override
	public PageInfo<SysTable> findTablePage(Paging page, SysTable table) {
		// 执行分页查询
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());

		List<SysTable> list = sysGeneratorMapper.findList(table);
		return new PageInfo<>(list);
	}


	@Override
	public byte[] generatorCode(String[] tableNames,String allPackage,String author,String email) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GeneratorConfig.generatorCode(table, columns, zip,allPackage,author,email);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
