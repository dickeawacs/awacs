package com.cdk.ats.plugs.biz.impl;

import java.net.DatagramPacket;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdk.ats.plugs.biz.IPlugsReceiveCmdBiz;
import com.cdk.ats.plugs.cmd.CmdPlugs;

/****
 * 
* @Title: AnalogQuantityReceiver.java 
* @Package com.cdk.ats.plugs.biz.impl 
* @Description: 模拟量命令业务类
* @author 陈定凯 
* @date 2015年5月27日 上午1:10:49 
* @version V1.0
 */
@Service
public class AnalogQuantityReceiver implements IPlugsReceiveCmdBiz {
	@Resource
	private CmdPlugs cmd;
	@Override
	public Object receive(DatagramPacket dp) {
		 if(isResponse(  dp))
			;// cmd.processDownResponse(responseData, params)
		return null;
	}
	/***
	 * 
	* 
	* @Description:  问李，是否可以用一个字段标记是不是响应的数据，还是请求的数据
	* @author 陈定凯 
	* @date 2015年5月27日 上午1:16:03  
	* @param dp
	* @return
	 */
	private boolean isResponse(DatagramPacket dp){
		return false;
	}

}
