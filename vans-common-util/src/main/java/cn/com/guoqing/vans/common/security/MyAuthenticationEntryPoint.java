package cn.com.guoqing.vans.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import cn.com.guoqing.vans.common.exception.VansExceptionEnum;
import cn.com.guoqing.vans.common.tips.ErrorTip;
import cn.com.guoqing.vans.common.util.RenderUtil;

/**
 * 
 * @Description  
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		RenderUtil.renderJson(response, new ErrorTip(VansExceptionEnum.TOKEN_NOT_FUND.getIsSuccess(),
    			VansExceptionEnum.TOKEN_NOT_FUND.getResponseCode(),
    			VansExceptionEnum.TOKEN_NOT_FUND.getResponseMsg()));
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未授权");
	}
	
	

}
