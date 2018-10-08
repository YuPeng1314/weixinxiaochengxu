package com.huayu.irla.manage.application.sectionCourse;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 课程集数设置权限
 * @author GuGuangting
 *
 */
@Path("/")
//课程管理的权限控制
@PrivilegePointor("Course Section")
public interface ISectionCourse {
	/**
	 * 课程下的资源搜索
	 * @param resourse 课程资源
	 * @return
	 */
	@POST
    @Path("/findResourse")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Resourse")
	JSONObject findResourse(CoursewareVO resourse);
	/**
	 * 修改课程下资源的序列
	 * @param resourse
	 * @return
	 */
	@POST
    @Path("/updateResourseSection")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="update Course Section")
    boolean updateResourseSection(Map<String, String>[] resourse);
}
