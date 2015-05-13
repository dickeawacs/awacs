package com.cdk.ats.web.action.contorl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.transmitter.TransmitterUtils;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.action.login.UserInfo;
import com.cdk.ats.web.dao.SystemDao;
import com.cdk.ats.web.dao.Table10DAO;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.pojo.hbm.Table10Bak;
import com.cdk.ats.web.utils.AjaxResult;
import com.cdk.ats.web.utils.AtsParameterUtils;
import common.cdk.config.files.sqlconfig.SqlConfig;

public class SystemManagement {

	private static Logger logger=Logger.getLogger(SystemManagement.class);
	@Autowired
	private Table10DAO t10dao;

	@Autowired
	private SystemDao sysdao;

	public Table10DAO getT10dao() {
		return t10dao;
	}

	public void setT10dao(Table10DAO t10dao) {
		this.t10dao = t10dao;
	}

	public SystemDao getSysdao() {
		return sysdao;
	}

	public void setSysdao(SystemDao sysdao) {
		this.sysdao = sysdao;
	}

	public String sysMngment() {
		return "sysReset";

	}

	/***
	 * 系统数据重置
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetAll() throws IOException {/*

		AjaxResult ar = new AjaxResult();
		List<ActionReady> readys =null;
		ActionParams groupParams =null;
		try {
			int count = sysdao.updateReset();
			List<Table10> t10s = sysdao.findAll();

			int groupKey = TransmitterContext.getSequnce();
			  groupParams = new ActionParams();
			groupParams.setSequnce(groupKey);
			
			  readys = new BatchDatagram(sysdao).PackageDatagram(t10s,groupParams);
			TransmitterContext.pushGroupDatagram(readys);
			if (count > 0) {
				TransmitterUtils.pushGroup(groupParams, readys);		
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
				//ar.isSuccess().setMessage("操作已经成功 ，但需要一定的响应时间 ，请等候...");
			}else{
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有找到设备！");
				TransmitterUtils.distoryGroup(groupParams, readys);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ar.isFailed("数据重置失败！");
			TransmitterUtils.distoryGroup(groupParams, readys);
		} finally {
			ar.writeToJsonString();
		}

	 */
		return null;
		}

	/***
	 * 系统数据重置
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetOne() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		AjaxResult ar = new AjaxResult();
		Integer field3 = -1, field4 = 0;
		ActionParams groupParams=null;//参数组，
		List<ActionReady> readys=null ;
		try {
			if(!checkAdminDev()){
				
				throw new AtsException("当前不是处于编程状态，请重新输入密码!");
			}
			field3 = AtsParameterUtils.getInteger("firstDevice", request);
			field4 =0;
			Table10Bak t10=null;//一层设备 
			Constant.checkUsed(field3,0);
			if(field3!=null){
				// 二层设置如果 没有 ID，则是将整个一层设备 以及其下所有 二层设备 都 重置
				//找到一层设备 
				t10=(Table10Bak) sysdao.findOnlyByHql("from Table10Bak t where t.field3=? and field4=0", new Object[] { field3});
				if(t10!=null&&t10.getFip()!=null&&t10.getFport()!=null){
				//找到所有二层
				List<Table10Bak> seconds=sysdao.findObjectsByHql("from Table10Bak t where t.field3=? and field4>0", new Object[] { field3});
				
				if(seconds!=null){
					t10.getSecond().addAll(seconds);
				}
					//刷新设备树的内容 
					//AtsEquipmentCache.reset(t10);
					/// 封装数据发送包
					List<Table10Bak> stable10s=new ArrayList<Table10Bak>();
					stable10s.add(t10);
					
					int groupKey = TransmitterContext.getSequnce();
					groupParams= new ActionParams();
					groupParams.setOneP(t10.getField3());//在发送包时需要，不能删除
					groupParams.setSequnce(groupKey);
					readys = new BatchDatagram(sysdao).PackageDatagram(stable10s,groupParams);	
					if(readys.size()>0){
					//TransmitterContext.pushGroupDatagram(readys);
					//TransmitterUtils.pushGroup(groupParams, readys);
					//调用特殊的发送器
					TransmitterUtils.pushResetGroup(groupParams, readys);
					ar.isSuccess().setReturnVal(groupKey);
					ar.stateWaiting();
					}else{
						ar.setCode(901);
						ar.setSuccess(true);
						ar.setMessage("系统生成数据包失败");
						TransmitterUtils.distoryGroup(groupParams, readys);
						
					}
				}else 
				{
					//ar.isFailed("没有可用的设备需要重置！");
					ar.setCode(901);
					ar.setSuccess(true);
					ar.setMessage("操作失败，没有在备份数据中找到对应的设备！");
					TransmitterUtils.distoryGroup(groupParams, readys);
				}
			}
		}catch(AtsException e){ 
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			//销毁缓存中的参数 
			TransmitterUtils.distoryGroup(groupParams, readys);			
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			ar.isFailed("数据重置失败！请联系管理员");  
			ar.setExceptionMessage(e.getMessage());
			//销毁缓存中的参数 
			TransmitterUtils.distoryGroup(groupParams, readys);			
			
		} finally {
			ar.writeToJsonString();
		}
		return null;

	}
	
	

	/***
	 * 加载数据包发送数据
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String loadBase() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			 sysdao.saveBaseInfo();
		} catch (Exception e) {
			e.printStackTrace();
			ar.isFailed("数据重置失败！");
		} finally {
			ar.writeToJsonString();
		}
		return null;
	
	}
	
	
	 /***
	 * 系统数据重置
	 * 
	 * @return
	 * @throws IOException
	 */
	/*private String resetOne3() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		AjaxResult ar = new AjaxResult();
		Integer field3 = -1, field4 = 0;
		ActionParams groupParams=null;//参数组，
		List<ActionReady> readys=null ;
		try {
			field3 = AtsParameterUtils.getInteger("firstDevice", request);
			field4 =0;//AtsParameterUtils.getIntegerParam("secondDevice", request);
			Table10 t10=null;//二层设备，或是一层设备嵌套二层设备 
			if(field3!=null){
				// 二层设置如果 没有 ID，则是将整个一层设备 以及其下所有 二层设备 都 重置
				int count=0;
				//List<Table10> t10s =null;
				if(field4==null){ field4=0;
					*//***
					 * 重置一层设备以及其下所有二层设备的数据
					 *//*
					//count= sysdao.updateResetOne(new Object[] { field3});
					if(count>0)
					{
						//找到一层设备 
						t10=(Table10) sysdao.findOnlyByHql("from Table10Bak t where t.field3=? and field4=0", new Object[] { field3});
						//找到所有二层
						List<Table10> seconds=sysdao.findObjectsByHql("from Table10Bak t where t.field3=? and field4>0", new Object[] { field3});
						if(seconds!=null){
							t10.getSecond().addAll(seconds);
						}
					}
					*//**
					 * 查找更新后的数据
					 * *//*
				}
				else {
					*//***
					 * 重置指定的设备
					 *//*
					//count= sysdao.updateResetTwo(new Object[] { field3, field4 });
					 {
					//获取重置后的数据
					 t10=(Table10) sysdao.findOnlyByHql("from Table10 t where t.field3=? and field4=?", new Object[] { field3,field4});
					 
					 //如果field4!=0 即不为二层设备的第一个设备
					 if(!field4.equals(0)){
						 //查出设备所属的一层设备，	
						 Table10 temp10=(Table10) sysdao.findOnlyByHql("from Table10 t where t.field3=? and field4=0", new Object[] { field3});
						 //设置一层设备的地址与端口用于数据包的发送
						 if(temp10!=null){
							 t10.setFip(temp10.getFip());
							 t10.setFport(temp10.getFport());
						  }else {
							  throw new Exception("缺少一层设备，无法执行重置");
							  
						  }
					 }
					}
				}
				// 设备重置不为空。就需要发送数据包与同步 设备树了。
				if (count > 0){
					
					//如果判断为二层设备
					if(!isAll){
						//则只需要更新一台设备的命令
						t10=t10s.get(0);
					}else{
						//一层设备嵌套二层设备
						t10=new Table10();
						//装载二层设备
						for (int i = 0; i < t10s.size(); i++) {
							if(!t10s.get(i).getField4().equals(0)){
								t10.getSecond().add(t10s.get(i));								
							}else{
								t10.setField1(t10s.get(i).getField1());
								t10.setField3(t10s.get(i).getField3());
								t10.setField4(t10s.get(i).getField4());
								t10.setField121(t10s.get(i).getField121());
							}
						}
					}
					//刷新设备树的内容 
					AtsEquipmentCache.reset(t10);
					/// 封装数据发送包
					List<Table10> stable10s=new ArrayList<Table10>();
					stable10s.add(t10);
					
					int groupKey = TransmitterContext.getSequnce();
					groupParams= new ActionParams();
					groupParams.setOneP(t10.getField3());//在发送包时需要，不能删除
					groupParams.setSequnce(groupKey);
					readys = new BatchDatagram(sysdao).PackageDatagram(stable10s,groupParams);	
					//TransmitterContext.pushGroupDatagram(readys);
					//TransmitterUtils.pushGroup(groupParams, readys);
					//调用特殊的发送器
					TransmitterUtils.pushResetGroup(groupParams, readys);
					ar.isSuccess().setReturnVal(groupKey);
					ar.stateWaiting();
				}else 
				{
					//ar.isFailed("没有可用的设备需要重置！");
					ar.setCode(901);
					ar.setSuccess(true);
					ar.setMessage("操作失败，没有找到设备！");
					TransmitterUtils.distoryGroup(groupParams, readys);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ar.isFailed("数据重置失败！请联系管理员");  
			ar.setExceptionMessage(e.getMessage());
			//销毁缓存中的参数 
			TransmitterUtils.distoryGroup(groupParams, readys);			
			
		} finally {
			ar.writeToJsonString();
		}
		return null;

	}*/
	/***
	 * 一层设备combo ， 只 获取所有的一层设备
	 * @return
	 * @throws IOException
	 */
 	public String first() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
				List<Object[]> firstObjs = sysdao.findObjectsByHql(SqlConfig.SQL("ats.reset.tree.first.all"));
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
 	 * 
 	 * 描述：  管理员重新输入密码 
 	 * @createBy dingkai
 	 * @createDate 2014-1-9
 	 * @lastUpdate 2014-1-9
 	 * @return
 	 * @throws IOException
 	 */
 	public String reLoginAdmin() throws IOException{
		AjaxResult ar = new AjaxResult();
		try {
			HttpServletRequest  request=ServletActionContext.getRequest();
			String pwd=request.getParameter("adminPwd");
			if(!StringUtils.isEmpty(pwd)&&pwd.length()==8){
				Object temp=ServletActionContext.getRequest().getSession().getAttribute("UserInfo");
				if(temp!=null){ 
					UserInfo userInfo=(UserInfo)temp;
					if(sysdao.reloginAdmin(userInfo, pwd)){
						userInfo.setDev(1);
						ServletActionContext.getRequest().getSession().setAttribute("UserInfo",userInfo);
						ar.isSuccess();
					}else{
						ar.isFailed("密码验证失败！");	
					}
				}else{
					ar.isFailed("密码验证失败！");
				}
			}else{
				ar.isFailed("密码验证失败！");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ar.isFailed("密码验证失败！");
		} finally {
			ar.writeToJsonString();
		}
		return null;
	
	}
 	
 	private boolean checkAdminDev(){
 		boolean isDev=false;
 		try{
 		Object temp=ServletActionContext.getRequest().getSession().getAttribute("UserInfo");
		if(temp!=null){ 
			UserInfo userInfo=(UserInfo)temp;
			if(userInfo.getDev()==1){
				isDev=true;
			}
		}
 		}catch (Exception e) {
			logger.error(e.getMessage());
		}
 		return isDev;
 		
 	}
}
