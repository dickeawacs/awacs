package com.cdk.ats.udp.event;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.udp.webplug.dao.DaoFactory;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.EventRecord;
import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.pojo.hbm.Table10;
import common.cdk.config.files.sqlconfig.SqlConfig;

public class EventContext {


	private static Logger logger = Logger.getLogger(EventContext.class);
	private static BaseDao baseDao = null;

	private static BaseDao getBaseDao() {
		if (baseDao == null)
			baseDao = DaoFactory.getBaseDao();
		return baseDao;
	}

	/**
	 * 
	 * 描述：关闭session
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-28
	 * @lastUpdate 2013-12-28
	 */
	public static void distory() {
		getBaseDao().closeSession();

	}
	 

	/******
	 * 
	 * 描述：
	 * @createBy dingkai
	 * @createDate 2013-12-29
	 * @lastUpdate 2013-12-29
	 * @param one  一层设备网络地址
	 * @param two 二层设备网络地址
	 * @param uid 用户ID
	 * @param type 事件类型
	 * @param eventName 类型名称
	 * @param input  输入端口或输出端口
	 */
	private  static void logBase(EventRecord record) {
		try {
			record.setEventTime(new Timestamp(System.currentTimeMillis()));
			getBaseDao().save(record);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
/***
 * 
 * 描述： 获取一个当前的用户
 * 若不存在 则从数据库中查询 
 * @createBy dingkai
 * @createDate 2014-1-1
 * @lastUpdate 2014-1-1
 * @param one
 * @param uid
 * @return
 */
	public static  Table1 getUser(int one,int uid){
		Table1 user=AtsEquipmentCache.getUserReadOnly(one, uid);
		if(user==null){
			user=(Table1) getBaseDao().findOnlyByHql(SqlConfig.SQL("ats.table1.query.0x10.0x05"), new Object[]{one,uid});
		}
		return user;
	}
	/***
	 * 
	 * 描述：根据设备ID。获取设备，若缓存中没有则去数据库查询。
	 * 当two=0时，查询的是一层设备
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two
	 * @return
	 */
	public  static Table10 getEquipment(int one, int two) {
		Table10 equipment=AtsEquipmentCache.getEquiReadOnly(one,two);
		if(equipment==null){
			equipment=(Table10) getBaseDao().findOnlyByHql(SqlConfig.SQL("ats.system.query.Table10.by"), new Object[]{one,two});
		}
		return equipment;
	}
	/**
	 * 
	 * 描述： 用户操作
	 * 根据ID。查找设备，获取设备名称、端口名称、获取用户信息
	 * @createBy dingkai
	 * @createDate 2013-12-29
	 * @lastUpdate 2013-12-29
	 */
	public static void logUserAction(int one, int uid,int action){
		int typeVal=0;
		String eventName=null;
		/**0x01为布防，0x02为撤防，0x03为确认*/
		switch (action) {
		case 1:
			eventName=Constant.USER_JOB;
			typeVal=Constant.USER_JOB_;
			break;
		case 2:
			eventName=Constant.USER_RETREAT;
			typeVal=Constant.USER_RETREAT_;
			break;
		case 3:
			eventName=Constant.USER_OK; 
			typeVal=Constant.USER_OK_; 
			break;
		default:
			eventName=Constant.SEND_STATE_ERROR;
			break;
		}		
		logAction(Constant.USER_ACTION,one, 0, uid, eventName, "用户",typeVal);
		
	};
	/*** 
	 * 描述： 管理 员操作
	 *  
	 * 描述：
	 * @createBy dingkai
	 * @createDate 2013-12-31
	 * @lastUpdate 2013-12-31
	 * @param one 
	 * @param two
	 * @param uid
	 * @param userName
	 * @param eventDesc
	 * @param eventTerminal
	 */
	public static void logAdminAction(int one, int uid,int action){
		String eventName=null;
		int typeVal=0;
		switch (action) {
		case 1:
			eventName=Constant.ADMIN_JOB;
			typeVal=Constant.ADMIN_JOB_;
			break;
		case 2:
			eventName=Constant.ADMIN_RETREAT;
			typeVal=Constant.ADMIN_RETREAT_;
			break;
		case 3:
			eventName=Constant.ADMIN_OK;
			typeVal=Constant.ADMIN_OK_;
			break;
		case 4:
			eventName=Constant.ADMIN_DEVELOP;
			typeVal=Constant.ADMIN_DEVELOP_;
			break;
		default:
			eventName=Constant.SEND_STATE_ERROR;
			break;
		}		
		logAction(Constant.ADMIN_ACTION,one, 0, uid, eventName, "管理员",typeVal);
	}
	/**
	 * 
	 * 描述： 用户操作
	 * 根据ID。查找设备，获取设备名称、端口名称、获取用户信息
	 * @createBy dingkai
	 * @createDate 2013-12-29
	 * @lastUpdate 2013-12-29
	 */
	private static void logAction(int type,int one, int two, int uid,String  eventName,String role,int typeVal ){
		EventRecord record=new EventRecord();
		record.setEquipmentFid(one);
		record.setEquipmentSid(two); 
		Table1 user=getUser(one,uid);
		Table10 equipment=getEquipment(one,two);
		if(equipment!=null&&user!=null){
		StringBuffer desc=new StringBuffer();
		desc.append(equipment.getField121()).append(role).append(user.getField3()).append(eventName);
		record.setProcessBy(user.getField3());
		record.setProcessUid(uid);
		record.setEventDesc(desc.toString());
		record.setEventTerminal(one+"一层设备 ");
		record.setEventType(type);
		record.setEventTypeVal(typeVal);
		logBase(record);
		}else{
			
			System.out.println("equipment!=null:"+equipment!=null);
			System.out.println("&&user!=null:"+user!=null);
		}
		//logBase(one, two, uid, Constant.ADMIN_ACTION, eventDesc, eventTerminal,userName);
		
	}

	
	/***
	 * 
	 * 描述： 设备被撬
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two
	 * @param state
	 */
	public static int logFaultLose(int one, int two, int state){
		String eventName=null;
		int typeVal=0;
		if(state==0x00){
			eventName=Constant.EQUIPMENT_FAULT_FIND;
			typeVal=Constant.EQUIPMENT_FAULT_FIND_;
		}else if(state==0xff){
			eventName=Constant.EQUIPMENT_FAULT_LOSE;
			typeVal=Constant.EQUIPMENT_FAULT_LOSE_;
		} else  {//异常
			eventName=Constant.CANT_FOUND_STATE;
		}
		logFaultAction(one, two, state, eventName,typeVal);
		return Constant.portStateValue(typeVal);
	}
	/***
	 * 
	 * 描述： 设备低压
	 * 设备电压信息：0x00代表低电压报警，0xff代表高电压报警，0x80代表电压正常。
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two 
	 * @param state
	 */
	public static int logFaultLow(int one, int two, int state){
		String eventName=null;
		int typeVal=0;
		if(state==0x00){
			eventName=Constant.EQUIPMENT_FAULT_LOW_POWER;//"低压报警";
			typeVal=Constant.EQUIPMENT_FAULT_LOW_POWER_;//"低压报警";
		}else if(state==0xff){
			eventName=Constant.EQUIPMENT_FAULT_UPPER_POWER;
			typeVal=Constant.EQUIPMENT_FAULT_UPPER_POWER_;
		}else if(state==0x80){
			eventName=Constant.EQUIPMENT_FAULT_LOW_NORMAL;
			typeVal=Constant.EQUIPMENT_FAULT_LOW_NORMAL_;
		} else  {//异常
			eventName=Constant.CANT_FOUND_STATE;
		}
		logFaultAction(one, two, state, eventName,typeVal);
		return Constant.portStateValue(typeVal);
	}
	/***
	 * 
	 * 描述： 设备连接故障
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two 
	 * @param state
	 */
	public static int logFaultConnection(int one, int two,int state){
		String eventName=null;
		 int typeVal=0;
		if(state==0x00){
			eventName=Constant.EQUIPMENT_FAULT_CONNECTED;
			typeVal=Constant.EQUIPMENT_FAULT_CONNECTED_;
		}else if(state==0xff){
			eventName=Constant.EQUIPMENT_FAULT_CONNECT_FAULT;
			typeVal=Constant.EQUIPMENT_FAULT_CONNECT_FAULT_;
		} else  {//异常
			eventName=Constant.CANT_FOUND_STATE;
		}
		logFaultAction(one, two, state, eventName,typeVal);
		return Constant.portStateValue(typeVal);
	}
	/***
	 * 
	 * 描述： 故障类事件 
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two
	 * @param uid
	 * @param userName
	 * @param eventDesc
	 * @param eventTerminal
	 */
	private  static void logFaultAction(int one, int two,int state,String eventName,int typeVal){
		 
		EventRecord record=new EventRecord();
		record.setEquipmentFid(one);
		record.setEquipmentSid(two); 
		
		Table10 one_equipment=getEquipment(one,0);
		Table10 two_equipment=null;
		if(two!=0){
			two_equipment=getEquipment(one,two);
		}
		
		StringBuffer desc=new StringBuffer();
		//设备名称
		desc.append(one_equipment.getField121());

		StringBuffer eventTerminal=new StringBuffer();
		//终端 一层设备
		eventTerminal.append(one).append("一层设备");
		//record.setEventTerminal(eventTerminal.toString());
		
		if(two_equipment!=null){
			//终端二层设备，端口描述
			eventTerminal.append(two).append("二层设备");
			//desc.append("｛").append(two_equipment.getField121()).append("｝");
			if(one_equipment.getField121()!=null){
				desc.append(two_equipment.getField121());
			}else{
				desc.append(one_equipment.getField121()!=null?one_equipment.getField121():one+"一层设备");
				desc.append(two_equipment.getField121()!=null?two_equipment.getField121():two+"二层设备");
			}
		} else{
			desc.append(one_equipment.getField121()!=null?one_equipment.getField121():one+"一层设备");
			
		}
		desc.append(eventName);
		record.setEventDesc(desc.toString());
		record.setEventType(Constant.EQUIPMENT_FAULT);
		record.setEventTypeVal(typeVal);
		record.setEventTerminal(eventTerminal.toString());
		logBase(record);
		
	}

	
	/***
	 * 
	 * 描述： 报警 事件 
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two
	 * @param uid
	 * @param userName
	 * @param eventDesc
	 * @param eventTerminal
	 */
	public static int logAlarmAction(int one, int two,int input, int state){
	 
		
		String eventName=null;
		int typeVal=0;
		EventRecord record=new EventRecord();
		record.setEquipmentFid(one);
		record.setEquipmentSid(two); 
		record.setPtype(1);
		//获取是第几个端口
		int tage=CommandTools.getByteTag(input);
		record.setPort(tage+1);
		//对应端口的值 
		int val=CommandTools.getByteTag(state,tage);
		
		
		
		Table10 one_equipment=getEquipment(one,0);
		Table10 two_equipment=null;
		if(two!=0){
			two_equipment=getEquipment(one,two);
		}
		//终端描述
		StringBuffer eventTerminal=new StringBuffer();
		StringBuffer desc=new StringBuffer();
		if(two_equipment!=null){
			
			/***
			 * 直接显示设备端口名称
			 */
			desc.append(two_equipment.findPortInputName(tage));
			int temp_val=two_equipment.loadPortInputValue(tage);
			eventName=alarmName(temp_val);
			typeVal=alarmName_(temp_val);
			eventTerminal.append(one).append("一层设备").append(two).append("二层设备").append("输入端口").append(tage+1);
			 
		}else{
			int temp_val=one_equipment.loadPortInputValue(tage);
			eventName=alarmName(temp_val);
			typeVal=alarmName_(temp_val);
			eventTerminal.append(one).append("一层设备").append("输入端口").append(tage+1);
			desc.append(one_equipment.findPortInputName(tage));
		}
		
		//对应的位等0则说明恢复正常
		if(val==0){
			eventName=Constant.ALARM_NORMAL;
			typeVal=Constant.ALARM_NORMAL_;
		}else if(val==-1){//异常
			eventName=Constant.CANT_FOUND_PORT;
			typeVal=-1;
		}
		desc.append(eventName);
		
		record.setEventDesc(desc.toString());
		record.setEventType(Constant.ALERM);
		record.setEventTypeVal(typeVal);
		record.setEventTerminal(eventTerminal.toString());
		logBase(record);
		return Constant.portStateValue(typeVal);
	}
	

	/***
	 * 
	 * 描述：0x02	0x07  输入触发
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two
	 * @param input
	 * @param state
	 */
	public  static int logInputTrigger(int one, int two,int input, int state){
		String eventName=null;
		int typeVal=0;
		//获取是第几个端口
		int tage=CommandTools.getByteTag(input);
		//对应端口的值 
		int val=CommandTools.getByteTag(state,tage);
		//对应的位等0则说明恢复正常
		if(val==0){
			eventName=Constant.PORT_STATE_INPUT_RESTORE;
			typeVal=Constant.PORT_STATE_INPUT_RESTORE_;
		}else if(val==1){
			eventName=Constant.PORT_STATE_INPUT_TRIGGER;
			typeVal=Constant.PORT_STATE_INPUT_TRIGGER_;
		}else if(val==-1){//异常
			eventName=Constant.CANT_FOUND_PORT;
		}
		
		logInputStateAction(one, two, input, state, eventName,typeVal);
		return Constant.portStateValue(typeVal);
	}
	
	/***
	 * 
	 * 描述： 0x02	0x06  输入异常
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two
	 * @param input
	 * @param state
	 */
	public  static int logInputException(int one, int two,int input, int state){
		String eventName=null;
		
		//获取是第几个端口
		int tage=CommandTools.getByteTag(input);
		//对应端口的值 
		int val=CommandTools.getByteTag(state,tage);
		
		int typeVal=0;
		//对应的位等0则说明恢复正常
		if(val==0){
			eventName=Constant.PORT_STATE_INPUT_RESTORE;
			typeVal=Constant.PORT_STATE_INPUT_RESTORE_;
		}else if(val==1){
			eventName=Constant.PORT_STATE_INPUT_EXCEPTION;
			typeVal=Constant.PORT_STATE_INPUT_EXCEPTION_;
		}else if(val==-1){//异常
			eventName=Constant.CANT_FOUND_PORT;
		}
		
		logInputStateAction(one, two, input, state, eventName,typeVal);
		return Constant.portStateValue(typeVal);
	}
	
	
	/**
	 * 
	 * 描述： 输入状态
	 * @createBy dingkai
	 * @createDate 2013-12-30
	 * @lastUpdate 2013-12-30
	 * @param one
	 * @param two   	
	 * @param input   端口标记
	 * @param state   触发标记  0/1  0-恢复，1触发
	 * @param eventName 事件类型名称 
	 */
	private  static void logInputStateAction(int one, int two,int input, int state,String eventName,int typeVal){
		 
		EventRecord record=new EventRecord();
		record.setEquipmentFid(one);
		record.setEquipmentSid(two); 
		record.setPtype(1);
		//获取是第几个端口
		int tage=CommandTools.getByteTag(input);
		record.setPort(tage+1);
		Table10 one_equipment=getEquipment(one,0);
		Table10 two_equipment=null;
		if(two!=0){
			two_equipment=getEquipment(one,two);
		}
		
		StringBuffer desc=new StringBuffer();
		
		StringBuffer eventTerminal=new StringBuffer(); 
		if(two_equipment!=null){
			//终端二层设备，端口描述
			eventTerminal.append(one).append("一层设备");
			eventTerminal.append(two).append("二层设备").append("输入端口").append(tage+1);
			//输入端口名称 
			desc.append(two_equipment.findPortInputName(tage));
		}else{			//输入端口名称 
			desc.append(one_equipment.findPortInputName(tage));
			//终端二层设备，端口  
			eventTerminal.append(one).append("一层设备");
			eventTerminal.append("输入端口").append(tage+1);
		}
		
		desc.append(eventName);
		
		record.setEventDesc(desc.toString());
		record.setEventType(Constant.PORT_STATE);
		record.setEventTypeVal(typeVal);
		record.setEventTerminal(eventTerminal.toString());
		logBase(record);
		
	}
	/***
	 * 
	 * 描述： 输出闭合	输出断开
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param two
	 * @param input
	 * @param state
	 */
	public   static int logOutputClose(int one, int two,int input, int state){
		String eventName=null;
		//获取是第几个端口
		int tage=CommandTools.getByteTag(input);
		//对应端口的值 
		int val=CommandTools.getByteTag(state,tage);
		int typeVal=0;
		//对应的位等0则说明恢复正常
		if(val==0){
			eventName=Constant.PORT_STATE_OUTPUT_CLOSE;
			typeVal=Constant.PORT_STATE_OUTPUT_CLOSE_;
		}else if(val==1){
			eventName=Constant.PORT_STATE_OUTPUT_OPEN;
			typeVal=Constant.PORT_STATE_OUTPUT_OPEN_;
		}
		else if(val==-1){//异常
			eventName=Constant.CANT_FOUND_PORT;
		}
		logOutputStateAction(one, two, input, state, eventName,typeVal);
		return Constant.portStateValue(typeVal);
	}
	
	/**
	 * 
	 * 描述： 输出状态：｛输出闭合,输出断开｝
	 * @createBy dingkai
	 * @createDate 2013-12-30
	 * @lastUpdate 2013-12-30
	 * @param one
	 * @param two   	
	 * @param input   端口标记
	 * @param state   触发标记  0/1  0-恢复，1触发
	 * @param eventName 事件类型名称 
	 */
	private  static void logOutputStateAction(int one, int two,int input, int state,String eventName,int typeVal){
		 
		EventRecord record=new EventRecord();
		record.setEquipmentFid(one);
		record.setEquipmentSid(two); 
		
		//获取是第几个端口
		int tage=CommandTools.getByteTag(input);
		record.setPort(tage+1);
		record.setPtype(2);
		Table10 one_equipment=getEquipment(one,0);
		Table10 two_equipment=null;
		if(two!=0){
			two_equipment=getEquipment(one,two);
		}
		
		StringBuffer desc=new StringBuffer();
		StringBuffer eventTerminal=new StringBuffer();

		eventTerminal.append(one).append("一层设备"); 
		
		if(two_equipment!=null){
			eventTerminal.append(two).append("二层设备").append("输出端口").append(tage+1);
			desc.append(two_equipment.loadPortOutputName(tage));
		}else{			 
			desc.append(one_equipment.loadPortOutputName(tage));
			eventTerminal.append("输出端口").append(tage+1);
		}
		
		desc.append(eventName);
		record.setEventDesc(desc.toString());
		record.setEventType(Constant.PORT_STATE);
		record.setEventTypeVal(typeVal);
		record.setEventTerminal(eventTerminal.toString());
		logBase(record);
		
	}
	
	/**
	 * 
	 * 描述：根据报警的类型返回描述
	 * @createBy dingkai
	 * @createDate 2013-12-29
	 * @lastUpdate 2013-12-29
	 * @param type
	 * @return
	 */
	public static String alarmName(int type) {
		switch (type) {
		case 2:
			return Constant.ALARM_POSITION;
		case 3:
			return Constant.ALARM_24H;

			
		case 4:
			return Constant.ALARM_FIRE;

		case 5:
			return Constant.ALARM_HELP;

		case 6:
			return Constant.ALARM_GAS; 
		default:
			return Constant.CANT_FOUND_STATE;
		}

	} 
	/**
	 * 
	 * 描述： ：根据报警的类型返回描述
	 * @createBy dingkai
	 * @createDate 2014-2-25
	 * @lastUpdate 2014-2-25
	 * @param type
	 * @return
	 */
	public static int alarmName_(int type) {
		switch (type) {
		case 2:
			return Constant.ALARM_POSITION_;
		case 3:
			return Constant.ALARM_24H_;

			
		case 4:
			return Constant.ALARM_FIRE_;

		case 5:
			return Constant.ALARM_HELP_;

		case 6:
			return Constant.ALARM_GAS_; 
		default:
			return 0;
		}

	} 
	
}
