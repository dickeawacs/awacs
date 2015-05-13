package common.filethread.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import common.filethread.exception.ProcessException;
import common.filethread.file.FilePojo;
/***
 * 
 * @author cdk
 * this is file load thread ,if you reg FilePojo with fileContext
 */
public class FileThread extends Thread {
	private static Logger log = Logger.getLogger(FileThread.class);
	public  static int FileThreadSwitch = 1;//线程开关  非1为不启用,可以做为停止线程的指令
	private static List<FilePojo>  fileContext=new ArrayList<FilePojo>();
	private static String FileThreadName = "common-Config-Listener";//线程名称
	private static ServletContext servletContext;
	private static String Project_realPath=null;
	private static int  Cycle_length=5000;// ms

	public FileThread() {
		this.setName(FileThreadName);
	}
	public FileThread(String threadname) {
		this.setName(threadname);
		setFileThreadName(threadname);
	}
	@Override
	public void run() { 
		if(fileContext!=null) {
			Collections.sort(fileContext, new Comparator<FilePojo>() {
				public int compare(FilePojo o1, FilePojo o2) {
					return o1.getId()-o2.getId();
				}
			});
			
		}
		while (FileThreadSwitch == 1) {
			for ( int i=0;i<fileContext.size();i++) {
				FilePojo pojo=fileContext.get(i);
				//1、get file last modefied
				if(pojo.getLastModified()==0L) {
					pojo.setLastModified(lastModified(pojo.getFilePath()));
					if(pojo.getLastModified()!=0L) {
						try {
							pojo.getDataprocess().process(pojo);
							pojo.setLastModified(lastModified(pojo.getFilePath()));
						} catch (ProcessException e) {
							log.error("file process error:"+pojo.getFilePath());
						}
					}
				}else if(pojo.getLoadSwitch()==1&& pojo.getLastModified()!=lastModified(pojo.getFilePath())){
					try {
						pojo.getDataprocess().process(pojo);
						pojo.setLastModified(lastModified(pojo.getFilePath()));
					} catch (ProcessException e) {
						log.error("file process error:"+pojo.getFilePath());
					}
				}
			} 
			try {
				Thread.sleep(Cycle_length);
			} catch (InterruptedException e) {
				log.error("this thread sleep failed!",e);
				FileThreadSwitch=0;
			}finally{
			}
		}
	}
 
 
	public static int getFileThreadSwitch() {
		return FileThreadSwitch;
	}
	/***
	 * 
	 * @param fileThreadSwitch   0: off ,1:on
	 */
	public static void setFileThreadSwitch(int fileThreadSwitch) {
		FileThreadSwitch = fileThreadSwitch;
	}
	public static List<FilePojo> getFileContext() {
		return fileContext;
	}
	public static void setFileContext(List<FilePojo> fileContext) {
		FileThread.fileContext = fileContext;
	}
	/***
	 * 注册一个文件处理对象
	 * @param filepojo
	 */
	public static void addFilePojo(FilePojo filepojo)  {
		try {
			filepojo.getDataprocess().process(filepojo);
			filepojo.setLastModified(lastModified(filepojo.getFilePath()));
			FileThread.fileContext.add(filepojo);
		} catch (ProcessException e) { 
			log.error("call  DataProcess error",e);
		}
	}
	public static void removeFilePojo(FilePojo filepojo) {
		FileThread.fileContext.remove(filepojo);
	}
	
	public static ServletContext getServletContext() {
		return servletContext;
	}
	public static void setServletContext(ServletContext servletContext) {
		FileThread.servletContext = servletContext;
	}
	public static String getProject_realPath() {
		return Project_realPath;
	}
	public static void setProject_realPath(String projectRealPath) {
		Project_realPath = projectRealPath;
	}
	public static int getCycle_length() {
		return Cycle_length;
	}
	public static void setCycle_length(int cycleLength) {
		Cycle_length = cycleLength;
	} 
	
	
	public static String getFileThreadName() {
		return FileThreadName;
	}
	public static void setFileThreadName(String fileThreadName) {
		FileThreadName = fileThreadName;
	}
	/***
	 * 
	 * @param path
	 * @return
	 */
	private static long lastModified(String path) {
		long lastModified = 0l;
		try {
		 
			File file = new File(path);
			if (file.exists()) {
				lastModified = file.lastModified();
			} else {
				throw new FileNotFoundException("A serious error  can't found file :"+ path);
			}
		} catch (Exception e) {
			log.info("system to skip load file :"+path);
			log.info("A serious error  can't found file :"+ path);
			log.info(e);
		} finally {
			
		}
		return lastModified;
	}
	
}
