package com.huayu.irla.core.phiz.vo;

import com.huayu.irla.core.base.BaseVO;
/**
 * 表情包实体类
 * @author ggt
 * @date 2018-07-24
 */
public class PhizVO extends BaseVO{

	private static final long serialVersionUID = -3106459569720856620L;
	/**
	 * 表情id
	 */
	private Integer phizId;
	/**
	 * 表情图片名称
	 */
	private String imgName;
	/**
	 * 表情图片地址
	 */
	private String imgAddress;
	/**
	 * 节日编码
	 */
	private String holidayCode;
	/**
	 * 节日名称
	 */
	private String holidayName;
	/**
	 * 是否推荐
	 */
	private String isRecommend;
	/**
	 * 主题编码
	 */
	private String themeCode;
	/**
	 * 主题名称
	 */
	private String themeName;
	
	public String getThemeCode() {
		return themeCode;
	}
	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getPhizId() {
		return phizId;
	}
	public void setPhizId(Integer phizId) {
		this.phizId = phizId;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public String getHolidayCode() {
		return holidayCode;
	}
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	
	
	
}
