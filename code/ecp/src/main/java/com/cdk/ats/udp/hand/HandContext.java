package com.cdk.ats.udp.hand;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.utils.CommandTools;

/***
 * 握手包容器
 * @author dingkai
 *
 */
public class HandContext {
	private final static Logger logger=Logger.getLogger(HandContext.class);
	//握手后成功的流水号将被记录，并记录当前时间，key=流水号，value=当前时间的long
	private static ConcurrentMap<String,Long>  HandMap=new ConcurrentHashMap<String, Long>();
	/***
	 * 判断是否成功握手,每次调用将重置有效时间 
	 * @param key
	 * @return
	 */
	public static boolean has(int one,int two){
		//LogContent.r( "接收到握手包："+one+","+two);
		boolean end=false;
		String key=CommandTools.handKeyFormat(one, two);
		if(HandMap.containsKey(key)){HandMap.put(key,System.currentTimeMillis());end=true;}
		logger.debug("[refresh:"+key+" ="+end+"]");
		return end;
	}
 
	
	/***
	 * 删除一个握手纪录 
	 * @param key
	 */
	public static void del(int one,int two){
		HandMap.remove(CommandTools.handKeyFormat(one, two));
	}
	/***
	 * 根据时间点删除此时间点之前的信息
	 * 删除需要由线程调用 
	 * @param point
	 */
	public static void clear(Long point)
	{
		long thistime=System.currentTimeMillis();
		for (Iterator<String> keys = HandMap.keySet().iterator(); keys.hasNext();) {
			String key =   keys.next();
			if((thistime-HandMap.get(key))>point){
				logger.info("握手包超时，key:"+key);
				HandMap.remove(key);
			}
		}
	}

	/***
	 * 删除所有的握手包纪录 
	 */
	public static void clearAll(){
		HandMap.clear();
	}
	/*****
	 * 执行握手 ，由握手命令  0x40 0x02  调用  ,
	 * 描述 ：握手则需要清除以前的一整套设备缓存信息。
	 * @param key
	 */
	public static void shakeHands(int one,int two){
			if(one==0)return ;
		//如果没有握过手，就添加一个，
			if(!has(one, two)){ 
			//记录握手包
				HandMap.put(CommandTools.handKeyFormat(one, two), System.currentTimeMillis());
			}
			//删除缓存的设备快速查询信息
			AtsEquipmentCache.removeEquiIndex(one);
			//删除缓存的设备信息
			AtsEquipmentCache.removeEquipmentCacheVO(one);
			//记录心跳包
			HeartContext.StartHeartbeat(one, two);
			//创建缓存设备
			AtsEquipmentCache.getEqui(one, two);
	}
	/*****
	 * 刷新握手时间 
	 * @param key
	 */
/*	public static void refreshHand(String key){
		if(HandMap.containsKey(key))
		HandMap.put(key, System.currentTimeMillis());
		
	}*/
	
}
