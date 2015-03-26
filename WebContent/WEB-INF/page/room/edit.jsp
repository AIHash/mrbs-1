<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<html>
<head>
	<title><comm:message key="admin.meetingroom_manage" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.MetaData.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<script type="text/javascript">
function check()
{
	if ((strTrim(form.name.value)) == "")
	{
	   jAlert("<comm:message key='js.need_input' args='admin.meetingroom_name' />", "<comm:message key='meeting.m.infotishi'/>");
	   form.name.focus();
	   return false;
	}
	
	if ((strTrim(form.sn.value)) == "")
	{
	   jAlert("<comm:message key='js.need_input' args='admin.meetingroom_sn' />", "<comm:message key='meeting.m.infotishi'/>");
	   form.sn.focus();
	   return false;
	}
	return true;
}

$(document).ready(function() {
	var count=getUnicodeLength(document.getElementById("remark").value);
   	$('#tip').html(500-count);
});
</script>
</head>
<body onload="focus('form','name');heigthReset(64);" onresize="heigthReset(64);" style="background:url('<%=request.getContextPath()%>/resources/images/theme/bg_right-2.gif');">
<div id="center" style="overflow-x:hidden;overflow-y:hidden;">
	<comm:navigator position="admin.title_system_manage>>admin.meetingroom_manage>>admin.meetingroom_update" />
	<div id="main" style="overflow-x:hidden;overflow-y:hidden;">
	<form name="form" action="${root}/meeting/room/update/${MeetingRoom.id}" method="post" onsubmit="return check()">
		  <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
				<table border="0" cellspacing="0" cellpadding="0" align="center">
					<tr>
						<td colspan="4" height="10"></td>
					</tr>
					<tr>
						<td height="25"  width="15%" align="right"><comm:message key="unified.and.expert_meetingroom_name"/>:<span class="required">*</span></td>
						<td width="35%" align="left"><input type="text" name="name" maxlength="50" size="30" style="width:150px" value="${MeetingRoom.name}" ></td>
						<td width="10%" align="right"><comm:message key="admin.meetingroom_sn"/>:<span class="required">*</span></td>
						<td width="40%" align="left"><input type="text" name="sn" maxlength="20" size="30" style="width:150px" value="${MeetingRoom.sn}"></td>
					</tr>
					<tr>
						<td height="25" align="right"><comm:message key="unified.and.expert_meetingroom_purpose"/>:&nbsp;</td>
						<td align="left"><input type="text" name="purpose" maxlength="50" size="30" style="width:150px" value="${MeetingRoom.purpose}"></td>
						<td align="right"><comm:message key="admin.meetingroom_size"/>:&nbsp;</td>
						<td align="left"><input type="text" name="size" maxlength="20" size="30" style="width:150px" value="${MeetingRoom.size}"></td>
					</tr>
					<tr>
							<td align="right"><comm:message key="unified.and.icuDeviceNO"/>:&nbsp;</td>
							<td align="left"><input type="text" name="icuDeviceNO" maxlength="50" size="30" style="width:150px" value="${MeetingRoom.icuDeviceNO}"/></td>
							<td align="right"></td>
							<td align="left"></td>
					</tr>
					<tr>
						<td height="25"  align="right"><comm:message key="admin.meetingroom_confirm"/>:&nbsp;</td>
						<td align="left"><input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="confirm" value="${SYSTEM_VALUE_YES}" <c:if test="${MeetingRoom.needConfirm}">checked</c:if>><comm:message key="comm.yes"/>&nbsp;&nbsp;<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="confirm" value="${SYSTEM_VALUE_NO}" <c:if test="${!MeetingRoom.needConfirm}">checked</c:if>><comm:message key="comm.no"/></td>
						<td align="right"><comm:message key="admin.meetingroom_state"/>:<span class="required">*</span></td>
						<td align="left"><input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="${MEETING_ROOM_STATE_ON}" <c:if test="${MeetingRoom.state == MEETING_ROOM_STATE_ON}">checked</c:if>><comm:message key="admin.meetingroom_state_on"/>&nbsp;&nbsp;<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="${MEETING_ROOM_STATE_OFF}" <c:if test="${MeetingRoom.state == MEETING_ROOM_STATE_OFF}">checked</c:if>><comm:message key="admin.meetingroom_state_off"/></td>
					</tr>
					<tr>
						<td height="25"  width="15%" align="right"><comm:message key="unified.and.expert_comm.remark"/>:&nbsp;<div id="hint">(<span id="tip"></span>/500)&nbsp;</div></td>
						<td colspan="3" align="left"><textarea name="remark" id="remark" cols="100" rows="8" style="width:80%;border: 1px solid #7F9DB9;" maxlength="500" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>500">${MeetingRoom.remark}</textarea></td>
					</tr>
					<tr>
						<td height="5" colspan="4" align="right"></td>
					</tr>
					<tr>
					  <td height="40" colspan="4"><div align="center"><input type="submit" name="new" value=" <comm:message key="comm.save"/>" class="button" >&nbsp;<input type="button" name="new" value=" <comm:message key="comm.cancel"/>" class="button" onClick="JavaScript:window.history.back()"></div></td>
					</tr>
				</table>            
            </div>
			<div id="titleStyle2">
				<span>&nbsp;</span>
			</div>
          </div>  	

	</form>
</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body2>
</html>