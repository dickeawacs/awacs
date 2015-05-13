package common.cdk.config.init;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import common.cdk.config.fileload.Log4jFileLoad;
import common.cdk.config.fileload.MsgFileLoad;
import common.cdk.config.fileload.SqlFileLoad;
import common.cdk.config.fileload.WebAppFileLoad;
import common.filethread.file.FilePojo;
import common.filethread.thread.FileThread;

public class ConfigInit extends HttpServlet {
	private static final long serialVersionUID = 8789394424045553034L;
	private static String 	log4j="log4jPath";
	private static String 	msgconfigpath="msgConfigPath";
	private static String 	sqlconfigpath="sqlConfigPath";
	private static String   webAppConfigPath="webAppConfigPath";
	public ConfigInit() {
		super();
	}
	public void destroy() {
		super.destroy();  
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
		this.doGet(request, response);
	}
	
	
 
	public void init() throws ServletException { 
		//Object  l4j=this.getServletContext().getInitParameter((log4j);
		String  log4jPath=this.getServletConfig().getInitParameter(log4j);
		String  msgpath=this.getServletConfig().getInitParameter(msgconfigpath);
		String  sqlpath=this.getServletConfig().getInitParameter(sqlconfigpath);
		String webappath=this.getServletConfig().getInitParameter(webAppConfigPath);
		
		//启动监听线程
			if(log4jPath!=null&&!log4jPath.equals("")){
				FilePojo logFilepojo=new FilePojo();
				logFilepojo.setLoadSwitch(1);//required ,if you need up to date,must set 1
				logFilepojo.setFilePath(FileThread.getProject_realPath()+log4jPath); //set file absolute path
				System.out.println(logFilepojo.getFilePath());
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
			 
	}

}
