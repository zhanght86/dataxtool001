package com.job;

import java.util.List;

import org.springframework.stereotype.Service;

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
		String url="d://json01/";
		return jsonManagement.parseJsonFileToJsonObjects(url);

	}
	public void saveWriter(JSONObject writerJson, String url) {
		//在保存之前要进行处理，因为readerjson中不是标准的reader格式
		url=url+writerJson.getString("filename")+".json";
		JSONObject formatReader=processRows(writerJson);
		jsonManagement.JSONToFile(formatReader, url);
	}
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
		String url="d://json01//";
		filename=url+filename+".json";
		jsonManagement.DeleteFolder(filename);
		
	}

}
