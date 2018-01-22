package cn.com.guoqing.vans.admin.web.controller.system;

import java.util.ArrayList;
import java.util.List;

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

import cn.com.guoqing.vans.admin.web.common.controller.BaseController;
import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.system.api.entity.DictInfo;
import cn.com.guoqing.vans.system.api.service.IDictService;

/**
 * 
 * @Description  公共处理的请求方法
 *
 * @author Guoqing
 * @Date 2018年1月22日
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {
	
	@Reference(timeout = 60000,version="1.0.0")
	private IDictService dictService;
	@Autowired
	private RedisRepository redisRepository;

	
	/**
	 * post方法获取业务字典信息
	 * @param jsonObject
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/getDict")
	public List<DictInfo> getDictInfo( @RequestBody JSONObject jsonObject){
		String type = jsonObject.getString("dictTypeId");
		if( "".equals(type) || type == null ){
			return new ArrayList<DictInfo>();
		}
		List<DictInfo> dictList = new ArrayList<DictInfo>();
		
		//判断缓存中是否存在业务字典信息
		if( redisRepository.exists( "OM_SYS_DICT_" + type )){
			String json = redisRepository.get("OM_SYS_DICT_" + type);
			dictList = JSONObject.parseArray(JSONObject.parseObject(json).getString("dictList"), DictInfo.class);
		}else{
			dictList = dictService.getDictInfoByTypeId(type, "0");
			if( !dictList.isEmpty() ){
				JSONObject dictJson = new JSONObject();
				dictJson.put("dictList", dictList);
				redisRepository.set("OM_SYS_DICT_" + type, dictJson.toJSONString());
			}
		}
		return dictList;
	}
	
}
