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
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

/**
 * 定义session失效的情况
 * @author stef-shiqing
 *
 */
public final class DefineRedirectSessionExpiredStrategy implements SessionInformationExpiredStrategy {
	private final Log logger = LogFactory.getLog(getClass());
	private final String destinationUrl;
	private final RedirectStrategy redirectStrategy;
	private boolean redirect = false;

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public DefineRedirectSessionExpiredStrategy(String invalidSessionUrl) {
		this(invalidSessionUrl, new DefaultRedirectStrategy());
	}

	public DefineRedirectSessionExpiredStrategy(String invalidSessionUrl, RedirectStrategy redirectStrategy) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl),
				"url must start with '/' or with 'http(s)'");
		this.destinationUrl=invalidSessionUrl;
		this.redirectStrategy=redirectStrategy;
	}

	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
		if(redirect) {
			logger.debug("Redirecting to '" + destinationUrl + "'");
			redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), destinationUrl);
		} else {
			HttpServletResponse response = event.getResponse();
			HttpServletRequest request = event.getRequest();
			String contextPath = request.getContextPath();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			try {
				pw.write("<script type='text/javascript' src='" + contextPath + "/pluginjs/jquery-2.1.1.min.js'></script>");
				pw.write("<script type='text/javascript' src='" + contextPath + "/pluginjs/bootstrap.min.js'></script>");
				pw.write("<script type='text/javascript' src='" + contextPath + "/pluginjs/bootbox.min.js'></script>");
				pw.write("<link rel='stylesheet' type='text/css' href='" + contextPath + "/plugincss/bootstrap.min.css'/>");
				StringBuilder sb = new StringBuilder();
				sb.append("<script type='text/javascript' language='javascript'>$(document).ready(function(){bootbox.setLocale('zh_CN');");
				sb.append("bootbox.alert({size : 'large',");
				sb.append("title : '提示信息',");
				sb.append("message : '<div class=\"text-center\"><font color=\"blue\">您好，您的账号在其它地方登录了,若不是本人操作，请修改密码</font></div>',");
				sb.append("callback : function() {window.location.reload();}});");
				sb.append("});</script>");
				pw.write(sb.toString());
			} finally {
				pw.close();
			}
			return;
		}
	}
}
