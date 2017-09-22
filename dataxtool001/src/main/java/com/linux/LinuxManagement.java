package com.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
@Service
public class LinuxManagement {
	public StringBuffer callShell(String command) {
		//process对象是jvm开启的一个子进程，通过该对象获得进程的信息
        Process process = null;
		BufferedReader read=new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer stringBuffer=new StringBuffer();
			try {
				process = Runtime.getRuntime().exec(command);
				process.waitFor();//阻塞，等待脚本执行完
				String temp=null;
				while ((temp = read.readLine()) != null)  
	            {  
	                   stringBuffer.append(temp);  
	                    stringBuffer.append("\n");  
	            }  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

     
            return stringBuffer;

	}
	/*8
	 * 执行默认的命令
	 * 
	 */
	public StringBuffer callDefaultShell() {
		String command="/home/datax/bin/datax.py /home/datax/job/job.json";
		//StringBuffer sb=this.callShell(command);
		return this.callShellBySSH("192.168.100.165", "root", "wangrui", command);
		
		
	}
	
	public StringBuffer callShellBySSH(String hostname,String username,String password,String command) {
		Connection conn = new Connection(hostname);
		StringBuffer sb=new StringBuffer();
		try {
			conn.connect();
			boolean isAuthenticated=conn.authenticateWithPassword(username, password);
			if(isAuthenticated==false) {
				throw new IOException("登录失败");
			}
			Session session=conn.openSession();
			session.execCommand(command);
			InputStream in=new StreamGobbler(session.getStdout());
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			while(true) {
				String line=br.readLine();
				if(line==null) {
					break;
				}
				sb.append(line+"<br/>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb;
		
	}

        
}
