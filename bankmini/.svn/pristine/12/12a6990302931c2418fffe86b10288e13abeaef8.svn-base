package com.huayu.irla.manage.application.courseManage;

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
import com.huayu.irla.core.course.vo.CourseVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 课程管理权限
 * @author GuGuangting
 * @time 2018年6月20日-am5:05:19
 */
@Path("/")
//课程管理的权限控制
@PrivilegePointor("Course Manage")
public interface ICourseManage {
	@POST
    @Path("/getCourse")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Course")
    JSONObject findCourse(CourseVO course);
	/**
	 * @Description:新增修改课程有图片操作
	 * @param attachments 图片附件
	 * @param course 课程
	 * @return
	 */
	@POST
	@Path("/optCourse")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@PrivilegePoint(privilegeMode = PrivilegePoint.ROLE_UPDATE, privilegeName = "Operate Img Course")
	boolean optCourse(@Multipart(value = "files") List<Attachment> attachments, @FormParam("") CourseVO course);
	/**
	 * 修改课程不修改图片
	 * @param course
	 * @return
	 */
	@POST
    @Path("/updateCourse")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Course")
    boolean updateCourse(CourseVO course);
	/**
	 * 删除课程
	 * @param course
	 * @return
	 * @throws Exception
	 */
    @POST
    @Path("/deleteCourse")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Course")
    boolean deleteCourse(CourseVO course) throws Exception;

    /**
	 * 
	  * @Title: validCourse
	  * @Description: 切换目录状态
	  * @return String
	  * @author ggt
	 */
	@GET
	@Path("/validCourseState/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="valid CourseState")
	String validCourse(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);

}
