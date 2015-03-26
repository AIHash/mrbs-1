<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>

<html>
<head>
	<title><comm:message key="admin.department_manage" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
    <link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<script type="text/javascript">
function check()
{
	if ((strTrim(form.deptName.value)) == "")
	{
	   jAlert("<comm:message key='js.need_input' args='admin.department_name'/>", "<comm:message key='meeting.m.infotishi'/>");
	   form.deptName.focus();
	   return false;
	}
	if(!checkbadwords(form.deptName.value,'#&'))
	{
		jAlert("<comm:message key='js.cannot_input' args="admin.department_name,'#&'"/>", "<comm:message key='meeting.m.infotishi'/>");
		form.deptName.focus();
		return false;

	}
	return true;
}
	$(function(){
		$("#submitDept").click(function(){
			var flag=check();
			if(flag){
				$.post('${pageContext.request.contextPath}/dept/create',{deptName:$("#deptname").val(),deptRemark:$("#deptremark").val()}, function(msg){
					if(msg == "adddept_successful"){
						jAlert("<comm:message key='admin.department_add_success'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
							if(resultConfirm){
								parent.location.href="<%=request.getContextPath()%>/dept/mainForDelete";
							}
						});
					}else if(msg == "adddept_error"){
						jAlert("<comm:message key='admin.department_add_repetition'/>", "<comm:message key='meeting.m.infotishi'/>");
					}else{
						jAlert("<comm:message key='admin.department_add_fail'/>", "<comm:message key='meeting.m.infotishi'/>");
					}	 
				});				
			}else{
				return false;
			}

		});		
	});
	
	function cancelAddDept(){
		parent.location.href="<%=request.getContextPath()%>/dept/mainForDelete";
	}
</script>
<style type="text/css">
#depttable tr td input{margin-left:3px;}
#depttable tr td textarea{margin-left:3px;}
</style>
</head>


<body leftmargin="5" topmargin="0" >
	<form name="form" id="addDeptForm" onsubmit="return check()">
			  <div id="viewappinfor">
				<div id="titleStyle">
					<span>&nbsp;</span>
				</div>		
	            <div class="subinfor" id="patientinfor1">
					<table align="center" id="depttable" width="400px" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="7" colspan="2" align="right"></td>
						</tr>
						<tr>
							<td width="20%" align="right"><comm:message key="admin.department_name"/>:<span class="required">*</span></td>
							<td width="80%" align="left"><input type="text" name="deptName" id="deptname" maxlength="50" size="30" style="width:80%;height:20px;line-height:20px;"></td>
						</tr>
						<tr>
							<td width="20%" align="right"><comm:message key="unified.and.expert_comm.remark"/>:&nbsp;<div id="hint">(500/500)&nbsp;</div></td>
							<td colspan=3 width="80%"  align="left"><textarea name="deptRemark" id="deptremark" style="border: 1px solid #7F9DB9;width:80%;height:120px;overflow-y:auto;" maxlength="500" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>500"></textarea></td>
						</tr>
						<tr> 
							<td height="5" colspan="2"></td>
						</tr>
						<tr>
						  <td colspan="4"><div align="center"><input type="button" id="submitDept" name="new" value=" <comm:message key="comm.save"/>" class="button" >&nbsp;<input type="button" name="new" value=" <comm:message key="comm.cancel"/>" class="button" onClick="cancelAddDept();"></div></td>
						</tr>
						<tr>
							<td height="7" colspan="2" align="right"></td>
						</tr>
					</table>	            
	            </div>
				<div id="titleStyle2">
					<span>&nbsp;</span>
				</div>	            
	          </div>  	

	</form>
</body>
<script type="text/javascript">
	parent.document.getElementById('dataFrame').style.height = document.body.scrollHeight;
</script>
</html>