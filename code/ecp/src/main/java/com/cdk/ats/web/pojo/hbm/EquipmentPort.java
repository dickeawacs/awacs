package com.cdk.ats.web.pojo.hbm;
/***
 * 
 * 描述：  设备端口 
 * @author dingkai
 * 2014-1-9
 *
 */
public class EquipmentPort {
	//
	private Integer id;//Equipment ID
	private Integer equipmentFid;
	private Integer equipmentSid;
	private Integer index;//port NO,
	private int type=1;// [1-input,2-output]default input port,
	private int port=-1;//if this port using return 1  else return 0;
	private String name;
	private String asName;
	
	/***
	 * 
	 * @param equipmentFid
	 * @param equipmentSid
	 * @param index
	 * @param type
	 * @param port
	 * @param name
	 * @param asname
	 */
	public EquipmentPort(int id,Integer equipmentFid, Integer equipmentSid,
			Integer index, int type, int port, String name,String asname) {
		super();
		this.id=id;
		this.equipmentFid = equipmentFid;
		this.equipmentSid = equipmentSid;
		this.index = index;
		this.type = type;
		this.port = port;
		this.name = name;
		this.asName=asname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getAsName() {
		return asName;
	}
	public void setAsName(String asName) {
		this.asName = asName;
	} 
	
	
}
