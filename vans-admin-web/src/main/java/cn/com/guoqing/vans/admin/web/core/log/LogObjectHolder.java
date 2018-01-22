package cn.com.guoqing.vans.admin.web.core.log;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import cn.com.guoqing.vans.common.util.SpringContextHolder;

import java.io.Serializable;

/**
 * 
 * @Description  被修改的bean临时存放处
 *
 * @author Guoqing
 * @Date 2018年1月17日
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
public class LogObjectHolder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Object object = null;

    public void set(Object obj) {
        this.object = obj;
    }

    public Object get() {
        return object;
    }

    public static LogObjectHolder me(){
        LogObjectHolder bean = SpringContextHolder.getBean(LogObjectHolder.class);
        return bean;
    }
}
