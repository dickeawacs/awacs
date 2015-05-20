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
	<link rel="stylesheet" type="text/css" href="ext-3.4/ux/fileuploadfield/css/fileuploadfield.css"/>
	<link rel="stylesheet" type="text/css" href="ext-3.4/ux/progressColumn/ProgressColumn.css"/>
    <link rel="stylesheet" type="text/css" href="pub/css/app.css" />
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
        .rowColor{background-color: #F9C8D8 !important;}
    </style> 
	<script type="text/javascript" src="ext-3.4/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="ext-3.4/ext-all-debug.js"></script>
	 <script type="text/javascript" src="ext-3.4/src/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="ext-3.4/ux/ColumnNodeUI.js"></script>
    <script type="text/javascript" src="ext-3.4/ux/fileuploadfield/FileUploadField.js"></script>
    <script type="text/javascript" src="ext-3.4/ux/progressColumn/ProgressColumn.js"></script>
    <script type="text/javascript" src="pages/80001.js"></script> 
    
     <script type="text/javascript">
   
    </script>
  </head>
  <body>
  <!-- use class="x-hide-display" to prevent a brief flicker of the content -->
     <div id="north" class="x-hide-display">
       
    </div>
    <div id="west" class="x-hide-display">
       
    </div>
    <div id="center2" class="x-hide-display">
    <a id="hideit"  >Toggle the west region</a>
	 </div>
    <div id="center1" class="x-hide-display">
    </div>
    <div id="props-panel" class="x-hide-display" style="width:200px;height:200px;overflow:hidden;">
    </div>
    <div id="south" class="x-hide-display">
        <p>消息 显示</p>
    </div>
    <div id="fileUpload-Win" class="x-hide-display">
      <div id="fi-form"></div>
    </div>
    <div id="action-Win-SXF" class="x-hide-display"></div>
  </body>
</html>
