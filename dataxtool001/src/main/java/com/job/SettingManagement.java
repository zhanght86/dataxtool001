package com.job;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
@Service
public class SettingManagement {
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
}
