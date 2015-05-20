package com.cdk.ats.web.pojo.hbm;

import java.sql.Timestamp;

/**
 * 2.6 系统操作日志表 Table6 entity. @author MyEclipse Persistence Tools
 */

public class Table6 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8571103532155511241L;
	/*** 序号 ***/
	private Integer field1;
	/*** 操作时间 ***/
	private Timestamp field2;
	/*** 操作描述 ***/
	private String field3;
	/*** 操作类型 ***/
	private String field4;
	/*** 操作内容 ***/
	private String field5;
	/*** 操作人 ***/
	private String field6;

	// Constructors

	/** default constructor */
	public Table6() {
	}

	/** full constructor */
	public Table6(Timestamp field2, String field3, String field4,
			String field5, String field6) {
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

	public Timestamp getField2() {
		return this.field2;
	}

	public void setField2(Timestamp field2) {
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