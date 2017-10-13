//加载一级联动下拉框		
$(function(){
			$("#getreader").click(function(){
				$.get("http://localhost:8080/dataxtool001/ControlServlet",function(data){
					alert(data);
					$("#dd").text(data);
						
					
				});
				
			});

		});
//加载二级联动下拉框
$(function(){
			$("#readertype").combobox({
				url:'tt.json',
				onSelect:function(){	//当下拉框被选中
					var select=$("#readertype").combobox('getText');
					var url=select+'parameter.json';
					$("#readerparameter").combobox('reload',url);
					
				}
			});
				
			
	});

