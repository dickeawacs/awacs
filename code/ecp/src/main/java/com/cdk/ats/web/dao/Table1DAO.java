package com.cdk.ats.web.dao;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Table1;
import common.cdk.config.files.sqlconfig.SqlConfig;

/**
 * A data access object (DAO) providing persistence and search support for
 * Table1 entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.cdk.ats.web.pojo.hbm.Table1
 * @author MyEclipse Persistence Tools
 */

public class Table1DAO extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(Table1DAO.class);
	// property constants
	public static final String FIELD2 = "field2";
	public static final String FIELD3 = "field3";
	public static final String FIELD4 = "field4";
	public static final String FIELD5 = "field5";
	public static final String FIELD6 = "field6";
	public static final String FIELD7 = "field7";
	public static final String FIELD8 = "field8";
	public static final String FIELD9 = "field9";
	public static final String FIELD10 = "field10";
	public static final String FIELD11 = "field11";

	protected void initDao() {
		// do nothing
	}
	/***
	 * 保存设备传入的用户密码（暂时没有加密）
	 * @param t1
	 */
	public boolean saveOrUpdate(Table1 t1) {
		boolean end=false;
		 
		try {
			List<Table1> t1s=getHibernateTemplate().find(SqlConfig.SQL("ats.table1.query.0x10.0x05"),new Object[]{t1.getField11(),t1.getField12()});
			if(t1s!=null&&t1s.size()>0){				
				//int count=getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.table1.update.0x10.0x05"),new Object[]{new MD5Utils().getMD5_16(t1.getField4()), t1.getField11(),t1.getField12()});
				int count=getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.table1.update.0x10.0x05"),new Object[]{t1.getField4(), t1.getField11(),t1.getField12()});
				if(count>0)end=true;
			}else{
				/*t1.setField4(new MD5Utils().getMD5_16(t1.getField4()));*/
				Object key=getHibernateTemplate().save(t1);
				if(key!=null&&NumberUtils.isNumber(key.toString()))end=true;
			}
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return end;
	}
	
	/***
	 * 批量新增或修改
	 * @param users
	 * @return
	 */
	public boolean saveOrUpdateAll(List<Table1> users) {
		boolean end = false;

		try {
			if(users!=null){
			for (int i = 0; i < users.size(); i++) {
					Table1 t1 = users.get(i);
					try{
					t1.setField1((Integer)getHibernateTemplate().save(t1));
					getHibernateTemplate().clear();
				}catch(ConstraintViolationException ex){
					System.out.println("error:"+t1.getField3());
					ex.printStackTrace();
					
				}
			}
			log.debug("保存或更新用户信息成功");
			}
		} catch (RuntimeException re) {
			log.debug("保存或更新用户信息失败");
			log.error("save  failed", re);
			throw re;
		}
		return end;
	}
	public void save(Table1 transientInstance) {
		log.debug("saving Table1 instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Table1 persistentInstance) {
		log.debug("deleting Table1 instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public int deleteById(java.lang.Integer id) {
		log.debug("getting Table1 instance with id: " + id);
		try {
			
			 int count=getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.del.Table10.one.users"), id );
			 getHibernateTemplate().flush();
			 return count;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	public Table1 findById(java.lang.Integer id) {
		log.debug("getting Table1 instance with id: " + id);
		try {
			Table1 instance = (Table1) getHibernateTemplate().get(
					"com.cdk.ats.web.pojo.hbm.Table1", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	

	public List findByExample(Table1 instance) {
		log.debug("finding Table1 instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Table1 instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Table1 as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByField2(Object field2) {
		return findByProperty(FIELD2, field2);
	}

	public List findByField3(Object field3) {
		return findByProperty(FIELD3, field3);
	}

	public List findByField4(Object field4) {
		return findByProperty(FIELD4, field4);
	}

	public List findByField5(Object field5) {
		return findByProperty(FIELD5, field5);
	}

	public List findByField6(Object field6) {
		return findByProperty(FIELD6, field6);
	}

	public List findByField7(Object field7) {
		return findByProperty(FIELD7, field7);
	}

	public List findByField8(Object field8) {
		return findByProperty(FIELD8, field8);
	}

	public List findByField9(Object field9) {
		return findByProperty(FIELD9, field9);
	}

	public List findByField11(Object field10) {
		return findByProperty(FIELD11, field10);
	}

	public List findAll() {
		log.debug("finding all Table1 instances");
		try {
			String queryString = "from Table1";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Table1 merge(Table1 detachedInstance) {
		log.debug("merging Table1 instance");
		try {
			Table1 result = (Table1) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Table1 instance) {
		log.debug("attaching dirty Table1 instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Table1 instance) {
		log.debug("attaching clean Table1 instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static Table1DAO getFromApplicationContext(ApplicationContext ctx) {
		return (Table1DAO) ctx.getBean("Table1DAO");
	}
	public void closeSession(){
		getSession().close();		
	}
}