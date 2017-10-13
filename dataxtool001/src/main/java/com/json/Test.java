package com.json;

import com.alibaba.fastjson.parser.JSONReaderScanner;

public class Test {
	public static void main(String[] args) {
		JsonManagement jsonManagement=new JsonManagement();
		jsonManagement.saveFileData("d://json/job/", "job.json", "d://json/", "data.json");
	}
}
