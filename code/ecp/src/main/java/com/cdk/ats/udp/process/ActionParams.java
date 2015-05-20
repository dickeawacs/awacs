package com.cdk.ats.udp.process;


import java.util.HashMap;
import java.util.Map;

import com.cdk.ats.web.utils.AjaxResult;

/***
 * 下传参数实体
 * @author dingkai
 *
 */
public class ActionParams extends AjaxResult{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5061289283927752065L;
	private String  message;//返回的消息 
	private Integer sequnce;//流水号 
	private Integer userID;//用户id
	private int     userCode=-1;//用户编号 一层设备中的16个用户其中的1用户号
	private String userName;//用户名
	private Integer groupID=0;//组 流水包号（数据包组专用 ）
	private Map<Integer,Boolean> childrens=new HashMap<Integer, Boolean>();//子级数据包 流水号
	private int  oneP=0;//一层设备地址
	private int onePT=-1;//一层设备类型
	private int twoP=0;//二层设备
	private int twoPT=-1;//二层设备类型
	private int comand0=0;//命令
	private int comand1=0;//命令
	private int comand2=0;//命令
	private int comand3=0;//命令
	private int comand4=0;//命令
	private int comand5=0;//命令
	private int comand6=0;//命令

	
	private 	Object	data0	;
	private 	Object	data1	;
	private 	Object	data2	;
	private 	Object	data3	;
	private 	Object	data4	;
	private 	Object	data5	;
	private 	Object	data6	;
	private 	Object	data7	;
	private 	Object	data8	;
	private 	Object	data9	;
	private 	Object	data10	;
	private 	Object	data11	;
	private 	Object	data12	;
	private 	Object	data13	;
	private 	Object	data14	;
	private 	Object	data15	;
	private 	Object	data16	;
	private 	Object	data17	;
	private 	Object	data18	;
	private 	Object	data19	;
	private 	Object	data20	;
	
	
 
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getSequnce() {
		return sequnce;
	}
	public void setSequnce(Integer sequnce) {
		this.sequnce = sequnce;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getOneP() {
		return oneP;
	}
	public void setOneP(int oneP) {
		this.oneP = oneP;
	}
	public int getOnePT() {
		return onePT;
	}
	public void setOnePT(int onePT) {
		this.onePT = onePT;
	}
	public int getTwoP() {
		return twoP;
	}
	public void setTwoP(int twoP) {
		this.twoP = twoP;
	}
	public int getTwoPT() {
		return twoPT;
	}
	public void setTwoPT(int twoPT) {
		this.twoPT = twoPT;
	}
	public Object getData1() {
		return data1;
	}
	public void setData1(Object data1) {
		this.data1 = data1;
	}
	public Object getData2() {
		return data2;
	}
	public void setData2(Object data2) {
		this.data2 = data2;
	}
	public Object getData3() {
		return data3;
	}
	public void setData3(Object data3) {
		this.data3 = data3;
	}
	public Object getData4() {
		return data4;
	}
	public void setData4(Object data4) {
		this.data4 = data4;
	}
	public Object getData5() {
		return data5;
	}
	public void setData5(Object data5) {
		this.data5 = data5;
	}
	public Object getData6() {
		return data6;
	}
	public void setData6(Object data6) {
		this.data6 = data6;
	}
	public Object getData7() {
		return data7;
	}
	public void setData7(Object data7) {
		this.data7 = data7;
	}
	public Object getData8() {
		return data8;
	}
	public void setData8(Object data8) {
		this.data8 = data8;
	}
	public Object getData9() {
		return data9;
	}
	public void setData9(Object data9) {
		this.data9 = data9;
	}
	public Object getData10() {
		return data10;
	}
	public void setData10(Object data10) {
		this.data10 = data10;
	}
	public Object getData11() {
		return data11;
	}
	public void setData11(Object data11) {
		this.data11 = data11;
	}
	public Object getData12() {
		return data12;
	}
	public void setData12(Object data12) {
		this.data12 = data12;
	}
	public Object getData13() {
		return data13;
	}
	public void setData13(Object data13) {
		this.data13 = data13;
	}
	public Object getData14() {
		return data14;
	}
	public void setData14(Object data14) {
		this.data14 = data14;
	}
	public Object getData15() {
		return data15;
	}
	public void setData15(Object data15) {
		this.data15 = data15;
	}
	public Object getData16() {
		return data16;
	}
	public void setData16(Object data16) {
		this.data16 = data16;
	}
	public Object getData17() {
		return data17;
	}
	public void setData17(Object data17) {
		this.data17 = data17;
	}
	public Object getData18() {
		return data18;
	}
	public void setData18(Object data18) {
		this.data18 = data18;
	}
	public Object getData19() {
		return data19;
	}
	public void setData19(Object data19) {
		this.data19 = data19;
	}
	public Object getData20() {
		return data20;
	}
	public void setData20(Object data20) {
		this.data20 = data20;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public int getComand0() {
		return comand0;
	}
	public void setComand0(int comand0) {
		this.comand0 = comand0;
	}
	public int getComand1() {
		return comand1;
	}
	public void setComand1(int comand1) {
		this.comand1 = comand1;
	}
	public int getComand2() {
		return comand2;
	}
	public void setComand2(int comand2) {
		this.comand2 = comand2;
	}
	public int getComand3() {
		return comand3;
	}
	public void setComand3(int comand3) {
		this.comand3 = comand3;
	}
	public int getComand4() {
		return comand4;
	}
	public void setComand4(int comand4) {
		this.comand4 = comand4;
	}
	public int getComand5() {
		return comand5;
	}
	public void setComand5(int comand5) {
		this.comand5 = comand5;
	}
	public Object getData0() {
		return data0;
	}
	public void setData0(Object data0) {
		this.data0 = data0;
	}
 
	public Map<Integer, Boolean> getChildrens() {
		return childrens;
	}
	public void setChildrens(Map<Integer, Boolean> childrens) {
		this.childrens = childrens;
	}
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	public int getComand6() {
		return comand6;
	}
	public void setComand6(int comand6) {
		this.comand6 = comand6;
	}
	 
	
}
