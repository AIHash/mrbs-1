<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<html>
<%
	String returnmessage = (String) request.getSession().getAttribute(
			"returnmessage");
	request.getSession().removeAttribute("returnmessage");
%>
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
		function myAlert(){  
		var message='<%=returnmessage%>';
		if(message&&message!=""&&message!="null") {
			jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
			}
		}
		function deleteCheck(appId){
			parent.deleteCheck(appId);
		}
	</script>
</head>
<body style="overflow-x:hidden;" leftmargin="0" topmargin="0"onload="init();myAlert();">

	<table width="700" border="0" cellspacing="0" cellpadding="0" height="100%">

	    <tr>
			<td colspan="4" height="5"></td>
		</tr>
		
		<tr>
			<td colspan="3" valign="top">
			<ec:table tableId="ec"
		        var="data"
				items="report_data"
				action="${root}/meeadIndex/videoLecturesList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="lectureContent" title="unified.lecture.content" width="29%">
					   <span title="${data.lectureContent}">${data.lectureContent}</span>
					</ec:column>					
					<ec:column property="videoRequester.name" title="unified.meeting.requester" width="14%"/>
					<ec:column property="suggestDate" title="meeitng.time" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="15%"/>
					<%-- <ec:column property="appDate" title="unified.app.time" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="17%"/> --%>
					<ec:column property="state" title="unified.status" sortable="true"  width="10%">
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
					<ec:column property="null" title="unified.operater" sortable="false"  width="31%">
						<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/viewVideoExamineApp/${data.id}" class="various3" ><comm:message key='meeting.m.view'/></a>
						<c:choose>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING && (data.videoRequester.userType.value == USER_TYPE_PROFESSIONAL||data.videoRequester.userType.value == USER_TYPE_UNION)}">
								<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewVideoAppMeeting/${data.id}" target="WorkBench" ><comm:message key='unified.edit'/> </a>
							</c:when>
							<c:when test="${(data.state == MEETING_APPLICATION_STATE_PENDING && data.videoRequester.userType.value == USER_TYPE_MEETING_ADMIN)||(data.state == MEETING_APPLICATION_STATE_PASS && data.endTime >= currDate)}">
								<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewvedioappmeetingForPass/${data.id}" target="WorkBench"><comm:message key="unified.edit" /></a>
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='unified.edit'/>
								</font>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING }">
								<a style="COLOR:blue;" class="various3"  href="<%=request.getContextPath()%>/meeadmdbd/redirctvideomeetingpass?meetingappserchflag=0&requestmeetid=${data.id}" ><comm:message key='meeting.m.through'/></a>
			    				<a style="COLOR:blue;" class="various3"  href="<%=request.getContextPath()%>/meeadmdbd/redirctvideomeetingrefuse?meetingappserchflag=0&refusemeetingid=${data.id}" ><comm:message key='meeting.m.refuse'/></a>              
								<font color="gray">
									<comm:message key='meeting.noticeName'/>(0)
								</font>
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='meeting.m.through'/>
									<comm:message key='meeting.m.refuse'/>
								</font>
								<c:if test="${data.state == MEETING_APPLICATION_STATE_PASS }">
									<a style="color:blue;" class="various3" href="<%=request.getContextPath()%>/meeadmdbd/vidoAuditNotice?refusemeetingid=${data.id}"><comm:message key='meeting.noticeName'/>(${data.noticeSendTimes})</a>
								</c:if>
								<c:if test="${data.state != MEETING_APPLICATION_STATE_PASS }">
									<font color="gray">
										<comm:message key='meeting.noticeName'/>(0)
									</font>
								</c:if>
							</c:otherwise>
						</c:choose>
						<a style="color:blue; cursor: pointer;" onclick="return deleteCheck(${data.id});"><comm:message key='comm.delete'/></a>
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
