package com.huayu.irla.manage.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 对request请求的内容进行UTF-8编码
 * 
 * @author stef-shiqing
 *
 */
public class EncodingFilter implements Filter {
    protected FilterConfig filterConfig;

    private String targetEncoding = "UTF-8";

    public EncodingFilter() {
    }

    /**
     * init
     * 
     * @param filterConfig
     *            FilterConfig
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        this.targetEncoding = filterConfig.getInitParameter("encoding");
    }

    /**
     * doFilter
     * 
     * @param servletRequest
     *            ServletRequest
     * @param servletResponse
     *            ServletResponse
     * @param filterChain
     *            FilterChain
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws UnsupportedEncodingException, ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding(targetEncoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * destroy
     */
    public void destroy() {
        this.filterConfig = null;
    }

}