package com.huayu.irla.manage.application.categoryManage;

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

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.category.vo.CategoryVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 类别管理权限
 * @author GuGuangting
 * @time 2018年6月20日-am5:05:19
 */
@Path("/")
//类别管理的权限控制
@PrivilegePointor("Category Manage")
public interface ICategoryManage {
 	@POST
    @Path("/getCategory")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Category")
    JSONObject findCategory(CategoryVO category);
    /**
      * @Description: 添加或修改
      * @param category
     */
    @POST
    @Path("/addCategory")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Category")
    boolean addCategory(CategoryVO category);

    @POST
    @Path("/deleteCategory")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Category")
    Integer deleteCategory(CategoryVO category) throws Exception;

    @POST
    @Path("/updateCategory")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Category")
    boolean updateCategory(CategoryVO category);
    /**
	 * 
	  * @Title: validCategory
	  * @Description: 切换目录状态
	  * @return String
	  * @author ggt
	 */
	@GET
	@Path("/validCategory/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="valid Category")
	String validCategory(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);
	/**
	 * 保存图片
	 * @param attachments
	 * @param eqp
	 * @return
	 */
	@POST
	@Encoded 
	@Path("/saveCategoryImg")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	boolean saveCategoryImg(@Multipart(value = "files") List<Attachment> attachments,@FormParam("") CategoryVO eqp);
}
