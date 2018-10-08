package com.huayu.irla.manage.application.log;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 
  * @ClassName: ILogManage
  * @Description: 日志管理
  * @author liuwei
  * @date 2017年1月3日 上午10:57:42
  *
 */

@Path("/")
//日志管理的权限控制
@PrivilegePointor("Log Manage")
public interface ILogManage {
	
	/**
	 * 
	  * @Title: findLogList
	  * @Description: 日志展示
	  * @author liuwei
	  * @date 2017年1月3日 下午5:30:19
	  * @return
	 */
	
    @GET
    @Path("/findLog")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Log")
    JSONObject findLogList();
    
    /**
     * 
      * @Title: downLog
      * @Description: 下载日志
      * @author liuwei
      * @date 2017年1月3日 下午5:30:37
      * @param fileName
      * @param response
     */
    
	@GET
	@Path("/downLog/{fileName}")
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DOWNLOAD ,privilegeName="down Log")
	void downLog(@PathParam(value = "fileName") String fileName, @Context HttpServletResponse response);

}
