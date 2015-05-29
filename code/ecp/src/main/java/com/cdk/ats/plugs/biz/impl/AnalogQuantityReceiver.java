package com.cdk.ats.plugs.biz.impl;

import java.net.DatagramPacket;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdk.ats.plugs.biz.IPlugsReceiveCmdBiz;
import com.cdk.ats.plugs.cmd.CmdPlugs;
import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.event.EventContext;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.web.pojo.hbm.Table10;

/****
 * 
* @Title: AnalogQuantityReceiver.java 
* @Package com.cdk.ats.plugs.biz.impl 
* @Description: 模拟量命令业务类
* @author 陈定凯 
* @date 2015年5月27日 上午1:10:49 
* @version V1.0
 */
@Service
public class AnalogQuantityReceiver extends BaseReceiveCmdBiz implements IPlugsReceiveCmdBiz {
	@Resource
	private CmdPlugs cmd;
	@Override
	public Object receive(DatagramPacket dp) {
		 if(isResponse(dp))
		 {
			 
	 }else{
		 	response(dp);
		 }
			;// cmd.processDownResponse(responseData, params)
		 
		return null;
	}
	
	private Object request(){
		return null;
	}
	
	private Object response(DatagramPacket dp){

		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			
			//此数据包为二层设备上报信息
			/*** 一层设备网络地址 ***/
			int one=(data[9]&0xff);
			/*** 二层设备网络地址 ***/
			int two =(data[12]&0xff);
			/*** 设备输入状态  *输入端口*         触发值*/
			int input=(data[14]&0xff);
			int state=(data[15]&0xff); //0正常，1触发 
			Table10 t10=AtsEquipmentCache.getEquiReadOnly(one, two);
			if(t10!=null){
				t10.setPortState(1, CommandTools.switchPortIndex128To8(input), state==0?1:2);//1绿色，2红色
			}
			
			EventContext.logAlarmAction(one, two, input, state);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}
		return end;
	
	}
	/***
	 * 
	* 
	* @Description:  问李，是否可以用一个字段标记是不是响应的数据，还是请求的数据
	* @author 陈定凯 
	* @date 2015年5月27日 上午1:16:03  
	* @param dp
	* @return
	 */
	private boolean isResponse(DatagramPacket dp){
		return false;
	}

}
