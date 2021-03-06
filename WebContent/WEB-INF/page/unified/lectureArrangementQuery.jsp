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
	<title><comm:message key="common.videoLectures.lectureArrangement" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
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
		$(function(){
			$('#departmentName').change(function(){
				$("#mainUserId").empty();
				$("<option value='-1'><comm:message key='unified.status.all' /></option>").appendTo("#mainUserId");
			     //动态的给主讲人姓名填值
			     $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/ajaxGetZhuanJian', 
						data : {depart: $(this).val() },
						dataType : 'json',
						dataFilter : function(json, type){
							if(json.indexOf('<html>') != -1){
								window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
								return;
							}
							return json;
						},
						success : function(json) {
							$.each(json, function(i, item){
								$("<option value=" + item.id+ ">" + item.name + "</option>").appendTo("#mainUserId");
							});
				}});
			});
		});
		/* $(function(){
			$('#autoDepartmentName').autocomplete({
				source		: '${root}/meeadIndex/searchDept',
				minLength	: 2,
				scroll		: true,
				select		: function(event, ui) {
					$('#autoDepartmentName').val(ui.item.label);
					return false;
				}
			});
			$('#mainUserId').autocomplete({
				source		: '${root}/meeadIndex/searchUser',
				minLength	: 2,
				scroll		: true,
				select		: function(event, ui) {
					$('#mainUserId').val(ui.item.label);
					return false;
				}
			});
		}); */
		function check(){
			 var startTime=document.getElementById("startTime").value;
			 var endTime=document.getElementById("endTime").value;
			 if (startTime.length> 1&&endTime.length > 1&&!compareDateString(startTime, endTime)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }			 		
			return true;
		}
		
		function acceptedOrRefused(meetingId,flag){
			jConfirm("<comm:message key='unified.isAccept'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
				if(resultConfirm){
			    	$.post('${pageContext.request.contextPath}/unifiedindex/acceptedOrRefused?meetingId=' + meetingId+'&flag='+flag, function(text) {
						if(text=='succ'){
							window.location.href = "<%=request.getContextPath()%>/unifiedindex/lectureArrangementQuery";
						} else if(text=='error'){
							if(flag=='accept'){
								jAlert("<comm:message key='unified.acceptFailed'/>", "<comm:message key='meeting.m.infotishi'/>");
							}else if(flag=='refuse'){
								jAlert("<comm:message key='unified.refuseFailed'/>", "<comm:message key='meeting.m.infotishi'/>");
							}
							return false;
						}else if(text.toLowerCase().indexOf('<html>') != -1){
							parent.window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						}
					}, 'text');
			    }else{
			    	return false;
			    }				
			});
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
		<comm:navigator position="common.videoLectures>>common.videoLectures.lectureArrangement" />
		<div id="main">
		<form name="MyForm" action="${root}/unifiedindex/lectureArrangementList" method="post" onsubmit="return check()" target="Report">
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
						  		<input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:80px;height: 20px;line-height:20px;border: 1px solid #7F9DB9;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/> -
						 		<input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:80px;height: 20px;line-height:20px;border: 1px solid #7F9DB9;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>
						 	</td>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="unified.and.expert_app.keyword"/>:&nbsp;&nbsp;
						  	</td>						  		
						 	<td  width="30%" align="left">
								<input type="text" name="keyWord" id="keyWord" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;" title="<comm:message key="meeting.m.keyWord.VideoTip"/>"/>						 	
						 	</td>
						</tr>
						<tr>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="metting.speakerDepartmentName"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						 		<input id="autoDepartmentName" name="departmentName" style="width:183px;height:20px;"/>
						 	</td>
						 	<td width="20%" align="right" class="text_left_red2">
						  		<comm:message key="unified.and.expert_metting.speakerName"/>:&nbsp;&nbsp;
						 	</td>
			             	<td width="30%" align="left">
			              		<input name="mainUserId" id="mainUserId" style="width:183px;height:20px;"/>
			              		<%-- <select name="mainUserId" id="mainUserId" style="width:187px;height:20px;">
			              			<option value='-1'><comm:message key='unified.status.all' /></option>
			              		</select> --%>
			             	</td>
						</tr>
						<tr>
							<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="unified.invite.status"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
			              		<select name="invitedState" id="invitedState" style="width:187px;height:20px;">
									<option value=""><comm:message key='unified.status.all'/></option>
									<option value="${applicationScope.APPLICATION_STATE_NONE }"><comm:message key='unified.invite.none'/></option>
									<option value="${applicationScope.APPLICATION_STATE_ACCEPT }"><comm:message key='unified.invite.accept'/></option>
									<option value="${applicationScope.APPLICATION_STATE_REFUSED }"><comm:message key='unified.invite.refuse'/></option>
								</select>
			             	</td>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="metting.lectureState"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
			              		<select name="state" id="state" style="width:187px;height:20px;">
									<option value=""><comm:message key='unified.status.all'/></option>
								    <%-- <option value="${MEETING_STATE_NONE }"><comm:message key='unified.status.none'/></option> --%>
								    <option value="${applicationScope.MEETING_STATE_PENDING }"><comm:message key='unified.status.nostart'/></option>
								    <option value="${applicationScope.MEETING_STATE_START }"><comm:message key='unified.status.start'/></option>
								    <option value="${applicationScope.MEETING_STATE_END }"><comm:message key='unified.status.end'/></option>
								    <%-- <option value="${MEETING_STATE_ABSENCE }"><comm:message key='unified.status.absence'/></option> --%>
								</select>
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
				<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/unifiedindex/lectureArrangementList"></iframe></td>
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
