package com.cdk.ats.web.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;

public class PageBean {

	private HttpServletRequest request;
	private AjaxResult ra;
	private Map<String,Object> params=new HashMap<String, Object>();

	public PageBean(HttpServletRequest request,AjaxResult ar) {
		this.request = request;
		this.ra=ar;
		formateParamter();
	}

	private void formateParamter() {
		Map<String, String> paramters = this.getRequest().getParameterMap();
		Set<String> keys = paramters.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			if (paramters.get(name)!=null) {
				if (name.equalsIgnoreCase("limit")) {					
					if(NumberUtils.isNumber(request.getParameter(name).toString())){
						ra.setLimit(NumberUtils.createInteger(request.getParameter(name).toString()));
					}
				}else  if (name.equalsIgnoreCase("start")) {
					if(NumberUtils.isNumber(request.getParameter(name).toString())){
						ra.setStart(NumberUtils.createInteger(request.getParameter(name).toString()));
					}
				}else  if (name.equalsIgnoreCase("page")) {
					if(NumberUtils.isNumber(request.getParameter(name).toString())){
						ra.setPage(NumberUtils.createInteger(request.getParameter(name).toString()));
					}
				}else{  
					
					if(request.getParameter(name)!=null&&request.getParameter(name).trim().length()>0){ 
						params.put(name.toUpperCase(),request.getParameter(name));
					/*if(NumberUtils.isNumber(request.getParameter(name).toString())){
						ra.setPage(NumberUtils.createInteger(request.getParameter(name).toString()));
					}*/
				}
				}
				

			}

		}

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public AjaxResult getRa() {
		return ra;
	}

	public void setRa(AjaxResult ra) {
		this.ra = ra;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	
	
}

