/**
 *
 * @Title: CollectDateEnum.java
 * @Package com.huayu.ietl.exhibition.constant
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author ao.chen
 * @date 2016年10月31日 上午11:10:19
 * @version V1.0
 */

package com.huayu.core.enums;

/**
  * 收藏历程时间轴
  * @ClassName: CollectDateEnum
  * @author ao.chen
  * @date 2016年10月31日 上午11:10:19
  *
  */

public enum CollectDateEnum {
	
	JRSC("jrsc", "今日收藏", 1),
	BYSC("busc","本月收藏", 30),
	JZSC("jzsc","较早收藏", 365);

	private String code;
	private String name;
	private Integer days;
	
	//获取名称
	public static String getName(String code){
		for(CollectDateEnum collect: values()){
			if(collect.getCode().equals(code)){
				return collect.getName();
			}
		}
		return null;
	}
	
	//获取天数
	public static Integer getDays(String code){
		for(CollectDateEnum collect: values()){
			if(collect.getCode().equals(code)){
				return collect.getDays();
			}
		}
		return null;
	}
	
	private CollectDateEnum(String code, String name, Integer days) {
		this.code = code;
		this.name = name;
		this.days = days;
	}



	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
	
	
	
	
	
	
}
