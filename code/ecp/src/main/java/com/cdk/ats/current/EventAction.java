package com.cdk.ats.current;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.sys.AppStartupListener;
import com.cdk.ats.web.utils.AjaxResult;
import common.cdk.config.files.appconfig.WebAppConfig;
import common.cdk.config.utils.PropertiesUtils;
import common.filethread.thread.FileThread;

public class EventAction {
	private static Logger logger=Logger.getLogger(EventAction.class);
	private AjaxResult result;
	/***
	 * 端口号
	 */
	private Integer  portsetting;
	private String  ipsetting;
	
	public AjaxResult getResult() {
		return result;
	}

	/***
	 * 
	 * 描述：ats设置
	 * @createBy dingkai
	 * @createDate 2014-4-20
	 * @lastUpdate 2014-4-20
	 * @return
	 * @throws IOException 
	 */
	public String atssetting() throws IOException{
		FileOutputStream out=null;
		try {
			result=new AjaxResult();
			if(portsetting!=null&&portsetting>0&&ipsetting!=null&&StringUtils.isNotEmpty(ipsetting)&&ipsetting.length()>=7){
					String webappath=FileThread.getProject_realPath() +	ServletActionContext.getServletContext().getInitParameter(AppStartupListener.webAppConfigPath);
					Properties prop=PropertiesUtils.getProperties(webappath);
					prop.put(Constant.ATS_PORT_KEY, portsetting.toString());
					prop.put(Constant.ATS_IP_KEY, ipsetting.toString());
					out=new FileOutputStream(webappath);
					prop.store(out, "");
					out.flush();
					result.isSuccess();
			}
			} catch (IOException e) {
				 logger.error("保存端口失败 ",e);
				 result.isFailed("保存端口失败 ");
			}finally{
				try {
					if(out!=null)
					out.close();
				} catch (IOException e) {
					 logger.error("保存端口失败 ",e);
					 result.isFailed("保存端口失败 ");
				}
				result.writeToJsonString();
			}
		
		return null;
	}
	public String loadSetting() throws IOException{
		result=new AjaxResult();
		try{
		String webappath=FileThread.getProject_realPath() +	ServletActionContext.getServletContext().getInitParameter(AppStartupListener.webAppConfigPath);
		Properties prop=PropertiesUtils.getProperties(webappath);
		String port=prop.getProperty(Constant.ATS_PORT_KEY);
		String ip=prop.getProperty(Constant.ATS_IP_KEY);
		String [] re=new String[2];
		
		if(StringUtils.isNotBlank(port)){
			re[0]=port.trim();
		}else{
			re[0]=("系统当前没有配置有效的端口！");
		}
		if(StringUtils.isNotBlank(ip)){
			re[1]=ip.trim();
		}else{
			re[1]=("系统当前没有配置有效的IP！");
		}
		
		
		
		result.setReturnVal(re);
		result.isSuccess();
		
		}catch (Exception e) {
			result.isFailed();
		}finally{
			
			result.writeToJsonString();
		}
		
		return null;
	}

	public Integer getPortsetting() {
		return portsetting;
	}

	public void setPortsetting(Integer portsetting) {
		this.portsetting = portsetting;
	}

	public String getIpsetting() {
		return ipsetting;
	}

	public void setIpsetting(String ipsetting) {
		this.ipsetting = ipsetting;
	}

 
	
}
