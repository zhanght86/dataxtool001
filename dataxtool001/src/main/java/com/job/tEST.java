package com.job;

import java.util.List;

import com.json.FileJson;

import net.sf.json.JSONObject;

public class tEST {
	public static void main(String[] args) {
		SettingManagement settingManagement=new SettingManagement();
		List<JSONObject> jsonObjects=settingManagement.findAllSetting();
		System.out.println(jsonObjects);
	}
}
