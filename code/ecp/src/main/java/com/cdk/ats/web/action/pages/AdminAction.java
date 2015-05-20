package com.cdk.ats.web.action.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

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
import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.AjaxResult;
import com.cdk.ats.web.utils.AtsParameterUtils;
import common.cdk.config.files.msgconfig.MsgConfig;
import common.cdk.config.files.sqlconfig.SqlConfig;
/***
 * 管理员的专用类（一）
 * 用于管理的一些操作功能
 * @author cdk
 *
 */
public class AdminAction {
	private Logger logger=Logger.getLogger(AdminAction.class);
	private BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public String test0() {
		return "treeTest";
	}
	/***
	 * 打开主界面
	 * @return
	 */
	public String main() {
		return "success";
	}
	
	public String userMain(){
		return "success";
	}
	/***
	 * 
	 * 描述：ats设置
	 * @createBy dingkai
	 * @createDate 2014-4-20
	 * @lastUpdate 2014-4-20
	 * @return
	 */
	public String atssetting(){
		
		return "success";
	}
	/***
	 * 打开端口设置界面
	 * @return
	 */
	public String portseting() {

		/*try{
		List<Object[]> oneP=baseDao.findObjectsByHql(SqlConfig.SQL("ats.table9.tree.query"));
		ServletActionContext.getRequest().setAttribute("oneP",JSONArray.fromObject(oneP).toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}*/
		return "portSeting";
	}
	
	/***
	 * 打开端口设置界面
	 * @return
	 */
	public String sysSeting() {
		return "sysSeting";
	}
	/***
	 * 打开用户设置界面
	 * @return
	 */
	public String userSeting() {
		//try{
		/*List<Object[]> oneP=baseDao.findObjectsByHql(SqlConfig.SQL("ats.table9.tree.query"));
		ServletActionContext.getRequest().setAttribute("oneP",JSONArray.fromObject(oneP).toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}*/
		
		return "userSeting";
	}
	/***
	 * 打开设备输控制界面
	 * @return
	 */
	public String outputHelp() {
		return "outputHelp";
	}
	
	/***
	 * 保存用户权限设置
	 * @return
	 * @throws IOException
	 */
	public String  saveUserSet()throws IOException{
		AjaxResult ar = new AjaxResult();
		List<ActionReady> actionReadys=null;
		int groupKey=-1,upc=0;
		try {
			
			HttpServletRequest request = ServletActionContext.getRequest();
			//table1 的主键 
			String	field1	=	request.getParameter("field1");
			//登录名
			String	field3	=	request.getParameter("field3");	
			//密码
			String	field4	=	request.getParameter("field4");	
			//String	field5	=	request.getParameter("field5");
			//禁用此用户
			String	field6	=	request.getParameter("field6");	
			if(field6==null||field6.isEmpty())field6="0";
			/*** 电话号码 ***/
			String	field7	=	request.getParameter("field7");
			/***
			 *  短信号码 
			 *  ***/
			String	field9	=	request.getParameter("field9");
		/*	
			String field10=request.getParameter("field10");
			if(field10==null||field10.isEmpty())field10="0";
			
			String field8=request.getParameter("field8");
			if(field8==null||field8.isEmpty())field8="0";*/
		  
			/**
			 * 一层设备地址
			 * **/
			String	field11	=	request.getParameter("firstDevice");
			//用户类型[3-普通用户，2-设备管理员]
			String userType=	request.getParameter("userType");
			if(userType==null||field3==null||field4==null||field11==null){
				throw new AtsException("设备，用户名，密码，用户类型 均是必填项.");
			}
			if(userType.equals(""+Constant.ROLE_USER)&&field4.getBytes("GBK").length!=6){
				throw new AtsException("用户密码必须为0至9，a至z，A至Z组成的6位密码");
			}
			if(userType.equals(""+Constant.ROLE_ADMIN)&&field4.getBytes("GBK").length!=8){
				throw new AtsException("用户密码必须为0至9，a至z，A至Z组成的8位密码");
			}
			if(!(userType.equals(""+Constant.ROLE_USER)||userType.equals(""+Constant.ROLE_ADMIN))){
				throw new AtsException("请选择用户类型");
			}
			if(StringUtils.isEmpty(field3)){
				throw new AtsException("用户名不合法，只能是5个中文或是12个数字或字母组合 !");
			}
			int userName_len=CommandTools.getGBKleng(field3);
			if(userName_len<1||userName_len>12){
				throw new AtsException("用户名不合法，只能是5个中文或是12个数字或字母组合 !");
			}
			/**用户用户1（0x01）用户2（0x02）用户3（0x03）用户4（0x04）。。。。。用户10（0x0a）。。。。。。用户16（0x10）**/
			/***
			 * 对应的用户编号 （0x00-0x10）	
			 */
			String	field12	=	request.getParameter("field12");		

			/***
			 * 验证对应的值是否为空
			 */
			Constant.checkUsed(NumberUtils.createInteger(field11), 0);
			Table1  t1=(Table1) baseDao.findById("com.cdk.ats.web.pojo.hbm.Table1", NumberUtils.createInteger(field1));
			if(t1==null){
				t1=new Table1();
				t1.setField11(NumberUtils.createInteger(field11));
				t1.setField12(NumberUtils.createInteger(field12));
				t1.setField3("");
				t1.setField4("");
				t1.setField5(NumberUtils.createInteger(userType));
				t1.setField1((Integer) baseDao.save(t1));
			}
			//Table1 t1=AtsEquipmentCache.getUserReadOnly(one, userNum)
			
			if( !HeartContext.hasHeart(CommandTools.handKeyFormat(t1.getField11(),0)))throw new AtsException("设备可能已经掉线,无法操作！");
			Table10  t10=AtsEquipmentCache.getEquiReadOnly(t1.getField11(),0);
			if(t10.getFip()==null||t10.getFport()==null){
				throw new  AtsException("设备异常，无法获取对应的设备地址！");
			}
			/**
			 * 如果被打上屏蔽标记，则不能再下传信息
			 */
			/*if(t10==null||!t10.getField130().equals(Constant.EQUIPMENT_STATUS_NORMAL)){
				throw new Exception("用户对应的设备已屏蔽，无法保存！");
			}*/
			
			/**一层设备地址**/
			if(!field11.trim().equals(t1.getField11().toString()))throw new Exception(MsgConfig.msg("ats-1010")); 
			
			if(!field12.trim().equals(t1.getField12().toString()))throw new Exception(MsgConfig.msg("ats-1010"));
			
		     actionReadys=new ArrayList<ActionReady>(); 
		     ArrayList<ActionParams> aps=new ArrayList<ActionParams>();
			  groupKey=TransmitterContext.getSequnce();
			ActionParams groupParams=new ActionParams();
			
			
			//登录名,不相同 时，需要先验证是否有重名的情况 
			//下传登录用户名信息		0x10	0x1b
			if(field3!=null&&!field3.trim().equals(t1.getField3())){
				upc++;
				//if(field3.getBytes("GBK").length>10)throw new AtsException("用户名过长,最多只能输入10英文，或5个汉字");
				Object obj=baseDao.findOnlyByHql(SqlConfig.SQL("ats.table1.checkLoginName"),new Object[]{field3.trim(),t1.getField1()});
				if(NumberUtils.createInteger(obj.toString())>0)  throw new AtsException("用户名已存在，请重输.");
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t1.getField11());
				params.setOnePT(t1.getField13()==null?0:t1.getField13());
				params.setUserCode(t1.getField12());
				params.setUserID(t1.getField1());
				
				params.setData0(field3);//下传的数据 ：用户名 /登录名
				
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x1b();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				aps.add(params);
				Constant.getInstance().bindTarget(t10, aReady);
				
				actionReadys.add(aReady);
			}
			 
			
			//密码(只能是数字)
			//12	下传用户密码信息		0x10	0x06
			
			if(field4!=null&&!t1.getField4().equals(field4.trim())){
				/**
				 * 如果加密了，就要进行处理
				 */
				//AtsMD5util.getMD5_16(field4.trim())
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t1.getField11());
				params.setOnePT(t1.getField13()==null?0:t1.getField13());
				params.setUserCode(t1.getField12());
				params.setUserID(t1.getField1());
				params.setData0(field4.trim());//下传的数据 ：登录密码
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady=null;
				if(userType.equals(""+Constant.ROLE_ADMIN)){
					aReady= ap.ox10_0x08();
				}else {
					aReady= ap.ox10_0x06();
				}
				
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				//ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aps.add(params);
				Constant.getInstance().bindTarget(t10, aReady);
				actionReadys.add(aReady);
			}
			 
			/*** 禁用此用户（0/1  1为禁用） ***/
			//32	下传开启/屏蔽用户信息		0x10	0x1a
			if(field6!=null&&NumberUtils.isNumber(field6)&&!NumberUtils.createInteger(field6.trim()).equals(t1.getField6())){
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t1.getField11());
				params.setOnePT(t1.getField13()==null?0:t1.getField13());
				params.setUserCode(t1.getField12());
				params.setUserID(t1.getField1());
				params.setComand0(NumberUtils.createInteger(field6.trim()));//下传的数据 ：开启或屏蔽用户
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x1a();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				//ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aps.add(params);
				Constant.getInstance().bindTarget(t10, aReady);
				actionReadys.add(aReady);
			}
			
			/*** 电话号码 ***/
			//18	下传用户电话号码信息		0x10	0x0c				
			if(field7!=null&&!field7.trim().equals(t1.getField7()!=null?t1.getField7():"")){
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t1.getField11());
				params.setOnePT(t1.getField13()==null?0:t1.getField13());
				params.setUserCode(t1.getField12());
				params.setUserID(t1.getField1());
				
				params.setData0(field7.trim());//下传的数据 ：用户电话号码
				
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x0c();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				//ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aps.add(params);
				Constant.getInstance().bindTarget(t10, aReady);
				actionReadys.add(aReady);
			}
			
			/*** 禁用电话（0/1  1为禁用） ***/
			//24	下传开启/屏蔽电话端口信息		0x10	0x12
			/*if(StringUtils.isNotBlank(field8)&&!NumberUtils.createInteger(field8.trim()).equals(t1.getField8())){
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t1.getField11());
				params.setOnePT(t1.getField13());
				params.setUserCode(t1.getField12());
				params.setUserID(t1.getField1());
				params.setComand0(NumberUtils.createInteger(field8.trim()));//下传的数据 ：下传开启/屏蔽电话端口信息	
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x12();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				//ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aps.add(params);
				Constant.getInstance().bindTarget(t10, aReady);
				actionReadys.add(aReady);
			}
		**/
			/*** 短信号码 ***/
			//20	下传用户短信号码信息		0x10	0x0e
			if(field9!=null&&!field9.trim().equals(t1.getField9()!=null?t1.getField9():"")){
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t1.getField11());
				params.setOnePT(0);
				params.setUserCode(t1.getField12());
				params.setUserID(t1.getField1());
				
				params.setData0(field9.trim());//下传的数据 ：用户电话号码
				
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x0e();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				//ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aps.add(params);
				Constant.getInstance().bindTarget(t10, aReady);
				actionReadys.add(aReady);
			}
			/**用户权限设置
			 * 下传输入所对应2级用户信息		0x10	0x1f
			 * **/
			/* if(tcvs.size()>0){
				 upc++;
				 UserSetFormate.treeCheckValuesFormateTable9(t10,tcvs, t1.getField12());
				 List<Table10> t10s= UserSetFormate.treeCheckValuesFormateTable10(tcvs, t1.getField12());
				 for(int x=0;x<t10s.size();x++){
					ActionParams params = new ActionParams();
					params.setCode(300);
					
					params.setOneP(t1.getField11());
					params.setOnePT(t1.getField13());
					
					params.setTwoP(t10s.get(x).getField4());
					params.setTwoPT(t10s.get(x).getField10());
					
					params.setUserCode(t1.getField12());
					params.setUserID(t1.getField1());
					params.setComand0(t1.getField12());//用户
					
					if(t10s.get(x).getField25()>-1){
						params.setComand0(1);//
					}else if(t10s.get(x).getField26()>-1){
						params.setComand0(2);//
					}else if(t10s.get(x).getField27()>-1){
						params.setComand0(4);//
					}else if(t10s.get(x).getField28()>-1){
						params.setComand0(8);//
					}else if(t10s.get(x).getField29()>-1){
						params.setComand0(16);//
					}else if(t10s.get(x).getField30()>-1){
						params.setComand0(32);//
					}else if(t10s.get(x).getField31()>-1){
						params.setComand0(64);//
					}else if(t10s.get(x).getField32()>-1){
						params.setComand0(128);//
					}else continue;
					params.setComand5(2);//将此设置为2，表示是二层设备的命令
					
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x1f();
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					Constant.getInstance().bindTarget(t10, aReady);
					actionReadys.add(aReady);
				 }
				 
				 for (int i = 8; i < 9; i++) {
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(t1.getField11());
						params.setOnePT(t1.getField13());
					 
						params.setUserCode(t1.getField12());
						params.setUserID(t1.getField1());
						params.setComand0(t1.getField12());//用户
						
						if(t9.getField23()>-1&&i==1){
							params.setComand0(1);//
						}else if(t9.getField24()>-1&&i==2){
							params.setComand0(2);//
						}else if(t9.getField25()>-1&&i==3){
							params.setComand0(4);//
						}else if(t9.getField26()>-1&&i==4){
							params.setComand0(8);//
						}else if(t9.getField27()>-1&&i==5){
							params.setComand0(16);//
						}else if(t9.getField28()>-1&&i==6){
							params.setComand0(32);//
						}else if(t9.getField29()>-1&&i==7){
							params.setComand0(64);//
						}else if(t9.getField30()>-1&&i==8){
							params.setComand0(128);//
						}else continue;
						params.setComand5(1);//将此设置为1，表示是一层设备的命令
						
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x1f();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						aReady.setTargetIp(t10.getFip());
						aReady.setTargetPort(t10.getFport());
						actionReadys.add(aReady);
					 }
				 
			 }
			*/
			
			/*** 禁用短信（0/1  1为禁用） ***/
			//24	下传开启/屏蔽禁用短信		0x10	0x10
			/*if(StringUtils.isNotBlank(field10)&&NumberUtils.isNumber(field10)&&!NumberUtils.createInteger(field10.trim()).equals(t1.getField10())){
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t1.getField11());
				params.setOnePT(t1.getField13());
				params.setUserCode(t1.getField12());
				params.setUserID(t1.getField1());
				
				params.setComand0(NumberUtils.createInteger(field10.trim()));//下传的数据 ：下传开启/屏蔽电话端口信息	
				
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x10();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				//ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aps.add(params);
				Constant.getInstance().bindTarget(t10, aReady);
				actionReadys.add(aReady);
			}*/
			if(upc>0){	
				for (int j = 0; j < aps.size(); j++) {
					ActionContext.putValues( actionReadys.get(j).getKey(), aps.get(j));
				}
				
				
				
			//将组的父级放入缓存容器
			ActionContext.putValues(groupKey,groupParams);
			//将一个 准备好的数据包组 放入发送器中
			TransmitterContext.pushGroupDatagram(actionReadys);
			ar.isSuccess().setReturnVal(groupKey);
			ar.stateWaiting();
			}else {
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有任何的有效修改！");				
			}
		} catch (Exception e) { 
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
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
	 * 测试已通过
	 * 保存设备信息
	 * @return
	 * @throws IOException
	 */
	public String saveDevice() throws IOException{
		AjaxResult ar=new AjaxResult();
		  ActionParams groupParams=null;
		  List<ActionReady> actionReadys=null;
		  int groupKey=-1,upc=0;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String	firstDevice	=	request.getParameter("firstDevice");
			int     firDevVal=-1;
			String	secondDevice	=	request.getParameter("secondDevice");
			int     secDevVal=-1;
			String	deviceName	=	request.getParameter("deviceName");
			String	deviceDisable	=	request.getParameter("deviceDisable");
			int 	devDisVal=1; 
			if(firstDevice==null||!NumberUtils.isNumber(firstDevice.trim()))throw new AtsException(MsgConfig.msg("ats-1010"));
			else firDevVal=NumberUtils.createInteger(firstDevice);
			if(deviceName==null||deviceName.trim().equals(""))throw new AtsException(MsgConfig.msg("ats-1010"));
			else if(deviceName.getBytes("GBK").length>12){
				throw new AtsException("设备名称不得超过12个字（一个中文占2字，英文点一个）！");
			}
			else{ deviceName=StringUtils.deleteWhitespace(deviceName);
			}
			if( !HeartContext.hasHeart(CommandTools.handKeyFormat(firDevVal,0)))throw new AtsException("设备可能已经掉线,无法操作！");
			
			//当屏蔽的值不空为，且为1时，说明勾选屏蔽用框，
			if(deviceDisable!=null&&deviceDisable.equals("1"))
				devDisVal=2;//数据库中的屏蔽值=2
			  	groupKey=TransmitterContext.getSequnce();
			  	groupParams=new ActionParams();
				if(secondDevice==null||!NumberUtils.isNumber(StringUtils.deleteWhitespace(secondDevice)))throw new AtsException(MsgConfig.msg("ats-1010"));
				else secDevVal=NumberUtils.createInteger(StringUtils.deleteWhitespace(secondDevice));
				actionReadys=new ArrayList<ActionReady>();
				Table10   first=(Table10)   baseDao.findOnlyByHql(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{firDevVal,0});
				Table10   second=(Table10)     baseDao.findOnlyByHql(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{firDevVal,secDevVal});
			
				if(!deviceName.equals(StringUtils.deleteWhitespace(second.getField121()))){
					upc++;
					//修改二层设备名称
					ActionParams params = new ActionParams();
					params.setCode(300);
					params.setOneP(firDevVal);
					params.setOnePT(first.getField10());
					params.setTwoP(secDevVal);
					params.setTwoPT(second.getField10());
					params.setData0(deviceName);//下传的数据 ：设备名称
					//设备 类型
					if(second.getField4()==0)
						params.setComand1(1);
					else params.setComand1(2);
					
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x1c();
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					Constant.getInstance().bindTarget(first, aReady);
					actionReadys.add(aReady);
				}
				if(second.getField130()==null)second.setField130(0);
				if(!second.getField130().equals(devDisVal)){
					upc++;
					ActionParams params = new ActionParams();
					params.setCode(300);
					params.setOneP(firDevVal);
					params.setOnePT(first.getField10());
					params.setTwoP(secDevVal);
					params.setTwoPT(second.getField10());
					//屏蔽对象的值 是2
					params.setComand0(devDisVal);//下传的数据 ：禁用设备
					//设备 类型
					if(second.getField4()==0)
						params.setComand1(1);
					else params.setComand1(2);
					
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x1e();
					
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					Constant.getInstance().bindTarget(first, aReady);
					actionReadys.add(aReady);				
				}
			if(upc>0&&groupKey>0&&groupParams!=null&&actionReadys!=null&&actionReadys.size()>0){		
				//将组的父级放入缓存容器
				ActionContext.putValues(groupKey,groupParams);
				//将一个 准备好的数据包组 放入发送器中
				TransmitterContext.pushGroupDatagram(actionReadys);
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
			}else {
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有任何的有效修改！");				
			}
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
	 * 保存设置系统设置
	 * @return
	 * @throws IOException
	 */
	public String saveSysSetting() throws IOException{

		AjaxResult ar=new AjaxResult();
		  ActionParams groupParams=null;
		  List<ActionReady> actionReadys=null;
		  int groupKey=-1,upc=0;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			//一层设备地址
			int firDevVal=AtsParameterUtils.getInteger("firstDevice", request);
			/***
			 * 验证对应的值是否为空
			 */
			Constant.checkUsed(firDevVal, 0);
			//num1接警中心号码一      ,num2接警中心号码二
			String  num1,num2;
			//tels电话通迅间隔(分钟),msgs短信通迅间隔(分钟),num1dis接警1禁止,num2dis接警2禁止,telsdis电话通迅禁止,
			//msgsdis短信通迅禁止,printdis打印机禁止,content1报警事件,content2普通输入事件,content3用户操作事件;
			int tels,msgs,num1dis,num2dis,telsdis,msgsdis,printdis,content1,content2,content3;
			num1=AtsParameterUtils.getStringAllowNull("num1", request);
			num2=AtsParameterUtils.getStringAllowNull("num2", request);
			tels=AtsParameterUtils.getIntegerAllowNull("tels", request);
			msgs=AtsParameterUtils.getIntegerAllowNull("msgs", request);
			num1dis=AtsParameterUtils.getCheckbox("num1dis", request);
			num2dis=AtsParameterUtils.getCheckbox("num2dis", request);
			telsdis=AtsParameterUtils.getCheckbox("telsdis", request);
			msgsdis=AtsParameterUtils.getCheckbox("msgsdis", request);
			printdis=AtsParameterUtils.getCheckbox("printdis", request);
			content1=AtsParameterUtils.getCheckbox("content1", request);
			content2=AtsParameterUtils.getCheckbox("content2", request);
			content3=AtsParameterUtils.getCheckbox("content3", request);
			String printSeting=null;//打印设置串
			StringBuffer sbstrs=new StringBuffer();
			sbstrs.append(printdis).append(",").append(content1).append(",").append(content2).append(",").append(content3);
			printSeting=sbstrs.toString();
			groupKey=TransmitterContext.getSequnce();
			groupParams=new ActionParams();
				 
				actionReadys=new ArrayList<ActionReady>();
				if( !HeartContext.hasHeart(CommandTools.handKeyFormat(firDevVal,0)))throw new AtsException("设备可能已经掉线,无法操作！");
				Table10   first=(Table10)   baseDao.findOnlyByHql(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{firDevVal,0});
				
				//接警电话1
				  if(!num1.equals(StringUtils.deleteWhitespace(first.getTelNum1()==null?"":first.getTelNum1()))){
					upc++; 
					ActionParams params = new ActionParams();
					params.setCode(300);
					params.setOneP(firDevVal);
					params.setOnePT(first.getField10());
					params.setTwoP(0);
					params.setTwoPT(0);
					params.setData0(CommandTools.formatTelNum(num1));//下传的数据 ：接警电话1
					params.setComand6(1);//标记一层设备 
					
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x16();
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					Constant.getInstance().bindTarget(first, aReady);
					actionReadys.add(aReady);
				}
				  //接警电话2
				  if(!num2.equals(StringUtils.deleteWhitespace(first.getTelNum2()==null?"":first.getTelNum2()))){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(firDevVal);
						params.setOnePT(first.getField10());
						params.setTwoP(0);
						params.setTwoPT(0);
						params.setData0(CommandTools.formatTelNum(num2));//下传的数据 ：接警电话1
						params.setComand6(1);//标记一层设备  
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x18();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(first, aReady);
						actionReadys.add(aReady);
					}
				/*  //禁用电话1
				  if(num1dis!=(first.getNum1Dis()==null?0:first.getNum1Dis().intValue())){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(firDevVal);
						params.setOnePT(first.getField10());
						params.setTwoP(0);
						params.setTwoPT(0); 
						params.setComand1(telsdis);//下传的数据 ：接警电话1
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x12();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(first, aReady);
						actionReadys.add(aReady);
					}*/
				  //禁用电话通迅
				  if(telsdis!=(first.getDisableTel()==null?0:first.getDisableTel().intValue())){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(firDevVal);
						params.setOnePT(first.getField10());
						params.setTwoP(0);
						params.setTwoPT(0); 
						params.setComand0(telsdis);//下传的数据 ：禁止电话，1
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x12();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(first, aReady);
						actionReadys.add(aReady);
					}
				  //禁用短信通迅
				  if(msgsdis!=(first.getDisableMessage()==null?0:first.getDisableMessage().intValue())){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(firDevVal);
						params.setOnePT(first.getField10());
						params.setTwoP(0);
						params.setTwoPT(0); 
						params.setComand0(msgsdis);//下传的数据 ：接警电话1
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x10();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(first, aReady);
						actionReadys.add(aReady);
					}
				  
				  //设置电话间隔
				  if(tels!=(first.getTelSpace()==null?0:first.getTelSpace().intValue())){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(firDevVal);
						params.setOnePT(first.getField10());
						params.setTwoP(0);
						params.setTwoPT(0);
						params.setComand0(tels);//下传的数据 ：间隔时间
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x22();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(first, aReady);
						actionReadys.add(aReady);
					}
				  //设置短信间隔
				  if(msgs!=(first.getMessageSpace()==null?0:first.getMessageSpace().intValue())){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(firDevVal);
						params.setOnePT(first.getField10());
						params.setTwoP(0);
						params.setTwoPT(0);
						params.setComand0(msgs);//下传的数据 ：间隔时间
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x23();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(first, aReady);
						actionReadys.add(aReady);
					}
				  //打印设置 
				  if(!printSeting.equals(first.getPrintSet())){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(firDevVal);
						params.setOnePT(first.getField10());
						params.setTwoP(0);
						params.setTwoPT(0);
						params.setComand0(printdis);//打印设置 ：禁用打印机
						params.setComand1(content1);//打印设置 ：,content1报警事件
						params.setComand2(content2);//,content2普通输入事件,
						params.setComand3(content3);//content3用户操作事件
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox10_0x14();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(first, aReady);
						actionReadys.add(aReady);					  
				  }			  
			if(upc>0&&groupKey>0&&groupParams!=null&&actionReadys!=null&&actionReadys.size()>0){		
				//将组的父级放入缓存容器
				ActionContext.putValues(groupKey,groupParams);
				//将一个 准备好的数据包组 放入发送器中
				TransmitterContext.pushGroupDatagram(actionReadys);
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
			}else {
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有任何的有效修改！");				
			}
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
	 * 修改输出控制开关
	 * @return
	 * @throws IOException 
	 */
	public String changOut() throws IOException{
		AjaxResult ar=new AjaxResult();
		  ActionParams groupParams=null;
		  List<ActionReady> actionReadys=null;
		  int groupKey=-1,upc=0;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			//一层设备地址
			int firstLayerID=AtsParameterUtils.getInteger("firstLayerID", request);
			int secondLayerID=AtsParameterUtils.getInteger("secondLayerID", request);//二层设备网络地址
			if( !HeartContext.hasHeart(CommandTools.handKeyFormat(firstLayerID,0)))throw new AtsException("设备可能已经掉线,无法操作！");
			//判断是设备是否已经被屏蔽或是已经掉线
			Constant.checkUsed(firstLayerID, secondLayerID);
			
			int outid=AtsParameterUtils.getComboInt("out", request,new int[]{1,2,3,4,5,6,7,8});//对应的输出端口[1-8]
			Integer sw=AtsParameterUtils.getInteger("sw", request);//切换开关后的值
			groupKey=TransmitterContext.getSequnce();
			groupParams=new ActionParams();
				actionReadys=new ArrayList<ActionReady>();
				/**AtsEquipmentCache.queryDevice(firstLayerID, 0);**/
				Table10 parent=AtsEquipmentCache.getEquiReadOnly(firstLayerID, 0);
				Table10   equipment=AtsEquipmentCache.getEquiReadOnly(firstLayerID, secondLayerID);
				int tag=0;
				boolean doup=false;
				switch (outid) {				
					case 1: tag=1 ;doup=(!sw.equals(equipment.getField155()));break;
					case 2: tag=2 ;doup=(!sw.equals(equipment.getField156())); break;
					case 3: tag=4 ;doup=(!sw.equals(equipment.getField157())); break;
					case 4: tag=8 ; doup=(!sw.equals(equipment.getField158()));break;
					case 5: tag=16 ;doup=(!sw.equals(equipment.getField159())); break;
					case 6: tag=32 ;doup=(!sw.equals(equipment.getField160())); break;
					case 7: tag=64 ; doup=(!sw.equals(equipment.getField161()));break;
					case 8: tag=128 ;doup=(!sw.equals(equipment.getField162())); break;
				}
				
				  //设置输出
				  if(doup){
						upc++; 
						ActionParams params = new ActionParams();
						params.setCode(300);
						params.setOneP(equipment.getField3());
						params.setOnePT(0);
						params.setTwoP(equipment.getField4());
						params.setTwoPT(equipment.getField10());
						params.setComand0(tag);//输出选择
						params.setComand1((sw==1?tag:0));//输出状态
						params.setComand3(outid);//端口位置的缓存，当设备响应后使用此值
						params.setComand4(sw);//开关的缓存，当设备响应后使用此值
						ActionProcess ap = new ActionProcess();
						ap.setParams(params);// 设置参数
						ActionReady aReady = ap.ox20_0x05();
						params.setGroupID(groupKey);
						groupParams.getChildrens().put(aReady.getKey(), false);
						ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
						Constant.getInstance().bindTarget(parent, aReady);
						actionReadys.add(aReady);
				  }			  
			if(upc>0&&groupKey>0&&groupParams!=null&&actionReadys!=null&&actionReadys.size()>0){		
				//将组的父级放入缓存容器
				ActionContext.putValues(groupKey,groupParams);
				//将一个 准备好的数据包组 放入发送器中
				TransmitterContext.pushGroupDatagram(actionReadys);
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
			}else {
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有任何的有效修改！");				
			}
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
}
