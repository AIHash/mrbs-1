<%@include file="/common.jsp"%>

<html>
	<head>
	  <title><comm:message key="admin.usergroup_manage"/></title>
	  <comm:pageHeader hasEcList="false"/>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/JSFunc.js"></script>
	<script type="text/javascript">
	function check(){
		if ((strTrim(form.name.value)) == "")	{
		   alert("<comm:message key="js.need_input" args="admin.usergroup.name"/>");
		   form.name.focus();
		   return false;
		}

		var bool = false;
		var checkboxObj = document.getElementsByName("userId");
		for(var i = 0; i < checkboxObj.length; i++){
			if(checkboxObj[i].checked){
		       bool = true;
		       break;
		   }
		}
		if(!bool){
			alert("<comm:message key='admin.group.members.limit' />");
			return false;
		}
	
		if (getUnicodeLength(form.remark.value) > 100){
			alert("<comm:message key='admin.content_overflow' args='remark,100' />");
			form.remark.focus();
			return false;
		}
		return true;
	}
	</script>
</head>
<body style="margin-top: 0; margin-bottom: 0;" onload="focus('form','name');javascript:realTimeCount(form.memo,'hint')" >
	<form  name="form" action="${pageContext.request.contextPath}/group/saveOrUpdate" method="post" onsubmit="return check()">
		<table border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
			<comm:navigator position="admin.title_system_config>>admin.usergroup_manage>>comm.modify" />
			<tr>
				<td colspan="3" class='queryBackground' height="20">&nbsp;</td>
			</tr>
			<tr id="inputForm" style="display:block;">
				<td class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="text_left_red2" height="25" align="right"><comm:message key="admin.usergroup.name"/>:</td>
						<td  align="left">&nbsp;&nbsp;<input type="text" name="name" maxlength="32" style="width:200px " value="${GROUP.name}"/></td>
					</tr>
					<tr>
						<td height="12" width="15%" align="right" valign="top" class="text_left2"><img src="${pageContext.request.contextPath}/resources/skin/style/pix.gif" width="1" height="15"/><comm:message key="admin.usergroup.remark"/>:<div id="hint">(100/100)</div></td>
						<td colspan="3" align="left">&nbsp;&nbsp;<textarea name="remark" maxlength="100" onkeyup="javascript:realTimeCount(this,'hint');"  onkeypress="realTimeCount(this,'hint')"   cols="81" rows="3" title=" <comm:message key="admin.content_maxlength"/>100">${GROUP.remark}</textarea></td>
				  	</tr>
					<tr id="phone">
						<td height="25" align="right" class="text_left_red2"><comm:message key="admin.usergroup.user_name"/>:</td>					
						<td colspan="3">
							<input type="hidden" name="id" value="${GROUP.id}">
							<div>
								<c:forEach var="user" items="${ALL_UNIFIEDUSER}" varStatus="status">
									<input name="userId" type="checkbox" value="${user.userId}" 
										<c:if test="${GROUP.members.contains(user)}">
											checked="checked"
										</c:if>>${user.name}
									<c:if test="${status.count % 5 == 0 }">
										<br/>
									</c:if>
								</c:forEach>
							</div>
						</td>
					</tr>
		
					<tr> 
					  <td height="20" colspan="4"></td>
					</tr>
					</table>
				</td>
				<td class='queryBackground'> &nbsp;</td>
			</tr>
			<tr>
				 <td height="40" colspan="4" class='buttonToolBar'><div align="center"><input type="submit" name="new" value="<comm:message key="comm.save"/>" class="button" >&nbsp;<input type="button" name="new" value=" <comm:message key="comm.cancel"/>" class="button" onClick="javascript:history.back()"></div></td>
			</tr>	
			<tr>
				<td></td>
				<td><div style='width:750px'></div></td>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>
