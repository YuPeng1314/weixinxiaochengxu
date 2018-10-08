$(function() {
	commentManage.initTable();
});
var commentManage={};
commentManage.initTable = function(){
	$('#typemanege_table').bootstrapTable('destroy').bootstrapTable({
		method : "POST",
		url : "/ietlAdmin/ws/findComment",// 请求后台的URL（*）
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
			formatter : commentManage.actionFormatter
		}, {
			field : 'resourseCode',
			title : '资源编码',
			align : 'center',
		}, {
			field : 'userCode',
			title : '用户编码',
			align : 'center',
		}, {
			field : 'comment',
			title : '评论内容',
			align : 'center',
		}, {
			field : 'creationDate',
			title : '创建时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}]
	});
}
//传递的参数
function queryParams2(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"resourseCode" : $("#holidayNameSrh").val(),
		"comment" : $("#holidayNameSrh").val(),
		"userCode" : $("#holidayNameSrh").val()
	};
}
// 操作列图标的显示
commentManage.actionFormatter = function(value, row, index) {
	var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteComment' class='ico_a btn btn-danger btn-xs ' title='删除' " +
	"onclick='commentManage.delComment("+row.id+")' value='删'>";
	return del;
}
//搜索框清空
commentManage.emptySearch = function(){
	$("#holidayNameSrh").val("");
}
//删除
commentManage.delComment = function(id){
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/deleteComment/" + id,
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$('#typemanege_table').bootstrapTable('refresh');
			if (data==0) {
				common.alertInfoMsg("评论删除成功!");
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