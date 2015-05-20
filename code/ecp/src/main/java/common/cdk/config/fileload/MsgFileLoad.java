package common.cdk.config.fileload;

import java.io.IOException;

import org.apache.log4j.Logger;


import common.cdk.config.files.msgconfig.MsgConfig;
import common.filethread.exception.ProcessException;
import common.filethread.file.FilePojo;
import common.filethread.process.DataProcessing;

/***
 * 
 * @author cdk
 * Implement DataProcessing Class  
 */
public class MsgFileLoad implements DataProcessing {
	private static Logger  logger=Logger.getLogger(MsgFileLoad.class);
	public void process(FilePojo filepojo) throws ProcessException {
		try {
			MsgConfig.setMsgMapFromProp(filepojo.getFilePath());
			logger.info("reload  "+filepojo.getFileName()+" sucessfully! ");
			logger.info(" load log4j successfully!");
			logger.info(filepojo.getFileName()+" file path:"+filepojo.getFilePath());
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
