<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp" %>
<html>
<head>
	<title><comm:message key="admin.system_service_log"/></title>
	<comm:pageHeader hasEcList="false"/>	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/style.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	function check(){
		if (MyForm.startDate.value.length < 1) {
			jAlert("<comm:message key="js.need_input" args="comm.start_time"/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		if (MyForm.endDate.value.length < 1) {
			jAlert("<comm:message key="js.need_input" args="comm.end_time"/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}

		if (!compareDateString(MyForm.startDate.value, MyForm.endDate.value)) {
			jAlert("<comm:message key="js.need_input_larger" args="comm.end_time,comm.start_time"/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		return true;
	}
</script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('../../resources/images/theme/bg_right-2.gif');">
<div id="center" style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');">
	<comm:navigator position="admin.title_log_manage>>admin.system_service_log" />
	<div id="main">
		<form name="MyForm" action="${root}/log/service/query" method="post" onsubmit="return check()" target="Report">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" align="center" cellspacing="0" cellpadding="0">
						<tr>
						  <td width="25%" class="text_left_red2" height="25" align="right"><comm:message key="admin.log.type"/>:&nbsp;&nbsp;</td>
						  <td width="25%" align="left"><select name="objectId" style="width:159px;background: #ffffff;border: 1px solid #7F9DB9;color:#000000;height:22px;">
				  			<option value="${SERVICE_ALL}"><comm:message key="service.all.desc"/></option>
					  		<c:forEach var="entry" items="${SERVICE_LOG_TYPE}" >
					  			<option value="${entry.key}">${entry.value}</option>
					  		</c:forEach>
				  			</select>
				  		</td>
				  		<td width="25%" class="text_left_red2" align="right"><comm:message key="unified.and.expert_admin.log.result"/>:&nbsp;&nbsp;</td>
				  		<td width="25%" align="left"><input type="radio" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" name="result" value="${SYSTEM_LOG_SUCCESS}"/><comm:message key="comm.success"/>
				  				<input type="radio" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" name="result" value="${SYSTEM_LOG_FAILED}"/><comm:message key="comm.failed"/>
				  				<input type="radio" style="border:0px;background:#eaf9ff;height:13px;vertical-align:middle;align:absmiddle;" name="result" value="${SYSTEM_LOG_ALL}" checked="checked" /><comm:message key="comm.all"/>
						</td>
					</tr>
					<tr>
					  <td width="25%" class="text_left_red2" align="right"><comm:message key="comm.start_time"/>:&nbsp;&nbsp;</td>
					 <!--<td width="223">&nbsp;<comm:dateTimeList name="startDate" others="style='width:150px'" /></td>  --> 
					<td width="25%" align="left"><input type="text" name="startDate" id="startDate" value="${SYSTEM_CURRENT_DATE}" onclick="WdatePicker({dateFmt:'yyyy-M-d'})"
						style="background:url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;height:20px;width:155px;"
						onfocus="this.blur()" /></td>
				      <td width="25%" align="right" class="text_left_red2"><comm:message key="comm.end_time"/>:&nbsp;&nbsp;</td>
	                  <td width="25%" align="left"><input type="text" name="endDate" id="endDate" value="${SYSTEM_CURRENT_DATE}" onclick="WdatePicker({dateFmt:'yyyy-M-d'})"
						style="background:url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;height:20px;width:155px;"
						onfocus="this.blur()" /></td>
					</tr>
				</table>
			</td>
			<td width="1" class='queryBackground'></td>
		</tr>
	</table>
	    <table width="697" align="center" height="100%">
		<tr>
		  <td height="30" align="center" valign="bottom" class='queryBackground' colspan="3">
			<table class='buttonToolBar' id="querybuttonBk">
			  <tr>
				<td align="center"><input type="submit" name="new" value=" <comm:message key='comm.view'/>" class="button" />&nbsp;&nbsp;&nbsp;<img id="img1"  src="${root}/resources/skin/style/Button_top2.gif" style="cursor:pointer;" border="0" onclick="javascript:hideOrDisplayInputForm(inputForm, img1)" alt="<comm:message key="comm.hidden_input_parameter"/>" /></td>
			  </tr>
			</table>
		  </td>
		</tr> 
		<tr>
			<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/log/service/query"></iframe></td>
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
