<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>${appInfo.webAppName}</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page"> 
	<link rel="stylesheet" type="text/css" href="ext-3.4/resources/css/ext-all.css" />
	<script type="text/javascript">
	//var onePs=${oneP};
	</script>
	 <script type="text/javascript" src="pub/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="ext-3.4/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="ext-3.4/ext-all-debug.js"></script>
	<script type="text/javascript" src="ext-3.4/src/locale/ext-lang-zh_CN.js"></script>
	 <script type="text/javascript" src="pub/utils.js"></script> 
    <script type="text/javascript" src="pages/admin/1003.js"></script> 
       <script type="text/javascript">
      var gobal_user_level='${UserInfo.level}'||3;
    </script>
  </head>
  <body> 
  <div id="formArea"></div>
  <div id="adminResetPwd"></div>
  </body>
</html>

