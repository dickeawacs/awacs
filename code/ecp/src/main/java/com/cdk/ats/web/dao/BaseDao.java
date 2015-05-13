package com.cdk.ats.web.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Table1;

/***
 * 基础dao
 * 
 * @author dingkai
 * 
 */
public class BaseDao extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(BaseDao.class);

	/***
	 * 根据hql查询，返回唯一的结果
	 * 
	 * @param hql
	 * @return
	 */
	public Object findOnlyByHql(String hql) {
		log.debug("finding by hql:" + hql);
		try {
			List list = getHibernateTemplate().find(hql);
			if (list.isEmpty())
				return null;
			else
				return list.get(0);
		} catch (RuntimeException re) {
			log.error("find by hql failed", re);
			throw re;
		}
	}

	/***
	 * 根据hql查询，返回唯一的结果
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Object findOnlyByHql(String hql, Object[] values) {
		log.debug("finding by hql:" + hql);
		try {
			List list = getHibernateTemplate().find(hql, values);
			if (list.isEmpty())
				return null;
			else
				return list.get(0);
		} catch (RuntimeException re) {
			log.error("find by hql failed", re);
			throw re;
		}
	}

	/***
	 * 根据hql 查询结果集
	 * 
	 * @param hql
	 * @return
	 */
	public List findObjectsByHql(String hql) {
		log.debug("finding by hql:" + hql);
		try {
			return getHibernateTemplate().find(hql);
		} catch (RuntimeException re) {
			log.error("find by hql failed", re);
			throw re;
		}

	}

	/***
	 * 根据hql 查询结果集
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public List findObjectsByHql(String hql, Object[] values) {
		log.debug("finding by hql:" + hql);
		try {
			return getHibernateTemplate().find(hql, values);
		} catch (RuntimeException re) {
			log.error("find by hql failed", re);
			throw re;
		}

	}
/***
 * 根据 id 查询实体信息
 * @param classString  
 * @param id
 * @return
 */
	public Object findById(String classString, java.lang.Integer id) {
		log.debug("getting “"+classString+"” instance with id: " + id);
		try {
			return getHibernateTemplate().get(classString,id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	/***
	 * 根据 hql执行修改
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public int updateByHql(String hql, Object[] values) {

		log.debug("update by hql:" + hql);
		try {
			return getHibernateTemplate().bulkUpdate(hql, values);
		} catch (RuntimeException re) {
			log.error("update by hql failed", re);
			throw re;
		}
	}
	/***
	 * 根据 hql执行修改
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public int updateByHqlForInit(String hql, Object[] values,int one,int two) {
		log.debug("update by hql:" + hql);
		try {
			
			return getHibernateTemplate().bulkUpdate(hql, values);
		} catch (RuntimeException re) {
			log.error("update by hql failed", re);
			throw re;
		}
	}

	/***
	 * 根据 hql执行修改
	 * 
	 * @param hql
	 * @return
	 */
	public int updateByHql(String hql) {

		log.debug("update by hql:" + hql);
		try {
			return getHibernateTemplate().bulkUpdate(hql);
		} catch (RuntimeException re) {
			log.error("update by hql failed", re);
			throw re;
		}
	}

	/***
	 * 根据sql执行修改
	 * @param sql
	 * @return
	 */
	public int updateBySql(String sql) {
		log.debug("update by sql: " + sql);
		try {
			SQLQuery query = getSession().createSQLQuery(sql);
			return query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("update by hql failed", re);
			throw re;
		}

	}
	public void updateObject(String entityName,Object entity){
		log.debug("update : " + entityName);
		try {
			getHibernateTemplate().update(entityName, entity);
		} catch (RuntimeException re) {
			log.error("update "+entityName+" failed", re);
			throw re;
		}
		
	}
	/***
	 * 根据sql执行删除
	 * @param sql
	 * @return
	 */
	public int deleteByhql(String hql) {
		log.debug("delete by sql: " + hql);
		try {
			 
			return  getHibernateTemplate().bulkUpdate(hql);
		} catch (RuntimeException re) {
			log.error("delete by hql failed", re);
			throw re;
		}

	}
	/***
	 * 根据sql执行删除
	 * @param sql
	 * @return
	 */
	public int deleteByhql(String hql ,Object[] values) {
		log.debug("delete by sql: " + hql);
		try {
			 
			return  getHibernateTemplate().bulkUpdate(hql, values);
		} catch (RuntimeException re) {
			log.error("delete by hql failed", re);
			throw re;
		}

	}
 
	/**
	 * 
	 * 描述：  保存或修改对象 
	 * @createBy dingkai
	 * @createDate 2013-12-11
	 * @lastUpdate 2013-12-11
	 * @param obj
	 */
	public void saveOrUpdate(Object obj){
		log.debug("excute save!!!");
		try {
			getHibernateTemplate().saveOrUpdate(obj);
		} catch (RuntimeException re) {
			log.error("save  failed ", re);
			throw re;
		}
		
	
		
	}
	/**
	 * 
	 * 描述： 保存，并返回 主键 
	 * @createBy dingkai
	 * @createDate 2013-12-11
	 * @lastUpdate 2013-12-11
	 * @param obj
	 * @return
	 */
	public Serializable save(Object obj){
		log.debug("excute save!!!");
		try {
			return getHibernateTemplate().save(obj);
		} catch (RuntimeException re) {
			log.error("save  failed ", re);
			throw re;
		}
		
	
		
	}
	/**
	 * 保存
	 * @param obj
	 */
	public void saveOrUpdateAll(List array){
		log.debug("excute save!!!");
		try {
			getHibernateTemplate().saveOrUpdateAll(array);
		} catch (RuntimeException re) {
			log.error("save  failed ", re);
			throw re;
		}
		
	
		
	}

	public void closeSession(){
		if(getSession()!=null)
		getSession().close();		
	}
}
