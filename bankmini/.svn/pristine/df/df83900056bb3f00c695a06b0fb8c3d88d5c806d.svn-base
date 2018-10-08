var common = {
	/**
	 * 将表单数据转为JSON字符串
	 */
	form2JosnStr : function(formId) {
		var data = {};
		$(formId).serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		return JSON.stringify(data);
	},

	loadContent : function(url) {
		$(".main_content").show();
		if (url == null || url == "") {
			url = "/ietlAdmin/pages/basic/basicPage.html";
		}
		location.href = "/ietlAdmin" + url;
	},

	alertInfoMsg : function(msg) {
		bootbox.setLocale("zh_CN");
		bootbox.alert({
			size : 'small',
			title : "提示信息",
			message : "<div class='text-center'><font color='blue'>" + msg + "</font></div>"
		});
	},

	confirmInfoMsg : function(msg, title, callbak) {
		bootbox.confirm({
			title : title,
			message : msg,
			buttons : {
				confirm : {
					label : '确定',
					className : 'btn-success'
				},
				cancel : {
					label : '取消',
					className : 'btn-danger'
				}
			},
			callback : callbak
		});
	},

	alertErrorMsg : function(msg) {
		bootbox.setLocale("zh_CN");
		bootbox.alert({
			size : 'small',
			title : "<font color='red'>错误信息</font>",
			message : "<div class='text-center'><font color='red'>&nbsp;&nbsp;" + msg + "</font></div>"
		});
	},

	alertWarnMsg : function(msg) {
		bootbox.setLocale("zh_CN");
		bootbox.alert({
			size : 'small',
			title : "<font color='red'>警告信息</font>",
			message : "<div class='text-center'><font  color='red'>" + msg + "</font></div>"
		});
	},
	isBlankStr : function(str) {
		if (str != undefined && str != null && str.length > 0) {
			return false;
		}
		return true;
		/*
		 * if (str == "" || str == null || str == undefined) { return true; }
		 * return false;
		 */
	},
	isNotBlankStr : function(str) {
		return !common.isBlankStr(str);
	},

	getContextPath : function() {
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		return result;
	},
	getRandomNum : function(length) {
		var num = "";
		for (var i = 0; i < length; i++) {
			num += Math.floor(Math.random() * 10)
		}
		return num;
	},
	htmlEncode : function(value) {
		return $('<div/>').text(value).html();
	},
	valueEncode : function(value, row, index) {
		return $('<div/>').text(value).html();
	},

	htmlDecode : function(value) {
		return $('<div/>').html(value).text();
	},
	valueDecode : function(value, row, index) {
		return $('<div/>').html(value).text();
	},
	/**
	 * 获取UUID
	 * 
	 * @returns
	 */
	generateUUID : function() {
		var d = new Date().getTime();
		var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			var r = (d + Math.random() * 16) % 16 | 0;
			d = Math.floor(d / 16);
			return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
		});
		return uuid.replace(/\-/g, "");
	},
	 stringToDate : function(fDate){  
		    var fullDate = fDate.split("-");  
		    return new Date(fullDate[0], fullDate[1]-1, fullDate[2], 0, 0, 0);  
		 } 
};

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}