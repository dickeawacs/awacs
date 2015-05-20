package com.cdk.ats.plugs.utils;

import com.cdk.ats.plugs.biz.IPlugsReceiveCmdBiz;
import com.cdk.ats.plugs.context.ReceiveStrategyContext;
import common.ecp.utils.SpringBeanUtil;

public class ReceiveStrategyFactory {
	
	private static ReceiveStrategyFactory strategy;
	private ReceiveStrategyContext strategyContext;
	private ReceiveStrategyFactory(){
	}

	public static ReceiveStrategyFactory instance(){
		if(strategy==null){
			strategy=new ReceiveStrategyFactory();
			strategy.strategyContext=SpringBeanUtil.getInstance().getBean(ReceiveStrategyContext.class);
		}
		return strategy;
	}
	
	public IPlugsReceiveCmdBiz getReceiver(String cmd){
		return getStrategyContext().getReceiveCmdBiz(cmd);
	}

	/**
	 * @return the strategyContext
	 */
	public ReceiveStrategyContext getStrategyContext() {
		return strategyContext;
	}
	
	
	
	
	
}
