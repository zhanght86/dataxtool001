package com.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * jocconfigurationmanagement:用来对配置文件做管理
 * 		该对象有四个组件
 * 		SettingManagement
 * 		JsonManagement
 * @author Johnny
 *
 */
@Service
public class JobConfigurationManagement {
	@Autowired
	private SettingManagement settingManagement;
	@Autowired
	private ContentManagement contentManagement;
	public JSONObject generateDefaultConfiguration() {
		JSONArray defaultContent=contentManagement.generateDefaultContent();
		JSONObject defaultSetting=settingManagement.generateDefaultSetting();
		JSONObject job=new JSONObject();
		job.put("setting", defaultSetting);
		job.put("content", defaultContent);
		JSONObject c=new JSONObject();
		c.put("job", job);
		return c;
	}
	public JSONObject processConfiguration(JSONObject job) {
		
		JSONObject c=new JSONObject();
		c.put("job", job);
		return c;
	}
	

	public void updateReader(String name, String value, JSONObject json) {
		ReaderManagement readerManagement=contentManagement.getReaderManagement();
		readerManagement.updateReader(name,value,json);
		
	}
	
	public ReaderManagement findReaderManagement() {
		return  contentManagement.getReaderManagement();
		
		
	}

}
