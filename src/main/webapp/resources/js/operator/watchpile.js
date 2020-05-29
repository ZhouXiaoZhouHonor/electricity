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
				'<div style="width:900px;height:400px;border:1px solid black;" id="pile-watch" class="layui-col-md12">'+
				'</div>'+
			'</div>';
		'</div>';
	//定义函数
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		exports('watchPile',function(){
			//将布局样式先插入
			$('#container').html(html);
			//将电压，电流等数据同时在三个图表上进行显示
			setPileChart();
		});
	});
	var electricityHz=[];
	var pileHz=[];
	var hzTime=[];
	var pileChartHz;
	//频率折线图
	/*
	var pileOption={
		    title: {
		        text: '动态监测充电桩-频率'
		    },
		    xAxis: {
		        type: 'category',
		        data:hzTime
		    },
		    series: [{
		        name: '频率',
		        type: 'line',
		        data:electricityHz
		     
		    },
		    {
		    	name: '桩频率',
		        type: 'line',
		        itemStyle : {  
                    normal : {  
                        lineStyle:{  
                            color:'blue'  
                        }  
                    }  
                },  
		        data:pileHz	
		    }],
		    yAxis: {
		        type: 'value',
		        minInterval: 1,
		        max:51,
		        min:49,
		    }
		};*/
	/*
	['15:12:05','15:12:07',
	'15:12:09','15:12:11','15:12:13',
	'15:12:15','15:12:17','15:12:19',
	'15:12:21','15:12:23','15:12:25',
	'15:12:27','15:12:29','15:12:31',
	'15:12:33','15:12:35','15:12:37',
	'15:12:39','15:12:41','15:12:43']
	*/
	  /* [50.02,49.96, 49.98,49.99,49.99,50.01,50.02,
	50.02,50.01,49.97,49.96,49.96,50.02,
	49.96,49.98,49.99,49.99,50.01,50.02,50.02]*/
	/*[50.52,50.76,50.98,49.99,49.99,
	50.01,50.02,50.02,50.01,49.97,49.96,
	49.96,50.02,49.96,49.98,49.99,49.99,50.01,50.02,50.02]*/
	
	//定义折线图
	function setPileChart(){
		pileChartHz=echarts.init($('#pile-watch')[0]);
		//pileChartHz.setOption(pileOption);
		//设置定时获取数据，每隔2秒钟执行一次
		setInterval(function(){getResult()},2000);
	}
	
	function getResult(){
		var getPileElectricityUrl='/zhou/watchpileelectricity/watchpile';
		/*electricityHz.push(50);
		pileHz.push(51);
		hzTime.push(getDate());
		pileChartHz.setOption({
			xAxis:{
				data:hzTime
			},
		    series: [
		    	{data:electricityHz}
		    ]
		});*/
		$.ajax({
			url:getPileElectricityUrl,
			type:'GET',
			/*dataType:'json',*/
			success:function(data){
				if(data.success){
					var result=data.resultList;
					console.log('进来了');
					console.log(result[0].electricityHz);
					console.log(result[0].pileHz);
					console.log('时间:'+getDate());
					electricityHz.push(result[0].electricityHz);
					pileHz.push(result[0].pileHz);
					hzTime.push(getDate());
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
					        data:hzTime
					    },
					    series: [{
					        name: '频率',
					        type: 'line',
					        data:electricityHz
					    }],
					    yAxis: {
					    	type:'value',
					    	min:48.5,
				            max:51,
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
	
	//定义时间格式
	function getDate() {
		var myDate = new Date();
		var year=myDate.getYear();    // 获取当前年份(2位)
		//myDate.getFullYear();  // 获取完整的年份(4位,1970-????)
		var month=myDate.getMonth();    // 获取当前月份(0-11,0代表1月)
		var day=myDate.getDate();    // 获取当前日(1-31)
		//myDate.getDay();     // 获取当前星期X(0-6,0代表星期天)
		//myDate.getTime();    // 获取当前时间(从1970.1.1开始的毫秒数)
		var hour=myDate.getHours();    // 获取当前小时数(0-23)
		var minute=myDate.getMinutes();   // 获取当前分钟数(0-59)
		var second=myDate.getSeconds();   // 获取当前秒数(0-59)
		//myDate.getMilliseconds();  // 获取当前毫秒数(0-999)
		//myDate.toLocaleDateString();   // 获取当前日期
		//var mytime=myDate.toLocaleTimeString();   // 获取当前时间
		//myDate.toLocaleString( );
		return year+"-"+month+"-"+day+"/"+hour+":"+minute+":"+second;
    };
});