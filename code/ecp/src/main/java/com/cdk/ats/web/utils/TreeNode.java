package com.cdk.ats.web.utils;

import java.util.List;

public class TreeNode {
	private String  id;//主键
	private String  parentid;//父级节点号  []
	private Object text;//树的显示名
	private boolean leaf=false;//树是否有子节点
	private List children;//了级节点
	
	public TreeNode() {
	}
	public TreeNode(String parentid,String id,String text, boolean leaf) {
		this.id=id;
		this.parentid=parentid;
		this.text = text;
		this.leaf = leaf;
	}
 
	public TreeNode(Integer id,String text, boolean leaf, List<TreeNode> children) {
		this.text = text;
		this.leaf = leaf;
		this.children = children;
	}
	 
 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
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
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
 
	 
 
	

}
