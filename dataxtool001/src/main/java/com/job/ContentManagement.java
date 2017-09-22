package com.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.json.JsonManagement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class ContentManagement {
	@Autowired
	private JsonManagement jsonManagement=new JsonManagement();
	@Autowired
	private ReaderManagement readerManagement=new ReaderManagement();
	@Autowired
	private WriterManagement writerManagement=new WriterManagement();
	public JSONArray processContent() {
		JSONArray content=new JSONArray();
		JSONObject obj=jsonManagement.createNewJsonObject();
		JSONObject obj1=jsonManagement.createNewJsonObject();
		JSONObject reader=readerManagement.processReader();
		JSONObject writer=writerManagement.processWriter();
		obj.put("reader", reader);
		obj.put("writer", writer);
		content.add(obj);
		//content.add(writer);
		return content;
	}
	public JSONArray processContent(JSONObject reader,JSONObject writer) {
		JSONArray content=new JSONArray();
		JSONObject obj=jsonManagement.createNewJsonObject();
		obj.put("reader", reader);
		obj.put("writer", writer);
		content.add(obj);
		return content;
	}
	public JSONArray generateDefaultContent() {
		JSONArray defaultContent=new JSONArray();
		JSONObject obj=new JSONObject();
		JSONObject defaultReader=readerManagement.generateDefaultReader();
		JSONObject defaultWriter=writerManagement.generateDefaultWriter();
		obj.put("reader", defaultReader);
		obj.put("writer", defaultWriter);
		defaultContent.add(obj);
		return defaultContent;
	}
	public void updateReader(String name, String value, String op, JSONObject json) {
		
		
	}
	public ReaderManagement getReaderManagement() {
		return readerManagement;
	}



}
