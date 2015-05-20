package com.cdk.ats.udp.transmitter;

import java.util.List;

import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionReady;

/***
 * 发送器工具包
 * @author dingkai
 *
 */
public class TransmitterUtils {

	/***
	 * 填装发送数据包
	 * @param group
	 * @param readys
	 */
	public static boolean  pushGroup(ActionParams group,List<ActionReady> readys ){
		 boolean end=false;
		 		if(group!=null&&readys!=null){
		 			ActionContext.putValues(group.getSequnce(),group);
					//将一个 准备好的数据包组 放入发送器中
					TransmitterContext.pushGroupDatagram(readys);
		 			end=true;
		 		}
		 	
		 return end;
		
	} 
	/***
	 * 填装设备信息重置发送数据包
	 * @param group
	 * @param readys
	 */
	public static boolean  pushResetGroup(ActionParams group,List<ActionReady> readys ){
		 boolean end=false;
		 		if(readys!=null){
		 			//不做数据缓存 ，数据处理由重置线程处理
		 			//ActionContext.putValues(group.getSequnce(),group);
					//将一个 准备好的数据包组 放入发送器中
					TransmitterContext.pushResetDatagram(group,readys);
		 			end=true;
		 		}
		 	
		 return end;
		
	} 
	/***
	 * 填装发送数据包
	 * @param group
	 * @param readys
	 */
	public static boolean  distoryGroup(ActionParams group,List<ActionReady> readys ){
		 boolean end=false;
		 		if(group!=null||readys!=null){
					if(readys!=null&&readys.size()>0){
						for (int i = 0; i < readys.size(); i++) {
								ActionContext.distoryParam(readys.get(i).getKey());
						}
						end=true;
					}
					if(group.getSequnce()>0){
						ActionContext.distoryParam(group.getSequnce());
						end=true;
					}
				}
		 	
		 return end;
		
	} 
}
