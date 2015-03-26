<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="meeting.m.detailedpage"/></title>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryui/css/jquery-ui-1.8.16.custom.css.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryui/jquery-ui-1.8.16.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryui/jqueryTab/jquery.idTabs.min.js"></script>
<style type="text/css">
.titleStyle{color:#3c3645;font-size:24px;font-weight: bold;height:20px;line-height:20px;margin-top:10px;margin-bottom:5px;margin-left:20px;margin-right:25px;border:0px dashed #3c3645; }
#viewappinfor{border-top:0px solid #7F9DB9;border-bottom:0px solid #7F9DB9;}
.meetingapp tr{height:25px;}
.meetingapp tr td{height:15px;border-top:0px solid #7F9DB9;border-bottom:0px solid #7F9DB9;}
.meetingapp tr td select{border:1px solid #7F9DB9;margin-left:3px;background-color:#F8F8FF;width:95%;}
.meetingapp tr td textarea{border:1px solid #7F9DB9;margin-left:3px;background-color:#F8F8FF;width:95%;}
.subinfor{border:0px solid #7F9DB9;margin-left:30px;margin-right:30px;}
._table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px;margin-left:0px; }
._table caption {font-size:18px; font-weight:bolder;}
._table tr td input{width:151px;}
._table td{height:25px; line-height:25px;border:1px solid #D2D2D2;}
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
#myFileListName a{text-decoration:none;padding-top:1px;}
.usual ul a:hover { 
    color:#FFF; 
    background:#D2D2D2; 
    } 
#myFileListName a:hover{text-decoration:underline;padding-top:1px;}
.usual ul a.selected { 
    color:#000; 
    background:snow; 
    cursor:default; 
    } 
     
    /*tab页*/ 
/* .usual div { 
    padding:1px 1px 0px 1px; 
    background:snow; /* 
    font:10pt Arial;  */
} */
.submit_table tr td{width:155px;height:20px;overflow:hidden;white-space:nowrap;}
</style>
<script type="text/javascript">
       window.onload = function(){
    	   //alert("width:"+window.screen.width);
    	   //alert("height:"+window.screen.height);
		   var acceptPeopleCount = parseInt("${fn:length(acceptPeople)}");
    	   var acceptPeopleRows = (acceptPeopleCount%3 == 0 ? parseInt(acceptPeopleCount/3) : (parseInt(acceptPeopleCount/3) + 1));
    	   if(acceptPeopleRows == 0){
    		   acceptPeopleRows = acceptPeopleRows + 1;
    	   }else{
    		   acceptPeopleRows = acceptPeopleRows;
    	   }
    	   
    	   var noneAcceptPeopleCount = parseInt("${fn:length(noneAcceptPeople)}");
    	   var noneAcceptPeopleRows = (noneAcceptPeopleCount%3 == 0 ? parseInt(noneAcceptPeopleCount/3) : (parseInt(noneAcceptPeopleCount/3) + 1));
    	   if(noneAcceptPeopleRows == 0){
    		   noneAcceptPeopleRows = noneAcceptPeopleRows + 1;
    	   }else{
    		   noneAcceptPeopleRows = noneAcceptPeopleRows;
    	   }
    	   
    	   var refusedAcceptPeopleCount = parseInt("${fn:length(refusedAcceptPeople)}");
    	   var refusedAcceptPeopleRows = (refusedAcceptPeopleCount%3 == 0 ? parseInt(refusedAcceptPeopleCount/3) : (parseInt(refusedAcceptPeopleCount/3) + 1));
    	   if(refusedAcceptPeopleRows == 0){
    		   refusedAcceptPeopleRows = refusedAcceptPeopleRows + 1;
    	   }else{
    		   refusedAcceptPeopleRows = refusedAcceptPeopleRows;
    	   }

    	   var totalState = acceptPeopleRows + noneAcceptPeopleRows + refusedAcceptPeopleRows;
      	 
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
    		   //alert("1440*900");
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
       	getHeight(); 
       };
       function getHeight() {      
    		var bodyHeight = document.documentElement.clientHeight;

        	var ulId = document.getElementById('ulId').offsetHeight;
         	var firstTitle = document.getElementById('firstTitle').offsetHeight;
        	var lastTitle = document.getElementById('lastTitle').offsetHeight;
        	var totalTitle = ulId + firstTitle + lastTitle;
    		var tableHeight = (document.getElementById("shenqing").offsetHeight)+totalTitle;
            
        	
    		if(bodyHeight >= tableHeight){
    			var patientinfor1Height =document.getElementById("patientinfor2").style.height = bodyHeight-totalTitle;		
    	    }else{
    	    	document.documentElement.style.overflowY = 'auto';
    	    }
    	}

       function Exp(filepath,title){  alert(filepath +"***"+title)
			title = encodeURI(encodeURI(title));
			window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
		}
</script>
</head>

<body style=" overflow-y:auto;"> 
<div id="usual1" class="usual">    
    <ul id="ulId">    
        <li><a class="selected" href="#tab1">详细信息</a></li>    
        <li><a href="#tab2">受邀状态</a></li>    
    </ul>    
    <div id="tab1">
	    <div id="viewappinfor" style="position:relative;background-color:#dee;overflow-y:auto;">
		    <div id="firstTitle" class="subtitle" style="background-color:#dee;">
				   <div class="titleStyle" id="titleid" style="font-size:22px;font-weight: bold;color:#000;background-color:#dee;">
						  <comm:message key='meeting.view.video.xiangxi.information' />
				   </div>	    
		    </div>
		    <div class="subinfor" id="patientinfor2">
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
					<c:if test="${vmapp.state==MEETING_APPLICATION_STATE_PASS}">
						<tr>
							<td width="10%"  align="right"><comm:message key='unified.starttime' />:&nbsp;&nbsp;</td>
							<td width="25%" align="left"><fmt:formatDate value="${meeting.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td width="10%"  align="right"><comm:message key='unified.endtime' />:&nbsp;&nbsp;</td>
							<td width="25%" align="left"><fmt:formatDate value="${meeting.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
					</c:if>
					<tr>	
						<td width="10%" align="right"><comm:message key='unified.lecture.people.dept' />:&nbsp;&nbsp;</td>
						<td width="25%" align="left">${vmapp.deptName.name}</td>
						<td width="10%" align="right"><comm:message key='unified.lecture.people.name' />:&nbsp;&nbsp;</td>
						<td width="25%" align="left">${ vmapp.userName.name}</td>
						<td style="display:none;" width="10%" align="right"><comm:message key='lecture.m.lecturelevel' />:&nbsp;&nbsp;</td>
						<td style="display:none;" width="25%" align="left">${vmapp.level.name}</td>
					</tr>
					<c:if test="${vmapp.state==MEETING_APPLICATION_STATE_PASS}">
						<tr>
							<td width="10%"  align="right"><comm:message key='view.video.meetingroom' />:&nbsp;&nbsp;</td>
							<td width="25%" align="left">${meeting.meetingRoomId.name}</td>
						</tr>
					</c:if>
					<c:if test="${vmapp.state==MEETING_APPLICATION_STATE_PENDING||vmapp.state==MEETING_APPLICATION_STATE_REFUSED}">
						<tr>
							<%-- <td width="25%" align="right"><comm:message key='unified.lecture.people.position' />:&nbsp;&nbsp;</td>
							<td width="25%" align="left">${vmapp.applicationPosition.name }</td> --%>
							<td width="10%"  align="right"><comm:message key='meeitng.suggestTime' />:&nbsp;&nbsp;</td>
							<td width="25%"  align="left"><fmt:formatDate value="${vmapp.suggestDate}" pattern="yyyy-MM-dd HH:mm"/></td>														
						</tr>
					</c:if>	
					<c:if test="${vmapp.state==MEETING_APPLICATION_STATE_REFUSED}">
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
			              <li style="width:230px;line-height:20px;vertical-align:top;overflow:hidden;white-space:nowrap;">
	           				 <div style="width:200px;">
							    <div id="myFileListName" style="float:left;width:80px;" title="${myfilelist.type.name}">
							          ${fn:substring(myfilelist.type.name, 0, 6)}<span>:</span>
							    </div>
							    <c:if test="${fn:length(myfilelist.name) > 10}">
								    <div id="myFileListName" style="float:left;width:80px;">
								       <a style="color:blue;background:snow;" onclick="Exp('${myfilelist.path}','${myfilelist.name}');"  title="${myfilelist.name}">${fn:substring(myfilelist.name, 0, 10)}...</a>
								    </div>
							    </c:if>
							    <c:if test="${fn:length(myfilelist.name) <= 10}">
								    <div id="myFileListName" style="float:left;width:50px;">
								       <a style="color:blue;background:snow;"  onclick="Exp('${myfilelist.path}','${myfilelist.name}');"  title="${myfilelist.name}">${myfilelist.name}</a>
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
			<div id="lastTitle" class="titleStyle" style="background-color:#dee;">
				<span>&nbsp;</span>
			</div>	
		</div>   
    </div>    
    <div id="tab2">
	  <div id="viewappinfor" style="position:relative;overflow-y:auto;">
   		<div class="subtitle" id="firstTitle" style="background-color:#dee;">
			   <div class="titleStyle" id="titleid" style="font-size:22px;font-weight: bold;color:#000;background-color:#dee;">
					  <strong><comm:message key="unified.invite.accept"/></strong>
			   </div>	    
	    </div>
		<div class="subinfor" id="patientinfor1">
	        <table class="submit_table" width="80%" id="firstTable" style="table-layout:fixed;positon:fixed;" cellspacing="0" cellpadding="0" border="0"> 
	         <c:if test="${vmapp.state == 2}"> 
	          <c:if test="${fn:length(acceptPeople) != 0}">
		        <c:forEach items="${acceptPeople}" var="accept_state" varStatus="status">
		        	<c:choose>
		        		<c:when test="${status.count == 1  || status.count % 3 == 1 }">
		        		  <tr class="defaultTr" id="firstTableTr"><td class="acceptFirstTd" title="${accept_state.name}">${accept_state.name}&nbsp;</td>
		        		</c:when>
		        		<c:when test="${status.count % 3 == 0 }">
		        			<td width="33%" class="acceptFirstTd" title="${accept_state.name}">${accept_state.name}&nbsp;</td></tr>	
		        		</c:when>
		        		<c:otherwise>
		        			<td width="33%" class="acceptFirstTd" title="${accept_state.name}">${accept_state.name}&nbsp;</td>
		        		</c:otherwise>
		        	</c:choose>
		       	</c:forEach>	          
	          </c:if>
              <c:if test="${acceptPeople == null || fn:length(acceptPeople) == 0}">
       	          <tr height="20">&nbsp;</tr>
              </c:if>
             </c:if>
             <c:if test="${vmapp.state == 1}">
                  <tr height="20"><td align="center" style="font-size:18px;"><comm:message key="video.has.been.no.start"/></td></tr>
             </c:if> 
             <c:if test="${vmapp.state == 3}">
                  <tr height="20"><td align="center" style="font-size:18px;"><comm:message key="video.has.been.refuse"/></td></tr>
             </c:if>			       		 
	        </table>
		</div>   
	    <div class="subtitle" style="background-color:#dee;">
			   <div class="titleStyle" id="titleid" style="font-size:22px;font-weight: bold;color:#000;background-color:#dee;">
					  <strong><comm:message key="unified.invite.none"/></strong>
			   </div>	    
	    </div>
		<div class="subinfor" id="patientinfor3"> 
	        <table class="submit_table" id="twoTable" style="table-layout:fixed;" cellspacing="0" cellpadding="0" border="0">
	          <c:if test="${fn:length(noneAcceptPeople) != 0}">
		       	<c:forEach items="${noneAcceptPeople}" var="none_accept_state" varStatus="status">
		       	     <c:choose>
		       	         <c:when test="${status.count == 1  || status.count % 3 == 1 }">
		       	             <tr class="defaultTr" id="twoTableTr" style="positon:fixed;"><td class="noneAcceptFirstTd" title="${none_accept_state.name}">${none_accept_state.name}&nbsp;</td>
		       	         </c:when>
		       	         <c:when test="${status.count % 3 == 0 }">
		       	             <td width="33%" class="noneAcceptFirstTd" title="${none_accept_state.name}">${none_accept_state.name}&nbsp;</td></tr>
		       	         </c:when>
		       	         <c:otherwise>
		       	             <td width="33%" class="noneAcceptFirstTd" title="${none_accept_state.name}">${none_accept_state.name}&nbsp;</td>
		       	         </c:otherwise>
		       	     </c:choose>		 
		       	</c:forEach>
	          </c:if>
              <c:if test="${noneAcceptPeople == null || fn:length(noneAcceptPeople) == 0}">
       	          <tr height="20">&nbsp;</tr>
              </c:if>
	        </table>		 
		</div>
		<div class="subtitle" style="background-color:#dee;">
			   <div class="titleStyle" id="titleid" style="font-size:22px;font-weight: bold;color:#000;background-color:#dee;">
					  <strong><comm:message key="unified.invite.refuse"/></strong>
			   </div>	    
	    </div>
		<div class="subinfor" id="patientinfor4">
	        <table class="submit_table" id="threeTable" cellspacing="0" cellpadding="0" border="0">
	          <c:if test="${fn:length(refusedAcceptPeople) != 0}">
		       	<c:forEach items="${refusedAcceptPeople}" var="refused_accept_state" varStatus="status">
		       	    <c:choose>
		       	         <c:when test="${status.count == 1  || status.count % 3 == 1 }">
		       	             <tr class="defaultTr" id="threeTableTr"><td class="refusedAcceptFirstTd" title="${refused_accept_state.name}">${refused_accept_state.name}&nbsp;</td>
		       	         </c:when>
		       	         <c:when test="${status.count % 3 == 0 }">
		       	             <td width="33%" class="refusedAcceptFirstTd" title="${refused_accept_state.name}">${refused_accept_state.name}&nbsp;</td></tr>
		       	         </c:when>
		       	         <c:otherwise>
		       	             <td width="33%" class="refusedAcceptFirstTd" title="${refused_accept_state.name}">${refused_accept_state.name}&nbsp;</td>
		       	         </c:otherwise>
		       	    </c:choose>		 
		       	</c:forEach>	          
	          </c:if>
              <c:if test="${refusedAcceptPeople == null || fn:length(refusedAcceptPeople) == 0}">
       	          <tr height="20" id="refusedAcceptLastTr">&nbsp;</tr>
              </c:if>
	        </table>		
		</div>
		<div class="titleStyle" id="lastTitle" style="background-color:#dee;">
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