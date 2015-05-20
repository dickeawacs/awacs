package common.filethread.process;

import common.filethread.exception.ProcessException;
import common.filethread.file.FilePojo;

public interface DataProcessing {

	/****
	 *  数据处理方式 
	 */
	public  void  process(FilePojo filepojo)throws ProcessException ;
}
