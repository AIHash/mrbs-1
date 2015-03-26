<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="system.system_name" /></title>
	<link href="<%=request.getContextPath()%>/resources/skin/login/login.css" rel="StyleSheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/skin/style/main.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	
	<link rel="icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
 	<link rel="shortcut icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
 	<link rel="bookmark" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
 
	<script src="<%=request.getContextPath()%>/resources/js/JSFunc.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/md5.js" type="text/javascript"></script>
	<script type="text/javascript">
	
		function check(r){
			var ua = navigator.userAgent.toLowerCase();
			return r.test(ua);
		}
		function checkBrowser(){
			var isOpera = check(/opera/);
			var isIE = !isOpera && check(/msie/);
			/* if(!isIE){
				jAlert("<comm:message key='common.IE.browser' />", "<comm:message key='meeting.m.infotishi'/>");
				return false;
			}
			var isIE8 = isIE && check(/msie 8/);
			if(!isIE8){
				jAlert("<comm:message key='common.IE.version' />", "<comm:message key='meeting.m.infotishi'/>");
				return false;
			}  */
		}
		
		if( self != top ) {
			top.window.location="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
		}else{
			<%
			String message = (String) request.getParameter("message");
			if (null == message)
				message = (String)request.getSession().getAttribute("message");

			if (null == message)
				message="";

			request.getSession().removeAttribute("message");
			%>	
		}

		function checkForm(){
			var loginId = document.getElementById("LoginId");
			var pwd = document.getElementById("prePassword");
			if (strTrim(loginId.value) == ""){
				jAlert("<comm:message key='js.need_input' args='admin.logon_name' />", "<comm:message key='meeting.m.infotishi'/>");
				loginId.focus();
				return false;
			}
			if (strTrim(pwd.value) == ""){
				jAlert("<comm:message key='js.need_input' args='admin.logon_password' />", "<comm:message key='meeting.m.infotishi'/>");
				pwd.focus();
				return false;
			} 

			document.getElementById("Password").value = b64_md5(pwd.value);
			return true;
		}
	</script>
</head>

<body onload="document.loginForm.LoginId.focus();checkBrowser();">
<div class="login">
<form id="loginForm" name="loginForm" action="<%=request.getContextPath()%>/login/" method="post" onsubmit="return checkForm();">
	<table width="400" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="2" valign="bottom" align="center"><font color="red"><b><comm:message key='<%=message%>'/></b></font></td>
		</tr>
	  <tr>
	    <td height="30"><strong><comm:message key="admin.logon_name"/><strong>:</td>
	    <td><input class="txt" type="text" id="LoginId" name="LoginId" tabindex="1"/></td>
	    <td rowspan="2"><input class="btn" type="submit" name="button" value="<comm:message key='admin.logon'/>"/></td>
	  </tr>
	  <tr>
	    <td height="30"><strong><comm:message key="admin.logon_password"/><strong>:</td>
	    <td><input class="txt" type="password" id="prePassword" name="prePassword" tabindex="2"/>
	    	<input type="hidden" name="Password" id="Password" />
	    </td>
	   </tr>
	</table>
	
	<div class="copydiv" style=" font-size:13px;font-family:Arial, Helvetica, sans-serif"><comm:message key='system.software_copy'/></div>

</form>

</div>
</body>
</html>