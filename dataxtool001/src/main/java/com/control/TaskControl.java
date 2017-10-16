package com.control;

import java.util.List;

import javax.json.Json;

import org.omg.DynamicAny.NameDynAnyPairSeqHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.dataxmanagement.DataxManagement;
import com.job.JobManagement;
import com.linux.Linux;
import com.service.DataxServiceManagement;
import com.service.LinuxServiceManagement;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 执行任务
 * @author wang
 *
 */
@Controller
public class TaskControl {
	
	@Autowired
	private DataxServiceManagement dataxServiceManagement;
	
	@Autowired
	private JobManagement jobManagement;
	//private LinuxServiceManagement linuxServiceManagement;
	//加载任务
	@RequestMapping("/datax/task/loadjob.do")
	public @ResponseBody String loadJob() {
		List<String> names=jobManagement.findAllJobNames();
		JSONArray array=new JSONArray();
		for(int i=0;i<names.size();i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", i);
			jsonObject.put("text", names.get(i));
			array.add(jsonObject);
		}
		return array.toString();
	}
	@RequestMapping("/datax/task/loadlinux.do")
	public @ResponseBody String loadLinux() {
		JSONArray array=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", 1);
		jsonObject.put("text", "linux1");
		JSONObject jsonObject1=new JSONObject();
		jsonObject1.put("id", 2);
		jsonObject1.put("text", "linux2");
		array.add(jsonObject);
		array.add(jsonObject1);
		return array.toString();
	}
	
	@RequestMapping("/datax/task/exe.do")
	public @ResponseBody String exe() {
		Linux linux=new Linux();
		linux.setHostname("192.168.50.165");
		linux.setUsername("root");
		linux.setPassword("wangrui");
		
		return linux.exe();
	}
	
	
}
