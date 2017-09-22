package com.domain.job.reader;

import java.util.LinkedList;

public class StreamReader {
	//每一个stream对象都是一个文件
	private String filename="";
	private LinkedList<Row> rows=null;
	private String name=null;
	private String sliceRecordCourt;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSliceRecordCourt() {
		return sliceRecordCourt;
	}
	public void setSliceRecordCourt(String sliceRecordCourt) {
		this.sliceRecordCourt = sliceRecordCourt;
	}
}
