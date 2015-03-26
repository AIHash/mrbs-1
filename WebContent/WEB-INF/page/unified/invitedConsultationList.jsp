<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
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
		function acceptedOrRefused(meetingId,flag){
		    parent.acceptedOrRefused(meetingId,flag);
		}
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
				<input type="hidden" name="startTime" value="${startTime}"/>
			</td>
		</tr>
		
		<tr>
			<td colspan="3" valign="top">
			<ec:table tableId="ec"
		        var="data"
				items="report_data"
				action="${root}/unifiedindex/invitedConsultationList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="applicationId.patientInfo.name" title="meeting.patientsName" width="20%" sortable="true"/>
					<%-- <ec:column property="meetingType.name" title="meeting.m.app.type" width="13%" sortable="true"/> --%>
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
					<%-- <ec:column property="level" title="meeting.m.meetinglevel" sortable="true"  width="11%">
						<c:choose>
							<c:when test="${data.level.value == MEETING_LEVEL_EMERGENCY }">
								<comm:message key="meeting.meetingLevelEmergency" />
							</c:when>
							<c:when test="${data.level.value == MEETING_LEVEL_COMMON }">
								<comm:message key="meeting.meetingLevelCommon" />
							</c:when>
						</c:choose>
					</ec:column> --%>
					<ec:column property="meetingType.name" title="unified.meetingtype" sortable="true"  width="15%"></ec:column>
					<ec:column property="applicatonstate" title="unified.invite.status" sortable="false"  width="15%">
						<c:choose>
							<c:when test="${data.beforeMin == APPLICATION_STATE_NONE }">
								<comm:message key="unified.invite.none" />
							</c:when>
							<c:when test="${data.beforeMin == APPLICATION_STATE_ACCEPT }">
								<comm:message key="unified.invite.accept" />
							</c:when>
							<c:when test="${data.beforeMin == APPLICATION_STATE_REFUSED }">
								<comm:message key="unified.invite.refuse" />
							</c:when>
						</c:choose>
					</ec:column>
					<ec:column property="null" title="unified.operater" sortable="false"  width="25%">
						<a style="color:blue;" href="<%=request.getContextPath()%>/unified/meetView/${data.id}" class='various3'><comm:message key='unified.detail'/></a>
						<c:choose >
							<c:when test="${data.state == MEETING_STATE_PENDING&&data.beforeMin == APPLICATION_STATE_NONE }">
								<a style="color:blue; cursor: pointer;" onclick="return acceptedOrRefused(${data.id},'accept');"><comm:message key='unified.accept'/></a>
								<a style="color:blue; cursor: pointer;" onclick="return acceptedOrRefused(${data.id},'refuse');"><comm:message key='unified.refuse'/></a>
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='unified.accept'/>
									<comm:message key='unified.refuse'/>
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
