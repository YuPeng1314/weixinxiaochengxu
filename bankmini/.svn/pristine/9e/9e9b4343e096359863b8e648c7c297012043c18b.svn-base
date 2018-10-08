package com.huayu.irla.manage.application.carouselManage;

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
import com.huayu.irla.core.carousel.vo.CarouselVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 轮播图管理权限
 * @author GuGuangting
 * @time 2018年6月28日-pm5:05:19
 */
@Path("/")
//权限配置
@PrivilegePointor("Carousel Manage")
public interface IcarouselManage {
	/**
	 * @Description: 查询
	 * @param carousel
	 * @return
	 */
	@POST
	@Path("findCarouse")
	@Produces({MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Carousel")
	JSONObject findCarouse(CarouselVO carousel);
	
	/**
	 * 更新轮播图
	 * */
	@POST
	@Path("/updatePageImg")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Carousel")
	boolean updateCarousel(CarouselVO carousel);
	
	/**
	 * @Description: 删除轮播
	 * @param id
	 */
	@POST
	@Path("/deletePagebyId")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="delete Carousel")
    boolean deletePagebyId(CarouselVO carousel);
	
	/**
     * 增加动态图片
     */
    @POST
    @Path("/addhomePageImg")
    @Consumes({ MediaType.MULTIPART_FORM_DATA})
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Carousel")
    boolean addCarousel(@Multipart(value = "files") List<Attachment> attachments,@FormParam("") CarouselVO carousel);
    
    /**
     * 根据ID获取记录
     * */
	@GET
	@Path("/getHomePageInfo/{homepage_id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Carousel")
	CarouselVO getCarouselInfo(@PathParam (value = "homepage_id")String homepage_id);
	
	/**
	 * @Description: 判断图片名称是否重复
	 * @param homePage_name
	 */
    @POST
    @Path("/checkHomePageName")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    boolean checkName(CarouselVO carousel);
    
	/**
	 * @Description: 发布未发布切换
	 * @param ID state 状态
	 */
	@GET
	@Path("/validPublish/{id}/{state}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="Publish Carousel")
	boolean validPublish(@PathParam(value = "id") Integer id, @PathParam(value = "state") String state);
	
}
