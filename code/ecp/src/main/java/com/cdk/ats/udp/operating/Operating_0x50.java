package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.udp.webplug.dao.DaoFactory;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.PortFormateUtils;

/**
 * 
 * @author cdk 上传初始化信息1（输入属性） 0x50 0x01
 */
public class Operating_0x50 {
	private static Logger logger = Logger.getLogger(Operating_0x50.class);
	private DatagramPacket dp;

	private BaseDao baseDao;
	
 

	public BaseDao getBaseDao() {
		if (baseDao == null) {
			baseDao = DaoFactory.getBaseDao();
		}
		return baseDao;
	}
	public void CloseDao(){
		if(baseDao!=null){
			baseDao.closeSession();
			
		}
		
	}
	public Operating_0x50() {
	}
	public Operating_0x50(DatagramPacket dp) {
		this.dp = dp;
	}

	/****
	 * 
	 * 
	 * 
	 *  上传初始化信息1（输入属性）
	 *   **/
	public void process_0x50_0x01() {
		boolean end=true;
		byte[] data = dp.getData();
		int key = 0;
		try {

			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));
			int one ,two ,type_,inputPro,uid;
			String jiaoca,inputName;//,inputCascade1,inputCascade2,inputCascade3,inputCascade4,inputCascade5,inputCascade6,inputCascade7,inputCascade8;
			String [] in=new String[8];
			one=data[9] & 0xff;//一层设备地址
			two=data[12] & 0xff;//二层设备地址
			type_=data[14] & 0xff;//输入选择（即第几输入端口）属性[1至8]
			inputPro=data[15] & 0xff;//输入属性（对应防区属性）
			//交叉信息    ，以逗号分开，第一: 交叉输入所在的1层设备。第二：交叉输入所在的2层设备。第三：对应交叉输入在2层设备中的位置(0为禁止交叉)。
			jiaoca=(data[16] & 0xff)+","+(data[17] & 0xff)+","+(data[18] & 0xff);//+","+((data[18] & 0xff)==0?1:0);//如果交叉输入选择的值为0，则为禁止交叉
			in[0]=inputCascade(data, 19);//联动输出1
			in[1]=inputCascade(data, 24);//联动输出2
			in[2]=inputCascade(data, 29);//联动输出3
			in[3]=inputCascade(data, 34);//联动输出4
			in[4]=inputCascade(data, 39);//联动输出5
			in[5]=inputCascade(data, 44);//联动输出6
			in[6]=inputCascade(data, 49);//联动输出7
			in[7]=inputCascade(data, 54);//联动输出8
			inputName= CommandTools.byteToString(data, 59,12);//输入名称
			uid=data[71]&0xff;//隶属用户
			//String updateString="";
			Table10 equi=AtsEquipmentCache.getEqui(one, two);
			if(equi==null)return ;
			int begin=0;
			switch (type_) {
			case 1:
				//inputPro,jiaoca,inputCascade1,inputCascade2,inputCascade3,inputCascade4,inputCascade5,inputCascade6,inputCascade7,inputCascade8,inputName,uid,one,two
				equi.setField17(inputPro);
				equi.setField33(jiaoca);
				equi.setField105(inputName);
				equi.setField25(uid);
				begin =41;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				
				
	/*			  														   field17\=?, field33\=?, field41\=?, field42\=?, field43\=?, field44\=?, field45\=?, field46\=?, field47\=?, field48\=?, field105\=?, field25\=?  where field3\=? and field4\=?  
																		 //field18\=?, field34\=?, field49\=?, field50\=?, field51\=?, field52\=?, field53\=?, field54\=?, field55\=?, field56\=?,  
						ats.table10.update.0x50.0x01-3= update Table10 set field19\=?, field35\=?, field57\=?, field58\=?, field59\=?, field60\=?, field61\=?, field62\=?, field63\=?, field64\=?, field107\=?, field27\=?  where field3\=? and field4\=?
						ats.table10.update.0x50.0x01-4= update Table10 set field20\=?, field36\=?, field65\=?, field66\=?, field67\=?, field68\=?, field69\=?, field70\=?, field71\=?, field72\=?, field108\=?, field28\=?  where field3\=? and field4\=?  
						ats.table10.update.0x50.0x01-5= update Table10 set field21\=?, field37\=?, field73\=?, field74\=?, field75\=?, field76\=?, field77\=?, field78\=?, field79\=?, field80\=?, field109\=?, field29\=?  where field3\=? and field4\=?  
						ats.table10.update.0x50.0x01-6= update Table10 set field22\=?, field38\=?, field81\=?, field82\=?, field83\=?, field84\=?, field85\=?, field86\=?, field87\=?, field88\=?, field110\=?, field30\=?  where field3\=? and field4\=?  
						ats.table10.update.0x50.0x01-7= update Table10 set field23\=?, field39\=?, field89\=?, field90\=?, field91\=?, field92\=?, field93\=?, field94\=?, field95\=?, field96\=?, field111\=?, field31\=?  where field3\=? and field4\=?  
						ats.table10.update.0x50.0x01-8= update Table10 set field24\=?, field40\=?, field97\=?, field98\=?, field99\=?, field100\=?,field101\=?,field102\=?,field103\=?,field104\=?,field112\=?, field32\=?  where field3\=? and field4\=?

				*/
				//updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-1");
				break;
			case 2:
				//field18\=?, field34\=?, field49\=?, field50\=?, field51\=?, field52\=?, field53\=?, field54\=?, field55\=?, field56\=?, 
				//eld106\=?, field26\=?  where field3\=? and field4\=?  
				equi.setField18(inputPro);
				equi.setField34(jiaoca);
				equi.setField106(inputName);
				equi.setField26(uid);
				begin =49;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				break;
			case 3:
				equi.setField19(inputPro);
				equi.setField35(jiaoca);
				equi.setField107(inputName);
				equi.setField27(uid);
				begin =57;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				break;
			case 4:
				equi.setField20(inputPro);
				equi.setField36(jiaoca);
				equi.setField108(inputName);
				equi.setField28(uid);
				begin =65;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				break;
			case 5:
				equi.setField21(inputPro);
				equi.setField37(jiaoca);
				equi.setField109(inputName);
				equi.setField29(uid);
				begin =73;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				break;
			case 6:
				equi.setField22(inputPro);
				equi.setField38(jiaoca);
				equi.setField110(inputName);
				equi.setField30(uid);
				begin =81;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				break;
			case 7:
				equi.setField23(inputPro);
				equi.setField39(jiaoca);
				equi.setField111(inputName);
				equi.setField31(uid);
				begin =89;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				break;
			case 8:
				equi.setField24(inputPro);
				equi.setField40(jiaoca);
				equi.setField112(inputName);
				equi.setField32(uid);
				begin =97;//从字段开始写入，至48结束 
				for(int i=begin,j=0;i<begin+8;i++,j++)
				{MethodUtils.invokeMethod(equi, "setField" + i,in[j] );}
				break;
			}
			end=true;
			/*	switch (type_) {
				case 1:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-1");
					break;
				case 2:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-2");
					break;
				case 3:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-3");
					break;
				case 4:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-4");
					break;
				case 5:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-5");
					break;
				case 6:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-6");
					break;
				case 7:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-7");
					break;
				case 8:
					updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-8");
					break;
				}*/
				/*int count = this.getBaseDao().updateByHqlForInit(updateString,
						new Object[] {inputPro,jiaoca,inputCascade1,inputCascade2,inputCascade3,inputCascade4,inputCascade5,inputCascade6,inputCascade7,inputCascade8,inputName,uid,one,two},one,two);
				if (count > 0)
					end = true;*/
				
				
				 
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12((byte) 0x50, (byte) 0x01, data[4],
							data[5], data[6], data[7], data[9], data[10],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	}

 
	
	/**
	 * 	信息以逗号分隔！
	 *  输入联动信息格式化【联动1输出所在的1层设备,联动1输出所在的2层设备,联动1输出在2层设备中的位置,联动属性,[是否禁用，1为禁用，0为可用],联动1输出所在的2层设备所在的485线】
	 * @param data
	 * @param index
	 * @return
	 */
	private String inputCascade(byte[] data, int index) {
		StringBuffer sb = new StringBuffer();
		sb.append(data[index + 0] & 0xff).append(",").append(
				data[index + 1] & 0xff).append(",").append(
				data[index + 3] & 0xff).append(",").append(
				data[index + 4] & 0xff).append(",").append(
			    (data[index + 3] & 0xff)==0?1:0);
		//忽略：联动1输出所在的2层设备所在的485线。
			    //.append(",").append(data[index + 2] & 0xff);
		return sb.toString();
	}
	/***
	 * 设置用户密码
	 * @param begin
	 */
	private void setUserPwd(int begin){
		

		boolean end = false;
		byte[] data = dp.getData();
		int one ;
		int key = 0;
		try {
			
			one=data[9] & 0xff;//一层设备地址
			//two=data[12] & 0xff;//二层设备地址
			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));
			List<String> pwds=new ArrayList<String>();
			pwds.add(CommandTools.byteToString(data, 14,6));
			pwds.add(CommandTools.byteToString(data, 20,6));
			pwds.add(CommandTools.byteToString(data, 26,6));
			pwds.add(CommandTools.byteToString(data, 32,6));
			pwds.add(CommandTools.byteToString(data, 38,6));
			pwds.add(CommandTools.byteToString(data, 44,6));
			if(begin==1){
				pwds.add(CommandTools.byteToString(data, 50,6));
				pwds.add(CommandTools.byteToString(data, 56,6));
				pwds.add(CommandTools.byteToString(data, 62,6));
				pwds.add(CommandTools.byteToString(data, 68,6));
			}else{
				pwds.add(CommandTools.byteToString(data, 50,8));
			} 
				
			//	Table1 user=AtsEquipmentCache.getUser(one,);
			
			for (int i = 0; i < pwds.size(); i++) {
				Table1 user=AtsEquipmentCache.getUser(one,begin);
				user.setField4(pwds.get(i));
				begin++;
				if(begin==17) begin=0;
			}
		/*	String updateString =SqlConfig.SQL("ats.table1.update.0x50.setuserPwd");
			for (int i = 0; i < pwds.length; i++) {
				if (i + begin > 16)  //如果 （用户1-用户10） 则begin =1 
					begin = 0;
				else  //如果 （用户11-用户16,设备管理员 0）则 begin=11
					userIndex = begin;
				this.getBaseDao().updateByHqlForInit(updateString,new Object[] {pwds[i],one,begin},one,two);
				begin++;
				if(begin==17) begin=0;
			}*/
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	
	}
	
	/***
	 * @接收
	 * 上传初始化信息2
	 * 初始化设备用户密码（用户1-用户10）
	 */
	public void process_0x50_0x02() { setUserPwd(1);}
	/****
	 * @接收
	 * 上传初始化信息3
	 * 初始化设备用户密码（用户11-用户16,设备管理员 0）
	 */
	public void process_0x50_0x03() { setUserPwd(11);}
	/***
	 * @接收
	 * 上传初始化信息4（输入属性）
	 * 
	 */
	public void process_0x50_0x04() {
		this.process_0x50_0x03();
		
	}
	/***
	 * @接收
	 * 上传初始化信息5（输入属性  调协用户屏蔽信息）
	 */
	public void process_0x50_0x05() { 
		boolean end = false;
		byte[] data = dp.getData();
		int one ;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			//two=data[12] & 0xff;//二层设备地址
			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));
			//String updateString =SqlConfig.SQL("ats.table1.update.0x50.0x05");
			int begin_1=14,begin_2=30,begin_3=46;
			for (int i = 0; i < 16; i++) {
				Table1 user=AtsEquipmentCache.getUser(one,i+1);
				//UPDATE Table1 SET field6\=?  ,field8\=? ,field10\=?  WHERE  field11\=?  AND field12\=?
				/*user.setField6((int)data[i+begin_1]);
				user.setField8((int)data[i+begin_2]);
				user.setField10((int)data[i+begin_3]);*/
				//System.out.println("!"+((data[i+begin_1]==(byte)0x80)?0:1));
				//System.out.println(":"+((data[i+begin_1]==0x80)?0:1));
				user.setField6((data[i+begin_1]==(byte)0x80)?0:1);
				user.setField8((data[i+begin_2]==(byte)0x80)?0:1);
				user.setField10((data[i+begin_3]==(byte)0x80)?0:1);
			}
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	
		
		
		
	}
	/****
	 * @接收
	 * 上传初始化信息6（输入属性） 用户1-8
	 */
	public void process_0x50_0x06() {
		this.setUserName(1,8);
		}
	/****
	 * @接收
	 * 上传初始化信息6（输入属性） 用户9-16
	 */
	public void process_0x50_0x07() {
		this.setUserName(9,8);
		
	}
	/***
	 * 设置用户名称
	 * @param begin
	 */
	private void setUserName(int userCodeStart,int size){

		boolean end = false;
		byte[] data = dp.getData();
		int one  ;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			//two=data[12] & 0xff;//二层设备地址
			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));
			for (int i = userCodeStart,j=0; i <userCodeStart+size; i++,j++) {
				if(14+(j*10)+10>=dp.getLength()){
					break;
				}
				Table1 user=AtsEquipmentCache.getUser(one,(i==17)?0:i);
				user.setField3(CommandTools.byteToString(data, 14+(j*10),10));
				if(logger.isDebugEnabled()){
					logger.debug("用户"+userCodeStart+","+user.getField3());
				}
			}
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	
		
	}
	
	/**
	 * 设置用户电话号码 
	 * @param begin
	 */
	private void setTelNum(int userCodeStart,int size){
		boolean end = false;
		byte[] data = dp.getData();
		int one;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			key = ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			for (int i = userCodeStart,j=0; i <userCodeStart+size; i++,j++) {
			 
				if(14+(j*16)+16>=dp.getLength()){
					break;
				}
				Table1 user=AtsEquipmentCache.getUser(one,(i==17)?0:i);
				user.setField7(CommandTools.byteToTel(data, 14+(j*16),16));
				if(logger.isDebugEnabled())
					logger.debug (i+"电话："+user.getField9());
			}
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 
		}
	
		
	
		
	}
	/**
	 * 设置用户短信号码 
	 * @param begin
	 */
	private void setMsgNum(int userCodeStart,int size){
		boolean end = false;
		byte[] data = dp.getData();
		int one ;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			key = ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			for (int i = userCodeStart,j=0; i <userCodeStart+size; i++,j++) {
			 
				if(14+(j*16)+16>=dp.getLength()){
					break;
				}
				Table1 user=AtsEquipmentCache.getUser(one,(i==17)?0:i);
				user.setField9(CommandTools.byteToTel(data, 14+(j*16),16));
				if(logger.isDebugEnabled())
					logger.debug(i+"短信："+user.getField9());
			}
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	
		
	
		
	}
	
	/****
	 * @接收
	 * 上传初始化信息8（输入属性）
	 */
	public void process_0x50_0x08() {setTelNum(1,4);}
	/****
	 * @接收
	 * 上传初始化信息9（输入属性）
	 */
	public void process_0x50_0x09() {setTelNum(5,4);}
	/****
	 * @接收
	 * 上传初始化信息10（输入属性）
	 */
	public void process_0x50_0x0a() {setTelNum(9,4);}
	/****
	 * @接收
	 * 上传初始化信息11（输入属性）
	 */
	public void process_0x50_0x0b() {setTelNum(13,5);}
	/****
	 * @接收
	 * 上传初始化信息12（输入属性）
	 */
	public void process_0x50_0x0c() {setMsgNum(1,4);}
	/****
	 * @接收
	 * 上传初始化信息13（输入属性）
	 */
	public void process_0x50_0x0d() {setMsgNum(5,4);}
	/****
	 * @接收
	 * 上传初始化信息14（输入属性）
	 */
	public void process_0x50_0x0e() {setMsgNum(9,4);}
	/****
	 * @接收
	 * 上传初始化信息15（输入属性）
	 */
	public void process_0x50_0x0f() {setMsgNum(13,5);}
	/****
	 * @接收
	 * 上传初始化信息16（输入属性）
	 */
	public void process_0x50_0x10() { 

		

		boolean end = false;
		byte[] data = dp.getData();
		int one ,two ;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			two=data[12] & 0xff;//二层设备地址
			if(one==0) throw new AtsException("无效的设备地址");
			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data)); 
			Table10 equi=AtsEquipmentCache.getEqui(one, two);
			
			String 	tel1=CommandTools.byteToTel(data, 14, 16);//字节14-29	接警中心号码1	接警中心号码1						
			equi.setTelNum1(tel1);
			String 	tel2=CommandTools.byteToTel(data, 30, 16);//字节30-45	接警中心号码2	接警中心号码2
			equi.setTelNum2(tel2);
			
			/*int t_46=(data[46]==(byte)0xff?1:0);//字节46	接警中心号码1屏蔽标志位	接警中心号码1屏蔽标志位（0xff代表屏蔽，0x80代表启用）
			if(t_46==1)equi.setTelNum1("");
			int t_47=(data[47]==(byte)0xff?1:0);//字节47	接警中心号码2屏蔽标志位	接警中心号码2屏蔽标志位（0xff代表屏蔽，0x80代表启用）
			if(t_47==1)equi.setTelNum2("");
			*/
			
			int t_48=(data[48]==(byte)0xff?1:0);//字节48	电话通讯禁止位	电话通讯禁止位（0xff代表屏蔽=1，0x80代表启用=0）
			equi.setDisableTel(t_48);
			int t_49=(data[49]==(byte)0xff?1:0);//字节49	短信通讯禁止位	短信通讯禁止位（0xff代表屏蔽，0x80代表启用）
			equi.setDisableMessage(t_49);
			
			StringBuffer ps=new StringBuffer();
			int t_50=(data[50]==(byte)0x01?1:0);//字节50	打印机禁止位	打印机禁止位（0x01代表屏蔽，0x02代表启用）
				ps.append(t_50).append(",");			 
			int t_51=(data[51]==(byte)0x01?1:0);//字节51	打印类容报警事件禁止位	打印类容报警事件禁止位（0x01代表屏蔽，0x02代表启用）
				ps.append(t_51).append(",");
			int t_52=(data[52]==(byte)0x01?1:0);//字节52	打印类容普通事件禁止位	打印类容普通事件禁止位（0x01代表屏蔽，0x02代表启用）
				ps.append(t_52).append(",");
			int t_53=(data[53]==(byte)0x01?1:0);//字节53	打印类容用户操作事件禁止位	打印类容用户操作事件禁止位（0x01代表屏蔽，0x02代表启用）
				ps.append(t_53);
			 
				equi.setPrintSet(ps.toString());
			int t_54=CommandTools.byteToInt(data, 54, 2);//字节54-55	电话通讯时间间隔	电话通讯时间间隔（分钟）0-65535分钟
			equi.setTelSpace(t_54);
			int t_56=CommandTools.byteToInt(data, 56, 2);//字节56-57	短讯通讯时间间隔	短讯通讯时间间隔（分钟）0-65536分钟
			equi.setMessageSpace(t_56);
			//String updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-1");
			//this.getBaseDao().updateByHqlForInit(updateString,new Object[] {tel1,tel2,t_46,t_47,t_48,t_49,t_50,t_51,t_52,t_53,t_54,t_56,one,two},one,two);
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	
	
	}
	

	/***
	 * 设置设备屏蔽
	 * @param begin
	 */
	private void setShielded(int begin){
		boolean end = false;
		byte[] data = dp.getData();
		int one ;//,two ;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			//two=data[12] & 0xff;//二层设备地址
			key = ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
		 
			//String updateString =SqlConfig.SQL("ats.table10.update.0x50.0x01-1");
			for (int i = 14; i < 78; i++) {
				Table10 equi=AtsEquipmentCache.getEquiReadOnly(one, (begin++));
				if(equi!=null){
					//0x01=开启，0x02=屏蔽
					//对应数据库中的存储，2为屏蔽，1为开启
					/****
					 * 如果这个设备当前的状态不是正常状态，则说明这个设备没有被正常上报，而是以前遗留的掉线设备。
					 * 如果它的状态不是正常状态，则忽略他是否属于屏蔽状态。
					 */
					if(Integer.valueOf(Constant.EQUIPMENT_STATUS_NORMAL_VAL).equals(equi.getField130())&&data[i]==0x02){
						equi.setField130(Constant.EQUIPMENT_STATUS_DISABLE_VAL);
					}else if(Integer.valueOf(Constant.EQUIPMENT_STATUS_NORMAL_VAL).equals(equi.getField130())&&data[i]==0x01){
						equi.setField130(Constant.EQUIPMENT_STATUS_NORMAL_VAL);
					}else if(!Integer.valueOf(Constant.EQUIPMENT_STATUS_NORMAL_VAL).equals(equi.getField130())){
						if(data[i]==0x01)
						equi.setField130(Constant.EQUIPMENT_STATUS_NORMAL_VAL);
						else if(data[i]==0x02)
						equi.setField130(Constant.EQUIPMENT_STATUS_DISABLE_VAL);
					}
					//System.out.println("屏蔽："+equi.getField3()+"-"+equi.getField4()+","+equi.getField130());
				}
				  //this.getBaseDao().updateByHqlForInit(updateString,new Object[] {(data[i]==(byte)0x01?0:1),one,(begin++)},one,(begin++));
			}
			 
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	}
	
	
	/***
	 * @接收
	 * 上传初始化信息17（输入属性）
	 */
	public void process_0x50_0x11() {setShielded(0);}
	/***
	 * @接收
	 * 上传初始化信息18（输入属性）
	 */
	public void process_0x50_0x12() {setShielded(64);}
	/***
	 * @接收
	 * 上传初始化信息19（输入属性）
	 */
	public void process_0x50_0x13() {setShielded(128);}
	/***
	 * @接收
	 * 上传初始化信息20（输入属性）
	 */
	public void process_0x50_0x14() {setShielded(192);}
	/***
	 * @接收
	 * 上传初始化信息21（输入属性）
	 */
	public void process_0x50_0x15() {
		boolean end = false;
		byte[] data = dp.getData();
		int one ,two,index;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			two=data[12] & 0xff;//二层设备地址
			index=data[26] & 0xff;//输出端口编号 （1-8）
			
			if(one==0) throw new AtsException("无效的设备地址");
			key = ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			//int fieldNum=112;
			String outPutName=CommandTools.byteToString(data, 14,12);
		//	String updateString =SqlConfig.SQL("", "field", "field"+(fieldNum+(int)data[26]));
			//this.getBaseDao().updateByHqlForInit(updateString,new Object[] {outPutName,one,two},one,two);
			Table10 equi=AtsEquipmentCache.getEqui(one, two);
			System.out.println(index+":,:"+outPutName);
			switch (index) {
			case 1:
				equi.setField113(outPutName);
				break;

			case 2:
				equi.setField114(outPutName);
				break;

			case 3:
				equi.setField115(outPutName);
				break;

			case 4:
				equi.setField116(outPutName);
				break;

			case 5:
				equi.setField117(outPutName);
				break;

			case 6:
				equi.setField118(outPutName);
				break;

			case 7:
				equi.setField119(outPutName);
				break;

			case 8:
				equi.setField120(outPutName);
				break;

			}
			end=true;
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	}
	/***
	 * @接收
	 * 上传初始化信息22（输入属性）
	 */
	public void process_0x50_0x16() {
		

		boolean end = false;
		byte[] data = dp.getData();
		int one ,two ;
		int key = 0;
		try {
			one=data[9] & 0xff;//一层设备地址
			two=data[12] & 0xff;//二层设备地址
			if(one==0) throw new AtsException("无效的设备地址");
			key = ReceiverContext.putReceiveSequnce(CommandTools
					.getSequnces(data));
			 
			String id= CommandTools.byteToString(data, 14,8);
			String seq= CommandTools.byteToString(data, 22,11);
			String name= CommandTools.byteToString(data, 35,12);
			int  parentType=data[33];
			int  childType=(data[34]&0xff);
			
			Table10 equi=AtsEquipmentCache.getEqui(one, two);
			equi.setField130(Constant.EQUIPMENT_STATUS_NORMAL_VAL);
			if(name==null||StringUtils.isBlank(name)){
				if(two==0){
					name="一层设备"+one;
				}else{
					name="二层设备"+one+"-"+two;
					
				}
				
				
			}
			equi.setField121(name);
			equi.setField8(id);
			equi.setField9(seq);
			if(two>0){
			equi.setDtype(childType);
			int[] cnt=PortFormateUtils.getPortCount(equi.getDtype());
			equi.setInCount(cnt[0]);
			equi.setOutCount(cnt[1]);
			equi.setPortState(1, equi.getInCount());
			equi.setPortState(2, equi.getOutCount());
			}
			//输入端口数量 与输出端口数量   暂时不保存
			//String updateString =SqlConfig.SQL("");
			//this.getBaseDao().updateByHqlForInit(updateString,new Object[] {id,seq,name,inputCount,outputCount,one,two},one,two);
			
			
			end=true;
			//LogContent.times(4);
			//LogContent.times(System.currentTimeMillis());
		} catch (Exception e) {
			end=false;
			logger.error(e.getMessage());
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			//LogContent.times(10005);
			//LogContent.times(System.currentTimeMillis());
			TransmitterContext.sendResponseDatagram(end, key, dp);
			//LogContent.times(10006);
			//LogContent.times(System.currentTimeMillis());
/*			 TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12(cmd1, cmd2, data[4],
							data[5], data[6], data[7], data[8], data[9],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/ 
		}
	
	}
	 
 
	
 
}
