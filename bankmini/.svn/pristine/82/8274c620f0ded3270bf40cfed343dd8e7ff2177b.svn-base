/**
 * @Title: IUserMuneAuthDao.java
 * @Package com.huayu.ietl.core.dao
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月9日 下午12:12:52
 * @version V1.0
 */

package com.huayu.irla.manage.dao.menu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.privilege.vo.SysAuthoritiesResourceVO;
import com.huayu.irla.core.privilege.vo.SysRoleAuthVO;
import com.huayu.irla.core.privilege.vo.SysUserRoleVO;

/**
  * @ClassName: IUserMuneAuthDao
  * @Description: 用户，权限，资源等表的操作
  * @author luozehua
  * @date 2016年11月9日 下午12:12:52
  *
  */

public interface IUserMuneAuthDao {
    
    //通过用户ID查找角色ID  sys_users_roles
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    List<SysUserRoleVO> findRoleByUser(@Param("sysUserRole") SysUserRoleVO sysUserRole);

    //通过角色ID查询权限ID  sys_roles_authorities
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    List<SysRoleAuthVO> findAuthByRole(@Param("sysRoleAuth") SysRoleAuthVO sysRoleAuth);

   //通过权限ID查询Resource_ID sys_authorities_resources
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    List<SysAuthoritiesResourceVO> findAuthResByAuth(@Param("sysAuthRes") SysAuthoritiesResourceVO sysAuthRes);

    /*  //通过Resource_ID查询resourcePath sys_resources
    SysResoucesVO findResById(@Param("resId") String resId);*/

}
