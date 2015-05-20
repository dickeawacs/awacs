package common.cdk.config.fileload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import common.cdk.config.utils.folder.FileOperate;
import common.filethread.exception.ProcessException;
import common.filethread.file.FilePojo;
import common.filethread.process.DataProcessing;
import common.filethread.thread.FileThread;

public class Log4jFileLoad implements DataProcessing {
	private static Logger logger = Logger.getLogger(Log4jFileLoad.class);

	public void process(FilePojo filepojo) throws ProcessException {
		File logfile = new File(filepojo.getFilePath());
		if (!logfile.exists())
			logger.info("can't found  file " + filepojo.getFilePath());
		else {
			InputStream is = null;
			try {
				is = new FileInputStream(logfile);
				Properties prop = new Properties();
				prop.load(is);
				String logpath= FileThread.getProject_realPath()+ prop.getProperty("log4j.appender.file.File");
				FileOperate.createFile(logpath);
				prop.setProperty("log4j.appender.file.File",logpath);
				PropertyConfigurator.configure(prop);
				logger.info(" load log4j successfully!");
				logger.info(" log path:"+logpath);
				logger.info("logger properties file path:"+filepojo.getFilePath());
			} catch (FileNotFoundException e) {
				logger.error("this  log4j prorperties file can't found,throw   FileNotFoundException",e);
			} catch (IOException e) {
				logger.error("this  log4j prorperties file load,throw   IOException",e);
			} finally {
				if (is != null)
					try {
						is.close();
					} catch (IOException e) {
						logger.error("close log4j inputstream failed!",e);
					}
			}
		}

	}

}
