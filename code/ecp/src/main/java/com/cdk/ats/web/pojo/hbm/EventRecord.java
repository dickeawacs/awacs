package com.cdk.ats.web.pojo.hbm;

import java.sql.Timestamp;

/**
 * EventRecord entity. @author MyEclipse Persistence Tools
 */

public class EventRecord implements java.io.Serializable {

	// Fields

	/**
	 * dingkai 2013-12-29
	 */
	private static final long serialVersionUID = -7148385473788627354L;
	/***
	 * 事件ID
	 */
	private Long eventId;
	/***
	 * 一层设备ID
	 */
	private Integer equipmentFid;
	/***
	 * 二层设备ID
	 */
	private Integer equipmentSid;
	/***
	 * 事件描述
	 */
	private String eventDesc;
	/***
	 * 终端描述
	 */
	private String eventTerminal;
	/***
	 * 事件类型
	 */
	private Integer eventType;
	/***
	 * 事件类型子分类号 
	 */
	private Integer eventTypeVal;
	
	/***
	 * 事件发生时间
	 */
	private Timestamp eventTime;
	/***
	 * 事件处理人
	 */
	private String processBy;
	/***
	 * 事件处理人ID
	 */
	private Integer processUid;
	/***
	 * 处理时间
	 */
	private Timestamp processTime;
	/***
	 * 处理备注
	 */
	private String processDesc;
	

	/**
	 * 端口号
	 */
	private Integer port=0;
	/***
	 * 端口类型,1-输入 ,2-输出
	 */
	private Integer ptype=0;
	
	private Integer onReady=0; 
	
	public EventRecord() {
		super();
	}

	public EventRecord(Long eventId, String processBy, Integer processUid,
			Timestamp processTime, String processDesc) {
		super();
		this.eventId = eventId;
		this.processBy = processBy;
		this.processUid = processUid;
		this.processTime = processTime;
		this.processDesc = processDesc;
	}

	public EventRecord(Integer equipmentFid, Integer equipmentSid,
			String eventDesc, String eventTerminal, Integer eventType,Integer eventTypeVal,
			Timestamp eventTime) {
		super();
		this.equipmentFid = equipmentFid;
		this.equipmentSid = equipmentSid;
		this.eventDesc = eventDesc;
		this.eventTerminal = eventTerminal;
		this.eventType = eventType;
		this.eventTypeVal = eventTypeVal;
		this.eventTime = eventTime;
		
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Integer getEquipmentFid() {
		return equipmentFid;
	}

	public void setEquipmentFid(Integer equipmentFid) {
		this.equipmentFid = equipmentFid;
	}

	public Integer getEquipmentSid() {
		return equipmentSid;
	}

	public void setEquipmentSid(Integer equipmentSid) {
		this.equipmentSid = equipmentSid;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getEventTerminal() {
		return eventTerminal;
	}

	public void setEventTerminal(String eventTerminal) {
		this.eventTerminal = eventTerminal;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Timestamp getEventTime() {
		return eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

	public String getProcessBy() {
		return processBy;
	}

	public void setProcessBy(String processBy) {
		this.processBy = processBy;
	}

	public Integer getProcessUid() {
		return processUid;
	}

	public void setProcessUid(Integer processUid) {
		this.processUid = processUid;
	}

	public Timestamp getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Timestamp processTime) {
		this.processTime = processTime;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

 
   
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public Integer getEventTypeVal() {
		return eventTypeVal;
	}

	public void setEventTypeVal(Integer eventTypeVal) {
		this.eventTypeVal = eventTypeVal;
	}

	public Integer getOnReady() {
		return onReady;
	}

	public void setOnReady(Integer onReady) {
		this.onReady = onReady;
	}

	
}