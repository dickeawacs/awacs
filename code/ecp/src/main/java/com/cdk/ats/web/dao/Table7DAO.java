package com.cdk.ats.web.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Table7;

/**
 * A data access object (DAO) providing persistence and search support for
 * Table7 entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.cdk.ats.web.pojo.hbm.Table7
 * @author MyEclipse Persistence Tools
 */

public class Table7DAO extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(Table7DAO.class);
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
	public static final String FIELD12 = "field12";
	public static final String FIELD13 = "field13";
	public static final String FIELD14 = "field14";
	public static final String FIELD15 = "field15";
	public static final String FIELD16 = "field16";
	public static final String FIELD17 = "field17";
	public static final String FIELD18 = "field18";
	public static final String FIELD19 = "field19";

	protected void initDao() {
		// do nothing
	}

	public void save(Table7 transientInstance) {
		log.debug("saving Table7 instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Table7 persistentInstance) {
		log.debug("deleting Table7 instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Table7 findById(java.lang.Integer id) {
		log.debug("getting Table7 instance with id: " + id);
		try {
			Table7 instance = (Table7) getHibernateTemplate().get(
					"com.cdk.ats.web.pojo.hbm.Table7", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Table7 instance) {
		log.debug("finding Table7 instance by example");
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
		log.debug("finding Table7 instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Table7 as model where model."
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

	public List findByField10(Object field10) {
		return findByProperty(FIELD10, field10);
	}

	public List findByField11(Object field11) {
		return findByProperty(FIELD11, field11);
	}

	public List findByField12(Object field12) {
		return findByProperty(FIELD12, field12);
	}

	public List findByField13(Object field13) {
		return findByProperty(FIELD13, field13);
	}

	public List findByField14(Object field14) {
		return findByProperty(FIELD14, field14);
	}

	public List findByField15(Object field15) {
		return findByProperty(FIELD15, field15);
	}

	public List findByField16(Object field16) {
		return findByProperty(FIELD16, field16);
	}

	public List findByField17(Object field17) {
		return findByProperty(FIELD17, field17);
	}

	public List findByField18(Object field18) {
		return findByProperty(FIELD18, field18);
	}

	public List findByField19(Object field19) {
		return findByProperty(FIELD19, field19);
	}

	public List findAll() {
		log.debug("finding all Table7 instances");
		try {
			String queryString = "from Table7";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Table7 merge(Table7 detachedInstance) {
		log.debug("merging Table7 instance");
		try {
			Table7 result = (Table7) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Table7 instance) {
		log.debug("attaching dirty Table7 instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Table7 instance) {
		log.debug("attaching clean Table7 instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static Table7DAO getFromApplicationContext(ApplicationContext ctx) {
		return (Table7DAO) ctx.getBean("Table7DAO");
	}
}