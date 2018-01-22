package cn.com.guoqing.vans.common.exception;

/**
 * 封装guns的异常
 *
 * @author fengshuonan
 * @Date 2017/12/28 下午10:32
 */
public class VansException extends RuntimeException {
	
	private Boolean isSuccess;

    private Integer responseCode;

    private String responseMsg;

    public VansException(ServiceExceptionEnum serviceExceptionEnum) {
        this.isSuccess = serviceExceptionEnum.getIsSuccess();
    	this.responseCode = serviceExceptionEnum.getResponseCode();
        this.responseMsg = serviceExceptionEnum.getResponseMsg();
    }

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
