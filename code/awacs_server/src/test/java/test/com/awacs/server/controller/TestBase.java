package test.com.awacs.server.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

public class TestBase {
	/***
	 * 
	* 
	* @Description: 读取文件流（文本类文件 ）
	* @author 陈定凯 
	* @date 2015年4月17日 下午5:57:22  
	* @param in
	* @return
	 */
	public String getJsonData(InputStream in) {
		StringBuffer sb = new StringBuffer();
		if (in == null){
			System.out.println("无效的资源文件 ！");
			return null;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	/***
	 * 
	* 
	* @Description: 读取文件数据文本类文件 
	* @author 陈定凯 
	* @date 2015年4月17日 下午5:57:04  
	* @param file
	* @return
	 */
	public String getJsonData(File file) {
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/***
	 * 
	* 
	* @Description: 读取目录下的资源 文件 
	* @author 陈定凯 
	* @date 2015年4月17日 下午5:59:37  
	* @param classpath
	* @return
	 */
	public String getJsonData(String classpath) {
		return getJsonData(this.getClass().getResourceAsStream(classpath));
	}
}
