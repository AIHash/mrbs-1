<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8"%>
<html>
<head>
	<comm:pageHeader/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript">
		$(function(){
			parent.parent.popWindow();
		});
		function deleteCheck(deptId){
			parent.deleteCheck(deptId);
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
				action="${root}/meeadIndex/icuVisitList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row> 
					<ec:column property="requester.name" title="unified.meeting.requester" width="25%"/>
					<ec:column property="expectedTime" title="meeitng.time" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="20%"/> 
					<ec:column property="state" title="unified.status" sortable="true"  width="20%">
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
					<ec:column property="null" title="unified.operater" sortable="false"  width="45%">
						<a style="color:blue;" href="<%=request.getContextPath()%>/unified/viewicuVisitofManager/${data.id}" class="various3"><comm:message key='meeting.m.view'/></a>
						<c:choose>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING}">
								<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewICUVisitEditForMan/${data.id}" target="WorkBench" ><comm:message key='unified.edit'/> </a>
							</c:when>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PASS && data.endTime >= currDate}">
								<a style="COLOR:blue;" href="<%=request.getContextPath()%>/icumonitor/viewEditICUVisitForPass/${data.id}" target="WorkBench"><comm:message key="unified.edit" /></a>
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='unified.edit'/>
								</font>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING }">
								<a style="color:blue;" class="various3" href="<%=request.getContextPath()%>/icumonitor/redirctICUVisitPass?meetingappserchflag=0&requestmeetid=${data.id}"><comm:message key='meeting.m.through'/></a>
								<a style="color:blue;" class="various3" href="<%=request.getContextPath()%>/icumonitor/redirctRefuseIcuVist?meetingappserchflag=0&refusemeetingid=${data.id}"><comm:message key='meeting.m.refuse'/></a>
							</c:when>
							<c:otherwise>
								<font color="gray">
									<comm:message key='meeting.m.through'/>
									<comm:message key='meeting.m.refuse'/>
								</font>
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
