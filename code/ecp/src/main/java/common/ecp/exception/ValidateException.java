package common.ecp.exception;

/***********************************************************************
 * Module:  ValidateException.java
 * Author:  huangjiej_2
 * Purpose: Defines the Class ValidateException
 ***********************************************************************/

import java.util.ArrayList;
import java.util.List;

/** 放置校验异常 */
public class ValidateException extends BusinessException {
	
	
	public ValidateException(int errcode, String message, Throwable cause) {
		super(errcode, message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 手机号码不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_MOBILE_NOT_EXISTS=new DataInvalidException(10101, "手机号码不存在");
	/**
	 * 手机号码已存在
	 */
	public static final DataInvalidException ERROR_EXISTING_MOBILE_EXISTS=new DataInvalidException(10102, "手机号码已存在");
	/**
	 * 用户不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_USER_NOT_EXISTS=new DataInvalidException(10103, "用户不存在");
	/**
	 * 用户已存在
	 */
	public static final DataInvalidException ERROR_EXISTING_USER_EXISTS=new DataInvalidException(10104, "用户已存在");
	/**
	 * 应用不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_APP_NOT_EXISTS=new DataInvalidException(10105, "应用不存在");
	/**
	 * 应用已存在
	 */
	public static final DataInvalidException ERROR_EXISTING_APP_EXISTS=new DataInvalidException(10106, "应用已存在");
	/**
	 * 订单流水号不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_ORDERNO_NOT_EXISTS=new DataInvalidException(10107, "订单流水号不存在");
	/**
	 * 订单流水号已存在
	 */
	public static final DataInvalidException ERROR_EXISTING_ORDERNO_EXISTS=new DataInvalidException(10108, "订单流水号已存在");
	/**
	 * 用户账户不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_ACCOUNT_NOT_EXISTS=new DataInvalidException(10109, "用户账户不存在");
	/**
	 * 用户账户已存在
	 */
	public static final DataInvalidException ERROR_EXISTING_ACCOUNT_EXISTS=new DataInvalidException(10110, "用户账户已存在");
	/**
	 * 产品不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_PRODUCT_NOT_EXISTS=new DataInvalidException(10111, "产品不存在");
	/**
	 * 产品已存在
	 */
	public static final DataInvalidException ERROR_EXISTING_PRODUCT_EXISTS=new DataInvalidException(10112, "产品已存在");
	/**
	 * 商户已存在
	 */
	public static final DataInvalidException ERROR_EXISTING_SELLER_EXISTS=new DataInvalidException(10113, "商户已存在");
	/**
	 * 商户不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_SELLER_NOT_EXISTS=new DataInvalidException(10114, "商户不存在");
	/**
	 * 订单不存在
	 */
	public static final DataInvalidException ERROR_EXISTING_ORDER_NOT_EXISTS=new DataInvalidException(10115, "订单不存在");
	/**
	 * 手机号码不匹配
	 */
	public static final DataInvalidException ERROR_MATCH_MOBILE=new DataInvalidException(10201, "手机号码不匹配");
	/**
	 * 用户不匹配
	 */
	public static final DataInvalidException ERROR_MATCH_USER=new DataInvalidException(10202, "用户不匹配");
	/**
	 * 短信验证码不正确
	 */
	public static final DataInvalidException ERROR_MATCH_SMSCODE=new DataInvalidException(10203, "短信验证码不正确");
	/**
	 * 用户密码不正确
	 */
	public static final DataInvalidException ERROR_MATCH_PASSWORD=new DataInvalidException(10204, "用户密码不正确");
	/**
	 * 验证码不正确
	 */
	public static final DataInvalidException ERROR_MATCH_VALIDATECODE=new DataInvalidException(10205, "验证码不正确");
	/**
	 * 订单流水号不匹配
	 */
	public static final DataInvalidException ERROR_MATCH_ORDERNO=new DataInvalidException(10206, "订单流水号不匹配");
	/**
	 * 用户账户不匹配
	 */
	public static final DataInvalidException ERROR_MATCH_ACCOUNT=new DataInvalidException(10207, "用户账户不匹配");
	/**
	 * 支付码不匹配
	 */
	public static final DataInvalidException ERROR_MATCH_PAYMENT_CODE=new DataInvalidException(10208, "支付密码不匹配");
	 /**
	 * 字符集错误，要求使用UTF-8
	 */
	public static final DataInvalidException ERROR_MESSAGE_CHARSET=new DataInvalidException(10401, "字符集错误，要求使用UTF-8");
	/**
	 * 报文长度超出规定要求
	 */
	public static final DataInvalidException ERROR_MESSAGE_OVERLENGTH=new DataInvalidException(10402, "报文长度超出规定要求");
	/**
	 * 报文格式不正确
	 */
	public static final DataInvalidException ERROR_MESSAGE_FORMATING=new DataInvalidException(10403, "报文格式不正确");
 
	/**
	 * 应用已暂停
	 */
	public static final DataInvalidException 	 ERROR_APP_STATUS_PAUSE=new DataInvalidException(10701, "应用已暂停");
	
	/**
	 * 应用已下线
	 */
	public static final DataInvalidException 	 ERROR_APP_OFFLINE=new DataInvalidException(10702, "应用已下线");
	
	/**
	 * 产品已下线
	 */
	public static final DataInvalidException 	 ERROR_PRODUCT_OFFLINE=new DataInvalidException(10703, "产品已下线");
	/**
	 * 商户已下线
	 */
	public static final DataInvalidException 	 ERROR_SELLER_OFFLINE=new DataInvalidException(10704, "商户已下线");
	
	/**
	 * 系统内部错误
	 */
	public static final ValidateException 	 ERROR_SYSTEM_INTERNAL=new ValidateException(10801, "系统内部错误");
	
	/**
	 * 参数格式错误
	 */
	public static final DataInvalidException 	 ERROR_PARAM_FORMAT_ERROR=new DataInvalidException(10101, "【${msg}】参数格式错误");
	
	/**
	 * 参数不存在
	 */
	public static final DataInvalidException 	 ERROR_PARAM_NOTEXIST=new DataInvalidException(10102, "【${msg}】参数不存在");
	
	/**
	 * 参数值为空
	 */
	public static final DataInvalidException 	 ERROR_PARAM_NULL=new DataInvalidException(10103, "【${msg}】参数值为空");
	
	
	
	
	/**
	 * 内容为空的异常
	 */
	public static final int ERRCODE_NULLVALUE=1;
	/**
	 * 数字签名不通过
	 */
	public static final int ERRCODE_SIGNATURE_FAIL=2;
	/**
	 * 手机号码错误
	 */
	public static final int ERRCODE_MOBILE_INVALID = 3;
	/**
	 * 限额上限错误
	 */
	public static final int ERRCODE_QUOTALIMIT = 4;
	/**
	 * 商户异常
	 */
	public static final int ERRCODE_SELLER_INVALID = 5;
	/**
	 * app异常
	 */
	public static final int ERRCODE_APP_INVALID = 6;
	/**
	 * 校验错误
	 */
	public static final int ERRCODE_AUTHENTICATION_FAIL = 7;
	
	static {
		//设置为常用基础类，不被覆盖错误码
		ERROR_EXISTING_MOBILE_NOT_EXISTS.baseException=true;
		ERROR_EXISTING_MOBILE_EXISTS.baseException=true;
		ERROR_EXISTING_USER_NOT_EXISTS.baseException=true;
		ERROR_EXISTING_USER_EXISTS.baseException=true;
		ERROR_EXISTING_APP_NOT_EXISTS.baseException=true;
		ERROR_EXISTING_APP_EXISTS.baseException=true;
		ERROR_EXISTING_ORDERNO_NOT_EXISTS.baseException=true;
		ERROR_EXISTING_ORDERNO_EXISTS.baseException=true;
		ERROR_EXISTING_ACCOUNT_NOT_EXISTS.baseException=true;
		ERROR_EXISTING_ACCOUNT_EXISTS.baseException=true;
		ERROR_MATCH_MOBILE.baseException=true;
		ERROR_MATCH_USER.baseException=true;
		ERROR_MATCH_SMSCODE.baseException=true;
		ERROR_MATCH_PASSWORD.baseException=true;
		ERROR_MATCH_VALIDATECODE.baseException=true;
		ERROR_MATCH_ORDERNO.baseException=true;
		ERROR_MATCH_ACCOUNT.baseException=true;
		ERROR_MESSAGE_CHARSET.baseException=true;
		ERROR_MESSAGE_OVERLENGTH.baseException=true;
		ERROR_MESSAGE_FORMATING.baseException=true;
		ERROR_APP_STATUS_PAUSE.baseException=true;
		ERROR_APP_OFFLINE.baseException=true;
		ERROR_SYSTEM_INTERNAL.baseException=true;
		ERROR_PARAM_FORMAT_ERROR.baseException=true;
		ERROR_PARAM_NOTEXIST.baseException=true;
		ERROR_PARAM_NULL.baseException=true;

	}
	
	/**
	 * 批量处理时每一条记录的出错信息
	 */
	protected List detailMsg = new ArrayList();

	public ValidateException() {
		super();
	}

	public ValidateException(String message, Throwable cause) {
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

	public ValidateException(String message) {
		super(message);
		errcode=1;
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}
	
	public ValidateException(int errcode,String message) {
		super(message);
		this.errcode=errcode;
		
	}
	
	
	/**
	 * 复制一个新的异常
	 * @param e
	 * @return
	 */
	public ValidateException clone(Throwable e){
		ValidateException validateException = new ValidateException(this.errcode, this.getMessage(), e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	/**
	 * 复制一个新的异常并用新的内容进行替换原有内容
	 * @param e
	 * @return
	 */
	public ValidateException clone(Throwable e,String errmsg){
		ValidateException validateException = new ValidateException(this.errcode, errmsg, e);
		validateException.baseException=this.baseException;
		return validateException;
	}
	/**
	 * 复制一个新的异常并用新的内容追加原有内容
	 * @param e
	 * @return
	 */
	public ValidateException cloneAndAppend(Throwable e,String errmsg){
		
		ValidateException validateException = new ValidateException(this.errcode,this.getMessage(), e);
		validateException.appendMessage(errmsg);
		validateException.baseException=this.baseException;
		return validateException;
	}
	
	
 
	
}