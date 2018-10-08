$(function(){
	$("#search").click(function(){
		$("#courseCount").css("display:inline");
		section.resourse();
	});
	$("#save").click(function(){
		$("#courseCount").css("display:inline");
		section.saveSort();
	});
}); 
var section={
		//加载类别表
		resourse : function(){
			$('#section_table').bootstrapTable('destroy');
			$('#section_table').bootstrapTable({
				method : "post",
				url : "/ietlAdmin/ws/findResourse",// 请求后台的URL（*）
				cache : false, // 不缓存
				striped : true, // 隔行加亮
				pagination : true, // 开启分页功能
				pageSize : 100, // 设置默认分页为 50
				pageList : [ 100, 200], // 自定义分页列表
				search : false, // 开启搜索功能
				showColumns : false, // 开启自定义列显示功能
				showRefresh : false, // 开启刷新功能
				sidePagination : "server",// 设置在哪里进行分页
				queryParams : section.queryDataParams,
				minimumCountColumns : 2, // 设置最少显示列个数
				clickToSelect : true, // 单击行即可以选中
				smartDisplay : true, // 智能显示 pagination 和 cardview 等
				responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
					document.getElementById('typeCount').innerHTML = res.total;
					return res;
				},
				columns : [{
					field : 'operate',
					title : '操作',
					align : 'center',
					width : "10%",
					formatter : section.actionFormatter
				},{
					field : 'sequenceNumber',
					title : '集数',
					align : 'center'
				},{
					field : 'id',
					title : '资源ID',
					align : 'center'
				},{
					field : 'resourceName',
					title : '资源名称',
					align : 'center'
				},{
					field : 'courseName',
					title : '课程名称',
					align : 'center'
				},{
					field : 'lastUpdatedBy',
					title : '修改人',
					align : 'center',
					formatter : common.valueEncode
				},{
					field : 'lastUpdateDate',
					title : '修改时间',
					align : 'center',
					formatter : function(date) {
						return new Date(date).format("yyyy-MM-dd hh:mm:ss");
					}
				}]

			});
		},
		queryDataParams : function(params) {
			return {
				"limit" : null,
				"offset" : null,
				"courseCode" : $("#courseName").val(),
			};
		},
		//操作
		actionFormatter : function(value, row, index) {
			var sectionchange = "<input type='button' id = '' class='ico_a btn btn-info btn-xs ' title='集数设置' "
				+ "onclick='section.updateSection(" + index + "," + row.sequenceNumber + ")' value='集数设置'>";
			
			return sectionchange;
		},
		
		option : function(category_code) {
			var course={
					"category_code" : category_code
			};
			$.ajax({
				type : "post",
				url : "/ietlAdmin/ws/getCourse",
				data: JSON.stringify(course),
				contentType : "application/json;charset=utf-8",
				async:false,
				success : function(data) {
					$('#courseName').empty();
					$('#courseName').append("<option value='' selected='selected' style='text-align:center;'>--------请选择--------</option>");
					for (var i = 0; i < data.rows.length; i++) {
						$('#courseName').append("<option value=" + data.rows[i].course_code + ">" + data.rows[i].course_name + "</option>");
					};
					
				},
			});
		},
		updateSection : function(index,seqNum) {
			if (seqNum == "" || seqNum == null) {
				seqNum = "";
			}
			$('#section_table').bootstrapTable(
					'updateRow',{
						index : index,
						row : {
							sortFlag : "1",
							sequenceNumber : '<input type="text" class="form-control" id="sequenceNumber" name="sequenceNumber" value="'
								+seqNum+'" style="width: 100px;margin: 0 auto;">',
							}
					});
			$("#sequenceNumber").blur(function(){
				var sequence=$("#sequenceNumber").val();
				$('#section_table').bootstrapTable(
						'updateRow',{
							index : index,
							row : {
								sortFlag : "1",
								sequenceNumber : sequence,
								}
						});
			});
		},
		saveSort : function() {
			var resourses = new Array();
			$("#section_table tbody tr").each(function() {
				var sequenceNumber = $($(this).children("td").get(1)).text();
				var id = $($(this).children("td").get(2)).text();
				if(!isNaN(sequenceNumber)) {
					var tmpJson = {"id":id,"sequenceNumber":sequenceNumber};
					resourses.push(tmpJson);
				}
			});
			for (var i = 0; i < resourses.length - 1; i++){
				var temp = resourses[i].sequenceNumber;
				for (var j = i + 1; j < resourses.length; j++){
					if (temp==resourses[j].sequenceNumber){
						common.alertInfoMsg("集数不能重复，请重新设置！");
						return;
					}
				}
			}
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/updateResourseSection",
				data : JSON.stringify(resourses),
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					$('#section_table').bootstrapTable('refresh');
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
};