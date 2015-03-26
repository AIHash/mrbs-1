<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="meeting.m.detailedpage"/></title>
	<comm:pageHeader hasEcList="false"></comm:pageHeader>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" ></script>
    <script> 
	//获取div显示内容的高度，并给改div的内容以外的地方预留高度
    function getHeight() {      
		var bodyHeight = document.documentElement.clientHeight;
		var tableHeight = (document.getElementById("noticeDetailList").offsetHeight)+60;
		if(bodyHeight >= tableHeight ){
			var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight-60;		
	    }else{
	    	document.documentElement.style.overflowY = 'auto';
	    }
	}
        window.onload = function(){ 	
    	getHeight();       	
    	};
    </script>
    <style>
        #titleStyle{font-size:18px;font-weight: bold;height:30px;line-height:30px;margin-bottom:0px;margin-left:30px;margin-right:30px;}
		#viewappinfor{border:0px solid red;width:95%;align:center;margin-left:18px;margin-top:2px;margin-bottom:10px;margin-right:18px;position:relative;}
		.subinfor{border:0px solid ;margin-left:30px;margin-right:30px;}
		._table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px;}
		._table caption {font-size:20px; font-weight:bold;background-color:#FFFFFF/* rgb(255,255,153) */;height:50px;line-height:50px; border:solid #D2D2D2; border-width:1px 1px 0 1px;}
		._table th {border:solid #D2D2D2;height:30px;line-height:30px;border-width:0 1px 1px 0; padding:2px;}
		._table tr td input{width:151px;}
		._table td{height:25px; line-height:25px;border:1px solid #D2D2D2;}
    </style>
    
</head>
<body style=" overflow-y:auto;">
    <div id="viewappinfor" style="position:relative;background-color:#dee;">
		<div id="titleStyle">
			<span>&nbsp;</span>
		</div>
		<div class="subinfor" id="patientinfor1">
			<c:if test="${noticeDetailList != null}">
				<table width="100%" border="0" class="_table" id="noticeDetailList">					
					<caption><strong><comm:message key="meeting.detail.summary" /></strong></caption>
					<col width="10%"/>
					<col width="25%"/>
					<col width="65%"/>
					<thead>
				         <tr align="center">
				         	<th><font><comm:message key="metting.report.id" /></font></th>
				         	<th><font><comm:message key="meeting.sendTime" /></font></th>
				         	<th><font><comm:message key="meeting.notice.person" /></font></th>
				         </tr>
			         </thead>
			         <tbody>
			            <c:if test="${noticeDetailList != null && fn:length(noticeDetailList) > 0 }">
			         	<c:forEach items="${noticeDetailList}" var="list">
				         	       <tr style="background-color: #FFFFFF;">
							         	<td align="center">${list.id}</td>
							         	<td align="center">${list.sendTime}</td>
							         	<td align="left">${list.userName}</td>
						         	</tr>			         	
				         </c:forEach>
				         </c:if>
			         </tbody>
				</table>
			</c:if>
		</div>		
		<div id="titleStyle">
			<span>&nbsp;</span>
		</div>
	</div>
</body>
</html>