package common.filethread.exception;

/*****
 * 
 * @author cdk
 * 处理异常
 */
public class ProcessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMsg;
	private Throwable throwable;
	public ProcessException(String errorMsg, Throwable throwable) {
		super(errorMsg,throwable);
		this.errorMsg = errorMsg;
		this.throwable = throwable;
	}
	public ProcessException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	 

}
