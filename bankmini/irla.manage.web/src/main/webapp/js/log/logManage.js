var log = {};


/*面包屑*/

log.crumb = function(){
	$.ajax({
		type : "get",
		url : "/ietlAdmin/ws/findLog",
		success : function(data) {
			if(data.record ==1){
			var add_i =0;
			for(var i =0;i<data.row.length;i++){
				$("#log_Breadcrumbs").css("display","inline");
				var ul = document.getElementById("log_Breadcrumbs");
				// 添加 li
				var li = document.createElement("li");
				add_i++;
				// 设置 li 属性，如 id
				li.setAttribute("id", "logli_" + add_i);
				li.innerHTML = 
					"<a href='/ietlAdmin/ws/downLog/"+data.row[i]+"' id='"+data.row[i]+"' >" +data.row[i] +"----"+data.tal[i]+ "</a>";
				ul.appendChild(li);
			}
			}else{
				common.alertInfoMsg("暂无记录查询");
			}
		},
		error : function(request) {
			common.alertErrorMsg("查询失败!");
		}
	});
}
