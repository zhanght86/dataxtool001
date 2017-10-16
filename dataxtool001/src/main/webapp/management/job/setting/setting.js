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
        	alert("success");
        	alert(data);
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
function addreader(){

	//获得所有行的数据
	var rows = $("#pg").datagrid("getRows");//json对象
	var type=$('#cc').combo('getText');
	var filename=$("#filename").val();
	//提交
	var json={};
	json.type=type;
	json.rows=rows;
	json.filename=filename;
	var arg="reader="+JSON.stringify(json);
	url= "http://localhost:8080/dataxtool001/datax/job/setting/addsetting.do";
	aj("POST",url,arg);
	closeform();
	$('#dg').datagrid('reload'); 
	
}

//根据文件名删除指定的文件，使用异步提交
function deletefilebyid(id){
	var arg="id="+id;
	url= "http://localhost:8080/dataxtool001/datax/job/setting/deletesetting.do";
	aj("post", url, arg);
	$('#dg').datagrid('reload');    
}

//通过文件名到	查找详细的文件信息
function findReaderByFilename() {
	//只会编辑第一个选中的按钮
	//返回第一个被选中的行
	var rowselect=$('#dg').datagrid("getSelected");
	var filename=rowselect.filename;
	//弹出窗口
	var url="http://localhost:8080/dataxtool001/datax/job/setting/findsetting.do";
	//异步加载数据
	var arg="filename="+filename;
	url=url+"?"+arg;
	//加载数据
	$('#pg').propertygrid({    
	    url: url,    
	    showGroup: true,    
	    scrollbarSize: 0    
	});
	$("#filename").val(filename);
	showform();

	
}

//初始化列表框
function initdatagrid(){
	//初始化表格
	$('#dg').datagrid({ 
		url:"/dataxtool001/datax/job/setting/findallsetting.do",
	    columns:[	//定义列
	    	[   
	    	{field:'checkbox',title:'checkbox',width:100,checkbox:true},
	    	{field:'id',title:'id',width:100},
	    	{field:'filename',title:'filename',width:100}, 
	    	{field:'type',title:'type',width:100}, 
	        ]
	    	],
	    pagination:true,//分页
	    rownumbers:true,//行号
	    //工具条
		toolbar: [{
			iconCls: 'icon-edit',
			handler: function(){	//编辑按钮

				findReaderByFilename();
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
	
	//为按钮绑定响应事件,根据下拉框的不同的值创建不同的行
    $('#addbutton').bind('click', function(){
    	var rows = $("#pg").datagrid("getRows");//json对象
		var row = {    
				  "name":"",    
				  "value":"",    
				  "group":"",    
				  "editor":'text'   
				}; 
		
		row.name=$("#shurukuang").val();
		row.value=$("#shurukuang2").val();
    	rows.push(row);
    	$('#pg').propertygrid('loadData', rows);
    	
       
    });  
    
	//设置属性表格，数据表格会分为三个组，column,parameter,reader
	$("#pg").propertygrid({ 
		fitColumns:false,
	    showGroup: true,  //显示属性分组  
	    scrollbarSize: 0,
        columns: [[
            { field: 'name', title: 'Name', width: 100, resizable: false},
            { field: 'value', title: 'Value', width: 100, resizable: false }
        ]],
     
	});
    
	
	//提交添加的数据
    $('#committable').bind('click', function(){ 
    	addreader();
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
