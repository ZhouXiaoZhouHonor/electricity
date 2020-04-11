/**
 * 编写关于验证码点击的响应js。用于更换图片
 */
//根据url获取后面的参数(GET方法参数)
function getQueryString(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");//name代表需要获取的参数名称
	var r=window.location.search.substr(1).match(reg);
	if(r!=null){
		return decodeURIComponent(r[2]);
	}
	return '';
}

//验证码图片的随机生成
function changeVerifyCode(img){
	img.src="../Kaptcha?"+Math.floor(Math.random()*100);
	
}

//需要在卡片显示中，添加时间显示。需要进行格式化
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

//定义通用的图标(地图标识)
function makeIcon(){
	var icon = new AMap.Icon({
        // 图标尺寸
        size: new AMap.Size(25, 34),
        // 图标的取图地址
        //image:'//a.amap.com/jsapi_demos/static/demo-center/icons/dir-marker.png', 
		image:'../resources/images/electricity.png',
        // 图标所用图片大小
        imageSize: new AMap.Size(25, 34),
        // 图标取图偏移量
        imageOffset: new AMap.Pixel(0, 0) 
    });
	return icon;
}