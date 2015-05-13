package common.cdk.fileupload.struts2;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileUploadAction {

	private Logger logger=Logger.getLogger(FileUploadAction.class); 
	private String fileFileName;
	private File file;
	private FileProcessI process;
	private final String sucess = "sucess";
	private final String failed = "failed";

	/***
	 * 文件上传后，最后是通过response 直接输出给界面，则调用此方法 
	 * @return
	 * @throws IOException 
	 */
	public String struts2Ajax() throws IOException {
		logger.debug("file name:"+fileFileName);
		process.struts2ProcessAjax(file,fileFileName);
		return null;
	}

	/***
	 * 文件上传后，最后是通过跳转页面的方式返回结果，则调用此方法 
	 * @return
	 * @throws IOException 
	 */
	public String struts2forward() throws IOException {
		logger.debug("file name:"+fileFileName);
		if(process.struts2ProcessForward(file,fileFileName)) 
			return sucess;
		else 
			return failed;
	}

	
	
	
	
 

 
	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileProcessI getProcess() {
		return process;
	}

	public void setProcess(FileProcessI process) {
		this.process = process;
	}

	
}
