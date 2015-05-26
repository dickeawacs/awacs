package com.cdk.ats.plugs.cmd.impl;

import java.net.DatagramPacket;

import org.springframework.stereotype.Service;

import com.cdk.ats.plugs.biz.IPlugsReceiveCmdBiz;
import com.cdk.ats.plugs.cmd.CmdPlugs;
import com.cdk.ats.plugs.cmd.DownResultModel;
import com.cdk.ats.plugs.vo.CmdDownParamsVO;
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
public class AnalogQuantityCmdBiz implements CmdPlugs {

	/* (non-Javadoc)
	 * @see com.cdk.ats.plugs.cmd.CmdDown#processDown(byte[])
	 */
	@Override
	public byte[] processDown(byte[] inData) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cdk.ats.plugs.cmd.CmdDown#processDownResponse(byte[], com.cdk.ats.plugs.vo.CmdDownParamsVO)
	 */
	@Override
	public DownResultModel processDownResponse(byte[] responseData,
			CmdDownParamsVO params) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cdk.ats.plugs.cmd.CmdPlugs#processInit(byte[])
	 */
	@Override
	public byte[] processInit(byte[] inData) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cdk.ats.plugs.cmd.CmdPlugs#cmd()
	 */
	@Override
	public byte[] cmd() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cdk.ats.plugs.cmd.CmdPlugs#cmdKey()
	 */
	@Override
	public String cmdKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cdk.ats.plugs.cmd.CmdPlugs#processUp(byte[])
	 */
	@Override
	public byte[] processUp(byte[] inData) {
		// TODO Auto-generated method stub
		return null;
	}

 
 
}
