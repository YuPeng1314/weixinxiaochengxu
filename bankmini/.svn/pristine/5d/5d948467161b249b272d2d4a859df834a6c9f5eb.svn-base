var datadict = {};

/**
 * 表格数据初始化
 */
datadict.initTable = function() {
	$('#datadict_table').bootstrapTable('destroy');
	$('#datadict_table').bootstrapTable({
		method: "post",
		url : "/ietlAdmin/ws/getValue",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		queryParams: queryDataParams,
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		onLoadSuccess : datadict.initSwitch,// 表格加载完成后再初始化这个方法
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : datadict.actionFormatter
		}, {
			field : 'dictionaryKey',
			title : '属性名',
			align : 'center',
		}, {
			field : 'dictionaryValue',
			title : '属性值',
			align : 'center',
			formatter : common.valueEncode
		},{
			field : 'lastUpdatedBy',
			title : '最后更新人',
			align : 'center',
		}, {
			field : 'lastUpdateDate',
			title : '最后更新时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			field : 'description',
			title : '描述',
			align : 'center',
			formatter : common.valueEncode
		}, {
			field : 'isValid',
			title : '状态标识',
			align : 'center',
			formatter : datadict.changeFormatter
		} ]

	});
}

//传递的参数
function queryDataParams(params) {
	 return  {
	    "limit":params.limit,
	    "offset": params.offset,
	    "dictionaryKey":$("#dictionaryKey_search").val(),
	    "description":$("#remark_search").val()
	  };
}

//查询
datadict.searchBtn = function(){
				 datadict.initTable();
}

//清空
datadict.empty = function(){
	$('#searchFrom')[0].reset();
}

//操作列的图标显示
datadict.actionFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		var edit = "<input type='button' id = 'update_btn' pri_url='/ws/updateDataDict' class='ico_a btn btn-info btn-xs ' title='修改' " +
		"onclick='datadict.updateDataDict("+row.id+")' value='改'>";
		return edit;
	} else {
		var edit = "<a  id = 'update_btn' ></a>";
		return edit;
	}
}


//有效无效的样式显示
datadict.changeFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		return "<input id= " + row.id + " pri_url='/ws/validDataDict'   name='isValid' type='checkbox' "
				+ " data-size='small' checked>";
	} else {
		return "<input id= " + row.id + " pri_url='/ws/validDataDict' name='isValid' type='checkbox' "
				+ " data-size='small'>";
	}
}

//有效无效的切换
datadict.initSwitch = function() {
	if ($('[name="isValid"]').length > 0) {
		$('[name="isValid"]')
				.bootstrapSwitch(
						{
							onText : "有效",
							offText : "失效",
							onColor : "success",
							offColor : "danger",
							wrapperClass : "privilege-hints",
							onSwitchChange : function(event, state) {
									datadict.validDataDict($(this).attr("id"),
											state);
							}
						});

	} else {
		setTimeout(datadict.initSwitch, 1000);
	}
	//权限切换
	privilege.privilegeControl();
}

//有效无效的切换方法
datadict.validDataDict = function(id, state) {
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/validDataDict/" + id + "/" + state,
		success : function(data) {
			if (data){
				$('#datadict_table').bootstrapTable('refresh');
				common.alertInfoMsg("切换成功!");
			}else {
				common.alertErrorMsg("删除失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("切换失败!");
		}
	});
}

//增加时弹出的模态框
datadict.showModel = function() {
	$("#addDatadictModal").modal("show");
	$('#addDatadictForm')[0].reset();
	$('#switch').show();
	$("#check_box").attr("checked", true);
	$("#check_box").prop("value", "1");
	$("#data_switch").val("1");
			datadict.Validator();
	    $("#addDatadictForm").data('bootstrapValidator').resetForm();
}	

//选中是否为显示值
datadict.radio = function(radioObj) {
	var radioCheck = $(radioObj).val();
	if ("1" == radioCheck) {
		$(radioObj).attr("checked", false);
		$(radioObj).val("0");
		$("#data_switch").val("0");
	} else {
		$(radioObj).attr("checked", true);
		$(radioObj).val("1");
		$("#data_switch").val("1");
	}
}

//修改数据
datadict.updateDataDict = function(id) {
		datadict.Validator();
	$("#addDatadictForm").data('bootstrapValidator').resetForm();
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/updateDataDict/" + id,
		success : function(data) {
			if (data != null) {
				$('#addDatadictModal').modal();
				$('#addDatadictForm')[0].reset();
				if(data.dataSwitch == 0){
					data.dictionaryValue=null;
				}
				$('#switch').hide();
				$("#addDatadictForm").fill(data, {
					styleElementName : 'none'
				});
			} else {
				common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");

			}
		},
		error : function(request) {
			common.alertErrorMsg("修改失败!");
		}
	});

}

//增加
datadict.addDataDict = function() {
	$('#addDatadictForm').data('bootstrapValidator').validate();
	if (!$('#addDatadictForm').data('bootstrapValidator').isValid()) {
		return;
	}
	$("#addDatadictForm").data('bootstrapValidator').resetForm();
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/addDataDict",
		data : common.form2JosnStr("#addDatadictForm"),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if(data == false){
				common.alertErrorMsg("属性名重复!");
			}else{
				$("#addDatadictModal").modal("hide");
				$('#addDatadictForm')[0].reset();
				$('#datadict_table').bootstrapTable('refresh');
				common.alertInfoMsg("操作成功!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}

//Key的验证
datadict.checkKey = function(dictionaryKey) {
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/checkKey",
		data: JSON.stringify({
			"dictionaryKey": dictionaryKey
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

//表单验证
datadict.Validator = function() {
	$('#addDatadictForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {/* 验证 */
			dictionaryKey : {
				message : 'Key不能为空',
				validators : {
					notEmpty : {
						message : 'Key不能为空'
					},
					regexp: { //只需加此键值对 
                        regexp: /^[a-zA-Z0-9_.\s]*$/,
                        message: '只能输入英文字母，数字，下划线，空格及点'
                    },
				}
			},
			dictionaryValue : {/* 键名和input name值对应 */
				message : 'Value不能为空',
				validators : {
					notEmpty : {/* 非空提示 */
						message : 'Value不能为空'
					},
				}
			},
			description : {
				message : '描述不能为空',
				validators : {
					notEmpty : {
						message : '描述不能为空'
					}
				}
			}
		}
	});
}