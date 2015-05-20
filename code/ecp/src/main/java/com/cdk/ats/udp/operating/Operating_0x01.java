package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.webplug.dao.DaoFactory;
import com.cdk.ats.web.dao.Table10DAO;
import com.cdk.ats.web.pojo.hbm.Table10;

/***
 * 
 * 针对命令总分类 0x01 的处理类 上报设备信息 0x01 0x01
 * 
 * 
 * @author cdk
 * 
 */
public class Operating_0x01 {
private static Logger logger=Logger.getLogger(Operating_0x01.class);
	private DatagramPacket dp;
 
	private Table10DAO t10DAO;

	public Operating_0x01() {
	}

	public Operating_0x01(DatagramPacket dp) {
		this.dp = dp;
	}
 
 
	
	private Table10DAO getT10DAO() {
		if (t10DAO == null) {
			t10DAO = DaoFactory.getTable10Dao();
		}
		return t10DAO;
	}

	public void CloseDao(){
		if(t10DAO!=null){
			t10DAO.closeSession();
			
		}
		 
	}
	/***
	 * 接收上报信息（一层设备和二层设备）
	 * 处理方法 ，执行数据的校验、存储与数据包响应
	 * @return
	 */
	public Object process1() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			//此数据包为二层设备上报信息
			Table10 t10 = new Table10();
				/*** 一层设备网络地址 ***/
				t10.setField3(data[9]&0xff);
				/*** 二层设备网络地址 ***/
				t10.setField4(data[12]&0xff);
				/*** 设备启用状态位，0为无状态 ***/
				t10.setField5(0);
				/*** 设备唯一ID ***/
				t10.setField8(CommandTools.byteToString(data, 40,9));
				/*** 设备生产序列号 ***/
				t10.setField9(CommandTools.byteToString(data, 16,24));
				/*** 设备种类 ***/
				t10.setField10(data[13]&0xff);
				t10.setFip(dp.getAddress().toString());
				t10.setFport(dp.getPort());
				//记录上报二层设备信息
				t10.setDtype(0); 
			end=this.getT10DAO().saveOrUpdate(t10);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//TransmitterContext.sendResponseDatagram(end,1,data);
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x01,(byte)0x01,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/		
		}
		return end;
	}

	public DatagramPacket getDp() {
		return dp;
	}

	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}
 
	 

	/*public static void main(String[] args) {
		//System.out.println(255 == 0xff);
	}*/
}
