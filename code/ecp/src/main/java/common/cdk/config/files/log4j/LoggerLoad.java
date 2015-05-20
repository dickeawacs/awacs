package common.cdk.config.files.log4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.PropertyConfigurator;

import common.cdk.config.utils.folder.FileOperate;
import common.filethread.thread.FileThread;

public class LoggerLoad {
	private   ServletContext  servletContext;
	private   String log4jpath;
	private   int replaceFile=1;//if log file path is absolute ,please set 0,else set 1
	
	/***
	 * 
	 * @param servletContext
	 * @param log4jpath          log4j.properties path
	 */
	public LoggerLoad(ServletContext servletContext, String log4jpath) {
		this.servletContext = servletContext;
		this.log4jpath = log4jpath;
	}
	/****
	 * 
	 * @param servletContext
	 * @param log4jpath  log4j.properties path
	 * @param replaceFile log4j.properties path
	 */
	public LoggerLoad(ServletContext servletContext, String log4jpath,int replaceFile) {
		this.servletContext = servletContext;
		this.log4jpath = log4jpath;
		this.replaceFile=replaceFile;
	}
	
	/****
	 *  load log4j properties
	 * @param loggerpath
	 * @return
	 */
	public static boolean loadLog(String loggerpath) {
		boolean loaded = false;
		File logfile = new File(loggerpath);
		if (!logfile.exists())
			System.out.println(loggerpath + " not exists");
		else {
			InputStream is=null;
			try {
				is = new FileInputStream(loggerpath);
				Properties prop = new Properties();
				prop.load(is);
				String lp = FileThread.getProject_realPath()+ prop.getProperty("log4j.appender.file.File");
				FileOperate.createFile(lp);
				prop.setProperty("log4j.appender.file.File", lp);
				PropertyConfigurator.configure(prop);
				if(is!=null)is.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loaded;
	}
	/***
	 * load and start log4j properites
	 * @throws IOException 
	 */
	public  void startLog4j() throws IOException {
		File logfile = new File(getServletContext().getRealPath("/")+log4jpath);
		if (!logfile.exists())
			System.out.println(log4jpath + " not exists");
		else {
			InputStream is = new FileInputStream(logfile);
			Properties prop = new Properties();
			prop.load(is);
			if(replaceFile==1)
			prop.setProperty("log4j.appender.file.File", this
					.getServletContext().getRealPath("/")
					+ prop.getProperty("log4j.appender.file.File"));
			PropertyConfigurator.configure(prop);
			is.close();
		}
	}
	



	public ServletContext getServletContext() {
		return servletContext;
	}


	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	

	public String getLog4jpath() {
		return log4jpath;
	}



	public void setLog4jpath(String log4jpath) {
		this.log4jpath = log4jpath;
	}



	public int getReplaceFile() {
		return replaceFile;
	}



	public void setReplaceFile(int replaceFile) {
		this.replaceFile = replaceFile;
	}


}
