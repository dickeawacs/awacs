package com.cdk.ats.current;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.cache.EquipmentCacheVO;
import com.cdk.ats.udp.event.EventContext;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.action.login.UserInfo;
import com.cdk.ats.web.action.login.UserLoginAction;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.AjaxResult;
import com.cdk.ats.web.utils.PortFormateUtils;
import common.cdk.config.files.msgconfig.MsgConfig;

/***
 * 
 * 描述： 用于查询设备的当前信息
 * 
 * @author dingkai 2014-1-11
 * 
 */
public class EquipmentAction {
	private static Logger logger=Logger.getLogger(EquipmentAction.class);
	private String SUCCESS = "success";
	private AjaxResult result;
	private Integer equipmentFid;
	private Integer equipmentSid;
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}



	public AjaxResult getResult() {
		return result;
	}



	public void setEquipmentFid(Integer equipmentFid) {
		this.equipmentFid = equipmentFid;
	}

	public void setEquipmentSid(Integer equipmentSid) {
		this.equipmentSid = equipmentSid;
	}

	/***
	 * 查询当前设备的输出端口信息
	 * @throws IOException 
	 */
	public String findOutPort() throws IOException {
		
		result=new AjaxResult();
		try{
		if (equipmentFid != null && equipmentSid != null) {
			Table10 tempPorts =EventContext.getEquipment(equipmentFid, equipmentSid);
			//AtsEquipmentCache.getEquiReadOnly(equipmentFid,
				//	equipmentSid);	
			if(tempPorts!=null){
			result.setArray(PortFormateUtils.getOutPorts(tempPorts));
			result.setTotal(result.getArray().size());
			}
			result.setLimit(8);
			result.isSuccess();
		}
		}catch (Exception e) {
			result.isFailed(e.getMessage());
		}finally{
			result.writeToJsonString();
			
		}
		return null;
		//return "success";
	}
	/***
	 * 查询当前设备的输入端口信息
	 * @throws IOException 
	 */
	public String findInPort() throws IOException {
		
		result=new AjaxResult();
		try{
		if (equipmentFid != null && equipmentSid != null) {
			Table10 tempPorts =EventContext.getEquipment(equipmentFid, equipmentSid);
			//AtsEquipmentCache.getEquiReadOnly(equipmentFid,
				//	equipmentSid);	
			if(tempPorts!=null){
			result.setArray(PortFormateUtils.getInPorts(tempPorts));
			result.setTotal(result.getArray().size());
			}
			result.setLimit(8);
			result.isSuccess();
		}
		}catch (Exception e) {
			result.isFailed(e.getMessage());
		}finally{
			result.writeToJsonString();
			
		}
		return null;
		//return "success";
	}

	/***
	 * 
	 * 描述： 查询缓存 设备树
	 * 
	 * @createBy dingkai
	 * @createDate 2014-1-11
	 * @lastUpdate 2014-1-11
	 * @return
	 */
	public String findEquipmentTree() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			ar.isSuccess();
			List<EquipmentCacheVO>  trees=AtsEquipmentCache.getTree();
		/*	StringBuffer sb=new StringBuffer();
			for (int i = 0; i < trees.size(); i++) {
				
			}*/
			
			ar.setArray(trees);
		} catch (Exception e) { 
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonArrayString();
		}
		return null;
		}
	
	/***
	 * 
	 * 描述： 获取缓存中的一层设备
	 * @createBy dingkai
	 * @createDate 2014-1-12
	 * @lastUpdate 2014-1-12
	 * @return
	 */
	public String findEquipmentFirst(){
		result=new AjaxResult();
		UserInfo user=UserLoginAction.getLoginUser();
		
		if(user!=null){
			if(Integer.valueOf(Constant.ROLE_ADMIN).equals(user.getLevel())){
				result.setArray(AtsEquipmentCache.getEquipmentFirstClone(user.getAddres()));
			}else if(Integer.valueOf(Constant.ROLE_SYS_ADMIN).equals(user.getLevel())){
				result.setArray(AtsEquipmentCache.getEquipmentFirstClone());
			}
		result.isSuccess();
		}else result.isFailed();
		return SUCCESS;
	}

	
	/***
	 * 
	 * 描述： 获取缓存中的二层设备
	 * @createBy dingkai
	 * @createDate 2014-1-12
	 * @lastUpdate 2014-1-12
	 * @return
	 */
	public String findEquipmentSecond(){
		result=new AjaxResult();
		if(equipmentFid!=null){
			result.setArray(AtsEquipmentCache.getEquipmentSecondClone(equipmentFid));
		}
			result.isSuccess();
		return SUCCESS;
	}
/***
 * 
 * 描述：系统是否可用
 * @createBy dingkai
 * @createDate 2014-4-27
 * @lastUpdate 2014-4-27
 * @return
 */
	public String systemUsed(){
	 
		result=new AjaxResult();
		
	 if(Constant.SYS_PORT_CAN_USE==810){
			result.isFailed(MsgConfig.msg("ats.ip.cant.find"));
		}else if(Constant.SYS_PORT_CAN_USE==805){
			result.isFailed(MsgConfig.msg("ats.port.was.used","port",Constant.SYS_NOW_PORT));
		}else result.isSuccess();
		return SUCCESS;
	
		
		
	}
}
