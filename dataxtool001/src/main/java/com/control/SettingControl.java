package com.control;
import java.util.LinkedList;
import java.util.List;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import com.control.info.SettingInfo;
import com.control.info.TaskInfo;
import com.job.SettingManagement;
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
	private SettingManagement settingManagement;
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
			//result=dataxManagement.saveJsonobj(filename,rows);
			result=settingManagement.saveSetting(filename,rows);
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
	public @ResponseBody String findallsetting(HttpServletRequest request) {
		//第几页
		int page=Integer.parseInt(request.getParameter("page"));
		//一页多少数据
		int rows=Integer.parseInt(request.getParameter("rows"));
		List<JSONObject> jsonObjects=settingManagement.findAllSetting(page,rows);
		return jsonObjects.toString();
	}
	
	@RequestMapping("/datax/job/setting/deletesetting.do")
	public @ResponseBody String deletesetting(HttpServletRequest request) {
		String id=request.getParameter("id");
		int i=Integer.parseInt(id);
		settingManagement.deleteSettingById(i);
		return "";
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.23 下午1:32:08
	 *@description 装配
	 *	前台传来数据
	 *	{
	 *		name		//装配之后的名字
	 *		type:		//要装配的类型，有两种情况，jsongobj和jsonarray
	 *		rows
	 *		]
	 *	}
	 *
	 *	后台
	 *		或根据前台传来的id然后找出各个jsonFile文件，使用的是filename作为子模块的名字
	 *		之后根据type的类型决定要装配成为jsonobject还是jsonarrayarrary
	 *
	 */
	@RequestMapping("/datax/job/setting/pg.do")
	public @ResponseBody String pg(@RequestBody String arg) {
		JSONObject jsonObject=new JSONObject().fromObject(arg);
		
		JSONArray rows=jsonObject.getJSONArray("rows");
		String name=jsonObject.getString("name");
		String type=jsonObject.getString("type");
		List<Integer> ids=new LinkedList<Integer>();
		for(int i=0;i<rows.size();i++) {
			JSONObject row=rows.getJSONObject(i);
			int id=row.getInt("id");
			ids.add(id);
			
		}
		
		SettingInfo settingInfo=new SettingInfo();
		settingInfo.setName(name);
		settingInfo.setType(type);
		settingInfo.setIds(ids);

		
		settingManagement.pg(settingInfo);
		return "zhuanpeihchenggong";
	}

}
