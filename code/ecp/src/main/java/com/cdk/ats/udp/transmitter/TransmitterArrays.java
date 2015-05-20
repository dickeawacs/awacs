package com.cdk.ats.udp.transmitter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.udp.utils.CommandTools;
import common.cdk.config.files.msgconfig.MsgConfig;


/***
 * 发送器：用于针对指定的地址与端口发送数据包
 * 
 * @author cdk
 * 
 */
public class TransmitterArrays implements Runnable {
	public TransmitterArrays( ) {
	}
	public TransmitterArrays(List<ActionReady> readyData) {
		super();
		this.readyData = readyData;
	}
	private static Logger logger=Logger.getLogger(TransmitterArrays.class);
	//需要发送的数据 
	private List<ActionReady> readyData;
	private Map<Integer,ActionReady> cacheKey=new ConcurrentHashMap<Integer, ActionReady>();
	/**
	 * 格式化目录端口
	 * @param ip
	 * @param port
	 * @return
	 */
	public SocketAddress initTarget(String ip,int port) {
		try{
			
			 return TransmitterContext.targetSocketAddressFactory(ip, port);
		}catch (Exception e) {
			return null;
		}
	}
	
	/***
	 * 创建一个需要发送的数据包
	 * @param ready
	 * @return
	 */
	private  DatagramPacket createDataPacket(ActionReady ready) {
		return new DatagramPacket(ready.getData(), ready.getData().length);
	}
 
private String message=null;
	/***
	 * 在任务执行时调用此方法 ，用于对地址进行数据发送
	 * 逻辑：
	 * 1、准备数据包，写入字节信息、指定字节长度、指定发送地址与端口
	 * 2、创建发送数据报的socket通道
	 * 3、执行数据发送
	 * 4、判断此数据包是否为控制中心主动发送的数据包，若是，则在响应检查线程池中加入一个任务,
	 *    若是响应信息，则将对应记录接收到数据包的流水号容器中删除此流水号
	 * 
	 */
	public void run() {
		boolean end=false;
		try { 
			//创建 一个发送的SOKECT
			DatagramSocket  dataSocket = new DatagramSocket();
			 if(readyData!=null&&readyData.size()>0){
				for (int i = 0; i < readyData .size(); i++) {
					this.send(readyData .get(i),dataSocket);
					cacheKey.put(readyData .get(i).getKey(),readyData.get(i));
					Thread.sleep(TransmitterContext.splitTime);  
				}
				Thread.sleep(TransmitterContext.splitTime);  
				int cc=0;
				 while (cc<5) {
					if(cacheKey.size()>0){
						int hasCount=0;
						Set<Integer> keys=cacheKey.keySet();
						for (Integer tempKey : keys) {
							if(TransmitterContext.checek_Sequnce(tempKey)){
								hasCount++;
								this.send(cacheKey.get(tempKey),dataSocket);
								
								Thread.sleep(TransmitterContext.splitTime);  
							}else{
								cacheKey.remove(tempKey);
							}
						}
						if(hasCount==0){
							break;
						}else{
							Thread.sleep(TransmitterContext.splitTime);  
						}
					}
					cc++;
				}
				if(cacheKey.size()==0){			
					end=true; 
				}
				
			}   
		} catch (SocketException e) {
			logger.error(MsgConfig.msg("ats-1000", "key","[批量包]"),e);
			
		} catch (IOException e) {
			logger.error(MsgConfig.msg("ats-1003", "error",e.getMessage()),e);
		 
		} catch (AtsException e) {
			 message=e.getMessage() ;
			logger.error(e.getMessage(),e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(!end){//如果有异常，则需要处理
				TransmitterContext.transmitterErrorProcess(true, 0000, message!=null?message:MsgConfig.msg("ats-1000", "key","[批量包]"));
			}
		}

	} 
	
	/***
	 * 发送数据包 
	 * @param ready  需要发送的实体 
	 * @param dataSocket 发送的SOCKET通道 
	 * @throws AtsException 
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	private  void  send(ActionReady ready,DatagramSocket dataSocket) throws AtsException, IOException, InterruptedException{
		SocketAddress targetAddress=initTarget(ready.getTargetIp(), ready.getTargetPort());
		if(targetAddress==null)throw new AtsException(MsgConfig.msg("ats-1002", "key",ready.getKey()+""));
		if(ResetContext.isReset(CommandTools.getOne(ready.getData())))throw new AtsException("设备正处于编程状态，无法执行操作！");
		if(!HeartContext.hasHeart(CommandTools.handKeyFormat(ready.getData())))throw new AtsException("设备可能已经掉线！");
		if (ready.getData() != null && ready.getData().length > 0) {
			DatagramPacket mydatagram = createDataPacket(ready);
			mydatagram.setSocketAddress(targetAddress);//设置数据包的发送目标地址
			dataSocket.send(mydatagram);//调用SOCKET进行发送
		}else throw new  AtsException(MsgConfig.msg("ats-1001"));
	}
	public List<ActionReady> getReadyData() {
		return readyData;
	}
	public void setReadyData(List<ActionReady> readyData) {
		this.readyData = readyData;
	}
	
 
	 
 
}
