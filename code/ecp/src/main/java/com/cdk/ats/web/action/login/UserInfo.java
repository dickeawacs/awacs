package com.cdk.ats.web.action.login;

import java.io.Serializable;
import java.util.List;

import com.cdk.ats.web.pojo.hbm.Table2;

public class UserInfo implements  Serializable{
 
	private static final long serialVersionUID = -2081904790096260234L;
	
	private Integer id;//用户序号
	private String userName;//真实姓名
	private String loginName;//帐号
	private String loginPwd; //密码
	private Integer level;//用户级别
	private Integer use=0 ;//用户是否启用
	private String tel;//电话号码
	private Integer telUse;//电话是否启用
	private String phone;//短信号码
	private Integer phoneUse;//短信是否启用
	private List<Table2> rights;//用户权限 
	private Integer addres;//一层设备 地址
	private Integer ptype;//一层设备类型
	private Integer userCode;//用户
	private int dev=0;//编程标记
	
	
	public UserInfo(Integer id, String userName, String loginName,
			String loginPwd, Integer level, Integer use, String tel,
			Integer telUse, String phone, Integer phoneUse ,Integer  addres,Integer userCode,Integer ptype) {
		this.id = id;
		this.userName = userName;
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.level = level;
		this.use = use;
		this.tel = tel;
		this.telUse = telUse;
		this.phone = phone;
		this.phoneUse = phoneUse;
		this.addres=addres;
		this.ptype=ptype;
		this.userCode=userCode;
		
	}

	public UserInfo() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getUse() {
		return use;
	}

	public void setUse(Integer use) {
		this.use = use;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getTelUse() {
		return telUse;
	}

	public void setTelUse(Integer telUse) {
		this.telUse = telUse;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPhoneUse() {
		return phoneUse;
	}

	public void setPhoneUse(Integer phoneUse) {
		this.phoneUse = phoneUse;
	}

	public List<Table2> getRights() {
		return rights;
	}

	public void setRights(List<Table2> rights) {
		this.rights = rights;
	}

	public String toString(){
		return this.id+this.userName+this.loginName+this.loginPwd;
	}
 
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public Integer getAddres() {
		return addres;
	}

	public void setAddres(Integer addres) {
		this.addres = addres;
	}

	public Integer getUserCode() {
		return userCode;
	}

	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}

	public Integer getPtype() {
		return ptype;
	}

	public int getDev() {
		return dev;
	}

	public void setDev(int dev) {
		this.dev = dev;
	}

	
}
