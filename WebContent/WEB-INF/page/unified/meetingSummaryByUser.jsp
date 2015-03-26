<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<comm:pageHeader hasEcList="false"></comm:pageHeader>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<script type="text/javascript">
$(function(){
	$('#submitaa').click(function(){
		if(isNull($.trim($('#summary_textarea').val()))){
			jAlert("<comm:message key='report.notNull'/>");
			return false;
		}

		$.post('${root}/summary/saveDataForUser', $("#form").serialize(), function(result){
			if(result == 'succ'){
				parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href;
				parent.$.fancybox.close();
			}else if(result.indexOf('<HTML>') != -1){
				window.location.href="${root}/index.jsp?message=system.session_expire";
			}
		});
	});
});
/* 
//获取div显示内容的高度，并给改div的内容以外的地方预留高度
function getHeight() {      
var bodyHeight = document.body.clientHeight;
var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight - 60;
}

window.onload = function() { 	
getHeight();       	
}; */
</script>
</head>
<body style="overflow-y:auto;">
<form id="form">
		  <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
			     <table id="shenqing" align="center"  border="1" class="submit_table" >
			          <tr>
			             <td align="center" colspan="2"><strong><comm:message key='manager.teleconsultation.consultationSummary'/></strong>
			            	 <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}" />
			            	 <input type="hidden" name="meetingType" id="meetingType" value="${meeting.meetingType.id}" />
			             </td>
			          </tr>
			          <c:forEach var="summary" items="${requestScope.summarys}">
			          <c:choose>
			          	<c:when test="${summary.user.userId != sessionScope.USER_LOGIN_SESSION.userId}">
						<tr>
							<td width="20%" align="right">
								<strong>${summary.user.name}</strong>:<span class="required"></span>
							</td>
							<td>
								<textarea maxlength="500" rows="6" onpaste="return realTimeCountClip(this,'${summary.user.userId}_hint');" 
								onkeyup="return realTimeCount(this,'${summary.user.userId}_hint');" title="最大长度500" 
								style="width: 100%; border: 1px none;" class="text-long" disabled="disabled">${summary.summary}</textarea>
							</td>
						</tr>
						</c:when>
						<c:otherwise>
							<c:set var="currentUserSummary" value="${summary}" />
						</c:otherwise>
						</c:choose>
			          </c:forEach>
						<tr>
							<td width="20%" align="right">
								<strong>${sessionScope.USER_LOGIN_SESSION.name }</strong>:<span class="required">*</span>
									<div style="margin-right: 10px;" id="hint">(<span id="tip">${500 - fn:length(currentUserSummary.summary)}</span>/500)</div>
							</td>
							<td>
								<textarea id="summary_textarea" name="summary" maxlength="500" rows="6" onpaste="realTimeCountClip(this,'hint')" 
								onkeyup="realTimeCount(this,'hint')" title="最大长度500" style="width: 100%; border: 1px none;" class="text-long">${currentUserSummary.summary}</textarea>
							</td>
						</tr>
			     </table>
			     <table align="center">
			        <tr>
			           <td>
			              <input id="submitaa" class="button" name="submitaa" type="button" align="middle" value="提交" />
			           </td>
			       </tr>
			       <tr><td>&nbsp;</td></tr>
			       <tr><td>&nbsp;</td></tr>
			       <tr><td>&nbsp;</td></tr>
			       <tr><td>&nbsp;</td></tr>
			       <tr><td>&nbsp;</td></tr>
			       <tr><td>&nbsp;</td></tr>
			       <tr><td>&nbsp;</td></tr>
			    </table>
            </div>
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>
          </div>  
</form>
</body>

</html>