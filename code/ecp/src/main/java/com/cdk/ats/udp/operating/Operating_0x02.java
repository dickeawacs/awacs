package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.event.EventContext;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.webplug.dao.DaoFactory;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.dao.Table10DAO;
import com.cdk.ats.web.pojo.hbm.Table10;

/***
 * 
 * 针对命令总分类 0x02 的处理类
 * 
 * <pre>
 * 上报设备电压信息		0x02	0x01
 * 上报设备通讯状态		0x02	0x02
 * 上报设备被撬			0x02	0x03
 * 上报设备输入状态		0x02	0x04
 * 上报设备输出状态		0x02	0x05
 * 
 * </pre>
 * 
 * 
 * @author cdk
 * 
 */
public class Operating_0x02 {
private static Logger logger=Logger.getLogger(Operating_0x02.class);
	private DatagramPacket dp;
 
	private Table10DAO t10DAO;
	private BaseDao baseDao;
	
	public BaseDao getBaseDao() {

		if (baseDao == null) {
			baseDao = DaoFactory.getBaseDao();
		}
		return baseDao;
	
	}

	public Operating_0x02() {
	}

	public Operating_0x02(DatagramPacket dp) {
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
	 * 接收上报设备电压信息		0x02	0x01
	 * 处理方法 ，执行数据的校验、存储与数据包响应
	 * @return
	 */
	public Object process_0x02_0x01() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			//此数据包为二层设备上报信息
			int one=data[9]&0xff;
			int two=data[12]&0xff;
			/****设备电压信息：0x00代表低电压报警，0xff代表高电压报警，0x80代表电压正常。***/
			int state=data[14]&0xff;
		    EventContext.logFaultLow(one, two,state);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}
		return end;
	}
	
	
	
	/***
	 * 接收上报 设备通讯正/异常 	0x02	0x02
	 * 处理方法 ，执行数据的校验、存储与数据包响应
	 * @return
	 */
	public Object process_0x02_0x02() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 
			 
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			int one=data[9]&0xff;
			int two=data[12]&0xff;
			/*** 设备通讯正/异常  0x00代表设备通讯正常，0xff代表设备通讯异常。 ***/
			int state=data[14]&0xff;
		    EventContext.logFaultConnection(one, two,state);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			}
		return end;
	}
	/***
	 * 接收上报设备被撬 0x02	0x03
	 * 处理方法 ，执行数据的校验、存储与数据包响应
	 * @return
	 */
	public Object process_0x02_0x03() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			int one=data[9]&0xff;
			int two=data[12]&0xff;
			/*** 设备被撬   0x00代表设备正常，0xff代表设备被撬***/
			int state=data[14]&0xff;
		    EventContext.logFaultLose(one, two,state);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
	 	}
		return end;
	}
	
	
	/***
	 * 接收 上报设备输入状态 0x02	0x04
	 * 处理方法 ，执行数据的校验、存储与数据包响应
	 * 
	 * 先查缓存区有没有设备，没有就去数据库中查
	 * 
	 * @return
	 */
	public Object process_0x02_0x04() {
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
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}
		return end;
	}
	
	/***
	 * 接收 上报设备输出状态 0x02	0x05
	 * @return
	 */
	public Object process_0x02_0x05() {
		boolean end = false;
		byte[] data = dp.getData();
		int key = 0;
		try {

			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));

			int one = data[9] & 0xff;
			int two = data[12] & 0xff;
			int input = data[14] & 0xff;
			int state = data[15] & 0xff;
			Table10 t10=AtsEquipmentCache.getEquiReadOnly(one, two);
			if(t10!=null){
				t10.setPortState(2, CommandTools.switchPortIndex128To8(input), state==0?-1:1);//1绿色，3黄色
			}
			//断开 是红色，闭合 是绿色
			EventContext.logOutputClose(one, two, input, state);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}
		return end;
	}
	/***
	 * 上报设备输入状态异常 0x02	0x06
	 * @return
	 */
	public Object process_0x02_0x06() {
		boolean end = false;
		byte[] data = dp.getData();
		int key = 0;
		try {

			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));

			int one = data[9] & 0xff;
			int two = data[12] & 0xff;
			int input = data[14] & 0xff;
			int state = data[15] & 0xff;
			Table10 t10=AtsEquipmentCache.getEquiReadOnly(one, two);
			if(t10!=null){
				t10.setPortState(1, CommandTools.switchPortIndex128To8(input), state==0?1:3);//1绿色，3黄色
			}
			
			EventContext.logInputException(one, two, input, state);//(one, two, input, state);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}
		return end;
	}
	
	/***
	 * 上报设备输入状态触发 0x02	0x07 
	 * @return
	 */
	public Object process_0x02_0x07() {
		boolean end = false;
		byte[] data = dp.getData();
		int key = 0;
		try {

			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));

			int one = data[9] & 0xff;
			int two = data[12] & 0xff;
			int input = data[14] & 0xff;
			int state = data[15] & 0xff;
			Table10 t10=AtsEquipmentCache.getEquiReadOnly(one, two);
			if(t10!=null){
				t10.setPortState(1, CommandTools.switchPortIndex128To8(input), state==0?1:3);//绿色，3黄色
			}
			EventContext.logInputTrigger(one, two, input, state);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}
		return end;
	}
	
	
	
	
	public DatagramPacket getDp() {
		return dp;
	}

	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	} 
}
