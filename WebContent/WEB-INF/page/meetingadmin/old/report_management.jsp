<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title><comm:message key="system.system_name" /></title>
	<comm:pageHeader />
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/comm.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".various3").fancybox({
				'width'				: '75%',
				'height'			: '75%',
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'padding'			: '5px',
				'type'				: 'iframe'
			});

			$('#preview').click(function(){
				var action = '${pageContext.request.contextPath}/';
				var startDate = $.trim($('#startDate').val());
				var endDate = $.trim($('#endDate').val());
				var category = $.trim($('#category').val());
				if(startDate == null || startDate == ""){
					alert('请选择开始时间');
					return;
				}
				if(endDate == null || endDate == ""){
					alert('请选择结束时间');
					return;
				}
				if($('#type').val() == 1){
					action += 'summary/report?type=preview';
				}else{
					action += 'detail/report?type=preview';
				}
				action += '&category=' + category + '&startDate=' + startDate + '&endDate=' + endDate;
			//	$('#dataFrame').attr('src', action);
			
				$.fancybox({
					'width'				: '75%',
					'height'            : '100%',
					'padding'			: '5px',
					'centerOnScroll'	: true,
					'autoScale'			: true,
					'transitionIn'		: 'none',
					'transitionOut'		: 'none',
					'href'				: action,
					'type'				: 'iframe',
					'hideOnOverlayClick': false
				});
				
			});

			$('#refusereasomform').submit(function(){
				var action = '${pageContext.request.contextPath}/';
				if($.trim($('#startDate').val()) == null || $.trim($('#startDate').val()) == ""){
					alert('请选择开始时间');
					return false;
				}

				if($.trim($('#endDate').val()) == null || $.trim($('#endDate').val()) == ""){
					alert('请选择结束时间');
					return false;
				}
				if($('#type').val() == 1){
					action += 'summary/report';
				}else{
					action += 'detail/report';
				}
				$(this).attr('action', action);
			});
		});
	</script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" class="bg">
<div id="head" class="c">
	<div id="user">
    	<div id="userinfo"> 
    	${USER_LOGIN_SESSION.name}
    	<br />[<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/getUserInfo" class='various3'><comm:message key='meeting.m.editprofile'/></a>]&nbsp;&nbsp;[<a href="javascript:exitsys()"><img src="<%=request.getContextPath()%>/resources/skin/style/exit.gif" border="0"/>&nbsp;<comm:message key="admin.Exit" /></a>]</div>
    </div>
	<div id="navigation">
		<ul>
        	<li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/listmeetingaplication"><comm:message key='meeting.m.homepage'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingappsearchlist"><comm:message key='meeting.m.myaccraditation'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingsearchlist"><comm:message key='meeting.m.consultation'/></a></li>
        	<li class="active"><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/reportManagement">报表管理</a></li>
        </ul>
    </div>
</div>

<div id="main" class="c" style="overflow: hidden;">
 <div id="class-search" class="c">
  <form id="refusereasomform" name="refusereasomform" method="post">
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
			<tr>
				<td>报表类型</td>
				<td><select name="category" id="category" style="width: 150px">
					<option value="1">会诊类型</option>
					<option value="2">科室分类</option>
					<option value="3">专家分类</option>
					</select>
				</td>
				<td><select id='type' style='width: 150px'>
						<option value="1">汇总</option>
						<option value="2">明细</option>
					</select>
					
				</td>
				<td><comm:message key='meeting.m.starttime'/></td>
				<td>
					<input type="text" id="startDate" name="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" size="25"/>
				</td>
				<td><comm:message key='meeting.m.endtime'/></td>
				<td>
					<input type="text" id="endDate" name="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" size="25"/>
				</td>
			</tr>
			<tr>
				<td align="right" colspan="8" >
					<input class="button" type="button" name="button" id="preview" value="预览" />
					<input class="button" type="submit" name="button" id="test" value="导出" />
				</td>
			</tr>
		</table>
	</form>
  </div>

  <div id="class-table" class="c">

	<div align="center" style="height: 200px;">
<!--
		<iframe id="dataFrame" width="80%" style="border: 0;height: auto;" scrolling="no"></iframe>
-->	
	</div>

</div>
</div>
<div id="footer" class="c">
	<div id="copyright" class="c"><comm:message key='system.software_copy'/></div>
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