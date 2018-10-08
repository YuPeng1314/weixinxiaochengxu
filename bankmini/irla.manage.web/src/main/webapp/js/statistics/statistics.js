var dataStatistics={};
//资源数据统计
dataStatistics.resStatistics = function(){
	$('#homepage_table').bootstrapTable('destroy').bootstrapTable({
		method : "POST",
		url : "/ietlAdmin/ws/getResStatistics",// 请求后台的URL（*）
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
			field : 'resourceCode',
			title : '资源编码',
			align : 'center',
		}, {
			field : 'resourceName',
			title : '资源名称',
			align : 'center',
		}, {
			field : 'courseCode',
			title : '课程编码',
			align : 'center',
		}, {
			field : 'courseName',
			title : '课程名称',
			align : 'center',
		}, {
			field : 'resVisitNum',
			title : '资源点击量',
			align : 'center',
		}, {
			field : 'resCollectNum',
			title : '资源收藏量',
			align : 'center',
		}, {
			field : 'resShareNum',
			title : '资源转发量',
			align : 'center',
		} ]
	});
	$("#diff").val(1);
}
//课程数据统计
dataStatistics.courseStatistics = function(){
	$('#homepage_table').bootstrapTable('destroy').bootstrapTable({
		method : "POST",
		url : "/ietlAdmin/ws/getCourseStatistics",// 请求后台的URL（*）
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
		columns : [{
			field : 'courseCode',
			title : '课程编码',
			align : 'center',
		}, {
			field : 'courseName',
			title : '课程名称',
			align : 'center',
		}, {
			field : 'courseVisitNum',
			title : '课程点击量',
			align : 'center',
		}, {
			field : 'courseCollectNum',
			title : '课程收藏量',
			align : 'center',
		}, {
			field : 'courseShareNum',
			title : '课程转发量',
			align : 'center',
		}]
	});
	$("#diff").val(2);
};
//传递的参数
function queryParams2(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"resourceName" : $("#name_search").val(),
		"courseName" : $("#name_search").val(),
	};
};
//搜索框清空
dataStatistics.emptySearch = function(){
	$("#name_search").val("");
};
dataStatistics.searchBtn = function(){
	if($("#diff").val()==1){
		dataStatistics.resStatistics();
	}
	if($("#diff").val()==2){
		dataStatistics.courseStatistics();
	}
}
$(function(){
	dataStatistics.resStatistics();
});
