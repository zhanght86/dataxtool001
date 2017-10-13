package com.linux;
import java.util.List;

import com.job.JobManagement;
import com.json.JsonManagement;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		JsonManagement jsonManagement=new JsonManagement();
		List<JSONObject> jsonObjects=jsonManagement.findAllFileByUrl_001("d://json/");
		JSONObject jsonObject=jsonObjects.get(0);
		System.out.println(jsonManagement.formatJson(jsonObject.toString()));
		
		//jsonManagement.saveFileData("d://json/job/","job.json", "d:\\json\\", "data.json");
	
	}
}
