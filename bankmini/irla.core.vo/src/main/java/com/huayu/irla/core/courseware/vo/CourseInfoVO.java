package com.huayu.irla.core.courseware.vo;

import com.huayu.irla.core.base.BaseVO;

/**
 * 
 * @ClassName: CoursewareVO
 * @Description: 课件维护的实体类
 * @author liuwei
 * @date 2017年5月24日 下午5:05:54
 *
 */

public class CourseInfoVO extends BaseVO {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2486171464460570358L;

	/**
	 * 课程ID
	 */
	private String id;

	/**
	 * 课程编码
	 */
	private String courseCode;

	
	/**
	 * 课程名称
	 */
	private String courseName;
	
	
	/**
	 * 类别编码
	 */
	private String categoryCode;
	
	/**
	 * 目录编码
	 */
	private String dirCode;
	
	/**
	 * 课程图片
	 */
	private String courseImg;
	
	/**
	 * 课程下资源数量
	 */
	private Integer sequenceNumber;
	
	/**
	 * 搜索（手机端所用）
	 */
	private String search;

	/**
	 * 浏览记录统计
	 */
	private Integer visitNum;
	
	/**
	 * 收藏记录统计
	 */
	private Integer collectNum;
	
	/**
	 * 0:按照热门度拍讯，1:安装最后更新时间排序
	 */
	private String orderType;
	
	/**
	 * 其他人在看的课程
	 */
	private String otherCourse;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCourseImg() {
		return courseImg;
	}

	public void setCourseImg(String courseImg) {
		this.courseImg = courseImg;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public String getOtherCourse() {
		return otherCourse;
	}

	public void setOtherCourse(String otherCourse) {
		this.otherCourse = otherCourse;
	}

	public String getDirCode() {
		return dirCode;
	}

	public void setDirCode(String dirCode) {
		this.dirCode = dirCode;
	}	
}
