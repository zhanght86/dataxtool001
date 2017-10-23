package com.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.set.ListOrderedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dao.domain.JsonFile;
import com.job.JobManagement;
import com.job.ReaderManagement;
import com.job.SettingManagement;

import javafx.scene.transform.Translate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class JobControl {
	@Autowired
	private JobManagement jobManagement;
	
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 在开启页面的时候加载数据
	 *	加载reader
	 *
	 */
	@RequestMapping("/datax/job/job/loadcc1.do")
	public @ResponseBody String  loadcc1() {
		JSONArray data=new JSONArray();
		//List<String> names=dataxManagement.findAllReaderName(1,10);
		List<String> names=jobManagement.findAllReaderName();
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			data.add(jsonObject);
		}
		return data.toString();
	}
	
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 加载writer的name
	 *
	 */
	@RequestMapping("/datax/job/job/loadcc2.do")
	public @ResponseBody String  loadcc2() {
		JSONArray data=new JSONArray();
		//List<String> names=dataxManagement.findAllWriterName(1,10);
		List<String> names=jobManagement.findAllWriterName();
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			data.add(jsonObject);
		}
		return data.toString();
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 加载setting的name
	 *
	 */
	@RequestMapping("/datax/job/job/loadcc3.do")
	public @ResponseBody String  loadcc3() {
		JSONArray data=new JSONArray();
		//List<String> names=dataxManagement.findAllWriterName(1,10);
		List<String> names=jobManagement.findAllSettingName();
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			data.add(jsonObject);
		}
		return data.toString();
	}
	
	@RequestMapping("/datax/job/job/pg.do")
	public @ResponseBody String  createJob(HttpServletRequest request) {
		String reader=request.getParameter("reader");
		String writer=request.getParameter("writer");
		String setting=request.getParameter("setting");
		String filename=request.getParameter("filename");
		//dataxManagement.pg(reader,writer,setting);
		jobManagement.createJob(reader,writer,setting,filename);
		return "";
	}
	
	@RequestMapping("/datax/job/job/findalljob.do")
	public @ResponseBody String  findalljob() {
		List<JSONObject> jsonObjects=jobManagement.findAllJobs();
		return jsonObjects.toString();
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description job管理
	 *
	 */
	public @ResponseBody String  findalljobname() {
		List<String> names=jobManagement.findAllJobNames();
		JSONArray jsonArray=translatecombobox(names);
		return jsonArray.toString();
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description 转化为combobox需要的数据格式
	 *
	 */
	private JSONArray translatecombobox(List<String> names) {
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			if(i==0) {
				jsonObject.put("selected", true);
			}
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	

}
