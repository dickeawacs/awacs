package com.cdk.ats.web.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.pojo.hbm.EventRecord;
import com.cdk.ats.web.utils.AjaxResult;

/**
 * A data access object (DAO) providing persistence and search support for
 * EventRecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.cdk.ats.web.pojo.hbm.EventRecord
 * @author MyEclipse Persistence Tools
 */

public class EventRecordDAO extends HibernateDaoSupport {
	private static final Logger log=Logger.getLogger(EventRecordDAO.class);

	protected void initDao() {
		// do nothing
	}

	public void save(EventRecord transientInstance) {
		log.debug("saving EventRecord instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(EventRecord persistentInstance) {
		log.debug("deleting EventRecord instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EventRecord findById(java.lang.Integer id) {
		log.debug("getting EventRecord instance with id: " + id);
		try {
			EventRecord instance = (EventRecord) getHibernateTemplate().get(
					"com.cdk.ats.web.pojo.hbm.EventRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EventRecord instance) {
		log.debug("finding EventRecord instance by example");
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
		log.debug("finding EventRecord instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from EventRecord as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all EventRecord instances");
		try {
			String queryString = "from EventRecord";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public EventRecord merge(EventRecord detachedInstance) {
		log.debug("merging EventRecord instance");
		try {
			EventRecord result = (EventRecord) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(EventRecord instance) {
		log.debug("attaching dirty EventRecord instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EventRecord instance) {
		log.debug("attaching clean EventRecord instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
/*	private static final String query_current_sql=
"SELECT  DISTINCT u.EVENT_ID,  u.EQUIPMENT_FID,  u.EQUIPMENT_SID,  u.EVENT_DESC,  u.EVENT_Terminal,  u.EVENT_TYPE,  u.EVENT_TYPE_VAL,  u.EVENT_TIME,  u.PROCESS_BY,  u.PROCESS_UID,  u.PROCESS_TIME,  u.PROCESS_DESC,  u.PORT,  u.PTYPE,  u.ONREADY FROM ( "
+"SELECT * FROM (SELECT * FROM ats.event_records  ORDER BY  event_id DESC  LIMIT 0,20) t WHERE  (t.EVENT_TIME BETWEEN  DATE_ADD(SYSDATE() ,INTERVAL -1 MINUTE ) AND SYSDATE())  UNION ALL SELECT * FROM ats.event_records  ORDER BY  event_id DESC LIMIT 0,20) ulimit 0, 20";
	*/
	private static final String query_current_sql=
		"SELECT  DISTINCT EVENT_ID,  EQUIPMENT_FID,  EQUIPMENT_SID,  EVENT_DESC,  EVENT_Terminal,  EVENT_TYPE,  EVENT_TYPE_VAL,  EVENT_TIME,  PROCESS_BY,  PROCESS_UID,  PROCESS_TIME,  PROCESS_DESC,  PORT,  PTYPE,  ONREADY FROM ( "
		+"SELECT * ,1 AS 'onready'FROM (SELECT * FROM ats.event_records  ORDER BY  event_id DESC  LIMIT 0,20) t WHERE  (t.EVENT_TIME BETWEEN  DATE_ADD(SYSDATE() ,INTERVAL -1 MINUTE ) AND SYSDATE())  UNION ALL SELECT * ,0 AS 'onready'FROM ats.event_records  ORDER BY  event_id DESC LIMIT 0,20) u limit 0, 20";
	public AjaxResult findCurrentEvents(final AjaxResult page,final Long maxId){ 
		return (AjaxResult) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				DetachedCriteria dc= DetachedCriteria.forClass(EventRecord.class);
				/*if(maxId!=null&&maxId>0){
					dc.add(Restrictions.gt("eventId",maxId));
					dc.addOrder(Order.desc("eventId"));
					Criteria c=dc.getExecutableCriteria(arg0);
					page.setArray(c.list());
				}else{
					dc.addOrder(Order.desc("eventId"));
					Criteria c=dc.getExecutableCriteria(arg0);
					c.setFirstResult(0);
					c.setMaxResults(page.getLimit());
					page.setArray(c.list());
				}*/
				Criteria c=dc.getExecutableCriteria(arg0);
				if(maxId!=null&&maxId>0){
					dc.add(Restrictions.gt("eventId",maxId));
					dc.addOrder(Order.desc("eventId"));
					 
				}else{
					dc.addOrder(Order.desc("eventId"));
					c.setFirstResult(0);
					c.setMaxResults(page.getLimit());
				}
				page.setArray(c.list());
				return page;
			}
		});
		
	}
	public AjaxResult findCurrentEventSQL(final AjaxResult page,final String condition){ 
		return (AjaxResult) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {			 
				SQLQuery sqlQuery= arg0.createSQLQuery(query_current_sql);				
				sqlQuery.addScalar("EVENT_ID",Hibernate.LONG);
				sqlQuery.addScalar("EQUIPMENT_FID",Hibernate.INTEGER);
				sqlQuery.addScalar("EQUIPMENT_SID",Hibernate.INTEGER);
				sqlQuery.addScalar("EVENT_DESC",Hibernate.STRING);
				sqlQuery.addScalar("EVENT_Terminal",Hibernate.STRING);
				sqlQuery.addScalar("EVENT_TYPE",Hibernate.INTEGER);
				sqlQuery.addScalar("EVENT_TYPE_VAL",Hibernate.INTEGER);
				sqlQuery.addScalar("EVENT_TIME",Hibernate.TIMESTAMP);
				sqlQuery.addScalar("PROCESS_BY",Hibernate.STRING);
				sqlQuery.addScalar("PROCESS_UID",Hibernate.INTEGER);
				sqlQuery.addScalar("PROCESS_TIME",Hibernate.TIMESTAMP);
				sqlQuery.addScalar("PROCESS_DESC",Hibernate.STRING);
				sqlQuery.addScalar("PORT",Hibernate.INTEGER);
				sqlQuery.addScalar("PTYPE",Hibernate.INTEGER);
				sqlQuery.addScalar("ONREADY",Hibernate.INTEGER);
				List<Object[]> result=sqlQuery.list();
				List<EventRecord>  list=new 	ArrayList<EventRecord>(); 
				if(result!=null&&!result.isEmpty()){
					for (Object[] tempr : result) {
							EventRecord  tempe=new EventRecord();
							tempe.setEventId((Long) tempr[0]);
							tempe.setEquipmentFid((Integer) tempr[1]);
							tempe.setEquipmentSid((Integer) tempr[2]);
							tempe.setEventDesc((String) tempr[3]);
							tempe.setEventTerminal((String) tempr[4]);
							tempe.setEventType((Integer) tempr[5]);
							tempe.setEventTypeVal((Integer) tempr[6]);
							tempe.setEventTime((Timestamp) tempr[7]);
							tempe.setProcessBy((String) tempr[8]);
							tempe.setProcessUid((Integer) tempr[9]);
							tempe.setProcessTime((Timestamp) tempr[10]);
							tempe.setProcessDesc((String) tempr[11]);
							tempe.setPort((Integer) tempr[12]);
							tempe.setPtype((Integer) tempr[13]);
							tempe.setOnReady((Integer) tempr[14]);
							list.add(tempe);
					}
				}
				page.setArray(list);
				return page;
			}
		});
		
	}
	
	public AjaxResult findCurrentEventView(final AjaxResult page,final String condition){ 
		return (AjaxResult) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
			 
				Query hqlQuery= arg0.createQuery("from com.cdk.ats.web.pojo.hbm.EventRecord  "+condition+" order by eventId desc");				
				hqlQuery.setFirstResult(0);
				hqlQuery.setMaxResults(page.getLimit());
				page.setArray(hqlQuery.list());
				return page;
			}
		});
		
	}

	public AjaxResult findEventView(final AjaxResult page,final String condition){ 
		return (AjaxResult) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				String hql="select count(t.eventId) from com.cdk.ats.web.pojo.hbm.EventRecord t "+condition;
				Query count = arg0.createQuery(hql);
				Query hqlQuery= arg0.createQuery( "from com.cdk.ats.web.pojo.hbm.EventRecord  "+condition+" order by eventId desc");				
				page.setTotal(((Long) count.uniqueResult()).intValue());
				hqlQuery.setFirstResult(page.getStart());
				hqlQuery.setMaxResults(page.getLimit());
				page.setArray(hqlQuery.list());
				return page;
			}
		});
		
	}
	public List<Object[]> findExportData(final AjaxResult page,final String condition){ 
		return   (List<Object[]>)getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				String hql="select count(t.eventId) from com.cdk.ats.web.pojo.hbm.EventRecord t "+condition;
				Query count = arg0.createQuery(hql);
				page.setTotal(((Long) count.uniqueResult()).intValue());
				Query hqlQuery= arg0.createQuery( "select eventType ,	eventTime,	eventDesc,	eventTerminal,	processBy,	processTime,	processDesc from com.cdk.ats.web.pojo.hbm.EventRecord  "+condition+" order by eventId desc");
				if(page.getStart()>-1){
					hqlQuery.setFirstResult(page.getStart());
					hqlQuery.setMaxResults(page.getLimit());
				}
				return hqlQuery.list();
			}
		});
		
	}
	
	
	public static EventRecordDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (EventRecordDAO) ctx.getBean("EventRecordDAO");
	}
}