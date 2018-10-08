package com.huayu.irla.manage.application.holiday;

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
import com.huayu.irla.core.holiday.vo.HolidayVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;
/**
 * 节日管理的权限控制
 * @author ggt
 *
 */
@Path("/")
//节日管理的权限控制
@PrivilegePointor("Holiday Manage")
public interface IHolidayManage {

	@POST
    @Path("/getHoliday")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Holiday")
    JSONObject findHoliday(HolidayVO holiday);

    @POST
    @Path("/addHoliday")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Holiday")
    boolean addHoliday(@Multipart(value = "files") List<Attachment> attachments, @FormParam("") HolidayVO holiday);

    @GET
    @Path("/deleteHoliday/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Holiday")
    Integer delHoliday(@PathParam(value = "id") Integer id) throws Exception;

    @POST
    @Path("/updateHoliday")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Holiday")
    boolean updateHoliday(@Multipart(value = "files") List<Attachment> attachments, @FormParam("") HolidayVO holiday);

    @POST
    @Path("/updateHolidayNoImg")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update HolidayNoImg")
    boolean updateHolidayNoImg(HolidayVO holiday);
}
