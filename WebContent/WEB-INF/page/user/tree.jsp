<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@page pageEncoding="UTF-8"%>
<html>

<head>
	<style>
		<!--
			body,table,ul,li{
				font-family:tahoma,arial;
				border: 0;
				font-size:12px;
				padding: 0; MARGIN: 0;
			}
			li{white-space:nowrap;}
			img{border: 0; vertical-align: middle;display: inline;}
			td {font-size: 12px}

			A:hover {COLOR: #990000; TEXT-DECORATION: underline}
			A:link {COLOR: #000000; TEXT-DECORATION: underline}
			A:visited {COLOR: #000000;TEXT-DECORATION: underline}
		--> 
	</style>
<script type="text/javascript">
// --- 获取ClassName
document.getElementsByClassName = function(cl) {
var retnode = [];
var myclass = new RegExp('\\b'+cl+'\\b');
var elem = this.getElementsByTagName('*');
for (var j = 0; j < elem.length; j++) {
var classes = elem[j].className;
if (myclass.test(classes)) retnode.push(elem[j]);
}
return retnode;
};

function mymousemove(srcElement){
  srcElement.style.backgroundColor="#fee8cd";
}

/* 鼠标移出背景变换 */
function mymouseout(srcElement){
  if(srcElement.className=="menuItem_selected"){
    srcElement.style.backgroundColor="#FCDDB8";
  }else{
    srcElement.style.backgroundColor="#ffffff";
  }
}

function changeIconMenu(menuId){
  var p = document.getElementById("Child_node"+menuId);
  p.className="menuItem_selected";

  var items = document.getElementsByClassName("menuItem_selected");
  for(var j=0; j<items.length; j++){
    if(items[j].id != ("Child_node"+menuId)){
      items[j].className="menuItem";
      items[j].style.backgroundColor="#ffffff";
    }
  }
}
</script>
</head>

<body><font size="1"><br></font>
<comm:tree treeName='deptTree' href='/user/list' target="Report" expandLevel='1'>
	<comm:treeRoot id='${deptId}' name='${deptName}' nodeNo='${deptNodeCode}' showHref='true' />
	<comm:treeStyle imagesDir='/resources/images/tree' showLine='true' />
</comm:tree>
</body>
</html>