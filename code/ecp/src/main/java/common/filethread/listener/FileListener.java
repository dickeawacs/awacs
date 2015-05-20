package common.filethread.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import common.filethread.thread.FileThread;
/***
 * 
 * @author cdk
 * file thread listener,use to  start thread
 */
public class FileListener implements ServletContextListener {
	private String param1Name="cycle_length";//刷新间隔时间长度
	public void contextDestroyed(ServletContextEvent arg0) {
			 FileThread.setFileThreadSwitch(0);
	}
	public void contextInitialized(ServletContextEvent event) {
		try {
			String clen= event.getServletContext().getInitParameter(param1Name);
			if(clen!=null) {
			Integer  tcl=new Integer(clen);
			FileThread.setCycle_length(tcl.intValue());
			System.out.println(" set cycle length suceess!"+FileThread.getCycle_length());
			}
		}catch (Exception e) {
			System.out.println(" set cycle length failed!");
		}
		FileThread filethread=new FileThread();
		FileThread.setProject_realPath(event.getServletContext().getRealPath("/"));
		FileThread.setServletContext(event.getServletContext());
		filethread.start();
		System.out.println(" filethread start suceess!");
	}
}
