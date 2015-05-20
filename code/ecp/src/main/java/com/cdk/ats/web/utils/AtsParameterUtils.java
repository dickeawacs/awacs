package com.cdk.ats.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.cdk.ats.udp.exception.AtsException;
import common.cdk.config.files.msgconfig.MsgConfig;

public class AtsParameterUtils {
	/***
	 * 返回一个参数名对应的值,允许为null或“”，为空或“”时返回0
	 * @param name
	 * @param request
	 * @return
	 * @throws AtsException
	 */
	public static Integer getIntegerAllowNull(String name,HttpServletRequest request) throws AtsException{
		int end;
		String value=null;
		value=request.getParameter(name);
		if(value==null)return 0;
		value=StringUtils.deleteWhitespace(value);
		if(value.equals(""))return 0;
		if(!NumberUtils.isNumber(value))throw new AtsException(MsgConfig.msg("ats-1010"));
		end=NumberUtils.createInteger(value);	
		return end;
	}
	/***
	 * 返回一个参数名对应的值,若找不到则抛出异常
	 * @param name 不可为null
	 * @param request
	 * @return
	 * @throws AtsException
	 */
	public static Integer getInteger(String name,HttpServletRequest request) throws AtsException{
		int end;
		String value=null;
		value=request.getParameter(name);
		if(value==null)throw new AtsException(MsgConfig.msg("ats-1010"));
		value=StringUtils.deleteWhitespace(value);
		if(!NumberUtils.isNumber(value))throw new AtsException(MsgConfig.msg("ats-1010"));
		end=NumberUtils.createInteger(value);	
		return end;
	}
	/***
	 * 获取对应的值 ，如果找不到或是为空，则返回 空； 若不为空则返回 转换成INTEGER后的值，若无法转换则返回 NULL
	 * @param name
	 * @param request
	 * @return
	 * @throws AtsException
	 */
	public static Integer getIntegerParam(String name,
			HttpServletRequest request) throws AtsException {
		Integer end = null;
		String value = request.getParameter(name);
		if (value != null) {
			value = StringUtils.deleteWhitespace(value);
			if (NumberUtils.isNumber(value)) {
				end = NumberUtils.createInteger(value);
			}
		}
		return end;
	}
	
	/***
	 * 返回一个参数名对应的值
	 * @param name
	 * @param request
	 * @return
	 * @throws AtsException
	 */
	public static String getString(String name,HttpServletRequest request) throws AtsException{
		String value=null;
			value=request.getParameter(name);
			if(value==null)throw new AtsException(MsgConfig.msg("ats-1010"));
			value=StringUtils.deleteWhitespace(value);
		return value;
	}
	/***
	 * 返回一个参数名对应的值允许为null，当为空时返回“”
	 * @param name
	 * @param request
	 * @return
	 * @throws AtsException
	 */
	public static String getStringAllowNull(String name,HttpServletRequest request) throws AtsException{
		String value=null;
			value=request.getParameter(name);
			if(value==null)return "";
			value=StringUtils.deleteWhitespace(value);
		return value;
	}
	/***
	 * 返回checkbox的值，请注意只有当对应的值1时才会返回1，其它值或空值都返回0
	 * @param name
	 * @param request
	 * @return
	 */
	public static int getCheckbox(String name,HttpServletRequest request){
		int end=0;
		String value=null;
		value=request.getParameter(name);
		if(value!=null){
			value=StringUtils.deleteWhitespace(value);
			if(value.equals("1"))end=1;
		}
		return end;
		
	}
	/***
	 * 获取combobox控制的值，注意：只返回int型的
	 * @param name
	 * @param request
	 * @return
	 * @throws AtsException
	 */
	public static int getComboInt(String name,HttpServletRequest request) throws AtsException{ 
		return getInteger(name, request);
		
	}
	/***
	 * 获取combobox控件的值，注意：只返回包含在args中的有效值,并且值为空时会抛错。
	 * @param name
	 * @param request
	 * @param args int类型的选项，将会依据args数组中的值对进行判断，只有包含在args中的值才是有效值
	 * @return
	 * @throws AtsException
	 */
	public static int getComboInt(String name,HttpServletRequest request,int[] args) throws AtsException{
		int end=-1;
		String value=null;
		value=request.getParameter(name);
		if(value==null)throw new AtsException(MsgConfig.msg("ats-1010"));
		value=StringUtils.deleteWhitespace(value);
		if(!NumberUtils.isNumber(value))throw new AtsException(MsgConfig.msg("ats-1010"));
		end=NumberUtils.createInteger(value);
		boolean tag=false;
		for (int i = 0; i < args.length; i++) {
			if(end==args[i]){tag=true;break;}
		}
		if(!tag)throw new AtsException(MsgConfig.msg("ats-1010"));
		return end;
	}
	/***
	 * 电话号码长16 不够则 * 补位
	 * @param tel
	 * @return
	 */
	public static String tel_16(String tel){
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < 16; i++) {
			sb.append((tel.length()>i?tel.charAt(i):'*'));
		}
		return tel;
	}
}
