package common.cdk.config.fileload;

import java.io.IOException;

import org.apache.log4j.Logger;

import common.cdk.config.files.appconfig.WebAppConfig;
import common.filethread.exception.ProcessException;
import common.filethread.file.FilePojo;
import common.filethread.process.DataProcessing;
import common.filethread.thread.FileThread;
/***
 * 
 * @author cdk
 *
 */
public class WebAppFileLoad implements DataProcessing {
	private static Logger  logger=Logger.getLogger(WebAppFileLoad.class);
	private static String AppInfoName="appInfo";//application cache name;
	public void process(FilePojo filepojo) throws ProcessException {
			try {
				WebAppConfig webapp=new WebAppConfig();
				webapp.setConfigFormFilePath(filepojo.getFilePath());
				FileThread.getServletContext().setAttribute(AppInfoName, webapp);
				logger.info(" load "+filepojo.getFileName()+" successfully!");
				logger.info(filepojo.getFileName()+"  file path:"+filepojo.getFilePath());
			} catch (IOException e) {
				logger.error(" load  webappconfig info error:"+filepojo.getFilePath());
			}
	}

}
