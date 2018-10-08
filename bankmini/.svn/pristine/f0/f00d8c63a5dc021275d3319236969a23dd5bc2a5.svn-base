package com.huayu.irla.privilege.manage.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class DefineLogoutHandler extends SimpleUrlLogoutSuccessHandler {
	
	@Autowired  
    private UserCache userCache; 
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if(authentication != null) {
			//清掉用户缓存
			String userName = authentication.getName();
			userCache.removeUserFromCache(userName);
		}
		
		//跳转对应页面
		super.onLogoutSuccess(request, response, authentication);
	}


	
}
