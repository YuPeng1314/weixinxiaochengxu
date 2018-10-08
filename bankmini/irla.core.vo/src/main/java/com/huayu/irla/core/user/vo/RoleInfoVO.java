package com.huayu.irla.core.user.vo;

import java.util.Date;

import com.huayu.irla.core.base.BaseVO;

/**
 * 
 * @ClassName: RoleInfoVO
 * @Description: 角色信息实体类
 * @author liuwei
 * @date 2016年10月24日 下午4:15:23
 *
 */

public class RoleInfoVO extends BaseVO {

	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = -8739615474430496366L;

	/*SYS角色管理*/
	
	/**
	 * SYS角色id
	 */
	private Integer roleId;

	/**
	 * SYS角色名称
	 */
	private String roleName;

	private String roleDesc;

	private String issys;

	private String moduleId;

	private String type;
	


	/*SYS用户 */
	
	/**
	 * SYS用户id
	 */
	private Integer userId;
	
	/**
	 * SYS角色与用户id
	 */
	private Integer urId;

	/**
	 * SYS用户编码
	 */
	private String userCode;

	/**
	 * SYS用户密码
	 */
	private String password;

	/**
	 * 上线时间
	 */
	private Date dtCreate;

	/**
	 * 登陆时间
	 */
	private Date lastLogin;

	/**
	 * 下线时间
	 */
	private Date deadline;


	/* 用户信息 */
	
	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户id
	 */
	private String id;
	
	/**
	 * 用户性别
	 */
	private String sex;

	/**
	 * 用户出生日期
	 */
	private Date dateOfBirth;

	/**
	 * 用户注册名
	 */
	private String registeredName;

	/**
	 * 用户联系电话
	 */
	private Long phone;

	/**
	 * 用户居住详细地址
	 */
	private String residentialAddress;

    /**
     * 用户所属部门的名称
     */
	private String department;

    /**
     * 用户积分
     */
    private String integral;

    /**
     * 用户级别
     */
    private String level;

    /**
     * 用户头像链接
     */
    private String pictureLinks;
	
	  /**
     * 用户所在省/直辖市
     */
    private String province;
    
    /**
     * 用户所在市/区
     */
    private String town;
    
    /**
     * 用户所在县、区
     */
    private String district;
	
	
	//SYS权限
	
    /**
     * SYS权限id
     */
	private String authorityId;

	/**
     * SYS权限方法
     */
	private String authorityMark;

	/**
     * SYS权限名称
     */
	private String authorityName;

	/**
     * SYS资源id
     */
	private String resourceId;

	/**
     * SYS资源名称
     */
	private String resourceName;
	

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityMark() {
		return authorityMark;
	}

	public void setAuthorityMark(String authorityMark) {
		this.authorityMark = authorityMark;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPictureLinks() {
		return pictureLinks;
	}

	public void setPictureLinks(String pictureLinks) {
		this.pictureLinks = pictureLinks;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getIssys() {
		return issys;
	}

	public void setIssys(String issys) {
		this.issys = issys;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer getUrId() {
		return urId;
	}

	public void setUrId(Integer urId) {
		this.urId = urId;
	}
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}