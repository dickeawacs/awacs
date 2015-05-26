package com.cdk.ats.plugs.vo;

import java.util.List;
/***
 * 
* @Title: ShedVO.java 
* @Package com.cdk.ats.plugs.vo 
* @Description: 棚VO
* @author 陈定凯 
* @date 2015年5月26日 下午11:43:22 
* @version V1.0
 */
public class ShedVO {
	
	
	/***
	 * 仪表
	 */
	private List<MeterVO> meters;
	
	private String shedName;
	private Integer shedId;
	private String  mapSrc;
	/**
	 * @return the meters
	 */
	public List<MeterVO> getMeters() {
		return meters;
	}
	/**
	 * @param meters the meters to set
	 */
	public void setMeters(List<MeterVO> meters) {
		this.meters = meters;
	}
	/**
	 * @return the shedName
	 */
	public String getShedName() {
		return shedName;
	}
	/**
	 * @param shedName the shedName to set
	 */
	public void setShedName(String shedName) {
		this.shedName = shedName;
	}
	/**
	 * @return the shedId
	 */
	public Integer getShedId() {
		return shedId;
	}
	/**
	 * @param shedId the shedId to set
	 */
	public void setShedId(Integer shedId) {
		this.shedId = shedId;
	}
	/**
	 * @return the mapSrc
	 */
	public String getMapSrc() {
		return mapSrc;
	}
	/**
	 * @param mapSrc the mapSrc to set
	 */
	public void setMapSrc(String mapSrc) {
		this.mapSrc = mapSrc;
	}
	
	
	
}
