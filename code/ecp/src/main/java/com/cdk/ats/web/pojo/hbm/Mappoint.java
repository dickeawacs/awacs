package com.cdk.ats.web.pojo.hbm;

/**
 * Mappoint entity. @author MyEclipse Persistence Tools
 */

public class Mappoint implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6230892273217595768L;
	private Integer pointid;
	private Integer mapid;
	private Integer leftpx;
	private Integer toppx;
	private Integer isshow;
	private Integer zindex;
	private Integer width;
	private Integer height;
	private String color;
	private Integer equiparent;
	private Integer equiid;
	
	private int status=-1;//状态 -1 表示此点不存在 ，或是设备不存在 
	/***
	 * 端口名称
	 */
	private String name;

	/**
	 * 端口号1-8
	 */
	private Integer port;
	/**
	 * 端口类型，1-输入 ，2-输出 
	 */
	private Integer ptype;
	
	/**
	 * 图标
	 */
	private String icon;

	// Constructors

	/** default constructor */
	public Mappoint() {
	}

	/** full constructor */
	public Mappoint(Integer mapid, Integer leftpx, Integer toppx,
			Integer isshow, Integer zindex, Integer width, Integer height,
			String color, Integer equiparent, Integer equiid) {
		this.mapid = mapid;
		this.leftpx = leftpx;
		this.toppx = toppx;
		this.isshow = isshow;
		this.zindex = zindex;
		this.width = width;
		this.height = height;
		this.color = color;
		this.equiparent = equiparent;
		this.equiid = equiid;
	}

	// Property accessors

	public Integer getPointid() {
		return this.pointid;
	}

	public void setPointid(Integer pointid) {
		this.pointid = pointid;
	}

	public Integer getMapid() {
		return this.mapid;
	}

	public void setMapid(Integer mapid) {
		this.mapid = mapid;
	}

	public Integer getLeftpx() {
		return this.leftpx;
	}

	public void setLeftpx(Integer leftpx) {
		this.leftpx = leftpx;
	}

	public Integer getToppx() {
		return this.toppx;
	}

	public void setToppx(Integer toppx) {
		this.toppx = toppx;
	}

	public Integer getIsshow() {
		return this.isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	public Integer getZindex() {
		return this.zindex;
	}

	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getEquiparent() {
		return this.equiparent;
	}

	public void setEquiparent(Integer equiparent) {
		this.equiparent = equiparent;
	}

	public Integer getEquiid() {
		return this.equiid;
	}

	public void setEquiid(Integer equiid) {
		this.equiid = equiid;
	}

	/**
	 * 端口号1-8
	 */
	public Integer getPort() {
		return port;
	}
	/**
	 * 端口号1-8
	 */
	public void setPort(Integer port) {
		this.port = port;
	}
	/**
	 * 端口类型，1-输入 ，2-输出 
	 */
	public Integer getPtype() {
		return ptype;
	}
	/**
	 * 端口类型，1-输入 ，2-输出 
	 */
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}
	/***
	 * 端口名称
	 * **/
	public String getName() {
		return name;
	}

	/***
	 * 端口名称
	 * **/
	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	  
	
}