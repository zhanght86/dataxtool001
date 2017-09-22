package com.domain.job.reader;
/**
 * 
 * reader对象的数据被分割成为了row
 * @author Johnny
 *
 */
public class Row {
	private String name;
	private String group;
	private String value;
	private String editon;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getEditon() {
		return editon;
	}
	public void setEditon(String editon) {
		this.editon = editon;
	}
}
