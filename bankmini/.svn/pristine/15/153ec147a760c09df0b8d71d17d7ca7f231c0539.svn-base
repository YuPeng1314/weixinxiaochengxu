$(function() {
	coursemanage.courseTable();
	$("#addDomain").click(function(){
		$('#addCourseForm')[0].reset();
		$('#catalogName').empty();
		$('#catalogName').append("<option value='' selected='selected' style='text-align:center;'>-------加载中--------</option>");
		$("#imgUploadDivId_Course").show();
		$("#viewImgId_Course").hide();
		coursemanage.initFileUpload();
		coursemanage.courseOption();
	});

	 $('#catalogName').change(function(){
          $('#category_code').empty();
		  $('#category_code').append("<option value='' selected='selected' style='text-align:center;'>--------加载中--------</option>");
		  coursemanage.categoryOption($(this).val());
      });
	 
	 coursemanage.validator();
});
var coursemanage = {
		//加载类别表
		courseTable : function(){
			$('#typemanege_table').bootstrapTable('destroy');
			$('#typemanege_table').bootstrapTable({
				method : "post",
				url : "/ietlAdmin/ws/getCourse",// 请求后台的URL（*）
				cache : false, // 不缓存
				striped : true, // 隔行加亮
				pagination : true, // 开启分页功能
				pageSize : 10, // 设置默认分页为 10
				pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
				search : false, // 开启搜索功能
				showColumns : false, // 开启自定义列显示功能
				showRefresh : false, // 开启刷新功能
				sidePagination : "server",// 设置在哪里进行分页
				queryParams : coursemanage.queryDataParams,
				minimumCountColumns : 2, // 设置最少显示列个数
				clickToSelect : true, // 单击行即可以选中
				smartDisplay : true, // 智能显示 pagination 和 cardview 等
				onLoadSuccess:function(){ 
					coursemanage.initSwitch();
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
					formatter : coursemanage.actionFormatter
				},{
					field : 'course_code',
					title : '课程编码',
					align : 'center'
				},{
					field : 'course_name',
					title : '课程名称',
					align : 'center',
					formatter : common.valueEncode
				},{
					field : 'catalogName',
					title : '目录',
					align : 'center'
				},{
					field : 'categoryName',
					title : '类别',
					align : 'center'
				},{
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
				},{
					field : 'isValid',
					title : '状态标识',
					align : 'center',
					formatter : coursemanage.isValid
				}]

			});
		},
		
		//参数传递
		queryDataParams : function(params) {
			return {
				"limit" : params.limit,
				"offset" : params.offset,
				"course_name" : $("#courseNameSrh").val(),
			};
		},
		//操作
		actionFormatter : function(value, row, index) {
			var courseImg = row.course_img;
			if (courseImg == null || courseImg == undefined) {
				courseImg = "";
			}
			var update = "<input type='button' id = 'updateDomain' class='ico_a btn btn-info btn-xs ' title='修改' "
					+ "onclick='coursemanage.updateCourse(\"" + row.id + "\");' value='修'>";
			var del = "<input type='button' id = '' class='ico_a btn  btn-danger btn-xs ' title='删除' "
					+ "onclick='coursemanage.delbtn(\"" + row.id + "\")' value='删'>";
			return update + " " + del ;
		},
		//是否发布
		isValid : function(value, row, index) {
			if (row.isValid == '1') {
				return "<input id= " + row.id + " pri_url='/ws/validCourseState'  name='isValid' type='checkbox' " + " data-size='small' checked>";
			} else if (row.isValid == '0') {
				return "<input id= " + row.id + " pri_url='/ws/validCourseState' name='isValid' type='checkbox' " + " data-size='small'>";
			} 
		},
		//发布组件加载
		initSwitch : function() {
			if ($('[name="isValid"]').length > 0) {
				$('[name="isValid"]').bootstrapSwitch({
					onText : "有效",
					offText : "失效",
					onColor : "success",
					offColor : "danger",
					wrapperClass : "privilege-hints",
					onSwitchChange : function(event, state) {
						coursemanage.validCourse($(this).attr("id"), state);
					}
				});

			} else {
				setTimeout(coursemanage.initSwitch, 1000);
			}
			// 权限切换
		},
		//发布按钮切换
		validCourse : function(id, state) {
			$.ajax({
				type : "GET",
				url : "/ietlAdmin/ws/validCourseState/" + id + "/" + state,
				success : function(data) {
					$('#typemanege_table').bootstrapTable('refresh');
					if (data == "1") {
						common.alertInfoMsg("切换成功!");
					}
					if (data == "0") {
						common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");
					}
					if (data == "2") {
						common.alertInfoMsg("当前课程下没有视频，不能发布!");
					}
					if (data == "3") {
						common.alertInfoMsg("当前课程下有视频没有设置集数，不能发布!");
					}
				},
				error : function(request) {
					$('#typemanege_table').bootstrapTable('refresh');
					common.alertErrorMsg("切换失败!");
				}
			});
		},
		//搜索清空
		emptySearch : function(){
			$("#courseNameSrh").val("");
		},
		//加载目录列表
		catalogOption : function(){
			
			var category={
					"category_level" : 1
			};
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/getCategory",
				data: JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				async:false,
				success : function(data) {
					$('#catalogName').empty();
					$('#catalogName').append("<option value='' selected='selected' style='text-align:center;'>--------请选择--------</option>");
					for (var i = 0; i < data.rows.length; i++) {
						$('#catalogName').append("<option value=" + data.rows[i].category_code + ">" + data.rows[i].category_name + "</option>");
					};
				},
			});
		},
		//加载类别列表
		categoryOption : function(category_code) {
			var category={
					"category_level" : 2,
					"parent_category_code" : category_code
			};
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/getCategory",
				data: JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				async:false,
				success : function(data) {
					$('#category_code').empty();
					$('#category_code').append("<option value='' selected='selected' style='text-align:center;'>--------请选择--------</option>");
					for (var i = 0; i < data.rows.length; i++) {
						$('#category_code').append("<option value=" + data.rows[i].category_code + ">" + data.rows[i].category_name + "</option>");
					};
				},
			});
		},
		//新增按钮
		courseOption : function(){
			$(".modal-title").text("添加课程");
			$("#course_code").val("");
			$("#addCourseModal").modal("show");
			coursemanage.catalogOption();
		},
		//修改按钮
		updateCourse : function(id){
			var course={
				"id" : id	
			};
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/getCourse",
				data: JSON.stringify(course),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					$(".modal-title").text("修改课程");
					coursemanage.catalogOption();
					coursemanage.categoryOption();
					$("#addCourseModal").modal("show");
					var courseImg = data.rows[0].course_img;
					$("#imgUploadDivId_Course").hide();
					$("#viewImgId_Course").show();
					$("#course_code").val(data.rows[0].course_code);
					$("#course_name").val(data.rows[0].course_name);
					$("#catalogName").val(data.rows[0].catalogCode);
					$("#category_code").val(data.rows[0].category_code);
					$("#description").val(data.rows[0].description);
				},
			});
		},
		//确定按钮
		optCourse : function() {
			$('#addCourseForm').data('bootstrapValidator').validate();
			if (!$('#addCourseForm').data('bootstrapValidator').isValid()) {
				return;
			}
			var courseCode=$("#course_code").val();
			var img_upload = $("#img_upload_Course").val();
			if(courseCode==null || courseCode==""){
				if (!img_upload) {
					common.alertInfoMsg("请选择图片上传");
					return;
				}else{
					coursemanage.optCourseImg();
				}
			}else{
				if (!img_upload) {
					coursemanage.optCourseNoImg();
				}else{
					coursemanage.optCourseImg();
				}
			}
			
		},
		//修改课程不修改图片
		optCourseNoImg :function(){
			var course={
					"course_code" : $("#course_code").val(),
					"category_code" : $("#category_code").val(),
					"course_name" : $("#course_name").val(),
					"description" : $("#description").val()
			}
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/updateCourse",
				data : JSON.stringify(course),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					$("#addCourseModal").modal("hide");
					$('#addCourseForm')[0].reset();
					$('#typemanege_table').bootstrapTable('refresh');
					common.alertInfoMsg("修改成功!");
				},
				error : function(request) {
					$("#addCourseModal").modal("hide");
					$('#addCourseForm')[0].reset();
					$('#typemanege_table').bootstrapTable('refresh');
					common.alertErrorMsg("修改失败!");
				}
			});
		},
		//新增修改课程有图片
		optCourseImg : function() {
			$("#addCourseForm").ajaxSubmit({
				type : "POST",
				url : "/ietlAdmin/ws/optCourse",
				data : common.form2JosnStr("#addCourseForm"),
				contentType : "multipart/form-data;charset=UTF-8",
				success : function(data) {
					$("#addCourseModal").modal("hide");
					$('#addCourseForm')[0].reset();
					$('#typemanege_table').bootstrapTable('refresh');
					if (data) {
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
		addempty : function(){
			$("#course_name").val("");
			$("#catalogName").val("");
			$("#category_code").val("");
			$("#description").val("");
		},
		delbtn : function(id){
			var r=confirm("确定删除该课程吗？");
			if (r==true){
				coursemanage.delcourse(id);
			}
		},
		delcourse : function(id){
			var course={
					"id" : Number(id)
			};
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/deleteCourse",
				data : JSON.stringify(course),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					$("#addCourseModal").modal("hide");
					$('#addCourseForm')[0].reset();
					$('#typemanege_table').bootstrapTable('refresh');
					if(data){
						common.alertInfoMsg("删除成功!");
					}
					if(!data){
						common.alertErrorMsg("请先删除该课程下资源!");
					}
				},
				error : function(request) {
					$("#addCourseModal").modal("hide");
					$('#addCourseForm')[0].reset();
					$('#typemanege_table').bootstrapTable('refresh');
					common.alertErrorMsg("删除失败!");
				}
			});
		},
		changeImg : function() {
			$("#uploadImgDiv_Course").show();
			$("#imgUploadDivId_Course").show();
			$("#viewImgId_Course").hide();
			$("#remarkImgId_Course").val("0");
			coursemanage.initFileUpload();
		},
		initFileUpload : function() {
			$("#img_upload_Course").fileinput({
				language : 'zh', // 设置语言
				allowedFileExtensions : [ 'jpg', 'png' ],
				showUpload : false, // 是否显示上传按钮
				showCaption : true,// 是否显示标题
				showCancel : false,
				browseClass : "btn btn-inverse", // 按钮样式
				previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
				maxFileSize : 102400000
			});
		},
		validator : function(){
			$('#addCourseForm').bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {/* 验证 */
					course_name : {
						message : '名称不能为空',
						validators : {
							notEmpty : {
								message : '名称不能为空'
							}
						}
					},
					catalogName : {
						message : '目录不能为空',
						validators : {
							notEmpty : {
								message : '目录不能为空'
							}
						}
					},
					category_code : {
						message : '类别不能为空',
						validators : {
							notEmpty : {
								message : '类别不能为空'
							}
						}
					}
				}
			});
		},
}