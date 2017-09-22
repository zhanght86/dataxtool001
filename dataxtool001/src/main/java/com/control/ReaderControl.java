package com.control;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.LineListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataxmanagement.DataxManagement;
import com.oracle.jrockit.jfr.RequestableEvent;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import net.sf.json.JSONObject;
@Controller
public class ReaderControl {
	@Autowired
	private DataxManagement dataxManagement;
	/**
	 * 
	 * addStreamReader
	 * @return
	 */
	@RequestMapping("/datax/job/reader/addstreamreader.do")
	public @ResponseBody String addStreamReader(@RequestParam("reader") String reader) {
		JSONObject readerJson=JSONObject.fromObject(reader);
		//保存一个json对象
		dataxManagement.addReader(readerJson);
		return "";
	}
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
		JSONObject result=dataxManagement.findAllReader(page,rows);
		return result.toString();
	}
	
	/**
	 * 前台根据文件名字取得前台格式的jsonreader
	 * 
	 */
	@RequestMapping("/datax/job/reader/findreaderbyfilename.do")
	public @ResponseBody String findReaderByFilename(HttpServletRequest request) {
		String filename= request.getParameter("filename");
		JSONObject jsonObject=dataxManagement.findReaderByFilename(filename);
		return jsonObject.toString();
	}
}
