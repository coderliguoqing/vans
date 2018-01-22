package cn.com.guoqing.vans.admin.web.common.config;

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
    
//    /**
//     * 验证码生成相关
//     */
//    @Bean
//    public DefaultKaptcha kaptcha() {
//        Properties properties = new Properties();
//        //图片边框
//        properties.put("kaptcha.border", "no");
//        //边框颜色
//        properties.put("kaptcha.border.color", "105,179,90");
//        //字体颜色
//        properties.put("kaptcha.textproducer.font.color", "blue");
//        //图片宽度
//        properties.put("kaptcha.image.width", "125");
//        //图片高度
//        properties.put("kaptcha.image.height", "40");
//        //字体大小
//        properties.put("kaptcha.textproducer.font.size", "35");
//        //session中存放验证码的key
//        properties.put("kaptcha.session.key", "code");
//        //验证码长度
//        properties.put("kaptcha.textproducer.char.length", "4");
//        //图片样式
//        properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
//        //字体
//        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
//        Config config = new Config(properties);
//        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
//        defaultKaptcha.setConfig(config);
//        return defaultKaptcha;
//    }
}
