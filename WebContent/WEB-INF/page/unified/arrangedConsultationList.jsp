<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
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
				action="${root}/unifiedindex/arrangedConsultationList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="applicationId.patientInfo.name" title="meeting.patientsName" width="25%" sortable="true"/>
					<%-- <ec:column property="meetingType.name" title="meeting.m.app.type" width="11%"/> --%>
					<ec:column property="startTime" title="meeting.m.starttime" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="20%"/>
					<ec:column property="state" title="unified.consultationState" sortable="true"  width="15%">
						<c:choose>
							<c:when test="${data.state == MEETING_STATE_NONE }">
								<comm:message key="unified.status.none" />
							</c:when>
							<c:when test="${data.state == MEETING_STATE_PENDING }">
								<comm:message key="unified.status.nostart" />
							</c:when>
							<c:when test="${data.state == MEETING_STATE_START }">
								<comm:message key="unified.status.start" />
							</c:when>
							<c:when test="${data.state == MEETING_STATE_END }">
								<comm:message key="unified.status.end" />
							</c:when>
							<c:when test="${data.state == MEETING_STATE_ABSENCE }">
								<comm:message key="unified.status.absence" />
							</c:when>
						</c:choose>
					</ec:column>
					<ec:column property="meetingType.name" title="unified.meetingType" sortable="true"  width="15%">
					</ec:column>
					<%-- <ec:column property="level" title="meeting.m.meetinglevel" sortable="true"  width="10%">
						<c:choose>
							<c:when test="${data.level.value == MEETING_LEVEL_EMERGENCY }">
								<comm:message key="meeting.meetingLevelEmergency" />
							</c:when>
							<c:when test="${data.level.value == MEETING_LEVEL_COMMON }">
								<comm:message key="meeting.meetingLevelCommon" />
							</c:when>
						</c:choose>
					</ec:column> --%>
					<ec:column property="null" title="unified.operater" sortable="false"  width="35%">
						<a style="color:blue;" href="<%=request.getContextPath()%>/unified/arrangedmeetView/${data.id}" class='various3'><comm:message key='unified.detail'/></a>
						<c:choose>
							<c:when test="${data.state == MEETING_STATE_END || data.state == MEETING_STATE_START}">
<%-- 								<c:if test="${fn:length(data.satisfactions) == 0}"> --%>
                                   <c:if test="${data.state == MEETING_STATE_END }">
                                    <a style="color:blue;" href="${root}/unified/meetOpintion?opintionmeetingid=${data.id}&${requestParameter}" class='various3'><comm:message key='unified.opintion'/></a>
                                   </c:if>
								   <c:if test="${data.state != MEETING_STATE_END }">
                                     <font color="gray"><comm:message key='unified.opintion'/></font>
                                   </c:if>
<%-- 								</c:if>
								<c:if test="${fn:length(data.satisfactions) != 0}">
								    <font color="gray">
									    <comm:message key='unified.opintion'/>
									</font>
								</c:if> --%>
								<c:choose>
									<c:when test="${currUser.userType.value == USER_TYPE_PROFESSIONAL ||
										(currUser.userType.value == USER_TYPE_UNION && data.creator.userId == currUser.userId)}">
										<a style="color:blue;" href="<%=request.getContextPath()%>/summary/indexForUser/${data.id}" class='various3'><comm:message key='meeting.m.summary'/></a>
									</c:when>
									<c:otherwise>
										<font color="gray"><comm:message key='meeting.m.summary'/></font>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${fn:length(data.meetingSummarys) > 0}">
										<a style="color:blue;" href="${pageContext.request.contextPath}/summary/exportExcel/${data.id}"><comm:message key='report.m.reportLink'/></a>
									</c:when>
									<c:otherwise>
										<font color="gray"><comm:message key='report.m.reportLink'/></font>
									</c:otherwise>
								</c:choose>

							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='unified.opintion'/>
									<comm:message key='meeting.m.summary'/>
									<comm:message key='report.m.reportLink'/>
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
