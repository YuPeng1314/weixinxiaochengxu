/**
 * @Title: CacheAction.java
 * @Package com.huayu.irla.manage.cache.application
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月18日 上午10:45:27
 * @version V1.0
 */

package com.huayu.irla.manage.application.cache;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
  * @ClassName: CacheAction
  * @Description: 缓存管理
  * @author luozehua
  * @date 2016年11月18日 上午10:45:27
  *
  */
@Path("/")
//缓存管理的权限控制
@PrivilegePointor("Cache Manage")
public interface ICacheManager {
	
	@GET
	@Path("/remoteClearCache")
	@Produces({ MediaType.APPLICATION_JSON })
	@PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="clear Remote Cache")
	public String remoteClearCache();
	
    /**
      * @Title: clearCache
      * @Description: 清空所有缓存
      * @author luozehua
      * @date 2016年11月18日 上午10:50:18
      * @return
     */
    @GET
    @Path("/clearCache")
    @Produces({ MediaType.APPLICATION_JSON })
    boolean clearCache();

    /**
      * @Title: clearCacheByName
      * @Description:  根据缓存块情况缓存
      * @author luozehua
      * @date 2016年11月18日 上午10:50:52
      * @param name
      * @return
     */
    @GET
    @Path("/clearCacheByName/{name}")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_DELETE ,privilegeName="clear CacheByName")
    boolean clearCacheByName(@PathParam("name") String name);

    @GET
    @Path("/getCacheNameList")
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find Cache")
    List<String> getAllCacheName();
}
