package cn.com.guoqing.vans.admin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author Guoqing
 *
 */
@RestController
@SpringBootApplication
public class WebApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class);
		LOGGER.info("Web admin started!");
	}
	
	/**
	 * 做允许全局跨域的处理
	 * @return
	 */
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
