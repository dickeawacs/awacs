package com.cdk.ats.udp.utils;

import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.web.pojo.hbm.Table10;

/***
 * 
 * @author dingkai 用来记录常用的常量
 */
public class Constant {

	private static final Logger logger = Logger.getLogger(Constant.class);
	/**
	 * 端口的属性键
	 */
	public  static final String ATS_PORT_KEY="ats.port";
	/**
	 * IP
	 */
	public static final String ATS_IP_KEY="ats.ip";
	
	/**
	 * 系统当前使用的端口号，它由UdpListener.contextInitialized(ServletContextEvent)中设置 
	 */
	public static  String SYS_NOW_PORT="";
	/***
	 * 用来记录当前端口是否可用，它由UdpListener.contextInitialized(ServletContextEvent)中设置 
	 * 代码：800=正常，805=系统端口被占用，810=系统无法获取本地IP
	 */
	public static  int SYS_PORT_CAN_USE=800;
	private DatagramSocket dataSocket = null;

	public DatagramSocket getDataSocket() throws SocketException {
		if (dataSocket == null) {
				dataSocket = new DatagramSocket();
		}
		return dataSocket;
	}

	public void setDataSocket(DatagramSocket dataSocket) {
		this.dataSocket = dataSocket;
	}

	private static Constant uniqueInstance = null;

	private Constant() {
		try {
			dataSocket = new DatagramSocket();
		} catch (SocketException e) {
			logger.error(logger);
		}
	}

	public static Constant getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Constant();
		}
		return uniqueInstance;
	}

	
/***
 * 
 * 描述： 端口的状态值，1-绿色（正常），2-红色（报警），3-黄色（触发），-1-灰色（无效设备）
 * @createBy dingkai
 * @createDate 2014-4-5
 * @lastUpdate 2014-4-5
 * @param val
 * @return
 */
	public static int portStateValue(int typeVal){
		if((typeVal>7&&typeVal<13)||typeVal==14||typeVal==16||typeVal==18||typeVal==19||typeVal==20){
    		/**
    		 * 红色，黑字
    		 */
    		return 2;
    	}
    	else if((typeVal>21&&typeVal<27)){
    	/**
    	 * 黄底，黑字
    	 */
    		return 3;
    	}else if(typeVal==21||typeVal==17||typeVal==15||typeVal==13){
    		/***
        	 * 绿底，白字
        	 */
    		return 1;
    	}
		return -1;
	}
	
	/***
	 * 
	 * 描述：检查设备是否可用
	 * @createBy dingkai
	 * @createDate 2014-4-6
	 * @lastUpdate 2014-4-6
	 * @param one
	 * @param two
	 * @throws AtsException
	 */
	public static void checkUsed(int one,int two )throws AtsException{
		if(two!=0){checkUsed(one, 0);}
		Table10 t10=AtsEquipmentCache.getEquiReadOnly(one,two);
		if(t10!=null){
			if(Integer.valueOf(Constant.EQUIPMENT_STATUS_LOSE_VAL).equals(t10.getField130())){
				throw new AtsException("不可操作：设备 "+Constant.EQUIPMENT_STATUS_LOSE_TEXT);
			}else if(Integer.valueOf(Constant.EQUIPMENT_STATUS_DISABLE_VAL).equals(t10.getField130())){
				throw new AtsException("不可操作：设备 "+Constant.EQUIPMENT_STATUS_DISABLE_TEXT);
			}
		}else{
			throw new AtsException("不可操作：设备不存在 ");
		}
		//throw new AtsException("无效的设备！");
		
	}
	/**
	 * 1系统管理 员
	 */
	public static final int ROLE_SYS_ADMIN = 1;
	/**
	 * 2管理 员
	 */
	public static final int ROLE_ADMIN = 2;
	/***
	 * 3普通设备用户
	 */
	public static final int ROLE_USER = 3;
	/*****
	 * 设备的状态
	 */
	/*****
	 * 2-已屏蔽（设备状态）
	 */
	public static String EQUIPMENT_STATUS_DISABLE = "2";
	/*****
	 * 2-已屏蔽（设备状态）
	 */
	public static int EQUIPMENT_STATUS_DISABLE_VAL = 2;

	public static String EQUIPMENT_STATUS_DISABLE_TEXT = "屏蔽";

	/*****
	 * 
	 * 3-掉线（设备状态）
	 * 
	 */
	public static String EQUIPMENT_STATUS_LOSE = "3";
	/*****
	 * 
	 * 3-掉线（设备状态）
	 * 
	 */
	public static int EQUIPMENT_STATUS_LOSE_VAL = 3;
	public static String EQUIPMENT_STATUS_LOSE_TEXT = "掉线";
	
	/*****
	 * 1-正常（设备状态）
	 */
	public static String EQUIPMENT_STATUS_NORMAL = "1";
	/*****
	 * 1-正常（设备状态）
	 */
	public static int EQUIPMENT_STATUS_NORMAL_VAL = 1;
	
	public static String EQUIPMENT_STATUS_NORMAL_TEXT = "正常";

	/***
	 * 设备类型\ 1- 一层设备
	 */
	public static int EQUIPMENT_TYPE_ONE = 1;
	/***
	 * 设备类型 2- 二层设备
	 */
	public static int EQUIPMENT_TYPE_TWO = 2;

	/**
	 * 统计 select count(1) from com.cdk.ats.web.pojo.hbm.EventView e
	 */

	/*** 1 管理员操作 ***/
	public static int ADMIN_ACTION = 1;
	/*** 1 1.1用户/管理员布防 0x20 0x01 ***/
	public static String ADMIN_JOB = "管理员布防";
	public static int ADMIN_JOB_ = 1;

	/*** 2 用户操作 1.2 用户/管理员撤防 0x20 0x01 ***/
	public static String ADMIN_RETREAT = "管理员撤防";
	public static int ADMIN_RETREAT_ = 2;

	/*** 3 用户操作 1.3 用户/管理员确认 0x20 0x01 ***/
	public static String ADMIN_OK = "管理员处理";
	public static int ADMIN_OK_ = 3;

	/*** 4 用户操作 1.4 管理员编程 0x20 0x01 ***/
	public static String ADMIN_DEVELOP = "管理员编程";
	public static int ADMIN_DEVELOP_ = 4;

	/*** 2用户操作 ***/
	public static int USER_ACTION = 2;

	/*** 5用户操作 2.1 用户布防 0x20 0x01 ***/
	public static String USER_JOB = "用户布防";
	public static int USER_JOB_ = 5;

	/*** 6 用户操作 2.2 用户撤防 0x20 0x01 ***/
	public static String USER_RETREAT = "用户撤防";
	public static int USER_RETREAT_ = 6;

	/*** 7 用户操作 2.3 用户确认 0x20 0x01 ***/
	public static String USER_OK = "用户处理";
	public static int USER_OK_ = 7;

	/***
	 * 3 报警
	 */
	public static int ALERM = 3;
	/*** 8 报警 3.1 防区报警 0x02 0x04 ***/
	public static String ALARM_POSITION = "报警";
	public static int ALARM_POSITION_ = 8;

	/*** 9 报警 3.2 火警 0x02 0x04 ***/
	public static String ALARM_FIRE = "火警";
	public static int ALARM_FIRE_ = 9;

	/*** 10 报警 3.3 紧急求助 0x02 0x04 ***/
	public static String ALARM_HELP = "紧急求助";
	public static int ALARM_HELP_ = 10;

	/*** 11 报警 3.4 燃气 0x02 0x04 ***/
	public static String ALARM_GAS = "燃气报警";
	public static int ALARM_GAS_ = 11;

	/*** 12 报警 3.5 24小时防区 0x02 0x04 ***/
	public static String ALARM_24H = "报警";
	public static int ALARM_24H_ = 12;

	/*** 13 报警 3.6 报警恢复 0x02 0x04 ***/
	public static String ALARM_NORMAL = "报警恢复";
	public static int ALARM_NORMAL_ = 13;

	/***
	 * 4 设备故障
	 */
	public static int EQUIPMENT_FAULT = 4;
	/*** 14 4 设备故障 4.1 设备被撬 0x02 0x03 ***/
	public static String EQUIPMENT_FAULT_LOSE = "设备被撬";
	public static int EQUIPMENT_FAULT_LOSE_ = 14;

	/*** 15 设备故障 4.2 设备被撬恢复 0x02 0x03 ***/
	public static String EQUIPMENT_FAULT_FIND = "被撬恢复";
	public static int EQUIPMENT_FAULT_FIND_ = 15;

	/*** 16 设备故障 4.3 设备欠压 0x02 0x01 ***/
	public static String EQUIPMENT_FAULT_LOW = "设备欠压";
	public static int EQUIPMENT_FAULT_LOW_ = 16;

	/*** 17 设备故障 4.4 电压恢复 0x02 0x01 ***/
	public static String EQUIPMENT_FAULT_LOW_NORMAL = "电压恢复";
	public static int EQUIPMENT_FAULT_LOW_NORMAL_ = 17;
	/***
	 * 18 低压报警
	 */
	public static String EQUIPMENT_FAULT_LOW_POWER = "低压报警";
	public static int EQUIPMENT_FAULT_LOW_POWER_ = 18;
	/***
	 * 19---高压报警
	 */
	public static String EQUIPMENT_FAULT_UPPER_POWER = "高压报警";
	public static int EQUIPMENT_FAULT_UPPER_POWER_ = 19;

	/*** 20 设备故障 4.5 设备连接故障 0x02 0x02 ***/
	public static String EQUIPMENT_FAULT_CONNECT_FAULT = "连接故障";
	public static int EQUIPMENT_FAULT_CONNECT_FAULT_ = 20;

	/*** 21 设备故障 4.6 设备连接恢复 0x02 0x02 ***/
	public static String EQUIPMENT_FAULT_CONNECTED = "连接成功";
	public static int EQUIPMENT_FAULT_CONNECTED_ = 21;

	/***
	 * 5 端口状态
	 */
	public static int PORT_STATE = 5;
	/*** 22 端口状态 5.1 输入异常 0x02 0x04 ***/
	public static String PORT_STATE_INPUT_EXCEPTION = "输入异常";
	public static int PORT_STATE_INPUT_EXCEPTION_ = 22;

	/*** 23 端口状态 5.2 输入触发 0x02 0x04 ***/
	public static String PORT_STATE_INPUT_TRIGGER = "输入触发";
	public static int PORT_STATE_INPUT_TRIGGER_ = 23;

	/*** 24 端口状态 5.3 输入恢复 0x02 0x04 ***/
	public static String PORT_STATE_INPUT_RESTORE = "输入恢复";
	public static int PORT_STATE_INPUT_RESTORE_ = 24;

	/*** 25 端口状态 5.4 输出闭合 0x20 0x05 ***/
	public static String PORT_STATE_OUTPUT_OPEN = "输出闭合";
	public static int PORT_STATE_OUTPUT_OPEN_ = 25;

	/*** 26 端口状态 5.5 输出断开 0x20 0x05 ***/
	public static String PORT_STATE_OUTPUT_CLOSE = "输出断开";
	public static int PORT_STATE_OUTPUT_CLOSE_ = 26;

	/***
	 * 设备端口异常，请联系管理员
	 */
	public static String CANT_FOUND_PORT = "设备端口异常，请联系管理员";
	/***
	 * 上报状态异常，请联系管理员
	 */
	public static String CANT_FOUND_STATE = "上报状态异常，请联系管理员";
	/**
	 * 下传状态异常，请联系管理员
	 */
	public static String SEND_STATE_ERROR = "下传状态异常，请联系管理员";

	public void bindTarget(Table10 t10, ActionReady aReady) {
		if(t10.getField4()==null||t10.getField4()>0){
			Table10 parent=AtsEquipmentCache.getEquiReadOnly(t10.getField3(), 0);
			aReady.setTargetIp(parent.getFip());
			aReady.setTargetPort(parent.getFport());
		}else{
			aReady.setTargetIp(t10.getFip());
			aReady.setTargetPort(t10.getFport());
		}
		
	}

}
