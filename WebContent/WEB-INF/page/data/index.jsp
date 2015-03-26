<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<comm:pageHeader hasEcList="false"/>
<title></title>
<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
<script type="text/javascript">
function check()
{
	var backupPath = strTrim(document.getElementById("backupPath").value);
	if(backupPath == ""){
		jAlert("<comm:message key='js.need_input' args='admin.backup_catalog'/>", "<comm:message key='comm.message_info'/>");
		return false;
	}

    if(backupPath.match(/\s/) != null){
    	jAlert("<comm:message key='admin.backup_not_blank'/>", "<comm:message key='comm.message_info'/>");
		return false;
	}

    if(backupPath.match(/[\u0391-\uFFE5]+/)){
    	jAlert("备份路径不能包含中文", "<comm:message key='comm.message_info'/>");
     	return false;
    }

	if (!validDir(document.getElementById("backupPath"))) {
		jAlert("<comm:message key='js.need_input' args='admin.backup_valid_path'/>", "<comm:message key='comm.message_info'/>");
		return false;
	}
	return true;
}

</script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding: 0; margin: 0; background: url('../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="admin.title_db_manage>>admin.db_backup" />
		<div id="main">
			<form name="MyForm" action="${pageContext.request.contextPath}/data/tip" method="post" onsubmit="return check();">
			  <div id="viewappinfor">
				<div id="titleStyle">
					<span>&nbsp;</span>
				</div>		
	            <div class="subinfor" id="patientinfor1">
                       <table border="0" cellspacing="0" cellpadding="0" align="center" width="98%">
							<tr id="inputForm" style="display: block;">
								<td>&nbsp;</td>
								<td align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td colspan="2" height="15">&nbsp;</td>
										</tr>
										<tr>
											<td width="23%" align="right">
											    <comm:message key="admin.backup_catalog" />:&nbsp;&nbsp;<span class="required">*</span>
											</td>
											<td width="77%" align="left">
												<input type="text" id="backupPath" name="backupPath" style="width: 400px" value="${requestScope.backupPath}"/>
											</td>
										</tr>
										<tr><td>&nbsp;</td><td align="left">提示：备份路径不能包含中文和空格</td></tr>
									</table></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td height="45" align="center" colspan="3">
									<table>
										<tr>
											<td align="center">
											<input type="submit" name="new" value=" <comm:message key='admin.next'/>"  class="button" />
											</td>
										</tr>
									</table></td>
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