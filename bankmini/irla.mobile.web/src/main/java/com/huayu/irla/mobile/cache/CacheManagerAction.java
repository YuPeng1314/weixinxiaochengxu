/**
 * @Title: CacheAction.java
 * @Package com.huayu.ietl.manage.cache.application
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月18日 上午10:45:27
 * @version V1.0
 */

package com.huayu.irla.mobile.cache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.core.util.EhcacheUtils;

import net.sf.ehcache.CacheManager;

/**
  * @ClassName: CacheAction
  * @Description: 缓存管理
  * @author luozehua
  * @date 2016年11月18日 上午10:45:27
  *
  */
@RequestMapping("/cachemobile")
@Controller
public class CacheManagerAction {
	
	
    /**
      * @Title: clearCache
      * @Description: 清空所有缓存
      * @author luozehua
      * @date 2016年11月18日 上午10:50:18
      * @return
     */
	@ResponseBody
    @RequestMapping(value = "/clearCache", method = RequestMethod.GET)
    public boolean clearCache() {
    	EhcacheUtils ehcacheUtils = EhcacheUtils.getInstance(new String[]{"mobileManager"});
	    CacheManager manager[] = ehcacheUtils.getCacheManager();
        if (manager != null) {
          for(CacheManager tmp:manager) {
          	tmp.clearAll();
          }
        }
        return true;
    }
}
