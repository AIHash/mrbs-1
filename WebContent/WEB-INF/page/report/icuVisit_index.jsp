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
		.submit_table caption {font-size:20px; font-weight:bold;background-color:#E6ECF1/* #eaf9ff *//* #FFFFCC *//* rgb(255,255,153) */;height:50px;line-height:50px;}
		.submit_table td{line-height:20px;}
		.submit_table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0; padding:2px;}
		#shenqing th {background-color:#E6ECF1/* rgb(0,0,128) */; line-height: 20px;}
		#shenqing th font{color:#0B4278;font-weight: bold;}
		#shenqing tbody tr td{/* background-color: rgb(255,255, 204); */}
		.odd td{background-color: #FFFFFF;}
		.even td{background-color: #f3f4f3;}
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

		document.getElementById("dataFrame").src = "${root}/teleStati/ICUvisitExcel?startDate=" + start + "&endDate=" + end;
		return true;
	}
</script>
</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" onload="heigthReset(173);" onresize="heigthReset(173);">
	<div id="center">
		<comm:navigator position="report.manager>>meeitng.report.icuVisit" />
		<form name="MyForm" id="MyForm" method="post" action="${root}/teleStati/ICUVisitPreview" onsubmit="return preview();">
		<table width="600" border="0" cellspacing="0" cellpadding="0" align="center" class="table_98per">
			<tr id="inputForm"  style="display:block;">
				<td class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="700" class="table_98per" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="140" class="text_left_red2" height="25" align="right"><comm:message key="comm.start_time"/>:&nbsp;&nbsp;</td>
							<td width="215" align="left"><input type="text" value="${startDate}" name="startDate" id="startDate" onclick="WdatePicker({dateFmt:'yyyy-M-d'})"
								style="background:url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;height:20px;width:155px;background-color:white;"
								onfocus="this.blur()"/></td>
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
						<input type="button" onclick="download(this)" value="<comm:message key="meeting.exportAllData"/>" class="button-large" />&nbsp;&nbsp;&nbsp;
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
				<table id="shenqing" class="submit_table" align="center" width="100%"  border="1" >
					<caption><comm:message key="meeting.icuVisit.summaryReport"/></caption>
					<col width="8%"/>
					<col width="14%"/>
					<col width="18%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
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
				         	<th><font><comm:message key="metting.consultationStatistics.date"/></font></th>
				         	<th><font><comm:message key="metting.report.meeting.submit.company"/></font></th>
				         	<th><font><comm:message key="metting.consultationStatistics.purpose"/></font></th>
				         	<th><font><comm:message key="comm.start_time"/></font></th>
				         	<th><font><comm:message key="comm.end_time"/></font></th>
				     
				         </tr>
			         </thead>
			         <tbody>
			          <c:if test="${summary != null && fn:length(summary) > 0 }">
			         	<c:forEach items="${summary}" var="videoVO">
				         	<c:if test="${videoVO.numId%2==0}">
					         	<tr style="background-color: #f3f4f3;" onmouseover="this.style.backgroundColor='#ecfbd4'" onmouseout="this.style.backgroundColor='f3f4f3'">
						         	<td align="center">${videoVO.numId}</td>
						            <td align="center">${videoVO.videoDate}</td>
						         	<td align="left">${videoVO.submitCompany.name}</td>
						         	<td align="left">${videoVO.videoContent}</td>
						         	<td align="left">${videoVO.startTime}</td>
						         	<td align="left">${videoVO.endTime}</td>
						         	
					         	</tr>
				         	</c:if>
				         	<c:if test="${videoVO.numId%2!=0}">
					         	 <tr style="background-color: #FFFFFF;" onmouseover="this.style.backgroundColor='#ecfbd4'" onmouseout="this.style.backgroundColor='#FFFFFF'">
							         	<td align="center">${videoVO.numId}</td>
							         	<td align="center">${videoVO.videoDate}</td>
							         	<td align="left">${videoVO.submitCompany.name}</td>
							         	<td align="left">${videoVO.videoContent}</td>
							         	<td align="left">${videoVO.startTime}</td>
						         	    <td align="left">${videoVO.endTime}</td>
						         	</tr>
					         </c:if>
				         </c:forEach>
				         </c:if>  
			         </tbody>
				</table>
			</c:if>
		</div>
	</div>
	<iframe id="dataFrame" align="top" frameborder="0" name="Report" width="100%" scrolling="no" style="display: none;"></iframe>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25" />
	</div>

<!-- <script language="javascript"> 
//table隔行换色
function tr_color(o,a,b,c,d){
 var t=document.getElementById(o).getElementsByTagName("tr");
 for(var i=0;i<t.length;i++){
  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
  t[i].onmouseover=function(){
   if(this.x!="1")this.style.backgroundColor=c;
  }
  t[i].onmouseout=function(){
   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
  }
 }
}

//tr_color("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
tr_color("shenqing","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
</script>	 -->
</body>
</html>