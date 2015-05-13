package com.cdk.ats.web.sys;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.math.NumberUtils;

import common.cdk.config.fileload.Log4jFileLoad;
import common.cdk.config.fileload.MsgFileLoad;
import common.cdk.config.fileload.SqlFileLoad;
import common.cdk.config.fileload.WebAppFileLoad;
import common.cdk.login.connections.ConnectionControl;
import common.filethread.file.FilePojo;
import common.filethread.thread.FileThread;

public class AppStartup extends HttpServlet {

	private static final long serialVersionUID = -4313003748340626251L;
	private static String 	log4j="log4jPath";
	private static String 	msgconfigpath="msgConfigPath";
	private static String 	sqlconfigpath="sqlConfigPath";
	private static String   webAppConfigPath="webAppConfigPath";
	//private static String LecensePathName = "lecensePath";
	//private static String LecenseDefaultPath = "WEB-INF/classes/webAppKey.pak";
	private static String SessionMaxInactiveInterval_Name = "SessionMaxInactiveInterval";
	public void destroy() {
	}

	public void init() throws ServletException {
		//Object  l4j=this.getServletContext().getInitParameter((log4j);
		String  log4jPath=this.getServletConfig().getInitParameter(log4j);
		String  msgpath=this.getServletConfig().getInitParameter(msgconfigpath);
		String  sqlpath=this.getServletConfig().getInitParameter(sqlconfigpath);
		String webappath=this.getServletConfig().getInitParameter(webAppConfigPath);
		
		//String lecensePath = this.getServletConfig().getInitParameter(LecensePathName);
		String maxTime = this.getServletConfig().getInitParameter(SessionMaxInactiveInterval_Name);
		//设置用户最大使用数量 
		ConnectionControl.setConnectionNumber(100);
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

			// 启动监听线程
		/*	if (lecensePath != null && !lecensePath.equals("")) {
				LecenseDefaultPath = lecensePath.trim();
			}
			FilePojo msgFilepojo = new FilePojo();
			msgFilepojo.setLoadSwitch(1);// required ,if you need up to date,must
											// set 1
			msgFilepojo.setFilePath(FileThread.getProject_realPath()+ LecenseDefaultPath); // set file absolute path
			msgFilepojo.setDataprocess(new ConnectionLoad());// required ,
			msgFilepojo.setFileName("ConnectionControl");
			msgFilepojo.setFileType("pak");
			FileThread.addFilePojo(msgFilepojo);*/
			 
	}
}
