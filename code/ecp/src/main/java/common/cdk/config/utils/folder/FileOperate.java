package common.cdk.config.utils.folder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class FileOperate {
	private static Logger logger = Logger.getLogger(FileOperate.class);

	public FileOperate() {
	}

	/**
	 * create folder by path 
	 * @param folderPath String As c:/fqf
	 * @return boolean
	 */
	public static boolean createFolder(String folderPath) {
		boolean mkd = false;
		try {
			File myFilePath = new File(folderPath);
			if (!myFilePath.exists()) {
				mkd = myFilePath.mkdirs();
			} else
				mkd = true;
		} catch (Exception e) {
			logger.error("create  folderPath was failed, " + folderPath, e);
		}
		return mkd;
	}

	/**
	 * create file and writ file context 
	 * @param filePathAndName String as: "c:/fqf.txt"
	 * @param fileContent  String file context
	 * @return boolean
	 */
	public static boolean createFile(String filePathAndName, String fileContent) {
		boolean mk = false;
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.getParentFile().mkdirs();
				mk = myFilePath.createNewFile();
			} else
				mk = true;

			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
		} catch (Exception e) {
			mk = false;
			logger.error("create file thow Exception,filePath:"
					+ filePathAndName, e);
		}
		return mk;

	}

	/**
	 * create file  
	 * @param filePathAndName    String as: "c:/a/b/c/d/e/fqf.txt"
	 * @param fileContent      String file context
	 * @return boolean
	 */
	public static boolean createFile(String filePath) {
		boolean mk = false;
		try {
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.getParentFile().mkdirs();
				mk = myFilePath.createNewFile();
			} else
				mk = true;

		} catch (Exception e) {
			logger.error("create file thow Exception,filePath:" + filePath, e);
		}
		return mk;
	}

	/**
	 * delete file by path 
	 * @param filePathAndName    String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent    String
	 * @return boolean
	 */
	public void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			logger.error("del file error ,file path:" + filePathAndName, e);
		}

	}

	/**
	 * delete folder and child by path 
	 * @param folderPath     folder path 如c:/fqf
	 * @return boolean
	 */
	public void delFolder(String folderPath) {
		try {
			delFolderChild(folderPath); // delete child
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // delete folder

		} catch (Exception e) {
			logger.error("delete folder and child error", e);
		}

	}

	/**
	 * delete all the files in the folder 
	 * @param path AS: "c:/fqf/fold1"
	 */
	public void delFolderChild(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delFolderChild(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * copy file to there 
	 * @param fromPath from where AS "c:/fqf.txt"
	 * @param toPath to where AS "f:/fqf.txt"
	 * @return boolean
	 */
	public void copyFile(String fromPath, String toPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(fromPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(fromPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(toPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				if (inStream != null)
					inStream.close();
			}
		} catch (Exception e) {
			logger.error(" copy file to there error,form " + fromPath + "  to "
					+ toPath, e);
		}

	}

	/**
	 * copy folder and child from "formpath" to "topath" 
	 * @param fromPath  from where AS "c:/folder1"
	 * @param toPath  to where AS "f:/folder1"
	 * @return boolean
	 */
	public void copyFolder(String fromPath, String toPath) {

		try {
			(new File(toPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(fromPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (fromPath.endsWith(File.separator)) {
					temp = new File(fromPath + file[i]);
				} else {
					temp = new File(fromPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(toPath + "/"
							+ (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(fromPath + "/" + file[i], toPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * move file from "frompath " to "topath"  
	 * @param fromPath from where AS "c:/fqf.txt"
	 * @param toPath to where AS "f:/fqf.txt"
	 */
	public void moveFile(String fromPath, String toPath) {
		copyFile(fromPath, toPath);
		delFile(fromPath);

	}

	/**
	 * move folder from "frompath " to "topath" 
	 * @param fromPath   from where AS "c:/folder1"
	 * @param toPath   to where AS "f:/folder2"
	 */
	public void moveFolder(String fromPath, String toPath) {
		copyFolder(fromPath, toPath);
		delFolder(fromPath);

	}
}
