<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
._table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px;margin-left:0px; }
._table caption {font-size:18px; font-weight:bolder;}
._table tr td input{width:151px;}
._table td{height:25px; line-height:25px;border:1px solid #D2D2D2;}
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
			    <table width="100%" class="meetingapp" border="0" align="center" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%" align="right"><strong><comm:message key='common.patientInfo.socialSecurityNO' /></strong>:&nbsp;&nbsp;</td>
							<td>
								${application.patientInfo.socialSecurityNO}
							</td>
						</tr>
						<tr>
						    <td width="20%" align="right"><strong><comm:message key="unified.and.expert_name"/></strong>:&nbsp;&nbsp;</td>
							<td height="30">
							     <table width="100%" border="0">
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
	    
	     
	    <div class="subinfor" id="patientinfor2">
			    <table width="100%" class="meetingapp" border="0" align="center" cellspacing="0" cellpadding="0">
				            <c:if test="${3 == application.state}">
						        <tr style="height:5px;"></tr>
					            <tr>
					              <td width="20%" align="right"><strong><comm:message key="meeting.m.refuseReson"/></strong>:&nbsp;&nbsp;</td>
						          <td><textarea rows="5" class="text-long" maxlength="255" onfocus="this.blur()">${application.refuseReason}</textarea></td>							            
					            </tr>
					            <tr>
						            <td width="20%" align="right"><strong><comm:message key="unified.datetime"/></strong>:&nbsp;&nbsp;</td>
						            <td>
						                 <table border="0" width="100%" cellspacing="0" cellpadding="0">
						                        <tr>
						                            <td width="29%">&nbsp;<fmt:formatDate value="${application.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
									                <td width="32%" align="right"><strong><comm:message key="unified.and.expert_meeting.m.state"/></strong>:&nbsp;</td>
									                <td width="34%">
										                <c:if test="${1==application.state}">
											               <comm:message key="meeting.m.statewait"/>
											            </c:if>
											            <c:if test="${2==application.state}">
											               <comm:message key="meeting.m.statepass"/>
											            </c:if>
											            <c:if test="${3==application.state}">
											               <comm:message key="meeting.m.staterefuse"/>
											            </c:if>	
									                </td>
									                					           												    
						                        </tr>
						                 </table>
						            </td>
					              </tr>
				              </c:if>
				              <c:if test="${1 == application.state}">
						        <tr style="height:5px;"></tr>
					            <tr>
						            <td width="20%" align="right"><strong><comm:message key="unified.datetime"/></strong>:&nbsp;&nbsp;</td>
						            <td>
						                 <table border="0" width="100%" cellspacing="0" cellpadding="0">
						                        <tr>
						                            <td width="29%">&nbsp;<fmt:formatDate value="${application.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
									                <td width="32%" align="right"><strong><comm:message key="unified.and.expert_meeting.m.state"/></strong>:&nbsp;</td>
									                <td width="34%" align="left">
										                <c:if test="${1==application.state}">
											               <comm:message key="meeting.m.statewait"/>
											            </c:if>
											            <c:if test="${2==application.state}">
											               <comm:message key="meeting.m.statepass"/>
											            </c:if>
											            <c:if test="${3==application.state}">
											               <comm:message key="meeting.m.staterefuse"/>
											            </c:if>	
									                </td>
									                					           												    
						                        </tr>
						                 </table>
						            </td>
					              </tr>
				              </c:if>   
				              <c:if test="${2 == application.state}">					           
					           <tr>
						            <td width="20%" align="right"><strong><comm:message key="meeting.m.starttime"/></strong>:&nbsp;&nbsp;</td>
						            <td>
						                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
						                       <tr>
						                           <td width="30%">						                                
										                 <c:if test="${ application.startTime != null}">
										                     <fmt:formatDate value="${application.startTime}" pattern="yyyy-MM-dd HH:mm" />
										                 </c:if>
						                           </td>
						                           <td width="35%" align="right"><strong><comm:message key="meeting.m.endtime"/></strong>:&nbsp;</td>
						                           <td>
											            <c:if test="${ application.endTime != null}">
											            	<fmt:formatDate value="${application.endTime}" pattern="yyyy-MM-dd HH:mm" />
											            </c:if>		                           
						                           </td>
						                       </tr>
						                 </table>
						            </td>
					           </tr>
					           <tr>
						            <td width="15%" align="right"><strong><comm:message key="unified.and.expert_meeting.m.state"/></strong>:&nbsp;&nbsp;</td>
						            <td>
						                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
						                       <tr>
						                           <td width="30%">						                                
										                 <c:if test="${1==application.state}">
											               <comm:message key="meeting.m.statewait"/>
											            </c:if>
											            <c:if test="${2==application.state}">
											               <comm:message key="meeting.m.statepass"/>
											            </c:if>
											            <c:if test="${3==application.state}">
											               <comm:message key="meeting.m.staterefuse"/>
											            </c:if>
						                           </td>
						                       </tr>
						                 </table>
						            </td>
					           </tr> 
				            </c:if>					
						            		                
			    </table>
			    <table width="100%"  border="0" align="center" cellspacing="0" cellpadding="0">
			    
			         <tr>
			            <td width="20%"  align="right">
					            <c:if test="${application.requester.userType.value==4||application.requester.userType.value==2}">
						            <strong><comm:message key='unified.and.expert.meeting.requester' /></strong>:&nbsp;&nbsp;
					            </c:if>
					            <c:if test="${application.requester.userType.value==5}">
						            <strong><comm:message key='unified.and.expert_requester'/></strong>:&nbsp;&nbsp;
					            </c:if>
						</td>
						<td>
						    <table background-color="#ddeefa" align="left">
							<tr>
								<td align="center" height="80">
								     <table width="567" align="center" class="_table" style="margin-left:3px; border-bottom:0px;border-right:0px;" border="1">
								            <tr>
								                <c:if test="${fn:substring(application.requester.deptId.parentDeptCode, 0, 6) == '001001'||application.requester.deptId.deptcode == '001001'}">
									                <td width="94" align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;</td>
									                <td width="188" align="left"><comm:message key='meeting.type.inner'/></td>
									                <td width="94" align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
									                <c:if test="${fn:length(application.requester.name) <= 10}">
									                  <td align="left">${application.requester.name}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(application.requester.name) > 10}">
									                  <td align="left" title="${application.requester.name}">${fn:substring(application.requester.name, 0, 10)}...&nbsp;</td>
									                </c:if>
									            </c:if>
									            <c:if test="${fn:substring(application.requester.deptId.parentDeptCode, 0, 6) == '001002'||application.requester.deptId.deptcode == '001002'}"> 
									            	<td align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;</td>
									                <td align="left"><comm:message key='meeting.type.outter'/></td>
									                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
													<c:if test="${fn:length(application.requester.name) <= 10}">
									                  <td align="left">${application.requester.name}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(application.requester.name) > 10}">
									                  <td align="left" title="${application.requester.name}">${fn:substring(application.requester.name, 0, 10)}...&nbsp;</td>
									                </c:if>					                
									            </c:if>
									            <c:if test="${fn:substring(application.requester.deptId.parentDeptCode, 0, 6) == '001003'||application.requester.deptId.deptcode == '001003'}">
									            	<td align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;</td>
										            <c:if test="${fn:length(application.requester.name) <= 12}">
									                  <td align="left">${application.requester.name}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(application.requester.name) > 12}">
									                  <td align="left" title="${application.requester.name}">${fn:substring(application.requester.name, 0, 12)}...&nbsp;</td>
									                </c:if>	
									                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
													<c:if test="${fn:length(application.requester.username) <= 10}">
									                  <td align="left">${application.requester.username}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(application.requester.username) > 10}">
									                  <td align="left" title="${application.requester.username}">${fn:substring(application.requester.username, 0, 10)}...&nbsp;</td>
									                </c:if>
									            </c:if>
								            </tr>
								            <tr>
								                <td align="right" style="height:25px;"><comm:message key='unified.and.expert_mobile'/>:&nbsp;</td>
								                <td align="left">${application.requester.mobile}</td>
								                <td align="right"><comm:message key='admin.user_tel'/>:&nbsp;</td>
								                <td align="left">${application.requester.telephone}</td>
								            </tr>
								            <tr>
								                <td width="94" align="right" style="height:25px;"><comm:message key='group.department'/>:&nbsp;</td>
								                <td width="188" align="left">${application.requester.deptId.name}</td>
								                <td width="94" align="right"><comm:message key='unified.and.expert_email'/>:&nbsp;</td>
								                <c:if test="${fn:length(application.requester.mail) <= 23}">
								                  <td width="188" align="left">${application.requester.mail}<input type="hidden" name="applicantid" id="applicantid"/></td>
								                </c:if>
								                <c:if test="${fn:length(application.requester.mail) > 23}">
								                  <td width="188" align="left" title="${application.requester.mail}">${fn:substring(application.requester.mail, 0, 23)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
								                </c:if>
								            </tr>					            
								     </table>
								  </td>
							 </tr>
						  </table>
					    </td>
					    </tr>
					    <tr>
						    <td width="20%"  align="right">
						           <strong><comm:message key='unified.and.expert_author'/></strong>:&nbsp;&nbsp;
							</td>					  
						  <td>							
						      <table background-color="#ddeefa" align="left">
								<tr>
								    <td height="80" align="center">
								        <table width="567" class="_table" style="margin-left:3px;border-bottom:0px;border-right:0px;" align="center" border="1">
							                <tr>							                							                	
									             <td align="right" width="94"><comm:message key='group.name'/>:&nbsp;</td>
									             <c:if test="${fn:length(application.authorInfo.groupname) <= 10}">
								                   <td align="left">${application.authorInfo.groupname}&nbsp;</td>
								                 </c:if>
								                 <c:if test="${fn:length(application.authorInfo.groupname) > 10}">
								                   <td align="left" title="${application.authorInfo.groupname}">${fn:substring(application.authorInfo.groupname, 0, 10)}...&nbsp;</td>
								                 </c:if>								                 
								                 <td align="right" width="94"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
									             <c:if test="${fn:length(application.authorInfo.name) <= 10}">
								                   <td align="left">${application.authorInfo.name}&nbsp;</td>
								                 </c:if>
								                 <c:if test="${fn:length(application.authorInfo.name) > 10}">
								                   <td align="left" title="${application.authorInfo.name}">${fn:substring(application.authorInfo.name, 0, 10)}...&nbsp;</td>
								                 </c:if>								                 							                
							                </tr>
							                <tr>
							                    <td align="right"><comm:message key='unified.and.expert_mobile'/>:&nbsp;</td>
							                    <td align="left">${application.authorInfo.mobile}</td>
							                    <td align="right"><comm:message key='admin.user_tel'/>:&nbsp;</td>
							                    <td align="left">${application.authorInfo.telephone}</td>
							                </tr>
							                <tr>
							                    <td align="right" width="94"><comm:message key='group.department'/>:&nbsp;</td>
							                    <td align="left" width="188">${application.authorInfo.department}</td>
							                    <td align="right" width="94"><comm:message key='unified.and.expert_email'/>:&nbsp;</td>
							                    <c:if test="${fn:length(application.authorInfo.email) <= 23}">
								                  <td align="left" width="188">${application.authorInfo.email}</td>
								                </c:if>
								                <c:if test="${fn:length(application.authorInfo.email) > 23}">
								                  <td width="188" align="left" title="${application.authorInfo.email}">${fn:substring(application.authorInfo.email, 0, 23)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
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
		<div class="titleStyle">
			<span>&nbsp;</span>
		</div>	    	    
	</div>
</body>
</html>