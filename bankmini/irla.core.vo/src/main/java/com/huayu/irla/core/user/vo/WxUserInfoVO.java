package com.huayu.irla.core.user.vo;

import com.huayu.irla.core.base.BaseVO;

public class WxUserInfoVO extends BaseVO  {
	
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer user_id;
	/**用户编码*/
	private String user_code;
	/**用户昵称*/
	private String user_nkname; 
	/**用户图像*/
	private String user_img; 
	/**所在区域*/
	private String area_info; 
	/**所在国家*/
	private String country; 
	/**所在省份*/
	private String province; 
	/**所在城市*/
	private String city; 
	/**用户名*/
	private String user_name; 
	/**用户性别*/
	private String user_sex; 
	/**用户工作*/
	private String user_work; 
	/**用户登录状态*/
	private String user_long_status; 
	/**经度*/
	private String longitude; 
	/**纬度*/
	private String latitude;
	
	/** 最小纬度 */
    private double minlat;
    
    /** 最大纬度 */ 
    private double maxlat;
    
    /** 最小经度 */ 
    private double minlng;
    
    /** 最大经度 */ 
    private double maxlng;
    
	/** 查询  （0：可能认识，1：系统推荐，2：正常查询信息） */
    private String isQuery;
    
    public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_nkname() {
		return user_nkname;
	}
	public void setUser_nkname(String user_nkname) {
		this.user_nkname = user_nkname;
	}
	public String getUser_img() {
		return user_img;
	}
	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}
	public String getArea_info() {
		return area_info;
	}
	public void setArea_info(String area_info) {
		this.area_info = area_info;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_work() {
		return user_work;
	}
	public void setUser_work(String user_work) {
		this.user_work = user_work;
	}
	public String getUser_long_status() {
		return user_long_status;
	}
	public void setUser_long_status(String user_long_status) {
		this.user_long_status = user_long_status;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
    public double getMinlat() {
		return minlat;
	}
	public void setMinlat(double minlat) {
		this.minlat = minlat;
	}
	public double getMaxlat() {
		return maxlat;
	}
	public void setMaxlat(double maxlat) {
		this.maxlat = maxlat;
	}
	public double getMinlng() {
		return minlng;
	}
	public void setMinlng(double minlng) {
		this.minlng = minlng;
	}
	public double getMaxlng() {
		return maxlng;
	}
	public void setMaxlng(double maxlng) {
		this.maxlng = maxlng;
	}
	public String getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
}
