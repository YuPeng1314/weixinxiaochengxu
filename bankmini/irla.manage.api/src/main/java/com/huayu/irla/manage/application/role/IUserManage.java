package com.huayu.irla.manage.application.role;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.user.vo.UserInfoVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 
  * @ClassName: IUserManage
  * @Description: 用户管理action层的接口
  * @author liuwei
  * @date 2016年10月25日 下午9:00:04
  *
 */
@Path("/")
//用户管理的权限控制
@PrivilegePointor("User Manage")
public interface IUserManage {
    
	/**
	 * 
	  * @Title: findUserList
	  * @Description: 取得用户详细信息
	  * @author liuwei
	  * @date 2016年10月25日 下午9:20:45
	  * @param userInfoVO
	  * @return
	 */
	
	@POST
	@Path("/findUser")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find User")
	JSONObject findUserList(UserInfoVO userInfoVO);
	
	/**
	 *  
	  * @Title: addUser
	  * @Description: 增加用户信息
	  * @author liuwei
	  * @date 2016年10月25日 下午9:22:15
	  * @param userInfoVO
	 */
	
	@POST
	@Path("/addUser")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add User")
	boolean addUser(UserInfoVO userInfoVO);

	/**
	 * 
	  * @Title: updateUser
	  * @Description: 修改用户信息
	  * @author liuwei
	  * @date 2016年10月25日 下午9:22:37
	  * @param id
	  * @return
	 */
	
	@GET
	@Path("/updateUser/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update User")
	UserInfoVO updateUser(@PathParam(value = "id") Integer id);
    
	/**
	 * 
	  * @Title: deleteUser
	  * @Description: 删除用户信息
	  * @author liuwei
	  * @date 2016年10月25日 下午9:22:50
	  * @param id
	  * @return
	 */
	
	@POST
	@Path("/deleteUser/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete User")
	boolean deleteUser(@PathParam(value = "id") Integer id);
    
	/**
	 * 
	  * @Title: validUser
	  * @Description: 有效无效的切换
	  * @author liuwei
	  * @date 2016年10月25日 下午9:23:01
	  * @param id
	  * @param state
	  * @return
	 */
	
	@GET
	@Path("/validUser/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="valid User")
	boolean validUser(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);

	/**
	 * 
	  * @Title: resetPassword
	  * @Description: 重置用户密码
	  * @author liuwei
	  * @date 2016年11月7日 下午5:23:15
	  * @param userInfoVO
	 */
	
	@POST
	@Path("/resetPassword")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="reset Password")
	void resetPassword(UserInfoVO userInfoVO);
	
}
