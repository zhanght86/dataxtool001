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
	/**
	 * ִ��Ĭ�ϵ����/home/datax/bin/datax.py /home/datax/job/job.json
	 * 
	 */
	public StringBuffer defaultExe() {
		return lm.callDefaultShell();//Ĭ�ϵ�����
		
	}
	public ReaderManagement getReaderManagement() {
		return null;
	}

	/*
	 * 根据文件名查询一个对应的reader，并且返回标准的前台格式
	 * 
	 */
/*	public JSONObject findReaderByFilename(String filename) {
		
		JSONObject jsonObject=readerManagement.findReaderByFilename(filename);
		return readerManagement.translateReaderJson(jsonObject);
	}*/

	/**
	 * 
	 * 
	 * 删除指定文件名的文件
	 * @param filename
	 */
	public void deleteFileByFilename_001(String filename) {
		String url=Configuration.settingurl;
		filename=url+filename;
		jsonManagement.DeleteFolder(filename);
	}
	public void deleteWriterFileByFilename(String filename) {
		writerManagement.deleteFileByFilename(filename);
	}
	
	/**
	 * 
	 * 得到所有的类型为reader的json，返回的时候会返回reader和total
	 * 这里的reader是前台格式的reader
	 *  	{ 
			"name":"streamreader",
			"filename":"reader1",
			"type":"reader"
			}
			
			
	 * @param page
	 * @param rows
	 * @return
	 */
/*	public JSONObject findAllReader(int page, int rows) {
		List<JSONObject> readers=readerManagement.findAllReaders();
		//JSONObject result=readerManagement.processReadersToResult(readers);
		return null;
	}*/
	/**
	 * 
	 * 查询所有的writer
	 * @param page
	 * @param rows
	 * @return
	 */
	public JSONObject findAllWriter(int page, int rows) {
		List wirters=writerManagement.findAllWriters();
		JSONObject result=processWritersToResult(wirters);
		return result;
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

	public void addWriter(JSONObject writerJson) {
		String url=Configuration.writerurl;
		writerManagement.saveWriter(writerJson,url);
		
	}
	public JSONObject findWriterByFilename(String filename) {
		JSONObject jsonObject=writerManagement.findWriterByFilename(filename);
		return writerManagement.translateWriterJson(jsonObject);

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
	 * 得到所有的setting的配置
	 * 
	 */
	public List<JSONObject> findAllSetting() {
		return settingManagement.findAllSetting();
		
	}
	/**
	 * 
	 * 根据文件名查询setting
	 * 查找不到返回为null
	 * @param filename
	 * @return
	 */
	public JSONObject findSettingByFilename(String filename) {
		return settingManagement.findSettingByFilename(filename);
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
	 * 
	 * 组装job任务
	 * @param reader
	 * @param writer
	 * @param setting
	 */
/*	public void pg(String reader, String writer, String setting) {
		JSONObject readerjson=readerManagement.findReaderByFilename(reader);
		JSONObject writerjson=writerManagement.findWriterByFilename(writer);
		JSONObject settingjson=settingManagement.findPrimarySettingByFilename(setting);
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("reader", readerjson.get("data"));
		JSONObject w1=new JSONObject();
		jsonObject.put("writer", writerjson.get("data"));
		JSONArray content=new JSONArray();
		content.add(jsonObject);
		
		
		JSONObject job=new JSONObject();
		job.put("setting", settingjson.get("data"));
		job.put("content", content);
		
		FileJson fileJson=new FileJson();
		fileJson.setData(job.toString());
		fileJson.setDescription("");
		fileJson.setFilename("job.json");
		jsonManagement.saveFile(Configuration.joburl, fileJson);
	}*/
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
