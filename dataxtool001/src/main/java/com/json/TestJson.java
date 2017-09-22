package com.json;

import com.dataxmanagement.DataxManagement;
import net.sf.json.JSONObject;
public class TestJson {
	public static void main(String[] args) {
		DataxManagement dm=new DataxManagement();
		JsonManagement jm=new JsonManagement();
		JSONObject jsonObject=dm.generateDefaultJob();
		JSONObject reader=(JSONObject) jm.findJSONByKey("reader", jsonObject, false);
		jm.translateJsonObjToTable(reader);		
		//System.out.println(column.toString());
		System.out.println(JsonManagement.formatJson(jsonObject.toString()));
	}
}
