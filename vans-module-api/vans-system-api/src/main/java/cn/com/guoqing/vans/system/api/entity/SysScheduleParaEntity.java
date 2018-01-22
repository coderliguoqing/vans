package cn.com.guoqing.vans.system.api.entity;

import java.util.Date;
import cn.com.guoqing.vans.common.api.DataEntity;



/**
 * 定时器作业的参数表
 * 
 * @author Guoqing
 * @email 514471352@qq.com
 * @date 2018-01-18 17:00:19
 */
public class SysScheduleParaEntity extends DataEntity {
    private static final long serialVersionUID = 1L;

	//关联定时作业的ID
	private Integer jobId;
	//定时器作业参数值
	private String paramValue;
	//定时器作业参数的顺序
	private Integer sortno;
	//备注
	private String remark;

	/**
	 * 设置：关联定时作业的ID
	 */
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取：关联定时作业的ID
	 */
	public Integer getJobId() {
		return jobId;
	}
	/**
	 * 设置：定时器作业参数值
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	/**
	 * 获取：定时器作业参数值
	 */
	public String getParamValue() {
		return paramValue;
	}
	/**
	 * 设置：定时器作业参数的顺序
	 */
	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}
	/**
	 * 获取：定时器作业参数的顺序
	 */
	public Integer getSortno() {
		return sortno;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
