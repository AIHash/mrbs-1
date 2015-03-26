<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="meeting.m.detailedpage" /></title>
<link href="${pageContext.request.contextPath }/resources/css/style.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/style/main.css"
	rel="StyleSheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/jqueryui/css/jquery-ui-1.8.16.custom.css.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/jqueryui/jquery-ui-1.8.16.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/jqueryui/jqueryTab/jquery.idTabs.min.js"></script>
<style type="text/css">
.titleStyle {
	color: #3c3645;
	font-size: 24px;
	font-weight: bold;
	height: 20px;
	line-height: 20px;
	margin-top: 10px;
	margin-bottom: 5px;
	margin-left: 20px;
	margin-right: 25px;
	border: 0px dashed #3c3645;
}

#viewappinfor {
	border-top: 0px solid #7F9DB9;
	border-bottom: 0px solid #7F9DB9;
}

.meetingapp tr {
	height: 25px;
}

.meetingapp tr td {
	height: 15px;
	line-height: 25px;
	border-top: 0px solid #7F9DB9;
	border-bottom: 0px solid #7F9DB9;
}

.meetingapp tr td select {
	border: 1px solid #7F9DB9;
	margin-left: 3px;
	background-color: #F8F8FF;
	width: 95%;
}

.meetingapp tr td textarea {
	border: 1px solid #7F9DB9;
	margin-left: 3px;
	background-color: #F8F8FF;
	width: 95%;
}

.subinfor {
	border: 0px solid #7F9DB9;
	margin-left: 30px;
	margin-right: 30px;
}

._table {
	border-collapse: collapse;
	border: solid #D2D2D2;
	border-width: 1px 0 0 1px;
	margin-left: 0px;
}

._table caption {
	font-size: 18px;
	font-weight: bolder;
}

._table tr td input {
	width: 151px;
}

._table td {
	height: 25px;
	line-height: 25px;
	border: 1px solid #D2D2D2;
}
/*主容器*/
.usual {
	/* background:snow; */
	background: #fff;
	color: #111;
	padding: 0px 0px;
	width: 97%;
	border: 0px solid red;
	margin: 0px auto;
	vertial-align: middle;
}

/*选项卡*/
.usual ul {
	margin: 0;
	padding: 0;
	padding-left: 0em;
	overflow: auto; /*火狐等清理浮动*/
	_display: inline-block; /*ie6清理浮动*/
}

.usual li {
	list-style: none;
	float: left;
}

.usual ul a {
	display: block;
	padding: 6px 10px;
	text-decoration: none;
	font: 12px /* Arial */;
	color: #000;
	background: #D2D2D2;
	outline: none;
}

#myFileListName a {
	text-decoration: none;
	padding-top: 1px;
}

.usual ul a:hover {
	color: #FFF;
	background: #D2D2D2;
}

#myFileListName a:hover {
	text-decoration: underline;
	padding-top: 1px;
}

.usual ul a.selected {
	color: #000;
	background: snow;
	cursor: default;
}

/*tab页*/
.usual div {
	padding: 1px 1px 0px 1px;
	background: snow; /* 
    font:10pt Arial;  */
}

.submit_table tr td {
	width: 155px;
	height: 20px;
	overflow: hidden;
	white-space: nowrap;
}
</style>
<script type="text/javascript">
       window.onload = function(){ 
      	 
        	 //分辨率为1280*1024时
      	   if(window.screen.height == 1024){
          	   if(totalState < 14){
              	   var noState = 14 - totalState;
                  	   for(var i=noState; i>0; i--){
                  		   var objTable = document.getElementById('threeTable');//获得table
                  		   objTable.style.cssText = "border:0px solid red;";//将table的边框置0
                  		   var objTr= objTable.insertRow();//添加一行
                  		   objTr.style.cssText = "BORDER:#fff 0px solid";//将该行的边框置0
                  		   var objTd = objTr.insertCell();//添加一列
                  		   objTd.colSpan = 2;//合并列
                  		   objTd.innerHTML = "&nbsp;";//给tr创建一个td将其边框置0
                  		   objTd.style.cssText = "BORDER:green 0px solid";//将td边框置0
                  	   }
                     } 
      	   }

        	 
           //分辨率为1280*960时
      	   if(window.screen.height == 960){
          	   if(totalState < 11){
              	   var noState = 11 - totalState;
                  	   for(var i=noState; i>0; i--){
                  		   var objTable = document.getElementById('threeTable');//获得table
                  		   objTable.style.cssText = "border:0px solid red;";//将table的边框置0
                  		   var objTr= objTable.insertRow();//添加一行
                  		   objTr.style.cssText = "BORDER:#fff 0px solid";//将该行的边框置0
                  		   var objTd = objTr.insertCell();//添加一列
                  		   objTd.colSpan = 2;//合并列
                  		   objTd.innerHTML = "&nbsp;";//给tr创建一个td将其边框置0
                  		   objTd.style.cssText = "BORDER:green 0px solid";//将td边框置0
                  	   }
                     } 
      	   } 
    	   //分辨率为1440*900时
    	   if(window.screen.height == 900){
        	   if(totalState < 10){
            	   var noState = 10 - totalState;
                	   for(var i=noState; i>0; i--){
                		   var objTable = document.getElementById('threeTable');//获得table
                		   objTable.style.cssText = "border:0px solid red;";//将table的边框置0
                		   var objTr= objTable.insertRow();//添加一行
                		   objTr.style.cssText = "BORDER:#fff 0px solid";//将该行的边框置0
                		   var objTd = objTr.insertCell();//添加一列
                		   objTd.colSpan = 2;//合并列
                		   objTd.innerHTML = "&nbsp;";//给tr创建一个td将其边框置0
                		   objTd.style.cssText = "BORDER:green 0px solid";//将td边框置0
                	   }
                   }   
    	   }
         	 
       	   //分辨率为1152*864时
    	   if(window.screen.height == 864){
        	   if(totalState < 9){
            	   var noState = 9 - totalState;
                	   for(var i=noState; i>0; i--){
                		   var objTable = document.getElementById('threeTable');//获得table
                		   objTable.style.cssText = "border:0px solid red;";//将table的边框置0
                		   var objTr= objTable.insertRow();//添加一行
                		   objTr.style.cssText = "BORDER:#fff 0px solid";//将该行的边框置0
                		   var objTd = objTr.insertCell();//添加一列
                		   objTd.colSpan = 2;//合并列
                		   objTd.innerHTML = "&nbsp;";//给tr创建一个td将其边框置0
                		   objTd.style.cssText = "BORDER:green 0px solid";//将td边框置0
                	   }
                   } 
    	   }
       	   
    	   //分辨率为1024*768时
    	   if(window.screen.height == 768){

        	   if(totalState < 6){
            	   var noState = 6 - totalState;
                	   for(var i=noState; i>0; i--){
                		   var objTable = document.getElementById('threeTable');//获得table
                		   objTable.style.cssText = "border:0px solid red;";//将table的边框置0
                		   var objTr= objTable.insertRow();//添加一行
                		   objTr.style.cssText = "BORDER:#fff 0px solid";//将该行的边框置0
                		   var objTd = objTr.insertCell();//添加一列
                		   objTd.colSpan = 2;//合并列
                		   objTd.innerHTML = "&nbsp;";//给tr创建一个td将其边框置0
                		   objTd.style.cssText = "BORDER:green 0px solid";//将td边框置0
                	   }
                   } 
    	   }

         	
           //分辨率为1280*720时
    	   if(window.screen.height == 720){
        	   if(totalState < 5){
            	   var noState = 5 - totalState;
                	   for(var i=noState; i>0; i--){
                		   var objTable = document.getElementById('threeTable');//获得table
                		   objTable.style.cssText = "border:0px solid red;";//将table的边框置0
                		   var objTr= objTable.insertRow();//添加一行
                		   objTr.style.cssText = "BORDER:#fff 0px solid";//将该行的边框置0
                		   var objTd = objTr.insertCell();//添加一列
                		   objTd.colSpan = 2;//合并列
                		   objTd.innerHTML = "&nbsp;";//给tr创建一个td将其边框置0
                		   objTd.style.cssText = "BORDER:green 0px solid";//将td边框置0
                	   }
                   } 
    	   }
           
           //分辨率为1280*600时
    	   if(window.screen.height == 600){
        	   if(totalState < 1){
            	   var noState = 1 - totalState;
                	   for(var i=noState; i>0; i--){
                		   var objTable = document.getElementById('threeTable');//获得table
                		   objTable.style.cssText = "border:0px solid red;";//将table的边框置0
                		   var objTr= objTable.insertRow();//添加一行
                		   objTr.style.cssText = "BORDER:#fff 0px solid";//将该行的边框置0
                		   var objTd = objTr.insertCell();//添加一列
                		   objTd.colSpan = 2;//合并列
                		   objTd.innerHTML = "&nbsp;";//给tr创建一个td将其边框置0
                		   objTd.style.cssText = "BORDER:green 0px solid";//将td边框置0
                	   }
                   } 
    	   }
       };
</script>
</head>

<body style=" overflow-y:auto;">
	<div id="usual1" class="usual">
		<ul>
			<li><a class="selected" href="#tab1"> <comm:message
						key='unified.detail.info' /></a></li>
		</ul>
		<div id="tab1">
			<div id="viewappinfor"
				style="position:relative;background-color:#dee;overflow-y:auto;">
				<div class="subtitle" style="background-color:#dee;">
					<div class="titleStyle" id="titleid"
						style="font-size:22px;font-weight: bold;color:#000;background-color:#dee;">
						<comm:message key='unified.basic_patient_information' />
					</div>
				</div>
				<div class="subinfor" id="patientinfor1">
					<table width="100%" class="meetingapp" border="0" align="center"
						cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%" align="right"><strong><comm:message
										key='common.patientInfo.socialSecurityNO' /></strong>:&nbsp;&nbsp;</td>
							<td>${application.patientInfo.socialSecurityNO}</td>
						</tr>
						<tr>
							<td width="20%" align="right"><strong><comm:message
										key="unified.and.expert_name" /></strong>:&nbsp;&nbsp;</td>
							<td height="30">
								<table width="100%" border="0">
									<tr>
										<td width="20%">${application.patientInfo.name}</td>
										<td width="20%" align="right"><strong><comm:message
													key="unified.and.expert_user_sex" /></strong>:&nbsp;&nbsp;</td>
										<td width="15%"><c:choose>
												<c:when test="${0 == application.patientInfo.sex}">
													<comm:message key="comm.male" />
												</c:when>
												<c:otherwise>
													<comm:message key="comm.female" />
												</c:otherwise>
											</c:choose></td>
										<td width="20%" align="right"><strong><comm:message
													key="unified.and.expert_patient.age" /></strong>:&nbsp;&nbsp;</td>
										<td width="20%">${application.patientInfo.age}</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right"><strong><comm:message
										key="unified.address" /></strong>:&nbsp;&nbsp;</td>
							<td>${application.patientInfo.address}</td>
						</tr>
					</table>
				</div>
				<div class="subinfor" id="patientinfor2">
					<table width="100%" class="meetingapp" border="0" align="center"
						cellspacing="0" cellpadding="0">
						<c:if test="${2 == application.state}">
							<tr>
								<td width="20%" align="right"><strong><comm:message
											key="meeting.m.starttime" /></strong>:&nbsp;&nbsp;</td>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="30%"><c:if
													test="${ meeting.startTime != nulll}">
													<fmt:formatDate value="${meeting.startTime}"
														pattern="yyyy-MM-dd HH:mm" />
												</c:if></td>
											<td width="35%" align="right"><strong><comm:message
														key="meeting.m.endtime" /></strong>:&nbsp;&nbsp;</td>
											<td><c:if test="${ meeting.endTime != nulll}">
													<fmt:formatDate value="${meeting.endTime}"
														pattern="yyyy-MM-dd HH:mm" />
												</c:if></td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
						<c:if test="${3 == application.state}">
							<tr style="height:5px;"></tr>
							<tr>
								<td width="20%" align="right"><strong><comm:message
											key="meeting.m.refuseReson" /></strong>:&nbsp;&nbsp;</td>
								<td><textarea rows="5" class="text-long" maxlength="255"
										onfocus="this.blur()">${application.refuseReason}</textarea></td>
							</tr>
							<tr>
								<td width="20%" align="right"><strong><comm:message
											key="unified.datetime" /></strong>:&nbsp;&nbsp;</td>
								<td>
									<table border="0" width="100%" cellspacing="0" cellpadding="0">
										<tr>
											<td width="30%"><fmt:formatDate
													value="${application.expectedTime}"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<%-- 
												    <td width="35%" align="right"><strong><comm:message key="unified.meetinglevel"/></strong>:&nbsp;&nbsp;</td>
												    <td>${application.level.name}</td> --%>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
						<c:if test="${2 != application.state}">
							<tr>
								<td width="20%" align="right"><strong><comm:message
											key="unified.applicant.mobile" /></strong>:&nbsp;&nbsp;</td>
								<td>
									<table width="100%" border="0">
										<tr>
											<td width="30%">${application.authorInfo.mobile}</td>
											<td width="35%" align="right"><strong><comm:message
														key="unified.applicant.phone" /></strong>:&nbsp;&nbsp;</td>
											<td>${application.authorInfo.telephone}</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
						<c:if test="${1 == application.state}">
							<tr>
								<td width="20%" align="right"><strong><comm:message
											key="unified.datetime" /></strong>:&nbsp;&nbsp;</td>
								<td>
									<table border="0" width="100%" cellspacing="0" cellpadding="0">
										<tr>
											<td width="30%"><fmt:formatDate
													value="${application.expectedTime}"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<%-- 
														   <td width="35%" align="right"><strong><comm:message key="unified.meetinglevel"/></strong>:&nbsp;&nbsp;</td>
														   <td>${application.level.name}</td>         --%>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>

						<c:if test="${2 == application.state}">
							<tr>
								<td width="20%" align="right"><strong><comm:message
											key="unified.and.expert_meeting.m.state" /></strong>:&nbsp;&nbsp;</td>
								<td>
									<table width="100%" border="0">
										<tr>
											<td width="30%"><c:if test="${1==application.state}">
													<comm:message key="meeting.m.statewait" />
												</c:if> <c:if test="${2==application.state}">
													<comm:message key="meeting.m.statepass" />
												</c:if> <c:if test="${3==application.state}">
													<comm:message key="meeting.m.staterefuse" />
												</c:if></td>

										</tr>
									</table>
								</td>
							</tr>
						</c:if>
					</table>
					<table width="100%" border="0" align="center" cellspacing="0"
						cellpadding="0">

						<tr>
							<td width="20%" align="right"><c:if
									test="${application.requester.userType.value==4}">
									<strong><comm:message
											key='unified.and.expert.meeting.requester' />:&nbsp;&nbsp;</strong>
								</c:if> <c:if test="${application.requester.userType.value==5}">
									<strong><comm:message
											key='unified.and.expert_requester' />:&nbsp;&nbsp;</strong>
								</c:if></td>
							<td>
								<table background-color="#ddeefa" align="left">
									<tr>
										<td align="center" height="80">
											<table width="544" align="center" class="_table"
												style="margin-left:3px; border-bottom:0px;border-right:0px;"
												border="1">
												<tr>
													<c:if
														test="${fn:substring(application.requester.deptId.parentDeptCode, 0, 6) == '001001'||application.requester.deptId.deptcode == '001001'}">
														<td width="90" align="right" style="height:25px;"><comm:message
																key='group.name' />:&nbsp;</td>
														<td width="180" align="left"><comm:message
																key='meeting.type.inner' /></td>
														<td width="90" align="right"><comm:message
																key='unified.and.expert_username' />:&nbsp;</td>
														<c:if
															test="${fn:length(application.requester.name) <= 10}">
															<td align="left">${application.requester.name}&nbsp;</td>
														</c:if>
														<c:if test="${fn:length(application.requester.name) > 10}">
															<td align="left" title="${application.requester.name}">${fn:substring(application.requester.name, 0, 10)}...&nbsp;</td>
														</c:if>
													</c:if>
													<c:if
														test="${fn:substring(application.requester.deptId.parentDeptCode, 0, 6) == '001002'||application.requester.deptId.deptcode == '001002'}">
														<td align="right" style="height:25px;"><comm:message
																key='group.name' />:&nbsp;</td>
														<td align="left"><comm:message
																key='meeting.type.outter' /></td>
														<td align="right"><comm:message
																key='unified.and.expert_username' />:&nbsp;</td>
														<c:if
															test="${fn:length(application.requester.name) <= 10}">
															<td align="left">${application.requester.name}&nbsp;</td>
														</c:if>
														<c:if test="${fn:length(application.requester.name) > 10}">
															<td align="left" title="${application.requester.name}">${fn:substring(application.requester.name, 0, 10)}...&nbsp;</td>
														</c:if>
													</c:if>
													<c:if
														test="${fn:substring(application.requester.deptId.parentDeptCode, 0, 6) == '001003'||application.requester.deptId.deptcode == '001003'}">
														<td align="right" style="height:25px;"><comm:message
																key='group.name' />:&nbsp;</td>
														<td align="left">${application.requester.name}</td>
														<td align="right"><comm:message
																key='unified.and.expert_username' />:&nbsp;</td>
														<c:if
															test="${fn:length(application.requester.username) <= 10}">
															<td align="left">${application.requester.username}&nbsp;</td>
														</c:if>
														<c:if
															test="${fn:length(application.requester.username) > 10}">
															<td align="left"
																title="${application.requester.username}">${fn:substring(application.requester.username, 0, 10)}...&nbsp;</td>
														</c:if>
													</c:if>
												</tr>
												<tr>
													<td align="right" style="height:25px;"><comm:message
															key='unified.and.expert_mobile' />:&nbsp;</td>
													<td align="left">${application.requester.mobile}</td>
													<td align="right"><comm:message key='admin.user_tel' />:&nbsp;</td>
													<td align="left">${application.requester.telephone}</td>
												</tr>
												<tr>
													<td width="90" align="right" style="height:25px;"><comm:message
															key='group.department' />:&nbsp;</td>
													<td width="180" align="left">${application.requester.deptId.name}</td>
													<td width="90" align="right"><comm:message
															key='unified.and.expert_email' />:&nbsp;</td>
													<c:if test="${fn:length(application.requester.mail) <= 23}">
														<td width="180" align="left">${application.requester.mail}<input
															type="hidden" name="applicantid" id="applicantid" /></td>
													</c:if>
													<c:if test="${fn:length(application.requester.mail) > 23}">
														<td width="180" align="left"
															title="${application.requester.mail}">${fn:substring(application.requester.mail, 0, 23)}...&nbsp;<input
															type="hidden" name="applicantid" id="applicantid" /></td>
													</c:if>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right"><strong><comm:message
										key='unified.and.expert_author' />:&nbsp;&nbsp;</strong></td>
							<td>
								<table background-color="#ddeefa" align="left">
									<tr>
										<td height="80" align="center">
											<table width="544" class="_table"
												style="margin-left:3px;border-bottom:0px;border-right:0px;"
												align="center" border="1">
												<tr>
													<td align="right" width="90"><comm:message
															key='group.name' />:&nbsp;</td>
													<td align="left" width="180">${application.authorInfo.groupname}</td>
													<td align="right" width="90"><comm:message
															key='unified.and.expert_username' />:&nbsp;</td>
													<c:if
														test="${fn:length(application.authorInfo.name) <= 10}">
														<td align="left">${application.authorInfo.name}&nbsp;</td>
													</c:if>
													<c:if test="${fn:length(application.authorInfo.name) > 10}">
														<td align="left" title="${application.authorInfo.name}">${fn:substring(application.authorInfo.name, 0, 10)}...&nbsp;</td>
													</c:if>
												</tr>
												<tr>
													<td align="right"><comm:message
															key='unified.and.expert_mobile' />:&nbsp;</td>
													<td align="left">${application.authorInfo.mobile}</td>
													<td align="right"><comm:message key='admin.user_tel' />:&nbsp;</td>
													<td align="left">${application.authorInfo.telephone}</td>
												</tr>
												<tr>
													<td align="right" width="90"><comm:message
															key='group.department' />:&nbsp;</td>
													<td align="left" width="180">${application.authorInfo.department}</td>
													<td align="right" width="90"><comm:message
															key='unified.and.expert_email' />:&nbsp;</td>
													<c:if
														test="${fn:length(application.authorInfo.email) <= 23}">
														<td align="left" width="180">${application.authorInfo.email}</td>
													</c:if>
													<c:if
														test="${fn:length(application.authorInfo.email) > 23}">
														<td width="180" align="left"
															title="${application.authorInfo.email}">${fn:substring(application.authorInfo.email, 0, 23)}...&nbsp;<input
															type="hidden" name="applicantid" id="applicantid" /></td>
													</c:if>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!-- 会诊总结 -->
				<c:if test="${fn:length(meeting.meetingSummarys) > 0}">
					<div class="subtitle" style="background-color:#dee;">
						<div class="titleStyle"
							style="font-size:22px;font-weight:bold;color:#000;background-color:#dee;">
							<comm:message key='manager.teleconsultation.consultationSummary' />
						</div>
					</div>
					<div class="subinfor">
						<table width="100%" class="meetingapp" border="0" align="center"
							cellspacing="0" cellpadding="0">
							<c:forEach var="summary" items="${meeting.meetingSummarys}">
								<tr style="height:5px;"></tr>
								<tr>
									<td width="20%" align="right"><strong>${summary.user.name}</strong>:&nbsp;&nbsp;</td>
									<td><textarea rows="5" class="text-long"
											onfocus="this.blur()">${summary.summary}</textarea></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<div class="titleStyle" style="background-color:#dee;">
					<span>&nbsp;</span>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript">    
    $("#usual1 ul").idTabs();    
</script>
</body>
</html>