package com.json;

import java.util.LinkedList;
import java.util.List;

import com.domain.Linux;

public class Configuration {
	public static String settingurl="d://json/setting/";
	public static String readerurl="d://json/reader/";
	public static String writerurl="d://json/writer/";
	public static String joburl="d://json/job/";
	public static List<Linux> linuxs=new LinkedList<Linux>();
	static {
		Linux linux=new Linux();
		linux.setIp("192.168.100.165");
		linux.setUsername("root");
		linux.setPassword("root");
		linuxs.add(linux);
	}
	public static Linux getlinux() {
		return linuxs.get(0);
	}
}
