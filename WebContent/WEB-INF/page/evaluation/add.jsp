<%@include file="/common.jsp"%>

<html>

<head>
	<title><comm:message key="admin.evaluation_manage" /></title>
	<comm:pageHeader hasEcList="false"/>
</head>

<script language="javascript">
function check()
{
	if ((strTrim(form.name.value)) == "")
	{
	   alert("<comm:message key='js.need_input' args='admin.evaluation_name' />");

	   form.name.focus();
	   return false;
	}

	return true;
}

</script>

<body leftmargin="0" topmargin="0" onLoad="focus('form','name')" >
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
	    <comm:navigator position="admin.title_system_manage>>admin.evaluation_manage>>admin.evaluation_create" />
		<td colspan="3" height="15" class='queryBackground'>&nbsp;</td>
	</table>

	<form name="form" action="${root}/evaluation/create" method="post" onsubmit="return check()">
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
		<tr>
			<td colspan="4" height="10"></td>
		</tr>

		
		<tr>
			<td class="text_left_red2" height="25"  width="23%" align="right"><comm:message key="evaluation.name"/>:</td>
			<td width="27%" align="left">&nbsp;<input type="text" name="name" maxlength="50" size="30" style="width:150px" value="${evaluation.name}" ></td>
		</tr>
		
		<tr>
			<td class="text_left2" height="25" align="right"><comm:message key="evaluation.title1"/>:</td>
			<td align="left">&nbsp;<input type="text" name="title1" maxlength="250" size="80" style="width:550px" value="${evaluation.title1}"></td>
			
		</tr>
		<tr>
			<td class="text_left2" height="25" align="right"><comm:message key="evaluation.title2"/>:</td>
			<td align="left">&nbsp;<input type="text" name="title2" maxlength="250" size="80" style="width:550px" value="${evaluation.title2}"></td>
			
		</tr>
				<tr>
			<td class="text_left2" height="25" align="right"><comm:message key="evaluation.title3"/>:</td>
			<td align="left">&nbsp;<input type="text" name="title3" maxlength="250" size="80" style="width:550px" value="${evaluation.title3}"></td>
			
		</tr>
				<tr>
			<td class="text_left2" height="25" align="right"><comm:message key="evaluation.title4"/>:</td>
			<td align="left">&nbsp;<input type="text" name="title4" maxlength="250" size="80" style="width:550px" value="${evaluation.title4}"></td>
			
		</tr>
				<tr>
			<td class="text_left2" height="25" align="right"><comm:message key="evaluation.title5"/>:</td>
			<td align="left">&nbsp;<input type="text" name="title5" maxlength="250" size="80" style="width:550px" value="${evaluation.title5}"></td>
			
		</tr>
						<tr>
			<td class="text_left2" height="25" align="right"><comm:message key="admin.user_type"/>:</td>
			<td align="left">&nbsp;<select id="usertype" name ="userType" style='width:150px' >
						<c:forEach items="${USER_TYPE}" var="type" varStatus="var">
							<option value="${type.value}"><comm:message key="${type.name}"/></option>
						</c:forEach>      
					</select></td>
			
		</tr>
		<tr>
			<td height="5" colspan="4" align="right"></td>
		</tr>

		<tr>
		  <td height="40" colspan="4" class='buttonToolBar'><div align="center"><input type="submit" name="new" value=" <comm:message key="comm.save"/>" class="button" >&nbsp;<input type="button" name="new" value=" <comm:message key="comm.cancel"/>" class="button" onClick="JavaScript:window.history.back()"></div></td>
		</tr>
	</table>
	</form>

</body>
</html>