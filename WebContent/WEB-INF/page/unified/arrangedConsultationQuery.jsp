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
	<title><comm:message key="common.teleconsultation.arrangedConsultation" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<style type="text/css">
		.table_98per tr td input{margin-left:3px;}
		.table_98per tr td select{margin-left:3px;}
	</style>
	<script type="text/javascript"> 
		function check(){
			 var startTime=document.getElementById("meetingStartTime").value;
			 var endTime=document.getElementById("meetingEndTime").value;
			 if (startTime.length> 1&&endTime.length > 1&&!compareDateString(startTime, endTime)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }	
			return true;
		}
	</script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="common.teleconsultation>>common.teleconsultation.arrangedConsultation" />
		<div id="main">
		<form name="MyForm" action="${root}/unifiedindex/arrangedConsultationList?applicatonstate=${APPLICATION_STATE_ACCEPT}" method="post" onsubmit="return check()" target="Report">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td width="20%"  class="text_left_red2" align="right">
								<comm:message key="meeting.m.starttime"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="meetingStartTime" id="meetingStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:80px;height: 20px;line-height:20px;border: 1px solid #7F9DB9;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/> -
						 		<input type="text" name="meetingEndTime" id="meetingEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:80px;height: 20px;line-height:20px;border: 1px solid #7F9DB9;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>
						 	</td>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="meeting.m.keyWord"/>:&nbsp;&nbsp;
						  	</td>						  		
						 	<td  width="30%" align="left">
								<input type="text" name="keyWord" id="keyWord" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;" title="<comm:message key="meeting.m.keyWord.MeetingTip"/>"/>						 	
						 	</td>
						</tr>
						<tr>
						 	<td width="20%" align="right" class="text_left_red2">
						  		<comm:message key="unified.and.expert_meeting.m.state"/>:&nbsp;&nbsp;
						 	</td>
			             	<td width="30%" align="left">
								<select name="state" id="state" style="width:187px;height:20px;">
									<option value="" selected="selected"><comm:message key='unified.status.all'/></option>
								    <option value="${applicationScope.MEETING_STATE_PENDING }"><comm:message key='unified.status.nostart'/></option>
								    <option value="${applicationScope.MEETING_STATE_START }"><comm:message key='unified.status.start'/></option>
								    <option value="${applicationScope.MEETING_STATE_END }"><comm:message key='unified.status.end'/></option>
								</select>
			             	</td>
						</tr>
					</table>
				</td>
				<td class='queryBackground'></td>
			</tr>
		</table>
		<table align="center" width="697">
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
				<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/unifiedindex/arrangedConsultationList?applicatonstate=${APPLICATION_STATE_ACCEPT}"></iframe></td>
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
