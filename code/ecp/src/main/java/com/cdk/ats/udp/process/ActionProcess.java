package com.cdk.ats.udp.process;


import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.web.utils.AtsParameterUtils;
import com.cdk.ats.web.utils.Time;
import common.cdk.config.files.msgconfig.MsgConfig;
import common.cdk.config.files.sqlconfig.SqlConfig;

/***
 * 
 * @author dingkai
 *  下传数据处理器，
 *  所有需要发送的udp数据都由此处进行发送
 */
public class ActionProcess {
	
	private ActionParams params;//下传数据

	public ActionParams getParams() {
		return params;
	}

	public void setParams(ActionParams params) {
		this.params = params;
	}
	
	
	/***
	 * 下传用户操作信息{ 布防、撤防、查看}
	 * @return
	 */
	public ActionReady ox20_0x02(){
		byte [] data=new byte[17];
		data[0]=0x20	;
		data[1]=0x02	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00	;
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
		data[14]=(byte)params.getUserCode()	;
		data[15]=(byte)params.getComand0()	;
		CommandTools.countCheckCode(data);
		return new ActionReady(data,key); 
	}

	/***
	 * 下传管理员作信息{ 布防、撤防、查看}
	 * @return
	 */
	public ActionReady ox20_0x04(){
		byte [] data=new byte[16];
		data[0]=0x20	;
		data[1]=0x04	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00;
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		data[11]=0x00;
		data[12]=0x00;
		data[13]=0x00;
		data[14]=(byte)params.getComand0()	;
		CommandTools.countCheckCode(data);
		return new ActionReady(data,key); 
	}
	/***
	 * 下传管理员作信息{ 编程}
	 * @return
	 */
	public ActionReady ox20_0x04(int key){
		byte [] data=new byte[16];
		data[0]=0x20	;
		data[1]=0x04	;
		CommandTools.setDatagramLength(data);
		//int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00;
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		data[11]=0x00;
		data[12]=0x00;
		data[13]=0x00;
		data[14]=(byte)0x04	;
		CommandTools.countCheckCode(data);
		return new ActionReady(data,key); 
	}
	
	
	/***
	 *  33 下传用户名信息 **
	 * @return
	 */
	public ActionReady ox10_0x1b(){
		ActionReady ready=new ActionReady();
		byte [] data=new byte[26];
		data[0]=0x10	;
		data[1]=0x1b	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00	;
		
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
		
		data[14]=(byte)params.getUserCode()	;
		CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 15, 10);
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
	}

	/***
	 * 下传用户密码信息		0x10	0x06
	 * @return
	 */
	public ActionReady ox10_0x06() {
		ActionReady ready=new ActionReady();
		byte [] data=new byte[22];
		data[0]=0x10	;
		data[1]=0x06	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00	;
		
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
		
		data[14]=(byte)params.getUserCode()	;
		CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 15, 6);
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
	}
	/***
	 * 下传管理员密码信息		0x10	0x08
	 * @return
	 */
	public ActionReady ox10_0x08() {
		ActionReady ready=new ActionReady();
		byte [] data=new byte[23];
		data[0]=0x10	;
		data[1]=0x08	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00	;
		
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
		
		data[14]=(byte)params.getUserCode()	;
		CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 14, 8);
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
	}

	/****
	 * /32	下传开启/屏蔽用户信息		0x10	0x1a
	 * @return
	 */
	public ActionReady ox10_0x1a() {
		ActionReady ready=new ActionReady();
		byte [] data=new byte[17];
		data[0]=0x10	;
		data[1]=0x1a	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00	;
		
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
		
		data[14]=(byte)params.getUserCode()	;
		data[15]=(byte)(params.getComand0()==0?0x01:0x02)	;//0x01代表开启。0x02代表屏蔽
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
	}
/***
 * 18	下传用户电话号码信息		0x10	0x0c
 * @return
 */
	public ActionReady ox10_0x0c() {
		ActionReady ready=new ActionReady();
		byte [] data=new byte[32];
		data[0]=0x10	;
		data[1]=0x0c	;
		CommandTools.setDatagramLength(data);
		int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00	;
		
		data[9]=(byte)params.getOneP()	;
		data[10]=(byte)params.getOnePT()	;
		
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
		
		data[14]=(byte)params.getUserCode()	;
		//字节15-30	用户电话号码	用户电话号码16字节
		CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 15, 16);
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
	}
/**
 * 下传开启/屏蔽电话端口信息		0x10	0x12
 * @return
 */
public ActionReady ox10_0x12() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[16];
	data[0]=0x10	;
	data[1]=0x12	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	;
	
	data[14]=(byte)(params.getComand0()==1?0xff:0x80)	;//0x08代表开启。0xff代表关闭
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/**
 * 下传开启/屏蔽短信端口信息		0x10	0x10
 * @return
 */
public ActionReady ox10_0x10() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[16];
	data[0]=0x10	;
	data[1]=0x10	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	;
	
	data[14]=(byte)(params.getComand0()==1?0xff:0x80)	;//0x80代表开启。0xff代表屏蔽
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/**
 * 20	下传用户短信号码信息		0x10	0x0e
 * @return
 */
public ActionReady ox10_0x0e() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[32];
	data[0]=0x10	;
	data[1]=0x0e	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	;
	
	data[14]=(byte)params.getUserCode()	;
	//字节15-30	用户短信号码	用户电话号码16字节	
	CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 15, 16);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/***
 * 下传输入所对应2级用户信息		0x10	0x1f
 * 
 * @return
 */
public ActionReady ox10_0x1f() { 
	ActionReady ready=new ActionReady();
	byte [] data=new byte[17];
	data[0]=0x10	;
	data[1]=0x1f	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	
	data[8]=0x00	;
	
	data[9]=(byte)params.getOneP();
	data[10]=(byte)params.getOnePT();
	//if(params.getComand5()==1){
		data[11]=0x00	;
		data[12]=(byte)params.getTwoP();
		data[13]=0x00	;
/*	}
	else if(params.getComand5()==2){
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}*/
	//输入端口  1 2 4 8 16 32 64 128 
	data[14]=(byte)params.getComand0()	;
	data[15]=(byte)params.getUserCode()	;//用户编码
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready; 
}

/***
 * 34	下传设备名信息		0x10	0x1c
 * @return
 */
public ActionReady ox10_0x1c() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[27];
	data[0]=0x10	;
	data[1]=0x1c	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;	
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	if(params.getComand1()==1){//一层设备
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
	}
	else if(params.getComand1()==2){//二层设备
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}
	//字节14-25	设备名称12字节
	CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 14,12);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/***
 * 下传开启/屏蔽设备信息		0x10	0x1e
 * 
 * @return
 */
public ActionReady ox10_0x1e() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[16];
	data[0]=0x10	;
	data[1]=0x1e	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	if(params.getComand1()==1){//一层设备
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
	}
	else if(params.getComand1()==2){//二层设备
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}
	
	data[14]=(byte)(params.getComand0()==1?0x01:0x02)	;//0x01代表开启。0x02代表屏蔽
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/***
 * 下传输入名信息		0x10	0x20
 * @return
 */
public ActionReady ox10_0x20() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[28];
	data[0]=0x10	;
	data[1]=0x20	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	if(params.getComand5()==1){//一层设备
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
	}
	else if(params.getComand5()==2){//二层设备
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}
	
	data[14]=(byte)params.getComand0();//输入选择：即端口号（1、2、4、8、16、32、64、128）
	//字节15-26	输入名，长度12字节
	CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 15,12);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/***
 * 下传交叉设置信息		0x10	0x02
 * @return
 */
public ActionReady ox10_0x02() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[19];
	data[0]=0x10	;
	data[1]=0x02	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	if(params.getComand5()==1){//一层设备
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
	}
	else if(params.getComand5()==2){//二层设备
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}
	
	data[14]=(byte)params.getComand0();//当前输入端口号
	
	data[15]=(byte)params.getComand1();//交叉输入所在的1层设备地址。
	data[16]=(byte)params.getComand2();//交叉输入所在的2层设备地址。
	int tip=params.getComand3();
	switch (tip) {				
	case 1: tip=1  ;break;
	case 2: tip=2 ;break;
	case 3: tip=4 ;break;
	case 4: tip=8 ;break;
	case 5: tip=16 ;break;
	case 6: tip=32 ;break;
	case 7: tip=64 ;break;
	case 8: tip=128 ;break;
}
	data[17]=(byte)tip;//交叉输入选择,即端口号（0,1、2、4、8、16、32、64、128）
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/***
 * 下传输入联动信息		0x10	0x04
 * 
 * @return
 */
public ActionReady ox10_0x04() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[21];
	data[0]=0x10	;
	data[1]=0x04	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	if(params.getComand5()==1){//一层设备
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
	}
	else if(params.getComand5()==2){//二层设备
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}
	
	data[14]=(byte)params.getComand0();//当前的输入端口
	data[15]=(byte)params.getComand1();//输出联动设置选择字节
	data[16]=(byte)params.getComand2();//联动输出所在的1层设备地址。
	data[17]=(byte)params.getComand3();//联动输出所在的2层设备地址。
	int tip=params.getComand4();
	switch (tip) {				
	case 1: tip=1  ;break;
	case 2: tip=2 ;break;
	case 3: tip=4 ;break;
	case 4: tip=8 ;break;
	case 5: tip=16 ;break;
	case 6: tip=32 ;break;
	case 7: tip=64 ;break;
	case 8: tip=128 ;break;
	}
	data[18]=(byte)tip;//联动输出端口,即端口号（1、2、4、8、16、32、64、128）
	data[19]=(byte)params.getComand6();//联动属性
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/**
 * 下传设置信息
命令总分类0x10
命令子分类0x0a
 * @return
 */
public ActionReady ox10_0x0a() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[17];
	data[0]=0x10	;
	data[1]=0x0a	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	if(params.getComand5()==1){//一层设备
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
	}
	else if(params.getComand5()==2){//二层设备
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}
	
	data[14]=(byte)params.getComand0();//输入选择,即端口号（1、2、4、8、16、32、64、128）
	data[15]=(byte)params.getComand1();//输入属性
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/**
 * 下传输出名信息		0x10	0x21
 * @return
 */
public ActionReady ox10_0x21() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[28];
	data[0]=0x10	;
	data[1]=0x21	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00	;
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	if(params.getComand5()==1){//一层设备
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
	}
	else if(params.getComand5()==2){//二层设备
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT()	;
	}
	
	data[14]=(byte)params.getComand0();//输入选择：即端口号（1、2、4、8、16、32、64、128）
	//字节15-26	输入名，长度12字节
	CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 15,12);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/**
 * 下传文件信息		
 * 0x30	0x01
 * @return
 */
public ActionReady ox30_0x01() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[79];
	data[0]=0x30	;
	data[1]=0x01	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00;
	data[9]=(byte)params.getOneP()	;
	data[10]=(byte)params.getOnePT()	;
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	; 
	//字节15-26	输入名，长度12字节
	CommandTools.insertByteToArray(data, CommandTools.stringToByte(params.getData0().toString()), 14,64);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
} 
/**
 * 下传时间信息 （12点发送）
 * 参见 Operation_0x30_0x02
 * @return
 */

/***
 * 下传接警中心电话1
 * @return
 */
public ActionReady ox10_0x16() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[31];
	data[0]=0x10	;
	data[1]=0x16	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00;
	data[9]=(byte)params.getOneP();
	data[10]=(byte)params.getOnePT();
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	; 
	//字节14-29	电话号码，长度16字节
	CommandTools.insertByteToArray(data, CommandTools.stringToByte(AtsParameterUtils.tel_16(params.getData0().toString())), 14,16);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
} 
/***
 * 下传接警中心电话2
 * @return
 */
public ActionReady ox10_0x18() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[31];
	data[0]=0x10	;
	data[1]=0x18	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00;
	data[9]=(byte)params.getOneP();
	data[10]=(byte)params.getOnePT();
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	; 
	//字节14-29	电话号码，长度16字节
	CommandTools.insertByteToArray(data, CommandTools.stringToByte(AtsParameterUtils.tel_16(params.getData0().toString())), 14,16);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/****
 * 电话通迅时间间隔 0x10 0x22
 * @return
 */
public ActionReady ox10_0x22() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[17];
	data[0]=0x10	;
	data[1]=0x22	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00;
	data[9]=(byte)params.getOneP();
	data[10]=(byte)params.getOnePT();
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	; 
	//字节14-15	时间间隔值，长度2字节
	CommandTools.insertByteToArray(data, CommandTools.int10CaseToBytes(params.getComand0(),2), 14,2);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
} 
/****
 * 短信通迅时间间隔 0x10 0x23
 * @return
 */
public ActionReady ox10_0x23() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[17];
	data[0]=0x10	;
	data[1]=0x23	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00;
	data[9]=(byte)params.getOneP();
	data[10]=(byte)params.getOnePT();
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	; 
	//字节14-15	时间间隔值，长度2字节
	CommandTools.insertByteToArray(data, CommandTools.int10CaseToBytes(params.getComand0(),2), 14,2);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}

/***
 * 打印设置下传命令 0x10 0x14
 * @return
 */
public ActionReady ox10_0x14() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[19];
	data[0]=0x10	;
	data[1]=0x14	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00;
	data[9]=(byte)params.getOneP();
	data[10]=(byte)params.getOnePT();
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	; 
	data[14]=(byte)(params.getComand0()==1?0x01:0x02);
	data[15]=(byte)(params.getComand1()==0?0x01:0x02);
	data[16]=(byte)(params.getComand2()==0?0x01:0x02);
	data[17]=(byte)(params.getComand3()==0?0x01:0x02);
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
}
/**
 * 下传输出状态信息  0x20 0x05
 * @return
 */
public ActionReady ox20_0x05() {
	ActionReady ready=new ActionReady();
	byte [] data=new byte[17];
	data[0]=0x20	;
	data[1]=0x05	;
	CommandTools.setDatagramLength(data);
	int key=TransmitterContext.getSequnce();
	CommandTools.formateSequnce(key,data);
	data[8]=0x00;
	data[9]=(byte)params.getOneP();
	data[10]=(byte)params.getOnePT();
	if(params.getTwoP()==0){
	data[11]=0x00	;
	data[12]=0x00	;
	data[13]=0x00	;
	}else{
		data[11]=(byte)0xff	;
		data[12]=(byte)params.getTwoP()	;
		data[13]=(byte)params.getTwoPT();
	}
	data[14]=(byte)(params.getComand0());
	data[15]=(byte)(params.getComand1());
	CommandTools.countCheckCode(data);
	ready.setKey(key);
	ready.setData(data);
	return ready;
} 

/**
 * 重置启示命令

 */
	public ActionReady ox10_0x24(int key) {  
		ActionReady ready=new ActionReady();
		byte [] data=new byte[15];
		data[0]=0x10	;
		data[1]=0x24	;
		CommandTools.setDatagramLength(data);
		//int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00;
		data[9]=(byte)params.getOneP();
		data[10]=(byte)params.getOnePT();
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;  
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
}

	/**
	 * 重置结束命令

	 */
	public ActionReady ox10_0x25(int key) {
	//	System.out.println("结束！"+System.currentTimeMillis());
		ActionReady ready=new ActionReady();
		byte [] data=new byte[16];
		data[0]=0x10	;
		data[1]=0x25	;
		CommandTools.setDatagramLength(data);
		//int key=TransmitterContext.getSequnce();
		CommandTools.formateSequnce(key,data);
		data[8]=0x00;
		data[9]=(byte)params.getOneP();
		data[10]=(byte)params.getOnePT();
		data[11]=0x00	;
		data[12]=0x00	;
		data[13]=0x00	;
		data[14]=(params.getComand0()==1?(byte)0x01:(byte)0x02); 
		CommandTools.countCheckCode(data);
		ready.setKey(key);
		ready.setData(data);
		return ready;
}
 

}