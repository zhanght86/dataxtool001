package com.control;

import javax.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataxmanagement.DataxManagement;
import com.domain.Linux;
import com.service.DataxServiceManagement;
import com.service.LinuxServiceManagement;

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
	private LinuxServiceManagement linuxServiceManagement;
	//加载任务
	@RequestMapping("/datax/task/loadjob.do")
	public @ResponseBody String loadJob() {
		JSONArray array=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", 1);
		jsonObject.put("text", "job1");
		JSONObject jsonObject1=new JSONObject();
		jsonObject1.put("id", 2);
		jsonObject1.put("text", "job2");
		array.add(jsonObject);
		array.add(jsonObject1);
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
		JSONObject job=null;
		Linux linux=new Linux();
		linux.setIp("192.168.100.165");
		linux.setUsername("root");
		linux.setPassword("wangrui");
		String exeResult=linuxServiceManagement.exe(linux,job);
		JSONObject result=new JSONObject();
		result.put("result", exeResult);
		return result.toString();
	}
	
	
}
