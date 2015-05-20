package com.cdk.ats.udp.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.pojo.hbm.Table10;

/***
 * 
 * @author dingkai
 * 用户设备缓存
 */
public class EquipmentCacheVO {
	/***
	 * 主键 
	 */
	private Integer id;
	private String  parentid;//父级节点号  []
	private String text;//树的显示名
	private boolean leaf=false;//树是否有子节点
	/***
	 * 设备名
	 */
	private String name;
 
	/***
	 * 设备 类型
	 */
	private String type;
	/***
	 * 设备状态[]
	 */
	private String status;
	/***
	 * 屏蔽
	 */
	private String shieled;
	/**
	 * 二层设备网络地址，，若是一级设备 则为0 
	 */
	private Integer address;
	/***
	 * 一层设备网络地址 
	 */
	private Integer parentAddress;
	/***
	 * 设备生产序列号(设备条码)
	 */
	private String barCode;
	/***
	 * 设备唯一ID
	 */
	private String sequence;
	/**
	 * 端口
	 */
	private String port;
	/***
	 * ip地址 
	 */
	private String ip;
	/***
	 * 通讯[正常/异常]
	 */
	private String communication;
	/**
	 * 电压[低电压/高电压/正常]
	 */
	private String voltage;
	/***
	 * 设备被撬[正常/被撬]
	 */
	private String lose;
	/***
	 * 输入数量
	 */
	private Integer inputCount;
	/***
	 * 输出数量
	 */
	private Integer outputCount;
	/***
	 * 下级设备（一层设备下才带有二级设备 ）
	 */
	private List<EquipmentCacheVO> children;
	
	
	public EquipmentCacheVO() {
	}
	public EquipmentCacheVO(Integer id, String name, String type,
			String status, String shieled, Integer address,
			Integer parentAddress, String barCode, String sequence,
			String port, String ip, String communication, String voltage,
			String lose, Integer inputCount, Integer outputCount,
			List<EquipmentCacheVO> children) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.status = status;
		this.shieled = shieled;
		this.address = address;
		this.parentAddress = parentAddress;
		this.barCode = barCode;
		this.sequence = sequence;
		this.port = port;
		this.ip = ip;
		this.communication = communication;
		this.voltage = voltage;
		this.lose = lose;
		this.inputCount = inputCount;
		this.outputCount = outputCount;
		this.children = children;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShieled() {
		return shieled;
	}
	public void setShieled(String shieled) {
		this.shieled = shieled;
	}
	public Integer getAddress() {
		return address;
	}
	public void setAddress(Integer address) {
		this.address = address;
	}
	public Integer getParentAddress() {
		return parentAddress;
	}
	public void setParentAddress(Integer parentAddress) {
		this.parentAddress = parentAddress;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getLose() {
		return lose;
	}
	public void setLose(String lose) {
		this.lose = lose;
	}
	public Integer getInputCount() {
		return inputCount;
	}
	public void setInputCount(Integer inputCount) {
		this.inputCount = inputCount;
	}
	public Integer getOutputCount() {
		return outputCount;
	}
	public void setOutputCount(Integer outputCount) {
		this.outputCount = outputCount;
	}
	public List<EquipmentCacheVO> getChildren() {
		if(children==null){
			children=new ArrayList<EquipmentCacheVO>();
		}
		return children;
	}
	public void setChildren(List<EquipmentCacheVO> childrens) {
		this.children = childrens;
	}
	
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getText() {
		if(text==null){text="设备"+parentAddress+"-"+address;}
		if(Constant.EQUIPMENT_STATUS_NORMAL.equals(status)){
			return text;
		}else
		if(Constant.EQUIPMENT_STATUS_LOSE.equals(status)){
			return "<font color=red >"+text+"(掉线)</font>";
		}else if(Constant.EQUIPMENT_STATUS_DISABLE.equals(status)){
			//TEXT-DECORATION: line-through;
			return "<font color=red  style='background-color:#CDC0B0;'  >"+text+"(屏蔽)</font>";
			
		}/*else if(status.equals(Constant.EQUIPMENT_STATUS_)){
			return "<font color=#FACC2E   >(加载中)"+text+"</font>";
		}*/
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * 
	 * 描述：复制属性
	 * @createBy dingkai
	 * @createDate 2014-1-3
	 * @lastUpdate 2014-1-3
	 * @param t10
	 */
	public void copyfrom(Table10 t10){
		this.name=t10.getField121();
		this.text=this.name;
		
	}
 
	
	
	
	
	

}
