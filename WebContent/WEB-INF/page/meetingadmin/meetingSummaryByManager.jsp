<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<comm:pageHeader hasEcList="false"></comm:pageHeader>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<script type="text/javascript">
function removeTr(type, userId, expert_name){
	$('#' + userId + "_tr").remove();
	var options = $('#expert_tr option');
	var optionArray = new Array();
	var length = options.length;
	var optionValueSplit = "";
	if(length > 0){
		for(var i = 0; i < length; i++){
			optionValueSplit = options[i].value.split('#');
			optionArray[i] = optionValueSplit[0] + "#" + options[i].innerHTML + "#" + optionValueSplit[1];
		}
		optionArray[length] = type + "#" + expert_name + "#" + userId;
		optionArray.sort(function(a,b){return a.localeCompare(b)});

		$('#expert_select').empty();
		var splitArray = "";
		for(var i = 0; i < length + 1; i++){
			splitArray = optionArray[i].split('#');
			$("<option value=" + splitArray[0] + "#" + splitArray[2] + ">" + splitArray[1] +"</option>").appendTo($("#expert_select"));
		}
	}else{
		$("<option value=" + type + "#" + userId + ">" + expert_name +"</option>").appendTo($("#expert_select"));
	}
	$("#expert_tr").show();
}

$(function(){
	$("#addButton").click(function(){
		var expert_name = $('#expert_select option:selected').text();
		var typeAndUserId = $('#expert_select').val();
		var optionValueSplit = typeAndUserId.split('#');
		var type = optionValueSplit[0];
		var userId = optionValueSplit[1];
		var tr = $("<tr></tr>").attr('id', userId + "_tr");
		var remove = $("<input/>").attr('type', 'button').addClass('button').val("<comm:message key='comm.delete'/>").click(function(){
			removeTr(type, userId, expert_name);
		});
		var label_td = $("<td></td>").attr('align', 'right').attr('width', '20%')
			.append("<strong> " + expert_name + "</strong>").append("<span>:</span>").append("<span class='required'>*</span>")
			.append("<div id='" + userId + "_hint' style='margin-right: 10px;'>(<span id=" + userId + "_tip>500</span>/500)</div>")
			.append(remove);
		var textarea = $("<textarea></textarea>").attr('name', userId).attr('maxlength', 500).attr('rows', 5)
			.attr('onpaste', "return realTimeCountClip(this,'" + userId + "_hint');")
			.attr('onkeyup', "return realTimeCount(this,'" + userId + "_hint');")
			.attr('title', "<comm:message key='comm.content_maxlength'/>500")
			.css('width', "99.5%").css('border', '1px').addClass("text-long");
		var text_td = $("<td></td>").append(textarea);
		tr.append(label_td).append(text_td);
		$('#shenqing tr:last').after(tr);

		$('#expert_select option:selected').remove();
		if($('#expert_select option').length <= 0) {
			$("#expert_tr").hide();
		}
	});

	$('#submitaa').click(function(){
		var isSubmit = true;
		$('textarea').each(function(index, item){
			if(isNull($.trim(item.value))){
				jAlert("<comm:message key='report.notNull'/>","<comm:message key='meeting.m.infotishi'/>");
				isSubmit = false;
				return false;
			}
		});

		if(!isSubmit){
			return isSubmit;
		}
		
		$.post('${root}/summary/saveDataForManager', $("#form").serialize(), function(result){
			if(result == 'succ'){
				parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href;
				parent.$.fancybox.close();
			}else if(result.indexOf('<HTML>') != -1){
				window.location.href="${root}/index.jsp?message=system.session_expire";
			}
		});
	});
});

</script>
<style type="text/css">
/**修饰表格的边框**/
.submit_table{ border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px; width:90%; margin:auto; }
.submit_table caption {font-size:18px; font-weight:bolder;}
.submit_table td{ height:30px; line-height:30px;}
.submit_table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0; padding:2px;}
</style>
</head>
<body style="overflow-y:auto;">
<form id="form">
		  <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
			    <table width="90%" border="0" align="center">
			        <tr><td>&nbsp;</td></tr>
			    </table>    
			     <table id="shenqing" align="center"  border="1" class="submit_table" >
			          <tr>
			             <td align="center" colspan="2"><strong><comm:message key='meeting.m.msummary'/></strong>
			            	 <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}" />
			             </td>
			          </tr>
			          <c:choose>
			          	<c:when test="${fn:length(members) > 0}">
			          		<tr id="expert_tr">
					         <td height="150" width="20%" align="right"><strong><comm:message key='meeting.m.attend.user'/></strong>:&nbsp;&nbsp;</td>	
					         <td><select id="expert_select">
					         	<c:forEach var="member" items="${members}">
					         		<option value="${member.member.userType.value}#${member.member.userId}">${member.member.name}</option>
					         	</c:forEach>
					         </select>
					        <input type="button" class="button-large" value="<comm:message key='meeting.addSummary'/>" id="addButton"/>
					         </td>
				          </tr>
			          	</c:when>
			          	<c:otherwise>
			          		<tr id="expert_tr" style="display: none;">
						         <td height="150" width="20%" align="right"><strong><comm:message key='meeting.m.attend.user'/></strong>:&nbsp;&nbsp;</td>	
						         <td><select id="expert_select" style="width: 100px;">
						         </select>
						        	<input type="button" class="button-large" value="<comm:message key='meeting.addSummary'/>" id="addButton"/>
						         </td>
				          	</tr>
			          	</c:otherwise>
			          </c:choose>
			          <c:forEach var="summary" items="${requestScope.summarys}">
						<tr id="${summary.user.userId}_tr">
							<td width="20%" align="right">
								<strong>${summary.user.name}</strong>:<span class="required">*</span>
									<div style="margin-right: 10px;" id="${summary.user.userId}_hint">(<span id="${summary.user.userId}_tip">${500 - fn:length(summary.summary)}</span>/500)</div>
										<input type="button" class="button" value="删除" onclick="removeTr('${summary.user.userType.value}','${summary.user.userId}', '${summary.user.name}')"/>
							</td>
							<td>
								<textarea name="${summary.user.userId}" maxlength="500" rows="5" onpaste="return realTimeCountClip(this,'${summary.user.userId}_hint');" 
								onkeyup="return realTimeCount(this,'${summary.user.userId}_hint');" title="最大长度500"
								style="width: 100%; border: 1px none;" class="text-long">${summary.summary}</textarea>
							</td>
						</tr>
			          </c:forEach>
			     </table>
			     <table align="center">
			        <tr>
			           <td>
			              <input id="submitaa" class="button" name="submitaa" type="button" align="middle" value="提交" />
			           </td>
			           <c:if test="${fn:length(meeting.meetingSummarys) > 0}">
				           <td>
				              <input id="exportSuggestion" class="button" name="exportSuggestion" type="button" value="<comm:message key='report.m.reportLink'/>" align="middle" onclick="javascript:location.href='${root}/summary/exportExcel/${meeting.id}'"/>
				           </td>
			           </c:if>
			       </tr>
			       <tr><td>&nbsp;</td></tr>
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