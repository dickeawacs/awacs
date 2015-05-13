package com.cdk.ats.web.pojo.hbm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.math.NumberUtils;


/**
 * 2.10 二层设备表 Table10 entity. @author MyEclipse Persistence Tools
 */

public class Table10Bak  implements java.io.Serializable { 

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7873822612039289662L;
	// Fields
 
	/**
	 * 一层设备的用户
	 */
	private List<Table1>  users;
	/**二层设备*/
	private List<Table10Bak>  second;
	/***序号 ***/
	private Integer field1;
	/*** [废弃] ***/
	private  Integer  field2;
	
	/*** 一层设备网络地址 ***/
	private  Integer  field3;
	
	/*** 二层设备网络地址 ***/
	private  Integer  field4;
	
	/*** 设备启用状态位 ***/
	private  Integer  field5;
	/*** 输入交叉状态位 ***/
	private String field6;
	/*** 联动输出状态位 ***/
	private String field7;
	/*** 设备唯一ID ***/
	private String field8;
	/*** 设备生产序列号 ***/
	private String field9;
	/*** 设备种类 ***/
	private  Integer  field10=0;
	/*** 设备通讯正/异常 ***/
	private  Integer  field11;
	/*** 电压检测 ***/
	private  Integer  field12;
	/*** 设备被撬 ***/
	private  Integer  field13;
	/*** 输入状态 ***/
	private  String  field14;
	/*** 输出状态 ***/
	private  String  field15;
	/*** 用户防区布撤防状态 ***/	
	private  String  field16;
	
//----------------------------------------端口属性    ---------------------
	/****
	 * 	1.屏蔽输入。	1.0x00
		2.普通输入。	2.0x01
		3.立即防区。	3.0x02
		4.24小时防区。4.0x03

	 */
//------------------------------------------------------------------------------
	/*** 1输入（防区）属性 ***/
	private  Integer  field17;
	/*** 2输入（防区）属性 ***/
	private  Integer  field18;
	/*** 3输入（防区）属性 ***/
	private  Integer  field19;
	/*** 4输入（防区）属性 ***/
	private  Integer  field20;
	/*** 5输入（防区）属性 ***/
	private  Integer  field21;
	/*** 6输入（防区）属性 ***/
	private  Integer  field22;
	/*** 7输入（防区）属性 ***/
	private  Integer  field23;
	/*** 8输入（防区）属性 ***/
	private  Integer  field24;
	
	/*** 1输入（防区）隶属属性 ***/
	private  Integer  field25;
	/*** 2输入（防区）隶属属性 ***/
	private  Integer  field26;
	/*** 3输入（防区）隶属属性 ***/
	private  Integer  field27;
	/*** 4输入（防区）隶属属性 ***/
	private  Integer  field28;
	/*** 5输入（防区）隶属属性 ***/
	private  Integer  field29;
	/*** 6输入（防区）隶属属性 ***/
	private  Integer  field30;
	/*** 7输入（防区）隶属属性 ***/
	private  Integer  field31;
	/*** 8输入（防区）隶属属性 ***/
	private  Integer  field32;
	//-----------------------------------------------交叉      格式[二层设备地址(标号) , 端口号, 交叉禁止]--------------------
	//---------------------------------------------------------[（1-128）                        ,（1-8）,0/1]--------------------
	/*** 输入1输入交叉信息 ***/
	private String field33;
	/*** 输入2输入交叉信息 ***/
	private String field34;
	/*** 输入3输入交叉信息 ***/
	private String field35;
	/*** 输入4输入交叉信息 ***/
	private String field36;
	/*** 输入5输入交叉信息 ***/
	private String field37;
	/*** 输入6输入交叉信息 ***/
	private String field38;
	/*** 输入7输入交叉信息 ***/
	private String field39;
	/*** 输入8输入交叉信息 ***/
	private String field40;

	
//输入1联动////////////////
	/*** 输入1联动输出信息1 ***/
	private String field41;
	/*** 输入1联动输出信息2 ***/
	private String field42;
	/*** 输入1联动输出信息3 ***/
	private String field43;
	/*** 输入1联动输出信息4 ***/
	private String field44;
	/*** 输入1联动输出信息5 ***/
	private String field45;
	/*** 输入1联动输出信息6 ***/
	private String field46;
	/*** 输入1联动输出信息7 ***/
	private String field47;
	/*** 输入1联动输出信息8 ***/
	private String field48;
	
//输入2联动////////////////
	/*** 输入2联动输出信息1 ***/
	private String field49;
	/*** 输入2联动输出信息2 ***/
	private String field50;
	/*** 输入2联动输出信息3 ***/
	private String field51;
	/*** 输入2联动输出信息4 ***/
	private String field52;
	/*** 输入2联动输出信息5 ***/
	private String field53;
	/*** 输入2联动输出信息6 ***/
	private String field54;
	/*** 输入2联动输出信息7 ***/
	private String field55;
	/*** 输入2联动输出信息8 ***/
	private String field56;
	
//输入3联动////////////////
	/*** 输入3联动输出信息1 ***/
	private String field57;
	/*** 输入3联动输出信息2 ***/
	private String field58;
	/*** 输入3联动输出信息3 ***/
	private String field59;
	/*** 输入3联动输出信息4 ***/
	private String field60;
	/*** 输入3联动输出信息5 ***/
	private String field61;
	/*** 输入3联动输出信息6 ***/
	private String field62;
	/*** 输入3联动输出信息7 ***/
	private String field63;
	/*** 输入3联动输出信息8 ***/
	private String field64;
	
//输入4联动////////////////	
	/*** 输入4联动输出信息1 ***/
	private String field65;
	/*** 输入4联动输出信息2 ***/
	private String field66;
	/*** 输入4联动输出信息3 ***/
	private String field67;
	/*** 输入4联动输出信息4 ***/
	private String field68;
	/*** 输入4联动输出信息5 ***/
	private String field69;
	/*** 输入4联动输出信息6 ***/
	private String field70;
	/*** 输入4联动输出信息7 ***/
	private String field71;
	/*** 输入4联动输出信息8 ***/
	private String field72;
	
//输入5联动////////////////
	/*** 输入5联动输出信息1 ***/
	private String field73;
	/*** 输入5联动输出信息2 ***/
	private String field74;
	/*** 输入5联动输出信息3 ***/
	private String field75;
	/*** 输入5联动输出信息4 ***/
	private String field76;
	/*** 输入5联动输出信息5 ***/
	private String field77;
	/*** 输入5联动输出信息6 ***/
	private String field78;
	/*** 输入5联动输出信息7 ***/
	private String field79;
	/*** 输入5联动输出信息8 ***/
	private String field80;

//输入6联动////////////////
	/*** 输入6联动输出信息1 ***/
	private String field81;
	/*** 输入6联动输出信息2 ***/
	private String field82;
	/*** 输入6联动输出信息3 ***/
	private String field83;
	/*** 输入6联动输出信息4 ***/
	private String field84;
	/*** 输入6联动输出信息5 ***/
	private String field85;
	/*** 输入6联动输出信息6 ***/
	private String field86;
	/*** 输入6联动输出信息7 ***/
	private String field87;
	/*** 输入6联动输出信息8 ***/
	private String field88;
	
//输入7联动////////////////
	/*** 输入7联动输出信息1 ***/
	private String field89;
	/*** 输入7联动输出信息2 ***/
	private String field90;
	/*** 输入7联动输出信息3 ***/
	private String field91;
	/*** 输入7联动输出信息4 ***/
	private String field92;
	/*** 输入7联动输出信息5 ***/
	private String field93;
	/*** 输入7联动输出信息6 ***/
	private String field94;
	/*** 输入7联动输出信息7 ***/
	private String field95;
	/*** 输入7联动输出信息8 ***/
	private String field96;
	
//输入8联动////////////////
	/*** 输入8联动输出信息1 ***/
	private String field97;
	/*** 输入8联动输出信息2 ***/
	private String field98;
	/*** 输入8联动输出信息3 ***/
	private String field99;
	/*** 输入8联动输出信息4 ***/
	private String field100;
	/*** 输入8联动输出信息5 ***/
	private String field101;
	/*** 输入8联动输出信息6 ***/
	private String field102;
	/*** 输入8联动输出信息7 ***/
	private String field103;
	/*** 输入8联动输出信息8 ***/
	private String field104;
	
/////   input  end !
	
	/*** 输入1名称 ***/
	private String field105;
	/*** 输入2名称 ***/
	private String field106;
	/*** 输入3名称 ***/
	private String field107;
	/*** 输入4名称 ***/
	private String field108;
	/*** 输入5名称 ***/
	private String field109;
	/*** 输入6名称 ***/
	private String field110;
	/*** 输入7名称 ***/
	private String field111;
	/*** 输入8名称 ***/
	private String field112;
	
	
	
	/*** 输出1名称 ***/
	private String field113;
	/*** 输出2名称 ***/
	private String field114;
	/*** 输出3名称 ***/
	private String field115;
	/*** 输出4名称 ***/
	private String field116;
	/*** 输出5名称 ***/
	private String field117;
	/*** 输出6名称 ***/
	private String field118;
	/*** 输出7名称 ***/
	private String field119;
	/*** 输出8名称 ***/
	private String field120;
	//--------------------------------------------设备名称----------------------------
	
	/*** 二层设备名称 ***/
	private String field121;
	
	
	private  Integer    field122;		//	输入1触发提示音
	private  Integer    field123;		//	输入2触发提示音
	private  Integer    field124;		//	输入3触发提示音
	private  Integer    field125;		//	输入4触发提示音
	private  Integer    field126;		//	输入5触发提示音
	private  Integer    field127;		//	输入6触发提示音
	private  Integer    field128;		//	输入7触发提示音
	private  Integer    field129;		//	输入8触发提示音


	/**屏蔽状态（1为屏蔽禁用）**/
	private  Integer  field130;	
	
	private  Integer    field131;		//	输入1恢复提示音
	private  Integer    field132;		//	输入2恢复提示音
	private  Integer    field133;		//	输入3恢复提示音
	private  Integer    field134;		//	输入4恢复提示音
	private  Integer    field135;		//	输入5恢复提示音
	private  Integer    field136;		//	输入6恢复提示音
	private  Integer    field137;		//	输入7恢复提示音
	private  Integer    field138;		//	输入8恢复提示音

	private Integer field139 ;	//	输出1闭合提示音
	private Integer field140 ;	//	输出2闭合提示音
	private Integer field141 ;	//	输出3闭合提示音
	private Integer field142 ;	//	输出4闭合提示音
	private Integer field143 ;	//	输出5闭合提示音
	private Integer field144 ;	//	输出6闭合提示音
	private Integer field145 ;	//	输出7闭合提示音
	private Integer field146 ;	//	输出8闭合提示音
	private Integer field147 ;	//	输出1断开提示音
	private Integer field148 ;	//	输出2断开提示音
	private Integer field149 ;	//	输出3断开提示音
	private Integer field150 ;	//	输出4断开提示音
	private Integer field151 ;	//	输出5断开提示音
	private Integer field152 ;	//	输出6断开提示音
	private Integer field153 ;	//	输出7断开提示音
	private Integer field154 ;	//	输出8断开提示音

	private Integer field155 ;	//	输出1状态
	private Integer field156 ;	//	输出2状态
	private Integer field157 ;	//	输出3状态
	private Integer field158 ;	//	输出4状态
	private Integer field159 ;	//	输出5状态
	private Integer field160 ;	//	输出6状态
	private Integer field161 ;	//	输出7状态
	private Integer field162 ;	//	输出8状态
	
	private Integer oneType=0;//一层设备类型
	
	
	
	private String fip;		//	设备的IP地址
	private Integer fport;		//	设备的访问端口	
	private Integer disableTel;		//	禁用电话端口  禁用为1，启用为0，默认为0
	private Integer disableMessage;		//	禁用短信端口 禁用为1，启用为0，默认为0
	private String telNum1;		//	接警中心号码一
	private String telNum2;		//	接警中心号码2
	private Integer telSpace;		//	电话通迅间隔(分钟):
	private Integer messageSpace;		//	短信通迅间隔(分钟):
	private String printSet;		//	打印设置（禁止时为“”，不禁止时为"报警事件,普通输入事件,用户操作事件"，其中0表示未选中，1表示选中
	private Integer dtype;		//设置类型(1:一层设备，2：二层设备）
	
	private Table10 parent;

	
	// Constructors
	public Table10Bak() { 
	}
	/*****
	 * 一层设备初始化
	 * @param field3
	 * @param field121
	 */
	public Table10Bak(Object field3,Object field121) throws Exception{
		this.field3=NumberUtils.createInteger(field3.toString());
		this.field121=field121+"";
	}

	/** full constructor */
	public Table10Bak( Integer  field2,  Integer  field3,  Integer  field4,  Integer  field5,
			String field6, String field7, String field8, String field9,
			 Integer  field10,  Integer  field11,  Integer  field12,  Integer  field13,
			 String  field14,  String  field15,  String  field16,  Integer  field17,
			 Integer  field18,  Integer  field19,  Integer  field20,  Integer  field21,
			 Integer  field22,  Integer  field23,  Integer  field24,  Integer  field25,
			 Integer  field26,  Integer  field27,  Integer  field28,  Integer  field29,
			 Integer  field30,  Integer  field31,  Integer  field32, String field33,
			String field34, String field35, String field36, String field37,
			String field38, String field39, String field40, String field41,
			String field42, String field43, String field44, String field45,
			String field46, String field47, String field48, String field49,
			String field50, String field51, String field52, String field53,
			String field54, String field55, String field56, String field57,
			String field58, String field59, String field60, String field61,
			String field62, String field63, String field64, String field65,
			String field66, String field67, String field68, String field69,
			String field70, String field71, String field72, String field73,
			String field74, String field75, String field76, String field77,
			String field78, String field79, String field80, String field81,
			String field82, String field83, String field84, String field85,
			String field86, String field87, String field88, String field89,
			String field90, String field91, String field92, String field93,
			String field94, String field95, String field96, String field97,
			String field98, String field99, String field100, String field101,
			String field102, String field103, String field104, String field105,
			String field106, String field107, String field108, String field109,
			String field110, String field111, String field112, String field113,
			String field114, String field115, String field116, String field117,
			String field118, String field119, String field120, String field121,
			Integer field122, Integer field123, Integer field124, Integer field125,
			Integer field126, Integer field127, Integer field128, Integer field129, Integer  field130) {
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.field6 = field6;
		this.field7 = field7;
		this.field8 = field8;
		this.field9 = field9;
		this.field10 = field10;
		this.field11 = field11;
		this.field12 = field12;
		this.field13 = field13;
		this.field14 = field14;
		this.field15 = field15;
		this.field16 = field16;
		this.field17 = field17;
		this.field18 = field18;
		this.field19 = field19;
		this.field20 = field20;
		this.field21 = field21;
		this.field22 = field22;
		this.field23 = field23;
		this.field24 = field24;
		this.field25 = field25;
		this.field26 = field26;
		this.field27 = field27;
		this.field28 = field28;
		this.field29 = field29;
		this.field30 = field30;
		this.field31 = field31;
		this.field32 = field32;
		this.field33 = field33;
		this.field34 = field34;
		this.field35 = field35;
		this.field36 = field36;
		this.field37 = field37;
		this.field38 = field38;
		this.field39 = field39;
		this.field40 = field40;
		this.field41 = field41;
		this.field42 = field42;
		this.field43 = field43;
		this.field44 = field44;
		this.field45 = field45;
		this.field46 = field46;
		this.field47 = field47;
		this.field48 = field48;
		this.field49 = field49;
		this.field50 = field50;
		this.field51 = field51;
		this.field52 = field52;
		this.field53 = field53;
		this.field54 = field54;
		this.field55 = field55;
		this.field56 = field56;
		this.field57 = field57;
		this.field58 = field58;
		this.field59 = field59;
		this.field60 = field60;
		this.field61 = field61;
		this.field62 = field62;
		this.field63 = field63;
		this.field64 = field64;
		this.field65 = field65;
		this.field66 = field66;
		this.field67 = field67;
		this.field68 = field68;
		this.field69 = field69;
		this.field70 = field70;
		this.field71 = field71;
		this.field72 = field72;
		this.field73 = field73;
		this.field74 = field74;
		this.field75 = field75;
		this.field76 = field76;
		this.field77 = field77;
		this.field78 = field78;
		this.field79 = field79;
		this.field80 = field80;
		this.field81 = field81;
		this.field82 = field82;
		this.field83 = field83;
		this.field84 = field84;
		this.field85 = field85;
		this.field86 = field86;
		this.field87 = field87;
		this.field88 = field88;
		this.field89 = field89;
		this.field90 = field90;
		this.field91 = field91;
		this.field92 = field92;
		this.field93 = field93;
		this.field94 = field94;
		this.field95 = field95;
		this.field96 = field96;
		this.field97 = field97;
		this.field98 = field98;
		this.field99 = field99;
		this.field100 = field100;
		this.field101 = field101;
		this.field102 = field102;
		this.field103 = field103;
		this.field104 = field104;
		this.field105 = field105;
		this.field106 = field106;
		this.field107 = field107;
		this.field108 = field108;
		this.field109 = field109;
		this.field110 = field110;
		this.field111 = field111;
		this.field112 = field112;
		this.field113 = field113;
		this.field114 = field114;
		this.field115 = field115;
		this.field116 = field116;
		this.field117 = field117;
		this.field118 = field118;
		this.field119 = field119;
		this.field120 =	field120;
		this.field121 =	field121;
		this.field122 =	field122;
		this.field123 =	field123;
		this.field124 =	field124;
		this.field125 =	field125;
		this.field126 =	field126;
		this.field127 =	field127;
		this.field128 =	field128;
		this.field129 =	field129;
		this.field130= field130;

	}

	public Integer getField1() {
		return field1;
	}

	public void setField1(Integer field1) {
		this.field1 = field1;
	}

	public  Integer  getField2() {
		return field2;
	}

	public void setField2( Integer  field2) {
		this.field2 = field2;
	}

	public  Integer  getField3() {
		return field3;
	}

	public void setField3( Integer  field3) {
		this.field3 = field3;
	}

	public  Integer  getField4() {
		return field4;
	}

	public void setField4( Integer  field4) {
		this.field4 = field4;
	}

	public  Integer  getField5() {
		return field5;
	}

	public void setField5( Integer  field5) {
		this.field5 = field5;
	}

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public String getField7() {
		return field7;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public String getField8() {
		return field8;
	}

	public void setField8(String field8) {
		this.field8 = field8;
	}

	public String getField9() {
		return field9;
	}

	public void setField9(String field9) {
		this.field9 = field9;
	}

	public  Integer  getField10() {
		if(field10==null)field10=0;
		return field10;
	}

	public void setField10( Integer  field10) {
		this.field10 = field10;
	}



	public  Integer  getField11() {
		return field11;
	}

	public void setField11( Integer  field11) {
		this.field11 = field11;
	}

	public  Integer  getField12() {
		return field12;
	}

	public void setField12( Integer  field12) {
		this.field12 = field12;
	}

	public  Integer  getField13() {
		return field13;
	}

	public void setField13( Integer  field13) {
		this.field13 = field13;
	}

	public  String  getField14() {
		return field14;
	}

	public void setField14( String  field14) {
		this.field14 = field14;
	}

	public  String  getField15() {
		return field15;
	}

	public void setField15( String  field15) {
		this.field15 = field15;
	}


	public  String  getField16() {
		return field16;
	}

	public void setField16( String  field16) {
		this.field16 = field16;
	}

	public  Integer  getField17() {
		return field17;
	}

	public void setField17( Integer  field17) {
		this.field17 = field17;
	}

	public  Integer  getField18() {
		return field18;
	}

	public void setField18( Integer  field18) {
		this.field18 = field18;
	}

	public  Integer  getField19() {
		return field19;
	}

	public void setField19( Integer  field19) {
		this.field19 = field19;
	}

	public  Integer  getField20() {
		return field20;
	}

	public void setField20( Integer  field20) {
		this.field20 = field20;
	}

	public  Integer  getField21() {
		return field21;
	}

	public void setField21( Integer  field21) {
		this.field21 = field21;
	}

	public  Integer  getField22() {
		return field22;
	}

	public void setField22( Integer  field22) {
		this.field22 = field22;
	}

	public  Integer  getField23() {
		return field23;
	}

	public void setField23( Integer  field23) {
		this.field23 = field23;
	}

 
	public  Integer  getField24() {
		return field24;
	}

	public void setField24( Integer  field24) {
		this.field24 = field24;
	}

	

	public  Integer  getField25() {
		return field25;
	}

	public void setField25( Integer  field25) {
		this.field25 = field25;
	}

	public  Integer  getField26() {
		return field26;
	}

	public void setField26( Integer  field26) {
		this.field26 = field26;
	}

	public  Integer  getField27() {
		return field27;
	}

	public void setField27( Integer  field27) {
		this.field27 = field27;
	}

	public  Integer  getField28() {
		return field28;
	}

	public void setField28( Integer  field28) {
		this.field28 = field28;
	}

	public  Integer  getField29() {
		return field29;
	}

	public void setField29( Integer  field29) {
		this.field29 = field29;
	}

	public  Integer  getField30() {
		return field30;
	}

	public void setField30( Integer  field30) {
		this.field30 = field30;
	}

	public  Integer  getField31() {
		return field31;
	}

	public void setField31( Integer  field31) {
		this.field31 = field31;
	}

	public  Integer  getField32() {
		return field32;
	}

	public void setField32( Integer  field32) {
		this.field32 = field32;
	}

	public String getField33() {
		return field33;
	}

	public void setField33(String field33) {
		this.field33 = field33;
	}

	public String getField34() {
		return field34;
	}

	public void setField34(String field34) {
		this.field34 = field34;
	}

	public String getField35() {
		return field35;
	}

	public void setField35(String field35) {
		this.field35 = field35;
	}

	public String getField36() {
		return field36;
	}

	public void setField36(String field36) {
		this.field36 = field36;
	}

	public String getField37() {
		return field37;
	}

	public void setField37(String field37) {
		this.field37 = field37;
	}

	public String getField38() {
		return field38;
	}

	public void setField38(String field38) {
		this.field38 = field38;
	}

	public String getField39() {
		return field39;
	}

	public void setField39(String field39) {
		this.field39 = field39;
	}

	public String getField40() {
		return field40;
	}

	public void setField40(String field40) {
		this.field40 = field40;
	}

	public String getField41() {
		return field41;
	}

	public void setField41(String field41) {
		this.field41 = field41;
	}

	public String getField42() {
		return field42;
	}

	public void setField42(String field42) {
		this.field42 = field42;
	}

	public String getField43() {
		return field43;
	}

	public void setField43(String field43) {
		this.field43 = field43;
	}

	public String getField44() {
		return field44;
	}

	public void setField44(String field44) {
		this.field44 = field44;
	}

	public String getField45() {
		return field45;
	}

	public void setField45(String field45) {
		this.field45 = field45;
	}

	public String getField46() {
		return field46;
	}

	public void setField46(String field46) {
		this.field46 = field46;
	}

	public String getField47() {
		return field47;
	}

	public void setField47(String field47) {
		this.field47 = field47;
	}

	public String getField48() {
		return field48;
	}

	public void setField48(String field48) {
		this.field48 = field48;
	}

	public String getField49() {
		return field49;
	}

	public void setField49(String field49) {
		this.field49 = field49;
	}

	public String getField50() {
		return field50;
	}

	public void setField50(String field50) {
		this.field50 = field50;
	}

	public String getField51() {
		return field51;
	}

	public void setField51(String field51) {
		this.field51 = field51;
	}

	public String getField52() {
		return field52;
	}

	public void setField52(String field52) {
		this.field52 = field52;
	}

	public String getField53() {
		return field53;
	}

	public void setField53(String field53) {
		this.field53 = field53;
	}

	public String getField54() {
		return field54;
	}

	public void setField54(String field54) {
		this.field54 = field54;
	}

	public String getField55() {
		return field55;
	}

	public void setField55(String field55) {
		this.field55 = field55;
	}

	public String getField56() {
		return field56;
	}

	public void setField56(String field56) {
		this.field56 = field56;
	}

	public String getField57() {
		return field57;
	}

	public void setField57(String field57) {
		this.field57 = field57;
	}

	public String getField58() {
		return field58;
	}

	public void setField58(String field58) {
		this.field58 = field58;
	}

	public String getField59() {
		return field59;
	}

	public void setField59(String field59) {
		this.field59 = field59;
	}

	public String getField60() {
		return field60;
	}

	public void setField60(String field60) {
		this.field60 = field60;
	}

	public String getField61() {
		return field61;
	}

	public void setField61(String field61) {
		this.field61 = field61;
	}

	public String getField62() {
		return field62;
	}

	public void setField62(String field62) {
		this.field62 = field62;
	}

	public String getField63() {
		return field63;
	}

	public void setField63(String field63) {
		this.field63 = field63;
	}

	public String getField64() {
		return field64;
	}

	public void setField64(String field64) {
		this.field64 = field64;
	}

	public String getField65() {
		return field65;
	}

	public void setField65(String field65) {
		this.field65 = field65;
	}

	public String getField66() {
		return field66;
	}

	public void setField66(String field66) {
		this.field66 = field66;
	}

	public String getField67() {
		return field67;
	}

	public void setField67(String field67) {
		this.field67 = field67;
	}

	public String getField68() {
		return field68;
	}

	public void setField68(String field68) {
		this.field68 = field68;
	}

	public String getField69() {
		return field69;
	}

	public void setField69(String field69) {
		this.field69 = field69;
	}

	public String getField70() {
		return field70;
	}

	public void setField70(String field70) {
		this.field70 = field70;
	}

	public String getField71() {
		return field71;
	}

	public void setField71(String field71) {
		this.field71 = field71;
	}

	public String getField72() {
		return field72;
	}

	public void setField72(String field72) {
		this.field72 = field72;
	}

	public String getField73() {
		return field73;
	}

	public void setField73(String field73) {
		this.field73 = field73;
	}

	public String getField74() {
		return field74;
	}

	public void setField74(String field74) {
		this.field74 = field74;
	}

	public String getField75() {
		return field75;
	}

	public void setField75(String field75) {
		this.field75 = field75;
	}

	public String getField76() {
		return field76;
	}

	public void setField76(String field76) {
		this.field76 = field76;
	}

	public String getField77() {
		return field77;
	}

	public void setField77(String field77) {
		this.field77 = field77;
	}

	public String getField78() {
		return field78;
	}

	public void setField78(String field78) {
		this.field78 = field78;
	}

	public String getField79() {
		return field79;
	}

	public void setField79(String field79) {
		this.field79 = field79;
	}

	public String getField80() {
		return field80;
	}

	public void setField80(String field80) {
		this.field80 = field80;
	}

	public String getField81() {
		return field81;
	}

	public void setField81(String field81) {
		this.field81 = field81;
	}

	public String getField82() {
		return field82;
	}

	public void setField82(String field82) {
		this.field82 = field82;
	}

	public String getField83() {
		return field83;
	}

	public void setField83(String field83) {
		this.field83 = field83;
	}

	public String getField84() {
		return field84;
	}

	public void setField84(String field84) {
		this.field84 = field84;
	}

	public String getField85() {
		return field85;
	}

	public void setField85(String field85) {
		this.field85 = field85;
	}

	public String getField86() {
		return field86;
	}

	public void setField86(String field86) {
		this.field86 = field86;
	}

	public String getField87() {
		return field87;
	}

	public void setField87(String field87) {
		this.field87 = field87;
	}

	public String getField88() {
		return field88;
	}

	public void setField88(String field88) {
		this.field88 = field88;
	}

	public String getField89() {
		return field89;
	}

	public void setField89(String field89) {
		this.field89 = field89;
	}

	public String getField90() {
		return field90;
	}

	public void setField90(String field90) {
		this.field90 = field90;
	}

	public String getField91() {
		return field91;
	}

	public void setField91(String field91) {
		this.field91 = field91;
	}

	public String getField92() {
		return field92;
	}

	public void setField92(String field92) {
		this.field92 = field92;
	}

	public String getField93() {
		return field93;
	}

	public void setField93(String field93) {
		this.field93 = field93;
	}

	public String getField94() {
		return field94;
	}

	public void setField94(String field94) {
		this.field94 = field94;
	}

	public String getField95() {
		return field95;
	}

	public void setField95(String field95) {
		this.field95 = field95;
	}

	public String getField96() {
		return field96;
	}

	public void setField96(String field96) {
		this.field96 = field96;
	}

	public String getField97() {
		return field97;
	}

	public void setField97(String field97) {
		this.field97 = field97;
	}

	public String getField98() {
		return field98;
	}

	public void setField98(String field98) {
		this.field98 = field98;
	}

	public String getField99() {
		return field99;
	}

	public void setField99(String field99) {
		this.field99 = field99;
	}

	public String getField100() {
		return field100;
	}

	public void setField100(String field100) {
		this.field100 = field100;
	}

	public String getField101() {
		return field101;
	}

	public void setField101(String field101) {
		this.field101 = field101;
	}

	public String getField102() {
		return field102;
	}

	public void setField102(String field102) {
		this.field102 = field102;
	}

	public String getField103() {
		return field103;
	}

	public void setField103(String field103) {
		this.field103 = field103;
	}

	public String getField104() {
		return field104;
	}

	public void setField104(String field104) {
		this.field104 = field104;
	}

	public String getField105() {
		return field105;
	}

	public void setField105(String field105) {
		this.field105 = field105;
	}

	public String getField106() {
		return field106;
	}

	public void setField106(String field106) {
		this.field106 = field106;
	}

	public String getField107() {
		return field107;
	}

	public void setField107(String field107) {
		this.field107 = field107;
	}

	public String getField108() {
		return field108;
	}

	public void setField108(String field108) {
		this.field108 = field108;
	}

	public String getField109() {
		return field109;
	}

	public void setField109(String field109) {
		this.field109 = field109;
	}

	public String getField110() {
		return field110;
	}

	public void setField110(String field110) {
		this.field110 = field110;
	}

	public String getField111() {
		return field111;
	}

	public void setField111(String field111) {
		this.field111 = field111;
	}

	public String getField112() {
		return field112;
	}

	public void setField112(String field112) {
		this.field112 = field112;
	}

	public String getField113() {
		return field113;
	}

	public void setField113(String field113) {
		this.field113 = field113;
	}

	public String getField114() {
		return field114;
	}

	public void setField114(String field114) {
		this.field114 = field114;
	}

	public String getField115() {
		return field115;
	}

	public void setField115(String field115) {
		this.field115 = field115;
	}

	public String getField116() {
		return field116;
	}

	public void setField116(String field116) {
		this.field116 = field116;
	}

	public String getField117() {
		return field117;
	}

	public void setField117(String field117) {
		this.field117 = field117;
	}

	public String getField118() {
		return field118;
	}

	public void setField118(String field118) {
		this.field118 = field118;
	}

	public String getField119() {
		return field119;
	}

	public void setField119(String field119) {
		this.field119 = field119;
	}

	public String getField120() {
		return field120;
	}

	public void setField120(String field120) {
		this.field120 = field120;
	}

	public String getField121() {
		return  field121;
	}

	public void setField121(String field121) {
		this.field121 = field121;
	}
 
	public Integer getField122() {
		return field122;
	}

	public void setField122(Integer field122) {
		this.field122 = field122;
	}

	public Integer getField123() {
		return field123;
	}

	public void setField123(Integer field123) {
		this.field123 = field123;
	}

	public Integer getField124() {
		return field124;
	}

	public void setField124(Integer field124) {
		this.field124 = field124;
	}

	public Integer getField125() {
		return field125;
	}

	public void setField125(Integer field125) {
		this.field125 = field125;
	}

	public Integer getField126() {
		return field126;
	}

	public void setField126(Integer field126) {
		this.field126 = field126;
	}

	public Integer getField127() {
		return field127;
	}

	public void setField127(Integer field127) {
		this.field127 = field127;
	}

	public Integer getField128() {
		return field128;
	}

	public void setField128(Integer field128) {
		this.field128 = field128;
	}

	public Integer getField129() {
		return field129;
	}

	public void setField129(Integer field129) {
		this.field129 = field129;
	}

	public  Integer  getField130() {
		return field130;
	}

	public void setField130( Integer  field130) {
		this.field130 = field130;
	}

	public Integer getField131() {
		if(field131==null)field131=1;
		return field131;
	}

	public void setField131(Integer field131) {
		this.field131 = field131;
	}

	public Integer getField132() {
		return field132;
	}

	public void setField132(Integer field132) {
		this.field132 = field132;
	}

	public Integer getField133() {
		return field133;
	}

	public void setField133(Integer field133) {
		this.field133 = field133;
	}

	public Integer getField134() {
		return field134;
	}

	public void setField134(Integer field134) {
		this.field134 = field134;
	}

	public Integer getField135() {
		return field135;
	}

	public void setField135(Integer field135) {
		this.field135 = field135;
	}

	public Integer getField136() {
		return field136;
	}

	public void setField136(Integer field136) {
		this.field136 = field136;
	}

	public Integer getField137() {
		return field137;
	}

	public void setField137(Integer field137) {
		this.field137 = field137;
	}

	public Integer getField138() {
		return field138;
	}

	public void setField138(Integer field138) {
		this.field138 = field138;
	}

	public Integer getField139() {
		return field139;
	}

	public void setField139(Integer field139) {
		this.field139 = field139;
	}

	public Integer getField140() {
		return field140;
	}

	public void setField140(Integer field140) {
		this.field140 = field140;
	}

	public Integer getField141() {
		return field141;
	}

	public void setField141(Integer field141) {
		this.field141 = field141;
	}

	public Integer getField142() {
		return field142;
	}

	public void setField142(Integer field142) {
		this.field142 = field142;
	}

	public Integer getField143() {
		return field143;
	}

	public void setField143(Integer field143) {
		this.field143 = field143;
	}

	public Integer getField144() {
		return field144;
	}

	public void setField144(Integer field144) {
		this.field144 = field144;
	}

	public Integer getField145() {
		return field145;
	}

	public void setField145(Integer field145) {
		this.field145 = field145;
	}

	public Integer getField146() {
		return field146;
	}

	public void setField146(Integer field146) {
		this.field146 = field146;
	}

	public Integer getField147() {
		return field147;
	}

	public void setField147(Integer field147) {
		this.field147 = field147;
	}

	public Integer getField148() {
		return field148;
	}

	public void setField148(Integer field148) {
		this.field148 = field148;
	}

	public Integer getField149() {
		return field149;
	}

	public void setField149(Integer field149) {
		this.field149 = field149;
	}

	public Integer getField150() {
		return field150;
	}

	public void setField150(Integer field150) {
		this.field150 = field150;
	}

	public Integer getField151() {
		return field151;
	}

	public void setField151(Integer field151) {
		this.field151 = field151;
	}

	public Integer getField152() {
		return field152;
	}

	public void setField152(Integer field152) {
		this.field152 = field152;
	}

	public Integer getField153() {
		return field153;
	}

	public void setField153(Integer field153) {
		this.field153 = field153;
	}

	public Integer getField154() {
		return field154;
	}

	public void setField154(Integer field154) {
		this.field154 = field154;
	}

	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	public Integer getFport() {
		return fport;
	}

	public void setFport(Integer fport) {
		this.fport = fport;
	}

	public Integer getDisableTel() {
		return disableTel;
	}

	public void setDisableTel(Integer disableTel) {
		this.disableTel = disableTel;
	}

	public Integer getDisableMessage() {
		return disableMessage;
	}

	public void setDisableMessage(Integer disableMessage) {
		this.disableMessage = disableMessage;
	}

	public String getTelNum1() {
		return telNum1;
	}

	public void setTelNum1(String telNum1) {
		this.telNum1 = telNum1;
	}

	public String getTelNum2() {
		return telNum2;
	}

	public void setTelNum2(String telNum2) {
		this.telNum2 = telNum2;
	}
 
	 

	 

	public String getPrintSet() {
		return printSet;
	}

	public void setPrintSet(String printSet) {
		this.printSet = printSet;
	}

	public Integer getDtype() {
		return dtype;
	}

	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}
	public Integer getTelSpace() {
		return telSpace;
	}
	public void setTelSpace(Integer telSpace) {
		this.telSpace = telSpace;
	}
	public Integer getMessageSpace() {
		return messageSpace;
	}
	public void setMessageSpace(Integer messageSpace) {
		this.messageSpace = messageSpace;
	}
	public Integer getField155() {
		return field155;
	}
	public void setField155(Integer field155) {
		this.field155 = field155;
	}
	public Integer getField156() {
		return field156;
	}
	public void setField156(Integer field156) {
		this.field156 = field156;
	}
	public Integer getField157() {
		return field157;
	}
	public void setField157(Integer field157) {
		this.field157 = field157;
	}
	public Integer getField158() {
		return field158;
	}
	public void setField158(Integer field158) {
		this.field158 = field158;
	}
	public Integer getField159() {
		return field159;
	}
	public void setField159(Integer field159) {
		this.field159 = field159;
	}
	public Integer getField160() {
		return field160;
	}
	public void setField160(Integer field160) {
		this.field160 = field160;
	}
	public Integer getField161() {
		return field161;
	}
	public void setField161(Integer field161) {
		this.field161 = field161;
	}
	public Integer getField162() {
		return field162;
	}
	public void setField162(Integer field162) {
		this.field162 = field162;
	}
	public Integer getOneType() {
		if(oneType==null)oneType=0;
		return oneType;
	}
	public void setOneType(Integer oneType) {
		this.oneType = oneType;
	}
	public Table10 getParent() {
		return parent;
	}
	public void setParent(Table10 parent) {
		this.parent = parent;
	}

	
	
	public List<Table1> getUsers() {
		if(users==null)this.users=new ArrayList<Table1>();
		return users;
	}
	public void setUsers(List<Table1> users) {
		this.users = users;
	}
	public List<Table10Bak> getSecond() {
		if(second==null) this.second=new ArrayList<Table10Bak>();
		return second;
	}
	public void setSecond(List<Table10Bak> second) {
		this.second = second;
	}
	/**
	 * 准备128台二层设备
	 */
/*	public void loadSecond(){
		this.second=new ArrayList<Table10>();
		for (int i = 1; i <= 128; i++) {
			Table10 sec=new Table10();
			sec.setField3(this.field3);
			sec.setField4(i);
			this.second.add(sec);
		}
	}*/

	/**
	 * 保存初始信息时使用。
	 * 这样做的原因 是为了减少session的冲突 
	 * @param info
	 */
	public void copyU(Table10 info){
		//填充除了主键外的所有信息
		Method[] ms=this.getClass().getMethods();
		for (int i = 0; i < ms.length; i++) {
			if(ms[i].getName().indexOf("set")<1&&!ms[i].getName().equals("setField1")){
				try 
				{
					Object obj= MethodUtils.invokeExactMethod(info, ms[i].getName().replaceFirst("set","get"),new Object[]{});
					if(obj!=null)
					MethodUtils.invokeExactMethod(this, ms[i].getName(),new Object[]{});
				} catch (NoSuchMethodException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				} catch (InvocationTargetException e) {
					continue;
				}
				
			}
		}
		
	}
	/**
	 * 更新缓存对象时使用.这样做的原因 是为了减少session的冲突 
	 * @param info
	 */
	public void copyN(Table10 info){
		//填充主键1
		this.setField1(info.getField1());
		//填充其它信息
		this.copyU(info);
	}
	
	// Property accessors
 


	
	
}