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
	<link rel="stylesheet" type="text/css" href="pub/css/ats.css" />	
	<link rel="stylesheet" type="text/css" href="ext-3.4/resources/css/ext-all.css" />
	<style type=text/css>
        .upload-icon {
            background: url('pub/images/shared/icons/fam/image_add.png') no-repeat 0 0 !important;
        }
        #fi-button-msg {
            border: 2px solid #ccc;
            padding: 5px 10px;
            background: #eee;
            margin: 5px;
            float: left;
        }
        .x-panel-body2{ 
       	  background-color:transparent;
        }
        .rowColor{background-color: #F9C8D8 !important;}
        .hello-button { 
        width:475px;
        height:60px;
        
        background: url(http://info-database.csdn.net/Upload/2012-09-10/ms-teched-475-60-0910.gif) left top no-repeat !important; 
}
        
        
        
    </style>
       <script type="text/javascript">
            var gobal_user_level='${UserInfo.level}'||3;
   var App_User_Name='${UserInfo.loginName}';
   var App_System_Name='${appInfo.webAppName}';
   App_System_Name="安防及智能控制系统";
   
    </script>
	<script type="text/javascript" src="ext-3.4/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="ext-3.4/ext-all-debug.js"></script>
	<script type="text/javascript" src="ext-3.4/src/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="pub/utils.js"></script>
    <script type="text/javascript" src="pages/admin/index.js"></script> 
     <script type="text/javascript">
   
    </script>
  </head>
  <body>
  <jsp:include page="../public/player.jsp"></jsp:include>
  <!-- use class="x-hide-display" to prevent a brief flicker of the content -->
     <div id="north" class="x-hide-display">
       
    </div>
    <div id="west" class="x-hide-display">
       
    </div>
    <div id="center2" class="x-hide-display">
    <a id="hideit"  ></a>
	 </div>
    <div id="center1" class="x-hide-display">
    </div>
    <div id="props-panel" class="x-hide-display" style="width:200px;height:200px;overflow:hidden;">
    </div>
    <div id="south" class="x-hide-display">
        <img alt="" src="">
    </div>
    <div id="fileUpload-Win" class="x-hide-display">
      <div id="fi-form"></div>
    </div>
    <div id="action-Win-SXF" class="x-hide-display"></div>
  </body>
</html>
