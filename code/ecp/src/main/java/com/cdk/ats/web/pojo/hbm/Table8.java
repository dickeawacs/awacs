package com.cdk.ats.web.pojo.hbm;

import java.sql.Timestamp;

/**
 * 2.8 系统配置地图表 Table8 entity. @author MyEclipse Persistence Tools
 */

public class Table8 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 136441926997991722L;
	/*** 地图序号 ***/
	private Integer field1;
	/*** 地图名称 ***/
	private String field2;
	/*** 状态 ***/
	private Integer field3;
	/*** 绝对路径 ***/
	private String field4;
	/*** 相对路径 ***/
	private String field5;
	/*** 上传人 ***/
	private String field6;
	/*** 上传时间 ***/
	private Timestamp field7;
	/*** 宽度 ***/
	private Integer field8;
	/*** 高度 ***/
	private Integer field9;

	// Constructors

	/** default constructor */
	public Table8() {
	}

	/** full constructor */
	public Table8(String field2, Integer field3, String field4, String field5,
			String field6, Timestamp field7, Integer field8, Integer field9) {
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.field6 = field6;
		this.field7 = field7;
		this.field8 = field8;
		this.field9 = field9;
	}

	// Property accessors

	public Integer getField1() {
		return this.field1;
	}

	public void setField1(Integer field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return this.field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public Integer getField3() {
		return this.field3;
	}

	public void setField3(Integer field3) {
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

	public Timestamp getField7() {
		return this.field7;
	}

	public void setField7(Timestamp field7) {
		this.field7 = field7;
	}

	public Integer getField8() {
		return this.field8;
	}

	public void setField8(Integer field8) {
		this.field8 = field8;
	}

	public Integer getField9() {
		return this.field9;
	}

	public void setField9(Integer field9) {
		this.field9 = field9;
	}

}