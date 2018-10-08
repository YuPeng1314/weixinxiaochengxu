$(function() {
		oholiday.initTable();
		oholiday.Validator();
});
var oholiday={};
oholiday.initTable = function(){
	$('#typemanege_table').bootstrapTable('destroy').bootstrapTable({
		method : "POST",
		url : "/ietlAdmin/ws/getHoliday",// 请求后台的URL（*）
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
			formatter : oholiday.actionFormatter
		}, {
			field : 'holidayName',
			title : '节日名称',
			align : 'center',
		}, {
			field : 'holidayCode',
			title : '节日编码',
			align : 'center',
		}, {
			field : 'description',
			title : '节日描述',
			align : 'center',
		}, {
			field : 'createdBy',
			title : '创建人',
			align : 'center',
		}, {
			field : 'lastUpdatedBy',
			title : '修改人',
			align : 'center',
		}, {
			field : 'lastUpdateDate',
			title : '修改时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			field : 'isValid',
			title : '是否发布',
			align : 'center',
			formatter : oholiday.changeFormatter
		} ]
	});
}
//传递的参数
function queryParams2(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"holidayName" : $("#holidayNameSrh").val()
	};
}
// 操作列图标的显示
oholiday.actionFormatter = function(value, row, index) {
	var edit = "<input type='button' id = 'update_btn' pri_url='/ws/optCarousel' class='ico_a btn btn-info btn-xs ' title='修改' " +
	"onclick='oholiday.update("+row.id+")' value='改'>";
	var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteCarousel' class='ico_a btn btn-danger btn-xs ' title='删除' " +
	"onclick='oholiday.deleteHoliday("+row.id+")' value='删'>";
	return edit + "&nbsp;&nbsp;" + del;
}
// 发布未发布的样式显示
oholiday.changeFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		return "已发布";
	} else {
		return "未发布"
	}
}
//搜索框清空
oholiday.emptySearch = function(){
	$("#holidayNameSrh").val("");
}
//表单清空
oholiday.optempty = function(){
	$("#holidayName").val("");
	$("#description").val("");
	$("#isValid").val("");
}
oholiday.addholiday = function(){
	$("#addModal").modal();
	// 重置表单
	$('#holidayForm')[0].reset();
	$("#homepageshow").empty();
	$("#homepageshow").append("<div id='fileshow'><input id='homeimg_upload' name='files' type='file'   class='file-loading'></div>");
	$("#holidayForm").data('bootstrapValidator').resetForm();
	$("#holidayaddress label:first").empty();
	$("#holidayaddress label:first").append("节日图片<font color='red'>&ensp;*</font>");
	$("#homeimg_upload").fileinput({
		language : 'zh', // 设置语言
		/* allowedFileTypes : [ 'image' ], */
		allowedFileExtensions : [ 'jpg', 'gif', 'png' ],// 接收的文件后缀
		showUpload : false, // 是否显示上传按钮
		showCaption : true,// 是否显示标题
		showCancel:false,
		browseClass : "btn btn-inverse", // 按钮样式
		previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
		maxFileSize : 1024000
	});
}
oholiday.update = function(id){
	// 重置表单
	$('#holidayForm')[0].reset();
	$("#holidayForm").data('bootstrapValidator').resetForm();
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/getHoliday",
		data: JSON.stringify({
			"id" : id
		}),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$("#id").val(data.rows[0].id);
			$("#holidayName").val(data.rows[0].holidayName);
			$("#description").val(data.rows[0].description);
			$("#isValid").val(data.rows[0].isValid);
			$("#homepageshow").empty();
			$("#homepageshow").append("<div id='fileshow'><input id='homeimg_upload' name='files' type='file'   class='file-loading'></div>");
			$("#holidayaddress label:first").empty();
			$("#holidayaddress label:first").append("节日图片");
			$("#homeimg_upload").fileinput({
				language : 'zh', // 设置语言
				/* allowedFileTypes : [ 'image' ], */
				allowedFileExtensions : [ 'jpg', 'gif', 'png' ],// 接收的文件后缀
				showUpload : false, // 是否显示上传按钮
				showCaption : true,// 是否显示标题
				showCancel:false,
				browseClass : "btn btn-inverse", // 按钮样式
				previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
				maxFileSize : 1024000
			});
			$("#addModal").modal();
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}
//点击确定按钮
oholiday.optHoliday = function(){
	$('#holidayForm').data('bootstrapValidator').validate();
	if (!$('#holidayForm').data('bootstrapValidator').isValid()) {
		return;
	}
	$("#holidayForm").data('bootstrapValidator').resetForm();
	var id = $("#id").val();
	var holidayName = $("#holidayName").val();
	var description = $("#description").val();
	var isValid = $("#isValid").val();
	var holiday={
			"id":parseInt(id),
			"holidayName":holidayName,
			"description":description,
			"isValid":isValid
	}
	if(id==0){
		oholiday.holidayAdd();
	}else{
		var photo = $(".file-caption-name").attr("title");
		if (!photo) {
			oholiday.updateNoImg(holiday);
		}else{
			oholiday.holidayUpdate();
		}		
	}
}
//新增
oholiday.holidayAdd = function(){
	// 检查图片上传是否为空
	var photo = $(".file-caption-name").attr("title");
	if (!photo) {
		common.alertErrorMsg("请选择图片上传");
		return;
	}
	// 添加正在上传的面板提示
	var uploadMsg = bootbox.dialog({
		size : 'small',
		closeButton: false,
		message : '<div class="text-center" style="color:green;"><i class="fa fa-spin fa-spinner"></i>正在上传...</div>'
	});
	$("#holidayForm").ajaxSubmit({
		type : "POST",
		url : "/ietlAdmin/ws/addHoliday",
		data : common.form2JosnStr("#holidayForm"),
		contentType : "multipart/form-data;charset=UTF-8",
		success : function(data) {
			uploadMsg.modal('hide');
			$("#addModal").modal('hide');
			$('#holidayForm')[0].reset();
			$('#typemanege_table').bootstrapTable('refresh');
			bootbox.setLocale("zh_CN");
			if (data) {
				common.alertInfoMsg("节日添加成功!");
			}
			if (!data) {
				common.alertInfoMsg("操作失败!");
			}
		},
		error : function(request) {
			uploadMsg.modal('hide');
			common.alertErrorMsg("操作失败!");
		}
	});
}
//修改带图片
oholiday.holidayUpdate = function(){
	var uploadMsg = bootbox.dialog({
		size : 'small',
		closeButton: false,
		message : '<div class="text-center" style="color:green;"><i class="fa fa-spin fa-spinner"></i>正在上传...</div>'
	});
	$("#holidayForm").ajaxSubmit({
		type : "post",
		url : "/ietlAdmin/ws/updateHoliday",
		data : common.form2JosnStr("#holidayForm"),
		contentType : "multipart/form-data;charset=UTF-8",
		success : function(data) {
			uploadMsg.modal('hide');
			$("#addModal").modal('hide');
			$('#holidayForm')[0].reset();
			$('#typemanege_table').bootstrapTable('refresh');
			bootbox.setLocale("zh_CN");
			if (data) {
				common.alertInfoMsg("节日修改成功!");
			}
			if (!data) {
				common.alertInfoMsg("操作失败!");
			}
		},
		error : function(request) {
			uploadMsg.modal('hide');
			common.alertErrorMsg("操作失败!");
		}
	});
}
//修改不包括图片
oholiday.updateNoImg = function(holiday){
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/updateHolidayNoImg" ,
		data: JSON.stringify(holiday),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$("#addModal").modal('hide');
			$('#holidayForm')[0].reset();
			$('#typemanege_table').bootstrapTable('refresh');
			if (data) {
				common.alertInfoMsg("节日修改成功!");
			}
			if (!data) {
				common.alertInfoMsg("操作失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}
//删除
oholiday.deleteHoliday = function(id){
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/deleteHoliday/" + id,
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$('#typemanege_table').bootstrapTable('refresh');
			if (data==0) {
				common.alertInfoMsg("节日删除成功!");
			}
			if (data==1) {
				common.alertInfoMsg("参数获取失败!");
			}
			if (data==2) {
				common.alertInfoMsg("请先删除这个节日中的表情!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}
//验证
oholiday.Validator = function(){
	$('#holidayForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {// 输入框不同状态，显示图片的样式
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {// 验证
			holidayName : {// 键名和input name值对应
				message : '节日名称不能为空',
				validators : {
					notEmpty : {// 非空提示
						message : '节日名称不能为空'
					},
					stringLength : {
						min : 2,
						max : 200,
						message : '标题长度必须在2到200之间'
					},
				}
			},
			isValid : {// 键名和input name值对应
				message : '是否发布不能为空',
				validators : {
					notEmpty : {// 非空提示
						message : '是否发布不能为空'
					}
				}
			}
		}
	});
}