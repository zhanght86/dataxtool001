package com.json;
/**
 * 用来存放遍历的结果
 * @author wang
 *
 */

import java.util.HashMap;

public class Result {
	/**
	 * 每次遍历之后都会用一个map装入遍历得到的节点
	 * 
	 */
	private HashMap<String, Object> jsonmap=new HashMap<String, Object>();

	public HashMap<String, Object> getJsonmap() {
		return jsonmap;
	}

	public void setJsonmap(HashMap<String, Object> jsonmap) {
		this.jsonmap = jsonmap;
	}

	public void addJson(String key,Object json) {
		this.jsonmap.put(key, json);
		
	}
	
	
}
