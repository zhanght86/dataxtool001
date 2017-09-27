package com.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.set.ListOrderedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dataxmanagement.DataxManagement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class JobControl {
	@Autowired
	private DataxManagement dataxManagement;
	@RequestMapping("/datax/job/job/loadcc1")
	public @ResponseBody String  loadcc1() {
		JSONArray data=new JSONArray();
		List<String> names=dataxManagement.findAllReaderName(1,10);
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			data.add(jsonObject);
		}
		return data.toString();
	}
	@RequestMapping("/datax/job/job/loadcc2")
	public @ResponseBody String  loadcc2() {
		JSONArray data=new JSONArray();
		List<String> names=dataxManagement.findAllWriterName(1,10);
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			data.add(jsonObject);
		}
		return data.toString();
	}
	@RequestMapping("/datax/job/job/loadcc3")
	public @ResponseBody String  loadcc3() {
		JSONArray data=new JSONArray();
		List<String> names=dataxManagement.findAllSettingName(1,10);
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			data.add(jsonObject);
		}
		return data.toString();
	}
	
	@RequestMapping("/datax/job/job/pg")
	public @ResponseBody String  pg(HttpServletRequest request) {
		String reader=request.getParameter("reader");
		String writer=request.getParameter("writer");
		String setting=request.getParameter("setting");
		dataxManagement.pg(reader,writer,setting);
		return "";
	}
	
	@RequestMapping("/datax/job/job/findalljob")
	public @ResponseBody String  findalljob() {
		List<String> jobs=dataxManagement.findAllJobNames(1, 1);
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i<jobs.size();i++) {
			JSONObject jsonObject=new JSONObject();
			String name=jobs.get(i);
			jsonObject.put("name", name);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}
	

}
