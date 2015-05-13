package com.cdk.ats.web.sys;
 
import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.cdk.config.fileload.Log4jFileLoad;
import common.cdk.config.fileload.MsgFileLoad;
import common.cdk.config.fileload.SqlFileLoad;
import common.cdk.config.fileload.WebAppFileLoad;
import common.cdk.login.connections.ConnectionControl;
import common.filethread.file.FilePojo;
import common.filethread.thread.FileThread;

public class AppStartupListener implements ServletContextListener{
	 
	public static final   String 	log4j="log4jPath";
	public static final   String 	msgconfigpath="msgConfigPath";
	public static final   String 	sqlconfigpath="sqlConfigPath";
	public  static final  String   webAppConfigPath="webAppConfigPath";
	//private static String LecensePathName = "lecensePath";
	//private static String LecenseDefaultPath = "WEB-INF/classes/webAppKey.pak";
	private static String SessionMaxInactiveInterval_Name = "SessionMaxInactiveInterval";
	
	private Log log=LogFactory.getLog(getClass());
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void contextInitialized(ServletContextEvent arg0) {

		//Object  l4j=this.getServletContext().getInitParameter((log4j);
		String  log4jPath=arg0.getServletContext().getInitParameter(log4j);
		String  msgpath=arg0.getServletContext().getInitParameter(msgconfigpath);
		String  sqlpath=arg0.getServletContext().getInitParameter(sqlconfigpath);
		String webappath=arg0.getServletContext().getInitParameter(webAppConfigPath);
		
		//String lecensePath = arg0.getServletContext().getInitParameter(LecensePathName);
		String maxTime = arg0.getServletContext().getInitParameter(SessionMaxInactiveInterval_Name);
		//设置用户最大使用数量 
		//ConnectionControl.setConnectionNumber(100);
		//启动监听线程
			if(log4jPath!=null&&!log4jPath.equals("")){
				FilePojo logFilepojo=new FilePojo();
				logFilepojo.setLoadSwitch(1);//required ,if you need up to date,must set 1
				logFilepojo.setFilePath(FileThread.getProject_realPath()+log4jPath); //set file absolute path
				//System.out.println(logFilepojo.getFilePath());
				logFilepojo.setDataprocess(new Log4jFileLoad());//required ,
				logFilepojo.setFileName("log4jProperties");
				logFilepojo.setFileType("properties");
				FileThread.addFilePojo(logFilepojo)	;	
			}
			if(msgpath!=null&&!msgpath.equals("")){
				FilePojo msgFilepojo=new FilePojo();
				msgFilepojo.setLoadSwitch(1);//required ,if you need up to date,must set 1
				msgFilepojo.setFilePath(FileThread.getProject_realPath()+msgpath); //set file absolute path
				msgFilepojo.setDataprocess(new MsgFileLoad());//required ,
				msgFilepojo.setFileName("MsgConfig");
				msgFilepojo.setFileType("properties");
				FileThread.addFilePojo(msgFilepojo)	;	
			}
			if(sqlpath!=null&&!sqlpath.equals("")){
				FilePojo sqlFilepojo=new FilePojo();
				sqlFilepojo.setLoadSwitch(1);//required ,if you need up to date,must set 1
				sqlFilepojo.setFilePath(FileThread.getProject_realPath()+sqlpath); //required ,set file absolute path
				sqlFilepojo.setDataprocess(new SqlFileLoad());//required ,
				sqlFilepojo.setFileName("SQLConfig");
				sqlFilepojo.setFileType("properties");
				FileThread.addFilePojo(sqlFilepojo);
			}
			if(webappath!=null&&!webappath.equals("")) {
				FilePojo webAppFilepojo=new FilePojo();
				webAppFilepojo.setLoadSwitch(1);//required ,if you need up to date,must set 1
				webAppFilepojo.setFilePath(FileThread.getProject_realPath()+webappath); //required ,set file absolute path
				webAppFilepojo.setDataprocess(new WebAppFileLoad());//required ,
				webAppFilepojo.setFileName("webApp");
				webAppFilepojo.setFileType("properties");
				FileThread.addFilePojo(webAppFilepojo);
				
			}
			
			

			if (maxTime != null && !maxTime.trim().equals("")&& NumberUtils.isNumber(maxTime)) {
				ConnectionControl.setConnectionNumber(NumberUtils.createInteger(maxTime));
			}

			createStrutsTempDir(arg0);
	
		
	}

/***+
 * 
* 
* @Description: 创建 struts临时文件上传路径
* @author 陈定凯 
* @date 2015年5月12日 下午11:09:25  
* @param arg0
 */
	private void createStrutsTempDir(ServletContextEvent arg0){
		String tempPath=arg0.getServletContext().getRealPath("/")+File.separator+"templates";
		log.warn("struts临时文件上传路径 ："+tempPath);
		File temp=new File(tempPath);
		if(!temp.exists()){
			temp.mkdirs();
			log.warn("创建 struts临时文件上传路径 ："+tempPath);
		}
		
	}
	 
	 
	

}
