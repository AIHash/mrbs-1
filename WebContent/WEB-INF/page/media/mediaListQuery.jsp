<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page language="java" import="com.wafersystems.mrbs.*,com.wafersystems.mrbs.vo.user.UserInfo" %>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
 UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
%>
<html>
<head>
	<title></title>
	<comm:pageHeader hasEcList="true"/>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript"> 
		$(document).ready(function() {
			parent.parent.popWindow();
		});
		function myAlert() {
			   var message='<%=returnmessage%>';
			    if(message&&message!=""&&message!="null")
			    {
			      jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
			    }
		}
		
		
		function delMedia(mediaId){
			parent.delMedia(mediaId);
		}
		
		function Exp(filepath,title){  
			title = encodeURI(encodeURI(title));
			window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
		}
		
		
		function previewmedia(mediaType,filepath,pathType){
			parent.chechpreviewmedia(mediaType,filepath,pathType);

		}
	</script>
</head>
<body style="overflow-x:hidden;" leftmargin="0" topmargin="0" onload="init();myAlert()">

	<table width="700" border="0" cellspacing="0" cellpadding="0" height="100%">
	    <tr>
			<td colspan="4" height="5">
			</td>
		</tr>
		
		<tr>
			<td colspan="3" valign="top">
			<ec:table tableId="ec"
		        var="data"
				items="report_data"
				action="${root}/media/mediaList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback=""> 
				<ec:row>
					<ec:column property="medianame" title="common.media.name" width="25%" sortable="true"/>
					<ec:column property="mediaType" title="common.media.type" width="15%" sortable="true">
					<c:choose>
					   <c:when test="${data.mediaType == MEDIA_TYPE_HTML }">
					    <comm:message key="common.mediaType.html" />  
					   </c:when>
					   <c:when test="${data.mediaType == MEDIA_TYPE_MEDIA }">
					    <comm:message key="common.mediaType.media" />  
					   </c:when>
					   <c:when test="${data.mediaType == MEDIA_TYPE_TEXT }">
					    <comm:message key="common.mediaType.text" />  
					   </c:when>
					   <c:when test="${data.mediaType == MEDIA_TYPE_IMG }">
					    <comm:message key="common.mediaType.img" />  
					   </c:when>
					</c:choose>
					</ec:column>
					<ec:column property="pathType" title="common.media.pathtype" sortable="true"  width="15%" >
					<c:choose>
					  <c:when test="${data.pathType == MEDIA_PATH_TYPE_LOCAL }">
					       <comm:message key="common.pathType.local" />  
					  </c:when>
					  <c:when test="${data.pathType == MEDIA_PATH_TYPE_NETWORK }">
					   <comm:message key="common.pathType.network" />  
					  </c:when>
					</c:choose>
			        </ec:column>
					<ec:column property="auditTime" title="common.media.auditTime" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="20%" />
					<ec:column property="null" title="unified.operater" sortable="false"  width="35%"  >
					       <a style="color:blue; cursor: pointer;" onclick="previewmedia('${data.mediaType}','${data.filepath }','${data.pathType }');"><comm:message key='common.media.preview'/></a>
					  <c:choose>
					    <c:when test="${data.pathType == MEDIA_PATH_TYPE_LOCAL }">
      					  <a style="color:blue; cursor: pointer;" onclick="Exp('${data.filepath}','${data.filename}');"><comm:message key='common.media.download'/></a>
					    </c:when>
					    <c:otherwise>
					     <font color="gray"><comm:message key='common.media.download'/></font>
					    </c:otherwise>
					  </c:choose>
					  <c:choose>
					   <c:when test="${data.auditUser eq userId}">
					    <a style="color:blue; cursor: pointer;" onclick="return delMedia(${data.id});"><comm:message key='comm.delete'/></a>
					   </c:when>
					   <c:otherwise>   
					     <font color="gray"><comm:message key='comm.delete'/></font>
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
	
	<script type="text/javascript">
		parent.document.getElementById('dataFrame').height = document.body.scrollHeight;
	</script>
</body>
</html>
