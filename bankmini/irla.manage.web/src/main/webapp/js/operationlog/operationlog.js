var log = {};

log.initTable = function() {
	$('#searchLoginFrom').hide();
	$('#realTimeLog').hide();
	$('#searchFrom').show();
	$('#login_table').bootstrapTable('destroy');  
	$('#log_table').bootstrapTable('destroy').bootstrapTable({
		method: "post",
		url : "/ietlAdmin/ws/findlogList",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		queryParams: queryParams,
		sidePagination : "server",//设置在哪里进行分页
		minimumCountColumns : 2, // 设置最少显示列个数       
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		onLoadSuccess : privilege.privilegeControl,
		columns : [{
			field : 'operation_module',
			title : '操作模块',
			align : 'center',
		}, {
			field : 'opeartion_method',
			title : '操作方法',
			align : 'center',
		},{
			field : 'operation_param',
			title : '操作参数',
			align : 'center',
			formatter :log.omitParams
		},{
			field : 'operation_flag',
			title : '操作结果',
			align : 'center',
			formatter :log.omitFlag
		},{
			field : 'operation_err',
			title : '操作信息',
			align : 'center',
			formatter :log.omitErr
		},{
			field : 'opeartion_user',
			title : '操作人',
			align : 'center',
		},{
			field : 'operation_ip',
			title : 'IP地址',
			align : 'center',
		},{
			field : 'opeartion_time',
			title : '操作时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}]
	});
}

//操作结果
log.omitFlag =function(value,row,index) {
	var value;
	if(value=="1"){
		value = "正常";
	}
	if(value=="0"){
		value = "异常";
	}
	return value;
}

//参数省略
log.omitParams = function(value,row,index){
	var paramString = value;  
	if(paramString.length>6){
		var omitparamtemp = paramString.substring(0,5) + "..."; 
    	return omitParams = "<span class='ico_a' title="+paramString+" >"+omitparamtemp+"</span>";
	}else{
		return paramString;
	}
    	
}

//操作信息省略
log.omitErr = function(value,row,index){
	var errString = value;  
	if(errString.length>6){
    	var omitErrtemp = errString.substring(0,3) + "..."; 
    	return omitErr = "<span class='ico_a' title="+errString+" >"+omitErrtemp+"</span>";
	}
	else{
		return errString;
	}
}

log.restBtn = function(){
	$('#searchFrom').resetForm(); 
	$("#opeartion_time").val('');
	$("#opeartion_etime").val('');
}

function queryParams(params) {
	 return  {
	    "limit":params.limit,
	    "offset": params.offset,
	    "opeartion_time": $("#opeartion_time").val(),
	    "opeartion_etime": $("#opeartion_etime").val(),
	    "operation_flag": $("#operation_flag").val()
	  };
}

log.searchBtn = function(){
	
	var startdate = $("#opeartion_time").val();
	var enddate = $("#opeartion_etime").val();
	   var startTime=new Date(startdate.replace(/\-/g,'/')),
	    endTime=new Date(enddate.replace(/\-/g,'/'));
	   if(endTime<startTime){
		   common.alertInfoMsg("结束时间不能小于开始时间!");
		   return;
	   }
		log.initTable();
	
}	


/**
 * 操作日志与登录日志的切换
 */

log.operaStatus = function(operaStatus){
	$("#opera_status").empty();
	$("#opera_status").val(operaStatus);
	if(operaStatus ==1){
		log.initTable();
	}
	else if(operaStatus==2){
		
		log.initLoginTable();
	} else if(operaStatus==3) {
		$('#realTimeLog').show();
		$('#log_table').bootstrapTable('destroy'); 
		$('#login_table').bootstrapTable('destroy');
		$('#searchLoginFrom').hide();
		$('#searchFrom').hide();
		
		//得到url
		$.ajax({
			type : "Get",
			url : "/ietlAdmin/ws/getRealLogURL",
			success:function(data) {
				if (data){
					$("#realTimeLogFlag").attr("src", data);
				}
			},
			error:function(request) {
				common.alertErrorMsg("加载失败");
			}
		});
		
	}
}

/**
 * 登录日志的表格
 */
log.initLoginTable = function() {
	$('#realTimeLog').hide();
	$('#searchLoginFrom').show();
	$('#searchFrom').hide();
	$('#log_table').bootstrapTable('destroy'); 
	$('#login_table').bootstrapTable('destroy').bootstrapTable({
		method: "post",
		url : "/ietlAdmin/ws/findLoginList",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		queryParams: queryLoginLogParams,
		sidePagination : "server",//设置在哪里进行分页
		minimumCountColumns : 2, // 设置最少显示列个数       
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		columns : [ {
			field : 'userCode',
			title : '用户账号',
			align : 'center',
		}, {
			field : 'userIP',
			title : '用户ip',
			align : 'center',
		},{
			field : 'type',
			title : '操作类型',
			align : 'center',
			formatter:log.actionType 
		},{
			field : 'actionDate',
			title : '触发时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}]
	});
}


function queryLoginLogParams(params) {
	 return  {
	    "limit":params.limit,
	    "offset": params.offset,
	    "userCode": $("#user_code").val(),
	    "userIP": $("#user_ip").val(),
	    "type": $("#type").val(),
	  };
}

//操作类型
log.actionType  =function(value,row,index){
	var value;
	if(value=="1"){
		value = "登录";
	}
	if(value=="2"){
		value = "登出";
	}
	return value;
	
}

//查询
log.loginSearch = function(){
	log.initLoginTable();
}

//清空
log.loginRefresh = function() {
	$('#searchLoginFrom')[0].reset();
}

