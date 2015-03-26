<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<html>
<head>
	<title></title>
	<comm:pageHeader hasEcList="true"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/skin/style/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
<script type="text/javascript">
 function checkSelected(){
   var checks = document.all.tags("INPUT");
   var value = 0;
   for(var i=0; i<checks.length; i++){
     if (checks[i].name == "userIDs" && checks[i].checked) {
       value = 1;
     }
   }
   if(value == 0){
     jAlert("<comm:message key='js.select_atleast_one_record'/>");
     return(false);
   }
   return true;
 }
 
 function setValue() {
 // var tag = "false";
   jConfirm("<comm:message key="js.confirm_delete_many" args="admin.user" />\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
	  if(checkSelected() && resultConfirm){
		  
		   var checks = document.getElementsByName('userIDs');
		   var values = new Array();
		   for(var i=0,j=0; i<checks.length; i++){
		     if (checks[i].type== "checkbox" && checks[i].checked) {
		       values[j]=checks[i].value;
		       j++;
		     }
		   }
		  $.post('${pageContext.request.contextPath}/user/delete?userIDs=' + values,  function(text) {
				if(text=='success'){
					parent.location.href="<%=request.getContextPath()%>/user/queryUser";
				}else if(text.indexOf('<HTML>') != -1){
					window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
				}
			}, 'text');
		  	  
//	    var url='${root}/user/delete';
	//  ECSideUtil.operateECForm(url,refreshList,{},true,"ec");
//		ECSideUtil.WaitingBar.hide();
//		document.getElementById("ec").action = url;
	//   document.getElementById("ec").submit();
//	    return true;
	//  }else{
	//   return false;
	  }	
});
}

//允许参加会议
function setAllowedValue() {
	jConfirm("<comm:message key="js.confirm_allowed_many" args="admin.user" />\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
		if(checkSelected() && resultConfirm){
			var checks = document.getElementsByName('userIDs');
		    var values = new Array();
		    for(var i=0,j=0; i<checks.length; i++){
		      if (checks[i].type== "checkbox" && checks[i].checked) {
		        values[j]=checks[i].value;
		        j++;
		      }
		    }
		    $.post('${pageContext.request.contextPath}/user/allowedOrRefused?userIDs=' + values+'&flag=Y',  function(text) {
				if(text=='success'){
					parent.location.href="<%=request.getContextPath()%>/user/queryUser";
				} else if(text=='failed'){
					jAlert("<comm:message key='comm.operateFailed'/>", "<comm:message key='meeting.m.infotishi'/>");
					return false;
				}else if(text.indexOf('<HTML>') != -1){
					window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
				}
			}, 'text');
		}else{
		    return false;
		}	
});
}	


//无法参加会议
function setRefusedValue() {
	jConfirm("<comm:message key="js.confirm_refused_many" args="admin.user" />\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
		if(checkSelected() && resultConfirm){
			var checks = document.getElementsByName('userIDs');
		    var values = new Array();
		    for(var i=0,j=0; i<checks.length; i++){
		      if (checks[i].type== "checkbox" && checks[i].checked) {
		        values[j]=checks[i].value;
		        j++;
		      }
		    }
		    $.post('${pageContext.request.contextPath}/user/allowedOrRefused?userIDs=' + values+'&flag=N',  function(text) {
				if(text=='success'){
					parent.location.href="<%=request.getContextPath()%>/user/queryUser";
				} else if(text=='failed'){
					jAlert("<comm:message key='comm.operateFailed'/>", "<comm:message key='meeting.m.infotishi'/>");
					return false;
				}else if(text.indexOf('<HTML>') != -1){
					window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
				}
			}, 'text');
		}else{
		    return false;
		}		
		});
}

function refreshList(){
  ECSideUtil.reload("ec",1);
  ECSideUtil.WaitingBar.hide();
}

</script>
</head>
<body onload="init()" style="overflow-x:hidden;">
<table align=center width="480" border="0" cellspacing="0" cellpadding="0">

	<tr>
		<td colspan="4" height="15" >&nbsp;</td>
	</tr>
	<c:if test="${parentNodeNo!='001'}">
		<tr>	
			<td colspan="5" height="30" valign="bottom">
				<input type="button" name="new" value=" <comm:message key="comm.add"/>" class="button" onclick="javascript:window.location.href='${root}/user/add'" />&nbsp;
				<input type="button" value="<comm:message key='comm.delete'/>" class="button" onclick="setValue();"/>&nbsp;
				<input  type="button" value="<comm:message key='comm.allowed'/>" class="button" onclick="setAllowedValue();"/>&nbsp;
				<input type="button" align="left" value="<comm:message key='comm.refused'/>" class="button" onclick="setRefusedValue();"/>&nbsp;
				<%-- <c:if test="${showButton}">
					<input type="button" value="<comm:message key='admin.user.excel'/>" class="button" onclick="javascript:location.href='${root}/user/getUserExcelForm'"/>
				</c:if> --%>
				<input type="button" value="<comm:message key='admin.user.excel'/>" class="button" onclick="javascript:location.href='${root}/user/getUserExcelForm'"/>
			</td>
		</tr>
	</c:if>
    <tr>
		<td colspan="3" height="5"></td>
	</tr>
	<tr>              
		<td colspan="10" valign="top">
		   <ec:table tableId="ec"
			var="datas"
			items="report_data"
			action="${root}/user/list"
			title=""
			width="100%"
			retrieveRowsCallback=""
			filterRowsCallback=""
			sortRowsCallback="" >
			<ec:row>
				<ec:column cell="checkbox"  headerCell="checkbox" alias="userIDs" value="${datas.userId}" width="7%" style="text-align:center;"/>
				<ec:column property="userId" title="admin.logon_name" width="12%"/>
				<ec:column property="name" title="admin.user_name" width="12%"/>

			 	<ec:column property="userType.value" title="admin.user_type" width="14%" >
			 		${datas.userType.name}
			 	</ec:column>
				<ec:column property="mobile" title="admin.user_mobile" width="15%" />
				<ec:column property="allowedOrRefusedFlag" title="admin.allowed_or_refused" width="12%" style="texta">
					<c:choose>
    					<c:when test="${datas.allowedOrRefusedFlag == USER_STATE_ON}">
							<comm:message key="comm.yes" />
    					</c:when>
    					<c:when test="${datas.allowedOrRefusedFlag == USER_STATE_OFF}">
							<comm:message key="comm.no" />
    					</c:when>   
   						<c:otherwise> 
							<comm:message key="comm.no" />
  						</c:otherwise>
					</c:choose>
				</ec:column>
				
				<ec:column property="state" title="admin.user_state" width="10%" style="texta">
					<c:if test="${datas.state == USER_STATE_ON}"><comm:message key="admin.user_state_on" /></c:if><c:if test="${datas.state == USER_STATE_OFF}"><comm:message key="admin.user_state_off" /></c:if>
				</ec:column>
	
				<ec:column property="null" title="comm.modify" sortable="false" viewsAllowed="html" width="18%" resizeable="false" style="text-align:center;">
				  <a onclick="javascript:window.location.href='${root}/user/edit/${datas.userId}'"><img alt="<comm:message key="comm.modify"/>" src="${root}/resources/skin/style/update.gif" style="cursor:pointer" /></a>
				</ec:column>
			</ec:row>
		</ec:table>

		   <table bgcolor="#ffffff" width="100%">
			  <tr>
				<td height="3"></td>
			  </tr>
		   </table>
		 </td>
	 </tr>	  
</table>
</body>
<script type="text/javascript">
		parent.document.getElementById('Report').height = document.body.scrollHeight;
</script>
</html>