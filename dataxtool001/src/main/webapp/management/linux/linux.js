

$(function(){
	//设置搜索框
	$('#ss').searchbox({ 
		menu:'#mm', 
		prompt:'Please Input Value' ,
		searcher:function(value,name){
			alert(value+"  "+name);
			var arg={
					"name":name,
					"value":value
					};
			//ajax搜索数据
	        $.ajax({
	            type: "POST",//请求类型
	            contentType: "application/json",
	            url:"http://localhost:8080/dataxtool001/datax/linux/findalllinux.do",
	            data: JSON.stringify(arg),
	            success: function (data) {
	     
	            	//var datajson=JSON.stringify(data);
	            	var jsondata=JSON.parse(data);
	            	//更新表格的数据
	            	$('#dg').datagrid({
	            		data:jsondata
	            	});    
	            },
	            error: function () {
	            	alert("执行失败");
	            	alert("error");
	            }
	        });
		}
	}); 
	
	//设置按钮 ,并未按钮添加点击事件
	$('#btn').linkbutton({    
	    iconCls: 'icon-search'   
	});
	//绑定点击事件，弹出一个窗口用来连接linux
    $('#btn').bind('click', function(){    
    	$('#win').window('open');  // open a window 
    }); 
    
    //设置表单成ajax提交
	$("#btn1").click(function(){
		var ip=$("#ip").val();
		var username=$("#username").val();
		var password=$("#password").val();
		var jsonarg={
			"ip":ip,
			"username":username,
			"password":password
		};
		//根据不同的请求op请求不同的url
		var urlarg="http://localhost:8080/dataxtool001/datax/linux/connection.do";
        $.ajax({
            type: "POST",//请求类型
            //url: "http://localhost:8080/dataxtool001/datax/managementdatax.do",
            url:urlarg,
            contentType: "application/json",
            data: JSON.stringify(jsonarg),
            success: function (data) {
            	//执行成功之后会有两种类型的json数据，一种是表格形式的数据，另外一种是原生的json数据
            	//分别显示在不同的组件
            	//一个是table，一个是json
            	alert("添加成功");
            	$('#win').window('close');
            },
            error: function () {
            	alert("执行失败");
            	alert("error");
            }
        });
	});  
	
	
	//datagrid初始化 

	$('#dg').datagrid({    
	       
	    columns:[[    
	        {field:'ip',title:'ip',width:100},    
	        {field:'username',title:'username',width:100},    
	        {field:'password',title:'password',width:100,align:'right'}    
	    ]]    
	}); 
	
	
});