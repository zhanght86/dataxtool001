package com.job;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.parser.JSONScanner;
import com.json.Configuration;
import com.json.JsonManagement;

import net.sf.json.JSONObject;

@Controller
public class JobManagement {
	@Autowired
	private JsonManagement jsonManagement=new JsonManagement();
	public List<JSONObject> findAllJobs() {
		String url=Configuration.joburl;
		List jobs=jsonManagement.parseJsonFileToJsonObjects(url);
		return jobs;
		
	}
	/**
	 * 
	 * 得到所有job的名字
	 * @return
	 */
	public List<String> findAllJobNames() {
		List<JSONObject> jsonObjects=findAllJobs();
		List<String> names=new LinkedList<String>();
		for(int i=0;i<jsonObjects.size();i++) {
			names.add(jsonObjects.get(i).getString("filename"));
		}
		return names;
		
	}
}
