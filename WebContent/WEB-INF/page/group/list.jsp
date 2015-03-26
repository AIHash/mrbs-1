<%@include file="/common.jsp"%>
<html>
<head>
	<title></title>
	<comm:pageHeader hasEcList="true"/>
</head>

<body leftmargin="0" topmargin="0" onload="init()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<tr>
<td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr>
        <td height="20" colspan="5" valign="bottom">
        	<input type="button" name="new" value=" <comm:message key="comm.add"/>" class="button" 
        		onclick="javascript:parent.location.href='${pageContext.request.contextPath}/group/add'">&nbsp;</td> 
        </tr>
		<tr>
		    <td colspan="5" height="5"></td>
		</tr>

		<tr>
		    <td>
		    <ec:table tableId="ec"
				var="userGroup"
				items="report_data"
				action="${pageContext.request.contextPath}/group/list"
				title=""
				width="100%"
				retrieveRowsCallback=""
				filterRowsCallback=""
				sortRowsCallback=""
				sortable="false"
				>
				<ec:row>				  
				  <ec:column property="name"  sortable="true" title="admin.usergroup.name" width="10%" />				  
				  <ec:column property="remark" title="admin.usergroup.remark"   width="20%"/>					  
				  <ec:column property="null" title="comm.modify" sortable="false" viewsAllowed="html" width="5%" resizeable="false" style="text-align:center;">
				  <a target="WorkBench" href="${pageContext.request.contextPath}/group/modify?id=${userGroup.id}"><img alt="<comm:message key="comm.modify"/>" src="${pageContext.request.contextPath}/resources/skin/style/update.gif" /></a>
				  </ec:column>
				  <ec:column property="null" title="comm.delete" sortable="false" viewsAllowed="html" width="5%" resizeable="false"  style="text-align:center;">
					<a target="WorkBench" href="javascript:if (confirm('<comm:message key="js.confirm_delete" args="${userGroup.name}" />')) window.location.href='${pageContext.request.contextPath}/group/delete?id=${userGroup.id}&name=${userGroup.name}&operName=${param.operName}'"><img alt="<comm:message key="comm.delete"/>" src="${pageContext.request.contextPath}/resources/skin/style/delete.gif" /></a>
				  </ec:column>
				</ec:row>
			  </ec:table>
	        </td>
		</tr>								
	</table>
</td>
</tr>
</table>
</body>
<script type="text/javascript">
	parent.document.getElementById('dataFrame').style.height = document.body.scrollHeight;
</script>
</html>
