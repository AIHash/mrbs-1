<!DOCTYPE html>
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp" %>
<html>
<head>
	<title><comm:message key="admin.system_operation_log" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/jqueryui/css/jquery-ui-1.8.16.custom.css" />
	<script src="${pageContext.request.contextPath }/resources/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryalerts/jquery.alerts.js"></script>
	<script src="${pageContext.request.contextPath }/resources/My97DatePicker/WdatePicker.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.core.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.widget.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.position.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.autocomplete.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#autoUserId').autocomplete({
			source		: '${root}/log/operation/searchUser',
			minLength	: 2,
			scroll		: true,
			select		: function(event, ui) {
				$('#autoUserId').val(ui.item.label);
				//$('#userId').val(ui.item.value);
				$('#userId').val(ui.item.label);
				return false;
			}
		});
	});

	function check(){
		if (!(strTrim(MyForm.userId.value)) == ""){
			if(!checkUserName(strTrim(MyForm.userId.value))){
				jAlert("<comm:message key='admin.userName.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
				return false;
			}
		}

		if (MyForm.startDate.value.length < 1){
			jAlert("<comm:message key='js.need_input' args='comm.start_time'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}

		if (MyForm.endDate.value.length < 1){
			jAlert("<comm:message key='js.need_input' args='comm.end_time'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}

		if (!compareDateString(MyForm.startDate.value, MyForm.endDate.value)){
		  jAlert("<comm:message key="js.need_input_larger" args="comm.end_time,comm.start_time"/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		
		var check = $('#autoUserId').val();
		if(check==null || check=="")
		$('#userId').val("");
		
		return true;
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
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('${pageContext.request.contextPath }/resources/images/theme/bg_right-2.gif');">
<div id="center">
		<comm:navigator position="admin.title_log_manage>>admin.system_operation_log" />
		<div id="main">
		<form name="MyForm" action="${root}/log/operation/query" method="post" onsubmit="return check()" target="Report">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" align="center" cellspacing="0" cellpadding="0">
						<tr>
							  <td width="25%"  class="text_left_red2" align="right"><comm:message key="unified.and.expert_admin.user"/>:&nbsp;&nbsp;</td>
							  <td width="25%" align="left"><input type="text" id="autoUserId" name="userId" width="100%" maxlength="20" size="23" />
							  	<!-- <input type="hidden" id="userId" name="userId" /> -->
							  </td>
							  <td width="25%" class="text_left_red2" align="right"><comm:message key="unified.and.expert_admin.log.result"/>:&nbsp;&nbsp;</td>
							  <td width="25%" align="left">
							  	<input type="radio" name="state" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" value="${SYSTEM_LOG_SUCCESS}"><comm:message key="comm.success"/><input type="radio" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="${SYSTEM_LOG_FAILED}"><comm:message key="comm.failed"/><input type="radio" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="" checked><comm:message key="comm.all"/>
							  </td>
							</tr>
							<tr>
							  <td width="25%"  class="text_left_red2" align="right"><comm:message key="comm.start_time"/>:&nbsp;&nbsp;</td>
							  <td width="25%" align="left">
							  	<input type="text" name="startDate" id="startDate" value="${SYSTEM_CURRENT_DATE}" onclick="WdatePicker({dateFmt:'yyyy-M-d'})"	style="border: 1px solid #7F9DB9;width:144px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"	onfocus="this.blur()" size="25" />
							  </td>
							  <td width="25%" align="right" class="text_left_red2"><comm:message key="comm.end_time"/>:&nbsp;&nbsp;</td>
				              <td width="25%" align="left">
				              	<input type="text" name="endDate" id="endDate" value="${SYSTEM_CURRENT_DATE}" onclick="WdatePicker({dateFmt:'yyyy-M-d'})"	style="border: 1px solid #7F9DB9;width:152px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"	onfocus="this.blur()" size="25" />
				             </td>
						</tr>
					</table>
				</td>
				<td width="1" class='queryBackground'></td>
	</tr>
	</table>
	<table align="center" width="697" height="100%">
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
			<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/log/operation/query"></iframe></td>
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
