var role = {};

role.initTable = function() {
	$('#role_table').bootstrapTable('destroy');
	$('#role_table').bootstrapTable({
		method: "post",
		url : "/ietlAdmin/ws/findUser",// 请求后台的URL（*）
		cache : false, // 不缓存
		striped : true, // 隔行加亮
		pagination : true, // 开启分页功能
		pageSize : 10, // 设置默认分页为 50
		pageList : [ 10, 25, 50, 100, 200 ], // 自定义分页列表
		search : false, // 开启搜索功能
		showColumns : false, // 开启自定义列显示功能
		showRefresh : false, // 开启刷新功能
		sidePagination : "server",//设置在哪里进行分页
		minimumCountColumns : 2, // 设置最少显示列个数
		queryParams : queryUserParam,
		clickToSelect : true, // 单击行即可以选中
		smartDisplay : true, // 智能显示 pagination 和 cardview 等
		/*responseHandler: function (res) {//这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
			$("#plateFormType").val(res.plateformType);
			if(res.plateformType != "1"){
				$("#addManage").hide();
				$("#uploadUsers").hide();
			}else{
				$("#addManage").show();
				$("#uploadUsers").show();
			}
            return res;
        },*/
		onLoadSuccess:function(){ 
			role.initSwitch();
			privilege.privilegeControl();
		},
		columns : [ {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : "10%",
			formatter :role.actionFormatter 
		},{
			field : 'userCode',
			title : '账号',
			align : 'center'
			/*formatter :role.omitUserCode*/
		},{
			field : 'userName',
			title : '姓名',
			align : 'center',
			formatter : common.valueEncode
		},{
			field : 'sex',
			title : '性别',
			align : 'center',
			formatter:role.actionType 
		},{
			field : 'dateOfBirth',
			title : '出生日期',
			align : 'center',
				formatter : function(text) {
					return new Date(text).format("yyyy-MM-dd");
				}
		},/*{
			field : 'phone',
			title : '联系电a话',
			align : 'center',
			formatter :role.omitPhone
		},*//*{
			field : 'idCard',
			title : '身份证号',
			align : 'center',
			formatter :role.omitIdCard
		},*//*,{
			field : 'email',
			title : '邮箱',
			align : 'center',
		},{
			field : 'residentialAddress',
			title : '居住地址',
			align : 'center',
			formatter :role.omitAddress
		},*/
		/*{
			field : 'plateformName',
			title : '所属平台',
			align : 'center',
		},*/{
			field : 'lastUpdateDate',
			title : '最后更新时间',
			align : 'center',
			formatter : function(date) {
				return new Date(date).format("yyyy-MM-dd hh:mm:ss");
			}
		},{
			field : 'lastUpdatedBy',
			title : '最后更新人',
			align : 'center',
		},{
			field : 'description',
			title : '描述',
			align : 'center',
			formatter : role.handleContent
		},{
			field : 'isValid',
			title : '状态标识',
			align : 'center',
			formatter :role.changeFormatter
		},]
	});
}

//性别类型
role.actionType =function(value,row,index) {
	var value;
	if(value==1){
		value = "男";
	}
	if(value==0){
		value = "女";
	}
	return value;
}

//传递的参数
function queryUserParam(params) {
	return {
		"limit" : params.limit,
		"offset" : params.offset,
		"userName" : $("#userName_search").val(),
		"userCode" : $("#userCode").val(),
		"sex" : $("#sex").val()
	};
} 	

role.handleContent = function(value, row, index) {
	if(!value){
		value = "--"
			return value;
	}
	else{
		var tempValue = $('<div/>').text(value).html();
		var result = "";
		if (tempValue.length >= 10) {
			result = tempValue.substring(0, 10) + "...";
		} else {
			result = tempValue;
		}
		return "<a style='cursor:pointer;text-decoration:none' id='conten" + index + "' onmouseover='role.overCell(this.id,\"" + tempValue
				+ "\")'  onmouseout='role.outCell(\"" + index + "\")'>" + result + "</a>";
	}
}

role.overCell = function(id, tempValue){
	layer.tips(tempValue, "#" + id, {
		tips : [ 2, '#3595CC' ],
		time : 10000
	});
}

role.outCell = function(index){
	layer.closeAll('tips');
}


//用户账号与手机同步输入
function copyCode () {
	document.all["phone"].value=document.all["user_code"].value;
}

//帐号省略
role.omitUserCode = function(value,row,index){
	var UserCodeString = row.userCode;  
    	var omitUserCodetemp = UserCodeString.substring(0,3) + "..."; 
    	return omitUserCode = "<span id = 'omitShow' data-tooltip="+UserCodeString+" >"+omitUserCodetemp+"</span>";
}

//手机号省略
role.omitPhone = function(value,row,index){
	var phone = row.phone; 
	var PhoneString = new String(phone);
	var omitPhonetemp = PhoneString.substring(0,3) + "..."; 
	return omitPhone = "<span id = 'omitShow' data-tooltip="+PhoneString+"  >"+omitPhonetemp+"</span>";
}


/*//身份证号省略
role.omitIdCard = function(value,row,index){
	var IdCardString = row.idCard;  
	var omitIdCardtemp = IdCardString.substring(0,3) + "..."; 
	return omitIdCard = "<span id = 'omitShow' data-tooltip="+IdCardString+"  >"+omitIdCardtemp+"</span>";
}*/

//地址省略
role.omitAddress  = function(value,row,index){
	if(row.town==row.province){
		var objString = row.province+row.district+row.residentialAddress;  
	}else{
		var objString = row.province+row.town+row.district+row.residentialAddress;
	}
    var objLength = objString.length; 
    var num = 3;
    if(objLength > num){
    	var omitAddresstemp = objString.substring(0,num) + "...";
    	return omitAddress = "<span id = 'omitShow' data-tooltip="+objString+" >"+omitAddresstemp+"</span>";
}  else{
	return objString;
}
}


//查询
role.searchBtn = function(){
				role.initTable();
}

//清空
role.empty = function(){
	$('#searchFrom')[0].reset();
}

//操作列的的图标显示
role.actionFormatter = function(value,row,index) {
	if(row.isValid == '1'){
		var edit = "<input type='button' id = 'update_btn' pri_url='/ws/updateUser' class='ico_a btn btn-info btn-xs ' title='修改' " +
		"onclick='role.updateUser("+row.id+")' value='改'>";
		var del = "<input type='button' id = 'delete_btn' pri_url='/ws/deleteUser' class='ico_a btn btn-danger btn-xs privilege-hints' title='删除' " +
		"onclick='role.deleteUser("+row.id+")' value='删'>";
		var reset = "<input type='button' id = 'reset_btn' pri_url='/ws/resetPassword' class='ico_a btn btn-warning btn-xs ' title='密码重置' " +
		"onclick='role.resetPassword("+row.id+")' value='置'>";
		return edit + "  "+ del + "  "+ reset;
	}else{
		var edit = "<a  id = 'update_btn'></a>";
		var del = "<a id = 'delete_btn'></a>";
		var reset = "<a  id = 'reset_btn'></a>";
		return edit + "  "+ del + "  "+ reset;
	}
	
}

//有效无效的样式显示
role.changeFormatter = function(value,row,index) {
	if(row.isValid == '1'){
		return "<input id= "+row.id+" pri_url='/ws/validUser' name='isValid' type='checkbox' " +
		" data-size='small' checked>";
	}else{
		return "<input id= "+row.id+" pri_url='/ws/validUser' name='isValid' type='checkbox' " +
		" data-size='small'>";
	}
}

//有效无效的切换
role.initSwitch = function(){
	if($('[name="isValid"]').length > 0){
		$('[name="isValid"]').bootstrapSwitch({  
		    onText:"有效",  
		    offText:"失效",  
		    onColor:"success",  
		    offColor:"danger",  
		    wrapperClass : "privilege-hints",
		    onSwitchChange : function(event, state) { 
		        	role.validUser($(this).attr("id"),state);
		    }
		 });
	}else{
		setTimeout(role.initSwitch,1000);
  }
	//权限切换
	
}

//有效无效的切换方法
role.validUser = function(id, state) {
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/validUser/" + id + "/" + state,
		success : function(data) {
			if(data){
				$('#role_table').bootstrapTable('refresh');
				common.alertInfoMsg("切换成功!");
			}
			if (!data) {
				common.alertInfoMsg("切换失败!");
			}
		},
		error : function(request) {
			common.alertErrorMsg("切换失败!");
		}
	});
}

//增加时弹出的模态框
role.showModel = function(modal) {
//	$("#department_Code").prop("value", "");
	$("#addressContent").fadeOut("fast");
	$("#addUserModal").modal("show");
	$("#user_code").attr("disabled",false);
	$('#addUserForm')[0].reset();
	/*$("#cerAuDiv").hide();*/
	/*$("#check_box").attr("checked", false);
	$("#check_box").prop("value", "0");
	$("#is_authentication").val("0");
	$("#choseDepa").attr({
		"disabled" : "disabled"
	});*/
	/*var type = $("#plateFormType").val();
	if(type != "1" && type != "2" ){
		$("#adminManage").hide();
		role.depaUsertree(type);
	}else{
		var plateformCode = "";
		role.plateformShow(plateformCode);
		$("#adminManage").show();
	}*/
	/*$("#addUserType").val(modal);*/
	/*if(modal == "0"){
		$("#depaShow").show();
		$("#attestation").show();
	}else{
		$("#depaShow").hide();
		$("#attestation").hide();
	}*/
		role.Validator();
		role.cityZtree();
		/*role.depaUsertree();*/
	$("#addUserForm").data('bootstrapValidator').resetForm() ;
}

//平台选择
/*role.plateformShow= function(plateformCode){
	$("#plateform_code").empty();
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/findPlateform",
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			var json = eval(data.rows);
			for (var i = 0; i < json.length; i++) {
				$('#plateform_code').append("<option value=" + json[i].depaCode + ">" + json[i].depaName + "</option>");
			}
			$('#plateform_code').selectpicker('refresh');
			if(plateformCode){
				$('#plateform_code').selectpicker('val',plateformCode);
				("#choseDepa").removeAttr("disabled");
				var modal = $("#addUserType").val();
				if(modal == "0"){
					role.depaUsertree(plateformCode);
				}
			}
		}
	});
}

//部门人员树
role.depaUsertree = function(plateformCode){
	$("#choseDepa").removeAttr("disabled");
	if(plateformCode){
	$.ajax({
		type: 'GET',
		url: "/ietlAdmin/ws/getDepartmentTree/"+ plateformCode,
	 	success:function(dataStr){
	 		if(dataStr){
	 			$('#treeDepa').treeview('remove');
	 			$('#treeDepa').treeview({
					data : dataStr,
					selectedIcon : 'glyphicon glyphicon-ok-sign',
					expandIcon : 'glyphicon glyphicon-plus',
					collapseIcon : 'glyphicon glyphicon-minus',
					selectedBackColor : '#A4D3EE',
					borderColor : "#C4C4C4",
					levels : '1',
	                onNodeSelected: function (event,data) {
	                	$("#department_name").val(data.text);
						$("#department_Code").val(data.depaCode);
						$("#treeDepa").hide();
						$('#addUserForm').data('bootstrapValidator').updateStatus('departmentName', 'NOT_VALIDATED', null)
					    .validateField('departmentName');
	                },
	                onNodeExpanded : function (event,data) {
	                 	   var brotherNode =  $('#treeDepa').treeview('getSiblings', data.nodeId);
	                 	   for(var n = 0; n < brotherNode.length;n++){
	                 		   $('#treeDepa').treeview('collapseNode', [ brotherNode[n].nodeId, { silent: true, ignoreChildren: false } ]);
	                 	   }
	                }
				});
 		    }
	 		var departmentCode = $("#department_Code").val();
	 		if(departmentCode){
	 		var node = $("#treeDepa").treeview("getEnabled");
	 		for(var i = 0;i<node.length;i++){
	 			if(departmentCode == node[i].depaCode){
	 				$("#treeDepa").treeview("selectNode", [ node[i].nodeId, { silent: true } ]);
		 			$('#treeDepa').treeview('revealNode', [ node[i].nodeId, { silent: true } ]);
	 			}
	 		}
	 		}
	 		},
		 	error : function(request) {
				common.alertErrorMsg("查询失败!");
			}
		 	});	
	}else{
		return;
	}
}


$(function() {
	$("#plateform_code").change(function() {
		var plateformCode = $("#plateform_code").val();
		$("#department_Code").val("");
		$("#department_name").val("");
		if (common.isBlankStr(plateformCode)) {
			// 移除树形数据
			$('#treeDepa').treeview('remove');
			$("#choseDepa").attr({
				"disabled" : "disabled"
			});
		} else {
			$("#choseDepa").removeAttr("disabled");
			role.depaUsertree(plateformCode);
		}
	});
	// 目录选择变化
	$("#choseDepa").on("click", function(e) {
		if ($("#treeDepa").is(":hidden")) {
			$("#treeDepa").show();
		} else {
			$("#treeDepa").hide();
		}
		e.stopPropagation();
	});

	$("#treeDepa").on("mouseleave", function() {
		$("#treeDepa").hide();
	});
});

//树形展示框出现
role.showMenu = function() {
	var deptObj = $("#department");
	$("#menuContent").css({ top:deptObj.top + deptObj.outerHeight() + "px", 
		"background"   : "#F5F5F5",
		"border-style" : "solid",
		"border-width" : "1px",
		"border-color" : "#3B3B3B"}).slideDown("normal");
	$("#addUserModal").bind("mousedown", DepaBodyDown);
}

var  nodeList = [], fontCss = {};

//模糊查询
role.blurQuery = function(txtObj){
	if (txtObj.value.length > 0) {
		$.ajax({
			type: 'GET',
			url: "/ietlAdmin/ws/findDepartment",
			contentType : "application/json;charset=utf-8",
		 	success:function(data){
		 		if(data){
		 			var zNodes = [];
	 				$(data.rows).each(function(j, dir){
	 						var zNode = {}
	 						zNode.id = dir.depaCode;
	 						zNode.pId = dir.parentDepaCode;
	 						zNode.name = dir.depaName;
	 						zNode.nocheck = true;
	 						zNodes.push(zNode);
		 			});
	 			   //将找到的nodelist节点更新至Ztree内
	 		       var setting = {
	 						view: {
	 							showLine: true,      //是否显示连接线
	 			                selectedMulti: false, //是否允许多选
	 			                 fontCss: getFontCss,//选中节点颜色改变
	 			                showIcon: showDepaForTree  //是否显示节点图标
	 			            },
	 						check: {
	 							enable: true,
	 						},
	 						data: {
	 							simpleData: {
	 								enable: true
	 							}
	 						},
	 						callback: {
	 							onClick: onDepaClick
	 						}
	 				};
	 		      $.fn.zTree.init($("#deptZtree"), setting, zNodes);
	 		 	 var treeObj = $.fn.zTree.getZTreeObj("deptZtree");
	 		 	 nodeList = treeObj.getNodesByParamFuzzy("name", txtObj.value, null);
	 		 
	 		 	if(nodeList.length==0){
	 		 		 role.deptZtree();
	 		 		role.showMenu();
	 		 	}
	 		 	else{
	 		 	updateNodes(true);
	 		 	for (var i = 0; i < nodeList.length; i++) {
	 		 		var nodes = treeObj.getNodesByParam("name", nodeList[i].name, null);
	 		 	}
	 		 	for(var j = 0; j < nodes.length; j++){
	 		 		var parentNode = nodes[j].getParentNode();
	 		 		treeObj.expandNode(parentNode, true, false, true);
	 		 	}
	 		        role.showMenu();
	 		 	}
	 		    }
		 		},
			 	error : function(request) {
					common.alertErrorMsg("选择失败!");
				}
			 	});
	}else {
	        //隐藏树
	 	   role.hideMenu();
	 	  role.deptZtree();
	 	}
}



function updateNodes(highlight) {
	var zTree = $.fn.zTree.getZTreeObj("deptZtree");
	for( var i=0, l=nodeList.length; i<l; i++) {
		nodeList[i].highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}

function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

//树形展示框消失
role.hideMenu = function() {
	$("#menuContent").fadeOut("fast");
	$("#addUserModal").unbind("mousedown", DepaBodyDown);
}
//鼠标点击其它地方，树形展示框消失
function DepaBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		role.hideMenu();
	}
}

//树形结构部门选择
role.deptZtree = function(){
	$.ajax({
		type: 'GET',
		url: "/ietlAdmin/ws/findDepartment",
		contentType : "application/json;charset=utf-8",
	 	success:function(data){
	 		if(data){
	 			var zNodes = [];
 				$(data.rows).each(function(j, dir){
 						var zNode = {}
 						zNode.id = dir.depaCode;
 						zNode.pId = dir.parentDepaCode;
 						zNode.name = dir.depaName;
 						zNode.nocheck = true;
 						zNodes.push(zNode);
	 			});
 				var setting = {
 						view: {
 							showLine: true,      //是否显示连接线
 			                selectedMulti: false, //是否允许多选
			                showIcon: showDepaForTree  //是否显示节点图标
 			            },
	 					check: {
	 						enable: true,
	 					},
	 					data: {
	 						simpleData: {
	 							enable: true
	 						}
	 					},
	 					callback: {
	 						onClick: onDepaClick
	 					}
	 			};
 				$.fn.zTree.init($("#deptZtree"), setting, zNodes);
 				var treeObj = $.fn.zTree.getZTreeObj("deptZtree");
 				var ztreeid = $("#department_Code").val();
 				if(ztreeid){
 				var nodes =  treeObj.getNodeByParam("id",ztreeid);
 				var parentNode  = nodes.getParentNode();
 				treeObj.expandNode(parentNode, true, false, true);
 				}
	 		}
	 	},
	 	error : function(request) {
			common.alertErrorMsg("选择失败!");
		}
	 	});
}

//是否显示节点图标
function showDepaForTree(treeId, treeNode) {
	return !treeNode;
};



//点击某个节点 然后将该节点的名称赋值值文本框
function onDepaClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("deptZtree");
	//获得选中的节点
	nodes = zTree.getSelectedNodes();
	 v = "";
	//根据id排序
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0; i<nodes.length; i++) {
		v += nodes[i].name + ",";
	}
	 //将选中节点的名称显示在文本框内
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	$("#department").prop("value", v);
	$("#department_Code").val(nodes[0].id);
	$("#menuContent").fadeOut("fast");
    return false;
}*/

//居住地址选择框出现
function showAddress() {
	var addressObj = $("#address_Btn");
	$("#addressContent").css({ top:addressObj.top + addressObj.outerHeight() + "px", 
		"background": "#F5F5F5"}).slideDown("fast");
	$("#addUserModal").bind("mousedown", BodyDown);
}
//鼠标点击其它地方，居住地址树形展示框消失
function BodyDown(event) {
	if (!(event.target.id == "showAddressBtn" || event.target.id == "addressContent" || $(event.target).parents("#addressContent").length>0)) {
		$("#addressContent").fadeOut("fast");
	}
}

//居住地址初始化
role.cityZtree = function(){
	$("#addressContent").citySelect({  
	    url:"/ietlAdmin/plugin/city.min.js",  
	    prov:"重庆", //省份 
	    city:"万州区", //城市 
	    nodata:"none" //当子集无数据时，隐藏select 
	}); 
}

//保存居住地址
role.addressSave = function(){
	var province  = $('#prov option:selected').val();
    var town = $('#city option:selected').val();
    var district = $('#dist option:selected').val();
    if(district == undefined ){
    		var addressBtn = province+"市"+town;
    	    $("#province").val(province+"市");
    		 $("#town").val(province+"市");
    		 $("#district").val(town);
    }else {
    	var addressBtn = province+"省"+town+"市"+district;
    	$("#province").val(province+"省");
    	$("#town").val(town+"市");
    	$("#district").val(district);
    }
    $("#addressContent").fadeOut("fast");
    $("#address_Btn").prop("value", addressBtn);
}

//增加
role.addUser = function() {
	//表单验证
	$('#addUserForm').data('bootstrapValidator').validate(); 
    if(!$('#addUserForm').data('bootstrapValidator').isValid()){ 
        return;
    }$("#addUserForm").data('bootstrapValidator').resetForm() ;
   /* var checkBox = $("#check_box").val();
    if(checkBox == "1"){
    	var certificationAuthority = $("#certification_authority").val();
    	if(!certificationAuthority){
    		common.alertInfoMsg("请填写认证机构名称");
    		return;
    	}
    }*/
/*	//检查单位是否为空
	var depaName = $("#department_Code").val();
	if(!depaName){
		common.alertErrorMsg("请选择单位");
		return;
	}*/
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/addUser",
		data : common.form2JosnStr("#addUserForm"),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if(data){
				$("#addUserModal").modal("hide");
				$('#addUserForm')[0].reset();
				$('#role_table').bootstrapTable('refresh');
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

//重置密码
role.resetPassword = function(id) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("你确定重置该用户密码吗?", function(result) {
		if (result) {
	$.ajax({
		type : "POST",
		url : "/ietlAdmin/ws/resetPassword",
		data: JSON.stringify({
			"id" : id,
		}),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			common.alertInfoMsg("密码已被重置为“123456”！");
		},
		error : function(request) {
			common.alertErrorMsg("操作失败!");
		}
	});
		}
	});
}

//修改
role.updateUser = function(id) {
	//表单验证
		role.Validator();
	$("#addUserForm").data('bootstrapValidator').resetForm() ;
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/updateUser/"+ id,
		success : function(data) {
			if(data != null){
				if(data.province==data.town){
					data.addressBtn = data.province+data.district;
				}else{
					data.addressBtn = data.province+data.town+data.district;
				}
				data.dateOfBirth = new Date(data.dateOfBirth).format('yyyy-MM-dd');
				$("#addUserForm")[0].reset();
				$("#addUserForm").fill(data,{styleElementName:'none'});
				/*$("#department_name").val(data.department);*/
				$("#addUserModal").modal("show");
				/*var departmentName = data.department;
				if(departmentName){
					$("#depaShow").show();
					$("#attestation").show();
					$("#addUserType").val("0");
				}else{
					$("#depaShow").hide();
					$("#attestation").hide();
					$("#addUserType").val("1");
				}
				var type = $("#plateFormType").val();
				if(type != "1" && type != "2" ){
					$("#adminManage").hide();
					role.depaUsertree(type);
				}else{
					role.plateformShow(data.plateformCode);
					$("#adminManage").show();
				}
				$("#check_box").val(data.isAuthentication);
				if(data.isAuthentication == "1"){
					$("#check_box").prop("checked", true);
					$("#cerAuDiv").show();
				}else{
					$("#check_box").prop("checked", false);
					$("#cerAuDiv").hide();
				}*/
				role.cityZtree();
			
			$("#interim_Code").val(data.userCode);
			if(data.userType!="1"){
			$("#user_code").attr("disabled",true);
			}
			}
			else{
				common.alertInfoMsg("您想要修改的记录可能不存在，请刷新重试!");
			}
			},
		error : function(request) {
			common.alertErrorMsg("修改失败!");
		}
	});
}

//删除
role.deleteUser = function(id) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("你确定删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/ietlAdmin/ws/deleteUser/" + id,
				success : function(data) {
					if (data){
						$('#role_table').bootstrapTable('refresh');
						common.alertInfoMsg("删除成功!");
					}else {
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

//表单验证
role.Validator = function() {
	     $('#addUserForm').bootstrapValidator({
	    	message: 'This value is not valid',
	            feedbackIcons: {//输入框不同状态，显示图片的样式
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {//验证
	                userName: {
	                    message:'姓名不能为空',
	                    validators: {
	                        notEmpty: {
	                            message: '姓名不能为空'
	                        },
	                        stringLength: {
	                            min: 2,
	                            max: 30,
	                            message: '姓名长度必须在2到30之间'
	                        },
	                    }
	                },
	                sex: {
	                    validators: {
	                        notEmpty: {
	                            message: '请选择性别'
	                        }
	                    }
	                },
	               /* departmentName: {//键名和input name值对应
	                    message: '部门不能为空',
	                    validators: {
	                        notEmpty: {//非空提示
	                            message: '部门不能为空'
	                        }
	                    }
	                },
	                plateformCode: {//键名和input name值对应
	                    message: '平台不能为空',
	                    validators: {
	                        notEmpty: {//非空提示
	                            message: '平台不能为空'
	                        }
	                    }
	                },*/
	                dateOfBirth: {
	                    validators: {
	                    	notEmpty: {//非空提示
	                            message: '出生日期不能为空'
	                        }
	                    }
	                },
	                /*email: {//键名和input name值对应
	                    message: '邮箱为找回密码所用，不能为空',
	                    validators: {
	                        notEmpty: {//非空提示
	                            message: '邮箱为找回密码所用，不能为空'
	                        },
	                        emailAddress: {
	                        	 message: '邮箱地址格式有误'
	                        }
	                    }
	                },*/
	             /*   idCard: {//键名和input name值对应
	                    message: '身份证号不能为空',
	                    validators: {
	                        notEmpty: {//非空提示
	                            message: '身份证号不能为空'
	                        },
	                        stringLength: {
	                            min: 18,
	                            max: 18,
	                            message: '请输入18位身份证号'
	                        },
	                        regexp: { //只需加此键值对 
	                            regexp: /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
	                            message: '请输入正确的身份证号'
	                        }
	                    }
	                },*/
	                userCode: {
	                    message: '用户帐号不能为空',
	                    validators: {
	                        notEmpty: {
	                            message: '用户帐号不能为空'
	                        },
	                        stringLength: {
	                            min: 11,
	                            max: 11,
	                            message: '请输入11位手机号码'
	                        },
	                        regexp: {
	                            regexp: /^1[34578][0-9]{9}$/,
	                            message: '请输入正确的手机号码'
	                        }
	                    }
	                }/*,
	                phone: {
	                    validators: {
	                    	notEmpty: {
	                            message: '手机号码不能为空'
	                        },
	                        stringLength: {
	                            min: 11,
	                            max: 11,
	                            message: '请输入11位手机号码'
	                        },
	                        regexp: {
	                            regexp: /^1[34578][0-9]{9}$/,
	                            message: '请输入正确的手机号码'
	                        }
	                    }
	                }*/
	              /*  departmentCode: {//键名和input name值对应
	                    message: '部门不能为空',
	                    validators: {
	                        notEmpty: {//非空提示
	                            message: '部门不能为空'
	                        }
	                    }
	                }*/,
	                description: {//键名和input name值对应
	                    validators: {
	                    	stringLength: {
	                            min: 0,
	                            max: 200,
	                            message: '描述不能超过200个字数'
	                        }
	                    }
	                },
	                residentialAddress: {//键名和input name值对应
	                    validators: {
	                    	stringLength: {
	                            min: 0,
	                            max: 80,
	                            message: '居住地址不能超过80个字数'
	                        }
	                    }
	                }
	            }
	        });
	}

//是否得到机构认证
/*role.radio = function() {
	var radioCheck = $("#check_box").val();
	if ("1" == radioCheck) {
		$("#check_box").attr("checked", false);
		$("#check_box").val("0");
		$("#is_authentication").val("0");
		$("#cerAuDiv").hide();
	} else {
		$("#check_box").attr("checked", true);
		$("#check_box").val("1");
		$("#is_authentication").val("1");
		$("#cerAuDiv").show();
	}
}*/

//导入用户
role.uploadMoreUser = function() {
	$("#uploadUserModal").modal("show");
	$("#plateform_code_up").empty();
	$('#plateform_code_up').selectpicker('refresh');
	$('#uploadUserForm')[0].reset();
	role.ValidatorUser();
    $("#uploadUserForm").data('bootstrapValidator').resetForm() ;
	role.plateCodeShow();
	$("#user_upload").fileinput('destroy');
	// 初始化文件上传组件
	$("#user_upload").fileinput({
		language : 'zh', // 设置语言
		allowedFileExtensions : [ 'xls', 'xlsx' ],// 接收的文件后缀
		showUpload : false, // 是否显示上传按钮
		showCaption : true,// 是否显示标题
		showCancel:false,
		browseClass : "btn btn-inverse", // 按钮样式
		previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
		maxFileSize : 307200, // 3M
		layoutTemplates:{
        	actionDelete:'',
        	actionUpload:'',
        },
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
	});
}

//导入用户时平台展示
role.plateCodeShow= function(){
	$("#plateform_code_up").empty();
	$.ajax({
		type : "GET",
		url : "/ietlAdmin/ws/findPlateform",
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			var json = eval(data.rows);
			for (var i = 0; i < json.length; i++) {
				$('#plateform_code_up').append("<option value=" + json[i].depaCode + ">" + json[i].depaName + "</option>");
			}
			$('#plateform_code_up').selectpicker('refresh');
		}
	});
}

//确定导入用户
role.uploadUser = function(){
	//表单验证
	$('#uploadUserForm').data('bootstrapValidator').validate(); 
    if(!$('#uploadUserForm').data('bootstrapValidator').isValid()){ 
        return;
    }$("#uploadUserForm").data('bootstrapValidator').resetForm() ;
    //检查文件上传是否为空
	var file = $("#user_upload").val();
		if(!file){
			common.alertErrorMsg("请选择文件导入！");
			return;
		}
		// 添加正在上传的面板提示
		var uploadMsg = bootbox.dialog({
			size : 'small',
			closeButton: false,
			message : '<div class="text-center" style="color:green;"><i class="fa fa-spin fa-spinner"></i>正在导入中，请稍等...</div>'
		});
	$("#uploadUserForm").ajaxSubmit({
		type : "post",
		url : "/ietlAdmin/ws/uploadMoreUser",
		contentType : "multipart/form-data;charset=UTF-8",
		success : function(data) {
			uploadMsg.modal('hide');
			$("#uploadUserModal").modal('hide');
			$('#uploadUserForm')[0].reset();
			$('#role_table').bootstrapTable('refresh');
			if(data == "1"){
				common.alertInfoMsg("操作成功!");
			}else if(data == "0"){
				common.alertInfoMsg("操作失败!");
			}
			else {
				common.alertErrorMsg("部分用户数据导入失败：<a style = 'color:#0f09f9;cursor:pointer;' " +
						"href='/ietlAdmin/ws/downErrorUser/"+data+"'>点此下载错误用户数据信息</a>");
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			uploadMsg.modal('hide');
			common.alertErrorMsg("操作失败!");
		}
	});
}

//表单验证
/*role.ValidatorUser = function() {
	     $('#uploadUserForm').bootstrapValidator({
	    	message: 'This value is not valid',
	            feedbackIcons: {//输入框不同状态，显示图片的样式
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {//验证
	                plateformCode: {//键名和input name值对应
	                    message: '平台不能为空',
	                    validators: {
	                        notEmpty: {//非空提示
	                            message: '平台不能为空'
	                        }
	                    }
	                }
	            }
	        });
	}*/
