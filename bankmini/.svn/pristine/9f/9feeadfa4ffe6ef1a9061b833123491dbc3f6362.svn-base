/**
 * @Title: LoginSetting.java
 * @Package com.huayu.ietl.privilege.manage.common
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author stef-shiqing
 * @date 2016年10月25日 下午5:10:53
 * @version V1.0
 */

package com.huayu.irla.privilege.manage.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Component;

/**
  * @ClassName: LoginSetting
  * @Description: TODO
  * @author stef-shiqing
  * @date 2016年10月25日 下午5:10:53
  *
  */
@Component
public class PrivilegeControl implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	/**
	 * @throws IOException 
	 * 
	  * resourceRead(判断资源读取，没有登录就提醒)
	  * @Title: resourceRead
	  * @Description: TODO
	  * @param @param request
	  * @param @param response
	  * @return void
	  * @throws
	 */
	public static void resReadIsLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = UserMesCommon.getAuthentication(request);
		if(null == auth) {
			response.sendRedirect(request.getContextPath()+"/login/loginUser.html");
		} else {
			//如果已登录，看是否有权限，若没有权限则跳转到提示没有权限页面
			//已登录的用户默认有所有的视频的观看权限
			
			boolean isWatch = true;
			//TODO 判断是否有权限
			if(!isWatch) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,"该视频您没有查看权限");
			}
			
		}
	}
	
	/**
	 * 通过URL获取到是否有权限,前端
	 * @param uri
	 * @return
	 */
	public static boolean isAuthorize(String uri, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		//系统管理员就全部显示
		if(UserMesCommon.isSystemAdmin(request)) {
			return true;
		}
		return getPrivilgeEvaluator().isAllowed(contextPath, uri, null, currentUser);
	}
	
	/**
	 * 得到权限处理类
	 * @return
	 */
	private static WebInvocationPrivilegeEvaluator getPrivilgeEvaluator() {
		WebInvocationPrivilegeEvaluator evaluator = (WebInvocationPrivilegeEvaluator)applicationContext.getBean("webInvocationPrivilegeEvaluator");
		return evaluator;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		PrivilegeControl.applicationContext = applicationContext;
	}
}
