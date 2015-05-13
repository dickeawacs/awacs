package com.cdk.ats.web.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Map;

/**
 * A data access object (DAO) providing persistence and search support for Map
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.cdk.ats.web.pojo.hbm.Map
 * @author MyEclipse Persistence Tools
 */

public class MapDAO extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(MapDAO.class);

	protected void initDao() {
		// do nothing
	}

	public void save(Map transientInstance) {
		log.debug("saving Map instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Map persistentInstance) {
		log.debug("deleting Map instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Map findById(java.lang.Integer id) {
		log.debug("getting Map instance with id: " + id);
		try {
			Map instance = (Map) getHibernateTemplate().get(
					"com.cdk.ats.web.pojo.hbm.Map", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Map instance) {
		log.debug("finding Map instance by example");
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
		log.debug("finding Map instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Map as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Map instances");
		try {
			String queryString = "from Map";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Map merge(Map detachedInstance) {
		log.debug("merging Map instance");
		try {
			Map result = (Map) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Map instance) {
		log.debug("attaching dirty Map instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Map instance) {
		log.debug("attaching clean Map instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MapDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MapDAO) ctx.getBean("MapDAO");
	}
}