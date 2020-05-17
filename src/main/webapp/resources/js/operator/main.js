/*
 * 
 * */

layui.use(['element','jquery','layer'], function(){
  var element = layui.element;
  var $=layui.jquery;
  var myJquery=layui.jquery;
  var layer=layui.layer;
  
  //首页管理，点击首页显示刚进入网站的页面
  $('#main-operator').on('click',function(){
	  window.location.href='/zhou/page/main';
  });
  
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
  
  //手机用户数量显示
  $('#phoneUser-message').on('click',function(){
	  layui.phoneUserList();
  });
  
  //点击团队，显示团队信息
  $('#team-introduce').on('click',function(){
	  //alert('点击了信息框');
	  layui.teamIntroduce();
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

//定义信息窗体函数
function makeInfoWindow(pile,map){
	var title=pile.pileName;//信息窗体的标题
	var content=[];//信息窗体的主体内容
	//使用layui对内容进行布局
	var infoWindow_layui=''+
	'<div class="layui-fluid">'+
		'<div class="layui-row">'+
			'<div class="layui-col-md7">'+
				'<img id="infoWindow-pileImage" src="'+pile.pileImg+'"/>'+
			'</div>'+
			'<div class="layui-col-md5">'+
				'<div class="layui-row infowindow-font">'+
					'管理员:'+pile.operator.operatorName+
				'</div>'+
				'<div class="layui-row infowindow-font">'+
					'运行状态:'+(pile.pileEnableStatus==1?'<span style="color:#00ff33;font-size:16px;">良好</span>':'<span style="color:red;font-size:16px;">故障</span>')+
				'</div>'+
			'</div>'+
		'</div>'+
		'<div class="layui-row infowindow-font">'+
			'地址:'+pile.pileAddr+
		'</div>'+
		'<div class="layui-row infowindow-font" >'+
			'<button type="button" class="layui-btn layui-btn-radius layui-btn-normal">'+
				'<i class="iconfont layui-icon-extendshishijiance-copy-copy"></i>'+
			'</button>'+
		'</div>'+
	'</div> ';
	content.push(infoWindow_layui);
	return  new AMap.InfoWindow({
	    isCustom: true,  //使用自定义窗体
	    size:new AMap.Size(400,200),
	    content:createInfoWindow(title, content.join("<br/>"),map),
	    offset: new AMap.Pixel(16,-45)
	});
}
//构建自定义信息窗体
function createInfoWindow(title,content,map) {
    var info = document.createElement("div");
    info.className= "custom-info input-card content-window-card";

    //可以通过下面的方式修改自定义窗体的宽高
    //info.style.width = "400px";
    // 定义顶部标题
    var top = document.createElement("div");
    var titleD = document.createElement("div");
    var closeX = document.createElement("img");//信息窗体上的‘叉’图片
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
    middle.className="info-middle";
    middle.style.backgroundColor='white';
    middle.innerHTML=content;
    info.appendChild(middle);

    // 定义底部内容
    var bottom=document.createElement("div");
    bottom.className="info-bottom";
    bottom.style.position='relative';
    bottom.style.top='0px';
    bottom.style.margin='0 auto';
    var sharp=document.createElement("img");
    sharp.src="https://webapi.amap.com/images/sharp.png";
    bottom.appendChild(sharp);
    info.appendChild(bottom);
    return info;
}
//关闭信息窗体
/*function closeInfoWindow() {
    map.clearInfoWindow();
}*/

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






