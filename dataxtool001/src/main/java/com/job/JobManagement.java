package com.job;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.parser.JSONScanner;
import com.dao.domain.JsonFile;
import com.json.JsonManagement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class JobManagement {
	
	@Autowired
	private JsonManagement jsonManagement=new JsonManagement();
	@Autowired
	private ReaderManagement readerManagement;
	@Autowired
	private WriterManagement writerManagement;
	@Autowired
	private SettingManagement settingManagement;
	public List<JSONObject> findAllJobs() {
		List<JsonFile> jobJsonFiles=jsonManagement.findJsonFilesByType("job");
		//转化为前台需要的reader的格式
		List<JSONObject> jsonObjects=translateJsonFilesToJsonObjects(jobJsonFiles);
		return jsonObjects;
	}
	private List<JSONObject> translateJsonFilesToJsonObjects(List<JsonFile> jsonFiles) {
		List<JSONObject> jsonObjects=new LinkedList<JSONObject>();
		for(int i=0;i<jsonFiles.size();i++) {
			JsonFile jsonFile=jsonFiles.get(i);
			JSONObject jsonObject=translateJsonFileToJsonObject(jsonFile);
			jsonObjects.add(jsonObject);
		}
		return jsonObjects;
	}
	
	private JSONObject translateJsonFileToJsonObject(JsonFile jsonFile) {
		JSONObject jsonobject=new JSONObject();
		jsonobject.put("filename", jsonFile.getFilename());
		jsonobject.put("id", jsonFile.getId());
		jsonobject.put("type", jsonFile.getType());
		return jsonobject;
	}
	/**
	 * 
	 * 得到所有job的名字
	 * @return
	 */
	public List<String> findAllJobNames() {
		List<JSONObject> jsonObjects=findAllJobs();
		List<String> names=new LinkedList<String>();
		for(int i=0;i<jsonObjects.size();i++) {
			names.add(jsonObjects.get(i).getString("filename"));
		}
		return names;
		
	}
	
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 查询所有额reader的名字
	 *
	 */
	public List<String> findAllReaderName() {
		List<String> names=readerManagement.findAllReadersName();
		return names;
	}
	public List<String> findAllWriterName() {
		List<String> names=writerManagement.findAllWritersName();
		return names;
	}
	public List<String> findAllSettingName() {
		List<String> names=settingManagement.findAllSettingsName();
		return names;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 根据writer，reader，setting生成一个job
	 *
	 */
	public void createJob(String reader, String writer, String setting) {
		//JSONObject readerjson=readerManagement.findReaderByFilename(reader);
		//JSONObject writerjson=writerManagement.findWriterByFilename(writer);
		//JSONObject settingjson=settingManagement.findPrimarySettingByFilename(setting);
		
		JsonFile readerJsonFile=readerManagement.findReaderByFilename(reader);
		JsonFile writerJsonFile=writerManagement.findWriterByFilename(writer);
		JsonFile settingJsonFile=settingManagement.findSettingByFilename(setting);
		//生成content
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("reader", readerJsonFile.getData());
		JSONObject w1=new JSONObject();
		jsonObject.put("writer", writerJsonFile.getData());
		JSONArray content=new JSONArray();
		content.add(jsonObject);
		
		//生成job
		JSONObject job=new JSONObject();
		job.put("setting", settingJsonFile.getData());
		job.put("content", content);
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.put("job", job);//job外面其实还有一层
		
		//保存job
		jsonManagement.save("job", jsonObject2.toString(), "job");
		
	}
}
