package com.cdk.ats.web.utils;

import java.util.List;
/***
 * 树实体
 * @author cdk
 *
 */
public class Tree {
	private Integer id;//主键
	private Integer parentid=-1;//父级节点号  []
	private Object text;//树的显示名
	private boolean leaf=false;//树是否有子节点
	private List<Tree> children;//了级节点
	
	public Tree() {
	}
	public Tree(Integer parentid,Integer id,String text, boolean leaf) {
		this.id=id;
		this.parentid=parentid;
		this.text = text;
		this.leaf = leaf;
	}
	public Tree(Integer id,String text, boolean leaf) {
		this.id=id;
		this.text = text;
		this.leaf = leaf;
	}
	public Tree(Integer id,String text, boolean leaf, List<Tree> children) {
		this.text = text;
		this.leaf = leaf;
		this.children = children;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Object getText() {
		return text;
	}
	public void setText(Object text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
 
	 
 
	

}
