package com.huayu.irla.privilege.manage.vo;

import java.io.Serializable;
import java.util.Date;

public class SysLoginoutVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 序列id
	 */
	private int id;

	/**
	 * 用户编码
	 */
	private String userCode;

	/**
	 * 用户ip
	 */
	private String userIP;

	/**
	 * 类型
	 */
	private int type;

	/**
	 * 触发时间
	 */
	private Date actionDate;

	/**
	 * 用户区域
	 */
	private String eara;

	/**
	 * 每一页数据量
	 */
	private Integer limit;
	/**
	 * 数据偏移量
	 */
	private Integer offset;

	private String terminal;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getEara() {
		return eara;
	}

	public void setEara(String eara) {
		this.eara = eara;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

}
