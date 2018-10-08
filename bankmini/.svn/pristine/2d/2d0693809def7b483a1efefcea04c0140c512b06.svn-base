package com.huayu.irla.privilege.manage.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
	
	/**
	 * 错误页面地址
	 */
	private String errorPage;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		   boolean isAjax = DefaultAccessDeniedHandler.isAjaxRequest(request);  
	        if(isAjax){  
	        	 //若为ajax请求处理逻辑，设置为403错误 
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  
	        }else if (!response.isCommitted()) {  
	            if (errorPage != null) {  
	                // Put exception into request scope (perhaps of use to a view)  
	                request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);  
	  
	                // Set the 403 status code.  
	                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  
	  
	                // forward to error page.  
	                RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);  
	                dispatcher.forward(request, response);  
	            } else {  
	                response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());  
	            }  
	        }  
	    }  
	  
	    /** 
	     * The error page to use. Must begin with a "/" and is interpreted relative to the current context root. 
	     * 
	     * @param errorPage the dispatcher path to display 
	     * 
	     * @throws IllegalArgumentException if the argument doesn't comply with the above limitations 
	     */  
	    public void setErrorPage(String errorPage) {  
	        if ((errorPage != null) && !errorPage.startsWith("/")) {  
	            throw new IllegalArgumentException("errorPage must begin with '/'");  
	        }  
	  
	        this.errorPage = errorPage;  
	    }  
	    
	    /**
    	 * 判断ajax请求
    	 * @param request
    	 * @return
    	 */
	    public static boolean isAjaxRequest(HttpServletRequest request){
	    	boolean isAjax = (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString()));
	    	return  isAjax;
	    }
}
