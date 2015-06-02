package com.cdk.ats.plugs.cmd;

import com.cdk.ats.plugs.vo.CmdDownParamsVO;


/***
 * 
* @Title: CmdDown.java 
* @Package com.cdk.ats.plugs.cmd 
* @Description: 下行命令接口
* @author 陈定凯 
* @date 2015年5月26日 下午9:43:43 
* @version V1.0
 */
public interface CmdDown extends CmdPlugs {


	/***
	 * 
	* 
	* @Description: 处理用户向下发的设备命令，需要保存至数据库或缓存
	* @author 陈定凯 
	* @date 2015年5月26日 下午9:31:36  
	* @param inData
	* @return
	 */
	public byte[] processDown(byte[] inData); 
	
	/***
	 * 
	* 
	* @Description: 下传命令，设备响应事件处理方法 。
	* @author 陈定凯 
	* @date 2015年5月26日 下午10:30:15  
	* @param responseData 响应的字节数据
	* @param params 下行前缓存的业务数据，用于响应后处理。
	* @return
	 */
	public DownResultModel processDownResponse(byte[] responseData,CmdDownParamsVO params); 



}
