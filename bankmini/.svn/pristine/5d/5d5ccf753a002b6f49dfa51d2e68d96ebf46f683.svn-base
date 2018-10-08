/**
 * @Title: DefineLoginUrlAuthenticationEntryPoint.java
 * @Package com.huayu.ietl.privilege.manage.service
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author stef-shiqing
 * @date 2016年10月19日 下午12:26:52
 * @version V1.0
 */

package com.huayu.irla.privilege.manage.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

/**
  * 用户请求没有登录前的逻辑
  * @ClassName: DefineLoginUrlAuthenticationEntryPoint
  * @Description:
  * @author stef-shiqing
  * @date 2016年10月19日 下午12:26:52
  *
  */

public class DefineLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {


	private static final Log logger = LogFactory.getLog(LoginUrlAuthenticationEntryPoint.class);

	// ~ Instance fields
	// ================================================================================================

	private PortMapper portMapper = new PortMapperImpl();

	private PortResolver portResolver = new PortResolverImpl();

	private String loginFormUrl;

	private boolean forceHttps = false;

	private boolean useForward = false;

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	//请求缓存
	//private RequestCache requestCache = new HttpSessionRequestCache();

	/**
	 * Allows subclasses to modify the login form URL that should be applicable for a
	 * given request.
	 *
	 * @param request the request
	 * @param response the response
	 * @param exception the exception
	 * @return the URL (cannot be null or empty; defaults to {@link #getLoginFormUrl()})
	 */
	protected String determineUrlToUseForThisRequest(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception) {

		return getLoginFormUrl();
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		//ajax请求处理的逻辑
		if(DefaultAccessDeniedHandler.isAjaxRequest(request)) {
			PrintWriter pw = null;
			try {
				String contentType = "application/json";  
		        response.setContentType(contentType);  
		        response.setCharacterEncoding("UTF-8");
				pw = response.getWriter();
				pw.write("{\"success\":false,\"isLoginRequired\":true}");
			} finally {
			    IOUtils.closeQuietly(pw);
			}
			
		} else { //非ajax请求处理的逻辑
			String redirectUrl = null;
			if (useForward) {
				if (forceHttps && "http".equals(request.getScheme())) {
					redirectUrl = buildHttpsRedirectUrlForRequest(request);
				}
	
				if (redirectUrl == null) {
					String loginForm = determineUrlToUseForThisRequest(request, response, authException);
					if (logger.isDebugEnabled()) {
						logger.debug("Server side forward to: " + loginForm);
					}
					RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
					dispatcher.forward(request, response);
	
					return;
				}
			}
			else {
				//转向登录页面
				redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
			}
	        
//			//拼装URL,再后面加上跳转的url
//			SavedRequest savedRequest = requestCache.getRequest(request, response);
//			String paramURL = "";
//			if(null != savedRequest) {
//				paramURL = savedRequest.getRedirectUrl();
//				if(!StringUtils.isEmpty(paramURL)) {
//					//进行编码
//					paramURL = URLEncoder.encode(paramURL, "UTF-8");
//				}
//			}
//			//拼装整个URL
//			if(!StringUtils.isEmpty(paramURL)) {
//				redirectUrl += "?url="+paramURL;
//			}
			redirectStrategy.sendRedirect(request, response, redirectUrl);
		}
	}

	protected String buildRedirectUrlToLoginPage(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException) {

		String loginForm = determineUrlToUseForThisRequest(request, response,
				authException);

		if (UrlUtils.isAbsoluteUrl(loginForm)) {
			return loginForm;
		}

		int serverPort = portResolver.getServerPort(request);
		String scheme = request.getScheme();

		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

		urlBuilder.setScheme(scheme);
		urlBuilder.setServerName(request.getServerName());
		urlBuilder.setPort(serverPort);
		urlBuilder.setContextPath(request.getContextPath());
		urlBuilder.setPathInfo(loginForm);

		if (forceHttps && "http".equals(scheme)) {
			Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));

			if (httpsPort != null) {
				// Overwrite scheme and port in the redirect URL
				urlBuilder.setScheme("https");
				urlBuilder.setPort(httpsPort.intValue());
			}
			else {
				logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port "
						+ serverPort);
			}
		}

		return urlBuilder.getUrl();
	}

	/**
	 * Builds a URL to redirect the supplied request to HTTPS. Used to redirect the
	 * current request to HTTPS, before doing a forward to the login page.
	 */
	protected String buildHttpsRedirectUrlForRequest(HttpServletRequest request)
			throws IOException, ServletException {

		int serverPort = portResolver.getServerPort(request);
		Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));

		if (httpsPort != null) {
			RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
			urlBuilder.setScheme("https");
			urlBuilder.setServerName(request.getServerName());
			urlBuilder.setPort(httpsPort.intValue());
			urlBuilder.setContextPath(request.getContextPath());
			urlBuilder.setServletPath(request.getServletPath());
			urlBuilder.setPathInfo(request.getPathInfo());
			urlBuilder.setQuery(request.getQueryString());

			return urlBuilder.getUrl();
		}

		// Fall through to server-side forward with warning message
		logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port "
				+ serverPort);

		return null;
	}

	/**
	 * Set to true to force login form access to be via https. If this value is true (the
	 * default is false), and the incoming request for the protected resource which
	 * triggered the interceptor was not already <code>https</code>, then the client will
	 * first be redirected to an https URL, even if <tt>serverSideRedirect</tt> is set to
	 * <tt>true</tt>.
	 */
	public void setForceHttps(boolean forceHttps) {
		this.forceHttps = forceHttps;
	}

	protected boolean isForceHttps() {
		return forceHttps;
	}

	public String getLoginFormUrl() {
		return loginFormUrl;
	}
	
	public void setLoginFormUrl(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	public void setPortMapper(PortMapper portMapper) {
		Assert.notNull(portMapper, "portMapper cannot be null");
		this.portMapper = portMapper;
	}

	protected PortMapper getPortMapper() {
		return portMapper;
	}

	public void setPortResolver(PortResolver portResolver) {
		Assert.notNull(portResolver, "portResolver cannot be null");
		this.portResolver = portResolver;
	}

	protected PortResolver getPortResolver() {
		return portResolver;
	}

	/**
	 * Tells if we are to do a forward to the {@code loginFormUrl} using the
	 * {@code RequestDispatcher}, instead of a 302 redirect.
	 *
	 * @param useForward true if a forward to the login page should be used. Must be false
	 * (the default) if {@code loginFormUrl} is set to an absolute value.
	 */
	public void setUseForward(boolean useForward) {
		this.useForward = useForward;
	}

	protected boolean isUseForward() {
		return useForward;
	}

}
