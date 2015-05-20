package com.cdk.ats.web.action.login;


import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdk.ats.web.exception.UserExpiredException;
import com.cdk.ats.web.exception.UserNotFoundException;
import com.cdk.ats.web.exception.UserPwdErrorException;
import com.cdk.ats.web.pojo.hbm.Table1;

import common.cdk.config.files.sqlconfig.SqlConfig;
import common.cdk.cryption.md5.MD5Utils;

/****
 * 用户登录dao
 * 
 * @author cdk
 * 
 */
public class UserLoginDao extends HibernateDaoSupport {
	public static MD5Utils md5 = new MD5Utils();

	/***
	 * 
	 * @param name
	 *            登录用户名
	 * @param pwd
	 *            登录密码
	 * @return
	 * @throws UserExpiredException
	 *             用户过期异常
	 * @throws UserNotFoundException 用户不存在 
	 * @throws UserPwdErrorException 登录密码错误
	 */
	public UserInfo excuteLogin(String name, String pwd)
			throws UserExpiredException, UserNotFoundException, UserPwdErrorException ,JDBCException{
		UserInfo userinfo = null;
		Table1 tempUserInfo = null;

		/**
		 * 是普通可用用户，或是管理员
		 * */
		String loginSql1 = SqlConfig.SQL("ats.login.loginHQL1"); //"select password,username,unitcode,passdate,rolename,roleid from com.cdk.ats.web.pojo.hbm.Unituser where username =? ";
		Query query = this.getSession().createQuery(loginSql1);
		query.setString(0, name);
		Object resultObj = query.uniqueResult();
		if (resultObj != null) {
			tempUserInfo = (Table1) resultObj;
		} else throw new UserNotFoundException();
//		if (tempUserInfo != null&& tempUserInfo.getField4().equals(md5.getMD5_16(pwd))) {
		if (tempUserInfo != null&& tempUserInfo.getField4().equals(pwd)) {
			try{
			userinfo = new UserInfo(tempUserInfo.getField1(),
					tempUserInfo.getField3(),tempUserInfo.getField3(),
					tempUserInfo.getField4(),tempUserInfo.getField5(),					
					tempUserInfo.getField6(),tempUserInfo.getField7(),
					tempUserInfo.getField8(),tempUserInfo.getField9(),
					tempUserInfo.getField10(),tempUserInfo.getField11(),
					tempUserInfo.getField12(),tempUserInfo.getField13());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else throw new UserPwdErrorException();

		return userinfo;
	}
	/**
	 * 加载用户的权限信息
	 * @param userinfo
	 */
	public  void loadRight(UserInfo userinfo){
		
		
		
	}
	/***
	 * 修改登录密码
	 * @param name
	 * @param pwd
	 * @param newPwd
	 * @return
	 */
	public boolean updatePwd(String name,String pwd,String newPwd) {
		boolean end=false;
		try {
		int updateCount=0;
	/*	System.out.println(SqlConfig.SQL("hhy.login.updatepwd"));
		System.out.println(md5.getMD5_16(newPwd));
		System.out.println(name);
		System.out.println(md5.getMD5_16(pwd));*/
		//updateCount=getHibernateTemplate().bulkUpdate(SqlConfig.SQL("hhy.login.updatepwd"),new Object[] {md5.getMD5_16(newPwd),name,md5.getMD5_16(pwd)});
		updateCount=getHibernateTemplate().bulkUpdate(SqlConfig.SQL("ats.login.updatepwd"),new Object[] { newPwd,name,pwd});
		if(updateCount>0)end=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return end;
		
	}


	
	
/*	public static void main(String[] args) {
		System.out.println(UserLoginDao.md5.getMD5_16("123456"));
	}*/
}
