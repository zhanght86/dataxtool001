package com.control;
/**
 *@ahthor wang
 *@date  2017.10.12
 *@description
 *	前端传来的数据格式是一个键值对
 *	属性有:filename，data
 *	后台会使用jsonmanagement来保存数据
 *
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.control.info.JsonInfo;
import com.control.info.TaskInfo;
import com.dao.domain.JsonFile;
import com.json.JsonManagement;
import com.json.Result;

import net.sf.json.JSONObject;
@Controller
public class JsonControl {
	@Autowired
	private JsonManagement jsonManagement;
	/**
	 *@ahthor wang
	 *@date  2017.10.12
	 *@description
	 *	前台获取数据filename，data，并添加一个默认的type为json，然后进行保存
	 *	保存成功之后会返回success，失败则保存fail
	 *
	 */

	@RequestMapping("/datax/job/json/addjson.do")
	public @ResponseBody String addJson(HttpServletRequest request) {
		String json=request.getParameter("json");
		JSONObject jsonObject=JSONObject.fromObject(json);
		String filename=jsonObject.getString("filename");
		String data=jsonObject.getString("data");
		String type="json";//这里是默认为json
		String message=jsonManagement.save(filename,data,type);
		return "success";
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 查询所有的json
	 		返回的json对象的格式
	 		{ 
			"name":"streamreader",
			"filename":"reader1",
			"type":"reader",
			data:""
			}
	 *
	 */
	@RequestMapping("/datax/job/json/findalljson.do")
	public @ResponseBody String findalljsonfile() {
		//得到了所有的文件
		//List<JSONObject> jsonObjects=dataxManagement.findAllSetting();
		List<JSONObject> jsonObjects=jsonManagement.findAllJsonFiles();
		return jsonObjects.toString();
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 根据前台json的id删除信息
	 *
	 */
	@RequestMapping("/datax/job/json/deletejson.do")
	public @ResponseBody String deletesetting(HttpServletRequest request) {
	
		int id=Integer.parseInt(request.getParameter("id"));
		//dataxManagement.deleteFileByFilename_001(filename);
		jsonManagement.deleteJsonFileById(id);
		return "";
	}
	
	/**
	 *@ahthor wang
	 *@date  2017.10.23 上午10:32:35
	 *@description 查看json
	 *
	 */
	@RequestMapping("/datax/job/json/findjson.do")
	public @ResponseBody String showJson(@RequestBody JsonInfo JsonInfo) {
		JSONObject jsonObject=jsonManagement.findJsonObjectById(JsonInfo.getId());
		System.out.println(jsonManagement.formatJson(jsonObject.getString("data")));
		return jsonObject.toString();
	
	}


	
	
	
	
}
