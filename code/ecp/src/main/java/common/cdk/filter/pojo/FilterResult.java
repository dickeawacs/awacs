package common.cdk.filter.pojo;

/***
 * 
 * @author dingkai
 * 
 */
public class FilterResult {
	private String toolName;

	private boolean pass = true;// if check path set 'true' ,else 'false',the  default true;

	private String forward;// forward to the URL
	
	private String redirect; //redirect to the URL

	public FilterResult(){}

	/***
	 * 
	 * @param toolName  tool name
	 * @param pass      if check path set 'true' ,else 'false',the  default true;
	 * @param forward  forward to the URL
	 * @param redirect redirect to the URL
	 */
	public FilterResult(String toolName, boolean pass, String forward,
			String redirect) {
		this.toolName = toolName;
		this.pass = pass;
		this.forward = forward;
		this.redirect = redirect;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public boolean isPass() {
		return pass;
	}
	public boolean getPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
	
	
 
	 
 
 
}
