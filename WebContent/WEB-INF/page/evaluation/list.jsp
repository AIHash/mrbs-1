<%@include file="/common.jsp"%>

<html>

<head>
    <comm:pageHeader />
</head>

<body leftmargin="5" topmargin="0"onload="init()">
<table border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
    <comm:navigator position="admin.title_system_manage>>admin.evaluation_manage" />
	<td colspan="3" height="15" class='queryBackground'>&nbsp;</td>
</table>
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
		
	    <td  colspan="3" height="30" valign="bottom"><input type="button" name="new" value=" <comm:message key="comm.add"/>" class="button" onClick="javascript:location.href='${root}/evaluation/add'">&nbsp;</td>
	</tr>
    <tr>
		<td colspan="3" height="5" ></td>
	</tr>
	<tr>
	   <td colspan="3" valign="top" align="center">
       <ec:table tableId="ec"
        var="evaluation"
        items="report_data"
        action="${root}/evaluation/list"
        title=""
        width="100%"
        retrieveRowsCallback=""
        filterRowsCallback=""
        sortRowsCallback="">
        <ec:row>
        <ec:column property="name" title="evaluation.name" width="10%"/>
        <ec:column property="title1" title="evaluation.title1" width="15%"/>
        <ec:column property="title2" title="evaluation.title2" width="15%"/>
        <ec:column property="title3" title="evaluation.title3" width="15%"/>
        <ec:column property="title4" title="evaluation.title4" width="15%"/>
        <ec:column property="title5" title="evaluation.title5" width="15%"/>
        <ec:column property="userType.value" title="admin.user_type" width="12%" >
			 		${evaluation.userType.name}
		</ec:column>
 	    <ec:column property="null" title="comm.modify" sortable="false" viewsAllowed="html" width="10%" resizeable="false" style="text-align:center;">
         <a href="${root}/evaluation/edit/${evaluation.id}"><img alt="<comm:message key="comm.modify"/>" src="${root}/resources/skin/style/update.gif" /></a>
          </ec:column>
         
         <ec:column property="null" title="comm.delete" sortable="false" viewsAllowed="html" width="10%" resizeable="false"  style="text-align:center;">
             <c:if test="${evaluation.id!=1}">
            <a  href="javascript:if (confirm('<comm:message key="js.confirm_delete" args="admin.evaluation" />')) window.location.href='${root}/evaluation/delete/${evaluation.id}'"><img alt="<comm:message key="comm.delete"/>" src="${root}/resources/skin/style/delete.gif" /></a>
          </c:if>          
          <c:if test="${evaluation.id==1}">
          <a  href="javascript:if (alert('<comm:message key="js.confirm_not_delete" args="admin.evaluation" />')) "><img alt="<comm:message key="comm.delete"/>" src="${root}/resources/skin/style/delete.gif" /></a>
          </c:if> 
          </ec:column> 
                
         </ec:row>
      </ec:table>
      </td>
	</tr>
	


</table>

</body>

</html>
<%
	String freshTree = (String) request.getAttribute("freshTree");
	if (null != freshTree)
	{
%>
<script type="text/javascript">
	parent.document.frames['deptTree'].window.location.reload();
</script>
<%
	}	
%>