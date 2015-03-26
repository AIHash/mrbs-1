<!DOCTYPE html>
<%@include file="/common.jsp"%>
<html>
<head>
	<title>service log list</title>
	<comm:pageHeader hasEcList="true"/>
</head>
<body style="padding:0; margin:0;overflow-y:auto;background:url('../../resources/images/theme/bg_right-2.gif');" onload="init()">
<table cellspacing="0" cellpadding="0" style="width: 700px; border: 0;height: 100%">
    <tr>
		<td colspan="4" height="5"></td>
     </tr>
	<tr>
	   <td colspan="3" valign="top">
       <ec:table tableId="ec"
        var="logs"
        items="report_data"
        action="${root}/log/service/query"
        title=""
        width="700px"
        retrieveRowsCallback=""
        filterRowsCallback=""
        sortRowsCallback="">
        <ec:row>
        <ec:column property="objectId" title="admin.log.title" width="90px">
        		${SERVICE_LOG_TYPE[logs.objectId]}
       	</ec:column>
		<ec:column property="content" sortable="false" title="admin.log.content" width="341px">
		      <span title="${logs.content}">${logs.content}</span>
		</ec:column>
        <ec:column property="createTime" sortable="true" title="comm.date" cell="date" format="yyyy-MM-dd HH:mm:ss" width="100px" />
 	    <ec:column property="result" title="admin.log.result" width="8%" style="text-align:center;">
 	    	<c:choose>
 	    		<c:when test="${ logs.result == 1}">
 	    			<comm:message key="comm.success" />
 	    		</c:when>
 	    		<c:otherwise>
 	    			<comm:message key="comm.failed" />
 	    		</c:otherwise>
 	    	</c:choose>
	  	 </ec:column>
         </ec:row>
      </ec:table>
      </td>
	  </tr>
	<tr>
		<td colspan="4" height="20"></td>
	</tr>
</table>
</body>
<script type="text/javascript">
	parent.document.getElementById('dataFrame').height = document.body.scrollHeight;
</script>
</html>