$(function(){    
    $('#btn').bind('click', function(){ 
    	$('#btn').linkbutton('disable');
    	//封装参数
    	var filename=$("#filename").combobox('getText');
    	var hostname=$("#hostname").combobox('getText');
    	var arg={};
    	arg.filename=filename;
    	arg.hostname=hostname;
    	//执行
        $.ajax({
            type: "post",//请求类型
            url: "http://localhost:8080/dataxtool001/datax/task/exe.do",
            contentType: "application/json; charset=utf-8", //json格式的数据
            data: JSON.stringify(arg),//如果没有参数，也要写成"{}",否则不能附加在request中
            success: function (data) {
            	//显示执行结果
            	alert(data);
            	$('#btn').linkbutton('enable');
            },
            error: function (data) {
            	
            	alert("执行失败");
            	alert(data);
            	$('#btn').linkbutton('enable');
            	alert("error");
            }
        });
    });    
}); 