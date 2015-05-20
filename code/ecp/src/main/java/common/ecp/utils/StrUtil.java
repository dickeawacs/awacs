/**
 * 
 * StrUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package common.ecp.utils;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
* @Title: StrUtil.java 
* @Package common.ecp.utils 
* @Description: 本类主要做为文本工具类
* @author 陈定凯 
* @date 2015年5月13日 下午5:11:27 
* @version V1.0
 */
public class StrUtil {

	/**
	 * 检查字符串是否为空，只有制表符也认为是空的
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * 检查字符串是否为非空
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	/**
	 * 拼接字符串
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Object[] array, String separator) {
		return StringUtils.join(array, separator);
	}

	/**
	 * 拼接字符串
	 * @param iterator
	 * @param separator
	 * @return
	 */
	public static String join(Iterator iterator, String separator) {
		return StringUtils.join(iterator, separator);
	}

	/**
	 * 搜索到目标内容后进行substring
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String substringAfter(String str, String separator) {
		return StringUtils.substringAfter(str, separator);
	}
	/**
	 * 搜索到目标内容后进行substring
	 * @param str
	 * @param separator
	 * @return
	 */

	public static String substringBefore(String str, String separator) {
		return StringUtils.substringBefore(str, separator);
	}
	/**
	 * 搜索到目标内容后进行substring
	 * @param str
	 * @param separator
	 * @return
	 */

	public static String substringAfterLast(String str, String separator)
	{
		return StringUtils.substringAfterLast(str, separator);
	}

	/**
	 * 搜索到目标内容后进行substring
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String substringBeforeLast(String str, String separator) {
		return StringUtils.substringBeforeLast(str, separator);
	}
	
	/**
	 * 替换字符串的特定内容
	 * @param str
	 * @param param
	 * @return
	 */
	public static String replaceAll(String str,Map param){
		if(str==null)
			return str;
		if(param==null)
			return str;
		for (Iterator iterator = param.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry en = (Map.Entry) iterator.next();
			String key = ObjectUtils.toString(en.getKey(),"");
			//拼接token模式
			key = "\\$\\{"+key+"\\}";
			Object value = en.getValue();
			str = replaceAll(str,key,value);
		}
		return str;
	}
	
	/**
	 * 替换内容，对key值采用${key}的形式进行封装
	 * @param str
	 * @param key
	 * @param value
	 * @return
	 */
	public static String replaceAllWithToken(String str,String key,String value){
		if(str==null)
			return str;
		if(key==null)
			return str;
		//拼接token模式
		key = "\\$\\{"+key+"\\}";
		str = replaceAll(str,key,value);
		return str;
	}

	/**
	 * 替换字符串内容
	 * @param str
	 * @param key
	 * @param value
	 * @return
	 */
	public static String replaceAll(String str, Object key, Object value) {
		if(str==null)
			return str;
		if(key==null)
			return str;
		String skey = ObjectUtils.toString(key);
		String svalue=ObjectUtils.toString(value);
		return str.replaceAll(skey, svalue);
	}
	
	/**
	 * 生成随机数
	 * @param len
	 * @param prefix
	 * @param subfix
	 * @return
	 */
	public static String genRandomCode(int len,String prefix,String subfix){
		return prefix+genRandomCode(len)+subfix;
	}
	/**
	 * 生成随机数
	 * @param len
	 * @return
	 */
	public static String genRandomCode(int len){
		String rawauthCode = new DecimalFormat("000000000000000").format(Math.random()*100000000000000L);
		len = len>15?15:len;
		return rawauthCode.substring(0,len);
	}

}
