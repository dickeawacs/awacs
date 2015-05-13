package common.cdk.login.connections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import common.cdk.config.utils.Time;
import common.cdk.login.rsa.RSAutils;
import common.filethread.exception.ProcessException;
import common.filethread.file.FilePojo;
import common.filethread.process.DataProcessing;

/***
 * 加载系统的lecense ，获取其中的最大用户授权数量 。
 * 
 * @author dingkai
 * 
 */
public class ConnectionLoad implements DataProcessing {
	private static Logger logger = Logger.getLogger(ConnectionLoad.class);
	private static FilePojo tempFile = null;

	/***
 * 
 */
	public void process(FilePojo filepojo) throws ProcessException {
		tempFile = filepojo;
		filepojo.setLoadSwitch(0);
		File file = new File(filepojo.getFilePath());
		try {
			if (!file.exists()) {
				/*
				 * logger.error("load lecense failed  :" +
				 * filepojo.getFilePath());
				 * logger.error("System is shut down......"); System.exit(0);
				 * return;
				 */
			} else {
				ConnectionControl.setWebReged(regKeyFileProcess(file, filepojo
						.getFileName()));
			}
		} catch (Exception e) {
		 logger.error(e);
		}

	}

	public boolean reload() {
		boolean end = false;

		return end;

	}

	/*****
	 * 
	 * @param keyFile
	 * @param pubkeyFile
	 * @return
	 * @throws Exception
	 */
	public  boolean regKeyFileProcess(File keyFile, String pubkeyFile)
			throws Exception {
		boolean end = false;
		Reader read = null;
		StringBuffer sb = new StringBuffer();
		try {
			read = new FileReader(keyFile);
			char[] temp = new char[100];
			int i = 0;
			i = read.read(temp);
			while (i > -1) {
				if (i < 100)
					sb.append(temp, 0, i);
				else
					sb.append(temp);
				temp = new char[100];
				i = read.read(temp);
			}
			String keystr = sb.toString().replaceAll("\\s", "");
			RSAutils rsa = new RSAutils();
			RSAPublicKey publickKey = (RSAPublicKey) rsa
					.readFromFile(pubkeyFile);
			byte[] decByte = rsa.decrypt(keystr, publickKey);
			String decStr = new String(decByte, "GBK");
			if (decStr != null) {
				String[] keyInfo = decStr.split("[;]+");

				if (check(keyInfo[0])) {
					try {
						if (!NumberUtils.isNumber(keyInfo[1]))
							throw new Exception();
						ConnectionControl.setConnectionNumber(new Integer(
								keyInfo[1]));

						if (keyInfo[0].length() != 32)
							throw new Exception();
						ConnectionControl.setWebSequnce(keyInfo[0]);

						Long the = new Date().getTime();
						Long endt = Time.switchToyMd(keyInfo[2]).getTime();
						System.out.println(the);
						if (the > endt) {
							logger.info("超过系统使用期限");
							throw new Exception();
						}
						ConnectionControl.setEndTime(Time
								.switchToyMd(keyInfo[2]));

						// if(!NumberUtils.isNumber(keyInfo[1]))throw new
						// Exception();
						ConnectionControl.setWebVersion(keyInfo[3]);
						logger.info("注册信息读取成功！");
						logger.info("有效期至" + ConnectionControl.getEndTime());
						logger.info("版本" + ConnectionControl.getWebVersion());
						logger.info("");
						end = true;
						logger.info("注册成功！");
					} catch (Exception e) {
						logger.info("注册码错误 ");
					}
				} else {
					logger.info("注册码错误 ");
					// System.exit(0);

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (read != null)
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return end;
	}

	
	
	/****
	 * 验证校验码
	 * @param key
	 * @return
	 */
	private boolean check(String key) {/*
		boolean end = false;
		try {
			String localKey = new SysInfo().infomation();
			logger.info("local:"+localKey);
			logger.info("key:"+key);
			if (key.trim().toUpperCase().equals(localKey.trim().toUpperCase()))
				return true;
			else logger.info("验证码与本系统不相符");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return end;
	*/
	return true;	
	}

	
	
	
	public static FilePojo getTempFile() {
		return tempFile;
	}

	public static void setTempFile(FilePojo tempFile) {
		ConnectionLoad.tempFile = tempFile;
	}

	
	
	
	public static void main(String[] args) throws ProcessException {
		FilePojo fp = new FilePojo();
		fp.setDataprocess(new ConnectionLoad());
		fp.setFileName("F:/01tzjreg/public.tzj");
		fp.setFilePath("F:/01tzjreg/mytest2.tzj");
		fp.getDataprocess().process(fp);
	}

	/*
	 * public static void main(String[] args) throws Exception { Reader read =
	 * null; StringBuffer sb = new StringBuffer(); try { read = new
	 * FileReader("F:/01tzjreg/mytest.tzj"); char[] temp = new char[100]; int i
	 * = 0; i = read.read(temp); while (i > -1) { System.out.println(i); if (i <
	 * 100) sb.append(temp, 0, i); else sb.append(temp); temp = new char[100]; i
	 * = read.read(temp); } String keystr = sb.toString().replaceAll("\\s", "");
	 * System.out.println(keystr); RSAutils rsa = new RSAutils(); RSAPublicKey
	 * publickKey = (RSAPublicKey) rsa .readFromFile("F:/01tzjreg/public.tzj");
	 * byte[] decByte = rsa.decrypt(keystr, publickKey);
	 * System.out.println("公钥解密后："); String decStr = new String(decByte, "GBK");
	 * System.out.println(decStr); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * finally { if (read != null) try { read.close(); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 * }
	 */

}
