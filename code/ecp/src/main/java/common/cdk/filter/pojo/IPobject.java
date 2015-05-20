package common.cdk.filter.pojo;

import org.apache.commons.lang.math.NumberUtils;
/***
 * 
 * @author dingkai
 *
 */
public class IPobject {
private String name;
private String beginStr;
private String endStr;
private Integer[] begin=new Integer[4];
private Integer[] end=new Integer[4];
private String ipSwtich="on";
public IPobject(String name, String beginStr, String endStr, Integer[] begin,
		Integer[] end,String ipSwtich) {
	super();
	this.name = name;
	this.beginStr = beginStr;
	this.endStr = endStr;
	this.begin = begin;
	this.end = end;
	this.ipSwtich=ipSwtich;
}
public IPobject(){}
public IPobject(String name,String beginStr,String endStr,String ipSwtich){
	this.name = name;
	this.beginStr = beginStr;
	this.endStr = endStr;
	this.begin=formatIP(beginStr);
	this.end=formatIP(endStr);
	this.ipSwtich=ipSwtich;
}
public static Integer[] formatIP(String ipStr){
	Integer[] ip=null;
	if(ipStr!=null&&!ipStr.trim().equals(""))
	{
		ip=new Integer[4];
		String[] temp=ipStr.split("[.]+");
		if(temp.length==4){
			for (int i = 0; i < temp.length; i++) {
				if(temp[i].length()<=3&&NumberUtils.isNumber(temp[i])){
					Integer it=NumberUtils.createInteger(temp[i]);
					if(i==0&&it>0&&it<224){
						ip[i]=it;
					}else if(it>=0&&it<=224)
						ip[i]=it;
					else return null;
				}
				else return null;
			}
		}else return null;
	}
	return ip;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getBeginStr() {
	return beginStr;
}
public void setBeginStr(String beginStr) {
	this.beginStr = beginStr;
}
public String getEndStr() {
	return endStr;
}
public void setEndStr(String endStr) {
	this.endStr = endStr;
}
public Integer[] getBegin() {
	return begin;
}
public void setBegin(Integer[] begin) {
	this.begin = begin;
}
public Integer[] getEnd() {
	return end;
}
public void setEnd(Integer[] end) {
	this.end = end;
}
public String getIpSwtich() {
	return ipSwtich;
}
public void setIpSwtich(String ipSwtich) {
	this.ipSwtich = ipSwtich;
}

}
