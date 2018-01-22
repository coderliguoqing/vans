package cn.com.guoqing.vans.system.provider.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.DictInfo;
import cn.com.guoqing.vans.system.api.entity.SysDictEntry;
import cn.com.guoqing.vans.system.api.entity.SysDictType;


@Mapper
public interface SysDictTypeMapper extends CrudDao<SysDictType> {
	
	/**
	 * 根据字典id查询业务字典信息
	 * @param dicttypeId, del_flag
	 * @return
	 */
	List<DictInfo> getDictInfoByTypeId(HashMap<String, Object> params);
	
	/**
	 * 根据字典信息获取entry列表
	 * @param dicttypeId
	 * @return
	 */
	List<SysDictEntry> getDictEntryByTypeId( Integer id );
	
}