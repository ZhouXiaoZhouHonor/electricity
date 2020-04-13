/*
 * 
 * */

layui.use(['element','jquery','layer'], function(){
  var element = layui.element;
  var $=layui.jquery;
  var myJquery=layui.jquery;
  var layer=layui.layer;
  
  //充电桩管理
  $('#pile-message').on('click',function(){
	  //console.log('点击了充电桩详情标签');
	  //调用pilelist.js中的方法pileTable()
	  layui.pileTable();
  });
  //添加充电桩
  $('#pile-add').on('click',function(){
	  window.location.href='/zhou/page/addpile';
  });
  
  //初步加载高德地图,同时添加充电桩坐标点
  mainMapInit(myJquery);
});

//异步方式加载高德地图，同时添加坐标点
function mainMapInit(jquery){
	var $=jquery;
	//定义地图样式
	var map=new AMap.Map('container',{
		resizeEnable: true,
		expandZoomRange:true,
		zoom:13,
	    zooms:[3,20],//级别范围
	    center:[117.254932,31.751945],//中心点坐标
	});
	//异步加载高德地图
	function loadScript() {
		var url='https://webapi.amap.com/maps?v=1.4.15&key=22744305285882a70dbdcad3f5e8fb77&callback=onLoad';
		var jsapi=document.createElement('script');
		jsapi.charset='utf-8';
		jsapi.src=url;
		document.body.appendChild(jsapi);
	}
	window.onreadystatechange=loadScript;
	
	//获取该管理员的所有充电桩
	var getPileListUrl='/zhou/operator/getpilelist';
	$.getJSON(getPileListUrl,function(data){
		if(data.success){
			//创建数据指定长度
			var markers=[];
			var pileList=data.pileList;
			pileList.map(function(item,index){//遍历充电桩，并组装成标点marker
				var icon=makePileIcon();//获取图标
				var marker=makePileMarker(icon,item);
				markers.push(marker);
				//console.log('经纬度'+item.pileLongitude+","+item.pileLatitude);
				//TODO 为消息窗体放置对应的展示内容
				var infoWindow=makeInfoWindow(item,map); 
				//鼠标点击marker弹出自定义的信息窗体
			    AMap.event.addListener(marker, 'click', function () {
			        infoWindow.open(map,marker.getPosition());
			    });
			});
			map.add(markers);////该方法可以添加单个，也可以添加数组对象
		}else{
			
		}
	});
}

//定义图标函数
function makePileIcon(){
	return new AMap.Icon({
        // 图标尺寸
        size:new AMap.Size(25, 34),
        // 图标的取图地址
		image:'../resources/images/electricity.png',
        // 图标所用图片大小
        imageSize:new AMap.Size(25, 34),
    });
}

//定义标点函数
function makePileMarker(icon,pile){
	return new AMap.Marker({
        position:new AMap.LngLat(pile.pileLongitude,pile.pileLatitude),
        icon:icon,
        offset:new AMap.Pixel(-13, -30),
        title:pile.pileAddr
    });
}

//定义信息窗体函数
function makeInfoWindow(pile,map){
	var title=pile.pileName;//信息窗体的标题
	var content=[];//信息窗体的主体内容
	content.push("<img src='http://tpc.googlesyndication.com/simgad/5843493769827749134'>地址：北京市朝阳区阜通东大街6号院3号楼东北8.3公里");
	content.push("电话：010-64733333");
	content.push("<a href='https://ditu.amap.com/detail/B000A8URXB?citycode=110105'>详细信息</a>");
	return  new AMap.InfoWindow({
	    isCustom: true,  //使用自定义窗体
	    content:createInfoWindow(title, content.join("<br/>"),map),
	    offset: new AMap.Pixel(16,-45)
	});
}
//构建自定义信息窗体
function createInfoWindow(title, content,map) {
    var info = document.createElement("div");
    info.className = "custom-info input-card content-window-card";

    //可以通过下面的方式修改自定义窗体的宽高
    //info.style.width = "400px";
    // 定义顶部标题
    var top = document.createElement("div");
    var titleD = document.createElement("div");
    var closeX = document.createElement("img");
    top.className = "info-top";
    titleD.innerHTML = title;
    closeX.src = "https://webapi.amap.com/images/close2.gif";
    //关闭信息窗体
    closeX.onclick=function(){
    	map.clearInfoWindow();
    }
    
    top.appendChild(titleD);
    top.appendChild(closeX);
    info.appendChild(top);

    // 定义中部内容
    var middle = document.createElement("div");
    middle.className = "info-middle";
    middle.style.backgroundColor = 'white';
    middle.innerHTML = content;
    info.appendChild(middle);

    // 定义底部内容
    var bottom = document.createElement("div");
    bottom.className = "info-bottom";
    bottom.style.position = 'relative';
    bottom.style.top = '0px';
    bottom.style.margin = '0 auto';
    var sharp = document.createElement("img");
    sharp.src = "https://webapi.amap.com/images/sharp.png";
    bottom.appendChild(sharp);
    info.appendChild(bottom);
    return info;
}
//关闭信息窗体
/*function closeInfoWindow() {
    map.clearInfoWindow();
}*/








