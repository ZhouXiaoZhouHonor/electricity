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
	var pileOption={
		    title: {
		        text: '动态监测充电桩'
		    },
		    tooltip: {
		        trigger: 'axis',
		        formatter: function (params) {
		            params = params[0];
		            var date = new Date(params.name);
		            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
		        },
		        axisPointer: {
		            animation: false
		        }
		    },
		    xAxis: {
		        type: 'time',
		        splitLine: {
		            show: false
		        }
		    },
		    yAxis: {
		        type: 'value',
		        boundaryGap: [0, '100%'],
		        splitLine: {
		            show: false
		        }
		    },
		    series: [{
		        name: '模拟数据',
		        type: 'line',
		        showSymbol: false,
		        hoverAnimation: false,
		        data: data
		    }]
		};
	
	//定义折线图
	function setPileChart(){
		var pileChart=echarts.init($('#pile-watch')[0]);
		pileChart.setOption(pileOption);
		setInterval(function(){
			for(var i=0;i<100;i++){
				data.push(i);
			}
			pileChart.setOption({
		        series: [{
		            data: data
		        }]
		    });
		},5000);
	}
	
	
});