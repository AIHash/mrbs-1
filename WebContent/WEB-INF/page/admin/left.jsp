<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@page pageEncoding="utf-8"%>
<html>
<head>
	<title><comm:message key="system.system_name" /></title>
	<link href="<%=request.getContextPath()%>/resources/skin/login/login.css" rel="StyleSheet" type="text/css" />
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript">
function MM_swapImgRestore() { //v3.0
	var i, x, a = document.MM_sr;
	for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
		x.src = x.oSrc;
}
function MM_preloadImages() { //v3.0
	var d = document;
	if (d.images) {
		if (!d.MM_p)
			d.MM_p = new Array();
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
		for (i = 0; i < a.length; i++)
			if (a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image;
				d.MM_p[j++].src = a[i];
			}
	}
}
function MM_findObj(n, d) { //v4.01
	var p, i, x;
	if (!d)
		d = document;
	if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
		d = parent.frames[n.substring(p + 1)].document;
		n = n.substring(0, p);
	}
	if (!(x = d[n]) && d.all)
		x = d.all[n];
	for (i = 0; !x && i < d.forms.length; i++)
		x = d.forms[i][n];
	for (i = 0; !x && d.layers && i < d.layers.length; i++)
		x = MM_findObj(n, d.layers[i].document);
	if (!x && d.getElementById)
		x = d.getElementById(n);
	return x;
}
function MM_swapImage() { //v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
	document.MM_sr = new Array;
	for (i = 0; i < (a.length - 2); i += 3)
		if ((x = MM_findObj(a[i])) != null) {
			document.MM_sr[j++] = x;
			if (!x.oSrc)
				x.oSrc = x.src;
			x.src = a[i + 2];
		}
}
function findSubMenu(id){
	for ( var i = 0; i < document.getElementsByTagName("ul").length; i++) {
		var obj = document.getElementsByTagName("ul")[i];
		//if(obj.id != ""){
		//	obj.id == id ? obj.style.display = 'block' : obj.style.display = 'none';
		//}

		
		var visible = ( obj.style.display != 'none');
		if (obj.id != ""&&obj.id == id) {
			if(visible){
				obj.style.display="none";
			}else {
				obj.style.display="block";
			}
		} else if(obj.id != ""){
			obj.style.display="none";
		}
	}
}
function test(obj,nodeNo){
	var arr = document.getElementsByName("twoTitle");
	for(var i=0; i<arr.length; i++) {
		if (arr[i] != obj){
			arr[i].style.background="url('${pageContext.request.contextPath }/resources/images/theme/two_no_light.gif')";		  
		}
	}
	document.getElementById("hightLightFlag").value=nodeNo;
	obj.style.background="url('${pageContext.request.contextPath }/resources/images/theme/two_light.gif')";
};
function testover(obj){
	obj.style.background = "url('${pageContext.request.contextPath }/resources/images/theme/two_light.gif')";
}; 
function testout(obj,nodeNo){
	var hightLightFlag = document.getElementById("hightLightFlag").value;
	if(nodeNo != hightLightFlag){
		obj.style.background = "url('${pageContext.request.contextPath }/resources/images/theme/two_no_light.gif')";
	} 
};

 function setAllout(){
	document.getElementById("hightLightFlag").value = "";
	var arr = document.getElementsByName("twoTitle");
	for(var i=0; i<arr.length; i++) {
		    arr[i].style.background="url('${pageContext.request.contextPath }/resources/images/theme/two_no_light.gif')";
			arr[i].parentNode.parentNode.style.display="none";//修改bug5777添加的，完成菜单切换显示建议
		}
}; 
</script>
</head>
<body style="margin: 0; background:url('../resources/images/theme/left01-2.gif');">
	<div id="meun_u">
		<input type="hidden" id="hightLightFlag" value="">
		<div id="mm">
			<div class="menu">
				<ul class="ul">
				<c:forEach items="${sessionScope.modules}" var="firstMenu" varStatus="status">
					<c:if test="${fn:length(firstMenu.url) == 0 && fn:length(firstMenu.nodeNo) == 3}">
						<li class="item">
							<a class="title" name="totalUlName" onmouseout="MM_swapImgRestore()" onfocus="this.blur()" onclick="findSubMenu('opt_${status.index}')"
							onmouseover="MM_swapImage('first_${status.index}','','${pageContext.request.contextPath }/resources/images/theme/${firstMenu.swapUrl}',1)">
								<img src="${pageContext.request.contextPath }/resources/images/theme/${firstMenu.imageUrl}" name="Image15" width="154" height="39" border="0" id="first_${status.index}" />
							</a>
							<ul class="submenu" id="opt_${status.index}" style="margin: 0; padding: 0; padding-top: 0px; color: #fff; display: none;">
							 	<c:forEach var="submenu" items = "${sessionScope.modules}" varStatus="subSta">
							 		<c:if test="${submenu.parentNodeNo == firstMenu.nodeNo}">
										<li class="item" style="line-height:35px;background:url('${pageContext.request.contextPath}/resources/images/theme/${submenu.imageUrl}') no-repeat;display:block;cursor:hand;width:154px;height:35px;padding:0px;margin-top:0px;">
										<a class="twoTitle" name="twoTitle" onmouseover="testover(this);" onmouseout="testout(this,'${submenu.nodeNo}');" onclick="test(this,'${submenu.nodeNo}');" style="text-decoration:none;color:#fff;font-family:'微软雅黑','黑体';font-size:12px;line-height:35px;display:block;cursor:hand;width:154px;height:35px;"
											<c:choose>
												<c:when test="${fn:startsWith(fn:trim(submenu.url), 'http://') && fn:contains(fn:trim(submenu.url),'haina_url')}">
													href="${fn:replace(fn:replace(fn:replace(submenu.url, 'userId', USER_LOGIN_SESSION.userId), 'user_name', encodeName), 'haina_url', HAINA_URL)}" target="_blank"
												</c:when>
												<c:when test="${fn:startsWith(fn:trim(submenu.url), 'http://') && !fn:contains(fn:trim(submenu.url),'haina_url')}">
													href="${submenu.url}" target="_blank"
												</c:when>
												<c:otherwise>
													href="${pageContext.request.contextPath}${submenu.url}" target="WorkBench"
												</c:otherwise>
											</c:choose>
										onfocus="this.blur()" onmouseout="MM_swapImgRestore()">
											<%-- <img src="${pageContext.request.contextPath}/resources/images/theme/${submenu.imageUrl}" name="Image15" width="154" height="35" border="0" id="sub_${subSta.index}" /> --%>
										<span style="margin-left:50px;">${submenu.name}</span>
										</a>
										</li>
									</c:if>
								</c:forEach><!-- href="${submenu.url}" target="_blank" -->
							</ul>
						</li>
					</c:if>
				</c:forEach>
				<c:if test="${user.userType.value == 4 || user.userType.value == 5}">
					<li class="item" style="margin-top:12px;">
					    <a href="<%=request.getContextPath()%>/unified/application" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/an_yuancheng.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>
					<li class="item" style="margin-top:5px;">
					 	<a href="<%=request.getContextPath()%>/unified/applicationVideo" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/sqspjz.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>
 
					 <li class="item" style="margin-top:5px;">
					 	<a href="<%=request.getContextPath()%>/unified/icuMonit" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/sqzzjh.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>  
					 <li class="item" style="margin-top:5px;">
					 	<a href="<%=request.getContextPath()%>/unified/icuVisit" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/sqycts.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>  
				</c:if>
				<c:if test="${user.userType.value == 2}">
					<li class="item" style="margin-top:12px;">
						<a href="<%=request.getContextPath()%>/meeadmdbd/applicationVideo" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/sqspjz.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>
					<li class="item" style="margin-top:5px;">
						<a href="<%=request.getContextPath()%>/meeadIndex/managementOfattachment" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/fjgl.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>
					<li class="item" style="margin-top:5px;">
					 	<a href="<%=request.getContextPath()%>/icumonitor/addIcuMonitForAdmin" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/sqzzjh.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>  
					<li class="item" style="margin-top:5px;">
					 	<a href="<%=request.getContextPath()%>/meeadmdbd/addmanagerOfIcuVisitView" onfocus="this.blur()" target="WorkBench"><img src="<%=request.getContextPath()%>/resources/images/theme/sqycts.png" onclick="setAllout();" width="150" height="35" border=0/></a>
					</li>
				</c:if>
				</ul>
			</div>
		</div>
	</div>
	<!-- left execute over set haina URL -->
	<c:set var="HAINA_URL" scope="session" value="http://${HAINA_URL}"/>
</body>
</html>