package com.cdk.ats.web.utils;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

/****
 * 
 * @author cdk ajax 请求后的返回值实体
 * 
 * 说明 ：向设备的请求，只要有响应  则code=900，失败则code=901,至于 操作是否成功通过 suceess标记 
 */
public class AjaxResult implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1173816070601158833L;
	private boolean success = false;//在普通业务中，建议用此参数，来标记是否成功，
	private Integer code = 901; //900：成功;901:失败;300:等待状态;
								//多个值的情况建议用code标记，
								//如需要与UDP通信，并根据其结果判断是否成功的，于 他们有可能会出现多个值，所以,
	private String message = "";
	private String exceptionMessage = "";
	private Object returnVal;
	private Integer total=0;
	private int start=0;
	private int limit=20;
	private int page=1;
	private List   array;

	/***
	 * 将当前实体中的array属性 转为 json
	 * 
	 * @return
	 */
	public String toJsonArrayString() {
		if(returnVal==null)returnVal="";
		return JSONArray.fromObject(this.getArray()).toString();
	}
	/***
	 * 将当前实体转为 json
	 * 
	 * @return
	 */
	public String toJsonString() {
		if(returnVal==null)returnVal="";
		return JSONObject.fromObject(this).toString();
	}
	/***
	 * 直接将对象写入response  以object的方式 
	 * @throws IOException
	 */
	public void writeToJsonString() throws IOException{
		ServletActionContext.getResponse().setContentType("text/html; charset=UTF-8");   
		ServletActionContext.getResponse().getWriter().write(this.toJsonString());
		ServletActionContext.getResponse().getWriter().flush();
	}
	/***
	 * 直接将对象写入response ，以数组的形式
	 * @throws IOException
	 */
	public void writeToJsonArrayString() throws IOException{
		ServletActionContext.getResponse().setContentType("text/html; charset=UTF-8");   
		ServletActionContext.getResponse().getWriter().write(this.toJsonArrayString());
		ServletActionContext.getResponse().getWriter().flush();
	}	
	
/*	public static void main(String[] args) {
		AjaxResult ar = new AjaxResult(true, 900, "操作成功", " exceptionMessage");
		List<Control> l=new ArrayList<Control>();
		l.add(new Control());
		ar.setArray(l);
		ar.isSuccess();
		System.out.println(ar.toJsonString());
	}*/

	public AjaxResult isSuccess() {
		this.code = 900;
		this.success=true;
		return this;
	}
	public AjaxResult isFailed() {
		this.code=901;
		this.success=false;
		return this;
	}
	public void isFailed(String errorMessage) {
		this.code=901;
		this.success=false;
		this.message=errorMessage;
	}
	public AjaxResult() {
	}

	public AjaxResult(boolean success, Integer code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public AjaxResult(boolean success, Integer code, Object returnVal) {
		super();
		this.success = success;
		this.code = code;
		this.returnVal = returnVal;
	}

	public AjaxResult(boolean success, Integer code, String message,
			String exceptionMessage) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.exceptionMessage = exceptionMessage;
	}

	public Object getReturnVal() {
		return returnVal;
	}

	public void setReturnVal(Object returnVal) {
		this.returnVal = returnVal;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List getArray() {
		return array;
	}
	public void setArray(List array) {
		this.array = array;
	}
	public void stateWaiting(){
		this.code=300;		
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
 
	
}
