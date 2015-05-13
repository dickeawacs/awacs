package com.cdk.ats.web.utils;

public class TreeNodeCheckbox extends TreeNode{ 
 
	private String  name;//
	private String  value;//对应的值  
	private boolean checked=false;//是否被选中

	public TreeNodeCheckbox(){
		this.setLeaf(true);
	}
	/***
	 * 使用此构造函数，叶节点的checkbox的name默认为 “treeCheckValues”,value默认为 @id  
	 * @param id 节点ID
	 * @param parentID 父级ID
	 * @param text   显示内容 
	 */
	public TreeNodeCheckbox(String id,String parentID,String text){
		super.setId(id);
		super.setParentid(parentID);
		super.setText(text);
		
		super.setLeaf(true);
		this.name="treeCheckValues";
		this.value=id;
	}
	public TreeNodeCheckbox(String id,String parentID,boolean checked){
		super.setId(id);
		super.setParentid(parentID);
		
		super.setLeaf(true);
		this.name="treeCheckValues";
		this.value=id;
		this.checked=checked;
	}
	public TreeNodeCheckbox(String id,String parentID,String text,boolean checked,String name,Object value){
		super.setId(id);
		super.setParentid(parentID);
		super.setText(text);
		super.setLeaf(true);	
		this.checked=checked;
		this.name=name;
		this.value=(value!=null?value.toString():"-1");
	}
	 
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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
