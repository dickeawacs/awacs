package com.cdk.ats.web.action.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.utils.AjaxResult;
import common.cdk.config.files.msgconfig.MsgConfig;

/****
 * 主要用于客户端查看 用户表 
 * @author dingkai
 *
 */
public class Table1QueryAction {
	private static Log logger=LogFactory.getLog(Table1QueryAction.class);
 
	private  BaseDao baseDao;

 
 
	public BaseDao getBaseDao() {
		return baseDao;
	}



	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}



	/**
	 * 根据一层设备序号获取其下的所有用户集合
	 * @return
	 * @throws IOException 
	 */
	public String getUser() throws IOException{

		AjaxResult ar = new AjaxResult();
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String forPort = request.getParameter("forPort");
			String deviceID = request.getParameter("deviceID");
			String utype = request.getParameter("utype");
			Integer dID=null,typeval=null;
			if(NumberUtils.isNumber(deviceID)&&NumberUtils.isNumber(utype)){
				dID = NumberUtils.createInteger(deviceID);
				typeval= NumberUtils.createInteger(utype);
			}else{
				throw new Exception(MsgConfig.msg("ats-1009"));
			}
			String hql="from com.cdk.ats.web.pojo.hbm.Table1 where field11=?  and field5=? order by field12";
//SqlConfig.SQL("ats.table1.query.byOne"),			
			List<Table1> t1s = baseDao.findObjectsByHql(hql, new Object[]{dID,typeval});
			//普通用户
			if(typeval==3){
				
				Map<Integer, Table1> userMap=new HashMap<Integer, Table1>();
				int i=1;
				if(forPort!=null&&forPort.equals("1"))
				{
					i=0;
				}
				for (  ; i <=16; i++) {
					Table1 tempTable1= new Table1();
					if(i==0)
					tempTable1.setField3(" 无 ");
					else
						tempTable1.setField3(CommandTools.createUserName(dID, i));
					tempTable1.setField12(i);
					userMap.put(Integer.valueOf(i),tempTable1 );
				}
				if(t1s!=null)
				for (Table1 table1:t1s) {
					 if(userMap.containsKey(table1.getField12())){
						 userMap.put(table1.getField12(),table1);
					 }
				}
				List<Table1> table1s=new ArrayList<Table1>();
				Set<Integer> keys=userMap.keySet();
				for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
					Integer key = (Integer) iterator.next();
					table1s.add(userMap.get(key));
					
				}
				Collections.sort(table1s,new Comparator<Table1>(){
					public int compare(Table1 o1, Table1 o2) {
						 
						return o1.getField12()-o2.getField12();
					}
					
					
				});
				ar.setArray(table1s);
			}else if(typeval==2){
				if(	t1s==null){
					  t1s=new ArrayList<Table1>();
				}
				if(t1s.isEmpty()){

					Table1 tempTable1= new Table1();
					tempTable1.setField3(CommandTools.createAdminName(dID));
					tempTable1.setField12(0);
					t1s.add(tempTable1 );
				}
				ar.setArray(t1s);
			}
			else{
			
				ar.setArray(t1s);
			}
			
			
			
			ar.setTotal(ar.getArray()!=null?ar.getArray().size():0);
			ar.isSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		}finally{
			ar.writeToJsonString();
		}
	
		
		return null;
	}

	
}
