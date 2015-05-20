package com.cdk.ats.web.pojo.hbm;

/**
 * 2.3 用户提示音表 Table3 entity. @author MyEclipse Persistence Tools
 */

public class Table3 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6909833588527421906L;
	/*** 提示音序号 ***/
	private Integer field1;
	/*** 用户序号 ***/
	private Integer field2;
	/*** 名称 ***/
	private String field3;
	/*** 存储绝对路径 ***/
	private String field4;
	/*** 存储相对路径 ***/
	private String field5;
	/*** 提示音类型[系统/普通] ***/
	private String field6;

	// Constructors

	/** default constructor */
	public Table3() {
	}

	/** full constructor */
	public Table3(Integer field2, String field3, String field4, String field5,
			String field6) {
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.field6 = field6;
	}

	// Property accessors

	public Integer getField1() {
		return this.field1;
	}

	public void setField1(Integer field1) {
		this.field1 = field1;
	}

	public Integer getField2() {
		return this.field2;
	}

	public void setField2(Integer field2) {
		this.field2 = field2;
	}

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

	public String getField5() {
		return this.field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getField6() {
		return this.field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

}