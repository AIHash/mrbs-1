<%@include file="/common.jsp"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><comm:message key="system.system_name" /></title>
	<link href="<%=request.getContextPath()%>/resources/skin/login/login.css" rel="StyleSheet" type="text/css">
	<script language=javascript src="<%=request.getContextPath()%>/resources/js/JSFunc.js" type=""></script>
</head>

<style type="text/css">
<!--
body
{
	background-color: #b3d7e7;
	margin-left: 0px;
	margin-top: 0px;
}

.ipt{ border:solid #7f9db9 1px; width:148px; height:20px;}
td{ font-size:12px;}
.tbLogin{ margin-left:476px;}
.tblogin1{ margin-top:170px;}
.tbLogin2{ height:30px;}
-->
</style>

<script language="javaScript" type="text/javascript">
if( self != top )
{
	parent.location="<%=request.getContextPath()%>/index.jsp?message=${param.message}";
}

function checkForm()
{
	//test();
	if (strTrim(MyForm.LoginId.value) == "")
	{
		alert("<comm:message key="js.need_input" args="admin.logon_name" />");
		MyForm.LoginId.focus();
		return false;
	}
	if (strTrim(MyForm.Password.value) == "")
	{
		alert("<comm:message key="js.need_input" args="admin.logon_password" />");
		MyForm.Password.focus();
		return false;
	}
	return true;
}
</script>

<%
	String message = (String) request.getParameter("message");
	if (null == message)
		message = (String)request.getSession().getAttribute("message");

	if (null == message)
		message="";

	request.getSession().removeAttribute("message");
%>

<body>
<table class="tblogin1" width="742" height="251" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/resources/images/ifms/login.gif">
	<tr>
		<td>
			<form name="MyForm" action="<%=request.getContextPath()%>/login/" method="POST" onsubmit="return checkForm();">
			<table class="tbLogin" width="260" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tbLogin2" colspan="2" valign="bottom" align="center"><font color="red"><b><comm:message key='<%=message%>'/></b></font></td>
				</tr>

				<tr>
					<td class="tbLogin2" align="right"><comm:message key="admin.logon_name"/>:</td>
					<td><input type="text" name="LoginId"  class="ipt" style="width:160px"></td>
				</tr>
				
				<tr>
					<td class="tbLogin2" align="right"><comm:message key="admin.logon_password"/>:</td>
					<td><input type="password" name="Password" style="width:160px" class="ipt" redisplay="false"></td>
				</tr>

				<tr>
					<td class="tbLogin2">&nbsp;</td>
					<td><input type="submit" name="button" id="button" class="button" value="<comm:message key='admin.logon'/>"></td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>
<table width="742" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr >
              <td height="42" valign="bottom">
			  <table width="742" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="right"><comm:message key='system.software_copy'/> <a href="http://www.microsoft.com/china/windows/ie/default.mspx" target="_blank">IE 6.0+ </a></td>
                  </tr>
              </table></td>
            </tr>
</table>
</body>

</html>