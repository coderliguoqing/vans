package cn.com.guoqing.vans.admin.timer.common.config;

import java.io.IOException;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


/**
 * 
 * @Description  定时任务调用器配置类
 *
 * @author Guoqing
 * @Date 2018年1月19日
 */
@Configuration
public class TimerConfig {
	
    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean(){
    	SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
    	factoryBean.setOverwriteExistingJobs(true);
    	return factoryBean;
    }
    
    /*
     * quartz初始化监听器
     * 这个监听器可以监听到工程的启动，在工程停止再启动时可以让已有的定时任务继续进行。
     */
    @Bean
    public QuartzInitializerListener executorListener() {
       return new QuartzInitializerListener();
    }
    
    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
	
}
