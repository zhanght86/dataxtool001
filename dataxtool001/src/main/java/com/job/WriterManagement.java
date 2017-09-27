package com.job;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.json.Configuration;
import com.json.JsonManagement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class WriterManagement {
	private JsonManagement jsonManagement=new JsonManagement();
	public JSONObject processWriter() {
		JSONObject parameter=jsonManagement.createNewJsonObject();
		JSONObject reader=jsonManagement.createNewJsonObject();
		reader.put("name", "mysqlwriter");
		reader.put("parameter", parameter);
		return reader;
	}
	public void addName(JSONObject writer,String valueOfName) {
		writer.put("name", valueOfName);
	}
	public void addParameter(JSONObject writer,Object key,Object value) {

		if(!writer.containsKey("parameter")) {//没有则创建一个
			JSONObject p=new JSONObject();
			p.put(key, value);
			writer.put("parameter", p);
			
		}else {
			JSONObject p=(JSONObject) writer.get("parameter");
			p.put(key, value);
			writer.put("parameter", p);
		}
	}
	public JSONObject generateDefaultWriter() {
		JSONObject parameter=new JSONObject();
		JSONObject defaultWriter=new JSONObject();
		addName(defaultWriter, "streamwriter");
		addParameter(defaultWriter,  "print", false);
		addParameter(defaultWriter, "encoding", "UTF-8");
		
		return defaultWriter;
	}
	
	public List findAllWriters() {
		String url=Configuration.writerurl;
		return jsonManagement.parseJsonFileToJsonObjects(url);

	}
	public void saveWriter(JSONObject writerJson, String url) {
		//在保存之前要进行处理，因为readerjson中不是标准的reader格式
		url=url+writerJson.getString("filename");
		JSONObject formatReader=processRows(writerJson);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("filename", writerJson.getString("filename"));
		jsonObject.put("description", "");
		jsonObject.put("data", formatReader.toString());
		jsonManagement.JSONToFile(jsonObject, url);
	}
	/**
	 * 
	 * 前台传来的数据处理成后台需要的数据
	 * @param writerJson
	 * @return
	 */
	private JSONObject processRows(JSONObject writerJson) {
		JSONObject formateReader=new JSONObject();
		JSONArray rows=writerJson.getJSONArray("rows");
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
	public void addName(JSONObject writer,String key,String value) {
		writer.put(key, value);
	}
	public void addColumn(JSONObject writer,String value,String type) {
		JSONObject c=new JSONObject();
		c.put("value", 	value);
		c.put("type", type);

		JSONObject p=null;
		if(!writer.containsKey("parameter")) {
			p=new JSONObject();
			writer.put("parameter", p);

		}else {
			p=writer.getJSONObject("parameter");
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
		writer.put("parameter", p);
	}
	public void deleteFileByFilename(String filename) {
		//需要全路径名
		String url=Configuration.writerurl;
		filename=url+filename;
		jsonManagement.DeleteFolder(filename);
		
	}
	public JSONObject findWriterByFilename(String filename) {
		List<JSONObject> jsons=findAllWriters();
		JSONObject jsonObject=null;
		for(int i=0;i<jsons.size();i++) {
			if(filename.equals(jsons.get(i).getString("filename"))) {
				jsonObject=jsons.get(i);
			}
		}
		return jsonObject;
	}
	public JSONObject translateWriterJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		JSONArray rows=new JSONArray();
		
		result.put("filename", jsonObject.getString("filename"));
		
		
		JSONObject writer=jsonObject.getJSONObject("data");
		Map names=findAllName(writer);
		Map parameters=findAllParameters(writer);
		JSONArray column=findColumn(writer);
		
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
	private JSONArray findColumn(JSONObject writer) {
		JSONObject parameter=(JSONObject) writer.get("parameter");
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
	private Map findAllParameters(JSONObject writer) {
		Map paramters=new HashMap();
		JSONObject p=(JSONObject) writer.get("parameter");
		//System.out.println(p);
		if(p == null) {
			return paramters;
		}
		paramters=findAllName(p);
		return paramters;
	}
	private Map findAllName(JSONObject writer) {
		Map<String, String> keyAndValue=new HashMap();
		//将json对象的key遍历
		Iterator it=writer.keys();
		while(it.hasNext()) {
			//每次遍历得到的是属性的名字
			String subKey=it.next().toString();
				if(writer.get(subKey) instanceof String) {
					keyAndValue.put(subKey, writer.getString(subKey));
				}
		}
		return keyAndValue;
	}
	public List<String> findAllWriterName() {
		List<JSONObject> jsons=findAllWriters();
		List<String> names=new LinkedList<String>();
		for(int i=0;i<jsons.size();i++) {
			names.add(jsons.get(i).getString("filename"));
		}
		return names;
	}

}
