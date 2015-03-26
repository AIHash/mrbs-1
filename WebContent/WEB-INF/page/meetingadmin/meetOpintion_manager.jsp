<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html >
  <head>
    <base href="<%=basePath%>"/>
    <title><comm:message key='unified.meetingopintion'/></title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
    <comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
    <script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/style/jrating/documentation.css" rel="StyleSheet" type="text/css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryui/jqueryTab/jquery.idTabs.min.js"></script>
	<link href="<%=request.getContextPath()%>/resources/style/jrating/jquery.rating.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.rating.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.MetaData.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
	/*主容器*/ 
	.usual { 
	    /* background:snow; */
	    background:#fff;
	    color:#111; 
	    padding:0px 0px; 
	    width:97%; 
	    border:0px solid red; 
	    margin:0px auto; 
	    vertial-align:middle;
	} 
	
	/*选项卡*/ 
	.usual ul 
	{ 
	margin:0; 
	padding:0; 
	padding-left:0em; 
	overflow:auto;/*火狐等清理浮动*/ 
	_display:inline-block;/*ie6清理浮动*/ 
	} 
	.usual li {list-style:none;float:left;} 
	.usual ul a { 
	    display:block; 
	    padding:6px 10px; 
	    text-decoration:none; 
	    font:12px /* Arial */; 
	    color:#000; 
	    background:#D2D2D2; 
	    outline:none; 
	} 
	.usual ul a:hover { 
	    color:#FFF; 
	    background:#D2D2D2; 
	    } 
	.usual ul a.selected { 
	    color:#000; 
	    background:snow; 
	    cursor:default; 
	    } 
	     
	    /*tab页*/ 
	.usual div { 
	    padding:1px 1px 0px 1px; 
	    /*background:snow;  
	    font:10pt Arial;  */
	}
		#titleStyle{color:#3c3645;font-size:24px;font-weight: bold;height:20px;line-height:20px;margin-top:10px;margin-bottom:5px;margin-left:20px;margin-right:25px;border:0px dashed #3c3645; }
		#titleStyle2{color:#3c3645;font-size:24px;font-weight: bold;height:20px;line-height:20px;margin-top:10px;margin-bottom:5px;margin-left:20px;margin-right:25px;border:0px dashed #3c3645; }
		#titleStyle3{color:#3c3645;font-size:24px;font-weight: bold;height:20px;line-height:20px;margin-top:10px;margin-bottom:5px;margin-left:20px;margin-right:25px;border:0px dashed #3c3645; }
        #viewappinfor{border-top:0px solid #7F9DB9;border-bottom:0px solid #7F9DB9;}
		.exemple{margin-left:0px;}
		.auto-submit-star{margin:2px;}
		.main-submit-star{margin:2px;}
        /* .subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:40px;margin-right:40px;} */
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
				 jAlert("<comm:message key='unified.need.meetingTime'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
			 }
			 var realStartTime=document.getElementById("realStartTime").value;
			 if(strTrim(realStartTime) == "")
			 {
				 jAlert("<comm:message key='meeting.real.starttime.isnotnull'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
			 }
			 var realEndTime=document.getElementById("realEndTime").value;
			 if(strTrim(realEndTime) == "")
			 {
				 jAlert("<comm:message key='meeting.real.endtime.isnotnull'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
			 }

			 if (compareCheckDate(realStartTime,realEndTime) != '-1'){
				 jAlert("<comm:message key='js.need_input_larger' args='meeting.real.endtime,meeting.real.starttime'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
			 }

			jConfirm("<comm:message key='meeting.SubmitMeeting'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
				if(resultConfirm){
					 $.post('<%=request.getContextPath()%>/unified/addmanageropintion',
							 {evalvalue :$('#evalvalue').val(), meetingid : $('#meetingid').val(), meetingTime : $('#meetingTime').val(),realStartTime : $('#realStartTime').val(),realEndTime : $('#realEndTime').val()},
						function(text){
							if(text == 'success'){
								parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href;
						    	parent.$.fancybox.close();
							}else if(text.indexOf('<HTML>') != -1){
								window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							}
					 }, 'text');					
				}
			});

		 });
		});
		//获取div显示内容的高度，并给改div的内容以外的地方预留高度
		function getHeight() {      
			var bodyHeight = document.documentElement.clientHeight;
			var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight - 98;
			document.getElementById("patientinfor1").style.overflowY = 'auto';
		}
		function getHeight2() {      
			var bodyHeight = document.documentElement.clientHeight;
			var patientinfor1Height =document.getElementById("patientinfor2").style.height = bodyHeight - 98;
			document.getElementById("patientinfor2").style.overflowY = 'auto';
		}
		function getHeight3() {      
			var bodyHeight = document.documentElement.clientHeight;
			var patientinfor1Height =document.getElementById("patientinfor3").style.height = bodyHeight - 98;
			document.getElementById("patientinfor3").style.overflowY = 'auto';
		}
	    window.onload = function() { 	
	    	getHeight();       	
	    	getHeight2();       	
	    	getHeight3();       	
	    	};
		function setEndTime() {
			var realStartTime=document.getElementById("realStartTime").value;
			 if(!strTrim(realStartTime) == ""){
				 realStartTime=realStartTime.replace(/-/g, "/");
				 var realStartTimetemp = new Date(realStartTime);
				 var realEndDate = new Date(realStartTimetemp.valueOf() + 60*60*1000); 
				 document.getElementById("realEndTime").value=getDate(realEndDate,"YYYY-MM-DD HH:MM");
			 }
		}
	</script>
  </head>  
  <body id="windows" style="overflow-y:auto;">
  <div id="usual1" class="usual">    
    <ul id="tabHeight">    
        <li><a class="selected" href="#tab1"><comm:message key='meeting.admin.evaluation'/></a></li> 
        <li><a href="#tab2"><comm:message key='meeting.expert.evaluation'/></a></li>
        <li><a href="#tab3"><comm:message key='meeting.unified.evaluation'/></a></li>
    </ul>    
    <div id="tab1">
	  <form name="form1" action="<%=request.getContextPath()%>/unified/addmanageropintion" method="post">
		 <div id="viewappinfor" style="vertical-align:middle;text-align:center;">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
	           <div class="subinfor" id="patientinfor1">   
			    <table id="shenqing" class="submit_table" align="center" width="100%" border="1">
				    <tr>
				    	<td style="text-align:center;vertical-align:middle;" colspan="2">
				    	<strong>
				    		<c:if test="${meeting.meetingKind==APPLICATION_TYPE_TELECONSULTATION}"><comm:message key='manager.teleconsultation.consultationEvaluation'/></c:if>
				    		<c:if test="${meeting.meetingKind==APPLICATION_TYPE_VIDEOLECTURES}"><comm:message key='manager.videoLectures.lectureEvaluation'/></c:if>
				    	</strong>
				    </td>
				    </tr>
				    <tr>
					    <td style="vertical-align:middle;" align="right" width="50%"><strong><comm:message key='meeting.time'/></strong>:<span class="required">*</span></td>
					    <td align="left"><input type="text" style="height:20px;line-height:20px;width:50px" name="meetingTime" id="meetingTime" maxlength="50" value="${satisfactionManager.meetingTime}" onkeyup="value=value.replace(/[^\d\.]/g,'')"/><comm:message key='meetingTime.unit'/></td>
				    </tr>
					<tr>
					    <td style="vertical-align:middle;" align="right" width="50%"><strong><comm:message key='meeting.real.starttime'/></strong>:<span class="required">*</span></td>
					    <td align="left">
	                           <input type="text" name="realStartTime" id="realStartTime" class="Wdate" value="<fmt:formatDate value="${meeting.realStartTime}" pattern="yyyy-MM-dd HH:mm" />" onclick="var endTime=$dp.$('endTime');WdatePicker({readOnly:true,onpicked:setEndTime,dateFmt:'yyyy-MM-dd HH:mm'})" onfocus="this.blur()" size="25"/>					    
					    </td>					   
					</tr>
					<tr>
					    <td style="vertical-align:middle;" align="right" width="50%"><strong><comm:message key='meeting.real.endtime'/></strong>:<span class="required">*</span></td>
					    <td align="left">
					        <input type="text" name="realEndTime" id="realEndTime" class="Wdate" value="<fmt:formatDate value="${meeting.realEndTime}" pattern="yyyy-MM-dd HH:mm" />" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})" onfocus="this.blur()" size="25"/>	
					    </td>					   
					</tr>
				    <c:forEach var="evaluation" items="${evallist}" varStatus="status"> 
				    <tr>
				       <td style="vertical-align:middle;" align="right"><strong>${evaluation.name}</strong>:&nbsp;</td>
				       <td style="vertical-align:middle;" align="left">
					       <c:if test="${satisfactionManager.score == null}">
					       <input type="hidden" value="3" name="evalvalue" id="evalvalue"/>
					       </c:if>
					       <c:if test="${satisfactionManager.score != null}">
					        <input type="hidden" value="${satisfactionManager.score}" name="evalvalue" id="evalvalue"/>
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
			     <table width="80%" border="0" align="center">
			        <tr><td>&nbsp;</td></tr>
				    <tr>
					    <td align="center">
						    <input type="hidden" value="${meeting.id}" name="meetingid" id="meetingid"/>
						    <input class="button" type="button" value="<comm:message key='unified.sure'/>" id="mainSubmit"/>
					    </td>
				    </tr>
			        <tr height="240"><td>&nbsp;</td></tr>
			    </table>            
	           </div>
			<div id="titleStyle2">
				<span>&nbsp;</span>
			</div>
	         </div>   
		</form>    
    </div>    
    <div id="tab2">
		<div id="viewappinfor" style="overflow-y:auto;vertical-align:middle;text-align:center;">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
		    <div class="subinfor" id="patientinfor2">
		      <c:if test="${meeting.meetingKind == 1}">
		        <c:if test="${fn:length(expertSatisfactionList)  != 0}">
					    <table id="shenqing" class="submit_table" name="meetiOpintion" align="center" width="100%" border="1">
				            <tr>
				               <td align="center" width="25%"><strong><comm:message key='meeting.evaluation.human'/></strong></td>
				               <td align="center" width="25%" style="vertical-align:middle;"><strong><comm:message key='local.number'/></td>
				               <td align="center" width="25%" style="vertical-align:middle;"><strong><comm:message key='unified.and.expert_purpose'/></strong></td>
					           <td align="center" style="vertical-align:middle;"><strong><comm:message key='unified.and.expert.attend.suggestion'/></strong></td>				            
				            </tr>
							<c:forEach var="appSatisfaction" items="${expertSatisfactionList}" varStatus="status">
							   <c:if test="${appSatisfaction.localNumber != null}">
						            <tr>
				 		               <td align="left">${appSatisfaction.user.name}</td>
				 		               <td align="left">${appSatisfaction.localNumber}<comm:message key='localnumber.unit'/></td>
									   <c:if test="${fn:length(meeting.title) <= 10}">
				 		                   <td align="left">${meeting.title}</td>
									   </c:if>
									   <c:if test="${fn:length(meeting.title) > 10}">
				 		                   <td align="left" title="${meeting.title}">${fn:substring(meeting.title, 0, 10)}...</td>
									   </c:if>
									   <c:if test="${fn:length(appSatisfaction.suggestion) <= 10}">
				 		                   <td align="left">${appSatisfaction.suggestion}</td>
									   </c:if>
									   <c:if test="${fn:length(appSatisfaction.suggestion) > 10}">
				 		                   <td align="left" title="${appSatisfaction.suggestion}">${fn:substring(appSatisfaction.suggestion, 0, 10)}...</td>
									   </c:if>
						            </tr>				   
							   </c:if> 
					        </c:forEach>
				        </table>		        
		        </c:if>
		        <c:if test="${fn:length(expertSatisfactionList) == 0}">
		                               参会专家暂无评价！
		        </c:if>
		      </c:if>
		      <c:if test="${meeting.meetingKind == 2}">
		        <c:if test="${fn:length(expertSatisfactionList)  != 0}">
					    <table id="shenqing" class="submit_table" name="meetiOpintion" align="center" width="100%" border="1">
				            <tr>
				               <td align="center" width="25%"><strong><comm:message key='video.satisfaction.human'/></strong></td>
				               <td align="center" width="25%"><strong><comm:message key='local.number'/></strong></td>
				               <td align="center" width="25%"><strong><comm:message key='unified.lecture.content'/></strong></td>
				               <td align="center"><strong><comm:message key='unified.suggestion'/></strong></td>
				            </tr>
							<c:forEach var="appSatisfaction" items="${expertSatisfactionList}" varStatus="status">
							   <c:if test="${appSatisfaction.localNumber != null}">
							   	  <td align="left">${appSatisfaction.user.name}</td>
							   	  <td align="left">${appSatisfaction.localNumber}<comm:message key='localnumber.unit'/></td>		   
								  <c:if test="${fn:length(meeting.content) <= 10}">
			 		                 <td align="left">${meeting.content}</td>
								  </c:if>
								  <c:if test="${fn:length(meeting.content) > 10}">
			 		                 <td align="left" title="${meeting.content}">${fn:substring(meeting.content, 0, 10)}...</td>
								  </c:if>
								  <c:if test="${fn:length(appSatisfaction.suggestion) <= 10}">
			 		                 <td align="left">${appSatisfaction.suggestion}</td>
								  </c:if>
								  <c:if test="${fn:length(appSatisfaction.suggestion) > 10}">
			 		                 <td align="left"title="${appSatisfaction.suggestion}">${fn:substring(appSatisfaction.suggestion, 0, 10)}...</td>
								  </c:if>								 
							   </c:if> 
					        </c:forEach>
				        </table>			        
		        </c:if>
		        <c:if test="${fn:length(expertSatisfactionList) == 0}">
		                               参会专家暂无评价！
		        </c:if>		      
		      </c:if>

		    </div>
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>
		</div>           
</div> 
<div id="tab3">
  <div id="viewappinfor" style="overflow-y:auto;vertical-align:middle;text-align:center;">
	<div id="titleStyle3">
		<span>&nbsp;</span>
	</div>		
    <div class="subinfor" id="patientinfor3">
      <c:if test="${meeting.meetingKind == 1}">
	   <c:if test="${fn:length(unifiedSatisfactionList) != 0}">
			   <table id="shenqing" class="submit_table" name="meetiOpintion" align="center" width="100%" border="1">
		              <tr>
		                 <td width="15%" align="center"><strong><comm:message key='meeting.evaluation.human'/></strong></td>
		                 <td width="15%" align="center"><strong><comm:message key='local.number'/></strong></td>
		                 <td width="10%" align="center"><strong><comm:message key='unified.purpose'/></strong></td>
				         <c:forEach var="evaluation" items="${applicationEvaluation}" varStatus="status">
				             <c:if test="${fn:length(evaluation.name) <= 6}">
		                         <td width="15%" align="center"><strong>${evaluation.name}</strong></td>
				             </c:if>
				             <c:if test="${fn:length(evaluation.name) > 6}">
		                         <td width="15%" align="center" title="${evaluation.name}"><strong>${fn:substring(evaluation.name, 0, 6)}</strong></td>
				             </c:if>				             
		                 </c:forEach>
		                 <td width="15%" align="center"><strong><comm:message key='unified.suggestion'/></strong></td>
		              </tr>
		   <c:forEach var="unifiedSatisfaction" items="${unifiedSatisfactionList}" varStatus="status">
			  <c:if test="${unifiedSatisfaction.localNumber != null}">
		              <tr>
						 <c:if test="${fn:length(unifiedSatisfaction.user.name) <= 8}">
						    <td>${unifiedSatisfaction.user.name}</td>
						 </c:if>
						 <c:if test="${fn:length(unifiedSatisfaction.user.name) > 8}">
						    <td title="${unifiedSatisfaction.user.name}">${fn:substring(unifiedSatisfaction.user.name, 0, 8)}...</td>
						 </c:if>
		                 <td align="left">${unifiedSatisfaction.localNumber}<comm:message key='localnumber.unit'/></td>
		                 <td align="left" title="${meeting.title}">${meeting.title}</td>
						 <c:forEach var="evaluation" items="${applicationEvaluation}" varStatus="status">
					        <td align="left">
								<div id="set_${status.index}" class="exemple">
									 <c:if test="${status.index == 0}">
									 	${unifiedSatisfaction.evaluationScore1}<comm:message key='meeting.and.video.star'/>
			 					     </c:if>
									 <c:if test="${status.index == 1}">
									 	${unifiedSatisfaction.evaluationScore2}<comm:message key='meeting.and.video.star'/>
									 </c:if>
									 <c:if test="${status.index == 2}">
									 	${unifiedSatisfaction.evaluationScore3}<comm:message key='meeting.and.video.star'/>
									 </c:if>                 
								 </div>
						    </td>
						 </c:forEach>
						 <c:if test="${fn:length(unifiedSatisfaction.suggestion) <= 10}">
						    <td>${unifiedSatisfaction.suggestion}</td>
						 </c:if>
						 <c:if test="${fn:length(unifiedSatisfaction.suggestion) > 10}">
						    <td title="${unifiedSatisfaction.suggestion}">${fn:substring(unifiedSatisfaction.suggestion, 0, 10)}...</td>
						 </c:if>		                 
		              </tr>			   
		    </c:if> 
		   </c:forEach>	
				    <c:if test="${unifiedSatisfaction.evaluationScore1 == null}">
				   		<input type="hidden" value="3" name="star0" id="star0"/>
				    </c:if>
				    <c:if test="${unifiedSatisfaction.evaluationScore1 != null}">
				   		<input type="hidden" value="${unifiedSatisfaction.evaluationScore1}" name="star0" id="star0"/>
				    </c:if>
				    
				    <c:if test="${unifiedSatisfaction.evaluationScore2 == null}">
				   		<input type="hidden" value="3" name="star1" id="star1"/>
				    </c:if>
				    <c:if test="${unifiedSatisfaction.evaluationScore2 != null}">
				   		<input type="hidden" value="${unifiedSatisfaction.evaluationScore2}" name="star1" id="star1"/>
				    </c:if>
				    
				    <c:if test="${unifiedSatisfaction.evaluationScore3 == null}">
				   		<input type="hidden" value="3" name="star2" id="star2"/>
				    </c:if>
				    <c:if test="${unifiedSatisfaction.evaluationScore3 != null}">
				   		<input type="hidden" value="${unifiedSatisfaction.evaluationScore3}" name="star2" id="star2"/>
				    </c:if>	
			  </table>	   
	   </c:if>
	   <c:if test="${fn:length(unifiedSatisfactionList) == 0}">
	                   参会共同体暂无评价！
	   </c:if>
      </c:if>
      <c:if test="${meeting.meetingKind == 2}">
	   <c:if test="${fn:length(unifiedSatisfactionList) != 0}">
			   <table id="shenqing" class="submit_table" name="meetiOpintion" align="center" width="100%" border="1">
		              <tr>
		                 <td align="center"><strong><comm:message key='meeting.evaluation.human'/></strong></td>
		                 <td align="center"><strong><comm:message key='local.number'/></strong></td>
		                 <td align="center"><strong><comm:message key='unified.lecture.content'/></strong></td>
		                 <c:forEach var="evaluation" items="${applicationEvaluation}" varStatus="status">
				             <c:if test="${fn:length(evaluation.name) <= 6}">
		                         <td align="center"><strong>${evaluation.name}</strong></td>
				             </c:if>
				             <c:if test="${fn:length(evaluation.name) > 6}">
		                         <td align="center" title="${evaluation.name}"><strong>${fn:substring(evaluation.name, 0, 6)}</strong></td>
				             </c:if>		                 
		                 </c:forEach>
		                 <td align="center"><strong><comm:message key='unified.suggestion'/></strong></td>
		              </tr>
					   <c:forEach var="unifiedSatisfaction" items="${unifiedSatisfactionList}">			
						  <c:if test="${unifiedSatisfaction.localNumber != null}">
				              <tr>
								 <c:if test="${fn:length(unifiedSatisfaction.user.name) <= 8}">
								    <td align="left">${unifiedSatisfaction.user.name}</td>
								 </c:if>
								 <c:if test="${fn:length(unifiedSatisfaction.user.name) > 8}">
								    <td align="left" title="${unifiedSatisfaction.user.name}">${fn:substring(unifiedSatisfaction.user.name, 0, 8)}...</td>
								 </c:if>
				                 <td align="left">${unifiedSatisfaction.localNumber}<comm:message key='localnumber.unit'/></td>
				                 <td align="left">${meeting.content}</td>
								 <c:forEach var="evaluation" items="${applicationEvaluation}" varStatus="status">
							        <td align="left">
										<div id="set_${status.index}" class="exemple">
											 <c:if test="${status.index == 0}">
											 	${unifiedSatisfaction.evaluationScore1}<comm:message key='meeting.and.video.star'/>
					 					     </c:if>
											 <c:if test="${status.index == 1}">
											 	${unifiedSatisfaction.evaluationScore2}<comm:message key='meeting.and.video.star'/>
											 </c:if>
											 <c:if test="${status.index == 2}">
											 	${unifiedSatisfaction.evaluationScore3}<comm:message key='meeting.and.video.star'/>
											 </c:if>                 
										 </div>
								    </td>
								 </c:forEach>
								 <c:if test="${fn:length(unifiedSatisfaction.suggestion) <= 10}">
								    <td align="left">${unifiedSatisfaction.suggestion}</td>
								 </c:if>
								 <c:if test="${fn:length(unifiedSatisfaction.suggestion) > 10}">
								    <td align="left" title="${unifiedSatisfaction.suggestion}">${fn:substring(unifiedSatisfaction.suggestion, 0, 10)}...</td>
								 </c:if>		                 
				              </tr>				   
					    </c:if> 
					   </c:forEach>     
				    <c:if test="${unifiedSatisfaction.evaluationScore1 == null}">
				   		<input type="hidden" value="3" name="star0" id="star0"/>
				    </c:if>
				    <c:if test="${unifiedSatisfaction.evaluationScore1 != null}">
				   		<input type="hidden" value="${unifiedSatisfaction.evaluationScore1}" name="star0" id="star0"/>
				    </c:if>
				    
				    <c:if test="${unifiedSatisfaction.evaluationScore2 == null}">
				   		<input type="hidden" value="3" name="star1" id="star1"/>
				    </c:if>
				    <c:if test="${unifiedSatisfaction.evaluationScore2 != null}">
				   		<input type="hidden" value="${unifiedSatisfaction.evaluationScore2}" name="star1" id="star1"/>
				    </c:if>
				    
				    <c:if test="${unifiedSatisfaction.evaluationScore3 == null}">
				   		<input type="hidden" value="3" name="star2" id="star2"/>
				    </c:if>
				    <c:if test="${unifiedSatisfaction.evaluationScore3 != null}">
				   		<input type="hidden" value="${unifiedSatisfaction.evaluationScore3}" name="star2" id="star2"/>
				    </c:if>
			  </table>	 
	   </c:if>
	   <c:if test="${fn:length(unifiedSatisfactionList) == 0}">
	                   参会共同体暂无评价！
	   </c:if>      
      </c:if>

    </div>
	<div id="titleStyle">
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
