package com.cdk.ats.udp.hand;

/*****
 * 握手包自动 清理线程  
 * spaceTime为清理的间隔时间 
 * @author dingkai
 *
 */
public class HandClearThread implements Runnable {
	/***
	 * 5分钟自动清更一次握手包
	 */
	private static Long spaceTime=300000L;
	public static Long getSpaceTime() {
		return spaceTime;
	}

	public static void setSpaceTime(Long spaceTime) {
		HandClearThread.spaceTime = spaceTime;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(spaceTime);
				HandContext.clear(spaceTime);				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
