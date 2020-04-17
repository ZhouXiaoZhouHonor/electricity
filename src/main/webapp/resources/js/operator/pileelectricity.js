/**
 * 
 */
//使用layui中的jQuery才可以
layui.use(['jquery','element','layer'],function(){
	var $=layui.jquery;
	var layer=layui.layer;
	
	//先将图表定义好
	var myChart = echarts.init(document.getElementById('main'));
	var xData=[];
	var yData=[];
	// 指定图表的配置项和数据
	var option = {
			title: {
				text: 'ECharts 入门示例'
	        },
	        tooltip: {},
	        legend: {
	            data:['销量']
	        },
	        xAxis: {
	            data:[]//"衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"
	        },
	        yAxis: {},
	        series: [{
	            name: '销量',
	            type: 'bar',
	            data: []//5, 20, 36, 10, 10, 20
	        }]
	};
	//TODO 先把时间写死，用于数据的测试，之后会通过HTML向后台请求数据过程中，获取时间段。
	//主要是为了展示历史记录，可以不需要进行动态的展示，直接将运行的折线图展示出来就可以了。
	var getPileElectricityList='/zhou/watchpileelectricity/getpileelectricity?pileId=23&firstTime=1586959578000&endTime=1586959582000';
	$.getJSON(getPileElectricityList,function(data){
		if(data.success){
			console.log("获取数据成功");
			var pEL=data.pileElectricityList;
			myChart.setOption(option);//将图标显示出来
			var i=0;
			/*setInterval(function(){
				if(i<pEL.length){
					xData.push(new Date(pEL[i].pileElectricityTime).Format("yyyy-MM-dd hh:mm:ss"));
					yData.push(pEL[i].pileElectricityV);
					myChart.setOption({
						xAxis:{
							data:xData
						},
						yAxis:{
							data:yData
						},
						series:[{
							name:'销量',
							data:yData,
							type:'line'
						}]
					});
				}else{
					clearInterval();
				}
				i++;
			},1000);*/
			pEL.map(function(item,index){
				setTimeout(function(){
					xData.push(new Date(item.pileElectricityTime).Format("yyyy-MM-dd"));
					yData.push(item.pileElectricityV);
					myChart.setOption({
						xAxis:{
							data:xData
						},
						yAxis:{
							data:yData
						},
						series:[{
							name:'销量',
							data:yData,
							type:'line'
						}]
					});
				},5000);
			})
		}else{
			layer.msg('没有获取到数据');
		}
	});
});
