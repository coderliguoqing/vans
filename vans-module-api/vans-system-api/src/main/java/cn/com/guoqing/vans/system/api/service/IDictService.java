package cn.com.guoqing.vans.system.api.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.system.api.entity.DictInfo;
import cn.com.guoqing.vans.system.api.entity.SysDictEntry;
import cn.com.guoqing.vans.system.api.entity.SysDictType;


/**
 * 业务字典相关的接口信息接口类
 * 
 * DATE:2017-06-09
 * @author Guoqing
 *
 */
public interface IDictService {
	
	/**
	 * 查询业务字典列表
	 * @param page	分页信息
	 * @param dictType	业务字典
	 * @return	分页数据 page info 
	 */
	PageInfo<SysDictType> findDictTypePage(Paging page, SysDictType dictType);
	
	/**
	 * 保存业务字典信息
	 * @param dictType 业务字典
	 * @return	dictType
	 */
	SysDictType saveDictType(SysDictType dictType);
	
	/**
	 * 更新业务字典信息
	 * @param dictType
	 */
	void updateDictType(SysDictType dictType);
	
	/**
	 * 删除业务字典信息
	 * @param id
	 */
	void deleteDictTypeById(Integer id);
	
	/**
	 * 查询业务字典子项列表
	 * @param page
	 * @param dictEntry
	 * @return
	 */
	PageInfo<SysDictEntry> findDictEntryPage( Paging page, SysDictEntry dictEntry );
	
	/**
	 * 保存字典子项信息
	 * @param dictEntry
	 * @return
	 */
	SysDictEntry saveDictEntry(SysDictEntry dictEntry);
	
	/**
	 * 更新字典子项信息
	 * @param dictEntry
	 */
	void updateDictEntry(SysDictEntry dictEntry);
	
	/**
	 * 根据主键删除字典子项信息
	 * @param id
	 */
	void deleteDictEntryById(Integer id);
	
	/**
	 * 检查dicttypeId是否存在，存在返回true，不存在返回false
	 * @param dicttypeId
	 * @return
	 */
	boolean checkDictTypeId( String dicttypeId );
	
	/**
	 * 检查dictId是否存在，存在返回true,不存在返回false
	 * @param dicttypeId
	 * @param dictId
	 * @return
	 */
	boolean checkDictId( String dicttypeId, String dictId );
	
	/**
	 * 根据业务字典编码获取业务字典的全部信息
	 * @param dicttypeId	字典编码
	 * @param delFlag		删除标记
	 * @return
	 */
	List<DictInfo> getDictInfoByTypeId( String dicttypeId, String delFlag );
	
	/**
	 * 根据字典项ID获取entry的列表
	 * @param dicttypeId
	 * @return
	 */
	List<SysDictEntry> getDictEntryByTypeId( Integer id );
	
	/**
	 * 根据类型和Id查询entry
	 * @param dicttypeId
	 * @param dictId
	 * @param delFlag
	 * @return
	 */
	SysDictEntry getDictEntryByTypeIdAndId(String dicttypeId, String dictId);
}
