package common.ecp.exception;

/***********************************************************************
 * Module:  BusinessException.java
 * Author:  huangjiej_2
 * Purpose: Defines the Class BusinessException
 ***********************************************************************/


/** 放置业务异常 */
public class BusinessException extends Exception {
	
	/**
	 * 业务权限鉴权异常，一般是没有权限
	 */
	public static final int ERRCODE_AUTHORITY=8;
	/**
	 * 错误号，0为正常，大于0为出错
	 */
	protected int errcode=0;
	
	/**
	 * 错误信息
	 */
	protected String errmsg;
	
	/**
	 * 是否常用异常
	 */
	protected boolean baseException=false ;
	
	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		errcode=1;
	}
	/**
	 * 错误号，0为正常，大于0为出错
	 */
	public int getErrcode() {
		return errcode;
	}
	/**
	 * 错误号，0为正常，大于0为出错
	 */
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public BusinessException(String message) {
		super(message);
		errmsg = message;
		errcode=1;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(int errcode,String message) {
		super(message);
		errmsg = message;
		this.errcode=errcode;
	}
	public BusinessException(int errcode,String message, Throwable cause) {
		super(message, cause);
		errmsg = message;
		this.errcode=errcode;
	}

	/**
	 * @return the baseException
	 */
	public boolean isBaseException() {
		return baseException;
	}

	/**
	 * @param baseException the baseException to set
	 */
	public void setBaseException(boolean baseException) {
		this.baseException = baseException;
	}

	/**
	 * @param errmsg the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return this.errmsg;
	}
	
	/**
	 * 追加异常信息
	 * @param msg
	 * @return
	 */
	public BusinessException appendMessage(String msg){
		if(this.errmsg==null){
			this.errmsg = msg;
		}
		else if(this.errmsg.indexOf("${msg}")!=-1){
			this.errmsg = this.errmsg.replace("${msg}", msg);
		}
		
		else {
			this.errmsg = this.errmsg+"，"+msg;
		}
		return this;
	}
	
	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public BusinessException clone(Throwable e){
		BusinessException validateException = new BusinessException(this.errcode, this.getMessage(), e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	/**
	 * 复制一个新的异常并用新的内容进行替换原有内容
	 * @param e
	 * @return
	 */
	public BusinessException clone(Throwable e,String errmsg){
		BusinessException validateException = new BusinessException(this.errcode, errmsg, e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	/**
	 * 复制一个新的异常并用新的内容追加原有内容
	 * @param e
	 * @return
	 */
	public BusinessException cloneAndAppend(Throwable e,String errmsg){
		BusinessException validateException = new BusinessException(this.errcode,this.getMessage(), e);
		validateException.appendMessage(errmsg);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	
	
}