<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	
	<!-- 导入jquery的js -->
	<script type="text/javascript" src="/dataxtool001/jquery/jquery-1.4.2.min.js"></script>
	<!-- 导入sasyui -->
	<script type="text/javascript" src="/dataxtool001/easyui/jquery.easyui.min.js"></script>
	<!-- 导入easyui的主样式文件样式 -->
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/default/easyui.css">
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/icon.css">
	<script type="text/javascript" src="/dataxtool001/management/job/json/json.js"></script>
</head>
	<body>
		<select id="zjop" class="easyui-combobox" name="dept" style="width:200px;">   
		    <option value="jsonobj">jsonobj</option>   
		    <option value="jsonarray">jsonarray</option>    
		</select>
		<input id="newfilename" /> 
		<a id="zj" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">组装选择的内容</a> 
		<!-- 表格 -->
		<table id="dg"></table>
		<!-- 弹出窗口 -->
		<div id="win" class="easyui-window" title="My Window" style="width:600px;height:400px"   
	        data-options="iconCls:'icon-save',modal:true,closed:true">   
	        <input id="filename" class="easyui-validatebox" data-options="required:true" /> 
			<!-- 提交整个属性表单 -->
		    <a id="committable" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">保存reader</a>
			<br>
			<input id="data" class="easyui-textbox" data-options="multiline:true" style="width:300px;height:100px">
			
		</div> 
	</body> 
</html>