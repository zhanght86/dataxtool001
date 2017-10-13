package com.dao;

import java.io.IOException;
import java.io.InputStream;

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
public class Test {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		JsonFileMapper mapper=(JsonFileMapper) context.getBean("jsonFileMapper");
		System.out.println(mapper.getJsonFile(1));
	}
}
