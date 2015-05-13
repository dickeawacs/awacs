package common.cdk.filter.tools.impl;

import java.io.UnsupportedEncodingException;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.cdk.filter.pojo.FilterResult;
import common.cdk.filter.tools.ToolInterface;
/****
 * 系统访问的输出的字符编码设置，默认为utf-8;
 * @author dingkai
 *
 */
public class CharacterEncoding implements ToolInterface {
	private static Logger  logger=Logger.getLogger(CharacterEncoding.class);
	private static String  	encodeing_Name="encoding";
	private static String 	encodeing_Value = "UTF-8";
	
 

	public FilterResult after(HttpServletRequest request, HttpServletResponse response,FilterResult result) {
		logger.debug("after  call CharacterEncoding");
		return result;
	}

	public FilterResult befor(HttpServletRequest request, HttpServletResponse response,FilterResult result) {
		
				try {
					request.setCharacterEncoding(encodeing_Value);
					response.setCharacterEncoding(encodeing_Value);
				} catch (UnsupportedEncodingException e) {
					logger.error("setCharacterEncoding failed! UnsupportedEncodingException:"+encodeing_Value);
				}
		return result;
	}

	public void destory() {
	}

	public void init(FilterConfig filterConfig) {
		 String ecdn=filterConfig.getInitParameter(encodeing_Name);
		 if(ecdn!=null&&!ecdn.trim().equals("")) {encodeing_Value=ecdn;
			logger.info("load  " + encodeing_Value + " value:" + ecdn);
		 }
	}

}
