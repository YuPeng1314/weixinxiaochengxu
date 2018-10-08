var coursenew = {};
/**
 * 生成进度条封装方法
 * 
 * @param file
 *            文件
 * @param percentage
 *            进度值
 * @param id_Prefix
 *            id前缀
 * @param titleName
 *            标题名
 */
coursenew.getProgressBar = function(file, percentage, id_Prefix, titleName) {
    var $li = $('#' + file.id), $percent = $li.find('#' + id_Prefix + '-progress-bar');
    // 避免重复创建
    if (!$percent.length) {
        $percent = $('<div id="' + id_Prefix + '-progress" class="progress progress-striped active">' +
                '<div id="' + id_Prefix + '-progress-bar" class="progress-bar" style="color:black;font-weight bold;" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>'
        ).appendTo($li).find('#' + id_Prefix + '-progress-bar');
    }
    // 百分比为整数
    var progressPercentage = parseInt(percentage* 100) + '%';
    $percent.css('width', progressPercentage);
    $percent.html(titleName + ':' + progressPercentage);
}

/**
 * 隐藏进度条
 * 
 * @param file
 *            文件对象
 * @param id_Prefix
 *            id前缀
 */
coursenew.fadeOutProgress = function(file, id_Prefix) {
    $('#' + file.id).find('#' + id_Prefix + '-progress').fadeOut();
}


// 启动加载对应的类
$(function() {
	$("#docFile").fileinput('destroy');
	coursenew.initDocFileInput();
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
	
	
    var $btn = $('#ctlBtn');
    var $thelist = $('#thelist');
    // 分片大小
    var chunkSize = 5 * 1024 * 1024;
    
    

    // HOOK 这个必须要再uploader实例化前面,before-send-file 文件发送之前 before-send 分片分片之前
    WebUploader.Uploader.register({
        'before-send-file': 'beforeSendFile',
        'before-send': 'beforeSend'
    }, {
    	 // 文件上传，当文件检查MD5之后继续上传
        beforeSendFile: function (file) {
            // Deferred对象在钩子回掉函数中经常要用到，用来处理需要等待的异步操作。
            var task = new $.Deferred();
            // 根据文件内容来查询MD5, 及时显示进度
            uploader.md5File(file).progress(function (percentage) { 
            	coursenew.getProgressBar(file, percentage, "MD5", "校验");
            }).then(function (val) { // 完成文件MD5的生成后的操作
                file.md5 = val;
                // 模拟用户id
                file.uid = WebUploader.Base.guid();
                // 进行md5判断
                $.post("/ietlAdmin/ws/checkFileMd5", {uid: file.uid, md5: file.md5, fileName:file.name},
                        function (data) {
                            var status = data.status.value;
                            // 从未完成变为已完成状态
                            task.resolve();
                            if (status == 101) {
                                // 文件不存在，那就正常流程
                            } else if (status == 100) {
                                // 忽略上传过程，直接标识上传成功；
                                uploader.skipFile(file);
                                file.pass = true;
                            } else if (status == 102) {
                                // 部分已经上传到服务器了，但是差几个模块。保存到丢失的模板中
                                file.missChunks = data.data;
                            }
                        });
            });
            return $.when(task);
        },
        
        // 分片逻辑，如果有分片没有上传则继续上传
        beforeSend: function (block) {
            // console.log("block")
            var task = new $.Deferred();
            var file = block.file;
            var missChunks = file.missChunks;
            var blockChunk = block.chunk;
            // 缺失分块
            if (missChunks !== null && missChunks !== undefined && missChunks !== '') {
                var flag = true;
                for (var i = 0; i < missChunks.length; i++) {
                    if (blockChunk == missChunks[i]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    task.reject();
                } else {
                    task.resolve();
                }
            } else {
                task.resolve();
            }
            return $.when(task);
        }
    });

    // 实例化
    var uploader = WebUploader.create({
        pick: {
            id: '#picker',
            label: '点击选择资源(视频\音频)文件',
            multiple:true
        },
        formData: {
            uid: 0,
            md5: '',
            chunkSize: chunkSize
        },
        swf: '/ietlAdmin/js/courseware/Uploader.swf',
        chunked: true,
        chunkSize: chunkSize, // 字节 5M分块
        threads: 10,
        fileVal:'files',
        server: '/ietlAdmin/ws/fileUploadList',
        auto: false,
        accept: {
            title: '资源文件后缀',
            extensions: 'mp4,flv,avi,rmvb,mpeg,wmv,rm,mkv,vob,mov,mpg,mp3,wma,rm,wav,midi,ape,flac'
        },

        // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
        disableGlobalDnd: true,
// sendAsBinary:true,
        fileNumLimit: 100,
        fileSizeLimit: 1024 * 1024 * 1024 * 1024*5,    // 5G
        fileSingleSizeLimit: 1024 * 1024 * 1024 * 50    // 50 M
    });

    // 当有文件被添加进队列的时候
    uploader.on('fileQueued', function (file) {
    	  // li标签
    	  var tmpLi = $('<li style="margin-top:10px;"></li>');
	      $li = $('<div id="' + file.id + '" class="file-item thumbnail" style="background-color:#f1e1e1">' +   
   		   '<img style="float:left;">' +   
   		     '<div class="info" style="clear:both;">' + file.name + '</div>' +   
   		     '</div>'
           ); 
	       // 查询到img镖旗
           var $img = $li.find('img');
           
           tmpLi.append($li); 
           tmpLi.append('<label>上传状态</label><p class="state" style="margin-left: 5px;color: green;">等待上传...</p>' +
        		   // '<input type="button" style="margin-left:5px;height:30px;" class="btn btn-default" id="pas'+file.id+'" value="暂停"/>'
					// +
        		    '<input type="button" style="margin-left:5px;height:30px;" class="btn btn-default" id="can'+file.id+'" value="取消上传"/>' +
	               // '<input type="button" style="margin-left:5px;height:30px;" class="btn btn-default" id="con'+file.id+'"
					// value="继续上传"/>' +
	                // '<input type="button" style="margin-left:5px;height:30px;" class="btn btn-default" id="del'+file.id+'"
					// value="删除文件"/>' +
	                '</div></li>');
           $thelist.append(tmpLi);
   
// $("#pas"+file.id).bind("click", function() {uploader.stop(file)});
// $("#con"+file.id).bind("click", function() {uploader.retry(file,true)});
       $("#can"+file.id).bind("click", function() {uploader.cancelFile(file)});
// $("#del"+file.id).bind("click", function() {uploader.removeFile(file)});
       // 创建缩略图
       uploader.makeThumb(file, function( error, src ) {   
	       if (error) {   
	      		$img.replaceWith('<span>无预览图，资源上传</span>');   
	           return;   
	        }      
	           $img.attr('src', src );// 设置预览图
	        }, 100, 100 ); // 100x100为缩略图尺寸
    });

    // 当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
    uploader.onUploadBeforeSend = function (obj, data) {
        var file = obj.file;
        data.md5 = file.md5 || '';
        data.uid = file.uid;
        
    };
    // 上传中
    uploader.on('uploadProgress', function (file, percentage) {
    	coursenew.getProgressBar(file, percentage, "FILE", "上传进度");
    });
    // 上传返回结果
    uploader.on('uploadSuccess', function (file) {
        var text = '';
        if (file.pass) {
            text = "同样的文件已经上传过"
        } else {
        	text = "已上传成功"
        }
        $('#' + file.id).siblings('p.state').text(text).css("font-weight","bold");
       
    });
    uploader.on('uploadError', function (file) {
        $('#' + file.id).siblings('p.state').text('上传出错').css("color","red").css("font-weight","bold");
    });
    
    uploader.on('uploadComplete', function (file) {
        // 隐藏进度条
        coursenew.fadeOutProgress(file, 'MD5');
        coursenew.fadeOutProgress(file, 'FILE');
    });
    
    
    
    // 判断文件格式
    uploader.on("error", function (type) {
        if (type == "Q_TYPE_DENIED") {
        	common.alertErrorMsg("请上传mp4,flv,avi,rmvb,mpeg,wmv,rm,mkv,vob,mov,mpg,mp3,wma,rm,wav,midi,ape,flac资源格式文件");
        }
    });
    
    // 流程开始，流程全部结束
    uploader.on('startUpload', function (file) {
    	 $(window).bind('beforeunload',
	    	function(){
	    		return "正在上传文件，退出要重新上传，你确定要退出吗?";
	    	}
	    )
    });
    
    
    uploader.on("uploadFinished",function() {
    	 // 解除绑定
        $(window).unbind('beforeunload'); 
    });
    
    // 文件上传
    $btn.on('click', function () {
        uploader.upload();
    });
    
    coursenew.changeType(0);
    
    // 树加载
    coursenew.catalogtree("treeCatalog");
    
});  

coursenew.changeType = function(pws_status) {
	$("#pws_status").empty();
	$("#pws_status").val(pws_status);
	// 初始化日历插件
	$('.form_date').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		pickerPosition : "bottom-left",
		forceParse : 0
	});
	
	var t = new Date();
	var newDate = t.format("yyyy-MM-dd");
    $('.form_date').datetimepicker('setEndDate', newDate);
    
	if(pws_status == "0"){
		$("#myUploadedFile").show();
		$("#uploadFileTranscoding").hide();
		$("#docResDiv").hide();
	} else if(pws_status=="1") {
		$("#myUploadedFile").hide();
		$("#uploadFileTranscoding").show();
		$("#docResDiv").hide();
		$('#searchUploadFileFrom')[0].reset();
		coursenew.searchFileTranscoding();
	} else if(pws_status=="2"){
		$("#myUploadedFile").hide();
		$("#uploadFileTranscoding").hide();
		$("#docResDiv").show();
		// 初始化表格
		docmanage.initTable();
		docmanage.catalogtree("docTreeCatalog");
	}
}

    
coursenew.searchFileTranscoding = function() {
	$('#fileTransCodingList').bootstrapTable('destroy');
	$('#fileTransCodingList').bootstrapTable({
		method : "get",
		url : "/ietlAdmin/ws/getUploadFileList",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		queryParams : queryUploadFileDocParam,
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",// 设置在哪里进行分页
		minimumCountColumns : 2, // 设置最少显示列个数
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		rowStyle: function (row, index) {
            // 这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isPostil == "0") {
                strclass = 'danger';// 还有一个active
            }
            else if (row.isPostil == "1") {
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
			width : "15%",
			formatter : coursenew.actionApprovingFormatter
		},
		{
			field : 'id',
			align : 'center',
			class: 'hideClass'
		},
		{
			field : 'showTransCode',
			align : 'center',
			class: 'hideTranClass'
		},
		{
			field : 'resourceName',
			title : '资源名称',
			align : 'center'
		}, {
			field : 'status',
			title : '资源状态',
			align : 'center',
			formatter : coursenew.showStatus
		}, {
			field : 'createdBy',
			title : '创建人',
			align : 'center'
		},{
			field : 'creationDate',
			title : '创建时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		}]
	});
}
  
function queryUploadFileDocParam(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"startTime" : $("#resUploadStartTime").val(),
		"endTime" : $("#resUploadEndTime").val(),
		"status" : $("#searchResourceStatus").val(),
		"resourceName":$("#searchResourceName").val()
	};
} 


coursenew.actionApprovingFormatter = function(value,row,index) {
	if(row.status==0) {
		return '<span title="正在上传,若上传完成后还在此状态，请重新上传" class="glyphicon glyphicon-info-sign"></span><input type="button" id = "more_btn"  class="ico_a btn btn-info btn-xs" style="margin-left:10px;" onclick="coursenew.deleteUploadFile('+ row.id +')" value="删除">';
	}
	var	more = "";
	// 等待转码，以及转码失败，可以重新再转
	if(row.status=='1' || row.status=='3') {
			more = "<input type='button' id = 'more_btn'  class='ico_a btn btn-info btn-xs' style='margin-right:3px;'" +
			"onclick='coursenew.mountCatalog("+ row.id+",\""+row.resourceName +"\")' value='目录'>";
			if(index !=0 && row.showTransCode=='0') {		
				more += "<input type='button' id = 'more_btn' title='同上一个目录挂载'  class='ico_a btn btn-info btn-xs' style='margin-right:3px;' onclick='coursenew.dittoInfo("+ row.id+"," + index +")' value='同上'>";
			}
		
			if(row.showTransCode=='0') {
				more += "<input type='button' id = 'more_btn' title='请先挂点击目录进行挂载'  class='ico_a btn btn-info btn-xs' style='margin-right:3px;'  disabled='disabled' onclick='coursenew.transcoding("+ row.id +")' value='转码'>";
			} else {
				more += "<input type='button' id = 'more_btn'  class='ico_a btn btn-info btn-xs' style='margin-right:3px;' onclick='coursenew.transcoding("+ row.id +")' value='转码'>";
			}
	  }
    	more +="<input type='button' id = 'more_btn'  class='ico_a btn btn-info btn-xs 'onclick='coursenew.deleteUploadFile("+ row.id +")' value='删除'>";
	return more;
}

// 状态标识
coursenew.showStatus = function(value, row, index) {
	var logContent = value.toString(); 
	if(logContent=='0') {
		return '上传中';
	}
	if(logContent=='1') {
		return '等待转码';
	} else if(logContent=='2') {
		return '<label style="color:black;">转码进行中</label>';
	} else if(logContent=='3') {
		return '<label style="color:red;">转码失败</label>';
	} else if(logContent=='4') {
		return '<label style="color:green;">转码成功</label>';
	}
}

coursenew.mountCatalog=function(id, resName) {
	$('#searchUploadFileFrom')[0].reset();
	// 取到对应id的上传文件内容
	$("#id").val(id);
	// 文件名称
	$("#uploadResourceName").val(resName);
	
	//清理
	$("#isPublic").val("");
	$("#uploadFilePicTime").val("");
	$("#uploadFileOwner").val("");
	$("#uploadFileContent").val("");
	
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/getUploadFileRelation",
		data :{fileId:$("#id").val()},
		contentType : "application/json;charset=utf-8",
		async:false,
		success : function(data) {
			$('#treeCatalog').treeview('collapseAll', { silent: true });
			if(data != undefined && data.length>0) {
				var tmpData = data[0];
				
				
				$("#fileCatalogCode").val(tmpData.courseCode);
				//得到目录code
		 		var dataObj = $("#fileCatalogCode").val().split(",");
		 		var jsonStr = "";
		 		for(var i=0; i<dataObj.length; i++) {
		 			jsonStr += "catalogCodes=" + dataObj[i];
		 			if(i !== dataObj.length-1) {
		 				jsonStr += "&";
		 			}
		 		}
		 		
				$("#uploadFilePicTime").val(tmpData.screenshotPos);
				$("#isPublic").val((tmpData.isPublic==null||tmpData.isPublic=='')?"1":tmpData.isPublic);
				$("#uploadFileOwner").val(tmpData.resourceOwner);
				$("#uploadFileContent").val(tmpData.description);
				
		 		
		 		
		 		//目录名称
		 		var typeArr = tmpData.courseCode.split(",");
				var typeNameArr = new Array();
		 		var typeAllNodes = $('#treeCatalog').treeview('getEnabled');
		 		for(var i=0; i<typeAllNodes.length; i++) {
		 			if(typeAllNodes[i].catalogLevel==3) {
		 				typeAllNodes[i].icon="glyphicon glyphicon-unchecked";
		 			}
		 			for(var j=0;j <typeArr.length; j++) {
		 				if(typeAllNodes[i].catalogCode == typeArr[j] && typeAllNodes[i].catalogLevel==3) {
		 					typeAllNodes[i].icon="glyphicon glyphicon-check";
		 					typeNameArr.push(typeAllNodes[i].text);
		 				}
		 			}
		 		}
		 		$("#fileCatalogName").val(typeNameArr);
				
			} else {
				var allNodes = $('#treeCatalog').treeview('getEnabled');
		 		for(var i=0; i<allNodes.length; i++) {
		 			if(allNodes[i].catalogLevel==3) {
		 				allNodes[i].icon="glyphicon glyphicon-unchecked";
		 			}
		 		}
		 		$("#fileCatalogName").val("");
		 		$("#fileCatalogCode").val("");
		 		
			}
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
	
	coursenew.ValidatorCatalog();
	// 验证
	$("#mountCatalogDiv").modal("show");
	$("#addUploadFileForm").data('bootstrapValidator').resetForm();
}

coursenew.ValidatorCatalog = function() {
	// 校验
	$('#addUploadFileForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {/* 验证 */
			fileCatalogName : {/* 键名和input name值对应 */
				message : '文件挂目录不能为空',
				validators : {
					notEmpty : {/* 非空提示 */
						message : '文件挂目录不能为空'
					}
				}
			},
			uploadFilePicTime:{
				message:"截图指定需为数字",
				validators : {
			 		numeric:{message:'截图位置只能输入数字'}
				}
			}
		}
	});
}

coursenew.initDocFileInput=function() {
	$("#docFile").fileinput('destroy');
	$("#docFile").fileinput({
		language : 'zh',
        previewFileType: "image",
        browseClass: "btn btn-success",
        browseLabel: "选择文档",
        showUpload : false,
        showCancel:false,
        autoReplace:true,
        allowedFileExtensions : [ 'doc', 'docx', 'ppt', 'pptx', 'xls', 'xlsx', 'wps', 'pdf', 'txt' ],
		slugCallback : function(filename) {
			return filename.replace('(', '_').replace(']', '_');
		},
//		maxFileCount : 4,
//		validateInitialCount : true,
//		msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
    });
	
	
},

// 转码
coursenew.transcoding = function(id) {
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/sendFileMessTransCoding",
		data :JSON.stringify({"id":id}),
		contentType : "application/json;charset=utf-8",
		async:false,
		success : function(data) {
			$("#fileTransCodingList").bootstrapTable("refresh");
			$("#mountCatalogDiv").modal("hide");
			if(data){
				common.alertInfoMsg("操作成功!");
			}

		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}

coursenew.deleteUploadFile = function(id) {
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/deleteUploadFile",
		data : JSON.stringify({"id":id}),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$("#fileTransCodingList").bootstrapTable("refresh");
			common.alertInfoMsg("操作成功!");
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}

coursenew.saveUploadFileRelation = function() {
	$('#addUploadFileForm').data('bootstrapValidator').updateStatus('fileCatalogName', 'NOT_VALIDATED', null).validateField('fileCatalogName');
	$('#addUploadFileForm').data('bootstrapValidator').validate(); 
	if(!$('#addUploadFileForm').data('bootstrapValidator').isValid()){ 
        return false;
    }
	$("#addUploadFileForm").data('bootstrapValidator').resetForm() ;
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/insertUploadFileRelation",
		data :combineApprovingParam(),
		contentType : "application/json;charset=utf-8",
		async:false,
		success : function(data) {
			$("#mountCatalogDiv").modal("hide");
			common.alertInfoMsg("操作成功，请点击进行转码");
			$('#fileTransCodingList').bootstrapTable('refresh');
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
	return true;
}

var combineApprovingParam = function() { 
	var reqParam = {"uploadFileId":$("#id").val(),
        "courseCode":$("#fileCatalogCode").val(),
        "isPublic" :$('#isPublic').val(),
        "resourceOwner":$('#uploadFileOwner').val(),
        "screenshotPos":$("#uploadFilePicTime").val(),
        "description":$("#uploadFileContent").val()
       }
   var reqJsonObj = JSON.stringify(reqParam);
   return reqJsonObj;
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


coursenew.catalogtree = function(generObj){
	$.ajax({
		type: 'GET',
		url: "/ietlAdmin/ws/getCatalogTree",
		async : false,
		contentType : "application/json;charset=utf-8",
	 	success:function(dataStr){
	 		coursenew.successCatalogOper(dataStr,generObj);
 		},
	 	error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
	
}

//树生成成功之后运行地操作
coursenew.successCatalogOper = function(dataStr, generObj) {
		if(dataStr){
			$('#' + generObj).treeview({
			data : dataStr,
			levels : '1',
			backColor : '#eee',
			//showCheckbox:true,
			multiSelect:true,
			onNodeSelected :function(event, node) {
				if(node.catalogLevel==3) {
					var treeObj = $('#' + generObj).treeview('getNode', node.nodeId);
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
					
					$('#addUploadFileForm').data('bootstrapValidator').updateStatus('fileCatalogName', 'NOT_VALIDATED', null).validateField('fileCatalogName');
					
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
					treeObj.icon = "glyphicon glyphicon-unchecked";
					var cataCodeStr = $("#fileCatalogCode").val();
					 $("#fileCatalogCode").val(popValue(cataCodeStr, node.catalogCode));
					 var cataNameStr = $("#fileCatalogName").val();
					 $("#fileCatalogName").val(popValue(cataNameStr, node.text));
					 
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
}

//复位值
coursenew.restVal = function() {
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



// 同上
coursenew.dittoInfo = function(fileId, indexId) {
	if(indexId>0) {
		--indexId;
	}
	// 得到上一个的文件id
	var preTransCode = $("tr[data-index='"+indexId+"'] .hideTranClass").text();
	if(preTransCode=='0') {
		common.alertErrorMsg("上一个文件还没有配置目录");
		return;
	}
	
	// 得到上一个的文件id
	var preFileId = $("tr[data-index='"+indexId+"'] .hideClass").text();
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/dittoUploadFileRelation",
		data :{"uploadFileId":fileId, "otherUploadFileId":preFileId},
		contentType : "application/json;charset=utf-8",
		async:false,
		success : function(data) {
			$('#fileTransCodingList').bootstrapTable('refresh');
		},
		error:function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
}




