package com.json;

import java.util.HashMap;

public class Context {
	//用于遍历之后所查找的对象
	private Object jo=null;
	private Object joParent=null;
	private HashMap<String, String> tables=new HashMap<String, String>();
	public HashMap<String, String> getTables() {
		return tables;
	}

	public void setTables(HashMap<String, String> tables) {
		this.tables = tables;
	}

	public Object getJoParent() {
		return joParent;
	}

	public void setJoParent(Object joParent) {
		this.joParent = joParent;
	}

	public Object getJo() {
		return jo;
	}

	public void setJo(Object jo) {
		this.jo = jo;
	}

}
