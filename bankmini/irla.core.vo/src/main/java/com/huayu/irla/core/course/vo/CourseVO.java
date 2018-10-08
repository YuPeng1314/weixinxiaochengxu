package com.huayu.irla.core.course.vo;

import com.huayu.irla.core.base.BaseVO;
/**
 * 课程实体类
 * @author GuGuangting
 *
 */
public class CourseVO extends BaseVO {
	private static final long serialVersionUID = 3655584944652229891L;
	/**
	 * 课程id
	 */
	private Integer id;
	/**
	 * 课程编码
	 */
	private String course_code;
	/**
	 * 目录编码
	 */
	private String catalogCode;
	/**
	 * 目录名称
	 */
	private String catalogName;
	/**
	 * 类别编码
	 */
	private String category_code;
	/**
	 * 类别名称
	 */
	private String categoryName;
	/**
	 * 课程名称
	 */
	private String course_name;
	/**
	 * 课程图片描述
	 */
	private String course_img;
	/**
	 * 用于课程排序
	 */
	private Integer sequence_number;

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCourse_code() {
		return course_code;
	}
	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getCourse_img() {
		return course_img;
	}
	public void setCourse_img(String course_img) {
		this.course_img = course_img;
	}
	public Integer getSequence_number() {
		return sequence_number;
	}
	public void setSequence_number(Integer sequence_number) {
		this.sequence_number = sequence_number;
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
	
}
