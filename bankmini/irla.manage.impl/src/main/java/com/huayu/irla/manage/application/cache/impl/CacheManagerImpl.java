/**
 * @Title: CacheManagerImpl.java
 * @Package com.huayu.ietl.manage.cache.application.impl
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月18日 上午10:55:02
 * @version V1.0
 */

package com.huayu.irla.manage.application.cache.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.huayu.core.send.HttpConnection;
import com.huayu.core.util.EhcacheUtils;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.cache.ICacheManager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
  * @ClassName: CacheManagerImpl
  * @author luozehua
  * @date 2016年11月18日 上午10:55:02
  *
  */
@Component("cacheManagerHy")
public class CacheManagerImpl implements ICacheManager {
	
	@Override
	public String remoteClearCache() {
		StringBuffer sb = new StringBuffer();
		String remoteAdd = DataDricCommon.getValueByKey("admin.remote.address");
        
		if(StringUtils.isNotBlank(remoteAdd)) {
			String[] ipAddress = remoteAdd.split("[|]");
			for(int i=0; i<ipAddress.length; i++) {
				String mesAddress = ipAddress[i].replaceAll("http:|[a-zA-Z/]|(:\\d+)", "").replace("..","");
				try {
					String hintInfo = HttpConnection.getHttpResult(ipAddress[i]+"/ws/clearCache");
	                if(!"true".equals(hintInfo)) {
	                	sb.append(mesAddress).append("刷新失败; ");
	                }
				} catch (Exception ex) {
					Logger.getLogger(this.getClass()).error(ex);
					sb.append(mesAddress).append("刷新异常; ");
				}
			}
		}
		remoteAdd = DataDricCommon.getValueByKey("mobile.remote.address");
		if(StringUtils.isNotBlank(remoteAdd)) {
			String[] ipAddress = remoteAdd.split("[|]");
			for(int i=0; i<ipAddress.length; i++) {
				String mesAddress = ipAddress[i].replaceAll("http:|[a-zA-Z/]|(:\\d+)", "").replace("..","");
				try {
					String hintInfo = HttpConnection.getHttpResult(ipAddress[i]+"/cachemobile/clearCache");
	                if(!"true".equals(hintInfo)) {
	                	sb.append(mesAddress).append("刷新失败; ");
	                }
				} catch (Exception ex) {
					Logger.getLogger(this.getClass()).error(ex);
					sb.append(mesAddress).append("刷新异常;");
				}
			}
		}
		remoteAdd = DataDricCommon.getValueByKey("exb.remote.address");
		if(StringUtils.isNotBlank(remoteAdd)) {
			String[] ipAddress = remoteAdd.split("[|]");
			for(int i=0; i<ipAddress.length; i++) {
				String mesAddress = ipAddress[i].replaceAll("http:|[a-zA-Z/]|(:\\d+)", "").replace("..","");
				try {
					String hintInfo = HttpConnection.getHttpResult(ipAddress[i]+"/cacheexb/clearCache.json");
					if(!"true".equals(hintInfo)) {
						sb.append(mesAddress).append("刷新失败; ");
					}
				} catch (Exception ex) {
					Logger.getLogger(this.getClass()).error(ex);
					sb.append(mesAddress).append("刷新异常;");
				}
			}
		}
		return sb.toString();
	}

    @Override
    public boolean clearCache() {
    	EhcacheUtils ehcacheUtils = EhcacheUtils.getInstance(new String[]{"adminManager","privilegeManager"});
        CacheManager manager[] = ehcacheUtils.getCacheManager();
        if (manager == null) {
            return false;
        }
        for(CacheManager tmp:manager) {
        	tmp.clearAll();
        }
        return true;
    }

    @Override
    public boolean clearCacheByName(String name) {
    	EhcacheUtils ehcacheUtils = EhcacheUtils.getInstance(new String[]{"adminManager","privilegeManager"});
        CacheManager[] manager = ehcacheUtils.getCacheManager();
        if (manager == null) {
            return false;
        }
        for(CacheManager tmp:manager) {
        	Cache cache = tmp.getCache(name);
        	if(null != cache) {
        		cache.removeAll();
        		return true;
        	}
        }
        return false;
    }

    @Override
    public List<String> getAllCacheName() {
    	EhcacheUtils ehcacheUtils = EhcacheUtils.getInstance(new String[]{"adminManager","privilegeManager"});
        CacheManager[] manager = ehcacheUtils.getCacheManager();
        List<String> cacheNames = new ArrayList<String>();
        if (manager != null) {
        	for(CacheManager tmp:manager) {
        		cacheNames.addAll(Arrays.asList(tmp.getCacheNames()));
        	}
        }
        
        return cacheNames;
    }
}
