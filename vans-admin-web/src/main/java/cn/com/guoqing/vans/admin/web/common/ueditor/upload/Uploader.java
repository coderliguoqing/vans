package cn.com.guoqing.vans.admin.web.common.ueditor.upload;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.bluemoon.fastdfs.dubbo.BaseFileService;

import cn.com.guoqing.vans.admin.web.common.ueditor.define.State;


public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	private BaseFileService baseFileService;

	public Uploader(HttpServletRequest request, Map<String, Object> conf, BaseFileService baseFileService) {
		this.request = request;
		this.conf = conf;
		this.baseFileService = baseFileService;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName),
					this.conf);
		} else {
			state = BinaryUploader.save(this.request, this.conf, this.baseFileService);
		}

		return state;
	}
}
