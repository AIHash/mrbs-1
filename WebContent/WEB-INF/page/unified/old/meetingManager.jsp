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
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<script type="text/javascript" > 
	
	function resizeauto()
	{
		if(parseInt($('#ec')[0].scrollHeight) < 300){
			$('#ec').css('height', $('#ec')[0].scrollHeight + screen.availHeight - document.body.scrollHeight);
		}
	}
   function init(){
      var ecside1=new ECSide("ec");
      ecside1.doPrep=false;
      ecside1.doPrepPrev=false;
      ecside1.init();
   }
   function myAlert()
	{
		var message='<%=returnmessage%>';
		if(message&&message!=""&&message!="null")
		{
			jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
		}
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
		});
	</script>
	<style>
	   input {height:20px;}
        select{background: #ffffff;border: 1px solid #7F9DB9;width:180px;color:#000000;height:24px;}
</style>
</head>

<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" onload="init();resizeauto();myAlert();heigthReset(64);" onresize="heigthReset(64);" class="bg">
<div id="center">
		<comm:navigator position="我的会诊>>我的会诊" />
	<div id="main" >
  <form name="form1" method="post" action="<%=request.getContextPath()%>/unifiedindex/meetingSearch" >
  	<table width="100%" border="0" cellspacing="5" cellpadding="0">
  	  <tr>
  	    <td align="right" class="text_left_red2">
  	    	<comm:message key='unified.starttime'/>：
  	    </td>
  	    <td>
          <input type="text" name="datetime" id="datetime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="border:#7F9DB9 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" readonly="readonly" size="25"/>
		</td>

        <td align="right" class="text_left_red2">
        	<comm:message key='unified.endtime'/>：
        </td>
  	 	 <td>
          <input type="text" name="enddatetime" id="enddatetime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="border:#7F9DB9 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" readonly="readonly" size="25"/>
        </td> 
  	    <td align="right" class="text_left_red2">
  	    	<comm:message key='unified.meetinglevel'/>：
  	    </td>
  	  	<td>
          <select name="meetingleveld" id="meetingleveld" style="width:160px;">
	  	              <option value=""><comm:message key='unified.status.all'/></option>
	                <c:forEach var="meetinglevel" items="${basecode['meetinglevel']}">
	                <option value="${meetinglevel.id}">${meetinglevel.name}</option>  
	                </c:forEach>
	            </select>
    	 </td>
    </tr>
	       <tr>
   	    <td align="right" class="text_left_red2">
  	    	<comm:message key='unified.meeting.name'/>：
  	    </td>
  	    <td>
         <input type="text" name="meetingname" size="25" id="meetingname" />
        </td>   	   
     
  	  <td align="right" class="text_left_red2">
  	  	<comm:message key='unified.meetingtype'/>：
  	  </td>
  	  <td>
  	   <select name="meetingtype" id="meetingtype" style="width:160px;">
          <option value="" selected="selected"><comm:message key='unified.status.all'/></option>
       <c:forEach var="meetingtype" items="${basecode['meetingtype']}">
                <option value="${meetingtype.id}">${meetingtype.name}</option>  
                </c:forEach>
    </select>
  	  </td>
  	   	  <td align="right" class="text_left_red2">
  	  	<comm:message key='unified.meeting.requester'/>：
  	  </td>
  	  <td>
  	   <input type="text" name="requester" size="25" id="requester"/>
  	  </td>
  	  </tr>
  	  <tr>
  	  <td align="right" class="text_left_red2">
  	  	<comm:message key='unified.status'/>：
  	  </td>
  	  <td><select name="state" id="state" style="width:160px;">
		    <option value=""><comm:message key='unified.status.all'/></option>
		    <option value="${MEETING_STATE_NONE }"><comm:message key='unified.status.none'/></option>
		    <option value="${MEETING_STATE_PENDING }"><comm:message key='unified.status.nostart'/></option>
		    <option value="${MEETING_STATE_START }"><comm:message key='unified.status.start'/></option>
		    <option value="${MEETING_STATE_END }"><comm:message key='unified.status.end'/></option>
		    <option value="${MEETING_STATE_ABSENCE }"><comm:message key='unified.status.absence'/></option>
  		</select></td>
  		 <td align="right" class="text_left_red2">
  	  	<comm:message key='unified.invite.status'/>：
  	  </td>
  	  <td><select name="applicatonstate" id="applicatonstate" style="width:160px;">
      <option value=""><comm:message key='unified.status.all'/></option>
      <option value="${APPLICATION_STATE_NONE }"><comm:message key='unified.invite.none'/></option>
      <option value="${APPLICATION_STATE_ACCEPT }"><comm:message key='unified.invite.accept'/></option>
      <option value="${APPLICATION_STATE_REFUSED }"><comm:message key='unified.invite.refuse'/></option>
      </select></td>
	    </tr>
  	  <tr>
  	    <td align="right" colspan="6">
  	    	<input class="button" type="submit" name="button" id="button" value="<comm:message key='unified.selected'/>" />
  	    </td>
	    </tr>
	  </table>
  </form>

	  <ec:table tableId="ec"
		var="datas"
		items="report_data"
		action="${root}/unifiedindex/meetingSearch"
		title=""
		width="100%"
		retrieveRowsCallback=""
		filterRowsCallback=""
		sortRowsCallback=""
		xlsFileName="Reoprt.xls">
			<ec:row>
				<ec:column property="title" title="unified.meeting.name" sortable="false"  width="20%"/>
				<ec:column property="meetingType" title="unified.meetingtype" sortable="false"  width="10%"/>
				<ec:column property="eventType" title="unified.eventtype" sortable="false"  width="7%"/>
				<ec:column property="startTime" title="unified.meeting.starttime" cell="date" format="yyyy-MM-dd HH:mm:ss" sortable="false" width="14%" />
				<ec:column property="requester" title="unified.meeting.requester" sortable="false"  width="10%"/>
				<ec:column property="status" title="unified.status" sortable="false"  width="8%"/>
				<ec:column property="operater" title="unified.operater" sortable="false"  width="14%"/>
			</ec:row>
	  </ec:table>
  </div>
</div>
				<div id="center_right">
				<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
			</div>
</body>
</html>