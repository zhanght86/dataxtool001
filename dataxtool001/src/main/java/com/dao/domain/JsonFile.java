package com.dao.domain;
/**
 * 
 * 持久化类，映射到表上面
 * 
 * @author wang
 *
 */
public class JsonFile {
	/**
	 * 每一次前台创建的json都会有一个名字，这个名字是用来后期组装的时候通过名字来组装
	 * 
	 */
	private String filename;

	/**
	 * date里面是真正的数据，datax使用该数据来运行
	 * 
	 */
	private String data;//json格式的数据
	/**
	 * 类型是用来表示数据属于哪种类型的datax数据
	 * reader:指明该数据是reader类型的数据
	 * writer:指明该数据是writer类型的数据
	 * setting:指明该数据是setting类型的数据
	 * job:指明该数据是job类型的数据
	 * 
	 */
	private String type;
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "JsonFile [filename=" + filename + ", data=" + data + ", type=" + type + ", id=" + id + "]";
	}
}
