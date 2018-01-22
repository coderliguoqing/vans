package cn.com.guoqing.vans.admin.web.controller.system;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.com.guoqing.vans.admin.web.common.controller.BaseController;
import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.common.xss.XssHttpServletRequestWrapper;
import cn.com.guoqing.vans.system.api.entity.SysTable;
import cn.com.guoqing.vans.system.api.service.ISysGeneratorService;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @Description  代码生成器
 *
 * @author Guoqing
 * @Date 2018年1月15日
 */
@Validated
@Controller
@CrossOrigin
@RequestMapping("/sys/generator")
public class SysGeneratorController extends BaseController {

	@Reference(timeout=60000,version="1.0.0")
	private ISysGeneratorService sysGeneratorService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@PostMapping(value="/list")
	public PageInfo<SysTable> list(@RequestBody JSONObject jsonObject) {

		SysTable table = JSONObject.parseObject(jsonObject.getJSONObject("table").toJSONString(), SysTable.class);
		Paging page = (Paging) JSONObject.parseObject(jsonObject.getJSONObject("page").toJSONString(), Paging.class);

		PageInfo<SysTable> list=sysGeneratorService.findTablePage(page,table);
		if(list.getList().size() > 0){
			System.out.println(list.getList().get(0));
		}
		return list;
	}
	
	/**
	 * 生成代码
	 */
	@GetMapping(value="/code")
//	@PreAuthorize("hasAuthority('generator:generator:code')")
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] tableNames = new String[]{};
		//获取表名，不进行xss过滤
		HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
		String allPackage=orgRequest.getParameter("package");
		String author=orgRequest.getParameter("author");
		String email=orgRequest.getParameter("email");
		System.out.println(allPackage+"==="+author+"===="+email);

		String tables = orgRequest.getParameter("tables");
		tableNames =  tables.split(",");
		System.out.println("------------"+tables+"-------------");

		byte[] data = sysGeneratorService.generatorCode(tableNames,allPackage,author,email);

		response.reset();
		response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Content-Disposition", "attachment;filename=\"lee-vans.zip\"");
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());
	}
}
