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
	<title><comm:message key="manager.teleconsultation.consultationEvaluation" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/jqueryui/css/jquery-ui-1.8.16.custom.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.core.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.widget.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.position.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.autocomplete.min.js"></script>
	<script type="text/javascript">		
		$(function(){
			$('#attachmentUploadPerson').autocomplete({
				source		: '${root}/meeadIndex/searchUser',
				minLength	: 2,
				scroll		: true,
				select		: function(event, ui) {
					$('#attachmentUploadPerson').val(ui.item.label);
					return false;
				}
			});
		});
		
		function check(){
/* 			 var startTime=document.getElementById("startTime").value;
			 var endTime=document.getElementById("endTime").value;
			 if (startTime.length> 1&&endTime.length > 1&&!compareDateString(startTime, endTime)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  } */
		}
		
		/* function getManualInput(){
			var manualInput = $('#accessoriesType').val();
 			if(manualInput == 8){
 				$("#inputManual").show();
			}else{
				$("#inputManual").hide();
			}  
		}; */
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
		<comm:navigator position="meeting.management.of.attachment>>meeting.management.of.attachment" /> 
		<div id="main">
		<form name="MyForm" action="${root}/meeadIndex/managementOfAttachmentList" method="post" onsubmit="return check()" target="Report">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="meeting.attachment.of.type"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
			              		<select name="accessoriesType" id="accessoriesType" onchange="getManualInput();" style="width:187px;height: 20px;line-height:20px;border: 1px solid #7F9DB9;">
									<option value=""><comm:message key='unified.status.all'/></option>
									<c:forEach var="accessoriesType" items="${accessoriesTypes}" varStatus="cc">
									  <c:if test="${accessoriesType.value == 1}">
									     <option value="${accessoriesType.id}">${accessoriesType.name}</option>
									  </c:if>
									  <c:if test="${accessoriesType.value == 3}">
									     <option value="${accessoriesType.id}">${accessoriesType.name}</option>
									  </c:if>
									  <c:if test="${accessoriesType.value == 4}">
									     <option value="${accessoriesType.id}">${accessoriesType.name}</option>
									  </c:if>
									  <c:if test="${accessoriesType.value == 5}">
									     <option value="${accessoriesType.id}">${accessoriesType.name}</option>
									  </c:if>
									  <c:if test="${accessoriesType.value == 6}">
									     <option value="${accessoriesType.id}">${accessoriesType.name}</option>
									  </c:if>
									  <c:if test="${accessoriesType.value == 7}">
									     <option value="${accessoriesType.id}">${accessoriesType.name}</option>
									  </c:if>
									  <c:if test="${accessoriesType.value == 8}">
									     <option value="${accessoriesType.id}">${accessoriesType.name}</option>
									  </c:if>									  									  									  									  									  									  									  
									</c:forEach>
								</select>
			             	</td>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="meeting.attachment.of.name"/>:&nbsp;&nbsp;
						  	</td>						  		
						 	<td  width="30%" align="left">
								<input type="text" name="accessoriesName" id="accessoriesName" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;" title="<comm:message key="meeting.attachment.of.name.MeetingTip"/>"/>						 	
						 	</td>
						</tr>
						<tr>
						 	
						 	<td width="20%" align="right" class="text_left_red2">
						  		<comm:message key="meeting.attachment.of.upload.person"/>:&nbsp;&nbsp;
						 	</td>
			             	<td width="30%" align="left">
								<input type="text" id="attachmentUploadPerson" name="attachmentUploadPerson" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;" />
			             	</td>
						</tr>
						<%-- <tr style="display:none;" id="inputManual">
						   <td width="20%" align="right" class="text_left_red2">
						       <comm:message key="manual.input"/>:&nbsp;&nbsp;
						   </td>
						   <td width="30%" align="left">
						       <input type="text" id="manualInput" name="manualInput" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;" />
						   </td>
						</tr> --%>
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
				<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700"scrolling="no" src="${root}/meeadIndex/managementOfAttachmentList"></iframe></td>
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
