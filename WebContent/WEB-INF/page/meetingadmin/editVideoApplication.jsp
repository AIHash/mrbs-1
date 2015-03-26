<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
	String returnmessage = (String) request.getSession().getAttribute("returnmessage");
	request.getSession().removeAttribute("returnmessage");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="unified.application" /></title>
	<comm:pageHeader hasEcList="false" />
	<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" /><%-- 
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css" /> --%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryui/css/jquery-ui-1.8.16.custom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css"  media="screen" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryui/jquery-ui-1.8.16.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>

<script type="text/javascript"> 

  function check(){
	 var content=document.getElementById("lectureContent").value;
	 if(content==""||content==null){
		 jAlert("<comm:message key='meeting.lectureContent.notNull'/>", "<comm:message key='meeting.m.infotishi'/>");
		 return false;
	 }
	 var deptmentid = document.getElementById("deptmentid").value;
	 if(deptmentid==""||deptmentid==null){
		  jAlert("<comm:message key='unified.need.dept'/>", "<comm:message key='meeting.m.infotishi'/>");
		 return false;
	 }
	 var userName = document.getElementById("deptpersonid").value;
	 if(userName == ""||userName==null){
		  jAlert("<comm:message key='meeting.chooseSpeaker'/>", "<comm:message key='meeting.m.infotishi'/>");
		 return false;
	 }
	 
	  var meetingleveld=document.getElementById("meetingleveld").value;
	 if(meetingleveld==""||meetingleveld==null){
		  jAlert("<comm:message key='unified.need.meetinglevel'/>", "<comm:message key='meeting.m.infotishi'/>");
		 return false;
	 }
	 
	 var expecttime = document.getElementById("expecttime").value;
	 if(expecttime == ""||expecttime==null) {
		 jAlert("<comm:message key='meeting.suggestTime.isNotNull'/>", "<comm:message key='meeting.m.infotishi'/>");
		 return false;
	 }
	 return true;
  }
	
	$(document).ready(function() {
    	var count=getUnicodeLength(document.getElementById("lectureContent").value);
	   	$('#tip').html(500-count);
	   	
	   	var count=getUnicodeLength(document.getElementById("teachingObject").value);
	   	$('#tipContent').html(200-count);
	   	
	   	var count=getUnicodeLength(document.getElementById("teachingObject").value);
	   	$('#tip1').html(500-count);
	   	
	    $("#innaiUserSelect").dblclick(function(){
			topmoveInnaiUser();
		});
		//院内		
		changeInnaiUser();
		$("#deptpersonid").val('${vmapp.userName.userId}');
		if(${vmapp.userName.name!=null}){
			$("#queryInnaiUserName").val('${vmapp.userName.name}(${vmapp.deptName.name})');
			$("#innaiUserOldValue").val('${vmapp.userName.name}(${vmapp.deptName.name})'); 
		}
	    			
		setInterval(function() {
			//比对院内专家的速查条件是否有变化
			var innaiUserOldValue = document.getElementById("innaiUserOldValue").value;
			var queryInnaiUserName=$('#queryInnaiUserName').val();
			if(innaiUserOldValue!=queryInnaiUserName){
				changeInnaiUser();
			}
			document.getElementById("innaiUserOldValue").value=queryInnaiUserName;
			
		}, 3000); 
	});
	
	function changeInnaiUser(){
		var queryInnaiUserName=$('#queryInnaiUserName').val();
		$("#innaiUserSelect").empty();
		//已选择专家
		var innaiUserIds = "";
		$.ajaxSettings.traditional = true;
	    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/getUserSelectItem', 
				data : {queryUserName:queryInnaiUserName,userIds:innaiUserIds,departmentKind:'${HIS_DEPT_CODE}'},
				dataType : 'json',
				dataFilter : function(json, type){
					if(json.indexOf('<html>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						return;
					}
					return json;
				},
				success : function(json) {
				$.each(json, function(i,n){
					$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#innaiUserSelect");
				});
			}
		});
	}	
	function topmoveInnaiUser(){
		var so = $("#innaiUserSelect option:selected").text();
        $("#queryInnaiUserName").val(so);
        $("#innaiUserOldValue").val(so);
        		
		//获取被选择的专家
		var innaiUserSelecteds=$('#innaiUserSelect option:selected');
		var selectedUserIds = innaiUserSelecteds[0].value;
		$("#deptpersonid").val(selectedUserIds);
	}
	
	function cancelFun(){
		jConfirm("<comm:message key='js.confirm_cancel' args='comm.modify'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
			if(resultConfirm){
				location='<%=request.getContextPath()%>/meeadIndex/videoLecturesQuery';
			}else {
				return false;
			}	
		});
	}
	
	function beforeSubmit(){
    	var testName=$("#queryInnaiUserName").val();
    	if(testName==null||testName=="")
    		$("#deptpersonid").val("");
    }
	
	function sAlert(strTitle){ 
	    var msgw,msgh,bordercolor; 
	    msgw=450;//提示窗口的宽度 
	    msgh=230;//提示窗口的高度 
	    titleheight=25; //提示窗口标题高度 
	    bordercolor="#336699";//提示窗口的边框颜色 
	    titlecolor="#99CCFF";//提示窗口的标题颜色

	    var sWidth,sHeight; 
	    sWidth=document.body.offsetWidth; 
	    sHeight=screen.height; 
	    var bgObj=document.createElement("div"); 
	    bgObj.setAttribute('id','bgDiv'); 
	    bgObj.style.position="absolute"; 
	    bgObj.style.top="0"; 
	    bgObj.style.background="#777"; 
	    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
	    bgObj.style.opacity="0.6"; 
	    bgObj.style.left="0"; 
	    bgObj.style.width=sWidth + "px"; 
	    bgObj.style.height=sHeight + "px"; 
	    bgObj.style.zIndex = "10000"; 
	    document.body.appendChild(bgObj);

	    var msgObj=document.createElement("div");
	    msgObj.setAttribute("id","msgDiv"); 
	    msgObj.setAttribute("align","center"); 
	    msgObj.style.background="white"; 
	    msgObj.style.border="1px solid " + bordercolor; 
	    msgObj.style.position = "absolute"; 
	    msgObj.style.left = "50%"; 
	    msgObj.style.top = "50%"; 
	    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    msgObj.style.marginLeft = "-225px" ; 
	    msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px"; 
	    msgObj.style.width = msgw + "px"; 
	    msgObj.style.height = msgh + "px"; 
	    msgObj.style.textAlign = "center"; 
	    msgObj.style.lineHeight ="25px"; 
	    msgObj.style.zIndex = "10001";

	    var title=document.createElement("h4"); 
	    title.setAttribute("id","msgTitle"); 
	    title.setAttribute("align","right"); 
	    title.style.margin="0"; 
	    title.style.padding="0px"; 
	    title.style.background=bordercolor; 
	    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);"; 
	    title.style.opacity="0.75"; 
	    title.style.border="1px solid " + bordercolor; 
	    title.style.height="28px"; 
	    title.style.width="100%";
	    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    title.style.color="white"; 
	    title.style.cursor="pointer"; 
	    title.title ="<comm:message key='unified.close'/>"; 
	    title.close="<comm:message key='unified.window.close'/>"; 
	    title.innerHTML="<table border='0′ align='center' width='100%' ><tr><td align='left' width='12%'>"+' '+"</td><td align='left' width='69%'>"+strTitle+"</td><td width='9%' >"+title.close+"</td></tr></table>";
	    title.onclick = function(){
		    document.body.removeChild(bgObj);
		    document.getElementById("msgDiv").removeChild(title);
		    document.body.removeChild(msgObj);
	    };
	    document.body.appendChild(msgObj); 
	    document.getElementById("msgDiv").appendChild(title); 
	    var txt=document.createElement("p"); 
	    txt.style.margin="0em 0";
	    //txt.style.border="1px solid red";
	    txt.setAttribute("id","msgTxt"); 
	    txt.innerHTML="<iframe style='width:100%;height:200px;'  src='<%=request.getContextPath()%>/resources/fileupload.jsp' scrolling='no'></iframe>"; 
	    document.getElementById("msgDiv").appendChild(txt); 
	}

	function  delfile(liid,fileid){
		jConfirm("<comm:message key='js.confirm_delete_accessory'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>", function(select){
			if(select){
				var filelist=document.getElementById("filelist");
			    var filevalue=document.getElementById("filevalue");
				var delobj=document.getElementById(fileid);
				var testobj=document.getElementById(liid);	
				testobj.parentNode.removeChild(testobj);
				filevalue.removeChild(delobj);
			}
			return false;	
		});
	}
	 function Exp(filepath,title){  
			title = encodeURI(encodeURI(title));
			window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
		}

</script>
<style>
#center{background-color:#ffffff;}
#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;line-height:30px;margin-top:15px;margin-bottom:0px;margin-left:40px;margin-right:40px;}
#viewappinfor{margin-left:25px;margin-top:0px;margin-bottom:5px;margin-right:25px;position:relative;background-color:#dee; overflow-y:auto;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:40px;margin-right:40px;}
select{background: #ffffff;border: 1px solid #7F9DB9;width:100px;color:#000000;height:22px;}
.main1_sub{border:1px solid #000000;background-color:#ffffff;}
/**修饰表格的边框**/
.main1_sub{border:0px solid #000000;background-color:#F8F8FF;}
._table{border-bottom:0px;border-right:0px;border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px; width:470; margin:auto; }
._table caption {font-size:18px; font-weight:bolder;}
._table tr td input{width:151px;}
._table td{height:25px; line-height:25px;border:1px solid #D2D2D2;}
._table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0; padding:2px;}
</style>
</head>
<body style="padding:0; margin:0;background:url('../../resources/images/theme/bg_right-2.gif');" class="bg" onload="heigthReset(64);" onresize="heigthReset(64);">
	<div id="center">
		<comm:navigator position="manager.videoLectures.lectureAudit>>unified.video.application.edit" />
		  <div id="main" style=" overflow-y:auto;overflow-x:hidden;width:725px;margin:0px;float:left;">
			<form method="post" action="<%=request.getContextPath()%>/meeadmdbd/editVideoApplication" onsubmit="return check()">
			  <input type="hidden" id="innaiUserOldValue" value=""/>
			  <div id="viewappinfor">
			  <input type="hidden" name="id" value="${vmapp.id }"/>
				<div id="titleStyle">
					<span><comm:message key='unified.video.application.edit' /></span>
				</div>		
	            <div class="subinfor" id="patientinfor1">
					<table id="shenqing" class="shenqing_table" width="100%" border="0">
						<tr>
							<td width="15%"  align="right"><comm:message key='unified.lecture.content' />:<span class="required">*</span><div id="hint" style="margin-right: 10px;">(<span id="tipContent"></span>/200)</div></td>
							<td colspan="3">
							     <textarea name="lectureContent" style="border: 1px solid #7F9DB9;width:98%; height:30px;" id="lectureContent" rows="2" class="text-long" maxlength="200" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>200">${vmapp.lectureContent}</textarea>
							</td>
						</tr>
						<tr>
							<td width="15%"  align="right"><comm:message key='meeting.teachingObject' />:&nbsp;<div id="hint1" style="margin-right: 10px;">(<span id="tip1"></span>/500)</div></td>
							<td colspan="3">
							    <textarea name="teachingObject" style="border: 1px solid #7F9DB9;width:98%; height:50px; " id="teachingObject" rows="5" class="text-long" maxlength="500" onpaste="return realTimeCountClip(this,'hint1');" onkeyup="javascript:realTimeCount(this,'hint1');" title=" <comm:message key="comm.content_maxlength"/>500">${vmapp.teachingObject}</textarea>
							</td>
						</tr>
						<tr>
						   <td style="display:none;" width="15%"  align="right"><comm:message key='lecture.m.lecturelevel' />:<span class="required">*</span></td>
		                   <td style="display:none;" width="30%">
								<select name="meetinglevel" id="meetinglevel" style="width:130px;">
									<c:forEach var="meetinglevel" items="${basecode['meetinglevel']}">
									    <option value="${meetinglevel.id}" <c:if test="${meetinglevel.id == vmapp.level.id}">selected</c:if>>${meetinglevel.name}</option>
									</c:forEach>
							   </select>					                   
		                   </td>
		                   <td width="15%" align="right">			
						       <comm:message key='meeitng.suggestTime' />:<span class="required">*</span> 
						   </td>
						   <td colspan="3" align="left">
						       <input type="text" name="expecttime" id="expecttime" onclick="WdatePicker({dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s',maxDate:'{%y+1}-%M-%d 21:00:00'})" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" onfocus="this.blur()" size="20" value="<fmt:formatDate value="${vmapp.suggestDate}" pattern="yyyy-MM-dd HH:mm" />"/>
						   </td>
						</tr>						
						<tr>
						    <td width="15%"  align="right" style="vertical-align:middle;"><comm:message key='unified.and.expert_metting.speakerName'/>:&nbsp;</td>		
					        <td colspan="3">
					    	<table style="border:1px solid #000;">
								<tr>
									<!-- <td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;" > -->
									<td bgcolor="#D0D0D0">
										 &nbsp;<strong>速查条件</strong><input id="queryInnaiUserName" style="align:right;line-height:18px;height:18px;" value="" size="30"></input>
									</td>
								</tr>
								<tr>
									<!-- <td style="border-right:1px solid #000;border-bottom:1px solid #000;"> -->
									<td>
										<select  multiple="multiple" name="innaiUserSelect" id="innaiUserSelect"  style="align:center;width:260px;height:143px;border:none;">
										</select>	
									</td>															
								</tr>
							</table>
					        </td>
							<%-- <td width="15%"  align="right"><comm:message key='unified.lecture.people.dept' />:<span class="required">*</span>
							</td>
							<td>
							     <table border="0" width="100%">
							           <tr>
						                   <td width="30%">
												<select name="deptmentid" id="deptmentid" style="width:130px;" >
													<c:forEach var="deptment" items="${basecode['deptment']}">
														<option value="${deptment.id}" <c:if test="${deptment.id == vmapp.deptName.id}">selected</c:if>>${deptment.name}</option>
													</c:forEach>
												</select>							                
							                </td>
							                <td width="30%" align="right">
							                     <comm:message key='unified.lecture.people.name' />:<span class="required">*</span>
							                </td>
							                <td>
							                     <select name="deptpersonid" id="deptpersonid" style="width:130px;"></select>
							                </td>
							           </tr>
							     </table>
							</td> --%>
							<input type="hidden" name="deptpersonid" id="deptpersonid" value=""/>
						</tr>	
						<c:if test="${vmapp.accessories!=null&&vmapp.accessories.size() > 0}">
							<tr>
								<td width="15%"  align="right"><comm:message key='unified.and.expert_accessories' />:&nbsp;</td>
								<td colspan="3">
								<div class="main1_sub">
								        <table width="100%" border="0">
								        	<tr>
								                <td width="50%">
								                	 <a href="#" onclick='sAlert("<comm:message key='unified.upload.accessories'/>")' style="COLOR:blue;margin-top:10px;">
														   <comm:message key='unified.select.accessories' />
								                     </a>
								                </td>
								           </tr>
								           <tr>
								           
						                       <td>
												<ul id="filelist" style="list-style-type:none;">
													<c:forEach var="myfilelist"	items="${vmapp.accessories}">
														<li id = "li${myfilelist.id}">
														    <div style="width:200px;">
														        <div style="float:left;width:80px;" title="${myfilelist.type.name}">
														            ${fn:substring(myfilelist.type.name,0,6)}<span>:</span>
														        </div>
														        <div style="float:left;width:80px;">
														            <a style="COLOR:blue;" href="#" onclick="Exp('${myfilelist.path}','${myfilelist.name}');" title="${myfilelist.name}">${fn:substring(myfilelist.name,0,6)}</a>
														        </div>
														        <div style="float:left;width:30px;margin-left:3px;">
														            <a style="COLOR:blue;" href="#" onclick="return delfile('li${myfilelist.id}','input${myfilelist.id}')"><comm:message key='unified.delete' /></a>
														        </div>
														    </div>
														</li>
													</c:forEach>
												</ul>
												<table style="display: none">
													<tr>
														<td id="filevalue">
														<c:forEach var="file" items="${vmapp.accessories}">
															<input type="hidden" id="input${file.id}" name="myfiles" value="${file.name}@${file.path}@${file.type.id}@${file.type.name}"/>
														</c:forEach>
														</td>
													</tr>
												</table>                      
						                      </td>					           
								           </tr>
								      </table>
								</div>
							  </td>
							</tr>
						</c:if>
						<tr>
							<td width="15%"  align="right">
								<c:if test="${vmapp.videoRequester.userType.value == 4||vmapp.videoRequester.userType.value == 2}">
						            <comm:message key='unified.and.expert.meeting.requester' />:&nbsp;
					            </c:if>
					            <c:if test="${vmapp.videoRequester.userType.value == 5}">
						            <comm:message key='unified.and.expert_requester'/>:&nbsp;
					            </c:if>
							</td>
							<td colspan="3">
							      <table width="99%" id="videoappinfor" style="margin-left:0px;" class="_table" cellspacing="0" cellpadding="0" border="1">
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
								                <c:if test="${fn:length(vmapp.videoRequester.name) <= 8}">
								                  <td>${vmapp.videoRequester.name}&nbsp;</td>
								                </c:if>
								                <c:if test="${fn:length(vmapp.videoRequester.name) > 8}">
								                  <td title="${vmapp.videoRequester.name}">${fn:substring(vmapp.videoRequester.name, 0, 8)}...&nbsp;</td>
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
					                        <c:if test="${vmapp.videoRequester.userType.value == 5}">
										        <c:if test="${fn:length(vmapp.videoRequester.mail) <= 30}">
								                  <td>${vmapp.videoRequester.mail}&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
								                </c:if>
								                <c:if test="${fn:length(vmapp.videoRequester.mail) > 30}">
								                  <td title="${vmapp.videoRequester.mail}">${fn:substring(vmapp.videoRequester.mail, 0, 30)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
								                </c:if>
					                        </c:if>
					                        <c:if test="${vmapp.videoRequester.userType.value == 4}">
										        <c:if test="${fn:length(vmapp.videoRequester.mail) <= 28}">
								                  <td>${vmapp.videoRequester.mail}&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
								                </c:if>
								                <c:if test="${fn:length(vmapp.videoRequester.mail) > 28}">
								                  <td title="${vmapp.videoRequester.mail}">${fn:substring(vmapp.videoRequester.mail, 0, 28)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
								                </c:if>
					                        </c:if>					             
							             </tr>
							     </table>		
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
							 <input class="button" type="submit" name="button" id="button" value="<comm:message key='comm.save'/>" onclick="beforeSubmit()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <input	class="button" type="button" value="<comm:message key='comm.cancel'/>" onclick="cancelFun();"/>
							</td>
						</tr>
					</table>	            
	            </div>
				<div id="titleStyle">
					<span>&nbsp;</span>
				</div>			  
			  </div>	

			</form>
		  </div>	
		</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>	
	<script type="text/javascript">
		function exitsys()
		{
   			 var ask=confirm("<comm:message key='admin.exit_sure'/>\n\n<comm:message key='admin.click_ok'/>");
    		 if(ask)
				parent.location.href="${root}/login/exit";
		}
</script>
</body>

</html>