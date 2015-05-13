package com.cdk.ats.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;

import com.cdk.ats.web.action.login.UserInfo;
import com.cdk.ats.web.exception.UserNotFoundException;
import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.TreeNode;
import com.cdk.ats.web.utils.TreeNodeCheckbox;

import common.cdk.config.files.sqlconfig.SqlConfig;

@Component
public class SystemDao extends BaseDao{

	private static final Logger log=Logger.getLogger(SystemDao.class);
	
	public void saveBaseInfo(){
		try{
			//log.info("删除 了 tble_1_bak表 "+this.getSession().createSQLQuery("delete from table_1_bak").executeUpdate()+"条");
			log.info("删除 了 tble_10_bak表 "+this.getSession().createSQLQuery("delete from table_10_bak").executeUpdate()+"条");
			for (int i = 1; i < 129; i++) {
				List<Table10> t10s=new ArrayList<Table10>();
				for (int j = 1; j < 129; j++) {
					Table10 t10=new Table10();
					if(j==0){
						t10.setField121(i+"一层设备");
						t10.setDtype(0);
						t10.setField3(i);
						t10.setField4(0);
					} else{
						t10.setField121(j+"二层设备");
						t10.setDtype(0);
						t10.setField3(i);
						t10.setField4(j);
					}
					
					t10.setDisableTel(1);
					t10.setDisableMessage(1);
					t10.setTelNum1("");
					t10.setTelNum2("");
					t10.setTelSpace(5);
					t10.setPrintSet("");
					t10.setField130(0);
					t10s.add(t10);
				}
			
				getHibernateTemplate().saveOrUpdateAll(t10s);
				//getHibernateTemplate().saveOrUpdateAll(t1s);
				getHibernateTemplate().flush();
				getHibernateTemplate().clear();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/****
	 * 重置所有 设备 table10
	 * @return
	 * @throws Exception
	 */
	public int updateReset() throws Exception{
		try{
		int count=0;
		getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.del.Table10"));
		getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.del.Table1All"));
		getHibernateTemplate().flush();
		SQLQuery query= this.getSession().createSQLQuery(SqlConfig.SQL("ats.system.reset"));
		count=	query.executeUpdate();
		log.info("批量恢复设备表成功，影响行数："+count);
		SQLQuery query2= this.getSession().createSQLQuery(SqlConfig.SQL("ats.table1.reset"));
		count=	query2.executeUpdate();
		log.info("批量恢复设备表成功，影响行数："+count);
		return count;
		}catch (Exception e) {
			log.error("批量恢复设备表失败",e);
			 throw e;
		
		}
		
	}
	/****
	 * 
	 * @return
	 * @throws Exception
	 */
	public int updateResetOne(Object[] objs) throws Exception{
		try{
		int count=0;
		getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.del.Table10.one.childs"),objs);
		getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.del.Table10.one.users"),objs);
		SQLQuery query= this.getSession().createSQLQuery(SqlConfig.SQL("ats.system.reset.one.childs"));
		query.setParameter(0, objs[0]); 
		count=query.executeUpdate();
		SQLQuery query2= this.getSession().createSQLQuery(SqlConfig.SQL("ats.system.reset.one.users"));
		query2.setParameter(0, objs[0]); 
		count+=query2.executeUpdate();
		log.info("恢复一层设备"+objs[0]+" 成功，影响行数："+count);
		return count;
		}catch (Exception e) {
			log.error("批量恢复设备表失败",e);
			 throw e;
		
		}
		
	}
	/****
	 * 重置指定的二层设备
	 * @return
	 * @throws Exception
	 */
	public int updateResetTwo(Object[] objs) throws Exception{
		try{
		int count=0;
		getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.del.Table10.one.target"),objs);
		SQLQuery query= this.getSession().createSQLQuery(SqlConfig.SQL("ats.system.reset.one.target"));
		query.setParameter(0, objs[0]);
		query.setParameter(1, objs[1]);
		count=query.executeUpdate();
		log.info("恢复指定设备"+objs[0]+","+objs[1]+"成功，影响行数："+count);
		return count;
		}catch (Exception e) {
			log.error("批量恢复设备表失败",e);
			 throw e;
		}
		
	}
	
	/**
	 * 查出所有一二层设备，父子关系 集
	 * @return
	 * @throws Exception
	 */
	public List<Table10> findAll() throws Exception{
		List<Table10> first=null;
		try{
			first=getHibernateTemplate().find(SqlConfig.SQL("ats.userset.first.all"));
			for (int i = 0; i <first.size(); i++) {
				first.get(i).getSecond().addAll(getHibernateTemplate().find(SqlConfig.SQL("ats.first.query.by.field3"), new Object[] {first.get(i).getField3(),first.get(i).getField4()}));
			}
		}catch (Exception e) {
			log.error("批量恢复设备表失败",e);
			 throw e;
		}
		return first;
	}
	
	/***
	 * 
	 * 描述： 验证管理员用户密码
	 * @createBy dingkai
	 * @createDate 2014-1-9
	 * @lastUpdate 2014-1-9
	 * @param userInfo
	 * @param pwd
	 * @return
	 */
	public boolean reloginAdmin(UserInfo userInfo,String pwd){
		boolean isReady=false;
		try{
			Table1 tempUserInfo = null;

			/**
			 * 是普通可用用户，或是管理员
			 * */
			Query query = this.getSession().createQuery("from com.cdk.ats.web.pojo.hbm.Table1 where field1 =?  and((field6=0  and field5=2 )or(field5=1))");
			query.setInteger(0,userInfo.getId());
			Object resultObj = query.uniqueResult();
			if (resultObj != null) {
				tempUserInfo = (Table1) resultObj;
				if(tempUserInfo.getField4().equals(pwd)){
					 isReady=true;
				}
			} else throw new UserNotFoundException();
		}catch (Exception e) {
			log.error("验证密码失败",e);
		}
		return isReady;
		
	}
}
