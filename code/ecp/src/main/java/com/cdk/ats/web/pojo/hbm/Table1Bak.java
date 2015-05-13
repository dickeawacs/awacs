package com.cdk.ats.web.pojo.hbm;

/**
 * 2.1	用户表
 * Table1 entity. @author MyEclipse Persistence Tools
 */

public class Table1Bak implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5867075256372670767L;
	/*** 用户序号 ***/
	private Integer field1;
	/*** 真实姓名/  用户名称 ***/
	//private String field2;
	/*** 帐号 ***/
	private String field3;
	/*** 密码 ***/
	private String field4;
	/*** 用户级别 [[1,"系统管理员"],[2,"设备管理员"],[3,"设备用户"]]***/
	private Integer field5;
	/*** 禁用此用户（0/1  1为禁用） ***/
	private Integer field6;
	/*** 电话号码 ***/
	private String field7;
	/*** 禁用电话（0/1  1为禁用） ***/
	private Integer field8;
	/*** 短信号码 ***/
	private String field9;
	/*** 禁用短信（0/1  1为禁用） ***/
	private Integer field10;
	/**一层设备地址**/
	private Integer field11;
	/**用户用户1（0x01）用户2（0x02）用户3（0x03）用户4（0x04）。。。。。用户10（0x0a）。。。。。。用户16（0x10）**/
	private Integer field12;
	/**设备类型*/
	private Integer field13;
	/**布防状态[布防 1，撤防 2，查看 3]**/
	private Integer field14;
	
	private String   bakpwd;//临时密码
	// Constructors

	/** default constructor */
	public Table1Bak() {
	}

	/** full constructor */
	public Table1Bak( String field3, String field4, Integer field5,
			Integer field6, String field7, Integer field8, String field9,
			Integer field10,Integer field11,Integer field12,Integer field13,Integer field14) {
		//this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.field6 = field6;
		this.field7 = field7;
		this.field8 = field8;
		this.field9 = field9;
		this.field10 = field10;
		this.field11=field11;
		this.field12=field12;
		this.field13=field13;
		this.field14=field14;
	}

	// Property accessors

	public Integer getField1() {
		return this.field1;
	}

	public void setField1(Integer field1) {
		this.field1 = field1;
	}
/*
	public String getField2() {
		return this.field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}*/

	public String getField3() {
		return this.field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return this.field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public Integer getField5() {
		return this.field5;
	}

	public void setField5(Integer field5) {
		this.field5 = field5;
	}

	public Integer getField6() {
		return this.field6;
	}

	public void setField6(Integer field6) {
		this.field6 = field6;
	}

	public String getField7() {
		return this.field7;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public Integer getField8() {
		return this.field8;
	}

	public void setField8(Integer field8) {
		this.field8 = field8;
	}

	public String getField9() {
		return this.field9;
	}

	public void setField9(String field9) {
		this.field9 = field9;
	}

	public Integer getField10() {
		return this.field10;
	}

	public void setField10(Integer field10) {
		this.field10 = field10;
	}

	public Integer getField11() {
		return field11;
	}

	public void setField11(Integer field11) {
		this.field11 = field11;
	}

	public Integer getField12() {
		return field12;
	}

	public void setField12(Integer field12) {
		this.field12 = field12;
	}

	public Integer getField13() {
		return field13;
	}

	public void setField13(Integer field13) {
		this.field13 = field13;
	}

	public Integer getField14() {
		return field14;
	}

	public void setField14(Integer field14) {
		this.field14 = field14;
	}

	public String getBakpwd() {
		return bakpwd;
	}

	public void setBakpwd(String bakpwd) {
		this.bakpwd = bakpwd;
	}

}