package com.huayu.irla.mobile.utils;

import com.huayu.irla.core.service.impl.datadict.DataDricCommon;

/**
 * 
  * @ClassName: CommonUtils
  * @Description: 取得数据字典工具类
  * @author liuwei
  * @date 2018年6月27日 上午11:08:41
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

public class CommonUtils {

    private CommonUtils() {
    }

    public static String getNginxPath() {
        String nginxIP = DataDricCommon.getValueByKey("nginx.ip");
        String nginxPort = DataDricCommon.getValueByKey("nginx.port");
        return nginxIP + ":" + nginxPort;
    }
    
    public static String getResFile() {
        String resUrl = DataDricCommon.getValueByKey("res.file");
        return resUrl;
    }
    
    public static String getVideoFile() {
        String videoUrl = DataDricCommon.getValueByKey("video.file");
        return videoUrl ;
    }
    
    public static String getNginxPathIpPortRes() {
        String nginxIP = DataDricCommon.getValueByKey("img.http.ip");
        return nginxIP ;
    }
    
    public static String getPhizFile() {
        String phizUrl = DataDricCommon.getValueByKey("phiz.file","/emoirla/");
        return phizUrl;
    }

}
