package common.cdk.login.connections;


public class Lisence {
	private String version;// 版本
	private String projectSequnce;// 项目编号 唯一
	private String ProjectName;// 产品名称
	private String maxUser;// 最大用户数量
	private String userTime; // 用户最在使用有效时间 -1 为永久 ，0为试用3个月
	private String endTime;//有效截止日期
	private String other1; // 其它属性1
	private String other2;// 其它属性2
	private String other3;// 其它属性3
 

 


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getProjectSequnce() {
		return projectSequnce;
	}


	public void setProjectSequnce(String projectSequnce) {
		this.projectSequnce = projectSequnce;
	}


	public String getProjectName() {
		return ProjectName;
	}


	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}


	public String getMaxUser() {
		return maxUser;
	}


	public void setMaxUser(String maxUser) {
		this.maxUser = maxUser;
	}


	public String getUserTime() {
		return userTime;
	}


	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getOther1() {
		return other1;
	}


	public void setOther1(String other1) {
		this.other1 = other1;
	}


	public String getOther2() {
		return other2;
	}


	public void setOther2(String other2) {
		this.other2 = other2;
	}


	public String getOther3() {
		return other3;
	}


	public void setOther3(String other3) {
		this.other3 = other3;
	}


	public String toString1() {
		String regx=";";
		return ProjectName+regx+maxUser+regx+endTime+regx+version+regx;
	}
}
