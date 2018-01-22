package cn.com.guoqing.vans.admin.web.security.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import cn.com.guoqing.vans.common.security.JwtTokenUtil;

@Component
@ConfigurationProperties("jwt")
public class TokenUtil extends JwtTokenUtil {

}
