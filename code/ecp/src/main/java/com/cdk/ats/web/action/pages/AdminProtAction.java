package com.cdk.ats.web.action.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionProcess;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.AjaxResult;
import common.cdk.config.files.msgconfig.MsgConfig;
import common.cdk.config.files.sqlconfig.SqlConfig;



/***
 * 
 * @author cdk
 *管理员的专用类（二）---端口设置 
 *用于管理的一些操作功能
 */
public class AdminProtAction {
	private Logger logger=Logger.getLogger(AdminProtAction.class);
	private BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	/***
	 * 输入端口信息保存
	 * @return
	 * @throws IOException
	 */
	public String   inPort()throws IOException{
		AjaxResult ar=new AjaxResult();
		  ActionParams groupParams=null;
		  List<ActionReady> actionReadys=null;
		  int groupKey=-1,cup=0;//统计需要直接操作数据库的次数,或是记录影响数据库的行数
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String	firstDevice	=	request.getParameter("firstDevice");
			int     firDevVal=-1;
			String	secondDevice	=	request.getParameter("secondDevice");
			int     secDevVal=-1;
			
			/***端口信息***************/
			
			//输入端口标记
			String	inputNumTag	=	request.getParameter("inputNumTag");
			int tag=0;
			//输入名称
			String	inputName	=	request.getParameter("inputName");
			//输入属性   [1.屏蔽输入。			2.普通输入。			3.立即防区。			4.24小时防区。 ]
			String	inputP	=	request.getParameter("inputP");
			int inputPV=-1;
			//对应二级用户
			String	userId	=	request.getParameter("userId");
			int userCode=-1;
			//触发提示音
			String  trigger=request.getParameter("trigger");
			Integer triggerV=1;
			//恢复提示音
			String  recovery=request.getParameter("recovery");
			Integer recoveryV=2;
			
			/**交叉信息集合**/
			//交叉输入 一层设备标号:
			String  targetFirstID=request.getParameter("targetFirstID");
			//交叉输入 二层设备标号:
			String  targetSecondID=request.getParameter("targetSecondID");
			//交叉输入端口:
			String targetInPort=request.getParameter("targetInPort");
			//交叉禁用标记：
			String targetDesable=request.getParameter("targetDisable");
			int targetDV=0;
			
			/***  end 端口信息***************/
		
			if(trigger!=null&&NumberUtils.isNumber(trigger.trim())) triggerV=NumberUtils.createInteger(trigger);
			if(recovery!=null&&NumberUtils.isNumber(recovery.trim())) recoveryV=NumberUtils.createInteger(recovery);
			
			if(firstDevice==null||!NumberUtils.isNumber(firstDevice.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
				else firDevVal=NumberUtils.createInteger(firstDevice);
			if(secondDevice==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(secondDevice)))throw new AtsException(MsgConfig.msg("ats-1010"));
				else secDevVal=NumberUtils.createInteger(StringUtils.deleteWhitespace(secondDevice));
			if( !HeartContext.hasHeart(CommandTools.handKeyFormat(firDevVal,0)))throw new AtsException("设备可能已经掉线,无法操作！");
			if(inputName==null)throw new AtsException(MsgConfig.msg("ats-1010"));
			else if(inputName.getBytes("GBK").length>12){
				throw new AtsException("输入名称不得超过12个字（一个中文占2字，英文点一个）！");
			}
			else inputName=StringUtils.deleteWhitespace(inputName);
			if(inputP==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(inputP)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else inputPV=NumberUtils.createInteger(StringUtils.deleteWhitespace(inputP));
			if(userId==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(userId)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else userCode=NumberUtils.createInteger(StringUtils.deleteWhitespace(userId));
			if(targetSecondID==null)throw new AtsException(MsgConfig.msg("ats-1010"));
			else targetSecondID=StringUtils.deleteWhitespace(targetSecondID);
			if(targetDesable!=null&&NumberUtils.isNumber(StringUtils.deleteWhitespace(targetDesable))){
				targetInPort="0";
			} 
			if(inputNumTag==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(inputNumTag)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else tag=NumberUtils.createInteger(StringUtils.deleteWhitespace(inputNumTag));
			if(tag!=1&&tag!=2&&tag!=4&&tag!=8&&tag!=16&&tag!=32&&tag!=64&&tag!=128)throw new AtsException(MsgConfig.msg("ats-1010"));
			//当禁止交叉项被选中时，则将交叉目标输入端口设置为0;
			//if(targetDV==1){targetInPort="0";}	
			//判断是设备是否已经被屏蔽或是已经掉线
			Constant.checkUsed(firDevVal,0);
			//Table9   t9=(Table9)   baseDao.findOnlyByHql(SqlConfig.SQL("ats.table9.query.saveBeforQuery"),new Object[]{firDevVal});
			Table10  t10=(Table10) baseDao.findOnlyByHql(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{firDevVal,secDevVal});
			if(t10!=null){
			//	System.out.println(tag);
				switch (tag) {
				case 1:
						{
						// 输入端口1相关设置
						actionReadys = new ArrayList<ActionReady>();
						groupKey = TransmitterContext.getSequnce();
						groupParams = new ActionParams();
						// 输入名称
						if (!inputName.equals(t10.getField105())) {
							formate_0x10_0x20(tag, t10, inputName, groupKey,
									groupParams, actionReadys);
						}	
						// 输入属性信息
						if (!Integer.valueOf(inputPV).equals(t10.getField17())) {
							formate_0x10_0x0a(tag, t10, inputPV, groupKey,
									groupParams, actionReadys);
						}
						
						// 输入隶属用户
						if (!Integer.valueOf(userCode).equals(t10.getField25())) {
							formate_0x10_0x1f(tag,t10, userCode, groupKey,
									groupParams, actionReadys);
						}
						//触发提示音
						if(!triggerV.equals(t10.getField122())) 
							cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field122"),new Object[]{triggerV,t10.getField3(),t10.getField4()});

						/**恢复提示音**/
						if(!recoveryV.equals(t10.getField131())) 
							cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field131"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});
						
						/*******************************************/
						// 交叉输入 信息
						if (!StringUtils.deleteWhitespace(
								targetFirstID + "," + targetSecondID + ","
										+ targetInPort).equals(t10.getField33())) {
							formate_0x10_0x02(tag,  t10, inputPV, groupKey,
									groupParams, actionReadys, targetInPort,
									targetFirstID, targetSecondID);
						}
					
					}
					
					break;
				case 2:
				{
					// 输入端口2相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!inputName.equals(t10.getField106())) {
						formate_0x10_0x20(tag, t10, inputName, groupKey,
								groupParams, actionReadys);
					}	
					// 输入属性信息
					if (!Integer.valueOf(inputPV).equals(t10.getField18())) {
						formate_0x10_0x0a(tag, t10, inputPV, groupKey,
								groupParams, actionReadys);
					}
					
					// 输入隶属用户
					if (!Integer.valueOf(userCode).equals(t10.getField26())) {
						formate_0x10_0x1f(tag,  t10, userCode, groupKey,
								groupParams, actionReadys);
					}
					//触发提示音
					if(!triggerV.equals(t10.getField123()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field123"),new Object[]{triggerV,t10.getField3(),t10.getField4()});	
					/**恢复提示音**/	
					if(!recoveryV.equals(t10.getField132()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field132"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});
					
					
					/*******************************************/
					// 交叉输入 信息
					if (!StringUtils.deleteWhitespace(
							targetFirstID + "," + targetSecondID + ","
									+ targetInPort).equals(t10.getField34())) {
						formate_0x10_0x02(tag,  t10, inputPV, groupKey,
								groupParams, actionReadys, targetInPort,
								targetFirstID, targetSecondID);
					}
			 
				}
					break;
				case 4:
				{
					// 输入端口3相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!inputName.equals(t10.getField107())) {
						formate_0x10_0x20(tag,t10, inputName, groupKey,
								groupParams, actionReadys);
					}	
					// 输入属性信息
					if (!Integer.valueOf(inputPV).equals(t10.getField19())) {
						formate_0x10_0x0a(tag,t10, inputPV, groupKey,
								groupParams, actionReadys);
					}
					
					// 输入隶属用户
					if (!Integer.valueOf(userCode).equals(t10.getField27())) {
						formate_0x10_0x1f(tag, t10, userCode, groupKey,
								groupParams, actionReadys);
					}
					
					//触发提示音
					if(!triggerV.equals(t10.getField124()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field124"),new Object[]{triggerV,t10.getField3(),t10.getField4()});
					/**恢复提示音**/
					if(!recoveryV.equals(t10.getField133()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field133"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});
					
					/*******************************************/
					// 交叉输入 信息
					if (!StringUtils.deleteWhitespace(
							targetFirstID + "," + targetSecondID + ","
									+ targetInPort).equals(t10.getField35())) {
						formate_0x10_0x02(tag, t10, inputPV, groupKey,
								groupParams, actionReadys, targetInPort,
								targetFirstID, targetSecondID);
					}
			 
				}
					break;
				case 8:
				{
					// 输入端口4相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!inputName.equals(t10.getField108())) {
						formate_0x10_0x20(tag,t10, inputName, groupKey,
								groupParams, actionReadys);
					}	
					// 输入属性信息
					if (!Integer.valueOf(inputPV).equals(t10.getField20())) {
						formate_0x10_0x0a(tag,t10, inputPV, groupKey,
								groupParams, actionReadys);
					}
					
					// 输入隶属用户
					if (!Integer.valueOf(userCode).equals(t10.getField28())) {
						formate_0x10_0x1f(tag, t10, userCode, groupKey,
								groupParams, actionReadys);
					}
					
					//触发提示音
					if(!triggerV.equals(t10.getField125()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field125"),new Object[]{triggerV,t10.getField3(),t10.getField4()});	
					/**恢复提示音**/	
					if(!recoveryV.equals(t10.getField134()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field134"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});
					
					/*******************************************/
					// 交叉输入 信息
					if (!StringUtils.deleteWhitespace(
							targetFirstID + "," + targetSecondID + ","
									+ targetInPort).equals(t10.getField36())) {
						formate_0x10_0x02(tag, t10, inputPV, groupKey,
								groupParams, actionReadys, targetInPort,
								targetFirstID, targetSecondID);
					}
			 
				}
					break;
				case 16:
				{
					// 输入端口5相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!inputName.equals(t10.getField109())) {
						formate_0x10_0x20(tag,t10, inputName, groupKey,
								groupParams, actionReadys);
					}	
					// 输入属性信息
					if (!Integer.valueOf(inputPV).equals(t10.getField21())) {
						formate_0x10_0x0a(tag,t10, inputPV, groupKey,
								groupParams, actionReadys);
					}
					
					// 输入隶属用户
					if (!Integer.valueOf(userCode).equals(t10.getField29())) {
						formate_0x10_0x1f(tag, t10, userCode, groupKey,
								groupParams, actionReadys);
					}
					
					//触发提示音
					if(!triggerV.equals(t10.getField126()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field126"),new Object[]{triggerV,t10.getField3(),t10.getField4()});
					/**恢复提示音**/
					if(!recoveryV.equals(t10.getField135()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field135"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});
					
					/*******************************************/
					// 交叉输入 信息
					if (!StringUtils.deleteWhitespace(
							targetFirstID + "," + targetSecondID + ","
									+ targetInPort).equals(t10.getField37())) {
						formate_0x10_0x02(tag, t10, inputPV, groupKey,
								groupParams, actionReadys, targetInPort,
								targetFirstID, targetSecondID);
					}
			 
				}
					break;
				case 32:
				{
					// 输入端口6相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!inputName.equals(t10.getField110())) {
						formate_0x10_0x20(tag,t10, inputName, groupKey,
								groupParams, actionReadys);
					}	
					// 输入属性信息
					if (!Integer.valueOf(inputPV).equals(t10.getField22())) {
						formate_0x10_0x0a(tag,t10, inputPV, groupKey,
								groupParams, actionReadys);
					}
					
					// 输入隶属用户
					if (!Integer.valueOf(userCode).equals(t10.getField30())) {
						formate_0x10_0x1f(tag, t10, userCode, groupKey,
								groupParams, actionReadys);
					}
					
					//触发提示音
					if(!triggerV.equals(t10.getField127()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field127"),new Object[]{triggerV,t10.getField3(),t10.getField4()});					
					/**恢复提示音**/
					if(!recoveryV.equals(t10.getField136()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field136"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});
					
					/*******************************************/
					// 交叉输入 信息
					if (!StringUtils.deleteWhitespace(
							targetFirstID + "," + targetSecondID + ","
									+ targetInPort).equals(t10.getField38())) {
						formate_0x10_0x02(tag, t10, inputPV, groupKey,
								groupParams, actionReadys, targetInPort,
								targetFirstID, targetSecondID);
					}
			 
				}
					break;
				case 64:
				{
					// 输入端口7相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!inputName.equals(t10.getField111())) {
						formate_0x10_0x20(tag,t10, inputName, groupKey,
								groupParams, actionReadys);
					}	
					// 输入属性信息
					if (!Integer.valueOf(inputPV).equals(t10.getField23())) {
						formate_0x10_0x0a(tag,t10, inputPV, groupKey,
								groupParams, actionReadys);
					}
					
					// 输入隶属用户
					if (!Integer.valueOf(userCode).equals(t10.getField31())) {
						formate_0x10_0x1f(tag, t10, userCode, groupKey,
								groupParams, actionReadys);
					}
					
					//触发提示音
					if(!triggerV.equals(t10.getField128()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field128"),new Object[]{triggerV,t10.getField3(),t10.getField4()});	
					/**恢复提示音**/	
					if(!recoveryV.equals(t10.getField137()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field137"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});
					
					/*******************************************/
					// 交叉输入 信息
					if (!StringUtils.deleteWhitespace(
							targetFirstID + "," + targetSecondID + ","
									+ targetInPort).equals(t10.getField39())) {
						formate_0x10_0x02(tag, t10, inputPV, groupKey,
								groupParams, actionReadys, targetInPort,
								targetFirstID, targetSecondID);
					}
			 
				}
					break;
				case 128:
				{
					// 输入端口8相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!inputName.equals(t10.getField112())) {
						formate_0x10_0x20(tag,t10, inputName, groupKey,
								groupParams, actionReadys);
					}	
					// 输入属性信息
					if (!Integer.valueOf(inputPV).equals(t10.getField24())) {
						formate_0x10_0x0a(tag,t10, inputPV, groupKey,
								groupParams, actionReadys);
					}
					
					// 输入隶属用户
					if (!Integer.valueOf(userCode).equals(t10.getField32())) {
						formate_0x10_0x1f(tag, t10, userCode, groupKey,
								groupParams, actionReadys);
					}
					//触发提示音
					if(!triggerV.equals(t10.getField129()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field129"),new Object[]{triggerV,t10.getField3(),t10.getField4()});
					/**恢复提示音**/	
					if(!recoveryV.equals(t10.getField138()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field138"),new Object[]{recoveryV,t10.getField3(),t10.getField4()});

					/*******************************************/
					// 交叉输入 信息
					if (!StringUtils.deleteWhitespace(
							targetFirstID + "," + targetSecondID + ","
									+ targetInPort).equals(t10.getField40())) {
						formate_0x10_0x02(tag, t10, inputPV, groupKey,
								groupParams, actionReadys, targetInPort,
								targetFirstID, targetSecondID);
					}
			 
				}
					break;
				default:throw new AtsException(MsgConfig.msg("ats-1010"));
				}
				
				
				if(actionReadys.size()>0){
					//将组的父级放入缓存容器
					ActionContext.putValues(groupKey,groupParams);
					//将一个 准备好的数据包组 放入发送器中
					TransmitterContext.pushGroupDatagram(actionReadys);
					ar.isSuccess().setReturnVal(groupKey);
					ar.stateWaiting();
				}else if(cup>0){
					ar.isSuccess().setMessage(MsgConfig.msg("ats-2020"));
				}
				else throw new AtsException("操作失败，没有任何的有效修改！");
			}else throw new AtsException(MsgConfig.msg("ats-1010"));
		}catch (AtsException e) {
			ar.isFailed(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed("");
			ar.setExceptionMessage(e.getMessage());
			if(actionReadys!=null){
				for (int i = 0; i < actionReadys.size(); i++) {
					try{
					ActionContext.distoryParam(actionReadys.get(i).getKey());
					}catch (Exception exc) {
					}
				}
			}
			if(groupKey>0){
				ActionContext.distoryParam(groupKey);
			}
		}finally{
			ar.writeToJsonString();
		}
		return null;
		
	}
	/***
	 * 输入端口信息保存
	 * @return
	 * @throws IOException
	 */
	public String   outPort()throws IOException{
		AjaxResult ar=new AjaxResult();
		  ActionParams groupParams=null;
		  List<ActionReady> actionReadys=null;
		  int groupKey=-1,cup=0;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String	firstDevice	=	request.getParameter("firstDevice");
			Integer     firDevVal=-1;
			String	secondDevice	=	request.getParameter("secondDevice");
			Integer     secDevVal=-1;
			
			String	outName	=	request.getParameter("outName");
			
		/*	String	outP	=	request.getParameter("outP");
			Integer outPV=0;*/
			String	outputNumTag	=	request.getParameter("outputNumTag");
			Integer tag=0;
			String	connSound	=	request.getParameter("connSound");
			Integer connSV=1;
			String	closeSound	=	request.getParameter("closeSound");
			Integer closeSV=1;
			
			/*if(outP==null||!NumberUtils.isNumber(outP.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
			else outPV=NumberUtils.createInteger(outP);*/
			if(outputNumTag==null||!NumberUtils.isNumber(outputNumTag.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
			else tag=NumberUtils.createInteger(outputNumTag);
			if(connSound==null||!NumberUtils.isNumber(connSound.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
			else connSV=NumberUtils.createInteger(connSound);
			if(closeSound==null||!NumberUtils.isNumber(closeSound.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
			else closeSV=NumberUtils.createInteger(closeSound);
			if(outName==null)throw new AtsException(MsgConfig.msg("ats-1010"));
			else outName=StringUtils.deleteWhitespace(outName);
			
			if(firstDevice==null||!NumberUtils.isNumber(firstDevice.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
			else firDevVal=NumberUtils.createInteger(firstDevice);
			if(secondDevice==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(secondDevice)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else secDevVal=NumberUtils.createInteger(StringUtils.deleteWhitespace(secondDevice));
			if( !HeartContext.hasHeart(CommandTools.handKeyFormat(firDevVal,0)))throw new AtsException("设备可能已经掉线,无法操作！");
			//判断是设备是否已经被屏蔽或是已经掉线
			Constant.checkUsed(firDevVal,0);
			//Table9   t9=(Table9)   baseDao.findOnlyByHql(SqlConfig.SQL("ats.table9.query.saveBeforQuery"),new Object[]{firDevVal});
			Table10  t10=(Table10) baseDao.findOnlyByHql(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{firDevVal,secDevVal});
			if(t10!=null){
				switch (tag) {
				case 1:
						{
						// 输入端口1相关设置
						actionReadys = new ArrayList<ActionReady>();
						groupKey = TransmitterContext.getSequnce();
						groupParams = new ActionParams();
						
						// 输入名称
						if (!t10.getField113().equals(outName)) {
							formate_0x10_0x21(tag,t10, outName, groupKey,
									groupParams, actionReadys);
						}	
					 
						
					 
						//闭合提示音
						if(!connSV.equals(t10.getField139())) 
							cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field139"),new Object[]{connSV,t10.getField3(),t10.getField4()});

						/**断开提示音**/
						if(!closeSV.equals(t10.getField147())) 
							cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field147"),new Object[]{closeSV,t10.getField3(),t10.getField4()});
					 
					
					}
					
					break;
				case 2:
				{
					// 输入端口2相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!t10.getField114().equals(outName)) {
						formate_0x10_0x21(tag,t10, outName, groupKey,
								groupParams, actionReadys);
					}	
				 
					//闭合提示音
					if(!connSV.equals(t10.getField140()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field140"),new Object[]{connSV,t10.getField3(),t10.getField4()});	
					/**断开提示音**/	
					if(!closeSV.equals(t10.getField148()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field148"),new Object[]{closeSV,t10.getField3(),t10.getField4()});
					 
			 
				}
					break;
				case 4:
				{
					// 输入端口3相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!t10.getField115().equals(outName)) {
						formate_0x10_0x21(tag,t10, outName, groupKey,
								groupParams, actionReadys);
					}	
					 
					//闭合提示音
					if(!connSV.equals(t10.getField141()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field141"),new Object[]{connSV,t10.getField3(),t10.getField4()});
					/**断开提示音**/
					if(!closeSV.equals(t10.getField149()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field149"),new Object[]{closeSV,t10.getField3(),t10.getField4()});
					
				 
			 
				}
					break;
				case 8:
				{
					// 输入端口4相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!t10.getField116().equals(outName)) {
						formate_0x10_0x21(tag,t10, outName, groupKey,
								groupParams, actionReadys);
					}	
					 
					
					//闭合提示音
					if(!connSV.equals(t10.getField142()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field142"),new Object[]{connSV,t10.getField3(),t10.getField4()});	
					/**断开提示音**/	
					if(!closeSV.equals(t10.getField150()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field150"),new Object[]{closeSV,t10.getField3(),t10.getField4()});
					 
			 
				}
					break;
				case 16:
				{
					// 输入端口5相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!t10.getField117().equals(outName)) {
						formate_0x10_0x21(tag,t10, outName, groupKey,
								groupParams, actionReadys);
					}	
					
					//闭合提示音
					if(!connSV.equals(t10.getField143()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field143"),new Object[]{connSV,t10.getField3(),t10.getField4()});
					/**断开提示音**/
					if(!closeSV.equals(t10.getField151()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field151"),new Object[]{closeSV,t10.getField3(),t10.getField4()});
					
				 
			 
				}
					break;
				case 32:
				{
					// 输入端口6相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!t10.getField118().equals(outName)) {
						formate_0x10_0x21(tag,t10, outName, groupKey,
								groupParams, actionReadys);
					}	
					
					
					//闭合提示音
					if(!connSV.equals(t10.getField144()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field144"),new Object[]{connSV,t10.getField3(),t10.getField4()});					
					/**断开提示音**/
					if(!closeSV.equals(t10.getField152()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field152"),new Object[]{closeSV,t10.getField3(),t10.getField4()});
					
					 
			 
				}
					break;
				case 64:
				{
					// 输入端口7相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!t10.getField119().equals(outName)) {
						formate_0x10_0x21(tag,t10, outName, groupKey,
								groupParams, actionReadys);
					}	
				
					
				 
					//闭合提示音
					if(!connSV.equals(t10.getField145()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field145"),new Object[]{connSV,t10.getField3(),t10.getField4()});	
					/**断开提示音**/	
					if(!closeSV.equals(t10.getField153()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field153"),new Object[]{closeSV,t10.getField3(),t10.getField4()});
					
				 
			 
				}
					break;
				case 128:
				{
					// 输入端口8相关设置
					actionReadys = new ArrayList<ActionReady>();
					groupKey = TransmitterContext.getSequnce();
					groupParams = new ActionParams();
					// 输入名称
					if (!t10.getField120().equals(outName)) {
						formate_0x10_0x21(tag,t10, outName, groupKey,
								groupParams, actionReadys);
					}	 
					
				 
					//闭合提示音
					if(!connSV.equals(t10.getField146()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field146"),new Object[]{connSV,t10.getField3(),t10.getField4()});
					/**断开提示音**/	
					if(!closeSV.equals(t10.getField154()))	cup+=baseDao.updateByHql(SqlConfig.SQL("ats.table10.update.field154"),new Object[]{closeSV,t10.getField3(),t10.getField4()});

				 
			 
				}
					break;
				default:throw new AtsException(MsgConfig.msg("ats-1010"));
				}
				
				
				if(actionReadys.size()>0){
					//将组的父级放入缓存容器
					ActionContext.putValues(groupKey,groupParams);
					//将一个 准备好的数据包组 放入发送器中
					TransmitterContext.pushGroupDatagram(actionReadys);
					ar.isSuccess().setReturnVal(groupKey);
					ar.stateWaiting();
				}else if(cup>0){
					ar.isSuccess().setMessage(MsgConfig.msg("ats-2020"));
				}
				else throw new AtsException("操作失败，没有任何的有效修改！");
			}else throw new AtsException(MsgConfig.msg("ats-1010"));
		}catch (AtsException e) {
			ar.isFailed(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed("");
			ar.setExceptionMessage(e.getMessage());
			if(actionReadys!=null){
				for (int i = 0; i < actionReadys.size(); i++) {
					try{
					ActionContext.distoryParam(actionReadys.get(i).getKey());
					}catch (Exception exc) {
					}
				}
			}
			if(groupKey>0){
				ActionContext.distoryParam(groupKey);
			}
		}finally{
			ar.writeToJsonString();
		}
		return null;
		
	}
	/***
	 * 联动端口信息保存
	 * @return
	 * @throws IOException
	 */
	public String   cascadePort()throws IOException{
		AjaxResult ar=new AjaxResult();
		  ActionParams groupParams=null;
		  List<ActionReady> actionReadys=null;
		  int groupKey=-1 ;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String	firstDevice	=	request.getParameter("firstDevice");
			Integer     firDevVal=-1;
			String	secondDevice	=	request.getParameter("secondDevice");
			Integer     secDevVal=-1;
			//输入端口标记
			String	inputNumTag	=	request.getParameter("inputNumTag");
			Integer tag=0;
			//联动标记（1-8）
			String cascadeTag=request.getParameter("cascadeTag");
			Integer cascadeTv=-1;
			//联动对象一层设备,  目前默认为当前设备的一层设备号
			//String cascadeFirstCode=request.getParameter("cascadeFirstCode");
			Integer cascadeFCV=0;
			//联动对象二层设备
			String cascadeSecondCode=request.getParameter("cascadeSecondCode");
			Integer cascadeSCV=0;
			//联动对象输出端口
			String cascadeOutPort=request.getParameter("cascadeOutPort");
			Integer cascadeOPV=0;
			//联动对象属性
			String cascadeP=request.getParameter("cascadeP");
			Integer cascadePV=0;
			//禁用联运 
			String cascadeDisable=request.getParameter("cascadeDisable");
			Integer cascadeDV=0;
			//if(cascadeDisable!=null&&cascadeDisable.equals("1"))cascadeOutPort="0";
			
			
			if(firstDevice==null||!NumberUtils.isNumber(firstDevice.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
			else firDevVal=NumberUtils.createInteger(firstDevice);
			
			if(secondDevice==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(secondDevice)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else secDevVal=NumberUtils.createInteger(StringUtils.deleteWhitespace(secondDevice));
			
			if(inputNumTag==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(inputNumTag)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else tag=NumberUtils.createInteger(StringUtils.deleteWhitespace(inputNumTag));
			if(tag!=1&&tag!=2&&tag!=4&&tag!=8&&tag!=16&&tag!=32&&tag!=64&&tag!=128)throw new AtsException(MsgConfig.msg("ats-1010"));
			if(cascadeTag==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(cascadeTag)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else cascadeTv=NumberUtils.createInteger(StringUtils.deleteWhitespace(cascadeTag));
			if(cascadeTv>8||cascadeTv<1)throw new AtsException(MsgConfig.msg("ats-1010")); 
			/*if(cascadeFirstCode==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(cascadeFirstCode)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else cascadeFCV=NumberUtils.createInteger(StringUtils.deleteWhitespace(cascadeFirstCode));*/
			if(cascadeSecondCode==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(cascadeSecondCode)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else cascadeSCV=NumberUtils.createInteger(StringUtils.deleteWhitespace(cascadeSecondCode));
			if(cascadeOutPort==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(cascadeOutPort)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else cascadeOPV=NumberUtils.createInteger(StringUtils.deleteWhitespace(cascadeOutPort));
			if(cascadeP==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(cascadeP)))throw new AtsException(MsgConfig.msg("ats-1010"));
			else cascadePV=NumberUtils.createInteger(StringUtils.deleteWhitespace(cascadeP));
			if(cascadeDisable!=null&&NumberUtils.isNumber(StringUtils.deleteWhitespace(cascadeDisable)))
			 cascadeDV=NumberUtils.createInteger(StringUtils.deleteWhitespace(cascadeDisable));
			
			{
				//默认只联动同一个一层设备下的二层设备,所以默认值 为当前的一层设备号
				cascadeFCV=firDevVal;
				
			}
			//如果禁止联动.则将数据置0
			if(cascadeDV==1){
			cascadeFCV = 0;
			cascadeSCV = 0;
			cascadeOPV = 0;
			cascadePV = 0;
			//cascadeDV = 0;
			}
			
			//判断是设备是否已经被屏蔽或是已经掉线
			Constant.checkUsed(firDevVal,0);
			//Table9   t9=(Table9)   baseDao.findOnlyByHql(SqlConfig.SQL("ats.table9.query.saveBeforQuery"),new Object[]{firDevVal});\
			Table10 parent=AtsEquipmentCache.getEquiReadOnly(firDevVal,0);
			Table10  t10=(Table10) baseDao.findOnlyByHql(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{firDevVal,secDevVal});
			//Table10  t10=AtsEquipmentCache.getEquiReadOnly(firDevVal,secDevVal);
			t10.setFport(parent.getFport());
			t10.setFip(parent.getFip());
		 if(t10!=null){
				/*cascadeFCV=t10.getField3();*/
			//输入1联动//每个输入联动属性有8种，需要分类处理
		   boolean end=false;
			switch (tag) {
			//输入端口1的8个联动输出
			case 1:{
					switch (cascadeTv) {
					case 1:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField41());
						break;
					case 2:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField42());
						break;
					case 3:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField43());
						break;
					case 4:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField44());
						break;
					case 5:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField45());
						break;
					case 6:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField46());
						break;
					case 7:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField47());
						break;
					case 8:
						end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField48());
						break;
					}
				}
				break;
			case 2:
			{
				switch (cascadeTv) {
				case 1:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField49());
					break;
				case 2:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField50());
					break;
				case 3:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField51());
					break;
				case 4:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField52());
					break;
				case 5:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField53());
					break;
				case 6:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField54());
					break;
				case 7:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField55());
					break;
				case 8:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField56());
					break;
				}
			}break;
			case 4:
			{
				switch (cascadeTv) {
				case 1:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField57());
					break;
				case 2:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField58());
					break;
				case 3:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField59());
					break;
				case 4:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField60());
					break;
				case 5:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField61());
					break;
				case 6:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField62());
					break;
				case 7:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField63());
					break;
				case 8:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField64());
					break;
				}
			}break;
			case 8:
			{
				switch (cascadeTv) {
				case 1:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField65());
					break;
				case 2:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField66());
					break;
				case 3:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField67());
					break;
				case 4:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField68());
					break;
				case 5:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField69());
					break;
				case 6:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField70());
					break;
				case 7:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField71());
					break;
				case 8:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField72());
					break;
				}
			}break;
			case 16:
			{
				switch (cascadeTv) {
				case 1:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField73());
					break;
				case 2:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField74());
					break;
				case 3:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField75());
					break;
				case 4:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField76());
					break;
				case 5:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField77());
					break;
				case 6:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField78());
					break;
				case 7:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField79());
					break;
				case 8:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField80());
					break;
				}
			}break;
			case 32:
			{
				switch (cascadeTv) {
				case 1:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField81());
					break;
				case 2:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField82());
					break;
				case 3:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField83());
					break;
				case 4:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField84());
					break;
				case 5:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField85());
					break;
				case 6:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField86());
					break;
				case 7:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField87());
					break;
				case 8:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField88());
					break;
				}
			}break;
			case 64:
			{
				switch (cascadeTv) {
				case 1:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField89());
					break;
				case 2:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField90());
					break;
				case 3:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField91());
					break;
				case 4:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField92());
					break;
				case 5:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField93());
					break;
				case 6:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField94());
					break;
				case 7:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField95());
					break;
				case 8:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField96());
					break;
				}
			}break;
			case 128:
			{
				switch (cascadeTv) {
				case 1:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField97());
					break;
				case 2:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField98());
					break;
				case 3:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField99());
					break;
				case 4:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField100());
					break;
				case 5:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField101());
					break;
				case 6:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField102());
					break;
				case 7:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField103());
					break;
				case 8:
					end=!StringUtils.deleteWhitespace(cascadeFCV + "," + cascadeSCV + ","+ cascadeOPV + "," + cascadePV+","+cascadeDV).equals(t10.getField104());
					break;
				}
			}break;
			
			}
			if(end){
				actionReadys = new ArrayList<ActionReady>();
				groupKey = TransmitterContext.getSequnce();
				groupParams = new ActionParams();
				
				 
				//如果禁止联动.则将数据置0
			/*	if(cascadeDV==1){
				cascadeFCV = 0;
				cascadeSCV = 0;
				cascadeOPV = 0;
				cascadePV = 0;
				cascadeDV = 0;
				}*/
				
			formate_0x10_0x04(tag,cascadeTv, t10, groupKey, groupParams, actionReadys, cascadePV, cascadeFCV, cascadeSCV, cascadeOPV);
			}else  throw new AtsException("没有需要修改的数据!");
			if(actionReadys!=null&&actionReadys.size()>0){
				//将组的父级放入缓存容器
				ActionContext.putValues(groupKey,groupParams);
				//将一个 准备好的数据包组 放入发送器中
				TransmitterContext.pushGroupDatagram(actionReadys);
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
			} else throw new AtsException("操作失败，没有任何的有效修改！");
		}else throw new AtsException(MsgConfig.msg("ats-1010"));
		
			 
		}catch (AtsException e) {
			ar.isFailed(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			ar.isFailed("");
			ar.setExceptionMessage(e.getMessage());
			if(actionReadys!=null){
				for (int i = 0; i < actionReadys.size(); i++) {
					try{
					ActionContext.distoryParam(actionReadys.get(i).getKey());
					}catch (Exception exc) {
					}
				}
			}
			if(groupKey>0){
				ActionContext.distoryParam(groupKey);
			}
		}finally{
			ar.writeToJsonString();
		}
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
 /***
  * 下传输入属性信息
  * @param tag
  * @param 
  * @param t9
  * @param t10
  * @param inputPV
  * @param groupKey
  * @param groupParams
  * @param actionReadys
  */
	private void formate_0x10_0x0a(int tag,  Table10 t10,
			int inputPV, int groupKey, ActionParams groupParams,
			List<ActionReady> actionReadys) {
			if(inputPV<0)return ;
			ActionParams params = new ActionParams();
			params.setCode(300);
			params.setOneP(t10.getField3());
			params.setOnePT(t10.getOneType());
			params.setTwoP(t10.getField4());
			params.setTwoPT(t10.getField10());
			
			params.setComand0(tag);//对应的输入端口1 2 4 8 16 32 64 128
			params.setComand1(inputPV);//下传的数据 ：输入属性			[1.屏蔽输入。			2.普通输入。			3.立即防区。			4.24小时防区。 ]
			
			params.setComand5(2);//标记设备是一层（1）还是二层（2）
			ActionProcess ap = new ActionProcess();
			ap.setParams(params);// 设置参数
			ActionReady aReady = ap.ox10_0x0a();
			params.setGroupID(groupKey);
			groupParams.getChildrens().put(aReady.getKey(), false);
			ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
			Constant.getInstance().bindTarget(t10, aReady);
			actionReadys.add(aReady);
		
	}
	
	/***
	 * 
	 * 修改二层设备的  输入端口 的 输入名称
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param inputName
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @throws Exception
	 */
	private void formate_0x10_0x20(int tag,Table10 t10,String inputName,int groupKey,ActionParams groupParams,List<ActionReady> actionReadys)throws Exception{
	
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		params.setData0(inputName);//下传的数据 ：输入名称
		params.setComand0(tag);//对应的输入端口1 2 4 8 16 32 64 128
		params.setComand5(2);//标记设备是一层（1）还是二层（2）
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x20();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		Constant.getInstance().bindTarget(t10, aReady);
		actionReadys.add(aReady);
	}
	/***
	 * 设置对应的二级用户
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param userCode
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @throws Exception
	 */
	private void formate_0x10_0x1f(int tag, Table10 t10,int userCode,int groupKey,ActionParams groupParams,List<ActionReady> actionReadys)throws Exception{

		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		params.setUserCode(userCode);//用户	
		params.setComand0(tag);//输入1
		
		params.setComand5(2);//标记设备是一层（1）还是二层（2）
		
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x1f();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		Constant.getInstance().bindTarget(t10, aReady);
		actionReadys.add(aReady);
	
		
	}
	/***
	 * 交叉设置
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param inputPV
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @param targetInPort
	 * @param targetFirstID
	 * @param targetSecondID
	 * @throws Exception
	 */
	private void formate_0x10_0x02(int tag,Table10 t10,int inputPV,int groupKey,ActionParams groupParams,List<ActionReady> actionReadys,String targetInPort,String targetFirstID,String targetSecondID) throws Exception{
		int tfid=0,tsid=0,tip=0;
		if(targetInPort==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(targetInPort)))throw new AtsException(MsgConfig.msg("ats-1010"));
		else tip=NumberUtils.createInteger(StringUtils.deleteWhitespace(targetInPort));
		
	
		
		if(!(tip>=0&&tip<=8)){			throw new AtsException(MsgConfig.msg("ats-1010"));}
		//跨一层设备情况 
		/*if(targetFirstID==null||!NumberUtils.isNumber(targetFirstID.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
		else tfid=NumberUtils.createInteger(targetFirstID);*/
		tfid=t10.getField3();
		
		if(targetSecondID==null||!NumberUtils.isNumber(targetSecondID.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
		else tsid=NumberUtils.createInteger(targetSecondID);

		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		
		params.setComand0(tag);//当前输入端口 
		params.setComand1(tfid);//交叉一层设备号
		params.setComand2(tsid);//交叉二层设备号
		params.setComand3(tip);//交叉输入端口  如果被禁用则传0
		
		params.setComand5(2);//标记设备是一层（1）还是二层（2）
		
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x02();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		Constant.getInstance().bindTarget(t10, aReady);
		actionReadys.add(aReady);
	}
	/***
	 * 下传输入联动信息
	 * @param 
	 * @param t9
	 * @param t10
	 * @param inputPV
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @param cascadeOPV
	 * @param cascadeFCV
	 * @param cascadeSCV
	 * @param cascadePV
	 * @throws Exception
	 */
	private void formate_0x10_0x04(int tag,int cascadeTag,Table10 t10,int groupKey,ActionParams groupParams,List<ActionReady> actionReadys,int cascadePV,int cascadeFCV,int cascadeSCV,int cascadeOPV) throws Exception{
		
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType()!=null?t10.getOneType():0);
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		params.setComand0(tag);//当前的输入端口
		params.setComand1(cascadeTag);//输出联动设置选择字节
		params.setComand2(cascadeFCV);//一层设备号
		params.setComand3(cascadeSCV);//二层设备号
		params.setComand4(cascadeOPV);//联动输出端口
		params.setComand6(cascadePV);//联动属性
		
		params.setComand5(2);////标记设备是一层（1）还是二层（2）
		
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x04();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		Constant.getInstance().bindTarget(t10, aReady);
		actionReadys.add(aReady);
	}

	/***
	 * 设置  输出端口名称
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param outName
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @throws Exception
	 */
	private void formate_0x10_0x21(int tag, Table10 t10,String outName,int groupKey,ActionParams groupParams,List<ActionReady> actionReadys)throws Exception{

		
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		params.setData0(outName);//下传的数据 ：输出名称
		params.setComand0(tag);//对应的输入端口1 2 4 8 16 32 64 128
		params.setComand5(2);//标记设备是一层（1）还是二层（2）
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x21();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		Constant.getInstance().bindTarget(t10,aReady);
		actionReadys.add(aReady);
		
	}
	/***
	 * 设置  输出端口属性
	 */
	/*private void formate_outputPp(){
		
	}*/
}
