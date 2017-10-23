package com.linux;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Scanner;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.domain.JsonFile;
import com.dao.mapping.JsonFileMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.StreamGobbler;

public class Linux {
	private String hostname="192.168.50.165";
	private String username="root";
	private String password="wangrui";
	LinuxManagement linuxManagement=new LinuxManagement();
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		JsonFileMapper mapper=(JsonFileMapper) context.getBean("jsonFileMapper");
		JsonFile job=mapper.getJsonFile(21);
		Linux linux =new Linux();
		linux.exe();
		
	}
	
	public String exe() {
		
		//上传文件
		sshSftp(hostname, username, password, -1, "d://job13.json", "/home/datax/job/");
		//执行操作
		String command="/home/datax/bin/datax.py /home/datax/job/job13.json";
		StringBuffer sb=linuxManagement.callShellBySSH(hostname, username, password, command);
		return sb.toString();
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 将jsonFile转化为本地的文件,程序会在后面加.json的后缀
	 *
	 */
	public boolean translateJsonFileToFile(JsonFile jsonFile,String path) {
		String res=jsonFile.getData();
		String filePath=path+"/"+jsonFile.getFilename()+".json";
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
	
	
	
    /**
     * 密码方式登录 通过sftp来上传文件
     * 
     * @param ip
     * @param user
     * @param psw
     * @param port
     * @param sPath
     * @param dPath   文件的存储目录
     */
    public void sshSftp(String ip, String user, String psw, int port,
            String sPath, String dPath) {
        System.out.println("password login");
        Session session = null;

        JSch jsch = new JSch();
        try {
            if (port <= 0) {
                // 连接服务器，采用默认端口
                session = jsch.getSession(user, ip);
            } else {
                // 采用指定的端口连接服务器
                session = jsch.getSession(user, ip, port);
            }

            // 如果服务器连接不上，则抛出异常
            if (session == null) {
                throw new Exception("session is null");
            }

            // 设置登陆主机的密码
            session.setPassword(psw);// 设置密码
            // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            // 设置登陆超时时间
            session.connect(300000);
            upLoadFile(session, sPath, dPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("success");
    }
    
    public void upLoadFile(Session session, String sPath, String dPath) {

        Channel channel = null;
        try {
            channel = (Channel) session.openChannel("sftp");
            channel.connect(10000000);
            ChannelSftp sftp = (ChannelSftp) channel;
/*            try {
                sftp.cd(dPath);
                Scanner scanner = new Scanner(System.in);
                System.out.println(dPath + ":此目录已存在,文件可能会被覆盖!是否继续y/n?");
                String next = scanner.next();
                if (!next.toLowerCase().equals("y")) {
                    return;
                }

            } catch (SftpException e) {

                sftp.mkdir(dPath);
                sftp.cd(dPath);

            }*/
            File file = new File(sPath);
            copyFile(sftp, file, sftp.pwd());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }

    public void copyFile(ChannelSftp sftp, File file, String pwd) {

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            try {
                try {
                    String fileName = file.getName();
                    sftp.cd(pwd);
                    System.out.println("正在创建目录:" + sftp.pwd() + "/" + fileName);
                    sftp.mkdir(fileName);
                    System.out.println("目录创建成功:" + sftp.pwd() + "/" + fileName);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                pwd = pwd + "/" + file.getName();
                try {

                    sftp.cd(file.getName());
                } catch (SftpException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int i = 0; i < list.length; i++) {
                copyFile(sftp, list[i], pwd);
            }
        } else {

            try {
                sftp.cd(pwd);

            } catch (SftpException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("正在复制文件:" + file.getAbsolutePath());
            InputStream instream = null;
            OutputStream outstream = null;
            try {
                outstream = sftp.put(file.getName());
                instream = new FileInputStream(file);

                byte b[] = new byte[1024];
                int n;
                try {
                    while ((n = instream.read(b)) != -1) {
                        outstream.write(b, 0, n);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (SftpException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    outstream.flush();
                    outstream.close();
                    instream.close();

                } catch (Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                }
            }
        }
    }

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
