package com.cdk.ats.web.utils;

public class DataTypeUtils {
	public static String UTF82GBK(String srcStr) throws Exception{
	     char[] strChar = srcStr.toCharArray();         // 获得UTF8编码数据
	     byte[] strByte = new byte[strChar.length];     // 转换成byte数组
	     for (int i = 0; i < strByte.length; i++){
	         strByte[i] = (byte)strChar[i];
	     }
	     return new String(strByte, "UTF-8");         // 编码字符串
	}


 public static void main(String[] args) {
	System.out.println(Integer.toBinaryString(128));
	System.out.println(Integer.toBinaryString(64));
	System.out.println(Integer.toBinaryString(32));
	System.out.println(Integer.toBinaryString(16));
	System.out.println(Integer.toBinaryString(8));
	System.out.println(Integer.toBinaryString(4));
	System.out.println(Integer.toBinaryString(2));
	System.out.println(Integer.toBinaryString(1));
/*	10000000
	01000000
	00100000
	00010000
	00001000
	00000100
	00000010
	00000001*/

}
}
