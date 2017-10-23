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
	<script type="text/javascript" src="/dataxtool001/management/linux/linux.js"></script>
</head>
	<body>
		<!-- 表格 -->
		<table id="dg"></table>
		<!-- 弹出窗口 -->
		<div id="win" class="easyui-window" title="My Window" style="width:600px;height:400px"  > 
			<form id="ff" method="post">   
			    <div>   
			        <label for="hostname">hostname:</label>   
			        <input class="easyui-validatebox" type="text" name="hostname" data-options="required:true" />   
			    </div>   
			    <div>   
			        <label for="username">username:</label>   
			        <input class="easyui-validatebox" type="text" name="username" data-options="validType:'email'" />   
			    </div>   
			    <div>   
			        <label for="password">password:</label>   
			        <input  class="easyui-validatebox" type="text" name="password" data-options="validType:'email'" />   
			    </div>  
			</form>  
			<!-- 提交按钮 -->
			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">easyui</a> 
		</div> 
	</body> 
</html>