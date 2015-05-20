package com.cdk.ats.udp.reset;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.cdk.ats.udp.process.ActionReady;

/**
 * 缓存器（特殊，监听 ）
1.接收重置包流水号与接收缓存包
移除缓存包
超速报警
事故叫停重置线程

 * @author dingkai
 *
 */
public class ResetCache {
	//用于存放发送过的数据包
	private  ConcurrentMap<Integer, ActionReady> keys=new ConcurrentHashMap<Integer, ActionReady>();
	//最大的缓存数
	private int maxBorder=200;
	//超速缓存数的界限
	private int outBorder=50;
	/**
	 * 提速的界限,超过了此界限，且小于此界限的2倍才会提速
	 *  minBorder=5;
	 */
	private int minBorder=5;
	
	//发送器
	private ResetSender send;
	//一层设备编号 
	private int one;
	
	private int groupKey=0;
	
	
	
	public ResetCache(ResetSender send, int one, int groupKey) {
		this.send = send;
		this.one = one;
		this.groupKey = groupKey;
	}
	/***
	 * 1.接收重置包流水号与接收缓存包
	 * @param key  流水号
	 * @param data  数据 包
	 */
	public void add(ActionReady data){
		keys.put(data.getKey(), data);
		setDecelerate();//调用速度控制功能

	}
	/***
	 * 
	 * 描述： 调用速度控制功能
	 * @createBy dingkai
	 * @createDate 2013-12-7
	 * @lastUpdate 2013-12-7
	 */
	private void  setDecelerate(){
		 	//是否超过最大缓存数？
		if(keys.size()>maxBorder){
			//给出紧急暂停标记
			send.pause();
			send.decelerate();
		}else if(isOutBorder()){
			ResetContext.outBorder(groupKey, one, 0);
			//减速
			send.decelerate();
		}else if (canDccelerate()){
			//当前包数量小于报警线的1/2时允许 提速；
			send.dccelerate();	
		}		
	}
	/***
	 * 获取缓存数据 
	 * @return
	 */
	public ConcurrentMap<Integer, ActionReady> getCache(){
		
		return keys;
	}

	/***
	 * 是否超过最大边界
	 * @return
	 */
	public boolean isOutMaxBorder(){
		return keys.size()>maxBorder;
	}
	
	/***
	 * 判断当前是否允许减少发送的间隔时间
	 * @return
	 *嗯啊咂*/
	public boolean canDccelerate(){
		return keys.size()<(minBorder*10);//&&keys.size()>minBorder;
		
	}
	/**
	 * 是否超过报警界限
	 * @return
	 */
	public boolean isOutBorder(){
		
		return keys.size()>outBorder;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isCacheEmpty(){
		return keys==null||keys.isEmpty()||keys.size()<1;
	}	
	public int getGroupKey() {
		return groupKey;
	}
	public void setGroupKey(int groupKey) {
		this.groupKey = groupKey;
	}
	public int getOutBorder() {
		return outBorder;
	}
	public void setOutBorder(int outBorder) {
		this.outBorder = outBorder;
	}
	public int getMinBorder() {
		return minBorder;
	}
	public void setMinBorder(int minBorder) {
		this.minBorder = minBorder;
	}
	
}
