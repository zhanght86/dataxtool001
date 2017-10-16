package com.job;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dao.domain.JsonFile;
import com.json.Configuration;
import com.json.JsonManagement;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class ReaderManagement {
	@Autowired
	private JsonManagement jsonManagement=new JsonManagement();
	public JSONObject processReader() {
		JSONObject parameter=new JSONObject();
		JSONObject reader=new JSONObject();
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
/*	public void saveReader(JSONObject readerJson, String url) {
		//在保存之前要进行处理，因为readerjson中不是标准的reader格式
		url=url+readerJson.getString("filename");
		JSONObject formatReader=processRows(readerJson);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("filename", readerJson.getString("filename"));
		jsonObject.put("description", "");
		jsonObject.put("data", formatReader.toString());
		jsonManagement.JSONToFile(jsonObject, url);
		
	}*/
	/**
	 * 
	 * 处理前台传来的json
	 * 前台传来的数据不是标准的格式，需要进行处理转化为json格式的
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
	 * 将标准格式的json转化为前端需要的格式
	 * 	 	{ 
			"name":"streamreader",
			"filename":"reader1",
 			"type":"reader"
 			}
	 * @param readers
	 * @return
	 */
	public JSONObject processReadersToResult(List readers) {
		JSONObject result=new JSONObject();
		JSONArray rows=new JSONArray();
		for(int i=0;i<readers.size();i++) {
			JSONObject reader=(JSONObject) readers.get(i);
			JSONObject row=new JSONObject();
			String name=reader.getJSONObject("data").getString("name");
			String filename=reader.getString("filename");
			String type="reader";
			row.put("name", name);
			row.put("filename", filename);
			row.put("type", type);
			rows.add(row);	
		}
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
	/**
	 * 
	 * 根据文件名查询对应的reader，并对格式进行处理
	 * @param filename
	 * @return
	 */
/*	public JSONObject findReaderByFilename(String filename) {
		List<JSONObject> jsons=findAllReaders();
		JSONObject jsonObject=null;
		for(int i=0;i<jsons.size();i++) {
			if(filename.equals(jsons.get(i).getString("filename"))) {
				jsonObject=jsons.get(i);
			}
		}
		return jsonObject;
	}*/
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 根据id查找对应的reader,并将数据转化为前台需要的格式
	 *是一个多行的数据
	 *
	 */
	public JSONObject findReaderById(int id) {
		//List<JSONObject> jsons=findAllReaders();
		List<JsonFile> jsonFiles=jsonManagement.findAllPrimaryJsonFiles();
		JSONObject jsonObject=new JSONObject();
		JsonFile jsonFile=null;
		for(int i=0;i<jsonFiles.size();i++) {
			if(id==jsonFiles.get(i).getId()) {
				jsonFile=jsonFiles.get(i);
			}
		}
		if(jsonFile==null) {
			System.out.println("没有指定id的reader");
			return null;
		}else {
			JSONObject data =JSONObject.fromObject(jsonFile.getData());
			jsonObject.put("filename", jsonFile.getFilename());
			jsonObject.put("type", jsonFile.getType());
			jsonObject.put("id", jsonFile.getId());
			jsonObject.put("data",translateReaderJson(data));
			return jsonObject;
		}

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
	public JSONArray translateReaderJson(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		JSONArray rows=new JSONArray();
		
		//result.put("filename", jsonObject.getString("filename"));
		
		
		//JSONObject reader=jsonObject.getJSONObject("data");
		Map names=findAllName(data);
		Map parameters=findAllParameters(data);
		JSONArray column=findColumn(data);
		
		JSONArray arr1=processNames(names);
		JSONArray arr2=processParameters(parameters);
		JSONArray arr3=processColumn(column);
		rows.addAll(arr1);
		rows.addAll(arr2);
		rows.addAll(arr3);
		//result.put("rows", rows);
		return rows;
	}

	private JSONArray processColumn(JSONArray column) {
		JSONArray array=new JSONArray();
		if(column==null) {
			return array;
		}else {
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
		JSONObject parameter=(JSONObject) reader.get("parameter");
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
		JSONObject p=(JSONObject) reader.get("parameter");
		//System.out.println(p);
		if(p == null) {
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
	/**
	 * 
	 * 删除指定文件
	 * @param filename
	 */
	public void deleteFileByFilename(String filename) {
		//需要全路径名
		String url="d://json//";
		filename=url+filename+".json";
		jsonManagement.DeleteFolder(filename);
		
	}
	/**
	 * 
	 * 找到所有的文件的名字
	 * @return
	 */
/*	public List<String> findAllReadersName() {
		List<JSONObject> jsons=findAllReaders();
		List<String> names=new LinkedList<String>();
		for(int i=0;i<jsons.size();i++) {
			names.add(jsons.get(i).getString("filename"));
		}
		return names;
		
	}*/
	
	
	/**
	 *@param rows 
	 * @param page 
	 * @ahthor wang
	 *@date  2017.10.13
	 *@description 从后台得到所有的类型type=reader的json对象
	 *	并且转化为前台需要的数据格式
	 *	{
	 *		data:
	 *		total
	 *	}
	 *
	 */
	public List<JSONObject> findAllReaders(int page, int rows) {
		List<JsonFile> jsonFiles=jsonManagement.findAllPrimaryJsonFiles();
		//得到偶有类型为reader的json文件
		List<JsonFile> readerJsonFiles=getTypeIsReader(jsonFiles);
		//转化为前台需要的reader的格式
		List<JSONObject> jsonObjects=translateJsonFilesToJsonObjects(readerJsonFiles);
		return jsonObjects;
		
	}
	
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 判断找出type为reader的jsonFile
	 *
	 */
	private List<JsonFile> getTypeIsReader(List<JsonFile> jsonFiles) {
		List<JsonFile> result=new LinkedList<JsonFile>();
		for(int i=0;i<jsonFiles.size();i++) {
			JsonFile jsonFile=jsonFiles.get(i);
			if("reader".equals(jsonFile.getType())) {
				result.add(jsonFile);
			}
			
		}
		return result;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 将后台传来的数据转化为前台reader需要的格式
	 *	 * 后台传来的数据
	 * {
	 * 	filename:
	 * 	id:
	 * 	type:
	 * }
	 * 
	 * 前台需要的格式
	 * {
	 * 	filename:
	 * 	id:
	 * 	type:reader  //查出类型为reader的json数据
	 * }
	 *
	 */
	private JSONObject translateJsonFileToJsonObject(JsonFile jsonFile) {
		JSONObject jsonobject=new JSONObject();
		jsonobject.put("filename", jsonFile.getFilename());
		jsonobject.put("id", jsonFile.getId());
		jsonobject.put("type", jsonFile.getType());
		return jsonobject;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 将一批jsonFIle转化为reader需要的jsonObject
	 *
	 */
	private List<JSONObject> translateJsonFilesToJsonObjects(List<JsonFile> jsonFiles){
		List<JSONObject> jsonObjects=new LinkedList<JSONObject>();
		for(int i=0;i<jsonFiles.size();i++) {
			JsonFile jsonFile=jsonFiles.get(i);
			JSONObject jsonObject=translateJsonFileToJsonObject(jsonFile);
			jsonObjects.add(jsonObject);
		}
		return jsonObjects;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 保存一个reader
	 *	前台传来的格式需要进行处理
	 *
	 */
	public void saveReader(JSONObject readerJson) {
		//在保存之前要进行处理，因为readerjson中不是标准的reader格式
		JSONObject formatReader=processRows(readerJson);
		JSONObject jsonObject=new JSONObject();
		String filename=readerJson.getString("filename");
		String data=formatReader.toString();
		String type="reader";
		jsonManagement.save(filename, data, type);
		
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 根据指定的id来删除reader
	 *
	 */
	public void deleteReaderById(int i) {
		jsonManagement.deleteJsonFileById(i);
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 得到所有的reader的名字
 	 *
	 */
	public List<String> findAllReadersName() {
		List<String> names=new LinkedList<String>();
		List<JSONObject> jsonObjects=jsonManagement.findAllJsonFiles();
		for(int i=0;i<jsonObjects.size();i++) {
			String filename=jsonObjects.get(i).getString("filename");
			String type=jsonObjects.get(i).getString("type");
			if("reader".equals(type)) {
				names.add(filename);
			}
		
		}
		return names;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 根据filename名字查找reader
	 *
	 */
	public JsonFile findReaderByFilename(String filename) {
		List<JsonFile> jsonFiles=jsonManagement.findJsonFilesByType("reader");
		for(int i=0;i<jsonFiles.size();i++) {
			JsonFile jsonFile=jsonFiles.get(i);
			if(filename.equals(jsonFile.getFilename())) {
				return jsonFile;
			}
		}
		return null;
	}


	
}
