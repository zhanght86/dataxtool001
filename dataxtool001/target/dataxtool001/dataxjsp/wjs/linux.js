$(function(){//页面加载完毕之后进行设置事件
	//设置窗口
	$('#win').dialog({    
	    title: 'My Dialog',    
	    width: 400,    
	    height: 200,    
	    closed: true,    
	    cache: false,      
	    modal: true   
	});
	
 

	//设置按钮弹出消息框
	 $('#btn').bind('click', function(){   
		 $('#win').window('open');  // open a window 
	 });   
	 
/*	 $('#linuxform').form({    
		    url:"http://localhost:8080/dataxtool001/datax/linux/connection.do",    
		    onSubmit: function(){ 
		    	alert("提交");
		    },    
		    success:function(data){    
		        alert(data)    
		    }    
	});  */
	 //提交按钮的点击事件
	 $("#link").bind('click',function(){
		 var ip=$("#ip").val();
		 var username=$("#username").val();
		 var password=$("#password").val();
		 var json={
			"ip":ip,
			"username":username,
			"password":password
				 
		 };
		 //异步提交表单
		 $.ajax({
	         type: "POST",//请求类型
	         //url: "http://localhost:8080/dataxtool001/datax/managementdatax.do",
	         url:"http://localhost:8080/dataxtool001/datax/linux/connection.do",
	         contentType: "application/json",
	         data: JSON.stringify(json),
	         success: function (data) {
	         	//执行成功之后会有两种类型的json数据，一种是表格形式的数据，另外一种是原生的json数据
	         	//分别显示在不同的组件
	         	//一个是table，一个是json
	         	alert("success");
	         	alert(data);
	         	var linux=jQuery.parseJSON(data);
	         
	         	$("#status").text("用户名:"+linux.username);
	  
	         },
	         error: function () {
	         	alert("执行失败");
	         	alert("error");
	         }
		 });
	 });

	 
	// submit the form    
	//$('#linuxform').submit(); 
	 
	
});




