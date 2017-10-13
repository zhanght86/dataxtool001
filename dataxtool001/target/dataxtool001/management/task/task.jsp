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
	 
	 <script type="text/javascript" src="/dataxtool001/management/task/task.js"></script>
</head>
	<body>   
		<!-- job下拉框 -->
		<input id="job" class="easyui-combobox" name="job"   
	    data-options="valueField:'id',textField:'text',url:'http://localhost:8080/dataxtool001/datax/task/loadjob.do'" /> 
		<!-- linux下拉框 -->
		<input id="linux" class="easyui-combobox" name="linux"   
   		 data-options="valueField:'id',textField:'text',url:'http://localhost:8080/dataxtool001/datax/task/loadlinux.do'" />
   		 <a id="btn" class="easyui-linkbutton" data-options="iconCls:'icon-search'">执行</a>  
	</body> 
</html>