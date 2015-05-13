package com.cdk.ats.udp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;


	/***
	 * 
	 * @author cdk
	 * 对MD5算法简要的叙述可以为：MD5以512位分组来处理输入的信息，且每一分组又被划分为16个32位子分组，经过了一系列的处理后，算法的输出由四个32位分组组成，将这四个32位分组级联后将生成一个128位散列值。 
	 */
public class AtsMD5util {
		private static Logger logger = Logger.getLogger(AtsMD5util.class);
		 /***
		  *  返回文本的32位摘要码
		  * @param plainText
		  * @return
		  */
		 public static String  getMD5_32(String plainText) {
			 String md5String="";
		  try {
		   MessageDigest md = MessageDigest.getInstance("MD5");
		   md.update(plainText.getBytes());
		   byte b[] = md.digest();
		   int i;
		   StringBuffer buf = new StringBuffer("");
		   for (int offset = 0; offset < b.length; offset++) {
		    i = b[offset];
		    if (i < 0)
		     i += 256;
		    if (i < 16)
		     buf.append("0");
		    buf.append(Integer.toHexString(i));
		   }
		   md5String=buf.toString();
		  } catch (NoSuchAlgorithmException e) {
		   logger.error(e.getMessage(),e);
		   e.printStackTrace();
		  }
		  return md5String;
		 }
		 /***
		  *  返回文本的16位摘要码
		  * @param plainText
		  * @return
		  */
		 public static String  getMD5_16(String plainText) {
			 String md5String=getMD5_32(plainText);
			 if(md5String!=null&&md5String.length()==32)
			 return md5String.substring(8, 24);
			 else return null;
		 }
	}
