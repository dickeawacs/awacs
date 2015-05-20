package common.cdk.config.files.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import common.cdk.config.files.log4j.MyLogSeting.logLevel;

public class MyLogger {
 
	private Logger logger;
	public MyLogger() {
	}
	public MyLogger(Logger logger) {
		this.logger=logger;
	}
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	/***
	 * 静态方法 
	 * 输出sql
	 * @param logger
	 * @param message
	 * @param t
	 */
	public static void sql(Logger log, Object message, Throwable t) {
		if (MyLogSeting.isShow(logLevel.sql)) {
			//System.out.println(message);
			log.info("[sql]"+message,t);
		}
	}
/***
 * 静态方法 
 * 输出sql
 * @param logger
 * @param message
 */
	public static void sql(Logger log, Object message) {
		if (MyLogSeting.isShow(logLevel.sql)) {
			//System.out.println(message);
			log.info("[sql]"+message);
		}
	}
/***
 * 静态方法 
 * 输出  参数
 * @param logger
 * @param message
 * @param t
 */
	public static void param(Logger log, Object message, Throwable t) {
		if (MyLogSeting.isShow(logLevel.param)) {
			//System.out.println(message);
			log.info("[sql]"+message,t);
		}
	}

	/***
	 * 静态方法 
	 * 输出  参数
	 * @param logger
	 * @param message
	 */
	public static void param(Logger log, Object message) {
		if (MyLogSeting.isShow(logLevel.param)) {
			//System.out.println(message);
			log.info("[param]"+message);
		}
	}
	/***
	 * 输出sql
	 * @param logger
	 * @param message
	 * @param t
	 */
	public  void sql( Object message, Throwable t) {
		if (MyLogSeting.isShow(logLevel.sql)) {
			//System.out.println(message);
			logger.info("[sql]"+message,t);
		}
	}
/***
 * 输出sql
 * @param logger
 * @param message
 */
	public  void sql( Object message) {
		if (MyLogSeting.isShow(logLevel.sql)) {
			//System.out.println(message);
			logger.info("[sql]"+message);
		}
	}
/***
 * 输出  参数
 * @param logger
 * @param message
 * @param t
 */
	public  void param( Object message, Throwable t) {
		if (MyLogSeting.isShow(logLevel.param)) {
			//System.out.println(message);
			logger.info("[param]"+message,t);
		}
	}

	/***
	 * 输出  参数
	 * @param logger
	 * @param message
	 */
	public  void param(Object message) {
		if (MyLogSeting.isShow(logLevel.param)) {
			//System.out.println(message);
			logger.info("[param]"+message);
		}
	}
	public void assertLog(boolean assertion, String msg) {
		 
		logger.assertLog(assertion, msg);
	}
	public void debug(Object message, Throwable t) {
		// TODO Auto-generated method stub
		logger.debug(message, t);
	}
	public void debug(Object message) {
		// TODO Auto-generated method stub
		logger.debug(message);
	}
	public void error(Object message, Throwable t) {
		// TODO Auto-generated method stub
		logger.error(message, t);
	}
	public void error(Object message) {
		// TODO Auto-generated method stub
		logger.error(message);
	}
	 
	public void fatal(Object message, Throwable t) {
		// TODO Auto-generated method stub
		logger.fatal(message, t);
	}
	 
	public void fatal(Object message) {
		// TODO Auto-generated method stub
		logger.fatal(message);
	}
	 
	public void info(Object message, Throwable t) {
		// TODO Auto-generated method stub
		logger.info(message, t);
	}
	 
	public void info(Object message) {
		// TODO Auto-generated method stub
		logger.info(message);
	}
	 
	public void l7dlog(Priority priority, String key, Object[] params,
			Throwable t) {
		// TODO Auto-generated method stub
		logger.l7dlog(priority, key, params, t);
	}
	 
	public void l7dlog(Priority priority, String key, Throwable t) {
		// TODO Auto-generated method stub
		logger.l7dlog(priority, key, t);
	}
	 
	public void log(Priority priority, Object message, Throwable t) {
		// TODO Auto-generated method stub
		logger.log(priority, message, t);
	}
	 
	public void log(Priority priority, Object message) {
		// TODO Auto-generated method stub
		logger.log(priority, message);
	}
	 
	public void log(String callerFQCN, Priority level, Object message,
			Throwable t) {
		// TODO Auto-generated method stub
		logger.log(callerFQCN, level, message, t);
	}
	 
	public void warn(Object message, Throwable t) {
		// TODO Auto-generated method stub
		logger.warn(message, t);
	}
	 
	public void warn(Object message) {
		// TODO Auto-generated method stub
		logger.warn(message);
	}
	
	
}
