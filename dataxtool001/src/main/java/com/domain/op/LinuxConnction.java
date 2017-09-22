package com.domain.op;
/**
 * 
 * 该对象是用于封装前台传来的用于连接linux的请求的数据
 * @author wang
 *
 */
public class LinuxConnction {
	private  String ip;
	private String username;
	private String password;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
