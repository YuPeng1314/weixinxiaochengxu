package com.huayu.irla.mobile.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huayu.irla.core.user.vo.MobSysUsersVO;
import com.huayu.irla.privilege.manage.common.UserMesCommon;


/**
 * 
 * @ClassName: FirstLoginFilter
 * @Description: 检验用户是否首次登录
 * @author liuwei
 * @date 2017年2月13日 上午9:21:14
 *
 */
public class VerifyLoginFilter implements Filter {
	
	/**
	 * 登录URL定义
	 */
	public static final String DEFAULT_FILTER_PROCESSES_URL = "/miniInfo/login";
	
	/**
	 * redis对象
	 */
	private StringRedisTemplate stringRedisTemplate;

	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		stringRedisTemplate = (StringRedisTemplate)context.getBean(StringRedisTemplate.class);
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		//得到url
		String contextUrl = httpRequest.getServletPath();
		//得到自定义会话id
		String sessionId = httpRequest.getHeader("sessionId");
		if(StringUtils.isBlank(sessionId)) {
			sessionId = httpRequest.getParameter("sessionId");
		}
		if(StringUtils.isNotBlank(sessionId)) {
			httpRequest.setAttribute("sessionId", sessionId);
		}
		if(!(StringUtils.equals(contextUrl, "/phizAction/findPhiz")||StringUtils.equals(contextUrl, "/phizAction/findHolidayPhiz")||StringUtils.equals(contextUrl, "/phizAction/findThemePhiz"))) {
			//url为登录时
			if (!StringUtils.equals(contextUrl, DEFAULT_FILTER_PROCESSES_URL)) {
				//sessionId不能为空
				if(StringUtils.isBlank(sessionId)) {
					reLogin(httpResponse);
					return;
				} 
					
				//用户编码不能为空
				String userCode = stringRedisTemplate.opsForValue().get(sessionId);
				if(StringUtils.isBlank(userCode)) {
					reLogin(httpResponse);
					return;
				}
				
				//当权限token不为空时
				MobSysUsersVO mobSysUsersVO = UserMesCommon.getAuthorizeInfo(httpRequest);
				if(null == mobSysUsersVO) {
					reLogin(httpResponse);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	/**
	 * 重新登录
	 * @param response
	 * @throws IOException
	 */
	private void reLogin(HttpServletResponse response) throws IOException {
		//设置处理成功，状态为没有登录
		PrintWriter pw = null;
		try {
			String contentType = "application/json";  
	        response.setContentType(contentType);  
	        response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write("{\"netCode\":332}");
		} finally {
		    IOUtils.closeQuietly(pw);
		}
	}
	

	@Override
	public void destroy() {
	}

}
