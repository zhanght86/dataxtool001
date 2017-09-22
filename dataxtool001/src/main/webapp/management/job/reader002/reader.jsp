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
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/default\easyui.css">
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/icon.css">


	
	<script type="text/javascript" src="/dataxtool001/management/job/reader002/reader.js"></script>
</head>
	<body>
		<!-- 表格 -->
		<table id="dg"></table>
		<!-- 弹出窗口 -->
		<div id="win" class="easyui-window" title="My Window" style="width:600px;height:400px"   
	        data-options="iconCls:'icon-save',modal:true,closed:true">   
			
			文件名<input id="filename" class="easyui-validatebox" data-options="required:true" />
					   		    <!-- 提交整个属性表单 -->
		    <a id="committable" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">保存reader</a>
			<br>
			
			<!-- 创建下拉框和按钮 -->
			<input id="cc" name="dept" value="aa">
					    <!-- 输入框 -->
		   <input id="shurukuang" class="easyui-validatebox" data-options="required:true,validType:'email'" />

			<!-- 创建按钮 -->
			<a id="addbutton" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">添加属性</a>

		    <table id="pg" style="width:300px" align="center"></table> 

		</div> 
	</body> 
</html>