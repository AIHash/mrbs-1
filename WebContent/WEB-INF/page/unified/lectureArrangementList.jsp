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
			<td colspan="4" height="5"></td>
		</tr>
		
		<tr>
			<td colspan="3" valign="top">
			<ec:table tableId="ec"
		        var="data"
				items="report_data"
				action="${root}/unifiedindex/lectureArrangementList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="content" title="unified.lecture.content" width="20%" sortable="true">
					     <span title="${data.content}">${data.content}</span>
					</ec:column>
					<ec:column property="startTime" title="meeting.m.starttime" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="14%"/>
					<ec:column property="videoapplicationId.deptName.name" title="metting.speakerDepartmentName" sortable="false" width="12%" />
					<ec:column property="videoapplicationId.userName.name" title="metting.speakerName" sortable="false" width="10%" />
					<ec:column property="state" title="metting.lectureState" sortable="true"  width="9%">
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
					<ec:column property="applicatonstate" title="unified.invite.status" sortable="false"  width="9%">
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
					<ec:column property="null" title="unified.operater" sortable="false"  width="18%">
						<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/viewVideoArrangedApp/${data.id}" class="various3"><comm:message key='meeting.m.view'/></a>  	
						<c:choose >
							<c:when test="${data.state == MEETING_STATE_END&&data.beforeMin == APPLICATION_STATE_ACCEPT }">
								<%-- <c:if test="${fn:length(data.satisfactions) == 0}"> --%>
								    <a style="COLOR:blue;" style='color:blue' href="<%=request.getContextPath()%>/unified/meetOpintion?opintionmeetingid=${data.id}&${requestParameter}" class="various3" ><comm:message key='unified.opintion'/></a>
				<%-- 				</c:if>
								<c:if test="${fn:length(data.satisfactions) != 0}">
								    <font color="gray">
									    <comm:message key='unified.opintion'/>
									</font>
								</c:if> --%>
								
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='unified.opintion'/>
								</font>
							</c:otherwise>
						</c:choose>
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
