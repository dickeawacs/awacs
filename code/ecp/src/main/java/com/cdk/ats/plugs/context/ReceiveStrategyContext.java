package com.cdk.ats.plugs.context;

import java.util.HashMap;
import java.util.Map;

import com.cdk.ats.plugs.biz.IPlugsReceiveCmdBiz;
import common.ecp.utils.SpringBeanUtil;


public class ReceiveStrategyContext {
	
	Map<String, String> plugs=new HashMap<String, String>();

	/**
	 * @return the plugs
	 */
	public Map<String, String> getPlugs() {
		return plugs;
	}

	/**
	 * @param plugs the plugs to set
	 */
	public void setPlugs(Map<String, String> plugs) {
		this.plugs = plugs;
	}
	/***
	 * 
	* 
	* @Description:通过命令，匹配获取业务处理类，无则返回NULL
	* @author 陈定凯 
	* @date 2015年5月13日 下午6:40:35  
	* @param cmd
	* @return
	 */
	public IPlugsReceiveCmdBiz getReceiveCmdBiz(String cmd){
		if(plugs.get(cmd)!=null){
			return  (IPlugsReceiveCmdBiz) SpringBeanUtil.getInstance().getBean(plugs.get(cmd));
		}
		return null;
	}
	
	

	
	
	
}
