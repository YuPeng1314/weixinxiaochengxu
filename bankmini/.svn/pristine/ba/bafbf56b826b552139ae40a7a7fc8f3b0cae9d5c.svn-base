package com.huayu.irla.manage.application.userInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.user.vo.WxUserInfoVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 用户信息管理权限
 * @author 顾广婷
 */
@Path("/")
@PrivilegePointor("userInfo Manage")
public interface IUserInfoManage {
	/**
	 * 查询用户信息
	 * @param userInfo
	 * @return
	 */
	@POST
	@Path("findUserInfo")
	@Produces({MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find UserInfo")
	JSONObject findUserInfo(WxUserInfoVO userInfo);
	/**
	 * 删除用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GET
    @Path("/deleteUserInfo/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete UserInfo")
    Integer deleteUserInfo(@PathParam(value = "id") Integer id) throws Exception;
}
