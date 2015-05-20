package common.ecp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;

import common.ecp.exception.DataInvalidException;
import common.ecp.exception.ValidateException;

/**
 * 
* @Title: RequestUtil.java 
* @Package common.ecp.utils 
* @Description: Request请求工具类
* @author 陈定凯 
* @date 2015年5月13日 下午5:14:13 
* @version V1.0
 */
public class RequestUtil {
	private static final Log log = LogFactory.getLog(RequestUtil.class);

	public static String getFromRequestPostData2Str(HttpServletRequest request) throws IOException{
		String jsonstr = getRequestPostData(request);
		if(StringUtils.isEmpty(jsonstr)){
			log.debug("获取postdata(request.playload)数据为空，现尝试从form-data中获取");
			//查找数据中parametername有值而parameter value为空的数据
			Enumeration pnames = request.getParameterNames();
			while (pnames.hasMoreElements()) {
				String pname = (String) pnames.nextElement();
				String parametervalue = request.getParameter(pname);
				if(log.isDebugEnabled()){
					log.debug(String.format("form-data[参数名：%s，参数值 ：%s]",pname,parametervalue));
				}
				if(org.apache.commons.lang3.StringUtils.isEmpty(parametervalue)){
					log.debug("从form-data中找到合适的数据"+pname);
					jsonstr = pname;
					break;
				}
			} 
			if(org.apache.commons.lang.xwork.StringUtils.isEmpty(jsonstr)){
				if(log.isDebugEnabled()){
					log.debug("无法找到post数据");
				}
			}
		}
		return jsonstr;
	}



	/**
	 * 从formdata获取数据
	 * @param request
	 * @param jsonstr
	 * @return
	 */
	public static String getFromFormData(HttpServletRequest request) {
		Enumeration pnames = request.getParameterNames();
		StringBuilder sb = new StringBuilder();
		String pairspliter = "";
		while (pnames.hasMoreElements()) {
			String pname = (String) pnames.nextElement();
			String parametervalue = request.getParameter(pname);
			String keyvaluespliter = "";
			if(log.isDebugEnabled()){
				log.debug(String.format("form-data[参数名：%s，参数值 ：%s]",pname,parametervalue));
			}
			if(org.springframework.util.StringUtils.hasText(parametervalue)&&org.springframework.util.StringUtils.hasText(pname)){
				keyvaluespliter="=";
			}
			sb.append(pairspliter);
			sb.append(pname);
			sb.append(keyvaluespliter);
			sb.append(parametervalue);
			pairspliter="&";
			
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 获取post的请求数据,并转化成对象
	 * 
	 * @param request
	 * @param c
	 * @return
	 * @throws ValidateException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T getFromRequestPostData(HttpServletRequest request,
			Class<T> c) throws ValidateException, JsonParseException,
			JsonMappingException, IOException {
		String jsonstr = getFromRequestPostData2Str(request);
		return convertJson2Obj(jsonstr,c);
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
	public static <T> T convertJson2Obj(String jsonstr,Class<T> c) throws JsonParseException, JsonMappingException, IOException, ValidateException{
		log.debug(String.format("把字符串内容[%s]映射为对象[%s]",jsonstr,c.getName()));
		if(StringUtils.isEmpty(jsonstr)){
			log.error("post的数据为空");
			throw new ValidateException(1203,"参数不正确,请求参数为空");
		}
		T binding;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,
					Boolean.TRUE);
			binding = mapper.readValue(jsonstr, c);
		} catch (Exception e) {
			log.error("转换失败",e);
			throw new ValidateException(1203,"参数不正确,请求参数转换失败");
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
	public static <T> T convertJson2Obj(String jsonstr,Class<T> c,Class parametricType) throws JsonParseException, JsonMappingException, IOException, ValidateException{
		log.debug(String.format("把字符串内容[%s]映射为对象[%s]",jsonstr,c.getName()));
		if(StringUtils.isEmpty(jsonstr)){
			log.error("post的数据为空");
			throw new ValidateException(1203,"参数不正确,请求参数为空");
		}
		T binding;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,
					Boolean.TRUE);
			JavaType type = mapper.getTypeFactory().constructParametricType(c, parametricType);
			binding = mapper.readValue(jsonstr, type);
		} catch (Exception e) {
			log.error("转换失败",e);
			throw new ValidateException(1203,"参数不正确,请求参数转换失败");
		}
		return binding;
	}
	
	

	/**
	 * 获取post的请求数据
	 * 
	 * @param request
	 * @return
	 * @throws DataInvalidException
	 * @throws IOException
	 */
	public static String getRequestPostData(HttpServletRequest request)
			throws IOException {
		if(log.isDebugEnabled()){
			log.debug("使用inputStream从payload中获取数据");
		}
		InputStream is = null;
		try {
			is = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			// 读取HTTP请求内容
			String buffer = null;
			StringBuffer sb = new StringBuffer();
			while ((buffer = br.readLine()) != null) {
				sb.append(buffer);
			}
			log.debug("post请求参数为:"+sb.toString());
			return sb.toString();
		} catch (IOException e) {
			log.error("获取请求的数据出错", e);
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 取出请求域中的url
	 * @param request
	 * @return
	 * @author 黄杰俊
	 */
	public static String geturl(HttpServletRequest request) 
	{
		StringBuilder sb = new StringBuilder(1000);
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getRemoteAddr());
		sb.append(":");
		sb.append(request.getServerPort());
		sb.append("");
		sb.append(request.getRequestURI());
		Map map = request.getParameterMap();
		boolean first=true;
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();)
		{
			String key =  iterator.next().toString();
			String[] values = (String[]) map.get(key);
			if(first)
			{
				first=false;
				sb.append("?");
			}
			else
			{
				sb.append("&");
			}
			sb.append(key);
			
			sb.append("=");
			for (int i = 0; i < values.length; i++)
			{
				String value = values[i];
				sb.append(value);
				if(i!=0)
				{
					sb.append(",");
				}
			}
			
		}
		return sb.toString();
	}

	 public static Map<String,Object> getParameterMap(HttpServletRequest request,boolean addSystemParam)
	    {
	    	Map<String, Object> result = new HashMap<String, Object>();
	    	Map map = request.getParameterMap();
	    	for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();)
			{
				String key =  iterator.next().toString();
				String[] values = (String[]) map.get(key);
				if(values.length==1)
				{
					//һ��ֵ��ֱ��д
					result.put(key, values[0]);
				}
				else 
				{
					result.put(key, values);
				}
				
				
			}
	    	if(addSystemParam)
	    	{
//	    		String userID = UserUtil.getUserID(request);
//	    		String userName = UserUtil.getUserName(request);
//	    		result.put("userID", userID);
//	    		result.put("userName", userName);
	    	}
	    	return result;
	    	
	    	
	    }
	 
	 public static Map call4service(String gwurl,String postdata){
		 CloseableHttpClient httpClient=HttpClients.createDefault();
		if(log.isDebugEnabled()){
			log.debug(String.format("发起http请求[url=%s,param=%s]", gwurl,postdata));
		}
		Map result;
		Map doPost = ExHttpClientUtils.doPostByPostData(httpClient, gwurl, postdata);
		String rc = ObjectUtils.toString(doPost.get("resultCode"));
		String rd = (String) doPost.get("resultData");
		if(log.isDebugEnabled())
		{
			log.debug(String.format("接收到如下结果[响应标识=%s,结果内容=%s]", rc,rd));
		}
		if(rc!=null&&rc.equals("200")){
			// 正常，获取到内容，将对内容进行json解释
			if(log.isDebugEnabled())
			{
				log.debug(String.format("对内容尝试json解释"));
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,
					Boolean.TRUE);
			try {
				result = mapper.readValue(rd, Map.class);
			} catch (Exception e) {
				log.error("转换json为对象出错",e);
				//throw new ValidateException()
				return null;
			}
			//result = JSONObject.parseObject(rd);
			
		}
		else{
			result = new HashMap();
			result.put("errcode", 1);
			if(StringUtils.isEmpty(rd)){
				rd="无内容";
			}
			result .put("errmsg", rd);
		}
		return result;
	 }
	 
	 
	 /**
	  * 调用网关实现业务
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static Map call4Service(String gwurl,Map param) throws JsonGenerationException, JsonMappingException, IOException{
		CloseableHttpClient httpClient=HttpClients.createDefault();
		//转换成json
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,
				Boolean.TRUE);
		String bsstr = mapper.writeValueAsString(param);
		Map post = new HashMap();
		post.put(bsstr, "");
		if(log.isDebugEnabled()){
			log.debug(String.format("发起http请求[url=%s,param=%s]", gwurl,bsstr));
		}
		Map doPost = ExHttpClientUtils.doPost(httpClient, gwurl, post);
		
		
		return doPost;
	 }
	/**
	 * 调用网关实现业务
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static String call4ServiceJson(String url,Map param) throws JsonGenerationException, JsonMappingException, IOException{
		Map doPost = call4Service(url,param);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,
				Boolean.TRUE);
		String bsresult = mapper.writeValueAsString(doPost);
		return bsresult;
	}
	
	
	/**
	 * 获取访问客户端地址
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}

}
