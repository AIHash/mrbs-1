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
		function Exp(filepath,title){  
			title = encodeURI(encodeURI(title));
			window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
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
		        var="accessory"
				items="report_data.resultlist"
				action="${root}/meeadIndex/managementOfAttachmentList?${requestParameter}"
		        title=""
		        width="700"
		        retrieveRowsCallback=""
		        filterRowsCallback=""
		        sortRowsCallback="">
				<ec:row>
					<ec:column property="name" title="meeting.attachment.of.name" width="30%"/>
					<ec:column property="owner.name" title="meeting.attachment.of.upload.person" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="20%"/>
					<ec:column property="type.value" title="meeting.attachment.of.type" sortable="true"  width="20%">
						<c:choose>
							<c:when test="${accessory.type.value == 1}">
								<comm:message key="meeting.case.discussion" />
							</c:when>
							<c:when test="${accessory.type.value == 2 }">
								<comm:message key="meeting.radiation.imaging" />
							</c:when>
							<c:when test="${accessory.type.value == 3 }">
								<comm:message key="meeting.check.result" />
							</c:when>
							<c:when test="${accessory.type.value == 4 }">
								<comm:message key="meeting.test.result" />
							</c:when>
							<c:when test="${accessory.type.value == 5 }">
								<comm:message key="meeting.pathology" />
							</c:when>
							<c:when test="${accessory.type.value == 6 }">
								<comm:message key="meeting.ultrasound" />
							</c:when>
							<c:when test="${accessory.type.value == 7 }">
								<comm:message key="meeting.electrocardiogram" />
							</c:when>
							<c:when test="${accessory.type.value >= 8 }">
								<comm:message key="meeting.other.manual.input" />
							</c:when>
						</c:choose>
					</ec:column>
					<ec:column property="null" title="unified.operater" sortable="false"  width="15%">
					    <a style="COLOR:blue;" href="#" onclick="Exp('${accessory.path}','${accessory.name}')"><comm:message key='report.m.reportLink'/></a> 	
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
