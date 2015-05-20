/* 
 * Copyright (c) 2014, S.F. Express Inc. All rights reserved.
 */
package com.cdk.ats.web.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 描述：
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    Feb 18, 2014      sfit0203         Create
 * ****************************************************************************
 * </pre>
 * @author sfit0203
 * @since 
 */
public class WriteExcel {
	String basePath="/";
	String writePath;
	FileOutputStream writeFile =null;
	HSSFWorkbook book;
	public WriteExcel( ){ 
		book=new HSSFWorkbook();
	}
	
	
	public WriteExcel(String path){
		this.writePath=path;
	}
	
	/**
	 * 
	 * 描述:创建一个以时间毫秒
	 * @author sfit0203
	 * @date   Feb 18, 2014
	 *
	 * @throws IOException
	 */
	public String writeAuto() throws IOException{
		this.writePath=basePath+System.currentTimeMillis()+".xls";
		write();
		return this.writePath;
	}
	/***
	 * 
	 * 描述:写出文件 
	 * @author sfit0203
	 * @date   Feb 18, 2014
	 *
	 * @param path
	 * @throws IOException
	 */
	public String write(String path) throws IOException{
		this.writePath=path;
		write();
		return this.writePath;
	}
	/**
	 * 
	 * 描述:写出文件 
	 * @author sfit0203
	 * @date   Feb 18, 2014
	 *
	 * @throws IOException
	 */
	public void write() throws IOException{
		this.writeFile=new FileOutputStream(this.writePath);
		book.write(this.writeFile);
		this.writeFile.close();
	} 
	
	/**
	 * 
	 * 描述:
	 * @author sfit0203
	 * @date   Feb 18, 2014
	 *
	 * @param name  （请不要给 , / 这一类特殊符号 ）
	 * @param results
	 */
	public void writeContent(String name ,List<Object[]> results,String[] heads){
		HSSFSheet tsheet =null;
		if(name!=null&&name.trim().length()>0)
			tsheet=book.createSheet(name);
		else tsheet=book.createSheet();
		HSSFRow headRow=tsheet.createRow(0);
		for (int j = 0; j < heads.length&&j<255; j++) {
			HSSFCell cell = headRow.createCell((short)j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(heads[j]!=null?heads[j].toString():""));
		}
		
		for (int i = 0; i < results.size()&&i<65536; i++) {
			HSSFRow trow=tsheet.createRow(i+1);
			Object[] cellContent=results.get(i);
			//序号列
			{
				HSSFCell cell = trow.createCell((short)0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new HSSFRichTextString(""+(i+1)));
				
			}
			
			 
			for (int j = 0; j <cellContent.length&&j<255; j++) {
				HSSFCell cell = trow.createCell((short)(j+1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				//事件分类
				if(j==0){
					String oldVal=cellContent[j].toString();
					String tempVal=null;
					 if(oldVal==null)
						 tempVal="";
					 else if(oldVal.equals("1"))
						tempVal="管理员操作";
					else if(oldVal.equals("2"))tempVal="用户操作";
					else if(oldVal.equals("3"))tempVal="报警 ";
					else if(oldVal.equals("4"))tempVal="设备故障";
					else if(oldVal.equals("5"))tempVal="端口状态";
					else tempVal=oldVal;
					cell.setCellValue(new HSSFRichTextString(tempVal));
				}else{
					cell.setCellValue(new HSSFRichTextString(cellContent[j]!=null?cellContent[j].toString():""));
				}
			}
		}
	}


	public String getBasePath() {
		return basePath;
	}


	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}


	public String getWritePath() {
		return writePath;
	}


	public void setWritePath(String writePath) {
		this.writePath = writePath;
	}


	public FileOutputStream getWriteFile() {
		return writeFile;
	}


	public void setWriteFile(FileOutputStream writeFile) {
		this.writeFile = writeFile;
	}


	public HSSFWorkbook getBook() {
		return book;
	}


	public void setBook(HSSFWorkbook book) {
		this.book = book;
	}
	
	
}
