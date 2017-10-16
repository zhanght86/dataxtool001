package com.json;

import com.alibaba.fastjson.parser.JSONReaderScanner;
import com.dao.domain.JsonFile;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		JsonManagement jsonManagement=new JsonManagement();
		JSONObject jsonObject=jsonManagement.parseJsonFileToJsonObject("");
		JsonFile jsonFile=new JsonFile();
		jsonFile.setData(jsonObject.toString());
		jsonFile.setFilename("job1");
		jsonFile.setType("job");
		jsonManagement.save(jsonFile);
		//System.out.println(jsonManagement.formatJson(jsonObject.toString()));
	}
}
