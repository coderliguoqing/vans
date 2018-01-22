package cn.com.guoqing.vans.common.tips;

/**
 * 
 * @Description  返回给前台的提示
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
public class ErrorTip extends Tip {

	public ErrorTip(boolean isSuccess, int responseCode, String responseMsg) {
		super();
		this.isSuccess = isSuccess;
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}
    
}
