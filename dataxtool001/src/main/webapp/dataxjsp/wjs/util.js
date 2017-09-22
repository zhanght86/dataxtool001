//用来帮助处理js
/**
 *将key-value形式的数据转化为指定形式的数据
 *例如 {name:wang} ---->   {key:name,value:wang} 
 *由json格式的{arg1，arg2}---》 [{name1,arg1},{name2,arg2}]
 * json为传入的json数据
 * name1数据的第一个名字
 * name2数据的第二个名字
 */
function ts(json,name1,name2){
    var arr=[];
    //显示表格内的数据
    for(var j in json){//依次遍历取出值
    	var value=json[j];
    	//将值进行封装
    	var row={name1:j,name2:value};
    	//var rowjson=JSON.parse(row);
    	arr.push(row);

    }
    return arr;
}