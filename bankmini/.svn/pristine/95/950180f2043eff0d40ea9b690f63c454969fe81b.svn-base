package com.huayu.irla.manage.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ResourceFilter implements Filter {
	/**
	 * 文件类型
	 */
	private ApplicationContext context;

	/**
	 * 不包含的文件
	 */
	private String include;
	
	/**
	 * 不包含的页面
	 */
	private String excludePage;

	public ResourceFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        
		//保存的文件名称
		String fileHTML = null;
		String reqURL = httpRequest.getServletPath();
		String pathInfo = ((HttpServletRequest)request).getPathInfo();
		if (pathInfo != null && pathInfo.length() > 0) {
			reqURL = reqURL + pathInfo;
		}
		
		if(null != reqURL) {
			int pointPos = reqURL.lastIndexOf(".");
			
			if(pointPos>0) {
				String suffix = reqURL.substring(pointPos+1, reqURL.length());
				//根据元素后缀名过滤
				if(this.include.indexOf(suffix) == -1) {
					chain.doFilter(request, response);
					return;
				}
				
				//根据文件名称过滤
				int filePoint = reqURL.lastIndexOf("/");
				filePoint = (filePoint==-1?-1:filePoint);
				String fileName = reqURL.substring(filePoint+1, reqURL.length());
				if(this.excludePage.indexOf(fileName) > -1) {
					chain.doFilter(request, response);
					return;
				}
				fileHTML = reqURL.substring(1, reqURL.length());
			}
		}
		//文件名称为空就不做处理
		if(null==fileHTML) {
			chain.doFilter(request, response);
			return;
		}
		
		// 得到文件
		ResourceHandle resourceHandle = (ResourceHandle) context.getBean("resourceHandle");
		resourceHandle.loadResourceFile(fileHTML, (HttpServletRequest)request, (HttpServletResponse) response);

		//chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		context = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		// 得到不包含文件后缀列表
		this.include = fConfig.getInitParameter("include");
		//得到不包含文件的页面
		this.excludePage = fConfig.getInitParameter("excludePage");
	}

}
