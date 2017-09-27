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
function bindbtn(){
	   $('#btn').bind('click', function(){    
	      var type="POST";
	      var url="/dataxtool001/datax/job/job/pg.do";
	      var reader=$("#reader").combobox('getText');
	      var writer=$("#writer").combobox('getText');
	      var setting=$("#setting").combobox('getText');
	      var arg={};
	      arg.reader=reader;
	      arg.writer=writer;
	      arg.setting=setting;
		   aj(type,url,arg);
	   });    
}
function initdg(){
	$('#dg').datagrid({    
	    url:'/dataxtool001/datax/job/job/findalljob.do',    
	    columns:[[    
	    	{field:'checkbox',title:'checkbox',width:100,checkbox:true},   
	        {field:'name',title:'Name',width:100},    
	         
	    ]],
	    pagination:true,//分页
	    rownumbers:true,//行号
		toolbar: [{
			iconCls: 'icon-edit',
			handler: function(){	//编辑按钮
			}
		},'-',{
			iconCls: 'icon-help',
			handler: function(){alert('帮助按钮')}
		},'-',{
			iconCls:'icon-add',
			handler: function(){
			}
		},'-',{
			iconCls:'icon-remove',
			handler: function(){	

		
			}
		}
		]
	}); 
}
function init(){
	bindbtn();
	initdg();
}
$(function(){
	init();
});