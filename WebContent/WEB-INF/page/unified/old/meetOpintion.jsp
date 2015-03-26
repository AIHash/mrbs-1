<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
  <head>
    <base href="<%=basePath%>">
    <title><comm:message key='unified.meetingopintion'/></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/style/jrating/documentation.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/jrating/jquery.rating.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.rating.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jrating/jquery.MetaData.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<style type="text/css">
		.exemple{margin-left:10px;}
		.auto-submit-star{margin:2px;}
		.main-submit-star{margin:2px;}
	</style>
	<script type="text/javascript">
	$(function(){
		 $('.auto-submit-star').rating({
		  callback: function(value, link){
		  // alert( $(this).attr('name') );
		   //var name=$(this).attr('name');
		   //alert(value);
		   var meetingid = $.trim($('#meetingid').val());
		   $.post('${pageContext.request.contextPath}/unified/addopintion',{evalvalue : value, meetingid :meetingid }, function(text){
			  //if(text  == 'success')
				  //alert('评价成功');
		   }, 'text');
		  }
		 });
		 $('.main-submit-star').rating({
			  callback: function(value, link){
			   $('#evalvalue').val(value);
			  }
			 });

		 $('#mainSubmit').click(function(){
			 $.post('<%=request.getContextPath()%>/unified/addmainopintion',
					 {evalvalue :$('#evalvalue').val(), meetingid : $('#meetingid').val(), suggestion : $('#suggestion').val()},
				function(text){
					if(text == 'success'){
						window.parent.location.href = window.parent.location.href;

					} 
			 }, 'text');
		 });
		});
	</script>
  </head>  
  <body id="windows">
  	<div>
		<img src="${pageContext.request.contextPath}/resources/images/unified/logo.jpg"/>
		<div style="height: 10px;"></div>
	</div>
  <form name="form1" action="<%=request.getContextPath()%>/unified/addmainopintion" method="post">

    <table id="shenqing" class="submit_table" align="center" width="100%" border="1">
    <tr>
    <td align="center" colspan="2"><strong><comm:message key='unified.meetingopintion'/></strong></td>
    </tr>
    <tr>
    <td align="right"><strong><comm:message key='unified.title'/>：</strong></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${meeting.title}</td>
    </tr>
     <tr>
    <td align="right"><strong>会议总体评价：</strong></td>
    
    <td>
               <div id="set1" class="exemple">
               <input type="hidden" value="3" name="evalvalue" id="evalvalue">
						<input title="1分 很不满意 ;对会议很失望，感觉很差" class="main-submit-star" type="radio" name="mainstar" value="1" />
						
						<input title="2分 不满意 ;对会议失望，感觉不太好" class="main-submit-star" type="radio" name="mainstar" value="2" />
						
						<input title="3分 一般 ;对会议觉得一般，感觉很行" class="main-submit-star" type="radio" name="mainstar" value="3" checked="checked" />
					
						<input title="4分 还好 ;对会议挺满意，感觉挺好" class="main-submit-star" type="radio" name="mainstar" value="4"/>
						
						<input title="5分 很好 ;对会议很满意，感觉很好" class="main-submit-star" type="radio" name="mainstar" value="5" />
			 </div>
					</td>
					
    </tr>
	<tr>
    <td align="right"><strong>建议：</strong></td>
    <td><textarea name="suggestion" rows="3"  id="suggestion" cols="100"></textarea></td>
    </tr>
    <c:forEach var="evaluation" items="${evallist}" varStatus="status">
    <tr>
    <td align="right"><strong>${evaluation.name}：</strong></td>
       <td>
			<div id="set_${status.index}" class="exemple">                             
						<input title="${evaluation.title1}" class="auto-submit-star" type="radio" name="star${status.index}" value="${evaluation.id}_1"  />
						 
						<input title="${evaluation.title2}" class="auto-submit-star" type="radio" name="star${status.index}" value="${evaluation.id}_2" />
						
						<input title="${evaluation.title3}" class="auto-submit-star" type="radio" name="star${status.index}" value="${evaluation.id}_3" checked="checked" />
						
						<input title="${evaluation.title4}" class="auto-submit-star" type="radio" name="star${status.index}" value="${evaluation.id}_4" />
						
						<input title="${evaluation.title5}" class="auto-submit-star" type="radio" name="star${status.index}" value="${evaluation.id}_5" />
			 </div>
	</td>
    </tr>
    </c:forEach>
    	
    </table>
     <table align="center">
    <tr>
    <td>
    <input type="hidden" value="${meeting.id}" name="meetingid" id="meetingid">
    <input type="button" value="<comm:message key='unified.sure'/>" id="mainSubmit">
    </tr>
    </table>
    </form>
  </body>
  <div style="margin:auto; width:100%; text-align:center; padding:10px 0;"></div>
<script language="javascript"> 
//table隔行换色
function tr_color(o,a,b,c,d){
 var t=document.getElementById(o).getElementsByTagName("tr");
 for(var i=0;i<t.length;i++){
  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
  t[i].onclick=function(){
   if(this.x!="1"){
    this.x="1";
    this.style.backgroundColor=d;
   }else{
    this.x="0";
    this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
   }
  }
  t[i].onmouseover=function(){
   if(this.x!="1")this.style.backgroundColor=c;
  }
  t[i].onmouseout=function(){
   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
  }
 }
}

//tr_color("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
tr_color("shenqing","#fafaff","#fafaff","#ecfbd4","#e7e7e7");
</script>
</html>
