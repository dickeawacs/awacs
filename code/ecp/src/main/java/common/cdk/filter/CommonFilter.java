package common.cdk.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.cdk.filter.pojo.FilterResult;
import common.cdk.filter.tools.ToolInterface;
/****
 * 
 * @author cdk
 *
 */
public class CommonFilter implements Filter {
	private static String Names="ClassName";
	private static Logger logger=Logger.getLogger(CommonFilter.class);
	private static List<ToolInterface>  Tools=new ArrayList<ToolInterface>();
	public void destroy() {
		destoryExcute();
	}

	/***
	 * 过滤方法 
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		FilterResult   result=beforExcute(req, res);
		if(result.getPass()){
			chain.doFilter(request, response);
			result=null;
			result=afterExcute(req, res);
			if(result.getRedirect()!=null&&!result.getRedirect().trim().equals("")){
				res.sendRedirect(result.getRedirect());
			}else if(result.getForward()!=null&&!result.getForward().trim().equals("")){
				req.getRequestDispatcher(result.getForward()).forward(req, res);
			}
		}else {
			if(result.getRedirect()!=null&&!result.getRedirect().trim().equals("")){
				res.sendRedirect(result.getRedirect());
			}else if(result.getForward()!=null&&!result.getForward().trim().equals("")){
				req.getRequestDispatcher(result.getForward()).forward(req, res);
			}
			
			
		} 
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String ClassNameStr=filterConfig.getInitParameter(Names);
		loadTools(ClassNameStr);
		initExcute(filterConfig);
	}
	
	/***
	 * 过滤前的操作，调用子过滤器的所有befor方法
	 * @param request
	 * @param response
	 * @return
	 */
	private FilterResult beforExcute(HttpServletRequest request, HttpServletResponse response) {
		FilterResult   beforResult=new FilterResult();
		for (int i = 0; i < Tools.size(); i++) {
			beforResult=Tools.get(i).befor(request, response,new FilterResult());
			if(!beforResult.isPass())break;
		}
		return beforResult;
	}
	
	private FilterResult afterExcute(HttpServletRequest request, HttpServletResponse response) {
		FilterResult   afterResult=new FilterResult();
		for (int i = 0; i < Tools.size(); i++) {
			afterResult=Tools.get(i).after(request, response,new FilterResult());
			if(!afterResult.isPass())break;
		}
		return afterResult;
	}
	
	
	private void initExcute(FilterConfig filterConfig) {
		for (int i = 0; i < Tools.size(); i++) {
			 Tools.get(i).init(filterConfig);
		}
	}
	
	private void destoryExcute() {
		for (int i = 0; i < Tools.size(); i++) {
			 Tools.get(i).destory();
		}
	}
	/****
	 *  load  by classNames 
	 * @param classNames
	 */
	private void loadTools(String classNames) {
		classNames=classNames.replaceAll("\n", "").replaceAll("\t", "");
		if(classNames!=null&&!classNames.trim().equals("")) {
			String[] toolNames=classNames.split("[,，]+");
			for (int i = 0; i < toolNames.length; i++) {
				 try {
					ToolInterface tool=(ToolInterface) Class.forName(toolNames[i]).newInstance();
					Tools.add(tool);
				} catch (InstantiationException e) {
					 logger.error("InstantiationException:"+toolNames[i],e);
				} catch (IllegalAccessException e) {
					logger.error("IllegalAccessException:"+toolNames[i],e);
				} catch (ClassNotFoundException e) {
					logger.error("ClassNotFoundException:"+toolNames[i],e);
				}
				 
			}
		}
	}
	

}
