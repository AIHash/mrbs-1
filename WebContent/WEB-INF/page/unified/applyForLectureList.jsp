<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<html>
<head>
	<title></title>
	<comm:pageHeader/>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript"> 
		$(document).ready(function() {
			parent.parent.popWindow();
		});
	</script>
</head>
<body style="overflow-x:hidden;" leftmargin="0" topmargin="0"onload="init()">

	<table width="700" border="0" cellspacing="0" cellpadding="0" height="100%">

	    <tr>
			<td colspan="4" height="5"></td>
		</tr>
		
		<tr>
			<td colspan="3" valign="top">
			<ec:table tableId="ec"
		        var="data"
				items="report_data"
				action="${root}/unifiedindex/applyForLectureList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="lectureContent" title="unified.lecture.content" width="20%" sortable="true">
					      <span title="${data.lectureContent}">${data.lectureContent}</span>
					</ec:column>
					<%-- <ec:column property="videoRequester.name" title="unified.meeting.requester" width="12%" sortable="true"/> --%>
					<ec:column property="suggestDate" title="unified.datetime" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="12%"/>
					<%-- <ec:column property="appDate" title="unified.app.time" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="12%"/> --%>
					<ec:column property="deptName.name" title="metting.speakerDepartmentName" sortable="false" width="12%"/>
					<ec:column property="userName.name" title="metting.speakerName" sortable="false" width="8%"/>
					<ec:column property="state" title="unified.status" sortable="true"  width="9%">
						<c:choose>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING }">
								<comm:message key="unified.status.pending" />
							</c:when>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PASS }">
								<comm:message key="unified.status.pass" />
							</c:when>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_REFUSED }">
								<comm:message key="unified.status.refused" />
							</c:when>
						</c:choose>
					</ec:column>
					<ec:column property="null" title="unified.operater" sortable="false"  width="10%">
						<a style="COLOR:blue;" href='${pageContext.request.contextPath}/meeadmdbd/viewVideoApp/${data.id}' class='various3'><comm:message key="unified.detail"/></a>
							<c:choose>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING && data.videoRequester.userId == USER_LOGIN_SESSION.userId}">
								<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/viewvedioappmeeting/${data.id}" target="WorkBench" ><comm:message key='unified.edit'/> </a>
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='unified.edit'/>
								</font>
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
