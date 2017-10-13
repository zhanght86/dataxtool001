//初始化一个reader对象
function getNewReader() {
	var reader={
		"name":"streamreader",
		"parameter":{
			"column":[
				
			]
		}
	}
	return reader;
}

//添加元素
//有的对象可能需要输入的是key value，如column
function add() {
	var name=document.getElementById("op");
	var value1=document.getElementById("value1");
	var value2=document.getElementById("value2");
	alert(value1.value);
	alert(value2.value);
	alert(op.value);
	postRequest()
}

//获得服务器的xmlhttp对象，用于与后台对象进行交互
function getXmlHttp() {
	var xmlhttp;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	
	return xmlhttp;
}

function postRequest() {
	var xmlhttp=getXmlHttp();
	xmlhttp.open("post","http://localhost:8080/dataxtool001/ControlServlet",true);
	xmlhttp.send();
	//获得回应
	var json=xmlhttp.responseText;
	document.getElementById("value1").innerHTML=json;
}