<%@ page isELIgnored="false" pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/skin/style/main.css"/>
	<link rel="icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
 	<link rel="shortcut icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
 	<link rel="bookmark" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="${pageContext.request.contextPath}/resources/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/fancybox/jquery.fancybox-1.3.4.js"></script>
	<script type="text/javascript"> 
		function popWindow(){
			$(".various3", document.getElementById('WorkBench').contentWindow.document.getElementById('dataFrame').contentWindow.document).fancybox({
				'width'				: '100%',
				'height'			: '100%',
				'autoScale'			: true,
                'transitionIn'  	: 'elastic',
                'transitionOut' 	: 'none',
//				'padding'			: '50px',
				hideOnOverlayClick	: false,
				'type'				: 'iframe'
			});
		}
		function exitsys(){
			jConfirm("<comm:message key='admin.exit_sure'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>", function(select){
				if(select)
					parent.location.href="${root}/login/exit";
			});
		}
	</script>
</head>
<body style="margin: 0px; padding: 0px;">
<table width="100%" cellpadding="0" height="100%" cellspacing="0" align="top">
	<tr style="width:922px;">
		<td width="172" valign="top"><iframe name="leftFrame" id="leftFrame" src="${pageContext.request.contextPath}/index/left" frameborder="0" width="100%" onload="this.height=getheight();"></iframe></td>
		<td valign="top"><iframe id="WorkBench" name="WorkBench" src="${pageContext.request.contextPath}/index/right" onload="this.height=getheight();" frameborder="0" width="100%"></iframe></td>
	</tr>
</table>
<script type="text/javascript"> 
	function getheight(){
		var h = (window.innerHeight) ? window.innerHeight : (document.documentElement && document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.body.offsetHeight;    
	    return h;
	}

	document.body.onresize = function(){
		var height = getheight();
		document.getElementById("leftFrame").height=height;
		document.getElementById("WorkBench").height=height;
	};
</script>
</body>
</html>