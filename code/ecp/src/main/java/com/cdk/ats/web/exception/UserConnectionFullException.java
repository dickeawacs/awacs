package com.cdk.ats.web.exception;

public class UserConnectionFullException extends Exception {
 
	private static final long serialVersionUID = -7255333415210771733L;
	private static  String errorMessage="用户授权连接已满,请稍后再登录.";
	public UserConnectionFullException() {
		super(errorMessage);
	}

	public UserConnectionFullException(String message) {
		super(message);
	}

	public UserConnectionFullException(String message, Throwable e) {
		super(message, e);
	}

	public UserConnectionFullException(Throwable e) {
		super(errorMessage,e);
	}

}
