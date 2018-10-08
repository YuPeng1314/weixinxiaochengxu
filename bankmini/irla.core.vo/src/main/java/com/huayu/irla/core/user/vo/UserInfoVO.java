package com.huayu.irla.core.user.vo;

import java.util.Date;
import java.util.List;

import com.huayu.irla.core.base.BaseVO;

/**
 * 用户信息
 * @author ao.chen
 *
 */
public class UserInfoVO extends BaseVO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4778897576783337573L;

	//用户基本信息表
	
	/**
	 * 用户id
	 */
	private Integer id;

	/**
	 * 用户帐号
	 */
    private String userCode;
    
    /**
     * 用户姓名
     */
    private String userName;
  
     /**
     * * fzh
     * 用于显示安全用户名　如***华
     */
    private String validuserName;
    
	/**
     * 用户性别
     */
    private String sex;

    /**
     * 用户出生日期
     */
    private Date dateOfBirth;
    
   /**
    * 用来处理前端Str post请求date报400错误
    * */
	private String strBirth;

    /**
     * 用户昵称    
     */
    private String registeredName;
    
    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户联系电话
     */
    private Long phone;
    
    /**
     * fzh
     * 用于显示安全手机号　如136****5486 
     */
    private String validMobile;

	/**
     * 用户居住详细地址
     */
    private String residentialAddress;

    /**
     * 用户所属部门的名称
     */
    private String department;
    
    /**
     * 用户所属部门的编码
     */
    private String departmentCode;
    
    /**
     * 平台名称
     */
    private String plateformName;
    
    /**
     * 用户是否认证
     */
    private String isAuthentication;
    
    /**
     * 认证机构名称
     */
    private String certificationAuthority;
    
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
    
    /**
     * 收件人
     */
    private String recipients;
    
    
    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 推荐周期
     */
    private String reMonth;
    
    /**
     * 判断当前月份是增还是减
     */
    private Integer  monthSA;
    
    /**
     * 推荐按月份算
     */
    private Integer reCycle;
    
    /**
     * 专业编码
     */
    private String profeCode;
    
    /**
	 * 临时帐号（用于修改用户帐号）
	 */
    private String interimCode;
    
    //用户密码权限表
    
    /**
     * 用户密码
     */
    private String password;
    
    /**
     * 用户创建时间
     */
    private Date dtCreate;
    
    /**
     * 用户最后登录时间
     */
	private Date deadline;
    
	/**
	 * 用户下线时间
	 */
    private Date lastLogin;
    
    private Integer AccountNonExpired;
    
    private Integer AccountNonLocked;
    
    private Integer CredentialsNonExpired;
    
    
    
    //部门管理表
    
	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	/**
     * 部门编码
     */
    private String depaCode;

    /**
     * 部门名称
     */
    private String depaName;

    /**
     * 上级部门编码
     */
    private String parentDepaCode;
    
    /**
     * 上级部门名称临时加的用于手机端
     */
    private String parentDepaName;
    
    /**
     * 个性签名
     */
    private String individuality;
    
    /**
     * 部门编码的查询
     */
    private List<String> departmentCodes;
    
    /**
     *   用户类型 
     */
    private String userType;
    
    /**
	 * 类型判断（用于部门人员树中区分部门和人员的字段,0:部门,1:人员）
	 */
    private String judgeType;

    public String getParentDepaName() {
		return parentDepaName;
	}

	public void setParentDepaName(String parentDepaName) {
		this.parentDepaName = parentDepaName;
	}

	public String getIndividuality() {
		return individuality;
	}

	public void setIndividuality(String individuality) {
		 this.individuality = individuality == null ? null : individuality.trim();
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getReCycle() {
		return reCycle;
	}

	public void setReCycle(Integer reCycle) {
		this.reCycle = reCycle;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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
        this.registeredName = registeredName == null ? null : registeredName.trim();
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
        this.residentialAddress = residentialAddress == null ? null : residentialAddress.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral == null ? null : integral.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getPictureLinks() {
        return pictureLinks;
    }

    public void setPictureLinks(String pictureLinks) {
        this.pictureLinks = pictureLinks == null ? null : pictureLinks.trim();
    }


	public String getDepaCode() {
		return depaCode;
	}

	public void setDepaCode(String depaCode) {
		this.depaCode = depaCode;
	}

	public String getDepaName() {
		return depaName;
	}

	public void setDepaName(String depaName) {
		this.depaName = depaName;
	}

	public String getParentDepaCode() {
		return parentDepaCode;
	}

	public void setParentDepaCode(String parentDepaCode) {
		this.parentDepaCode = parentDepaCode;
	}


	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
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

	public String getProfeCode() {
		return profeCode;
	}

	public void setProfeCode(String profeCode) {
		this.profeCode = profeCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public List<String> getDepartmentCodes() {
		return departmentCodes;
	}

	public void setDepartmentCodes(List<String> departmentCodes) {
		this.departmentCodes = departmentCodes;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getInterimCode() {
		return interimCode;
	}

	public void setInterimCode(String interimCode) {
		this.interimCode = interimCode;
	}


    public String getIsAuthentication() {
		return isAuthentication;
	}

	public void setIsAuthentication(String isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	public String getCertificationAuthority() {
		return certificationAuthority;
	}

	public void setCertificationAuthority(String certificationAuthority) {
		this.certificationAuthority = certificationAuthority;
	}
	
    public Integer getAccountNonExpired() {
		return AccountNonExpired;
	}

	public String getReMonth() {
		return reMonth;
	}

	public void setReMonth(String reMonth) {
		this.reMonth = reMonth;
	}

	public void setAccountNonExpired(Integer accountNonExpired) {
		AccountNonExpired = accountNonExpired;
	}

	public Integer getMonthSA() {
		return monthSA;
	}

	public void setMonthSA(Integer monthSA) {
		this.monthSA = monthSA;
	}

	public Integer getAccountNonLocked() {
		return AccountNonLocked;
	}

	public void setAccountNonLocked(Integer accountNonLocked) {
		AccountNonLocked = accountNonLocked;
	}

	public Integer getCredentialsNonExpired() {
		return CredentialsNonExpired;
	}

	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		CredentialsNonExpired = credentialsNonExpired;
	}
	
    public String getValidMobile() {
		return validMobile;
	}

	public void setValidMobile(String validMobile) {
		this.validMobile = validMobile;
	}

    public String getValiduserName() {
		return validuserName;
	}

	public void setValiduserName(String validuserName) {
		this.validuserName = validuserName;
	}
	
	public String getStrBirth() {
		return strBirth;
	}

	public void setStrBirth(String strBirth) {
		this.strBirth = strBirth;
	}

	public String getJudgeType() {
		return judgeType;
	}

	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}

	public String getPlateformName() {
		return plateformName;
	}

	public void setPlateformName(String plateformName) {
		this.plateformName = plateformName;
	}

}