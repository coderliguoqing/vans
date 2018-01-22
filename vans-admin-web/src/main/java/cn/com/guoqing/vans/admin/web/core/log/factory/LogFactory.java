package cn.com.guoqing.vans.admin.web.core.log.factory;

import java.util.Date;

import cn.com.guoqing.vans.admin.web.constant.state.LogSucceed;
import cn.com.guoqing.vans.admin.web.constant.state.LogType;
import cn.com.guoqing.vans.system.api.entity.SysLoginLogEntity;
import cn.com.guoqing.vans.system.api.entity.SysOperationLogEntity;

/**
 * 日志对象创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogFactory {

    /**
     * 创建操作日志
     *
     * @author fengshuonan
     * @Date 2017/3/30 18:45
     */
    public static SysOperationLogEntity createOperationLog(LogType logType, Integer userId, String bussinessName, String clazzName, String methodName, String msg, LogSucceed succeed) {
    	SysOperationLogEntity operationLog = new SysOperationLogEntity();
        operationLog.setLogType(logType.getMessage());
        operationLog.setLogName(bussinessName);
        operationLog.setUserId(userId);
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreateDate(new Date());
        operationLog.setUpdateDate(new Date());
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMessage(msg);
        return operationLog;
    }

    /**
     * 创建登录日志
     *
     * @author fengshuonan
     * @Date 2017/3/30 18:46
     */
    public static SysLoginLogEntity createLoginLog(LogType logType, Integer userId, String msg,String ip) {
    	SysLoginLogEntity loginLog = new SysLoginLogEntity();
        loginLog.setLogName(logType.getMessage());
        loginLog.setUserId(userId);
        loginLog.setCreateDate(new Date());
        loginLog.setUpdateDate(new Date());
        loginLog.setSucceed(LogSucceed.SUCCESS.getMessage());
        loginLog.setIp(ip);
        loginLog.setMessage(msg);
        return loginLog;
    }
}
