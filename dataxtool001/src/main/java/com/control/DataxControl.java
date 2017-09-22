package com.control;


import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.domain.op.DataxReaderOP;
import com.json.JsonManagement;
import com.service.DataxServiceManagement;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 用来处理datax请求的
 * @author Johnny
 * 通一个处理器方法用来处理不同的业务逻辑
 *
 */
//mvc层的控制器对象，由springmvc来管理
@Controller		//这里用control这个注解用来描述是springmvc的bean
public class DataxControl {
	//由由spring'容器来注入
	@Autowired
	private DataxServiceManagement dataxServiceManagement;
	
	/**
	 * 
	 * 将请求映射到不同的control的时候有不同的映射规则(URL,请求参数,请求方法,请求头)
	 * 
	 * @param dataxReaderOP
	 * @return
	 */
	@RequestMapping("/datax/managementdatax.do")
	public ModelAndView processDataxReaderOP(@RequestBody DataxReaderOP dataxReaderOP) {//springmvc自动解析参数成为一个对象
		//JsonObject jsonObject=dataxManagement.processReader(dataxReaderOP);
		//System.out.println(jsonObject);
		ModelAndView modelAndView=new ModelAndView("reader");
		return modelAndView;
	}

	
	
	@RequestMapping("/datax/reader/add")
	public @ResponseBody Map processDataxReaderAdd(@RequestBody DataxReaderOP dataxReaderOP) {
		//执行业务逻辑,病返回修改之后的reader对象
		Map json=dataxServiceManagement.processDataxReaderAdd(dataxReaderOP);
		return json;
	}
	
	@RequestMapping("/datax/reader/delete")
	public @ResponseBody Map processDataxReaderDelete(@RequestBody DataxReaderOP dataxReaderOP) {
		//执行业务逻辑,病返回修改之后的reader对象
		Map json=dataxServiceManagement.processDataxReaderDelete(dataxReaderOP);
		return json;
	}
	
	/**
	 * 
	 * 用于reader更新
	 * @param dataxReaderOP
	 * @return
	 */
	@RequestMapping("/datax/reader/update")
	public @ResponseBody Map processDataxReaderUpdate(@RequestBody DataxReaderOP dataxReaderOP) {
		//执行业务逻辑,病返回修改之后的reader对象
		Map json=dataxServiceManagement.processDataxReaderUpdate(dataxReaderOP);
		return json;
	}
	/**
	 * 
	 * 该对象用来查询整个reader的信息
	 * @param dataxReaderOP
	 * @return
	 */
	@RequestMapping("/datax/reader/selectall")
	public @ResponseBody Map processDataxReaderSelectall(@RequestBody DataxReaderOP dataxReaderOP) {
		//执行业务逻辑,病返回修改之后的reader对象
		Map json=dataxServiceManagement.processDataxReaderSelectAll(dataxReaderOP);
		return json;
	}
	/**
	 * 
	 * 查询指定key的值
	 * @param dataxReaderOP
	 * @return
	 */
	@RequestMapping("/datax/reader/select")
	public @ResponseBody Map processDataxReaderSelect(@RequestBody DataxReaderOP dataxReaderOP) {
		//执行业务逻辑,病返回修改之后的reader对象
		Map json=dataxServiceManagement.processDataxReaderSelect(dataxReaderOP);
		return json;
	}
	/**
	 * 
	 * 加载reader
	 */
	@RequestMapping("/datax/reader/loadreaders.do")
	public String loadReaders() {
		JSONArray jsonArray=dataxServiceManagement.getAllReaders();
		return jsonArray.toString();
	}

	
}
