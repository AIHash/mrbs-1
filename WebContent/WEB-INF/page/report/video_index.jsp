<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<comm:pageHeader hasEcList="false"/>
<title><comm:message key="meeting.m.detailedpage"/></title>
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<style type="text/css">
		.submit_table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px; width:98%; margin:auto;}
		.submit_table caption {font-size:20px; font-weight:bold;background-color:#E6ECF1/* rgb(255,255,153) */;height:50px;line-height:50px;}
		.submit_table td{line-height:20px;}
		.submit_table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0; padding:2px;}
		#shenqing th {background-color: #E6ECF1/* rgb(0,0,128) */; line-height: 20px;}
		#shenqing th font{color: #0B4278;font-weight: bold;}
		#shenqing tbody tr td{/* background-color: rgb(255,255, 204) */;}
	</style>
<script type="text/javascript">
	function preview(){
		var start = document.getElementById("startDate").value;
		if(start == null || start == ""){
			jAlert("<comm:message key='meeting.m.starttimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		var end = document.getElementById("endDate").value;
		if(end == null || end == ""){
			jAlert("<comm:message key='meeting.m.endtimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		if (start.length > 1 && end.length > 1
				&&!compareDateString(start, end)){
			jAlert("<comm:message key='meeting.alert.compareTime'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		return true;
	}

	function download(){
		var start = document.getElementById("startDate").value;
		if(start == null || start == ""){
			jAlert("<comm:message key='meeting.m.starttimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		var end = document.getElementById("endDate").value;
		if(end == null || end == ""){
			jAlert("<comm:message key='meeting.m.endtimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		if (start.length > 1 && end.length > 1
				&&!compareDateString(start, end)){
			jAlert("<comm:message key='meeting.alert.compareTime'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		
		document.getElementById("dataFrame").src = "${root}/videoStati/videoLecturesStatisticalExcel?startDate=" + start + "&endDate=" + end;
		return true;
	}
</script>
</head>

<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" onload="heigthReset(173);" onresize="heigthReset(173);">
	<div id="center">
		<comm:navigator position="report.manager>>meeting.video.summary" />
		<form name="MyForm" method="post" action="${root}/videoStati/videoLecturesStatisticalPreview" onsubmit="return preview();">
		<table width="600" border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
			<tr id="inputForm"  style="display:block;">
				<td class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="700" class="table_98per" border="0" cellspacing="0" cellpadding="0">
						<tr>
						 	<td width="140px" class="text_left_red2" height="25" align="right"><comm:message key="metting.consultationStatistics.reportType"/>:&nbsp;&nbsp;</td>
						 	<td width="215px" colspan="3" align="left">
						 	    <input type="radio" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" name="type" value="1" <c:if test="${type == 1 || type == null}">checked="checked"</c:if>/><comm:message key="metting.consultationStatistics.summary"/>
						 		<input type="radio" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" name="type" value="2" <c:if test="${type == 2}">checked="checked"</c:if>/><comm:message key="metting.consultationStatistics.detail"/>
				  			</td>
						</tr>
						<tr>
							<td width="140" class="text_left_red2" height="25" align="right"><comm:message key="comm.start_time"/>:&nbsp;&nbsp;</td>
							<td width="215" align="left"><input type="text" value="${startDate}" name="startDate" id="startDate" onclick="WdatePicker({dateFmt:'yyyy-M-d'})"
								style="background:url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;height:20px;width:155px;background-color:white;"
								onfocus="this.blur()" /></td>
						      <td width="140" align="right" class="text_left_red2"><comm:message key="comm.end_time"/>:&nbsp;&nbsp;</td>
			                  <td width="215" align="left"><input type="text" name="endDate" value="${endDate }" id="endDate" onclick="WdatePicker({dateFmt:'yyyy-M-d'})"
								style="background:url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;height:20px;width:155px;background-color:white;"
								onfocus="this.blur()" /></td>
						</tr>
				</table>
				</td>
				<td class='queryBackground'>&nbsp;</td>
			</tr>
			</table>
				<table align="center" border="0" width="715">
			<tr>
			  <td align="center" valign="bottom" class='queryBackground' colspan="3">
				<table class='buttonToolBar'  id="querybuttonBk">
				  <tr>
					<td align="center">
						<input type="submit" value="<comm:message key="meeting.preview"/>" class="button" />&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="download()" value="<comm:message key="meeting.exportAllData"/>" class="button-large" />&nbsp;&nbsp;&nbsp;
					</td>
				  </tr>
				</table>
			  </td>
			</tr>
		</table>
		</form>
		<div style="height: 10px;"></div>
		<div id="main">
			<c:if test="${summary != null}">
				<table id="shenqing" class="submit_table" align="center" width="100%" border="1">
				<caption><comm:message key="meeting.video.summaryReport"/></caption>
				<col width="8%"/>
				<col width="15%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="15%"/>
				<col width="11%"/>
				<col width="11%"/>
				<thead>
			        <tr>
						<td colspan="8">
			         		<comm:message key="metting.report.input.date"/>:&nbsp;<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/><br />
							<comm:message key="metting.report.input.perosn"/>:&nbsp;${sessionScope['USER_LOGIN_SESSION'].name}<br />
							<comm:message key="metting.report.time"/>:&nbsp;${startDate}至${endDate}
						</td>
			         </tr>
			         <tr align="center">
			         	<th><font><comm:message key="metting.report.id"/></font></th>
			         	<th><font><comm:message key="metting.videoLectures.video.date"/></font></th>
			         	<th><font><comm:message key="unified.lecture.content"/></font></th>
			         	<th><font><comm:message key="unified.lecture.people.dept"/></font></th>
			         	<th><font><comm:message key="unified.lecture.people.name"/></font></th>
			         	<th><font><comm:message key="metting.report.community.number"/></font></th>
			         	<th><font><comm:message key="metting.report.totalnumber"/></font></th>
			         </tr>
		         </thead>
		         <tbody>
		            <c:if test="${summary != null && fn:length(summary) > 0 }">
		         	<c:forEach items="${summary}" var="videoVO">
			         	<c:if test="${videoVO.numId%2==0}">
				         	<tr style="background-color: #f3f4f3;" onmouseover="this.style.backgroundColor='#ecfbd4'" onmouseout="this.style.backgroundColor='#f3f4f3'">
					         	<td align="center">${videoVO.numId}</td>
					         	<td align="center">${videoVO.videoDate}</td>
					         	<td align="left">${videoVO.videoContent}</td>
					         	<td align="left">${videoVO.clinicalDepartmentName}</td>
					         	<td align="left">${videoVO.expertsName}</td>
					         	<td align="center">${videoVO.community}</td>
					         	<td align="center">${videoVO.lecturesPeopleCount}</td>
				         	</tr>
			         	</c:if>
			         	<c:if test="${videoVO.numId%2!=0}">
				         	<tr style="background-color: #FFFFFF;" onmouseover="this.style.backgroundColor='#ecfbd4'" onmouseout="this.style.backgroundColor='#FFFFFF'">
					         	<td align="center">${videoVO.numId}</td>
					         	<td align="center">${videoVO.videoDate}</td>
					         	<td align="left">${videoVO.videoContent}</td>
					         	<td align="left">${videoVO.clinicalDepartmentName}</td>
					         	<td align="left">${videoVO.expertsName}</td>
					         	<td align="center">${videoVO.community}</td>
					         	<td align="center">${videoVO.lecturesPeopleCount}</td>
				         	</tr>
			         	</c:if>
			         </c:forEach>
			         </c:if>
		         </tbody>
		        </table>
			</c:if>

			<c:if test="${detail != null}">
				<table id="shenqing" class="submit_table" align="center" width="100%" border="1">
					<caption><comm:message key="meeting.video.detailReport"/></caption>
					<col width="8%"/>
					<col width="10%"/>
					<col width="18%"/>
					<col width="12%"/>
					<col width="15%"/>
					<col width="15%"/>
					<col width="14%"/>
					<col width="8%"/>
					<thead>
				        <tr>
							<td colspan="8">
				         		<comm:message key="metting.report.input.date"/>:&nbsp;<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/><br />
								<comm:message key="metting.report.input.perosn"/>:&nbsp;${sessionScope['USER_LOGIN_SESSION'].name}<br />
								<comm:message key="metting.report.time"/>:&nbsp;${startDate}至${endDate}
							</td>
				         </tr>
				         <tr align="center">
				         	<th><font><comm:message key="metting.report.id"/></font></th>
				         	<th><font><comm:message key="metting.videoLectures.video.date"/></font></th>
				         	<th><font><comm:message key="unified.lecture.content"/></font></th>
				         	<th><font><comm:message key="unified.lecture.people.dept"/></font></th>
				         	<th><font><comm:message key="unified.lecture.people.name"/></font></th>
				         	<th><font><comm:message key="metting.report.community"/></font></th>
				         	<th><font><comm:message key="local.number"/></font></th>
				         	<th><font><comm:message key="metting.report.totalnumber"/></font></th>
				         </tr>
			         </thead>
			         <tbody>
			         <c:if test="${detail != null && fn:length(detail) > 0 }">
			         	<c:forEach items="${detail}" var="vDetailVO">
			         		<c:forEach items="${vDetailVO.comms}" var="comm" varStatus="status">
			         		    <c:if test="${vDetailVO.numId%2==0}">
					         		<tr style="background-color: #f3f4f3;">
					         			<c:if test="${status.index == 0}">
					         				<td rowspan="${fn:length(vDetailVO.comms)}" align="center">${vDetailVO.numId}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="center">${vDetailVO.videoDate}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="left">${vDetailVO.videoContent}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="left">${vDetailVO.clinicalDepartmentName}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="left">${vDetailVO.expertsName}</td>
					         			</c:if>
						         			<td align="left">${comm.community}</td>
						         			<td align="center">${comm.lecturesPeopleCount}</td>
					         			<c:if test="${status.index == 0}">
					         				<td rowspan="${fn:length(vDetailVO.comms)}" align="center">${vDetailVO.count}</td>
					         			</c:if>
					         		</tr>
					         	</c:if>
					         	<c:if test="${vDetailVO.numId%2!=0}">
					         		<tr style="background-color: #FFFFFF;">
					         			<c:if test="${status.index == 0}">
					         				<td rowspan="${fn:length(vDetailVO.comms)}" align="center">${vDetailVO.numId}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="center">${vDetailVO.videoDate}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="left">${vDetailVO.videoContent}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="left">${vDetailVO.clinicalDepartmentName}</td>
							         		<td rowspan="${fn:length(vDetailVO.comms)}" align="left">${vDetailVO.expertsName}</td>
					         			</c:if>
						         			<td align="left">${comm.community}</td>
						         			<td align="center">${comm.lecturesPeopleCount}</td>
					         			<c:if test="${status.index == 0}">
					         				<td rowspan="${fn:length(vDetailVO.comms)}" align="center">${vDetailVO.count}</td>
					         			</c:if>
					         		</tr>
				         		</c:if>
				         	</c:forEach>
				         </c:forEach>
				         </c:if>
			         </tbody>
			        </table>
			</c:if>
		</div>
	</div>
	<iframe id="dataFrame" align="top" frameborder="0" name="Report" width="100%" scrolling="no" style="display: none;"></iframe>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>