package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.web.utils.Time;

import common.cdk.config.files.msgconfig.MsgConfig;

public class Operating_0x30 {
	private static Logger logger = Logger.getLogger(Operating_0x30.class);
	private DatagramPacket dp;
	
	
	public Operating_0x30( ) {
	}
	public Operating_0x30(DatagramPacket dp) {
		this.dp = dp;
	}
 
	public DatagramPacket getDp() {
		return dp;
	}
	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}
	/***
	 * 下传文件信息
	 * 命令总分类0x30
	 * 命令子分类0x01
	 */
	public void process_0x30_0x01(){

		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				ActionContext.setState(key, true, "操作成功！");
				/*int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x10.0x0c"), new Object[]{ap.getData0().toString(),ap.getUserID()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));*/
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));
			logger.error(e.getMessage(),e);
		}
	}
	/***
	 * 下传时间信息
	 * 命令总分类0x30
	 * 命令子分类0x02
	 */
	public void process_0x30_0x02(){


		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		try {
			
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				ActionContext.setState(key, true, "操作成功！");
				/*int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x10.0x0c"), new Object[]{ap.getData0().toString(),ap.getUserID()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));*/
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));
			logger.error(e.getMessage(),e);
		}
	}
	/***
	 * 
	 * 描述： 同步时间 ，默认是当前时间的时分秒
	 * @createBy dingkai
	 * @createDate 2014-4-7
	 * @lastUpdate 2014-4-7
	 * @param one
	 * @return
	 */
	public ActionReady ox30_0x02(int one) {
		//获取当前时间
		String  theTime= Time.getTimeStr();
		//int day=Time.day();
		int day=CommandTools.getDay();
		ActionReady ready=new ActionReady();
		byte [] data=new byte[32];
		data[0]=0x30	;
		data[1]=0x02	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00;
		data[9]=(byte)one;
		data[10]=(byte)1;
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	; 
		//字节14-29	时间串，长度12字节
		CommandTools.insertByteToArray(data, CommandTools.stringToByte(theTime.toString()), 14,16);
		data[30]=(byte)day;
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
	}
	 
}
