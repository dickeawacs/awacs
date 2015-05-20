package com.cdk.ats.web.action.map;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cdk.ats.web.pojo.hbm.Map;
import com.cdk.ats.web.pojo.hbm.Mappoint;
import com.cdk.ats.web.utils.AjaxResult;
import com.cdk.ats.web.utils.ImageTools;

/***
 * 
 * 地图ACTION
 * 
 */
public class MapAction {

	private Logger logger=Logger.getLogger(MapBiz.class);
	
	private AjaxResult result;
	private MapBiz biz;
	private Map defaultMap;
	private Mappoint point;
	private String otherName;
	private File image;
	private String imageFileName;
	
	
	public String uploadFile() throws IOException{
		try{
		getResult();
        String realpath = ServletActionContext.getServletContext().getRealPath("/pub/images");
        //System.out.println("realpath: "+realpath);
        if (image != null&&!StringUtils.isBlank(otherName)) {
        	String type=imageFileName.substring(imageFileName.lastIndexOf("."));
        	imageFileName=System.currentTimeMillis()+type;
        	//ImageUtil iu=new ImageUtil();
            File savefile = new File(new File(realpath), imageFileName);
            if (!savefile.getParentFile().exists())
            {    savefile.getParentFile().mkdirs();
            }
            
           // System.out.println(image.getName());
            FileUtils.copyFile(image, savefile);
            int [] style=new int[]{0,0};
            if(savefile.exists()&&ImageTools.imagesType(type)){
            	style=ImageTools.getImgWidthAndHeight(savefile);
            	if(style[0]>0&&style[1]>0){
            	 defaultMap=new Map();
            	 defaultMap.setWidth(style[0]);
            	 defaultMap.setHeight(style[1]);
                 defaultMap.setUrlpath("pub/images/"+imageFileName);
                 defaultMap.setMapname(otherName);
                 defaultMap.setMapid(biz.saveMap(defaultMap));
                 result.isSuccess().setReturnVal(defaultMap.getMapid());
            	}else{
                	savefile.deleteOnExit();
                	result.isFailed("上传失败!无效地图!");
            	}
            }else if(!ImageTools.imagesType(type)){

            	savefile.deleteOnExit();
            	result.isFailed("上传失败!图片类型不正确!");
            
            	
            }else{
            	savefile.deleteOnExit();
            	result.isFailed("上传失败!");
            }
            
           
        }
        else{
        	result.isFailed("上传失败，请选择文件！");
        }
		}catch (Exception e) {
			result.isFailed(e.getMessage());
			
		}finally{
			result.writeToJsonString();
			
		}
        return null;
    }
	
	
	/***
	 * 
	 * 描述：打开编辑页面
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-2
	 * @lastUpdate 2013-12-2
	 * @return
	 */
	public String editMap() {
		
		return "editPage";
	};

	/**
	 * 
	 * 描述： 保存地图设置的多个点
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-2
	 * @lastUpdate 2013-12-2
	 * @return
	 */
	public String saveMapPoint() {
		try {
			getResult();
			
			if (point != null) {
				result.setReturnVal(biz.savePoint(point));
				result.isSuccess();
			}
		} catch (Exception e) {
			result.isFailed(e.getMessage());
		}

		return "success";
	}

	/**
	 * 
	 * 描述： 保存地图设置的多个点
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-2
	 * @lastUpdate 2013-12-2
	 * @return
	 */
	public String delMapPoint() {
		try {
			getResult();
			
			if (point != null) {
				result.setSuccess(biz.deletePoint(point));
			}
		} catch (Exception e) {
			result.isFailed(e.getMessage());
		}

		return "success";
	}

	
	
	
	
	
	/**
	 * 
	 * 描述： 修改地图的默认显示选择
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-2
	 * @lastUpdate 2013-12-2
	 * @return
	 */
	public String updateShowMap() {
		try {
			getResult();
			if (defaultMap != null) {
				 biz.saveDefaultShow(defaultMap);
				result.isSuccess();
			}
		} catch (Exception e) {
			result.isFailed(e.getMessage());
		}
		return "success";
	}

	/**
	 * 
	 * 描述： 查询出所有的地图列表
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-2
	 * @lastUpdate 2013-12-2
	 * @return
	 */
	public String viewAllMaps() {
		try {
			 
			 getResult();
				 result.setArray(biz.findAllMap());
				result.isSuccess();
		} catch (Exception e) {	
			result.isFailed(e.getMessage());
		}
		return "allMap";
	}
/**
 * 
 * 描述：修改地图名称 
 * @createBy dingkai
 * @createDate 2013-12-22
 * @lastUpdate 2013-12-22
 * @return
 * @throws IOException 
 */
	public String updateMapName() throws IOException{
		try {
			 
			 getResult();
			 if(defaultMap!=null&&defaultMap.getMapid()!=null&&defaultMap.getMapname()!=null){
				biz.updateMapName(defaultMap);
				result.isSuccess();
			 }else{
				 result.isFailed("请填写名称!");
			 }
		} catch (Exception e) {	
			result.isFailed(e.getMessage());
		}finally{
			result.writeToJsonString();
			
		}
		return null;
	}
	/***
	 * 
	 * 描述： 删除指定的地图 
	 * @createBy dingkai
	 * @createDate 2013-12-15
	 * @lastUpdate 2013-12-15
	 * @return
	 */
	public String delMap(){
		try {
			getResult();
			if (defaultMap != null) {
				 biz.delMap(defaultMap);
				result.isSuccess();
			}
		} catch (Exception e) {
			result.isFailed(e.getMessage());
		}
		return "success";
	
	}
	/****
	 * 
	 * 描述： 显示地图
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-2
	 * @lastUpdate 2013-12-2
	 * @return
	 */
	public String viewOnlyMap() {
		 defaultMap=biz.viewOnlyMap(defaultMap); 		
		return "onlyMap";
	}

	
	public String viewCurrentOnlyMap() {
		 defaultMap=biz.viewCurrentOnlyMap(defaultMap); 		
		return "onlyMap";
	}
	/***
	 *  
	 * @return
	 * @throws IOException
	 */
	public String deviceTreePort() throws IOException {
		AjaxResult ar = new AjaxResult();
		try { 
			ar.setArray(biz.findEquipmentAndPort());
			ar.isSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonArrayString();
		}

		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Map getDefaultMap() {
		return defaultMap;
	}

	public void setDefaultMap(Map defaultMap) {
		this.defaultMap = defaultMap;
	}

	public void setBiz(MapBiz biz) {
		this.biz = biz;
	}

	public MapBiz getBiz() {
		return biz;
	}

	public Mappoint getPoint() {
		return point;
	}

	public void setPoint(Mappoint point) {
		this.point = point;
	}

	public AjaxResult getResult() {
		if(result==null){
			result=new AjaxResult();			
		}
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	 
	
}
