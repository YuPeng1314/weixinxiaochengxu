$(function() {
	categorymanage.categoryTable();
	$("#addDomain").click(function(){
		$("#addCatalogModal").modal("show");
		$('#addparentCode').empty();
		$('#addparentCode').append("<option value='' selected='selected'>------------------加载中------------------</option>");
		categorymanage.selectOption(1);
		categorymanage.addvalidator();
		$("#addCourseForm").data('bootstrapValidator').resetForm();
	});

});
var categorymanage = {
		//加载类别表
		categoryTable : function(){
			$('#typemanege_table').bootstrapTable('destroy');
			$('#typemanege_table').bootstrapTable({
				method : "post",
				url : "/ietlAdmin/ws/getCategory",// 请求后台的URL（*）
				cache : false, // 不缓存
				striped : true, // 隔行加亮
				pagination : true, // 开启分页功能
				pageSize : 10, // 设置默认分页为 50
				pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
				search : false, // 开启搜索功能
				showColumns : false, // 开启自定义列显示功能
				showRefresh : false, // 开启刷新功能
				sidePagination : "server",// 设置在哪里进行分页
				queryParams : categorymanage.queryDataParams,
				minimumCountColumns : 2, // 设置最少显示列个数
				clickToSelect : true, // 单击行即可以选中
				smartDisplay : true, // 智能显示 pagination 和 cardview 等
				onLoadSuccess:function(){ 
					categorymanage.initSwitch();
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
					formatter : categorymanage.actionFormatter
				}, {
					field : 'category_code',
					title : '编码',
					align : 'center'
				}, {
					field : 'category_name',
					title : '名称',
					align : 'center',
					formatter : common.valueEncode
				}, {
					field : 'category_level',
					title : '',
					align : 'center',
					formatter : function(data) {
						if (data == 1 ) {
							return "目录";
						} else if(data==2){
							return "类别";
						}
					}
				}, {
					field : 'category_img',
					title : '图标',
					align : 'center',
					formatter: categorymanage.handleContent
				},{
					field : 'creationDate',
					title : '创建时间',
					align : 'center',
					formatter : function(date) {
						return new Date(date).format("yyyy-MM-dd hh:mm:ss");
					}
				}, {
					field : 'sequence_number',
					title : '序列',
					align : 'center',
					formatter : function(data) {
						if (data == null || data == "") {
							return "--";
						} else {
							return data;
						}
					}
				}, {
					field : 'createdBy',
					title : '创建人',
					align : 'center',
					formatter : common.valueEncode
				}, {
					field : 'isValid',
					title : '状态标识',
					align : 'center',
					formatter : categorymanage.isValid
				}]

			});
		},
		//参数传递
		queryDataParams : function(params) {
			return {
				"limit" : params.limit,
				"offset" : params.offset,
				"category_name" : $("#catalogNameSrh").val(),
			};
		},
		//操作
		actionFormatter : function(value, row, index) {
			var update = "<input type='button' id = 'updateDomain' class='ico_a btn btn-info btn-xs ' title='修改' "
					+ "onclick='categorymanage.updatebutn(\"" + row.category_code + "\");' value='修'>";
			var del = "<input type='button' id = '' class='ico_a btn  btn-danger btn-xs ' title='删除' "
					+ "onclick='categorymanage.delbtn(\"" + row.id + "\",\""+row.category_level+"\")' value='删'>";
			var imgUP = "<input type='button' id = 'imgUP_btn'  class='ico_a btn btn-warning btn-xs ' title='维护图片' " +
			"onclick='categorymanage.coverImgChange(" + row.id + ")' value='图片维护'>";
			return update + " " + del + " " + imgUP ;
		},
		//是否发布
		isValid : function(value, row, index) {
			if (row.isValid == '1') {
				return "<input id= " + row.id + " pri_url='/ws/validCategory'  name='isValid' type='checkbox' " + " data-size='small' checked>";
			} else if (row.isValid == '0') {
				return "<input id= " + row.id + " pri_url='/ws/validCategory' name='isValid' type='checkbox' " + " data-size='small'>";
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
						categorymanage.validCategory($(this).attr("id"), state);
					}
				});

			} else {
				setTimeout(categorymanage.initSwitch, 1000);
			}
			// 权限切换
		},
		//发布按钮切换
		validCategory : function(id, state) {
			$.ajax({
				type : "GET",
				url : "/ietlAdmin/ws/validCategory/" + id + "/" + state,
				success : function(data) {
					$('#typemanege_table').bootstrapTable('refresh');
					if (data == "1") {
						common.alertInfoMsg("切换成功!");
					}
					if (data == "0") {
						common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");
					}
					if (data == "2") {
						common.alertInfoMsg("目录下暂无类别，或类别下暂无课程，不能发布！");
					}
				},
				error : function(request) {
					$('#typemanege_table').bootstrapTable('refresh');
					common.alertErrorMsg("切换失败!");
				}
			});
		},
		//图片展示
		handleContent : function(value, row, index) {
			if(value != null && value != "" && value != undefined){
			var tempValue = $('<div/>').text(value).html();
			var result = "";
			if (tempValue.length >= 10) {
				result = tempValue.substring(0, 10) + "...";
			} else {
				result = tempValue;
			}
			return "<a style='cursor:pointer;text-decoration:none' id='conten"+ index + "' onmouseover='categorymanage.overCell(this.id,\"" + tempValue
					+ "\")'  onmouseout='categorymanage.outCell(\"" + index + "\")'>" + result + "</a>";
			}else{
				return value;
			}
		},
		//设备图片展示
		overCell : function(id, tempValue){
			var img = '<img style="margin:1px;display: inline-block;"src="' + tempValue + '" width="170px;">';
			layer.tips(img, "#" + id, {
				tips : [ 2, '#3595CC' ],
				time : 10000
			});
		},

		//设备图片展示
		outCell : function(index){
			layer.closeAll('tips');
		},

		//设备图片维护
		coverImgChange : function(id) {
			var category={
					"id" : id
			};
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/getCategory",
				data: JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					if (data.length != 0) {
						    $("#areaImgModal").modal("show");
						    $('#areaImgForm')[0].reset();
							$("#id_img").val(id);
							if(data.category.category_img != null && data.category.category_img != "" && data.category.category_img != undefined){
								$("#viewImgId").show();
								$("#testimgname").attr('src',data.category.category_img);
								$("#remarkImgId").text(data.category.category_img.substring(data.category.category_img.lastIndexOf('/')+1));
								$("#delImgText").text("更换");
								$("#imgUploadDivId").hide(); //图片路径
							}else{
								categorymanage.initUploadImg(); //初始化上传插件
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
		uploadEqpImg : function(){
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
				url : "/ietlAdmin/ws/saveCategoryImg",
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
					categorymanage.categoryTable();
				},
				error : function(request) {
					uploadMsg.modal('hide');
					common.alertErrorMsg("图片修改失败!");
				}
			});
		},

		initUploadImg : function() {
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
			categorymanage.formEnd();
		},
		//阻止表单自动提交
		formEnd : function() {
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
		emptySearch : function(){
			$("#catalogNameSrh").val("");
		},
		//加载父级编码列表
		selectOption : function(num) {
			var category={
					"category_level" : 1
			};
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/getCategory",
				data: JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					if(num==1){
						$('#addparentCode').empty();
						$('#addparentCode').append("<option value='' selected='selected'>------------------请选择------------------</option><option value='-1'>目录</option>");
						for (var i = 0; i < data.rows.length; i++) {
							$('#addparentCode').append("<option value=" + data.rows[i].category_code + ">" + data.rows[i].category_name + "</option>");
						};
					}
					if(num==2){
						$('#updateparentCode').empty();
						$('#updateparentCode').append("<option value='' selected='selected'>------------------请选择------------------</option><option value='-1'>目录</option>");
						for (var i = 0; i < data.rows.length; i++) {
							$('#updateparentCode').append("<option value=" + data.rows[i].category_code + ">" + data.rows[i].category_name + "</option>");
						}
					}
				},
			});
		},
		addcategory : function(){
			$('#addCourseForm').data('bootstrapValidator').validate();
			if (!$('#addCourseForm').data('bootstrapValidator').isValid()) {
				return;
			}
			categorymanage.addcategory1();
		},
		//新增类别
		addcategory1 : function(){
			var parent_category_code=$("#addparentCode").val();
			if(parent_category_code==-1){
				category_level=1;
			}else{
				category_level=2;
			};
			var category={
				"category_name" : $("#addcategoryName").val(),
				"parent_category_code" : parent_category_code,
				"category_level" : category_level,
				"sequence_number" : $("#addsequenceNumber").val()
			};
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/addCategory",
				data : JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					common.alertInfoMsg("添加成功!");
					categorymanage.emptyupdate();
					$('#addCatalogModal').modal('hide');
					categorymanage.categoryTable();
				},
				error : function(request) {
					$('#addCatalogModal').modal('hide');
					common.alertErrorMsg("添加失败!");
					categorymanage.categoryTable();
				}
			});
		},
		addempty : function(){
			$("#addparentCode").val("");
			$("#addcategoryName").val("");
			$("#addsequenceNumber").val("");
			$("#adddescription").val("");
		},
		//修改带入参数
		updatebutn :function(category_code){
			$('#updateparentCode').empty();
			$('#updateparentCode').append("<option value='' selected='selected'>------------------加载中------------------</option>");
			categorymanage.updatevalidator();
			$("#updateCourseForm").data('bootstrapValidator').resetForm();
			categorymanage.selectOption(2);
			var category={
					"category_code" : category_code
			};
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/getCategory",
				data: JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					$("#categoryCode").val(data.rows[0].category_code);
					$("#updatecategoryName").val(data.rows[0].category_name);
					$("#updateparentCode").val(data.rows[0].parent_category_code);
					$("#updatesequenceNumber").val(data.rows[0].sequence_number);
					$("#updatedescription").val(data.rows[0].description);
					$('#updateCatalogModal').modal('show');
				}
			});
		},
		//修改类别
		updatecategoy : function(){
			$('#updateCourseForm').data('bootstrapValidator').validate();
			if (!$('#updateCourseForm').data('bootstrapValidator').isValid()) {
				return;
			}
			$("#updateCourseForm").data('bootstrapValidator').resetForm();
			var parent_category_code=$("#updateparentCode").val();
			if(parent_category_code==-1){
				category_level=1;
			}else{
				category_level=2;
			};
			var category={
				"category_code" : $("#categoryCode").val(),
				"category_name" : $("#updatecategoryName").val(),
				"parent_category_code" : parent_category_code,
				"category_level" : category_level,
				"sequence_number" : Number($("#updatesequenceNumber").val()),
				"description" : $("#updatedescription").val()
			};
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/updateCategory",
				data : JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					$('#updateCatalogModal').modal('hide');
					common.alertInfoMsg("修改成功!");
					categorymanage.emptyupdate();
					categorymanage.categoryTable();
				},
				error : function(request) {
					$('#updateCatalogModal').modal('hide');
					common.alertErrorMsg("修改失败!");
					categorymanage.categoryTable();
				}
			});
		},
		emptyupdate : function(){
			$("#categoryCode").val("");
			$("#addparentCode").val("");
			$("#updatecategoryName").val("");
			$("#updatesequenceNumber").val("");
			$("#updatedescription").val("");
		},
		delbtn : function(id,level){
			if(level==1){
				var r=confirm("确定删除该目录吗？");
			}else{
				var r=confirm("确定删除该类别吗？");
			}
			if (r==true){
				categorymanage.delcategory(id);
			}
		},
		delcategory : function(id){
			var category={
					"id" : Number(id)
			};
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/deleteCategory",
				data : JSON.stringify(category),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					if(data==0){
						common.alertInfoMsg("删除成功!");
						categorymanage.categoryTable();
					}
					if(data==1){
						common.alertErrorMsg("请先删除该目录下类别!");
						categorymanage.categoryTable();
					}
					if(data==2){
						common.alertErrorMsg("请先删除该类别下课程!");
						categorymanage.categoryTable();
					}
				},
				error : function(request) {
					common.alertErrorMsg("删除失败!");
					categorymanage.categoryTable();
				}
			});
		},
		addvalidator : function(){
			$('#addCourseForm').bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {/* 验证 */
					addcategoryName : {
						message : '类别名称不能为空',
						validators : {
							notEmpty : {
								message : '类别名称不能为空'
							}
						}
					},
					addparentCode : {
						message : '父级类别编码不能为空',
						validators : {
							notEmpty : {
								message : '父级类别编码不能为空'
							}
						}
					}
				}
			});
		},
		updatevalidator : function(){
			$('#updateCourseForm').bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/* 输入框不同状态，显示图片的样式 */
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {/* 验证 */
					updatecategoryName : {
						message : '类别名称不能为空',
						validators : {
							notEmpty : {
								message : '类别名称不能为空'
							}
						}
					},
					updateparentCode : {
						message : '父级类别名称不能为空',
						validators : {
							notEmpty : {
								message : '父级类别名称不能为空'
							}
						}
					}
				}
			});
		},
}