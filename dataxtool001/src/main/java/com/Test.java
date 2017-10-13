package com;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import ch.ethz.ssh2.SCPOutputStream;



public class Test {
	public static void main(String[] args) {
        File file = new File("d://json//job/job.json");  
        if (file.isDirectory()) {  
            throw new RuntimeException(  "  is not a file");  
        }  
        String fileName = file.getName();  
        //  传输的文件名    文件的长度   远程文件的目录   模式
        SCPOutputStream scpOutputStream;
		try {
			 Connection connection = new Connection("192.168.50.165");
			 connection.connect(); 
			
				boolean isAuthenticated=connection.authenticateWithPassword("root", "wangrui");
				if(isAuthenticated==false) {
					throw new IOException("登录失败");
				}
			 SCPClient scpClient = connection.createSCPClient(); 
			scpOutputStream = scpClient.put("job.json", 1000, "/home/testjob/", "0610");
	        String content="wangrui";
	        byte[] bytes=content.getBytes();
	        scpOutputStream.write(bytes);  
	        scpOutputStream.flush();  
	       
	        //scpOutputStream.close();  
	        connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  
       // String content = IOUtils.toString(new FileInputStream(file));  

		
	}
}