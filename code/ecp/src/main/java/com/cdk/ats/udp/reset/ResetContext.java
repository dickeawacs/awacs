package com.cdk.ats.udp.reset;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.web.utils.AjaxResult;

/**
 * 重置的上下文空间（辅助 ）
 * @author dingkai
 *
0.重置信息记录器
1.获取信息并移除已读
2.接收正常响应信息
3.接收超速报警信息
4.接收减速信息
5.接收失败信息
6.接收结束标记
7.移除未读信息

 *
 */

public class ResetContext {
	/***
	 * 自动清理信息的间隔时间是10分钟，60000毫秒
	 */
	private static long clearTime=600000;
	//0.重置信息存储器
	 //<Integer, Object[时间戳,List<String[]>]> 
	public static ConcurrentMap<Integer, Object[]>  resetinfo=new ConcurrentHashMap<Integer,Object[]>();
	/***
	 * 重置开启时打开 
	 */
	public static ConcurrentMap<Integer,Boolean> ResetOpen=new ConcurrentHashMap<Integer, Boolean>();
	
	/**
	 * 成功链接的标记，当0X10-0X24接收到响应包后，则认为对接成功。并开始发包
	 */
	public static ConcurrentMap<Integer,Integer> connection=new ConcurrentHashMap<Integer, Integer>();
	
	/***
	 * 超速 500
	 */
	public static int T_Speeding=500;
	/***
	 * 减速 600
	 */
	public static int T_Decelerate=600;
	/**
	 * 结束标记 700 
	 */
	public static int T_End_Tag=700;
	
	
	/***
	 * 错误信息类型 8000
	 */
	public static int T_Error_Msg=8000;
	/***
	 * 响应失败 
	 */
	public static int Result_False=0;
	/***
	 * 响应成功 1
	 */
	public static int Result_True=1;
	
	 /***
	  * 
	  * 描述： 判断当前数据包对应的一层设备 是否处于重置状态
	  * @createBy dingkai
	  * @createDate 2014-2-18
	  * @lastUpdate 2014-2-18
	  * @param dp
	  * @return
	  */
	public static boolean isReset(DatagramPacket dp){
		Boolean end=false;
		
		if(ResetOpen.size()>0){
			byte[] data = dp.getData();
			int len=dp.getLength();
			int one;
			if(len==12){
				one = data[8] & 0xff;
			}else{
				one = data[9] & 0xff;
			} 
			Integer key=Integer.valueOf(one);
			if(ResetOpen.containsKey(key)){
				return ResetOpen.get(key);
			}
			
		}
		return end;
	}
	public static boolean isReset(Integer key){
		Boolean end=false;
			if(ResetOpen.containsKey(key)){
				return ResetOpen.get(key);
			}
		return end;
	}
	 
	/***
	 * 1. 获取信息并移除已读
	 * @param key
	 * @return
	 */
	public static synchronized AjaxResult getResetInfos(Integer key){
		AjaxResult rs=new AjaxResult();
		Object []  records=resetinfo.get(key);
		if(records!=null&&records.length>1&&records[1]!=null){
			List<String[]> info=(List<String[]>) records[1];
			if(info!=null&&info.size()>0){
				StringBuffer sb=new StringBuffer();
				List<String[]> remove=new ArrayList<String[]>();
				sb.append("[");
				for (int i = 0; i < info.size(); i++) {
					//对象的第一位是结束标记，第二位是命令类型，第三位 成功与失败,第四位 设备（一层编号---二层编号 ）
					if(i>0)sb.append(",");
					sb.append("{a:").append(info.get(i)[0]).append(",b:")
					.append(info.get(i)[1]).append(",c:").append(info.get(i)[2])
					.append(",d:").append(info.get(i)[3]).append("}");
					remove.add(info.get(i));
				}
				sb.append("]");
				//移除已读
				info.removeAll(remove);
				rs.setReturnVal(sb.toString());
			}
		}
		/***
		 * 将信息写入返回载体中
		 */
		return rs;
	}
	/**
	 * 
	 * 描述： 若超过10分钟后仍然没有被前台提取，则清理数据
	 * @createBy dingkai
	 * @createDate 2013-12-6
	 * @lastUpdate 2013-12-6
	 */
	public static void clear(){
		for (Iterator<Integer> iterator = resetinfo.keySet().iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			if(resetinfo.containsKey(key)){
				Object []  records=resetinfo.get(key);
				if(records!=null&&records[0]!=null){
					Long lastt=(Long) records[1];
					if(System.currentTimeMillis()>(clearTime+lastt)){
						//超时则删除 
						resetinfo.remove(key);
					}
					
				}
			}
			
		}
		
		
		
		
		
	}
	/***
	 * 2.接收正常响应信息(正常)
	 * @param key 流水号
	 * @param one 一层设备 
	 * @param two 二层设备 
	 * @param type 命令类型
	 */
	public static void normal(int key ,int one,int two,int type){
		addInfo(key, one, two, 0, type, Result_True);
		
	}
	
	/***
	 * 3.接收超速报警信息(越界)
	 * @param key 流水号
	 * @param one 一层设备 
	 * @param two 二层设备   
	 */
	public static void outBorder(int key ,int one,int two){
		addInfo(key, one, two, 0, T_Speeding,Result_False);
		
	}
	/***
	 * 4.接收减速信息
	 * @param key 流水号
	 * @param one 一层设备 
	 * @param two 二层设备 
	 * @param type 命令类型
	 * @param success 成功/失败/无法接通
	 */
	public static void normal(int key ,int one,int two){
		addInfo(key, one, two, 0, T_Decelerate, Result_False);
		
	}
	/***
	 * 5.接收失败信息
	 * @param key 流水号
	 * @param one 一层设备 
	 * @param two 二层设备 
	 * @param type 命令类型
	 * @param success 成功/失败/无法接通
	 */
	public static void error(int key ,int one,int two,int type){
		addInfo(key, one, two, 0, type,Result_False);
		
	}

	/**6.接收结束标记
	 * @param key 流水号
	 * @param one 一层设备 
	 * @param two 二层设备 
	 * @param end 结束标记
	 * @param type 命令类型
	 * @param success 成功/失败/无法接通
	 */
	public static void over(int key ,int one,int two,int success){
		System.out.println("超时了，设备重置停止 .."+one+"-"+two);
		addInfo(key, one, two, T_End_Tag,0 ,success);
		
	}
	/***
	 * 
	7.移除未读信息
	 * @param key 流水
	 */
	public static void clearReset(int key ){
		resetinfo.remove(key);
	}
	/**
	 * 添加信息 
	 * @param key 流水号
	 * @param one 一层设备 
	 * @param two 二层设备 
	 * @param end 结束标记
	 * @param type 命令类型
	 * @param success 成功/失败/无法接通
	 */
	private  static void addInfo(int key, int one,int two,int end,int type,int success){
		create(key);
		resetinfo.get(key)[0]=System.currentTimeMillis();
		((List<String[]>)resetinfo.get(key)[1]).add(new String[]{""+end,""+type,""+success,one+"-"+two});		
	}
	/***
	 * 添加一个异常信息
	 * @param key
	 * @param msg
	 */
	public  static void error(int key ,String msg){
		create(key);
		resetinfo.get(key)[0]=System.currentTimeMillis();
		((List<String[]>)resetinfo.get(key)[1]).add(new String[]{ "0",T_Error_Msg+"",msg});
	}
	/***
	 * 
	 * 描述：  如果不存在 ，则创建 
	 * @createBy dingkai
	 * @createDate 2013-12-6
	 * @lastUpdate 2013-12-6
	 * @param key
	 */
	private static void create(int key ){
		if(!resetinfo.containsKey(key)){
			resetinfo.put(key,new Object[]{System.currentTimeMillis() ,new ArrayList<String []>()});			
		}
		
	}
	/***
	 * 判断是否存在流水号对应打开链接的标记 
	 * @param key
	 * @return
	 */
	public static boolean connectionHas(int key) {
		// 0 表示链接的状态，0-等待链接响应
		return  connection.containsKey(key)&&connection.get(key).equals(0);
	}
	/***
	 * 
	 * 描述：开启编程状态
	 * @createBy dingkai
	 * @createDate 2014-1-28
	 * @lastUpdate 2014-1-28
	 * @param key
	 */
	public static void  connectionOpenDevelop(int key){
		connection.put(key,200);
		
	}
	
	/****
	 * 
	 * 描述：开发状态开启成功  
	 * @createBy dingkai
	 * @createDate 2014-1-28
	 * @lastUpdate 2014-1-28
	 * @param key
	 */
	public static void connectionDevSuccess(int key ){
		
		connection.put(key,0);
	}
	/***
	 * 测试重置链接，并标记，标记值=0
	 * @param key
	 */
	public static void connectionTest(int key) {
		connection.put(key,0);
	}
	/***
	 * 链接成功，并标记，标记值=1
	 * @param key
	 */
	public static void connectionSuccess(int key) {
		connection.put(key,1);
	}
	/***
	 * 战用链接，并标记，标记值=2
	 * @param key
	 */
	public static void connectionUseing(int key) {
		connection.put(key,2);
	}
	
	/***
	 * 
	 * 描述： 返回链接记录窗口对象
	 * @createBy dingkai
	 * @createDate 2014-1-28
	 * @lastUpdate 2014-1-28
	 * @return
	 */
	public static Map<Integer, Integer> getConnection(){
		return connection;
		
	}
	/***
	 * 关闭重置的链接，关闭重置模式
	 * @param key
	 */
	public static void close(int one ,int groupKey ){
		connection.remove(one);
		ResetOpen.remove(one);		
		ActionContext.resetTempKeys.remove(groupKey);
	}
	/***
	 * 判断当前的设备是否处于重置链接状态，是则返回TRUE，反之则返回FALSE;
	 * @param data
	 * @return
	 */
	public static boolean connectioning(byte[] data){
	return connection.containsKey(CommandTools.getOne(data));
	}
	public static boolean connectioning(int one){
		return connection.containsKey(one);
		}
}