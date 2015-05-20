package common.ecp.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
/***
 * 
* @Title: PropertiesUtil.java 
* @Package common.ecp.utils 
* @Description: 读取common.properties属性配置文件的内容 ,采用的getResourceAsStream方式读取，
* 因此不会重复的对文件流获取，而是直接获取了缓存的文件。
* @author 陈定凯 
* @date 2015年5月13日 下午5:15:03 
* @version V1.0
 */
public class PropertiesUtil {
	Properties prop = new Properties();
	
	public PropertiesUtil(){
		InputStreamReader in=null;
		try {
			in = new InputStreamReader(PropertiesUtil.class
					.getResourceAsStream("/common.properties"),"utf8");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(in!=null)
			{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getProperty(String key) {
		
		return prop.getProperty(key);
	}
}
