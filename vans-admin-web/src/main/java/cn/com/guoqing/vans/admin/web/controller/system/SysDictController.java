package cn.com.guoqing.vans.admin.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.com.guoqing.vans.admin.web.common.controller.BaseController;
import cn.com.guoqing.vans.admin.web.response.ResponseBean;
import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.system.api.entity.DictInfo;
import cn.com.guoqing.vans.system.api.entity.SysDictEntry;
import cn.com.guoqing.vans.system.api.entity.SysDictType;
import cn.com.guoqing.vans.system.api.service.IDictService;

/**
 * 业务字典相关控制器类
 * 
 * @author Guoqing
 * Date:2017-06-09
 */
@Validated
@RestController
@CrossOrigin
@RequestMapping("/sys/dict")
public class SysDictController extends BaseController {
	
	@Reference
	private IDictService dictService;
	@Autowired
	private RedisRepository redisRepository;
	
	
	/**
	 * This get dictType page info.
	 * @param dictType
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasAuthority('sys:dict:view')")
	@PostMapping(value="/type/list")
	public PageInfo<SysDictType> dictTypeList( @RequestBody JSONObject jsonObject ){
		SysDictType dictType = JSONObject.parseObject(jsonObject.getJSONObject("dictType").toJSONString(), SysDictType.class);
		Paging page = (Paging) JSONObject.parseObject(jsonObject.getJSONObject("page").toJSONString(), Paging.class);
		return dictService.findDictTypePage(page, dictType);
	}
	
	/**
	 * save dict type info
	 * @param dictType
	 * @return
	 */
	@PreAuthorize("hasAuthority('sys:dict:edit')")
	@PostMapping(value = "/type/save")
	public ResponseBean saveDictType( @RequestBody JSONObject jsonObject ){
		SysDictType dictType = JSONObject.parseObject(jsonObject.getJSONObject("dictType").toJSONString(), SysDictType.class);
		//如果是在新增业务字典的情况下,校验dicttypeId是否存在
		if( dictType.getId() == null ){
			if( dictService.checkDictTypeId(dictType.getDicttypeId()) ){
				return new ResponseBean(false, 1001, "类型代码已经存在", null);
			}
		}
		dictService.saveDictType(dictType);
		return new ResponseBean(true, 0, "请求成功", null);
	}
	
	/**
	 * delete dict type info.
	 * @param id
	 */
	@PreAuthorize("hasAuthority('sys:dict:edit')")
	@PostMapping(value = "/type/delete")
	public void deleteDictType( @RequestBody JSONObject jsonObject ){
		Integer id = jsonObject.getInteger("id");
		List<SysDictEntry> entryList = dictService.getDictEntryByTypeId(id);
		
		dictService.deleteDictTypeById(id);
		
		//同时删除entry里面的字典子项信息
		if( !entryList.isEmpty() ){
			for( SysDictEntry entry:entryList ){
				dictService.deleteDictEntryById(entry.getId());
			}
		}
	}
	
	/**
	 * get dictEntry page info.
	 * @param dictEntry
	 * @param page
	 * @return
	 */
	@PreAuthorize("hasAuthority('sys:dict:view')")
	@PostMapping(value="/entry/list")
	public PageInfo<SysDictEntry> dictEntryList( @RequestBody JSONObject jsonObject ){
		SysDictEntry dictEntry = JSONObject.parseObject(jsonObject.getJSONObject("dictEntry").toJSONString(), SysDictEntry.class);
		Paging page = (Paging) JSONObject.parseObject(jsonObject.getJSONObject("page").toJSONString(), Paging.class);
		return dictService.findDictEntryPage(page, dictEntry);
	}
	
	/**
	 * save dict entry info.
	 * @param dictEntry
	 * @return
	 */
	@PreAuthorize("hasAuthority('sys:dict:edit')")
	@PostMapping(value = "/entry/save")
	public ResponseBean saveDictEntry(  @RequestBody JSONObject jsonObject ){
		SysDictEntry dictEntry = JSONObject.parseObject(jsonObject.getJSONObject("dictEntry").toJSONString(), SysDictEntry.class);
		//如果是在新增字典类型情况下，校验当前的dicttypeId是否已经存在
		if( dictEntry.getId() == null ){
			if( dictService.checkDictId(dictEntry.getDicttypeId(), dictEntry.getDictId()) ){
				return new ResponseBean(false, 1001, "字典项代码已经存在", null);
			}
		}
		dictService.saveDictEntry(dictEntry);
		return new ResponseBean(true, 0, "请求成功", null);
	}
	
	/**
	 * delete dict type info.
	 * @param id
	 */
	@PreAuthorize("hasAuthority('sys:dict:edit')")
	@PostMapping(value = "/entry/delete")
	public void deleteDictEntry( @RequestBody JSONObject jsonObject ){
		Integer id = jsonObject.getInteger("id");
		dictService.deleteDictEntryById(id);
	}
	
	/**
	 * 刷新业务字典的缓存信息
	 * @param jsonObject
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/type/reloadCache")
	public ResponseBean reloadDictCatch( @RequestBody JSONObject jsonObject ){
		Map<String, Object> result = new HashMap<>();
		String type = jsonObject.getString("dictTypeId");
		
		if( type == null || "".equals(type) ){
			result.put("code", false);
			result.put("message", "字典项不存在");
			return new ResponseBean(false, 1001, "字典不存在", null);
		}else{
			//如果缓存里面存在该数据
			//先删除缓存里面的业务字典
			if( redisRepository.exists( "OM_SYS_DICT_" + type )){
				redisRepository.del( "OM_SYS_DICT_" + type );
				//将数据刷入缓存
				List<DictInfo> dictList = dictService.getDictInfoByTypeId(type, "0");
				if( !dictList.isEmpty() ){
					JSONObject dictJson = new JSONObject();
					dictJson.put("dictList", dictList);
					redisRepository.set("OM_SYS_DICT_" + type, dictJson.toJSONString());
				}
			}
			return new ResponseBean(true, 0, "刷新缓存成功", null);
		}
	}
	
}
