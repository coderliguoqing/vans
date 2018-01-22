package cn.com.guoqing.vans.admin.timer.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsUtils;

import cn.com.guoqing.vans.common.config.AbstractWebSecurityConfig;


/**
 * spring-security配置
 *
 * @author Guoqing
 */
@Configuration
public class WebSecurityConfig extends AbstractWebSecurityConfig {

    @Override
    protected void configure(HttpSecurity security) throws Exception {
    	security
         .authorizeRequests()
         .antMatchers("/auth/token", "/kaptcha/generate",
                 "/sys/ueditor/**",
                 "/sys/generator/**").permitAll()
         .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();//设置可跨域请求时的放行
    	security.headers().frameOptions().disable();
        super.configure(security);
    }
}
