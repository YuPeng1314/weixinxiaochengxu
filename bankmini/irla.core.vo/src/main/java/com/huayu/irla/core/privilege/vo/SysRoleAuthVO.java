/**
 * @Title: SysRoleAuthVO.java
 * @Package com.huayu.ietl.core.privilege.vo
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月9日 下午12:23:46
 * @version V1.0
 */

package com.huayu.irla.core.privilege.vo;

import com.huayu.irla.core.base.BaseVO;

/**
  * @ClassName: SysRoleAuthVO
  * @Description: 角色权限关系表VO
  * @author luozehua
  * @date 2016年11月9日 下午12:23:46
  *
  */

public class SysRoleAuthVO extends BaseVO {

    private static final long serialVersionUID = 8630228644569005757L;

    /**
     * id
     */
    private String id;
    
    /**
     * SYS权限id
     */
    private String authorityId;
    
    /**
     * SYS角色id
     */
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
