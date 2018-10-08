package com.huayu.irla.manage.application.courseware;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 
  * @ClassName: ICoursewareEdit
  * @Description: 课件资源维护
  * @author liuwei
  * @date 2017年9月18日 上午11:52:44
  * Copyright: Copyright (c) 2017 
  * Company:华煜网络教育有限公司
 */

@Path("/")
//课件维护的权限控制
@PrivilegePointor("Courseware Manage Edit")
public interface ICoursewareedit {

	/**
	 * 
	  * @Title: getCoursewarelist
	  * @Description: 取得课件资源
	  * @return JSONObject
	  * @author liuwei
	  * @date 2017年9月18日 上午11:57:13
	 */
	@POST
	@Path("/getCourseware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="Get Courseware")
	JSONObject getCoursewarelist(CoursewareVO course);
	
	/**
	 * 
	  * @Title: updateTime
	  * @Description: 修改视频指定学时
	  * @return boolean
	  * @author liuwei
	  * @date 2017年9月18日 下午6:32:56
	 */
	
	@POST
	@Path("/updateTime")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="Update LreanTime")
	boolean updateTime(CoursewareVO course);
	
	/**
	 * 
	  * @Title: uploadCourseImg
	  * @Description: 修改视频的封面图片
	  * @return boolean
	  * @author liuwei
	  * @date 2017年9月18日 下午6:32:14
	 */
	
	@POST
	@Path("/uploadCourseImg")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPLOAD ,privilegeName="upload CourseImg")
	boolean uploadCourseImg(@Multipart(value = "files") List<Attachment> attachments,@FormParam("") CoursewareVO course);
	
	/**
	 * 
	  * @Title: validCourse
	  * @Description: 发布或者撤销课件资源
	  * @return boolean
	  * @author liuwei
	  * @date 2017年9月18日 上午11:57:29
	 */
	@POST
	@Path("/validCourse/{resourceId}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="valid Courseware")
	boolean validCourse(@PathParam(value = "resourceId") String resourceId, @PathParam(value = "state") String state);
	
	/**
	 * 
	  * @Title: deleteVideo
	  * @Description: 删除资源
	  * @return boolean
	  * @author liuwei
	  * @date 2017年9月30日 上午10:13:49
	 */
	
	@POST
	@Path("/deleteVideo/{resourceId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Video")
	boolean deleteVideo(@PathParam (value = "resourceId") String resourceId);
	
	/**
	 * 
	  * @Title: getCoursewareInfo
	  * @Description: 查看单个视频的信息（包含多个目录）
	  * @return JSONObject
	  * @author liuwei
	  * @date 2017年11月21日 下午5:45:30
	 */
	
	@POST
	@Path("/getCoursewareInfo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="Get CoursewareInfo")
	JSONObject getCoursewareInfo(CoursewareVO course);
	
}
