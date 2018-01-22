package cn.com.guoqing.vans.admin.web.core.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.com.guoqing.vans.admin.web.core.log.LogManager;
import cn.com.guoqing.vans.admin.web.core.log.factory.LogTaskFactory;
import cn.com.guoqing.vans.admin.web.security.model.AuthUser;
import cn.com.guoqing.vans.common.exception.VansException;
import cn.com.guoqing.vans.common.tips.ErrorTip;
import cn.com.guoqing.vans.common.util.WebUtils;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author Guoqing
 * @date 2018-01-17
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     *
     * @author fengshuonan
     */
    @ExceptionHandler(VansException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(VansException e) {
    	AuthUser user = WebUtils.getCurrentUser();
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(user.getId(), e));
        log.error("业务异常:", e);
        return new ErrorTip(false, e.getResponseCode(), e.getResponseMsg());
    }

}
