<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@include file="/common.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><comm:message key="system.system_name" /></title>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link rel="icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
 	<link rel="shortcut icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
 	<link rel="bookmark" href="<%=request.getContextPath()%>/resources/images/favicon.ico" type="image/x-icon" /> 
</head>
	<frameset cols="*,922,*"framespacing="0" frameborder="no" border="0">
		<frame id="leftBlank" name="leftBlank"  frameborder="no" scrolling="no" src="${pageContext.request.contextPath}/index/blank"></frame>
		<frameset rows="82,*" framespacing="0" frameborder="no" >
			<frame id="titleFrame" style="width:922px;" name="titleFrame" scrolling="no"  src="<%=request.getContextPath()%>/index/title"/>
			<frame id="mainFrames" name="mainFrames" src="<%=request.getContextPath()%>/index/mainFrames" scrolling="no" noresize/>
<!--
			<frameset id="mainFrameSet" style="width:922px;" name="mainFrameSet" rows="*" cols="172,*" framespacing="0" frameborder="no" border="0">
				<frame name="leftFrame" src="<%=request.getContextPath()%>/index/left" noresize scrolling="no"/>
				<frame id="mainFrame" name="WorkBench" src="<%=request.getContextPath()%>/index/right" scrolling="no"/>
			</frameset>
-->
		</frameset>
		<frame id="rightBlank" name="rightBlank" scrolling="no" src="${pageContext.request.contextPath}/index/blank"></frame>
	</frameset>
</html>