<%@include file="/common.jsp"%>
<html>
<head>
	<title>main</title>
	<comm:pageHeader hasEcList="false"/>
</head>

<base target="Report">
<body leftmargin="0" topmargin="0">
<table border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
	<comm:navigator position="admin.title_system_config>>admin.usergroup_manage" />
	<tr>
		<td colspan="3" height="15" class='queryBackground'>&nbsp;</td>
	</tr>
</table>
<form name="MyForm" action="${pageContext.request.contextPath}/group/list" method="post" target="Report">
	<table border="0" cellspacing="0" cellpadding="0" align="center"  class="table_98per">	
		<tr id="inputForm" style="display:block; ">
			<td class='queryBackground'>&nbsp;</td>
			<td class='queryBackground' align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				   <tr>
					<td class="text_left2"  width="23%" height="25" align="right"><comm:message key="admin.usergroup.name"/>:</td>
					<td width="27%" align="left">&nbsp;&nbsp;<input type="text" name="name" maxlength="32" style="width:200px "></td>
					<td class="text_left2"  width="23%" height="25" align="right"><comm:message key="admin.usergroup.remark"/>:</td>
					<td width="27%" align="left">&nbsp;&nbsp;<input type="text" name="remark" maxlength="32" style="width:200px "></td>
				  </tr>
				</table>
			</td>
			<td class='queryBackground'>&nbsp;</td>
		</tr>

		<tr>
		  <td height="45" align="center" valign="bottom" class='queryBackground' colspan="3">
			<table class='buttonToolBar'>
			  <tr>
				<td align="center"><input type="submit" name="new" value=" <comm:message key="comm.view"/>" class="button" >&nbsp;&nbsp;&nbsp;<img id="img1"  src="${pageContext.request.contextPath}/resources/skin/style/Button_top2.gif" style="cursor:hand;" border="0" onclick="javascript:hideOrDisplayInputForm(inputForm, img1)" alt="<comm:message key="hidden_input_parameter"/>"></td>
			  </tr>
			</table>
		  </td>
		</tr>

		<tr height="auto">
			<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report"  height="100%" width="100%" style="height:document.body.scrollHeight;aho:expression(FrameAutoResize('Report'))" scrolling="no" src="${pageContext.request.contextPath}/group/list"></iframe></td>
			<td></td>
		</tr>
	</table>
</form>
</body>
</html>

