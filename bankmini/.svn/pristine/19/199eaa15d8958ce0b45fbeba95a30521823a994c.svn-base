package com.huayu.irla.manage.application.commentManage;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.comment.vo.CommentVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;
/**
 * 评论管理权限
 * @author GuGuangting
 * @time 2018年8月27日-pm5:05:19
 */
@Path("/")
@PrivilegePointor("Comment Manage")
public interface ICommentManage {
	/**
	 * @Description: 查询
	 * @param comment
	 * @return
	 */
	@POST
	@Path("findComment")
	@Produces({MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Comment")
	JSONObject findComment(CommentVO comment);
	/**
	 * 
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	@GET
    @Path("/deleteComment/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Comment")
    Integer deleteComment(@PathParam(value = "id") Integer id) throws Exception;
}
