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
			//先静态显示运行状态吧，等论文写完在接着改
			setPileChart();
		});
	});
	var data=[];
	var time=[];
	var pileOption={
		    title: {
		        text: '动态监测充电桩-频率'
		    },
		    /*tooltip: {
		        trigger: 'axis',
		        formatter: function (params) {
		            params = params[0];
		            return params.value[1];
		        },
		        axisPointer: {
		            animation: false
		        }
		    },*/
		    xAxis: {
		        type: 'category',
		        data:['15:12:05','15:12:07',
		        	'15:12:09','15:12:11','15:12:13',
		        	'15:12:15','15:12:17','15:12:19',
		        	'15:12:21','15:12:23','15:12:25',
		        	'15:12:27','15:12:29','15:12:31',
		        	'15:12:33','15:12:35','15:12:37',
		        	'15:12:39','15:12:41','15:12:43']
		        /*splitLine: {
		            show: false
		        }*/
		    },
		    yAxis: {
		        type: 'value',
		        minInterval: 1,
		        max:51,
		        min:48,
		        /*boundaryGap: [0, '100%'],
		        splitLine: {
		            show: false
		        },*/
		        /*data:[48,48.5,49,49.5,50,50.5,51]*/
		    },
		    series: [{
		        name: '模拟数据',
		        type: 'line',
		       /* showSymbol: false,
		        hoverAnimation: false,*/
		        data:[50.02,49.96, 49.98,49.99,49.99,50.01,50.02,50.02,50.01,49.97,49.96,49.96,50.02,49.96,49.98,49.99,49.99,50.01,50.02,50.02]
		    }]
		};
	
	//定义折线图
	function setPileChart(){
		var pileChart=echarts.init($('#pile-watch')[0]);
		pileChart.setOption(pileOption);
		var i=0;
		/*
		var timeTask=setInterval(function(){
			data.push(i++);
			time.push(getDate());
			pileChart.setOption({
				xAxis: {
					data:time
				},
		        series: [{
		            data: data
		        }]
		    });
			if(i==10){
				clearInterval(timeTask);
				i=0;
			}
		},1000);
		*/
	}
	
	//定义时间格式
	function getDate(str) {
        if (str == null || str == "") {
            return '';
        }
        var oDate = new Date(str),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay = oDate.getDate(),
            oHour = oDate.getHours(),
            oMin = oDate.getMinutes(),
            oSen = oDate.getSeconds(),
            oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
        return oTime;
    };
});