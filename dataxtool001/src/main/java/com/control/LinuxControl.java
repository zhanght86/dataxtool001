package com.control;

import javax.json.Json;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.domain.Linux;
import com.domain.op.DataxReaderOP;
import com.domain.op.LinuxConnction;
import com.json.JsonManagement;
import com.linux.LinuxManagement;
import com.service.LinuxServiceManagement;

import ch.ethz.ssh2.Session;
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
	private LinuxServiceManagement linuxServiceManagement;
	/**
	 * 连接linux
	 * 该命令不会每一次都直接连接，而是在发送命令之后才会连接
	 * @param linuxConnction
	 * @return
	 */
	@RequestMapping("/datax/linux/connection.do")
	public @ResponseBody String processConnectionLinuxBySSH(@RequestBody LinuxConnction linuxConnction,HttpSession session) {//springmvc自动解析参数成为一个对象
		Linux linux=new Linux();
		linux.setIp(linuxConnction.getIp());
		linux.setUsername(linuxConnction.getUsername());
		linux.setPassword(linuxConnction.getPassword());
		System.out.println("linux");
		session.setAttribute("linux", linux);
		JSONObject linuxJson=JSONObject.fromObject(linux);
		return linuxJson.toString();
	}
	
	
	@RequestMapping("/datax/linux/findalllinux.do")
	public @ResponseBody String findAllLinux(@RequestBody String jsonarg,HttpSession session) {
		Linux linux=new Linux();
		linux.setIp("192.168.100.165");
		linux.setPassword("1");
		linux.setUsername("aaa");
		//String json=JSONObject.fromObject(linux).toString();
		JSONObject jsonObject=JSONObject.fromObject(linux);
		JSONArray array=new JSONArray();
		array.add(jsonObject);
		array.add(jsonObject);
		array.add(jsonObject);
		array.add(jsonObject);
		array.add(jsonObject);
		return array.toString();
	}
}
