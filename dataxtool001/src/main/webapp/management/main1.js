//改文件主要用来处理main.jsp
//当页面加载完毕之后进行处理
$(function(){
	//对菜单的处理
	$('#tt1').tree({  
	    url:"menu.json",
	});
	//绑定点击事件
	$('#tt1').tree({
		//每次点击都会创建一个新的面板
		onClick: function(node){
			//新建一个iframe,这是一个jquery对象
			var newIframe=$("<iframe width='100%' height='100%'  id='iframe' name='iframe' frameborder='0' scrolling='auto' ></iframe>");
			var newSrc="";
			if(node.text=="reader管理"){
				newSrc="/dataxtool001/management/job/reader/reader.jsp";
			}else if(node.text=="linux管理"){
				newSrc="/dataxtool001/management/linux/linux.jsp";
			}else if(node.text=="task管理"){
				newSrc="/dataxtool001/management/task/task.jsp";
			}else if(node.text=="reader002管理"){
				newSrc="/dataxtool001/management/job/reader002/reader.jsp";
			}
			//为新面板设置元素
			newIframe.attr({
				src:newSrc
			});
			//alert(newIframe.attr("src"));
			//根据不同的节点text打开不同的页面
			var titlepanel=node.text;
			//先创建一个新的面板
			$('#tt').tabs('add',{    
			    title:titlepanel,    
			    content:newIframe, //添加的新的面板   
			    closable:true,    
			    tools:[{    
			        iconCls:'icon-mini-refresh',    
			        handler:function(){    
			            alert('refresh');    
			        }    
			    }]    
			});
			
			//添加iframe到选项卡中
			//$("#tt").append(newIframe);
			//alert(node.text);  // 在用户点击的时候提示
		}
	});


});