package com.cdk.ats.udp.webplug.dao;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.dao.EventRecordDAO;
import com.cdk.ats.web.dao.SystemDao;
import com.cdk.ats.web.dao.Table10DAO;
import com.cdk.ats.web.dao.Table1DAO;
import com.cdk.ats.web.dao.Table2DAO;
import com.cdk.ats.web.dao.Table3DAO;
import com.cdk.ats.web.dao.Table5DAO;
import com.cdk.ats.web.dao.Table6DAO;
import com.cdk.ats.web.dao.Table7DAO;
import com.cdk.ats.web.dao.Table8DAO;

/***
 * dao工厂，它使用spring 的WebApplicationContext 容器获取其中的bean.
 * 调用的是web容器启动时spring初始化的bean容器
 * 
 * @author cdk
 * 
 */
public class DaoFactory {
	private static String BaseDaoName="BaseDao";
	private static String sysdaoName="sysDao";
	private static String table1DaoName = "Table1DAO";
	private static String table2DaoName = "Table2DAO";
	private static String table3DaoName = "Table3DAO";
	private static String eventRecordDAOName = "EventRecordDAO";
	private static String table5DaoName = "Table5DAO";
	private static String table6DaoName = "Table6DAO";
	private static String table7DaoName = "Table7DAO";
	private static String table8DaoName = "Table8DAO";
	private static String table9DaoName = "Table9DAO";
	private static String table10DaoName = "Table10DAO";

	/***
	 * 根据bean id 获取bean
	 * 
	 * @param daoName
	 * @return
	 */
	public static Object getDao(String daoName) {
		WebApplicationContext context = ContextLoader
				.getCurrentWebApplicationContext();
		return context.getBean(daoName);

	}
	/*** 获取一个由spring实例化的 BaseDao ***/
	public static SystemDao getSysDao() {
		SystemDao dao = (SystemDao) DaoFactory.getDao(sysdaoName);
		return dao;
	}
	/*** 获取一个由spring实例化的 BaseDao ***/
	public static BaseDao getBaseDao() {
		BaseDao dao = (BaseDao) DaoFactory.getDao(BaseDaoName);
		return dao;
	}
	/*** 获取一个由spring实例化的table1dao ***/
	public static Table1DAO getTable1Dao() {
		Table1DAO dao = (Table1DAO) DaoFactory.getDao(table1DaoName);
		return dao;
	}

	/*** 获取一个由spring实例化的table2dao ***/
	public static Table2DAO getTable2Dao() {
		Table2DAO dao = (Table2DAO) DaoFactory.getDao(table2DaoName);
		return dao;
	}

	/*** 获取一个由spring实例化的table3dao ***/
	public static Table3DAO getTable3Dao() {
		Table3DAO dao = (Table3DAO) DaoFactory.getDao(table3DaoName);
		return dao;
	}

	/*** 获取一个由spring实例化的table4dao ***/
	public static EventRecordDAO getTable4Dao() {
		EventRecordDAO dao = (EventRecordDAO) DaoFactory.getDao(eventRecordDAOName);
		return dao;
	}

	/*** 获取一个由spring实例化的table5dao ***/
	public static Table5DAO getTable5Dao() {
		Table5DAO dao = (Table5DAO) DaoFactory.getDao(table5DaoName);
		return dao;
	}

	/*** 获取一个由spring实例化的table6dao ***/
	public static Table6DAO getTable6Dao() {
		Table6DAO dao = (Table6DAO) DaoFactory.getDao(table6DaoName);
		return dao;
	}

	/*** 获取一个由spring实例化的table7dao ***/
	public static Table7DAO getTable7Dao() {
		Table7DAO dao = (Table7DAO) DaoFactory.getDao(table7DaoName);
		return dao;
	}

	/*** 获取一个由spring实例化的table8dao ***/
	public static Table8DAO getTable8Dao() {
		Table8DAO dao = (Table8DAO) DaoFactory.getDao(table8DaoName);
		return dao;
	}

	/*** 获取一个由spring实例化的table9dao ***/
	/*public static Table9DAO getTable9Dao() {
		Table9DAO dao = (Table9DAO) DaoFactory.getDao(table9DaoName);
		return dao;
	}*/

	/*** 获取一个由spring实例化的table10dao ***/
	public static Table10DAO getTable10Dao() {
		Table10DAO dao = (Table10DAO) DaoFactory.getDao(table10DaoName);
		return dao;
	}

}
