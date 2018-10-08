var menuManager = {
	initTable : function() {
		$('#menu_table').bootstrapTable('destroy');
		$('#menu_table').bootstrapTable({
			method: "post",
			url : "/ietlAdmin/ws/getMenus",// 请求后台的URL（*）
			toolbar : '#toolbar',
			cache : false, // 不缓存
			// height : 530, // 设置高度，会启用固定表头的特性
			striped : true, // 隔行加亮
			pagination : true, // 开启分页功能
			pageSize : 15, // 设置默认分页为 50
			pageList : [ 15, 25, 50, 100, 200 ], // 自定义分页列表
			sidePagination : "server",// 设置在哪里进行分页
			// search : false, // 开启搜索功能
			showColumns : false, // 开启自定义列显示功能
			showRefresh : false, // 开启刷新功能
			minimumCountColumns : 2, // 设置最少显示列个数
			clickToSelect : true, // 单击行即可以选中
			sortName : 'modifyDate', // 设置默认排序为 name
			sortOrder : 'desc', // 设置排序为反序 desc
			smartDisplay : true, // 智能显示 pagination 和 cardview 等
			onLoadSuccess : privilege.privilegeControl,
			queryParams : menuManager.queryDataParams,
			columns : [ {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : "10%",
				formatter : menuManager.actionFormatter
			}, {
				field : 'mCode',
				title : '栏目编码',
				align : 'center'
			}, {
				field : 'mCname',
				title : '栏目名称',
				align : 'center',
				formatter : common.valueEncode
			}, {
				field : 'mPid',
				title : '栏目父编码',
				align : 'center'
			}, {
				field : 'mUrl',
				title : '栏目URL',
				align : 'left',
				formatter : common.valueEncode
			}, {
				field : 'mIcon',
				title : '栏目图标',
				align : 'left',
				formatter : common.valueEncode
			}, {
				field : 'lastUpdatedBy',
				title : '最后更新人',
				align : 'center',
			}, {
				field : 'lastUpdateDate',
				title : '最后修改日期',
				align : 'center',
				formatter : function(text) {
					return new Date(text).format("yyyy-MM-dd");
				}
			}, {
				field : 'description',
				title : '描述',
				align : 'center',
				formatter : common.valueEncode
			} ]
		});
	},
	queryDataParams : function(params) {
		return {
			"limit" : params.limit,
			"offset" : params.offset,
			"mCname" : $("#mCnameSearch").val(),
			"mPid" : $("#mPidSearch").val()
		};
	},
	searchBtn : function() {
		menuManager.initTable();
	},
	emptySearch : function() {
		$('#searchFrom')[0].reset();
	},
	actionFormatter : function(value, row, index) {
		
		var edit = "<input type='button' id = 'update_btn' pri_url='/ws/updateMenuItem' class='ico_a btn btn-info btn-xs ' title='修改' " +
		"onclick='menuManager.updateUI("+row.id+")' value='改'>";
		var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteMenuItem' class='ico_a btn btn-danger btn-xs ' title='删除' " +
		"onclick='menuManager.deleteMenuItem("+row.id+")' value='删'>";
		// 顶级目录不能编辑，要么删除
		if (row.mPid == 0) {
			return del;
		} else {
			return edit + "  " + del;
		}
	},
	showModel : function() {
		$("#addMenuItemModal").modal("show");
		$('#menuForm')[0].reset();
		menuManager.Validator();
		$("#menuForm").data('bootstrapValidator').resetForm();
		// 初始化权限下拉？？
		$.get("/ietlAdmin/ws/getAdminAuth", function(data) {
			$.each(data, function(i, value) {
				$("#authorityNameMenu").append("<option value='" + value + "'>" + value + "</option>");
			});
			$("#authorityNameMenu").selectpicker('refresh');
		}, "json");
	},

	updateUI : function(id) {
		menuManager.Validator();
		$("#menuForm").data('bootstrapValidator').resetForm();
		bootbox.setLocale("zh_CN");
		$.ajax({
			type : "GET",
			url : "/ietlAdmin/ws/updateMenuItem/" + id,
			success : function(data) {
				if (data != null) {
					$("#addMenuItemModal").modal("show");
					$("#meneH4Id").text("修改菜单栏目");
					$('#menuForm')[0].reset();
					$("#menuForm").fill(data, {
						styleElementName : 'none'
					});
					// 回填下拉框
					$("#authorityNameMenu").empty();
					$.ajaxSetup({  
						    async : false  
					}); 
					$.get("/ietlAdmin/ws/getAdminAuth", function(data) {
						$.each(data, function(i, value) {
							$("#authorityNameMenu").append("<option value='" + value + "'>" + value + "</option>");
						});
						$("#authorityNameMenu").selectpicker('refresh');
					}, "json");
					// 选择要选中的下拉选项(延迟执行是为了是下拉框已经全部渲染完成)
					setTimeout("$(\"#authorityNameMenu\").selectpicker('val', '" + data.authorityName + "')", 300);

				} else {
					common.alertErrorMsg("您想要修改的记录可能不存在，请刷新重试!");
				}
			},
			error : function(request) {
				common.alertErrorMsg("您想要修改的记录可能不存在，请刷新重试!");
			}
		});
	},
	deleteMenuItem : function(id) {
		bootbox.setLocale("zh_CN");
		bootbox.confirm("你确定删除吗?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "/ietlAdmin/ws/deleteMenuItem/" + id,
					success : function(data) {
						if (data) {
							common.alertInfoMsg("删除成功!");
							$('#menu_table').bootstrapTable('refresh');
							// 刷新当前页面
							menuManager.refresh();
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
	},
	addMenuItem : function() {
		$('#menuForm').data('bootstrapValidator').validate();
		if (!$('#menuForm').data('bootstrapValidator').isValid()) {
			return;
		}
		$("#menuForm").data('bootstrapValidator').resetForm();
		bootbox.setLocale("zh_CN");
		$.ajax({
			type : "POST",
			url : "/ietlAdmin/ws/addMenuItem",
			data : common.form2JosnStr("#menuForm"),
			contentType : "application/json;charset=utf-8",
			success : function(data) {
				if (data) {
					$("#addMenuItemModal").modal("hide");
					$('#menuForm')[0].reset();
					$('#menu_table').bootstrapTable('refresh');
					// 刷新当前页面
					menuManager.refresh();
					common.alertInfoMsg("操作成功!");
				} else {
					common.alertErrorMsg("操作失败!");
				}
			},
			error : function(request) {
				common.alertErrorMsg("操作失败!");
			}
		});
	},
	refresh : function() {
		window.location.reload();
	},
	Validator : function() {
		$('#menuForm').bootstrapValidator({
			message : 'This value is not isValid',
			feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {/* 验证 */
				mCname : {
					message : '栏目名称不能为空',
					validators : {
						notEmpty : {
							message : '栏目名称不能为空'
						}
					}
				},
				mPid : {/* 键名和input name值对应 */
					message : '菜单父节点不能为空',
					validators : {
						notEmpty : {/* 非空提示 */
							message : '菜单父节点不能为空'
						},
						regexp : {
							regexp : /^[0-9]*$/,
							message : '只能输入数字'
						}
					}
				},
				mIcon : {/* 键名和input name值对应 */
					message : '栏目图标不能为空',
					validators : {
						notEmpty : {/* 非空提示 */
							message : '栏目图标不能为空'
						}
					}
				},
				authorityName : {/* 键名和input name值对应 */
					message : '拥有权限不能为空',
					validators : {
						notEmpty : {/* 非空提示 */
							message : '拥有权限不能为空'
						}
					}
				}
			}
		});
	}
}

$(function() {
	menuManager.initTable();
});