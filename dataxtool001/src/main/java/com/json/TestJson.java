package com.json;


public class TestJson {
	public static void main(String[] args) {
		JsonManagement jsonManagement=new JsonManagement();
		jsonManagement.saveFile("d://json/", "test.json", "hahah", "第一个json");
	}
}
