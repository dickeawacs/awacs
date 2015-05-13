package com.cdk.ats.web.pojo.hbm;

import java.util.List;

/**
 * Map entity. @author MyEclipse Persistence Tools
 */

public class Map implements java.io.Serializable {

	/**
	 * dingkai
	 * 2013-12-22
	 */
	private static final long serialVersionUID = 6773197488704202314L;
	// Fields

	private Integer mapid;
	private String mapname;
	private String mapdesc;
	private String urlpath;
	private Integer isuse;
	private Integer width;
	private Integer height;
	private List<Mappoint> points;

	// Constructors

	/** default constructor */
	public Map() {
	}

	/** full constructor */
	public Map(String mapname, String mapdesc, String urlpath, Integer isuse) {
		this.mapname = mapname;
		this.mapdesc = mapdesc;
		this.urlpath = urlpath;
		this.isuse = isuse;
	}

	// Property accessors

	public Integer getMapid() {
		return this.mapid;
	}

	public void setMapid(Integer mapid) {
		this.mapid = mapid;
	}

	public String getMapname() {
		return this.mapname;
	}

	public void setMapname(String mapname) {
		this.mapname = mapname;
	}

	public String getMapdesc() {
		return this.mapdesc;
	}

	public void setMapdesc(String mapdesc) {
		this.mapdesc = mapdesc;
	}

	public String getUrlpath() {
		return this.urlpath;
	}

	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}

	public Integer getIsuse() {
		return this.isuse;
	}

	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

	public List<Mappoint> getPoints() {
		return points;
	}

	public void setPoints(List<Mappoint> points) {
		this.points = points;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}