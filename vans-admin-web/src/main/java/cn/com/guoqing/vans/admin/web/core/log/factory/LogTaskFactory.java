package cn.com.guoqing.vans.admin.web.core.log.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.com.guoqing.vans.admin.web.constant.state.LogSucceed;
import cn.com.guoqing.vans.admin.web.constant.state.LogType;
import cn.com.guoqing.vans.admin.web.core.log.LogManager;
import cn.com.guoqing.vans.common.util.ToolUtil;
import cn.com.guoqing.vans.system.api.entity.SysLoginLogEntity;
import cn.com.guoqing.vans.system.api.entity.SysOperationLogEntity;
import cn.com.guoqing.vans.system.api.service.ISysLoginLogService;
import cn.com.guoqing.vans.system.api.service.ISysOperationLogService;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 * @author Guoqing
 * @date 2018-01-17
 */
public class LogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    
    @Reference
    private static ISysLoginLogService loginLogService;
    @Reference
    private static ISysOperationLogService operationLogService;

    public static TimerTask loginLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    SysLoginLogEntity loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                    loginLogService.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
            	SysLoginLogEntity loginLog = LogFactory.createLoginLog(
                        LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
                try {
                	loginLogService.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
            	SysLoginLogEntity loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null,ip);
                try {
                	loginLogService.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final Integer userId, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                SysOperationLogEntity operationLog = LogFactory.createOperationLog(
                        LogType.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS);
                try {
                    operationLogService.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Integer userId, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = ToolUtil.getExceptionMsg(exception);
                SysOperationLogEntity operationLog = LogFactory.createOperationLog(
                        LogType.EXCEPTION, userId, "", null, null, msg, LogSucceed.FAIL);
                try {
                	operationLogService.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
