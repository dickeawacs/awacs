package com.cdk.ats.udp.sys;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.event.EventContext;
import com.cdk.ats.udp.heart.HeartBeatCheckThread;
import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.sys.AppStartupListener;
import common.cdk.config.utils.PropertiesUtils;
import common.filethread.thread.FileThread;

/***
 * 此侦听器仅用于启动数据包接收线程和发送线程
 * 
 * @author cdk
 * 
 */
public class UdpListener implements ServletContextListener {
	private static Logger logger=Logger.getLogger(UdpListener.class);
	public void contextDestroyed(ServletContextEvent arg0) {
		EventContext.distory();
	}

	/*
	 * public static void main(String[] args) throws UnknownHostException {
	 * System.out.println(Inet4Address.getLocalHost().getHostAddress());
	 * System.out.println(InetAddress.getLocalHost()); }
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			
			
			/********
			 * 准备初始化数据
			 * ***********/
			String webappath=FileThread.getProject_realPath() +	arg0.getServletContext().getInitParameter(AppStartupListener.webAppConfigPath);
			Properties prop=PropertiesUtils.getProperties(webappath);
			String listenPort =prop.getProperty(Constant.ATS_PORT_KEY); /*arg0.getServletContext().getInitParameter(
					"listen_port");*/
			Constant.SYS_NOW_PORT=listenPort;
			//System.out.println("侦听 端口："+listenPort);
			if(StringUtils.isNotBlank(listenPort)&&NumberUtils.isNumber(listenPort)){
				String listenAddress =null;
				listenAddress=prop.getProperty(Constant.ATS_IP_KEY);
			
				if(listenAddress!=null){
					listenAddress=StringUtils.trim(listenAddress);
				}else{
					listenAddress= Inet4Address.getLocalHost().getHostAddress();					
				}		
			
			// 设置本地的侦听 端口号，默认是 6868
			if (listenPort != null && NumberUtils.isNumber(listenPort)) {
				ReceiverContext.setLOCAL_PORT(NumberUtils
						.createInteger(listenPort));
			}
			// 设置本地的侦听地址
			if (StringUtils.isNotBlank(listenAddress)) {
				ReceiverContext.setLOCAL_HOST_NAME(listenAddress);
			}
			
			//启动发送端口
			TransmitterContext.start();
			//启动接口端口侦听 
			ReceiverContext.start();
			// 初始化设备的信息缓存
			AtsEquipmentCache.init();
			// 初始化心跳容器
			HeartContext.init();
			// 心跳包检查线程
			HeartBeatCheckThread hbct = new HeartBeatCheckThread();
			Thread hbctThread = new Thread(hbct, "HeartBeatCheckThread");
			hbctThread.start();
			}
		} catch (UnknownHostException e) {
			Constant.SYS_PORT_CAN_USE=810;
			e.printStackTrace();
		}catch (Exception e) {
			Constant.SYS_PORT_CAN_USE=805;
			e.printStackTrace();
			logger.error("启动侦听失败："+e.getMessage(),e);
		}

	}

}
