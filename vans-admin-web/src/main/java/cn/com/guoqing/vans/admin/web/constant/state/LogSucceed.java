package cn.com.guoqing.vans.admin.web.constant.state;

/**
 * 业务是否成功的日志记录
 *
 * @author Guoqing
 * @Date 2018-01-18
 */
public enum LogSucceed {

    SUCCESS("成功"),
    FAIL("失败");

    String message;

    LogSucceed(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
