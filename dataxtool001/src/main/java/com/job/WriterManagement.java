package com.job;

import org.springframework.stereotype.Service;

import com.json.JsonManagement;

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
}
