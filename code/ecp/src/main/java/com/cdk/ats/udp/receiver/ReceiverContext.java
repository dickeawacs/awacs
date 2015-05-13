package com.cdk.ats.udp.receiver;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.util.NetUtils;
import org.apache.log4j.Logger;

import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;

/***
 * 接收器初始化容器：用于接收本机指定端口的数据包，接收的端口可以是一个或多个。 被 receiver类引用 。
 * 
 * @author cdk
 * 
 */
public class ReceiverContext {
	/***
	 * 本地接收数据的本地端口
	 */
	private static int 		LOCAL_PORT=6868;
	private static String 	LOCAL_HOST_NAME="127.0.0.1";
	private static Logger logger=Logger.getLogger(ReceiverContext.class);
	/***
	 * 数据包的业务处理线程池（以下统称业务处理池），用于执行数据包处理任务
	 */
	private static ThreadPoolExecutor Thread_Pool = null;
	/**
	 *数据包处理任务队列，用于业务处理池的排队
	 */
	private static BlockingQueue<Runnable> Task_Queue;

	/**
	 * 接收数据报的本地主机的 SocketAddress（通常为 IP 地址 + 端口号）。
	 */
	private static SocketAddress Socket_Address;
	/****
	 * 本地接收数据报的侦听通道
	 */
	private static DatagramSocket Local_DatagramSocket = null;

	/**
	 * 用于记录接收到数据包的流水号，每一次的非响应的 请求信息 都需要将信息中的流水号获取出来并记录在此变量中
	 */
	private static Map<Integer, byte[]> receive_Sequnce = new HashMap<Integer,  byte[]>();

	/***
	 * 初始化
	 * 
	 * @throws SocketException
	 */
	private static void init() throws SocketException {
		Task_Queue = new LinkedBlockingDeque<Runnable>();
		Thread_Pool = new ThreadPoolExecutor(10, 20, 1000,
				TimeUnit.MILLISECONDS, Task_Queue);
		Socket_Address = new InetSocketAddress(LOCAL_HOST_NAME, LOCAL_PORT);
		
		
		try {
			Local_DatagramSocket = new DatagramSocket(Socket_Address);
		} catch (SocketException e) {
			logger.error("监听IP端口无效:"+Socket_Address.toString());
			Socket_Address = new InetSocketAddress(getHostAddress(), LOCAL_PORT);
			Local_DatagramSocket = new DatagramSocket(Socket_Address);	
			logger.info("自动绑定，IP端口:"+Socket_Address.toString());
		}
	}

	/***
	 * 启动本地侦听，开始接收数据包
	 */
	public static void start() {
		try {
			init();
			Receiver rt = new Receiver();
			rt.setDatagramSocket(Local_DatagramSocket);
			Thread th = new Thread(rt, "datagramSocket" + LOCAL_PORT);
			th.start();
		} catch (Exception e) {
			Constant.SYS_PORT_CAN_USE=805;
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}

	}

	/**
	 * 将一个数据报处理任务加入到业务处理任务列表中等待业务处理
	 * 
	 * @param rp
	 *            等待执行的业务包处理线程
	 */
	public static synchronized void pushReceiverProcess(ReceiverProcess rp) {
		ReceiverContext.Thread_Pool.execute(rp);
	}

	/***
	 * 记录接收到的数据包的流水号
	 * @param key
	 * @return  流水号的10进值整数值
	 */
	public static int putReceiveSequnce(byte[] key){
		int val=CommandTools.formateByteToint(key); 
		receive_Sequnce.put(val,key);
		return val;
	}
	 
 
 

	public static ThreadPoolExecutor getThread_Pool() {
		return Thread_Pool;
	}

	public static void setThread_Pool(ThreadPoolExecutor threadPool) {
		Thread_Pool = threadPool;
	}

	public static BlockingQueue<Runnable> getTask_Queue() {
		return Task_Queue;
	}

	public static void setTask_Queue(BlockingQueue<Runnable> taskQueue) {
		Task_Queue = taskQueue;
	}

	public static SocketAddress getSocket_Address() {
		return Socket_Address;
	}

	public static void setSocket_Address(SocketAddress socketAddress) {
		Socket_Address = socketAddress;
	}

	public static DatagramSocket getLocal_DatagramSocket() {
		return Local_DatagramSocket;
	}

	public static void setLocal_DatagramSocket(
			DatagramSocket localDatagramSocket) {
		Local_DatagramSocket = localDatagramSocket;
	}

	/***
	 * 用于记录接收到数据包的流水号，对方发送过来的请求信息 都需要将信息中的流水号获取出来并记录在此变量中
	 * @return
	 */
	public static Map<Integer, byte[]> getReceive_Sequnce() {
		return receive_Sequnce;
	}

	public static void setReceive_Sequnce(Map<Integer, byte[]> receiveSequnce) {
		receive_Sequnce = receiveSequnce;
	}

	public static int getLOCAL_PORT() {
		return LOCAL_PORT;
	}

	public static void setLOCAL_PORT(int lOCALPORT) {
		LOCAL_PORT = lOCALPORT;
	}

	public static String getLOCAL_HOST_NAME() {
		return LOCAL_HOST_NAME;
	}

	public static void setLOCAL_HOST_NAME(String lOCALHOSTNAME) {
		LOCAL_HOST_NAME = lOCALHOSTNAME;
	}
private static   String getHostAddress() {
	try {
	  return InetAddress.getLocalHost().getHostAddress();
	} catch (UnknownHostException e) {
		return null;
	}
}   

}
