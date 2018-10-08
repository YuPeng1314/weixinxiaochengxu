/**
 * @Title: SysAuthoritiesResourceVO.java
 * @Package com.huayu.ietl.core.privilege.vo
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月8日 下午3:17:43
 * @version V1.0
 */

package com.huayu.irla.core.privilege.vo;

import com.huayu.irla.core.base.BaseVO;

/**
  * @ClassName: SysAuthoritiesResourceVO
  * @author luozehua
  * @date 2016年11月8日 下午3:17:43
  *
  */

public class SysAuthoritiesResourceVO extends BaseVO{

    private static final long serialVersionUID = 4776025409456991210L;
    
    /**
     * id
     */
    private String id;
    
    /**
     * SYS资源id
     */
    private String resourceId;
    
    /**
     * SYS权限id
     */
    private String authorityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }


}
