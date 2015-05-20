package com.cdk.ats.udp.process;

import java.util.ArrayList;
import java.util.List;
/***
 * 数据包发送实体，
 * @author dingkai
 *
 */
public class ActionReady {
	private byte [] data;//需要发送的数据
	private boolean isAction=true;//是否为请求
	private Integer key ;//流水号
	private String targetIp;//目标地址
	private int targetPort;//目标端口
	private List<ActionReady> childrens;//如果是一组命令发送则在此属性中加入对应的值 ，且只会发送此组
	public ActionReady(byte[] data,  Integer key) {
		this.data = data;
		this.key = key;
	}
	public ActionReady() {
	}
	 
	public ActionReady(byte[] data, boolean isAction, Integer key,
			String targetIp, int targetPort) {
		this.data = data;
		this.isAction = isAction;
		this.key = key;
		this.targetIp = targetIp;
		this.targetPort = targetPort;
	}
	public ActionReady(byte[] data, boolean isAction, Integer key) {
		this.data = data;
		this.isAction = isAction;
		this.key = key;
	}
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public boolean isAction() {
		return isAction;
	}
	public void setAction(boolean isAction) {
		this.isAction = isAction;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getTargetIp() {
		return targetIp;
	}
	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}
	public int getTargetPort() {
		return targetPort;
	}
	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}
	public List<ActionReady> getChildrens() {
		if(childrens==null)childrens=new ArrayList<ActionReady>();
		return childrens;
	}
	public void setChildrens(List<ActionReady> childrens) {
		this.childrens = childrens;
	}
	
}
