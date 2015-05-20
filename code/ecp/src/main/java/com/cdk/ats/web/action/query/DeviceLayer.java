package com.cdk.ats.web.action.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.cache.EquipmentCacheVO;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.AjaxResult;
import com.cdk.ats.web.utils.AtsParameterUtils;
import com.cdk.ats.web.utils.PortFormateUtils;
import com.cdk.ats.web.utils.TreeNode;
import com.cdk.ats.web.utils.TreeNodeCheckbox;
import common.cdk.config.files.msgconfig.MsgConfig;
import common.cdk.config.files.sqlconfig.SqlConfig;

/***
 * 第二层设备 相关处理与查询
 * @author cdk
 *
 */
public class DeviceLayer {
	private static Logger logger=Logger.getLogger(DeviceLayer.class);
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	/***
	 * json结果对象
	 */
	private AjaxResult result;
	/***
	 * 获取当前的系统有效设备树，注意这里显示的设备树是数据库的有效初始化状态的设备树 
	 * 
	 * @return
	 * @throws IOException
	 */
	public String  treeStatus  () throws IOException {
	AjaxResult ar = new AjaxResult();
	try {
		ar.isSuccess();
		List<EquipmentCacheVO>  trees=AtsEquipmentCache.getTree();
	/*	StringBuffer sb=new StringBuffer();
		for (int i = 0; i < trees.size(); i++) {
			
		}*/
		
		ar.setArray(trees);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(),e);
		ar.isFailed(e.getMessage());
		ar.setExceptionMessage(e.getMessage());
	} finally {
		ar.writeToJsonArrayString();
	}
	return null;
	}

	
	
	/***
	 * 左侧设备树：查出所有的一层二层设备，此方法 用于ajax请求,
	 * @return
	 * @throws IOException
	 */
	public String deviceTree() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			List<Object[]> t9s=baseDao.findObjectsByHql(SqlConfig.SQL("ats.device.tree.first.all"));
			List<TreeNode> firsts=new ArrayList<TreeNode>();			
			for(int h=0;h<t9s.size();h++){
				TreeNode ftt=new TreeNode();
				Object a=t9s.get(h)[0];
				Object b=t9s.get(h)[1];
				
				if(a==null||!NumberUtils.isNumber(a.toString()))continue;
				int field2=NumberUtils.createInteger(a.toString());
				if(b==null)b="一层设备-"+field2+"";
				ftt.setParentid("root_001");
				ftt.setId("first_"+field2);
				ftt.setText(b.toString());
				//设置二层设备
				List<Object[]> t10s = baseDao.findObjectsByHql(SqlConfig.SQL("ats.device.tree.second.byfirst.all"), new Object[] {field2});
				
				List<TreeNode> seconds=new ArrayList<TreeNode>();
				for (int i = 0; i < t10s.size(); i++) {
					Object c=t10s.get(i)[0];
					Object d=t10s.get(i)[1];
					if(c==null||!NumberUtils.isNumber(c.toString()))continue;
					if(d==null)d="二层设备-"+c.toString();
					TreeNode stt=new TreeNode();
					stt.setId("second_"+c.toString());					
					stt.setParentid(ftt.getId());
					stt.setText(d.toString());		
					stt.setLeaf(true);				
					seconds.add(stt);					
				}	
			
				ftt.setChildren(seconds);
				firsts.add(ftt);				
			}
			ar.isSuccess();
			ar.setArray(firsts);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonArrayString();
		}

		return null;
	}

	
	/***
	 * 一层设备combo ， 只 获取所有的一层设备
	 * @return
	 * @throws IOException
	 */
 	public String first() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
				List<Object[]> firstObjs = baseDao.findObjectsByHql(SqlConfig.SQL("ats.device.tree.first.all"));
				List<Table10> firstDs=new  ArrayList<Table10>();
				for (int i = 0; i < firstObjs.size(); i++) {
					firstDs.add(new Table10(firstObjs.get(i)[0],firstObjs.get(i)[1]));
				}
				ar.isSuccess();
				ar.setArray(firstDs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonString();
		}

		return null;
	}
	/***
	 * 根据一层设备的网络地址，查出所有二层设备
	 * @return
	 * @throws IOException
	 */
 	public String second() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			String firstDevID = ServletActionContext.getRequest().getParameter("firstDevID");
			if (firstDevID != null && NumberUtils.isNumber(firstDevID)&&NumberUtils.createInteger(firstDevID)>-1) {
				List<Table10> t10s = baseDao.findObjectsByHql(SqlConfig.SQL("ats.table10.tree.queryBy"), new Object[] {NumberUtils.createInteger(firstDevID)});
				ar.isSuccess();
				ar.setArray(t10s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonString();
		}

		return null;
	}
	 
 

/***
 * 
 * 描述： 根据一层设备ID与二层设备ID查询设备的详细信息
 * @createBy dingkai
 * @createDate 2014-2-16
 * @lastUpdate 2014-2-16
 * @return
 */
	public String queryEquipmentInfos(){
		try {
			result=new AjaxResult();
			HttpServletRequest request = ServletActionContext.getRequest();
			/***
			 * 一层设备ID
			 */
			Integer field3= AtsParameterUtils.getInteger("field3", request);
			/***
			 * 二层设备ID
			 */
			Integer field4= AtsParameterUtils.getInteger("field4", request);
			if(field3!=0&&field4>-1){
				Table10 temp10= (Table10) baseDao.findOnlyByHql("from Table10 where field3=? and field4=? order by field3,field4", new Object[]{field3,field4});
				int[] cnt=PortFormateUtils.getPortCount(temp10.getDtype());
				temp10.setInCount(cnt[0]);
				temp10.setOutCount(cnt[1]);
				result.setReturnVal(temp10);
				result.isSuccess();
			}
			
		} catch (AtsException e) {
			logger.error(e.getMessage(),e);
			result.isFailed(e.getMessage());
		}
		
		return "JSON_EQUIPMENT";
	}
	
	/***
	 * 
	 * 查出所有的一层二层设备，并将输入设置为第三级叶节点[用户权限设置需要用到]
	 * @return
	 * @throws IOException
	 */
	public String deviceTreePort() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			List<Table10> first=baseDao.findObjectsByHql(SqlConfig.SQL("ats.userset.first.all"));
			List<TreeNode> fristNodes=new ArrayList<TreeNode>();			
			for(int h=0;h<first.size();h++){
				TreeNode ftt=new TreeNode();
				int field3=first.get(h).getField3();
				ftt.setParentid("root_001");
				ftt.setId("first_"+field3);
				ftt.setText(first.get(h).getField121());	
				//设置二层设备
				List<Table10> t10s = baseDao.findObjectsByHql(SqlConfig.SQL("ats.userset.second.by"), new Object[] {field3});
				
				List<TreeNode> seconds=new ArrayList<TreeNode>();
				for (int i = 0; i < t10s.size(); i++) {
					TreeNode stt=new TreeNode();
					stt.setId("second_"+t10s.get(i).getField1());
					int field4=t10s.get(i).getField4();
					int field10=t10s.get(i).getField10();
					stt.setParentid(ftt.getId());
					stt.setText(t10s.get(i).getField121());					
					List<TreeNodeCheckbox> tcs=new ArrayList<TreeNodeCheckbox>();
					
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_1_"+field10,stt.getId(),"输入端口1"));
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_2_"+field10,stt.getId(),"输入端口2"));
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_3_"+field10,stt.getId(),"输入端口3"));
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_4_"+field10,stt.getId(),"输入端口4"));
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_5_"+field10,stt.getId(),"输入端口5"));
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_6_"+field10,stt.getId(),"输入端口6"));
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_7_"+field10,stt.getId(),"输入端口7"));
					tcs.add(new TreeNodeCheckbox(field3+"_"+field4+"_input_8_"+field10,stt.getId(),"输入端口8"));
					
					
					
					stt.setChildren(tcs);
					seconds.add(stt);					
				}	
				/*seconds.add(new TreeNodeCheckbox(field3+"_input_1",ftt.getId(),"输入端口1"));
				seconds.add(new TreeNodeCheckbox(field3+"_input_2",ftt.getId(),"输入端口2"));
				seconds.add(new TreeNodeCheckbox(field3+"_input_3",ftt.getId(),"输入端口3"));
				seconds.add(new TreeNodeCheckbox(field3+"_input_4",ftt.getId(),"输入端口4"));
				seconds.add(new TreeNodeCheckbox(field3+"_input_5",ftt.getId(),"输入端口5"));
				seconds.add(new TreeNodeCheckbox(field3+"_input_6",ftt.getId(),"输入端口6"));
				seconds.add(new TreeNodeCheckbox(field3+"_input_7",ftt.getId(),"输入端口7"));
				seconds.add(new TreeNodeCheckbox(field3+"_input_8",ftt.getId(),"输入端口8"));*/
				ftt.setChildren(seconds);
				fristNodes.add(ftt);				
			}
			ar.isSuccess();
			ar.setArray(fristNodes);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonArrayString();
		}

		return null;
	}

	/***
	 * 查询用户权限 ，根据一层设备地址与用户编号 
	 * 
	 * @return
	 * @throws IOException 
	  
	public String getUserRight() throws IOException{
		String userCode = ServletActionContext.getRequest().getParameter("userCode");
		String firstLayerID = ServletActionContext.getRequest().getParameter("firstLayerID");

		AjaxResult ar = new AjaxResult();
		try {
			if(userCode!=null&&firstLayerID!=null&&NumberUtils.isNumber(userCode.trim())&&NumberUtils.isNumber(firstLayerID.trim())){
			
				int ucVal=NumberUtils.createInteger(userCode.trim()),flidVal=NumberUtils.createInteger(firstLayerID);
			
				Table9 t9=(Table9) baseDao.findOnlyByHql(SqlConfig.SQL("ats.table9.tree.query.by.field2"),new Object[]{flidVal});
				if(t9==null)throw new AtsException("ats-1021");
				TreeNode ftt=new TreeNode();
				int field2=t9.getField2();
				ftt.setParentid("root_001");
				ftt.setId("first_"+field2);
				//设置二层设备
				List<Table10> t10s = baseDao.findObjectsByHql(SqlConfig.SQL("ats.table10.tree.query.byField2.field25-32"), new Object[] {field2,ucVal,ucVal,ucVal,ucVal,ucVal,ucVal,ucVal,ucVal});
				
				List<TreeNode> seconds=new ArrayList<TreeNode>();
				for (int i = 0; i < t10s.size(); i++) {
					TreeNode stt=new TreeNode();
					stt.setId("second_"+t10s.get(i).getField1());
					int field4=t10s.get(i).getField4();
					int field10=t10s.get(i).getField10();
					stt.setParentid(ftt.getId());
					stt.setText(t10s.get(i).getField121());					
					List<TreeNodeCheckbox> tcs=new ArrayList<TreeNodeCheckbox>();
					
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_1_"+field10,stt.getId(),t10s.get(i).getField25().equals(ucVal)));
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_2_"+field10,stt.getId(),t10s.get(i).getField26().equals(ucVal)));
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_3_"+field10,stt.getId(),t10s.get(i).getField27().equals(ucVal)));
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_4_"+field10,stt.getId(),t10s.get(i).getField28().equals(ucVal)));
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_5_"+field10,stt.getId(),t10s.get(i).getField29().equals(ucVal)));
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_6_"+field10,stt.getId(),t10s.get(i).getField30().equals(ucVal)));
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_7_"+field10,stt.getId(),t10s.get(i).getField31().equals(ucVal)));
					tcs.add(new TreeNodeCheckbox(field2+"_"+field4+"_input_8_"+field10,stt.getId(),t10s.get(i).getField32().equals(ucVal)));
					stt.setChildren(tcs);
					seconds.add(stt);					
				}
				//一层设备的权限 
				seconds.add(new TreeNodeCheckbox(field2+"_input_1",ftt.getId(),t9.getField23()!=null&&t9.getField23().equals(ucVal)));
				seconds.add(new TreeNodeCheckbox(field2+"_input_2",ftt.getId(),t9.getField24()!=null&& t9.getField24().equals(ucVal)));
				seconds.add(new TreeNodeCheckbox(field2+"_input_3",ftt.getId(),t9.getField25()!=null&& t9.getField25().equals(ucVal)));
				seconds.add(new TreeNodeCheckbox(field2+"_input_4",ftt.getId(),t9.getField26()!=null&& t9.getField26().equals(ucVal)));
				seconds.add(new TreeNodeCheckbox(field2+"_input_5",ftt.getId(),t9.getField27()!=null&& t9.getField27().equals(ucVal)));
				seconds.add(new TreeNodeCheckbox(field2+"_input_6",ftt.getId(),t9.getField27()!=null&& t9.getField28().equals(ucVal)));
				seconds.add(new TreeNodeCheckbox(field2+"_input_7",ftt.getId(),t9.getField29()!=null&& t9.getField29().equals(ucVal)));
				seconds.add(new TreeNodeCheckbox(field2+"_input_8",ftt.getId(),t9.getField30()!=null&& t9.getField30().equals(ucVal)));
				
				ftt.setChildren(seconds);
				ar.isSuccess();
				ar.setReturnVal(ftt);
			}else{
				ar.isFailed(MsgConfig.msg("ats-1021"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			//ar.writeToJsonArrayString();
			ar.writeToJsonString();
		}

		return null;
	}**/
	/*****
	 * 获取系统设置
	 * @return
	 * @throws IOException
	 */
	public String getSys() throws IOException{
		String firstLayerID = ServletActionContext.getRequest().getParameter("firstLayerID");

		AjaxResult ar = new AjaxResult();
		try {
			if(firstLayerID!=null&&NumberUtils.isNumber(firstLayerID.trim())){
			
				int flidVal=NumberUtils.createInteger(firstLayerID);
				Table10 first=(Table10) baseDao.findOnlyByHql(SqlConfig.SQL("ats.first.query.by.field3"),new Object[]{flidVal,0});
				ar.isSuccess();
				ar.setReturnVal(first);
			}else{
				ar.isFailed(MsgConfig.msg("ats-1021"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonString();
		}

		return null;
	}
	
	/***
	 * 获取缓存的设备信息
	 * @return
	 * @throws IOException 
	 */
	/*public   String getCD2() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		AjaxResult ar = new AjaxResult();
		try {			
			Integer firstLayerID = AtsParameterUtils.getInteger("firstLayerID", request);
			Integer secondLayerID = AtsParameterUtils.getInteger("secondLayerID", request);
			ar.isSuccess();
			ar.setReturnVal(AtsEquipmentCache.queryByAddress(firstLayerID, secondLayerID));			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		} finally {
			ar.writeToJsonString();
		}

		return null;
	
	}*/
	public AjaxResult getResult() {
		return result;
	}

	
	
}
