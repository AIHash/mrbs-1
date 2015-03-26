<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>
<html>
<head>
	<title><comm:message key="admin.changepassword"/></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script src="${pageContext.request.contextPath}/resources/js/md5.js" type="text/javascript"></script>
<script type="text/javascript">
function check()
{
	if (MyForm.OldPassword.value.length < 1)
	{
		jAlert("<comm:message key="js.need_input" args="admin.old_password"/>", "<comm:message key='meeting.m.infotishi'/>");
		return false;
	}
	var curPasswordValue= b64_md5(MyForm.OldPassword.value);
	var oldPasswordValue = '${user.password}';
	if(curPasswordValue!=oldPasswordValue){
		jAlert("<comm:message key='js.need_input_right' args='admin.old_password'/>", "<comm:message key='meeting.m.infotishi'/>");
		return false;
	}
	if (MyForm.NewPassword.value.length < 1)
	{
		jAlert("<comm:message key="js.need_input" args="admin.new_password"/>", "<comm:message key='meeting.m.infotishi'/>");
		return false;
	}

	if (MyForm.ReNewPassword.value.length < 1)
	{
		jAlert("<comm:message key="js.need_input" args="admin.confirm_password"/>", "<comm:message key='meeting.m.infotishi'/>");
		return false;
	}
	
	if (MyForm.NewPassword.value != MyForm.ReNewPassword.value)
	{
		jAlert("<comm:message key="js.pass_not_consistent"/>", "<comm:message key='meeting.m.infotishi'/>");
		return false;
	}
	
	MyForm.OldPassword.value = b64_md5(MyForm.OldPassword.value);
	MyForm.NewPassword.value = b64_md5(MyForm.NewPassword.value);
	MyForm.ReNewPassword.value = b64_md5(MyForm.ReNewPassword.value);
	return true;
}
function myAlert(){
	var message='<%=returnmessage%>';
	if(message&&message!=""&&message!="null") {
		jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
	}
}
</script>
	<style type="text/css">
		body {
		background-color: #ffffff;
	}
	</style>
</head>

<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" onload="focus_edit('MyForm','OldPassword');myAlert();heigthReset(64);" onresize="heigthReset(64);">
<div id="center">
	<comm:navigator position="admin.title_self_manage>>admin.changepassword" />
	<div id="main" style="overflow:hidden;">
<form name="MyForm" action="<%=request.getContextPath()%>/user/changepassword" onsubmit="return check()" method="post">
			  <div id="viewappinfor">
				<div id="titleStyle">
					<span>&nbsp;</span>
				</div>		
	            <div class="subinfor" id="patientinfor1">
					<table border="0" cellspacing="0" cellpadding="0" align="center">
						<tr>
							<td> &nbsp;</td>
							<td align="center">
								<table id="inputForm" style="display:block; " width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
								   	 <td colspan="2" height="15" >&nbsp;</td>
									</tr>
									<tr>
										<td width="30%" height="25"	align="right"><comm:message key="unified.and.expert_admin.old_password"/>:<span class="required">*</span></td>
									    <td width="70%" align="left"><input type="password" name="OldPassword" maxlength="16" size="30" style="width:170px" /></td>
									</tr>				
					                <tr>
					                    <td width="30%"  height="25" align="right"><comm:message key="unified.and.expert_admin.new_password"/>:<span class="required">*</span></td>
									    <td align="left"><input type="password" name="NewPassword" maxlength="16" size="30" style="width:170px " /></td>
										<td></td>
								     </tr>				
					                <tr>
										<td width="30%"  height="25" align="right"><comm:message key="admin.confirm_password"/>:<span class="required">*</span></td>
										<td align="left"><input type="password" name="ReNewPassword" maxlength="16" size="30" style="width:170px " /></td>
								     </tr>
							     </table>
						  </td>
					      <td>&nbsp;</td>
					  </tr>
				
						<tr>
							<td height="45" align="center" valign="bottom" colspan="3">
								<table>
									<tr>
										<td align="center"><input type="submit" name="new"	value=" <comm:message key='comm.save'/>" class="button" /></td>
									</tr>
								</table>
							</td>
						</tr>
				
						<tr>
							<td height="30"></td>
							<td><div style='width: 300px'></div>
							</td>
							<td></td>
						</tr>
					</table>	            
	            </div>
				<div id="titleStyle2">
					<span>&nbsp;</span>
				</div>	            
	          </div>  

 </form>
 </div>
 </div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>

</html>