package com.cdk.ats.plugs.cmd;

import com.cdk.ats.plugs.vo.CmdDownParamsVO;

/***
 * 
 * @Title: CmdPlugs.java
 * @Package com.cdk.ats.plugs.cmd
 * @Description: 所有命令接口的顶级接口
 * @author 陈定凯
 * @date 2015年5月26日 下午9:25:18
 * @version V1.0
 */
public interface CmdPlugs {

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
	 * @param responseData
	 *            响应的字节数据
	 * @param params
	 *            下行前缓存的业务数据，用于响应后处理。
	 * @return
	 */
	public DownResultModel processDownResponse(byte[] responseData,
			CmdDownParamsVO params);

	/***
	 * 
	 * 
	 * @Description: 获取当前命令
	 * @author 陈定凯
	 * @date 2015年5月26日 下午9:32:54
	 * @return
	 */
	public byte[] cmd();

	/***
	 * 
	 * 
	 * @Description: 获取当前命令串
	 * @author 陈定凯
	 * @date 2015年5月26日 下午9:33:20
	 * @return 0x01-0x01="0101"
	 */
	public String cmdKey();

	/***
	 * 
	 * 
	 * @Description: 处理设备上行命令:上行命令由设备触发， 需要直接响应结果。根据需要确认是保存至数据库还是缓存
	 * @author 陈定凯
	 * @date 2015年5月26日 下午9:31:36
	 * @param inData
	 * @return
	 */
	public byte[] processUp(byte[] inData);

}
