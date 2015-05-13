package common.filethread.file;

import common.filethread.process.DataProcessing;
/****
 * 
 * @author cdk
 * this file pojo ,use to thread process file load ,if the file was update
 */
public class FilePojo {

	private String fileName;
	private String filePath;// this  file  absolute path
	private String fileType;
	private int    loadSwitch=0; // set 0  is mean  close  thread load update
	private long  lastModified=0L;//this file last modified time 
	private DataProcessing dataprocess;//this  file  data processing interface ,
	private Integer id=500;// only sequnce
	private Integer befor=500;// befor start 
									//if you need process the data,you must implement this interface 
	
	public DataProcessing getDataprocess() {
		return dataprocess;
	}
	public void setDataprocess(DataProcessing dataprocess) {
		this.dataprocess = dataprocess;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getLoadSwitch() {
		return loadSwitch;
	}
	public void setLoadSwitch(int loadSwitch) {
		this.loadSwitch = loadSwitch;
	}
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBefor() {
		return befor;
	}
	public void setBefor(Integer befor) {
		this.befor = befor;
	}

}
