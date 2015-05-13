package common.cdk.config.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesUtils {
private static	Logger logger=Logger.getLogger(Properties.class);
/***
 * 加载属于配置文件
 * @param filePath
 * @return
 * @throws IOException
 */
	public static Properties getProperties(String filePath) throws IOException  {
		FileInputStream in = null;
		Properties prop = null;
		try {
			 
			File file = new File(filePath);
			if (file.exists()) {
				in = new FileInputStream(file);
				prop = new Properties();
				prop.load(in);
			}
		} catch (Exception e) {
			logger.error("commons-config:load Properties file  failed  filePath:"+filePath, e);
		} finally {
			if(in!=null)in.close();
		}
		return prop;
	}
}
