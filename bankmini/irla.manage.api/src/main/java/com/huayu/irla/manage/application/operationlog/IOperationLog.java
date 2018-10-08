package com.huayu.irla.manage.application.operationlog;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.operationlog.vo.OperationLogVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;
import com.huayu.irla.privilege.manage.vo.SysLoginoutVO;


/**
 * 记录操作日志实现接口定义
 * @author lanjiagnqi
 * @date 2016/10/19
 * @version 1.0
 * */
@Path("/")
//日志管理的权限配置
@PrivilegePointor("Journal Manage")
public interface IOperationLog {
	
	/**
	  * @Description: 查询日志
	  * @author lanjiangqi
	  * @date 2016/10/19
	  * @param operationLogVO
	 */
	@POST
	@Path("/findlogList")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Journal")
	JSONObject findlogList(OperationLogVO operationLogVO) ;
	
	
	/**
	 * 
	  * @Title: findLoginList
	  * @Description: 登录日志的展示
	  * @author liuwei
	  * @date 2017年1月11日 上午11:32:29
	  * @param loginVO
	  * @return
	 */
	@POST
	@Path("/findLoginList")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find LoginLog")
	JSONObject findLoginList(SysLoginoutVO loginVO);
	
	/**
	 * 重启goaccess日志
	 * @throws Exception
	 */
	@POST
	@Path("/reStartAccessLog")
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="Restart GoAccess Log")
	void reStartAccessLog() throws Exception;
	
	@GET
	@Path("/getRealLogURL")
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="Get Real Log URL")
	String getRealLogURL(@Context HttpServletResponse response) throws Exception;
	
}
