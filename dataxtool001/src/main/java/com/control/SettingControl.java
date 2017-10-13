package com.control;
import java.util.LinkedList;
import java.util.List;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import com.dataxmanagement.DataxManagement;
import com.json.FileJson;

import javafx.scene.transform.Translate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/*
 * 前台请求的格式都会固定,名字都为kdata，并且是一个json格式的字符串
 * data
 * {
 * 		filename:name  //文件的名字
 * 		arg:{}			//要传的参数
 * }
 * 
 * 后台就之间将其包装为一个json对象
 * 
 */ 
@Controller
public class SettingControl {
	@Autowired
	private DataxManagement dataxManagement;
	/**
	 * 前台：
	 * 		前台会传来数据的类型type，和每一行的数据
	 * 
	 * 后台:根据前台传来的类型将数据转化为jsonarray还是jsonobj
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("/datax/job/setting/addsetting.do")
	public @ResponseBody String addsetting(@RequestParam("reader") String reader) {
		//封装前台的数据
		JSONObject jsonObject=JSONObject.fromObject(reader);
		String type=jsonObject.getString("type");
		JSONArray rows=jsonObject.getJSONArray("rows");
		String filename=jsonObject.getString("filename");
		boolean result=false;//保存的结果
		if("json".equals(type)) {
			result=dataxManagement.saveJsonobj(filename,rows);
		}
		if(result==true) {
			return "true";
		}else {
			
			return "false";
		}
	}
	/**
	 * 
	 * 
	 * 查询所有的json的配置
	 * @param reader
	 * @return
	 */
	@RequestMapping("/datax/job/setting/findallsetting.do")
	public @ResponseBody String findallsetting() {
		//得到了所有的文件
		List<JSONObject> jsonObjects=dataxManagement.findAllSetting();
		return jsonObjects.toString();
	}
	
	@RequestMapping("/datax/job/setting/deletesetting.do")
	public @ResponseBody String deletesetting(HttpServletRequest request) {
		String filename=request.getParameter("filename");
		dataxManagement.deleteFileByFilename_001(filename);
		return "";
	}
	
	/**
	 * 
	 * 前台传来需要请求的文件名
	 * 从后台获得指定名字的文件
	 * 如果没有找到则返回null
	 * 
	 * 返回到前台的格式
	 * {
	 * 	filename:
	 * 	rows:[]
	 * 	
	 * }
	 *   row都是一个对象
	 *   格式为
	    row = {    
		  name:'AddName',    
		  value:'',    
		  group:'Marketing Settings',    
		  editor:'text'  
		  } 

	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/datax/job/setting/findsetting.do")
	public @ResponseBody String findsettingbyfilename(HttpServletRequest request) {
		String filename= request.getParameter("filename");
		JSONObject jsonObject=dataxManagement.findSettingByFilename(filename);
		return jsonObject.toString();
	}
	
	/**
	 * 
	 * 拼接多个json
	 * @param request
	 * @return
	 */
		@RequestMapping("/datax/job/setting/connect.do")
		public @ResponseBody String connection(HttpServletRequest request) {
			List<String> names=new LinkedList<String>();
			JSONObject arg=JSONObject.fromObject(request.getParameter("arg"));
			String newfilename= arg.getString("newfilename");
			String type=arg.getString("type");
			JSONArray rows=arg.getJSONArray("rows");
			for(int i=0;i<rows.size();i++) {
				JSONObject row =rows.getJSONObject(i);
				names.add(row.getString("filename"));
			}
			dataxManagement.connetjson(newfilename, type, names);
			return "";
		}
}
