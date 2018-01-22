package cn.com.guoqing.vans.system.provider.serviceimpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.system.api.entity.DictInfo;
import cn.com.guoqing.vans.system.api.entity.SysDictEntry;
import cn.com.guoqing.vans.system.api.entity.SysDictType;
import cn.com.guoqing.vans.system.api.service.IDictService;
import cn.com.guoqing.vans.system.provider.mapper.SysDictEntryMapper;
import cn.com.guoqing.vans.system.provider.mapper.SysDictTypeMapper;


/**
 * 业务字典相关的实现类
 * 
 * @author Guoqing
 *
 */
@Service
@com.alibaba.dubbo.config.annotation.Service
@Transactional(readOnly = true)
public class DictService implements IDictService {
	
	@Autowired
	private SysDictEntryMapper dictEntryMapper;
	@Autowired
	private SysDictTypeMapper dictTypeMapper;

	@Override
	public PageInfo<SysDictType> findDictTypePage(Paging page, SysDictType dictType) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), true, true);
		List<SysDictType> list = dictTypeMapper.findList(dictType);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional(readOnly = false)
	public SysDictType saveDictType(SysDictType dictType) {
		if( dictType.getId() == null ){
			dictType.preInsert();
			dictTypeMapper.insert(dictType);
		}else{
			dictType.preUpdate();
			dictTypeMapper.update(dictType);
		}
		return dictType;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateDictType(SysDictType dictType) {
		dictType.preUpdate();
		dictTypeMapper.update(dictType);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteDictTypeById(Integer id) {
		dictTypeMapper.deleteById(id);
	}

	@Override
	public PageInfo<SysDictEntry> findDictEntryPage(Paging page, SysDictEntry dictEntry) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), true, true);
		List<SysDictEntry> list = dictEntryMapper.findList(dictEntry);
		return new PageInfo<> (list);
	}

	@Override
	@Transactional(readOnly = false)
	public SysDictEntry saveDictEntry(SysDictEntry dictEntry) {
		if( dictEntry.getId() == null ){
			dictEntry.preInsert();
			dictEntryMapper.insert(dictEntry);
		}else{
			dictEntry.preUpdate();
			dictEntryMapper.update(dictEntry);
		}
		return dictEntry;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateDictEntry(SysDictEntry dictEntry) {
		dictEntry.preUpdate();
		dictEntryMapper.update(dictEntry);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteDictEntryById(Integer id) {
		dictEntryMapper.deleteById(id);
	}

	@Override
	public boolean checkDictTypeId(String dicttypeId) {
		SysDictType dictType = new SysDictType();
		dictType.setDicttypeId(dicttypeId);
		List<SysDictType> list = dictTypeMapper.findList(dictType);
		if( list.size() > 0 ){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkDictId(String dicttypeId, String dictId) {
		SysDictEntry dictEntry = new SysDictEntry();
		dictEntry.setDicttypeId(dicttypeId);
		dictEntry.setDictId(dictId);
		List<SysDictEntry> list = dictEntryMapper.findList(dictEntry);
		if( list.size() > 0 ){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<DictInfo> getDictInfoByTypeId(String dicttypeId, String delFlag) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", dicttypeId);
		params.put("delFlag", delFlag);
		return dictTypeMapper.getDictInfoByTypeId(params);
	}

	@Override
	public List<SysDictEntry> getDictEntryByTypeId(Integer id) {
		return dictTypeMapper.getDictEntryByTypeId(id);
	}
	
	@Override
	public SysDictEntry getDictEntryByTypeIdAndId(String dicttypeId, String dictId ) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dicttypeId", dicttypeId);
		params.put("dictId", dictId);
		return dictEntryMapper.getDictEntryByTypeIdAndId(params);
	}

}
