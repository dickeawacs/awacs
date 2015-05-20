package common.cdk.config.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import common.cdk.config.files.msgconfig.MsgConfig;


/****
 * 
 * @author cdk
 * 通过HTTP路径，获取相应的文件
 */
public class HttpResource  {
	private String imagName;
	private String imagURI;
	private String imagRealPath;
	private File   imagFile;
	private String filetype;
	private String httpUrl;
	private String imageSize;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2471766596821857960L;
	private static Logger  logger=Logger.getLogger(HttpResource.class);
 
		public HttpResource(String httpUrl,String filename) {
			this.httpUrl=httpUrl;
			this.imagRealPath= filename;
			this.imagFile=new File(this.imagRealPath);
		}
	 
		/***
		 * 
		 * @param httpUrl
		 */
		public HttpResource() {
			this.imagName=System.currentTimeMillis()+"img";
			this.filetype="";
			//this.imagURI=ListenerPropThread.configWebApp.getImagPath();

			
		}

	/***
	 * 
	 * @param httpUrl
	 */
	public HttpResource(String httpUrl) {
		this.imagName=System.currentTimeMillis()+"img";
		this.httpUrl = httpUrl;
		this.filetype=httpUrl.substring(httpUrl.lastIndexOf("."));
		//this.imagURI=ListenerPropThread.configINfos.getImagPath();
	//	this.imagRealPath= ListenerPropThread.Project_realPath+this.imagURI+this.imagName+this.filetype;
		this.imagFile=new File(this.imagRealPath);
	}

	/***
	 * 加载网络图片
	 * 调用此方法时前，需要为  httpUrl 属性赋值
	 * @return  返回相对路径 
	 * @throws IOException
	 */
		public String loadImgs() throws IOException  {
			String end=null;
			BufferedInputStream bis = null;
			OutputStream bos = null;
			try {
				
				URL url = new URL(this.httpUrl);
				bis = new BufferedInputStream(url.openStream());
				byte[] bytes = new byte[512];
				bos = new FileOutputStream(new File(this.imagRealPath));
				int len;
				while ((len = bis.read(bytes)) > 0) {
					bos.write(bytes, 0, len);
				}
				bos.flush();
				end=this.imagURI+this.imagName+this.filetype;
			} catch (Exception e) {
				logger.error(MsgConfig.msg("common-config.pubmsg.httpsrc.urlRead.error","url",httpUrl));
			} finally {
				if(this.imagFile!=null&&this.imagFile.exists())this.imageSize=this.imagFile.length()+"B";
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
			return end;
		}
/***
 * 加载网络图片
 * @param urlpath
 * @param filepath
 * @return  返回绝对路径 
 * @throws IOException
 */
	public String loadImgs(String urlpath,String filepath) throws IOException  {
		BufferedInputStream bis = null;
		OutputStream bos = null;
		try {
			
			URL url = new URL(urlpath);
			bis = new BufferedInputStream(url.openStream());
			byte[] bytes = new byte[512];
			bos = new FileOutputStream(new File(filepath));
			int len;
			while ((len = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			bos.flush();
		} catch (Exception e) {
			logger.error(MsgConfig.msg("common-config.pubmsg.httpsrc.urlRead.error","url",urlpath));
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return filepath;
	}




	/***
	  * 删除imag文件
	  * @return
	  * @throws Exception
	  */
	public boolean delimag() {
		boolean end = false;
		File imagfile = new File(getImagRealPath());
		if (imagfile.exists()) {
			imagfile.delete();
			end = true;
		}

		return end;
	}

  
	


	

 
  
	 public String getImagName() {
		return imagName;
	}



	public void setImagName(String imagName) {
		this.imagName = imagName;
	}



	public String getImagURI() {
		return imagURI;
	}



	public void setImagURI(String imagURI) {
		this.imagURI = imagURI;
	}



	public String getImagRealPath() {
		return imagRealPath;
	}



	public void setImagRealPath(String imagRealPath) {
		this.imagRealPath = imagRealPath;
	}



	public File getImagFile() {
		return imagFile;
	}



	public void setImagFile(File imagFile) {
		this.imagFile = imagFile;
	}



	public String getFiletype() {
		return filetype;
	}



	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}



	public String getHttpUrl() {
		return httpUrl;
	}



	public void setHttpUrl(String httpUrl) {
		this.imagName=System.currentTimeMillis()+"img";
		this.httpUrl = httpUrl;
		this.filetype=httpUrl.substring(httpUrl.lastIndexOf("."));
		//this.imagURI=ListenerPropThread.configINfos.getImagPath();
		//this.imagRealPath= ListenerPropThread.Project_realPath+this.imagURI+this.imagName+this.filetype;
		this.imagFile=new File(this.imagRealPath);
		this.httpUrl = httpUrl;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}




}
