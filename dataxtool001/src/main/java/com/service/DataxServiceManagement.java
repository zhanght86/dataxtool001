package com.service;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dataxmanagement.DataxManagement;
import com.domain.op.DataxReaderOP;
import com.job.ReaderManagement;
import com.json.JsonManagement;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//业务层的类，由spring容器来管理
@Service
public class DataxServiceManagement {
	@Autowired
	private DataxManagement dataxManagement;
	/**
	 * 
	 * 接收从前台的数据，添加指定名字和指定值
	 * 	添加操作会有两种情况，一种是直接添加key-value在reader下面
		还有一种是添加column
		首先验证要执行的操作是否为add
	 * @param dataxReaderOP
	 */
	public Map processDataxReaderAdd(DataxReaderOP dataxReaderOP) {
		ReaderManagement readerManagement=new ReaderManagement();
		//生成一个默认的reader
		JSONObject result=readerManagement.generateDefaultReader();
		if("add".equals(dataxReaderOP.getOp())) {
			//判断要添加的名字
			if(!"column".equals(dataxReaderOP.getReaderparameter())) {
				//为假，表示不为column
				//接下来还需要判断该字段是否已经存在，如果存在就则给出提示信息，并修改
				String name=dataxReaderOP.getReaderparameter();
				String value=dataxReaderOP.getArg();
				readerManagement.addName(result, name,value);
				
			}else { //添加column
				System.out.println("没有对column进行更新");
			}
		}else {
			System.out.println("不是所要求的操作");
		}
		
		
		
		JsonManagement jManagement=new JsonManagement();
		JSONObject table=jManagement.translateJsonObjToTable(result);
		Map json=new HashMap();
		json.put("result", result);
		json.put("table", table);
		return json;
		
	}
	
	/*
	 * 删除指定reader的指定字段
	 * 
	 */
	public Map processDataxReaderDelete(DataxReaderOP dataxReaderOP) {
		ReaderManagement readerManagement=new ReaderManagement();
		//生成一个默认的reader
		JSONObject result=readerManagement.generateDefaultReader();
		String key=dataxReaderOP.getReaderparameter();
		readerManagement.deleteByKey(result,key);
		
		JsonManagement jManagement=new JsonManagement();
		JSONObject table=jManagement.translateJsonObjToTable(result);
		Map json=new HashMap();
		json.put("result", result);
		json.put("table", table);
		return json;
	}
	
	/**
	 * 处理reader的更新
	 * @param dataxReaderOP
	 * @return
	 */
	public Map processDataxReaderUpdate(DataxReaderOP dataxReaderOP) {
		ReaderManagement readerManagement=new ReaderManagement();
		//生成一个默认的reader
		JSONObject result=readerManagement.generateDefaultReader();
		System.out.println(JsonManagement.formatJson(result.toString()));
		
		//
		dataxManagement.updateReader(dataxReaderOP.getReaderparameter(), dataxReaderOP.getArg(), result);
		System.out.println(JsonManagement.formatJson(result.toString()));
		
		//将reader生成为一张表
		JsonManagement jManagement=new JsonManagement();
		JSONObject table=jManagement.translateJsonObjToTable(result);
		Map json=new HashMap();
		json.put("result", result);
		json.put("table", table);
		return json;
	}

	/*
	 * 查询指定的数据
	 */
	public Map processDataxReaderSelectAll(DataxReaderOP dataxReaderOP) {
		ReaderManagement readerManagement=new ReaderManagement();
		//生成一个默认的reader
		JSONObject result=readerManagement.generateDefaultReader();
		System.out.println(JsonManagement.formatJson(result.toString()));
		//将reader生成为一张表
		JsonManagement jManagement=new JsonManagement();
		JSONObject table=jManagement.translateJsonObjToTable(result);
		Map json=new HashMap();
		json.put("result", result);
		json.put("table", table);
		return json;
	}
	
	/**
	 * 
	 * 查询指定key的值
	 * @param dataxReaderOP
	 * @return
	 */
	public Map processDataxReaderSelect(DataxReaderOP dataxReaderOP) {
		//生成一个默认的reader
		ReaderManagement readerManagement=new ReaderManagement();
		JSONObject reader=readerManagement.generateDefaultReader();
		
		//执行对json的操作
		String readertype=dataxReaderOP.getReadertype();//要处理的reader类型
		String readerParameter=dataxReaderOP.getReaderparameter();
		String op=dataxReaderOP.getOp();
		String arg=dataxReaderOP.getArg();
	
		//得到用于管理reader的对象，通过该对象进行对reader操作
		Object result=readerManagement.findJsonByKey(reader,readerParameter);
		System.out.println(JsonManagement.formatJson(result.toString()));
		
		//将reader生成为一张表
		JsonManagement jManagement=new JsonManagement();
		if(readerParameter.equals("column")) {//如果直接就为column则还需要对查询的结果进行包装
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("column", result);
			result=jsonObject;
		}
			
		JSONObject table=jManagement.translateJsonObjToTable(result);
		
		
		//装配返回结果
		Map json=new HashMap();
		json.put("result", result);
		json.put("table", table);
		return json;
	}

	/**
	 * 
	 * 得到的是经过处理之后的json，是所有的json的数据
	 * @return
	 */
	public JSONArray getAllReaders() {
		JSONObject reader1=dataxManagement.generateDefaultReader();
		JSONObject reader2=dataxManagement.generateDefaultReader();
		JSONObject reader11=processReader(reader1);
		JSONObject reader12=processReader(reader1);
		JSONArray jsons=new JSONArray();
		jsons.add(reader11);
		jsons.add(reader12);
		return jsons;
	}

	/**
	 * 只得到json的name和种类
	 * @param reader1
	 * @return
	 */
	private JSONObject processReader(JSONObject reader) {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("name", reader.get("name"));
		return jsonObject;
	}
	
	
}
