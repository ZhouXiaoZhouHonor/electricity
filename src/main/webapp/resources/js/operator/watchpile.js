/**
 * 
 */
layui.use(['jquery','table','layer','element'],function(){
	var $=layui.jquery;
	var form=layui.form;
	var table=layui.table;
	var element=element;
	//定义总体样式
	var html=''+
		'<div id="phoneUserInfo" class="layui-container">'+
			'<div class="layui-row">'+
				'<div class="layui-col-md3" style="height:40px;">'+
					'<div style="float:left;"><i style="font-size:40px;" class="layui-icon layui-icon-date"></i></div>'+
					'<div id="watch-pile-date" style="text-align:center;font-size:25px;float:left;height:40px;width:120px;line-height:45px;"></div>'+
				'</div>'+
				'<div class="layui-col-md9">'+
		      		/*'你的内容 3/12'+*/
		      	'</div>'+
			'</div>'+
			'<div class="layui-row" style="height:400px;overflow:auto;">'+
				'<div style="width:1100px;height:400px;" id="pile-watch-hz" class="layui-col-md12">'+
				'</div>'+
				'<div style="width:1100px;height:400px;" id="pile-watch-v" class="layui-col-md12">'+
				'</div>'+
				'<div style="width:1100px;height:400px;" id="pile-watch-a" class="layui-col-md12">'+
				'</div>'+
			'</div>'+
		'</div>'+
		'<div class="layui-row">'+
			'<div class="layui-col-md12">'+
				'<button type="button" class="layui-btn layui-btn-fluid layui-btn-normal">打印报表</button>'+
			'</div>'+
		'</div>';
	//定义函数
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		exports('watchPile',function(){
			//将布局样式先插入
			$('#container').html(html);
			layui.use('laydate', function(){
				var laydate = layui.laydate;
				//执行一个laydate实例
				laydate.render({
				    elem: '#watch-pile-date' //指定元素
				});
			});
			//将电压，电流等数据同时在三个图表上进行显示
			setPileChart();
		});
	});
	
	//折线图时间轴
	var time=[];
	//频率
	var electricityHz=[];
	var pileHz=[];
	var pileChartHz;
	//电压
	var electricityV=[];
	var pileV=[];
	var pileChartV;
	//电流
	var electricityA=[];
	var pileA=[];
	var pileChartA;
	//定义折线图
	function setPileChart(){
		//设置今天日期，仅仅显示年月日
		$('#watch-pile-date').html(getDate());
		pileChartHz=echarts.init($('#pile-watch-hz')[0]);
		pileChartV=echarts.init($('#pile-watch-v')[0]);
		pileChartA=echarts.init($('#pile-watch-a')[0]);
		//设置定时获取数据，每隔2秒钟执行一次
		//setInterval(function(){getResult()},2000);
	}
	
	function getResult(){
		var getPileElectricityUrl='/zhou/watchpileelectricity/watchpile';
		$.ajax({
			url:getPileElectricityUrl,
			type:'GET',
			success:function(data){
				if(data.success){
					var result=data.resultList;
					console.log('进来了');
					console.log(result[0].electricityHz);
					console.log(result[0].pileHz);
					console.log('时间:'+getDate());
					//频率
					electricityHz.push(result[0].electricityHz);
					pileHz.push(result[0].pileHz);
					//电压
					electricityV.push(result[0].electricityV);
					pileV.push(result[0].pileV);
					//电流
					electricityA.push(result[0].electricityA);
					pileA.push(result[0].pileA);
					time.push(getTime());
					console.log('频率数组长度:'+electricityHz.length);
					var a='';
					for(var i=0;i<electricityHz.length;i++){
						a+=electricityHz[i]+',';
					}
					console.log('当前数组内容为:'+a);
					pileChartHz.setOption({
						title: {
							text: '动态监测充电桩-频率'
						},
					    xAxis: {
					        type: 'category',
					        axisLabel:{interval: 0},
					        data:time
					    },
					    series: [{
					        name: '频率',
					        type: 'line',
					        data:electricityHz
					    },
					    {
					    	name:'桩频率',
					    	type:'line',
					    	data:pileHz,
					    	lineStyle:{
				                color:'green',
				            }
					    }],
					    yAxis: {
					    	type:'value',
					    	min:49.55,
				            max:50.20,
				            axisLabel:{                   
				                 formatter: function (value, index) {           
				                	 return value.toFixed(2);      
				                 }                
				             }
					    },
					});
					//电压
					pileChartV.setOption({
						title: {
							text: '动态监测充电桩-电压'
						},
					    xAxis: {
					        type: 'category',
					        axisLabel:{interval:0},
					        data:time
					    },
					    series: [{
					        name: '电压',
					        type: 'line',
					        data:electricityV
					    },
					    {
					    	name:'桩电压',
					    	type:'line',
					    	data:pileV,
					    	lineStyle:{
				                color:'green',
				            }
					    }],
					    yAxis: {
					    	type:'value',
					    	min:230.00,
				            max:245.00,
				            axisLabel:{                   
				                 formatter: function (value, index) {           
				                	 return value.toFixed(2);      
				                 }                
				             }
					    },
					});
					//电流
					pileChartA.setOption({
						title: {
							text: '动态监测充电桩-电流'
						},
					    xAxis: {
					        type: 'category',
					        axisLabel:{interval: 0},
					        data:time
					    },
					    series: [{
					        name: '电流',
					        type: 'line',
					        data:electricityA
					    },
					    {
					    	name:'桩电流',
					    	type:'line',
					    	data:pileA,
					    	lineStyle:{
				                color:'green',
				            }
					    }],
					    yAxis: {
					    	type:'value',
					    	min:0.00,
				            max:0.60,
				            axisLabel:{                   
				                 formatter: function (value, index) {           
				                	 return value.toFixed(2);      
				                 }                
				             }
					    },
					});
				}else{
					layer.msg('系统出现异常，退出监测画面');
				}
			}
		});
	}
	
	//定义时间格式,在坐标轴上只显示时分秒就可以了
	function getTime() {
		var myDate = new Date();
		var hour=myDate.getHours();    // 获取当前小时数(0-23)
		var minute=myDate.getMinutes();   // 获取当前分钟数(0-59)
		var second=myDate.getSeconds();   // 获取当前秒数(0-59)
		return hour+":"+minute+":"+second;
    };
    //获取年月日
    function getDate(){
    	var myDate = new Date();
		var year=myDate.getFullYear();    // 获取当前年份(2位)
		var month=myDate.getMonth()+1;    // 获取当前月份(0-11,0代表1月)
		var day=myDate.getDate();    // 获取当前日(1-31)
		return year+'-'+month+'-'+day;
    }
});


