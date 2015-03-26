<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><comm:message key="meeting.m.detailedpage"/></title>
<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<style type="text/css">
.titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:20px;line-height:20px;margin-top:10px;margin-bottom:5px;margin-left:20px;margin-right:25px;border:0px dashed #3c3645; }
#viewappinfor{border-top:0px solid #7F9DB9;border-bottom:0px solid #7F9DB9;}
.meetingapp tr{height:25px;}
.meetingapp tr td{height:15px;border-top:0px solid #7F9DB9;border-bottom:0px solid #7F9DB9;}
.meetingapp tr td select{border:1px solid #7F9DB9;margin-left:5px;background-color:#F8F8FF;width:95%;}
.meetingapp tr td textarea{border:1px solid #7F9DB9;margin-left:5px;background-color:#F8F8FF;width:94.5%;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:30px;margin-right:30px;}
a{text-decoration:none;}
a:hover{text-decoration:underline;}
</style>

<script type="text/javascript">
function Exp(filepath,title){  
	title = encodeURI(encodeURI(title));
	window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
}
</script>
</head>
<body style="overflow-y:auto;"> 
	<div id="viewappinfor" style="position:relative;background-color:#dee;">
		<div class="subtitle">
		   <div class="titleStyle" id="titleid">
				  <comm:message key='unified.basic_patient_information' />
		   </div>
	    </div>
	    <div class="subinfor" id="patientinfor1">
			<table class="meetingapp" style="width: 100%;border: 0">
				<tr>
				    <td width="20%" align="right"><strong><comm:message key="unified.and.expert_name"/></strong>:&nbsp;&nbsp;</td>
					<td height="30">
				     <table style="width: 100%;border: 0">
			             <tr>
			                  <td width="20%">${application.patientInfo.name}</td>
			                  <td width="20%" align="right"><strong><comm:message key="unified.and.expert_user_sex"/></strong>:&nbsp;&nbsp;</td>
			                  <td width="15%">
					            	<c:choose>
					            		<c:when test="${0 == application.patientInfo.sex}">
					            		  <comm:message key="comm.male"/>
					            		</c:when>
					            		<c:otherwise>
					            			<comm:message key="comm.female"/>
					            		</c:otherwise>
					            	</c:choose>					                  
			                  </td>
			                  <td width="20%" align="right"><strong><comm:message key="unified.and.expert_patient.age"/></strong>:&nbsp;&nbsp;</td>
			                  <td width="20%">${application.patientInfo.age}</td>
			             </tr>
				     </table>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right"><strong><comm:message key="unified.address"/></strong>:&nbsp;&nbsp;</td>
					<td>${application.patientInfo.address}</td>
				</tr>
			</table>
	    </div>
	    <c:if test="${application.accessories.size() > 0 || fn:length(accsImage) > 0 }">
		    <div class="subtitle">
				<div class="titleStyle">
					<strong><comm:message key="unified.accsAndImage"/></strong>
				</div>
		    </div>
	    	<div class="subinfor" id="accessories">
	            <table class="meetingapp">
	              <tr style="height:5px;"></tr>
	              <tr>
		            <td width="15%" align="right"><strong><comm:message key="unified.and.expert_accessories"/></strong>:&nbsp;&nbsp;</td>
		            <c:if test="${fn:length(application.accessories) > 0}">
			            <td style="width:30%;vertical-align:top;">
				            <ul>
					            <c:forEach var="myfilelist" items="${application.accessories}">		                 
					                 <li style="width:230px;vertical-align:top;overflow:hidden;white-space:nowrap;">		            		
				           				 <div style="width:150px;">
										    <div style="float:left;width:78px;" title="${myfilelist.type.name}">
										          ${fn:substring(myfilelist.type.name, 0, 6)}<span>:</span>
										    </div>
										    <!-- <div style="float:left;">:</div> -->
										    <c:if test="${fn:length(myfilelist.name) > 10}">
											    <div style="float:left;width:20px;">
											       <a style="color: blue;" href="#" onclick="Exp('${myfilelist.path}','${myfilelist.name}');"  title="${myfilelist.name}">${fn:substring(myfilelist.name, 0, 10)}...</a>
											    </div>
										    </c:if>
										    <c:if test="${fn:length(myfilelist.name) <= 10}">
											    <div style="float:left;width:50px;">
											       <a style="color: blue;" href="#" onclick="Exp('${myfilelist.path}','${myfilelist.name}');"  title="${myfilelist.name}">${myfilelist.name}</a>
											    </div>
										    </c:if>
										 </div>	
					            	</li>
					            </c:forEach>
				            </ul>
			            </td>
		            </c:if>
		            <c:if test="${fn:length(application.accessories) > 0 || accsImage.size()!=0}">
		            <td  style="width:30%;vertical-align:top;">
		             <ul>
		              <a style="color: blue" href="${HAINA_URL}/HinacomWeb/DisplayImage.aspx?OEM=162&UserID=${USER_LOGIN_SESSION.userId }&UserName=${sessionScope.encodeName}&ConferenceID=${application.id}" target="_blank"><comm:message key="unified.watchImage"/></a>
		              <c:forEach var="myfilelist" items="${accsImage}">
		                  <li style="width:230px;vertical-align:top;overflow:hidden;white-space:nowrap;">		                  
		                  <div style="width:150px;">
						    <div style="float:left;width:48px;">
						          ${myfilelist.type.name}
						    </div>
						    <div style="float:left;">:</div>
						    <c:if test="${fn:length(myfilelist.name) > 6}">						    
							    <div style="float:left;width:30px;" title="<comm:message key='unified.patient' />${myfilelist.name}<comm:message key='unifeid.image' />">
							       <comm:message key='unified.patient' />${fn:substring(myfilelist.name, 0, 6)}<comm:message key='unifeid.image' />...
							    </div>
						    </c:if>
						    <c:if test="${fn:length(myfilelist.name) <= 6}">
							    <div style="float:left;width:30px;" title="<comm:message key='unified.patient' />${myfilelist.name}<comm:message key='unifeid.image' />">
							       <comm:message key='unified.patient' />${myfilelist.name}<comm:message key='unifeid.image' />
							    </div>
						    </c:if>
						  </div>      	  
		            	  </li>
		              </c:forEach>		              
		            </ul>
		            </td>
		            </c:if>
		            <c:if test="${fn:length(application.accessories) == 0}">
		            	<td style="width:30%;vertical-align:top;"></td>
		            </c:if>
		          </tr>
		          <tr style="height:5px;"></tr>
	            </table>
	    	</div>
	    </c:if>
	    <div class="subtitle">
			<div class="titleStyle">
				   <strong><comm:message key='unified.medical_record' /></strong>
			</div>
	    </div>
	    <div class="subinfor" id="patientinfor2">
		    <table width="100%" class="meetingapp" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				     <td width="15%" align="right"><strong><comm:message key="unified.and.expert_complaint"/></strong>:&nbsp;&nbsp;</td>
				     <td><p style="width:560px;word-wrap:break-word;">&nbsp;${application.chiefComplaint}</p></td>
				</tr>
				<tr style="height:5px;"></tr>
	            <tr>
				    <td width="15%" align="right"><strong><comm:message key="unified.medical_record"/></strong>:&nbsp;&nbsp;</td>
				    <td>
				         <textarea rows="5" class="text-long" onfocus="this.blur()">${application.medicalRecord}</textarea>
				    </td>
				</tr>
				<tr>
				     <td width="15%" align="right"><strong><comm:message key="unified.and.expert_purpose"/></strong>:&nbsp;&nbsp;</td>
				     <td><p style="width:560px;word-wrap:break-word;">&nbsp;${application.purpose}</p></td>
				</tr>
				<c:if test="${application.problem != null && application.problem != ''}">
					<tr>
					     <td width="15%" align="right"><strong><comm:message key="unified.issuesToBe_discussed"/></strong>:&nbsp;&nbsp;</td>
					     <td>&nbsp;<input type="text" style="border:1px solid #7F9DB9;width:94.5%;height:20px;line-height:20px;" class="meetingsu" size="55" maxlength="200" value="${application.problem}" onfocus="this.blur()"/></td>
					</tr>
				</c:if>
				<tr style="height:5px;"></tr>
				<tr>
				    <td width="15%" align="right"><strong><comm:message key='unified.dept'/></strong>:&nbsp;&nbsp;</td>
				    <td>
			            <select id="deptmentid" multiple="multiple" class="multiselect">
			            	<option>${application.mainDept.name}</option> 
			            	<c:forEach var="c_deptment" items="${application.depts}">
		                		<option>${c_deptment.department.name}</option>
		                	</c:forEach>     
			           </select>
				    </td>
				</tr>
				<tr style="height:5px;"></tr>
					<tr>
					    <td width="20%" align="right"><strong><comm:message key='meeting.m.icd10'/></strong>:&nbsp;&nbsp;</td>
					    <td>
						    <select id="icd10Dic"  multiple="multiple" class="multiselect" >
		                       	<c:forEach var="icd10Dic" items="${application.applicationICD10s}">
									<option>${icd10Dic.icd.diagnosis}</option>
								</c:forEach>   
		                   </select>
					    </td>
					</tr>
			</table>
	    </div>
	    <!-- 会诊总结 -->
	    <c:if test="${fn:length(summarys) > 0}">
		    <div class="subtitle">
				<div class="titleStyle">
					   <strong><comm:message key='manager.teleconsultation.consultationSummary'/></strong>
				</div>
		    </div>
		    <div class="subinfor">
		    	<table width="100%" class="meetingapp" border="0" align="center" cellspacing="0" cellpadding="0">
		    		<c:forEach var="summary" items="${summarys}">
			    		<tr>
						    <td width="20%" align="right"><strong>${summary.user.name}</strong>:&nbsp;&nbsp;</td>
						    <td>
							   <textarea rows="5" class="text-long" onfocus="this.blur()">${summary.summary}</textarea>
						    </td>
						</tr>
					</c:forEach>
		    	</table>
		    </div>
	    </c:if>

	    <!-- VCR连接 -->
	    <%-- <div class="subtitle">
			<div class="titleStyle">
				<strong><comm:message key='manager.teleconsultation.video'/></strong>
			</div>
	    </div> 
	    <div class="subinfor">
	    	<a href="#"><comm:message key='manager.teleconsultation.searchVideo'/></a>
	    </div> --%>
		<div class="titleStyle">
			<span>&nbsp;</span>
		</div>	    	    
	</div>
</body>
</html>