package com.cdk.ats.udp.process;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.cdk.ats.udp.transmitter.TransmitterContext;


/***
 * 
 * @author dingkai
 * 下传处理器的缓存空间
 * 用于缓存下传的数据。
 */
public class ActionContext {

	/***
	 * 普通数据包的缓存容器
	 */
	public static ConcurrentMap<Integer, ActionParams> TempParams=new ConcurrentHashMap<Integer, ActionParams>();
	
	/***
	 * 重置数据包的缓存容器
	 */
	public static ConcurrentMap<Integer,ConcurrentMap<Integer, ActionReady>> resetTempKeys=new ConcurrentHashMap<Integer,ConcurrentMap<Integer, ActionReady>>();
	/***
	 * 写入缓存数据 
	 * @param key
	 * @param temp
	 */
	public static void putValues(Integer key,ActionParams temp){
		TempParams.put(key, temp);
	}
	
	/***
	 * 获取缓存数据 
	 * @param key
	 * @param temp
	 */
	public static ActionParams getValues(Integer key){
		return TempParams.get(key);
	}
	
	/****
	 * 
	 * @param key  流水号
	 * @param state  状态｛操作成功为true ，返之为false｝
	 * @param message   信息
	 */
	public static void setState(int key,boolean state,String message){
		if(TempParams.get(key)!=null){
			TempParams.get(key).setSuccess(state);
			TempParams.get(key).setMessage(message);		
			TempParams.get(key).setCode(900);
			TransmitterContext.delSequnce(key);
		}
	/*	if(TempParams.get(key).getParentID()>0){
			TempParams.get(TempParams.get(key).getParentID()).getChildrens().put(key,state);
			boolean end=true;
			StringBuffer sb=new StringBuffer();
			//判断父级的所有子级是否都已经完成响应
			for (Iterator<Integer> child = TempParams.get(TempParams.get(key).getParentID()).getChildrens().keySet().iterator(); child.hasNext();) {
				Integer type = (Integer) child.next();
				if(TempParams.get(type).getCode()==900){
					if(!TempParams.get(type).getSuccess()){
						sb.append("|");
						sb.append(TempParams.get(type).getMessage());
					}
					
				}else{end=false; break;}
			}
			if(end){
				TempParams.get(TempParams.get(key).getParentID()).isSuccess().setMessage(sb.toString());
			}
			sb=null;
			
		}*/
	} 
	public static void setState(int code,int key,boolean state,String message){
		if(TempParams.get(key)!=null){
		TempParams.get(key).setSuccess(state);
		TempParams.get(key).setMessage(message);		
		TempParams.get(key).setCode(code);
		TransmitterContext.delSequnce(key);
		}
		}
	
	/***
	 * 销毁指定的缓存数据
	 * @param key
	 */
	public static void distoryParam(Integer key){
		TempParams.remove(key);
		
	}
	
	/***
	 * 判断返回的包，是不是响应了设备重置命令
	 * @param key
	 * @return
	 */
	public static  boolean hasRestKey(Integer key){
		boolean end=false;
		
		for (Iterator iterator = resetTempKeys.keySet().iterator(); iterator.hasNext();) {
			Integer type = (Integer) iterator.next();
			ConcurrentMap<Integer,ActionReady>  iar=resetTempKeys.get(type);
			 if(iar!=null){
				 if(iar.containsKey(key)){
					 synchronized(iar){
						 iar.remove(key);
					 }
					 end=true;
					 break;
				 }
			 }
			
		}
		return end;
		
	}
}
