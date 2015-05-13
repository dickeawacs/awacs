package common.cdk.filter.tools.impl;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.cdk.filter.pojo.FilterResult;
import common.cdk.filter.tools.ToolInterface;

/****
 * user rights
 * 
 * @author dingkai
 * 
 */
public class UserRights implements ToolInterface {
	private static String UserInfo_Name = "UserRight";
	private static String UserInfo_Value = null;
	private static String LoginURL_Name = "loginURL";
	private static String LoginURL_Value = "";
	private static String PassURL_Name="PassURL";
	private static String[] PassURL_Value;

	private static Logger logger = Logger.getLogger(UserRights.class);

	public FilterResult after(HttpServletRequest request, HttpServletResponse response, FilterResult result) {
		logger.debug("  after  call  from common.cdk.filter.tools.impl.UserRights");
		return result;
	}

	/***
	 * 前置操作，
	 * 判断路径 是否可用：isPassURL（）;
	 * 判断是否正常登录 ： 
	 */
	public FilterResult befor(HttpServletRequest request,
			HttpServletResponse response, FilterResult result) {
		if(UserInfo_Value==null||UserInfo_Value.trim().equals(""))return result;
		if(isPassURL(request))return result;
		Object userInfo = request.getSession().getAttribute(UserInfo_Value);
		if (userInfo == null) {
			result.setPass(false);
			result.setRedirect(LoginURL_Value);
			//标记无效用户状态  401
			response.setStatus(401);
			logger.debug(" userInfo check  prevent");
		} else
			logger.debug(" userInfo check  pass");
		return result;
	}

	public void destory() {
	}

	public void init(FilterConfig filterConfig) {
		String userRight = filterConfig.getInitParameter(UserInfo_Name);
		if (userRight != null && !userRight.trim().equals("")) {
			UserInfo_Value = userRight;
			logger.info("load  " + UserInfo_Name + " value:" + UserInfo_Value);
		}
		String loginrurl = filterConfig.getInitParameter(LoginURL_Name);
		if (userRight != null && !userRight.trim().equals("")) {
			LoginURL_Value = loginrurl;
			logger.info("load  " + LoginURL_Name + " value:" + LoginURL_Value);
		}
 
		String passURLs = filterConfig.getInitParameter(PassURL_Name);
		if (passURLs != null && !passURLs.trim().equals("")) {
			String [] tempURLs=passURLs.split("[,]+");
			if(tempURLs.length>0)PassURL_Value=tempURLs;
			else PassURL_Value=new String[0];
			logger.info("load  " + PassURL_Name + " value:" + passURLs);
		}
		 
	}

	/***
	 * 
	 * @param request
	 * @return
	 */
	public boolean isPassURL(HttpServletRequest  request){
		boolean end=false;
		if(PassURL_Value!=null&&PassURL_Value.length>0){
			String uri=request.getRequestURI().toUpperCase();
			for (int i = 0; i < PassURL_Value.length; i++) {
				logger.debug("PassURL_Value["+i+"]:"+PassURL_Value[i]);
				if(uri.indexOf(PassURL_Value[i].toUpperCase())>-1)return true;
			}
		}
		return end;
	}
}
