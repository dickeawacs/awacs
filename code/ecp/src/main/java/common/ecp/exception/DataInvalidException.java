package common.ecp.exception;

/***********************************************************************
 * Module:  DataValidException.java
 * Author:  huangjiej_2
 * Purpose: Defines the Class DataValidException
 ***********************************************************************/


/** 主要描述数据不存在 */
public class DataInvalidException extends BusinessException {

	public DataInvalidException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataInvalidException(int errcode, String message) {
		super(errcode, message);
		// TODO Auto-generated constructor stub
	}

	public DataInvalidException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataInvalidException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataInvalidException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DataInvalidException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public DataInvalidException clone(Throwable e){
		DataInvalidException validateException = new DataInvalidException(this.errcode, this.getMessage(), e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	/**
	 * 复制一个新的异常并用新的内容进行替换原有内容
	 * @param e
	 * @return
	 */
	public DataInvalidException clone(Throwable e,String errmsg){
		DataInvalidException validateException = new DataInvalidException(this.errcode, errmsg, e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	/**
	 * 复制一个新的异常并用新的内容追加原有内容
	 * @param e
	 * @return
	 */
	public DataInvalidException cloneAndAppend(Throwable e,String errmsg){
		DataInvalidException validateException = new DataInvalidException(this.errcode,this.getMessage(), e);
		validateException.appendMessage(errmsg);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	
}