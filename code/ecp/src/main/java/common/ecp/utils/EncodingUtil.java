package common.ecp.utils;

import org.apache.commons.codec.binary.Base64;
/***
 * 
* @Title: EncodingUtil.java 
* @Package common.ecp.utils 
* @Description: ENCODING工具类。
* @author 陈定凯 
* @date 2015年5月13日 下午5:18:38 
* @version V1.0
 */
public class EncodingUtil {

	/**
	 * base64生成字符串
	 * 
	 * @param byteData
	 * @return
	 */
	public static String getBase64ofString(byte[] byteData) {
		return new String(Base64.encodeBase64((byteData)));
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * base64解密
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] decodeBase64(byte[] input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * base64加密
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] encodeBase64(byte[] input) {
		return Base64.encodeBase64(input);
	}

}
