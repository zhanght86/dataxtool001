package com.linux;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.domain.JsonFile;
import com.dao.domain.Linux;
import com.dao.mapping.LinuxMapper;
import com.jcraft.jsch.JSch;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPOutputStream;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
@Service
public class LinuxManagement {
	private String dataxpath="/home/datax/";//datax的安装路径
	private String localFilePath="d://";//本地文件的路径
	private String linuxpath="/home/datax/job/";//远程linux中task的保存路径
	@Autowired
	private LinuxMapper linuxMapper;
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
		return this.callShellBySSH("192.168.50.165", "root", "wangrui", command);
		
		
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
	
	/**
	 * 
	 * 远程传输单个文件
	 * @param localFile
	 * @param remoteTargetDirectory
	 * @throws IOException
	 */
    public void transferFile(String localFile, String remoteTargetDirectory) throws IOException {  
        File file = new File(localFile);  
        if (file.isDirectory()) {  
            throw new RuntimeException(localFile + "  is not a file");  
        }  
        String fileName = file.getName();  
        //execCommand("cd " + remoteTargetDirectory + ";rm " + fileName + "; touch " + fileName);  
        Connection connection = new Connection("192.168.50.165");
        SCPClient scpClient = connection.createSCPClient();  
        //  传输的文件名    文件的长度   远程文件的目录   模式
        SCPOutputStream scpOutputStream = scpClient.put(fileName, file.length(), remoteTargetDirectory, "7777");  
  
       // String content = IOUtils.toString(new FileInputStream(file));  
        String content="23123";
        byte[] bytes=content.getBytes("utf-8");
        scpOutputStream.write(bytes);  
        scpOutputStream.flush();  
        scpOutputStream.close();  
    }
    /**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description 查询所有的linux
	 *
	 */
	public List<Linux> findAllLinuxs() {
		List<Linux> linuxs=linuxMapper.selectAllLinux();
		return linuxs;
	}

	public void saveLinux(Linux linux) {
		linuxMapper.saveLinux(linux);
	}
	public List<String> findAllLinuxsName() {
		List<Linux> linuxs=this.findAllLinuxs();
		List<String> names=new LinkedList<String>();
		for(int i=0;i<linuxs.size();i++) {
			Linux linux=linuxs.get(i);
			names.add(linux.getHostname());
		}
		return names;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.17
	 *@description 根据主机名来查找对应的linux主机
	 *
	 */
	public Linux findLinuxByHostname(String hostname) {
		Linux linux=linuxMapper.selectLinuxByHostname(hostname);
		return linux;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.19 下午1:45:24
	 *@description 调用指定的机器，执行指定的任务
	 *
	 */
	public String exe(Linux linux, JsonFile task) {

		//保存文件再本地
		saveTaskToLocalFile(task,localFilePath);
		//从本地传到指定的linux机
		sendFileToLinuxBySFTP(localFilePath,task.getFilename(),linux,linuxpath);
		
		String command=generateCommand(task.getFilename());
		//执行命令通过ssh
		String message=callShellBySSH(linux,command);
		return message;
	}
	private void sendFileToLinuxBySFTP(String path, String filename, Linux linux, String linuxpath) {
		String sPath=path+filename+".json";
		
		linux.sshSftp(sPath, linuxpath);
		
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.19 下午2:10:12
	 *@description 生成command命令
	 *
	 */
	private String generateCommand(String filename) {
		String c1=dataxpath+"bin/"+"datax.py    ";
		String c2=linuxpath+filename+".json";
		String command=c1+c2;
		return command;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.19 下午2:07:25
	 *@description 执行shell脚本通过ssh
	 *
	 */
	private String callShellBySSH(Linux linux,String command) {
		
		//StringBuffer sb=linuxManagement.callShellBySSH(hostname, linux.getUsername(), linux.getPassword(), command);
		//return sb.toString();
		String message=this.callShellBySSH(linux.getHostname(), linux.getUsername(), linux.getPassword(), command).toString();
		return message;
	}

	/**
	 *@ahthor wang
	 *@date  2017.10.19 下午1:50:58
	 *@description 将任务task保存为一个文件到指定的路径
	 *
	 */
	private boolean saveTaskToLocalFile(JsonFile task,String path) {
		String res=task.getData();
		String filePath=path+"/"+task.getFilename()+".json";
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
                File distFile = new File(filePath);
                if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
                bufferedReader = new BufferedReader(new StringReader(res));
                bufferedWriter = new BufferedWriter(new FileWriter(distFile));
                char buf[] = new char[1024];         //字符缓冲区
                int len;
                while ((len = bufferedReader.read(buf)) != -1) {
                        bufferedWriter.write(buf, 0, len);
                }
                bufferedWriter.flush();
                bufferedReader.close();
                bufferedWriter.close();
        } catch (IOException e) {
                e.printStackTrace();
                flag = false;
                return flag;
        } finally {
                if (bufferedReader != null) {
                        try {
                                bufferedReader.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
        return flag; 
		
	}
    
    

	
        
}
