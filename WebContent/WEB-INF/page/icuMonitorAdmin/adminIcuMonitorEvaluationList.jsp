<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
	<title></title>
	<comm:pageHeader hasEcList="true"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript"> 
		$(document).ready(function() {
			parent.parent.popWindow();
		});
		function deleteCheck(meetingId){
			parent.deleteCheck(meetingId);
		}
		

		function  video(id){
			$.post('${pageContext.request.contextPath}/meeadmdbd/getMeetByVideo/' + id, function(text) {
				if(text!='error'){
					window.open(text);
				}else{
					alert("录播信息获取失败，请联系管理员！");
				}
				
			});
		}
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
				action="${root}/icumonitor/adminIcuMonitorEvaluationList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="creator.name" title="meeting.m.app.request" width="25%"/>
					<ec:column property="iCUMonitorId.patientInfo.name" title="meeting.patientsName" sortable="true"  width="15%"></ec:column>
					
					<%-- <ec:column property="meetingType.name" title="meeting.m.app.type" width="9%"/> --%>
					<ec:column property="startTime" title="meeitng.time" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="20%"/>
					<ec:column property="state" title="unified.status" sortable="true"  width="12%">
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
					<ec:column property="null" title="unified.operater" sortable="false"  width="35%">
						<a style="COLOR:blue;" href="<%=request.getContextPath()%>/icumonitor/icuMonitorViewAndInvitedState/${data.id}" class="various3"><comm:message key='meeting.m.view'/></a>  	
						<c:choose>
							<c:when test="${data.state == MEETING_STATE_END || data.state == MEETING_STATE_START}">
								<c:if test="${data.state == MEETING_STATE_END}">
								<a style="COLOR:blue;" href="<%=request.getContextPath()%>/icumonitor/meetOpintion?opintionmeetingid=${data.id}" class="various3" ><comm:message key='unified.opintion'/></a>
								</c:if>
								<c:if test="${data.state != MEETING_STATE_END}">
								 <font color="gray"><comm:message key='unified.opintion'/></font>
								</c:if>
								<a style="COLOR:blue;" href="<%=request.getContextPath()%>/summary/indexForManager/${data.id}" class="various3" ><comm:message key='meeting.m.summary'/></a>
							</c:when>

							<c:otherwise>
								<font color="gray">
									<comm:message key='unified.opintion'/>
									<comm:message key='meeting.m.summary'/>
								</font>
							</c:otherwise>
						</c:choose>
						<a style="color:blue; cursor: pointer;" onclick="return deleteCheck(${data.id});"><comm:message key='comm.delete'/></a>
						<c:choose>
						   <c:when test="${data.state == MEETING_STATE_END }">
								<a style="color:blue; cursor: pointer;" onclick="return video(${data.id});"><comm:message key='meeting.meetingviedo'/></a>
							</c:when>
							<c:when test="${data.state != MEETING_STATE_END }">
								<font color="gray"><comm:message key='meeting.meetingviedo'/></font> 
							</c:when>
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
