<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
  <head>
    <base href="<%=basePath%>">
    <title><comm:message key='unified.meetingopintion'/></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/style/jrating/documentation.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/jrating/jquery.rating.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.rating.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.MetaData.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<style type="text/css">
		.exemple{margin-left:10px;}
		.auto-submit-star{margin:2px;}
		.main-submit-star{margin:2px;}
	</style>
	<script type="text/javascript">
	$(function(){
		 $('.manager-submit-star').rating({
			  callback: function(value, link){
			   $('#evalvalue').val(value);
			  }
			 });

		 $('#mainSubmit').click(function(){
			 var meetingTime=document.getElementById("meetingTime").value;
			 if(meetingTime==""||meetingTime==null)
			 {
				 alert("<comm:message key='unified.need.meetingTime'/>");
				 return false;
			 }
			 $.post('<%=request.getContextPath()%>/unified/addmanageropintion',
					 {evalvalue :$('#evalvalue').val(), meetingid : $('#meetingid').val(), meetingTime : $('#meetingTime').val()},
				function(text){
					if(text == 'success'){
						window.parent.location.href = window.parent.location.href;
					}else if(text.indexOf('<HTML>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
					}
			 }, 'text');
		 });
		});
	</script>
  </head>  
  <body id="windows">
  	<div>
		<img src="${pageContext.request.contextPath}/resources/images/unified/logo.jpg"/>
		<div style="height: 10px;"></div>
	</div>
  <form name="form1" action="<%=request.getContextPath()%>/unified/addmanageropintion" method="post">

    <table id="shenqing" class="submit_table" align="center" width="100%" border="1">
    <tr>
    <td align="center" colspan="2"><strong><comm:message key='unified.meetingopintion'/></strong></td>
    </tr>
    <tr>
    <td align="right"><strong><comm:message key='unified.purpose'/></strong>:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${meeting.title}</td>
    </tr>
    <tr>
    <td align="right"><strong><comm:message key='meeting.time'/></strong>:&nbsp;&nbsp;</td>
    <td><input type="text" name="meetingTime" id="meetingTime" maxlength="4"  style="width:50px" value="${satisfactionManager.meetingTime}" onkeyup="value=value.replace(/[^\d\.]/g,'')"/><comm:message key='meetingTime.unit'/></td>
    </tr>
	
    <c:forEach var="evaluation" items="${evallist}" varStatus="status"> 
    <tr>
    <td align="right"><strong>${evaluation.name}</strong>:&nbsp;&nbsp;</td>
	       <td>
	       <c:if test="${satisfactionManager.score == null}">
	       <input type="hidden" value="3" name="evalvalue" id="evalvalue">
	       </c:if>
	       <c:if test="${satisfactionManager.score != null}">
	        <input type="hidden" value="${satisfactionManager.score}" name="evalvalue" id="evalvalue">
	       </c:if>
				<div id="set_${status.index}" class="exemple">                             
							<input title="${evaluation.title1}" class="manager-submit-star" type="radio" name="star${status.index}" value="1" <c:if test="${satisfactionManager.score == 1}">checked="checked"</c:if> />
							 
							<input title="${evaluation.title2}" class="manager-submit-star" type="radio" name="star${status.index}" value="2" <c:if test="${satisfactionManager.score == 2}">checked="checked"</c:if>/>
							<c:if test="${satisfactionManager.score == null}">
							<input title="${evaluation.title3}" class="manager-submit-star" type="radio" name="star${status.index}" value="3" checked="checked" />
							 </c:if>
							  <c:if test="${satisfactionManager.score != null}">
							  <input title="${evaluation.title3}" class="manager-submit-star" type="radio" name="star${status.index}" value="3" <c:if test="${satisfactionManager.score == 3}">checked="checked"</c:if> />
							   </c:if>
							<input title="${evaluation.title4}" class="manager-submit-star" type="radio" name="star${status.index}" value="4" <c:if test="${satisfactionManager.score == 4}">checked="checked"</c:if>/>
							
							<input title="${evaluation.title5}" class="manager-submit-star" type="radio" name="star${status.index}" value="5" <c:if test="${satisfactionManager.score == 5}">checked="checked"</c:if>/>
				 </div>
		   </td>
    </tr>
    </c:forEach>
    </table>
     <table align="center">
	    <tr>
		    <td>
			    <input type="hidden" value="${meeting.id}" name="meetingid" id="meetingid">
			    <input type="button" value="<comm:message key='unified.sure'/>" id="mainSubmit">
		    </td>
	    </tr>
    </table>
    </form>
  </body>
</html>
