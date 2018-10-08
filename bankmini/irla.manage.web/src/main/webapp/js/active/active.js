$(function() {
	activemanage.initTable();
	activemanage.validator();
});

var activemanage={};

activemanage.initTable = function(){
	$('#typemanege_table').bootstrapTable('destroy');
	$('#typemanege_table').bootstrapTable({
		method : "post",
		url : "/ietlAdmin/ws/getActive",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		queryParams : activemanage.queryDataParams,
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		onLoadSuccess:function(){ 
			activemanage.initSwitch();
			privilege.privilegeControl();
		},
		responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
			document.getElementById('typeCount').innerHTML = res.total;
			return res;
		},
		columns : [{
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : activemanage.actionFormatter
		}, {
			field : 'activeCode',
			title : '编码',
			align : 'center'
		}, {
			field : 'activeName',
			title : '名称',
			align : 'center'
		}, {
			field : 'content',
			title : '活动内容',
			align : 'center',
			formatter : activemanage.activecontent
		}, {
			field : 'activeImg',
			title : '图标',
			align : 'center',
			formatter: activemanage.handleContent
		}, {
			field : 'kinds',
			title : '分类',
			align : 'center',
			formatter : function(data) {
				if (data == 1 ) {
					return "社区";
				} else if(data==0){
					return "官方";
				}
			}
		}, {
			field : 'isHot',
			title : '热门',
			align : 'center',
			formatter : function(data) {
				if (data == 1 ) {
					return "热门";
				} else if(data==0){
					return "--";
				}
			}
		}, {
			field : 'createdBy',
			title : '创建人',
			align : 'center',
			formatter : common.valueEncode
		},{
			field : 'creationDate',
			title : '创建时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			field : 'lastUpdatedBy',
			title : '修改人',
			align : 'center',
		}, {
			field : 'lastUpdateDate',
			title : '修改日期',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			field : 'isValid',
			title : '状态标识',
			align : 'center',
			formatter : activemanage.isValid
		}]
	});
};
//参数传递
activemanage.queryDataParams = function(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"activeName" : $("#activeNameSrh").val(),
	};
},
//操作
activemanage.actionFormatter = function(value, row, index) {
	var update = "<input type='button' id = 'updateDomain' class='ico_a btn btn-info btn-xs ' title='修改' "
			+ "onclick='activemanage.updatebutn(\"" + row.activeCode + "\");' value='修'>";
	var del = "<input type='button' id = '' class='ico_a btn  btn-danger btn-xs ' title='删除' "
			+ "onclick='activemanage.delactive(\"" + row.id + "\");' value='删'>";
	var imgUP = "<input type='button' id = 'imgUP_btn'  class='ico_a btn btn-warning btn-xs ' title='维护图片' " +
	"onclick='activemanage.coverImgChange(" + row.id + ")' value='图片维护'>";
	return update + " " + del + " " + imgUP ;
},
//是否发布
activemanage.isValid = function(value, row, index) {
	if (row.isValid == '1') {
		return "<input id= " + row.id + " pri_url='/ws/validActive'  name='isValid' type='checkbox' " + " data-size='small' checked>";
	} else if (row.isValid == '0') {
		return "<input id= " + row.id + " pri_url='/ws/validActive' name='isValid' type='checkbox' " + " data-size='small'>";
	} 
},
//发布组件加载
activemanage.initSwitch = function() {
	if ($('[name="isValid"]').length > 0) {
		$('[name="isValid"]').bootstrapSwitch({
			onText : "有效",
			offText : "失效",
			onColor : "success",
			offColor : "danger",
			wrapperClass : "privilege-hints",
			onSwitchChange : function(event, state) {
				activemanage.validActive($(this).attr("id"), state);
			}
		});

	} else {
		setTimeout(activemanage.initSwitch, 1000);
	}
	// 权限切换
},
//发布按钮切换
activemanage.validActive = function(id, state) {
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/validActive/" + id + "/" + state,
		success : function(data) {
			$('#typemanege_table').bootstrapTable('refresh');
			if (data == "1") {
				common.alertInfoMsg("切换成功!");
			}
			if (data == "0") {
				common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");
			}
		},
		error : function(request) {
			$('#typemanege_table').bootstrapTable('refresh');
			common.alertErrorMsg("切换失败!");
		}
	});
},
//内容展示
activemanage.activecontent = function(value, row, index){
	var con = row.content;
	con = con.substring(0,15) + "...";
	return con;
}
//图片展示
activemanage.handleContent = function(value, row, index) {
	if(value != null && value != "" && value != undefined){
	var tempValue = $('<div/>').text(value).html();
	var result = "";
	if (tempValue.length >= 10) {
		result = tempValue.substring(0, 10) + "...";
	} else {
		result = tempValue;
	}
	return "<a style='cursor:pointer;text-decoration:none' id='conten"+ index + "' onmouseover='activemanage.overCell(this.id,\"" + tempValue
			+ "\")'  onmouseout='activemanage.outCell(\"" + index + "\")'>" + result + "</a>";
	}else{
		return value;
	}
},
//设备图片展示
activemanage.overCell = function(id, tempValue){
	var img = '<img style="margin:1px;display: inline-block;"src="' + tempValue + '" width="170px;">';
	layer.tips(img, "#" + id, {
		tips : [ 2, '#3595CC' ],
		time : 10000
	});
},

//设备图片展示
activemanage.outCell = function(index){
	layer.closeAll('tips');
},

//设备图片维护
activemanage.coverImgChange = function(id) {
	var active={
			"id" : id
	};
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/getActive",
		data: JSON.stringify(active),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if (data.length != 0) {
				    $("#areaImgModal").modal("show");
				    $('#areaImgForm')[0].reset();
					$("#id_img").val(id);
					if(data.active.activeImg != null && data.active.activeImg != "" && data.active.activeImg != undefined){
						$("#viewImgId").show();
						$("#testimgname").attr('src',data.active.activeImg);
						$("#remarkImgId").text(data.active.activeImg.substring(data.active.activeImg.lastIndexOf('/')+1));
						$("#delImgText").text("更换");
						$("#imgUploadDivId").hide(); //图片路径
					}else{
						activemanage.initUploadImg(); //初始化上传插件
					}
			} else {
				common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");

			}
		},
		error : function(request) {
			common.alertErrorMsg("您想要修改的记录可能不存在，请刷新重试!");
		}
	});
},

//保存修改后的图片
activemanage.uploadEqpImg = function(){
	var remarkImgId = $("#remarkImgId").text().trim();
	// 检查图片上传是否为空
	var photo = $("#img_upload").val();
	if (!remarkImgId) {
		if (!photo) {
			common.alertInfoMsg("请选择图片上传");
			return;
		}
	}
	// 添加正在上传的面板提示
	var uploadMsg = bootbox.dialog({
		size : 'small',
		closeButton: false,
		message : '<div class="text-center" style="color:green;"><i class="fa fa-spin fa-spinner"></i>正在上传...</div>'
	});
	$("#areaImgForm").ajaxSubmit({
		type : "POST",
		url : "/ietlAdmin/ws/saveActiveImg",
		data : common.form2JosnStr("#areaImgForm"),
		contentType : "multipart/form-data;charset=UTF-8",
		success : function(data) {
			uploadMsg.modal('hide');
			$("#areaImgModal").modal("hide");
			$('#areaImgForm')[0].reset();
			$('#area_table').bootstrapTable('refresh');
			if(data){
				common.alertInfoMsg("图片修改成功!");
			}else{
				common.alertErrorMsg("图片修改失败!");
			}
			activemanage.initTable();
		},
		error : function(request) {
			uploadMsg.modal('hide');
			common.alertErrorMsg("图片修改失败!");
		}
	});
},

activemanage.initUploadImg = function() {
	document.getElementById('remarkImgId').innerHTML = "";
	document.getElementById('delImgText').innerHTML = "";
	$("#viewImgId").hide();
	$("#imgUploadDivId").show(); //删除之后显示上传域
	$("#img_upload").fileinput({
		language : 'zh', // 设置语言
		allowedFileExtensions : [ 'jpg', 'png' ],
		showUpload : false, // 是否显示上传按钮
		showCaption : true,// 是否显示标题
		 showCancel:false,
		browseClass : "btn btn-inverse", // 按钮样式
		previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
		maxFileSize : 102400000
	});
	activemanage.formEnd();
},
//阻止表单自动提交
activemanage.formEnd = function() {
	 $('#areaImgForm').bootstrapValidator({
	    	message: 'This value is not valid',
	            feedbackIcons: {//输入框不同状态，显示图片的样式
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            }
	        }).on('success.form.bv', function(e) {
	            // 阻止默认事件提交
	            e.preventDefault();
	        });
},
//搜索清空
activemanage.emptySearch = function(){
	$("#activeNameSrh").val("");
},
//点击添加活动按钮
activemanage.addActivebtn = function(){
	$("#activeModal").modal("show");
	activemanage.validator();
	$("#activeForm").data('bootstrapValidator').resetForm();
}
//点击确定按钮
activemanage.optActive = function(){

	$('#activeForm').data('bootstrapValidator').validate();
	if (!$('#activeForm').data('bootstrapValidator').isValid()) {
		return;
	}
	$("#activeForm").data('bootstrapValidator').resetForm();
	var activeCode = $("#activeCode").val();
	if(activeCode == null || activeCode==""){
		activemanage.addActive();
	}else{
		activemanage.updateActive();
	}
}
//添加活动
activemanage.addActive = function(){
	var active={
		"activeName" : $("#activeName").val(),
		"content" : $("#content").val(),
		"isHot" : $("#isHot").val(),
		"kinds" : $("#kinds").val(),
		"description" : $("#description").val()
	};
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/addActive",
		data : JSON.stringify(active),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			common.alertInfoMsg("添加成功!");
			activemanage.empty();
			$('#activeModal').modal('hide');
			activemanage.initTable();
		},
		error : function(request) {
			$('#activeModal').modal('hide');
			common.alertErrorMsg("添加失败!");
			activemanage.initTable();
		}
	});
},
//清空表单
activemanage.empty = function(){
	$("#activeCode").val("");
	$("#activeName").val("");
	$("#content").val("");
	$("#isHot").val("");
	$("#kinds").val("");
	$("#description").val("");
},
//修改带入参数
activemanage.updatebutn = function(activeCode){
	var active={
			"activeCode" : activeCode
	};
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/getActive",
		data: JSON.stringify(active),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$("#activeCode").val(data.rows[0].activeCode);
			$("#activeName").val(data.rows[0].activeName);
			$("#content").val(data.rows[0].content);
			$("#isHot").val(data.rows[0].isHot);
			$("#kinds").val(data.rows[0].kinds);
			$("#description").val(data.rows[0].description);
			$('#activeModal').modal('show');
		}
	});
},
//修改类别
activemanage.updateActive = function(){
	var category={
		"activeCode" : $("#activeCode").val(),
		"activeName" : $("#activeName").val(),
		"content" : $("#content").val(),
		"isHot" : $("#isHot").val(),
		"kinds" : $("#kinds").val(),
		"description" : $("#description").val()
	};
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/updateActive",
		data : JSON.stringify(category),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$('#activeModal').modal('hide');
			common.alertInfoMsg("修改成功!");
			activemanage.empty();
			activemanage.initTable();
		},
		error : function(request) {
			$('#activeModal').modal('hide');
			common.alertErrorMsg("修改失败!");
			activemanage.initTable();
		}
	});
},
activemanage.delactive = function(id){
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/deleteActive/" + id,
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if(data==0){
				common.alertInfoMsg("删除成功!");
				activemanage.initTable();
			}
			if(data==1){
				common.alertErrorMsg("id获取失败!");
				activemanage.initTable();
			}
		},
		error : function(request) {
			common.alertErrorMsg("删除失败!");
			activemanage.initTable();
		}
	});
},
activemanage.validator = function(){
	$('#activeForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {/* 验证 */
			activeName : {
				message : '活动名称不能为空',
				validators : {
					notEmpty : {
						message : '活动名称不能为空'
					}
				}
			},
			content : {
				message : '活动内容不能为空',
				validators : {
					notEmpty : {
						message : '活动内容不能为空'
					}
				}
			},
			isHot : {
				message : '是否热门活动不能为空',
				validators : {
					notEmpty : {
						message : '是否热门活动不能为空'
					}
				}
			},
			kinds : {
				message : '活动类型不能为空',
				validators : {
					notEmpty : {
						message : '活动类型不能为空'
					}
				}
			}
		}
	});
}
