package cn.com.guoqing.vans.system.provider.mapper;


import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysDictEntry;


@Mapper
public interface SysDictEntryMapper extends CrudDao<SysDictEntry> {
	
	/**
	 * 根据类型和Id查询
	 * @param params
	 * @return
	 */
	SysDictEntry getDictEntryByTypeIdAndId(HashMap<String, Object> params);
}