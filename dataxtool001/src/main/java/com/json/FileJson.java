package com.json;
/**
 * 
 * 
 * 对文件格式的json的描述
 * 每次存放的数据都是这个对象，用于文件的保存
 * @author Johnny
 *  * 该对象是用来处理json，对于json的增删改查
 * 同时可以保存json格式对象到文件中
 * 文件的格式有规范的，不是原生的datax的json格式，而是自己定义的，如下
 * 文件名必须在保存的时候就需要给定，如果没有，默认为""
 * {
 * 	filename:   指定文件的文件名
 * 	description:  文件的描述
 * 	data:  文件的数据
 * }
 *
 */
public class FileJson {
	private String filename="";
	private String description="";
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	private String data="";
}
