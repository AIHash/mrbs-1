<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="system.system_name" /></title>
	<comm:pageHeader />
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<script type="text/javascript"> 
	function init(){
		var ecside1=new ECSide("ec");
		ecside1.doPrep=false;
		ecside1.doPrepPrev=false;
		ecside1.init();
   	}
		$(document).ready(function() {
			$(".various3").fancybox({
				'width'				: '100%',
				'height'			: '100%',
				'autoScale'			: false,
                'transitionIn'  	: 'elastic',
                'transitionOut' 	: 'elastic',
//				'padding'			: '50px',
				'type'				: 'iframe'
			});
			if(parseInt($('#ec')[0].scrollHeight) < 300){
				$('#ec').css('height', $('#ec')[0].scrollHeight + screen.availHeight - document.body.scrollHeight);
			}
		});

		function myAlert() {
			   var message='<%=returnmessage%>';
			    if(message&&message!=""&&message!="null")
			    {
			      jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
			    }
		}
	</script>
		<style>
		input {height:20px;}
        select{background: #ffffff;border: 1px solid #7F9DB9;width:100px;color:#000000;height:24px;}
html,body {
	margin: 0;
	height: 100%;
	font: 14px "宋体", Arial;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
    background-color: #fff;
}
</style>
</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');"  onload="init();myAlert();heigthReset(64);" onresize="heigthReset(64);" class="bg">
<div id="center">
	<comm:navigator position="consultation.app>>manager.teleconsultation.consultationAudit" />
	<div id="main" >
		<form name="form1" method="post"	action="<%=request.getContextPath()%>/meeadIndex/applicationSearch" class="ychzsq">
			<table width="100%" border="0" cellspacing="5" cellpadding="0">
				<tr>
					<td align="right" class="text_left_red2">
						<comm:message key='unified.expectedTimeStart' />：
					</td>
					<td>
						<input type="text" name="expectedTimeStart" value="${query_expectedTimeStart}" id="expectedTimeStart" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							style="border: 1px solid #7F9DB9;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							readonly="readonly" size="25" />
					</td>
					<td align="right" class="text_left_red2">
						<comm:message key='unified.expectedTimeEnd' />：
					</td>
					<td>
						<input type="text" name="expectedTimeEnd" value="${query_expectedTimeEnd}" id="expectedTimeEnd" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							style="border: 1px solid #7F9DB9;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							readonly="readonly" size="25" />
					</td>
					<td align="right" class="text_left_red2">
						<comm:message key='unified.meetingtype' />：
					</td>
					<td>
						<select name="meetingType.id" id="meetingType" style="width: 130px;">
							<option value="-1">
								<comm:message key='unified.status.all' />
							</option>
							<c:forEach var="meetingtype" items="${basecode['meetingtype']}">
								<option value="${meetingtype.id}" <c:if test="${meetingtype.id==query_meetingType}">selected="selected"</c:if>>${meetingtype.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" class="text_left_red2"><comm:message key='unified.meetinglevel' />：</td>
					<td><select name="level.id" id="level" style="width: 155px;">
							<option value="-1">
								<comm:message key='unified.status.all' />
							</option>
							<c:forEach var="meetinglevel"
								items="${basecode['meetinglevel']}">
								<option value="${meetinglevel.id}" <c:if test="${meetinglevel.id==query_meetinglevel}">selected="selected"</c:if>>${meetinglevel.name}</option>
							</c:forEach>
					</select></td>
					<td align="right" class="text_left_red2"><comm:message	key='unified.status' />：</td>
					<td><select name="state" id="state" style="width: 155px;">
							<option value="-1">
								<comm:message key='unified.status.all' />
							</option>
							<option value="${MEETING_APPLICATION_STATE_PENDING}" <c:if test="${query_state==MEETING_APPLICATION_STATE_PENDING}">selected="selected"</c:if>>
								<comm:message key='unified.status.pending' />
							</option>
							<option value="${MEETING_APPLICATION_STATE_PASS}" <c:if test="${query_state==MEETING_APPLICATION_STATE_PASS}">selected="selected"</c:if>>
								<comm:message key='unified.status.pass' />
							</option>
							<option value="${MEETING_APPLICATION_STATE_REFUSED}" <c:if test="${query_state==MEETING_APPLICATION_STATE_REFUSED}">selected="selected"</c:if>>
								<comm:message key='unified.status.refused' />
							</option>
					</select>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="6"><input class="button"
						type="submit" name="button" id="button"
						value="<comm:message key='unified.selected'/>" /></td>
				</tr>
			</table>
		</form>
		<ec:table tableId="ec"
	var="data"
	items="report_data"
	action="${root}/meeadIndex/applicationSearch"
	title=""
	width="100%"
	sortable="false"
	retrieveRowsCallback=""
	filterRowsCallback=""
	sortRowsCallback=""
	xlsFileName="Reoprt.xls">
		<ec:row>
			<ec:column property="purpose" title="unified.purpose" width="20%"/>
			<ec:column property="requester.name" title="unified.meeting.requester" width="10%"/>
			<ec:column property="expectedTime" title="unified.datetime" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="12%"/>
			<ec:column property="applicationTime" title="unified.app.time" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="14%"/>
			<ec:column property="meetingType.name" title="unified.meetingtype" width="10%"/>
			<ec:column property="state" title="unified.status" sortable="true"  width="8%">
				<c:choose>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING }">
						<comm:message key="unified.status.pending" />
					</c:when>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_PASS }">
						<comm:message key="unified.status.pass" />
					</c:when>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_REFUSED }">
						<comm:message key="unified.status.refused" />
					</c:when>
				</c:choose>
			</ec:column>
			<ec:column property="null" title="unified.operater" sortable="false"  width="14%">
				<a style="COLOR:#8E388E;" class="various3" href="<%=request.getContextPath()%>/meeadmdbd/viewappmeeting/${data.id}"  class="various3"><comm:message key='meeting.m.view'/></a>
				<c:if test="${data.state == MEETING_APPLICATION_STATE_PENDING }">
					&nbsp;<a style="COLOR:#8E388E;" class="various3" href="<%=request.getContextPath()%>/meeadmdbd/redirctmeetingpass?meetingappserchflag=0&requestmeetid=${data.id}" class="various3"><comm:message key='meeting.m.through'/></a>
					&nbsp;<a style="COLOR:#8E388E;" class="various3" href="<%=request.getContextPath()%>/meeadmdbd/redirctrefuse?meetingappserchflag=0&refusemeetingid=${data.id}" class="various3"><comm:message key='meeting.m.refuse'/></a>
				</c:if>
			</ec:column>
		</ec:row>
	</ec:table>
    </div>
</div>
<div id="center_right">
	<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
</div>
</body>
</html>
