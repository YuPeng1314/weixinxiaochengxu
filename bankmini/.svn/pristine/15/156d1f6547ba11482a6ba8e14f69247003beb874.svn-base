package com.huayu.irla.manage.application.menu;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.menu.vo.NodeMenuVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * @author luozehua
 *
 * @time 2016年8月24日-下午5:05:19
 */
@Path("/")
//菜单管理的权限控制
@PrivilegePointor("Menus Manage")
public interface IMenu {

    @POST
    @Path("/getMenus")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Menu")
    JSONObject findMenus(NodeMenuVO menu);

    /**
      * @Title: impromntMenuItem
      * @Description: 添加或修改
      * @author luozehua
      * @date 2016年11月9日 下午8:04:22
      * @param menu
      * @return
     */
    @POST
    @Path("/addMenuItem")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Menu")
    boolean impromntMenuItem(NodeMenuVO menu);

    @POST
    @Path("/deleteMenuItem/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Menu")
    boolean deleteMenuItem(@PathParam(value = "id") String ids);

    @GET
    @Path("/updateMenuItem/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Menu")
    NodeMenuVO updateUI(@PathParam(value = "id") String id);

    /**
      * @Title: getAdminAuth
      * @Description: 获取后台的权限列表，以便初始化权限选择下拉框
      * @author luozehua
      * @date 2016年11月8日 上午11:02:37
      * @return
     */
    @GET
    @Path("/getAdminAuth")
    List<String> getAdminAuthName();

}
