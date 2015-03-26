<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/jqueryui/css/jquery-ui-1.8.16.custom.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.core.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.widget.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.position.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.autocomplete.min.js"></script>
	<script type="text/javascript"> 		
		/* $(function(){
			$('#autoUserName').autocomplete({
				source		: '${root}/meeadIndex/searchUser',
				minLength	: 2,
				scroll		: true,
				select		: function(event, ui) {
					$('#autoUserName').val(ui.item.label);
					//$('#user_name').val(ui.item.label);
					return false;
				}
			});
		});		 */
	</script>
	<style type="text/css">
	.ui-autocomplete {
		max-height: 300px;
		overflow-y: auto;
		width: 145px;
	}
    </style>
</head>
<body style="padding:0; margin:0;background:url('<%=request.getContextPath()%>/resources/images/theme/bg_right-2.gif');" onload="document.getElementById('Report').style.height = (document.body.clientHeight - 64) + 'px';"onresize="document.getElementById('Report').style.height = (document.body.clientHeight - 64) + 'px';">
	<div>
		<form name="MyForm" action="${root}/user/queryUserList" method="get" onsubmit="return check()" target="Report">
		<table align="center" border="0" cellspacing="0" cellpadding="0" width="500" >
			<tr id="inputForm" style="display:block;">
				<td width="1">&nbsp;</td>
				<td class='queryBackground' align="right" width="1">&nbsp;</td>
				<td align="center">
					<table align="center" class='queryBackground' width="498" border="0" cellspacing="0" cellpadding="0">												
						<tr>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="admin.logon_name"/>:&nbsp;&nbsp;
						 	</td>						 	
						 	<td width="30%" align="left">
								<input type="text" name="logon_name" maxlength="50" size="30" style="width:140px;height:20px;line-height:20px;"/>
			             	</td>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="admin.user_name"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
			              		<input type="text" id="autoUserName" name="user_name" maxlength="50" size="30" style="width:140px;height:20px;line-height:20px;"/>
			             	</td>
						</tr>
					</table>
				</td>
				<td width="1" align="left">&nbsp;</td>
			</tr>
		</table>
		<table align="center" width="500">
			<tr>
			  <td height="30" align="center" valign="bottom" class='queryBackground'>
				<table class='buttonToolBar' id="querybuttonBk"  align="center" >
				  <tr>
					<td><input type="submit" name="new"  align="center"  value=" <comm:message key="comm.view"/>" class="button" >&nbsp;&nbsp;&nbsp;<img id="img1" src="${root}/resources/skin/style/Button_top2.gif" style="cursor:hand;" border="0" onclick="javascript:hideOrDisplayInputForm(inputForm, img1)" alt="<comm:message key='comm.hidden_input_parameter'/>"></td>
				  </tr>
				</table>
			  </td>
			</tr>  
			<tr >
				<td class="table_middle_bg"><iframe id="Report" align="top" frameborder="0" name="Report" width="500" scrolling="auto" src="${root}/user/list"></iframe></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
