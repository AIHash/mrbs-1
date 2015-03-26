<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8" %>
<%@include file="/common.jsp" %>
<html>

<head>
	<title><comm:message key="system.system_name" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
	function exitsys(){
		parent.window.document.getElementById('mainFrames').contentWindow.exitsys();
	}
	</script>
</head>
<body style="OVERFLOW:HIDDEN;">
<div class="id_12">
	<div id="id1">
		<img src="${pageContext.request.contextPath}/resources/images/theme/logo_01.gif" width="624" height="82" />
	</div>
	<div id="zhong">
		<p style="color:#ffffff;margin-top:10px;font-size:11px;">
			<div style="float: left;margin: 0px;padding: 0px;width: 80%;">
				<marquee scrollamount="2" id="marqueeId" onmouseover="this.scrollAmount=0" behavior="scroll" onmouseout="this.scrollAmount=speed;" width="70%">
					<font color="white" style="font-size: 12px">欢迎您&nbsp;:&nbsp;${userName}</font>
				</marquee>
			</div>
			<div style="float: right;margin: 0px;padding: 0px;"><a style="color:#ffffff;font-size: 12px;text-decoration: none;" href="javascript:exitsys()">[退出]</a></div>
			<script type="text/javascript">
				var speed=2; //speed of scroller (1-10 or more)
				var loops=-1; 
			</script>
		</p>
	</div>
	<div id="id2" style="background: url('${pageContext.request.contextPath}/resources/images/theme/top_r.gif') no-repeat;"></div>
	</div>
</body>
</html>