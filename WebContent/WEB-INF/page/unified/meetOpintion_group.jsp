<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html >
  <head>
  <comm:pageHeader hasEcList="false"></comm:pageHeader>
    <base href="<%=basePath%>"/>
    <title><comm:message key='unified.meetingopintion'/></title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/style/jrating/documentation.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/jrating/jquery.rating.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.rating.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.MetaData.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<style type="text/css">
		.exemple{margin-left:10px;}
		.auto-submit-star{margin:2px;}
		.main-submit-star{margin:2px;}
	</style>
	<script type="text/javascript">
	$(function(){
		 $('.auto-submit-star').rating({
		  callback: function(value, link){
		   var name=$(this).attr('name');
		   if(name=='star0')
		       $('#star0').val(value);
		   if(name=='star1')
			   $('#star1').val(value);
		   if(name=='star2')
			   $('#star2').val(value);
		  }
		 });

		 $('#mainSubmit').click(function(){
			 var localNumber=document.getElementById("localNumber").value;
			 if(localNumber==""||localNumber==null)
			 {
				  jAlert("<comm:message key='unified.need.localnumber'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
			 }
			 jConfirm("<comm:message key='unified.and.expert.evaluation.submit'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
				if(resultConfirm){
					 $.post('<%=request.getContextPath()%>/unified/addgroupopintion',
							 {localNumber : $('#localNumber').val(),evalvalue1 :$('#star0').val(),evalvalue2 :$('#star1').val(),evalvalue3 :$('#star2').val(), meetingid : $('#meetingid').val(), suggestion : $('#suggestion').val()},
						function(text){
							if(text == 'success'){
								parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href;
						    	parent.$.fancybox.close();
							}else if(text.indexOf('<HTML>') != -1){
								window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							}
					 }, 'text');
			    }else{
			    	return false;
			    }				
			});	 
		 });
		});
	
	$(document).ready(function() {
		var count=getUnicodeLength(document.getElementById("suggestion").value);
	   	$('#tip').html(500-count);	   	
	});

	//获取div显示内容的高度，并给改div的内容以外的地方预留高度
    function getHeight() {      
		var bodyHeight = document.documentElement.clientHeight;
		var tableHeight = (document.getElementById("shenqing").offsetHeight)+(document.getElementById("shenqingBut").offsetHeight)+60;
		if(bodyHeight >= tableHeight ){
			var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight-60;		
	    }else{
	    	document.documentElement.style.overflowY = 'auto';
	    }
	}
	
    window.onload = function() { 	
	getHeight();       	
	};
	</script>
  </head>  
  <body id="windows">
  <form name="form1" action="<%=request.getContextPath()%>/unified/addgroupopintion" method="post">
		  <div id="viewappinfor" >
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
			    <table id="shenqing" class="submit_table" name="meetiOpintion" align="center" width="100%" border="1">
			    <tr>
			         <c:if test="${meeting.meetingKind == 1}">
			              <td align="center" colspan="2" style="vertical-align:middle;"><strong><comm:message key='manager.teleconsultation.consultationEvaluation'/></strong></td>        
			         </c:if>
			         <c:if test="${meeting.meetingKind == 2}">
			              <td align="center" colspan="2" style="vertical-align:middle;"><strong><comm:message key='manager.videoLectures.lectureEvaluation'/></strong></td>
			         </c:if>
			    </tr>			    
			    <tr>
			     <c:if test="${meeting.meetingKind == 2}">
			        <td align="right" style="vertical-align:middle;"><strong><comm:message key='unified.lecture.content'/></strong>:&nbsp;</td>
			        <td style="vertical-align:middle;"><textarea name="content" style="border:0px;" rows="2" id="content" cols="40" maxlength="200" onfocus="this.blur()" title=" <comm:message key="comm.content_maxlength"/>200">${meeting.content}</textarea></td>
			     </c:if>
			     <c:if test="${meeting.meetingKind == 1}">
			        <td align="right" style="vertical-align:middle;"><strong><comm:message key='unified.purpose'/></strong>:&nbsp;</td><td style="vertical-align:middle;">${meeting.title}</td>
			     </c:if>
			    </tr>
			    
				<tr>
			    <td align="right" style="vertical-align:middle;"><strong><comm:message key='local.number'/></strong>:<font color="red">*</font></td>
			    <td style="vertical-align:middle;"><input type="text" name="localNumber" id="localNumber" maxlength="4"  style="width:50px" value="${satisfaction.localNumber}" onkeyup="value=value.replace(/[^\d\.]/g,'')"/><comm:message key='localnumber.unit'/></td>
			    </tr>
			    
			    <c:if test="${satisfaction.evaluationScore1 == null}">
			   		<input type="hidden" value="3" name="star0" id="star0"/>
			    </c:if>
			    <c:if test="${satisfaction.evaluationScore1 != null}">
			   		<input type="hidden" value="${satisfaction.evaluationScore1}" name="star0" id="star0"/>
			    </c:if>
			    
			    <c:if test="${satisfaction.evaluationScore2 == null}">
			   		<input type="hidden" value="3" name="star1" id="star1"/>
			    </c:if>
			    <c:if test="${satisfaction.evaluationScore2 != null}">
			   		<input type="hidden" value="${satisfaction.evaluationScore2}" name="star1" id="star1"/>
			    </c:if>
			    
			    <c:if test="${satisfaction.evaluationScore3 == null}">
			   		<input type="hidden" value="3" name="star2" id="star2"/>
			    </c:if>
			    <c:if test="${satisfaction.evaluationScore3 != null}">
			   		<input type="hidden" value="${satisfaction.evaluationScore3}" name="star2" id="star2"/>
			    </c:if>
			    
			    <c:forEach var="evaluation" items="${evallist}" varStatus="status">
			    <tr>
			    <td align="right" style="vertical-align:middle;"><strong>${evaluation.name}</strong>:&nbsp;</td>
			       <td style="vertical-align:middle;">
						<div id="set_${status.index}" class="exemple">
							 <c:if test="${status.index == 0}">
							 	
							 	<input title="${evaluation.title1}" class="auto-submit-star" type="radio" name="star${status.index}" value="1" <c:if test="${satisfaction.evaluationScore1 == 1}">checked="checked"</c:if> />
								 
								<input title="${evaluation.title2}" class="auto-submit-star" type="radio" name="star${status.index}" value="2" <c:if test="${satisfaction.evaluationScore1 == 2}">checked="checked"</c:if> />
								
								<input title="${evaluation.title3}" class="auto-submit-star" type="radio" name="star${status.index}" value="3" <c:if test="${satisfaction.evaluationScore1 ==null||satisfaction.evaluationScore1 == 3}">checked="checked"</c:if> />
								
								<input title="${evaluation.title4}" class="auto-submit-star" type="radio" name="star${status.index}" value="4" <c:if test="${satisfaction.evaluationScore1 == 4}">checked="checked"</c:if> />
								
								<input title="${evaluation.title5}" class="auto-submit-star" type="radio" name="star${status.index}" value="5" <c:if test="${satisfaction.evaluationScore1 == 5}">checked="checked"</c:if> />
							 </c:if>
							 <c:if test="${status.index == 1}">
							 	
							 	<input title="${evaluation.title1}" class="auto-submit-star" type="radio" name="star${status.index}" value="1" <c:if test="${satisfaction.evaluationScore2 == 1}">checked="checked"</c:if> />
								 
								<input title="${evaluation.title2}" class="auto-submit-star" type="radio" name="star${status.index}" value="2" <c:if test="${satisfaction.evaluationScore2 == 2}">checked="checked"</c:if> />
								
								<input title="${evaluation.title3}" class="auto-submit-star" type="radio" name="star${status.index}" value="3" <c:if test="${satisfaction.evaluationScore2 ==null||satisfaction.evaluationScore2 == 3}">checked="checked"</c:if> />
								
								<input title="${evaluation.title4}" class="auto-submit-star" type="radio" name="star${status.index}" value="4" <c:if test="${satisfaction.evaluationScore2 == 4}">checked="checked"</c:if> />
								
								<input title="${evaluation.title5}" class="auto-submit-star" type="radio" name="star${status.index}" value="5" <c:if test="${satisfaction.evaluationScore2 == 5}">checked="checked"</c:if> />
							 </c:if>
							 <c:if test="${status.index == 2}">
							 	
							 	<input title="${evaluation.title1}" class="auto-submit-star" type="radio" name="star${status.index}" value="1" <c:if test="${satisfaction.evaluationScore3 == 1}">checked="checked"</c:if> />
								 
								<input title="${evaluation.title2}" class="auto-submit-star" type="radio" name="star${status.index}" value="2" <c:if test="${satisfaction.evaluationScore3 == 2}">checked="checked"</c:if> />
								
								<input title="${evaluation.title3}" class="auto-submit-star" type="radio" name="star${status.index}" value="3" <c:if test="${satisfaction.evaluationScore3 ==null||satisfaction.evaluationScore3 == 3}">checked="checked"</c:if> />
								
								<input title="${evaluation.title4}" class="auto-submit-star" type="radio" name="star${status.index}" value="4" <c:if test="${satisfaction.evaluationScore3 == 4}">checked="checked"</c:if> />
								
								<input title="${evaluation.title5}" class="auto-submit-star" type="radio" name="star${status.index}" value="5" <c:if test="${satisfaction.evaluationScore3 == 5}">checked="checked"</c:if> />
							 </c:if>                 
						 </div>
				</td>
			    </tr>
			    </c:forEach>
			    <tr>
			    <td align="right" style="vertical-align:middle;"><strong><comm:message key='unified.suggestion'/></strong>:&nbsp;<div id="hint" style="margin-right: 10px;">(<span id="tip"></span>/500)</div></td>
			    <td style="vertical-align:middle;"><textarea name="suggestion" style="border:0px;" rows="5" id="suggestion" cols="40" maxlength="500" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>500">${satisfaction.suggestion}</textarea></td>
			    </tr>	
			    </table>
			     <table align="center" id="shenqingBut">
				    <tr>
				    <td>
				    <input type="hidden" value="${meeting.id}" name="meetingid" id="meetingid"/>
				    <input type="button" class="button" value="<comm:message key='unified.sure'/>" id="mainSubmit"/>
				    </td>
				    </tr>
			    </table>            
            </div>
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>
          </div>  

    </form>
  </body>
</html>
