package com.cdk.ats.web.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.Tree;

public class QueryBiz {
	private Logger logger = Logger.getLogger(QueryBiz.class);
	
	/***
	 * 格式为树
	 * @param t9
	 * @return
	 */
	public List<Tree> t9FormatTree(List<Object[]> t9){
		List<Tree> array = new ArrayList<Tree>();
		if(t9!=null&&!t9.isEmpty())
		for(int i=0;i<t9.size();i++){
			Tree tree = new Tree();
			Integer id= Integer.parseInt(t9.get(i)[0].toString());
			tree.setId(id);
			tree.setParentid(0);
			tree.setText(formateText(t9.get(i)[0],t9.get(i)[1]));
			tree.setLeaf(true);
			/*List<Tree> chs=new ArrayList<Tree>();
			chs.add(new Tree(id,"子节",true));
			tree.setChildren(chs);*/
			array.add(tree);
		}
		return array;
	}
	private String formateText(Object mac,Object name){ 
		return (mac!=null?mac:"")+"-"+(name!=null?name:"");
	}
	
	/***
	 * 格式为树
	 * @param t10
	 * @return
	 */
	public List<Tree> t10FormatTree(List<Table10> t10){
		List<Tree> array = new ArrayList<Tree>();
		try{
		if(t10!=null&&!t10.isEmpty())
		for(int i=0;i<t10.size();i++){
			Tree tree = new Tree();
			tree.setId(t10.get(i).getField1());
			tree.setParentid(t10.get(i).getField2());
			tree.setText(t10.get(i).getField4());
			boolean leaf = false;
			List<Tree> treeC = new ArrayList<Tree>();
			if(t10.get(i).getField113()!=null && t10.get(i).getField113()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField113(),false));
			}
			if(t10.get(i).getField114()!=null && t10.get(i).getField114()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField114(),false));
			}
			if(t10.get(i).getField115()!=null && t10.get(i).getField115()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField115(),false));
			}
			if(t10.get(i).getField116()!=null && t10.get(i).getField116()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField116(),false));
			}
			if(t10.get(i).getField117()!=null && t10.get(i).getField117()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField117(),false));
			}
			if(t10.get(i).getField118()!=null && t10.get(i).getField118()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField118(),false));
			}
			if(t10.get(i).getField119()!=null && t10.get(i).getField119()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField119(),false));
			}
			if(t10.get(i).getField120()!=null && t10.get(i).getField120()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField120(),false));
			}
			/*******输入********/
			if(t10.get(i).getField105()!=null && t10.get(i).getField105()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField105(),false));
			}
			if(t10.get(i).getField106()!=null && t10.get(i).getField106()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField106(),false));
			}
			if(t10.get(i).getField107()!=null && t10.get(i).getField107()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField107(),false));
			}
			if(t10.get(i).getField108()!=null && t10.get(i).getField108()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField108(),false));
			}
			if(t10.get(i).getField109()!=null && t10.get(i).getField109()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField109(),false));
			}
			if(t10.get(i).getField110()!=null && t10.get(i).getField110()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField110(),false));
			}
			if(t10.get(i).getField111()!=null && t10.get(i).getField111()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField111(),false));
			}
			if(t10.get(i).getField112()!=null && t10.get(i).getField112()!=""){
				leaf = true;
				treeC.add(new Tree(tree.getId(),tree.getId()*1000+1,t10.get(i).getField112(),false));
			}
			tree.setLeaf(leaf);
			if(treeC.size()>0){
				tree.setChildren(treeC);
			}
			array.add(tree);
			
		}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return array;
	}
	
}
