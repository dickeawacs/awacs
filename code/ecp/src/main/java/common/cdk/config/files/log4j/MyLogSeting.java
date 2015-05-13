package common.cdk.config.files.log4j;

import java.util.HashMap;
import java.util.Map;

/***
 * tsp日志器的控制类
 * @author cdk
 *
 */
public class MyLogSeting { 
	public enum logLevel{sql,param};
	public static String[] level=null;
	private  static Map<logLevel, String > levelmap=new HashMap<logLevel, String>(); 
 
	
	public static String[] getLevel() {
		return level;
	}

	public static void setLevel(String[] level) {
		MyLogSeting.level = level;
		loadLevel();
	}
	private static void loadLevel() {
		if(level!=null)
		for (int i = 0; i < level.length; i++) {
			if(level[i].trim().equals("sql"))levelmap.put(logLevel.sql, level[i].trim());
			else if(level[i].trim().equals("param"))levelmap.put(logLevel.param, level[i].trim());
		}
	}
	
	public  static boolean isShow(logLevel l ) {
		boolean end=false;
		if(logLevel.sql==l) {
			System.out.println("yes");
			end=levelmap.containsKey(logLevel.sql);
		}else if(logLevel.param==l) {
			System.out.println("yes");
			end=levelmap.containsKey(logLevel.param);
		} 
		return end;
	}
	 
	
}
