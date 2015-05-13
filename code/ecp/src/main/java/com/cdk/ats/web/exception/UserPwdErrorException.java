package com.cdk.ats.web.exception;

public class UserPwdErrorException extends Exception {
 
	private static final long serialVersionUID = 2029889433417153738L;
	private static  String errorMessage="登录密码错误.";
	public UserPwdErrorException() {
		super(errorMessage);
	}

	public UserPwdErrorException(String message) {
		super(message);
	}

	public UserPwdErrorException(String message, Throwable e) {
		super(message, e);
	}

	public UserPwdErrorException(Throwable e) {
		super(errorMessage,e);
	}

}
