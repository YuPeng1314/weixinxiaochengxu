var part = {};

/**
 * 角色信息表
 */
part.initTable = function() {
	$('#searchFrom')[0].reset();
	$('#part_table').bootstrapTable('destroy');
	$('#part_table').bootstrapTable({
		method : "post",
		url : "/ietlAdmin/ws/getRole",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		queryParams : queryRoleParam,
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		onLoadSuccess : part.initSwitch,// 表格加载完成后再初始化这个方法
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : part.actionFormatter
		}, {
			field : 'roleName',
			title : '角色名称',
			align : 'center',
			formatter : common.valueEncode
		}, {
			field : 'roleDesc',
			title : '角色描述',
			align : 'center',
			formatter : common.valueEncode
		}, {
			field : 'type',
			title : '角色类型',
			align : 'center',
			formatter : part.roleType
		}, {
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
			field : 'isValid',
			title : '状态标识',
			align : 'center',
			formatter : part.changeFormatter
		} ]

	});
}

// 传递的参数
function queryRoleParam(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
	};
}

// 角色类型
part.roleType = function(value, row, index) {
	var value;
	if (value == 1) {
		value = "系统管理员";
	}
	if (value == 2) {
		value = "专家导师";
	}
	if (value == 3) {
		value = "普通用户";
	}
	if (value == 4) {
		value = "甲方管理员";
	}
	if (value == 5) {
		value = "平台管理员";
	}
	if (value == 6) {
		value = "甲方认证用户";
	}
	if (value == 7) {
		value = "项目管理员";
	}
	if (value == 8) {
		value = "普通项目角色";
	}
	return value;
}

// 角色操作列的图标
part.actionFormatter = function(value, row, index) {
	if (row.isValid == '1' && row.type != '1') {
		var edit = "<input type='button' id = 'update_btn' pri_url='/ws/updateRole' class='ico_a btn btn-info btn-xs ' title='修改' " +
		"onclick='part.updateRole("+row.roleId+")' value='改'>";
		var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteRole' class='ico_a btn btn-danger btn-xs ' title='删除' " +
		"onclick='part.deleteRole("+row.roleId+")' value='删'>";
		var tem = "<input type='button' id = 'skip_btn'  class='ico_a btn btn-info btn-xs ' title='用户维护' " +
		"onclick='part.skip(\""+ row.roleId+ "\",\""+ row.roleName+ "\")' value='维'>";
		var power = "<input type='button' id = 'skip_power_btn' pri_url='/ws/getAuthority' class='ico_a btn btn-info btn-xs ' title='角色权限' " +
		"onclick='part.skip_power(\""+ row.roleId + "\",\""+ row.roleName + "\")' value='权'>";
		return edit + "  " + del + "  " + power + "  " + tem;
	} else if (row.isValid == '1' && row.type == '1') {
		var edit = "<input type='button' id = 'update_btn' pri_url='/ws/updateRole' class='ico_a btn btn-info btn-xs ' title='修改' " +
		"onclick='part.updateRole("+row.roleId+")' value='改'>";
		var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteRole' class='ico_a btn btn-danger btn-xs ' title='删除' " +
		"onclick='part.deleteRole("+row.roleId+")' value='删'>";
		var tem = "<input type='button' id = 'skip_btn'  class='ico_a btn btn-info btn-xs ' title='用户维护' " +
		"onclick='part.skip(\""+ row.roleId+ "\",\""+ row.roleName+ "\")' value='维'>";
		return edit + "  " + del + "  " + tem;
	} else if (row.isValid == '0') {
		var edit = "<a  id = 'update_btn' ></a>";
		var del = "<a  id = 'delete_btn'></a>";
		var tem = "<a id = 'skip_btn'></a>";
		var power = "<a id = 'skip_power_btn'></a>";
		return edit + "  " + del + "  " + power + "  " + tem;
	}
}

// 角色有效无效
part.changeFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		return "<input id= " + row.roleId
				+ " pri_url='/ws/validRole'   name='isValid' type='checkbox' "
				+ " data-size='small' checked>";
	} else {
		return "<input id= " + row.roleId
				+ " pri_url='/ws/validRole'  name='isValid' type='checkbox' "
				+ " data-size='small'>";
	}
}

/* 角色有效无效的切换 */
part.initSwitch = function() {
	if ($('[name="isValid"]').length > 0) {
		$('[name="isValid"]').bootstrapSwitch({
			onText : "有效",
			offText : "失效",
			onColor : "success",
			offColor : "danger",
			wrapperClass : "privilege-hints",
			onSwitchChange : function(event, state) {
				part.validRole($(this).attr("id"), state);
			}
		});

	} else {
		setTimeout(part.initSwitch, 1000);
	}
	// 权限切换
	privilege.privilegeControl();
}

/* 角色有效无效切换方法 */
part.validRole = function(id, state) {
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/validRole/" + id + "/" + state,
		success : function(data) {
			if (data) {
				$('#part_table').bootstrapTable("refresh");
				common.alertInfoMsg("切换成功!");
			}
			if (!data) {
				common.alertErrorMsg("切换失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("切换失败!");
		}
	});
}

/* 增加角色的模态框 */
part.showModel = function() {
	$("#addRoleModal").modal("show");
	$('#addRoleForm')[0].reset();
	
	var type = $("#type").val();
	part.roleTypeShow(type);
	part.Validator();
	$("#addRoleForm").data('bootstrapValidator').resetForm();
}

/* 角色类型展示 */
part.roleTypeShow = function(type) {
	$("#type").empty();
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/findRoleType",
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			var json = eval(data.types);
			for (var i = 0; i < json.length; i++) {
				$('#type').append("<option value=" + json[i].key + ">" + json[i].value + "</option>");
			}
			$('#type').selectpicker('refresh');
			if(type){
				$('#type').selectpicker('val',type);
			}
		}
	});
}




/* 增加角色 */
part.addRole = function() {
	$('#addRoleForm').data('bootstrapValidator').validate();
	if (!$('#addRoleForm').data('bootstrapValidator').isValid()) {
		return;
	}
	$("#addRoleForm").data('bootstrapValidator').resetForm();
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/addRole",
		data : common.form2JosnStr("#addRoleForm"),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$("#addRoleModal").modal("hide");
			$('#addRoleForm')[0].reset();
			$('#part_table').bootstrapTable('refresh');
			common.alertInfoMsg("操作成功!");
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}

/* 修改角色 */
part.updateRole = function(roleId) {
	part.Validator();
	$("#addRoleForm").data('bootstrapValidator').resetForm();
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/updateRole/" + roleId,
		success : function(data) {
			if (data) {
				$('#addRoleModal').modal("show");
				part.roleTypeShow(data.type);
				if(data.type==7 || data.type==8) {
					$("#projectFlagDiv").show();
					$('#projectFlag').selectpicker('val',data.projectFlag);
					
				} else {
					$("#projectFlagDiv").hide();
					$('#projectFlag').selectpicker('val',"");
				}
				$("#addRoleForm").fill(data, {
					styleElementName : 'none'
				});
			} else {
				common.alertErrorMsg("您想要修改的记录可能不存在，请刷新重试!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("修改失败!");
		}
	});

}

/* 删除角色 */
part.deleteRole = function(roleId) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("此角色下的用户将会删除，你确定删除角色吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/deleteRole/" + roleId,
				success : function(data) {
					if (data) {
						$('#part_table').bootstrapTable('refresh');
						common.alertInfoMsg("删除成功!");
					} else {
						common.alertErrorMsg("删除失败!");
					}
				},
				error : function() {
					common.alertErrorMsg("删除失败!");
				}
			});
		}
	});
}

// 跳转到角色下的用户表 （part.roleTable）
part.skip = function(roleId, roleName) {
	$("#RoleUserModal").modal("show");
	document.getElementById('roleName_search').innerHTML = "当前角色名称：" + roleName;
	$("#ROLE_ID_search").val(roleId);
	part.roleTable();

}

/**
 * 查询用户所含有的角色信息
 */
part.queryUser = function() {
	$("#ROLE_ID_search").val();
	part.userTable
}

// 清空查询
part.fromEmpty = function() {
	$('#searchFrom')[0].reset();
}

// 用户所含有的角色信息
part.userTable = function() {
	$("#searchUserModal").modal("show");
	$('#RoleUser_table').bootstrapTable('destroy');
	$('#RoleUser_table').bootstrapTable({
		method : "post",
		url : "/ietlAdmin/ws/getRoleUser",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		queryParams : queryRoleuserParam,
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		onLoadSuccess : part.UserinitSwitch, // 表格加载完成后再初始化这个方法
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : part.actionUserFormatter
		}, {
			field : 'userName',
			title : '用户名称',
			align : 'center',
		}, {
			field : 'roleName',
			title : '角色名称',
			align : 'center',
		}, {
			field : 'roleDesc',
			title : '角色描述',
			align : 'center',
		}, {
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
		}, {
			field : 'isValid',
			title : '状态标识',
			align : 'center',
			formatter : part.changeUserFormatter
		} ]
	});
}

// 传递的参数
function queryRoleuserParam(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"userName" : $("#user_name").val()
	};
}

// 角色下的用户（用户下的角色）操作列的图标
part.actionUserFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		var del ="<input type='button' id = 'deleteUser_btn' pri_url='/ws/deleteRoleUser' class='ico_a btn btn-danger btn-xs ' title='删除' " +
		"onclick='part.deleteRoleUser("+row.urId+")' value='删'>";
		return del;
	} else {
		var del = "<a  id = 'deleteUser_btn'></a>";
		return del;
	}
}

// 角色下的用户（用户下的角色）有效无效
part.changeUserFormatter = function(value, row, index) {
	if (row.isValid == '1') {
		return "<input id= "
				+ row.urId
				+ " pri_url='/ws/validRoleUser'  name='isValid' type='checkbox' "
				+ " data-size='small' checked>";
	} else {
		return "<input id= "
				+ row.urId
				+ " pri_url='/ws/validRoleUser' name='isValid' type='checkbox' "
				+ " data-size='small'>";
	}

}

// 角色用户(用户角色)有效无效的切换
part.UserinitSwitch = function() {
	if ($('[name="isValid"]').length > 0) {
		$('[name="isValid"]').bootstrapSwitch({
			onText : "有效",
			offText : "失效",
			onColor : "success",
			offColor : "danger",
			wrapperClass : "privilege-hints",
			onSwitchChange : function(event, state) {
				part.validRoleUser($(this).attr("id"), state);
			}
		});

	} else {
		setTimeout(part.UserinitSwitch, 1000);
	}
	// 权限切换
	privilege.privilegeControl();
}

// 角色用户(用户角色)有效无效的切换方法
part.validRoleUser = function(id, state) {
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/validRoleUser/" + id + "/" + state,
		success : function(data) {
			if (data) {
				$('#RoleUser_table').bootstrapTable("refresh");
				$('#PartUser_table').bootstrapTable("refresh");
				common.alertInfoMsg("切换成功!");
			} else {
				common.alertErrorMsg("切换失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("切换失败!");
		}
	});
}

/**
 * 查询角色所含有的用户信息
 */
part.roleTable = function() {
	$('#PartUser_table').bootstrapTable('destroy');
	$('#PartUser_table').bootstrapTable({
		method : "post",
		url : "/ietlAdmin/ws/getRoleUser",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		queryParams : queryRoletableParam,
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		onLoadSuccess : part.UserinitSwitch,// 表格加载完成后再初始化这个方法
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : part.actionUserFormatter
		}, {
			field : 'userCode',
			title : '用户账号',
			align : 'center',
		}, {
			field : 'userName',
			title : '用户名称',
			align : 'center',
		}, /*{
			field : 'description',
			title : '描述',
			align : 'center',
		},*/ {
			field : 'isValid',
			title : '状态标识',
			align : 'center',
			formatter : part.changeUserFormatter
		} ]

	});
}

// 传递的参数
function queryRoletableParam(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"userName" : $("#user_name_user").val(),
		"userCode" : $("#user_code_user").val(),
		"roleId" : $("#ROLE_ID_search").val(),
	};
}

// 查询角色下的用户信息
part.searchBtn = function() {
	part.roleTable();
}

// 清空查询
part.rotaEmpty = function() {
	$('#searchRoleUserFrom')[0].reset();
}

// 删除角色用户
part.deleteRoleUser = function(urId) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("你确定删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/deleteRoleUser/" + urId,
				success : function(data) {
					if (data) {
						$('#RoleUser_table').bootstrapTable('refresh');
						$('#PartUser_table').bootstrapTable('refresh');
						common.alertInfoMsg("删除成功!");
					} else {
						common.alertErrorMsg("删除失败!");
					}
				},
				error : function() {
					common.alertErrorMsg("删除失败!");
				}
			});
		}
	});
}

/**
 * 筛选之后的用户信息表
 */
part.User = function() {
	$('#user_table').bootstrapTable('destroy');
	$('#user_table').bootstrapTable({
		method : "post",
		url : "/ietlAdmin/ws/filterUser",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		clickToSelect : true, // 是否启用点击选中行
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		queryParams : queryFilterUser,
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		columns : [ {
			checkbox : true
		}, {
			field : 'userCode',
			title : '用户账号',
			align : 'center',
		}, {
			field : 'userName',
			title : '姓名',
			align : 'center',
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			formatter : part.actionType
		}, {
			field : 'dateOfBirth',
			title : '出生日期',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd");
			}
		}/*, {
			field : 'phone',
			title : '联系电话',
			align : 'center',
		}, {
			field : 'description',
			title : '描述',
			align : 'center',
		},*/ ]
	});
}

// 性别类型
part.actionType = function(value, row, index) {
	var value;
	if (value == 1) {
		value = "男";
	}
	if (value == 0) {
		value = "女";
	}
	return value;
}

// 传递的参数
function queryFilterUser(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"userCode" : $("#userCode_user").val(),
		"userName" : $("#userName_user").val(),
		"roleId" : $("#ROLE_ID_search").val()
	};
}

/* 查询用户信息 */
part.searchUser = function() {
	part.User();
}

// 清空查询的用户信息
part.cleanUser = function() {
	$('#searchUserFrom')[0].reset();
}

/* 选择用户提交到角色 */
part.sumbit = function() {
	var roleId = $("#ROLE_ID_search").val();
	var data = $('#user_table').bootstrapTable('getSelections');
	if (data.length == 0) {
		common.alertInfoMsg("请选择用户!");
		return;
	}
	var tem = [];
	for (var i = 0; i < data.length; i++) {
		tem[tem.length] = data[i].userCode;
	}
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/addRoleUser/" + roleId + '/' + tem,
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$("#addRoleUserModal").modal("hide");
			$(function() {
				part.roleTable();
			});
			common.alertInfoMsg("操作成功!");
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}

/* 增加用户到角色中 */
part.addUserModel = function() {
	$("#addRoleUserModal").modal("show");
	part.User();
}

/**
 * 跳转到所有权限配置
 */
part.skip_power = function(roleId, roleName) {
	$("#poewrShow").empty();
	$("#poewrShow").append("<div class='priviImage'></div>");
	$("#saveBtn").attr("disabled",true);
	$("#RolePowerModal").modal("show");
	document.getElementById('roleName_power').innerHTML = roleName;
	$("#ROLE_ID_power").val(roleId);
	$.ajax({
		type : "get",
		url : "/ietlAdmin/ws/getAuthority",
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			//得到全部的函数集合
			var funAllArr = part.handleTree(data);
			
			$.when.apply($, funAllArr).then(
				function() {
					$(".priviImage").hide();
					$("#saveBtn").attr("disabled",false);
				}, function() {
					common.alertErrorMsg("查询失败!");
				}
			);

		},
		error : function(request) {
			common.alertErrorMsg("查询失败!");
		}
	});
}


//处理函数树
part.handleTree = function(data) {
	var arr = eval(data.rows);
	var res = [];
	var num = [];
	var json = {};
	for (var i = 0; i < arr.length; i++) {
		if (!json[arr[i].authorityName]) {
			res.push(arr[i].authorityName);
			json[arr[i].authorityName] = 1;
		}
		if (!json[arr[i].authorityId]) {
			num.push(arr[i]);
			json[arr[i].authorityId] = 1;
		}
	}
	
	//用来存放函数
	var funPush = new Array();
	for (var n = 0; n < res.length; n++) {   	
			var ul = document.getElementById("poewrShow");
			// 添加 li
			var div = document.createElement("div");
			// 设置li 属性，如 id
			div.setAttribute("id", "power_" + n);
			div.setAttribute("class", "ztree");
			div.innerHTML = res[n];
			ul.appendChild(div);
		
			var div_parent = document.getElementById("power_" + n + "");
			var div_new = document.createElement("div");
			div_new.setAttribute("id", "div_new" + n);
			div_new.setAttribute("class", "ztree");
			div_parent.appendChild(div_new);
		
			var zNodes = [];
			$.each(num, function(m, val) {
				if (val.authorityName == res[n]) {
					if (val.authorityMark == 1 || val.authorityMark == 8) {
						var authMarkName = "Read Permission";
					} else if (val.authorityMark == 2) {
						var authMarkName = "Add Permission";
					} else if (val.authorityMark == 3) {
						var authMarkName = "Delete Permission";
					} else if (val.authorityMark == 4) {
						var authMarkName = "Update Permission";
					} else if (val.authorityMark == 5) {
						var authMarkName = "Upload Permission";
					} else if (val.authorityMark == 6) {
						var authMarkName = "Download Permission";
					} else if (val.authorityMark == 7) {
						var authMarkName = "Other Permission";
					}
		
					var zNode = {}
					zNode.id = val.authorityId;
					zNode.pId = val.authorityName;
					zNode.name = authMarkName;
					zNodes.push(zNode);
				}
			});
			$.each(arr,
					function(j, arrj) {
						if (arrj.authorityName == res[n]) {
							var zNode_new = {}
							zNode_new.id = arrj.resourceId + '_'
									+ arrj.authorityId;
							zNode_new.pId = arrj.authorityId;
							zNode_new.name = arrj.resourceName;
							zNode_new.nocheck = true;
							zNodes.push(zNode_new);
						}
					});
			var setting = {
				check : {
					enable : true,
					chkboxType : {
						"Y" : "ps",
						"N" : ""
					}
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};
			$.fn.zTree.init($("#div_new" + n + ""), setting, zNodes);
			var zTree = $.fn.zTree.getZTreeObj("div_new" + n + "");
			funPush.push(part.initZtee(zTree));
	}
	
	return funPush;
};



// 存在的角色权限
part.initZtee = function(zTree) {
	//延迟函数
	var dtd = $.Deferred();
	
	var roleId = $("#ROLE_ID_power").val();
	$.ajax({
		type : "get",
		url : "/ietlAdmin/ws/getRoleAuthority/" + roleId,
		success : function(data) {
			var json = eval(data.rows);
			var tmp = [];
			for ( var m in json) {
				tmp[json[m].authorityId] = json[m].resourceId;
			}
			// 再把键和值的位置再次调换
			var tmparr = [];
			for ( var n in tmp) {
				tmparr.push(n);
			}
			for (var i = 0; i < json.length; i++) {
				var authorityId = tmparr[i]
				var resourceId = json[i].resourceId;
				var node = zTree.getNodeByParam("id", authorityId);
				if (node == null) {
					continue;
				}
				node.checked = true;
			}
			zTree.refresh();
			
			//操作完成
			dtd.resolve();
		},
		error:function() {//错误就返回
			//延迟函数
			dtd.reject();
		}
		
	});
	return dtd.promise();
}

// 保存修改后的角色权限
part.saveNodeId = function() {
	var tem = "A";
	// 查询div根目录下面以div_new开头的树结构
	$("div[id^='div_new']").each(function(i, obj) {
		// 获取两个树结构的ID
		var currentZtree = $.fn.zTree.getZTreeObj($(obj).attr('id'));
		// 通过树结构ID查找树下面被勾选中的ID
		var nodes = currentZtree.getCheckedNodes(true);

		for (var i = 0; i < nodes.length; i++) {
			tem += "," + nodes[i].id;
		}
	});
	var roleId = $("#ROLE_ID_power").val();
	$.ajax({
		type : "post",
		url : "/ietlAdmin/ws/deleteRoleAuthority/" + roleId + '/' + tem,
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if (data) {
				common.alertInfoMsg("操作成功!");
				$("#RolePowerModal").modal("hide");
			} else {
				common.alertErrorMsg("操作失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}

/**
 * 增加或者修改角色时的表单验证
 */
part.Validator = function() {
	$('#addRoleForm').bootstrapValidator({
		message : 'This value is not isValid',
		feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {/* 验证 */
			roleName : {
				message : '角色名称不能为空',
				validators : {
					notEmpty : {
						message : '角色名称不能为空'
					},
					regexp : { // 只需加此键值对
						regexp : /^[a-zA-Z0-9_.\s]*$/,
						message : '只能输入英文字母，数字，下划线，空格及点.'
					}
				}
			},
			roleDesc : {/* 键名和input name值对应 */
				message : '角色描述不能为空',
				validators : {
					notEmpty : {/* 非空提示 */
						message : '角色描述不能为空'
					}
				}
			},
			type : {/* 键名和input name值对应 */
				validators : {
					notEmpty : {
						message : '请选择角色类型'
					}
				}
			},
			projectFlag:{
				validators : {
					notEmpty : {
						message : '请选择对应项目'
					},
					callback : {
						callback : function(value, validator, $field) {
							var tmpType = $("#type").val();
							if(tmpType!=7 && tmpType!=8) {
								return {
									valid : true
								};
							}
							
							return {
								valid : false,
								message:"请选择对应的项目"
							};
						}
					}
				},
				
			}
		}
	});
}