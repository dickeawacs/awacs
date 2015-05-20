package com.cdk.ats.udp.reset;

import com.cdk.ats.udp.utils.CommandTools;

/***
 * 重置命令处理类
 * 负责重置类命令接收处理
 * 负责保存操作
 * @author dingkai
 *
 */
public class ResetCmd {

	public void success(byte[] data){
		int key=CommandTools.formateSequnceToInt(data);
		//获取缓存的数据，写入结束标记 
		ResetContext.over(key, CommandTools.getOne(data), 0, ResetContext.Result_True);
		
	}
	
	
}
