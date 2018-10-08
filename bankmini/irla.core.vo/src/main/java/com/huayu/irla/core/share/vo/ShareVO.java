package com.huayu.irla.core.share.vo;

import com.huayu.irla.core.base.BaseVO;

public class ShareVO extends BaseVO {

	private static final long serialVersionUID = 3618402623392179644L;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 资源编码
	 */
	private String resourceCode;
	/**
	 * 课程编码
	 */
	private String courseCode;
	/**
	 * 转发分享数量统计
	 */
	private Integer shareNum;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public Integer getShareNum() {
		return shareNum;
	}
	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}
	
}
