package com.cdk.ats.web.utils;

/***
 * 树实体
 * @author cdk
 *
 */
public class TreeCheck   { 
	private String  id;//主键
	private String  parentid;//父级节点号  []
	private String  text;//树的显示名
	private String  name;//
	private String  value;//对应的值 
	//private String  
	private boolean leaf=true;//树是否有子节点
	private boolean checked=false;//是否被选中

	public TreeCheck(){
		this.setLeaf(true);
	}
	public TreeCheck(String id,String parentID,String text,boolean checked,String name,String value){
		this.id=id;
		this.checked=checked;
		this.parentid=parentID;
		this.text=text;
		this.leaf=true;	
		this.value=value;
		this.name=name;
	}
	public TreeCheck(String id,String parentID,String text,boolean checked,String name,Object value){
		this.id=id;
		this.checked=checked;
		this.parentid=parentID;
		this.text=text;
		this.leaf=true;	
		this.value=(value!=null?value.toString():"-1");
		this.name=name;
	}
	 
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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
 
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
	 
 
	

}
