package com.huayu.irla.manage.application.statistics;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.statistics.vo.StatisticsVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * @Description 数据统计管理权限
 * @author GuGuangting
 * @time 2018-08-30
 */
@Path("/")
@PrivilegePointor("Statistics Manage")
public interface IStatisticsManage {
	/**
	 * @Description: 查询资源数据统计
	 * @param statistics
	 * @return
	 */
	@POST
	@Path("getResStatistics")
	@Produces({MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find ResStatistics")
	JSONObject getResStatistics(StatisticsVO statistics);
	/**
	 * @Description: 查询课程数据统计
	 * @param statistics
	 * @return
	 */
	@POST
	@Path("getCourseStatistics")
	@Produces({MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find CourseStatistics")
	JSONObject getCourseStatistics(StatisticsVO statistics);
}
