package com.huayu.irla.manage.application.theme;

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
import com.huayu.irla.core.theme.vo.ThemeVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 主题管理的权限控制
 * @author ggt
 *
 */
@Path("/")
//主题管理的权限控制
@PrivilegePointor("Theme Manage")
public interface IThemeManage {
	@POST
    @Path("/getTheme")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Theme")
    JSONObject getTheme(ThemeVO theme);

    @POST
    @Path("/addTheme")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Theme")
    boolean addTheme(@Multipart(value = "files") List<Attachment> attachments, @FormParam("") ThemeVO theme);
    
    @POST
    @Path("/updateTheme")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Theme")
    boolean updateTheme(@Multipart(value = "files") List<Attachment> attachments, @FormParam("") ThemeVO theme);
    
    @POST
    @Path("/updateThemeNoImg")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update ThemeNoImg")
    boolean updateThemeNoImg(ThemeVO theme);

    @GET
    @Path("/delTheme/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Theme")
    Integer delTheme(@PathParam(value = "id") Integer id) throws Exception;
}
