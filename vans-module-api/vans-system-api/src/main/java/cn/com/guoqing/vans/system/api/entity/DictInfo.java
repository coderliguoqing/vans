package cn.com.guoqing.vans.system.api.entity;

import java.io.Serializable;

/**
 * @author Guoqing
 *
 */
public class DictInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dictId;
	
	private String dictName;

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	
	

}
