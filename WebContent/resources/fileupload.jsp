<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<html>
  <head>
    <title>fileupload</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>

  <script type="text/javascript">
  function checkvalue(){
	  var explainContent = $('#explainContent').val();
	  if(explainContent == 8){
		  var shoudong = $('#shoudong').val();
		  if(shoudong == ""){
			  alert("<comm:message key='unified.need.shoudong.explain'/>");
	    	  return false;
		  }else{
			  var sdsize = Math.floor(shoudong.length%2);
			  if(sdsize == 1){
				  $("#shoudong").val("  " + shoudong);
			  }
		  }
	  }
	  var fileobj=document.getElementById("fileid");
	  var value=fileobj.value;
	  if(value&&value!="")
	  {		  		 
		  return true;
      }else{
    	     alert("<comm:message key='unified.need.file'/>");
    	     return false;
      }
	  if(explain&&explain!="")
	  {		  		 
		  return true;
      }else{
    	     alert("<comm:message key='unified.need.explain'/>");
    	     return false;
      }
  }

	$(function() {
		changeExplain();
		$("#explainContent").change( function() {
			changeExplain();
		});
	});
	function changeExplain(){
		var explain = $('#explainContent').val();
		if(explain == 8){
			$("#shoudong").show();
		}else{
			$("#shoudong").hide();
		}
	};
  </script>
  </head>
  <body>
    <form action="<%=request.getContextPath()%>/upload"  method="post"  ENCTYPE="multipart/form-data" onsubmit="return checkvalue()">
    <table width="100%" align="center" border="0px;">
     <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
    <tr>
    <td align="right" width="30%"><comm:message key='unified.select.file'/>:</td>
    <td align="left"><input id="fileid" name="filename" type="file"/></td>
    </tr>
    <tr>
        <td>&nbsp;</td><td>&nbsp;</td>
    </tr>
    <tr>
        <td align="right"><comm:message key='unified.select.explain'/>:</td>
        <td><select id="explainContent" name="explainContents">
			<c:forEach var="c_accessories" items="${basecode['accessoriesType']}">
				<c:if test="${ c_accessories.id != 2}">
					<option value="${c_accessories.id}" selected="selected">${c_accessories.name}</option>
				</c:if>
			</c:forEach>               
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="shoudong" name="shoudong" maxlength="20" style="display:none;" type="text" size="10"/>
        </td>
    </tr>
    <tr>
        <td>&nbsp;</td><td>&nbsp;</td>
    </tr>    
    <tr>
    <td colspan="2" align="center"><font color="red"><comm:message key='unified.need.file.size'/></font></td>
    </tr>
     <tr>
    	<td>&nbsp;</td><td>&nbsp;</td>
    </tr>
    <tr>
    <td colspan='2' align="center"><input type="submit" value="<comm:message key='unified.sure'/>"></td>
    </tr>
    </table>
    </form>
  </body>
</html>
