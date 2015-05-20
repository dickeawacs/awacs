package com.cdk.ats.web.action.contorl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionProcess;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.web.dao.SystemDao;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.pojo.hbm.Table10Bak;
import com.cdk.ats.web.pojo.hbm.Table1Bak;
import common.cdk.config.files.sqlconfig.SqlConfig;

/***
 * 此类用于批量的组合数据包
 * 
 * @author dingkai
 * 
 */
public class BatchDatagram {
	
	private SystemDao dao;
	private int[] basePort = new int[] { 1, 2, 4, 8, 16, 32, 64, 128 };
	public BatchDatagram(SystemDao dao) {
		this.dao = dao;
	}
	
	
	
	/**
	 * 将设备集合信息发送给设备
	 * 
	 * @param t10s
	 * @return
	 */
	public List<ActionReady> PackageDatagram(List<Table10> t10s,String ip,Integer   port) {/*
		List<ActionReady> actionReadys = new ArrayList<ActionReady>();
		int groupKey = TransmitterContext.getSequnce();
		ActionParams groupParams = new ActionParams();
		groupParams.setSequnce(groupKey);

		for (int i = 0; i < t10s.size(); i++) {
			cmd1(t10s.get(i), groupKey, actionReadys, groupParams,ip,port);
			cmd2(t10s.get(i), groupKey, actionReadys, groupParams,ip,port);
			cmd3(t10s.get(i), groupKey, actionReadys, groupParams,ip,port);
			cmd4(t10s.get(i), groupKey, actionReadys, groupParams,ip,port);
			cmd5(t10s.get(i), groupKey, actionReadys, groupParams,ip,port);
			cmd6(t10s.get(i), groupKey, actionReadys, groupParams,ip,port);
			cmd7(t10s.get(i), groupKey, actionReadys, groupParams,ip,port);
		}
		return actionReadys;
	*/
		return null;
		}

	/**
	 * 将设备集合信息发送给设备
	 * 
	 * @param t10s
	 * @return
	 */
	public List<ActionReady> PackageDatagram(List<Table10Bak> t10s,ActionParams groupParams) {
		List<ActionReady> actionReadys = new ArrayList<ActionReady>();
		int groupKey =groupParams.getSequnce();
		//ActionParams groupParams = new ActionParams();
		//groupParams.setSequnce(groupKey);
       for (int i = 0; i < t10s.size(); i++) {
    	   if(t10s.get(i)==null)continue;
    	    cmd1(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			cmd2(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			cmd3(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			cmd4(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			cmd5(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			cmd6(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			cmd7(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			cmd8(t10s.get(i), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			if(t10s.get(i).getSecond()==null||t10s.get(i).getSecond().isEmpty())continue;
			for (int j = 0; j < t10s.get(i).getSecond().size(); j++) {
				 if(t10s.get(i).getSecond().get(j)==null)continue;
			    cmd1(t10s.get(i).getSecond().get(j), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
				cmd2(t10s.get(i).getSecond().get(j), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
				cmd3(t10s.get(i).getSecond().get(j), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
				cmd4(t10s.get(i).getSecond().get(j), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
				cmd5(t10s.get(i).getSecond().get(j), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
				cmd6(t10s.get(i).getSecond().get(j), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
				cmd7(t10s.get(i).getSecond().get(j), groupKey, actionReadys, groupParams,t10s.get(i).getFip(),t10s.get(i).getFport());
			}
       }
		return actionReadys;
	}

	

	/***
	 * 交叉输入设置
	 * 
	 * @param t10
	 * @param groupKey
	 * @param actionReadys
	 * @param groupParams
	 */
	private void cmd1(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port) {
		int j = 0;
		for (int i = 33; i < 41; i++, j++) {
			try {
				String[] targets;
				Object val = MethodUtils
						.invokeMethod(t10, "getField" + i, null);
				if (val == null)
					continue;
				targets = val.toString().split("[,]");
				if (targets.length !=3)
					continue;

				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getOneType());
				params.setTwoP(t10.getField4());
				params.setTwoPT(t10.getField10());

				params.setComand0(basePort[j]);// 当前输入端口

				params.setComand1(NumberUtils.createInteger(targets[0]));// 交叉一层设备号
				params.setComand2(NumberUtils.createInteger(targets[1]));// 交叉二层设备号
				params.setComand3(NumberUtils.createInteger(targets[2]));// 交叉输入端口

				params.setComand5(2);// 标记设备是一层（1）还是二层（2）

				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x02();
				params.setGroupID(groupKey);
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				actionReadys.add(aReady);
			} catch (Exception e) {

			}

		}

	}

	/***
	 * 下传输入联动信息
	 * 
	 * @param tag
	 * @param cascadeTag
	 * @param t9
	 * @param t10
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @param cascadePV
	 * @param cascadeFCV
	 * @param cascadeSCV
	 * @param cascadeOPV
	 * @return
	 * @throws Exception
	 */
	private void cmd2(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port) {
		int j = 0;
		for (int i = 41; i < 105; i++,j++) {
			try {
				Object val = MethodUtils
						.invokeMethod(t10, "getField" + i, null);
				if (val == null)
					continue;
				String[] targets = val.toString().split("[,]");
				if (targets.length !=4)
					continue;

				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getField10());
				params.setTwoP(t10.getField4());
				params.setTwoPT(t10.getField10());
				params.setComand0(basePort[(j/8)]);// 当前的输入端口
				params.setComand1((j%8)+1);// 输出联动设置选择字节
				params.setComand2(NumberUtils.createInteger(targets[0]));// 一层设备号
				params.setComand3(NumberUtils.createInteger(targets[1]));// 二层设备号
				params.setComand4(NumberUtils.createInteger(targets[2]));// 联动输出端口
				params.setComand6(NumberUtils.createInteger(targets[3]));// 联动属性

				params.setComand5(2);// //标记设备是一层（1）还是二层（2）

				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x04();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
			} catch (Exception e) {
			}
		/*	if (j == 7)
				j = 0;
			else
				j++;*/
		}

	}

	/***
	 * 下传输入属性信息
	 * 
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param inputPV
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @return
	 */
	private void cmd3(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port) {
		int j = 0;
		for (int i = 17; i < 25; i++,j++) {
		try{
			Object val = MethodUtils
			.invokeMethod(t10, "getField" + i, null);
	if (val == null||!NumberUtils.isNumber(val.toString()))
		continue;
	
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());

		params.setComand0(basePort[j]);// 对应的输入端口1 2 4 8 16 32 64 128
		params.setComand1(NumberUtils.createInteger(val.toString()));// 下传的数据 ：输入属性 [1.屏蔽输入。 2.普通输入。 3.立即防区。
		// 4.24小时防区。 ]

		params.setComand5(2);// 标记设备是一层（1）还是二层（2）
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x0a();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		aReady.setTargetIp(ip);
		aReady.setTargetPort(port);
		actionReadys.add(aReady);
		}catch (Exception e) { 
		}
		}
	}

	/***
	 * 下传设备名信息
下传开启/屏蔽设备信息 

	 * @param t10
	 * @param groupKey
	 * @param actionReadys
	 * @param groupParams
	 */
	private void cmd4(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port){
		//下传设备名称 
		{
		// 修改二层设备名称
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		params.setData0(t10.getField121());// 下传的数据 ：设备名称
		// 设备 类型
		if (t10.getField4() == 0)
			params.setComand1(1);
		else
			params.setComand1(2);

		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x1c();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		aReady.setTargetIp(ip);
		aReady.setTargetPort(port);
		actionReadys.add(aReady);
	}
		
		/*******下传开启或屏蔽设备 *******/
	/*	{
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		params.setComand0(t10.getField130()==null?1:t10.getField130());// 下传的数据 ：禁用设备
		// 设备 类型
		if (t10.getField4() == 0)
			params.setComand1(1);
		else
			params.setComand1(2);

		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x1e();

		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		aReady.setTargetIp(ip);
		aReady.setTargetPort(port);
		actionReadys.add(aReady);
		}*/
		if(t10.getField4()!=null&&t10.getField4().equals(0)){
		//开启或禁用电话
/*		{
			ActionParams params = new ActionParams();
			params.setCode(300);
			params.setOneP(t10.getField3());
			params.setOnePT(t10.getField10());
			params.setComand0(t10.getDisableTel()!=null?t10.getDisableTel():1);
			 

			ActionProcess ap = new ActionProcess();
			ap.setParams(params);// 设置参数
			ActionReady aReady = ap.ox10_0x12();

			params.setGroupID(groupKey);
			groupParams.getChildrens().put(aReady.getKey(), false);
			ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
			aReady.setTargetIp(ip);
			aReady.setTargetPort(port);
			actionReadys.add(aReady);
			}*/
		
		//开启或禁用短信
		/*{
			ActionParams params = new ActionParams();
			params.setCode(300);
			params.setOneP(t10.getField3());
			params.setOnePT(t10.getOneType()); 
			
			params.setComand0(t10.getDisableMessage()!=null?t10.getDisableMessage():1);
			 
		 
			ActionProcess ap = new ActionProcess();
			ap.setParams(params);// 设置参数
			ActionReady aReady = ap.ox10_0x10();

			params.setGroupID(groupKey);
			groupParams.getChildrens().put(aReady.getKey(), false);
			ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
			aReady.setTargetIp(ip);
			aReady.setTargetPort(port);
			actionReadys.add(aReady);
			}*/
		//打印 设置 
			/*if(t10.getPrintSet()!=null){
				String[] cmds=new String[]{"0","0","0","0"};
				if(t10.getPrintSet()!=null){
					String[] temp=t10.getPrintSet().split(",");
					for (int i = 0; i < temp.length; i++) {
						cmds[i]=temp[i];
					}
				}
				
				if(NumberUtils.isNumber(cmds[0])&&NumberUtils.isNumber(cmds[1])&&NumberUtils.isNumber(cmds[2])&&NumberUtils.isNumber(cmds[3]))
				{
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getField10());
				
				params.setComand0(NumberUtils.createInteger(cmds[0]));
				params.setComand1(NumberUtils.createInteger(cmds[1]));
				params.setComand2(NumberUtils.createInteger(cmds[2]));
				params.setComand3(NumberUtils.createInteger(cmds[3]));			 
	
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x14();
	
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
				}
			}*/
			
			//下传接警号码 1
			/*if(t10.getTelNum1()!=null){
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getField10());
				params.setData0(CommandTools.formatTelNum(t10.getTelNum1()));
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x16();
	
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
			}
			//下传接警号码 2
			if(t10.getTelNum2()!=null){
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getField10());
				params.setData0(CommandTools.formatTelNum(t10.getTelNum1()));
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x18();
	
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
			}*/
		}
	} 

	
	
	/***
	 * 
	 * 下传输入名称
	 * 
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param inputName
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @throws Exception
	 */
	private void cmd5(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port)  {
		for (int i = 105, j = 0; i < 113; i++, j++) {
			try {
				Object val = MethodUtils
						.invokeMethod(t10, "getField" + i, null);
				if (val == null)
					continue;

				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getOneType());
				params.setTwoP(t10.getField4());
				params.setTwoPT(t10.getField10());

				params.setData0(val.toString());// 下传的数据 ：输入名称
				params.setComand0(basePort[j]);// 对应的输入端口1 2 4 8 16 32 64 128

				params.setComand5(2);// 标记设备是一层（1）还是二层（2）
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x20();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
		}
	/***
	 * 设置 输出端口名称
	 * 
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param outName
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @throws Exception
	 */
	private void cmd6(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port) {
		for (int i = 113, j = 0; i < 121; i++, j++) {
			try {
				Object val = MethodUtils
						.invokeMethod(t10, "getField" + i, null);
				if (val == null)
					continue;
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(t10.getField3());
		params.setOnePT(t10.getOneType());
		params.setTwoP(t10.getField4());
		params.setTwoPT(t10.getField10());
		
		params.setData0(val.toString());// 下传的数据 ：输出名称
		//System.out.println("输出名称:"+params.getData0());
		params.setComand0(basePort[j]);// 对应的输入端口1 2 4 8 16 32 64 128
		
		params.setComand5(2);// 标记设备是一层（1）还是二层（2）
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = ap.ox10_0x21();
		params.setGroupID(groupKey);
		groupParams.getChildrens().put(aReady.getKey(), false);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		aReady.setTargetIp(ip);
		aReady.setTargetPort(port);
		actionReadys.add(aReady);
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
		}
	
	
	/**
	 * 下传用户密码/管理员密码//下传电话号码 /下传短信号码 
	 * @param t10
	 * @param groupKey
	 * @param actionReadys
	 * @param groupParams
	 */
	private void cmd7(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port) {
		if(t10.getField4()==null||(t10.getField4()!=null&&!t10.getField4().equals(0)))return;
		List<Table1Bak> t1s=dao.findObjectsByHql(SqlConfig.SQL("ats.system.query.users.bak"),new Object[]{t10.getField3()});
		if(t1s!=null)
		for (Table1Bak table1 : t1s) {

			try { if(table1.getField5()!=null&&table1.getField5().equals(2)){
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getField10());
				
				params.setData0(table1.getField4());// 密码
				params.setUserCode(table1.getField12());
				
				
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x08();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
			}else{
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getField10());
				
				params.setData0(table1.getField4());// 密码
				params.setUserCode(table1.getField12());
				
				
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x06();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
			}
		/*	//下传电话号码 
			if(table1.getField7()!=null){

				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(t10.getField3());
				params.setOnePT(t10.getField10());
				
				params.setData0(table1.getField7());// 电话 
				params.setUserCode(table1.getField12());
				
				
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x0c();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(ip);
				aReady.setTargetPort(port);
				actionReadys.add(aReady);
				 
			
				
			} 
			//下传短信号码 
			if(table1.getField9()!=null){
				ActionParams params = new ActionParams();
					params.setCode(300);
					params.setOneP(t10.getField3());
					params.setOnePT(t10.getField10());
					
					params.setData0(table1.getField9());// 电话 
					params.setUserCode(table1.getField12());
					
					
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x0e();
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					aReady.setTargetIp(ip);
					aReady.setTargetPort(port);
					actionReadys.add(aReady);
				}
			*/
			//下传开启屏蔽用户信息
			/*if(table1.getField6()!=null){
				ActionParams params = new ActionParams();
					params.setCode(300);
					params.setOneP(t10.getField3());
					params.setOnePT(t10.getField10());
					
					params.setComand0(table1.getField6());//  
					params.setUserCode(table1.getField12());
					
					
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x1a();
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					aReady.setTargetIp(ip);
					aReady.setTargetPort(port);
					actionReadys.add(aReady);
				}*/
			//下传用户名信息
			if(table1.getField6()!=null){
				ActionParams params = new ActionParams();
					params.setCode(300);
					params.setOneP(t10.getField3());
					params.setOnePT(t10.getField10());
					
					params.setData0(table1.getField3());// 用户名 
					params.setUserCode(table1.getField12());
					
					
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x1b();
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					aReady.setTargetIp(ip);
					aReady.setTargetPort(port);
					actionReadys.add(aReady);
				}
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		}
	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***
	 * 保存设置系统设置 下传开启/屏蔽电话端口信息 下传开启/屏蔽短信端口信息
	 * 
	 * @return
	 */
	private void cmd15() {/*

		AjaxResult ar = new AjaxResult();
		ActionParams groupParams = null;
		List<ActionReady> actionReadys = null;
		int groupKey = -1, upc = 0;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			// 一层设备地址
			int firDevVal = AtsParameterUtils
					.getInteger("firstDevice", request);
			// num1接警中心号码一 ,num2接警中心号码二
			String num1, num2;
			// tels电话通迅间隔(分钟),msgs短信通迅间隔(分钟),num1dis接警1禁止,num2dis接警2禁止,telsdis电话通迅禁止,
			// msgsdis短信通迅禁止,printdis打印机禁止,content1报警事件,content2普通输入事件,content3用户操作事件;
			int tels, msgs, num1dis, num2dis, telsdis, msgsdis, printdis, content1, content2, content3;
			num1 = AtsParameterUtils.getStringAllowNull("num1", request);
			num2 = AtsParameterUtils.getStringAllowNull("num2", request);
			tels = AtsParameterUtils.getIntegerAllowNull("tels", request);
			msgs = AtsParameterUtils.getIntegerAllowNull("msgs", request);
			num1dis = AtsParameterUtils.getCheckbox("num1dis", request);
			num2dis = AtsParameterUtils.getCheckbox("num2dis", request);
			telsdis = AtsParameterUtils.getCheckbox("telsdis", request);
			msgsdis = AtsParameterUtils.getCheckbox("msgsdis", request);
			printdis = AtsParameterUtils.getCheckbox("printdis", request);
			content1 = AtsParameterUtils.getCheckbox("content1", request);
			content2 = AtsParameterUtils.getCheckbox("content2", request);
			content3 = AtsParameterUtils.getCheckbox("content3", request);
			String printSeting = null;// 打印设置串
			StringBuffer sbstrs = new StringBuffer();
			sbstrs.append(content1).append(",").append(content2).append(",")
					.append(content3);
			printSeting = sbstrs.toString();
			groupKey = TransmitterContext.getSequnce();
			groupParams = new ActionParams();

			actionReadys = new ArrayList<ActionReady>();
			Table10 first = (Table10) baseDao.findOnlyByHql(SqlConfig
					.SQL("ats.table10.query.saveBeforQuery"), new Object[] {
					firDevVal, 0 });

			// 接警电话1
			if (!num1.equals(StringUtils
					.deleteWhitespace(first.getTelNum1() == null ? "" : first
							.getTelNum1()))) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(0);
				params.setTwoPT(0);
				if (num1dis == 1) {
					num1 = "";
				}
				params.setData0(num1);// 下传的数据 ：接警电话1
				params.setComand6(1);// 标记一层设备

				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x16();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			// 接警电话2
			if (!num2.equals(StringUtils
					.deleteWhitespace(first.getTelNum2() == null ? "" : first
							.getTelNum2()))) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(0);
				params.setTwoPT(0);
				if (num2dis == 1) {
					num2 = "";
				}
				params.setData0(num2);// 下传的数据 ：接警电话1
				params.setComand6(1);// 标记一层设备
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x18();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			// 禁用电话
			if (telsdis != (first.getDisableTel() == null ? 0 : first
					.getDisableTel().intValue())) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(0);
				params.setTwoPT(0);
				params.setComand1(telsdis);// 下传的数据 ：接警电话1
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x12();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			// 禁用短信
			if (msgsdis != (first.getDisableMessage() == null ? 0 : first
					.getDisableMessage().intValue())) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(0);
				params.setTwoPT(0);
				params.setComand1(msgsdis);// 下传的数据 ：接警电话1
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x10();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}

			// 设置电话间隔
			if (tels != (first.getTelSpace() == null ? 0 : first.getTelSpace()
					.intValue())) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(0);
				params.setTwoPT(0);
				params.setComand0(tels);// 下传的数据 ：间隔时间
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x22();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			// 设置短信间隔
			if (msgs != (first.getMessageSpace() == null ? 0 : first
					.getMessageSpace().intValue())) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(0);
				params.setTwoPT(0);
				params.setComand0(msgs);// 下传的数据 ：间隔时间
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x23();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			// 打印设置
			if (!printSeting.equals(first.getPrintSet())) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(0);
				params.setTwoPT(0);
				params.setComand0(printdis);// 打印设置 ：禁用打印机
				params.setComand1(content1);// 打印设置 ：,content1报警事件
				params.setComand2(content2);// ,content2普通输入事件,
				params.setComand3(content3);// content3用户操作事件
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x14();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			if (upc > 0 && groupKey > 0 && groupParams != null
					&& actionReadys != null && actionReadys.size() > 0) {
				// 将组的父级放入缓存容器
				ActionContext.putValues(groupKey, groupParams);
				// 将一个 准备好的数据包组 放入发送器中
				TransmitterContext.pushGroupDatagram(actionReadys);
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
			} else {
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有任何的有效修改！");
			}
		} catch (AtsException e) {
			ar.isFailed(e.getMessage());
		} catch (Exception e) {
			ar.isFailed("");
			ar.setExceptionMessage(e.getMessage());
			if (actionReadys != null) {
				for (int i = 0; i < actionReadys.size(); i++) {
					try {
						ActionContext
								.distoryParam(actionReadys.get(i).getKey());
					} catch (Exception exc) {
					}
				}
			}
			if (groupKey > 0) {
				ActionContext.distoryParam(groupKey);
			}
		} finally {
			// ar.writeToJsonString();
		}
		return null;

	*/}

	/***
	 * 测试已通过 保存设备信息 下传设备名称 下传开启/屏蔽设备信息
	 * 
	 * @return
	 * @throws IOException
	 */
	private void cmd6() throws IOException {/*
		AjaxResult ar = new AjaxResult();
		ActionParams groupParams = null;
		List<ActionReady> actionReadys = null;
		int groupKey = -1, upc = 0;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String firstDevice = request.getParameter("firstDevice");
			int firDevVal = -1;
			String secondDevice = request.getParameter("secondDevice");
			int secDevVal = -1;
			String deviceName = request.getParameter("deviceName");
			String deviceDisable = request.getParameter("deviceDisable");
			int devDisVal = 0;
			if (firstDevice == null
					|| !NumberUtils.isNumber(firstDevice.trim()))
				throw new AtsException(MsgConfig.msg("ats-1010"));
			else
				firDevVal = NumberUtils.createInteger(firstDevice);
			if (deviceName == null || deviceName.trim().equals(""))
				throw new AtsException(MsgConfig.msg("ats-1010"));
			else {
				deviceName = StringUtils.deleteWhitespace(deviceName);
			}
			if (deviceDisable != null && deviceDisable.equals("1"))
				devDisVal = 1;
			groupKey = TransmitterContext.getSequnce();
			groupParams = new ActionParams();
			if (secondDevice == null
					|| !NumberUtils.isNumber(StringUtils
							.deleteWhitespace(secondDevice)))
				throw new AtsException(MsgConfig.msg("ats-1010"));
			else
				secDevVal = NumberUtils.createInteger(StringUtils
						.deleteWhitespace(secondDevice));
			actionReadys = new ArrayList<ActionReady>();
			Table10 first = (Table10) baseDao.findOnlyByHql(SqlConfig
					.SQL("ats.table10.query.saveBeforQuery"), new Object[] {
					firDevVal, 0 });
			Table10 second = (Table10) baseDao.findOnlyByHql(SqlConfig
					.SQL("ats.table10.query.saveBeforQuery"), new Object[] {
					firDevVal, secDevVal });
			if (!deviceName.equals(StringUtils.deleteWhitespace(second
					.getField121()))) {
				upc++;
				// 修改二层设备名称
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(secDevVal);
				params.setTwoPT(second.getField10());
				params.setData0(deviceName);// 下传的数据 ：设备名称
				// 设备 类型
				if (second.getField4() == 0)
					params.setComand1(1);
				else
					params.setComand1(2);

				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x1c();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			if (second.getField130() == null)
				second.setField130(0);
			if (!second.getField130().equals(devDisVal)) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(firDevVal);
				params.setOnePT(first.getField10());
				params.setTwoP(secDevVal);
				params.setTwoPT(second.getField10());
				params.setComand0(devDisVal);// 下传的数据 ：禁用设备
				// 设备 类型
				if (second.getField4() == 0)
					params.setComand1(1);
				else
					params.setComand1(2);

				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox10_0x1e();

				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			if (upc > 0 && groupKey > 0 && groupParams != null
					&& actionReadys != null && actionReadys.size() > 0) {
				// 将组的父级放入缓存容器
				ActionContext.putValues(groupKey, groupParams);
				// 将一个 准备好的数据包组 放入发送器中
				TransmitterContext.pushGroupDatagram(actionReadys);
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
			} else {
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有任何的有效修改！");
			}
		} catch (AtsException e) {
			ar.isFailed(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ar.isFailed("");
			ar.setExceptionMessage(e.getMessage());
			if (actionReadys != null) {
				for (int i = 0; i < actionReadys.size(); i++) {
					try {
						ActionContext
								.distoryParam(actionReadys.get(i).getKey());
					} catch (Exception exc) {
					}
				}
			}
			if (groupKey > 0) {
				ActionContext.distoryParam(groupKey);
			}
		} finally {
			ar.writeToJsonString();
		}
		return null;

	*/}

	
	/***
	 * 设置对应的二级用户
	 * 
	 * @param tag
	 * @param t9
	 * @param t10
	 * @param userCode
	 * @param groupKey
	 * @param groupParams
	 * @param actionReadys
	 * @throws Exception
	 */
	private void cmd8(Table10Bak t10, Integer groupKey,
			List<ActionReady> actionReadys, ActionParams groupParams,String ip,Integer port)   {
			for(int i=0;i<8;i++){
					ActionParams params = new ActionParams();
					params.setCode(300);
					params.setOneP(t10.getField3());
					params.setOnePT(0);
					params.setTwoP(0);
					params.setTwoPT(0);
					params.setUserCode(0);// 用户
					
					params.setComand0(basePort[i]);// 输入1
					
					params.setComand5(2);// 标记设备是一层（1）还是二层（2）
					ActionProcess ap = new ActionProcess();
					ap.setParams(params);// 设置参数
					ActionReady aReady = ap.ox10_0x1f();
					params.setGroupID(groupKey);
					groupParams.getChildrens().put(aReady.getKey(), false);
					ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
					aReady.setTargetIp(ip);
					aReady.setTargetPort(port);
					actionReadys.add(aReady);
			}
	}

	

	/***
	 * 修改输出控制开关
	 * 
	 * @return
	 * @throws IOException
	 */
	private String cmd9() throws IOException {/*
		AjaxResult ar = new AjaxResult();
		ActionParams groupParams = null;
		List<ActionReady> actionReadys = null;
		int groupKey = -1, upc = 0;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			// 一层设备地址
			int firstLayerID = AtsParameterUtils.getInteger("firstLayerID",
					request);
			int secondLayerID = AtsParameterUtils.getInteger("secondLayerID",
					request);// 二层设备网络地址
			int outid = AtsParameterUtils.getComboInt("out", request,
					new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });// 对应的输出端口[1-8]
			Integer sw = AtsParameterUtils.getInteger("sw", request);// 切换开关后的值
			groupKey = TransmitterContext.getSequnce();
			groupParams = new ActionParams();
			actionReadys = new ArrayList<ActionReady>();
			Table10 first = EquipmentCacheVO.queryByAddress(firstLayerID, 0);
			Table10 second = AtsDeviceCache.queryDevice(firstLayerID,
					secondLayerID);
			int tag = 0;
			boolean doup = false;
			System.out.println(sw);
			switch (outid) {
			case 1:
				tag = 1;
				doup = (!sw.equals(second.getField155()));
				continue;
			case 2:
				tag = 2;
				doup = (!sw.equals(second.getField156()));
				continue;
			case 3:
				tag = 4;
				doup = (!sw.equals(second.getField157()));
				continue;
			case 4:
				tag = 8;
				doup = (!sw.equals(second.getField158()));
				continue;
			case 5:
				tag = 16;
				doup = (!sw.equals(second.getField159()));
				continue;
			case 6:
				tag = 32;
				doup = (!sw.equals(second.getField160()));
				continue;
			case 7:
				tag = 64;
				doup = (!sw.equals(second.getField161()));
				continue;
			case 8:
				tag = 128;
				doup = (!sw.equals(second.getField162()));
				continue;
			}

			// 设置输出
			if (doup) {
				upc++;
				ActionParams params = new ActionParams();
				params.setCode(300);
				params.setOneP(first.getField3());
				params.setOnePT(first.getField10());
				params.setTwoP(second.getField4());
				params.setTwoPT(second.getField10());
				params.setComand0(tag);// 输出选择
				params.setComand1((sw == 1 ? tag : 0));// 输出状态
				params.setComand3(outid);// 端口位置的缓存，当设备响应后使用此值
				params.setComand4(sw);// 开关的缓存，当设备响应后使用此值
				ActionProcess ap = new ActionProcess();
				ap.setParams(params);// 设置参数
				ActionReady aReady = ap.ox20_0x05();
				params.setGroupID(groupKey);
				groupParams.getChildrens().put(aReady.getKey(), false);
				ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
				aReady.setTargetIp(first.getFip());
				aReady.setTargetPort(first.getFport());
				actionReadys.add(aReady);
			}
			if (upc > 0 && groupKey > 0 && groupParams != null
					&& actionReadys != null && actionReadys.size() > 0) {
				// 将组的父级放入缓存容器
				ActionContext.putValues(groupKey, groupParams);
				// 将一个 准备好的数据包组 放入发送器中
				TransmitterContext.pushGroupDatagram(actionReadys);
				ar.isSuccess().setReturnVal(groupKey);
				ar.stateWaiting();
			} else {
				ar.setCode(901);
				ar.setSuccess(true);
				ar.setMessage("操作失败，没有任何的有效修改！");
			}
		} catch (AtsException e) {
			ar.isFailed(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ar.isFailed("");
			ar.setExceptionMessage(e.getMessage());
			if (actionReadys != null) {
				for (int i = 0; i < actionReadys.size(); i++) {
					try {
						ActionContext
								.distoryParam(actionReadys.get(i).getKey());
					} catch (Exception exc) {
					}
				}
			}
			if (groupKey > 0) {
				ActionContext.distoryParam(groupKey);
			}
		} finally {
			ar.writeToJsonString();
		}
		return null;
	*
	*/
	return null;	
	}
	
	 
}
