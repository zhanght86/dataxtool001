<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录界面</title>
	<!-- 导入jquery的js -->
	<script type="text/javascript" src="/dataxtool001/jquery/jquery-1.4.2.min.js"></script>
	<!-- 导入sasyui -->
	<script type="text/javascript" src="/dataxtool001/easyui/jquery.easyui.min.js"></script>
	<!-- 导入easyui的主样式文件样式 -->
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/default\easyui.css">
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/icon.css">
</head>
	<body style="height:100%;width:100%;overflow:hidden;border:none;visibility:visible;">
		<!-- 主窗口，表示整个登录框,用来easyui的窗口插件 -->
		<div id="mainwindow" class="easyui-window" 
			style="width:500px;height:300px;background:#fafafa;overflow:hidden"
			title="登录" border="false" resizable="false" draggable="false" 
			minimizable="false" maximizable="false">
			
			<!-- 主窗口的里面放置的div -->
			<div class="header" style="height:35px;">
		 	   <div class="toptitle" style="margin-top: 25px; font-size:20px; margin-left:60px;">
			   <%-- <%=PublicinformationUtil.GetProperties("projectname")%> --%>
			   </div>
			</div>
		
			<div style="padding:60px 0;">
			<div  id="loginForm">
			<div style="padding-left:150px">
		<!-- 登录的表格 -->
		<table cellpadding="0" cellspacing="3">
			<tr>
				<td>登录帐号</td>
					<td><input id="username"   style="width:114px;"></input>
				</td>
			</tr>
			
			<tr>
			  <td>登录密码</td>
			  	<td><input id="password" type="password"   style="width:114px;"></input>
			  </td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
			<tr>
				<td></td>
				<td>
					 <a id="btnLogin"  class="easyui-linkbutton"  >登 录</a>
		       		 <a class="easyui-linkbutton"  onclick="clearAll()">重 置</a>
				</td>
			</tr>
		</table>
			</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">	
		function clearAll(){
			document.getElementById('username').value="";
			document.getElementById('password').value="";
		}
		
		$("#PASSWORD").keydown(function(event){
			if(event.keyCode==13)
				$("#btnLogin").click();
		});
	
		//登录按钮的点击事件绑定
		$("#btnLogin").click(function(){
			//获得登录名和密码
			var username = $("#username").val();
			var password = $("#password").val();

			//封装数据
			var condition = {"username":username,"password":password};		
			alert(condition);
			condition1 = JSON.stringify(condition); 
		    //condition = escape(encodeURIComponent(condition));			
			var url1="http://localhost:8080/dataxtool001/user.do";
				//ajax提交
		    	$.ajax( {
		    	type : "post",
				url : url1,
				//dataType: 'json',
				dataType:"json",//指定数据的格式，给ajax使用的
				data:condition1,
				contentType : "application/json",  //指定的请求的信息的形式
				error : function(event,request, settings) {
					$.messager.alert("提示消息", "请求失败!", "info");
				},
				success : function(data) { 
					alert("success");
					if(data.total>0){
						window.location.href="main.jsp";   
					}
					else{
						$.messager.alert("提示消息", "用户名或密码错误!", "info");
					}	
				}
			});	
		});
	</script>
	</body>
</html>