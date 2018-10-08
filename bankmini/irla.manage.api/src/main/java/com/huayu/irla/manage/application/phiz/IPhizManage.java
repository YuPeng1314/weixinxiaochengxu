package com.huayu.irla.manage.application.phiz;

import java.util.List;

import javax.ws.rs.Consumes;
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
import com.huayu.irla.core.phiz.vo.PhizVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 表情管理的权限控制
 * @author ggt
 *
 */
@Path("/")
//表情管理的权限控制
@PrivilegePointor("Phiz Manage")
public interface IPhizManage {

	@POST
    @Path("/getPhiz")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Phiz")
    JSONObject findPhiz(PhizVO phiz);

    @POST
    @Path("/addPhiz")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Phiz")
    boolean addPhiz(@Multipart(value = "files") List<Attachment> attachments, @FormParam("") PhizVO phiz);
        
    @GET
    @Path("/delPhiz/{id}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Phiz")
    Integer delPhiz(@PathParam(value = "id") Integer id) throws Exception;

    @POST
    @Path("/updatePhiz")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Phiz")
    boolean updatePhiz(PhizVO phiz);
    /**
	 * @Description: 发布未发布切换
	 * @param ID state 状态
	 */
	@GET
	@Path("/validPhizPublish/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="Publish Phiz")
	boolean validPublish(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);
}
