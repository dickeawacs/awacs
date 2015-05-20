<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>"></base>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<title>${appInfo.webAppName}</title> 
<link rel="stylesheet" href="pub/login/css/login.css" type="text/css"></link> 
<script type="text/javascript" src="pub/login/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="pub/login/js/login.js"></script>
</head>
<script type="text/javascript">
$(function(){
	$("#userName").keypress(function(event){
		if(event.keyCode==13){
			$("#password").select();
		}
	}).select();
	$("#password").keypress(function(event){
		if(event.keyCode==13){
			 $("#subButton").click();
		}
	});
})
</script>
<body>
<div class="login-head"> 
<div class="home" onclick="loginObj.SetHome(this,window.location)" title="设置为主页"></div>
<div class="favorites" onclick="loginObj.AddFavorite(window.location,document.title)" title="添加到收藏夹"></div>
<div class="download"  title="下载文件"></div>
<div class="registered" onclick="loginObj.ResetPwd()" title="修改登录密码"></div>
 
</div>
<div class="login-div" style="background: url('');" >
  <table border="0" cellpadding="0" class="login_table" align="center">
    <tr>
      <td valign="middle" align="center">
      <div class="login-form">
      
          <table border="0" cellpadding="0" style="border-collapse: collapse" width="564">
            <tr>
              <td class="login-bg"><table style="width:100%">
                  <tr>
                  	<td><img src='${appInfo.all["app.logo"]}'  onerror="this.style.display='none';" style="border: 0px none ; height: 45px;width: 45px; display: none;"/></td>
                    <td class="login">${appInfo.webAppName}<span>${appInfo.version}</span></td>
                  </tr>
                  <tr>
                    <td colspan="2"><table style="width:100%">
                        <tr>
                          <td class="login-form-center">                        
                              <table border="0" cellpadding="0" class="form_table">
                              <tr ><td height="5px"></td><td></td></tr>
                                <tr>
                                  <td class="name" height="33"></td>
                                  <td class="logintd2"><input type="text" class="infoInput" maxlength="20" id="userName" value="" style="width:175px;"/></td>
                                </tr>
                                <tr>
                                  <td class="mima"></td>
                                  <td class="logintd2"><input type="password"  class="infoInput" maxlength="20" id="password" style="width:175px;"/></td>
                                </tr>
                                <tr>
                                  <td class="logintd"></td>
                                  <td class="logintd2">
                                  	<input type="button" onclick="loginObj.doLogin('userName','password')" class=" buttonBase buttonface" id="subButton" value="登 录"/>
                                  	<input type="button" onclick="window.close();"  class=" buttonBase buttonface" name="button" value="退 出" style="margin-left: 8px;" />
                                  </td>
                                </tr>
                                 <tr>
                                  <td  colspan="2" class="loginMessage" ></td>
                                </tr>
                              </table> </td>
                        </tr>
                      </table></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td class="login-down"></td>
            </tr>
          </table>
        </div>
        </td>
    </tr>
  </table>
</div>
<div class="foot">
  <div class="tip">${appInfo.all["app.login.browse"]}</div>
  <div class="banquan">${appInfo.all["app.login.copyright"]}</div>
</div>
</body>
</html>
