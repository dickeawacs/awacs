package com.cdk.ats.web.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Table8;

/**
 * A data access object (DAO) providing persistence and search support for
 * Table8 entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.cdk.ats.web.pojo.hbm.Table8
 * @author MyEclipse Persistence Tools
 */

public class Table8DAO extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(Table8DAO.class);
	// property constants
	public static final String FIELD2 = "field2";
	public static final String FIELD3 = "field3";
	public static final String FIELD4 = "field4";
	public static final String FIELD5 = "field5";
	public static final String FIELD6 = "field6";
	public static final String FIELD8 = "field8";
	public static final String FIELD9 = "field9";

	protected void initDao() {
		// do nothing
	}

	public void save(Table8 transientInstance) {
		log.debug("saving Table8 instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Table8 persistentInstance) {
		log.debug("deleting Table8 instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Table8 findById(java.lang.Integer id) {
		log.debug("getting Table8 instance with id: " + id);
		try {
			Table8 instance = (Table8) getHibernateTemplate().get(
					"com.cdk.ats.web.pojo.hbm.Table8", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Table8 instance) {
		log.debug("finding Table8 instance by example");
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
		log.debug("finding Table8 instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Table8 as model where model."
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

	public List findByField8(Object field8) {
		return findByProperty(FIELD8, field8);
	}

	public List findByField9(Object field9) {
		return findByProperty(FIELD9, field9);
	}

	public List findAll() {
		log.debug("finding all Table8 instances");
		try {
			String queryString = "from Table8";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Table8 merge(Table8 detachedInstance) {
		log.debug("merging Table8 instance");
		try {
			Table8 result = (Table8) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Table8 instance) {
		log.debug("attaching dirty Table8 instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Table8 instance) {
		log.debug("attaching clean Table8 instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static Table8DAO getFromApplicationContext(ApplicationContext ctx) {
		return (Table8DAO) ctx.getBean("Table8DAO");
	}
}