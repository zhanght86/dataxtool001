package com.control;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.control.info.TaskInfo;
import com.dao.domain.JsonFile;
import com.dao.domain.Linux;
import com.job.JobManagement;
import com.linux.LinuxManagement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *@ahthor wang
 *@date  2017.10.19 上午10:08:26
 *@description 学习
 *	
 *
 *
 */

/**
 *@ahthor wang
 *@date  2017.10.19 上午9:27:18
 *@description 
 *	前台传来的代码的json格式
 *	{
 *		linux:   //linux是linux的hostname
 *		job:     //job是要执行任务的job的filename
 *	
 *	}
 *
 *	后台返回的信息也是json格式的类型
 *		
 *
 */
@Controller
public class TaskControl {
	
	@Autowired
	private JobManagement jobManagement;
	
	@Autowired
	private LinuxManagement linuxManagement;
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
	
	
	/**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description 执行指定的job在指定的linux机器上面并返回执行结果
	 *			注解说明:@ResponseBody,用于将control方法的返回的内容转化为指定的格式如json或者xml
	 *				  @RequestBody，用于读取http请求的字符串将读取的内容转化为json或者xml格式，并将参数绑定到参数上面
	 *				  
	 */
	@RequestMapping("/datax/task/exe.do")
	public @ResponseBody String exe(@RequestBody TaskInfo taskInfo) {
		Linux linux=linuxManagement.findLinuxByHostname(taskInfo.getHostname());
		JsonFile task=jobManagement.findJobByFilename(taskInfo.getFilename());
		String message=linuxManagement.exe(linux,task);
		System.out.println(message);
		//通过ssh来执行远程的机器
		return message;
	}
	
	
}
