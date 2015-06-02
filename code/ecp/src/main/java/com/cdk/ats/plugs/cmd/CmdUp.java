package com.cdk.ats.plugs.cmd;


/**
 * 
* @Title: CmdUp.java 
* @Package com.cdk.ats.plugs.cmd 
* @Description: 上行命令
* @author 陈定凯 
* @date 2015年5月26日 下午9:28:11 
* @version V1.0
 */
public interface CmdUp extends CmdPlugs{

	/***
	 * 
	* 
	* @Description: 处理设备上行命令:上行命令由设备触发，
	* 需要直接响应结果。根据需要确认是保存至数据库还是缓存
	* @author 陈定凯 
	* @date 2015年5月26日 下午9:31:36  
	* @param inData
	* @return
	 */
	public byte[] processUp(byte[] inData); 
}
