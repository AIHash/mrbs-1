<!DOCTYPE html>
<%@include file="/common.jsp"%>
<html>
<head>
	<title><comm:message key="admin.system_operation_log" /></title>
	<comm:pageHeader hasEcList="true"/>
</head>
<body style="padding:0; margin:0;background:url('../../resources/images/theme/bg_right-2.gif');overflow-x:hidden;" onload="init()">
<table style="width:700px;border: 0;height: 100%;" cellspacing="0" cellpadding="0">
    <tr>
		<td colspan="4" height="5"></td>
     </tr>
	<tr>
	   <td colspan="3" valign="top">
       <ec:table tableId="ec"
	        var="logs"
	        items="report_data"
	        action="${root}/log/operation/query"
	        title=""
	        width="700"
	        retrieveRowsCallback=""
	        filterRowsCallback=""
	        sortRowsCallback="">
	        <ec:row>
	        	<ec:column property="user" title="admin.user" value="${logs.user.name}(${logs.user.userId})" width="18%" sortable="true"/>
	        	<ec:column property="logDate" title="comm.date"  cell="date" format="yyyy-MM-dd HH:mm:ss" width="18%" />
		        <ec:column property="moduleNo" title="admin.log.module" width="16%">
		        	<comm:message key="${logs.moduleNo}" />
		        </ec:column>
				<ec:column property="content" title="admin.log.content" width="22%" sortable="false"/>
		 	    <ec:column property="result" title="admin.log.result"  width="10%" sortable="false" style="text-align:center;">
		 	    	<c:choose>
		 	    		<c:when test="${logs.result}">
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