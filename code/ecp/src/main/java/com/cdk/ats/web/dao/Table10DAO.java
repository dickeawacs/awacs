package com.cdk.ats.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.Tree;
import common.cdk.config.files.sqlconfig.SqlConfig;

/**
 * A data access object (DAO) providing persistence and search support for
 * Table10 entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.cdk.ats.web.pojo.hbm.Table10
 * @author MyEclipse Persistence Tools
 */

public class Table10DAO extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(Table10DAO.class);
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
	public static final String FIELD20 = "field20";
	public static final String FIELD21 = "field21";
	public static final String FIELD22 = "field22";
	public static final String FIELD23 = "field23";
	public static final String FIELD24 = "field24";
	public static final String FIELD25 = "field25";
	public static final String FIELD26 = "field26";
	public static final String FIELD27 = "field27";
	public static final String FIELD28 = "field28";
	public static final String FIELD29 = "field29";
	public static final String FIELD30 = "field30";
	public static final String FIELD31 = "field31";
	public static final String FIELD32 = "field32";
	public static final String FIELD33 = "field33";
	public static final String FIELD34 = "field34";
	public static final String FIELD35 = "field35";
	public static final String FIELD36 = "field36";
	public static final String FIELD37 = "field37";
	public static final String FIELD38 = "field38";
	public static final String FIELD39 = "field39";
	public static final String FIELD40 = "field40";
	public static final String FIELD41 = "field41";
	public static final String FIELD42 = "field42";
	public static final String FIELD43 = "field43";
	public static final String FIELD44 = "field44";
	public static final String FIELD45 = "field45";
	public static final String FIELD46 = "field46";
	public static final String FIELD47 = "field47";
	public static final String FIELD48 = "field48";
	public static final String FIELD49 = "field49";
	public static final String FIELD50 = "field50";
	public static final String FIELD51 = "field51";
	public static final String FIELD52 = "field52";
	public static final String FIELD53 = "field53";
	public static final String FIELD54 = "field54";
	public static final String FIELD55 = "field55";
	public static final String FIELD56 = "field56";
	public static final String FIELD57 = "field57";
	public static final String FIELD58 = "field58";
	public static final String FIELD59 = "field59";
	public static final String FIELD60 = "field60";
	public static final String FIELD61 = "field61";
	public static final String FIELD62 = "field62";
	public static final String FIELD63 = "field63";
	public static final String FIELD64 = "field64";
	public static final String FIELD65 = "field65";
	public static final String FIELD66 = "field66";
	public static final String FIELD67 = "field67";
	public static final String FIELD68 = "field68";
	public static final String FIELD69 = "field69";
	public static final String FIELD70 = "field70";
	public static final String FIELD71 = "field71";
	public static final String FIELD72 = "field72";
	public static final String FIELD73 = "field73";
	public static final String FIELD74 = "field74";
	public static final String FIELD75 = "field75";
	public static final String FIELD76 = "field76";
	public static final String FIELD77 = "field77";
	public static final String FIELD78 = "field78";
	public static final String FIELD79 = "field79";
	public static final String FIELD80 = "field80";
	public static final String FIELD81 = "field81";
	public static final String FIELD82 = "field82";
	public static final String FIELD83 = "field83";
	public static final String FIELD84 = "field84";
	public static final String FIELD85 = "field85";
	public static final String FIELD86 = "field86";
	public static final String FIELD87 = "field87";
	public static final String FIELD88 = "field88";
	public static final String FIELD89 = "field89";
	public static final String FIELD90 = "field90";
	public static final String FIELD91 = "field91";
	public static final String FIELD92 = "field92";
	public static final String FIELD93 = "field93";
	public static final String FIELD94 = "field94";
	public static final String FIELD95 = "field95";
	public static final String FIELD96 = "field96";
	public static final String FIELD97 = "field97";
	public static final String FIELD98 = "field98";
	public static final String FIELD99 = "field99";
	public static final String FIELD100 = "field100";
	public static final String FIELD101 = "field101";
	public static final String FIELD102 = "field102";
	public static final String FIELD103 = "field103";
	public static final String FIELD104 = "field104";
	public static final String FIELD105 = "field105";
	public static final String FIELD106 = "field106";
	public static final String FIELD107 = "field107";
	public static final String FIELD108 = "field108";
	public static final String FIELD109 = "field109";
	public static final String FIELD110 = "field110";
	public static final String FIELD111 = "field111";
	public static final String FIELD112 = "field112";
	public static final String FIELD113 = "field113";
	public static final String FIELD114 = "field114";
	public static final String FIELD115 = "field115";
	public static final String FIELD116 = "field116";
	public static final String FIELD117 = "field117";
	public static final String FIELD118 = "field118";
	public static final String FIELD119 = "field119";
	public static final String FIELD120 = "field120";

	public void closeSession(){
		getSession().close();		
	}
	protected void initDao() {
		// do nothing
	}
	
	
	/***
	 * 更新电压、等状态字段信息
	 * @param   
	 * @return
	 */
	public int updateColumn(String hql,Object[] vals) {
		int end=0;
		//System.out.println(hql);
		log.debug("更新特殊字段信息");		
		try {
			end=getHibernateTemplate().bulkUpdate(hql, vals);
			if(end>0)
			log.debug("更新二层设备成功");
			else log.debug("更新二层设备失败，没有找到需要更新的数据");
			
		} catch (RuntimeException re) {
			log.error("操作失败", re);
			throw re;
		}
		return end;
	}
	/***
	 * 若二层设备不存在，则新增此二层设备，若存在则修改
	 * @param transientInstance
	 * @return
	 */
	public boolean saveOrUpdate(Table10 t10) {
		boolean end=false;
		log.debug("保存或更新二层设备");
		try {
			List<Table10> t10s=getHibernateTemplate().find(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{t10.getField3(),t10.getField4()});
			if(t10s!=null&&!t10s.isEmpty()&&t10s.size()>0){
				Table10 temp10=t10s.get(0);
				temp10.setField8(t10.getField8());
				temp10.setField9(t10.getField9());
				temp10.setField10(t10.getField10());
				temp10.setFip(t10.getFip());
				temp10.setFport(t10.getFport());
				temp10.setDtype(t10.getDtype());
				end=true;
			}else{
				getHibernateTemplate().save(t10);
				end=true;
			}
			log.debug("保存或更新二层设备成功");
		} catch (RuntimeException re) {
			log.error("操作失败", re);
			throw re;
		}
		return end;
	}
	/***
	 * 批量新增或更新
	 * @param equis
	 * @return
	 */
	public boolean saveOrUpdateAll(List<Table10> equis,String ip,Integer port ) {
		boolean end=false;
		try {
		//	List<Table10> updates=new ArrayList<Table10>();
			
			 for (int i = 0; i < equis.size(); i++) {
				 if(i==0){
				 Query query= this.getSession().createSQLQuery("UPDATE table_10_bak  t SET  t.fip=? ,t.fport=? WHERE t.field3=? AND t.field4=?");
					query.setString(0, ip);
					query.setInteger(1,port);
					query.setInteger(2,equis.get(i).getField3());
					query.setInteger(3,0);
					if(query.executeUpdate()>0){
						this.getSession().flush();
						//this.getSession().clear();
					}
				 
				 }
				 Table10 t10= equis.get(i);
				 //getHibernateTemplate().clear();
				    List<Table10> t10s=getHibernateTemplate().find(SqlConfig.SQL("ats.table10.query.saveBeforQuery"),new Object[]{t10.getField3(),t10.getField4()});
				    Table10 temp10=null;
					if(t10s!=null&&!t10s.isEmpty()&&t10s.size()>0&&t10s.get(0)!=null){
						//if(t10.getField8()==null)continue;
						 temp10=t10s.get(0);
						 t10.setField1(temp10.getField1());
						 temp10.copyU(t10);
						 getHibernateTemplate().clear();
						 //System.out.println("类型："+temp10.getDtype());
						 temp10.setField121(temp10.getField121()!=null&&!temp10.getField121().trim().equals("")?temp10.getField121():"设备"+temp10.getField3()+"-"+temp10.getField4());
						 getHibernateTemplate().update(temp10);
						 
					}else{
						temp10=new Table10();
						temp10.copyU(t10);
						temp10.setField121(temp10.getField121()!=null?temp10.getField121():"设备"+temp10.getField3()+"-"+temp10.getField4());
						getHibernateTemplate().clear();
						t10.setField1((Integer)getHibernateTemplate().save(temp10));
						//temp10.copyU(t10);
					}
					
			if(i%100==0) getHibernateTemplate().flush();
		 }
			log.debug("保存或更新1层设备成功");
		} catch (RuntimeException re) {
			log.error("操作失败", re);
			throw re;
		}finally{
			//getHibernateTemplate().clear();
			
		}
		return end;
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

	public void delete(Table10 persistentInstance) {
		log.debug("deleting Table10 instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Table10 findById(java.lang.Integer id) {
		log.debug("getting Table10 instance with id: " + id);
		try {
			Table10 instance = (Table10) getHibernateTemplate().get(
					"com.cdk.ats.web.pojo.hbm.Table10", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Table10 instance) {
		log.debug("finding Table10 instance by example");
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
		log.debug("finding Table10 instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Table10 as model where model."
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

	public List findByField20(Object field20) {
		return findByProperty(FIELD20, field20);
	}

	public List findByField21(Object field21) {
		return findByProperty(FIELD21, field21);
	}

	public List findByField22(Object field22) {
		return findByProperty(FIELD22, field22);
	}

	public List findByField23(Object field23) {
		return findByProperty(FIELD23, field23);
	}

	public List findByField24(Object field24) {
		return findByProperty(FIELD24, field24);
	}

	public List findByField25(Object field25) {
		return findByProperty(FIELD25, field25);
	}

	public List findByField26(Object field26) {
		return findByProperty(FIELD26, field26);
	}

	public List findByField27(Object field27) {
		return findByProperty(FIELD27, field27);
	}

	public List findByField28(Object field28) {
		return findByProperty(FIELD28, field28);
	}

	public List findByField29(Object field29) {
		return findByProperty(FIELD29, field29);
	}

	public List findByField30(Object field30) {
		return findByProperty(FIELD30, field30);
	}

	public List findByField31(Object field31) {
		return findByProperty(FIELD31, field31);
	}

	public List findByField32(Object field32) {
		return findByProperty(FIELD32, field32);
	}

	public List findByField33(Object field33) {
		return findByProperty(FIELD33, field33);
	}

	public List findByField34(Object field34) {
		return findByProperty(FIELD34, field34);
	}

	public List findByField35(Object field35) {
		return findByProperty(FIELD35, field35);
	}

	public List findByField36(Object field36) {
		return findByProperty(FIELD36, field36);
	}

	public List findByField37(Object field37) {
		return findByProperty(FIELD37, field37);
	}

	public List findByField38(Object field38) {
		return findByProperty(FIELD38, field38);
	}

	public List findByField39(Object field39) {
		return findByProperty(FIELD39, field39);
	}

	public List findByField40(Object field40) {
		return findByProperty(FIELD40, field40);
	}

	public List findByField41(Object field41) {
		return findByProperty(FIELD41, field41);
	}

	public List findByField42(Object field42) {
		return findByProperty(FIELD42, field42);
	}

	public List findByField43(Object field43) {
		return findByProperty(FIELD43, field43);
	}

	public List findByField44(Object field44) {
		return findByProperty(FIELD44, field44);
	}

	public List findByField45(Object field45) {
		return findByProperty(FIELD45, field45);
	}

	public List findByField46(Object field46) {
		return findByProperty(FIELD46, field46);
	}

	public List findByField47(Object field47) {
		return findByProperty(FIELD47, field47);
	}

	public List findByField48(Object field48) {
		return findByProperty(FIELD48, field48);
	}

	public List findByField49(Object field49) {
		return findByProperty(FIELD49, field49);
	}

	public List findByField50(Object field50) {
		return findByProperty(FIELD50, field50);
	}

	public List findByField51(Object field51) {
		return findByProperty(FIELD51, field51);
	}

	public List findByField52(Object field52) {
		return findByProperty(FIELD52, field52);
	}

	public List findByField53(Object field53) {
		return findByProperty(FIELD53, field53);
	}

	public List findByField54(Object field54) {
		return findByProperty(FIELD54, field54);
	}

	public List findByField55(Object field55) {
		return findByProperty(FIELD55, field55);
	}

	public List findByField56(Object field56) {
		return findByProperty(FIELD56, field56);
	}

	public List findByField57(Object field57) {
		return findByProperty(FIELD57, field57);
	}

	public List findByField58(Object field58) {
		return findByProperty(FIELD58, field58);
	}

	public List findByField59(Object field59) {
		return findByProperty(FIELD59, field59);
	}

	public List findByField60(Object field60) {
		return findByProperty(FIELD60, field60);
	}

	public List findByField61(Object field61) {
		return findByProperty(FIELD61, field61);
	}

	public List findByField62(Object field62) {
		return findByProperty(FIELD62, field62);
	}

	public List findByField63(Object field63) {
		return findByProperty(FIELD63, field63);
	}

	public List findByField64(Object field64) {
		return findByProperty(FIELD64, field64);
	}

	public List findByField65(Object field65) {
		return findByProperty(FIELD65, field65);
	}

	public List findByField66(Object field66) {
		return findByProperty(FIELD66, field66);
	}

	public List findByField67(Object field67) {
		return findByProperty(FIELD67, field67);
	}

	public List findByField68(Object field68) {
		return findByProperty(FIELD68, field68);
	}

	public List findByField69(Object field69) {
		return findByProperty(FIELD69, field69);
	}

	public List findByField70(Object field70) {
		return findByProperty(FIELD70, field70);
	}

	public List findByField71(Object field71) {
		return findByProperty(FIELD71, field71);
	}

	public List findByField72(Object field72) {
		return findByProperty(FIELD72, field72);
	}

	public List findByField73(Object field73) {
		return findByProperty(FIELD73, field73);
	}

	public List findByField74(Object field74) {
		return findByProperty(FIELD74, field74);
	}

	public List findByField75(Object field75) {
		return findByProperty(FIELD75, field75);
	}

	public List findByField76(Object field76) {
		return findByProperty(FIELD76, field76);
	}

	public List findByField77(Object field77) {
		return findByProperty(FIELD77, field77);
	}

	public List findByField78(Object field78) {
		return findByProperty(FIELD78, field78);
	}

	public List findByField79(Object field79) {
		return findByProperty(FIELD79, field79);
	}

	public List findByField80(Object field80) {
		return findByProperty(FIELD80, field80);
	}

	public List findByField81(Object field81) {
		return findByProperty(FIELD81, field81);
	}

	public List findByField82(Object field82) {
		return findByProperty(FIELD82, field82);
	}

	public List findByField83(Object field83) {
		return findByProperty(FIELD83, field83);
	}

	public List findByField84(Object field84) {
		return findByProperty(FIELD84, field84);
	}

	public List findByField85(Object field85) {
		return findByProperty(FIELD85, field85);
	}

	public List findByField86(Object field86) {
		return findByProperty(FIELD86, field86);
	}

	public List findByField87(Object field87) {
		return findByProperty(FIELD87, field87);
	}

	public List findByField88(Object field88) {
		return findByProperty(FIELD88, field88);
	}

	public List findByField89(Object field89) {
		return findByProperty(FIELD89, field89);
	}

	public List findByField90(Object field90) {
		return findByProperty(FIELD90, field90);
	}

	public List findByField91(Object field91) {
		return findByProperty(FIELD91, field91);
	}

	public List findByField92(Object field92) {
		return findByProperty(FIELD92, field92);
	}

	public List findByField93(Object field93) {
		return findByProperty(FIELD93, field93);
	}

	public List findByField94(Object field94) {
		return findByProperty(FIELD94, field94);
	}

	public List findByField95(Object field95) {
		return findByProperty(FIELD95, field95);
	}

	public List findByField96(Object field96) {
		return findByProperty(FIELD96, field96);
	}

	public List findByField97(Object field97) {
		return findByProperty(FIELD97, field97);
	}

	public List findByField98(Object field98) {
		return findByProperty(FIELD98, field98);
	}

	public List findByField99(Object field99) {
		return findByProperty(FIELD99, field99);
	}

	public List findByField100(Object field100) {
		return findByProperty(FIELD100, field100);
	}

	public List findByField101(Object field101) {
		return findByProperty(FIELD101, field101);
	}

	public List findByField102(Object field102) {
		return findByProperty(FIELD102, field102);
	}

	public List findByField103(Object field103) {
		return findByProperty(FIELD103, field103);
	}

	public List findByField104(Object field104) {
		return findByProperty(FIELD104, field104);
	}

	public List findByField105(Object field105) {
		return findByProperty(FIELD105, field105);
	}

	public List findByField106(Object field106) {
		return findByProperty(FIELD106, field106);
	}

	public List findByField107(Object field107) {
		return findByProperty(FIELD107, field107);
	}

	public List findByField108(Object field108) {
		return findByProperty(FIELD108, field108);
	}

	public List findByField109(Object field109) {
		return findByProperty(FIELD109, field109);
	}

	public List findByField110(Object field110) {
		return findByProperty(FIELD110, field110);
	}

	public List findByField111(Object field111) {
		return findByProperty(FIELD111, field111);
	}

	public List findByField112(Object field112) {
		return findByProperty(FIELD112, field112);
	}

	public List findByField113(Object field113) {
		return findByProperty(FIELD113, field113);
	}

	public List findByField114(Object field114) {
		return findByProperty(FIELD114, field114);
	}

	public List findByField115(Object field115) {
		return findByProperty(FIELD115, field115);
	}

	public List findByField116(Object field116) {
		return findByProperty(FIELD116, field116);
	}

	public List findByField117(Object field117) {
		return findByProperty(FIELD117, field117);
	}

	public List findByField118(Object field118) {
		return findByProperty(FIELD118, field118);
	}

	public List findByField119(Object field119) {
		return findByProperty(FIELD119, field119);
	}

	public List findByField120(Object field120) {
		return findByProperty(FIELD120, field120);
	}
	/***
	 * 获取table9下的子节点
	 * @param hql
	 * @param t9s
	 * @return
	 */
	public List<Tree> findChildren(String queryString, List<Tree> t9s){
		log.debug("find table9 children");
		for (int i = 0; i < t9s.size(); i++) {				
		try {
				List<Object[]> t10s= getHibernateTemplate().find(queryString,t9s.get(i).getId());
				List<Tree> child=new ArrayList<Tree>(); 
				for (int j = 0; j <t10s.size(); j++) {
					Tree temp=new Tree();
					if(t10s.get(j)[0]==null&&!NumberUtils.isNumber(t10s.get(j)[0].toString()))continue;
					temp.setId(NumberUtils.createInteger(t10s.get(j)[0].toString()));
					temp.setParentid(t9s.get(i).getId());
					temp.setLeaf(true);
					temp.setText(t10s.get(j)[0]+"-"+t10s.get(j)[1]);					
					child.add(temp);
				}
				if(child.size()>0) t9s.get(i).setLeaf(false);
				t9s.get(i).setChildren(child);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			//throw re;
		}
		}
		return t9s;
	}
	public List findAll() {
		log.debug("finding all Table10 instances");
		try {
			String queryString = "from Table10";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findAll(String queryString,Object[] values) {
		log.debug("finding all Table10 instances");
		try {
			return getHibernateTemplate().find(queryString,values);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public Table10 merge(Table10 detachedInstance) {
		log.debug("merging Table10 instance");
		try {
			Table10 result = (Table10) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Table10 instance) {
		log.debug("attaching dirty Table10 instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Table10 instance) {
		log.debug("attaching clean Table10 instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static Table10DAO getFromApplicationContext(ApplicationContext ctx) {
		return (Table10DAO) ctx.getBean("Table10DAO");
	}
}