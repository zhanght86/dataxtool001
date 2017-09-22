package com.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import com.dataxmanagement.DataxManagement;
import com.job.ReaderManagement;
import com.json.JsonManagement;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public class ServiceManagement {

	private DataxManagement dm=new DataxManagement();
	
	private JsonManagement jm=new JsonManagement();
	public void createjob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json=dm.generateDefaultJob();
		String s=jm.formatJson(json.toString());
		System.out.println(s);
		response.getWriter().write(s);
		
	
	}
	/**
	 * 
	 * ִ��linux����
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void exe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer sb=dm.defaultExe();
		String s=sb.toString();
		request.setAttribute("s", s);
		request.getRequestDispatcher("dataxjsp/result/result.jsp").forward(request, response);;
	}

	/**
	 *	用于更新reader的值
	 * 
	 */
	public void updateReader(HttpServletRequest request, HttpServletResponse response) {
		//获取前台表单的数据
		String arg=request.getParameter("arg");
		String readertype=request.getParameter("readertype");
		String readerparameter=request.getParameter("readerparameter");
		String op=request.getParameter("op");
		JSONObject json=dm.generateDefaultJob();
		//修改json
		dm.updateReader(readerparameter, arg, json);
		//将json格式转化为key-value格式
		JSONObject table=jm.translateJsonObjToTable(json);
		JSONObject result=new JSONObject();
		//返回结果
		result.put("table", table);
		result.put("json", json);
		//String s=jm.formatJson(json.toString());
		try {
			PrintWriter pw =response.getWriter();
			pw.print(result.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
