package com.cdk.ats.udp.heart;

import org.apache.commons.lang.math.NumberUtils;

import common.cdk.config.files.appconfig.WebAppConfig;

/***
 * 心跳验证线程，每隔一断时间判断是否有已经停止心跳的设备
 * @author dingkai
 *
 */
public class HeartBeatCheckThread implements Runnable{
	/**掉线间隔时间,默认30000ms , 来自webapp配置文件 ，drop.interval **/
	public static Long  maxSpace=dropInterval();//心跳的验证时间为5s
	/***
	 * 
	 * 描述：获取掉线间隔时间，毫秒
	 * @createBy dingkai
	 * @createDate 2014-10-8
	 * @lastUpdate 2014-10-8
	 * @return
	 */
	public static  Long dropInterval(){
		Long value=30000L;
		String times=WebAppConfig.app("drop.interval");
		if(times!=null&&NumberUtils.isNumber(times)){
			Long tempValue=NumberUtils.createLong(times);
			if (tempValue>500) {
				value=tempValue;				
			}
		}
		
		
		return value;
		
	}
	public void run() {
		
		while (true) {
			try {
				Thread.sleep(maxSpace);
				HeartContext.checkHeart();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
