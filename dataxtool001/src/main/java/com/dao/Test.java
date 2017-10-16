package com.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.domain.JsonFile;
import com.dao.mapping.JsonDao;
import com.dao.mapping.JsonFileMapper;
import com.dao.mapping.Service;
import com.json.JsonManagement;
import com.linux.LinuxManagement;

import net.sf.json.JSONObject;
public class Test {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		JsonFileMapper mapper=(JsonFileMapper) context.getBean("jsonFileMapper");
		
/*		JsonManagement jsonManagement=new JsonManagement();
		JSONObject jsonObject=jsonManagement.parseJsonFileToJsonObject("");
		JsonFile jsonFile=new JsonFile();
		jsonFile.setData(jsonObject.toString());
		jsonFile.setFilename("job1");
		jsonFile.setType("job");
	
		mapper.saveJson(jsonFile);*/
		JsonFile job=mapper.getJsonFile(21);
		
	}
}
