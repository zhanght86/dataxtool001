package com.dataxmanagement;



import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.job.JobConfigurationManagement;
import com.job.ReaderManagement;
import com.json.JsonManagement;
import com.linux.LinuxManagement;

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
	private JobConfigurationManagement jcm=new JobConfigurationManagement();
	@Autowired
	private LinuxManagement lm=new LinuxManagement();
	@Autowired
	private JsonManagement jsonManagement;
	public JSONObject generateDefaultReader() {
		ReaderManagement readerManagement=new ReaderManagement();
		JSONObject reader= readerManagement.generateDefaultReader();
		return reader;
	}
	public JSONObject generateDefaultJob() {
		JSONObject json=jcm.generateDefaultConfiguration();
		return json;
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

	public void updateReader(String name, String value, JSONObject json) {
		jcm.updateReader(name,value,json);
		
	}
	/**
	 * 
	 * 保存添加的reader
	 * @param readerJson
	 * @return
	 */
	public void addReader(JSONObject readerJson) {
		ReaderManagement readerManagement=jcm.findReaderManagement();
		String url="d://json//";
		readerManagement.saveReader(readerJson,url);
		//jsonManagement.JSONToFile(readerJson, url);
	}
	/**
	 * 
	 * 得到所有的reader，返回的时候会返回reader和total
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
	public JSONObject findAllReader(int page, int rows) {
		ReaderManagement readerManagement=jcm.findReaderManagement();
		List readers=readerManagement.findAllReaders();
		JSONObject result=processReadersToResult(readers);
		return result;
	}
	/**
	 * 
	 * 将标准格式的json转化为前端需要的格式
	 * 	 	{ 
			"name":"streamreader",
			"filename":"reader1",
 			"type":"reader"
 			}
	 * @param readers
	 * @return
	 */
	private JSONObject processReadersToResult(List readers) {
		
		JSONObject result=new JSONObject();
		
		JSONArray rows=new JSONArray();
		
		for(int i=0;i<readers.size();i++) {
			JSONObject reader=(JSONObject) readers.get(i);
			JSONObject row=new JSONObject();
			String name=reader.getJSONObject("data").getString("name");
			String filename=reader.getString("filename");
			String type="reader";
			row.put("name", name);
			row.put("filename", filename);
			row.put("type", type);
			rows.add(row);	
		}
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
		
	}
	/*
	 * 根据文件名查询一个对应的reader，并且返回标准的前台格式
	 * 
	 */
	public JSONObject findReaderByFilename(String filename) {
		ReaderManagement readerManagement=jcm.findReaderManagement();
		JSONObject jsonObject=readerManagement.findReaderByFilename(filename);
		return readerManagement.translateReaderJson(jsonObject);

	
		
	}

	
}
