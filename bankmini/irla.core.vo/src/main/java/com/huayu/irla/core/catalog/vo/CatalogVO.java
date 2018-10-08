package com.huayu.irla.core.catalog.vo;

import java.util.List;

import com.huayu.irla.core.base.BaseVO;

/**
 * 
 * @ClassName: CatalogVO
 * @Description: 目录层级实体类
 * @author liuwei
 * @date 2017年9月4日 下午3:50:47 Copyright: Copyright (c) 2017 Company:华煜网络教育有限公司
 */

public class CatalogVO extends BaseVO {

	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = -3028548037774105001L;

	/**
	 * 目录id
	 */
	private String id;

	/**
	 * 目录编码
	 */
	private String catalogCode;

	/**
	 * 目录名称
	 */
	private String catalogName;

	/**
	 * 上级目录编码
	 */
	private String parentCatalogCode;

	/**
	 * 上级目录名称
	 */
	private String parentCatalogName;

	/**
	 * 最高目录编码
	 */
	private String topCatalogCode;

	/**
	 * 目录图片
	 */
	private String catalogImg;

	/**
	 * 目录类型（0：私有，1：公开）
	 */
	private String catalogType;

	/**
	 * 目录层级（0：类型，1：专业，2：课程，3：章节）
	 */
	private String catalogLevel;

	/**
	 * 用户code
	 */
	private String userCode;

	private String percent;

	/**
	 * 搜索（手机端所用）
	 */
	private String search;

	/**
	 * 目录排序类型
	 */
	private String catalogNumType;

	/**
	 * 排列序号
	 */
	private Integer sequenceNumber;

	private Integer freeFlag = 0;

	// 免费课程名称，两个是方便Mybaties中能好做判断
	private String freeNameFalse;

	private String freeNameTrue;
	
	/**
	 * 是否叶节点，0:非叶节点 1为叶节点
	 * @return
	 */
	private String leafNode;
	
	/**
	 * 目标编码
	 */
	private List<String> catalogCodes;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getParentCatalogCode() {
		return parentCatalogCode;
	}

	public void setParentCatalogCode(String parentCatalogCode) {
		this.parentCatalogCode = parentCatalogCode;
	}

	public String getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}

	public String getCatalogLevel() {
		return catalogLevel;
	}

	public void setCatalogLevel(String catalogLevel) {
		this.catalogLevel = catalogLevel;
	}

	public String getCatalogImg() {
		return catalogImg;
	}

	public void setCatalogImg(String catalogImg) {
		this.catalogImg = catalogImg;
	}

	public String getTopCatalogCode() {
		return topCatalogCode;
	}

	public void setTopCatalogCode(String topCatalogCode) {
		this.topCatalogCode = topCatalogCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentCatalogName() {
		return parentCatalogName;
	}

	public void setParentCatalogName(String parentCatalogName) {
		this.parentCatalogName = parentCatalogName;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getCatalogNumType() {
		return catalogNumType;
	}

	public void setCatalogNumType(String catalogNumType) {
		this.catalogNumType = catalogNumType;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Integer getFreeFlag() {
		return freeFlag;
	}

	public void setFreeFlag(Integer freeFlag) {
		this.freeFlag = freeFlag;
	}

	public String getFreeNameFalse() {
		return freeNameFalse;
	}

	public void setFreeNameFalse(String freeNameFalse) {
		this.freeNameFalse = freeNameFalse;
	}

	public String getFreeNameTrue() {
		return freeNameTrue;
	}

	public void setFreeNameTrue(String freeNameTrue) {
		this.freeNameTrue = freeNameTrue;
	}

	public String getLeafNode() {
		return leafNode;
	}

	public void setLeafNode(String leafNode) {
		this.leafNode = leafNode;
	}

	public List<String> getCatalogCodes() {
		return catalogCodes;
	}

	public void setCatalogCodes(List<String> catalogCodes) {
		this.catalogCodes = catalogCodes;
	}

}
