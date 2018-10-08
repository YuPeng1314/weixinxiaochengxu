$(function() {
	userInfoManage.initTable();
});
var userInfoManage={};
userInfoManage.initTable = function(){
	$('#typemanege_table').bootstrapTable('destroy').bootstrapTable({
		method : "POST",
		url : "/ietlAdmin/ws/findUserInfo",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		queryParams : queryParams2,
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : userInfoManage.actionFormatter
		}, {
			field : 'user_id',
			title : 'id',
			align : 'center',
		}, {
			field : 'user_code',
			title : '用户编码',
			align : 'center',
			formatter : userInfoManage.subUsercode
		}, {
			field : 'user_name',
			title : '用户名',
			align : 'center',
		}, {
			field : 'user_nkname',
			title : '用户昵称',
			align : 'center',
		}, {
			field : 'user_sex',
			title : '用户性别',
			align : 'center',
		}, {
			field : 'user_img',
			title : '用户图像',
			align : 'center',
			formatter : userInfoManage.handleContent
		}, {
			field : 'country',
			title : '所在国家',
			align : 'center',
		}, {
			field : 'province',
			title : '所在省份',
			align : 'center',
		}, {
			field : 'city',
			title : '所在城市',
			align : 'center',
		}, {
			field : 'area_info',
			title : '所在区域',
			align : 'center',
		}, {
			field : 'user_work',
			title : '用户工作',
			align : 'center',
		}, {
			field : 'user_long_status',
			title : '用户登录状态',
			align : 'center',
		}, {
			field : 'longitude',
			title : '经度',
			align : 'center',
		}, {
			field : 'latitude',
			title : '纬度',
			align : 'center',
		}]
	});
}
//传递的参数
function queryParams2(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"user_name" : $("#userNameSrh").val(),
	};
}
// 操作列图标的显示
userInfoManage.actionFormatter = function(value, row, index) {
	var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteUserInfo' class='ico_a btn btn-danger btn-xs ' title='删除' " +
	"onclick='userInfoManage.delUserInfo("+row.user_id+")' value='删'>";
	return del;
}
//用户名截取
userInfoManage.subUsercode = function(value, row, index){
	if(value != null && value != "" && value != undefined){
		if(value.length >= 10){
			value = value.substring(0,10) + "...";
		}
	}
	return value;
}

//图像
userInfoManage.handleContent = function(value, row, index) {
	if(value != null && value != "" && value != undefined){
		var tempValue = $('<div/>').text(value).html();
		var result = "";
		if (tempValue.length >= 10) {
			result = tempValue.substring(0, 10) + "...";
		} else {
			result = tempValue;
		}
		return "<a style='cursor:pointer;text-decoration:none' id='conten"+ index + "' onmouseover='userInfoManage.overCell(this.id,\"" + tempValue
				+ "\")'  onmouseout='userInfoManage.outCell(\"" + index + "\")'>" + result + "</a>";
	}else{
		return value;
	}
},
//设备图片展示
userInfoManage.overCell = function(id, tempValue){
	var img = '<img style="margin:1px;display: inline-block;"src="' + tempValue + '" width="170px;">';
	layer.tips(img, "#" + id, {
		tips : [ 2, '#3595CC' ],
		time : 10000
	});
},

//设备图片展示
userInfoManage.outCell = function(index){
	layer.closeAll('tips');
},

//搜索框清空
userInfoManage.emptySearch = function(){
	$("#userNameSrh").val("");
}
//删除
userInfoManage.delUserInfo = function(id){
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/deleteUserInfo/" + id,
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$('#typemanege_table').bootstrapTable('refresh');
			if (data==0) {
				common.alertInfoMsg("用户信息删除成功!");
			}
			if (data==1) {
				common.alertInfoMsg("参数获取失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}