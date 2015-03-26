<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
	<title><comm:message key="admin.system_service_log"/></title>
	<comm:pageHeader hasEcList="false"/>	
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);"  style="padding:0; margin:0;background:url('../../resources/images/theme/bg_right-2.gif');">
<div id="center" style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');">
	<comm:navigator position="common.platformIntegration>>common.platformIntegration.radiationImaging" />
	<div id="main">
		<p>功能正在开发中</p>
		</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>
