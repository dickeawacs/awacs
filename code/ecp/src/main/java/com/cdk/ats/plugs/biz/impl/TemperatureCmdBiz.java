package com.cdk.ats.plugs.biz.impl;

import java.net.DatagramPacket;

import org.springframework.stereotype.Service;

import com.cdk.ats.plugs.biz.IPlugsReceiveCmdBiz;
/***
 * 
* @Title: TemperatureCmdBiz.java 
* @Package com.cdk.ats.plugs.biz.impl 
* @Description: 温度接收命令
* @author 陈定凯 
* @date 2015年5月13日 下午8:37:54 
* @version V1.0
 */
@Service
public class TemperatureCmdBiz implements IPlugsReceiveCmdBiz {

	@Override
	public Object receive(DatagramPacket dp) {
		// TODO Auto-generated method stub
		return null;
	}

}
