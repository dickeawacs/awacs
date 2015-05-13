package common.cdk.login.connections;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/***
 * 用户授权数量控制器
 * 
 * @author dingkai
 *
 */
public class ConnectionControl implements  HttpSessionListener{
	private static boolean webReged=false;
	private static Integer ConnectionNumber=0;//最大连接数
	private static  String WebVersion="";//当前系统 版本
	private static String webSequnce="";//系统标记
	private static Date   EndTime=null;//有效截止日期
	private static Integer WebUserNumber=0;//当前用户数量 
	private static Map<String, String> WebUserRecode=new HashMap<String, String>();//当前在线用户
	
	public static final String UserLecenseTag="lecense_Connection_num";//有效用户的标记
	
	
	public static Integer SessionMaxInactiveInterval=600;//seconds  session有效时间 
	
	/***
	 * 获取最大的用户连接数量
	 * @return connectionNumber
	 */
	public static Integer getConnectionNumber() {
		return ConnectionNumber;
	}

	/***
	 * 设置最大的用户连接数量
	 * @param connectionNumber
	 */
	public static void setConnectionNumber(Integer connectionNumber) {
		ConnectionNumber = connectionNumber;
	}
	/***
	 *  获取系统当前的用户登录数量
	 * @return
	 */
	public static Integer getWebUserNumber() {
		return WebUserNumber;
	}



	/***
	 * 登录时调用 此方法 ，获取一个用户使用的授权
	 * @return 若是返回false，则说明连接已满 ，返回true表示可以登录
	 */
	public static boolean regLogin(HttpServletRequest request,String key){
		//Object temp=request.getSession().getAttribute(UserLecenseTag);
	 
		if(key!=null&&WebUserRecode.containsKey(key))return false;
		
		WebUserRecode.put(key, key);
		request.getSession().setAttribute(UserLecenseTag,key);
		return true;
	/*	
		//return WebUserRecode.containsKey(key);
		boolean end=false;
		if(request.getSession(false)!=null)
		{
			//Object request.getSession().getAttribute(UserLecenseTag);
			
			Object temp=request.getSession().getAttribute(UserLecenseTag);
			if(temp!=null&&!temp.toString().trim().equals("")){
				String value=WebUserRecode.get(temp.toString());
				if(value!=null&&!value.trim().equals(""))return true;
			}
		}
			
		if(WebUserNumber<ConnectionNumber){
			WebUserNumber++;
			if(key==null||key.trim().equals("")){
				key=System.currentTimeMillis()+""+Math.random();
			}
			WebUserRecode.put(key, (WebUserNumber)+"");
			request.getSession().setAttribute(UserLecenseTag,key.trim());
			end=true;
		}
		return end;*/
	}
/***
 * 
 */
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		/*if(arg0.getSession().getAttributeNames().hasMoreElements()){
			//String tempkey=System.currentTimeMillis()+""+Math.random();
			WebUserRecode.put(tempkey, (WebUserNumber)+"");
			arg0.getSession().setAttribute(UserLecenseTag,tempkey);
		}*/
		arg0.getSession().setMaxInactiveInterval(SessionMaxInactiveInterval);
	}

	/**
	 * 
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		Object temp=arg0.getSession().getAttribute(UserLecenseTag);
		if(temp!=null&&!temp.toString().trim().equals("")){
			WebUserRecode.remove(temp.toString());
		}
		/*Object temp=arg0.getSession().getAttribute(UserLecenseTag);
		if(temp!=null&&!temp.toString().trim().equals("")){
			String value=WebUserRecode.get(temp.toString());
			if(value!=null&&!value.trim().equals("")) {
				WebUserNumber--;
				WebUserRecode.remove(temp.toString());
			}
		}*/
		
	}
	public static void  sessionInvalidate(HttpServletRequest request,String key) {
		//注销时，将ID移除 
		Object temp=request.getSession().getAttribute(UserLecenseTag);
		if(temp!=null&&!temp.toString().trim().equals("")){
			WebUserRecode.remove(temp.toString());
			//String value=WebUserRecode.get(temp.toString());
			/*if(value!=null&&!value.trim().equals("")) {
				WebUserNumber--;
				WebUserRecode.remove(temp.toString());
			}*/
		}
		
	}

	public static String getWebVersion() {
		return WebVersion;
	}

	public static void setWebVersion(String webVersion) {
		WebVersion = webVersion;
	}

	public static String getWebSequnce() {
		return webSequnce;
	}

	public static void setWebSequnce(String webSequnce) {
		ConnectionControl.webSequnce = webSequnce;
	}

	public static Date getEndTime() {
		return EndTime;
	}

	public static void setEndTime(Date endTime) {
		EndTime = endTime;
	}

	public static Map<String, String> getWebUserRecode() {
		return WebUserRecode;
	}

	public static void setWebUserRecode(Map<String, String> webUserRecode) {
		WebUserRecode = webUserRecode;
	}

	public static void setWebUserNumber(Integer webUserNumber) {
		WebUserNumber = webUserNumber;
	}

	public static boolean isWebReged() {
		return webReged;
	}

	public static void setWebReged(boolean webReged) {
		ConnectionControl.webReged = webReged;
	}

	/***
	 * 用于验证是否已经注册 
	 * @return
	 */
	public boolean isReged() {
		return webReged;
	}
}
