package com.cdk.ats.web.exception;

/***
 * 
 * @author cdk
 *  用户不存在 异常
 */
public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 2920227443473708984L;
	private static  String errorMessage="用户不存在.";
	public UserNotFoundException() {
		super(errorMessage);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable e) {
		super(message, e);
	}

	public UserNotFoundException(Throwable e) {
		super(errorMessage,e);
	}
}
