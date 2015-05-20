package common.cdk.login.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import common.cdk.login.connections.ConnectionControl;
import common.cdk.login.connections.ConnectionLoad;
import common.filethread.file.FilePojo;
import common.filethread.thread.FileThread;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5699526583089522450L;
	private static String LecensePathName = "lecensePath";
	private static String LecenseDefaultPath = "WEB-INF/classes/webAppKey.pak";
	private static String SessionMaxInactiveInterval_Name = "SessionMaxInactiveInterval";
	

	public LoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("befor requst:"+request.getSession(false));
		out.println("  <br/>");
		out.print("    login: "+ConnectionControl.regLogin(request,null));
		out.println("  <br/>");
		request.getSession(true).setAttribute("userINfo", new Date().getTime());
		
		out.println("connection Number :"+ConnectionControl.getConnectionNumber());
		out.println("  <br/>");
		out.println("user total:"+ConnectionControl.getWebUserNumber());
		out.println("  <br/>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		String lecensePath = this.getServletConfig().getInitParameter(LecensePathName);
		String maxTime = this.getServletConfig().getInitParameter(SessionMaxInactiveInterval_Name);

		if (maxTime != null && !maxTime.trim().equals("")&& NumberUtils.isNumber(maxTime)) {
			ConnectionControl.setConnectionNumber(NumberUtils.createInteger(maxTime));
		}

		// 启动监听线程
		if (lecensePath != null && !lecensePath.equals("")) {
			LecenseDefaultPath = lecensePath.trim();
		}
		FilePojo msgFilepojo = new FilePojo();
		msgFilepojo.setLoadSwitch(1);// required ,if you need up to date,must
										// set 1
		msgFilepojo.setFilePath(FileThread.getProject_realPath()+ LecenseDefaultPath); // set file absolute path
		msgFilepojo.setDataprocess(new ConnectionLoad());// required ,
		msgFilepojo.setFileName("ConnectionControl");
		msgFilepojo.setFileType("pak");
		FileThread.addFilePojo(msgFilepojo);

	}

}
