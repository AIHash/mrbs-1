<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
	<title></title>
	<comm:pageHeader hasEcList="false"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/skin/style/main.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
<script>
$(function(){
	$('#backupButton').click(function(){
		jConfirm('<comm:message key="js.confirm_backup_database"/>', '<comm:message key="meeting.m.infotishi"/>', function(select){
			if(select){
				$('#progress').html('&nbsp;&nbsp;<font color="red"><comm:message key="database_backuping"/></font>');
				$.ajax({type :'POST',  url : '${pageContext.request.contextPath}/data/backup', 
					data :  $("form").serialize(),
					dataType : 'json',
					dataFilter : function(json, type){
						if(json.toLowerCase().indexOf('<html>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							return;
						}
						return json;
					},
					success : function(msg){
						if(msg.state =='success'){
							$('#progress').html('<comm:message key="admin.backup_success"/>');
						}else if(msg.state == 'notExist'){
							$('#progress').html('备份目录不存在');
						}else{
							$('#progress').html('<comm:message key="admin.backup_failed"/>');
						}
					}
			});
			}
		});
	});
});
</script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="background:url('../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="admin.title_db_manage>>admin.dbBackupInfoConfirm" />
		<div id="main">
		<form name="MyForm" id="MyForm">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="98%">
			<tr id="inputForm"  style="display:block;">
				<td class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
				<table width="700" border="0" cellspacing="0" cellpadding="0" height="100%">
				<tr>
				    <td colspan="4" height="15" >&nbsp;</td>
				</tr>
				<tr>
					<td width="23%" class="text_left_red2"><comm:message key="unified.and.expert_admin.dbBackupPerson"/>:</td>
					<td width="27%" align="left">&nbsp;${USER_LOGIN_SESSION.name}</td>
					<td width="23%" class="text_left_red2"><comm:message key="admin.backup_catalog"/>:</td>
					<td width="27%" align="left">&nbsp;${backupPath}<input name="backupPath" type="hidden" value="${backupPath}" /></td>
				</tr>
			   	 <tr>
					<td width="23%" class="text_left_red2"><comm:message key="admin.dbBackupFileName"/>:</td>
					<td width="27%" align="left">&nbsp;${dataBackupFileName}
						<input type="hidden" name="dataBackupFileName" value="${dataBackupFileName}" />
					</td>
					<td width="23%" class="text_left_red2"><comm:message key="admin.dbBackupFileSize"/>:</td>
					<td width="27%" align="left">&nbsp;${dataBackupFileSize}&nbsp;MB</td>
				</tr>

			 <tr>
				<td height="20" colspan="4" id="progress">
					<c:choose>
						<c:when test="${warning == 0}">
							<span style="color: red;">当前磁盘所剩空间不足，请返回更换至其他磁盘。</span>
						</c:when>
						<c:when test="${warning == -1}">
							<span style="color: red;">备份磁盘不存在，请返回更换至其他磁盘。</span>
						</c:when>
					</c:choose>
				</td>
			 </tr>

		    </table>
		</td>
		<td class='queryBackground'>&nbsp;</td>
	</tr>

	<tr class='buttonToolBar'>
		<td colspan="3" align="center" style="BACKGROUND-COLOR: #AFD7DF;"><input type="button" id="backupButton" value="<comm:message key="admin.dbBackup"/>" class="button" <c:if test="${warning < 1}">disabled="disabled"</c:if>/>&nbsp;<input type="button" name="new" value="<comm:message key="admin.return"/>" class="button" onclick="javascript:history.back()"/></td>	     			 			    
   </tr>			   

	</table>
  </form>
  	</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>