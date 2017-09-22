package com.domain.op;

import java.io.Serializable;

/**
 * 
 * 该对象用来接收从前端传来的用于对reader进行的操作的对象
 * 前端传来的是一个json对象，后台用来接收并转化为该对象
 * @author Johnny
 *
 */
public class DataxReaderOP implements Serializable{
	
	private String readertype;//需要修改的类型
	private String readerparameter;//需要修改的参数的名字/key
	private String op;//需要进行的操作
	private String arg;//输入的value
	@Override
	public String toString() {
		return "DataxReaderOP [readertype=" + readertype + ", readerparameter=" + readerparameter + ", op=" + op
				+ ", arg=" + arg + "]";
	}

	public String getReadertype() {
		return readertype;
	}
	public void setReadertype(String readertype) {
		this.readertype = readertype;
	}
	public String getReaderparameter() {
		return readerparameter;
	}
	public void setReaderparameter(String readerparameter) {
		this.readerparameter = readerparameter;
	}
	public String getOp() {
		return op;
	}
	public String getArg() {
		return arg;
	}
	public void setArg(String arg) {
		this.arg = arg;
	}
	public void setOp(String op) {
		this.op = op;
	}

}
