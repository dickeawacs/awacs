package com.cdk.ats.udp.transmitter;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.reset.ResetSender;
import com.cdk.ats.udp.utils.CommandTools;

/***
 * 发送器容器： 1 用于初始发送器的相关信息，如：发送的地址、发送的端口。 2 初始化任务列和发送器线程 被 Transmitter类引用
 * 
 * @author cdk
 * 
 */
public class TransmitterContext {
	/***
	 * 普通发送间隔时间 
	 */
	public static final long splitTime=600;
	
	private static Logger logger = Logger.getLogger(TransmitterContext.class);
	/***
	 * 发送器的线程池，用于执行任务队列
	 */
	private static ThreadPoolExecutor Thread_Pool = null;
	/**
	 * 任务队列，用于线程池的生产里的排队
	 */
	private static BlockingQueue<Runnable> Task_Queue;
	/***
	 * 信息是否发送成功的检查线程池，用于执行检查的任务队列
	 */
	private static ThreadPoolExecutor Thread_Pool2 = null;
	/**
	 * 检查信息是否发送成功任务队列，用于检查的的排队
	 */
	private static BlockingQueue<Runnable> Task_Queue2;

	/**
	 * 数据报发往的远程主机的 SocketAddress（通常为 IP 地址 + 端口号）。
	 */
	private static SocketAddress Socket_Address;

	/**
	 * 用于记录发送报的流水号，每一次的非响应的 信息发送 都需要获取一个流水号，并记录在此变量中
	 */
	private static Map<Integer, Integer> Send_Sequnce = new HashMap<Integer, Integer>();

	/***
	 * 流水号的值，每次加1
	 */
	private static int Sequnce = 1;

	/***
	 * 启动初始化，开启任务处理线程和相关队列
	 */
	public static void start() {
		Task_Queue = new LinkedBlockingDeque<Runnable>();
		Thread_Pool = new ThreadPoolExecutor(10, 20, 1000,
				TimeUnit.MILLISECONDS, Task_Queue);

		// Socket_Address = new InetSocketAddress(Target_InetAddress,
		// Target_Port);

		Task_Queue2 = new LinkedBlockingDeque<Runnable>();
		Thread_Pool2 = new ThreadPoolExecutor(10, 20, 1000,
				TimeUnit.MILLISECONDS, Task_Queue2);
	}

	/***
	 * 生产发送目标的socket，
	 * 
	 * @param IPaddress
	 *            目标地址
	 * @param Target_Port
	 *            目标端口
	 * @return
	 * @throws UnknownHostException
	 */
	public static InetSocketAddress targetSocketAddressFactory(
			String targetAddress, int Target_Port) throws UnknownHostException {
		InetSocketAddress adres = null;
		InetAddress iadres = null;
		try {
			// 设置目标地址
			if (targetAddress != null && !targetAddress.trim().equals("")) {
				targetAddress = targetAddress.trim().replaceAll("[ /]+", "");
				// 验证是否为有效的IP
				if (targetAddress
						.matches("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b")) {
					iadres = InetAddress.getByName(targetAddress);
					adres = new InetSocketAddress(iadres, Target_Port);
				} else
					throw new java.net.UnknownHostException();
				// TransmitterContext.setTarget_InetAddress(InetAddress.getLocalHost());
			} else
				throw new java.net.UnknownHostException();
			// TransmitterContext.setTarget_InetAddress(InetAddress.getLocalHost());
			return adres;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			//throw new java.net.UnknownHostException(targetAddress);
			return null;
		}

	}

	/***
	 * 添加一个发送包
	 * 
	 * @param info
	 *            需要发送的数据
	 * @param sender
	 *            若是响应包则为false，否则 为true
	 * @param sendRequestKey
	 *            流水号
	 * @param targetIP
	 *            目标ip
	 * @param targetPort
	 *            目标端口
	 */
	public static void sendDatagram(byte[] info, boolean sender,
			int sendRequestKey, String targetIP, int targetPort) {
		Transmitter myt = new Transmitter(new ActionReady(info, sender, sendRequestKey, targetIP, targetPort));
		Thread_Pool.execute(myt);
	}

	/***
	 * 一个发送包
	 * @param info
	 * @param sendRequestKey
	 * @param dp
	 */
	public static void sendRequestDatagram(byte[] info, int sendRequestKey,
			DatagramPacket dp) {
		Transmitter myt = new Transmitter(new ActionReady(info, true, sendRequestKey,(dp.getAddress()!=null?dp.getAddress().toString():null), dp.getPort()));
		Thread_Pool.execute(myt);

	}

	/***
	 * 发送一个响应包
	 * 
	 * @param put
	 *            需要发送的数据
	 * @param requestKey
	 *            流水号
	 * @param dp
	 *            请求数据包，将会在其中获取目标ip与端口
	 */
	public static void sendResponseDatagram(byte[] put, int requestKey,
			DatagramPacket dp) {
		Transmitter myt = new Transmitter(new ActionReady(put, false, requestKey,(dp.getAddress()!=null?dp.getAddress().toString():null), dp.getPort()));
		Thread_Pool.execute(myt);
	}

	/***
	 * 发送一个响应包
	 * 
	 * @param sucess
	 *            标记成功为true，反之为false
	 * @param requestKey
	 *            流水号
	 * @param dp
	 *            请求数据包，将会在其中获取目标ip与端口
	 */
	public static void sendResponseDatagram(boolean sucess, int requestKey,
			DatagramPacket dp) {
		Transmitter myt = new Transmitter(new ActionReady(CommandTools.responseData(dp.getData(), sucess), false, requestKey,(dp.getAddress()!=null?dp.getAddress().toString():null), dp.getPort()));
		Thread_Pool.execute(myt);
	}

	/****
	 * 发送请求包
	 * 
	 * @param actions
	 */
	public static synchronized void pushDatagram(ActionReady action) {
		Transmitter myt = new Transmitter();
		/*myt.setBuff(action.getData());
		myt.setSender(action.isAction());
		myt.setSendRequestKey(action.getKey());
		myt
				.setTargetSocketAddress(action.getTargetIp(), action
						.getTargetPort());*/
		myt.setReadyData(action);
		Thread_Pool.execute(myt);
	}

	/****
	 * 批量发送数据包
	 * 
	 * @param List
	 *            <ActionReady> actions
	 */
	public static synchronized void pushGroupDatagram(List<ActionReady> actions) {
		/*for (int i = 0; i < actions.size(); i++) {
			Transmitter myt = new Transmitter();
		
			myt.setBuff(actions.get(i).getData());
			myt.setSender(actions.get(i).isAction());
			myt.setSendRequestKey(actions.get(i).getKey());
			myt.setTargetSocketAddress(actions.get(i).getTargetIp(), actions
					.get(i).getTargetPort());
			
			myt.setReadyData(actions.get(i));
			Thread_Pool.execute(myt);
		}*/
		TransmitterArrays myt = new TransmitterArrays();
		myt.setReadyData(actions);
		Thread_Pool.execute(myt);
	}

	/***
	 * 发送重置数据包（特殊 ）
	 * @param actions
	 */
	public static void pushResetDatagram(ActionParams group,List<ActionReady> actions){
		ResetSender rs=new ResetSender();
		rs.setData(actions);
		rs.setGroupKey(group.getSequnce());
		
		Thread_Pool.execute(rs);
	}
	
	/**
	 * 将一个控制中心主动 发送的数据报及流水号放入 响应检查队列中，用于检查信息是否被成功接收
	 * 
	 * @param info
	 *            被发送的数据报
	 * @param sendRequestKey
	 *            对应的流水号
	 */
/*	public static synchronized void checkResponseDatagram2(ActionReady ready) {
		TransmitterCheckResponse mycheck = new TransmitterCheckResponse(ready);
		Thread_Pool2.execute(mycheck);
	}*/

	/**
	 * 获取一个流水号
	 * 
	 * @return int 信息发送的本地唯一序号号
	 */
	public static synchronized int getSequnce() {
		if (Sequnce == 2147483647)
			Sequnce = 1;
		int temp = Sequnce++;
		Send_Sequnce.put(temp, 1);//临时标记为1 
		return temp;
	}

	public static synchronized void clearSequnce() {
		Sequnce = 1;
	}

	/***
	 * 验证流水号是否存在： 若存在 则认为没有收到响应包， 若不存在则视为已经成功收到响应包，则说明数据发送成功
	 * 
	 * @param key
	 * @return 若存在，则返回true 反之则返回false
	 */
	public static synchronized boolean checek_Sequnce(int key) {
		return Send_Sequnce.containsKey(key);
	}

	/***
	 * 当发送出现异常时，异常此处理办法
	 * 
	 * @param sender
	 *            是否为主动 发送
	 * @param key
	 *            流水号
	 */
	public static void transmitterErrorProcess(boolean sender, Integer key,
			String errorMsg) {
		if (sender) {
			ActionContext.setState(key, false, errorMsg);
		} else {
			ReceiverContext.getReceive_Sequnce().remove(key);
		}

	}

	/***
	 * 删除 发送包流水号中的 流水号
	 * 
	 * @param key
	 */
	public static void delSequnce(int key) {
		Send_Sequnce.remove(key);
	}

	/****
	 * 用于记录发送报的流水号，每一次的非响应的 信息发送 都需要获取一个流水号，并记录在此变量中
	 * 
	 * @return
	 */
	public static Map<Integer, Integer> getSend_Sequnce() {
		return Send_Sequnce;
	}

	public static void setSend_Sequnce(Map<Integer, Integer> sendSequnce) {
		Send_Sequnce = sendSequnce;
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

	public static ThreadPoolExecutor getThread_Pool2() {
		return Thread_Pool2;
	}

	public static void setThread_Pool2(ThreadPoolExecutor threadPool2) {
		Thread_Pool2 = threadPool2;
	}

	public static BlockingQueue<Runnable> getTask_Queue2() {
		return Task_Queue2;
	}

	public static void setTask_Queue2(BlockingQueue<Runnable> taskQueue2) {
		Task_Queue2 = taskQueue2;
	}
}
