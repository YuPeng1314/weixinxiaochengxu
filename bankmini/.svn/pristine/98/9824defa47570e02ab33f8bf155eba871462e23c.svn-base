 $(function() {
	  //检查要提交的修改密码表单       
      $("#loginForm").submit(function() {
    	 //用户名验证
    	 var userVal = $("#username").val();
    	 if(userVal.trim()=="") {
    		$(".errorArea").text("用户名输入错误");
 			$("#username").css({"border-color":"red","border-width":"2px"});
 			$("#username").focus().select();
 			return false; 
    	 }
    	 
    	 
    	 //密码验证
    	 var passVal = $("#password").val();
    	 if(passVal.trim()=="") {
    		$(".errorArea").text("密码输入错误");
  			$("#password").css({"border-color":"red","border-width":"2px"});
  			$("#password").focus().select();
  			return false;  
    	 }

         var captchaVal = $("#captcha").val();
         if(captchaVal.trim()=="") {
        	 $(".errorArea").text("请输入验证码");
 			$("#captcha").css({"border-color":"red","border-width":"2px"});
 			$("#captcha").focus().select();
  			return false; 
     	 }
       //用户密码验证
       var remVal = $("#remember-me:checked").val();
       //得到真正ajax请求的URL标识
       var ajaxID = $("#ajaxID").val();
       //得到请求类型进行判断
    	$.ajax({
 			//dataType:'json',	
 			type: 'POST',
 			//url: pathContext+"/login/validate?username="+userVal+"&password="+passVal+"&j_captcha="+captchaVal+"&remember-me="+remVal,
 			url: "/ietlAdmin/login/validate",
 			data:{
				"username":userVal,
				"password":passVal,
				"j_captcha":captchaVal,
				"remember-me":remVal,
				"ajaxID":ajaxID
			},	 			
 			cache: false,
 			async:false,
 			success:function(json,status, xhr){
 		 		if(json.errorMsg == 'validateError') {
 		 			$(".errorArea").text("验证码输入错误");
 		 			$("#captcha").css({"border-color":"red","border-width":"2px"});
 		 			$("#captcha").focus().select();
 		 			refreshCaptcha();
 		 			return;
 		 		} 
 		 		
 		 		if(json.errorMsg == "lockedUser") {
 		 			$(".errorArea").text("密码错误次数超过5次，将被锁30分钟");
 		 			refreshCaptcha();
 		 			$("#captcha").val("");
		 			return;
 			    }
 		 		
 		 		//var tpObj = JSON.parse(json);
 		 		if(json.errorMsg != "success") {
 		 			var loginNum = xhr.getResponseHeader("login-num");
 		 			if(null!=loginNum && loginNum>2) {
 		 				$(".errorArea").text("密码输入错误"+loginNum +"次，超过5次，用户被锁");
 		 			} else {
 		 				$(".errorArea").text("用户名或密码错误");
 		 			}
 		 			refreshCaptcha();
 		 			$("#captcha").val("");
 		 		}  else {
 		 			//取到URL
 		 			var tmpURL = json.targetURL
 		 			var myDate = new Date();
 		 			
 		 			//得到时间戳参数的位置
 		 			var changeParam = "dateStamp=";
 		 			var timePos = tmpURL.indexOf(changeParam);
 		 			if(timePos > -1) {
 		 				tmpURL = tmpURL.substring(0, timePos+changeParam.length) + myDate.getTime();
 		 			} else {
	 		 			if(tmpURL.indexOf("?")>-1 && tmpURL.indexOf("=")>-1) {
	 		 				tmpURL += '&dateStamp='+myDate.getTime();
	 		 			} else {
	 		 				tmpURL += '?dateStamp='+myDate.getTime();
	 		 			}
 		 			}
 		 			window.location.href = tmpURL;
	 		 	}
 			}
	 	   });
	       return false;
	     }); 
      
      //判断按钮，根据URL请求判断
      //得到属性为 pri_url的标签
      $(".privilege-hints").each(function(){
    	  var priURLObj = this;
    	  var priURL = $(priURLObj).attr("pri_url");
    	 
    	 //编码
    	 priURL = encodeURIComponent(priURL);
    	 $.ajax({
				type : "GET",
				url : "/ietlAdmin/ws/isHaveAuthention?url="+priURL,
				success : function(data) {
					if(data=='true') {
						$(priURLObj).removeClass("privilege-hints");
					} 
				}
			});
      });
	});
		
	function refreshCaptcha() {   
	    $('#captchaImg').hide().attr(     
	    'src' ,     
	    '/ietlAdmin/jcaptcha.jpg' + '?' + Math.floor(Math.random()*100)).fadeIn();     
	}  
	
var privilege = {};
//权限控制
privilege.privilegeControl = function() {
	 $(".bootstrap-switch-privilege-hints,.privilege-hints").each(function(){
		 if($(this).css("display")!="none") {
			 return;
		 }
		 var priURLObj = this;
   	  	 var priURL = $(priURLObj).attr("pri_url")||$($(priURLObj).find("[pri_url]")[0]).attr("pri_url");
   	 
	   	 //编码
	   	 priURL = encodeURIComponent(priURL);
	   	 $.ajax({
					type : "GET",
					url : "/ietlAdmin/ws/isHaveAuthention?url="+priURL,
					success : function(data) {
						if(data=='true') {
							$(priURLObj).removeClass("privilege-hints");
							$(priURLObj).removeClass("bootstrap-switch-privilege-hints");
						} 
					}
				});
	  });
  };