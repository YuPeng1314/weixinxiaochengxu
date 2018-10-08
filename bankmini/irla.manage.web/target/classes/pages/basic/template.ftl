<!DOCTYPE HTML>
<html>
    <head>
    	<link rel="shortcut icon" href="/ietlAdmin/img/fu.ico" type="image/x-icon" />
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>小程序后台</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="renderer" content="webkit">
        <link href="/ietlAdmin/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/template.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/bootstrap-treeview.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/bootstrap-table.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/fileinput.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/bootstrapValidator.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/bootstrap-switch.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/bootstrap-select.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/metroStyle.css" type="text/css" rel="stylesheet"/>
  		<link href="/ietlAdmin/css/bootstrap.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>
        <link href="/ietlAdmin/css/login-ht.css" rel="stylesheet" type="text/css">
        <link href="/ietlAdmin/plugin/zoom/zoom.css" rel="stylesheet" type="text/css">

       <script src="/ietlAdmin/plugin/jquery.min.js"></script>
	   <script src="/ietlAdmin/plugin/bootstrap.min.js"></script>
	   <script src="/ietlAdmin/plugin/bootstrap-treeview.min.js"></script>
	   <script src="/ietlAdmin/plugin/bootstrap-table.min.js"></script>
	   <script src="/ietlAdmin/plugin/bootstrap-table-zh-CN.min.js"></script>
	   <script src="/ietlAdmin/plugin/bootbox.min.js"></script>
	   <script src="/ietlAdmin/plugin/jquery.formFill.js"></script>
	   <script src="/ietlAdmin/plugin/jquery.form.js"></script>
	   <script src="/ietlAdmin/plugin/fileinput.min.js"></script>
	   <script src="/ietlAdmin/plugin/zh.js"></script>
	   <script src="/ietlAdmin/plugin/bootstrapValidator.min.js"></script>
       <script src="/ietlAdmin/plugin/bootstrap-switch.min.js"></script> 
       <script src="/ietlAdmin/plugin/bootstrap-select.min.js"></script> 
       <script src="/ietlAdmin/plugin/defaults-zh_CN.min.js"></script> 
	   <script src="/ietlAdmin/plugin/bootstrap-datetimepicker.js"></script>
	   <script src="/ietlAdmin/plugin/bootstrap-datetimepicker.zh-CN.js"></script>
	   <script src="/ietlAdmin/plugin/jquery.ztree.core.min.js"></script>
	   <script src="/ietlAdmin/plugin/jquery.ztree.excheck.min.js"></script>
	   <script src="/ietlAdmin/plugin/jquery.cityselect.js"></script>
	   <script src="/ietlAdmin/plugin/bootstrap-tab.js"></script>
	   
	   <script src="/ietlAdmin/plugin/pdfobject.min.js"></script>
	   <script src="/ietlAdmin/plugin/jquery.media.js"></script>
	   <script src="/ietlAdmin/plugin/zoom/zoom.js"></script>
	   <script src="/ietlAdmin/plugin/layer.js"></script>
	   
		
	   <script src="/ietlAdmin/js/common.js"></script>
	   <script src="/ietlAdmin/ckeditor/ckeditor.js"></script>  
	   <script src="/ietlAdmin/js/privilege/privilege.js"></script> 
	   
	   <script type="text/javascript">
	   	(function($) {
		    // 备份jquery的ajax方法
		    var _ajax = $.ajax;
		
		    // 重写jquery的ajax方法
		    $.ajax = function(opt) {
		        // 备份opt中error和success方法
		        var fn = {
		            error : function(XMLHttpRequest, textStatus, errorThrown) {
		            },
		            success : function(data, textStatus, xhr) {
		            }
		        };
		        if (opt.error) {
		            fn.error = opt.error;
		        }
		        if (opt.success) {
		            fn.success = opt.success;
		        }
		
		        // 扩展增强处理
		        var _opt = $.extend(opt, {
		            error : function(XMLHttpRequest, textStatus, errorThrown) {
		                // 错误方法增强处理
		                fn.error(XMLHttpRequest, textStatus, errorThrown);
		            },
		            success : function(data, textStatus, xhr) {
		                // 成功回调方法增强处理
		                if(data){
		                    if (!data.success && data.isLoginRequired) {
		                        showLoginWindow();
		                    } else {
		                        fn.success(data, textStatus, xhr);
		                    }
		                } else {
		                        fn.success(data, textStatus, xhr);
		                }
		
		            }
		        });
		        _ajax(_opt);
		    };
		})(jQuery);
		
         //得到登录页面 		
		 function showLoginWindow() {
		 	if($(".bg-boxajx").css("display")=='none') {
		    	$(".login_cover").show();
		    	$(".login_cover").css('z-index','8810');
				$(".login_cover").css('width','100%');
				$(".login_cover").css('height','100%');
				$(".bg-boxajx").css('z-index','8890');
				$(".bg-boxajx").show();
		    	refreshCaptcha();
		    }
		 }
	   </script>
	   
	   <script type="text/javascript">
	  		$(function() {
				var a = $("a.suba");
				a.each(function(i) {
					var flag = ($(a[i]).attr('href')).indexOf("${htmlUrl}");
					if (flag != -1) {
						$(a[i]).parent().parent().attr("class", "nav nav-list collapse secondmenu in");
						$(a[i]).css("backgroundColor","#EEEEEE");
					}
				});
			});
	   </script>   
    </head>
    
    <body style="margin:0; padding:0">
     
      <div class="navbar navbar-duomi navbar-static-top" role="navigation" >
        <div class="container-fluid"  style="height:80px">
            <div  class="navbar-header" style="padding-top:20px">
                <a class="" href="/ietlAdmin/pages/basic/basicPage.html" id="logo"> <img src="/ietlAdmin/img/logo-white.png" style="height: 40px"></a>
            </div>
            <div style="float:right;padding-top:30px;">欢迎登录 :<font style="font-size:15px;font-weight:bold;color:#1d1b1b;">${userName}</font>  [<a style="color:#a94442;text-decoration:underline;" href="/ietlAdmin/logout">退出</a>]</div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2" >
             
			   <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
				<li class="active">
	            <a href="/ietlAdmin/pages/basic/basicPage.html">
	            <i class="glyphicon glyphicon-th-large"></i>
	                       首页	
	            </a>
	            </li>
				<!-- 递归  宏定义 -->
				<#macro bpTree children> 
				<#if children?? && children?size gt 0> 
				<#list children as child> 
				<#if (child.children)?? && child.children?size gt 0>
				<li>
				<a href="#target_${child_index}" class="nav-header collapsed" data-toggle="collapse">
					 <i class="${(child.mIcon=='')?string('glyphicon glyphicon-globe',child.mIcon) }" aria-hidden="true"></i>
					 <span>${child.mCname}</span> 
					 <i class="pull-right glyphicon glyphicon-chevron-down" aria-hidden="true"></i>
				</a>
					<ul id="target_${child_index}" class="nav nav-list collapse secondmenu"><@bpTree children=child.children /></ul>
				</li> 
				<#else>
				<li>
				    <#if child.mBlank=='1'>
				       <#if child.mUrl?? && child.mUrl!=''>
						 <a href="/ietlAdmin${child.mUrl}"  target="_blank" class="suba">
					   <#else>
					      <a href="/ietlAdmin/pages/basic/basicPage.html"  target="_blank" class="suba">
					   </#if>
					<#else>
						<#if child.mUrl?? && child.mUrl!=''>
						 <a href="/ietlAdmin${child.mUrl}" class="suba">
					   <#else>
					      <a href="/ietlAdmin/pages/basic/basicPage.html" class="suba">
					   </#if>
					</#if>
					<i class="${ (child.mIcon=='')?string('glyphicon glyphicon-globe',child.mIcon) }" aria-hidden="true"></i>
						 ${child.mCname}
				 	</a>
				</li> 
				</#if> 
				</#list> 
				</#if>
				</#macro>
				<!-- 调用宏 生成递归树 -->
				<@bpTree children=treeMenu />
			</ul> 
			                
                
            </div>
            
            <div class="col-md-10">
	            <div class="main_content">
	           		${content}
				</div>
            </div>
        </div>
      </div>  
        <!--
          <div class="center-box" style="display:none;position:absolute;left:50%;">
            <input type="hidden" id="ajaxID" value="ajaxReq"/>
			<form id="loginForm" method="post">
  			<ul class="content-box">
  			    <li style="text-align:left"><div class="errorArea" style="margin:0px;"></div></li>
  				<li style="margin-top:0px;"><input id="username" type="text" name="username" placeholder="用户名..."></li>
  				<li><input id="password" type="password" name="password" placeholder="输入密码..."></li>
  				<li class="yz"><input type="text" placeholder="输入验证码" name="j_captcha" id="captcha" style="width:100px"> <img id="captchaImg"  style= "vertical-align:middle"/><a href="javascript:refreshCaptcha()" style="color:black;">换一张</a> </li>
  				<li class="login-02"><input type="submit" style="background:black;" value="登录"/></li>
  			</ul>
  			</form>
		</div>
		-->
		
	<!-- 增加一个掩盖层 -->
	<div class="login_cover" style="display:none; position: fixed; left: 0px; top: 0px; opacity: 0.5; background: rgb(0, 0, 0);"></div>
		
		
	<div class="bg-boxajx">
	  <input type="hidden" id="ajaxID" value="ajaxReq"/>
	   <form id="loginForm" method="post">
		<ul class="tm-cen" style="background-color:rgba(255,255,255,.8);">
				<img class="img_cen" src="/ietlAdmin/img/logo-red.png">
				 <li><div class="errorArea"></div></li>
				<li class="user">
					<input id="username" type="text" name="username" placeholder="用户名">
					<i></i>
					<div class="clearfix"></div>
				</li>
				<li class="password">
					<input id="password" type="password" name="password" placeholder="输入密码">
					<i></i>
					<div class="clearfix"></div>
				</li>
				<li class="yz">
				     <input style="text-indent:0px;width:110px;" type="text" placeholder="输入验证码" name="j_captcha" id="captcha"> 
				     
				       <img id="captchaImg" style="vertical-align: middle; display: inline;" src="/ietlAdmin/jcaptcha.jpg?76">
				         <a href="javascript:refreshCaptcha()" style="color:black;text-decoration:underline;">换一张</a>
				</li>
				<li class="remember-forget">
					<div class="clearfix"></div>
				</li>
				<li class="login">
					<input type="submit" value="登录" style="margin-right:50px;text-indent:0px;height:40px;">
				</li>
				
			</ul>
	   </form>
	</div>
    
    <!-- 
	<footer>
	  <div class="container">
	  	<div class="row navbar navbar-static-bottom" role="navigation">
	      <div class="col-md-12 text-right"><h4>www.huayu.com©Company 2015</h4></div>
	    </div>
	  </div>
	</footer>
 -->
    </body>
</html>
