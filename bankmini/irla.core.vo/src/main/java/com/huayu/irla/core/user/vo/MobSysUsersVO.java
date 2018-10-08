package com.huayu.irla.core.user.vo;

import java.io.Serializable;

public class MobSysUsersVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 73996054475422503L;


	/**
	 * 用户名称
	 */
	private String userCode;


	/**
	 * 性别
	 */
	private String sex;
	
	private String sessionKey;
	
	private String unionid;

	
	 /**
     * 用户所在省/直辖市
     */
    private String province;
    
    /**
     * 用户所在市/区
     */
    private String town;

	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Override
	public String toString() {
		return this.userCode;
	}

	@Override
	public int hashCode() {
		return this.userCode.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
}
