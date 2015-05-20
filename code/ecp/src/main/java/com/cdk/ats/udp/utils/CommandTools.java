package com.cdk.ats.udp.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.web.utils.Time;

/***
 * 命令工具类，用于辅助处理一些命令中的特殊功能
 * 
 * @author cdk
 * 
 */
public class CommandTools {
	private static Logger logger= Logger.getLogger(CommandTools.class);
	private static String charsetName = "GBK";
	/***
	 * 获取数据包中的流水号 固定位置 data[4,5,6,7]
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] getSequnces(byte[] data) {
		return new byte[] { data[4], data[5], data[6], data[7] };
	}

	/***
	 * 获取数据包中写入的数据长度 固定位置 data[2,3]
	 * 
	 * @param data
	 * @return
	 */
	public static int getDatagramLength(byte[] data) {
		int val = 0;
		for (int i = 0; i < 2; i++) {
			int temp = data[i + 2];
			for (int j = 0; j < 2 - i - 1; j++)
				temp = temp * 256;
			val += temp;
		}
		return val;
	}

	/***
	 * 统计数据包的有效长度，并将长度写入数据包中 固定写入位置：
	 * 
	 * @param data
	 */
	public static void setDatagramLength(byte[] data) {
		data[2]=0x00;
		data[3]=(byte)data.length;
	}

	/***
	 * 判断数据包是否针对二层设备，true为是，false为针对一层设备 固定位置 data[11]
	 * 
	 * @param data
	 * @return
	 */
	public static boolean hasSecondDevice(byte[] data) {
		return (data[11] & 0xff) == 0xff ? true : false;
	}


	/***
	 * 将数据包中的指定字节组成一个字符串，字符串的编码为UTF-8
	 * 
	 * @param data
	 *            byte[]数组
	 * @param begin
	 *            起始位置
	 * @param len
	 *            长度
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String byteToString(byte[] data, int begin, int len)
			throws UnsupportedEncodingException {
		
	/*	String temp=new String(data, begin, len, charsetName);
		temp=new String(temp.getBytes("UTF-8"),"UTF-8");
		if(temp!=null)temp=temp.replaceAll("[^\\d|^\\w]+$","");*/
		int readyLen=0;
		for (int i = begin; i < (begin+len); i++) {
			if(data[i]==0){
				break;
			} 
			++readyLen;
		}
		return new String(new String(data, begin, readyLen,charsetName).getBytes("UTF-8"),"UTF-8");
	}	
	/***
	 * 
	 * 描述：电话号码
	 * @createBy dingkai
	 * @createDate 2014-5-4
	 * @lastUpdate 2014-5-4
	 * @param data
	 * @param begin
	 * @param len
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String byteToTel(byte[] data, int begin, int len)
	throws UnsupportedEncodingException {
		len=11;
			int readyLen=0;
			for (int i = begin; i < (begin+len); i++) {
				if(data[i]==0){
					break;
				} 
				++readyLen;
			}
			return new String(new String(data, begin, readyLen,charsetName).getBytes("UTF-8"),"UTF-8");
		}	 

 
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] y=new byte[20];
		String x="18933205262";
		
		byte[] z=x.getBytes("GBK");
		
		for (int i = 0; i < z.length; i++) {
			y[i]=z[i];
		}
	
		String tel=CommandTools.byteToTel(y, 0,16);
			System.out.println(tel);
		
		
	} 
/*	public static String byteToString(byte[] data, int begin, int len)
			throws UnsupportedEncodingException {				
		String temp=new String(data, begin, len, charsetName);
		temp=new String(temp.getBytes("UTF-8"),"UTF-8");
		if(temp!=null)temp=temp.replaceAll("[^\\d|^\\w]+$","");
		return temp;
	}*/
	/***
	 * 将 字符串 转换为 byte 数组
	 * @param string
	 * @return
	 */
	public static byte[] stringToByte(String string) 
	{
		byte[] strs=null;
		try {
			strs = string.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		return strs;
	}

	/***
	 * 
	 * 描述： 字节完全 转为BGK
	 * @createBy dingkai
	 * @createDate 2014-2-20
	 * @lastUpdate 2014-2-20
	 * @param bytes
	 * @return
	 */
	public static String getGBKbyByte(byte[] bytes){
		String strs=null;
			try {
				strs = new String(bytes,charsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return strs;
	}
	/***
	 * 统计校验和，将一byte数组的0至(length-2)的总和然后%256,将得到的余数写入data[length-1]
	 * 
	 * @param data
	 *            data[2,3]
	 */
	public static void countCheckCode(byte[] data) {
		int count = 0;
		if (data.length > 1) {			
			for (int i = 0; i < data.length - 1; i++) {
				count += (data[i] & 0xff);
			}
			data[data.length - 1] = (byte) (count % 256);
		}
 
	}

	/***
	 * 将源字段数组插入到目标数组，对应的从index下标位置，长度为len
	 * 
	 * @param target 目标数组
	 * @param source 数据来源的数组
	 * @param index  插入的起始位置
	 * @param len    插入的长度 （长度与起始的总和必须小于或等于target的长度，否则多余的部分会被截断）
	 */

	public static void insertByteToArray(byte[] target, byte[] source,
			int index, int len) {
		if (index + len < target.length) {
			for (int i = 0; i < source.length&&i<len; i++) {
				insertByteToArray(target, source[i], i+index);
			}
		}
	}

	/***
	 * 将源字段插入到目标数组，对应的index下标位置
	 * 
		 * @param target 目标数组
	 * @param source 数据来源的数组
	 * @param index  插入的起始位置
	 */
	public static void insertByteToArray(byte[] target, byte source, int index) {
		if (index < target.length)
			target[index] = source;
	}

	/***
	 * 将一个流水号转换为四位的字节数组 
	 * @param key
	 * @return
	 */
	public static byte[] formateSequnce(int key,byte[] data){
		int temp=key;
	 
		data[4]=(byte)(temp/(256*256*256));
		temp=temp%(256*256*256);
		data[5]=(byte)(temp/(256*256));
		temp=temp%(256*256);
		data[6]=(byte)(temp/(256));
		temp=temp%(256);
		data[7]=(byte)(temp);
		return data;
		
	}
	/***
	 * 将int数据封装至一个byte数组中
	 * @param val  将要被转换的int类型数字
	 * @param len  数组长度 
	 * @return
	 */
	public static byte[] int10CaseToBytes(int val,int len){
		byte[] vals=new byte[len];
		int temp=val;
		int hex=256;
		for (int i = 0; i < vals.length; i++) {
			int d=1;
			for(int j=0;j<len-i-1;j++)
				d=d*hex;
			vals[i]=(byte)(temp/d);
			temp=temp%d;
		}
		return vals;
		
	}
	/***
	 * 将int数据封装至一个byte数组中
	 * @param val  将要被转换的int类型数字
	 * @param len  数组长度 
	 * @return
	 */
	public static int byteToInt(byte[] data,int index,int len){
		int val = 0;
			for (int i = 0; i <len; i++) {
				int temp = data[i+index] & 0xff;
				for (int j = 0; j < len - i - 1; j++)
					temp = temp * 256;
				val += temp;
			}
		return val;
	}
	 
	/**
	 *将一个字节数组转换为数字
	 *按照每个字节256进制
	 * @param key
	 * @return
	 */
	public static int formateByteToint(byte[] key){
	int val=0;
	for (int i =0; i <key.length; i++) {
		int temp=key[i]&0xff;	
		for (int j =0; j <key.length-i-1; j++)  temp=temp*256;
		val+=temp; 
	}
	return val;
	}
	/***
	 * 抽取数据包中的流水号
	 * @param data
	 * @return
	 */
	public static int formateSequnceToInt(byte[] data) {
		int val = 0;
		try {
			byte[] key = new byte[] { data[4], data[5], data[6], data[7] };
			for (int i = 0; i < key.length; i++) {
				int temp = key[i] & 0xff;
				for (int j = 0; j < key.length - i - 1; j++)
					temp = temp * 256;
				val += temp;
			}
		} catch (Exception e) {
			val = -1;
		}
		return val;
	}
	/***
	 * 判断响应标记是否为成功标记
	 * 返回的数据度长度为12
	 * key[10]的标记为0x00为成功 ，0xff为失败
	 * @param key 
	 * @return  
	 */
	public static boolean isSuccess(byte[] key){
		return  (key[10]&0xff)==0;
		
	}
	/***
	 * 判断响应包是不是有效数据包，根据 一层设备地址与设备类型
	 * @param data
	 * @param one
	 * @param onet
	 * @return  是有效则为true，反之则为false 
	 */
/*	public static boolean isValidDP(byte[] data,int one,int onet){
		return ((data[8]&0xff)==one&&(data[9]&0xff)==onet);
	}*/
	/***
	 * 判断响应包是不是有效数据包，根据 一层设备地址与设备类型
	 * @param data
	 * @param aparam  缓存参数
	 * @return  是有效则为true，反之则为false 
	 */
	public static boolean isValidDP(byte[] data,ActionParams aparam){
		//return ((data[8]&0xff)==aparam.getOneP()&&(data[9]&0xff)==aparam.getOnePT());
		return ((data[8]&0xff)==aparam.getOneP());
	}
	

	/***
	 * 备用方案（1-7  代表星期一至星期七）
	 * @return
	 */
	public static int getDay(){
		int day=0;
		day=Time.day();
		day--;
		if(day==0)day=7;
		return day;  
	}
	/***
	 * 获取与判断输入输出选择与对应的状态值 ，以数组返回
	 * @param select 输入或输出选择
	 * @param val    对应的状态值
	 * @return
	 * @throws Exception
	 */
	public static int[] checkTargetByte(byte select ,byte val) throws Exception{
		int[] end=new int[2];
		int[] choice =getBitByByte(select);
		int[] choiceVal=getBitByByte(val);
		for (int i =7; i>=0; i--) {
			if(choice[i]==1){
				end[0]=i+1;
				end[1]=choiceVal[i];
				break;
			}
		}
		return end;
	}
	
	/***
	 * 获取一个数据的8个位
	 * @param b
	 * @return
	 */
	public static int[] getBitByByte(byte b){
		int[] end=new int[]{0,0,0,0,0,0,0,0};
	    for(int i = 7; i >=0 ; i --){
	        int shiftleft = (b >> i) & 0x01;
	        end[i]=shiftleft;
	      //  System.out.print(shiftleft);
	    }
	  //  System.out.println();
	    return end;
	}
	/***
	 * 格式 化16位长度的电话号码，如果不够 则以*号代替 ，若为空，则返回 “”
	 * @param telNum
	 * @return
	 */
	public static String  formatTelNum(String telNum){
		if(telNum==null){
			telNum="";		
		}else{
			StringBuffer strBuf=new StringBuffer();
			strBuf.append(telNum.trim());
			while (strBuf.length()<16) {
				strBuf.append("*");				
			}
			
		}
		
		return telNum;

 
		
	
		
	}
	/***
	 * 获取返回成功标记的数据 包
	 * @param put 接收到的数据 包
	 * @return
	 */
	public static byte[] responseData(byte[] put,boolean end){
		return responseCmdBase12(put,(end?(byte)0x00:(byte)0xff));
	}
	/***
	 * 获取返回失败标记的数据 包
	 * @param put  接收到的数据 包
	 * @return
	 */
	public static byte[] responseFalse(byte[] put){
		return responseCmdBase12(put, (byte)0xff);
	}
	/***
	 * 获取返回成功标记的数据 包
	 * @param put 接收到的数据 包
	 * @return
	 */
	public static byte[] responseTrue(byte[] put){
		return responseCmdBase12(put, (byte)0x00);
	}
	/***
	 * 
	 * @param put
	 * @param end
	 * @return
	 */
	public static byte[] responseCmdBase12(byte[] put,byte end) {
		byte[] data = new byte[12];
		//命令分类 
		data[0] = put[0];
		data[1] = put[1];
		//数据包长度 
		//data[2] = 0x00;
		//data[3] = 0x0b;
		//流水号
		data[4] = put[4];
		data[5] = put[5];
		data[6] = put[6];
		data[7] = put[7];
		//设备地址 
		data[8] = put[9];
		data[9] = put[10];
		//应答标志	0x00代表执行成功，0xff代表执行失败
		data[10] = end;
		CommandTools.setDatagramLength(data);
		// 将检验和写入最后一个字节
		CommandTools.countCheckCode(data);
		return data;
	}
	/***
	 * 格式key
	 * @param one
	 * @param two
	 * @return
	 */
	public static String handKeyFormat(int one,int two){return StringUtils.leftPad("" + one, 3, '0')
		+ StringUtils.leftPad("" + two, 3, '0');}
	/***
	 * 
	 * 描述： 从发送包中获取对应的心跳键
	 * @createBy dingkai
	 * @createDate 2014-3-10
	 * @lastUpdate 2014-3-10
	 * @param data
	 * @return
	 */
	public static String handKeyFormat(byte[]data){
		return handKeyFormat(data[9],0);//data[12]
		
	}
	
	/***
	 * 获取一层设备号 
	 * @param data
	 * @return
	 */
	public static int getOne(byte[] data){
		if(data.length==12)
		return (data[8]&0xff);
		else if(data.length>12)return (data[9]&0xff);
		return -1;
	}
	/**
	 * 获取二层设备号 
	 * @param data
	 * @return
	 */
	public static int getTwo(byte[] data){
		if(data.length>12)
		return (data[12]&0xff);
		else return -1;
	}
	/***
	 * 
	 * 描述：端口的十进制128转为二进制 对应的十进制1 2 4 8 16 32 64 128
	 * @createBy dingkai
	 * @createDate 2014-4-6
	 * @lastUpdate 2014-4-6
	 * @param action
	 * @return
	 */
	public static int switchPortIndex8To128(int action) {

		switch (action) {
		case 1:
			action = 1;
			break;
		case 2:
			action = 2;
			break;
		case 3:
			action = 4;
			break;
		case 4:
			action = 8;
			break;
		case 5:
			action = 16;
			break;
		case 6:
			action = 32;
			break;
		case 7:
			action = 64;
			break;
		case 8:
			action = 128;
			break;
		default:
			action = 0;
		}
		return action;
	}
	/**
	 * 
	 * 描述：端口的8位下标转为十进制 1-8
	 * @createBy dingkai
	 * @createDate 2014-4-6
	 * @lastUpdate 2014-4-6
	 * @param action
	 * @return
	 */
	public static int switchPortIndex128To8(int action) {

		switch (action) {
		case 1:
			action = 1;
			break;
		case 2:
			action = 2;
			break;
		case 4:
			action = 3;
			break;
		case 8:
			action = 4;
			break;
		case 16:
			action = 5;
			break;
		case 32:
			action = 6;
			break;
		case 64:
			action = 7;
			break;
		case 128:
			action = 8;
			break;
		default:
			action = 0;
		}
		return action;
	}
	
	/***
	 * 获取数字八个位中标记1的下标位;
	 * @param b
	 * @return
	 */
	public static int getByteTag(int b){
	    for(int i = 7; i >=0 ; i --){
	        int shiftleft = (b >> i) & 0x01;
	        if(shiftleft==1)  return i;
	    }
	    return -1;
	}
	
	/***
	 * 
	 * 描述： 获取数据b中对应index位置的值
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param b
	 * @param index
	 * @return
	 */
	public static int getByteTag(int b,int index){
	    for(int i = 7; i >=0 ; i --){
	    	int shiftleft = (b >> i) & 0x01;
	        if(index==i){  
	        	return shiftleft;
	        	}
	    }
	    return -1;
	}
 
	/***
	 * 
	 * 描述：转换设备类型的输出 与输出 端口数量 ，
	 * @createBy dingkai
	 * @createDate 2014-1-11
	 * @lastUpdate 2014-1-11
	 * @param type
	 * @return [输入数 ，输出数]
	 */
	public static int[] getPortCountByType(int type){
		int[] end=new int[]{0,0};
		String hex=Integer.toHexString(type);
		if(hex.length()==1)hex="0"+hex;
		end[0]=new Integer(hex.substring(0,1));
		end[1]=new Integer(hex.substring(1));		
		return end;
		
	}
	
 /***
  * 
  * 描述：获取字符串转换为GBK编码后的字节长度 
  * @createBy dingkai
  * @createDate 2014-4-20
  * @lastUpdate 2014-4-20
  * @param str
  * @return
  */
	public static int getGBKleng(String str){
		if(str==null)return 0;
		try {
			return str.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			logger.error("GBK转换异常："+str+e.getMessage(),e);
		}
		return 0;
		
	}
	/**
	 * 
	 * 描述：生成 XXXX用户XX
	 * @createBy dingkai
	 * @createDate 2014-5-7
	 * @lastUpdate 2014-5-7
	 * @param one 一层设备号
	 * @param userNum 用户号
	 * @return
	 */
	public static String createUserName(int one,int userNum){
		return StringUtils.leftPad(one+"", 4, '0')+"用户"+StringUtils.leftPad(userNum+"", 2, '0');
	}
	/***
	 * 
	 * 描述：描述：生成 XXXX用户员
	 * @createBy dingkai
	 * @createDate 2014-5-7
	 * @lastUpdate 2014-5-7
	 * @param one  一层设备号
	 * @return
	 */
	public static String createAdminName(int one){
		return StringUtils.leftPad(one+"", 4, '0')+"管理员";
	}
}
