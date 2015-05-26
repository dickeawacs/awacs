package com.cdk.ats.web.pojo.hbm;

/**
 * AtsAnalogquantitycfg entity. @author MyEclipse Persistence Tools
 */

public class AtsAnalogquantitycfg implements java.io.Serializable {

	// Fields

	private Integer cfgId;
	private String aqTypeName;
	private Integer aqType;
	private Integer mark;
	private Integer precision;
	private Integer limit;
	private Integer limitValue;

	// Constructors

	/** default constructor */
	public AtsAnalogquantitycfg() {
	}

	/** full constructor */
	public AtsAnalogquantitycfg(String aqTypeName, Integer aqType,
			Integer mark, Integer precision, Integer limit, Integer limitValue) {
		this.aqTypeName = aqTypeName;
		this.aqType = aqType;
		this.mark = mark;
		this.precision = precision;
		this.limit = limit;
		this.limitValue = limitValue;
	}

	// Property accessors

	public Integer getCfgId() {
		return this.cfgId;
	}

	public void setCfgId(Integer cfgId) {
		this.cfgId = cfgId;
	}

	public String getAqTypeName() {
		return this.aqTypeName;
	}

	public void setAqTypeName(String aqTypeName) {
		this.aqTypeName = aqTypeName;
	}

	public Integer getAqType() {
		return this.aqType;
	}

	public void setAqType(Integer aqType) {
		this.aqType = aqType;
	}

	public Integer getMark() {
		return this.mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getPrecision() {
		return this.precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getLimitValue() {
		return this.limitValue;
	}

	public void setLimitValue(Integer limitValue) {
		this.limitValue = limitValue;
	}

}