package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.hand.HandContext;
import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.PortFormateUtils;

/***
 * 连接包处理类
 * 
 * @author dingkai
 * 
 */
public class Operating_0x40 {
	private static Logger logger = Logger.getLogger(Operating_0x40.class);
	private DatagramPacket dp;

	public Operating_0x40() {
	}

	public Operating_0x40(DatagramPacket dp) {
		this.dp = dp;
	}

	public DatagramPacket getDp() {
		return dp;
	}

	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}

	/***
	 * 心跳包
	 */
	public void process_0x40_0x01() { 
		boolean end = true;
		byte[] data = dp.getData();
		int key = 0;
		try {
			//LogContent.times(System.currentTimeMillis());
			int one, two;
			key=ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			one = data[9] & 0xff;
			two =0; //data[12] & 0xff;  只记录一层心跳
			// * 将心跳包记录入心跳包的管理容器内，或是发生状态的变更*
			//LogContent.times(System.currentTimeMillis());
			if(HeartContext.hasHeart(CommandTools.handKeyFormat(one, two))){
				HeartContext.heartbeat(one,two);
			}else end=false;
			//LogContent.times(System.currentTimeMillis());
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			if(end)
			TransmitterContext.sendResponseDatagram(end, key, dp);
			//LogContent.times(System.currentTimeMillis());
		}

	 }

	/***
	 * 握手包
	 */
	public void process_0x40_0x02() {
		boolean end = false;
		byte[] data = dp.getData();
		int key = 0, one, two;
		key=ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
		
		one = data[9] & 0xff;
		two = data[12] & 0xff;
		int  childType=(data[13]&0xff);
		try {
			if(one==0) throw new AtsException("无效的设备地址");
			HandContext.shakeHands(one,two);
			
			//删除之后创建一次。并设备一层设备的设备属性，由此决定输入输出端口个数
			Table10 equi=AtsEquipmentCache.getEqui(one, 0);
			equi.setDtype(childType);
			int[] cnt=PortFormateUtils.getPortCount(equi.getDtype());
			equi.setInCount(cnt[0]);
			equi.setOutCount(cnt[1]);
			equi.setPortState(1, equi.getInCount());
			equi.setPortState(2, equi.getOutCount());
			// end
			
			end = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			end=false;
		} finally {
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}

	}

}
