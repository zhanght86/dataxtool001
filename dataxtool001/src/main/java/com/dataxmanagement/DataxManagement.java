package com.dataxmanagement;


import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import com.job.JobConfigurationManagement;
import com.job.JobManagement;
import com.job.ReaderManagement;
import com.job.SettingManagement;
import com.job.WriterManagement;
import com.json.Configuration;
import com.json.FileJson;
import com.json.JsonManagement;
import com.linux.LinuxManagement;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * dataxmanagement:datax管理器主要包括两个部分
 * 		jobmanagement:任务管理
 * 		linuxmanagement:linux管理，用于调用linux执行相应的操作
 * @author wang
 *
 */
@Service
public class DataxManagement {
	@Autowired
	private JobManagement jobManagement;
	@Autowired
	private ReaderManagement readerManagement;
	@Autowired
	private WriterManagement writerManagement;
	@Autowired
	private SettingManagement settingManagement;
	@Autowired
	private LinuxManagement lm=new LinuxManagement();
	@Autowired
	private JsonManagement jsonManagement;
	public JSONObject generateDefaultReader() {
		ReaderManagement readerManagement=new ReaderManagement();
		JSONObject reader= readerManagement.generateDefaultReader();
		return reader;
	}








			



	private JSONObject processWritersToResult(List writers) {
		JSONObject result=new JSONObject();
		JSONArray rows=new JSONArray();
		for(int i=0;i<writers.size();i++) {
			JSONObject reader=(JSONObject) writers.get(i);
			JSONObject row=new JSONObject();
			String name=reader.getJSONObject("data").getString("name");
			String filename=reader.getString("filename");
			String type="writer";
			row.put("name", name);
			row.put("filename", filename);
			row.put("type", type);
			rows.add(row);	
		}
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}

	/**
	 * 
	 * 该方法用来保存前台传来的数据
	 * 因为前台传来的数据和后台的数据格式不一致，所以中间还需要进行转化
	 * 
	 * @param filename
	 * @param rows
	 */
	public boolean saveJsonobj(String filename,JSONArray rows) {
		//SettingManagement settingManagement=jcm.getSettingManagement();
		JSONObject data=new JSONObject();
		//转化数据成为后台需要的json数据
		for(int i=0;i<rows.size();i++) {
			JSONObject row=rows.getJSONObject(i);
			String name=row.getString("name");
			String value=row.getString("value");
			data.put(name, value);
		}
		FileJson fileJson=new FileJson();
		fileJson.setFilename(filename);
		fileJson.setData(data.toString());
		return jsonManagement.saveFile(Configuration.settingurl, fileJson);
	}


	/**
	 * 
	 * 拼接json
	 * @param type
	 * @param names
	 */
	public void connetjson(String newFilename,String type, List<String> names) {
		jsonManagement.connection(newFilename,type,names);
	}

	/**
	 * 
	 * @param page 页面数
	 * @param rows 行号
	 * @return
	 */
/*	public List<String> findAllReaderName(int page, int rows) {
		List<String> names=readerManagement.findAllReadersName();
		
		return names;
	}*/
	public List<String> findAllWriterName(int i, int j) {
		List<String> names=writerManagement.findAllWriterName();
		return names;
	}
	public List<String> findAllSettingName(int i, int j) {
		List<String> names=settingManagement.findAllSettingName();
		return names;
	}


	/**
	 * 
	 * 查询到所有的json，并且转化为前台的格式
	 * @return
	 */
	public List<String> findAllJobNames(int page,int rows) {
		List<String> names=jobManagement.findAllJobNames();
		return names;
		
	}

	
}
