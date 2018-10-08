var cacheHy = {
	initCacheNameList : function() {
		$.get("/ietlAdmin/ws/getCacheNameList", function(data) {
			//清空下拉框
			$("#cacheNameHy").empty();
			//首先添加一个空的
			$.each(data, function(i, value) {
				$("#cacheNameHy").append("<option value='" + value + "'>" + value + "</option>");
			});
			$("#cacheNameHy").selectpicker('refresh');
		});
	},
	clearAllCache : function() {
		$.ajax({
			type : "GET",
			url : "/ietlAdmin/ws/clearCache",
			success : function(data) {
				if (data) {
					common.alertInfoMsg("缓存清除成功");
				} else {
					common.alertErrorMsg("缓存清除失败");
				}
			},
			error : function(request) {
				common.alertErrorMsg("服务器内部错误,请联系管理员");
			}
		});
	},
	clearCacheByName : function() {
		//获取下拉框选择的值
		var cacheName = $("#cacheNameHy").val();
		if(cacheName=="" || cacheName == null || cacheName ==undefined){
			common.alertErrorMsg("选择错误，请重新选择...");
			return;
		}
		$.ajax({
			type : "GET",
			url : "/ietlAdmin/ws/clearCacheByName/"+cacheName,
			success : function(data) {
				if (data) {
					common.alertInfoMsg("缓存清除成功");
				} else {
					common.alertErrorMsg("缓存清除失败");
				}
			},
			error : function(request) {
				common.alertErrorMsg("服务器内部错误,请联系管理员");
			}
		});
	},
	remoteClearCache: function() {
		$.ajax({
			type : "GET",
			url : "/ietlAdmin/ws/remoteClearCache",
			contentType : "application/json;charset=utf-8",
			dataType:"text",
			success : function(data) {
				if (data==undefined || ""==data) {
					common.alertInfoMsg("缓存清除成功");
				} else {
					common.alertErrorMsg(data);
				}
			},
			error:function(request) {
				common.alertErrorMsg("服务器内部错误,请联系管理员");
			}
		});
	},
	remoteRedisCache: function() {
		$.ajax({
			type : "POST",
			url : "/ietlAdmin/ws/removeRedisList",
			contentType : "application/json;charset=utf-8",
			dataType:"text",
			success : function(data) {
				if (data) {
					common.alertInfoMsg("清理成功");
				} else {
					common.alertErrorMsg(data);
				}
			},
			error:function(request) {
				common.alertErrorMsg("服务器内部错误,请联系管理员");
			}
		});
	}
	
}
