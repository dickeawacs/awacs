package common.cdk.fileupload.struts2;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileProcessI {
	/***
	 * 文件上传的处理类
	 * @return 
	 */
	public boolean struts2ProcessForward(File uploadFile,String uploadFileFileName)throws IOException ;
	/***
	 *  struts2的 ajax方式文件上传处理类
	 * @param uploadFile
	 * @param uploadFileFileName
	 * @throws IOException
	 */
	public void struts2ProcessAjax(File uploadFile,String uploadFileFileName)throws IOException ;
	
	/***
	 *  servlet的 ajax方式文件上传处理类 
	 * @param uploadFile
	 * @param uploadFileFileName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void servletProcessAjax(File uploadFile,String uploadFileFileName,HttpServletRequest request,HttpServletResponse response)throws IOException ;
	
	
}
