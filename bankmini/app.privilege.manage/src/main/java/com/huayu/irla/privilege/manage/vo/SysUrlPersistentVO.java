package com.huayu.irla.privilege.manage.vo;

import java.io.Serializable;

public class SysUrlPersistentVO implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	/**
	 * getter method
	 * @return the batchID
	 */
	
	public long getBatchID() {
		return batchID;
	}

	/**
	 * setter method
	 * @param batchID the batchID to set
	 */
	
	public void setBatchID(long batchID) {
		this.batchID = batchID;
	}

	private long batchID;

	private int privilegeMode;
	
	private String privilegeName;
	
	private String url;
	
	private String resourceName;
	
	private long resourceID;
	
	private long authorityID;

	/**
	 * getter method
	 * @return the resourceID
	 */
	
	public long getResourceID() {
		return resourceID;
	}

	/**
	 * setter method
	 * @param resourceID the resourceID to set
	 */
	
	public void setResourceID(long resourceID) {
		this.resourceID = resourceID;
	}

	

	/**
	 * getter method
	 * @return the authorityID
	 */
	
	public long getAuthorityID() {
		return authorityID;
	}

	/**
	 * setter method
	 * @param authorityID the authorityID to set
	 */
	
	public void setAuthorityID(long authorityID) {
		this.authorityID = authorityID;
	}

	/**
	 * getter method
	 * @return the resourceName
	 */
	
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * setter method
	 * @param resourceName the resourceName to set
	 */
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * getter method
	 * @return the privilegeMode
	 */
	
	public int getPrivilegeMode() {
		return privilegeMode;
	}

	/**
	 * setter method
	 * @param privilegeMode the privilegeMode to set
	 */
	
	public void setPrivilegeMode(int privilegeMode) {
		this.privilegeMode = privilegeMode;
	}

	/**
	 * getter method
	 * @return the privilegeName
	 */
	
	public String getPrivilegeName() {
		return privilegeName;
	}

	/**
	 * setter method
	 * @param privilegeName the privilegeName to set
	 */
	
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	/**
	 * getter method
	 * @return the url
	 */
	
	public String getUrl() {
		return url;
	}

	/**
	 * setter method
	 * @param url the url to set
	 */
	
	public void setUrl(String url) {
		this.url = url;
	}
}
