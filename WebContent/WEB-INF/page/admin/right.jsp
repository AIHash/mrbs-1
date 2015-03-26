<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<head>
<title></title>
<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#main{ overflow-y:hidden;overflow-x:hidden;width:725px;margin:0px;float:left;}
#mr_pic{width:700px;background:repeat-x;complete:complete;}
</style>
</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<div id="daohang"></div>
		<div id="breadcrumb">&nbsp;</div>
		<div id="main">
		<img id="mr_pic" src="<%=request.getContextPath()%>/resources/images/theme/welcome_pic.jpg" />
		</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>
