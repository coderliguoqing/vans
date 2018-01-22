package cn.com.guoqing.vans.common.tips;

/**
 * 返回给前台的提示（最终转化为json形式）
 *
 * @author Guoqing
 * @Date 2017年1月11日 下午11:58:00
 */
public abstract class Tip {

	protected boolean isSuccess;
    protected int responseCode;
    protected String responseMsg;
    
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

    
}
