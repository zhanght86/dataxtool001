/**
 * 
 * 该函数用来处理通过ajax执行之后的json的结果
 * 该json里面会包含两种属性，一种是table形式的数据，另外一种是json的原生数据
 * 这两种数据分别存放在两种组件中
 * @returns
 */
function processResult(data) {
	//当按钮提交成功，而且成功的响应，并且响应的结果是json
    //var data = eval('(' + data + ')');  // change the JSON string to javascript object    
   
   var table=data.table;
    var result1=JSON.stringify(data.result);
    $("#p").text(result1);
    var arr=[];
    //显示表格内的数据
    for(var d in table){
    	var value=table[d];
    	var row={"key":d,"value":value};
    	//var rowjson=JSON.parse(row);
    	arr.push(row);
    }
    //更新数据
 	$("#dg").datagrid({
		"data":arr
	});
}
//页面加载的时候为该页面的组件添加各种的属性和时间
$(function(){
			//设置点击按钮的属性
			$("#getreader").click(function(){
				$.get("http://localhost:8080/dataxtool001/ControlServlet",function(data){		
					$("#p").text(data);
				});
			});

			//一级框点击之后会导致二级框内容的改变
			//设置一级下拉框的各种属性
			$("#readertype").combobox({
		        valueField: 'text',    
		        textField: 'text', 
				url:'/dataxtool001/dataxjsp/job/reader/readertype.json',
				onSelect:function(){	//当下拉框被选中
					var select=$("#readertype").combobox('getText');
					var url="/dataxtool001/dataxjsp/job/reader/"+select+'parameter.json';
					$("#readerparameter").combobox('reload',url);
				}
			});
			
			//二级框的选择操作会出现不同的表单
			//设置二级下拉框的属性
			$("#readerparameter").combobox({
		        valueField: 'text',    
		        textField: 'text', 
				onSelect:function(){	//当下拉框被选中
					var select=$("#readerparameter").combobox('getText');
					
				}
			});
			
			//对操作下拉框绑定事件，该按钮用来改变隐藏的操作
			$("#op").combobox({
		        valueField: 'default',    
		        textField: 'default', 
				onSelect:function(){	//当下拉框被选中
					var select=$("#op").combobox('getText');
					
				}
			});
			
			//设置表单属性成为ajax提交
			$('#ff').form({      
			    success:function(data){//当请求之后调用，传入返回后的数据，以及包含成功代码的字符串
			    	//当按钮提交成功，而且成功的响应，并且响应的结果是json
			        //var data = eval('(' + data + ')');  // change the JSON string to javascript object    
			        var result = jQuery.parseJSON(data);
			        var table=result.table;
			        var result1=JSON.stringify(result.json);
			        $("#p").text(result1);
			        var arr=[];
			        //显示表格内的数据
			        for(var d in table){
			        	var value=table[d];
			        	var row={"key":d,"value":value};
			        	//var rowjson=JSON.parse(row);
			        	arr.push(row);
			        }
			        //更新数据
		         	$("#dg").datagrid({
						"data":arr
		        	});
			    }    
			}); 
			//设置提交按钮，为提交按钮绑定提交事件
			$("#commit").click(function(){
				var readertype=$("#readertype").combobox("getValue");
				var readerparameter=$("#readerparameter").combobox("getValue");
				var op=$("#op").combobox("getValue");
				var arg=$("#arg").attr("value");
				var jsonarg={
					"readertype":readertype,
					"readerparameter":readerparameter,
					"op":op,
					"arg":arg	
				};
				//根据不同的请求op请求不同的url
				var urlarg="http://localhost:8080/dataxtool001/datax/reader/"+op+".do";
		    	alert("提交成功"); 
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
		            	alert("success");
		            	alert(data);
		            	processResult(data);
		            },
		            error: function () {
		            	alert("执行失败");
		            	alert("error");
		            }
		        });
			});
			//设置表格的属性和各个行的属性
			$('#dg').datagrid({    
			    columns:[[    
			        {field:'key',title:'key',width:100},    
			        {field:'value',title:'value',width:100,align:'right'}    
			    ]]   
			}); 
});

