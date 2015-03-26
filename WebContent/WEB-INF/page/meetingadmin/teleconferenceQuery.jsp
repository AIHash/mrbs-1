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
	<title><comm:message key="manager.teleconsultation.consultationAudit" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/jqueryui/css/jquery-ui-1.8.16.custom.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.core.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.widget.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.position.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.autocomplete.min.js"></script>
	<script type="text/javascript"> 
		function deleteCheck(deptId){
			jConfirm("<comm:message key='js.confirm_delete' args='unified.meeting'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
				if(resultConfirm){
			    	$.post('${pageContext.request.contextPath}/meeadmdbd/delTeleApp/' + deptId, function(text) {
						if(text == 'succ'){
							window.location.href = "<%=request.getContextPath()%>/meeadIndex/teleconferenceQuery";
						} else if(text=='error'){
							jAlert("删除失败！", "<comm:message key='meeting.m.infotishi'/>");
							return false;
						}else if(text.toLowerCase().indexOf('<html>') != -1){
							parent.window.location.href = "<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						}
					}, 'text');
			    }
			});
		}
		
	/* 	$(function(){
			$('#AutoRequesterUserName').autocomplete({
				source		: '${root}/meeadIndex/searchUser',
				minLength	: 2,
				scroll		: true,
				select		: function(event, ui) {
					$('#AutoRequesterUserName').val(ui.item.label);
					return false;
				}
			});
		});
		 */
		function check(){
			 var startTime=document.getElementById("expectedTimeStart").value;
			 var endTime=document.getElementById("expectedTimeEnd").value;
			 if (startTime.length> 1&&endTime.length > 1&&!compareDateString(startTime, endTime)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }			 
		}
	</script>
	<style type="text/css">
	.ui-autocomplete {
		max-height: 300px;
		overflow-y: auto;
		width: 145px;
	}
</style>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="consultation.app>>manager.teleconsultation.consultationAudit" />
		<div id="main">
		<form name="MyForm" action="${root}/meeadIndex/teleconferenceList" method="post" onsubmit="return check()" target="Report">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%"  class="text_left_red2" align="right">
								<comm:message key="meeitng.time"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="expectedTimeStart" id="expectedTimeStart" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:80px;height: 20px;line-height:20px;background:#fff url(${root}/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"/>
								<span>-</span><span>&nbsp;</span><input type="text" name="expectedTimeEnd" id="expectedTimeEnd"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:80px;height: 20px;line-height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>	
						 	</td>
						 	<td width="20%" class="text_left_red2" align="right">
						  		<comm:message key="meeting.m.app.request"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" id="AutoRequesterUserName" name="requesterId" maxlength="50" size="30" style="width:180px;height: 20px;line-height:20px;" />
						 	</td>
						</tr>
						<tr>
<%-- 						 	<td width="25%"  class="text_left_red2" align="right">
						  		<comm:message key="unified.meetingtype"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="25%" align="left">
						 		<select name="meetingType" id="meetingType" style="width:175px;height:20px;">
									<option value="-1">
										<comm:message key='unified.status.all' />
									</option>
									<c:forEach var="meetingtype" items="${basecode['meetingtype']}">
										<option value="${meetingtype.id}" <c:if test="${meetingtype.id==query_meetingType}">selected="selected"</c:if>>${meetingtype.name}</option>
									</c:forEach>
								</select>
						 	</td> --%>
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
						  		<comm:message key="meeting.m.app.keyword"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="keyWord" maxlength="50" size="30" style="width:180px;height: 20px;line-height:20px;" title="<comm:message key="meeting.m.keyWord.MeetingTip"/>"/>
						 	</td>
						</tr>
					</table>
				</td>
				<td width="1" class='queryBackground'></td>
			</tr>
		</table>
		<table align="center" width="697">
			<tr>
			  <td height="30" align="center" valign="bottom" class='queryBackground' colspan="3">
				<table class='buttonToolBar' id="querybuttonBk">
				  <tr>
					<td align="center"><input type="submit" name="new" value=" <comm:message key="comm.view"/>" class="button" >&nbsp;&nbsp;&nbsp;<img id="img1" src="${root}/resources/skin/style/Button_top2.gif" style="cursor:hand;" border="0" onclick="javascript:hideOrDisplayInputForm(inputForm, img1)" alt="<comm:message key='comm.hidden_input_parameter'/>"></td>
				  </tr>
				</table>
			  </td>
			</tr>  
			<tr>
				<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/meeadIndex/teleconferenceList"></iframe></td>
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
