package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.event.EventContext;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.udp.webplug.dao.DaoFactory;
import com.cdk.ats.web.dao.Table10DAO;
import com.cdk.ats.web.dao.Table1DAO;
import com.cdk.ats.web.pojo.hbm.Table10;

public class Operating_0x60 {

	private static Logger logger = Logger.getLogger(Operating_0x60.class);
	private DatagramPacket dp;
	private Table10DAO t10Dao;
	private Table1DAO t1Dao;
	public Table10DAO getT10Dao() {
		if (t10Dao == null) {
			t10Dao = DaoFactory.getTable10Dao();
		}
		return t10Dao;
	}
	public Table1DAO getT1Dao() {
		if (t1Dao == null) {
			t1Dao = DaoFactory.getTable1Dao();
		}
		return t1Dao;
	}
	public void CloseDao(){
		if(t10Dao!=null){
			t10Dao.closeSession();
		}
		if(t1Dao!=null){
			t1Dao.closeSession();
		}
	}
  
	public Operating_0x60(DatagramPacket dp) {
		this.dp = dp;
	}
	
	
	/***
	 * 初始化结束命令
	 */
	public void process_0x60_0x01() {
		
		boolean end = false;
		byte[] data = dp.getData();
		int key = 0,one=0,two;
		one=data[9] & 0xff;//一层设备地址
		two=data[12] & 0xff;//二层设备地址
		if(logger.isDebugEnabled()){
			logger.debug("..................结束标记：“"+one+","+two);
		}
		two=0;
		try {
			if(one==0) throw new AtsException("无效的设备地址");
			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));
			Table10 t10 = AtsEquipmentCache.getEquiReadOnly(one, two);
			if(t10==null){
				return;
			}
			t10.setFip(dp.getAddress().getHostAddress());
			t10.setFport(dp.getPort());
			if(t10 != null){
				List<Table10> temp10 = new ArrayList<Table10>();
				if (!t10.getSecond().isEmpty()) {
					List<Table10> removeT10 = new ArrayList<Table10>();
					for (int i = 0; i < t10.getSecond().size(); i++) {
						if(Integer.valueOf(Constant.EQUIPMENT_STATUS_LOSE_VAL).equals(t10.getSecond().get(i).getField130()))
						{
							//System.out.println(t10.getSecond().get(i).getField121()+"掉线");
							removeT10.add(t10.getSecond().get(i));
						}else{
							temp10.add(t10.getSecond().get(i));
						}
					}
					//Table10 otherT10=AtsEquipmentCache.getEquiReadOnly(one, two).get(CommandTools.handKeyFormat(one,0));
					//删除仍然是掉线状态的设备。它可能已经不存在 了
					if(removeT10.size()>0){
						for (int i = 0; i < removeT10.size(); i++) {
							t10.getSecond().remove(removeT10.get(i));
							AtsEquipmentCache.removeEquiIndex(removeT10.get(i).getField3(), removeT10.get(i).getField4());
						}
					}
				}
				temp10.add(t10);
				
				//保存一层设备级二层设备初始化信息
				if (!temp10.isEmpty())//&&("d".equals(MsgConfig.msg("0x60.01"))||"a".equals(MsgConfig.msg("0x60.01"))))
					this.getT10Dao().saveOrUpdateAll(temp10, t10.getFip(),t10.getFport());
				
				//保存设备下的所有用户信息
				if (!t10.getUsers().isEmpty())//&&("u".equals(MsgConfig.msg("0x60.01"))||"a".equals(MsgConfig.msg("0x60.01"))))
					{
						int delCount=this.getT1Dao().deleteById(t10.getField3());
						if(logger.isDebugEnabled()){
						logger.debug("删除用户数量："+delCount);
						}
						
						this.getT1Dao().saveOrUpdateAll(t10.getUsers());
					}
				
				end = true;
				//清空在初始化过程中的缓存信息，并保存一个系统设备树查询的缓存数据
				AtsEquipmentCache.refreshEquipmentCacheVO(one);
				//删除握手包
				//HandContext.del(one, two);
			//	LogContent.r("[初始化结束命令]:设备："+one);
				//链接成功
				EventContext.logFaultConnection(one,two,0x00);
			}else{
				//LogContent.r("[初始化结束<font style='color:red'>失败</font>]:设备："+one+","+two);
			}
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	}
 

}
