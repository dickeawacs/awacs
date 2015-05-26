package com.cdk.ats.plugs.biz;

import java.net.DatagramPacket;

import com.cdk.ats.plugs.cmd.CmdDown;

/***
 * 
 * @Title: PlugsReceiveCmdBiz.java
 * @Package com.cdk.ats.plugs.biz
 * @Description: 接收命令的业务处理类。针对上传的所有命令（初始化、设置、上报 、下传响应）
 * @author 陈定凯
 * @date 2015年5月13日 下午6:17:44
 * @version V1.0
 */
public interface IPlugsReceiveCmdBiz {

	/***
	 * 
	 * 
	 * @Description: TODO
	 * @author 陈定凯
	 * @date 2015年5月13日 下午6:22:05
	 * @param dp
	 * @return
	 */
	public Object receive(DatagramPacket dp);
}
