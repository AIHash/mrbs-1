<!DOCTYPE html>
<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp" %>
<html>

<head>
    <comm:pageHeader hasEcList="true" />
    <link href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript">
		function deleteCheck(roomId){
			jConfirm("<comm:message key='js.confirm_delete' args='admin.meetingroom' />", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
				if(resultConfirm){
			    	$.get('${pageContext.request.contextPath}/meeting/room/delete/' + roomId, function(text) {
						if(text=='succ'){
							window.location.href = "${pageContext.request.contextPath}/meeting/room/list";
						} else if(text == 'fail'){
							jAlert("此会议室已被使用，不能删除", "<comm:message key='meeting.m.infotishi'/>");
							return false;
						}else if(text.toLowerCase().indexOf('<html>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						}
					}, 'text');
			    }			
			});
		}
	</script>
	<style type="text/css">
	html,body {padding:0; margin:0;height:100%;background-color: #ffffff; OVERFLOW:HIDDEN;scrollbar-3d-light-color :threedhighlight;scrollbar-highlight-color :threedhighlight;scrollbar-face-color : threedface;scrollbar-arrow-color : buttontext;scrollbar-shadow-color :ThreedDarkShadow;scrollbar-dark-shadow-color :threeddarkshadow;scrollbar-base-color : buttonface;}
	#daohang{margin:0px;width:725px;height:14px;background:url(${pageContext.request.contextPath}/resources/images/theme/bg_center3.gif) repeat-x;}
	/*------------------*/
	
	#center{padding:0px;margin:0px;width:725px;float:left;}
	#main{ overflow-y:auto; overflow-x:hidden;width:725px;margin:0px;float:left;}
	
	#center_right{width:25px; margin:0px; float:left;}
	#mr_pic{width:700px;height:460px;background:repeat-x complete:complete;}
	#breadcrumb{ background:url("${pageContext.request.contextPath}/resources/images/theme/bg_center.gif") repeat-x; line-height:50px; font-size:12px;width:725px; }
	</style>
</head>

<body onload="heigthReset(64);init();" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('${pageContext.request.contextPath}/resources/images/theme/bg_right-2.gif');">
<div id="center">
	<comm:navigator position="admin.title_system_manage>>admin.meetingroom_manage" />
	<div id="main">
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
		
	    <td  colspan="3" height="20" valign="bottom"><input type="button" name="new" value=" <comm:message key="comm.add"/>" class="button" onClick="javascript:location.href='${root}/meeting/room/add'" />&nbsp;</td>
	</tr>
	<tr>
	   <td colspan="3" valign="top" align="center">
       <ec:table tableId="ec"
        var="room"
        items="report_data"
        action="${root}/meeting/room/list"
        title=""
        width="100%"
        retrieveRowsCallback=""
        filterRowsCallback=""
        sortRowsCallback="">
        <ec:row>
        <ec:column property="name" title="admin.meetingroom_name" width="20%"/>
        <ec:column property="sn" title="admin.meetingroom_sn" width="10%"/>
        <ec:column property="state" title="admin.meetingroom_state" width="10%" >
        	<c:if test="${room.state == MEETING_ROOM_STATE_ON}"><comm:message key="admin.meetingroom_state_on" /></c:if><c:if test="${room.state == MEETING_ROOM_STATE_OFF}"><comm:message key="admin.meetingroom_state_off" /></c:if>
        </ec:column>
        <ec:column property="needConfirm" title="admin.meetingroom_confirm" width="10%" >
        	<c:if test="${room.needConfirm}"><comm:message key="comm.yes" /></c:if><c:if test="${!room.needConfirm}"><comm:message key="comm.no" /></c:if>
        </ec:column>
        <ec:column property="size" title="admin.meetingroom_size" width="10%"/>
        <ec:column property="purpose" title="admin.meetingroom_purpose" width="20%"/>
 	    <ec:column property="null" title="comm.modify" sortable="false" viewsAllowed="html" width="10%" resizeable="false" style="text-align:center;">
         <a href="${root}/meeting/room/edit/${room.id}"><img alt="<comm:message key="comm.modify"/>" src="${root}/resources/skin/style/update.gif" /></a>
          </ec:column>
         <ec:column property="null" title="comm.delete" sortable="false" viewsAllowed="html" width="10%" resizeable="false"  style="text-align:center;">
            <a onclick="return deleteCheck(${room.id});"><img alt="<comm:message key="comm.delete"/>" src="${root}/resources/skin/style/delete.gif" /></a>
          </ec:column>       
         </ec:row>
      </ec:table>
      </td>
	</tr>
</table>
</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>