package com.cdk.ats.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.cdk.ats.web.pojo.hbm.Table10;
/****
 * 用户设置的格式化工具类
 * @author cdk
 *
 */
public class UserSetFormate {
	
	/***
	 * 格式化前台传入的用户权限选择
	 * 
	 * @param treeCheckValues
	 * @return
	 */
	public static List<String[]> treeCheckValuesFormate(String[] treeCheckValues){
		List<String[]> cvs=new ArrayList<String[]>();
		
		for (int i = 0; i < treeCheckValues.length; i++) {
			String[] temp=treeCheckValues[i].split("_");
			cvs.add(temp);
		}
		
		return cvs;
		
	}
	/***
	 * 格式化一层设置的输入输出权限
	 * @param formateValue
	 * @param ucode
	 * @return
	 *
	public static void treeCheckValuesFormateTable9(Table9 t9,List<String[]>  formateValue,int ucode){
		for (int i = 0; i < formateValue.size(); i++) {
			if(formateValue.get(i).length==3){
				t9.setField2(NumberUtils.createInteger(formateValue.get(i)[0]));
				Integer in=NumberUtils.createInteger(formateValue.get(i)[2]);
				if(formateValue.get(i)[2].equals("input")){
					switch (in) {
					case 1:
						t9.setField23(ucode);
						break;
					case 2:
						t9.setField24(ucode);

						break;
					case 3:
						t9.setField25(ucode);
						
						break;
					case 4:
						t9.setField26(ucode);
						
						break;
					case 5:
						t9.setField27(ucode);
						
						break;
					case 6:
						t9.setField28(ucode);
						
						break;
					case 7:
						t9.setField29(ucode);
						break;
					case 8:
						t9.setField30(ucode);
						break;
					default:
						break;
					}
				}else if(formateValue.get(i)[2].equals("output")){
					 
					switch (in) {
					case 1:
						t9.setField120(ucode);
						break;
					case 2:
						t9.setField121(ucode);

						break;
					case 3:
						t9.setField122(ucode);
						
						break;
					case 4:
						t9.setField123(ucode);
						
						break;
					case 5:
						t9.setField124(ucode);
						
						break;
					case 6:
						t9.setField125(ucode);
						
						break;
					case 7:
						t9.setField126(ucode);
						break;
					case 8:
						t9.setField127(ucode);
						break;
					default:
						break;
					}
				}
			} 
		}
		
	}*/
	/***
	 * 格式化一层设置的输入输出权限
	 * @param formateValue
	 * @param ucode
	 * @return
	 *
	public static Table9 treeCheckValuesFormateTable9(List<String[]>  formateValue,int ucode){
		Table9 t9=new Table9();
		for (int i = 0; i < formateValue.size(); i++) {
			if(formateValue.get(i).length==3){
				t9.setField2(NumberUtils.createInteger(formateValue.get(i)[0]));
				Integer in=NumberUtils.createInteger(formateValue.get(i)[2]);
				if(formateValue.get(i)[2].equals("input")){
					switch (in) {
					case 1:
						t9.setField23(ucode);
						break;
					case 2:
						t9.setField24(ucode);

						break;
					case 3:
						t9.setField25(ucode);
						
						break;
					case 4:
						t9.setField26(ucode);
						
						break;
					case 5:
						t9.setField27(ucode);
						
						break;
					case 6:
						t9.setField28(ucode);
						
						break;
					case 7:
						t9.setField29(ucode);
						break;
					case 8:
						t9.setField30(ucode);
						break;
					default:
						break;
					}
				}else if(formateValue.get(i)[2].equals("output")){
					 
					switch (in) {
					case 1:
						t9.setField120(ucode);
						break;
					case 2:
						t9.setField121(ucode);

						break;
					case 3:
						t9.setField122(ucode);
						
						break;
					case 4:
						t9.setField123(ucode);
						
						break;
					case 5:
						t9.setField124(ucode);
						
						break;
					case 6:
						t9.setField125(ucode);
						
						break;
					case 7:
						t9.setField126(ucode);
						break;
					case 8:
						t9.setField127(ucode);
						break;
					default:
						break;
					}
				}
			} 
		}
		return t9;
		
	}
	*/
	/***
	 * 封装用户的二层输入与输出端口权限
	 * @param formateValue   格式后的checkbox id
	 * @param ucode  用户的 一层设备所属编号 
	 * @return
	 */
	public static List<Table10> treeCheckValuesFormateTable10(List<String[]>  formateValue,int ucode){
		List<Table10> t10s=new ArrayList<Table10>();
		for (int i = 0; i < formateValue.size(); i++) {
			if(formateValue.get(i).length==5){
				Table10 t10=new Table10();
				//一层网络地址
				t10.setField3(NumberUtils.createInteger(formateValue.get(i)[0]));
				//二层网络地址
				t10.setField4(NumberUtils.createInteger(formateValue.get(i)[1]));
				//设置设备类型
				t10.setField10(NumberUtils.createInteger(formateValue.get(i)[4]));
				//端口号（1-8）
				Integer in=NumberUtils.createInteger(formateValue.get(i)[3]);
				//判断是输入还是输出
				if(formateValue.get(i)[2].equals("input")){
					switch (in) {
					case 1:
						t10.setField25(ucode);
						break;
					case 2:
						t10.setField26(ucode);

						break;
					case 3:
						t10.setField27(ucode);
						
						break;
					case 4:
						t10.setField28(ucode);
						
						break;
					case 5:
						t10.setField29(ucode);
						
						break;
					case 6:
						t10.setField30(ucode);
						
						break;
					case 7:
						t10.setField31(ucode);
						break;
					case 8:
						t10.setField32(ucode);
						break;
					default:
						break;
					}
				}else if(formateValue.get(i)[2].equals("output")){
					switch (in) {
					case 1:
						t10.setField122(ucode);
						break;
					case 2:
						t10.setField123(ucode);

						break;
					case 3:
						t10.setField124(ucode);
						
						break;
					case 4:
						t10.setField125(ucode);
						
						break;
					case 5:
						t10.setField126(ucode);
						
						break;
					case 6:
						t10.setField127(ucode);
						
						break;
					case 7:
						t10.setField128(ucode);
						break;
					case 8:
						t10.setField129(ucode);
						break;
					default:
						break;
					}
				}
				t10s.add(t10);
			}
		}
		return t10s;
		
	}
}
