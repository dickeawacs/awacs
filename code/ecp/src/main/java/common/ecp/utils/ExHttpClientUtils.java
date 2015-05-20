package common.ecp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;




/**
 * 
* @Title: ExHttpClientUtils.java 
* @Package common.ecp.utils 
* @Description: ENCODING工具类。
* @author 陈定凯 
* @date 2015年5月13日 下午5:19:02 
* @version V1.0
 */
public class ExHttpClientUtils {

	private static final Log log = LogFactory.getLog(ExHttpClientUtils.class);

	 
	 // The configuration items  
    private static String userName = "test";  
    private static String password = "111111"; 
    
    private static int paramConnectTimeout = 5000;
    
    public static CloseableHttpClient createHttpClient() {
    	return createHttpClient(5000);
    }
	public static CloseableHttpClient createHttpClient(int connectTimeout) {
		// 设置连接超时参数
		paramConnectTimeout = connectTimeout;
		// 构造HttpClient的实例
		CloseableHttpClient httpClient =  HttpClients.createDefault();	  
		return httpClient;
	}	
	
	private static RequestConfig getRequestConfig(){
		return RequestConfig.custom()
				.setConnectTimeout(paramConnectTimeout)
				.build();
	}
      
	
	
	public static Map doPost(CloseableHttpClient httpClient, String requestUrl, Map paramMap) {
		
		
		Map resultMap = new HashMap();
		
		HttpPost httPost = new HttpPost(requestUrl);
		httPost.setConfig(getRequestConfig());
		try {
			HttpEntity he ; 
			// All the parameters post to the web site
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for(Iterator<String> iter = paramMap.keySet().iterator(); iter.hasNext();){
				String key = iter.next();
				nvps.add(new BasicNameValuePair(key, (String)paramMap.get(key)));
			}
			//httPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			CloseableHttpResponse response = httpClient.execute(httPost);
//			GetMethod httpget = new GetMethod("http://www.myhost.com/");
			HttpEntity entity = response.getEntity();
			
			resultMap.put("resultCode", response.getStatusLine().getStatusCode());
			resultMap.put("resultData", EntityUtils.toString(entity));
			
			response.close();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			httPost.abort();
		}
		
		// 返回结果
		return resultMap;
	}
	
	
	public static Map doGet(CloseableHttpClient httpClient, String requestUrl) {
		
		Map resultMap = new HashMap();
		
		HttpGet httpGet = new HttpGet(requestUrl);
		httpGet.setConfig(getRequestConfig());
		
		try {
			
			CloseableHttpResponse response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			
			resultMap.put("resultCode", response.getStatusLine().getStatusCode());
			resultMap.put("resultData", EntityUtils.toString(entity));
			
			response.close();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			httpGet.abort();
		}
		
		// 返回结果
		return resultMap;
	}
	public static Map doPostByPostData(CloseableHttpClient httpClient,
			String requestUrl, String postdata) {
		Map resultMap = new HashMap();
		
		HttpPost httPost = new HttpPost(requestUrl);
		httPost.setConfig(getRequestConfig());
		try {
			StringEntity se = new StringEntity(postdata);
			//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httPost.setEntity(se);
			CloseableHttpResponse response = httpClient.execute(httPost);
			HttpEntity entity = response.getEntity();
			
			resultMap.put("resultCode", response.getStatusLine().getStatusCode());
			resultMap.put("resultData", EntityUtils.toString(entity));
			
			response.close();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultMap.put("resultCode", "-1");
			resultMap.put("resultData", e.getMessage());
		} finally {
			httPost.abort();
		}
		
		// 返回结果
		return resultMap;
	}
	

  
  


}
