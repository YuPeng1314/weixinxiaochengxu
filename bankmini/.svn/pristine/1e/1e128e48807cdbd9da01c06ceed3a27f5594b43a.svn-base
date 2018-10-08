/**
 *
 * @Title: PersonalEnum.java
 * @Package com.huayu.ietl.exhibition.constant
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author ao.chen
 * @date 2016年10月28日 上午11:30:36
 * @version V1.0
 */

package com.huayu.core.enums;

/**
 * 个人中心左侧菜单
 * 
 * @ClassName: PersonalEnum
 * @author ao.chen
 * @date 2016年10月28日 上午11:30:36
 *
 */

public enum PersonalEnum {

	XXLC("xxlc", "学习历程"), CSZX("cszx", "测试中心"), HD("hd", "活动"), CJCX("cjcx", "成绩查询"), WDSC("wdsc", "我的收藏"),
	JBXX("jbxx","基本信息"),GKJL("gkjl","观看记录");

	private String code;
	private String name;

	public static String getName(String code) {

		for (PersonalEnum personal : values()) {
			if (personal.getCode().equals(code)) {
				return personal.getName();
			}
		}
		return null;
	}

	private PersonalEnum(String code, String name) {
		this.code = code;
		this.name = name;
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
}
