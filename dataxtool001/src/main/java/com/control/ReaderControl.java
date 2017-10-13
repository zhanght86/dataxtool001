package com.control;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.set.ListOrderedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataxmanagement.DataxManagement;
import com.job.ReaderManagement;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import net.sf.json.JSONObject;
/*
 * 
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
public class ReaderControl {
	
	@Autowired
	private DataxManagement dataxManagement;
	@Autowired
	private ReaderManagement readerManagement;

	/**
	 * 前台用来加载数据
	 * 
	 * 
	 */
	@RequestMapping("/datax/job/reader/findallreader.do")
	public @ResponseBody String findAllReaders(HttpServletRequest request) {
		//第几页
		int page=Integer.parseInt(request.getParameter("page"));
		//一页多少数据
		int rows=Integer.parseInt(request.getParameter("rows"));
		List<JSONObject> jsonObjects=readerManagement.findAllReaders(page,rows);
		return jsonObjects.toString();
	}
	
	/**
	 * 前台根据文件名字取得前台格式的jsonreader
	 * 返回的结果前台需要的格式
	 * {    
		  name:'AddName',    
		  value:'',    
		  group:'Marketing Settings',    
		  editor:'text'   
		}   
		
		 返回的是经过处理之后的数据
	 * 
	 */
	@RequestMapping("/datax/job/reader/findreaderbyid.do")
	public @ResponseBody String findReaderById(HttpServletRequest request) {
		String id= request.getParameter("id");
		int i=Integer.parseInt(id);
		//JSONObject jsonObject=dataxManagement.findReaderByFilename(I);
		//return jsonObject.getJSONArray("rows").toString();
		JSONObject result=readerManagement.findReaderById(i);
		return result.get("data").toString();
	}
	
	@RequestMapping("/datax/job/reader/deletefilebyid.do")
	public @ResponseBody String deleteReaderById(HttpServletRequest request) {
		String id=request.getParameter("id");
		int i=Integer.parseInt(id);
		readerManagement.deleteReaderById(i);
		return "";
	}
	
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 添加一个reader
	 *
	 */
	@RequestMapping("/datax/job/reader/addreader")
	public @ResponseBody String addReader(@RequestParam("reader") String reader) {
		JSONObject readerJson=JSONObject.fromObject(reader);
		//保存一个json对象
		readerManagement.saveReader(readerJson);
		return "";
	}
}
