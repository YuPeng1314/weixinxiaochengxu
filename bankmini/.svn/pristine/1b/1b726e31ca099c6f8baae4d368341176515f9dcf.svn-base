$(function() {
		homePage.initTable();
});

var homePage = {};

// 表格数据初始化
homePage.initTable = function() {
	$('#homepage_table').bootstrapTable('destroy').bootstrapTable({
		method : "POST",
		url : "/ietlAdmin/ws/findCarouse",// 请求后台的URL（*）
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
		onLoadSuccess : homePage.initSwitch,// 表格加载完成后再初始化这个方法
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : homePage.actionFormatter
		}, {
			field : 'carouselTitle',
			title : '图片标题',
			align : 'center',
			formatter : common.valueEncode
		}, {
			field : 'carouselLinkCode',
			title : '课程编码',
			align : 'center',
		}, {
			field : 'carouselImgUrl',
			title : '图片地址',
			align : 'center',
			formatter : homePage.omitValue
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
			formatter : homePage.changeFormatter
		} ]
	});
}

homePage.omitValue = function(value, row, index) {
	if(value == null || value == "" || value == undefined){
		return "--"
	}else{
		var omitValue = common.htmlEncode(value);
		return omitValue;
	}
}

homePage.imgType = function(value, row, index) {
	if (row.imgType == "0") {
		return "项目详情页";
	} else if (row.imgType == "1") {
		return "课程详情页";
	}else  {
		return "";
	} 
}

// 清空
homePage.restBtn = function() {
	$('#searchFrom').resetForm();
}

// 查询
homePage.searchBtn = function() {
	homePage.initTable();
	
}

// 传递的参数
function queryParams2(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"carouselTitle" : $("#homepage_title_search").val()
	};
}

// 操作列图标的显示
homePage.actionFormatter = function(value, row, index) {
	if (row.isValid == '0') {
		var edit = "<input type='button' id = 'update_btn' pri_url='/ws/optCarousel' class='ico_a btn btn-info btn-xs ' title='修改' " +
		"onclick='homePage.updateImg("+row.id+")' value='改'>";
		var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteCarousel' class='ico_a btn btn-danger btn-xs ' title='删除' " +
		"onclick='homePage.deleteImg("+row.id+")' value='删'>";
		return edit + "&nbsp;&nbsp;" + del;
	} else {
		var del = "<a id = 'delete_btn'></a>";
		var edit = "<a  id = 'update_btn'></a>";
		return edit + "&nbsp;&nbsp;" + del;
	}
}

// 发布未发布的样式显示
homePage.changeFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		return "<input id= " + row.id + " pri_url='/ws/validPublish'  name='is_publish' type='checkbox' "
				+ " data-size='small' checked>";
	} else {
		return "<input id= " + row.id + " pri_url='/ws/validPublish' name='is_publish' type='checkbox' " + " data-size='small'>";
	}
}

// 发布未发布的切换
homePage.initSwitch = function() {
	if ($('[name="is_publish"]').length > 0) {
		$('[name="is_publish"]').bootstrapSwitch({
			onText : "发布",
			offText : "未发布",
			onColor : "success",
			offColor : "danger",
			wrapperClass : "privilege-hints",
			onSwitchChange : function(event, state) {
				homePage.validStatus($(this).attr("id"), state);
			}
		});

	} else {
		setTimeout(homePage.initSwitch, 1000);
	}
	// 权限切换
	privilege.privilegeControl();
}

// 发布未发布切换方法
homePage.validStatus = function(id, state) {
	$.ajax({
		type : "get",
		url : "/ietlAdmin/ws/validPublish/" + id + "/" + state,
		success : function(data) {
			if (data) {
				$('#homepage_table').bootstrapTable('refresh');
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
// 增加
homePage.addhomePageImg = function() {
	$("#addhomePage").modal();
	// 重置表单
	$('#addpageForm')[0].reset();

	$("#homepageshow").empty();
	$("#homepageshow").append("<div id='fileshow'><input id='homeimg_upload' name='files' type='file'   class='file-loading'></div>");

	homePage.Validator();
	$("#addpageForm").data('bootstrapValidator').resetForm();
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

// 删除
homePage.deleteImg = function(id) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("你确定删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/deletePagebyId",
				data : JSON.stringify({
					"id" : id
				}),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					if (data) {
						common.alertInfoMsg("删除成功!");
						$('#homepage_table').bootstrapTable('refresh')
					} else {
						common.alertInfoMsg("删除失败!");
					}
				},
				error : function() {
					common.alertErrorMsg("删除失败!");
				}
			});
		}
	});
}

// 修改
homePage.updateImg = function(id) {
	$("#addpageForm").resetForm();
	homePage.Validator();
	$("#addpageForm").data('bootstrapValidator').resetForm();
	$("#fileshow").remove();
	$("#carouseladdress").hide();
	$("#carouseltitle").hide();
	$.ajax({
		type : "get",
		url : "/ietlAdmin/ws/getHomePageInfo/" + id,
		success : function(data) {
			$("#testimgname").attr('src', "");
			if (data != null) {
				$("#addpageForm").data('bootstrapValidator').resetForm();
				$("#addpageForm").fill(data, {
					styleElementName : 'none'
				});
				$('#addhomePage').modal();	
			} else {
				common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("修改失败!");
		}
	});
}

homePage.uploadSub = function() {
	$('#addpageForm').data('bootstrapValidator').validate();
	if (!$('#addpageForm').data('bootstrapValidator').isValid()) {
		return;
	}
	$("#addpageForm").data('bootstrapValidator').resetForm();
	var address = $("#carouselImgUrl").val();
	var lasaddress = $("#testimgname").val();
	if (lasaddress != null && address != lasaddress) {
		$.ajax({
			type : "post",
			url : "/ietlAdmin/ws/updatePageImg",
			data : common.form2JosnStr("#addpageForm"),
			contentType : "application/json;charset=utf-8",
			success : function(data) {
				$("#addhomePage").modal('hide');
				// 重置表单
				$('#addpageForm')[0].reset();
				// common.alertInfoMsg("操作成功!");
				/*// 表格刷新
				$('#homepage_table').bootstrapTable('refresh');*/
				bootbox.setLocale("zh_CN");
			    bootbox.alert({
			        message: "操作成功!",
			        size: 'small',
			        callback: function () {
			        	location.reload();
			        }
			    })
			},
			error : function(request) {
				common.alertErrorMsg("操作失败!");
			}
		});
	} else {
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
		$("#addpageForm").ajaxSubmit({
			type : "post",
			url : "/ietlAdmin/ws/addhomePageImg",
			data : common.form2JosnStr("#addpageForm"),
			contentType : "multipart/form-data;charset=UTF-8",
			success : function(data) {
				uploadMsg.modal('hide');
				if(data=="true"){
					$("#addhomePage").modal('hide');
					// 重置表单
					$('#addpageForm')[0].reset();
					// 表格刷新
					$('#homepage_table').bootstrapTable('refresh');
					bootbox.setLocale("zh_CN");
				    bootbox.alert({
				        message: "操作成功!",
				        size: 'small',
				        callback: function () {
				        	location.reload();
				        }
				    })
				}
				else{
					$("#addhomePage").modal('hide');
					// 重置表单
					$('#addpageForm')[0].reset();
					// 表格刷新
					$('#homepage_table').bootstrapTable('refresh');
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
				common.alertErrorMsg("操作失败!");
			}
		});
	}
}

/**
 * 校验是否已经存在
 */
homePage.checkName = function(crawlerName) {
	var flag = true;
	// 如果是不是引文和数字
	var reg = /^\w+$/;
	if (!reg.test(crawlerName)) {
		return false;
	}
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/checkHomePageName",
		data : JSON.stringify({
			"crawlerName" : crawlerName
		}),
		contentType : "application/json;charset=utf-8",
		async : false,
		success : function(data) {
			flag = data;
		},
		error : function() {
			flag = false;
		}
	});
	return flag;
}

// 表单验证
homePage.Validator = function() {
	$('#addpageForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {// 输入框不同状态，显示图片的样式
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {// 验证
//			carouselTitle : {// 键名和input name值对应
//				message : '图片标题不能为空',
//				validators : {
//					notEmpty : {// 非空提示
//						message : '图片标题不能为空'
//					},
//					stringLength : {
//						min : 2,
//						max : 200,
//						message : '标题长度必须在2到200之间'
//					},
//				}
//			}
		}
	});
}
