<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />							
		<link href="<%=request.getContextPath()%>/resources/style/taobaorate/rate_center.css" rel="StyleSheet" type="text/css"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		<script type="text/javascript">
$(function(){
	
	$('.item-scrib').hover(function(){   
		    var i = $('#index_'+this.id).val();
			$(this).siblings('#box_'+this.id).css({
				"top": (i*43)+ "px",
				"left": "370px"
			}).toggle();  
	}).hover();
})
</script>
  </head>
    <body class="W950">
        <div id="page" class="w950" >
<div id="shop-rate-box" class="shop-rate-box" style="background-color: #FFFFFF;">       
    <div class="clearfix personal-info personal-info-fullwidth">
    <div class="personal-rating" id="personal-rating">
               
  
<div class="con" id="dynamic-rate">
    <h4 class="rate-icon rate-icon-shop">会议评分:</h4>
    <div class="seller-rate-info" id="sixmonth">
		<ul>
		<c:forEach var="evaluationrated" items="${evalratelist}" varStatus="status">
		 <input type="hidden" value="${status.index}" name="index" id="index_row_${status.index}"/>
			<li class="J_RateInfoTrigger">
				<div class="item-scrib" id="row_${status.index}">
					<span class="title">${evaluationrated.evaluationName}:&nbsp;&nbsp;</span>
					<em title="${evaluationrated.evaluationRate}分" class="count">${evaluationrated.evaluationRate}</em>分 
											<em title="计算规则:(会议评价得分)/(会议评价平均分)">
											<c:if test="${evaluationrated.high}">
											<strong class="percent over">${evaluationrated.evaluationRatePercent}%</strong>
											</c:if>
											<c:if test="${!evaluationrated.high}">
											<strong class="percent lower">${evaluationrated.evaluationRatePercent}%</strong>
											</c:if>
											</em>
									</div>
					<div class="box rate-info-box" id="box_row_${status.index}">
				
					<div class="bd">
						<div class="total" >
							<em title="${evaluationrated.evaluationRate}分" class="h">总平均分为:${evaluationrated.evaluationRate}</em>分
							<span class='star-value-no  ${evaluationrated.starScore}'></span>共<span>${evaluationrated.evaluationRateAllMN}</span>次会议
						</div>
						<div class="count count5">
							<span class="no5">5</span> <span class="unit">分</span>
														<span style="width: ${evaluationrated.evaluationRate5MNPercent}px;" class="rate-stat"></span>
							<em class="h">占${evaluationrated.evaluationRate5MNPercent}%</em>
							<span class="people-no">(共${evaluationrated.evaluationRate5MN}次会议)</span>
													</div>
						<div class="count count4">
							<span class="no4">4</span> <span class="unit">分</span>
														<span style="width: ${evaluationrated.evaluationRate4MNPercent}px;" class="rate-stat"></span>
							<em class="h">占${evaluationrated.evaluationRate4MNPercent}%</em>
							<span class="people-no">(共${evaluationrated.evaluationRate4MN}次会议)</span>
													</div>
						<div class="count count3">
							<span class="no3">3</span> <span class="unit">分</span>
														<span style="width: ${evaluationrated.evaluationRate3MNPercent}px;" class="rate-stat"></span>
							<em class="h">占${evaluationrated.evaluationRate3MNPercent}%</em>
							<span class="people-no">(共${evaluationrated.evaluationRate3MN}次会议)</span>
													</div>
						<div class="count count2">
							<span class="no3">2</span> <span class="unit">分</span>
														<span style="width: ${evaluationrated.evaluationRate2MNPercent}px;" class="rate-stat"></span>
							<em class="h">占${evaluationrated.evaluationRate2MNPercent}%</em>
							<span class="people-no">(共${evaluationrated.evaluationRate2MN}次会议)</span>
													</div>
						<div class="count count1">
							<span class="no1">1</span> <span class="unit">分</span>
														<span style="width: ${evaluationrated.evaluationRate1MNPercent}px;" class="rate-stat"></span>
							<em class="h">占${evaluationrated.evaluationRate1MNPercent}%</em>
							<span class="people-no">(共${evaluationrated.evaluationRate1MN}次会议)</span>
													</div>
					</div>
				</div>
			</li>
    </c:forEach>
		</ul>
	</div>
  </div>
 </div>
  </div>
    </div>
</div>


    </body>		
			
		
</html>