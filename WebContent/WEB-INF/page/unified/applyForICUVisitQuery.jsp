<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="manager.icuMonit" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript"> 		
		function check(){
			 var startTime=document.getElementById("expectedTimeStart").value;
			 var endTime=document.getElementById("expectedTimeEnd").value;
			 if (startTime.length> 1&&endTime.length > 1&&!compareDateString(startTime, endTime)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }
			 return true;
		}
	</script>
	<style type="text/css">
		.table_98per tr td input{margin-left:3px;}
		.table_98per tr td select{margin-left:3px;}
	</style>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('<%=request.getContextPath()%>/resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="manager.icuVisit>>icuVisit.consultation" />
		<div id="main">
		<form name="MyForm" action="${root}/unifiedindex/applyForICUVisitList" method="post" target="Report" onsubmit="return check()">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%"  class="text_left_red2" align="right">
								<comm:message key="unified.datetime"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="expectedTimeStart" id="expectedTimeStart" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="25" onfocus="this.blur()"
									style="width:80px;height: 20px;line-height:20px;border: 1px solid #7F9DB9;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>
								<span>-</span><span>&nbsp;</span><input type="text" name="expectedTimeEnd" id="expectedTimeEnd"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:80px;height: 20px;line-height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>							 	
						 	</td>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="meeting.patientsName"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="patientName" maxlength="50" size="30" style="width:180px;height: 20px;line-height:20px;" />
						 	</td>
						</tr>
						<tr>
						 	<td width="20%" align="right" class="text_left_red2">
						  		<comm:message key="unified.and.expert_meeting.m.state"/>:&nbsp;&nbsp;
						 	</td>
			             	<td width="30%" align="left">
			              		<select name="state" id="state" style="width:187px;height:20px;">
									<option value="-1">
										<comm:message key='unified.status.all' />
									</option>
									<option value="${applicationScope.MEETING_APPLICATION_STATE_PENDING}">
										<comm:message key='unified.status.pending' />
									</option>
									<option value="${applicationScope.MEETING_APPLICATION_STATE_PASS}">
										<comm:message key='unified.status.pass' />
									</option>
									<option value="${applicationScope.MEETING_APPLICATION_STATE_REFUSED}">
										<comm:message key='unified.status.refused' />
									</option>
								</select>
			             	</td>
						 	<td width="20%" class="text_left_red2" align="right">
						  		<comm:message key="unified.and.expert_app.keyword"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="keyWord" maxlength="50" size="30" style="width:180px;height: 20px;line-height:20px;" title="<comm:message key="meeting.m.keyWord.MeetingTip"/>"/>
						 	</td>						 	
						</tr>
					</table>
				</td>
				<td class='queryBackground'></td>
			</tr>
		</table>
		<table width="697" align="center">
			<tr>
			  <td height="30" align="center" valign="bottom" class='queryBackground' colspan="3">
				<table class='buttonToolBar' style="background: #eaf9ff;border-color:#AFD7DF;">
				  <tr>
					<td align="center"><input type="submit" name="new" value=" <comm:message key="comm.view"/>" class="button" >&nbsp;&nbsp;&nbsp;<img id="img1" src="${root}/resources/skin/style/Button_top2.gif" style="cursor:hand;" border="0" onclick="javascript:hideOrDisplayInputForm(inputForm, img1)" alt="<comm:message key='comm.hidden_input_parameter'/>"></td>
				  </tr>
				</table>
			  </td>
			</tr>
			<tr>
				<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/unifiedindex/applyForICUVisitList"></iframe></td>
			</tr>
		</table>
	</form>
	</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>
