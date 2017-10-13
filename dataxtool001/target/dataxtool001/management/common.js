
var JUDGE = {
		/**
		 * null的判断
		 */
		'isNull':function(obj){
			return (null==obj || obj.length==0 || 'null' == obj || undefined == obj);
		},
		
		/**
		 * 从网址链接中获取参数
		 */
		'getURLParameter':function(name) {
		    return decodeURI(
		        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
		    );
		}
}

function getURLParameter(name) {
    return decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
}

/**
 * 实现radio的取消选择
 */
$(document).ready(function(){
	var radiostate="";
	$("input[type=radio]").click(function(){
		var checked = $(this).attr("checked");
		if(checked=="checked" ){
			if(radiostate==this){
				$(this).removeAttr("checked");
				radiostate="";
			}
			else{
				radiostate=this;
			}		
		}	
	});
});


/**
 * 拆分多选框字符串 例如“03=饮食|04=锻炼|01=戒烟|05=减体重|02=健康饮酒|”将被拆分为“饮食、锻炼、戒烟、减体重、健康饮酒”
 */
function rebuildcheckboxlist(str){
	var resultvalue = "";
	if(!JUDGE.isNull(str)){
		var guidarr = str.split('|');
		var j=0;
		for(j=0;j<guidarr.length;j++){
			if(!JUDGE.isNull(guidarr[j])){
				var guidsubarr = guidarr[j].split('=');
				if(guidsubarr.length>1 && resultvalue.indexOf(guidsubarr[1])<0){
					if(resultvalue.length>0)
						resultvalue += "、";
					resultvalue+=guidsubarr[1];
				}
			}
		}
	}
	return resultvalue;
}



/**
 * 切换风格
 */
$(document).ready(function(){
	
	var baselinkhref = "css/theme/default/base.css";
	var indexlinkhref = "css/theme/default/index.css";
	
	// 从cookie中取到theme
	var theme = $.cookie('skynettheme');
	if(!JUDGE.isNull(theme) && theme=="pink"){
		baselinkhref = "css/theme/pink/base.css";
		indexlinkhref = "css/theme/pink/index.css";
	}	
	
	// 给index设置href
	var $linkindex = $("#linkindex");	
	if(!JUDGE.isNull($linkindex)){
		$linkindex.attr("href",indexlinkhref);
	}
});


/**
 * 右侧上下文菜单相关的js
 */
(function($) {
	$(function() {
		// 停靠或者展开
		$("#frameswitch").click(function(){
			var ishidden = $("#framerightnav").css("width");		
			if(ishidden=="0px"){								
				$("#framerightnav").removeClass("framerightnavclose");
				$("#framemain").removeClass("framemainfill");
				$("#frameswitch").attr('src','../../images/frameswitch1.png');
			}else{
				$("#framerightnav").addClass("framerightnavclose");
				$("#framemain").addClass("framemainfill");
				$("#frameswitch").attr('src','../../images/frameswitch2.png');
			}
		});

	});
})(window.jQuery);


/**
 * 清空指定表单中的内容,参数为目标form的id 注：在使用Jquery EasyUI的弹出窗口录入新增内容时，每次打开必须清空上次输入的历史
 * 数据，此时通常采用的方法是对每个输入组件进行置空操作:$("#name").val(""),这样做，
 * 当输入组件比较多时会很繁琐，产生的js代码很长，这时可以将所有的输入组件放入个form表单 中，然后调用以下方法即可。
 * 
 * @param formId
 *            将要清空内容的form表单的id
 */
function resetContent(formId) {
	var clearForm = document.getElementById(formId);
	if (null != clearForm && typeof(clearForm) != "undefined") {
		clearForm.reset();
	}
}


/**
 * 刷新DataGrid列表(适用于Jquery Easy Ui中的dataGrid)
 * 注：建议采用此方法来刷新DataGrid列表数据(也即重新加载数据)，不建议直接使用语句
 * $('#dataTableId').datagrid('reload');来刷新列表数据，因为采用后者，如果日后
 * 在修改项目时，要在系统中的所有刷新处进行其他一些操作，那么你将要修改系统中所有涉及刷新
 * 的代码，这个工作量非常大，而且容易遗漏；但是如果使用本方法来刷新列表，那么对于这种修 该需求将很容易做到，而去不会出错，不遗漏。
 * 
 * @param dataTableId
 *            将要刷新数据的DataGrid依赖的table列表id
 */
function flashTable(dataTableId){
	$('#'+dataTableId).datagrid('reload');
}

/**
 * 取消DataGrid中的行选择(适用于Jquery Easy Ui中的dataGrid)
 * 注意：解决了无法取消"全选checkbox"的选择,不过，前提是必须将列表展示
 * 数据的DataGrid所依赖的Table放入html文档的最全面，至少该table前没有 其他checkbox组件。
 * 
 * @param dataTableId
 *            将要取消所选数据记录的目标table列表id
 */
function clearSelect(dataTableId) {
	$('#'+dataTableId).datagrid('clearSelections');
	// 取消选择DataGrid中的全选
	for ( var i = 0; i < $("input[type='checkbox']").size(); i++) {
		if($("input[type='checkbox']").eq(i).val()=="on"){
			$("input[type='checkbox']").eq(i).attr("checked",false);
		}
	}
	
	
}

/**
 * 关闭Jquery EasyUi的弹出窗口(适用于Jquery Easy Ui)
 * 
 * @param dialogId
 *            将要关闭窗口的id
 */
function closeDialog(dialogId) {
	$('#'+dialogId).dialog('close');
}

/**
 * 自适应表格的宽度处理(适用于Jquery Easy Ui中的dataGrid的列宽),
 * 注：可以实现列表的各列宽度跟着浏览宽度的变化而变化，即采用该方法来设置DataGrid
 * 的列宽可以在不同分辨率的浏览器下自动伸缩从而满足不同分辨率浏览器的要求
 * 使用方法：(如:{field:'ymName',title:'编号',width:fillsize(0.08),align:'center'},)
 * 
 * @param percent
 *            当前列的列宽所占整个窗口宽度的百分比(以小数形式出现，如0.3代表30%)
 * 
 * @return 通过当前窗口和对应的百分比计算出来的具体宽度
 */
function fillsize(percent){
	var bodyWidth = document.body.clientWidth;
	return (bodyWidth-90)*percent;
 }
 
 /**
	 * 获取所选记录行(单选)
	 * 
	 * @param dataTableId
	 *            目标记录所在的DataGrid列表的table的id
	 * @param errorMessage
	 *            如果没有选择一行(即没有选择或选择了多行)的提示信息
	 * 
	 * @return 所选记录行对象，如果返回值为null,或者"null"(有时浏览器将null转换成了字符串"null")说明没有 选择一行记录。
	 */
function getSingleSelectRow(dataTableId, errorMessage) {
	var rows = $('#'+dataTableId).datagrid('getSelections');
	var num = rows.length;
	if(num == 1){
		return rows[0];
	}else{
		$.messager.alert('提示消息',errorMessage,'info');
		return null;
	}
}

/**
 * 在DataGrid中获取所选记录的id,多个id用逗号分隔
 * 注：该方法使用的前提是：DataGrid的idField属性对应到列表Json数据中的字段名必须为id
 * 
 * @param dataTableId
 *            目标记录所在的DataGrid列表table的id
 * 
 * @return 所选记录的id字符串(多个id用逗号隔开)
 */
function getSelectIds(dataTableId, noOneSelectMessage) {
	var rows = $('#'+dataTableId).datagrid('getSelections');
	var num = rows.length;
	var ids = null;
	if(num  < 1){
		if (null != noOneSelectMessage)$.messager.alert('提示消息',noOneSelectMessage,'info');
		return null;
	}else{
		for(var i = 0; i < num; i++){
			if(null == ids || i == 0){
				ids = rows[i].id;
			} else {
				ids = ids + "," + rows[i].id;
			}
		}
		return ids;
	}
}

/**
 * 删除所选记录(适用于Jquery Easy Ui中的dataGrid)(删除的依据字段是id)
 * 注：该方法会自动将所选记录的id(DataGrid的idField属性对应到列表Json数据中的字段名必须为id)
 * 动态组装成字符串，多个id使用逗号隔开(如：1,2,3,8,10)，然后存放入变量ids中传入后台，后台
 * 可以使用该参数名从request对象中获取所有id值字符串，此时在组装sql或者hql语句时可以采用in 关键字来处理，简介方便。
 * 另外，后台代码必须在操作完之后以ajax的形式返回Json格式的提示信息，提示的json格式信息中必须有一个
 * message字段，存放本次删除操作成功与失败等一些提示操作用户的信息。
 * 
 * @param dataTableId
 *            将要删除记录所在的列表table的id
 * @param requestURL
 *            与后台服务器进行交互，进行具体删除操作的请求路径
 * @param confirmMessage
 *            删除确认信息
 */
function deleteNoteById(dataTableId, requestURL, confirmMessage){
	if (null == confirmMessage || typeof(confirmMessage) == "undefined" || "" == confirmMessage) {
		confirmMessage = "确定删除所选记录?";
	}
	var rows = $('#'+dataTableId).datagrid('getSelections');
	var num = rows.length;
	var ids = null;
	if(num  < 1){
		$.messager.alert('提示消息','请选择你要删除的记录!','info');
	}else{
		$.messager.confirm('确认', confirmMessage, function(r){
			if (r) {
				for(var i = 0; i < num; i++){
					if(null == ids || i == 0){
						ids = rows[i].id;
					} else {
						ids = ids + "," + rows[i].id;
					}
				}
				$.getJSON(requestURL,{"ids":ids},function(data){
					if (null != data && null != data.message && "" != data.message) {
						$.messager.alert('提示消息',data.message,'info');	
						flashTable(dataTableId);
					} else {
						$.messager.alert('提示消息','删除失败！','warning');
					}
					clearSelect(dataTableId);
				});
			}
		});
	}
}

/* jquery cookie */
/*
 * ! jQuery Cookie Plugin v1.3.1 https://github.com/carhartl/jquery-cookie
 * 
 * Copyright 2013 Klaus Hartl Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as anonymous module.
		define(['jquery'], factory);
	} else {
		// Browser globals.
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function raw(s) {
		return s;
	}

	function decoded(s) {
		return decodeURIComponent(s.replace(pluses, ' '));
	}

	function converted(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}
		try {
			return config.json ? JSON.parse(s) : s;
		} catch(er) {}
	}

	var config = $.cookie = function (key, value, options) {

		// write
		if (value !== undefined) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setDate(t.getDate() + days);
			}

			value = config.json ? JSON.stringify(value) : String(value);

			return (document.cookie = [
				config.raw ? key : encodeURIComponent(key),
				'=',
				config.raw ? value : encodeURIComponent(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use
																						// expires
																						// attribute,
																						// max-age
																						// is
																						// not
																						// supported
																						// by
																						// IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// read
		var decode = config.raw ? raw : decoded;
		var cookies = document.cookie.split('; ');
		var result = key ? undefined : {};
		for (var i = 0, l = cookies.length; i < l; i++) {
			var parts = cookies[i].split('=');
			var name = decode(parts.shift());
			var cookie = decode(parts.join('='));

			if (key && key === name) {
				result = converted(cookie);
				break;
			}

			if (!key) {
				result[name] = converted(cookie);
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		if ($.cookie(key) !== undefined) {
			// Must not alter options, thus extending a fresh object...
			$.cookie(key, '', $.extend({}, options, { expires: -1 }));
			return true;
		}
		return false;
	};

}));

function BrowseFolder(){
	 try{
	  var Message = "请选择文件夹";  // 选择框提示信息
	  var Shell = new ActiveXObject( "Shell.Application" );
	  var Folder = Shell.BrowseForFolder(0,Message,0x0040,0x11);// 起始目录为：我的电脑
	  // var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
	  if(Folder != null){
	    Folder = Folder.items();  // 返回 FolderItems 对象
	    Folder = Folder.item();  // 返回 Folderitem 对象
	    Folder = Folder.Path;   // 返回路径
	    if(Folder.charAt(Folder.length-1) != "\\"){
	      Folder = Folder + "\\";
	    }
	    return Folder;
	  }
	 }catch(e){ 
	  alert(e.message);
	 }
	}

function toExcel(XLSname)
{
	try	
	{
		var arr =$('#dataview').datagrid('getSelections');
		if(arr.length<=0){
			$.messager.alert("提示消息", "请选择要导出的数据!", "info");
			return;
		}
		
		 //先声明Excel插件、Excel工作簿等对像
		 var jXls;
		 
		 try {
		  //插件初始化失败时作出提示
		  jXls = new ActiveXObject('Excel.Application');
		 }catch (e) {
		  alert("无法启动Excel!\n\n如果您确信您的电脑中已经安装了Excel，"+"那么请调整IE的安全级别。\n\n具体操作：\n\n"+"工具 → Internet选项 → 安全 → 自定义级别 → 对没有标记为安全的ActiveX进行初始化和脚本运行 → 启用");
		  return false;
		 }
		var filename= BrowseFolder();
		if(null==filename || ""==filename)
			return ;
		 var jdata= JSON.stringify(arr);	
		 //不显示警告 
		 jXls.DisplayAlerts = false;
            //创建AX对象excel  
            var oWB = jXls.Workbooks.Add(); 
            //获取workbook对象  
            var oSheet = oWB.ActiveSheet; 
		   for (i = 0; i <= arr.length; i++) 
		     { 
		         //取得每行的列数
		        	 
		        	 if(i==0){
						 oSheet.Cells(i + 1, 1).value = "CREATE_DATE";
			             oSheet.Cells(i + 1, 2).value = "MSISDN";
			             oSheet.Cells(i + 1, 3).value = "REALEAT_TEL";
			             oSheet.Cells(i + 1, 4).value = "RELEAT_NAME";
			             oSheet.Cells(i + 1, 5).value = "RELEAT_ADDR";
			             oSheet.Cells(i + 1, 6).value = "PEOPLEID";
			             oSheet.Cells(i + 1, 7).value = "PAGE";
//			             oSheet.Cells(i + 1, 8).value = "VERIFIER";
//			             oSheet.Cells(i + 1, 9).value = "VERIFIER_DATE";
//			             oSheet.Cells(i + 1, 10).value = "OPENCARDER";
//			             oSheet.Cells(i + 1, 11).value = "OPEN_DATE";
//			             oSheet.Cells(i + 1, 12).value = "SENDCARDER";
//			             oSheet.Cells(i + 1, 13).value = "SENDDATE";
//			             oSheet.Cells(i + 1, 14).value = "STAT";
//			             oSheet.Cells(i + 1, 15).value = "CARDID";
//			             oSheet.Cells(i + 1, 16).value = "ICODE";
//			             oSheet.Cells(i + 1, 17).value = "ROW_NUMBER";
//			             oSheet.Cells(i + 1, 18).value = "TEL";
		        	 }
		        	 else
	        		 {
		        		 var k = i-1;
			             oSheet.Cells(i + 1, 1).value = arr[k]["CREATE_DATE"];
			             oSheet.Cells(i + 1, 2).value = arr[k]["MSISDN"];
			             oSheet.Cells(i + 1, 3).value = arr[k]["REALEAT_TEL"];
			             oSheet.Cells(i + 1, 4).value = arr[k]["RELEAT_NAME"];
			             oSheet.Cells(i + 1, 5).value = arr[k]["RELEAT_ADDR"];
			             oSheet.Cells(i + 1, 6).value = arr[k]["PEOPLEID"];
			             oSheet.Cells(i + 1, 7).value = arr[k]["PAGE"];
//			             oSheet.Cells(i + 1, 8).value = arr[k]["VERIFIER"];
//			             oSheet.Cells(i + 1, 9).value = arr[k]["VERIFIER_DATE"];
//			             oSheet.Cells(i + 1, 10).value = arr[k]["OPENCARDER"];
//			             oSheet.Cells(i + 1, 11).value = arr[k]["OPEN_DATE"];
//			             oSheet.Cells(i + 1, 12).value = arr[k]["SENDCARDER"];
//			             oSheet.Cells(i + 1, 13).value = arr[k]["SENDDATE"];
//			             oSheet.Cells(i + 1, 14).value = arr[k]["STAT"];
//			             oSheet.Cells(i + 1, 16).value = arr[k]["CARDID"];
//			             oSheet.Cells(i + 1, 16).value = arr[k]["ICODE"];
//			             oSheet.Cells(i + 1, 17).value = arr[k]["ROW_NUMBER"];
//			             oSheet.Cells(i + 1, 18).value = arr[k]["TEL"];
	        		 }
		     } 
		   var myDate = new Date();
		   myDate.toLocaleDateString(); 
		   oWB.SaveAs(filename+""+XLSname+"-"+ myDate.toLocaleDateString()+".XLS");
		   jXls.Visible = true;	
		   return true;
	}catch(e)
	{
		alert(e.message);
	}
}



