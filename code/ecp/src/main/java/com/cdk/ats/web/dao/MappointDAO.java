package com.cdk.ats.web.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Mappoint;

/**
 * A data access object (DAO) providing persistence and search support for
 * Mappoint entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.cdk.ats.web.pojo.hbm.Mappoint
 * @author MyEclipse Persistence Tools
 */

public class MappointDAO extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(MappointDAO.class);

	protected void initDao() {
		// do nothing
	}

	public void save(Mappoint transientInstance) {
		log.debug("saving Mappoint instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Mappoint persistentInstance) {
		log.debug("deleting Mappoint instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Mappoint findById(java.lang.Integer id) {
		log.debug("getting Mappoint instance with id: " + id);
		try {
			Mappoint instance = (Mappoint) getHibernateTemplate().get(
					"com.cdk.ats.web.pojo.hbm.Mappoint", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Mappoint instance) {
		log.debug("finding Mappoint instance by example");
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
		log.debug("finding Mappoint instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Mappoint as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Mappoint instances");
		try {
			String queryString = "from Mappoint";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Mappoint merge(Mappoint detachedInstance) {
		log.debug("merging Mappoint instance");
		try {
			Mappoint result = (Mappoint) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Mappoint instance) {
		log.debug("attaching dirty Mappoint instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Mappoint instance) {
		log.debug("attaching clean Mappoint instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MappointDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MappointDAO) ctx.getBean("MappointDAO");
	}
}