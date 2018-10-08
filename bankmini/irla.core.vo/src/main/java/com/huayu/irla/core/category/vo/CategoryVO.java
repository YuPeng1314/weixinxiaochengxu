package com.huayu.irla.core.category.vo;

import com.huayu.irla.core.base.BaseVO;
/**
 * 类别实体类
 * @author GuGuangting
 *
 */
public class CategoryVO extends BaseVO {
	private static final long serialVersionUID = 1447993663295243557L;
	/**
	 * 类别id
	 */
	private Integer id;
	/**
	 * 类别所属目录名称
	 */
	private String catalogName;
	/**
	 * 类别编码
	 */
	private String category_code;
	
	/**
	 * 类别名称
	 */
	private String category_name;
	/**
	 * 类别图标
	 */
	private String category_img;
	/**
	 * 父级类别编码（目录）
	 */
	private String parent_category_code;
	/**
	 * 1：目录，2：类别
	 */
	private Integer category_level;
	/**
	 * 用于类别排序
	 */
	private Integer sequence_number;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getParent_category_code() {
		return parent_category_code;
	}
	public void setParent_category_code(String parent_category_code) {
		this.parent_category_code = parent_category_code;
	}
	public Integer getCategory_level() {
		return category_level;
	}
	public void setCategory_level(Integer category_level) {
		this.category_level = category_level;
	}
	public Integer getSequence_number() {
		return sequence_number;
	}
	public void setSequence_number(Integer sequence_number) {
		this.sequence_number = sequence_number;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getCategory_img() {
		return category_img;
	}
	public void setCategory_img(String category_img) {
		this.category_img = category_img;
	}
	
}
