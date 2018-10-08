package com.huayu.irla.core.user.vo;

public class WxUserFriendResVO {
    /**
     * 用户好友关系id
     * */
    private Integer user_res_id; 
    /**
     * 当前用户编码
     * */
	private String user_code;
	
	/**
	 * 用户好友编码
	 * **/
	private String user_friend_code;
	
	public Integer getUser_res_id() {
		return user_res_id;
	}
	public void setUser_res_id(Integer user_res_id) {
		this.user_res_id = user_res_id;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_friend_code() {
		return user_friend_code;
	}
	public void setUser_friend_code(String user_friend_code) {
		this.user_friend_code = user_friend_code;
	}
}
