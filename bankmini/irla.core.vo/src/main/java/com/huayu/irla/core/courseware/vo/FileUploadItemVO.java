package com.huayu.irla.core.courseware.vo;

import com.huayu.irla.core.base.BaseVO;

public class FileUploadItemVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3717297873572586703L;
	

	private Long id;
	
	/**
	 * 资源路径
	 */
	private String resourcePath;
	
	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 资源类型 0为视频，1为文档
	 */
	private String resourceType;
	

	
	/**
	 * 资源md5值
	 */
	private String md5;
	
	/**
	 * 是否有效
	 */
	private boolean enabled;
	
	/**
	 * 状态: 0上传中，1等待转码，2转码等待中，3转码失败，4转码成功
	 */
	private String status;
	
	/**
	 * 关系id
	 */
	private Long relationId;
	
	/**
	 * 上传文件id
	 */
	private Long uploadFileId;
	
	/**
	 * 目录编码
	 */
	private String catalogCode;
	/**
	 * 截屏的位置，单位为秒
	 */
	private String screenshotPos;
	
	/**
	 * 资源所有人
	 */
	private String resourceOwner;
	
	/**
	 * 学时
	 */
	private String learnTime;
	

	/**
	 * 发布开始时间
	 */
	private String startTime;
	
	
	/**
	 * 发布结束时间
	 */
	private String endTime;
	
	/**
	 * 是否显示转码 0为不显示 1为显示
	 */
	private String showTransCode;
	
	/**
	 * 是否公开
	 */
	private String isPublic;
	
	/**
	 * 课程编码
	 */
	private String courseCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getUploadFileId() {
		return uploadFileId;
	}

	public void setUploadFileId(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public String getScreenshotPos() {
		return screenshotPos;
	}

	public void setScreenshotPos(String screenshotPos) {
		this.screenshotPos = screenshotPos;
	}

	public String getResourceOwner() {
		return resourceOwner;
	}

	public void setResourceOwner(String resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	public String getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(String learnTime) {
		this.learnTime = learnTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getShowTransCode() {
		return showTransCode;
	}

	public void setShowTransCode(String showTransCode) {
		this.showTransCode = showTransCode;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


}
