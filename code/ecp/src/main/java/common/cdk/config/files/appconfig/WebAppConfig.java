package common.cdk.config.files.appconfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


import common.cdk.config.utils.PropertiesUtils;

/***
 * 
 * @author cdk
 *	这个实体类，将被用来存放在 web的全局变量中，
 *如果你有自己的其它属性需要用添加至此变量，你可以继承此类
 */
public class WebAppConfig implements java.io.Serializable{

	private static final long serialVersionUID = -6280929341315456775L;
	private   String webAppName ="";// 公司名称/系统名称  
	private   String  version="";
	private   String  msgSenderCom="off";
	private   Map<String,Object> all=new HashMap<String, Object>();
	public static Map<String, String> WebAppMap = new HashMap<String, String>();
	/***
	 * 配置文件加载（来自文件路径 ）
	 * @param filepath
	 * @throws IOException
	 */
	public  void  setConfigFormFilePath(String filepath) throws IOException{
		Properties prop=PropertiesUtils.getProperties(filepath);
		if (prop!=null) {
			loadConfig(prop);
		}else throw new FileNotFoundException(" common-config:can't find this properties file:"+filepath);
	}

	/***
	 * 状态配置文件
	 * @param prop
	 */
	public  void loadConfig(Properties prop){
		 for (Iterator<Object> tkey = prop.keySet().iterator(); tkey.hasNext();) {
			String keystr = (String) tkey.next();
			all.put(keystr,prop.getProperty(keystr));
			WebAppMap.put(keystr,prop.getProperty(keystr));
			if(keystr.equals("app.name"))webAppName=prop.getProperty(keystr);
			if(keystr.equals("app.version"))version=prop.getProperty(keystr);
			if(keystr.equals("app.msgSenderCom"))msgSenderCom=prop.getProperty(keystr);
		}
	}

	
	
	
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param regex  需要替换的变更
	 * @param repl  替换后的值
	 * @return
	 */
	public static String app(String key) {
		return WebAppConfig.app(key,null);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param regex  需要替换的变更
	 * @param repl  替换后的值
	 * @return
	 */
	public static String app(String key, String regex,String repl) {
		Map<String, String> tmap = new HashMap<String, String>();
		tmap.put(regex, repl);
		return WebAppConfig.app(key,tmap);
	}
	/***
	 * 
	 * @param key  错误信息的 索引
	 * @param Map<String, String> replace   需要替换的变更 ，key为变量名，值为替换后的值
	 * @return  如果存在 key对应 的值 ，则返回 此值，若不存在，则返回null
	 */
	public static String app(String key, Map<String, String> replace) {
		String APPinfo = null;
		if (key != null && !key.equals("")) {
			APPinfo = WebAppMap.get(key);
			 
			if (APPinfo == null) {
			} else if (replace!=null&&!replace.isEmpty()) {
				for (Iterator<String> iterator = replace.keySet().iterator(); iterator
						.hasNext();) {
					String tkey = (String) iterator.next();
					APPinfo = APPinfo.replaceAll("\\$\\{"+tkey+"\\}",replace.get(tkey));
				}

			}

		}
		return APPinfo;
	}

	
	
	
	
	
	
	
	
	public String getWebAppName() {
		return webAppName;
	}

	public void setWebAppName(String webAppName) {
		this.webAppName = webAppName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMsgSenderCom() {
		return msgSenderCom;
	}

	public void setMsgSenderCom(String msgSenderCom) {
		this.msgSenderCom = msgSenderCom;
	}

	public Map<String, Object> getAll() {
		return all;
	}

	public void setAll(Map<String, Object> all) {
		this.all = all;
	}

	 

	 


}
