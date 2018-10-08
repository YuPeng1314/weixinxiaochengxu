<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="shortcut icon" href="/ietlAdmin/img/fu.ico" type="image/x-icon" />
		<meta charset="utf-8">
		<meta name="renderer" content="webkit">
		<title>小程序后台</title>
		<link href="${pageContext.request.contextPath}/ssl/css/login-ht.css" rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath}/ssl/js/jquery-1.10.2.min.js"></script>
		<script src="${pageContext.request.contextPath}/ssl/js/privilege.js"></script>
    	<script type="text/javascript">
    		$(function() {
    			refreshCaptcha();
    			$("#password").val("");
    		})
    	</script>
     </head>

	<body>
	
	<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath"/>

	<div class="bg-box">		
		<form:form id="loginForm" method="post">
		<ul class="tm-cen">
			<img class="img_cen" src="${pageContext.request.contextPath}/ssl/img/logo-red.png" >
			 <li><div class="errorArea"></div></li>
				<li class="user">
					<input id="username" type="text" name="username" placeholder="用户名"/>
					<i></i>
					<div class="clearfix"></div>
				</li>
				<li class="password">
					<input id="password" type="text" name="password"  placeholder="输入密码" onfocus="this.type='password'" autocomplete="off"/>
					<i></i>
					<div class="clearfix"></div>
				</li>
				<li class="yz">
				     <input style="text-indent:0px;width:100px;" type="text" placeholder="输入验证码" name="j_captcha" id="captcha" style="width:100px"/> 
				     
				       <img id="captchaImg"  style= "vertical-align:middle"  src= "<c:url value='/jcaptcha.jpg'/>"/>
				         <a style="color:black;" href="javascript:refreshCaptcha()" >换一张</a>
				</li>
				<!-- 
				<li class="remember-forget">
					<input id="remember-me" name="remember-me" type="checkbox" style="width:20px;height:15px;"/>
					 <label style="margin-right:175px;">记住密码</label>
					 <a href="#" class="right">忘记密码？</a>  
					<div class="clearfix"></div>
				</li> 
				-->
				<!-- <li class="login">
					<button type="submit"> 登 录</button>
				</li> -->
				<li style="position:relative;">
					<input type="submit" value="登&nbsp;录" style="background:black;border:none;height:40px;line-height: 40px;text-align: center;color:white;margin:0;padding: 0;text-indent:0;">
					<div class="loginImage"></div>
				</li>
			</ul>
		</form:form>
	</div>	
  </body>
</html>
