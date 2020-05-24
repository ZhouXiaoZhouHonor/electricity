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
				'<div style="width:350px;height:300px;border:1px solid black;" id="user-number" class="layui-col-md12">'+
				'</div>'+
			'</div>';
		'</div>';
	//定义函数
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		exports('phoneUserList',function(){
			//将布局样式先插入
			$('#container').html(html);
			//获取所有手机用户
			getPhoneUser();
		});
	});
	
	//定义用户数量显示图表
	// 指定图表的配置项和数据
    var userNumberOption = {
        title: {
            text: '手机用户数量',
            left: 'center'
        },
        legend: {
            orient: 'vertical',
            left: 10,
            data: ['总人数', '在线人数']
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [{
            name: '',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };
	function getPhoneUser(){
		var allUser,onlineUser;
		var getAllUserUrl='/zhou/operator/getusernumber';
		$.getJSON(getAllUserUrl,function(data){
			if(data.success){
				allUser=data.count;
				console.log('获得总数为:'+allUser);
			}else{
				layer.msg('没有获取到数据1');
				return;
			}
		});
		var getAllUserUrl1='/zhou/operator/getusernumber?userOnline=1';
		$.getJSON(getAllUserUrl1,function(data){
			if(data.success){
				onlineUser=data.count;
				console.log("在线人数:"+onlineUser);
				var userNumberEchart=echarts.init($('#user-number').get(0));
				userNumberEchart.setOption(userNumberOption);
				userNumberEchart.setOption({
					series:[{
						data:[{name:'总人数',value:allUser},
							{name:'在线人数',value:onlineUser}]
					}]
				});
			}else{
				layer.msg('没有获取到数据2');
				return;
			}
		});
		//console.log('修饰图表');
	}
});