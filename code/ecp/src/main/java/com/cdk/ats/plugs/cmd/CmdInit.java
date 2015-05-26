package com.cdk.ats.plugs.cmd;


/***
 * 
* @Title: CmdInit.java 
* @Package com.cdk.ats.plugs.cmd 
* @Description: 初始化命令
* @author 陈定凯 
* @date 2015年5月26日 下午9:26:39 
* @version V1.0
 */
public interface CmdInit extends CmdPlugs {


	/***
	 * 
	* 
	* @Description: 处理设备初始化上行命令，需要保存至缓存 
	* @author 陈定凯 
	* @date 2015年5月26日 下午9:31:36  
	* @param inData
	* @return
	 */
	public byte[] processInit(byte[] inData); 

}
