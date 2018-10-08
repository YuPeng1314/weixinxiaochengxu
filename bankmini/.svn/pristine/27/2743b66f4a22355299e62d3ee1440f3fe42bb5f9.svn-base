package com.huayu.irla.core.dao.roleManage;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.user.vo.RoleInfoVO;
import com.huayu.irla.core.user.vo.UserInfoVO;

/**
 * 
 * @ClassName: IRoleManageDao
 * @Description: 角色管理DAO层接口
 * @author liuwei
 * @date 2016年10月24日 下午4:39:55
 *
 */

public interface IRoleManageDao {

	/**
	 * 
	 * @Title: getRole
	 * @Description: 角色详细信息
	 * @author liuwei
	 * @date 2016年10月24日 下午4:29:18
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<RoleInfoVO> getRole(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: getCount
	 * @Description: 取得角色信息记录总数
	 * @author liuwei
	 * @date 2016年10月24日 下午4:37:53
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCount(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: updateRole
	 * @Description: 修改角色信息
	 * @author liuwei
	 * @date 2016年10月24日 下午4:38:14
	 * @param role
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateRole(RoleInfoVO role);

	/**
	 * 
	 * @Title: addRole
	 * @Description: 增加角色信息
	 * @author liuwei
	 * @date 2016年10月24日 下午4:38:31
	 * @param role
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addRole(RoleInfoVO role);

	/**
	 * 
	 * @Title: deleteRole
	 * @Description: 删除角色信息
	 * @author liuwei
	 * @date 2016年10月24日 下午4:38:40
	 * @param id
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteRole(Integer roleId);

	/**
	 * 
	 * @Title: getRoleUser
	 * @Description: 取得用户所含有的角色信息
	 * @author liuwei
	 * @date 2016年10月25日 上午10:37:27
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<RoleInfoVO> getRoleUser(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: deleteRoleUser
	 * @Description: 删除用户角色信息
	 * @author liuwei
	 * @date 2016年10月25日 上午11:16:45
	 * @param id
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteRoleUser(Integer urId);

	/**
	 * 
	 * @Title: deleteRoleUser
	 * @Description: 删除用户角色信息
	 * @author liuwei
	 * @date 2016年10月25日 上午11:16:45
	 * @param id
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteRoleUserUserID(Integer userId);

	/**
	 * 
	 * @Title: filterUser
	 * @Description: 筛选用户信息
	 * @author liuwei
	 * @date 2016年10月26日 下午5:02:55
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<RoleInfoVO> filterUser(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: findMessageUserInfoList
	 * @Description: 发消息时查看所有用户信息
	 * @author liuwei
	 * @date 2017年07月13日 下午5:02:55
	 * @param userInfoVO
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<UserInfoVO> findMessageUserInfoList(@Param("userInfoVO") UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: getMessageUserInfoCount
	 * @Description: 取得总数
	 * @author liuwei
	 * @date 2017年07月13日 下午5:02:55
	 * @param userInfoVO
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getMessageUserInfoCount(@Param("userInfoVO") UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: addRoleUser
	 * @Description: 增加角色用户
	 * @author liuwei
	 * @date 2016年10月26日 下午8:04:32
	 * @param role
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addRoleUser(RoleInfoVO role);

	/**
	 * 
	 * @Title: getRoleUserCount
	 * @Description: 取得角色下的用户总数
	 * @author liuwei
	 * @date 2016年10月27日 下午4:14:49
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getRoleUserCount(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: getUserCount
	 * @Description:取得不是角色下的用户总数
	 * @author liuwei
	 * @date 2016年10月27日 下午4:15:10
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getUserCount(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: updateRoleUser
	 * @Description: 修改角色用户信息
	 * @author liuwei
	 * @date 2016年10月27日 下午5:43:46
	 * @param role
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateRoleUser(RoleInfoVO role);

	/**
	 * 
	 * @Title: findUserID
	 * @Description: 取得用户权限表中用户ID
	 * @author liuwei
	 * @date 2016年10月28日 上午11:04:51
	 * @param role
	 * @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	RoleInfoVO findUserID(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: getAuthority
	 * @Description: 取得权限信息
	 * @author liuwei
	 * @date 2016年11月1日 上午11:58:25
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<RoleInfoVO> getAuthority(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: getRoleAuthority
	 * @Description: 取得角色下的权限信息
	 * @author liuwei
	 * @date 2016年11月2日 下午3:03:16
	 * @param role
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<RoleInfoVO> getRoleAuthority(@Param("role") RoleInfoVO role);

	/**
	 * 
	 * @Title: deleteRoleAuthority
	 * @Description: 删除角色权限信息
	 * @author liuwei
	 * @date 2016年11月3日 下午12:38:49
	 * @param roleId
	 * @
	 */

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteRoleAuthority(Integer roleId);

	/**
	 * 
	 * @Title: addRoleAuthority
	 * @Description: 增加角色权限信息
	 * @author liuwei
	 * @date 2016年11月3日 下午12:07:44
	 * @param role
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addRoleAuthority(RoleInfoVO role);

}
