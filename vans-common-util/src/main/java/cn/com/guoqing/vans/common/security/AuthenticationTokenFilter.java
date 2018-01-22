package cn.com.guoqing.vans.common.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import cn.com.guoqing.vans.common.exception.VansExceptionEnum;
import cn.com.guoqing.vans.common.tips.ErrorTip;
import cn.com.guoqing.vans.common.util.RenderUtil;
import cn.com.guoqing.vans.common.util.StringHelper;

/**
 * 
 * @Description  token校验过滤器
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
public class AuthenticationTokenFilter extends GenericFilterBean {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String authHeader = httpRequest.getHeader("Authorization");
		
		if (authHeader == null || !authHeader.startsWith(JwtTokenUtil.TOKEN_TYPE_BEARER)) {
//			RenderUtil.renderJson(httpResponse, new ErrorTip(VansExceptionEnum.TOKEN_NOT_FUND.getIsSuccess(),
//        			VansExceptionEnum.TOKEN_NOT_FUND.getResponseCode(),
//        			VansExceptionEnum.TOKEN_NOT_FUND.getResponseMsg()));
            chain.doFilter(request, response);
            return;
        }
		
		final String authToken = StringHelper.substring(authHeader, 7);
        String username = StringHelper.isNotBlank(authToken) ? jwtTokenUtil.getUsernameFromToken(authToken) : null;

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtTokenUtil.isTokenExpired(authToken)) {
            UserDetails userDetails = jwtTokenUtil.getUserDetails(authToken);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
	}

}
