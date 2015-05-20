package com.cdk.ats.udp.heart;

import java.net.DatagramPacket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.cache.EquipmentCacheVO;
import com.cdk.ats.udp.event.EventContext;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.udp.utils.TimmerSynchronization;
import com.cdk.ats.web.pojo.hbm.Table10;

/*****
 * 心跳包容器
 * 
 * @author dingkai
 * 
 */
public class HeartContext  {
	private static Logger logger = Logger.getLogger(HeartContext.class);

	private HeartContext() { 
	}

	/***
	 * 初始化心跳容器
	 */
	public static  void init() {
		//reload();
	}
 
	/***
	 * 心跳纪录容器
	 * **/
	private static  ConcurrentMap<String, Long> heartRecord = new ConcurrentHashMap<String, Long>();

 

	/***
	 *  
	 */
	public static void reload() {/*
		BaseDao basedao = DaoFactory.getBaseDao();
		try {
			List<Object[]> devices = basedao.findObjectsByHql(SqlConfig.SQL("ats.heart.reload.query"));
			if (devices != null && !devices.isEmpty()) {
				for (int i = 0; i < devices.size(); i++) {
					Object[] objs = devices.get(i);
					heartRecord.put(objs[0].toString() + "-"
							+ objs[1].toString(), 0L);
				}
			}
		} catch (Exception e) {
			logger.error("心跳包重载失败！", e);
		} finally {
			if (basedao != null)
				basedao.closeSession();
		}
	*/}

	/***
	 * 心跳 ,若没有开启心跳的数据，将直接被忽略.若存在 心跳包，则直接刷新心跳时间 
	 * @param key {001002}
	 */
	public static void heartbeat(String key){
		
		if(heartRecord.containsKey(key)){
			//记录心跳最新时间 
		   heartRecord.put(key, System.currentTimeMillis());
		   //刷新握手包最新时间 
		  // HandContext.refreshHand(key);
		} 
		//如果二层设备也有心跳包，那么此处需要进行处理。
	}
	/***
	 * 判断是否存在 心跳包，如果存在 ，则刷新 
	 * @param key
	 * @return
	 */
	public static boolean hasHeart(String key){
		
		if(heartRecord.containsKey(key)){
			//若此时的心跳有超时则不处理
			long newTime=System.currentTimeMillis(); 
			//当前时间送去上次时间，大于 最大间隔时间，则为无效心跳
			if(newTime-heartRecord.get(key)>HeartBeatCheckThread.maxSpace){
				 return false;
			}
			
			
			//记录心跳最新时间 
		   heartRecord.put(key, System.currentTimeMillis());
		   //刷新握手包最新时间 
		   //HandContext.refreshHand(key);
		   return true;
		} 
		else return false;
		//如果二层设备也有心跳包，那么此处需要进行处理。
	}
	/***
	 * 心跳
	 * @param one
	 * @param two
	 */
	public static void heartbeat(int one,int two){ heartbeat(CommandTools.handKeyFormat(one, two));}
	/***
	 * 握手包启动心跳【无论是否在之前存在心跳纪录，都要在此时插入一条，或新增 或覆盖】
	 * @param one
	 * @param two
	 */
	public static void StartHeartbeat(int one,int two){
		heartRecord.put(CommandTools.handKeyFormat(one, two), System.currentTimeMillis());
		//System.out.println("心跳数量 ："+heartRecord.size());
		}
	/***
	 * 将一个设备标记为开启(对缓存有效)
	 * @param one
	 * @param two
	 */
/*	private static boolean  open(String key){
		boolean end=false;
		BaseDao basedao = DaoFactory.getBaseDao();
		try {
			  int count=basedao.updateByHql(SqlConfig.SQL("ats.heart.state"),new Object[]{1,one,two});
			  if(count>0)end= true;
		
		} catch (Exception e) {
			logger.error("心跳包重载失败！", e);
		} finally {
			if (basedao != null)
				basedao.closeSession();
		}
		return end;
		
	}*/
	/****
	 * 检查心跳,若是发现停止跳的，则将设备的状态设置为停止
	 */
	public static void checkHeart(){
		long newTime=System.currentTimeMillis(); 
		try {
			//按照指定的时间，同步设备与系统之间的时间 
			TimmerSynchronization.getinstance().submitTime();
			if(heartRecord!=null&&!heartRecord.isEmpty())
			for (Iterator<String> keys = heartRecord.keySet().iterator(); keys.hasNext();) {
				 String key = ( String) keys.next();
				 //if(ResetContext.isReset(dp))
				 //判断是否超时，若超时则将状态设置为不可用。
				 if(newTime-heartRecord.get(key)>HeartBeatCheckThread.maxSpace){
					 EquipmentCacheVO vo= AtsEquipmentCache.queryByAddress(key);
					 
					 if(vo!=null){
						 //添加设备掉线状态
						 vo.setStatus(Constant.EQUIPMENT_STATUS_LOSE);
						 AtsEquipmentCache.unUseEqui(vo.getParentAddress(),vo.getAddress());
						 System.out.println("超时了？");
						 //添加端口掉线标记,用于地图显示
						 Table10 t10=AtsEquipmentCache.getEquiReadOnly(vo.getParentAddress(),vo.getAddress());
						 if(t10!=null){
							 t10.distoryPort(true);
						 }
						 //记录掉线事件 
						 EventContext.logFaultConnection(vo.getParentAddress(),vo.getAddress(),0xff);
					 if(vo.getChildren()!=null&&!vo.getChildren().isEmpty()){
						 for (int i = 0; i < vo.getChildren().size(); i++) {
							 EquipmentCacheVO vo2=	 vo.getChildren().get(i);
							vo2.setStatus(Constant.EQUIPMENT_STATUS_LOSE);
							 AtsEquipmentCache.unUseEqui(vo2.getParentAddress(),vo2.getAddress());
							 //添加端口掉线标记,用于地图显示
							 Table10 t10Second=AtsEquipmentCache.getEquiReadOnly(vo2.getParentAddress(),vo2.getAddress());
							 if(t10Second!=null){
								 t10Second.distoryPort(true);
							 }
						}
						 
					 }
					 //如果停止心跳，则关闭其缓存
					// AtsEquipmentCache.removeEqui(vo.getParentAddress());
					 }
					 heartRecord.remove(key);
					 continue;
				 }
			}
			
		} catch (Exception e) {
			logger.error("心跳包重载失败！", e);
		} finally {}
	}
	/***
	 * 判断此次交互的设备是否存在 心跳
	 * @param dp
	 * @return
	 */
	public static boolean  inputHasheart(DatagramPacket dp){ 
		byte[] data = dp.getData();
		int len=dp.getLength();
		int one, two;
		if(len==12){
			one = data[8] & 0xff;
		}else{
			one = data[9] & 0xff;
		} 
		two =0; //data[12] & 0xff;  只记录一层心跳
		//一层设备若是也为0，则数据有问题
		if(one==0)return false;
		 String key =CommandTools.handKeyFormat(one, two);
		 //判断是否超时，若超时则将状态设置为不可用。
		return hasHeart(key)||ResetContext.isReset(dp);
	}
	
	
	
	
	
	
	
	
	
	/***
	 * 将   1-1的设备地址转为int数组
	 * @param key
	 * @return
	 */
/*	private static int[] formateAdress(String key) {
		int[] add = new int[] { 0, 0 };
		try {
			if (key.length()==6) {
				String[] vals ={key.substring(0, 3),key.substring(3, 6)} ;
				if (NumberUtils.isNumber(vals[0]))
					add[0] = NumberUtils.createInteger(vals[0]);
				if (NumberUtils.isNumber(vals[1]))
					add[1] = NumberUtils.createInteger(vals[1]);
			}
		} catch (Exception e) {
			logger.error("错误的心跳设备地址");
		}
		return add;
	}*/
	/*private static boolean validKey(int[] adres){
		 return adres[0]>-1&&adres[1]>-1;
	}*/
}
