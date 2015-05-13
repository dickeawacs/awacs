package common.cdk.config.files.log4j;

import org.apache.log4j.Logger;

public class TspLoggerTest {

	public static Logger logger =  Logger.getLogger(TspLoggerTest.class);
	public static MyLogger mylog=new MyLogger(logger);

	public void test() {
		mylog.error("error:!!!");
		mylog.info("111");
		mylog.sql("sql info");
		mylog.param("param info");
		System.out.println("加载配置");
		MyLogSeting.setLevel(new String[] { "sql", "param" });
		mylog.error("error:!!!");
		mylog.sql("sql info");
		mylog.param("param info");
	}
	public static void main(String[] args) {
		TspLoggerTest t=new TspLoggerTest();
		t.test();
	}
}
