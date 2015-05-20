package com.cdk.ats.web.action.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.JDBCException;

import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.exception.UserExpiredException;
import com.cdk.ats.web.exception.UserNotFoundException;
import com.cdk.ats.web.exception.UserPwdErrorException;
import com.cdk.ats.web.utils.AjaxResult;
import common.cdk.config.files.msgconfig.MsgConfig;
import common.cdk.login.connections.ConnectionControl;

/***
 * 
 * @author cdk 系统登录
 */
public class UserLoginAction {
	private static Logger logger = Logger.getLogger(UserLoginAction.class);
	private String name;// 登录名
	private String pwd;// 密码
	private String authCode;// 登录验证码
	private String newPwd;// 新密码（修改密码时使用）
	private String confirmPwd;// 确认密码（修改密码时使用）
	private UserLoginDao loginDao;//
	private String inputRegex = "[^`~!@#$%^&*()+=|{}\":;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+";

	/***
	 * 获取当前登录的用户信息
	 * 
	 * @return
	 */
	public static UserInfo getLoginUser() {

		return (UserInfo) ServletActionContext.getRequest().getSession()
				.getAttribute("UserInfo");
	}

	/***
	 * 登录
	 * 
	 * @return
	 * @throws IOException
	 */
	public String login() throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			if (name != null && pwd != null && !name.trim().equals("")
					&& name.matches(inputRegex) && !pwd.trim().equals("")
					&& pwd.matches(inputRegex)) {
				UserInfo userInfo = loginDao.excuteLogin(name.trim(), pwd
						.trim());
				if (ConnectionControl.regLogin(ServletActionContext
						.getRequest(), userInfo.getId().toString())) {
					loginDao.loadRight(userInfo);
					ServletActionContext.getRequest().getSession()
							.setAttribute("UserInfo", userInfo);
					if (Integer.valueOf(Constant.ROLE_SYS_ADMIN).equals(userInfo.getLevel())
							|| Integer.valueOf(Constant.ROLE_ADMIN).equals(userInfo.getLevel()))
						result.setReturnVal("adminMain.action");
					else
						//userMain.action
						result.setReturnVal("adminMain.action");
					result.isSuccess();
					result.setMessage(MsgConfig.msg("ats.login.success"));
				} else {
					throw new AtsException(
							"此用户已在其它位置登录，请先退出登录.若是原来界面已经意外关闭，请等待约10分钟后再登录");
				}
			} else {
				result.isFailed();
				result.setMessage(MsgConfig.msg("ats.login.inputError"));
			}
		} catch (AtsException e) {
			result.isFailed();
			result.setMessage(e.getMessage());
		} catch (JDBCException e) {
			result.isFailed();
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(),e);
		} catch (UserExpiredException e) {
			result.isFailed();
			result.setMessage(e.getMessage());
		} catch (UserNotFoundException e) {
			result.isFailed();
			result.setMessage(e.getMessage());
		} catch (UserPwdErrorException e) {
			result.isFailed();
			result.setMessage(e.getMessage());
		} finally {
			ServletActionContext.getResponse().getWriter().write(
					result.toJsonString());
		}
		return null;
	}

	/*
	 * public static void main(String[] args) { String
	 * x="[^`~!@#$%^&*()+=|{}:;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+";
	 * System.out.println("1adkfslakfjsadlkfj1".matches(x));
	 * System.out.println("123'".matches(x));
	 * System.out.println("量(顶)戴顶戴".matches(x));
	 * System.out.println("!kdjflaksdjflasdkjf".matches(x));
	 * System.out.println("123顶djfld!量顶jfl量顶".matches(x)); }
	 */

	/****
	 * 修改密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updatePwd() throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			Object temp=ServletActionContext.getRequest().getSession().getAttribute("UserInfo");
			if(temp==null){
				throw new UserExpiredException("请先登录系统！");
			}
				UserInfo loginUser=(UserInfo)temp;
				if(loginUser.getLevel()!=1){
					throw new UserExpiredException("对不起只有系统管理员才能修改密码！");
				}
				validateParams();
				UserInfo user= loginDao.excuteLogin(loginUser.getLoginName(), pwd);
			  if(user==null){
				throw new UserExpiredException("用户不存在");
			} 
				  if(user.getLevel()!=1){
						throw new UserExpiredException("对不起只有系统管理员才能修改密码！");
					}
			 
				if (loginDao.updatePwd(loginUser.getLoginName(), pwd, newPwd)) {
					result.isSuccess().setMessage("密码修改成功");
				} else
					{result.isFailed().setMessage("密码修改失败");}
			 
		} catch (UserExpiredException e) {
			result.isFailed().setMessage(e.getMessage());
		} catch (UserNotFoundException e) {
			result.isFailed().setMessage(e.getMessage());
		} catch (UserPwdErrorException e) {
			result.isFailed().setMessage(e.getMessage());
		} finally {
			result.writeToJsonString();
		}
		return null;
	}
	/**
	 * 
	 * 描述：修改管理员密码
	 * 
	 * 添加了js，和action方法 ，还没有添加处理类
	 * @createBy dingkai
	 * @createDate 2014-4-26
	 * @lastUpdate 2014-4-26
	 * @return
	 * @throws IOException
	 */
	public String resetAdminPwd() throws IOException{ 
		return this.updatePwd();
	}
	/**
	 * 
	 * 描述： 验证：密码、
	 * @createBy dingkai
	 * @createDate 2014-4-26
	 * @lastUpdate 2014-4-26
	 * @return
	 * @throws AtsException
	 * @throws UserExpiredException 
	 */
	private boolean validateParams() throws  UserExpiredException{
		  if (pwd == null || pwd.trim().equals("")
				|| !pwd.matches(inputRegex)) {
			throw new  UserExpiredException("密码无效");
		} else if (newPwd == null || newPwd.trim().equals("")
				|| !newPwd.matches(inputRegex)) {
			throw new  UserExpiredException("新密码无效");
		} else if (confirmPwd == null || confirmPwd.trim().equals("")
				|| !confirmPwd.matches(inputRegex)) {
			throw new  UserExpiredException("确认密码无效");
		} else if (!newPwd.trim().equals(confirmPwd.trim())) {
			throw new  UserExpiredException("新密码与确认密码不匹配");
		} 
		return true;
	}
	
	/***
	 * 用户退出
	 * 
	 * @return
	 * @throws IOException
	 */
	public String quit() throws IOException {
		AjaxResult result = new AjaxResult();
		ConnectionControl.sessionInvalidate(ServletActionContext.getRequest(),
				getLoginUser().toString());
		ServletActionContext.getRequest().getSession().invalidate();
		result.isSuccess();
		result.writeToJsonString();
		return null;
	}

	public UserLoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(UserLoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

}
