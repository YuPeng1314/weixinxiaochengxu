package com.huayu.irla.privilege.manage.vo;

import java.io.Serializable;
import java.util.Date;

public class SysPersistentLoginsVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SysPersistentLoginsVO(){
		
	}
	
	public SysPersistentLoginsVO(String userCode, String series, String token, Date lastUsed) {
		this.userCode = userCode;
		this.series = series;
		this.token = token;
		this.lastUsed = lastUsed;
	}
	private String userCode;
	
	private String series;
	
	private String token;

	private Date lastUsed;


	/**
	 * getter method
	 * @return the userCode
	 */
	
	public String getUserCode() {
		return userCode;
	}

	/**
	 * setter method
	 * @param userCode the userCode to set
	 */
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getSeries() {
		return series;
	}


	public void setSeries(String series) {
		this.series = series;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Date getLastUsed() {
		return lastUsed;
	}


	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
}
