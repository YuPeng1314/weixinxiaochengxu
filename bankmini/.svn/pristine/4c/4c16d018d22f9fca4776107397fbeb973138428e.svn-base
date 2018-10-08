/**
 * @Title: EhcacheUtils.java
 * @Package com.huayu.core.util
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月17日 下午3:31:17
 * @version V1.0
 */

package com.huayu.core.util;

import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;

/**
  * @ClassName: EhcacheUtils
  * @Description: 非spring集成ehcache缓存的使用
  * @author luozehua
  * @date 2016年11月17日 下午3:31:17
  *
  */

public class EhcacheUtils {

    private static CacheManager[] manager = null;
    
    private static String[] managerName = null;
    
    private static EhcacheUtils instance = null;

    private EhcacheUtils(String[] managerName) {
    	EhcacheUtils.managerName = managerName;
    }
    
    public static EhcacheUtils getInstance(String[] managerName) {
    	if(null == managerName) {
    		Assert.notNull(managerName);
    	}
    	if(null == instance) {
    		instance = new EhcacheUtils(managerName);
    		getCacheManagers();
    	}
    	
    	return instance;
    }

    
    private static void getCacheManagers() {
		if(null!=managerName && managerName.length!=0) {
			manager = new CacheManager[managerName.length]; 
			for(int i=0; i<managerName.length; i++) {
				 Configuration config = new Configuration();
				 config.setName(managerName[i]);
				 manager[i] = CacheManager.newInstance(config);
			}
		}
    }
    
    /**
     * 获取缓存块
     * @return
     */
    public  CacheManager[] getCacheManager() {
    	if (manager == null)  {
    		return null;
    	}
    	CacheManager[] tmp = new CacheManager[manager.length];
    	System.arraycopy(manager, 0, tmp, 0, manager.length);
    	return tmp;
    }

    /**
      * @Title: get
      * @Description: 通过缓存块名和键获取缓存值
      * @author luozehua
      * @date 2016年11月17日 下午4:27:41
      * @param cacheName 缓存块名称
      * @param key 缓存键
      * @return
     */
    public  Object get(String cacheName, Object key) {
    	if(null != manager) {
    		for(int i=0; i<manager.length; i++) {
    			Cache cache = manager[i].getCache(cacheName);
    			if (cache != null) {
    	            Element element = cache.get(key);
    	            if (element != null) {
    	                return element.getObjectValue();
    	            }
    	        }
    		}
    	}
        
        return null;
    }

    /**
      * @Title: put
      * @Description: 通过缓存块名称，缓存指定建对应的值
      * @author luozehua
      * @date 2016年11月17日 下午4:28:22
      * @param cacheName 缓存块名称
      * @param key 缓存键
      * @param value 缓存值
     */
    public  void put(String cacheName, Object key, Object value) {
    	if(null != manager) {
    		for(int i=0; i<manager.length; i++) {
    			Cache cache = manager[i].getCache(cacheName);
    			if (cache != null) {
    	            cache.put(new Element(key, value));
    	        }
    		}
    	}
    }

    /**
      * @Title: remove
      * @Description: 清楚指定块中指定键的缓存值
      * @author luozehua
      * @date 2016年11月17日 下午4:29:39
      * @param cacheName 缓存块名称
      * @param key 缓存键
      * @return
     */
    public  boolean remove(String cacheName, Object key) {
    	if(null != manager) {
    		for(int i=0; i<manager.length; i++) {
    			Cache cache = manager[i].getCache(cacheName);
    			if (cache != null) {
    				return cache.remove(key);
    	        }
    		}
    	}
    	return false;
    }
    
    
    /**
     * @Title: removeAll
     * @Description: 清除所有缓存
     * @author luozehua
     * @date 2016年11月17日 下午4:29:39
     * @param cacheName 缓存块名称
     * @return
    */
   public  void removeAll(String cacheName) {
   	if(null != manager) {
   		for(int i=0; i<manager.length; i++) {
   			Cache cache = manager[i].getCache(cacheName);
   			if (cache != null) {
   				cache.removeAll();
   	        }
   		}
   	}
   }
}
