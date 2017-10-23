//ajax提交
function aj(type,url,arg,callsuccess,callerror){
    $.ajax({
        type: type,//请求类型
        url: url,
        data: arg,
        success: function (data) {
        	alert("success");
        	alert(data);
        },
        error: function () {
        	alert("error");
        }
    });
}

//展示一个表格
function showform(){
	//每次打开属性表单的时候都需要清空
	$('#pg').datagrid('loadData',{total:0,rows:[]});
	$('#win').window('open');  // open a window 
}

//关闭弹出窗口
function closeform(){
	$('#win').window('close');  // open a window 
}
//添加一个reader
function addlinux(){
	//获得所有行的数据
	var rows = $("#pg").datagrid("getRows");//json对象
    var filename=$("#filename").val();
	//提交
	var json={
			"filename":filename,
			"rows":rows
	}	
	var arg="reader="+JSON.stringify(json);
	url= "http://localhost:8080/dataxtool001/datax/job/reader/addreader.do";
	aj("POST",url,arg);
	alert("添加成功");
	$('#win').window('close');
	$('#dg').datagrid('reload');    

}

//根据文件名删除指定的文件，使用异步提交
function deletefilebyfilename(id){
	var arg="id="+id;
	url= "http://localhost:8080/dataxtool001/datax/job/reader/deletefilebyid.do";
	aj("post", url, arg);
	$('#dg').datagrid('reload');    
}

//通过文件名到	查找详细的文件信息
function findReaderById() {
	//只会编辑第一个选中的按钮
	//返回第一个被选中的行
	var rowselect=$('#dg').datagrid("getSelected");
	var id=rowselect.id;
	//弹出窗口
	var url="http://localhost:8080/dataxtool001/datax/job/reader/findreaderbyid.do";
	//异步加载数据
	var arg="id="+id;
	url=url+"?"+arg;
	//加载数据
	$('#pg').propertygrid({    
	    url: url,    
	    showGroup: true,    
	    scrollbarSize: 0    
	});
	var filename=rowselect.filename;
	$("#filename").val(filename);
	showform();

	
}

//初始化列表框
function initdatagrid(){
	//初始化表格
	$('#dg').datagrid({ 
		url:"/dataxtool001/datax/linux/findalllinux.do",
	    columns:[	//定义列
	    	[   
	    	{field:'checkbox',title:'checkbox',width:100,checkbox:true},
	    	{field:'hostname',title:'hostname',width:100},  
	    	{field:'username',title:'username',width:100},
	    	{field:'password',title:'password',width:100}, 
	        ]
	    	],
	    pagination:true,//分页
	    rownumbers:true,//行号
	    //工具条
		toolbar: [{
			iconCls: 'icon-edit',
			handler: function(){	//编辑按钮

				findReaderById();
			}
		},'-',{
			iconCls: 'icon-help',
			handler: function(){alert('帮助按钮')}
		},'-',{
			iconCls:'icon-add',
			handler: function(){
				
				//弹出一个表单
				showform();
			}
			
		},'-',{
			iconCls:'icon-remove',
			handler: function(){	
				//删除按钮，删除一个或者多个按钮
				//返回所有的行，如果没有选中则返回一个空的数组
				var rowselects=$('#dg').datagrid("getSelections");
				if(rowselects.length==0){
					alert("没有指定要删除的行，请选择");
				}else{
					for(var i=0;i<rowselects.length;i++){
						alert("删除");
						var id=rowselects[i].id;
						//根据文件名删除指定的文件，然后异步提交
						deletefilebyfilename(id);
					}
					
				}
			}
		}
		]
	}); 
}

var rows=[];
//初始化弹出窗口
function initwin(){
	$('#win').window('close');  
	//使得表单成为ajax提交
	$('#ff').form({    
	    url:"/dataxtool001/datax/linux/addlinux.do",    
	    onSubmit: function(){    
	    	
	    	
	    },    
	    success:function(data){    
	           
	    }    
	}); 
	//初始化窗口里面的提交按钮
	$(function(){    
	    $('#btn').bind('click', function(){    
	        //ajax提交表单
	    	$('#ff').submit(); 
	    	$('#win').window('close');  // open a window 
	    	$('#dg').datagrid('reload');
	    });    
	}); 
	


	
}
//为各种组件设置初始值
function init(){
	//初始化列表
	initdatagrid();
	initwin();
}

//页面加载完毕之后进行js的初始化
$(function(){
	init();//组件的初始化
});
