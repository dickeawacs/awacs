package common.cdk.config.fileload;

import java.io.IOException;

import org.apache.log4j.Logger;

import common.cdk.config.files.sqlconfig.SqlConfig;
import common.filethread.exception.ProcessException;
import common.filethread.file.FilePojo;
import common.filethread.process.DataProcessing;

public class SqlFileLoad implements DataProcessing {
	private static Logger  logger=Logger.getLogger(SqlFileLoad.class);
	public void process(FilePojo filepojo) throws ProcessException {
		try {
			SqlConfig.setSQLMapFromProp(filepojo.getFilePath());
			logger.info("reload  "+filepojo.getFileName()+" sucessfully! ");
			logger.info(filepojo.getFileName()+"   file path:"+filepojo.getFilePath());
		} catch (IOException e) {
			logger.error(e);
		}

	}

}
