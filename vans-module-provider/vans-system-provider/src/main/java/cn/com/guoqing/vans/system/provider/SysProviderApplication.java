package cn.com.guoqing.vans.system.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
@EnableScheduling
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
public class SysProviderApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysProviderApplication.class);
	
    public static void main( String[] args ){
    	SpringApplication application = new SpringApplication(SysProviderApplication.class);
        application.setRegisterShutdownHook(false);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
		LOGGER.info("System service provider started!");
    }
}
