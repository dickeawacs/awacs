package com.cdk.ats.web.exception;
/***
 * 
 * @author cdk
 * 用户过期异常
 */
public class UserExpiredException extends Exception {
	private static final long serialVersionUID = 3671289067694148973L;
	private static  String errorMessage="用户密码已过期,请重置密码.";
	public UserExpiredException() {
		super(errorMessage);
	}

	public UserExpiredException(String message) {
		super(message);
	}

	public UserExpiredException(String message, Throwable e) {
		super(message, e);
	}

	public UserExpiredException(Throwable e) {
		super(errorMessage, e);
	}

}
