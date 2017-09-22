package com.domain;
/**
 * 
 * 每一台机器都会被表现为一个对象，并且存在在session中
 * @author wang
 *
 */
public class Linux {
	private String ip;
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
