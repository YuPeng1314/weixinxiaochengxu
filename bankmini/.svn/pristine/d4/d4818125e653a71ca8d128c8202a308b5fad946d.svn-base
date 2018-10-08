package com.huayu.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
* @author 作者 fzh: 
* @version 创建时间：2016年11月10日 下午4:20:25 
* 类说明 
*/
public class MapCacheManager {

    private final static Log log = LogFactory.getLog(MapCacheManager.class);

    private volatile long updateTime = 0L;// 更新缓存时记录的时间  

    public static volatile boolean updateFlag = true;// 正在更新时的阀门，为false时表示当前没有更新缓存，为true时表示当前正在更新缓存  

    private volatile static MapCacheManager mapCacheObject;// 缓存实例对象  

    private static Map<String, String> cacheMap = new ConcurrentHashMap<String, String>();// 缓存容器  

    public volatile static String copyuuid = "";

    private MapCacheManager() {
        MapCacheManager.LoadCache(copyuuid);// 加载缓存  
        updateTime = System.currentTimeMillis();// 缓存更新时间  

    }

    /** 
     * 采用单例模式获取缓存对象实例 
     *  
     * @return 
     */
    public static MapCacheManager getInstance() {
        if (null == mapCacheObject) {
            synchronized (MapCacheManager.class) {
                if (null == mapCacheObject) {
                    mapCacheObject = new MapCacheManager();
                }
            }
        }
        return mapCacheObject;
    }

    /** 
     * 装载缓存 
     */
    public static void LoadCache(String uuid) {

        updateFlag = true;// 正在更新  

        /********** 数据处理，将数据放入cacheMap缓存中 **begin ******/
        cacheMap.put("uukey", uuid);
        /********** 数据处理，将数据放入cacheMap缓存中 ***end *******/
        copyuuid = uuid;
        updateFlag = false;// 更新已完成  

    }

    /**
     * check Cache UUID
     */
    public static boolean checkUUID(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        String ckuuid = cacheMap.get(key);
        try {
            //cacheMap.clear();   //清除
            //copyuuid="";
        } catch (Exception e) {
            return false;
        }
        if (null == ckuuid)
            return false;

        return true;
    }

    /** 
     * 返回缓存对象 
     *  
     * @return 
     */
    public Map<String, String> getMapCache() {

        long currentTime = System.currentTimeMillis();

        if (MapCacheManager.updateFlag) {// 前缓存正在更新  
            log.info("cache is Instance .....");
            return null;

        }

        if (this.IsTimeOut(currentTime)) {// 如果当前缓存正在更新或者缓存超出时限，需重新加载  
            synchronized (this) {
                this.ReLoadCache();
                this.updateTime = currentTime;
            }
        }

        return MapCacheManager.cacheMap;
    }

    private boolean IsTimeOut(long currentTime) {

        return ((currentTime - this.updateTime) > 10000);// 超过时限，超时  
    }

    /** 
     * 获取缓存项大小 
     * @return 
     */
    public int getCacheSize() {
        return cacheMap.size();
    }

    /** 
     * 获取更新时间 
     * @return 
     */
    public long getUpdateTime() {
        return this.updateTime;
    }

    /** 
     * 获取更新标志 
     * @return 
     */
    public boolean getUpdateFlag() {
        return MapCacheManager.updateFlag;
    }

    /** 
     * 重新装载 
     */
    public void ReLoadCache() {
        // this.cacheMap.clear();  
        //this.LoadCache(copyuuid);  
    }

}
