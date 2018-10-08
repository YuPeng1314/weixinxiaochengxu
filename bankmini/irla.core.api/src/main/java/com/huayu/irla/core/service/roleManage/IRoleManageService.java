package com.huayu.irla.core.service.roleManage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.irla.core.user.vo.RoleInfoVO;
import com.huayu.irla.core.user.vo.UserInfoVO;

/**
 * 
  * @ClassName: IRoleManageService
  * @Description: 角色管理服务层接口
  * @author liuwei
  * @date 2016年10月24日 下午4:41:53
  *
 */

public interface IRoleManageService {
	
	/**
	 * 
	  * @Title: getRole
	  * @Description: 取得角色详细信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:42:22
	  * @param role
	  * @return
	  * @
	 */

	List<RoleInfoVO> getRole(RoleInfoVO  role) ;
	
	/**
	 * 
	  * @Title: getCount
	  * @Description: 取得角色信息记录总数
	  * @author liuwei
	  * @date 2016年10月24日 下午4:42:40
	  * @param role
	  * @return
	  * @
	 */
	
	Integer getCount( RoleInfoVO role) ;
	
	/**
	 * 
	  * @Title: updateRole
	  * @Description: 修改角色信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:43:00
	  * @param role
	  * @
	 */
	
	void updateRole(RoleInfoVO role) ;
	
	/**
	 * 
	  * @Title: addRole
	  * @Description: 增加角色信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:43:11
	  * @param role
	  * @
	 */
	
	void addRole(RoleInfoVO role) ;
	
	/**
	 * 
	  * @Title: deleteRole
	  * @Description: 删除角色信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:43:24
	  * @param id
	  * @
	 */
	
	void deleteRole(Integer roleId) ;
	
	/**
	 * 
	  * @Title: getRoleUser
	  * @Description: 取得用户所含有的角色信息
	  * @author liuwei
	  * @date 2016年10月25日 上午10:37:27
	  * @param role
	  * @return
	  * @
	 */
	
	List<RoleInfoVO> getRoleUser(@Param("role") RoleInfoVO  role) ;
	
	/**
	 * 
	  * @Title: deleteRoleUser
	  * @Description: 删除用户角色信息
	  * @author liuwei
	  * @date 2016年10月25日 上午11:16:45
	  * @param id
	  * @
	 */
	
	void deleteRoleUser(Integer urId) ;
	
	
	/**
	 * 
	  * @Title: deleteRoleUserUserID
	  * @Description: 删除用户角色信息
	  * @author liuwei
	  * @date 2016年10月25日 上午11:16:45
	  * @param userId
	  * @
	 */
	void deleteRoleUserUserID(Integer userId) ;
	
	  /**
	   * 
	    * @Title: filterUser
	    * @Description: 筛选用户信息
	    * @author liuwei
	    * @date 2016年10月26日 下午5:02:55
	    * @param role
	    * @return
	    * @
	   */
		
		List<RoleInfoVO> filterUser(@Param("role") RoleInfoVO  role) ;
		
		
	/**
	 * 
	 * @Title: findMessageUserInfoList
	 * @Description: 筛选用户信息
	 * @author liuwei
	 * @date 2017年07月13日 下午5:02:55
	 * @param userInfoVO
	 * @return @
	 */
	  List<UserInfoVO> findMessageUserInfoList(@Param("userInfoVO") UserInfoVO  userInfoVO);
				
	  Integer getMessageUserInfoCount(@Param("userInfoVO") UserInfoVO  userInfoVO);
		
		/**
		 * 
		  * @Title: addRoleUser
		  * @Description: 增加角色用户
		  * @author liuwei
		  * @date 2016年10月26日 下午8:04:32
		  * @param role
		  * @
		 */
		
		void addRoleUser(RoleInfoVO role) ;
		
		
		
	   /**
	    * 
	     * @Title: getRoleUserCount
	     * @Description: 取得角色下的用户总数
	     * @author liuwei
	     * @date 2016年10月27日 下午4:14:49
	     * @param role
	     * @return
	     * @
	    */
		
		Integer getRoleUserCount(@Param("role") RoleInfoVO role) ;
		
		
		/**
		 * 
		  * @Title: getUserCount
		  * @Description:取得不是角色下的用户总数 
		  * @author liuwei
		  * @date 2016年10月27日 下午4:15:10
		  * @param role
		  * @return
		  * @
		 */
		
		Integer getUserCount(@Param("role") RoleInfoVO role) ;
		
	    /**
	      * 
	       * @Title: updateRoleUser
	       * @Description: 修改角色用户信息
	       * @author liuwei
	       * @date 2016年10月27日 下午5:43:46
	       * @param role
	       * @
	      */
		
		void updateRoleUser(RoleInfoVO role) ;
		
		/**
		 * 
		  * @Title: findUserID
		  * @Description: 取得用户权限表中用户ID
		  * @author liuwei
		  * @date 2016年10月28日 上午11:04:51
		  * @param role
		  * @
		 */
		
		RoleInfoVO findUserID(@Param("role") RoleInfoVO role) ;
		
		
		/**
		 * 
		  * @Title: getAuthority
		  * @Description: 取得权限信息
		  * @author liuwei
		  * @date 2016年11月1日 上午11:58:25
		  * @param role
		  * @return
		  * @
		 */
		
		List<RoleInfoVO> getAuthority(@Param("role") RoleInfoVO  role) ;
		
		/**
		 * 
		  * @Title: getRoleAuthority
		  * @Description: 取得角色下的权限信息
		  * @author liuwei
		  * @date 2016年11月2日 下午3:03:16
		  * @param role
		  * @return
		  * @
		 */
		
		List<RoleInfoVO> getRoleAuthority(@Param("role") RoleInfoVO  role) ;
		
		/**
		 * 
		  * @Title: deleteRoleAuthority
		  * @Description: 删除角色权限信息
		  * @author liuwei
		  * @date 2016年11月3日 下午12:38:49
		  * @param roleId
		  * @
		 */
		 
		
		void deleteRoleAuthority(Integer roleId) ;
		
		/**
		 * 
		  * @Title: addRoleAuthority
		  * @Description: 增加角色权限信息 
		  * @author liuwei
		  * @date 2016年11月3日 下午12:07:44
		  * @param role
		  * @
		 */
		
		void addRoleAuthority(RoleInfoVO role) ;
	
}
