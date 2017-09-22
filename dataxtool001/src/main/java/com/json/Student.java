package com.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Student {
	private String usernmae="wang";
	private String password="rui";
	private Set set=new HashSet();
	private Map map=new HashMap<String, String>();
	public String getUsernmae() {
		return usernmae;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public void setUsernmae(String usernmae) {
		this.usernmae = usernmae;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
