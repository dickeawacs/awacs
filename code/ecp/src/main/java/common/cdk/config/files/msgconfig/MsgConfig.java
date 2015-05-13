package common.cdk.config.files.msgconfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import common.cdk.config.utils.PropertiesUtils;

 
/*****
 * 
 * @author dingkai
 * 公共信息获取工具类
 */
public class MsgConfig {
	public static Map<String, String> MsgMap = new HashMap<String, String>();
	public static String  defaultErrorKey="common-config.pubmsg.default";
	public static Map<String, String> getMsgMap() {
		return MsgMap;
	}

	public static void setMsgMap(Map<String, String> msgMap) {
		MsgMap = msgMap;
	}
	/**
	 * 直接装载属性配置文件作为信息库
	 * @param prop
	 */
	public static void setMsgMapFromProp(Properties prop) {
		prop.keySet();
		 for (Iterator<Object> tkey = prop.keySet().iterator(); tkey.hasNext();) {
			String keystr = (String) tkey.next();
			MsgConfig.MsgMap.put(keystr,prop.getProperty(keystr));
		}
	}
	/**
	 * 直接装载属性配置文件作为信息库
	 * @param prop
	 * @throws IOException 
	 */
	public static void setMsgMapFromProp(String msgFilePath) throws IOException {
		Properties prop=PropertiesUtils.getProperties(msgFilePath);
		if (prop!=null) {
			setMsgMapFromProp(prop);
		}else throw new FileNotFoundException(" config-commons:can't find this properties file:"+msgFilePath);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param regex  需要替换的变更
	 * @param repl  替换后的值
	 * @return
	 */
	public static String msg(String key) {
		return MsgConfig.msg(key,null);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param regex  需要替换的变更
	 * @param repl  替换后的值
	 * @return
	 */
	public static String msg(String key, String regex,String repl) {
		Map<String, String> tmap = new HashMap<String, String>();
		tmap.put(regex, repl);
		return MsgConfig.msg(key,tmap);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param Map<String, String> replace   需要替换的变更 ，key为变量名，值为替换后的值
	 * @return
	 */
	public static String msg(String key, Map<String, String> replace) {
		String msginfo = null;
		if (key != null && !key.equals("")) {
			msginfo = MsgMap.get(key);
			if (msginfo == null) {
				Map<String, String> tmap = new HashMap<String, String>();
				tmap.put("key", key);
				msginfo =MsgMap.get(defaultErrorKey)!=null? MsgConfig.msg(defaultErrorKey, tmap):"key '"+key+"' can't find this  value !!!  ";
			} else if (replace!=null&&!replace.isEmpty()) {
				for (Iterator<String> iterator = replace.keySet().iterator(); iterator
						.hasNext();) {
					String tkey = (String) iterator.next();
					if(replace.get(tkey)!=null){
					msginfo = msginfo.replaceAll("\\$\\{" + tkey + "\\}", replace
							.get(tkey));
					}
				}

			}

		}
		return msginfo;
	}



}
