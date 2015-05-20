/**
 * 
 * JsonUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package common.ecp.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;

import common.ecp.exception.DataInvalidException;
import common.ecp.exception.ValidateException;

/**
 * 
* @Title: JsonUtil.java 
* @Package common.ecp.utils 
* @Description: json转换
* @author 陈定凯 
* @date 2015年5月13日 下午5:17:55 
* @version V1.0
 */
public class JsonUtil {

	/**
	 * 转换字符串为对象
	 * @param jsonstr
	 * @param c
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ValidateException 
	 */
	public static <T> T convertJson2Obj(String jsonstr,Class<T> c) throws  DataInvalidException{
		//log.debug(String.format("把字符串内容[%s]映射为对象[%s]",jsonstr,c.getName()));
		T binding;
		try {
			ObjectMapper mapper = getJsonMapper();
			binding = mapper.readValue(jsonstr, c);
		} catch (Exception e) {
			//log.error("转换失败",e);
			throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e, "参数不正确,请求参数转换失败");
		}
		return binding;
	}
	
	/**
	 * 转换字符串为对象
	 * @param jsonstr
	 * @param c
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ValidateException 
	 */
	public static <T> T convertJson2Obj(String jsonstr,Class<T> c,Class parametricType) throws DataInvalidException
	{
//		log.debug(String.format("把字符串内容[%s]映射为对象[%s]",jsonstr,c.getName()));
		T binding;
		try {
			ObjectMapper mapper = getJsonMapper();
			JavaType type = mapper.getTypeFactory().constructParametricType(c, parametricType);
			binding = mapper.readValue(jsonstr, type);
		} catch (Exception e) {
//			log.error("转换失败",e);
			throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e, "参数不正确,请求参数转换失败"); //(ValidateException.err,,e);
		}
		return binding;
	}
	
	/**
	 * 转换对象为json
	 * @param obj
	 * @return
	 * @throws DataInvalidException
	 */
	public static String convert2Json(Object obj) throws DataInvalidException{
		if(obj==null){
			throw ValidateException.ERROR_PARAM_NULL.clone(null,"参数为空");
		}
		try {
			ObjectMapper mapper = getJsonMapper();
			String binding = mapper.writeValueAsString(obj);
			return binding;
		} catch (Exception e) {
//			log.error("转换失败",e);
			throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e, "参数不正确,请求参数转换失败"); //(ValidateException.err,,e);
		}
	}
	
	/**
	 * 获取mapper对象，自带有日期格式化函数针对yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static ObjectMapper getJsonMapper(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,Boolean.TRUE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		mapper.setDateFormat(format);
		return mapper;
	}
	
	
	
	
}
