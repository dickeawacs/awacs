package com.cdk.ats.udp.webplug.udpresult;

import java.util.HashMap;
import java.util.Map;

import com.cdk.ats.web.utils.AjaxResult;

/***
 * 响应结果容器
 * 当web端发送请求给服务端执行业务时，若此业务需要与设备层通信，系统将请求发送给设备，发送成功后，
 * 直接给web端请求以发送，并带有发送数据报的流水号， web端根据返回的流水号不停的发送请求给服务端，
 * 服务端当接收到本次与设备通信的响应后，将操作的结果写入本容器的变量udpresult中，
 * 每次的web端请求，实际上就是来udpresult中检索是否已经有返回结果。若有，则将结果返回给web端。
 * @author dingkai
 *
 */
public class ResultContext {
	/***
	 *记录 本地的请求被响应后的结果，它将用于web端的结果查询
	 */
	public static  Map<Integer, AjaxResult> udpresult=new HashMap<Integer, AjaxResult>();
	/**
	 * 
	 * @param key  流水号
	 * @param value 结果
	 */
	public static void  put(Integer key ,AjaxResult value){
		udpresult.put(key, value);		
	}
	/***
	 * 获取键对应的结果
	 * @param key
	 * @return
	 */
	public static AjaxResult get(Integer key){
		return udpresult.get(key);		
	}
	/**
	 * 判断是否存在 结果集
	 * @param key
	 * @return
	 */
	public static boolean constrct(Integer key){
		return udpresult.containsKey(key);		
	}
}
