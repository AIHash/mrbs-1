// simulate firefox to ie 
// 1. window.event               getter
// 2. event.srcElement           getter
// 3. runtimeStyle               getter
// 4. outerHTML                  getter/setter

if(window.addEventListener)
{
	FixPrototypeForGecko();
}

function FixPrototypeForGecko()
{
	window.constructor.prototype.__defineGetter__("event",window_prototype_get_event);
	Event.prototype.__defineGetter__("srcElement",event_prototype_get_srcElement);
	
	HTMLElement.prototype.__defineGetter__("runtimeStyle",element_prototype_get_runtimeStyle);
	
	HTMLElement.prototype.__defineSetter__("outerHTML", setOuterHtml);
    HTMLElement.prototype.__defineGetter__("outerHTML", getOuterHtml);
    HTMLElement.prototype.__defineGetter__("canHaveChildren",canHaveChildren);
}

function element_prototype_get_runtimeStyle()
{
	//return style instead...
	return this.style;
}

function window_prototype_get_event()
{
	return SearchEvent();
}

function event_prototype_get_srcElement()
{
	return this.target;
}

function SearchEvent(){
  var o = arguments.callee.caller;
  var e;
  while(o != null){
   e = o.arguments[0];
   if(e && (e.constructor == Event || e.constructor == MouseEvent)) return e;
   o = o.caller;
  }
  return null;
}



function setOuterHtml(s){
   var range = this.ownerDocument.createRange();
   range.setStartBefore(this);
   var fragment = range.createContextualFragment(s);
   this.parentNode.replaceChild(fragment, this);
};

function getOuterHtml(s){
    var a=this.attributes, str="<"+this.tagName, i=0;for(;i<a.length;i++)
    if(a[i].specified) str+=" "+a[i].name+'="'+a[i].value+'"';
    if(!this.canHaveChildren) return str+" />";
    return str+">"+this.innerHTML+"</"+this.tagName+">";
};

function canHaveChildren(){    
    return!/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
}

if(window.HTMLElement) {
    HTMLElement.prototype.__defineSetter__("outerHTML",function(sHTML){
        var r=this.ownerDocument.createRange();
        r.setStartBefore(this);
        var df=r.createContextualFragment(sHTML);
        this.parentNode.replaceChild(df,this);
        return sHTML;
        });

    HTMLElement.prototype.__defineGetter__("outerHTML",function(){
     var attr;
        var attrs=this.attributes;
        var str="<"+this.tagName.toLowerCase();
        for(var i=0;i<attrs.length;i++){
            attr=attrs[i];
            if(attr.specified)
                str+=" "+attr.name+'="'+attr.value+'"';
            }
        if(!this.canHaveChildren)
            return str+">";
        return str+">"+this.innerHTML+"</"+this.tagName.toLowerCase()+">";
        });
        
 HTMLElement.prototype.__defineGetter__("canHaveChildren",function(){
  switch(this.tagName.toLowerCase()){
            case "area":
            case "base":
         case "basefont":
            case "col":
            case "frame":
            case "hr":
            case "img":
            case "br":
            case "input":
            case "isindex":
            case "link":
            case "meta":
            case "param":
            return false;
        }
        return true;

     });
}