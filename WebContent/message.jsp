<%@include file="/common.jsp"%>
<html>

<head>
	<title></title>
	<comm:pageHeader hasEcList="false"/>
</head>

<body leftmargin="0" topmargin="0">
<table width="100%" height="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" height="200" valign="center" align="center"><img align="center" valign="center" src="<%=request.getContextPath()%>/resources/skin/style/error.gif"><font size="5"  color="#ff0404"><comm:message key="comm.message_info"/></font><br><br><%=(String) request.getSession().getAttribute("message")%>
		</td>
     </tr>
</table>
</body>
<%
	request.getSession().removeAttribute("message");
%>
</html>
