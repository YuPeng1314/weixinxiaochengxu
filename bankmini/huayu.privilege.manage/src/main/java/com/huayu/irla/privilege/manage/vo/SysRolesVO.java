package com.huayu.irla.privilege.manage.vo;

import java.io.Serializable;
	
public class SysRolesVO implements Serializable {
	
	private static final long serialVersionUID = 5600705311901528953L;

	/**
	 * 用户ID
	 */
	private String roleID;  
	
	/**
	 * 用户名称
	 */
    private String roleName;  
    
    /**
     * 名称
     */
    private String roleDesc;
    
    /**
     * 角色类型
     */
    private int type;
    
    

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
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
}
