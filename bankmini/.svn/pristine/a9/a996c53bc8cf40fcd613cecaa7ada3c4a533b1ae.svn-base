$(function() {
		ophiz.initTable();
		ophiz.Validator();
});
var ophiz={};
ophiz.initTable = function(){
	$('#typemanege_table').bootstrapTable('destroy').bootstrapTable({
		method : "POST",
		url : "/ietlAdmin/ws/getPhiz",// 请求后台的URL（*）
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
		onLoadSuccess : ophiz.initSwitch,// 表格加载完成后再初始化这个方法
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : ophiz.actionFormatter
		}, {
			field : 'phizId',
			title : 'id',
			align : 'center',
		},{
			field : 'imgName',
			title : '表情名称',
			align : 'center',
		}, {
			field : 'description',
			title : '表情描述',
			align : 'center',
		}, {
			field : 'imgAddress',
			title : '表情地址',
			align : 'center',
		}, {
			field : 'holidayName',
			title : '所属节日',
			align : 'center',
		},{
			field : 'isRecommend',
			title : '是否为推荐表情',
			align : 'center',
			formatter : ophiz.recommendFormatter
		},  {
			field : 'themeName',
			title : '所属节日',
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
			formatter : ophiz.changeFormatter
		} ]
	});
}
//传递的参数
function queryParams2(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"description" : $("#phizNameSrh").val()
	};
}
//是否推荐课程
ophiz.recommendFormatter = function(value, row, index) {
	if (row.isRecommend == '1') {
		return "推荐";
	} else {
		return "否"
	}
}
// 操作列图标的显示
ophiz.actionFormatter = function(value, row, index) {
	var edit = "<input type='button' id = 'update_btn' pri_url='/ws/optCarousel' class='ico_a btn btn-info btn-xs ' title='修改' " +
	"onclick='ophiz.update("+row.phizId+")' value='改'>";
	var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteCarousel' class='ico_a btn btn-danger btn-xs ' title='删除' " +
	"onclick='ophiz.deletePhiz("+row.phizId+")' value='删'>";
	return edit + "&nbsp;&nbsp;" + del;
}
// 发布未发布的样式显示
ophiz.changeFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		return "<input id= " + row.phizId + " pri_url='/ws/validPhizPublish'  name='is_publish' type='checkbox' "
				+ " data-size='small' checked>";
	} else {
		return "<input id= " + row.phizId + " pri_url='/ws/validPhizPublish' name='is_publish' type='checkbox' " + " data-size='small'>";
	}
}
//发布未发布的切换
ophiz.initSwitch = function() {
	if ($('[name="is_publish"]').length > 0) {
		$('[name="is_publish"]').bootstrapSwitch({
			onText : "发布",
			offText : "未发布",
			onColor : "success",
			offColor : "danger",
			wrapperClass : "privilege-hints",
			onSwitchChange : function(event, state) {
				ophiz.validStatus($(this).attr("id"), state);
			}
		});

	} else {
		setTimeout(ophiz.initSwitch, 1000);
	}
	// 权限切换
	privilege.privilegeControl();
}
//发布未发布切换方法
ophiz.validStatus = function(id, state) {
	$.ajax({
		type : "get",
		url : "/ietlAdmin/ws/validPhizPublish/" + id + "/" + state,
		success : function(data) {
			if (data) {
				$('#typemanege_table').bootstrapTable('refresh');
				if(state){
					common.alertInfoMsg("发布成功!");
				}else{
					common.alertInfoMsg("撤销发布成功!");
				}
			}
			if (!data) {
				common.alertInfoMsg("操作失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
},
//搜索框清空
ophiz.emptySearch = function(){
	$("#phizNameSrh").val("");
}
//表单清空
ophiz.optempty = function(){
	$("#description").val("");
	$("#holidayCode").val("");
	$("#themeCode").val("");
	$("#isRecommend").val("");
}
//加载节日列表
ophiz.selectOption = function() {
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/getHoliday",
		contentType : "application/json;charset=utf-8",
		async: false,
		success : function(data) {
			$('#holidayCode').empty();
			$('#holidayCode').append("<option value='' selected='selected'>请选择</option>");
			for (var i = 0; i < data.rows.length; i++) {
				$('#holidayCode').append("<option value=" + data.rows[i].holidayCode + ">" + data.rows[i].holidayName + "</option>");
			};
		},
	});
}
//加载主题列表
ophiz.selectThemeOption = function() {
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/getTheme",
		contentType : "application/json;charset=utf-8",
		async: false,
		success : function(data) {
			$('#themeCode').empty();
			$('#themeCode').append("<option value='' selected='selected'>请选择</option>");
			for (var i = 0; i < data.rows.length; i++) {
				$('#themeCode').append("<option value=" + data.rows[i].themeCode + ">" + data.rows[i].themeName + "</option>");
			};
		},
	});
}
//点击新增表情
ophiz.addphiz = function(){
	ophiz.selectOption();
	ophiz.selectThemeOption();
	$("#addModal").modal();
	$('#phizForm')[0].reset();
	$("#homepageshow").empty();
	$("#homepageshow").append("<div id='fileshow'><input id='homeimg_upload' name='files' type='file'   class='file-loading'></div>");
	$("#phizForm").data('bootstrapValidator').resetForm();
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
//点击修
ophiz.update = function(id){
	$('#phizForm')[0].reset();
	$("#phizForm").data('bootstrapValidator').resetForm();
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/getPhiz",
		data: JSON.stringify({
			"phizId" : id
		}),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			ophiz.selectOption();
			ophiz.selectThemeOption();
			$("#phizId").val(data.rows[0].phizId);
			$("#description").val(data.rows[0].description);
			$("#holidayCode").val(data.rows[0].holidayCode);
			$("#themeCode").val(data.rows[0].themeCode);
			$("#isRecommend").val(data.rows[0].isRecommend);
			$("#carouseladdress").hide();
			$("#addModal").modal();
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}
//点击确定按钮
ophiz.optPhiz = function(){
	$('#phizForm').data('bootstrapValidator').validate();
	if (!$('#phizForm').data('bootstrapValidator').isValid()) {
		return;
	}
	$("#phizForm").data('bootstrapValidator').resetForm();
	var phizId = $("#phizId").val();
	var description = $("#description").val();
	var holidayCode = $("#holidayCode").val();
	var themeCode = $("#themeCode").val();
	var isRecommend = $("#isRecommend").val();
	var phiz={
			"phizId" : parseInt(phizId),
			"description" : description,
			"holidayCode" : holidayCode,
			"themeCode" : themeCode,
			"isRecommend" : isRecommend
	}
	if(phizId==0){
		ophiz.phizAdd();
	}else{
		ophiz.phizUpdate(phiz);
	}
}
//新增
ophiz.phizAdd = function(){
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
	$("#phizForm").ajaxSubmit({
		type : "post",
		url : "/ietlAdmin/ws/addPhiz",
		data : common.form2JosnStr("#phizForm"),
		contentType : "multipart/form-data;charset=UTF-8",
		success : function(data) {
			uploadMsg.modal('hide');
			if(data){
				$("#addModal").modal('hide');
				$('#phizForm')[0].reset();
				$('#typemanege_table').bootstrapTable('refresh');
				bootbox.setLocale("zh_CN");
			    bootbox.alert({
			        message: "节日添加成功!",
			        size: 'small',
			        callback: function () {
			        	location.reload();
			        }
			    })
			}
			else{
				$("#addModal").modal('hide');
				$('#phizForm')[0].reset();
				$('#typemanege_table').bootstrapTable('refresh');
				bootbox.setLocale("zh_CN");
			    bootbox.alert({
			        message: "操作失败!",
			        size: 'small',
			        callback: function () {
			        	location.reload();
			        }
			    })
			}
		},
		error : function(request) {
			uploadMsg.modal('hide');
			common.alertErrorMsg("操作失败!");
		}
	});
}
//修改
ophiz.phizUpdate = function(phiz){
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/updatePhiz",
		data: JSON.stringify(phiz),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if (data) {
				$('#typemanege_table').bootstrapTable('refresh');
				common.alertInfoMsg("节日修改成功!");
				$("#addModal").modal('hide');
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
ophiz.deletePhiz = function(id){
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/delPhiz/" + id,
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
ophiz.Validator = function(){
	$('#phizForm').bootstrapValidator({
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
			themeName : {// 键名和input name值对应
				message : '主题名称不能为空',
				validators : {
					notEmpty : {// 非空提示
						message : '主题名称不能为空'
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
			},
			isRecommend : {// 键名和input name值对应
				message : '是否推荐不能为空',
				validators : {
					notEmpty : {// 非空提示
						message : '是否推荐不能为空'
					}
				}
			}
		}
	});
}