<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<html>
<head>
	<title></title>
	<comm:pageHeader hasEcList="true"/>
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
				action="${root}/unifiedindex/applyForICUVisitList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="patientInfo.name" title="meeting.patientsName" width="20%" sortable="true"/>
					<ec:column property="authorInfo.name" title="unified.medicalRecordSubmitPeople" width="15%" sortable="true">
						<c:choose>
							<c:when test="${data.authorInfo.name == null || data.authorInfo.name =='' }">
					            <c:if test="${fn:substring(data.requester.deptId.parentDeptCode, 0, 6) == '001002'||data.requester.deptId.deptcode == '001002' || fn:substring(data.requester.deptId.parentDeptCode, 0, 6) == '001001'||data.requester.deptId.deptcode == '001001'}"> 
					            	${data.requester.name}
					            </c:if>
					            <c:if test="${fn:substring(data.requester.deptId.parentDeptCode, 0, 6) == '001003'||data.requester.deptId.deptcode == '001003'}">
					            	${data.requester.username}
					            </c:if>
							</c:when>
							<c:otherwise>
								${data.authorInfo.name}
							</c:otherwise>
						</c:choose>
					</ec:column>
					<ec:column property="expectedTime" title="unified.datetime" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="20%"/>
					<ec:column property="state" title="unified.status" sortable="true"  width="15%" >
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
					<ec:column property="null" title="unified.operater" sortable="false"  width="20%">
						<a style="color:blue;" href='${pageContext.request.contextPath}/meeadmdbd/viewappICUVisit/${data.id}' class='various3'><comm:message key="unified.detail"/></a>
						<c:choose>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING && data.requester.userId == USER_LOGIN_SESSION.userId}">
								<a style="color:blue;" href="${root}/meeadmdbd/viewICUVisitEdit/${data.id}" target="WorkBench"><comm:message key="unified.edit" /></a>
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key="unified.edit" />
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
