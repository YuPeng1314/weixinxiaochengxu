/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huayu.irla.privilege.manage.service;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;

import com.huayu.irla.privilege.manage.common.UserMesCommon;
import com.huayu.irla.privilege.manage.dao.ISysUsersRepositoryDao;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;

/**
 * An authentication success strategy which can make use of the
 * {@link org.springframework.security.web.savedrequest.DefaultSavedRequest} which may have been stored in the session by the
 * {@link ExceptionTranslationFilter}. When such a request is intercepted and requires
 * authentication, the request data is stored to record the original destination before
 * the authentication process commenced, and to allow the request to be reconstructed when
 * a redirect to the same URL occurs. This class is responsible for performing the
 * redirect to the original URL if appropriate.
 * <p>
 * Following a successful authentication, it decides on the redirect destination, based on
 * the following scenarios:
 * <ul>
 * <li>
 * If the {@code alwaysUseDefaultTargetUrl} property is set to true, the
 * {@code defaultTargetUrl} will be used for the destination. Any
 * {@code DefaultSavedRequest} stored in the session will be removed.</li>
 * <li>
 * If the {@code targetUrlParameter} has been set on the request, the value will be used
 * as the destination. Any {@code DefaultSavedRequest} will again be removed.</li>
 * <li>
 * If a {@link org.springframework.security.web.savedrequest.SavedRequest} is found in the {@code RequestCache} (as set by the
 * {@link ExceptionTranslationFilter} to record the original destination before the
 * authentication process commenced), a redirect will be performed to the Url of that
 * original destination. The {@code SavedRequest} object will remain cached and be picked
 * up when the redirected request is received (See
 * <a href="{@docRoot}/org/springframework/security/web/savedrequest/SavedRequestAwareWrapper.html">SavedRequestAwareWrapper</a>).
 * </li>
 * <li>
 * If no {@link org.springframework.security.web.savedrequest.SavedRequest} is found, it will delegate to the base class.</li>
 * </ul>
 *
 * @author Luke Taylor
 * @since 3.0
 */
public class DefineURLAuthenticationSuccess extends
		SimpleUrlAuthenticationSuccessHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());
	

	/**
	 * 登录
	 */
	public static final int LOGIN_TYPE = 1;

	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private boolean contextRelative;
	
	/**
	 * 指定登录URL,如果缓存有值就取缓存，跟其它ajax请求不一样
	 */
	private String loginURL;
	
	/**
	 * 用户信息处理DAO
	 */
	@Autowired
	private ISysUsersRepositoryDao sysUsersRepositoryDao;
	
	/**
	 * mongdb对象
	 * @return
	 */
	@Autowired
	private MongoClient mongoClient;
	
	
	/**
	 * redis对象
	 */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public String getLoginURL() {
		return loginURL;
	}


	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}


	/**
	 * getter method
	 * @return the contextRelative
	 */
	
	public boolean isContextRelative() {
		return contextRelative;
	}


	/**
	 * setter method
	 * @param contextRelative the contextRelative to set
	 */
	
	public void setContextRelative(boolean contextRelative) {
		this.contextRelative = contextRelative;
	}


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {	 
			Object principal = authentication.getPrincipal();    
            if (principal instanceof SysUsersVO) {    
            	SysUsersVO user = (SysUsersVO) principal;    
                String userCode = user.getUsername();
	            String remoteAddress = UserMesCommon.getRemoteAddress(request);
	            
	            //用户信息
	            user.setLoginIp(remoteAddress);
	            
	             //更新用户情况表
	    		sysUsersRepositoryDao.updateOtherUserInfo(user);
	    		
	    		// 记录登录日志
	    		//插入mongodb数据库中
	    		Document userObj = new Document();
	    		userObj.append("type", LOGIN_TYPE);
	    		userObj.append("userIp", remoteAddress);
	    		userObj.append("userCode", userCode);
	    		userObj.append("depCode", user.getDepartmentCode());
	    		userObj.append("depName", user.getDepartmentName());
	    		userObj.append("termianl", "pc");
	    		userObj.append("plateformCode", user.getPlateformCode());
	    		userObj.append("creationDate", Calendar.getInstance().getTime());
	    		MongoDatabase mongoDB = mongoClient.getDatabase("TPLAT_DB");
	    		MongoCollection<Document> collectionOper = mongoDB.getCollection("SYS_USER_LOGINOUT_LOG");
	    		collectionOper.insertOne(userObj);
	
	    		//记录上线人员
	    		String personNumStr = stringRedisTemplate.opsForValue().get("loginNum"+user.getPlateformCode());
	    		int personNumInt = -1;
	    		try {
	    			personNumInt = Integer.valueOf(personNumStr);
	    		} catch(NumberFormatException ex) {
	    			logger.error(ex);
	    		}
	    		if(personNumInt < 1) {
	    			stringRedisTemplate.opsForValue().set("loginNum"+user.getPlateformCode(), "1");
	    		} else {
	    			try {
		    			int personNum = Integer.valueOf(personNumStr);
		    			stringRedisTemplate.opsForValue().set("loginNum"+user.getPlateformCode(), String.valueOf(++personNum));
	    			} catch(NumberFormatException ex) {
	    				logger.error(ex);
	    			}
	    		}
	    		
	    		//记录历史最高次数
	    		String personMaxNumStr = stringRedisTemplate.opsForValue().get("maxLoginNum"+user.getPlateformCode());
	    		int personMaxNumInt = -1;
	    		try {
	    			personMaxNumInt = Integer.valueOf(personMaxNumStr);
	    		} catch(NumberFormatException ex) {
	    			logger.error(ex);
	    		}
	    		if(personMaxNumInt < 1) {
	    			stringRedisTemplate.opsForValue().set("maxLoginNum"+user.getPlateformCode(), "1");
	    		} else {
	    			try {
		    			int personMaxNum = Integer.valueOf(personMaxNumStr);
		    			int personNum = Integer.valueOf(personNumStr);
		    			if(personNum > personMaxNum) {
		    				stringRedisTemplate.opsForValue().set("maxLoginNum"+user.getPlateformCode(), String.valueOf(personNum));
		    			}
	    			} catch(NumberFormatException ex) {
	    				logger.error(ex);
	    			}
	    		}
            }
			
			 //登录成功一次就刷新缓存
			 Configuration config = new Configuration();
			 config.setName("privilegeManager");
			 CacheManager cacheManager = CacheManager.newInstance(config);
			 Cache cache = cacheManager.getCache("pdSeriesLogin");
			 if(null != cache) {
				 cache.remove(authentication.getName());
			 }
		} catch(Exception ex) {
			logger.error(ex);
		}
		 
		
		//ajax请求处理的逻辑
    	if(DefaultAccessDeniedHandler.isAjaxRequest(request)) {
    		//清理session错误
    		clearAuthenticationAttributes(request);
    		//跳转到对应的URL
    		String targetUrl = "";
    		SavedRequest savedRequest = requestCache.getRequest(request, response);
    		
    		//为ajax请求就不进行跳转
			String ajaxID = request.getParameter("ajaxID");
			if(!StringUtils.isEmpty(ajaxID)) {
				targetUrl =  "";
			} else {    		
				if (savedRequest == null) {
					targetUrl = determineTargetUrl(request, response);
				} else {
					targetUrl = savedRequest.getRedirectUrl();
				}
			}
			//处理URL
			if(!StringUtils.isEmpty(targetUrl)) {
				targetUrl = calculateRedirectUrl(request.getContextPath(),targetUrl);
				requestCache.removeRequest(request, response);
			}
    		loginSuccess(response,targetUrl);
    	} else {
			SavedRequest savedRequest = requestCache.getRequest(request, response);
	
			if (savedRequest == null) {
				super.onAuthenticationSuccess(request, response, authentication);
	
				return;
			}
			String targetUrlParameter = getTargetUrlParameter();
			if (isAlwaysUseDefaultTargetUrl()
					|| (targetUrlParameter != null && StringUtils.hasText(request
							.getParameter(targetUrlParameter)))) {
				requestCache.removeRequest(request, response);
				super.onAuthenticationSuccess(request, response, authentication);
	
				return;
			}
	
			clearAuthenticationAttributes(request);
	
			// Use the DefaultSavedRequest URL
			String targetUrl = savedRequest.getRedirectUrl();
			logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
    	}
	}
	
	
	/**
	 * 
	  * calculateRedirectUrl(处理URL地址)
	  *
	  * @Title: calculateRedirectUrl
	  * @param @param contextPath
	  * @param @param url
	  * @param @return
	  * @return String
	  * @throws
	 */
	private String calculateRedirectUrl(String contextPath, String url) {
		if (!UrlUtils.isAbsoluteUrl(url)) {
			if (contextRelative) {
				return url;
			}
			else {
				return contextPath + url;
			}
		}

		// Full URL, including http(s)://

		if (!contextRelative) {
			return url;
		}

		// Calculate the relative URL from the fully qualified URL, minus the last
		// occurrence of the scheme and base context.
		url = url.substring(url.lastIndexOf("://") + 3); // strip off scheme
		url = url.substring(url.indexOf(contextPath) + contextPath.length());

		if (url.length() > 1 && url.charAt(0) == '/') {
			url = url.substring(1);
		}

		return url;
	}
	
	/**
	 * 
	  *登录成功后的跳转页面
	  *
	  * @Title: loginSuccess
	  * @Description: TODO
	  * @param @param response
	  * @return void
	  * @throws
	 */
	private void loginSuccess(HttpServletResponse response, String targetURL) {
    	String errorMsg = "success";
    		response.setCharacterEncoding("UTF-8");
    		response.setContentType("application/json; charset=utf-8");
        	Writer write = null;
        	try {
        		write = response.getWriter();
        		if(null != write) {
        			write.write("{\"errorMsg\":\""+errorMsg+"\",\"targetURL\":\""+targetURL+"\"}");
        		}
        	} catch (IOException ex) {
        		Logger.getLogger(getClass()).error(ex);
        	} finally {
        		if(null != write) {
        		  try {
        			  write.close(); 
        		  } catch(IOException ex) {
        			  
        		  }
        		}
        		
        	}
		
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
}
