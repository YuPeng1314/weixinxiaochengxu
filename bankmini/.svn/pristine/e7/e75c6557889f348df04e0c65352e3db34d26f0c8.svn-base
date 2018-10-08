var courseEdit = {};


courseEdit.initTable = function() {
	$('#courseShow_table').bootstrapTable('destroy');
	$('#courseShow_table').bootstrapTable({
		method : "post",
		url : "/ietlAdmin/ws/getCourseware",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		queryParams : queryCourseEdit,
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		onLoadSuccess:function(){ 
			courseEdit.initSwitch();
			privilege.privilegeControl();
		},
		rowStyle: function (row, index) {
             // 这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
             var strclass = "";
             if (row.isValid == "0") {
                 strclass = 'warning';// 还有一个active
             }
             else if (row.isValid == "1") {
                 strclass = 'success';
             }
             else {
                 return {};
             }
             return { classes: strclass }
        },
		columns : [  {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter : courseEdit.actionFormatter
		}, {
			field : 'resourceName',
			title : '资源名称',
			align : 'center',
			formatter : common.valueEncode
		}, 
		{
			field : 'courseNames',
			title : '课程名称',
			align : 'center',
			formatter : courseEdit.handleContent
		}, {
			field : 'isPublic',
			title : '是否公开',
			align : 'center',
			formatter:function(value) {
				if(value=='0') {
					return '收费';
				} else if(value=='1') {
					return '公开';
				}
			}
		}, {
			field : 'resourceSize',
			title : '资源大小',
			align : 'center',
			formatter : courseEdit.resourceSize
		}, {
			field : 'resourceLength',
			title : '时长',
			align : 'center',
			formatter : courseEdit.resourceLength
		}, {
			field : 'lastUpdatedName',
			title : '上传人',
			align : 'center',
		}, {
			field : 'lastUpdateDate',
			title : '上传日期',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		},{
			field : 'isValid',
			title : '状态标识',
			align : 'center',
			formatter :courseEdit.changeFormatter
		}]
	});
}



//传递的参数
function queryCourseEdit(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"search" : $("#resource_name_search").val(),
		"isPublic" : $("#isSearchPublic").val()/*,
		"directoryCode" : $("#dircCodeHidden").val(),
		"resourceType" : $("#resourceType").val()*/
	};
} 



//查询
courseEdit.searchBtn = function(){
	courseEdit.initTable();
}

//清空
courseEdit.restBtn = function(){
	$('#searchFrom')[0].reset();
}

courseEdit.handleContent = function(value, row, index) {
	var content = common.htmlEncode(value);
	var tempValue = $('<div/>').text(content).html();
	var result = "";
	if (tempValue.length >= 6) {
		result = tempValue.substring(0, 6) + "...";
	} else {
		result = tempValue;
	}
	return "<a style='cursor:pointer;text-decoration:none' id='conten" + index + "' onmouseover='courseEdit.overCell(this.id,\"" + escape(tempValue)
			+ "\")'  onmouseout='courseEdit.outCell(\"" + index + "\")'>" + result + "</a>";
}

courseEdit.overCell = function(id, tempValue){
	var content =  unescape(tempValue);
	layer.tips(content, "#" + id, {
		tips : [ 2, '#3595CC' ],
		time : 10000
	});
}

courseEdit.outCell = function(index){
	layer.closeAll('tips');
}
 
//资源大小
courseEdit.resourceSize = function(value, row, index) {
	if(!value){
		return "--"
	}else{
		var resourceSize;
		resourceSize = value + "KB";
		return resourceSize;
	}
	
}

//资源时长（文档与视频不同）
courseEdit.resourceLength = function(value, row, index) {
	if(!value){
		return "--"
	}else{
		var resourceLength;
		resourceLength = value + "秒";
		return resourceLength;
	}
}



courseEdit.actionFormatter = function(value, row, index) {
	if(row.isValid == '0'){
		var edit = "<input type='button' id = 'update_btn'  class='ico_a btn btn-info btn-xs ' title='修改' " +
		"onclick='courseEdit.updateCourse("+ row.resourceId + ")' value='改'>";
		var del = "<input type='button' id = 'delete_btn'  class='ico_a btn btn-danger btn-xs ' title='删除' " +
		"onclick='courseEdit.deleteVideo("+row.resourceId+")' value='删'>";
			return edit + "  " + del ;
	}else{
		return "--";
	}
}

$(function() {
	$("#choseCatalogName").click(function(e){
		if ($("#treeCatalog").is(":hidden")) {
			$("#treeCatalog").show();
		} else {
			$("#treeCatalog").hide();
		}
		e.stopPropagation();
	});
	
	$("#treeCatalog").on("mouseleave", function() {
		$("#treeCatalog").hide();
	});
	
	
	// 树加载
	courseEdit.catalogtree("treeCatalog");
});

	
courseEdit.updateCourse = function(resourceId){
	courseEdit.Validator();
	$("#updateCourseForm").data('bootstrapValidator').resetForm();
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/getCoursewareInfo",
		data : JSON.stringify({
			"resourceId":resourceId
		}),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if (data.rows != null) {
				
				var courseArr = data.rows.courseCodes;
				$("#isPublic").val(data.rows.isPublic);
				//得到目录code
		 		var jsonStr = "";
		 		for(var i=0; i<courseArr.length; i++) {
		 			jsonStr += "catalogCodes=" + courseArr[i];
		 			if(i !== courseArr.length-1) {
		 				jsonStr += "&";
		 			}
		 		}
				
				
				$('#updateCourseModal').modal();
				$("#testimgname").attr('src',"");
				$("#updateCourseForm").fill(data.rows, {
					styleElementName : 'none'
				});
				
				
				var courseCodes = courseArr.join(",");
				$("#fileCatalogCode").val(courseCodes);
				
				var timestamp = new Date().getTime();
					$("#viewImgId").show();
					if(data.rows.imgName!=null) {
						$("#testimgname").attr('src',data.ip+data.videoUrl+data.rows.imgName+"?dateStr="+timestamp);
						$("#remarkImgId").text(data.rows.imgName.substring(data.rows.imgName.lastIndexOf('/')+1));
						$("#delImgText").text("更换");
						$("#imgUploadDivId").hide(); //图片路径
					} else {
						courseEdit.delServerImg();
					}
					
					//初始化树结构
					
					//目录名称
					var fileNameArr = new Array();
			 		var fileAllNodes = $('#treeCatalog').treeview('getEnabled');
			 		$('#treeCatalog').treeview('collapseAll', { silent: true });
			 		
			 		/*$('#treeCatalog').treeview('unselectNode', allNodes);*/
			 		for(var i=0; i<fileAllNodes.length; i++) {
			 			/*allNodes[i].state.selected = false;*/
			 			if(fileAllNodes[i].catalogLevel==3) {
			 				fileAllNodes[i].icon="glyphicon glyphicon-unchecked";
			 				fileAllNodes[i].backColor = '';
			 			}
			 			for(var j=0;j <courseArr.length; j++) {
			 				if(fileAllNodes[i].catalogCode==courseArr[j] && fileAllNodes[i].catalogLevel==3) {
			 					fileAllNodes[i].icon="glyphicon glyphicon-check";
			 					fileAllNodes[i].backColor = '#A4D3EE';
					 			fileNameArr.push(fileAllNodes[i].text);
			 				}
			 			}
			 		}
			 		$("#fileCatalogName").val(fileNameArr);
								 		
			 		
			} else {
				common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("修改失败!");
		}
	});
}


//目录树
courseEdit.catalogtree = function(generObj){
	$.ajax({
		type: 'GET',
		url: "/ietlAdmin/ws/getCatalogTree",
		async : false,
		contentType : "application/json;charset=utf-8",
	 	success:function(dataStr){
	 		if(dataStr){
 				$('#' + generObj).treeview({
					data : dataStr,
					levels : '1',
					backColor : '#eee',
					multiSelect:true,
					onNodeSelected :function(event, node) {
						if(node.catalogLevel==3) {
							var treeObj = $('#' + generObj).treeview('getNode', node.nodeId);
							if(treeObj.icon == "glyphicon glyphicon-check"){
								cataUnCheck(treeObj,node);
							}else{
								cataCheck(treeObj,node);
							}
							
							//得到目录code
					 		var dataObj = $("#fileCatalogCode").val().split(",");
					 		var jsonStr = "";
					 		for(var i=0; i<dataObj.length; i++) {
					 			jsonStr += "catalogCodes=" + dataObj[i];
					 			if(i !== dataObj.length-1) {
					 				jsonStr += "&";
					 			}
					 		}
						}
		            },
		            onNodeUnselected:function(event,node) {
		            	if(node.catalogLevel==3) {
		            		var treeObj = $('#' + generObj).treeview('getNode', node.nodeId);
		            		if(treeObj.icon == "glyphicon glyphicon-check"){
								cataUnCheck(treeObj,node);
							}else{
								cataCheck(treeObj,node);
							}
		            		
		            		//得到目录code
					 		var dataObj = $("#fileCatalogCode").val().split(",");
					 		var jsonStr = "";
					 		for(var i=0; i<dataObj.length; i++) {
					 			jsonStr += "catalogCodes=" + dataObj[i];
					 			if(i !== dataObj.length-1) {
					 				jsonStr += "&";
					 			}
					 		}
		            	}
		            },
	                onNodeExpanded : function (event,data) {
	              	   var brotherNode =  $('#' + generObj).treeview('getSiblings', data.nodeId);
	              	   for(var n = 0; n < brotherNode.length;n++){
	              		   $('#' + generObj).treeview('collapseNode', [ brotherNode[n].nodeId, { silent: true, ignoreChildren: false } ]);
	              	   }
	                 }
				});
 				//得到有效的节点
 		 		var allNodes = $('#' + generObj).treeview('getEnabled');
 		 		for(var i=0; i<allNodes.length; i++) {
 		 			if(allNodes[i].catalogLevel==3) {
 		 				allNodes[i].icon="glyphicon glyphicon-unchecked";
 		 			}
 		 		}
 		    }
 		},
	 	error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
	
}

var cataCheck = function(treeObj, node) {
	treeObj.icon = "glyphicon glyphicon-check";
	var cataCodeStr = $("#fileCatalogCode").val();
	if(cataCodeStr!=null && cataCodeStr!="") {
		var cataCodesArr = cataCodeStr.split(",");
		unqiueValue(cataCodesArr, node.catalogCode);
		$("#fileCatalogCode").val(cataCodesArr.toString());
	} else {
		$("#fileCatalogCode").val(node.catalogCode)
	}
	var cataNameStr = $("#fileCatalogName").val();
	if(cataNameStr!=null && cataNameStr!="") {
		var cataNamesArr = cataNameStr.split(",");
		unqiueValue(cataNamesArr, node.text);
		$("#fileCatalogName").val(cataNamesArr.toString());
	} else {
		$("#fileCatalogName").val(node.text)
	}
}

var cataUnCheck = function(treeObj,node) {
	treeObj.icon = "glyphicon glyphicon-unchecked";
	var cataCodeStr = $("#fileCatalogCode").val();
	 $("#fileCatalogCode").val(popValue(cataCodeStr, node.catalogCode));
	 var cataNameStr = $("#fileCatalogName").val();
	 $("#fileCatalogName").val(popValue(cataNameStr, node.text));
}

var unqiueValue = function(dataArr, data) {
	if(dataArr!=null) {
		var isSaveData = true;
		for(var i=0; i<dataArr.length; i++) {
			if(dataArr[i] == data) {
				isSaveData = false;
				break;
			}
		}
		if(isSaveData) {
			dataArr.push(data);
		}
	}
}

var popValue = function(dateStr,data) {
	var tmpArr = new Array();
	if(dateStr!=null && dateStr!="") {
		var dataArr = dateStr.split(",");
		for(var i=0; i<dataArr.length; i++) {
			if(dataArr[i] != data) {
				tmpArr.push(dataArr[i]);
			}
		}
	}
	return tmpArr;
}

courseEdit.restVal = function() {
	$("#fileCatalogCode").val("");
	$("#fileCatalogName").val("");
	$('#treeCatalog').treeview('collapseAll', { silent: true });
	var allNodes = $('#treeCatalog').treeview('getEnabled');
	for(var i=0; i<allNodes.length; i++) {
		if(allNodes[i].catalogLevel==3) {
			allNodes[i].icon="glyphicon glyphicon-unchecked";
		}
	}
}


//删除图片
courseEdit.delServerImg = function() {
	$('#remarkImgId').text("");
	$('#delImgText').text("");
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
}

//删除视频
courseEdit.deleteVideo = function(resourceId){
	bootbox.setLocale("zh_CN");
	bootbox.confirm("你确定删除吗?", function(result) {
		if (result) {
			// 添加正在删除的面板提示
			var deleteMsg = bootbox.dialog({
				size : 'small',
				closeButton: false,
				message : '<div class="text-center" style="color:green;"><i class="fa fa-spin fa-spinner"></i>正在删除，请稍等...</div>'
			});
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/deleteVideo/"+resourceId,
				success : function(data) {
					deleteMsg.modal('hide');
					if (data){
						$('#courseShow_table').bootstrapTable('refresh');
						common.alertInfoMsg("删除成功!");
					}else {
						common.alertErrorMsg("删除失败!");
					}
				},
				error : function() {
					deleteMsg.modal('hide');
					common.alertErrorMsg("删除失败!");
				}
			});
		}
	});
}

//有效无效的样式显示
courseEdit.changeFormatter = function(value,row,index) {
	if(row.isValid == '1'){
		return "<input id= "+row.resourceId+"  pri_url='/ws/validCourse' name='isValid' type='checkbox' " +
		" data-size='small' checked>";
	}else{
		return "<input id= "+row.resourceId+"  pri_url='/ws/validCourse' name='isValid' type='checkbox' " +
		" data-size='small'>";
	}
}

//有效无效的切换
courseEdit.initSwitch = function(){
	if($('[name="isValid"]').length > 0){
		$('[name="isValid"]').bootstrapSwitch({  
			onText : "已发布",
			offText : "未发布",
			onColor : "success",
			offColor : "danger",
		    wrapperClass : "privilege-hints",
		    onSwitchChange : function(event, state) { 
		    	courseEdit.validCourse($(this).attr("id"),state);
		    }
		 });
	}else{
		setTimeout(courseEdit.initSwitch,1000);
  }
	//权限切换
	
}

//有效无效的切换方法
courseEdit.validCourse = function(resourceId,state) {
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/validCourse/" + resourceId + "/" + state,
		success : function(data) {
			$('#courseShow_table').bootstrapTable('refresh');
			if(data){
				common.alertInfoMsg("切换成功!");
			}else{
				common.alertInfoMsg("切换失败!");
			}
		},
		error : function(request) {
			$('#courseShow_table').bootstrapTable('refresh');
			common.alertErrorMsg("切换失败!");
		}
	});
}


//增加
courseEdit.uploadMoreFile = function() {
	//表单验证开启
	$('#updateCourseForm').data('bootstrapValidator').validate();
	if (!$('#updateCourseForm').data('bootstrapValidator').isValid()) {
		return;
	}
	$("#updateCourseForm").data('bootstrapValidator').resetForm();
	var remarkImgId = $("#remarkImgId").text().trim();
	var cataName = $("#fileCatalogName").val();
	if(!cataName){
		common.alertInfoMsg("请选择目录");
					return;
			  }
	        var fileDiv = $("#img_upload").val();
			if(remarkImgId=="" && fileDiv!=""){ //有图片的修改
			// 添加正在上传的面板提示
			var uploadMsg = bootbox.dialog({
				size : 'small',
				closeButton: false,
				message : '<div class="text-center" style="color:green;"><i class="fa fa-spin fa-spinner"></i>正在上传...</div>'
			});
			$("#updateCourseForm").ajaxSubmit({
				type : "POST",
				url : "/ietlAdmin/ws/uploadCourseImg",
				data : common.form2JosnStr("#updateCourseForm"),
				contentType : "multipart/form-data;charset=UTF-8",
				success : function(data) {
					uploadMsg.modal('hide');
					$("#updateCourseModal").modal("hide");
					$('#updateCourseForm')[0].reset();
					$('#courseShow_table').bootstrapTable('refresh');
					if(data){
						common.alertInfoMsg("操作成功!");
					}else{
						common.alertInfoMsg("操作失败!");
					}
				},
				error : function(request) {
					uploadMsg.modal('hide');
					common.alertErrorMsg("操作失败!");
				}
			});
		}else{  
			var imgFlagVal = $("#testimgname").attr('src');
			//无图片的修改
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/updateTime",
				data : JSON.stringify({
					"resourceId":$("#resource_Id").val(),
					"courseCode":$("#fileCatalogCode").val(),
					"isPublic" : $("#isPublic").val(),
					"imgFlag":$("#viewImgId").is(':hidden')||imgFlagVal==""?"0":"1"
				}),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					$("#updateCourseModal").modal("hide");
					$('#updateCourseForm')[0].reset();
					$('#courseShow_table').bootstrapTable('refresh');
					if(data){
						common.alertInfoMsg("操作成功!");
					}else{
						common.alertInfoMsg("操作失败!");
					}
				},
				error : function(request) {
					common.alertErrorMsg("操作失败!");
				}
			});
		}
}



//表单验证
courseEdit.Validator = function() {
    $('#updateCourseForm').bootstrapValidator({
    	message: 'This value is not valid',
            feedbackIcons: {//输入框不同状态，显示图片的样式
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {//验证
            	fileCatalogName : { //键名和input name值对应 
    				message : '文件挂课程不能为空',
    				validators : {
    					notEmpty : {  
    						message : '文件挂课程不能为空'
    					}
    				}
    			}
            }
        });
}