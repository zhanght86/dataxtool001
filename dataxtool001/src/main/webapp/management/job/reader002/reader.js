var rows=[];

//展示一个表格
function showform(){
	$('#win').window('open');  // open a window 
}

//通过文件名到后台查找详细的文件信息
function findReaderByFilename(filename) {
	//弹出窗口
	showform();
	//异步加载数据
	var arg="filename="+filename;
    $.ajax({
        type: "POST",//请求类型
        url: "http://localhost:8080/dataxtool001/datax/job/reader/findreaderbyfilename.do",
        data: arg,
        success: function (data) {
        	var datajson=JSON.parse(data);
        	var rows=datajson.rows;
        	var filename=datajson.filename;
        	//解析数据
        	$('#pg').propertygrid('loadData', rows);
        	$("#filename").val(filename);
        
        	
        },
        error: function () {
        	alert("执行失败");
        	alert("error");
        }
    });
	
}



$(function(){
	//初始化弹出窗口里面的下拉框
	$('#cc').combobox({    
	    data:[
	    	{    
	    	    "id":1,    
	    	    "text":"reader",
	    	    "selected":true  
	    	   
	    	},{    
	    	    "id":2,    
	    	    "text":"parameter"   
	    	},{    
	    	    "id":3,    
	    	    "text":"column",    
	    	     
	    	}
	    ],  
	    valueField:'id',    
	    textField:'text'   
	}); 
	
	//为按钮绑定响应事件,根据下拉框的不同的值创建不同的行
    $('#addbutton').bind('click', function(){
		var row = {    
				  "name":"",    
				  "value":"",    
				  "group":"",    
				  "editor":'text'   
				}; 
		row.name=$("#shurukuang").val();
		row.group=$('#cc').combobox('getText');
		if(row.group=="column"){
			row.name="type/value";
			row.value=$("#shurukuang").val();
		}else if(row.group=="filename"){
			row.name="filename";
			row.group="filename";
			row.value=$("#shurukuang").val();
		}
    	rows.push(row);
    	$('#pg').propertygrid('loadData', rows);
       
    });  
    
	//初始化表格
	$('#dg').datagrid({ 
		//加载数据
		//url:"datagrid.json",
		url:"/dataxtool001//datax/job/reader/findallreader.do",
		//定义列
	    columns:[
	    	[   
	    	{field:'checkbox',title:'checkbox',width:100,checkbox:true},
	    	{field:'filename',title:'filename',width:100}, 
	    	{field:'name',title:'Name',width:100},    
	        {field:'type',title:'type',width:100,align:'right'}    
	        ]
	    	],
	    pagination:true,//分页
	    rownumbers:true,//行号
	    //工具条
		toolbar: [{
			iconCls: 'icon-edit',
			handler: function(){
				//只会编辑第一个选中的按钮
				//返回第一个被选中的行
				var rowselect=$('#dg').datagrid("getSelected");
				var filename=rowselect.filename;
				findReaderByFilename(filename);
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
			handler: function(){alert('删除按钮')}
		}
		]
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
    	//获得所有行的数据
    	var rows = $("#pg").datagrid("getRows");//json对象
        var filename=$("#filename").val();
    	//提交
    	var json={
    			"filename":filename,
    			"rows":rows
    	}
    	
    	var arg="reader="+JSON.stringify(json);
        $.ajax({
            type: "POST",//请求类型
            url: "http://localhost:8080/dataxtool001/datax/job/reader/addstreamreader.do",
            data: arg,
            success: function (data) {
            	alert("success");
            	alert(data);
            },
            error: function () {
            	alert("执行失败");
            	alert("error");
            }
        });
    });    
	
});


