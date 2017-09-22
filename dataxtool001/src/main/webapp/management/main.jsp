<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- 项目名字 -->
 	<title>datax管理系统</title>
	<!-- jquery以及easyui -->
	<script type="text/javascript" src="/dataxtool001/jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/dataxtool001/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/default\easyui.css">
	<link rel="stylesheet" href="/dataxtool001/easyui/themes/icon.css">
	
	<script type="text/javascript" src="common.js"></script>	
	<script type="text/javascript" src="main1.js"></script>
</head>
<body style="border:none;visibility:visible;width: 100%;height: 100%;" onload="showTime();">
	<!-- 创建布局 -->
	<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
		<!-- 页面顶部top及菜单栏 -->  
	    <div region="north" style="height:96px;width: 100%;">
	    	<div class="header" style="background:#fff url('css/images/banner.jpg');">
				<div style="text-align: right; padding-right: 20px; padding-top: 30px; padding-bottom: 14px;">
 					<span style="color:#FDFDFD" id="loginuserInfo">欢迎你，wang</span>
					<span style="color:#FDFDFD" id="loginuserArea">,大数据产品部</span> 
					<span style="color:#FDFDFD" id="timeInfo"></span>
					<a href="login.jsp" style="color:#FDFDFD;text-decoration:none;">退出</a>
				</div>
				<div class="maintitle"  style="top: 12;font-size:20px;color:#FDFDFD; margin-left:5px;">Datax管理管理系统</div>
			</div>
	    	<div id="topmenu" class="topmenu" style="height:34px;background:url('css/images/maintop.png');color:#fff;font-size:14px;font-weight:bold;">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:addTab('首页','welcome.htm')" >首&nbsp;&nbsp;页</a>
	    	</div>  
	    </div>
	    
	    <!-- 页面底部信息 -->
	    <div region="south" style="height: 18px;" >
	    	<center>
	    		已经到底了！！！！！！！！！
	    	</center>
	    </div>  
		<!-- 左侧导航菜单 -->	    
	    <div region="west"  title="导航菜单栏" style="width:180px;">
			<ul id="tt1" class="easyui-tree" data-options="animate:true,dnd:true"></ul>
	    </div>  
	    
	    <!-- 主显示区域选项卡界面 -->
	    <div region="center">
	
			<div id="tt" class="easyui-tabs" style="width:500px;height:250px;" data-options="fit:true"> 
<!-- 			  
			    <div title="reader" data-options="closable:true" >   
			        <iframe width='100%' height='100%'  id='iframe' name='iframe' frameborder='0' scrolling='auto'  src='./job/reader/reader.jsp'></iframe> 
			    </div>  -->
			    
 			   	<div title="首页" data-options="closable:true" >
	    			<iframe width='100%' height='100%'  id='iframe' name='iframe' frameborder='0' scrolling='auto'  src='welcome.jsp'></iframe>
	    		</div>  

			</div>  
	    </div>
	</div>
</body>
</html>