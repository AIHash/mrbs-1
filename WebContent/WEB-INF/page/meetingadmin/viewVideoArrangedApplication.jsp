<%@ page isELIgnored="false" pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="unified.application" />
</title>
<comm:pageHeader hasEcList="false" />
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<style type="text/css">
    #center{background-color:#ffffff;border:0px solid red;width:100%;align:center;position:relative;}
	._table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px;margin-left:0px; }
	._table caption {font-size:18px; font-weight:bolder;}
	._table tr td input{width:151px;}
	._table td{height:25px; line-height:25px;border:1px solid #D2D2D2;}
</style>
<script type="text/javascript">
//获取div显示内容的高度，并给改div的内容以外的地方预留高度
function getHeight() {      
	var bodyHeight = document.documentElement.clientHeight;
	var tableHeight = (document.getElementById("shenqing").offsetHeight)+60;
	if(bodyHeight >= tableHeight ){
		var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight-60;		
    }else{
    	document.documentElement.style.overflowY = 'auto';
    }
}
function Exp(filepath,title){  
	title = encodeURI(encodeURI(title));
	window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
}
window.onload = function() { 
	var satisfaction = '${satisfaction}';
	if(satisfaction == ""){
		getHeight();
	}       	
};
</script>
</head>
<body class="bg" style="overflow-y:auto;">
	<div id="center">
	      <div id="viewappinfor">
			<div id="titleStyle" style="margin-left:25px;">
				<span><comm:message key='vedio.app' /></span>
			</div>
	        <div class="subinfor" id="patientinfor1">
				<table width="100%" id="shenqing" class="shenqing_table" border="0">
					<tr style="height:10px;"></tr>
					<tr>
						<td width="10%" align="right"><comm:message key='unified.lecture.content' />:&nbsp;&nbsp;</td>
						<td colspan="3" align="left">
                             <textarea onfocus="this.blur()" name="lectureContent" style="border: 1px solid #7F9DB9;width:93%;height:30px;" id="lectureContent" rows="2" class="text-long" maxlength="200" title=" <comm:message key="comm.content_maxlength"/>200">${vmapp.lectureContent}</textarea>
						</td>
					</tr>
					<tr style="height:5px;"></tr>
					<tr>
						<td width="10%"  align="right"><comm:message key='meeting.teachingObject' />:&nbsp;&nbsp;</td>						
						<td colspan="3" align="left">
						      <textarea onfocus="this.blur()" name="teachingObject" style="border: 1px solid #7F9DB9;width:93%;height:50px;" id="teachingObject" rows="5" class="text-long" maxlength="500" title=" <comm:message key="comm.content_maxlength"/>500">${vmapp.teachingObject}</textarea>
						</td>
					</tr>
					<tr>
						<%-- <td width="25%" align="right"><comm:message key='unified.lecture.people.position' />:&nbsp;&nbsp;</td>
						<td width="25%" align="left">${vmapp.applicationPosition.name }</td> --%>
						<c:if test="${vmapp.state==MEETING_APPLICATION_STATE_PENDING||vmapp.state==MEETING_APPLICATION_STATE_REFUSED}">
						    <td width="10%"  align="right"><comm:message key='meeitng.suggestTime' />:&nbsp;&nbsp;</td>
							<td width="25%"  align="left"><fmt:formatDate value="${vmapp.suggestDate}" pattern="yyyy-MM-dd HH:mm"/></td>
						</c:if>
						<c:if test="${vmapp.state==MEETING_APPLICATION_STATE_PASS}">
							<td width="10%"  align="right"><comm:message key='view.video.meetingroom' />:&nbsp;&nbsp;</td>
							<td width="25%" align="left">${meeting.meetingRoomId.name}</td>
						</c:if>	
						<td width="10%" align="right"><comm:message key='unified.lecture.people.name' />:&nbsp;&nbsp;</td>
						<td width="25%" align="left">${ vmapp.userName.name}</td>		
													
					</tr>
					<c:if test="${vmapp.state==MEETING_APPLICATION_STATE_PASS}">
						<tr>
							<td width="10%"  align="right"><comm:message key='unified.starttime' />:&nbsp;&nbsp;</td>
							<td width="25%" align="left"><fmt:formatDate value="${meeting.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td width="10%"  align="right"><comm:message key='unified.endtime' />:&nbsp;&nbsp;</td>
							<td width="25%" align="left"><fmt:formatDate value="${meeting.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
					</c:if>
					<tr>
						<td style="display:none;" width="10%" align="right"><comm:message key='lecture.m.lecturelevel' />:&nbsp;&nbsp;</td>
						<td style="display:none;" width="25%" align="left">${vmapp.level.name}</td>
						<td width="10%" align="right"><comm:message key='unified.lecture.people.dept' />:&nbsp;&nbsp;</td>
						<td align="left">${vmapp.deptName.name}</td>
					</tr>
					<c:if test="${vmapp.state==3}">
						<tr>
							<td width="10%" align="right"><comm:message key='meeting.m.refuseReson' />:&nbsp;&nbsp;</td>
							<td colspan="3" align="left">
	                             <textarea  onfocus="this.blur()" name="refuseReason" style="border: 1px solid #7F9DB9;width:93%;" id="refuseReason" rows="5" class="text-long" >${vmapp.refuseReason}</textarea>
							</td>
						</tr>
					</c:if>
					<c:if test="${vmapp.accessories!=null&&vmapp.accessories.size() > 0}">
			           <tr>
			            <td width="10%" align="right"><comm:message key="unified.and.expert_accessories"/>:&nbsp;&nbsp;</td>
			            <td colspan="3">
			            <ul id="filelist">
			            <c:forEach var="myfilelist" items="${vmapp.accessories}">
			              <li id="lyc${myfilelist.id}" style="line-height:15px;width:230px;margin-left:0px;vertical-align:top;overflow:hidden;white-space:nowrap;">
	           				 <div style="width:150px;">
							    <div style="float:left;width:78px;" title="${myfilelist.type.name}">
							          ${fn:substring(myfilelist.type.name, 0, 6)}<span>:</span>
							    </div>
							    <c:if test="${fn:length(myfilelist.name) > 10}">
								    <div style="float:left;width:20px;">
								       <a style="color: blue;" href="#" onclick="Exp('${myfilelist.path}','${myfilelist.name}');"  title="${myfilelist.name}">${fn:substring(myfilelist.name, 0, 10)}...</a>
								    </div>
							    </c:if>
							    <c:if test="${fn:length(myfilelist.name) <= 10}">
								    <div style="float:left;width:50px;">
								       <a style="color:blue;" href="#" onclick="Exp('${myfilelist.path}','${myfilelist.name}');"  title="${myfilelist.name}">${myfilelist.name}</a>
								    </div>
							    </c:if>
							 </div>
                             <%--   <a style="COLOR:blue;" href="<%=request.getContextPath()%>/download?filepath=${myfilelist.path}&filename=${myfilelist.name}" >${myfilelist.name}</a> --%>
			              </li>
			            </c:forEach> 
			            </ul>
			            </td>
			          </tr>  
			        </c:if>   
					<tr>
						<td width="10%"  align="right">
							<c:if test="${vmapp.videoRequester.userType.value == 4||vmapp.videoRequester.userType.value == 2}">
					            <comm:message key='unified.and.expert.meeting.requester' />:&nbsp;&nbsp;
				            </c:if>
				            <c:if test="${vmapp.videoRequester.userType.value == 5}">
					            <comm:message key='unified.and.expert_requester'/>:&nbsp;&nbsp;
				            </c:if>
						</td>
						<td colspan="3" align="left">
								<table width="93.5%" cellspacing="0" class="_table" cellpadding="0" border="1">
						             <tr>
						                 <c:if test="${fn:substring(vmapp.videoRequester.deptId.parentDeptCode, 0, 6) == '001001'||vmapp.videoRequester.deptId.deptcode == '001001'}">
							                <td align="right" width="15%"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
							                <td><comm:message key='meeting.type.inner'/></td>
							                <td align="right" width="15%"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
							                <td>${vmapp.videoRequester.name}&nbsp;</td>
							            </c:if>
							            <c:if test="${fn:substring(vmapp.videoRequester.deptId.parentDeptCode, 0, 6) == '001002'||vmapp.videoRequester.deptId.deptcode == '001002'}"> 
							            	<td align="right"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
							                <td><comm:message key='meeting.type.outter'/></td>
							                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
							                <td>${vmapp.videoRequester.name}&nbsp;</td>
							            </c:if>
							            <c:if test="${fn:substring(vmapp.videoRequester.deptId.parentDeptCode, 0, 6) == '001003'||vmapp.videoRequester.deptId.deptcode == '001003'}">
							            	<td align="right"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
						                	<c:if test="${fn:length(vmapp.videoRequester.name) <= 14}">
							                  <td>${vmapp.videoRequester.name}&nbsp;</td>
							                </c:if>
							                <c:if test="${fn:length(vmapp.videoRequester.name) > 14}">
							                  <td title="${vmapp.videoRequester.name}">${fn:substring(vmapp.videoRequester.name, 0, 12)}...&nbsp;</td>
							                </c:if>
							                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
							                <td>${vmapp.videoRequester.username}&nbsp;</td>
							            </c:if>
						             </tr>
						             <tr>
						                <td align="right"><comm:message key='unified.and.expert_mobile'/>:&nbsp;&nbsp;</td>
						                <td>${vmapp.videoRequester.mobile}&nbsp;</td>
						                <td align="right"><comm:message key='admin.user_tel'/>:&nbsp;&nbsp;</td>
						                <td>${vmapp.videoRequester.telephone}&nbsp;</td>
						             </tr>
						             <tr>
						                <td align="right"><comm:message key='group.department'/>:&nbsp;&nbsp;</td>
						                <td>${vmapp.videoRequester.deptId.name}&nbsp;</td>
						                <td align="right"><comm:message key='unified.and.expert_email'/>:&nbsp;&nbsp;</td>
					                	<c:if test="${fn:length(vmapp.videoRequester.mail) <= 30}">
						                  <td>${vmapp.videoRequester.mail}&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
						                </c:if>
						                <c:if test="${fn:length(vmapp.videoRequester.mail) > 30}">
						                  <td title="${vmapp.videoRequester.mail}">${fn:substring(vmapp.videoRequester.mail, 0, 30)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
						                </c:if>
						             </tr>
						        </table>	
						</td>
					</tr>
				</table>	        
	        </div>
	        <c:if test="${meeting.state == 3 && satisfaction != null}">
	        	<div class="subtitle" style="height:30px;line-height:30px;margin-left:25px;">
					<div class="titleStyle" style="margin:0px;">
						   <strong><comm:message key='unified.and.expert.video.evaluation.content' /></strong>
					</div>	    
		        </div>
		        <div class="subinfor" id="patientinfor3">
			       <c:if test="${userinfor.userType.value == 4}">
			         <table border="0" align="center" width="100%">
			             <tr>
			                <td width="12%" align="right"><comm:message key='metting.consultationStatistics.purpose1'/>:</td>
			                <c:if test="${fn:length(meeting.content) > 40}">
			                   <td title="${meeting.title}">&nbsp;&nbsp;${fn:substring(meeting.title,0,40)}...</td>
			                </c:if>
			                <c:if test="${fn:length(meeting.title) <= 40}">
			                   <td>&nbsp;&nbsp;${meeting.content}</td>
			                </c:if>
			             </tr>
			             <tr>
			                <td align="right"><comm:message key='unified.and.expert.attend.suggestion'/>:</td>
							<c:if test="${fn:length(satisfaction.suggestion) > 40}">
			                   <td title="${satisfaction.suggestion}">&nbsp;&nbsp;${fn:substring(satisfaction.suggestion,0,40)}...</td>
							</c:if>
							<c:if test="${fn:length(satisfaction.suggestion) <= 40}">
			                   <td>&nbsp;&nbsp;${satisfaction.suggestion}</td>
							</c:if>
			             </tr>
			             <tr>
			                <td align="right"><comm:message key='local.number'/>:</td>
			                <td>&nbsp;&nbsp;${satisfaction.localNumber}</td>
			             </tr>
			             <tr height="20">&nbsp;</tr>
			         </table>
			       </c:if>
			       
			       <c:if test="${userinfor.userType.value == 5}">
			         <table width="100%">
			            <tr>
			               <td width="10%" align="right"><comm:message key='metting.consultationStatistics.purpose1'/>:</td>
			               <c:if test="${fn:length(meeting.content) <= 40}">
			                   <td>&nbsp;&nbsp;${meeting.content}</td>
			               </c:if>
			               <c:if test="${fn:length(meeting.content) > 40}">
			                   <td title="${meeting.title}">&nbsp;&nbsp;${fn:substring(meeting.title,0,40)}...</td>
			               </c:if>
			            </tr>
			            <tr>
			               <td width="10%" align="right"><comm:message key='local.number'/>:</td>
			               <td>&nbsp;&nbsp;${satisfaction.localNumber}</td>
			            </tr>
		                <c:forEach var="evaluation" items="${evaluationList}" varStatus="status">
		                    <tr>
		                       <td width="40%" align="right">${evaluation.name}:</td>
		                       <td align="left">
								  <div id="set_${status.index}" class="exemple">
									 <c:if test="${status.index == 0}">
									 	&nbsp;&nbsp;${satisfaction.evaluationScore1}<comm:message key='meeting.and.video.star'/>
			 					     </c:if>
									 <c:if test="${status.index == 1}">
									 	&nbsp;&nbsp;${satisfaction.evaluationScore2}<comm:message key='meeting.and.video.star'/>
									 </c:if>
									 <c:if test="${status.index == 2}">
									 	&nbsp;&nbsp;${satisfaction.evaluationScore3}<comm:message key='meeting.and.video.star'/>
									 </c:if>                 
								  </div>
		                       </td>
		                    </tr>
		                </c:forEach>
			            <tr>
			                <td width="20%" align="right"><comm:message key='unified.suggestion'/>:</td>		               
							<c:if test="${fn:length(satisfaction.suggestion) <= 32}">
			                    <td>&nbsp;&nbsp;${satisfaction.suggestion}</td>
							</c:if>
							<c:if test="${fn:length(satisfaction.suggestion) > 32}">
			                    <td title="${satisfaction.suggestion}">&nbsp;&nbsp;${fn:substring(satisfaction.suggestion,0,32)}...</td>
							</c:if>
			            </tr>
			         </table>			       
			       </c:if>
		        </div>
	        </c:if>
			<div id="titleStyle">
			     <span>&nbsp;</span>
			</div>
	        
	      </div>
		</div>
</body>

</html>