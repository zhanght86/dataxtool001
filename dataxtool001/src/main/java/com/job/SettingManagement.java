package com.job;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.resource.spi.RetryableUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dao.domain.JsonFile;
import com.json.Configuration;

import com.json.FileJson;
import com.json.JsonManagement;
import com.sun.org.apache.bcel.internal.generic.I2F;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
@Service
public class SettingManagement {
	@Autowired
	private JsonManagement jsonManagement=new JsonManagement();
	public JSONObject processSetting() {
		JSONObject speed=new JSONObject();
		speed.put("byte", 10485760);
		JSONObject setting=new JSONObject();
		setting.put("speed", speed);
		JSONObject errorLimit=new JSONObject();
		//JSONObject record=new JSONObject();
		//JSONObject percentage=new JSONObject();
		errorLimit.put("record", 0);
		errorLimit.put("percentage", 0.02);
		setting.put("errorLimit", errorLimit);
		return setting;
	}

	public JSONObject generateDefaultSetting() {
		JSONObject speed=new JSONObject();
		speed.put("byte", 10485760);
		JSONObject setting=new JSONObject();
		setting.put("speed", speed);
		JSONObject errorLimit=new JSONObject();
		errorLimit.put("record", 0);
		errorLimit.put("percentage", 0.02);
		setting.put("errorLimit", errorLimit);
		return setting;
	}
	/**
	 *@param rows 
	 * @param page 
	 * @ahthor wang
	 *@date  2017.10.16
	 *@description 找到所有的setting
	 *
	 */
	public List<JSONObject> findAllSetting(int page, int rows) {
		//得到偶有类型为reader的json文件
		List<JsonFile> readerJsonFiles=jsonManagement.findJsonFilesByType("setting");
		//转化为前台需要的reader的格式
		List<JSONObject> jsonObjects=translateJsonFilesToJsonObjects(readerJsonFiles);
		return jsonObjects;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description  转化jsonfiles成为前台需要的格式
	 *
	 */
	private List<JSONObject> translateJsonFilesToJsonObjects(List<JsonFile> jsonFiles) {
		List<JSONObject> jsonObjects=new LinkedList<JSONObject>();
		for(int i=0;i<jsonFiles.size();i++) {
			JsonFile jsonFile=jsonFiles.get(i);
			JSONObject jsonObject=translateJsonFileToJsonObject(jsonFile);
			jsonObjects.add(jsonObject);
		}
		return jsonObjects;
	}

	private JSONObject translateJsonFileToJsonObject(JsonFile jsonFile) {
		JSONObject jsonobject=new JSONObject();
		jsonobject.put("filename", jsonFile.getFilename());
		jsonobject.put("id", jsonFile.getId());
		jsonobject.put("type", jsonFile.getType());
		return jsonobject;
	}

	/**
	 * @param filename 文件名
	 * @return 返回为null表示没有找到，返回的格式是前台和setting需要的标准格式
	 */
	public JsonFile findSettingByFilename(String filename) {
		List<JsonFile> jsonFiles=jsonManagement.findJsonFilesByType("setting");
		for(int i=0;i<jsonFiles.size();i++) {
			JsonFile jsonFile=jsonFiles.get(i);
			if(filename.equals(jsonFile.getFilename())) {
				return jsonFile;
			}
		}
		return null;
		
	}
	/**
	 * @param filename 文件名
	 * @return 返回为null表示没有找到，返回的格式是前台和setting需要的标准格式
	 */
	public JSONObject findPrimarySettingByFilename(String filename) {
		//得到原始的文件对象
		JSONObject file=jsonManagement.findFileByName(Configuration.settingurl, filename);
		if(file==null) {
			System.out.println("没有找到指定文件名的文件");
			return null;
		}else {
			return file;
		}
		
	}
	/**
	 * 
	 * 
	 * @param file 原始的数据格式
	 * @return  返回setting格式，该格式是前台需要的，并且进行了setting处理的格式
			{
				filename：
				rows:[]
			}

	 	    row = {    每一的标准格式
			  name:'',    
			  value:'',    
			  group:'setting',    
			  editor:'text'  
		  } 
		  
		  value会有三种情况
		  	1，String
		  	2.jsonobj
		  	3.jsonarr
	 */
	private JSONObject translatePrimaryToSettingJson(JsonFile jsonFile) {
		JSONObject settingjson=new JSONObject();
		JSONArray rows=new JSONArray();
		settingjson.put("filename", jsonFile.getFilename());
		//得到data  有三种情况  json  array
		String data=jsonFile.getData();
		Object json=new JSONTokener(data).nextValue();
		//将json对象转化为一张key-value表
		JSONObject table=jsonManagement.translateJsonObjToTable(json);
		Iterator iterator=table.keys();
		while(iterator.hasNext()) {
			JSONObject row=new JSONObject();
			//得到所有的key
			String key=iterator.next().toString();
			row.put("name", key);
			Object value=table.get(key);
			if(value instanceof String) {
				row.put("value", value);
			}else if(value instanceof JSONObject) {
				row.put("value", "jsonobj");
			}else if(value instanceof JSONArray) {
				row.put("value", "jsonarray");
			}
			row.put("group", "setting");
			row.put("editor", "text");
			rows.add(row);
			settingjson.put("rows", rows);
		}
		return settingjson;
	}

	public List<String> findAllSettingName() {
/*		List<JSONObject> jsons=findAllSetting();
		List<String> names=new LinkedList<String>();
		for(int i=0;i<jsons.size();i++) {
			names.add(jsons.get(i).getString("filename"));
		}
		return names;*/
		return null;
	}

	public boolean saveSetting(String filename, JSONArray rows) {
		//SettingManagement settingManagement=jcm.getSettingManagement();
		JSONObject data=new JSONObject();
		//转化数据成为后台需要的json数据
		for(int i=0;i<rows.size();i++) {
			JSONObject row=rows.getJSONObject(i);
			String name=row.getString("name");
			String value=row.getString("value");
			data.put(name, value);
		}
		//FileJson fileJson=new FileJson();
		JsonFile jsonFile=new JsonFile();
		jsonFile.setFilename(filename);
		jsonFile.setData(data.toString());
/*		fileJson.setFilename(filename);
		fileJson.setData(data.toString());*/
		jsonManagement.save(filename, data.toString(), "setting");
		return true;
		//return jsonManagement.saveFile(Configuration.settingurl, fileJson);
	}

	public void deleteSettingById(int i) {
		jsonManagement.deleteJsonFileById(i);
		
	}

	public List<String> findAllSettingsName() {
		List<String> names=new LinkedList<String>();
		List<JSONObject> jsonObjects=jsonManagement.findAllJsonFiles();
		for(int i=0;i<jsonObjects.size();i++) {
			String filename=jsonObjects.get(i).getString("filename");
			String type=jsonObjects.get(i).getString("type");
			if("setting".equals(type)) {
				names.add(filename);
			}
		}
		return names;
	}
}
