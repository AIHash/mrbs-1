<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<HEAD>
<TITLE>VLC VIEWER</TITLE>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<style>
	body{ padding:0; margin:0; font-family:"宋体"; color:#FFF;overflow-x: hidden;overflow-y: hidden;}
</style>
<script type="text/javascript">
var vlc = null;
var notPlayedTimes = 0;
//播放器状态检查时间间隔（秒）
var checkPlayerStatusInterval = 30 * 1000;
var times = 0;

function init(){
	if(vlc == null)
		vlc = getVLC("vlc");
	//alert(parent.urlTemp);
	addPlayUrl(parent.urlTemp);
	//30秒检测一次。
	setTimeout("isPlaying()", checkPlayerStatusInterval);	
};

function destroy(){
	doStop();
}

function getVLC(value)
{
	
	var name = "Player";
    if (window.document[name]) 
    {
        return window.document[name];
    }
    if (navigator.appName.indexOf("Microsoft Internet")==-1)
    {
        if (document.embeds && document.embeds[name])
            return document.embeds[name]; 
    }
    else // if (navigator.appName.indexOf("Microsoft Internet")!=-1)
    {
        return document.getElementById(name);
    }
};


function doPause()
{
	if( 3 == vlc.input.state || 4 == vlc.input.state)
	{
		vlc.playlist.togglePause();
	}
}

function doPlay()
{
	vlc.playlist.play();
}

function doStop()
{
	try{
    	vlc.playlist.stop();
	}catch(err){}
};

function onMute()
{
	vlc.audio.toggleMute();    
};


function addPlayUrl(url){
	vlc.playlist.items.clear();
	while( vlc.playlist.items.count > 0 )
    {
        // clear() may return before the playlist has actually been cleared
        // just wait for it to finish its job
    }
	var options = new Array(":rtsp-tcp", ":no-video-title-show", ":no-file-logging");
	var itemId = vlc.playlist.add(url,"",options);
	//var itemId = vlc.playlist.add(url.toLowerCase(),"",options);
    options = [];
	if( itemId != -1 )
    {
        // play MRL
        vlc.playlist.playItem(itemId);
        //var vlcState=''+window.parent.document.frames["Player_2"].vlc.input.state;
        //alert(window.parent.document.frames["Player_2"]);
    }
}

//检测VLC目前是否处于播放状态
function isPlaying()
{
	//times++;
	try
	{
		if (3 == vlc.input.state || 4 == vlc.input.state)	//playing
		{
			notPlayedTimes = 0;
			//debug.innerHTML = '播放状态正常，5秒后再次检测！';
		}
		else
		{
			if (notPlayedTimes >= 2)	//连续2次都是未播放状态
			{
				//doStop();
				//doPlay();
				//debug.innerHTML = '2次检测结果均为未播放，强制播放！';
			}
			else
			{
				notPlayedTimes++;
				//debug.innerHTML = '注意：VLC当前未播放，5秒后再次检测！';
			}
		}

		//playerStatus.innerHTML = "[" + times + "：" + vlc.input.state + "]";
	}
	catch (e)
	{
		//debug.innerHTML = '脚本执行错误：' + e.description;
	}

	//30秒检测一次。
	setTimeout("isPlaying()", checkPlayerStatusInterval);
}
var c = 0;
function checkObj(){
	if(c==0){c++;
		if(confirm("请下载并安装VCL插件！")){
			 if(c == 1){
				 //window.location.href="<%=request.getContextPath()%>/resources/vlc-1.1.10-win32.exe";
				 var filepath = "resources/vlc-1.1.10-win32.exe";
				 var title = "vlc-1.1.10-win32.exe";
				 window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
			 }
		}
	}
	
}
</script>
</HEAD>
<BODY onload="init();" bgcolor="#000000" onunload="destroy();">
<object id="Player" onerror="checkObj()" width="600px" height="400px" classid="CLSID:9BE31822-FDAD-461B-AD51-BE1D1C159921" events="True">
        <param name="MRL" value="">
		<param name="ShowDisplay" value="True" />
        <param name="AutoLoop" value="False" />
		<param name="AutoPlay" value="False" />
		<param name="Volume" value="50" />
		<param name="wmode" value="opaque" />
		<param name="toolbar" value="true" />
		<param name="StartTime" value="0" />
<EMBED pluginspage="http://www.videolan.org"
       type="application/x-vlc-plugin"
       version="VideoLAN.VLCPlugin.2"
       width="100%"
       height="100%"
	   toolbar="true"
       name="Player">
</EMBED>
</object>
</BODY>
</HTML>