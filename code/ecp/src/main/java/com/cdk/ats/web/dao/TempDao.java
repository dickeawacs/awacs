package com.cdk.ats.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.pojo.hbm.Table10;
import common.cdk.cryption.md5.MD5Utils;

public class TempDao extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(Table10DAO.class);
 

	public void closeSession(){
		getSession().close();		
	}
 
	public void save(Table10 transientInstance) {
		log.debug("saving Table10 instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void saveBaseInfo(){
		try{
			for (int i = 1; i < 129; i++) {
				List<Table10> t10s=new ArrayList<Table10>();
				for (int j = 1; j < 129; j++) {
					Table10 t10=new Table10();
					if(j==0){
						t10.setField121(i+"一层设备");
						t10.setDtype(1);
						t10.setField3(i);
						t10.setField4(0);
					} else{
						t10.setField121(j+"二层设备");
						t10.setDtype(2);
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
				List<Table1> t1s=new ArrayList<Table1>();
				for (int k = 0; k < 17; k++) {
					Table1 t1=new Table1();
					if(k==0){//管理员
						t1.setField4("83aa400af464c76d");
						t1.setField3("设备管理员");
					}else{
						t1.setField4("49ba59abbe56e057");
						t1.setField3("用户"+k);
					}
					t1.setField6(1);//启用
					t1.setField7("");
					t1.setField8(1);
					t1.setField9("");
					t1.setField10(1);
					t1.setField11(i);
					
					t1s.add(t1);
				}
				getHibernateTemplate().saveOrUpdateAll(t10s);
				getHibernateTemplate().saveOrUpdateAll(t1s);
				getHibernateTemplate().flush();
				getHibernateTemplate().clear();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

 

	public static Table10DAO getFromApplicationContext(ApplicationContext ctx) {
		return (Table10DAO) ctx.getBean("Table10DAO");
	}
	
/*	public static void main(String[] args) {
		MD5Utils m5=new MD5Utils();
		System.out.println(m5.getMD5_16("12345678"));
	}*/
}
