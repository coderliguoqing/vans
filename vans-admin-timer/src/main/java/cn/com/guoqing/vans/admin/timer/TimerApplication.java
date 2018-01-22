package cn.com.guoqing.vans.admin.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import cn.com.guoqing.vans.common.util.SpringContextHolder;


/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication
public class TimerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TimerApplication.class);
		LOGGER.info("Timer admin started!");
	}
	
	@Bean
	public SpringContextHolder springContextHolder(){
		return new SpringContextHolder();
	}
}
