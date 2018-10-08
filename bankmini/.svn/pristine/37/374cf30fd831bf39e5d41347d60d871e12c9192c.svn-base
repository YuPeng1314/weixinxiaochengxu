package com.huayu.irla.manage.application.roleManage;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.user.vo.RoleInfoVO;
import com.huayu.irla.core.user.vo.UserInfoVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 
  * @ClassName: IRoleManage
  * @Description: 角色管理action层接口
  * @author liuwei
  * @date 2016年10月24日 下午4:23:19
  *
 */

@Path("/")
//角色管理的权限控制
@PrivilegePointor("Role Manage")
public interface IRoleManage {
	
	/**
	 * 
	  * @Title: getRoleList
	  * @Description: 取得角色详细信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:35:46
	  * @param role
	  * @return
	 */
	
	@POST
	@Path("/getRole")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Role")
	JSONObject getRoleList(RoleInfoVO role);
	
	/**
	 * 
	  * @Title: addRole
	  * @Description: 增加角色信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:36:18
	  * @param role
	 */
	
	@POST
	@Path("/addRole")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Role")
	void addRole(RoleInfoVO role);
	
	/**
	 * 
	  * @Title: updateRole
	  * @Description: 修改角色信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:36:30
	  * @param id
	  * @return
	 */
	
	@GET
	@Path("/updateRole/{roleId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Role")
	RoleInfoVO updateRole(@PathParam (value = "roleId")Integer roleId);
	
	/**
	 * 
	  * @Title: deleteRole
	  * @Description: 删除角色信息
	  * @author liuwei
	  * @date 2016年10月24日 下午4:36:44
	  * @param id
	  * @return
	 */
	
	@POST
	@Path("/deleteRole/{roleId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Role")
	boolean deleteRole(@PathParam (value = "roleId") Integer roleId);
	
	/**
	 * 
	  * @Title: validRole
	  * @Description: 角色有效无效的切换
	  * @author liuwei
	  * @date 2016年10月24日 下午4:37:01
	  * @param id
	  * @param state
	  * @return
	 */
	
	@GET
	@Path("/validRole/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="valid Role")
	boolean validRole(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);

	/**
	 * 
	  * @Title: validRoleUser
	  * @Description: 角色用户有效无效的切换
	  * @author liuwei
	  * @date 2016年10月27日 下午5:40:45
	  * @param id
	  * @param state
	  * @return
	 */
	
	@GET
	@Path("/validRoleUser/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="valid RoleUser")
	boolean validRoleUser(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);
	
	
	
	/**
	 * 
	  * @Title: getRoleUserList
	  * @Description: 取得用户下的角色
	  * @author liuwei
	  * @date 2016年10月25日 下午5:33:05
	  * @param role
	  * @return
	 */
	 
	@POST
	@Path("/getRoleUser")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find RoleUser")
	JSONObject getRoleUserList(RoleInfoVO role);
	
	/**
	 * 
	  * @Title: deleteRoleUser
	  * @Description: 删除用户角色信息
	  * @author liuwei
	  * @date 2016年10月25日 上午11:17:01
	  * @param id
	  * @return
	 */
	
	@POST
	@Path("/deleteRoleUser/{urId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete RoleUser")
	boolean deleteRoleUser(@PathParam (value = "urId") Integer urId);
	/**
	 * 
	  * @Title: filterUserList
	  * @Description: 筛选不属于当前角色的用户信息
	  * @author liuwei
	  * @date 2016年10月27日 下午3:18:13
	  * @param userName
	  * @param roleId
	  * @return
	 */
	
	
	@POST
	@Path("/filterUser")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="filter User")
	JSONObject filterUserList(RoleInfoVO role);
	
	
	/**
	 * 
	  * @Title: findMessageUserInfoList
	  * @Description: 查询所有用户信息
	  * @author liuwei
	  * @date 2016年10月27日 下午3:18:13
	  * @param userName
	  * @param userCode
	  * @return
	 */
	@POST
	@Path("/findMessageUserInfoList")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="filter MessageUserInfoList")
	JSONObject findMessageUserInfoList(UserInfoVO userInfoVO);
	
	
     /**
	 * 
	  * @Title: addRoleUser
	  * @Description: 增加角色用户
	  * @author liuwei
	  * @date 2016年10月26日 下午7:58:21
	  * @param role
	 */
	@POST
	@Path("/addRoleUser/{roleId}/{tem}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add RoleUser")
	void addRoleUser(@PathParam("roleId") String roleId,@PathParam("tem") String tem);
	
	
	/**
	 * 
	  * @Title: getAuthorityList
	  * @Description: 取得权限信息
	  * @author liuwei
	  * @date 2016年11月1日 上午11:56:58
	  * @param roleId
	  * @return
	 */
	
	@GET
    @Path("/getAuthority")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Authority")
	JSONObject getAuthorityList(RoleInfoVO role);
	
	/**
	 * 
	  * @Title: getRoleAuthorityList
	  * @Description: 取得角色下的权限信息
	  * @author liuwei
	  * @date 2016年11月2日 下午3:02:23
	  * @param roleId
	  * @return
	 */
	
	@GET
	@Path("/getRoleAuthority/{roleId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find RoleAuthority")
	JSONObject getRoleAuthorityList(@PathParam("roleId") Integer roleId);
	
	/**
	 * 
	  * @Title: deleteRoleAuthority
	  * @Description: 删除角色下的权限信息
	  * @author liuwei
	  * @date 2016年11月3日 上午11:48:08
	  * @param role
	 */
	
	@POST
	@Path("/deleteRoleAuthority/{roleId}/{tem}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete RoleAuthority")
	boolean deleteRoleAuthority(@PathParam("roleId") Integer roleId,@PathParam("tem") String tem);
	
	/**
	 * 
	  * @Title: findRoleType
	  * @Description: 查找角色信息
	  * @return JSONObject
	  * @author liuwei
	  * @date 2017年9月26日 下午5:26:24
	 */
	
	@GET
	@Path("/findRoleType")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find RoleType")
	JSONObject findRoleType();
	
}

