package com.cdk.ats.udp.transmitter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionReady;
import common.cdk.config.files.msgconfig.MsgConfig;

/***
 * 发送数据包后，判断对方是否返回响应包。若没有再累计发送三次。
 * 发送失败与成功后，都将
 * @author cdk
 * 
 */
public class TransmitterCheckResponse implements Runnable {
	private static Logger logger=Logger.getLogger(TransmitterCheckResponse.class);
	private ActionReady ready;
	
	
	public TransmitterCheckResponse() {
	}
	
	public TransmitterCheckResponse(ActionReady ready) {
		super();
		this.ready = ready;
	}

	public static DatagramSocket checkDataSocket;
	//获取发送端口
	public static DatagramSocket getDatagramSocket(){
		if(checkDataSocket==null){
				try {
					checkDataSocket = new DatagramSocket();
				} catch (SocketException e) {
					logger.error(e.getMessage(),e);
				}
		}
		return checkDataSocket;
	}  
 
	/***
	 * 验证本地数据包发送后是否被响应
	 * 逻辑：
	 *  1、判断此数据包是否已经重复发送过大于2次，若是则返回不再发送，调用 isFaild()进行处理，并直接跳出方法体。
	 *  2、判断数据包发送后仍然没有响应，若没有，则第一次暂停线程1秒等待数据包的响应处理
	 *  3、判断数据包发送后仍然没有响应，若没有，则第二次暂停线程1秒等待数据包的响应处理
	 *  4、判断数据包发送后仍然没有响应，则重新发送此数据包，直到成功或是认为失败为止
	 ***/
	public void run() {
		try {
			Thread.sleep(500);
			//判断此数据包已经被重复发送了几次，大于 两次则认为发送失败
			if(TransmitterContext.checek_Sequnce(ready.getKey())&&TransmitterContext.getSend_Sequnce().get(ready.getKey())>5){isFaild();return;}
			else{
				
				
			}
/*			//判断我方发送的数据包是否已经接收到对应的响应包，若仍然存在 ，说明此数据包没有接收到响应包，
			//这里需要让线程等待一秒再做判断 
			if (TransmitterContext.checek_Sequnce(ready.getKey()))				
				Thread.sleep(2000);
			//判断我方发送的数据包是否已经接收到对应的响应包，若仍然存在 ，说明此数据包没有接收到响应包，
			//这里需要让线程等待一秒再做判断 
			if (TransmitterContext.checek_Sequnce(ready.getKey())) 
				Thread.sleep(2000);
			//判断我方发送的数据包是否已经接收到对应的响应包，若仍然存在 ，说明此数据包没有接收到响应包，
			//第三次判断是，仍然不存在 ，这里需要将数据包重新发送一次。并在流水包记录器中，将对应的值累加1
*/			if (TransmitterContext.checek_Sequnce(ready.getKey())) {
				Integer temp=TransmitterContext.getSend_Sequnce().get(ready.getKey());
				if(temp!=null)temp++;
				else temp=2;
				SocketAddress targetAddress=TransmitterContext.targetSocketAddressFactory(ready.getTargetIp(), ready.getTargetPort());
				if(ready.isAction()&&targetAddress==null)throw new AtsException(MsgConfig.msg("ats-1002", "key",ready.getKey()+""));
				
				TransmitterContext.getSend_Sequnce().put(ready.getKey(),temp);
				DatagramPacket mydatagram = createDataPacket(ready);
				mydatagram.setSocketAddress(targetAddress);//设置数据包的发送目标地址
				getDatagramSocket().send(mydatagram);
				TransmitterContext.sendRequestDatagram(ready.getData(),ready.getKey(),mydatagram);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} catch (AtsException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/***
	 * 数据包发送失败，调用此方法进行后继处理
	 * 当发送次数大于2 时，说明这个数据包已经被重复发送了2次以上了。因此认为对方没有接收到请求。
	 */
	private void isFaild(){
		/***
		 * 失败的处理块
		 */
		ActionContext.setState(901,ready.getKey(), false, "操作失败,无法连接远程设备!");
	}
	/***
	 * 创建一个需要发送的数据包
	 * @param ready
	 * @return
	 */
	public DatagramPacket createDataPacket(ActionReady ready) {
		return new DatagramPacket(ready.getData(), ready.getData().length);
	}
	public ActionReady getReady() {
		return ready;
	}

	public void setReady(ActionReady ready) {
		this.ready = ready;
	}

}
