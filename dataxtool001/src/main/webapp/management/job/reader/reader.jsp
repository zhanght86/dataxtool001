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


	
	<script type="text/javascript" src="/dataxtool001/management/job/reader/reader.js"></script>
</head>
	<body>   
		<!-- 
			该表单是使用ajax方式来提交的
			里面包含了许多的隐藏栏目，根据一级和二级的下拉框来确定显示的内容
		 -->
		<form id="ff" method="post">   
			<!-- 二级联动 -->
			<span>reader的种类：</span>  
			<input class="easyui-combobox"  style="width:50" id="readertype" name="readertype"> 
			<span>reader的参数：</span>  
			<input class="easyui-combobox"  style="width:30;" id="readerparameter" name="readerparameter">
			<!-- 操作 -->
			<select id="op" class="easyui-combobox" name="op" style="width:200px;"> <br>  
			    <option value="add">add</option>   
			    <option value="delete">delete</option>   
			    <option value="update">update</option>   
			    <option value="select">select</option>
			    <option value="selectall">selectall</option>    
			</select>
		    <div>   
		        <label for="name">key:</label>   
		        <input id="arg"  class="easyui-validatebox" type="text" name="arg" data-options="required:true" />   
		    </div>    
		   
		     <input id="commit" type="button" value="提交">  
		    
		</form>
		<!-- 该表格用来将讲查询得到的数据格式化,通过js来设置相应的属性 -->
		<table id="dg"></table>


		<div id="p" title="My Panel"     
		        style="width:500px;height:150px;padding:10px;background:#fafafa;"   
		        data-options="iconCls:'icon-save',closable:true,    
		                collapsible:true,minimizable:true,maximizable:true,fit:true">   
		</div> 
    



   
</body> 
</html>