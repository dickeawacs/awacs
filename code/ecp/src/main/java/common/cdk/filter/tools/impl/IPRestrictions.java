package common.cdk.filter.tools.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import common.cdk.filter.pojo.FilterResult;
import common.cdk.filter.pojo.IPobject;
import common.cdk.filter.tools.ToolInterface;
import common.cdk.xml.exception.XMLFactoryException;
import common.cdk.xml.reader.XMLFactory;

/****
 * ip限制处理类
 * 
 * @author dingkai
 * 
 */
public class IPRestrictions implements ToolInterface {
	private static Logger logger = Logger.getLogger(IPRestrictions.class);
	private String IP_Restrictions_Name = "IP_Restrictions";
	private List<IPobject> IP_pass = new ArrayList<IPobject>();
	private List<IPobject> IP_prevent = new ArrayList<IPobject>();
	private String ErrorURL_Name = "RestrictedErrorURL";
	private String ErrorURL_Value = "";
	private String PassURL_Name="PassURL";
	private String[] PassURL_Value;

	public FilterResult after(HttpServletRequest request,
			HttpServletResponse response, FilterResult result) {
		logger.debug("IPRestrictions after");
		return result;
	}

	public FilterResult befor(HttpServletRequest request,
			HttpServletResponse response, FilterResult result) {
		logger.debug("IPRestrictions befor");
		if (IP_pass.size() < 1 && IP_prevent.size() < 1)return result;
		if(isPassURL(request))return result;
		String requestIP = request.getRemoteAddr();
		Integer[] reqIP = IPobject.formatIP(requestIP);
		result.setPass(validateIP(reqIP));
		if (!result.getPass())
			result.setRedirect(ErrorURL_Value);
		return result;
	}

	public void destory() {
		logger.debug("IPRestrictions destory");
	}

	public void init(FilterConfig filterConfig) {
		String errorURL = filterConfig.getInitParameter(ErrorURL_Name);
		if (errorURL != null && !errorURL.trim().equals("")) {
			ErrorURL_Value = errorURL;
		}
		String passURLs = filterConfig.getInitParameter(PassURL_Name);
		if (passURLs != null && !passURLs.trim().equals("")) {
			String [] tempURLs=passURLs.split("[,]+");
			if(tempURLs.length>0)PassURL_Value=tempURLs;
			else PassURL_Value=new String[0];
			logger.info("load  " + PassURL_Name + " value:" + passURLs);
		}
		 
		String ipres = filterConfig.getInitParameter(IP_Restrictions_Name);
		if (ipres != null && !ipres.trim().equals("")) {
			String projectPath = filterConfig.getServletContext().getRealPath(
					"/");
			File xmlfile = new File(projectPath + ipres);
			if (xmlfile.exists()) {
				XMLFactory xmlf = new XMLFactory(xmlfile);
				try {
					Document document = xmlf.getDocument();
					Element passEle, preventEle;
					Element root = document.getRootElement();
					if (root != null) {
						passEle = root.element("pass");
						if (passEle != null
								&& passEle.attributeValue("switch")
										.equals("on")) {
							List<Element> plist = passEle.elements();
							for (int i = 0; i < plist.size(); i++) {
								if(plist.get(i).attributeValue("switch")
										.equals("on"))
								IP_pass.add(new IPobject(plist.get(i)
										.attributeValue("name"), plist.get(i)
										.attributeValue("begin"), plist.get(i)
										.attributeValue("end"), plist.get(i)
										.attributeValue("ipSwtich")));
							}
						}
						preventEle = root.element("prevent");
						if (preventEle != null
								&& preventEle.attributeValue("switch").equals(
										"on")) {
							List<Element> plist = preventEle.elements();
							for (int i = 0; i < plist.size(); i++) {
								if(plist.get(i).attributeValue("switch")
										.equals("on"))
								IP_prevent.add(new IPobject(plist.get(i)
										.attributeValue("name"), plist.get(i)
										.attributeValue("begin"), plist.get(i)
										.attributeValue("end"), plist.get(i)
										.attributeValue("ipSwtich")));
							}

						}

					}
				} catch (DocumentException e) {
					logger.error("load file  failed :" + xmlfile, e);
				} catch (XMLFactoryException e) {
					logger.error("load file  failed :" + xmlfile, e);
				}

			} else
				logger.info(" can't found file:" + projectPath + ipres);

		}

	}

	public List<IPobject> getIP_pass() {
		return IP_pass;
	}

	public void setIP_pass(List<IPobject> iPPass) {
		IP_pass = iPPass;
	}

	public List<IPobject> getIP_prevent() {
		return IP_prevent;
	}

	public void setIP_prevent(List<IPobject> iPPrevent) {
		IP_prevent = iPPrevent;
	}

	/***
	 *  validate request  IP
	 * @param reqIP
	 * @return
	 */
	private boolean validateIP(Integer[] reqIP) {
		 
		for (int i = 0; i < IP_pass.size(); i++) {
			Integer [] beginIP,endIP;
			beginIP=IP_pass.get(i).getBegin();
			endIP=IP_pass.get(i).getEnd();
			System.out.println(IP_pass.get(i).getBeginStr());
			 if(beginIP!=null&&endIP!=null&&beginIP.length==4&&endIP.length==4){
				 if(passValid(beginIP, endIP, reqIP)) return true;
				 else continue;
				 
			 }else if(beginIP!=null&&beginIP.length==4){
				 if(valid(beginIP, reqIP)) return true;
				 else continue;
			 }
			 else if(endIP!=null&&endIP.length==4){
				 if(valid(endIP, reqIP)) return true;
				 else continue;
			 }
		}
		for (int i = 0; i < IP_prevent.size(); i++) {
			Integer [] beginIP,endIP;
			beginIP=IP_prevent.get(i).getBegin();
			endIP=IP_prevent.get(i).getEnd();
			 if(beginIP!=null&&endIP!=null&&beginIP.length==4&&endIP.length==4){
				 if(preventValid(beginIP, endIP, reqIP)) return false;
				 else continue;
				 
			 }else if(beginIP!=null&&beginIP.length==4){
				 if(valid(beginIP, reqIP)) return false;
				 else continue;
			 }
			 else if(endIP!=null&&endIP.length==4){
				 if(valid(endIP, reqIP)) return false;
				 else continue;
			 }
		}
		return true;

	}
	/***
	 * IP  in  the base
	 * @param base
	 * @param ip
	 * @return
	 */
	private boolean valid(Integer[]  beginIP,Integer[] ip){
		boolean end=true;
		if( beginIP.length==4&&ip.length==4){
			for (int i = 0; i < 4; i++) {
				if( !beginIP[i].equals(ip[i])) return false;
			}
		}
		return end;
	}
	/***
	 * IP between and
	 * @param beginIP
	 * @param endIP
	 * @param ip
	 * @return
	 */
	private boolean passValid(Integer[] beginIP,Integer[] endIP,Integer[] ip){
		boolean end=false;
		if(beginIP.length==4&&ip.length==4&&endIP.length==4){
			for (int i = 0; i < 4; i++) {
				if(beginIP[i]<=ip[i]&&ip[i]<=endIP[i])end=true;
				else return false;
			}
		}
		return end;
	}
	/***
	 * IP not  between.... and....
	 * @param beginIP
	 * @param endIP
	 * @param ip
	 * @return
	 */
	private boolean preventValid(Integer[] beginIP,Integer[] endIP,Integer[] ip){
		boolean end=false;
		if(beginIP.length==4&&ip.length==4&&endIP.length==4){
			for (int i = 0; i < 4; i++) {
				if(beginIP[i]<=ip[i]&&ip[i]<=endIP[i])return true;
			}
		}
		return end;
	}
	/***
	 * 
	 * @param request
	 * @return
	 */
	public boolean isPassURL(HttpServletRequest  request){
		boolean end=false;
		if(PassURL_Value!=null&&PassURL_Value.length>0){
			String uri=request.getRequestURI().toUpperCase();
			for (int i = 0; i < PassURL_Value.length; i++) {
				logger.debug("PassURL_Value["+i+"]:"+PassURL_Value[i]);
				if(uri.indexOf(PassURL_Value[i].toUpperCase())>-1)return true;
			}
		}
		return end;
	}
}
