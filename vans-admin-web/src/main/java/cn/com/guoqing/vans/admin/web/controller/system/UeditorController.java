package cn.com.guoqing.vans.admin.web.controller.system;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bluemoon.fastdfs.dubbo.BaseFileService;

import cn.com.guoqing.vans.admin.web.common.ueditor.ActionEnter;
import cn.com.guoqing.vans.admin.web.common.controller.BaseController;


/**
 * 用于处理关于ueditor插件相关的请求
 * @author Guoqing
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/sys/ueditor")
public class UeditorController extends BaseController {
	
	@Reference(version="1.0.0", timeout=60000)
    private static BaseFileService baseFileService;

	@RequestMapping(value = "/exec")
	@ResponseBody
	public String exec(HttpServletRequest request) throws UnsupportedEncodingException{ 
		request.setCharacterEncoding("utf-8");
		String rootPath = request.getRealPath("/");
		return new ActionEnter( request, rootPath, baseFileService ).exec();
	}
	
}
