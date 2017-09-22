package com.job;

import java.security.Policy.Parameters;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.json.JsonManagement;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class ReaderManagement {
	@Autowired
	private JsonManagement jsonManagement=new JsonManagement();
	public JSONObject processReader() {
		JSONObject parameter=jsonManagement.createNewJsonObject();
		JSONObject reader=jsonManagement.createNewJsonObject();
		reader.put("name", "mysqlreader");
		reader.put("parameter", parameter);
		return reader;
	}
	public void addParameter(JSONObject reader,Object key,Object value) {
		JSONObject p=(JSONObject) reader.get("parameter");
		if(p==null) {//娌℃湁鍒欏垱寤轰竴涓�
			p=new JSONObject();
			p.put(key, value);
			reader.put("parameter", p);
			
		}else {
			p.put(key, value);
		}
	}
	public void addName(JSONObject reader,String key,String value) {
		reader.put(key, value);
	}
	public void addColumn(JSONObject reader,String value,String type) {
		JSONObject c=new JSONObject();
		c.put("value", 	value);
		c.put("type", type);

		JSONObject p=null;
		if(!reader.containsKey("parameter")) {
			p=new JSONObject();
			reader.put("parameter", p);

		}else {
			p=reader.getJSONObject("parameter");
		}
		//JSONObject column=p.getJSONObject("column");
		JSONArray column=null;
		if(!p.containsKey("column")) {
			column=new JSONArray();
			
		}else {
			column=p.getJSONArray("column");
		}
		column.add(c);
		p.put("column", column);
		reader.put("parameter", p);
		//addParameter(reader, "parameter", p);
		
	}
	public JSONObject generateDefaultReader() {
		JSONObject parameter=new JSONObject();
		JSONObject defaultReader=new JSONObject();
		addName(defaultReader,"name", "streamreader");
		addColumn(defaultReader, "DataX", "string");
		addColumn(defaultReader, "19890604", "long");
		addColumn(defaultReader, "1989-06-04 00:00:00", "date");
		addColumn(defaultReader, "true", "bool");
		addColumn(defaultReader, "tests", "bytes");
		addParameter(defaultReader, "sliceRecordCount", 10000);
		return defaultReader;
	}
	/**
	 * 
	 * 
	 * @param name
	 * @param value
	 * @param json
	 * 更新reader
	 */
	public void updateReader(String name, String value,  JSONObject json) {
		//这里得到的结果是字符串
		jsonManagement.anzlizeAndUpdate(name, value, json);
		
	}
	/**
	 * 
	 * 删除指定的参数
	 * @param reader
	 * @param key
	 */
	public void deleteByKey(JSONObject reader, String key) {
		jsonManagement.anzlizeAndDelete(key, reader);
		
	}
	/**
	 * 
	 * 
	 * 查询指定key的json对象
	 * @param reader
	 * @param readerParameter
	 * @return
	 */
	public Object findJsonByKey(JSONObject reader, String readerParameter) {
		//这里返回结果会有三种jsonObj,jsonArray,value
		//要对value进行特殊处理
		Object result=jsonManagement.findJSONByKey(readerParameter, reader, false);
		if((result instanceof JSONObject)||(result instanceof JSONArray)) {
			return result;
		}else {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put(readerParameter, result);
			return jsonObject;
			
		}
		
	}
	/**
	 * 
	 * 根据指定的url和json对象保存文件
	 * @param readerJson
	 * @param url
	 */
	public void saveReader(JSONObject readerJson, String url) {
		//在保存之前要进行处理，因为readerjson中不是标准的reader格式
		url=url+readerJson.getString("filename")+".json";
		JSONObject formatReader=processRows(readerJson);
		jsonManagement.JSONToFile(formatReader, url);
		
	}
	/**
	 * 
	 * 处理前台传来的json
	 * @param readerJson
	 * @return
	 */
	private JSONObject processRows(JSONObject readerJson) {
		
		JSONObject formateReader=new JSONObject();
		JSONArray rows=readerJson.getJSONArray("rows");
		//一行一行的处理数据
		for(int i=0;i<rows.size();i++) {
			JSONObject r=(JSONObject) rows.get(i);
			String group=r.getString("group");
			String name=r.getString("name");
			String value=r.getString("value");
			if("reader".equals(group)) {
				addName(formateReader, name, value);
			}else if("column".equals(group)) {
				String[] typeAndValue=value.split("/");
				addColumn(formateReader, typeAndValue[1], typeAndValue[0]);
			}else{//默认为parameter
				addParameter(formateReader, name, value);
			}
			
			
		}
		return formateReader;
	}
	/**
	 * 
	 * 查询所有的reader并返回
	 * 会从默认的路径中寻找
	 * 查找的的数据不是标准的readerjson格式
	 * {
	 * 	filename:
	 * 	data:
	 * }
	 * @return
	 */
	public List findAllReaders() {
		String url="d://json/";
		return jsonManagement.parseJsonFileToJsonObjects(url);
		
	}
	/**
	 * 
	 * 根据文件名查询对应的reader，并对格式进行处理
	 * @param filename
	 * @return
	 */
	public JSONObject findReaderByFilename(String filename) {
		List<JSONObject> jsons=findAllReaders();
		JSONObject jsonObject=null;
		for(int i=0;i<jsons.size();i++) {
			if(filename.equals(jsons.get(i).getString("filename"))) {
				jsonObject=jsons.get(i);
			}
		}
		return jsonObject;
	}
	/**
	 * 
	 * 将后台取出的json加工为前台需要的格式的数据
	 *			 {    
				  "name":"",    
				  "value":"",    
				  "group":"",    
				  "editor":'text'   
				}
	 * @param jsonObject
	 * @return
	 */
	public JSONObject translateReaderJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		JSONArray rows=new JSONArray();
		
		result.put("filename", jsonObject.getString("filename"));
		
		
		JSONObject reader=jsonObject.getJSONObject("data");
		Map names=findAllName(reader);
		Map parameters=findAllParameters(reader);
		JSONArray column=findColumn(reader);
		
		JSONArray arr1=processNames(names);
		JSONArray arr2=processParameters(parameters);
		JSONArray arr3=processColumn(column);
		rows.addAll(arr1);
		rows.addAll(arr2);
		rows.addAll(arr3);
		result.put("rows", rows);
		return result;
	}

	private JSONArray processColumn(JSONArray column) {
		JSONArray array=new JSONArray();
		
		for(int i=0;i<column.size();i++) {
			JSONObject c=(JSONObject) column.get(i);
			JSONObject jsonObject= new JSONObject();
			String typeAndValue=c.getString("type")+"/"+c.getString("value");
			jsonObject.put("name", "type/value");
			jsonObject.put("value", typeAndValue);
			jsonObject.put("group", "column");
			jsonObject.put("editor", "text");
			array.add(jsonObject);	
		}
		return array;
	}
	private JSONArray processParameters(Map<String,String> parameters) {
		JSONArray array=new JSONArray();
		
		for(Map.Entry<String, String> entry:parameters.entrySet()) {
			JSONObject jsonObject= new JSONObject();
			jsonObject.put("name", entry.getKey());
			jsonObject.put("value", entry.getValue());
			jsonObject.put("group", "parameter");
			jsonObject.put("editor", "text");
			array.add(jsonObject);
			
		}
		return array;
	}
	//处理names成为row格式的数据
	private JSONArray processNames(Map<String,String> names) {
		JSONArray array=new JSONArray();
		
		for(Map.Entry<String, String> entry:names.entrySet()) {
			JSONObject jsonObject= new JSONObject();
			jsonObject.put("name", entry.getKey());
			jsonObject.put("value", entry.getValue());
			jsonObject.put("group", "reader");
			jsonObject.put("editor", "text");
			array.add(jsonObject);
			
		}
		return array;
	}
	private JSONArray findColumn(JSONObject reader) {
		JSONObject parameter=reader.getJSONObject("parameter");
		if(parameter==null) {
			return null;
		}else {
			JSONArray column=parameter.getJSONArray("column");
			if(column==null) {
				return null;
				
			}else {
				return column;
			}
			
		}
		
	}
	private Map findAllParameters(JSONObject reader) {
		Map paramters=new HashMap();
		JSONObject p=reader.getJSONObject("parameter");
		if(p==null) {
			return paramters;
		}
		paramters=findAllName(p);
		return paramters;
	}
	/**
	 * 
	 * 查询reader下面的所有属性的名字
	 * 并且对这个结果进行封装
	 * group=reader
	 * @param reader
	 * @return
	 */
	private Map<String,String> findAllName(JSONObject reader) {
			Map<String, String> keyAndValue=new HashMap();
		//将json对象的key遍历
			Iterator it=reader.keys();
			while(it.hasNext()) {
				//每次遍历得到的是属性的名字
				String subKey=it.next().toString();
					if(reader.get(subKey) instanceof String) {
						keyAndValue.put(subKey, reader.getString(subKey));
					}
			}
			return keyAndValue;

		
	}
	
}
