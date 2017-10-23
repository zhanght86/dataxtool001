//清空各种表格内容
function flush(){
	//每次打开属性表单的时候都需要清空
	$('#pg').datagrid('loadData',{total:0,rows:[]});
	$('#dg').datagrid('loadData',{total:0,rows:[]});
}

//ajax提交
function aj(type,url,arg,callsuccess,callerror){
    $.ajax({
        type: type,//请求类型
        url: url,
        data: arg,
        success: function (data) {

        },
        error: function () {
        	alert("error");
        }
    });
}
//为组装按钮设置值
function initbtn(){
	//为按钮添加店家时间
    $('#zj').bind('click', function(){       
        //获得组装的类型
        var zjop=$("#zjop").combobox('getText');
        //得到所有的列的数据
       // var rowselect=$('#dg').datagrid("getSelected");
        var rows = $("#dg").datagrid("getSelections");//json对象
        var newfilename=$("#newfilename").val();
    	//提交
    	var json={};
    	json.type=zjop;
    	json.rows=rows;
    	json.newfilename=newfilename;
    	var arg="arg="+JSON.stringify(json);
    	url= "http://localhost:8080/dataxtool001/datax/job/setting/connect.do";
    	aj("POST",url,arg);
    	$('#dg').datagrid('reload');  
    }); 
}

//展示一个表格
function showform(){
	//每次打开属性表单的时候都需要清空
	$('#win').window('open');  // open a window 
}

//关闭弹出窗口
function closeform(){
	$('#win').window('close');  // open a window 
}
//添加一个reader
function addjson(){

	var filename=$("#filename").val();
	var d=$("#data").val();
	//提交
	var json={};
	json.filename=filename;
	json.data=d;
	var arg="json="+JSON.stringify(json);
	url= "http://localhost:8080/dataxtool001/datax/job/json/addjson.do";
	aj("POST",url,arg);
	closeform();
	$('#dg').datagrid('reload'); 
	
}

//根据文件名删除指定的文件，使用异步提交
function deletefilebyid(id){
	var arg="id="+id;
	url= "http://localhost:8080/dataxtool001/datax/job/json/deletejson.do";
	aj("post", url, arg);
	$('#dg').datagrid('reload');    
}

//通过文件名到	查找详细的文件信息
function findJsonById() {
	//只会编辑第一个选中的按钮
	//返回第一个被选中的行
	var rowselect=$('#dg').datagrid("getSelected");
	var arg={};
	arg.id=rowselect.id;
	//弹出窗口
	var url="http://localhost:8080/dataxtool001/datax/job/json/findjson.do";
    $.ajax({
        type: "post",//请求类型
        url: url,
        contentType: "application/json; charset=utf-8", //json格式的数据
        data: JSON.stringify(arg),//如果没有参数，也要写成"{}",否则不能附加在request中
        success: function (data) {
        	//显示执行结果
   
        	$("#p").text(data);
  
        },
        error: function (data) {   	
        	alert("执行失败");

        }
    });
	//showform();

	
}

//初始化列表框
function initdatagrid(){
	//初始化表格
	$('#dg').datagrid({ 
		url:"/dataxtool001/datax/job/json/findalljson.do",
	    columns:[	//定义列
	    	[   
	    	{field:'checkbox',title:'checkbox',width:100,checkbox:true},
	    	{field:'filename',title:'filename',width:100},
	    	{field:'type',title:'type',width:100},
	    	{field:'id',title:'id',width:100}, 
	        ]
	    	],
	    pagination:true,//分页
	    rownumbers:true,//行号
	    //工具条
		toolbar: [{
			iconCls: 'icon-edit',
			handler: function(){	//编辑按钮
				findJsonById();
		
			}
		},'-',{
			iconCls: 'icon-help',
			handler: function(){alert('帮助按钮')}
		},'-',{
			iconCls:'icon-add',
			handler: function(){
				//刷新选项卡
				rows=[];
				$('#pg').propertygrid('loadData', rows);
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
						deletefilebyid(id);
						$('#dg').datagrid('reload'); 
					}
					
				}
			}
		}
		]
	}); 
}
//初始化弹出窗口
function initwin(){
	//初始化弹出窗口里面的下拉框
	$('#cc').combobox({  
	    data:[{
    	    "id":1,    
    	    "text":"json",
    	    "selected":true  
    	   
    	},{    
    	    "id":2,    
    	    "text":"array"   
    	}
    ],  
	    valueField:'id',    
	    textField:'text'   
	}); 
	
    
	
	//提交添加的数据
    $('#committable').bind('click', function(){ 
    	addjson();
    });   
	
}
//为各种组件设置初始值
function init(){
	//初始化列表
	initdatagrid();
	initwin();
	initbtn();//组装的点击按钮
}
//页面加载完毕之后进行js的初始化
$(function(){
	init();//组件的初始化
});
