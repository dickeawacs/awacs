package common.cdk.config.files.sqlconfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


import common.cdk.config.files.sqlconfig.encryption.SQLDecryption;
import common.cdk.config.utils.PropertiesUtils;
/*****
 * 
 * @author cdk
 * SQL获取的类
 */
public class SqlConfig {
	private  static SQLDecryption  sqlDecryption;
	public static   int NeedDecryption=0;//非1则为不需要解密
	public static Map<String, String> SQLMap = new HashMap<String, String>();

	public static Map<String, String> getSQLMap() {
		return SQLMap;
	}

	public static void setSQLMap(Map<String, String> SQLMap) {
		SqlConfig.SQLMap = SQLMap;
	}
	/**
	 * 直接装载属性配置文件作为信息库
	 * @param prop
	 */
	public static void setSQLMapFromProp(Properties prop) {
		prop.keySet();
		 for (Iterator<Object> tkey = prop.keySet().iterator(); tkey.hasNext();) {
			String keystr = (String) tkey.next();
			SqlConfig.SQLMap.put(keystr,prop.getProperty(keystr));
		}
	}
	/**
	 * 直接装载属性配置文件作为信息库
	 * @param prop
	 * @throws IOException 
	 */
	public static void setSQLMapFromProp(String SQLFilePath) throws IOException {
		Properties prop=PropertiesUtils.getProperties(SQLFilePath);
		if (prop!=null) {
			setSQLMapFromProp(prop);
		}else throw new FileNotFoundException(" config-commons:can't find this properties file:"+SQLFilePath);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param regex  需要替换的变更
	 * @param repl  替换后的值
	 * @return
	 */
	public static String SQL(String key) {
		return SqlConfig.SQL(key,null);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param regex  需要替换的变更
	 * @param repl  替换后的值
	 * @return
	 */
	public static String SQL(String key, String regex,String repl) {
		Map<String, String> tmap = new HashMap<String, String>();
		tmap.put(regex, repl);
		return SqlConfig.SQL(key,tmap);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param Map<String, String> replace   需要替换的变更 ，key为变量名，值为替换后的值
	 * @return  如果存在 key对应 的值 ，则返回 此值，若不存在，则返回null
	 */
	public static String SQL(String key, Map<String, String> replace) {
		String SQLinfo = null;
		if (key != null && !key.equals("")) {
			SQLinfo = SQLMap.get(key);
			if(NeedDecryption==1)SQLinfo=sqlDecryption.decryption(SQLinfo);
			if (SQLinfo == null) {
			} else if (replace!=null&&!replace.isEmpty()) {
				for (Iterator<String> iterator = replace.keySet().iterator(); iterator
						.hasNext();) {
					String tkey = (String) iterator.next();
					SQLinfo = SQLinfo.replaceAll("\\$\\{"+tkey+"\\}",replace.get(tkey));
				}

			}

		}
		return SQLinfo;
	}

	public static SQLDecryption getSqlDecryption() {
		return sqlDecryption;
	}

	public static void setSqlDecryption(SQLDecryption sqlDecryption) {
		SqlConfig.sqlDecryption = sqlDecryption;
	}

	
}
