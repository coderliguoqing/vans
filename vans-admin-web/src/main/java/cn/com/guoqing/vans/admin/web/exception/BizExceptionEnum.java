package cn.com.guoqing.vans.admin.web.exception;

import cn.com.guoqing.vans.common.exception.ServiceExceptionEnum;

/**
 * 所有业务异常的枚举
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {

    /**
     * token异常
     */
    TOKEN_EXPIRED(false, 700, "token过期"),
    TOKEN_ERROR(false, 700, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(false, 700, "签名验证失败"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(false, 400, "账号密码错误");

    private BizExceptionEnum(Boolean isSuccess, Integer responseCode, String responseMsg) {
		this.isSuccess = isSuccess;
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}

	private Boolean isSuccess;
    
    private Integer responseCode;

    private String responseMsg;

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

    
}
