$(function(){    
    $('#btn').bind('click', function(){  
    	//封装参数
    	var job=$("#job").val();
    	var linux=$("#linux").val();
    	var arg={
    		job:job,
    		linux:linux
    	}
    	//执行
        $.ajax({
            type: "POST",//请求类型
            url: "http://localhost:8080/dataxtool001/datax/task/exe.do",
            contentType: "application/json",
            data: JSON.stringify(arg),
            success: function (data) {
            	//显示执行结果
            	alert(data);
            },
            error: function () {
            	alert("执行失败");
            	alert("error");
            }
        });
    });    
}); 