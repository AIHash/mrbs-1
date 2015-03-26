<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <comm:pageHeader hasEcList="false"></comm:pageHeader>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/style/jrating/documentation.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/jrating/jquery.rating.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.rating.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.MetaData.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>	
<script type="text/javascript">
$(function(){
	$('#submitaa').click(function(){
	var expertSummary=document.getElementById("expertSummary").value;
	if(expertSummary==""||expertSummary==null)
	 {
		  alert("<comm:message key='report.notNull'/>");
		  return false;
	 }
     $.post('<%=request.getContextPath()%>/summary/summaryData',
    		 {meetingId: $('#meetingId').val(),expertSummary:$('#expertSummary').val(),communitySummary:$('#communitySummary').val()},
	  function(text){	 
		  if(text == 'success'){
			  parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href;
			  parent.$.fancybox.close();
		  } else if(text.indexOf('<HTML>') != -1){
				window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
			}
     }, 'text');
	 });
});       
</script>
<style type="text/css">
#center{background-color:#ffffff;border:0px solid red;width:90%;align:center;position:relative;margin-left:10px;}
#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;line-height:30px;margin-bottom:0px;margin-left:30px;margin-right:30px;}
#viewappinfor{border:0px solid red;width:95%;align:center;margin-left:18px;margin-top:15px;margin-bottom:5px;margin-right:18px;position:relative;background-color:#dee;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:25px;margin-right:25px;}
</style>

</head>
<body style="overflow-y:auto;">
<form id="form" method="post" action="<%=request.getContextPath()%>/summary/summaryData">
		  <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
			     <table width="90%" border="0" align="center">
			        <tr><td>&nbsp;</td></tr>
			    </table>    
			     <table id="shenqing" align="center" height="345"  border="1" class="submit_table" >
			          <tr>
			             <td align="center" colspan="2" style="text-align:center; vertical-align:middle;"><strong><comm:message key='report.meetingSummary'/></strong>
			             <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}">
			             </td>
			          
			          </tr>
			
			          <tr>
				         <td width="20%" align="right" style="vertical-align:middle;"><strong><comm:message key='report.unifiedSummary'/>：</strong></td>	
				         <td style="vertical-align:middle;"><textarea name="communitySummary" id="communitySummary" style="width:100%;border: 1px;" rows="5" class="text-long" width="80%" onfocus="this.blur()">${summary.communitySummary}</textarea>
				         </td>
			          </tr>
			          <tr>
				         <td width="20%" align="right" style="vertical-align:middle;"><strong><comm:message key='report.expertSummary'/>：</strong><span class="required">*</span><div id="hint" style="margin-right: 10px;">(<span id="tip"></span>/500)</div></td>	         
				         <td style="vertical-align:middle;"><textarea name="expertSummary" id="expertSummary" style="width:100%; border: 1px;" rows="5" class="text-long" maxlength="500" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>500">${summary.expertSummary}</textarea>
				         </td>
			          </tr>
			     </table>
			     <table align="center">
			        <tr>
			           <td>
			              <input id="submitaa" class="button" name="submitaa" type="button" align="center" value="提交">
			           </td>
			       </tr>
			       <tr><td>&nbsp;</td></tr>
			    </table>            
            </div>
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>
          </div>  
</form>
</body>
<script language="javascript"> 
$(document).ready(function() {
	var count=getUnicodeLength(document.getElementById("expertSummary").value);
   	$('#tip').html(500-count);
});
</script>
</html>