package com.huayu.irla.manage.application.courseware;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.courseware.vo.FileUploadFileRelationVO;
import com.huayu.irla.core.courseware.vo.FileUploadItemVO;
import com.huayu.irla.core.courseware.vo.MultipartFileParamVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 
  * @ClassName: ICourseware
  * @Description: 课件维护Action层的接口类
  * @author liuwei
  * @date 2017年5月24日 下午5:53:15
  *
 */

@Path("/")
//课件维护的权限控制
@PrivilegePointor("Courseware Manage New")
public interface ICoursewarenew {
	
	/**
	 * 
	  * @Title: findCoursewarelist
	  * @Description: 查询所有的课件
	  * @author liuwei
	  * @date 2017年5月24日 下午6:01:27
	  * @param course
	  * @return
	 */
	
	@POST
	@Path("/checkFileMd5")
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="check File MD5")
	public Object checkFileMd5(@FormParam("md5")String md5, @FormParam("fileName")String fileName) throws IOException;

	/**
	 * 
	  * @Title: uploadMoreFile
	  * @Description: 上传多个文件
	  * @author liuwei
	  * @date 2017年5月24日 下午5:57:09
	  * @param attachments
	  * @param course
	  * @return
	 */
	
	@POST
	@Path("/fileUploadList")
	@Consumes({ MediaType.MULTIPART_FORM_DATA})
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPLOAD ,privilegeName="upload MoreFile")
    public Response fileUpload(@Multipart(value = "files") Attachment  file, @FormParam("")MultipartFileParamVO param);
	
	
	@POST
	@Path("/sendFileMessTransCoding")
	@Consumes({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="File Message TransCoding")
    public void sendFileMessTransCoding(FileUploadItemVO ietmVO);
	
	@GET
	@Path("/getCatalogTree")
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="File Catalog Tree")
	public JSONArray getCatalogTree();
	
	@GET
	@Path("/getUploadFileList")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode = PrivilegePoint.ROLE_READ, privilegeName = "Search Upload File List")
	public JSONObject getUploadFileList(@QueryParam("") FileUploadItemVO fileItemVO);
	
	@POST
	@Path("/deleteUploadFile")
	@Consumes({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="Delete Upload File")
	void deleteUploadFile(FileUploadItemVO ietmVO);

	@POST
	@Path("/insertUploadFileRelation")
	@Consumes({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode = PrivilegePoint.ROLE_ADD, privilegeName = "Add Upload File Relation")
	void insertUploadFileRelation(FileUploadFileRelationVO fileRelationVO);
	
	@GET
	@Path("/getUploadFileRelation")
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="Search Upload File Relation")
	List<FileUploadFileRelationVO> getUploadFileRelation(@QueryParam("fileId") Long uploadFileId);
	
	@GET
	@Path("/dittoUploadFileRelation")
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="Ditto Upload File Relation")
	public void dittoUploadFileRelation(@QueryParam("") FileUploadFileRelationVO fileRelationVO);

}
