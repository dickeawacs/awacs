package com.cdk.ats.udp.transmitter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import common.cdk.config.files.msgconfig.MsgConfig;


/***
 * 发送器：用于针对指定的地址与端口发送数据包
 * 
 * @author cdk
 * 
 */
public class Transmitter implements Runnable {
	public Transmitter( ) {
	}
	public Transmitter(ActionReady readyData) {
		super();
		this.readyData = readyData;
	}

	private static Logger logger=Logger.getLogger(Transmitter.class);
	//TransmitterContext.getSocket_Address()
	//目标的地址
	//private SocketAddress targetSocketAddress;
	//需要发送的数据 
	private ActionReady readyData;
	
	private Map<Integer,ActionReady> cacheKey=new ConcurrentHashMap<Integer, ActionReady>();
	
	/***
	 * 数据包：为发送时准备，其中指定了发送地址与端口，及要发送的信息
	 */
	//private DatagramPacket dp;
	
	/**
	 * 是否为主动发送包：只有当控制中心主动执行发送时，sender=true,响应包则为 sender=false;
	 */
	//private boolean  sender=false;
	/***
	 * 数据报流水号：若是控制中心主动执行发送时，需要用到此流水号，用于检查是否成功响应
	 */
	//private int  sendRequestKey;
	/***
	 * 需要发送的信息内容 
	 */
	//private byte[] buff;

	
	
	
	
	/*public SocketAddress getTargetSocketAddress() {
		return targetSocketAddress;
	}

	*//***
	 * 设置socket的目标IP与目标端口
	 * @param ip
	 * @param port
	 * @throws UnknownHostException 
	 *//*
	public void setTargetSocketAddress(String ip,int port) {
		try{
			if(this.targetSocketAddress==null)
			this.targetSocketAddress = TransmitterContext.targetSocketAddressFactory(ip, port);
		}catch (Exception e) {
			this.targetSocketAddress=null;
		}
	}*/
	
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
	
/*	public void setTargetSocketAddress(java.net.InetSocketAddress adr) {
		try{
			this.targetSocketAddress =adr;
		}catch (Exception e) {
			this.targetSocketAddress=null;
		}
	}*/
	/***
	 * 创建一个需要发送的数据包
	 * @param ready
	 * @return
	 */
	public DatagramPacket createDataPacket(ActionReady ready) {
		return new DatagramPacket(ready.getData(), ready.getData().length);
	}
	/*public DatagramPacket getDp() {
		if(dp==null){
			dp=new DatagramPacket(buff, buff.length);
			
		}
		return dp;
	}

	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}

	
	public boolean isSender() {
		return sender;
	}

	public void setSender(boolean sender) {
		this.sender = sender;
	}*/
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
			 if(readyData.isAction()){
				 DatagramSocket  dataSocket = new DatagramSocket();
				/***
				 * 还需要加入一个验证，在线程睡眠重启后，要去判断缓存中的数据包是否已经响应了，若已经响应了，就不需要再发送了
				 * **/
				if(readyData.getChildrens().size()>0){
					
					for (int i = 0; i < readyData.getChildrens().size(); i++) {
						
						this.send(readyData.getChildrens().get(i),dataSocket);
						cacheKey.put(readyData.getChildrens().get(i).getKey(),readyData.getChildrens().get(i));
						Thread.sleep(TransmitterContext.splitTime);  
						
					}
				}else{
					this.send(readyData,dataSocket);
					cacheKey.put(readyData.getKey(),readyData);
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
				
			}  else{
				 //如果是响应包，则只发送一次。
				  responsePack( readyData);
				  end=true; 
			 }
		} catch (SocketException e) {
			logger.error(MsgConfig.msg("ats-1000", "key",readyData.getKey()+""),e);
			
		} catch (IOException e) {
			logger.error(MsgConfig.msg("ats-1003", "error",e.getMessage()),e);
		 
		} catch (AtsException e) {
			 message=e.getMessage() ;
			logger.error(e.getMessage(),e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(!end){//如果有异常，则需要处理
				TransmitterContext.transmitterErrorProcess(readyData.isAction(), readyData.getKey(), message!=null?message:MsgConfig.msg("ats-1000", "key",readyData.getKey()+""));
			}
		}

	} 
	
	private void responsePack(ActionReady readyData2) throws AtsException, IOException, InterruptedException {
		send(readyData,Constant.getInstance().getDataSocket());
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
		if(ready.isAction()&&targetAddress==null)throw new AtsException(MsgConfig.msg("ats-1002", "key",ready.getKey()+""));
		if(ready.isAction()&&ResetContext.isReset(CommandTools.getOne(ready.getData())))throw new AtsException("设备正处于编程状态，无法执行操作！");
		if(ready.isAction()&&!HeartContext.hasHeart(CommandTools.handKeyFormat(ready.getData())))throw new AtsException("设备可能已经掉线！");
		
		if (ready.getData() != null && ready.getData().length > 0) {
			//创建一个需要发送的数据包
			DatagramPacket mydatagram = createDataPacket(ready);
			mydatagram.setSocketAddress(targetAddress);//设置数据包的发送目标地址
			dataSocket.send(mydatagram);//调用SOCKET进行发送
		/*	if (sender) {
				TransmitterContext.checkResponseDatagram(dp,sendRequestKey);
			} else {
			 
				ReceiverContext.getReceive_Sequnce().remove(sendRequestKey);
			}*/
			if(ready.isAction()){
				//System.out.println("ok!");
				//TransmitterContext.checkResponseDatagram(ready);
			
			}
			else{
				//如果是响应包，就要删除此数据包在缓存中的标记
				ReceiverContext.getReceive_Sequnce().remove(ready.getKey());				
			}
		//	LogContent.s(System.currentTimeMillis()+"[发送结束]");
		}else throw new  AtsException(MsgConfig.msg("ats-1001"));
	}
	
/*	public byte[] getBuff() {
		return buff;
	}

	public void setBuff(byte[] buff) {
		this.buff = buff;
	}

	public int getSendRequestKey() {
		return sendRequestKey;
	}

	public void setSendRequestKey(int sendRequestKey) {
		this.sendRequestKey = sendRequestKey;
	}*/

	public ActionReady getReadyData() {
		return readyData;
	}

	public void setReadyData(ActionReady readyData) {
		this.readyData = readyData;
	}
 

	 
 
}
