<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyAlert.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body align="center" style="background-color:#ecfbd4">
  <div style="margin:auto; width:100%; text-align:center; padding:10px 0;">
  <font color="red"><%=request.getParameter("message")%></font>
  </div>
  </body>
</html>
