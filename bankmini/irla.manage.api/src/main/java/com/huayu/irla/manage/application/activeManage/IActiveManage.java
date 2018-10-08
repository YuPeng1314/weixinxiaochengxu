package com.huayu.irla.manage.application.activeManage;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.active.vo.ActiveVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;
/**
 * 活动管理权限
 * @author GuGuangting
 */
@Path("/")
@PrivilegePointor("Active Manage")
public interface IActiveManage {
	/**
	 * 获取活动信息
	 * @param active
	 * @return
	 */
	@POST
    @Path("/getActive")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Active")
    JSONObject findActive(ActiveVO active);
    /**
     * 新增活动
     * @param active
     * @return
     */
    @POST
    @Path("/addActive")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Active")
    boolean addActive(ActiveVO active);
    /**
     * 删除活动
     * @param active
     * @return
     * @throws Exception
     */
    @GET
    @Path("/deleteActive/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Active")
    Integer deleteActive(@PathParam(value = "id") Integer id) throws Exception;
    /**
     * 修改活动
     * @param active
     * @return
     */
    @POST
    @Path("/updateActive")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Active")
    boolean updateActive(ActiveVO active);
    /**
     * 切换活动发布状态
     * @param id
     * @param state
     * @return
     */
	@GET
	@Path("/validActive/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="valid Active")
	String validActive(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);
	/**
	 * 保存活动图片
	 * @param attachments
	 * @param active
	 * @return
	 */
	@POST
	@Encoded 
	@Path("/saveActiveImg")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	boolean saveActiveImg(@Multipart(value = "files") List<Attachment> attachments,@FormParam("") ActiveVO active);
}
