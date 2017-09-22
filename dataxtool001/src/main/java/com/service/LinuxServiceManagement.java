package com.service;
/**
 * 
 * 用于linux的业务管理的对象
 * @author Johnny
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Linux;
import com.domain.op.LinuxConnction;
import com.linux.LinuxManagement;

import net.sf.json.JSONObject;
@Service
public class LinuxServiceManagement {
	@Autowired
	private LinuxManagement linuxManagement;
	/**
	 * 
	 * 该方法主要是用来将需要连接的信息宝座在session中
	 * @param linuxConnction
	 */
	public void connect(LinuxConnction linuxConnction) {
		// TODO Auto-generated method stub
		
	}
	public String exe(Linux linux, JSONObject job) {
		String command="/home/datax/bin/datax.py /home/datax/job/job.json";
		StringBuffer sb=linuxManagement.callShellBySSH(linux.getIp(), linux.getUsername(), linux.getPassword(), command);
		return sb.toString();
	}
	

}
