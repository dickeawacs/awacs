package com.cdk.ats.web.pojo.hbm;

/**
 * 2.5 系统类型码表 Table5 entity. @author MyEclipse Persistence Tools
 */

public class Table5 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -439355554514479375L;
	/*** 类型序号 ***/
	private Integer field1;
	/*** 类型描述 ***/
	private String field2;
	/*** 父级序号 ***/
	private Integer field3;
	/*** 显示顺序 ***/
	private Integer field4;
	/*** 备注 ***/
	private String field5;

	// Constructors

	/** default constructor */
	public Table5() {
	}

	/** full constructor */
	public Table5(String field2, Integer field3, Integer field4, String field5) {
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
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

	public Integer getField4() {
		return this.field4;
	}

	public void setField4(Integer field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return this.field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

}