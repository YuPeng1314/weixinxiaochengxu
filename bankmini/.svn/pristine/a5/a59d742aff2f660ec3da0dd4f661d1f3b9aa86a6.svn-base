/**
 * @Title: UserUtils.java
 * @Package com.huayu.ietl.manage.util
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年11月14日 下午7:24:46
 * @version V1.0
 */

package com.huayu.irla.manage.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import com.huayu.irla.privilege.manage.common.UserMesCommon;

/**
  * @ClassName: UserUtils
  * @Description: TODO
  * @author luozehua
  * @date 2016年11月14日 下午7:24:46
  *
  */

public class UserUtils {
    private UserUtils() {

    }

    public static String getLoginName() {
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        return UserMesCommon.getUserName(request);
    }
    
    /**
     * 取到平台标识
     * @return
     */
    public static String getplateCode() {
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        return UserMesCommon.getplateCode(request);
    }
    
    /**
     * 是否系统管理员
     * @return
     */
    public static boolean isSystemAdmin() {
    	 Message message = PhaseInterceptorChain.getCurrentMessage();
         HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
         return UserMesCommon.isSystemAdmin(request);
    }
}
