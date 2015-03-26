<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<html>

<head>
	<title><comm:message key="admin.department_manage" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body onload="heigthReset(64);document.getElementById('deptTree').style.height = (document.body.clientHeight - 64) + 'px';document.getElementById('dataFrame').style.height = (document.body.clientHeight - 64) + 'px';" onresize="heigthReset(64);document.getElementById('deptTree').style.height = (document.body.clientHeight - 64) + 'px';document.getElementById('dataFrame').style.height = (document.body.clientHeight - 64) + 'px';" style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="admin.title_system_manage>>admin.department_manage" />
		<div id="main" style="overflow-y:hidden;">
			<table border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
				<tr id="inputForm"  style="display:block;">
					<td width="210" valign="top">
					     <table width="100%" height="100%">
					         <tr>
					              <td valign="top">
					                   <iframe valign="top" frameborder="0" id="deptTree" name="deptTree" width="145" scrolling="auto" src="${root}/dept/tree"></iframe>
					              </td>
					         </tr>
					     </table>
					</td>
					<td width="515" valign="top">
					     <table width="100%" height="100%">
					         <tr>
					              <td valign="top">
					                   <iframe id="dataFrame" valign="top" frameborder="0" name="deptList" width="515" scrolling="auto" src="${root}/dept/list"></iframe></td>             
					              </td>
					         </tr>
					     </table>
				</tr>
			</table>
		</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>