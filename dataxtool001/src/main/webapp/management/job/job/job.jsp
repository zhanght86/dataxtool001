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
	<script type="text/javascript" src="/dataxtool001/management/job/job/job.js"></script>
</head>
	<body>
		job页面
			<input id="reader" class="easyui-combobox" name="dept"   
	    data-options="valueField:'id',textField:'text',url:'/dataxtool001/datax/job/job/loadcc1.do'" />
					<input id="writer" class="easyui-combobox" name="dept"   
	    data-options="valueField:'id',textField:'text',url:'/dataxtool001/datax/job/job/loadcc2.do'" />
	    					<input id="setting" class="easyui-combobox" name="dept"   
	    data-options="valueField:'id',textField:'text',url:'/dataxtool001/datax/job/job/loadcc3.do'" />
	    
	    <input id="filename" class="easyui-validatebox" data-options="required:true" /> 
	    <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">生成job</a>
	    
	    <table id="dg"></table> 
	</body> 
</html>