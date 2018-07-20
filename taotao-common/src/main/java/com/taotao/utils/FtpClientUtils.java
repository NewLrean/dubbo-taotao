package com.taotao.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

public class FtpClientUtils {
	private static FTPClient ftpclient=null;
	private static String path=null;
	public static boolean setByFtp(String address,int prot,String usern,String passw,String basePath,String imgpath,String fileName,InputStream is){
	ftpclient=new FTPClient();
	boolean flag=false;
	try {
		//创建一个ftp连接,包括 ftp服务器 ip地址，端口号
		ftpclient.connect(address,prot);
		//登录ftp服务器   ftp用户名，密码
		ftpclient.login(usern, passw);
		
		if(!FTPReply.isPositiveCompletion(ftpclient.getReplyCode())){
			ftpclient.disconnect();
			return flag;
		}
		//登陆成功开始上传
		//判断有无当天的文件夹
		
		path=basePath+"/"+imgpath;
		
		//找不到指定路径
		if(!ftpclient.changeWorkingDirectory(path)){
			ftpclient.makeDirectory(path);
		}
		
		//指定服务器路径
		ftpclient.changeWorkingDirectory(path);
		ftpclient.enterRemotePassiveMode();
		ftpclient.setBufferSize(1024);
		ftpclient.setControlEncoding("utf-8");
		ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
		//上传文件  参数一：文件名  ，参数二：读文件的流
		if(!ftpclient.storeFile(fileName, is)){
			return flag;
		}
		flag=true;
		//关闭连接
		is.close();
		ftpclient.logout();
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		if(ftpclient.isConnected()){
			try {
				ftpclient.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	return flag;
}
	
	
	public static boolean downLoadFile(String remotePath,String fileName,String basePath,OutputStream os){
	boolean flag=false;
	ftpclient=new FTPClient();
	//创建一个ftp连接,包括 ftp服务器 ip地址，端口号
			try {
				ftpclient.connect("192.168.25.150",21);
				//登录ftp服务器   ftp用户名，密码
				ftpclient.login("ftpuser", "123456");
				
				if(!FTPReply.isPositiveCompletion(ftpclient.getReplyCode())){
					ftpclient.disconnect();
					return flag;
				}
				
				path=basePath+"/"+remotePath;
				
				ftpclient.changeWorkingDirectory(path);
				FTPFile[] listFiles = ftpclient.listFiles();
				for (FTPFile ftpFile : listFiles) {
					if(ftpFile.getName().equals(fileName)){
						ftpclient.retrieveFile(ftpFile.getName(), os);
						os.close();
					}
				}
				ftpclient.logout();
				flag=true;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					ftpclient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	return flag;
	}
}
