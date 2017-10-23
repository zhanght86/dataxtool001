package com.control;

import java.util.List;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dao.domain.Linux;
import com.domain.op.DataxReaderOP;
import com.domain.op.LinuxConnction;
import com.json.JsonManagement;
import com.linux.LinuxManagement;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javafx.scene.transform.Translate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 用于处理对linux的请求
 * @author Johnny
 *
 */
@Controller	
public class LinuxControl {
	@Autowired
	private LinuxManagement linuxManagement;
	/**
	 * 连接linux
	 * 该命令不会每一次都直接连接，而是在发送命令之后才会连接
	 * @param linuxConnction
	 * @return
	 */
	@RequestMapping("/datax/linux/connection.do")
	public @ResponseBody String processConnectionLinuxBySSH(@RequestBody LinuxConnction linuxConnction,HttpSession session) {//springmvc自动解析参数成为一个对象
/*		Linux linux=new Linux();
		linux.setIp(linuxConnction.getIp());
		linux.setUsername(linuxConnction.getUsername());
		linux.setPassword(linuxConnction.getPassword());
		System.out.println("linux");
		session.setAttribute("linux", linux);
		JSONObject linuxJson=JSONObject.fromObject(linux);
		return linuxJson.toString();*/
		return null;
	}
	@RequestMapping("/datax/linux/addlinux.do")
	public @ResponseBody String addLinux(HttpServletRequest request) {
		String hostname=request.getParameter("hostname");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		Linux linux=new Linux();
		linux.setHostname(hostname);
		linux.setUsername(username);
		linux.setPassword(password);
		linuxManagement.saveLinux(linux);
		return "success";
	}
	
	
	@RequestMapping("/datax/linux/findalllinux.do")
	public @ResponseBody String findAllLinux() {
		List<Linux> linuxs=linuxManagement.findAllLinuxs();
		JSONArray jsonArray=translate(linuxs);
		return jsonArray.toString();
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description 查询所有的linux的名字并返回
	 *
	 */
	@RequestMapping("/datax/linux/findalllinuxnames.do")
	public @ResponseBody String findAllLinuxName() {
		
		List<String> names=linuxManagement.findAllLinuxsName();
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
	/**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description 将后台的linux转化为前台需要的json
	 *
	 */
	public JSONObject translate(Linux linux) {
		JSONObject jsonObject=JSONObject.fromObject(linux);
		return jsonObject;
	}
	
	/**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description
	 *
	 */
	public JSONArray translate(List<Linux> linuxs) {
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i<linuxs.size();i++) {
			Linux linux=linuxs.get(i);
			JSONObject jsonObject=translate(linux);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}
