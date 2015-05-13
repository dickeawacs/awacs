package common.cdk.filter.tools;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.cdk.filter.pojo.FilterResult;
/***
 * 工具类的接口，所有的在filter中需要操作的类，都需要实现他
 * @author cdk
 *
 */
public interface ToolInterface {
	
	/***
	 * 在初始化是被调用 
	 * @param filterConfig
	 */
	public void init(FilterConfig filterConfig) ;
	/***
	 * 销毁时被调用 
	 */
	public void destory();
	/***
	 *  befor  excute chain.doFilter(request,response);
	 * @param request
	 * @param response
	 */
	public FilterResult befor(HttpServletRequest request, HttpServletResponse response, FilterResult  result);
	
	/***
	 *  after  excute chain.doFilter(request,response);
	 * @param request
	 * @param response
	 */
	public  FilterResult  after(HttpServletRequest request, HttpServletResponse response, FilterResult  result);
 
	
	
	
}
